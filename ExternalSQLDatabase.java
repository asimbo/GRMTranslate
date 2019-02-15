package com.asimbongeni.asie.grmtranslate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.asimbongeni.asie.grmtranslate.Model.DatabaseAttributes;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;


public class ExternalSQLDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "cvDataNine.sqlite";
    private static final int DATABASE_VERSION = 2;

    public ExternalSQLDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
