// BackgroundColorDialogFragment.java
// Allows user to set the drawing backgroundColor on the DoodleView
package com.deitel.doodlz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

// class for the Select Color dialog
public class BackgroundColorDialogFragment extends DialogFragment {
    private SeekBar alphaSeekBar;
    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;
    private View backgroundColorView;
    private int backgroundColor;

    // create an AlertDialog and return it
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        // create dialog
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        View backgroundColorDialogView = getActivity().getLayoutInflater().inflate(
                R.layout.fragment_background_color, null);
        builder.setView(backgroundColorDialogView); // add GUI to dialog

        // get the backgroundColor SeekBars and set their onChange listeners
        alphaSeekBar = (SeekBar) backgroundColorDialogView.findViewById(
                R.id.alphaSeekBar);
        redSeekBar = (SeekBar) backgroundColorDialogView.findViewById(
                R.id.redSeekBar);
        greenSeekBar = (SeekBar) backgroundColorDialogView.findViewById(
                R.id.greenSeekBar);
        blueSeekBar = (SeekBar) backgroundColorDialogView.findViewById(
                R.id.blueSeekBar);
        backgroundColorView = backgroundColorDialogView.findViewById(R.id.backgroundColorView);

        // register SeekBar event listeners
        alphaSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        redSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        greenSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        blueSeekBar.setOnSeekBarChangeListener(colorChangedListener);

        // use current background color to set SeekBar values
        final DoodleView doodleView = getDoodleFragment().getDoodleView();
        backgroundColor = doodleView.getDrawingColor();
        alphaSeekBar.setProgress(Color.alpha(backgroundColor));
        redSeekBar.setProgress(Color.red(backgroundColor));
        greenSeekBar.setProgress(Color.green(backgroundColor));
        blueSeekBar.setProgress(Color.blue(backgroundColor));

        // set the AlertDialog's message
        builder.setTitle(R.string.title_background_color_dialog);

        // add Set Background Color Button
            builder.setPositiveButton(
                    R.string.button_set_background_color,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.setDrawingBackgroundColor(backgroundColor);
                        }
                    }
            );

        return builder.create(); // return dialog
    }

    // gets a reference to the MainActivityFragment
    private MainActivityFragment getDoodleFragment() {
        return (MainActivityFragment) getFragmentManager().findFragmentById(
                R.id.doodleFragment);
    }

    // tell MainActivityFragment that dialog is now displayed
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MainActivityFragment fragment = getDoodleFragment();

        if (fragment != null)
            fragment.setDialogOnScreen(true);
    }

    // tell MainActivityFragment that dialog is no longer displayed
    @Override
    public void onDetach() {
        super.onDetach();
        MainActivityFragment fragment = getDoodleFragment();

        if (fragment != null)
            fragment.setDialogOnScreen(false);
    }

    // OnSeekBarChangeListener for the SeekBars in the backgroundColor dialog
    private final OnSeekBarChangeListener colorChangedListener =
            new OnSeekBarChangeListener() {
                // display the updated backgroundColor
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {

                    if (fromUser) // user, not program, changed SeekBar progress
                        backgroundColor = Color.argb(alphaSeekBar.getProgress(),
                                redSeekBar.getProgress(), greenSeekBar.getProgress(),
                                blueSeekBar.getProgress());
                    backgroundColorView.setBackgroundColor(backgroundColor);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {} // required

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {} // required
            };
}