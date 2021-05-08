package com.example.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.data.DBManage;
import com.example.item.UserItem;

public class RegistActivity extends AppCompatActivity {

    private static final String TAG = "RegistActivity";

    private DBManage manager;
    EditText pn,pw1,pw2;
    int name;
    UserItem item,item1;
    Handler handler;
    UserItem userItem;
    String password1,password2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        pn=findViewById(R.id.editTextTextPersonName2);
        pw1=findViewById(R.id.editTextTextPassword2);
        pw2=findViewById(R.id.editTextTextPassword3);
    }


    public void regist1(View btn){

        try{

            manager=new DBManage();
            String str_name;
            str_name=pn.getText().toString();
            Log.i(TAG,"name:"+str_name);
            password1=pw1.getText().toString();
            Log.i(TAG,"password:"+password1);
            password2=pw2.getText().toString();

            name=Integer.parseInt(str_name);

            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    item=manager.findById(name);
                    Log.i(TAG,"item:"+item);

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
                        userItem = (UserItem) msg.obj;
                        Log.i(TAG, "sentOK" + userItem);

                        if(userItem.getCurPassword()!=""){
                            Log.i(TAG,"item:"+userItem);
                            Toast.makeText(RegistActivity.this,"账号已存在",Toast.LENGTH_SHORT).show();
                        }

                        else if(password1.equals(password2)){
                            item1=new UserItem();
                            item1.setCurName(name);
                            item1.setCurPassword(password1);

                            final Thread thread1 = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    manager.add(item1);
                                }
                            });
                            thread1.start();

                /*if(name==1){
                    data d;
                    d=new data();
                    d.data(this);
                }*/

                            Toast.makeText(RegistActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                            Intent regist=new Intent(RegistActivity.this,MainActivity.class);
                            startActivity(regist);
                        }
                        else{
                            Toast.makeText(RegistActivity.this,"两次密码不同，请重新输入",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            };


        } catch (NumberFormatException e) {
            Toast.makeText(this,"不规范输入值",Toast.LENGTH_SHORT).show();
        }

    }
}
