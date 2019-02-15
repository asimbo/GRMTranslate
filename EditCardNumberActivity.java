package com.asimbongeni.asie.grmtranslate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditCardNumberActivity extends AppCompatActivity {
    Button saveButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cardnumber);

        saveButton = (Button) findViewById(R.id.buttonEmailSave);
        cancelButton = (Button) findViewById(R.id.buttonEmailCancel);
        final EditText etUserEmail = (EditText) findViewById(R.id.etEditEmail);
        SharedPreferences sharedPreferences = getSharedPreferences("UserCredentials", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail", null);
        etUserEmail.setText(userEmail);


    }

    public void onEditEmailSaveCancel (View view){
        switch (view.getId()){
            case R.id.buttonEmailSave:
                EditText etUserEmail = (EditText) findViewById(R.id.etEditEmail);
                String email = etUserEmail.getText().toString();
                if (email != ""){
                    SharedPreferences sharedPreferences = getSharedPreferences("UserCredentials", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userEmail", email);
                    editor.commit();
                    Intent intent = new Intent(this, CustomizationActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                else {
                    Toast.makeText(this, "Field is empty", Toast.LENGTH_SHORT).show();
                    break;
                }
            case R.id.buttonEmailCancel:
                Intent intentCancel = new Intent(this, CustomizationActivity.class);
                startActivity(intentCancel);
                finish();
                break;
        }
    }
}
