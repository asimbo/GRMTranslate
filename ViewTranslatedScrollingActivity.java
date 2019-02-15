package com.asimbongeni.asie.grmtranslate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asimbongeni.asie.grmtranslate.Model.DatabaseAttributes;

import org.w3c.dom.Text;

public class ViewTranslatedScrollingActivity extends AppCompatActivity {
    TextView ndebele, isihloko, volumeNumber;
    EditText english;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_translated_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isihloko = (TextView) findViewById(R.id.viewIsihlokoHere);
        ndebele = (TextView) findViewById(R.id.ViewNdebeleSentence);
        volumeNumber = (TextView) findViewById(R.id.viewTheNumberTv);
        english = (EditText) findViewById(R.id.viewTranslatedEditTxt);

        Intent intent = getIntent();
        id  = intent.getIntExtra("id", 50000);
        String mIsihloko = intent.getStringExtra("isihloko");
        String mIndebele = intent.getStringExtra("ndebSentences");
        int mVolumeNumber =  intent.getIntExtra("volumeNumber", 0);
        String mEnglish = intent.getStringExtra("englishTranslated");
        int mId = intent.getIntExtra("id", 50000);

        isihloko.setText("Isihloko: " + mIsihloko);
        ndebele.setText(mIndebele);
        volumeNumber.setText(String.valueOf(mVolumeNumber));
        english.setText(mEnglish);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.saveEditionFab);

        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (english != null) {
                        if (english.getText() != null && english.getText().length() != 0) {
                            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                            DatabaseAttributes databaseAttributes = new DatabaseAttributes();
                            databaseAttributes.setNdebTranslated(english.getText().toString());
                            Log.d("English", english.getText().toString());
                            databaseAttributes.setNdebTranslatedCount(1);
                            databaseAccess.saveTranslation(databaseAttributes, String.valueOf(id));
                            Toast.makeText(ViewTranslatedScrollingActivity.this, "Save Successful", Toast.LENGTH_LONG).show();
                            Log.d("Translated in db", String.valueOf(databaseAccess.getEnglishTranslatedSentenceCount()));
                            //Snackbar.make(view, "Save Successfull!", Snackbar.LENGTH_LONG)
                            //        .setAction("Action", null).show();
                            finish();
                        }
                        else {
                            Toast.makeText(ViewTranslatedScrollingActivity.this, "Nothing to save", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }
}
