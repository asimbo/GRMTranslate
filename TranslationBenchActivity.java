package com.asimbongeni.asie.grmtranslate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asimbongeni.asie.grmtranslate.Model.DatabaseAttributes;

import java.util.List;
import java.util.Objects;

public class TranslationBenchActivity extends AppCompatActivity {
    private ExternalSQLDatabase eDb;
    private List<DatabaseAttributes> ndebSentencesList;
    int id,volumeNumber,wordCount,position;
    String ndebSentences, isihloko, englishTranslated;
    RecyclerView mRecycleView;
    private Button save;
    Button previous,next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation_bench);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecycleView = (RecyclerView) findViewById(R.id.recyclerView);

        save = (Button) findViewById(R.id.button2);

        final TextView scrollList = (TextView) findViewById(R.id.scrollingTextView2);
        final TextView isihlokoTv = (TextView) findViewById(R.id.isihlokoHere2);
        final TextView volNumber = (TextView) findViewById(R.id.theNumberTv2);
        final EditText translated = (EditText) findViewById(R.id.translatedEditTxt2);
        SharedPreferences sharedPreferences = getSharedPreferences("TranslationOptions", Context.MODE_PRIVATE);
        final int myChapterToTranslate = sharedPreferences.getInt("chapterToTranslate", 0);

        previous = (Button) findViewById(R.id.textViewPrevious);
        next = (Button) findViewById(R.id.textViewNext);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousDialogue();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextDialogue();
            }
        });

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
                        Toast.makeText(TranslationBenchActivity.this, "Save Successful", Toast.LENGTH_LONG).show();
                        Log.d("Translated in db", String.valueOf(databaseAccess.getEnglishTranslatedSentenceCount()));
                        Log.d("Untranslated", String.valueOf(databaseAccess.getNdebeleSentenceCountByIsihloko(myChapterToTranslate)));
                        finish();
                    }
                    else {
                        Toast.makeText(TranslationBenchActivity.this, "Nothing to save", Toast.LENGTH_LONG).show();
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
            isihlokoTv.setText("isihloko: " +isihloko);
        }
        if (volNumber != null) {
            volNumber.setText("volume: " + volumeNumber);
        }
        if (translated != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (!Objects.equals(englishTranslated, "7"))
                    translated.setText(englishTranslated);
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Seeking help from watsapp...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (translated.getText() == null) {
                    Toast.makeText(getApplicationContext(),"Please attempt to translate!!!", Toast.LENGTH_LONG).show();
                }
                if (translated.getText() != null) {
                    Log.d("TAG", "textView not null");
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.setPackage("com.whatsapp");
                    //intent.setData(Uri.parse("smsto:"));  // This ensures only SMS apps respond
                    intent.putExtra(Intent.EXTRA_TEXT, ndebSentences + ".\n Extract from the Combined Volumes. Info www.asimbongeni.co.zw");
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

    public void showPreviousDialogue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(TranslationBenchActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        //builder.setView(inflater.inflate(R.layout.view_sentences,null));
        builder.setTitle("  Previous Sentence");
        final TextView textView = new TextView(TranslationBenchActivity.this);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(TranslationBenchActivity.this);
        if (databaseAccess.getOneSentenceCount(id-1) > 0){
            DatabaseAttributes databaseAttributes = databaseAccess.getOneSentence(id-1);
            String mySentence = databaseAttributes.getNdebSentences();
            textView.setText(" " + mySentence);
        }else {
            textView.setText("  No Sentence Found!!!");
        }
        builder.setView(textView);


        //builder.setView(inflater.inflate(R.layout.dialog_chapterselect, null));

        builder
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        // Create the AlertDialog object and show
        builder.create().show();
    }

    public void showNextDialogue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(TranslationBenchActivity.this);
        //LayoutInflater inflater = getLayoutInflater();
        builder.setTitle("Next Sentence");
        final TextView textView = new TextView(TranslationBenchActivity.this);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(TranslationBenchActivity.this);
        if (databaseAccess.getOneSentenceCount(id+1) > 0){
            DatabaseAttributes databaseAttributes = databaseAccess.getOneSentence(id+1);
            String mySentence = databaseAttributes.getNdebSentences();
            textView.setText(mySentence);
        }else {
            textView.setText("No Sentence Found!!!");
        }
        builder.setView(textView);
        builder
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        // Create the AlertDialog object and show
        builder.create().show();
    }

}
