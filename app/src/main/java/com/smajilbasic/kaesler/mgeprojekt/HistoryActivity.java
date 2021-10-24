package com.smajilbasic.kaesler.mgeprojekt;

import static com.smajilbasic.kaesler.mgeprojekt.Helper.HISTORY_FILENAME;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.USER_PREFERENCES;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.getThemeId;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    TextView resultBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        setTheme(getThemeId(getApplication(), sharedPref));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        resultBox = findViewById(R.id.result);
        resultBox.setMovementMethod(new ScrollingMovementMethod());
//        Intent intent = this.getIntent();
//        Bundle extras = intent.getExtras();

//
//        fileName = extras.getString("fileName");
//
//        File folder = getFilesDir();
//        File file = new File(folder, fileName);
//
//        int length = (int) file.length();
//        byte[] bytes = new byte[length];
//        try (FileInputStream inputStream = new FileInputStream(file)) {
//            inputStream.read(bytes);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String output = new String(bytes);
//        append

        ObjectMapper mapper = new ObjectMapper();

        try {

            FileInputStream fileInputStream = openFileInput(HISTORY_FILENAME);

            List<FileEntry> history = Arrays.asList(mapper.readValue(fileInputStream, FileEntry[].class));
            fileInputStream.close();

            history.forEach(entry -> resultBox.append(entry.toString()));

        } catch (IOException e) {
            e.printStackTrace();
            deleteFile(HISTORY_FILENAME);
            Log.d("MGE.APP", "Error " + e.getMessage());
        }
        Log.d("MGE.APP", "Opened activity");

    }
}
