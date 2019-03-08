package com.ongo.dynamicformlibrary.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by gufran khan on 30-10-2018.
 */

public class FormAlertDialogsUtils {

    /**
     * @param mContext      Context
     * @param setCancelable dialog cancelable or not
     * @param resource      custom layout resourceId
     *                      <p>
     *                      Example: mContext, R.layout.alert_custom_view, new Utils.CustomAlertInterface()
     */
    public static void showCustomAlertDialog(Context mContext, int resource, boolean setCancelable, CustomAlertInterface customAlertInterface) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View alertView = null;
        if (inflater != null) {
            alertView = inflater.inflate(resource, null);
            builder.setView(alertView);
            final android.support.v7.app.AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(setCancelable);
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            customAlertInterface.setListenerCustomAlert(alertView, alertDialog);
        }
    }


    /**
     * Show AlertDialog
     *
     * @param mContext             context
     * @param mTitle               title to show
     * @param mMessage             message to show
     * @param positiveBtnName      for positive Click
     * @param negativeBtnName      for negative Click
     * @param alertDialogInterface is clickListener to send back the click.
     */
    public static void showAlertDialog(final Context mContext, String mTitle, String mMessage, String positiveBtnName, String negativeBtnName, final AlertDialogInterface alertDialogInterface) {
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(mContext);
        dialog.setTitle(mTitle);
        dialog.setCancelable(false);
        dialog.setMessage(mMessage);
        dialog.setPositiveButton(positiveBtnName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                alertDialogInterface.positiveButton();
            }
        });
        dialog.setNegativeButton(negativeBtnName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                alertDialogInterface.negativeButton();
            }
        });
        dialog.show();
    }





    public interface CustomAlertInterface {
        void setListenerCustomAlert(View alertView, android.support.v7.app.AlertDialog alertDialog);
    }

    public interface AlertDialogInterface {
        void positiveButton();

        void negativeButton();
    }
}
