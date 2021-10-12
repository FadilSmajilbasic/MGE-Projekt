package com.smajilbasic.kaesler.mgeprojekt;

import static com.smajilbasic.kaesler.mgeprojekt.Helper.DARK_MODE_KEY;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.LOCALE_VALUE_KEY;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.USER_PREFERENCES;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.updateLocale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.google.android.material.textview.MaterialTextView;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;


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

        initSpinner();
    }

    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        String lang = getPreferences(MODE_PRIVATE).getString(Helper.LOCALE_VALUE_KEY, "de");

        super.applyOverrideConfiguration(updateLocale(this, lang).getResources().getConfiguration());
    }

    private void initSpinner() {
        Spinner spinner = findViewById(R.id.language_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.languages_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (sharedPref.contains(LOCALE_VALUE_KEY)) {
            String currentLocale = sharedPref.getString(LOCALE_VALUE_KEY, "de");
            if (currentLocale.equals("en")) {
                spinner.setSelection(adapter.getPosition("English"));
            } else if (currentLocale.equals("it")) {
                spinner.setSelection(adapter.getPosition("Italian"));
            } else {
                spinner.setSelection(adapter.getPosition("German"));
            }
        }

        spinner.setOnItemSelectedListener(this);
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String lang;
        if (view != null) {
            String selected = ((Spinner) findViewById(R.id.language_spinner)).getSelectedItem().toString();
            switch (selected) {
                default:
                case "English":
                    lang = "en";
                    break;
                case "Italian":
                    lang = "it";
                    break;
                case "German":
                    lang = "de";
                    break;
            }
            editor.putString(LOCALE_VALUE_KEY, lang);
            editor.apply();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}