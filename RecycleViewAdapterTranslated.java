package com.asimbongeni.asie.grmtranslate;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.asimbongeni.asie.grmtranslate.Model.DatabaseAttributes;

import java.util.List;
import java.util.Objects;

/**
 * Created by Asie on 26/01/2017.
 */
public class RecycleViewAdapterTranslated extends RecyclerView.Adapter<RecycleViewAdapterTranslated.DatabaseViewHolder> {

    private List<DatabaseAttributes> mDatabaseAttributesList;
    private Context mContext;

    public RecycleViewAdapterTranslated(Context context, List<DatabaseAttributes> databaseAttributesList){
        mDatabaseAttributesList = databaseAttributesList;
        mContext = context;
    }
    @Override
    public DatabaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_translated, parent, false);
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

    public class DatabaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView ndebeleView;
        public TextView englishView;
        public TextView isihlokoView;
        public TextView volumeView;



        public DatabaseViewHolder(View itemView) {
            super(itemView);

            ndebeleView = (TextView) itemView.findViewById(R.id.fetchNdebeleView);
            englishView = (TextView) itemView.findViewById(R.id.fetchTranslatedEnglish);
            isihlokoView = (TextView) itemView.findViewById(R.id.translatedIsihloko);
            volumeView = (TextView) itemView.findViewById(R.id.translatedVolume);
            itemView.setOnClickListener(this);
        }

        public void bindDatabase(DatabaseAttributes databaseAttributes) {
            ndebeleView.setText(databaseAttributes.getNdebSentences());
            englishView.setText(databaseAttributes.getNdebTranslated());
            isihlokoView.setText("Isihloko: " + databaseAttributes.getIsihloko());
            volumeView.setText("Volume: "+ databaseAttributes.getVolumeNumber());
        }

        @Override
        public void onClick(View v) {
            DatabaseAttributes databaseAttributes = mDatabaseAttributesList.get(getAdapterPosition());
            Log.d("TAG", "position: "+ getAdapterPosition());
            Log.d("TAG", "isihloko: "+ databaseAttributes.getIsihloko());
            Toast.makeText(mContext, "Position" + getAdapterPosition(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(mContext, ViewTranslatedScrollingActivity.class);
            intent.putExtra("id", databaseAttributes.getId());
            intent.putExtra("ndebSentences", databaseAttributes.getNdebSentences());
            Log.d("Ndebele", databaseAttributes.getNdebSentences());
            intent.putExtra("isihloko", databaseAttributes.getIsihloko());
            intent.putExtra("ndebWordCount", databaseAttributes.getNdebSentenceCount());
            intent.putExtra("volumeNumber", databaseAttributes.getVolumeNumber());
            intent.putExtra("englishTranslated", databaseAttributes.getNdebTranslated());
            mContext.startActivity(intent);


        }
    }
}
