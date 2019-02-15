package com.asimbongeni.asie.grmtranslate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Asie on 21/08/2016.
 */
public class BranchNumberActivity extends AppCompatActivity {
    Button setIpAddress, btnIPCancel;
    EditText ipTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_branch_name);
        ipTxt = (EditText) findViewById(R.id.etIPAddressDialog);
        SharedPreferences sharedPreferencesIP = getSharedPreferences("UserCredentials", 0);
        String sharedIP = sharedPreferencesIP.getString("serverAddress", null);
        if (sharedIP != null){
            ipTxt.setText(sharedIP);
        }

        setIpAddress = (Button) findViewById(R.id.btnSetIp);
        btnIPCancel = (Button) findViewById(R.id.btnIPCancel);
        setIpAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newIp = ipTxt.getText().toString();
                SharedPreferences appPrefs = getSharedPreferences("UserCredentials", MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = appPrefs.edit();
                prefsEditor.putString("serverAddress", newIp);
                prefsEditor.commit();
                Intent intent = new Intent(BranchNumberActivity.this, CustomizationActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnIPCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchNumberActivity.this, CustomizationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
