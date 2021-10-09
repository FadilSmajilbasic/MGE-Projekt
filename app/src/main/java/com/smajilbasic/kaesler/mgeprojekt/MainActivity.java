package com.smajilbasic.kaesler.mgeprojekt;

/**
 * Licenses:
 * Calculator icon:
 * Icons by svgrepo.com - https://www.svgrepo.com/svg/8777/calculator
 * <p>
 * All other icons:
 * All emojis designed by OpenMoji (https://openmoji.org/) – the open-source emoji and icon project. License: CC BY-SA 4.0 (https://creativecommons.org/licenses/by-sa/4.0/#)
 */

import static com.smajilbasic.kaesler.mgeprojekt.Helper.updateLocale;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;


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

    }

    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        String lang = getPreferences(MODE_PRIVATE).getString(Helper.LOCALE_VALUE_KEY,"de");

        super.applyOverrideConfiguration(updateLocale(this,lang).getResources().getConfiguration());
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