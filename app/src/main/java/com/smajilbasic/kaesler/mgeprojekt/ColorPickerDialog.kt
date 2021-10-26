package com.smajilbasic.kaesler.mgeprojekt

import android.app.Activity
import android.app.Dialog
import android.content.*
import android.graphics.Color
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ColorPickerDialog : DialogFragment() {
    private var listener: ColorPickerDialogListener? = null

    interface ColorPickerDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment?, color: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity: Activity? = activity
        val builder = AlertDialog.Builder(activity!!)
        val inflater = requireActivity().layoutInflater
        val layout = inflater.inflate(R.layout.color_picker_dialog, activity.findViewById(R.id.dialog_root_element))
        builder.setView(layout)
        val redSeekBar = layout.findViewById<SeekBar>(R.id.red_seek_bar)
        val greenSeekBar = layout.findViewById<SeekBar>(R.id.green_seek_bar)
        val blueSeekBar = layout.findViewById<SeekBar>(R.id.blue_seek_bar)
        var color = 0
        if (arguments != null) {
            color = requireArguments().getInt("color")
        }
        redSeekBar.progress = Color.red(color) / 15
        greenSeekBar.progress = Color.green(color) / 15
        blueSeekBar.progress = Color.blue(color) / 15
        builder.setMessage(R.string.color_picker_dialog_title_de)
                .setPositiveButton(R.string.color_picker_dialog_save_de) { _: DialogInterface?, _: Int ->
                    val colorRead = Color.rgb(
                            redSeekBar.progress * 15,
                            greenSeekBar.progress * 15,
                            blueSeekBar.progress * 15)
                    listener!!.onDialogPositiveClick(this@ColorPickerDialog, colorRead)
                }
                .setNegativeButton(R.string.color_picker_dialog_cancel_de) { _: DialogInterface?, _: Int -> }
        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            context as ColorPickerDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString()
                    + " must implement ColorPickerDialogListener")
        }
    }

    companion object {
        fun newInstance(color: Int): ColorPickerDialog {
            val args = Bundle()
            args.putInt("color", color)
            val colorPickerDialog = ColorPickerDialog()
            colorPickerDialog.arguments = args
            return colorPickerDialog
        }
    }
}