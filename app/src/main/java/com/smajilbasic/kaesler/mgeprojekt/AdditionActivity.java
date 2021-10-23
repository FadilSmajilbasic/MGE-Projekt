package com.smajilbasic.kaesler.mgeprojekt;


import static com.smajilbasic.kaesler.mgeprojekt.Helper.USER_PREFERENCES;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.getThemeId;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class AdditionActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultBox;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = getSharedPreferences(USER_PREFERENCES,MODE_PRIVATE);
        setTheme(getThemeId(getApplication(),sharedPref));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);
        findViewById(R.id.calculateButton).setOnClickListener(this);
        resultBox = findViewById(R.id.result);
        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        fileName = extras.getString("fileName");
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.calculateButton) {

            String firstInput = ((EditText) findViewById(R.id.firstNumber)).getText().toString();
            String secondInput = ((EditText) findViewById(R.id.secondNumber)).getText().toString();

            if (!firstInput.isEmpty()) {
                if (!secondInput.isEmpty()) {
                    StringBuilder builder = new StringBuilder();
                    Double firstNumber = Double.valueOf(firstInput);
                    Double secondNumber = Double.valueOf(secondInput);
                    builder.append(firstInput).append(" + ").append(secondInput).append(" = ").append(firstNumber + secondNumber).append("\n");
                    resultBox.append(builder.toString());
                    try {
                        FileOutputStream outputStream = openFileOutput(fileName, MODE_APPEND);
                        outputStream.write(builder.toString().getBytes());
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(this, getString(R.string.second_number_error_message_de), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, getString(R.string.first_number_error_message_de), Toast.LENGTH_LONG).show();
            }
        }
    }
}