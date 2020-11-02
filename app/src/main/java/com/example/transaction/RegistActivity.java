package com.example.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.data.DBManager;
import com.example.data.data;
import com.example.item.UserItem;

public class RegistActivity extends AppCompatActivity {

    private static final String TAG = "RegistActivity";

    private DBManager manager;
    EditText pn,pw1,pw2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        pn=findViewById(R.id.editTextTextPersonName2);
        pw1=findViewById(R.id.editTextTextPassword2);
        pw2=findViewById(R.id.editTextTextPassword3);
    }


    public void regist1(View btn){

        try{

            manager=new DBManager(this);
            String str_name,password1,password2;
            int name;
            str_name=pn.getText().toString();
            Log.i(TAG,"name:"+str_name);
            password1=pw1.getText().toString();
            Log.i(TAG,"password:"+password1);
            password2=pw2.getText().toString();

            name=Integer.parseInt(str_name);
            UserItem item=manager.findById(name);

            if(item!=null){
                Log.i(TAG,"name123");
                Toast.makeText(this,"账号已存在",Toast.LENGTH_SHORT).show();
            }
            else if(password1.equals(password2)){
                UserItem item1=new UserItem();
                item1.setCurName(name);
                item1.setCurPassword(password1);
                manager.add(item1);

                if(name==1){
                    data d;
                    d=new data();
                    d.data(this);
                }

                Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
                Intent regist=new Intent(RegistActivity.this,MainActivity.class);
                startActivity(regist);
            }
            else{
                Toast.makeText(this,"两次密码不同，请重新输入",Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this,"不规范输入值",Toast.LENGTH_SHORT).show();
        }

    }
}
