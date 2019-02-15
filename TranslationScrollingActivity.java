package com.asimbongeni.asie.grmtranslate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.asimbongeni.asie.grmtranslate.Model.DatabaseAttributes;

import java.util.List;
import java.util.Objects;

public class TranslationScrollingActivity extends AppCompatActivity {
    private ExternalSQLDatabase eDb;
    private List<DatabaseAttributes> ndebSentencesList;
    int id,volumeNumber,wordCount,position;
    String ndebSentences, isihloko, englishTranslated;
    RecyclerView mRecycleView;
    private Button save;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecycleView = (RecyclerView) findViewById(R.id.recyclerView);

        save = (Button) findViewById(R.id.button);

        final TextView scrollList = (TextView) findViewById(R.id.scrollingTextView);
        final TextView isihlokoTv = (TextView) findViewById(R.id.isihlokoHere);
        final TextView volNumber = (TextView) findViewById(R.id.theNumberTv);
        final EditText translated = (EditText) findViewById(R.id.translatedEditTxt);
        SharedPreferences sharedPreferences = getSharedPreferences("TranslationOptions", Context.MODE_PRIVATE);
        final int myChapterToTranslate = sharedPreferences.getInt("chapterToTranslate", 0);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                DatabaseAttributes attributes = new DatabaseAttributes();
                if (translated != null) {
                    if (translated.getText() != null && translated.getText().length() != 0) {
                        attributes.setNdebTranslated(translated.getText().toString());
                        Log.d("English", translated.getText().toString());
                        attributes.setNdebTranslatedCount(1);
                        databaseAccess.saveTranslation(attributes, String.valueOf(id));
                        Toast.makeText(TranslationScrollingActivity.this, "Save Successful", Toast.LENGTH_LONG).show();
                        Log.d("Translated in db", String.valueOf(databaseAccess.getEnglishTranslatedSentenceCount()));
                        Log.d("Untranslated", String.valueOf(databaseAccess.getNdebeleSentenceCountByIsihloko(myChapterToTranslate)));
                        finish();
                    }
                    else {
                        Toast.makeText(TranslationScrollingActivity.this, "Nothing to save", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 50000);
        ndebSentences = intent.getStringExtra("ndebSentences");

        isihloko = intent.getStringExtra("isihloko");
        position = intent.getIntExtra("position", 50000);
        wordCount = intent.getIntExtra("ndebWordCount", 50000);
        volumeNumber = intent.getIntExtra("volumeNumber", 50000);
        englishTranslated = intent.getStringExtra("englishTranslated");
        Log.d("Value of volume", volumeNumber+ "");



        if (scrollList != null) {
            scrollList.setText(ndebSentences);
        }

        if (isihlokoTv != null) {
            isihlokoTv.setText("Isihloko: " +isihloko);
        }
        if (volNumber != null) {
            volNumber.setText("Volume:" + volumeNumber);
        }
        if (translated != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (!Objects.equals(englishTranslated, "7"))
                translated.setText(englishTranslated);
            }
        }

        FloatingActionButton fabAsk = (FloatingActionButton) findViewById(R.id.fabAsk);
        //DatabaseAttributes databaseAttributes = new DatabaseAttributes();
//        DatabaseAttributes databaseAttributes = ndebSentencesList.get(0);

       //FloatingActionButton fabSave = (FloatingActionButton) findViewById(R.id.fab);

        //final EditText translateTo = (EditText) findViewById(R.id.translatedEditTxt);
     /*   if (fabSave != null) {
            fabSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                    DatabaseAttributes attributes = new DatabaseAttributes();

                    if (translated != null) {
                        if (translated.getText() != null && translated.getText().length() != 0) {
                            attributes.setNdebTranslated(translated.getText().toString());
                            Log.d("English", translated.getText().toString());
                            attributes.setNdebTranslatedCount(1);
                            databaseAccess.saveTranslation(attributes, String.valueOf(id));
                            Toast.makeText(TranslationScrollingActivity.this, "Save Successful", Toast.LENGTH_LONG).show();
                            Log.d("Translated in db", String.valueOf(databaseAccess.getEnglishTranslatedSentenceCount()));
                            Log.d("Untranslated", String.valueOf(databaseAccess.getNdebeleSentenceCountByIsihloko(myChapterToTranslate)));
                            //Snackbar.make(view, "Save Successfull!", Snackbar.LENGTH_LONG)
                            //        .setAction("Action", null).show();

                            //final List<DatabaseAttributes> dbAttributes = databaseAccess.getNdebeleSentenceByIsihloko(Integer.parseInt(isihloko));
                            //final RecycleViewAdapater adapater = new RecycleViewAdapater(TranslationScrollingActivity.this, dbAttributes);
                            //mRecycleView.setAdapter(new RecycleViewAdapater(TranslationScrollingActivity.this, dbAttributes));
                            //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TranslationScrollingActivity.this);
//                            mRecycleView.setLayoutManager(layoutManager);
//                            mRecycleView.setHasFixedSize(true);
//                            mRecycleView.invalidate();
                            finish();
                        }
                        else {
                            Toast.makeText(TranslationScrollingActivity.this, "Nothing to save", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }*/


        if (fabAsk != null) {
            fabAsk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (translated.getText() == null) {
                        Toast.makeText(getApplicationContext(),"Please attempt to translate!!!", Toast.LENGTH_LONG).show();
                    }
                    if (translated.getText() != null) {
                        Log.d("TAG", "textView not null");
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.setPackage("com.whatsapp");
                        //intent.setData(Uri.parse("smsto:"));  // This ensures only SMS apps respond
                        intent.putExtra(Intent.EXTRA_TEXT, ndebSentences + ".\n Extract from Combined Volumes. Info www.asimbongeni.co.zw");
                        try {
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                        }

                        }catch (Exception e){
                            Log.e("TAG", e.getMessage());
                            Toast.makeText(getApplicationContext(), "Whatsapp has not been installed", Toast.LENGTH_LONG).show();
                        }

                    }

                }
            });
        }


    }

}
