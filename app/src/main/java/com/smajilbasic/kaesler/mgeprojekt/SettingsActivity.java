package com.smajilbasic.kaesler.mgeprojekt;

import static com.smajilbasic.kaesler.mgeprojekt.Helper.COLOR_KEY;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.DARK_MODE_KEY;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.THEME_KEY;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.USER_PREFERENCES;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.getThemeId;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;

/**
 * Color change source: https://stackoverflow.com/a/48517223
 */
public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, ColorPickerDialog.ColorPickerDialogListener {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        setTheme(getThemeId(getApplication(), sharedPref));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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
            ColorPickerDialog colorPickerDialog = ColorPickerDialog.newInstance(sharedPref.getInt(COLOR_KEY, 0));
            colorPickerDialog.show(getSupportFragmentManager(), "ColorPickerFragment");
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int colors) {

        editor.putInt(Helper.COLOR_KEY, colors);
        editor.apply();


        String themeName = "T_" + String.format("%02X", Color.red(colors))
                + String.format("%02X", Color.green(colors))
                + String.format("%02X", Color.blue(colors));
        themeName = themeName.toLowerCase();
        editor.putString(THEME_KEY, themeName);
        editor.apply();
        Toast.makeText(getApplicationContext(), R.string.application_restart_toast, Toast.LENGTH_SHORT).show();

        Handler handler = new Handler();

        Runnable r = () -> triggerRebirth(getApplicationContext());
        handler.postDelayed(r, 2000);
    }

    /**
     * source: https://stackoverflow.com/a/46848226
     *
     * @param context the context of the application
     */
    public static void triggerRebirth(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);

        context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }
}