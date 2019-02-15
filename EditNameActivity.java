package com.asimbongeni.asie.grmtranslate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditNameActivity extends AppCompatActivity {

    Button buttonSave, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);

        buttonSave = (Button) findViewById(R.id.buttonNameSave);
        buttonSave = (Button) findViewById(R.id.buttonNameCancel);
        SharedPreferences sharedPreferences = getSharedPreferences("UserCredentials", MODE_PRIVATE);
        String username = sharedPreferences.getString("userName", null);
        final EditText etEditUserName = (EditText) findViewById(R.id.etEditUserName);
        etEditUserName.setText(username);
    }

    public void onNameSaveCancel (View view){
        switch (view.getId()){
            case R.id.buttonNameSave:
                EditText etEditUserName = (EditText) findViewById(R.id.etEditUserName);
                String username = etEditUserName.getText().toString();
                if (username != ""){
                    SharedPreferences sharedPreferences = getSharedPreferences("UserCredentials", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userName", username);
                    editor.commit();
                    Intent intent = new Intent(EditNameActivity.this, CustomizationActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                else {
                    Toast.makeText(this, "Field is empty", Toast.LENGTH_SHORT).show();
                    break;
                }


            case R.id.buttonNameCancel:
                Intent intentCancel = new Intent(EditNameActivity.this, CustomizationActivity.class);
                startActivity(intentCancel);
                finish();
                break;
        }
    }
}
