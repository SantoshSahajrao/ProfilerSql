package com.example.ravi.profilersql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ravi on 2/12/17.
 */

public class DataopenHelper extends SQLiteOpenHelper {

    public DataopenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("Create table Data(ImageData String)");
        db.execSQL("Create table MusicData(Musicdatase String)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
