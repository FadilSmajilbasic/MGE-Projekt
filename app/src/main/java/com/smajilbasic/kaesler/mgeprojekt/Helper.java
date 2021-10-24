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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Helper {


    public static final String COLOR_KEY = "color_set_key";
    protected static final String USER_PREFERENCES = "user_preferences_name";
    protected static final String DARK_MODE_KEY = "dark_mode_setting";
    protected static final String THEME_KEY = "theme_to_set";
    protected static final String HISTORY_FILENAME = "calculator_history.json";

    public static int getThemeId(Application application, SharedPreferences sharedPref) {
        String themeName = sharedPref.getString(THEME_KEY, "Theme.MGEProjekt");
        return application.getResources().getIdentifier(themeName, "style", application.getPackageName());
    }

    public static boolean writeHistoryEntryToFile(Context context, FileEntry entry) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<FileEntry> historyList ;
            try {
                FileInputStream fileInputStream = context.openFileInput(HISTORY_FILENAME);
                historyList = new ArrayList<>(Arrays.asList(mapper.readValue(fileInputStream, FileEntry[].class)));
                fileInputStream.close();
            }catch (IOException e){
                Log.d("MGE.APP","Error occurred while saving entry: " + e.getMessage());
                historyList = new ArrayList<>();
            }

            historyList.add(entry);
            FileOutputStream fileOutputStream = context.openFileOutput(HISTORY_FILENAME, Context.MODE_PRIVATE);
            mapper.writeValue(fileOutputStream, historyList);
            fileOutputStream.close();
            Log.d("MGE.APP", "Data saved ");

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("MGE.APP", "Error: " + e.getMessage());
            Toast.makeText(context, "Unable to create file for history", Toast.LENGTH_LONG).show();

        }
        return false;

    }

}
