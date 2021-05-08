package com.example.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "transaction";
    public static final String TB_NAME1 = "user";
    public static final String TB_NAME2 = "retail";
    public static final String TB_NAME3 = "comfirmed";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }
    public DBHelper(Context context) {

        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TB_NAME1+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,PASSWORD TEXT)");
        db.execSQL("CREATE TABLE "+TB_NAME2+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,PRICE FLOAT," +
                "PICTURE TEXT,LASTPRICE FLOAT,SELLERID INTEGER,LASTBUYERID INTEGER)");
        db.execSQL("CREATE TABLE "+TB_NAME3+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,PRICE FLOAT," +
                "PICTURE TEXT,LASTPRICE FLOAT,SELLERID INTEGER,LASTBUYERID INTEGER)");

        //db.execSQL("CREATE TABLE "+TB_NAME3+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,PASSWORD TEXT)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

        // TODO Auto-generated method stub
    }

}