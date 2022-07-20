package com.cst2335.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "chat.db";
    public static String TABLE_MESSAGE = "message_table";
    public static String COL_MESSAGE_ID = "message_id";
    public static String COL_MESSAGE = "message";
    public static String COL_IS_SENT = "isSent";
    public static String COL_IS_RECEIVED = "isReceived";

    public MySQLiteHelper(@Nullable Context context,
                            @Nullable String name,
                            @Nullable SQLiteDatabase.CursorFactory factory,
                            int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.beginTransaction();
        String createDatabase = "create table " + TABLE_MESSAGE
                + " ("
                + COL_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_MESSAGE + " TEXT NOT NULL,"
                + COL_IS_SENT + " INTEGER,"
                + COL_IS_RECEIVED + " INTEGER"
                +")";
        sqLiteDatabase.execSQL(createDatabase);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }



}
