package ru.mirea.shutov.amlcryptocheck.presentation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.mirea.shutov.amlcryptocheck.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_view);
        bottomNav.setOnItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            replaceFragment(new HistoryFragment());
        }
    }

    private final BottomNavigationView.OnItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                int itemId = item.getItemId();
                if (itemId == R.id.navigation_history) {
                    selectedFragment = new HistoryFragment();
                } else if (itemId == R.id.navigation_profile) {
                    selectedFragment = new ProfileFragment();
                }

                if (selectedFragment != null) {
                    replaceFragment(selectedFragment);
                    return true;
                }
                return false;
            };

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .addToBackStack(null)
                .commit();
    }
}