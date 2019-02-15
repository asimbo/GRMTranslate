package com.asimbongeni.asie.grmtranslate;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.asimbongeni.asie.grmtranslate.Model.DatabaseAttributes;

import java.util.List;

//import com.android.volley.toolbox.ImageLoader;
//import com.android.volley.toolbox.NetworkImageView;

//import Config.AppController;
//import com.asimbongeni.asie.colorfragmenttut.Model.MyRecipe;

/**
 * Created by Asie on 30/07/2016.
 */
public class CustomListAdapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<DatabaseAttributes> myDatabaseAttributeList;
   // AppController appController = new AppController();


    //ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<DatabaseAttributes> myDatabaseAttributes){
        this.activity = activity;
        this.myDatabaseAttributeList = myDatabaseAttributes;
    }
    @Override
    public int getCount() {
        return myDatabaseAttributeList.size();
    }

    @Override
    public Object getItem(int location) {
        return myDatabaseAttributeList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null){
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);}
        if (convertView == null){
            convertView = inflater.inflate(R.layout.list_row, null);}
//        if (imageLoader== null){
//            imageLoader = AppController.getInstance().getImageLoader();}

        //NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        TextView isihloko = (TextView) convertView.findViewById(R.id.fetchIsihloko);
        TextView wordCount = (TextView) convertView.findViewById(R.id.fetchWordCount);
        TextView volumeNumber = (TextView) convertView.findViewById(R.id.fetchVolumeNumber);
        //TextView age = (TextView) convertView.findViewById(R.id.fetchId);
        //TextView id = (TextView) convertView.findViewById(R.id.fetchId);

        // getting family member data for row
        DatabaseAttributes f = myDatabaseAttributeList.get(position);

        // image
        //thumbNail.setImageUrl(f.getImageUrl(), imageLoader);

        //id
        //fetchId.setText(f.getId());
        //Log.d("In list Activity", String.valueOf(f.getId()));

        //name
        isihloko.setText(f.getIsihloko());
        wordCount.setText(f.getNdebSentenceCount());
        //sex
        //eaNumber.setText(String.valueOf(f.getEAnumber()));
        // id
        volumeNumber.setText(String.valueOf(f.getVolumeNumber()));
        return convertView;
    }
}
