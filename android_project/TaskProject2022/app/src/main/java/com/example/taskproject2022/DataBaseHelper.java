package com.example.taskproject2022;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataBaseHelper extends SQLiteOpenHelper
{
    // Tables Names
    public static final String TASKS_TABLE = "TASKS";

    // Tables columns
    public static final String _ID = "_id";
    public static final String DATE = "date";
    public static final String DESCRIPTION = "description";
    public static final String PRIORITY = "priority";
    public static final String DONE = "done";

    // Database Information
    static final String DB_NAME = "TASK.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TASKS_TABLE = "create table " + TASKS_TABLE + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ DATE + " TEXT NOT NULL, " + DESCRIPTION + " TEXT NOT NULL, " +  PRIORITY + " TEXT NOT NULL, " + DONE + " TEXT NOT NULL );";

    public DataBaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE);
        onCreate(db);
    }

    public void insertTasks(String date, String description, String priority, String done)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(DATE, date);
        value.put(DESCRIPTION, description);
        value.put(PRIORITY, priority);
        value.put(DONE,done);

        db.insert(TASKS_TABLE, null, value);
    }

    public Cursor fetch()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = new String[] { _ID, DATE, DESCRIPTION, PRIORITY, DONE};
        Cursor cursor = db.query(TASKS_TABLE, columns, null, null, null, null, null);

        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int edit(long _id, String date, String description, String priority, String done)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat(date);

        String d = sdf.format(new Date());

        values.put(DATE, d);
        values.put(DATE, d);
        values.put(DESCRIPTION, description);
        values.put(PRIORITY, priority);
        values.put(DONE, done);

        int i = db.update(TASKS_TABLE, values, _ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TASKS_TABLE, _ID + "=" + _id, null);
    }

    public Cursor chooseCompleted()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String str_query = "SELECT * FROM " + TASKS_TABLE + " WHERE " +  DONE + " = " + "'" + "Done" + "'";
        Cursor cursor = db.rawQuery(str_query, null);

        cursor.moveToFirst();
        return cursor;
    }

    public Cursor chooseIncomplete()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String str_query = "SELECT * FROM "+ TASKS_TABLE + " WHERE " +  DONE + " = " + "'"+ "Start" +"'";
        Cursor cursor = db.rawQuery(str_query, null);

        cursor.moveToFirst();
        return cursor;
    }

    public Cursor findBydate(String date)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String str_query = "SELECT * FROM "+ TASKS_TABLE + " WHERE "+  DATE + " = " + "'"+ date +"'";
        Cursor cursor = db.rawQuery(str_query, null);

        cursor.moveToFirst();
        return cursor;
    }
}