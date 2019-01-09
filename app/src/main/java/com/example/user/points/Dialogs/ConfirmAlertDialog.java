package com.example.user.points.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;


public class ConfirmAlertDialog extends DialogFragment {

    //public String dialogTitle = "";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setMessage("Для закрытия сделай что-нибудь").create();

//        builder.setMessage("Delete item?")
//                .setPositiveButton("Да", (dialog, which) -> {
//                    Toast.makeText(getActivity(), "Deleted item", Toast.LENGTH_SHORT).show();
//                })
//                .setNegativeButton("Нет", (dialog, which) ->{
//                    Toast.makeText(getActivity(), "not deleted item", Toast.LENGTH_SHORT).show();
//                });
//
//        return super.onCreateDialog(savedInstanceState);
    }
}
