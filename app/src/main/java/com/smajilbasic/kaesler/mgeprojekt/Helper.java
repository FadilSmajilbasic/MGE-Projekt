package com.smajilbasic.kaesler.mgeprojekt;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Locale;

public class Helper {


    public static final String COLOR_KEY = "color_set_key";
    protected static final String USER_PREFERENCES = "user_preferences_name";
    protected static final String DARK_MODE_KEY = "dark_mode_setting";
    protected static final String THEME_KEY = "theme_to_set";
    protected static final String HISTORY_FILENAME = "calculator_history.json";

    public static int getThemeId(Application application, SharedPreferences sharedPref){
        String themeName = sharedPref.getString(THEME_KEY,"Theme.MGEProjekt");
        return application.getResources().getIdentifier(themeName,"style", application.getPackageName());
    }

    public static boolean writeHistoryEntryToFile(FileEntry entry){

        ObjectMapper mapper = new ObjectMapper();

        try {
            File historyFile = new File(HISTORY_FILENAME);
            if(!historyFile.exists()) {
                if(!historyFile.createNewFile()){
                    return false;
                }
            }

            mapper.writeValue(Paths.get(HISTORY_FILENAME).toFile(), entry);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }

}
