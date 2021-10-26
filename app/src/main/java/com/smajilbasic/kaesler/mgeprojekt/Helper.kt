package com.smajilbasic.kaesler.mgeprojekt

import android.app.Application
import android.content.*
import android.util.Log
import android.widget.Toast
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException
import java.util.*

object Helper {
    const val COLOR_KEY = "color_set_key"
    const val USER_PREFERENCES = "user_preferences_name"
    const val DARK_MODE_KEY = "dark_mode_setting"
    const val THEME_KEY = "theme_to_set"
    const val HISTORY_FILENAME = "calculator_history.json"
    const val NOTIFICATION_KEY = "notification_key"
    fun getThemeId(application: Application, sharedPref: SharedPreferences?): Int {
        val themeName = sharedPref!!.getString(THEME_KEY, "Theme.MGEProjekt")
        return application.resources.getIdentifier(themeName, "style", application.packageName)
    }

    fun writeHistoryEntryToFile(context: Context, entry: FileEntry?): Boolean {
        try {
            val mapper = ObjectMapper()
            var historyList: ArrayList<FileEntry?>
            try {
                val fileInputStream = context.openFileInput(HISTORY_FILENAME)
                historyList = ArrayList(listOf(*mapper.readValue(fileInputStream, Array<FileEntry>::class.java)))
                fileInputStream.close()
            } catch (e: IOException) {
                Log.d("MGE.APP", "Error occurred while saving entry: " + e.message)
                historyList = ArrayList()
            }
            historyList.add(entry)
            val fileOutputStream = context.openFileOutput(HISTORY_FILENAME, Context.MODE_PRIVATE)
            mapper.writeValue(fileOutputStream, historyList)
            fileOutputStream.close()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("MGE.APP", "Error: " + e.message)
            Toast.makeText(context, "Unable to create file for history", Toast.LENGTH_LONG).show()
        }
        return false
    }
}