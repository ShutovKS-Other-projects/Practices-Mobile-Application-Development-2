#!/bin/bash

OUTPUT_FILE="project_context.txt"

> "$OUTPUT_FILE"

echo "Сборка контекста проекта..."
echo "Дата: $(date)" >> "$OUTPUT_FILE"
echo "========================================" >> "$OUTPUT_FILE"
echo "" >> "$OUTPUT_FILE"

safe_find() {
    local search_path="$1"
    local pattern="$2"

    find "$search_path" \
        \( -path "*/build" -o -path "*/.gradle" -o -path "*/.idea" -o -path "*/.git" -o -path "*/captures" -o -path "*/.cxx" -o -path "*/node_modules" \) -prune \
        -o -type f \( $pattern \) -print | sort
}

add_files() {
    echo "## $1" >> "$OUTPUT_FILE"
    echo "" >> "$OUTPUT_FILE"

    safe_find "." "$2" | while read -r file; do
        echo "### $file" >> "$OUTPUT_FILE"
        echo '```'"$3" >> "$OUTPUT_FILE"
        cat "$file" >> "$OUTPUT_FILE"
        echo "" >> "$OUTPUT_FILE"
        echo '```' >> "$OUTPUT_FILE"
        echo "" >> "$OUTPUT_FILE"
    done
}

echo "## Структура проекта" >> "$OUTPUT_FILE"
echo "" >> "$OUTPUT_FILE"
if command -v tree &> /dev/null; then
    tree -L 4 -I 'build|.gradle|.idea|.git|captures|.cxx|node_modules' >> "$OUTPUT_FILE"
else
    find . -maxdepth 4 \
        -not -path '*/build/*' \
        -not -path '*/.gradle/*' \
        -not -path '*/.idea/*' \
        -not -path '*/.git/*' \
        -not -path '*/node_modules/*' \
        | sort >> "$OUTPUT_FILE"
fi
echo "" >> "$OUTPUT_FILE"
echo "" >> "$OUTPUT_FILE"

echo "Добавляем конфигурационные файлы..."
add_files "Gradle & Config" "-name build.gradle* -o -name settings.gradle* -o -name libs.versions.toml -o -name gradle.properties -o -name proguard-rules.pro" "gradle"

echo "Добавляем манифесты..."
add_files "Manifests" "-name AndroidManifest.xml" "xml"

echo "Добавляем Kotlin файлы..."
add_files "Kotlin файлы" "-name *.kt" "kotlin"

echo "Добавляем Java файлы..."
add_files "Java файлы" "-name *.java" "java"

echo "Добавляем ресурсы..."
safe_find "." "-path */res/values/*.xml -o -path */res/xml/*.xml" | while read -r file; do
    echo "### $file" >> "$OUTPUT_FILE"
    echo '```xml' >> "$OUTPUT_FILE"
    cat "$file" >> "$OUTPUT_FILE"
    echo "" >> "$OUTPUT_FILE"
    echo '```' >> "$OUTPUT_FILE"
    echo "" >> "$OUTPUT_FILE"
done

echo "## Firebase Functions" >> "$OUTPUT_FILE"
echo "" >> "$OUTPUT_FILE"
echo "Добавляем скрипты Firebase..."
find functions \
    \( -path "*/node_modules" \) -prune \
    -o -type f \( -name "*.js" -o -name "*.json" -o -name "*.ts" \) -print | sort | while read -r file; do
        if [[ "$file" == *"package-lock.json" ]]; then continue; fi

        echo "### $file" >> "$OUTPUT_FILE"
        echo '```javascript' >> "$OUTPUT_FILE"
        cat "$file" >> "$OUTPUT_FILE"
        echo "" >> "$OUTPUT_FILE"
        echo '```' >> "$OUTPUT_FILE"
        echo "" >> "$OUTPUT_FILE"
done

echo "## Firebase Rules" >> "$OUTPUT_FILE"
add_files "Firebase Rules" "-name firestore.rules" "rules"

echo "Контекст сохранён в $OUTPUT_FILE"