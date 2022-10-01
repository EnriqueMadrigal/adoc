package com.intelik.appadoc.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

import Models.Custom;

public class DBHelper  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "AdocDb.db";
    public static final String NOTIFICATIONS_TABLE_NAME = "notifications";
    public static final String NOTIFICATIONS_COLUMN_ID = "id";
    public static final String NOTIFICATIONS_COLUMN_TITLE = "title";
    public static final String NOTIFICATIONS_COLUMN_DESC = "content";
    public static final String NOTIFICATIONS_COLUMN_IMAGE = "image";
    private HashMap hp;




    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table notifications(id integer primary key, title text, content text, image text)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS notifications");
        onCreate(db);

    }


    public boolean insertNotification (String title, String content, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("content", content);
        contentValues.put("image", image);
        db.insert(NOTIFICATIONS_TABLE_NAME, null, contentValues);
        return true;
    }


    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = String.format("select * from %s where id = %d", NOTIFICATIONS_TABLE_NAME, id);
        Cursor res = db.rawQuery( query, null );

        return  res;
    }

    public Integer deleteNotification (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(NOTIFICATIONS_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }


    public ArrayList<Custom> getAllNotifications() {
        ArrayList<Custom> array_list = new ArrayList<Custom>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( String.format("select * from %s",NOTIFICATIONS_TABLE_NAME), null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            if (res != null) {
                String title = res.getString(res.getColumnIndexOrThrow(NOTIFICATIONS_COLUMN_TITLE));
                String desc = res.getString(res.getColumnIndexOrThrow(NOTIFICATIONS_COLUMN_DESC));
                String image = res.getString(res.getColumnIndexOrThrow(NOTIFICATIONS_COLUMN_IMAGE));
                int id = res.getInt(res.getColumnIndexOrThrow(NOTIFICATIONS_COLUMN_ID));

                Custom newCustom = new Custom();
                newCustom.set_name(title);
                newCustom.set_desc(desc);
                newCustom.set_link(image);
                newCustom.set_id(id);

                array_list.add(newCustom);
            }

            res.moveToNext();
        }
        return array_list;
    }



}
