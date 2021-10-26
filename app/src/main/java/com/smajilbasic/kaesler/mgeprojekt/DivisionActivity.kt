package com.smajilbasic.kaesler.mgeprojekt

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.math.RoundingMode
import java.text.DecimalFormat

class DivisionActivity : AppCompatActivity(), View.OnClickListener {
    private var resultBox: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = getSharedPreferences(Helper.USER_PREFERENCES, MODE_PRIVATE)
        setTheme(Helper.getThemeId(application, sharedPref))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_division)
        findViewById<View>(R.id.calculateButton).setOnClickListener(this)
        resultBox = findViewById(R.id.result)
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

    override fun onClick(view: View) {
        if (view.id == R.id.calculateButton) {
            val firstInput = (findViewById<View>(R.id.firstNumber) as EditText).text.toString()
            val secondInput = (findViewById<View>(R.id.secondNumber) as EditText).text.toString()
            if (firstInput.isNotEmpty()) {
                if (secondInput.isNotEmpty()) {
                    val builder = StringBuilder()
                    val firstNumber = java.lang.Double.valueOf(firstInput)
                    val secondNumber = java.lang.Double.valueOf(secondInput)
                    val df = DecimalFormat("#.###")
                    val result = firstNumber / secondNumber
                    df.roundingMode = RoundingMode.HALF_UP
                    builder.append(firstInput).append(" / ").append(secondInput).append(" = ").append(df.format(result)).append("\n")
                    resultBox!!.append(builder.toString())
                    Helper.writeHistoryEntryToFile(this, FileEntry(firstNumber.toString(), secondNumber.toString(), df.format(result), '/'))
                } else {
                    Toast.makeText(this, getString(R.string.second_number_error_message_de), Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.first_number_error_message_de), Toast.LENGTH_LONG).show()
            }
        }
    }
}