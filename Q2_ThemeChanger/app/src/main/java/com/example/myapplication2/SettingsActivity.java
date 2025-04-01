package com.example.myapplication2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    private RadioGroup themeGroup;
    private RadioButton lightThemeButton, darkThemeButton;
    private Button saveButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize UI components
        themeGroup = findViewById(R.id.themeGroup);
        lightThemeButton = findViewById(R.id.lightThemeButton);
        darkThemeButton = findViewById(R.id.darkThemeButton);
        saveButton = findViewById(R.id.saveButton);

        // Initialize SharedPreferences to store theme selection
        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);

        // Check current theme setting and select the appropriate radio button
        int currentTheme = sharedPreferences.getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        if (currentTheme == AppCompatDelegate.MODE_NIGHT_YES) {
            darkThemeButton.setChecked(true);
        } else {
            lightThemeButton.setChecked(true);
        }

        // Set up save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveThemeSelection();
            }
        });
    }

    private void saveThemeSelection() {
        int themeMode;

        // Determine which theme was selected
        if (darkThemeButton.isChecked()) {
            themeMode = AppCompatDelegate.MODE_NIGHT_YES;
        } else {
            themeMode = AppCompatDelegate.MODE_NIGHT_NO;
        }

        // Save the selection to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("theme_mode", themeMode);
        editor.apply();

        // Apply the theme change
        AppCompatDelegate.setDefaultNightMode(themeMode);

        // Recreate the activity to apply changes
        recreate();
    }
}