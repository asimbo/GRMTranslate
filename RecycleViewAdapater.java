package com.asimbongeni.asie.grmtranslate;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asimbongeni.asie.grmtranslate.Model.DatabaseAttributes;

import java.util.List;
import java.util.Objects;

/**
 * Created by Asie on 26/01/2017.
 */
public class RecycleViewAdapater extends RecyclerView.Adapter<RecycleViewAdapater.DatabaseViewHolder> {

    private List<DatabaseAttributes> mDatabaseAttributesList;
    private Context mContext;




    public RecycleViewAdapater(Context context,List<DatabaseAttributes> databaseAttributesList){
        mDatabaseAttributesList = databaseAttributesList;
        this.mContext = context;
    }

    public void clear(){
        mDatabaseAttributesList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<DatabaseAttributes> myList){
        this.mDatabaseAttributesList = myList;
        notifyDataSetChanged();
    }


    @Override
    public DatabaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        DatabaseViewHolder viewHolder = new DatabaseViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(DatabaseViewHolder holder, int position) {
        holder.bindDatabase(mDatabaseAttributesList.get(position));

    }

    @Override
    public int getItemCount() {
        return mDatabaseAttributesList.size();
    }

    public void updateAdapter(List<DatabaseAttributes> newData) {
        mDatabaseAttributesList.clear();
        mDatabaseAttributesList.addAll(newData);
        notifyDataSetChanged();
    }

    public class DatabaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mIsihloko;
        public TextView mWordCount;
        public TextView mVolumeNumber;
        public TextView mNdebeleSentence;
        public TextView mTranslatedEnglish;

        public DatabaseViewHolder(View itemView) {
            super(itemView);

            mIsihloko = (TextView) itemView.findViewById(R.id.fetchIsihloko);
            mWordCount = (TextView) itemView.findViewById(R.id.fetchWordCount);
            mVolumeNumber = (TextView) itemView.findViewById(R.id.fetchVolumeNumber);
            mNdebeleSentence = (TextView) itemView.findViewById(R.id.fetchSentence);
            mTranslatedEnglish = (TextView) itemView.findViewById(R.id.fetchTranslatedEnglish);

            itemView.setOnClickListener(this);
        }

        public void bindDatabase(DatabaseAttributes databaseAttributes) {
            mIsihloko.setText("Isihloko: " + databaseAttributes.getIsihloko());
            mWordCount.setText("Word Count: " + databaseAttributes.getNdebSentenceCount() + "");
            mVolumeNumber.setText("Volume Number: " + databaseAttributes.getVolumeNumber() + "");
            if (databaseAttributes.getNdebSentences().length() > 30) {
                mNdebeleSentence.setText("Ndebele: " + databaseAttributes.getNdebSentences().subSequence(0, 30)+ "...");
            }
            if (databaseAttributes.getNdebSentences().length() < 30) {
                mNdebeleSentence.setText("Ndebele: "+databaseAttributes.getNdebSentences()+ "...");
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (Objects.equals(databaseAttributes.getNdebTranslated(), "7")) {
                    mTranslatedEnglish.setText("English: None translated");
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (!Objects.equals(databaseAttributes.getNdebTranslated(), "7")) {
                    mTranslatedEnglish.setText("English: " + databaseAttributes.getNdebTranslated().substring(0,30) + "...");
                }
            }

        }

        @Override
        public void onClick(View v) {
            final DatabaseAttributes databaseAttributes = mDatabaseAttributesList.get(getAdapterPosition());
            Log.d("TAG", "position: "+ getAdapterPosition());
            Log.d("TAG", "isihloko: "+ databaseAttributes.getIsihloko());
            Toast.makeText(mContext, "Position" + getAdapterPosition(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(mContext, TranslationBenchActivity.class);
            intent.putExtra("id", databaseAttributes.getId());
            intent.putExtra("position", getAdapterPosition());
            intent.putExtra("ndebSentences", databaseAttributes.getNdebSentences());
            Log.d("Ndebele", databaseAttributes.getNdebSentences());
            intent.putExtra("isihloko", databaseAttributes.getIsihloko());
            intent.putExtra("ndebWordCount", databaseAttributes.getNdebSentenceCount());
            intent.putExtra("volumeNumber", databaseAttributes.getVolumeNumber());
            intent.putExtra("englishTranslated", databaseAttributes.getNdebTranslated());
           /* // The enter/exit animations for the two activities are specified by xml resources
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                Bundle translateBundle =
                        ActivityOptions.
                                makeCustomAnimation(mContext,
                                        R.anim.slide_in_left,
                                        R.anim.slide_out_left).toBundle();

            }*/

            mContext.startActivity(intent);


        }
    }


}
