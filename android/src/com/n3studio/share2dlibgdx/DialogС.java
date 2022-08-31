package com.n3studio.share2dlibgdx;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

public class DialogС {
    Dialog dialog;
    AlertDialog.Builder alertDialog;
    String title;
    String textButtonP;
    String textButtonN;


    public DialogС(Context context) {
        this.title = title;
        this.textButtonP = textButtonP;
        this.textButtonN = textButtonN;
//        dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        alertDialog = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
//        View view = LayoutInflater.from(context).inflate(R.layout.dialog, (ConstraintLayout)findViewById(R.id.dialogC));
    }

    public void show() {
//        dialog.show();
    }

}
