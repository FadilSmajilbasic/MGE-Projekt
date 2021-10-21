package com.smajilbasic.kaesler.mgeprojekt;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;

import java.util.Locale;

public class Helper {


    public static final String COLOR_KEY = "color_set_key";
    protected static final String USER_PREFERENCES = "user_preferences_name";
    protected static final String DARK_MODE_KEY = "dark_mode_setting";
    protected static final String THEME_KEY = "theme_to_set";

    public static int getThemeId(Application application, SharedPreferences sharedPref){
        String themeName = sharedPref.getString(THEME_KEY,"Theme.MGEProjekt");
        return application.getResources().getIdentifier(themeName,"style", application.getPackageName());
    }

}
