package com.smajilbasic.kaesler.mgeprojekt

import android.app.Activity
import android.app.Dialog
import android.content.*
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


class ColorPickerDialog : DialogFragment(), SeekBar.OnSeekBarChangeListener {
    private var listener: ColorPickerDialogListener? = null
    private var colorPreviewLayout: LinearLayout? = null

    private var redSeekBar : SeekBar? = null
    private var greenSeekBar : SeekBar? = null
    private var blueSeekBar: SeekBar? = null

    interface ColorPickerDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment?, color: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity: Activity? = activity
        val builder = AlertDialog.Builder(activity!!)
        val inflater = requireActivity().layoutInflater
        val layout = inflater.inflate(
            R.layout.color_picker_dialog,
            activity.findViewById(R.id.dialog_root_element)
        )
        builder.setView(layout)
        redSeekBar = layout.findViewById(R.id.red_seek_bar)
        greenSeekBar = layout.findViewById(R.id.green_seek_bar)
        blueSeekBar = layout.findViewById(R.id.blue_seek_bar)

        val redSeekBarInternal = redSeekBar!!
        val greenSeekBarInternal = greenSeekBar!!
        val blueSeekBarInternal = blueSeekBar!!

        colorPreviewLayout = layout.findViewById(R.id.color_preview_space)

        redSeekBarInternal.setOnSeekBarChangeListener(this)
        greenSeekBarInternal.setOnSeekBarChangeListener(this)
        blueSeekBarInternal.setOnSeekBarChangeListener(this)

        var color = 0
        if (arguments != null) {
            color = requireArguments().getInt("color")
        }
        redSeekBarInternal.progress = Color.red(color) / 15
        greenSeekBarInternal.progress = Color.green(color) / 15
        blueSeekBarInternal.progress = Color.blue(color) / 15
        builder.setMessage(R.string.color_picker_dialog_title_de)
            .setPositiveButton(R.string.color_picker_dialog_save_de) { _: DialogInterface?, _: Int ->
                val colorRead = Color.rgb(
                    redSeekBarInternal.progress.times(15),
                    greenSeekBarInternal.progress.times(15),
                    blueSeekBarInternal.progress.times(15)
                )
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
            throw ClassCastException(
                activity.toString()
                        + " must implement ColorPickerDialogListener"
            )
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

    override fun onProgressChanged(view: SeekBar?, progress: Int, fromUser: Boolean) {
//        if (fromUser) {

            val color = Color.rgb(
                redSeekBar!!.progress * 15,
                greenSeekBar!!.progress * 15,
                blueSeekBar!!.progress * 15
            )

            colorPreviewLayout!!.setBackgroundColor(color)
//        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }
}