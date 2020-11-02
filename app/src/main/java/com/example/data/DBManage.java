package com.example.data;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;


public class DBManage {

    private static final String TAG = "DBManage";

    public static Connection getConnection(final String dbName) {
        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver"); //加载驱动
            String ip = "cd-cdb-hmbny6rw.sql.tencentcdb.com";
            conn = DriverManager.getConnection(
                    "jdbc:mysql://" + ip + ":62331/" + dbName,
                    "root", "502de520");
            Log.i("TAG", "连接数据库成功");

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    public static HashMap<String, String> getUserInfoByID(String ID) {
        HashMap<String,String> map = new HashMap<>();

        Connection conn = getConnection("test");
        Log.d(TAG, " 连接"+conn);
        try {
            Statement st = conn.createStatement();
            String sql = "select * from user where ID = '" + ID + "'";
            ResultSet res = st.executeQuery(sql);

            if (res == null) {
                return null;

            } else {

                int cnt = res.getMetaData().getColumnCount();
                //res.last(); int rowCnt = res.getRow(); res.first();
                res.next();

                for (int i = 1; i <= cnt; ++i) {
                    String field = res.getMetaData().getColumnName(i);
                    map.put(field, res.getString(field));

                }

                conn.close();
                st.close();
                res.close();
                return map;

            }

        } catch (Exception e) {

            e.printStackTrace();

            Log.d(TAG, " 数据操作异常");

            return null;

        }

    }

}

