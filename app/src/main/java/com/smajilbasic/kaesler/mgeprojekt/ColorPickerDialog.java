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

    public interface ColorPickerDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, int color);

    }

    public ColorPickerDialog() {
    }

    public static ColorPickerDialog newInstance(int color) {
        Bundle args = new Bundle();
        args.putInt("color", color);
        ColorPickerDialog colorPickerDialog = new ColorPickerDialog();
        colorPickerDialog.setArguments(args);
        return colorPickerDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.color_picker_dialog, activity.findViewById(R.id.dialog_root_element));
        builder.setView(layout);

        SeekBar redSeekBar = layout.findViewById(R.id.red_seek_bar);
        SeekBar greenSeekBar = layout.findViewById(R.id.green_seek_bar);
        SeekBar blueSeekBar = layout.findViewById(R.id.blue_seek_bar);
        int color = 0;
        if (getArguments() != null) {
            color = getArguments().getInt("color");
        }
        redSeekBar.setProgress(Color.red(color) / 15);
        greenSeekBar.setProgress(Color.green(color) / 15);
        blueSeekBar.setProgress(Color.blue(color) / 15);

        builder.setMessage(R.string.color_picker_dialog_title_de)
                .setPositiveButton(R.string.color_picker_dialog_save_de, (dialog, id) -> {

                    int colorRead = Color.rgb(
                            (redSeekBar.getProgress() * 15),
                            (greenSeekBar.getProgress() * 15),
                            (blueSeekBar.getProgress()) * 15);
                listener.onDialogPositiveClick(ColorPickerDialog.this, colorRead);

                })
                .setNegativeButton(R.string.color_pciker_dialog_cancel_de, (dialog, id) -> {});

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
