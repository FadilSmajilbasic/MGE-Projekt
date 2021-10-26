package com.smajilbasic.kaesler.mgeprojekt

import android.app.Application
import com.smajilbasic.kaesler.mgeprojekt.FileEntry
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.ArrayList
import java.io.FileInputStream
import java.util.Arrays
import java.io.IOException
import android.util.Log
import java.io.FileOutputStream
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.os.Bundle
import com.smajilbasic.kaesler.mgeprojekt.R
import android.content.SharedPreferences.Editor
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.app.AppCompatDelegate
import com.smajilbasic.kaesler.mgeprojekt.MainActivity
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.os.Build
import android.app.NotificationManager
import android.app.NotificationChannel
import com.smajilbasic.kaesler.mgeprojekt.AdditionActivity
import com.smajilbasic.kaesler.mgeprojekt.DivisionActivity
import com.smajilbasic.kaesler.mgeprojekt.MultiplicationActivity
import com.smajilbasic.kaesler.mgeprojekt.SubtractionActivity
import com.smajilbasic.kaesler.mgeprojekt.SettingsActivity
import com.smajilbasic.kaesler.mgeprojekt.HistoryActivity
import android.widget.TextView
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import android.widget.EditText
import java.lang.StringBuilder
import java.math.RoundingMode
import android.widget.CompoundButton
import com.smajilbasic.kaesler.mgeprojekt.ColorPickerDialog.ColorPickerDialogListener
import androidx.appcompat.widget.SwitchCompat
import com.smajilbasic.kaesler.mgeprojekt.ColorPickerDialog
import android.graphics.Color
import java.lang.Runnable
import android.content.pm.PackageManager
import android.app.Dialog
import android.app.Activity
import android.content.*
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import java.lang.ClassCastException

/**
 * Color change source: https://stackoverflow.com/a/48517223
 */
class SettingsActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener, View.OnClickListener, ColorPickerDialogListener {
    var sharedPref: SharedPreferences? = null
    private var editor: Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = getSharedPreferences(Helper.USER_PREFERENCES, MODE_PRIVATE)
        this.sharedPref = sharedPref;
        setTheme(Helper.getThemeId(application, sharedPref))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        editor = sharedPref.edit()
        val darkModeSetting = sharedPref.getBoolean(Helper.DARK_MODE_KEY, false)
        val notificationSetting = sharedPref.getBoolean(Helper.NOTIFICATION_KEY, true)
        val themeSwitch = findViewById<SwitchCompat>(R.id.theme_switch)
        val notificationSwitch = findViewById<SwitchCompat>(R.id.notification_switch)
        themeSwitch.isChecked = darkModeSetting
        notificationSwitch.isChecked = notificationSetting
        themeSwitch.setOnCheckedChangeListener(this)
        notificationSwitch.setOnCheckedChangeListener(this)
        findViewById<View>(R.id.color_picker_button).setOnClickListener(this)
        findViewById<View>(R.id.notification_switch).setOnClickListener(this)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCheckedChanged(compoundButton: CompoundButton, isChecked: Boolean) {
        if (compoundButton.id == R.id.theme_switch) {
            if (isChecked) {
                editor!!.putBoolean(Helper.DARK_MODE_KEY, true)
                editor!!.apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                editor!!.putBoolean(Helper.DARK_MODE_KEY, false)
                editor!!.apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        } else if (compoundButton.id == R.id.notification_switch) {
            editor!!.putBoolean(Helper.NOTIFICATION_KEY, isChecked)
            editor!!.apply()
        }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.color_picker_button) {
            val colorPickerDialog: ColorPickerDialog = ColorPickerDialog.newInstance(sharedPref!!.getInt(Helper.COLOR_KEY, 0))
            colorPickerDialog.show(supportFragmentManager, "ColorPickerFragment")
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment?, colors: Int) {
        editor!!.putInt(Helper.COLOR_KEY, colors)
        editor!!.apply()
        var themeName = "T_" + String.format("%02X", Color.red(colors)) + String.format("%02X", Color.green(colors)) + String.format("%02X", Color.blue(colors))
        themeName = themeName.lowercase()
        editor!!.putString(Helper.THEME_KEY, themeName)
        editor!!.apply()
        Toast.makeText(applicationContext, R.string.application_restart_toast, Toast.LENGTH_SHORT).show()
        val handler = Handler()
        val r = Runnable { triggerRebirth(applicationContext) }
        handler.postDelayed(r, 2000)
    }

    companion object {
        /**
         * source: https://stackoverflow.com/a/46848226
         *
         * @param context the context of the application
         */
        fun triggerRebirth(context: Context) {
            val packageManager = context.packageManager
            val intent = packageManager.getLaunchIntentForPackage(context.packageName)
            val componentName = intent!!.component
            val mainIntent = Intent.makeRestartActivityTask(componentName)
            context.startActivity(mainIntent)
            Runtime.getRuntime().exit(0)
        }
    }
}