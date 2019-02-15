package com.asimbongeni.asie.grmtranslate;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asimbongeni.asie.grmtranslate.Model.DatabaseAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public static final String PREFS_LANG = "LanguageSelectionOption";
    public static final String PREFS_TEXT = "TextSelectionOption";
    ListView listView;
    private CustomListAdapter adapter;
    ImageView translate,viewWork,viewStatistics,customize;
    EditText chapterSelected;
    private int mChapter;

    TextView unsentTranslations, translatedWords, translatedSentences;

    public final String URL_SERVERFOLDERS = "/site/homechef/translatorInsert.php";
    public static String IP_ADDRESS = "http://10.0.2.2";

    //public final String URL_SERVERFOLDERS = "/api/insert_entries.php";
    //public static String IP_ADDRESS = "http://www.asimbongeni.co.zw";

    int sumWordCount;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MyAppIntro.class);
                startActivity(intent);
            }
        }).start();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.logo_translate);


        unsentTranslations = (TextView) findViewById(R.id.textViewUnsent);
        translatedWords = (TextView) findViewById(R.id.textViewWordCount);
        translatedSentences = (TextView)findViewById(R.id.textViewSentenceCount);




        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        final List<Integer> wordCountList = databaseAccess.getAllTranslatedWordsCount();
        databaseAccess.close();
        sumWordCount = 0;
        for (int i = 0;i < wordCountList.size();i++ ){
            sumWordCount = wordCountList.get(i) + sumWordCount;}
        translatedWords.setText(String.format(getString(R.string.words_translated), sumWordCount));

        final int unsyncCounted = databaseAccess.getUnsyncCount();
        databaseAccess.close();
        unsentTranslations.setText(String.format(getString(R.string.sync_to_server), unsyncCounted));
        if (unsyncCounted > 8){
            unsentTranslations.setBackgroundColor(Color.parseColor("#FFFF5252"));
        }
        final int englishSentCount = databaseAccess.getEnglishTranslatedSentenceCount();
        databaseAccess.close();
        translatedSentences.setText(String.format(getString(R.string.sentence_translated), englishSentCount));

        translate = (ImageView) findViewById(R.id.imageViewTranslate);
        viewStatistics = (ImageView) findViewById(R.id.imageViewStatistics);
        viewWork =(ImageView) findViewById(R.id.imageViewTranslated);
        customize = (ImageView) findViewById(R.id.imageViewManage);

        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //LayoutInflater inflater = getLayoutInflater();
                builder.setTitle("Chapter to translate");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.setIcon(getDrawable(R.drawable.logo_translate));
                }
                final EditText editText = new EditText(MainActivity.this);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(editText);


                //builder.setView(inflater.inflate(R.layout.dialog_chapterselect, null));

                builder.setPositiveButton("  Select  ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //mListener.onDialogPositiveClick(SelectSearchParameterFragment.this);
                        String chapter = editText.getText().toString();
                        SharedPreferences sharedPreferences = getSharedPreferences("TranslationOptions", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("chapterToTranslate", Integer.parseInt(chapter));
                        editor.apply();
                        Toast.makeText(MainActivity.this, "Chapter Selected: " + chapter, Toast.LENGTH_LONG).show();
                        Log.d("TAG","select chosen");
                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(MainActivity.this);
                        try {
                            if (databaseAccess.getNdebeleSentenceCountByIsihloko(Integer.parseInt(chapter)) > 0
                                    &&  databaseAccess.getNdebeleSentenceCountByIsihloko(Integer.parseInt(chapter)) <= 669){
                                Intent intent = new Intent(MainActivity.this, DatabaseListActivity.class);
                                startActivity(intent);}
                            else {
                                Toast.makeText(MainActivity.this, "You have translated all of chapter " + chapter + ". Or its greater than 669" , Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            Log.d("MAIN ACT",e.getMessage());
                            Toast.makeText(MainActivity.this, "Chapter should be a number", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //mListener.onDialogNegativeClick(SelectSearchParameterFragment.this);
                                // User cancelled the dialog
                                Toast.makeText(MainActivity.this, "Translation chapter from settings", Toast.LENGTH_LONG).show();
                            }
                        });
                // Create the AlertDialog object and show
                builder.create().show();
            }
        });

        customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile_intent = new Intent(MainActivity.this, CustomizationActivity.class);
                startActivity(profile_intent);
            }
        });

        viewStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GraphViewActivity.class);
                startActivity(intent);
            }
        });

        viewWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewTranslatedActivity.class);
                startActivity(intent);
            }
        });

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Now opening your translation session ... ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent intent = new Intent(MainActivity.this, DatabaseListActivity.class);
                    startActivity(intent);
                }
            });
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void getStatisticsMain(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                final List<Integer> wordCountList = databaseAccess.getAllTranslatedWordsCount();
                databaseAccess.close();
                sumWordCount = 0;
                for (int i = 1;i < wordCountList.size();i++ ){
                    sumWordCount = wordCountList.get(i) + sumWordCount;}
                final int unsyncCounted = databaseAccess.getUnsyncCount();
                databaseAccess.close();
                final int englishSentCount = databaseAccess.getEnglishTranslatedSentenceCount();
                databaseAccess.close();
                translatedSentences.post(new Runnable() {
                    @Override
                    public void run() {
                        translatedSentences.setText(String.format(getString(R.string.sentence_translated), englishSentCount));
                    }
                });
                unsentTranslations.post(new Runnable() {
                    @Override
                    public void run() {
                        unsentTranslations.setText(String.format(getString(R.string.sync_to_server), unsyncCounted));
                        if (unsyncCounted > 8){
                            unsentTranslations.setBackgroundColor(Color.parseColor("#FFFF5252"));
                        }
                    }
                });
                translatedWords.post(new Runnable() {
                    @Override
                    public void run() {
                        translatedWords.setText(String.format(getString(R.string.words_translated), sumWordCount));
                    }
                });

            }
        }).start();

    }

    @Override
    public void onResume(){
        super.onResume();
        getStatisticsMain();

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent profile_intent = new Intent(this, CustomizationActivity.class);
            startActivity(profile_intent);
        } else if (id == R.id.nav_statistics) {
            Intent intent = new Intent(this, GraphViewActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_translate) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            //LayoutInflater inflater = getLayoutInflater();
            builder.setTitle("Type chapter to translate");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.setIcon(getDrawable(R.drawable.logo_translate));
            }
            final EditText editText = new EditText(MainActivity.this);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(editText);


            //builder.setView(inflater.inflate(R.layout.dialog_chapterselect, null));

            builder.setPositiveButton("  Select  ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //mListener.onDialogPositiveClick(SelectSearchParameterFragment.this);
                    String chapter = editText.getText().toString();
                    SharedPreferences sharedPreferences = getSharedPreferences("TranslationOptions", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("chapterToTranslate", Integer.parseInt(chapter));
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Chapter Selected: " + chapter, Toast.LENGTH_LONG).show();
                    Log.d("TAG","select chosen");
                    Intent intent = new Intent(MainActivity.this, DatabaseListActivity.class);
                    startActivity(intent);
                }
            })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //mListener.onDialogNegativeClick(SelectSearchParameterFragment.this);
                            // User cancelled the dialog
                            Toast.makeText(MainActivity.this, "Translation chapter from settings", Toast.LENGTH_LONG).show();
                        }
                    });
            // Create the AlertDialog object and show
            builder.create().show();


        } else if (id == R.id.nav_translated) {
            Intent intent = new Intent(MainActivity.this, ViewTranslatedActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_contact) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void syncToServer(final String[] dataHolder){

        SharedPreferences sharedPreferencesIP = getSharedPreferences("UserCredentials", 0);
        String ipaddress = sharedPreferencesIP.getString("serverAddress", null);
        if (ipaddress != null){
            IP_ADDRESS = ipaddress;
        }
        //Log.d("LINK",IP_ADDRESS+URL_SERVERFOLDERS);
        //Toast.makeText(this, IP_ADDRESS+URL_SERVERFOLDERS, Toast.LENGTH_LONG).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, IP_ADDRESS + URL_SERVERFOLDERS,
                new Response.Listener<String>() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {
                        int outResponse = Integer.parseInt(response);
                        Log.d("responseV", response.trim());
                        if (outResponse == 30000){
                            hidePDialog();
                            Log.d("Tag", response);
                            //Toast.makeText(MainActivity.this,"Connection error 30000! Try Again",Toast.LENGTH_LONG).show();
                        }
                        if (outResponse == 400000){
                            hidePDialog();
                            Log.d("Tag", response);
                            Toast.makeText(MainActivity.this,"Sending failed! Try Again",Toast.LENGTH_LONG).show();
                        }if (outResponse < 300000) {
                            Log.d("Tag", response);
                            int sentRowId = Integer.parseInt(response);
                            Log.d("send rowId", String.valueOf(outResponse));
                            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                            int updateCol = 1;
                            Log.d("UpdateVal", String.valueOf(updateCol));
                            Log.d("ResponseOut", String.valueOf(outResponse));
                            int out = databaseAccess.updateSendToServerColumn(sentRowId);
                            Log.d("outfromQuery", String.valueOf(out));
                            //InputInfo testInputInfo = db.getRowbyId(outResponse);
                            //Log.d("NewColValue", String.valueOf(testInputInfo.getSendtoserver()));
                            //Log.d("NewColValueA", String.valueOf(testInputInfo.getName()));
                            //Log.d("NewIDValue", String.valueOf(testInputInfo.getId()));
                            int unSynced = databaseAccess.getUnsyncCount();
                            unsentTranslations.setText(String.format("%s%s", String.valueOf(unSynced), getString(R.string.sync_to_server)));
                            hidePDialog();
                            Toast.makeText(MainActivity.this, "Sending successful!", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidePDialog();
                        Toast.makeText(MainActivity.this,"Error, check your connection",Toast.LENGTH_SHORT).show();
                        //Log.d("Error Response", error.getMessage());
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("appId",	dataHolder[0]);
                params.put("englishTranslated", dataHolder[1]);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        //Log.d("S2Server", String.valueOf(serverCode));
    }


    public void sendToServer(View v){
        /*SharedPreferences sharedPreferences = getSharedPreferences("ServerSync", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("lastServerRow", 0);
        editor.apply();*/
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Sending to server...");
        pDialog.show();

        // get a single row from database and send to server
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                List<DatabaseAttributes> sendtoServerList = databaseAccess.getAllUnsyncedEntries();
                int numberOfRows = sendtoServerList.size();
                //Toast.makeText(this, String.valueOf(numberOfRows), Toast.LENGTH_LONG).show();
                Log.d("SizeOfList", String.valueOf(numberOfRows));
                if (numberOfRows > 0) {
                    for (int x = 0;x < numberOfRows;x = x+1) {
                        Log.d("SendTS: x", String.valueOf(x));
                        DatabaseAttributes rowOutInputInfo = sendtoServerList.get(x);
                        //InputInfo rowOutInputInfo = databaseHandler.getRowbyId(x);
                        //Log.d("row id",String.valueOf(rowOutInputInfo.getId()));
                        //dbOutId = rowOutInputInfo.getId();
                        String dataHolder[] = new String[2];
                        dataHolder[0] =  String.valueOf(rowOutInputInfo.getId());
                        Log.d("dataH id", dataHolder[0]);
                        dataHolder[1] =  rowOutInputInfo.getNdebTranslated();
                        syncToServer(dataHolder);
                    }
                }

                if (numberOfRows < 1){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hidePDialog();
                            Toast.makeText(getApplicationContext(), "Nothing to send", Toast.LENGTH_LONG).show();

                        }
                    });

                }

            }
        }).start();


    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.setCancelable(true);
            pDialog.dismiss();
            pDialog = null;
        }
    }




}
