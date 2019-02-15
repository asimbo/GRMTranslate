package com.asimbongeni.asie.grmtranslate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;

import com.asimbongeni.asie.grmtranslate.Model.DatabaseAttributes;

import java.util.List;
import java.util.Objects;

public class DatabaseListActivity extends AppCompatActivity {
    RecyclerView mRecycleView;
    SwipeRefreshLayout swipeContainer;
    List<DatabaseAttributes> dbAttributes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_list);
        mRecycleView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);


        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);


        SharedPreferences sharedPreferences = getSharedPreferences("TranslationOptions", Context.MODE_PRIVATE);
        final int myChapterToTranslate = sharedPreferences.getInt("chapterToTranslate", 0);
        Log.d("TAG", "OUTCHAPTER: "+ myChapterToTranslate);
        int availableChapters = databaseAccess.getNdebeleSentenceCountByIsihloko(myChapterToTranslate);
        Log.d("countFrmDB", ": "+ availableChapters);

        if (availableChapters > 0){
            dbAttributes = databaseAccess.getNdebeleSentenceByIsihloko(myChapterToTranslate);
            Log.d("In < 0", "Chapter: " + availableChapters);
            RecycleViewAdapater adapater = new RecycleViewAdapater(this, dbAttributes);
            mRecycleView.setAdapter(adapater);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            mRecycleView.setLayoutManager(layoutManager);
            mRecycleView.setItemAnimator(new SimpleItemAnimator() {
                @Override
                public boolean animateRemove(RecyclerView.ViewHolder holder) {
                    return false;
                }

                @Override
                public boolean animateAdd(RecyclerView.ViewHolder holder) {
                    return false;
                }

                @Override
                public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
                    return false;
                }

                @Override
                public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromLeft, int fromTop, int toLeft, int toTop) {
                    return false;
                }

                @Override
                public void runPendingAnimations() {

                }

                @Override
                public void endAnimation(RecyclerView.ViewHolder item) {

                }

                @Override
                public void endAnimations() {

                }

                @Override
                public boolean isRunning() {
                    return false;
                }
            });
            //mRecycleView.setHasFixedSize(true);
        }

        else {
            Log.d("TAG", "selectingBy limit");
             dbAttributes = databaseAccess.getNdebeleSentencesAllLimit(30);
            RecycleViewAdapater adapater = new RecycleViewAdapater(DatabaseListActivity.this, dbAttributes);
            mRecycleView.setAdapter(adapater);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            mRecycleView.setLayoutManager(layoutManager);
            //mRecycleView.setHasFixedSize(true);
            mRecycleView.invalidate();
        }

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                RecycleViewAdapater recycleViewAdapater = new RecycleViewAdapater(DatabaseListActivity.this,dbAttributes);
                mRecycleView.setAdapter(recycleViewAdapater);

                recycleViewAdapater.updateAdapter(databaseAccess.getNdebeleSentenceByIsihloko(myChapterToTranslate));
                mRecycleView.invalidate();
                swipeContainer.setRefreshing(false);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
                );


    }

   /* @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }*/
}
