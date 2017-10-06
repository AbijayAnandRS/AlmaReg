package com.abijayana.user.almareg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by user on 01-08-2016.
 */
public class HotorNot {

    public static final String KEYROW_ID="_id";
    public static final String KEY_NAME="persons_name";
    public static final String KEY_HOTNESS="persons_hotness";


    public  String DATABASENAME="HotorNotdb";
    public  String TABLE_NAME="CHAT";
    private static final int DataBaseVersion  =   1;

    public   DbHlper dbhlpr;
    public Context ourcontxt;
    private SQLiteDatabase ourDatabase;




    private class DbHlper extends SQLiteOpenHelper {
        public DbHlper(Context context) {
            super(context, DATABASENAME, null, DataBaseVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE " + TABLE_NAME + " ("+
                            KEYROW_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                            KEY_NAME + " TEXT NOT NULL, "+
                            KEY_HOTNESS+ " INTEGER NOT NULL); "
            );

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(" DROP TABLE IF EXISTS "+ TABLE_NAME );
            onCreate(db);


        }
    }
    public HotorNot(Context c,String dbName) {
        ourcontxt = c;
        this.DATABASENAME=dbName;
    }
    public HotorNot open(){
        dbhlpr=new DbHlper(ourcontxt);
        ourDatabase=dbhlpr.getWritableDatabase();

        return this;
    }
    public void close(){


        dbhlpr.close();
    }
    public long createEntry(  String name, int hotness) {

        ContentValues cv=new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_HOTNESS,hotness);
        return ourDatabase.insert(TABLE_NAME, null, cv);
    }
    public ArrayList<nwsfeed> getDATA() {

        ArrayList<nwsfeed> lis=new ArrayList<nwsfeed>();

        String[] colums=new String[]{KEYROW_ID,KEY_NAME,KEY_HOTNESS};
        Cursor cr =ourDatabase.query(TABLE_NAME,colums,null,null,null,null,null);

        int irow=cr.getColumnIndex(KEYROW_ID);
        int iname=cr.getColumnIndex(KEY_NAME);
        int ihot=cr.getColumnIndex(KEY_HOTNESS);
        for(cr.moveToFirst();!cr.isAfterLast();cr.moveToNext()){
            nwsfeed o=new nwsfeed();
            o.setName(cr.getString(iname));
            o.setChatnumber(cr.getInt(ihot));
            lis.add(o);


        }



        return lis;
    }


}