package com.smajilbasic.kaesler.mgeprojekt

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/*
  Licenses:
  Calculator icon:
  Icons by svgrepo.com - https://www.svgrepo.com/svg/8777/calculator
  <p>
  All other icons:
  All emojis designed by OpenMoji (https://openmoji.org/) – the open-source emoji and icon project. License: CC BY-SA 4.0 (https://creativecommons.org/licenses/by-sa/4.0/#)
 */

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = getSharedPreferences(Helper.USER_PREFERENCES, MODE_PRIVATE)
        setTheme(Helper.getThemeId(application, sharedPref))
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val editor = sharedPref.edit()

        val plusButton = findViewById<AppCompatButton>(R.id.plusButton)
        val minusButton = findViewById<AppCompatButton>(R.id.minusButton)
        val divideButton = findViewById<AppCompatButton>(R.id.divideButton)
        val multiplyButton = findViewById<AppCompatButton>(R.id.multiplyButton)
        val settingsButton = findViewById<AppCompatButton>(R.id.settingsButton)
        val historyButton = findViewById<AppCompatButton>(R.id.historyButton)

        plusButton.setOnClickListener(this)
        minusButton.setOnClickListener(this)
        divideButton.setOnClickListener(this)
        multiplyButton.setOnClickListener(this)
        settingsButton.setOnClickListener(this)
        historyButton.setOnClickListener(this)

        if (sharedPref.contains(Helper.DARK_MODE_KEY)) {
            if (sharedPref.getBoolean(Helper.DARK_MODE_KEY, false)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                plusButton.setBackgroundResource(R.drawable.plus)
                minusButton.setBackgroundResource(R.drawable.minus)
                divideButton.setBackgroundResource(R.drawable.divide)
                multiplyButton.setBackgroundResource(R.drawable.multiply)
                historyButton.setBackgroundResource(R.drawable.history_light)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                plusButton.setBackgroundResource(R.drawable.plus_light)
                minusButton.setBackgroundResource(R.drawable.minus_light)
                divideButton.setBackgroundResource(R.drawable.divide_light)
                multiplyButton.setBackgroundResource(R.drawable.multiply_light)
                historyButton.setBackgroundResource(R.drawable.history)
            }
        } else {
            editor.putBoolean(Helper.DARK_MODE_KEY, false)
            editor.apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        if (sharedPref.getBoolean(Helper.NOTIFICATION_KEY, true)) greetingNotification()
    }

    private fun greetingNotification() {
        createNotificationChannel()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val date = Calendar.getInstance().time
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", resources.configuration.locales.get(0))
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.calculator_icon)
                .setContentTitle(getString(R.string.Notification_title_de))
                .setContentText(getString(R.string.greeting_notification_text_de) + dateFormat.format(date))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
        val notificationManager = NotificationManagerCompat.from(this)
        val notificationId = 123456
        notificationManager.notify(notificationId, builder.build())
    }

    private fun createNotificationChannel() {
        val name: CharSequence = getString(R.string.channel_name)
        val description = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)

    }

    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.plusButton) {
            val intent = Intent(this, AdditionActivity::class.java)
            startActivity(intent)
        } else if (id == R.id.divideButton) {
            val intent = Intent(this, DivisionActivity::class.java)
            startActivity(intent)
        } else if (id == R.id.multiplyButton) {
            val intent = Intent(this, MultiplicationActivity::class.java)
            startActivity(intent)
        } else if (id == R.id.minusButton) {
            val intent = Intent(this, SubtractionActivity::class.java)
            startActivity(intent)
        } else if (id == R.id.settingsButton) {
            startActivity(Intent(this, SettingsActivity::class.java))
        } else if (id == R.id.historyButton) {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        private const val CHANNEL_ID = "MGE.APP.CHANNEL"
    }
}