package com.asimbongeni.asie.grmtranslate;

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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

public class GraphViewActivity extends AppCompatActivity {
    private int chapter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());

  /*      Log.d("All work done: ", String.valueOf(databaseAccess.getEnglishTranslatedSentenceCount()));
        Log.d("Work by vol 1: ", String.valueOf(databaseAccess.getEnglishTranslatedSentenceCountByVolume(1)));
        Log.d("Work by vol 2: ", String.valueOf(databaseAccess.getEnglishTranslatedSentenceCountByVolume(2)));
        Log.d("Work by vol 3: ", String.valueOf(databaseAccess.getEnglishTranslatedSentenceCountByVolume(3)));
        Log.d("Work by vol 4: ", String.valueOf(databaseAccess.getEnglishTranslatedSentenceCountByVolume(4)));
        Log.d("Work by vol 5: ", String.valueOf(databaseAccess.getEnglishTranslatedSentenceCountByVolume(5)));
        Log.d("Work by vol 6: ", String.valueOf(databaseAccess.getEnglishTranslatedSentenceCountByVolume(6)));
        Log.d("Work by chpter 1: ", String.valueOf(databaseAccess.getTranslatedSentenceCountByIsihloko(1)));
        Log.d("Work by chpter 2: ", String.valueOf(databaseAccess.getTranslatedSentenceCountByIsihloko(2)));
        Log.d("Work by chpter 3: ", String.valueOf(databaseAccess.getTranslatedSentenceCountByIsihloko(3)));
        Log.d("Work by chpter 4: ", String.valueOf(databaseAccess.getTranslatedSentenceCountByIsihloko(4)));
        Log.d("Work by chpter 5: ", String.valueOf(databaseAccess.getTranslatedSentenceCountByIsihloko(5)));
        Log.d("Work by chpter 6: ", String.valueOf(databaseAccess.getTranslatedSentenceCountByIsihloko(6)));
        Log.d("Work by chpter 7: ", String.valueOf(databaseAccess.getTranslatedSentenceCountByIsihloko(7)));
        Log.d("Ndebele Sent by vol 1", String.valueOf(databaseAccess.getNdebeleSentenceCountByVolume(1)));
        Log.d("Ndebele Sent by vol 2", String.valueOf(databaseAccess.getNdebeleSentenceCountByVolume(2)));
        Log.d("Ndebele Sent by vol 3", String.valueOf(databaseAccess.getNdebeleSentenceCountByVolume(3)));
        Log.d("Ndebele Sent by vol 4", String.valueOf(databaseAccess.getNdebeleSentenceCountByVolume(4)));*/

        final GraphView graph = (GraphView) findViewById(R.id.graph);
        final GraphView volGraph = (GraphView) findViewById(R.id.graphVol);
        final GraphView perGraphComplete = (GraphView) findViewById(R.id.percGraphComplete);

        graph.setTitle("Number of translations by isihloko");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // max 8 views
                final BarGraphSeries<DataPoint> bseries = new BarGraphSeries<>(new DataPoint[]{
                        new DataPoint(chapter,databaseAccess.getTranslatedSentenceCountByIsihloko(chapter)),
                        new DataPoint(chapter + 1,databaseAccess.getTranslatedSentenceCountByIsihloko(chapter + 1)),
                        new DataPoint(chapter + 2,databaseAccess.getTranslatedSentenceCountByIsihloko(chapter + 2)),
                        new DataPoint(chapter + 3,databaseAccess.getTranslatedSentenceCountByIsihloko(chapter + 3)),
                        new DataPoint(chapter + 4,databaseAccess.getTranslatedSentenceCountByIsihloko(chapter + 4)),
                        new DataPoint(chapter + 5,databaseAccess.getTranslatedSentenceCountByIsihloko(chapter + 5)),
                        new DataPoint(chapter + 6,databaseAccess.getTranslatedSentenceCountByIsihloko(chapter + 1)),
                        new DataPoint(chapter + 7,databaseAccess.getTranslatedSentenceCountByIsihloko(chapter + 7))
                });
                graph.addSeries(bseries);
                graph.getGridLabelRenderer().setHorizontalAxisTitle("Chapters");
                graph.getViewport().setBackgroundColor(Color.parseColor("#FF9CB7C4"));
                //graph.getViewport().setScalable(true);
                graph.setTitleTextSize(20f);
                graph.getViewport().setBorderColor(Color.parseColor("#FFBBCCD1"));
                graph.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphViewActivity.this);
                        //LayoutInflater inflater = getLayoutInflater();
                        builder.setTitle("Type chapter to view");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder.setIcon(getDrawable(R.drawable.logo_translate));
                        }
                        final EditText editText = new EditText(GraphViewActivity.this);
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        editText.setHint("5");
                        builder.setView(editText);



                        //builder.setView(inflater.inflate(R.layout.dialog_chapterselect, null));

                        builder.setPositiveButton("  Select  ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //mListener.onDialogPositiveClick(SelectSearchParameterFragment.this);
                                chapter = Integer.parseInt(editText.getText().toString());
                        /*SharedPreferences sharedPreferences = getSharedPreferences("TranslationOptions", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("chapterToTranslate", Integer.parseInt(chapter));
                        editor.apply();*/
                                Toast.makeText(GraphViewActivity.this, "Chapter Selected: " + chapter, Toast.LENGTH_LONG).show();
                                Log.d("TAG","select chosen");
                                final DatabaseAccess databaseAccess2 = DatabaseAccess.getInstance(getApplicationContext());
                                /*bseries.resetData(
                                        new DataPoint[]{
                                                new DataPoint(chapter, databaseAccess2.getTranslatedSentenceCountByIsihloko(chapter)),
                                        new DataPoint(chapter +1, databaseAccess2.getTranslatedSentenceCountByIsihloko(chapter+1)),
                                        new DataPoint(chapter +2, databaseAccess2.getTranslatedSentenceCountByIsihloko(chapter+2)),
                                        new DataPoint(chapter +3, databaseAccess2.getTranslatedSentenceCountByIsihloko(chapter+3)),
                                        new DataPoint(chapter +4, databaseAccess2.getTranslatedSentenceCountByIsihloko(chapter+4)),
                                        new DataPoint(chapter +5, databaseAccess2.getTranslatedSentenceCountByIsihloko(chapter+5)),
                                        new DataPoint(chapter +6, databaseAccess2.getTranslatedSentenceCountByIsihloko(chapter+6)),
                                        new DataPoint(chapter +7, databaseAccess2.getTranslatedSentenceCountByIsihloko(chapter+7))});*/
                                graph.getSeries().clear();
                                graph.removeAllSeries();
                                final BarGraphSeries<DataPoint> bseries2 = new BarGraphSeries<>(new DataPoint[]{
                                                new DataPoint(chapter, databaseAccess2.getTranslatedSentenceCountByIsihloko(chapter)),
                                                new DataPoint(chapter +1, databaseAccess2.getTranslatedSentenceCountByIsihloko(chapter+1)),
                                                new DataPoint(chapter +2, databaseAccess2.getTranslatedSentenceCountByIsihloko(chapter+2)),
                                                new DataPoint(chapter +3, databaseAccess2.getTranslatedSentenceCountByIsihloko(chapter+3)),
                                                new DataPoint(chapter +4, databaseAccess2.getTranslatedSentenceCountByIsihloko(chapter+4)),
                                                new DataPoint(chapter +5, databaseAccess2.getTranslatedSentenceCountByIsihloko(chapter+5)),
                                                new DataPoint(chapter +6, databaseAccess2.getTranslatedSentenceCountByIsihloko(chapter+6))
                                });
                                graph.addSeries(bseries2);

                                //bseries.resetData(new DataPoint(chapter, databaseAccess.getTranslatedSentenceCountByIsihloko(chapter)));
                            }
                        })
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //mListener.onDialogNegativeClick(SelectSearchParameterFragment.this);
                                        // User cancelled the dialog
                                        Toast.makeText(GraphViewActivity.this, "Using default value : 1", Toast.LENGTH_LONG).show();
                                    }
                                });
                        // Create the AlertDialog object and show
                        builder.create().show();
                        return false;
                    }
                });


                bseries.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                    @Override
                    public int get(DataPoint dataPoint) {
                        return Color.rgb((int) dataPoint.getX()*255/4, (int) Math.abs(dataPoint.getY()*255/6), 100);
                    }
                });

                bseries.setSpacing(20);
                bseries.setDrawValuesOnTop(true);
                bseries.setValuesOnTopColor(Color.RED);
                bseries.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPointInterface) {
                        Toast.makeText(getApplicationContext(), "Series: On Data Point clicked: " + dataPointInterface.getY(), Toast.LENGTH_LONG ).show();
                    }
                });

            }
        });



        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                volGraph.setTitle("Completed Work by Volume Number");
                // max 8 views
                BarGraphSeries<DataPoint> volSeries = new BarGraphSeries<>(new DataPoint[]{
                        new DataPoint(1,databaseAccess.getEnglishTranslatedSentenceCountByVolume(1)),
                        new DataPoint(2,databaseAccess.getEnglishTranslatedSentenceCountByVolume(2)),
                        new DataPoint(3,databaseAccess.getEnglishTranslatedSentenceCountByVolume(3)),
                        new DataPoint(4,databaseAccess.getEnglishTranslatedSentenceCountByVolume(4)),
                        new DataPoint(5,databaseAccess.getEnglishTranslatedSentenceCountByVolume(5)),
                        new DataPoint(6,databaseAccess.getEnglishTranslatedSentenceCountByVolume(6))
                });
                volGraph.addSeries(volSeries);


                volSeries.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                    @Override
                    public int get(DataPoint dataPoint) {
                        return Color.rgb((int) dataPoint.getX()*255/4, (int) Math.abs(dataPoint.getY()*255/6), 100);
                    }
                });

                volSeries.setSpacing(20);
                volSeries.setDrawValuesOnTop(true);
                volSeries.setValuesOnTopColor(Color.RED);


            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // % translated by volume
                perGraphComplete.setTitle("% of completed translations by volume");
                // max 8 views
                BarGraphSeries<DataPoint> percSeries = new BarGraphSeries<>(new DataPoint[]{
                        new DataPoint(1,(databaseAccess.getEnglishTranslatedSentenceCountByVolume(1)/databaseAccess.getNdebeleSentenceCountByVolume(1)/100)),
                        new DataPoint(2,(databaseAccess.getEnglishTranslatedSentenceCountByVolume(2)/databaseAccess.getNdebeleSentenceCountByVolume(2)/100)),
                        new DataPoint(3,(databaseAccess.getEnglishTranslatedSentenceCountByVolume(3)/databaseAccess.getNdebeleSentenceCountByVolume(3)/100)),
                        new DataPoint(4,(databaseAccess.getEnglishTranslatedSentenceCountByVolume(4)/databaseAccess.getNdebeleSentenceCountByVolume(4)/100)),
                        new DataPoint(5,(databaseAccess.getEnglishTranslatedSentenceCountByVolume(5)/databaseAccess.getNdebeleSentenceCountByVolume(5)/100)),
                        new DataPoint(6,(databaseAccess.getEnglishTranslatedSentenceCountByVolume(6)/databaseAccess.getNdebeleSentenceCountByVolume(6)/100))
                });
                perGraphComplete.addSeries(percSeries);


                percSeries.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                    @Override
                    public int get(DataPoint dataPoint) {
                        return Color.rgb((int) dataPoint.getX()*255/4, (int) Math.abs(dataPoint.getY()*255/6), 100);
                    }
                });

                percSeries.setSpacing(20);
                percSeries.setDrawValuesOnTop(true);
                percSeries.setValuesOnTopColor(Color.RED);
                percSeries.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPointInterface) {
                        Toast.makeText(getApplicationContext(), "Series: On Data Point clicked: " + dataPointInterface.getY(), Toast.LENGTH_LONG ).show();
                    }
                });
            }
        });

    }

}
