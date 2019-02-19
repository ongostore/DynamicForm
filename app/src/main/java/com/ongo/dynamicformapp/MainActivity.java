package com.ongo.dynamicformapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ongo.dynamicformlibrary.SaleRegisterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.launchDynamicFormBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ToDo add the required data
                startActivity(new Intent(MainActivity.this, SaleRegisterActivity.class));
            }
        });
    }
}
