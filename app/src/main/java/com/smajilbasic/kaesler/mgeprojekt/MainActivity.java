package com.smajilbasic.kaesler.mgeprojekt;

/*
  Licenses:
  Calculator icon:
  Icons by svgrepo.com - https://www.svgrepo.com/svg/8777/calculator
  <p>
  All other icons:
  All emojis designed by OpenMoji (https://openmoji.org/) – the open-source emoji and icon project. License: CC BY-SA 4.0 (https://creativecommons.org/licenses/by-sa/4.0/#)
 */

import static com.smajilbasic.kaesler.mgeprojekt.Helper.DARK_MODE_KEY;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.HISTORY_FILENAME;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.NOTIFICATION_KEY;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.USER_PREFERENCES;
import static com.smajilbasic.kaesler.mgeprojekt.Helper.getThemeId;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String CHANNEL_ID = "MGE.APP.CHANNEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        setTheme(getThemeId(getApplication(), sharedPref));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences.Editor editor = sharedPref.edit();

        AppCompatButton plusButton = findViewById(R.id.plusButton);
        AppCompatButton minusButton = findViewById(R.id.minusButton);
        AppCompatButton divideButton = findViewById(R.id.divideButton);
        AppCompatButton multiplyButton = findViewById(R.id.multiplyButton);
        AppCompatButton settingsButton = findViewById(R.id.settingsButton);
        AppCompatButton historyButton = findViewById(R.id.historyButton);

        plusButton.setOnClickListener(this);
        minusButton.setOnClickListener(this);
        divideButton.setOnClickListener(this);
        multiplyButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        historyButton.setOnClickListener(this);

        if (sharedPref.contains(Helper.DARK_MODE_KEY)) {
            if (sharedPref.getBoolean(DARK_MODE_KEY, false)) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    plusButton.setBackgroundResource(R.drawable.minus);
                    minusButton.setBackgroundResource(R.drawable.plus);
                    divideButton.setBackgroundResource(R.drawable.divide);
                    multiplyButton.setBackgroundResource(R.drawable.multiply);
                    historyButton.setBackgroundResource(R.drawable.history_light);
            } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    plusButton.setBackgroundResource(R.drawable.plus_light);
                    minusButton.setBackgroundResource(R.drawable.minus_light);
                    divideButton.setBackgroundResource(R.drawable.multiply_light);
                    multiplyButton.setBackgroundResource(R.drawable.divide_light);
                    historyButton.setBackgroundResource(R.drawable.history);

            }
        } else {
            editor.putBoolean(DARK_MODE_KEY, false);
            editor.apply();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        if(sharedPref.getBoolean(NOTIFICATION_KEY,true))
            greetingNotification();
    }

    private void greetingNotification() {
        createNotificationChannel();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.calculator_icon)
                .setContentTitle(getString(R.string.Notification_title_de))
                .setContentText(getString(R.string.greeting_notification_text_de) + dateFormat.format(date))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        int notificationId = 123456;
        notificationManager.notify(notificationId, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.plusButton) {
            Intent intent = new Intent(this, AdditionActivity.class);
            startActivity(intent);
        } else if (id == R.id.divideButton) {
            Intent intent = new Intent(this, DivisionActivity.class);
            startActivity(intent);
        } else if (id == R.id.multiplyButton) {
            Intent intent = new Intent(this, MultiplicationActivity.class);
            startActivity(intent);
        } else if (id == R.id.minusButton) {
            Intent intent = new Intent(this, SubtractionActivity.class);
            startActivity(intent);
        } else if (id == R.id.settingsButton) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.historyButton) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        }
    }
}