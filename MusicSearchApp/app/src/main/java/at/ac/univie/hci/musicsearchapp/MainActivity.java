package at.ac.univie.hci.musicsearchapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        SearchFragment searchFragment = new SearchFragment();
        FavoritesFragment favoritesFragment = new FavoritesFragment();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.search_page:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
                    break;
                case R.id.favorites_page:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, favoritesFragment).commit();
                    break;
            }
            return true;
        });

        bottomNavigationView.setSelectedItemId(R.id.search_page);
    }

}