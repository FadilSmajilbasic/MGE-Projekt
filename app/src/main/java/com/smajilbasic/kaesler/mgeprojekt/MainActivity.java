package com.smajilbasic.kaesler.mgeprojekt;

/**
 * Licenses:
 * Calculator icon:
 * Icons by svgrepo.com - https://www.svgrepo.com/svg/8777/calculator
 * <p>
 * All other icons:
 * All emojis designed by OpenMoji (https://openmoji.org/) – the open-source emoji and icon project. License: CC BY-SA 4.0 (https://creativecommons.org/licenses/by-sa/4.0/#)
 */

import static com.smajilbasic.kaesler.mgeprojekt.Helper.DARK_MODE_KEY;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.USER_PREFERENCES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.plusButton).setOnClickListener(this);
        findViewById(R.id.minusButton).setOnClickListener(this);
        findViewById(R.id.divideButton).setOnClickListener(this);
        findViewById(R.id.multiplyButton).setOnClickListener(this);
        findViewById(R.id.settingsButton).setOnClickListener(this);

        SharedPreferences sharedPref = getSharedPreferences(USER_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if (sharedPref.contains(Helper.DARK_MODE_KEY)) {
            if (sharedPref.getBoolean(DARK_MODE_KEY, false)) {
                if(AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                if(AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }else{
            editor.putBoolean(DARK_MODE_KEY,false);
            editor.apply();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plusButton:
                startActivity(new Intent(this, AdditionActivity.class));
                break;
            case R.id.divideButton:
                startActivity(new Intent(this, DivisionActivity.class));
                break;
            case R.id.multiplyButton:
                startActivity(new Intent(this, MultiplicationActivity.class));
                break;
            case R.id.minusButton:
                startActivity(new Intent(this, SubtractionActivity.class));
                break;
            case R.id.settingsButton:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
    }
}