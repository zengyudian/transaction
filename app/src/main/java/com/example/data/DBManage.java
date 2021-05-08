package com.example.data;

import android.util.Log;

import com.example.item.RetailItem;
import com.example.item.UserItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DBManage {

    private static final String TAG = "DBManage";

    //连接数据库
    public static Connection getConnection() {
        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver"); //加载驱动
            String ip = "cdb-dh3ktjre.cd.tencentcdb.com";
            conn = DriverManager.getConnection(
                    "jdbc:mysql://" + ip + ":10024/transaction" ,
                    "root", "502de520");
            //时区   +"?characterEncoding=utf-8&serverTimezone=UTC"
            Log.i("TAG", "连接数据库成功");

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return conn;
    }


    //user表中添加用户
    public void add(UserItem item) {

        Connection conn = getConnection();
        Log.d(TAG, " 连接"+conn);
        int userID=item.getCurName();
        String password=item.getCurPassword();
        try {
            //Statement st = conn.createStatement();
            String sql = "insert into user values('"+userID+"','"+password+"')";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();

            conn.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, " 数据操作异常");
        }
    }

    //在商品中添加
    public void add_retail(RetailItem item) {
        Connection conn = getConnection();
        Log.d(TAG, " 连接"+conn);
        //int ID=item.getID();
        String name=item.getName();
        Float price=item.getPrice();
        String picture=item.getPicture();
        Float lastprice=item.getLastprice();
        int sellerID=item.getSellerID();
        int lastbuyerID=item.getLastbuyerID();
        try {
            //Statement st = conn.createStatement();
            String sql = "insert into retail(NAME,PRICE,PICTURE,LASTPRICE,SELLERID,LASTBUYERID) " +
                    "values('"+name+"','"+price+"','"+picture+"'" +
                    ",'"+lastprice+"','"+sellerID+"','"+lastbuyerID+"')";


            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();

            conn.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, " 数据操作异常");
        }
    }

    //在完成订单中添加
    public void add_comfirm(RetailItem item) {
        Connection conn = getConnection();
        Log.d(TAG, " 连接"+conn);
        int ID=item.getID();
        String name=item.getName();
        Float price=item.getPrice();
        String picture=item.getPicture();
        Float lastprice=item.getLastprice();
        int sellerID=item.getSellerID();
        int lastbuyerID=item.getLastbuyerID();
        try {
            //Statement st = conn.createStatement();
            String sql = "insert into comfirmed values('"+ID+"','"+name+"','"+price+"','"+picture+"'" +
                    ",'"+lastprice+"','"+sellerID+"','"+lastbuyerID+"')";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();

            conn.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, " 数据操作异常");
        }
    }


    //在未完成订单中删除
    public void delete_retail(int id) {
        Connection conn = getConnection();

        try {
            String sql = "delete from retail where ID="+id;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            conn.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, " 数据操作异常");
        }
    }

    //user用户表中根据ID查询
    public UserItem findById(int ID) {
        Connection conn = getConnection();
        Log.d(TAG, " 连接" + conn);
        UserItem userItem=new UserItem();
        try {
            Statement st = conn.createStatement();
            String sql = "select * from user where ID ="+ID;
            ResultSet rs = st.executeQuery(sql);

            if(rs!=null&&rs.next()){
                userItem.setCurName(rs.getInt("ID"));
                userItem.setCurPassword(rs.getString("PASSWORD"));

                }
                conn.close();
                st.close();
                rs.close();
                return userItem;
            } catch (SQLException e) {
                e.printStackTrace();
                Log.d(TAG, " 数据操作异常");
                return null;
        }
    }

    //完成订单中根据ID查询
    public RetailItem findById_comfirm(int ID) {
        Connection conn = getConnection();
        Log.d(TAG, " 连接" + conn);
        RetailItem retailItem=new RetailItem();
        try {
            Statement st = conn.createStatement();
            String sql = "select * from comfirmed where ID ="+ID;
            ResultSet rs = st.executeQuery(sql);

            if(rs!=null&&rs.next()){
                retailItem.setID(rs.getInt("ID"));
                retailItem.setName(rs.getString("NAME"));
                retailItem.setPrice(rs.getFloat("PRICE"));
                retailItem.setPicture(rs.getString("PICTURE"));
                retailItem.setLastprice(rs.getFloat("LASTPRICE"));
                retailItem.setSellerID(rs.getInt("SELLERID"));
                retailItem.setLastbuyerID(rs.getInt("LASTBUYERID"));
            }
            conn.close();
            st.close();
            rs.close();
            return retailItem;
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, " 数据操作异常");
            return null;
        }
    }


    //完成拍卖品中根据ID查询
    public RetailItem findById_retail(int ID) {
        Connection conn = getConnection();
        Log.d(TAG, " 连接" + conn);
        RetailItem retailItem=new RetailItem();
        try {
            Statement st = conn.createStatement();
            String sql = "select * from retail where ID ="+ID;
            ResultSet rs = st.executeQuery(sql);

            if(rs!=null&&rs.next()){
                retailItem.setID(rs.getInt("ID"));
                retailItem.setName(rs.getString("NAME"));
                retailItem.setPrice(rs.getFloat("PRICE"));
                retailItem.setPicture(rs.getString("PICTURE"));
                retailItem.setLastprice(rs.getFloat("LASTPRICE"));
                retailItem.setSellerID(rs.getInt("SELLERID"));
                retailItem.setLastbuyerID(rs.getInt("LASTBUYERID"));
            }
            conn.close();
            st.close();
            rs.close();
            return retailItem;
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, " 数据操作异常");
            return null;
        }
    }


    //完成拍卖品中根据名字查找
    public List<RetailItem> findByName_retail(String name) {
        List<RetailItem> list=new ArrayList<RetailItem>();
        Connection conn = getConnection();
        Log.d(TAG, " 连接" + conn);
        try {
            Statement st = conn.createStatement();
            String sql = "select * from retail where NAME like '%"+name+"%'";
            ResultSet rs = st.executeQuery(sql);

            RetailItem retailItem;
            if(rs!=null){
                while(rs.next()){
                    retailItem=new RetailItem();
                    retailItem.setID(rs.getInt("ID"));
                    retailItem.setName(rs.getString("NAME"));
                    retailItem.setPrice(rs.getFloat("PRICE"));
                    retailItem.setPicture(rs.getString("PICTURE"));
                    retailItem.setLastprice(rs.getFloat("LASTPRICE"));
                    retailItem.setSellerID(rs.getInt("SELLERID"));
                    retailItem.setLastbuyerID(rs.getInt("LASTBUYERID"));
                    list.add(retailItem);
                }

            }
            conn.close();
            st.close();
            rs.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, " 数据操作异常");
            return null;
        }
    }

    //更新物品价格和买家
    public void update_retail(RetailItem item) {
        Connection conn = getConnection();
        int ID=item.getID();
        Float price=item.getLastprice();
        int buyerID=item.getLastbuyerID();
        try {
            //String sql = "update retail set LASTPRICE="+price+" and LASTBUYERID="+buyerID+" where ID ="+ID;
            String sql = "update retail set LASTPRICE="+price+" where ID ="+ID;
            String sql1 = "update retail set LASTBUYERID="+buyerID+" where ID ="+ID;
            Log.d(TAG, " id"+buyerID);
            Log.d(TAG, " p"+price);
            Log.d(TAG, " buyerid"+buyerID);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps = conn.prepareStatement(sql1);
            ps.executeUpdate();

            conn.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, " 数据操作异常");
        }
    }


    public List<RetailItem> listAll_retail() {
        List<RetailItem> retailList = new ArrayList<RetailItem>();
        Connection conn = getConnection();
        RetailItem item;

        try {
            Statement st = conn.createStatement();
            String sql = "select * from retail";
            ResultSet rs = st.executeQuery(sql);

            if(rs!=null){
                while(rs.next()){
                    item=new RetailItem();
                    item.setID(rs.getInt("ID"));
                    item.setName(rs.getString("NAME"));
                    item.setPrice(rs.getFloat("PRICE"));
                    item.setPicture(rs.getString("PICTURE"));
                    item.setLastprice(rs.getFloat("LASTPRICE"));
                    item.setSellerID(rs.getInt("SELLERID"));
                    item.setLastbuyerID(rs.getInt("LASTBUYERID"));
                    retailList.add(item);
                }
            }
            conn.close();
            st.close();
            rs.close();
            return retailList;
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, " 数据操作异常");
            return null;
        }
    }

    public List<RetailItem> findByBuyerId_retail(int id) {
        List<RetailItem> retailList = new ArrayList<RetailItem>();
        Connection conn = getConnection();
        RetailItem item;

        try {
            Statement st = conn.createStatement();
            String sql = "select * from retail where LASTBUYERID="+id;
            ResultSet rs = st.executeQuery(sql);

            if(rs!=null){
                while(rs.next()){
                    item=new RetailItem();
                    item.setID(rs.getInt("ID"));
                    item.setName(rs.getString("NAME"));
                    item.setPrice(rs.getFloat("PRICE"));
                    item.setPicture(rs.getString("PICTURE"));
                    item.setLastprice(rs.getFloat("LASTPRICE"));
                    item.setSellerID(rs.getInt("SELLERID"));
                    item.setLastbuyerID(rs.getInt("LASTBUYERID"));
                    retailList.add(item);
                }
            }
            conn.close();
            st.close();
            rs.close();
            return retailList;
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, " 数据操作异常");
            return null;
        }
    }


    public List<RetailItem> findBySellerId_retail(int id) {
        List<RetailItem> retailList = new ArrayList<RetailItem>();
        Connection conn = getConnection();
        RetailItem item;

        try {
            Statement st = conn.createStatement();
            String sql = "select * from retail where SELLERID="+id;
            ResultSet rs = st.executeQuery(sql);

            if(rs!=null){
                while(rs.next()){
                    item=new RetailItem();
                    item.setID(rs.getInt("ID"));
                    item.setName(rs.getString("NAME"));
                    item.setPrice(rs.getFloat("PRICE"));
                    item.setPicture(rs.getString("PICTURE"));
                    item.setLastprice(rs.getFloat("LASTPRICE"));
                    item.setSellerID(rs.getInt("SELLERID"));
                    item.setLastbuyerID(rs.getInt("LASTBUYERID"));
                    retailList.add(item);
                }
            }
            conn.close();
            st.close();
            rs.close();
            return retailList;
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, " 数据操作异常");
            return null;
        }
    }



    public List<RetailItem> findByBuyerId_comfirm(int id) {
        List<RetailItem> retailList = new ArrayList<RetailItem>();
        Connection conn = getConnection();
        RetailItem item;

        try {
            Statement st = conn.createStatement();
            String sql = "select * from comfirmed where LASTBUYERID="+id;
            ResultSet rs = st.executeQuery(sql);

            if(rs!=null){
                while(rs.next()){
                    item=new RetailItem();
                    item.setID(rs.getInt("ID"));
                    item.setName(rs.getString("NAME"));
                    item.setPrice(rs.getFloat("PRICE"));
                    item.setPicture(rs.getString("PICTURE"));
                    item.setLastprice(rs.getFloat("LASTPRICE"));
                    item.setSellerID(rs.getInt("SELLERID"));
                    item.setLastbuyerID(rs.getInt("LASTBUYERID"));
                    retailList.add(item);
                }
            }
            conn.close();
            st.close();
            rs.close();
            return retailList;
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, " 数据操作异常");
            return null;
        }
    }


    public List<RetailItem> findBySellerId_comfirm(int id) {
        List<RetailItem> retailList = new ArrayList<RetailItem>();
        Connection conn = getConnection();
        RetailItem item;

        try {
            Statement st = conn.createStatement();
            String sql = "select * from comfirmed where SELLERID="+id;
            ResultSet rs = st.executeQuery(sql);

            if (rs != null) {
                while (rs.next()) {
                    item = new RetailItem();
                    item.setID(rs.getInt("ID"));
                    item.setName(rs.getString("NAME"));
                    item.setPrice(rs.getFloat("PRICE"));
                    item.setPicture(rs.getString("PICTURE"));
                    item.setLastprice(rs.getFloat("LASTPRICE"));
                    item.setSellerID(rs.getInt("SELLERID"));
                    item.setLastbuyerID(rs.getInt("LASTBUYERID"));
                    retailList.add(item);
                }
            }
            conn.close();
            st.close();
            rs.close();
            return retailList;
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, " 数据操作异常");
            return null;
        }
    }
}

