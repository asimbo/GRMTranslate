package com.asimbongeni.asie.grmtranslate;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Permission;
import java.util.Objects;

public class CustomizationActivity extends AppCompatActivity {
    private static final int CAPTURE_PROFIMAGE_ACTIVITY_REQUEST_CODE = 150;
    ImageView userProfPic, imageViewProfPicSelect;
    ImageButton imageButtonEditName, imageButtonEditEmail, imageButtonEditPhoneNumber, imageButtonEditIPAddress;
    EditText ipTxt;
    //TextView userName, userEmail, userPhoneNumber;
    private Bitmap bitmap;
    public static final int PICK_PHOTO_REQUEST = 4;
    private Uri fileUri;
    private String PREFS_TEXT;
    private String PREFS_LANG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customization);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("UserCredentials", MODE_PRIVATE);
        //Log.d("ImageUrl", sharedPreferences.getString("userPictureUrl", null));

        if (sharedPreferences.getString("userName", null) != null){
            String myUserName = sharedPreferences.getString("userName", null);
            final TextView userName = (TextView) findViewById(R.id.tvUserName);
            Log.d("S PrefsName", sharedPreferences.getString("userName", null));
            userName.setText(myUserName);
        }
        if (sharedPreferences.getString("userPhoneNumber", null) != null){
            final String myPhoneNumber = sharedPreferences.getString("userPhoneNumber", null);
            Log.d("S PrefsPNumber", sharedPreferences.getString("userPhoneNumber", null));
            TextView userPhoneNumber = (TextView) findViewById(R.id.tvUserPhoneNumber);
            assert myPhoneNumber != null;
            userPhoneNumber.setText(myPhoneNumber);
        }

        if (sharedPreferences.getString("userEmail", null) != null){
            String myUserEmail = sharedPreferences.getString("userEmail", null);
            Log.d("S PrefEmail", sharedPreferences.getString("userEmail", null));
            TextView userEmail = (TextView) findViewById(R.id.tvUserEmail);
            userEmail.setText(myUserEmail);
        }

        if (sharedPreferences.getString("serverAddress", null) != null){
            String sharedIP = sharedPreferences.getString("serverAddress", null);

            TextView ipadress = (TextView) findViewById(R.id.tvIPAddress);
            ipadress.setText(sharedIP);
        }



      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!Objects.equals(sharedPreferences.getString("imageUri", "null"), "null")){
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.MANAGE_DOCUMENTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                    Log.d("PERMISSION", "permission not granted");
                }
                String imageUriString = sharedPreferences.getString("imageUri", "null");
                Toast.makeText(this,"Uri : " + imageUriString, Toast.LENGTH_LONG).show();
                Log.d("Sharedprefs1","Uri : " + imageUriString);
                InputStream stream = null;
                try {
                    stream = new FileInputStream(imageUriString);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {

                    // We need to recyle unused bitmaps
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                    bitmap = BitmapFactory.decodeStream(stream);

                    userProfPic.setImageBitmap(bitmap);
                } finally {
                    {
                        if (stream != null)
                            try {
                                stream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                    }
                }
            }
        }*/


        imageButtonEditName = (ImageButton) findViewById(R.id.imageButtonEditName);
        imageButtonEditEmail = (ImageButton) findViewById(R.id.imageButtonEditEmail);
        imageButtonEditPhoneNumber = (ImageButton) findViewById(R.id.imageButtonEditNumber);
        imageButtonEditIPAddress = (ImageButton) findViewById(R.id.imageButtonEditIPAddress);

        imageButtonEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomizationActivity.this,EditNameActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imageButtonEditPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomizationActivity.this, EditPhoneNumberActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imageButtonEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imageButtonEditIPAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomizationActivity.this, BranchNumberActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imageButtonEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomizationActivity.this, EditCardNumberActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }



}


