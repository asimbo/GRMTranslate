package com.asimbongeni.asie.grmtranslate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditPhoneNumberActivity extends AppCompatActivity {
    Button buttonSave, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phone_number);

        buttonSave = (Button) findViewById(R.id.buttonPhoneNumberSave);
        buttonSave = (Button) findViewById(R.id.buttonPhoneNumberCancel);
        SharedPreferences sharedPreferences = getSharedPreferences("UserCredentials", MODE_PRIVATE);
        String userphone = sharedPreferences.getString("userPhoneNumber", null);
        final EditText etPhoneNumber = (EditText) findViewById(R.id.etEditPhoneNumber);
        etPhoneNumber.setText(userphone);

    }

    public void onPhoneNumberSaveCancel (View view){
        switch (view.getId()){
            case R.id.buttonPhoneNumberSave:
                EditText etPhoneNumber = (EditText) findViewById(R.id.etEditPhoneNumber);
                String phoneNumber = etPhoneNumber.getText().toString();
                if (phoneNumber != ""){
                    SharedPreferences sharedPreferences = getSharedPreferences("UserCredentials", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userPhoneNumber", phoneNumber);
                    editor.commit();
                    Intent intent = new Intent(EditPhoneNumberActivity.this, CustomizationActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(this, "Field is empty", Toast.LENGTH_SHORT).show();
                    break;
                }
            case R.id.buttonPhoneNumberCancel:
                Intent intentCancel = new Intent(EditPhoneNumberActivity.this, CustomizationActivity.class);
                startActivity(intentCancel);
                finish();
                break;
        }
    }
}
