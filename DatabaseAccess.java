package com.asimbongeni.asie.grmtranslate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.asimbongeni.asie.grmtranslate.Model.DatabaseAttributes;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Asie on 23/01/2017.
 */
public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    public static Context mContext;
    private final String TABLE_NAME_RAW = "cvdata";
    private final String TABLE_USER_CREDS = "user_creds";
    String seven = "7";

    private DatabaseAccess(Context context){
        this.openHelper = new ExternalSQLDatabase(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if (instance == null){
            instance = new DatabaseAccess(context);
            //mContext = context;
        }
        return instance;
    }
    public void open(){
        this.database = openHelper.getWritableDatabase();
    }

    public void close(){
        if (database!= null){
            this.database.close();
        }
    }

    public void insertNameSurname(String nameSurname){
        this.database = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name_surn", nameSurname);
        database.insert(TABLE_USER_CREDS,null, contentValues);
        database.close();
    }

    public void insertBranch(String branch){
        this.database = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("branch", branch);
        database.insert(TABLE_USER_CREDS,null, contentValues);
        database.close();
    }

    public void insertPhoneNumber(String phoneNumber){
        this.database = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("phone_number", phoneNumber);
        database.insert(TABLE_USER_CREDS,null, contentValues);
        database.close();
    }

    public void insertCardNumber(String cardNumber){
        this.database = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("card_number", cardNumber);
        database.insert(TABLE_USER_CREDS,null, contentValues);
        database.close();
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0, outputStream);
        return outputStream.toByteArray();
    }

    public void insertImage(Bitmap img){
        this.database = openHelper.getWritableDatabase();
        byte[] data = getBitmapAsByteArray(img);
        ContentValues contentValues = new ContentValues();
        contentValues.put("image", data);
        database.insert(TABLE_USER_CREDS,null, contentValues);
        database.close();
    }

    public void updateImage(Bitmap bitmap) {
        this.database = openHelper.getWritableDatabase();
        byte[] data = getBitmapAsByteArray(bitmap);
        ContentValues contentValues = new ContentValues();
        contentValues.put("image", data);
        database.update(TABLE_USER_CREDS, contentValues, "id" + " = ?", new String[] { String.valueOf(1) });
        database.close();
    }

    public Bitmap getImage(){
        this.database = openHelper.getReadableDatabase();
        String select = "SELECT image FROM " + TABLE_USER_CREDS;
        Cursor cursor = database.rawQuery(select, null);
        if (cursor.moveToFirst()){
            byte[] imgByte = cursor.getBlob(0);
            cursor.close();
            return BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);
        }
        if (cursor != null && !cursor.isClosed()){
            cursor.close();
        }
        return null;
    }

    public List<DatabaseAttributes> getNdebeleSentencesAll(){
        List<DatabaseAttributes> allEntriesFromDb = new ArrayList<DatabaseAttributes>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_RAW + " WHERE englishTranslated = " + seven + " AND ndebeleWordCount > 1";
        this.database = openHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                DatabaseAttributes dbAttributes = new DatabaseAttributes();
                dbAttributes.setId(Integer.parseInt(cursor.getString(0)));
                dbAttributes.setNdebSentences(cursor.getString(1));
                dbAttributes.setNdebSentenceCount(Integer.parseInt(cursor.getString(2)));
                dbAttributes.setIsihloko(cursor.getString(3));
                dbAttributes.setVolumeNumber(Integer.parseInt(cursor.getString(4)));
                dbAttributes.setNdebTranslated(cursor.getString(5));
                dbAttributes.setNdebTranslatedCount(Integer.parseInt(cursor.getString(6)));
                dbAttributes.setServerSync(Integer.parseInt(cursor.getString(7)));
                allEntriesFromDb.add(dbAttributes);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        this.database.close();
        return allEntriesFromDb;
    }

    public List<DatabaseAttributes> getNdebeleSentencesAllLimit(int limit){
        List<DatabaseAttributes> allEntriesFromDb = new ArrayList<DatabaseAttributes>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_RAW + " WHERE englishTranslated = " + seven + " LIMIT " + limit + ";";
        this.database = openHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                DatabaseAttributes dbAttributes = new DatabaseAttributes();
                dbAttributes.setId(Integer.parseInt(cursor.getString(0)));
                dbAttributes.setNdebSentences(cursor.getString(1));
                dbAttributes.setNdebSentenceCount(Integer.parseInt(cursor.getString(2)));
                dbAttributes.setIsihloko(cursor.getString(3));
                dbAttributes.setVolumeNumber(Integer.parseInt(cursor.getString(4)));
                dbAttributes.setNdebTranslated(cursor.getString(5));
                dbAttributes.setNdebTranslatedCount(Integer.parseInt(cursor.getString(6)));
                dbAttributes.setServerSync(Integer.parseInt(cursor.getString(7)));
                allEntriesFromDb.add(dbAttributes);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        this.database.close();
        return allEntriesFromDb;
    }

    public List<DatabaseAttributes> getNdebeleSentenceByIsihloko(int isihloko){
        List<DatabaseAttributes> allEntriesFromDb = new ArrayList<DatabaseAttributes>();
        //String selectQuery = "SELECT  * FROM " + TABLE_NAME_RAW + " WHERE ndebeleWordCount > 1 AND isihloko = " + isihloko + " AND englishTranslated = " +  "'" + seven + "'"  + ";";
        //String selectQuery = "SELECT * FROM " + TABLE_NAME_RAW + " WHERE englishTranslated = " + "'" + seven + "'" + " and isihloko = " + isihloko + ";";
        String [] columnsToReturn = {"id", "ndebeleSentence", "ndebeleWordCount", "isihloko", "volumeNumber", "englishTranslated",
                "englishTranslatedCount"};
        String columnForWhere =   "englishTranslated" + " LIKE ?" + "AND " + "isihloko = ?";
        String[] selectionArgs = {"7",String.valueOf(isihloko)};


        //Log.d("byShloko", selectQuery);
        this.database = openHelper.getReadableDatabase();
        //Cursor cursor = database.rawQuery(selectQuery, null);

        Cursor cursor = database.query(TABLE_NAME_RAW, columnsToReturn, columnForWhere, selectionArgs, null,null,null);

        if (cursor.moveToFirst()){
            do {
                DatabaseAttributes dbAttributes = new DatabaseAttributes();
                dbAttributes.setId(Integer.parseInt(cursor.getString(0)));
                dbAttributes.setNdebSentences(cursor.getString(1));
                dbAttributes.setNdebSentenceCount(Integer.parseInt(cursor.getString(2)));
                dbAttributes.setIsihloko(cursor.getString(3));
                dbAttributes.setVolumeNumber(Integer.parseInt(cursor.getString(4)));
                dbAttributes.setNdebTranslated(cursor.getString(5));
                dbAttributes.setNdebTranslatedCount(Integer.parseInt(cursor.getString(6)));
                allEntriesFromDb.add(dbAttributes);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        this.database.close();
        return allEntriesFromDb;
    }



    public List<DatabaseAttributes> getEnglishTranslatedSentenceAll(){
        List<DatabaseAttributes> allEntriesFromDb = new ArrayList<DatabaseAttributes>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_RAW + " WHERE englishTranslated != " + "'" + "7"+ "'" + ";";
        Log.d("translatedQ", selectQuery);
        this.database = openHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                DatabaseAttributes dbAttributes = new DatabaseAttributes();
                dbAttributes.setId(Integer.parseInt(cursor.getString(0)));
                dbAttributes.setNdebSentences(cursor.getString(1));
                dbAttributes.setNdebSentenceCount(Integer.parseInt(cursor.getString(2)));
                dbAttributes.setIsihloko(cursor.getString(3));
                dbAttributes.setVolumeNumber(Integer.parseInt(cursor.getString(4)));
                dbAttributes.setNdebTranslated(cursor.getString(5));
                dbAttributes.setNdebTranslatedCount(Integer.parseInt(cursor.getString(6)));
                dbAttributes.setServerSync(Integer.parseInt(cursor.getString(7)));
                allEntriesFromDb.add(dbAttributes);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        this.database.close();
        return allEntriesFromDb;
    }

    // Getting count of all work done
    public int getEnglishTranslatedSentenceCount() {
        //String countQuery = "SELECT  * FROM " + TABLE_NAME_RAW + " WHERE englishTranslated != " + seven;
        String selectQuery = "SELECT * FROM " + TABLE_NAME_RAW + " WHERE englishTranslated != " + "'" + seven + "'" + ";";
        Log.d("EnglishCountQ", selectQuery);
        this.database = openHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        this.database.close();
        return count;

    }

    // Getting count of work done by isihloko
    public int getTranslatedSentenceCountByIsihloko(int isihloko) {
        //String isihlokoText = "ISIHLOKO " + isihloko;
        String countQuery = "SELECT  * FROM " + TABLE_NAME_RAW + " WHERE englishTranslated != " + seven + " AND isihloko  =  " + isihloko;
        Log.d("DB query" ,countQuery);
        this.database = openHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        this.database.close();
        return count;
    }

    // Getting count of work done by volume number
    public int getEnglishTranslatedSentenceCountByVolume(int volumeNumber) {
        String countQuery = "SELECT  * FROM " + TABLE_NAME_RAW + " WHERE englishTranslated != " + seven + " AND volumeNumber = " + volumeNumber;
        this.database = openHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        this.database.close();
        return count;
    }

    // Getting count of ndebele by volume number
    public int getNdebeleSentenceCountByVolume(int volumeNumber) {
        String countQuery = "SELECT  * FROM " + TABLE_NAME_RAW + " WHERE englishTranslated = " + seven + " AND volumeNumber = " + volumeNumber;
        this.database = openHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        this.database.close();
        return count;
    }


    // Getting count of work done by isihloko
    public int getNdebeleSentenceCountByIsihloko(int isihloko) {
        //String isihlokoText = "ISIHLOKO " + isihloko;
        String countQuery = "SELECT  * FROM " + TABLE_NAME_RAW + " WHERE englishTranslated LIKE " + "'" + seven +"'" + " AND isihloko = " + isihloko+ ";";
        Log.d("ndebsByShlokoQ" ,countQuery);
        this.database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        this.database.close();
        return count;
    }

    public int saveTranslation (DatabaseAttributes databaseAttributes, String id){
        this.database = openHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("englishTranslated", databaseAttributes.getNdebTranslated());
        //values.put("translatedWordCount", databaseAttributes.getNdebTranslatedCount());
        //Date date = new Date();
        //values.put("saveDate",date.toString());

        // updating row by id
        //Toast.makeText(mContext, "Save successfull!", Toast.LENGTH_SHORT).show();
        return database.update(TABLE_NAME_RAW, values, "id" + " = ?",
                new String[] { id });

    }


    public List<DatabaseAttributes> getAllUnsyncedEntries() {
        List<DatabaseAttributes> allEntriesFromDb = new ArrayList<DatabaseAttributes>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_RAW + " WHERE englishTranslated != " + "'" + "7"+ "'" + " AND serverSync = " + "'" + "0"+ "'" + ";";
        Log.d("translatedQ", selectQuery);
        this.database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                DatabaseAttributes dbAttributes = new DatabaseAttributes();
                dbAttributes.setId(Integer.parseInt(cursor.getString(0)));
                dbAttributes.setNdebSentences(cursor.getString(1));
                dbAttributes.setNdebSentenceCount(Integer.parseInt(cursor.getString(2)));
                dbAttributes.setIsihloko(cursor.getString(3));
                dbAttributes.setVolumeNumber(Integer.parseInt(cursor.getString(4)));
                dbAttributes.setNdebTranslated(cursor.getString(5));
                dbAttributes.setNdebTranslatedCount(Integer.parseInt(cursor.getString(6)));
                dbAttributes.setServerSync(Integer.parseInt(cursor.getString(7)));
                allEntriesFromDb.add(dbAttributes);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        this.database.close();
        return allEntriesFromDb;

    }

    public int updateSendToServerColumn(int updateCol) {
        this.database = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("serverSync", "1");
        // updating server sync column
        Log.d("UpdateVal", values.toString());
        return database.update(TABLE_NAME_RAW, values, "id" + " = ?", new String[] { String.valueOf(updateCol) });
    }

    public int getUnsyncCount() {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_RAW + " WHERE englishTranslated != " + "'" + "7"+ "'" + " AND serverSync = " + "'" + "0"+ "'" + ";";
        Log.d("translatedQ", selectQuery);
        this.database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        int out = cursor.getCount();
        cursor.close();
        this.database.close();
        return out;
    }

    public int getOneSentenceCount(int id){
        String select = "SELECT * FROM " + TABLE_NAME_RAW + " WHERE id = " + id;
        Log.d("translatedQ", select);
        this.database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(select, null);
        return cursor.getCount();
    }

    public DatabaseAttributes getOneSentence(int id){
        String select = "SELECT * FROM " + TABLE_NAME_RAW + " WHERE id = " + id;
        Log.d("translatedQ", select);
        this.database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(select, null);
        DatabaseAttributes dbAttributes = new DatabaseAttributes();
        if (cursor.moveToFirst()){
            do {
                dbAttributes.setId(Integer.parseInt(cursor.getString(0)));
                dbAttributes.setNdebSentences(cursor.getString(1));
                dbAttributes.setNdebSentenceCount(Integer.parseInt(cursor.getString(2)));
                dbAttributes.setIsihloko(cursor.getString(3));
                dbAttributes.setVolumeNumber(Integer.parseInt(cursor.getString(4)));
                dbAttributes.setNdebTranslated(cursor.getString(5));
                dbAttributes.setNdebTranslatedCount(Integer.parseInt(cursor.getString(6)));
                dbAttributes.setServerSync(Integer.parseInt(cursor.getString(7)));
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        this.database.close();

        return dbAttributes;
    }

    public List<Integer> getAllTranslatedWordsCount(){
        List<Integer> wordCountArray = new ArrayList<>(670);
        String selectQuery = "SELECT  ndebeleWordCount FROM " + TABLE_NAME_RAW + " WHERE englishTranslated != " + "'" + "7"+ "'" + ";";
        Log.d("translatedQ", selectQuery);
        this.database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                wordCountArray.add(Integer.valueOf(cursor.getString(0)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        this.database.close();
        return wordCountArray;
    }


}
