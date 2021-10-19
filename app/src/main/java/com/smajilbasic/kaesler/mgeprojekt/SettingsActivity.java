package com.smajilbasic.kaesler.mgeprojekt;

import static com.smajilbasic.kaesler.mgeprojekt.Helper.COLOR_KEY;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.DARK_MODE_KEY;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.THEME_KEY;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.USER_PREFERENCES;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.google.android.material.textview.MaterialTextView;

/**
 * Color change source: https://stackoverflow.com/a/48517223
 */
public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, ColorPickerDialog.ColorPickerDialogListener {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    Dialog colorPickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPref = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        editor = sharedPref.edit();

        boolean darkModeSetting = sharedPref.getBoolean(DARK_MODE_KEY, false);

        SwitchCompat themeSwitch = findViewById(R.id.theme_switch);
        themeSwitch.setChecked(darkModeSetting);
        themeSwitch.setOnCheckedChangeListener(this);

        findViewById(R.id.color_picker_button).setOnClickListener(this);

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            editor.putBoolean(DARK_MODE_KEY, true);
            editor.apply();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            editor.putBoolean(DARK_MODE_KEY, false);
            editor.apply();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.color_picker_button) {
            ColorPickerDialog colorPickerDialog = new ColorPickerDialog(sharedPref.getInt(COLOR_KEY, 0));
            colorPickerDialog.show(getSupportFragmentManager(), "ColorPickerFragment");

        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int colors) {

        editor.putInt(Helper.COLOR_KEY, colors);
        editor.apply();

        int rgb = (Color.red(colors) + Color.green(colors) + Color.blue(colors)) / 3;

//        if (rgb > 210) { // Checking if title text color will be black
//            this.setTheme(R.style.AppTheme);
//        }

        String themeName = "T_" + String.format("%x", Color.red(colors))
                + String.format("%x", Color.green(colors))
                + String.format("%x", Color.blue(colors));

        Log.d("MGE.APP","Themen resource " + this.getResources().getIdentifier(themeName,"style", null));
        editor.putString(THEME_KEY,themeName);
        this.recreate();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}