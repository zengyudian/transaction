package com.example.transaction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.data.DBManager;
import com.example.item.UserItem;

public class MainActivity extends AppCompatActivity {

    private DBManager manager;
    EditText pn,pw;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager=new DBManager(this);

        pn=findViewById(R.id.PersonName);
        pw=findViewById(R.id.Password);

    }

    public void login(View btn){

        String str_name,password;

        str_name=pn.getText().toString();
        password=pw.getText().toString();

        int name=Integer.parseInt(str_name);

        try{

            UserItem item=manager.findById(name);

            //String p=item.getCurPassword();
            /*HashMap<String, String> map = DBManage.getUserInfoByID(name);
            Log.i("TAG", "run: " +map);
            //map.put("password","1");
            String p=map.get("password");
            //String p="1";*/


            if(item==null){
                Toast.makeText(this,"不存在账号",Toast.LENGTH_SHORT).show();
            }
            else if(password.equals(item.getCurPassword())){
                Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
                Intent login=new Intent(MainActivity.this,HomeActivity.class);
                login.putExtra("userID",name);
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