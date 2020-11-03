package com.example.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.item.RetailItem;
import com.example.item.UserItem;

public class DBManager {

    private DBHelper dbHelper;
    private String TBNAME1, TBNAME2, TBNAME3;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME1 = DBHelper.TB_NAME1;
        TBNAME2 = DBHelper.TB_NAME2;
        TBNAME3 = DBHelper.TB_NAME3;
        dbHelper.getWritableDatabase();
    }

    public void add(UserItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID", item.getCurName());
        values.put("PASSWORD", item.getCurPassword());
        db.insert(TBNAME1, null, values);
        db.close();
    }

    public void add_retail(RetailItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", item.getName());
        values.put("PRICE", item.getPrice());
        values.put("PICTURE", item.getPicture());
        values.put("SELLERID", item.getSellerID());
        values.put("LASTPRICE", item.getLastprice());
        values.put("LASTBUYERID", item.getLastbuyerID());
        db.insert(TBNAME2, null, values);
        db.close();
    }

    public void add_comfirm(RetailItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID", item.getID());
        values.put("NAME", item.getName());
        values.put("PRICE", item.getPrice());
        values.put("PICTURE", item.getPicture());
        values.put("SELLERID", item.getSellerID());
        values.put("LASTPRICE", item.getLastprice());
        values.put("LASTBUYERID", item.getLastbuyerID());
        db.insert(TBNAME3, null, values);
        db.close();
    }

    public void addAll(List<UserItem> list) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (UserItem item : list) {
            ContentValues values = new ContentValues();
            values.put("ID", item.getCurName());
            values.put("PASSWORD", item.getCurPassword());
            db.insert(TBNAME1, null, values);
        }
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME1, null, null);
        db.close();
    }

    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME1, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void delete_retail(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME2, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void update(UserItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PASSWORD", item.getCurPassword());
        db.update(TBNAME1, values, "ID=?", new String[]{String.valueOf(item.getCurName())});
        db.close();
    }

    public void update_retail(RetailItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("LASTPRICE", item.getLastprice());
        values.put("LASTBUYERID", item.getLastbuyerID());
        db.update(TBNAME2, values, "ID=?", new String[]{String.valueOf(item.getID())});
        db.close();
    }

    public List<UserItem> listAll() {
        List<UserItem> userList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME1, null, null, null, null, null, null);
        if (cursor != null) {
            userList = new ArrayList<UserItem>();
            while (cursor.moveToNext()) {
                UserItem item = new UserItem();
                item.setCurName(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setCurPassword(cursor.getString(cursor.getColumnIndex("PASSWORD")));

                userList.add(item);
            }
            cursor.close();
        }
        db.close();
        return userList;

    }

    public List<RetailItem> listAll_retail() {
        List<RetailItem> retailList = new ArrayList<RetailItem>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME2, null, null, null, null, null, null);
        if (cursor != null) {
            retailList = new ArrayList<RetailItem>();
            while (cursor.moveToNext()) {
                RetailItem item = new RetailItem();

                item.setID(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                item.setPrice(cursor.getFloat(cursor.getColumnIndex("PRICE")));
                item.setPicture(cursor.getString(cursor.getColumnIndex("PICTURE")));
                item.setLastprice(cursor.getFloat(cursor.getColumnIndex("LASTPRICE")));
                item.setSellerID(cursor.getInt(cursor.getColumnIndex("SELLERID")));
                item.setLastbuyerID(cursor.getInt(cursor.getColumnIndex("LASTBUYERID")));
                retailList.add(item);
            }
            cursor.close();
        }
        db.close();
        return retailList;

    }

    public UserItem findById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME1, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        UserItem userItem = null;
        if (cursor != null && cursor.moveToFirst()) {
            userItem = new UserItem();
            //userItem.setCurName(cursor.getInt(cursor.getColumnIndex("CURNAME")));
            userItem.setCurPassword(cursor.getString(cursor.getColumnIndex("PASSWORD")));
            cursor.close();
        }
        db.close();
        return userItem;
    }

    public RetailItem findById_retail(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME2, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        RetailItem retailItem = null;
        if (cursor != null && cursor.moveToFirst()) {
            retailItem = new RetailItem();
            //userItem.setCurName(cursor.getInt(cursor.getColumnIndex("CURNAME")));
            retailItem.setID(cursor.getInt(cursor.getColumnIndex("ID")));
            retailItem.setName(cursor.getString(cursor.getColumnIndex("NAME")));
            retailItem.setPrice(cursor.getFloat(cursor.getColumnIndex("PRICE")));
            retailItem.setPicture(cursor.getString(cursor.getColumnIndex("PICTURE")));
            retailItem.setLastprice(cursor.getFloat(cursor.getColumnIndex("LASTPRICE")));
            retailItem.setSellerID(cursor.getInt(cursor.getColumnIndex("SELLERID")));
            retailItem.setLastbuyerID(cursor.getInt(cursor.getColumnIndex("LASTBUYERID")));
            cursor.close();
        }
        db.close();
        return retailItem;
    }

    public RetailItem findById_comfirm(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME3, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        RetailItem retailItem = null;
        if (cursor != null && cursor.moveToFirst()) {
            retailItem = new RetailItem();
            //userItem.setCurName(cursor.getInt(cursor.getColumnIndex("CURNAME")));
            retailItem.setID(cursor.getInt(cursor.getColumnIndex("ID")));
            retailItem.setLastprice(cursor.getFloat(cursor.getColumnIndex("LASTPRICE")));
            retailItem.setLastbuyerID(cursor.getInt(cursor.getColumnIndex("LASTBUYERID")));
            cursor.close();
        }
        db.close();
        return retailItem;
    }

    public List<RetailItem> findByName_retail(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "select * from "+TBNAME2+" where NAME like ?";
        Cursor cursor = db.rawQuery(sql, new String[]{"%"+name+"%"});
        /*Cursor cursor=db.query(TBNAME2, new String[]{"ID","NAME","PRICE","PICTURE","LASTPRICE","SELLERID","LASTBUYERID"},
                "NAME=?", new String[]{name}, null, null, null);*/
        //Cursor cursor = db.query(TBNAME2, null, "NAME=?", new String[]{String.valueOf(name)}, null, null, null);
        List<RetailItem> retailList = new ArrayList<RetailItem>();
        if (cursor != null) {
            retailList = new ArrayList<RetailItem>();
            while (cursor.moveToNext()) {
                RetailItem item = new RetailItem();

                item.setID(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                item.setPrice(cursor.getFloat(cursor.getColumnIndex("PRICE")));
                item.setPicture(cursor.getString(cursor.getColumnIndex("PICTURE")));
                item.setLastprice(cursor.getFloat(cursor.getColumnIndex("LASTPRICE")));
                item.setSellerID(cursor.getInt(cursor.getColumnIndex("SELLERID")));
                item.setLastbuyerID(cursor.getInt(cursor.getColumnIndex("LASTBUYERID")));
                retailList.add(item);
            }
            cursor.close();
        }
        db.close();
        return retailList;
    }



    public List<RetailItem> findByBuyerId_retail(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME2, null, "LASTBUYERID=?", new String[]{String.valueOf(id)}, null, null, null);

        List<RetailItem> retailList = new ArrayList<RetailItem>();
        if (cursor != null) {
            retailList = new ArrayList<RetailItem>();
            while (cursor.moveToNext()) {
                RetailItem item = new RetailItem();
                item.setID(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                item.setPrice(cursor.getFloat(cursor.getColumnIndex("PRICE")));
                item.setPicture(cursor.getString(cursor.getColumnIndex("PICTURE")));
                item.setLastprice(cursor.getFloat(cursor.getColumnIndex("LASTPRICE")));
                item.setSellerID(cursor.getInt(cursor.getColumnIndex("SELLERID")));
                item.setLastbuyerID(cursor.getInt(cursor.getColumnIndex("LASTBUYERID")));
                retailList.add(item);
            }
            cursor.close();
        }
        db.close();
        return retailList;
    }


    public List<RetailItem> findBySellerId_retail(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME2, null, "SELLERID=?", new String[]{String.valueOf(id)}, null, null, null);
        List<RetailItem> retailList = new ArrayList<RetailItem>();
        if (cursor != null) {
            retailList = new ArrayList<RetailItem>();
            while (cursor.moveToNext()) {
                RetailItem item = new RetailItem();
                item.setID(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                item.setPrice(cursor.getFloat(cursor.getColumnIndex("PRICE")));
                item.setPicture(cursor.getString(cursor.getColumnIndex("PICTURE")));
                item.setLastprice(cursor.getFloat(cursor.getColumnIndex("LASTPRICE")));
                item.setSellerID(cursor.getInt(cursor.getColumnIndex("SELLERID")));
                item.setLastbuyerID(cursor.getInt(cursor.getColumnIndex("LASTBUYERID")));
                retailList.add(item);
            }
            cursor.close();
        }
        db.close();
        return retailList;
    }



    public List<RetailItem> findByBuyerId_comfirm(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME3, null, "LASTBUYERID=?", new String[]{String.valueOf(id)}, null, null, null);

        List<RetailItem> retailList = new ArrayList<RetailItem>();
        if (cursor != null) {
            retailList = new ArrayList<RetailItem>();
            while (cursor.moveToNext()) {
                RetailItem item = new RetailItem();
                item.setID(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                item.setPrice(cursor.getFloat(cursor.getColumnIndex("PRICE")));
                item.setPicture(cursor.getString(cursor.getColumnIndex("PICTURE")));
                item.setLastprice(cursor.getFloat(cursor.getColumnIndex("LASTPRICE")));
                item.setSellerID(cursor.getInt(cursor.getColumnIndex("SELLERID")));
                item.setLastbuyerID(cursor.getInt(cursor.getColumnIndex("LASTBUYERID")));
                retailList.add(item);
            }
            cursor.close();
        }
        db.close();
        return retailList;
    }


    public List<RetailItem> findBySellerId_comfirm(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME3, null, "SELLERID=?", new String[]{String.valueOf(id)}, null, null, null);
        List<RetailItem> retailList = new ArrayList<RetailItem>();
        if (cursor != null) {
            retailList = new ArrayList<RetailItem>();
            while (cursor.moveToNext()) {
                RetailItem item = new RetailItem();
                item.setID(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                item.setPrice(cursor.getFloat(cursor.getColumnIndex("PRICE")));
                item.setPicture(cursor.getString(cursor.getColumnIndex("PICTURE")));
                item.setLastprice(cursor.getFloat(cursor.getColumnIndex("LASTPRICE")));
                item.setSellerID(cursor.getInt(cursor.getColumnIndex("SELLERID")));
                item.setLastbuyerID(cursor.getInt(cursor.getColumnIndex("LASTBUYERID")));
                retailList.add(item);
            }
            cursor.close();
        }
        db.close();
        return retailList;
    }
}