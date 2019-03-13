package com.ongo.dynamicformapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ongo.dynamicformlibrary.DynamicServiceForm;
import com.ongo.dynamicformlibrary.utils.FormAlertDialogsUtils;

public class MainActivity extends AppCompatActivity implements DynamicServiceForm.DynamicServiceFormListener {

    Context context;
    DynamicServiceForm dynamicServiceForm;
    String postType = "Posts";
    String jobObj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        /* ===========
        Initializing the Dynamic Service Form
        =========== */
        dynamicServiceForm = new DynamicServiceForm(this, R.id.container, "http://149.129.138.145:8081", "46", "9989177604@ongo.com", this);

        findViewById(R.id.launchDynamicFormBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ToDo add the required data
                 /* ===========
                 getting and setting the form by using post type
                 jobObj is for editing. and it can be null
                 =========== */
                dynamicServiceForm.getForm(postType,"CAMPAIGNS", jobObj);
//                startActivity(new Intent(MainActivity.this, ServiceFormActivity.class));
            }
        });
    }

    /* ===========
    after clicking on submit bottom all the data and images
    will be update in server and set status here.
    =========== */
    @Override
    public void onSuccess(String status, String result) {
        if (status.equalsIgnoreCase("1")) {
            FormAlertDialogsUtils.showCustomAlertDialog(context, com.ongo.dynamicformlibrary.R.layout.thank_you_dialog_fragment, false, new FormAlertDialogsUtils.CustomAlertInterface() {
                @Override
                public void setListenerCustomAlert(View alertView, final AlertDialog alertDialog) {
//                    ImageView cancel_action = alertView.findViewById(R.id.cancel_action);
                    TextView okTV = alertView.findViewById(com.ongo.dynamicformlibrary.R.id.okTV);
//                    TextView callHelpline = alertView.findViewById(R.id.callHelpline);
                    okTV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            ((Activity) context).onBackPressed();
                        }
                    });
                    alertDialog.show();
                }
            });
        }

    }
}
