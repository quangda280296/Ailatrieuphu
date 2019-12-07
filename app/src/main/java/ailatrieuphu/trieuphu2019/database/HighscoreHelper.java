package com.quangda280296.ailatrieuphu.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by keban on 4/6/2018.
 */

public class HighscoreHelper extends SQLiteOpenHelper {

    private static final int SCHEMA_VERSION = 1;

    public HighscoreHelper(Context context) {
        super(context, "ailatrieuphu.db", null, SCHEMA_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tbl_ailatrieuphu (_id INTEGER PRIMARY KEY AUTOINCREMENT, reward INTEGER, time INTEGER)");
    }

    public Cursor getAll() {
        return (getReadableDatabase().rawQuery("SELECT * FROM tbl_ailatrieuphu ORDER BY reward DESC, time ASC", null));
    }

    public void insert(int reward, int time) {
        ContentValues cv = new ContentValues();
        cv.put("reward", reward);
        cv.put("time", time);
        getWritableDatabase().insert("tbl_ailatrieuphu", null, cv);
    }

    public int getSTT(Cursor c) {
        return (c.getPosition());
    }

    public int getReward(Cursor c) {
        return (c.getInt(1));
    }

    public int getTime(Cursor c) {
        return (c.getInt(2));
    }
}
