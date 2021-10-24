package com.smajilbasic.kaesler.mgeprojekt;

import static com.smajilbasic.kaesler.mgeprojekt.Helper.HISTORY_FILENAME;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.USER_PREFERENCES;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.getThemeId;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class MultiplicationActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = getSharedPreferences(USER_PREFERENCES,MODE_PRIVATE);
        setTheme(getThemeId(getApplication(),sharedPref));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplication);
        findViewById(R.id.calculateButton).setOnClickListener(this);
        resultBox = findViewById(R.id.result);
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
                    Double result = firstNumber * secondNumber;
                    builder.append(firstInput).append(" * ").append(secondInput).append(" = ").append(result).append("\n");
                    resultBox.append(builder.toString());
                    Helper.writeHistoryEntryToFile(this, new FileEntry(firstNumber.toString(),secondNumber.toString(),result.toString(),'*'));
                } else {
                    Toast.makeText(this, getString(R.string.second_number_error_message_de), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, getString(R.string.first_number_error_message_de), Toast.LENGTH_LONG).show();
            }
        }
    }
}