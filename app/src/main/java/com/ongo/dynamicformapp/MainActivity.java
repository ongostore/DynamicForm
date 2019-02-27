package com.ongo.dynamicformapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ongo.dynamicformlibrary.DynamicServiceForm;
import com.ongo.dynamicformlibrary.ServiceFormActivity;

import java.io.File;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements DynamicServiceForm.DynamicServiceFormListener{

    Context context;
    DynamicServiceForm dynamicServiceForm;
    String postType = "Posts";
    String jobObj = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        dynamicServiceForm = new DynamicServiceForm(this, R.id.container,"http://149.129.138.145:8081", "46","9989177604@ongo.com");

        findViewById(R.id.launchDynamicFormBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ToDo add the required data
                dynamicServiceForm.getForm(postType, jobObj);
//                startActivity(new Intent(MainActivity.this, ServiceFormActivity.class));
            }
        });
    }

    @Override
    public void onSuccess(HashMap<String, String> dataHashMap, HashMap<String, File> filesHashMap) {

    }
}
