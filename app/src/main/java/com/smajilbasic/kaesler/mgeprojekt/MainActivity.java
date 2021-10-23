package com.smajilbasic.kaesler.mgeprojekt;

/*
  Licenses:
  Calculator icon:
  Icons by svgrepo.com - https://www.svgrepo.com/svg/8777/calculator
  <p>
  All other icons:
  All emojis designed by OpenMoji (https://openmoji.org/) – the open-source emoji and icon project. License: CC BY-SA 4.0 (https://creativecommons.org/licenses/by-sa/4.0/#)
 */

import static com.smajilbasic.kaesler.mgeprojekt.Helper.DARK_MODE_KEY;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.USER_PREFERENCES;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.getThemeId;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        setTheme(getThemeId(getApplication(), sharedPref));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences.Editor editor = sharedPref.edit();
        fileName = "history.json";

        AppCompatImageButton plusButton = findViewById(R.id.plusButton);
        AppCompatImageButton minusButton = findViewById(R.id.minusButton);
        AppCompatImageButton divideButton = findViewById(R.id.divideButton);
        AppCompatImageButton multiplyButton = findViewById(R.id.multiplyButton);
        AppCompatImageButton settingsButton = findViewById(R.id.settingsButton);
        plusButton.setOnClickListener(this);
        minusButton.setOnClickListener(this);
        divideButton.setOnClickListener(this);
        multiplyButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);


        if (sharedPref.contains(Helper.DARK_MODE_KEY)) {
            if (sharedPref.getBoolean(DARK_MODE_KEY, false)) {
                if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    plusButton.setImageResource(R.drawable.plus_light);
                    minusButton.setImageResource(R.drawable.minus_light);
                    divideButton.setImageResource(R.drawable.multiply_light);
                    multiplyButton.setImageResource(R.drawable.divide_light);
                }
            } else {
                if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    plusButton.setImageResource(R.drawable.minus);
                    minusButton.setImageResource(R.drawable.plus);
                    divideButton.setImageResource(R.drawable.divide);
                    multiplyButton.setImageResource(R.drawable.multiply);
                }
            }
        } else {
            editor.putBoolean(DARK_MODE_KEY, false);
            editor.apply();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.plusButton) {
            Intent intent = new Intent(this, AdditionActivity.class);
            intent.putExtra("fileName", fileName);
            startActivity(intent);
        } else if (id == R.id.divideButton) {
            Intent intent = new Intent(this, DivisionActivity.class);
            intent.putExtra("fileName", fileName);
            startActivity(intent);
        } else if (id == R.id.multiplyButton) {
            Intent intent = new Intent(this, MultiplicationActivity.class);
            intent.putExtra("fileName", fileName);
            startActivity(intent);
        } else if (id == R.id.minusButton) {
            Intent intent = new Intent(this, SubtractionActivity.class);
            intent.putExtra("fileName", fileName);
            startActivity(intent);
        } else if (id == R.id.settingsButton) {
            startActivity(new Intent(this, SettingsActivity.class));
        }  else if (id == R.id.historyButton) {
            Intent intent = new Intent(this, HistoryActivity.class);
            intent.putExtra("fileName", fileName);
            startActivity(intent);
        }
    }
}