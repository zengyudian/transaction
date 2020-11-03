package com.example.transaction;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.data.DBManage;
import com.example.data.DBManager;
import com.example.item.UserItem;

import java.time.LocalDate;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private DBManager manager;
    DBManage m;
    EditText pn,pw;
    int name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager=new DBManager(this);

        pn=findViewById(R.id.PersonName);
        pw=findViewById(R.id.Password);

        //读取上次本地账号数据
        SharedPreferences sp = getSharedPreferences("userdata", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        Log.i(TAG,"读取数据");
        name=sp.getInt("localID",0);
        UserItem item1=manager.findById(name);

        if(item1!=null){
            Intent login=new Intent(MainActivity.this,HomeActivity.class);
            login.putExtra("userID",name);
            login.putExtra("page",1);
            startActivity(login);
        }

    }

    public void login(View btn){

        String str_name,password;

        str_name=pn.getText().toString();
        password=pw.getText().toString();

        name=Integer.parseInt(str_name);

        try{

            UserItem item=manager.findById(name);

            if(item==null){
                Toast.makeText(this,"不存在账号",Toast.LENGTH_SHORT).show();
            }
            else if(password.equals(item.getCurPassword())){
                Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();


                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        m.getConnection();
                    }

                });
                thread.start();

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
                Toast.makeText(this,"密码错误",Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this,"不规范输入值",Toast.LENGTH_SHORT).show();
        }

    }

    public void regist(View btn){
        Intent regist=new Intent(MainActivity.this,RegistActivity.class);
        startActivity(regist);
    }

}