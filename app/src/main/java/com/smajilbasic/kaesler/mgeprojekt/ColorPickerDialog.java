package com.smajilbasic.kaesler.mgeprojekt;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ColorPickerDialog extends DialogFragment {


    ColorPickerDialogListener listener;
    int color;

    public interface ColorPickerDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, int color);

        public void onDialogNegativeClick(DialogFragment dialog);
    }

    public ColorPickerDialog(int color) {
        this.color = color;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.color_picker_dialog, (ViewGroup) activity.findViewById(R.id.dialog_root_element));
        builder.setView(layout);

        SeekBar redSeekBar = (SeekBar) layout.findViewById(R.id.red_seek_bar);
        SeekBar greenSeekBar = (SeekBar) layout.findViewById(R.id.green_seek_bar);
        SeekBar blueSeekBar = (SeekBar) layout.findViewById(R.id.blue_seek_bar);

        redSeekBar.incrementProgressBy(15);
        greenSeekBar.incrementProgressBy(15);
        blueSeekBar.incrementProgressBy(15);

        redSeekBar.setProgress(Color.red(color));
        greenSeekBar.setProgress(Color.green(color));
        blueSeekBar.setProgress(Color.blue(color));

        builder.setMessage(R.string.color_picker_dialog_title_de)
                .setPositiveButton(R.string.color_picker_dialog_save_de, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        int color = Color.rgb(
                                redSeekBar.getProgress(),
                                greenSeekBar.getProgress(),
                                blueSeekBar.getProgress());


                        Log.d("MGE.APP", "colors: #" + String.format("%x", color));
                        Log.d("MGE.APP", "color: red" + redSeekBar.getProgress() + " green:" + greenSeekBar.getProgress() + " blue: " + blueSeekBar.getProgress());
                        listener.onDialogPositiveClick(ColorPickerDialog.this, color);
                    }
                })
                .setNegativeButton(R.string.color_pciker_dialog_cancel_de, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(ColorPickerDialog.this);
                    }
                });


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ColorPickerDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement ColorPickerDialogListener");
        }
    }
}
