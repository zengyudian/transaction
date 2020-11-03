package com.example.transaction;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.data.DBManage;
import com.example.data.DBManager;
import com.example.item.RetailItem;
import com.example.item.UserItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private DBManage manager;
    EditText pn,pw;
    int name;
    UserItem item,item1;
    Handler handler;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager=new DBManage();

        pn=findViewById(R.id.PersonName);
        pw=findViewById(R.id.Password);

        //读取上次本地账号数据
        SharedPreferences sp = getSharedPreferences("userdata", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        Log.i(TAG,"读取数据");
        name=sp.getInt("localID",0);

        if(name!=0){
            Intent login=new Intent(MainActivity.this,HomeActivity.class);
            login.putExtra("userID",name);
            login.putExtra("page",1);
            startActivity(login);
        }

    }

    public void login(View btn){

        String str_name;

        str_name=pn.getText().toString();
        password=pw.getText().toString();

        name=Integer.parseInt(str_name);

        try{

            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    item=manager.findById(name);

                    Log.i(TAG,"读取数据"+item);
                    Message msg = new Message();
                    msg.what = 5;
                    msg.obj = item;
                    handler.sendMessage(msg);
                }
            });
            thread.start();

            handler=new Handler(){
                public void handleMessage(Message msg) {
                    if (msg.what == 5) {
                        item1= (UserItem) msg.obj;
                        Log.i(TAG,"读取数据"+item1);

                        if(item1!=null){
                            if(password.equals(item1.getCurPassword())){
                                Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();

                                //保存本地用户账号数据
                                SharedPreferences sp = getSharedPreferences("userdata", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt("localID",name);
                                editor.apply();

                                Intent login=new Intent(MainActivity.this,HomeActivity.class);
                                login.putExtra("userID",name);
                                login.putExtra("page",1);
                                startActivity(login);
                            }
                            else{
                                Toast.makeText(MainActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this,"不存在账号",Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            };



        } catch (NumberFormatException e) {
            Toast.makeText(this,"不规范输入值",Toast.LENGTH_SHORT).show();
        }

    }

    public void regist(View btn){
        Intent regist=new Intent(MainActivity.this,RegistActivity.class);
        startActivity(regist);
    }

}