package com.cst2335.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.cst2335.myapplication.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageDatabaseSource {
    private MySQLiteHelper mySQLiteHelper;
    private SQLiteDatabase database;
    String[] allColumns = {
            MySQLiteHelper.COL_MESSAGE_ID,
            MySQLiteHelper.COL_MESSAGE,
            MySQLiteHelper.COL_IS_SENT,
            MySQLiteHelper.COL_IS_RECEIVED
    };

    public MessageDatabaseSource(Context context){
        mySQLiteHelper = new MySQLiteHelper(
                context,
                MySQLiteHelper.DATABASE_NAME,
                null,
                MySQLiteHelper.DATABASE_VERSION);
    }

    public void open(){
        database = mySQLiteHelper.getWritableDatabase();
    }

    public void close(){
        database.close();
        mySQLiteHelper.close();
        Log.i(MessageDatabaseSource.class.getName(), "SQLitehelper closed..!");
    }

    public void saveMessage(Message message){
        database.beginTransactionNonExclusive();
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COL_MESSAGE, message.getMessage());
        values.put(MySQLiteHelper.COL_IS_SENT, message.isSent() ? 1 : 0);
        values.put(MySQLiteHelper.COL_IS_RECEIVED, message.isReceived() ? 1: 0);
        long id = database.insert(MySQLiteHelper.TABLE_MESSAGE, null, values);
        database.setTransactionSuccessful();
        database.endTransaction();
        message.setId(id);
        Log.i(MessageDatabaseSource.class.getName(), "Message saved. id :" + id );
    }


    public List<Message> getAllMessages(){
        database.beginTransaction();
        List<Message> listMessages = new ArrayList<>();
        Cursor cursor = database.query(
                MySQLiteHelper.TABLE_MESSAGE,
                allColumns,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Message message = cursorToMessage(cursor);
            listMessages.add(message);
            cursor.moveToNext();
        }

        cursor.close();
        database.endTransaction();
        return listMessages;
    }


    public void fetchAllMessage(){
        database.beginTransaction();
        Log.i(MessageDatabaseSource.class.getName(), "Database Version: " + database.getVersion());
        Cursor cursor = database.query(
                MySQLiteHelper.TABLE_MESSAGE,
                allColumns,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        Log.i(MessageDatabaseSource.class.getName(), "Column Count:" + cursor.getColumnCount());
        Log.i(MessageDatabaseSource.class.getName(), "Columns are as below :");
        for (String col :
                cursor.getColumnNames()) {
            Log.i(MessageDatabaseSource.class.getName(), col);
        }
        Log.i(MessageDatabaseSource.class.getName(), "Row count:" + cursor.getCount());

        while(!cursor.isAfterLast()){
            Log.i(MessageDatabaseSource.class.getName(), "id: " + cursor.getLong(0));
            Log.i(MessageDatabaseSource.class.getName(), "message: " + cursor.getString(1));
            Log.i(MessageDatabaseSource.class.getName(), "isSent: " + cursor.getLong(2));
            Log.i(MessageDatabaseSource.class.getName(), "isRecieved: " + cursor.getLong(3));
            cursor.moveToNext();
        }

        cursor.close();
        database.endTransaction();
    }


    private Message cursorToMessage(Cursor cursor){
        Message message = new Message();
        message.setId(cursor.getLong(0));
        message.setMessage(cursor.getString(1));
        message.setSent(cursor.getInt(2)==1? true : false);
        message.setReceived(cursor.getInt(3) == 1 ? true : false);
        return message;
    }


    public void deleteMessage(long id){

        int st = database.delete(
                MySQLiteHelper.TABLE_MESSAGE,
                MySQLiteHelper.COL_MESSAGE_ID + "=?",
                new String[]{String.valueOf(id)});

        Log.i(Message.class.getName(), "Delete: "  + st);
    }

}

