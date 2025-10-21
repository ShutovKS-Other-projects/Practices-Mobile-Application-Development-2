package ru.mirea.shutov.data.storage.models;

public class Movie {
    private final int id;
    private final String name;
    private final String saveDate;

    public Movie(int id, String name, String saveDate) {
        this.id = id;
        this.name = name;
        this.saveDate = saveDate;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSaveDate() { return saveDate; }
}