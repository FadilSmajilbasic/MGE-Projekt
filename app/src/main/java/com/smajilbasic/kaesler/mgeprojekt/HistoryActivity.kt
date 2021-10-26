package com.smajilbasic.kaesler.mgeprojekt

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException
import java.util.*
import java.util.function.Consumer

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = getSharedPreferences(Helper.USER_PREFERENCES, MODE_PRIVATE)
        setTheme(Helper.getThemeId(application, sharedPref))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        var resultBox: TextView = findViewById(R.id.result)
        resultBox.movementMethod = ScrollingMovementMethod()
        val mapper = ObjectMapper()
        try {
            val fileInputStream = openFileInput(Helper.HISTORY_FILENAME)
            val history = listOf(*mapper.readValue(fileInputStream, Array<FileEntry>::class.java))
            fileInputStream.close()
            history.forEach(Consumer { entry: FileEntry -> resultBox.append(entry.toString()) })
        } catch (e: IOException) {
            e.printStackTrace()
            deleteFile(Helper.HISTORY_FILENAME)
            Log.d("MGE.APP", "Error " + e.message)
        }
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
}