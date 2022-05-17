package com.example.share2dlibgdx;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class MyAlertDialogFragment extends DialogFragment {

    public static final String TITLE = "dataKey";
    private OnYesNoClick yesNoClick;

    public static MyAlertDialogFragment newInstance(String dataToShow) {
        MyAlertDialogFragment frag = new MyAlertDialogFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, dataToShow);
        frag.setArguments(args);
        return frag;
    }

    public void setOnYesNoClick(OnYesNoClick yesNoClick) {
        this.yesNoClick = yesNoClick;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String mDataRecieved = getArguments().getString(TITLE, "defaultTitle");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setMessage("Message to Show")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (yesNoClick != null)
                            yesNoClick.onNoClicked();
                    }
                })

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (yesNoClick != null)
                            yesNoClick.onYesClicked();
                    }
                });
        Dialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        return dialog;

    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }


    public interface OnYesNoClick {
        void onYesClicked();

        void onNoClicked();
    }
}