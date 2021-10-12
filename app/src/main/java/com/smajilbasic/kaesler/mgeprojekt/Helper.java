package com.smajilbasic.kaesler.mgeprojekt;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class Helper {


    public static final String LOCALE_VALUE_KEY = "locale";
    public static final String USER_PREFERENCES = "user_preferences_name";
    public static final String DARK_MODE_KEY = "dark_mode_setting";


    public static Context updateLocale(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        context = context.createConfigurationContext(config);
        return context;
    }
}
