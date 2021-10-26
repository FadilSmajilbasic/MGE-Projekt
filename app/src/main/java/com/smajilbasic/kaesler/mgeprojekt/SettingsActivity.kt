package com.smajilbasic.kaesler.mgeprojekt

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.DialogFragment
import com.smajilbasic.kaesler.mgeprojekt.ColorPickerDialog.ColorPickerDialogListener

/**
 * Color change source: https://stackoverflow.com/a/48517223
 */
class SettingsActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener, View.OnClickListener, ColorPickerDialogListener {
    private var sharedPref: SharedPreferences? = null
    private var editor: Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = getSharedPreferences(Helper.USER_PREFERENCES, MODE_PRIVATE)
        this.sharedPref = sharedPref
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