package com.example.transaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.data.DBManage;
import com.example.data.DBManager;
import com.example.item.RetailItem;
import com.example.method.StringAndBitmap;

import java.util.List;


public class MysoldActivity extends AppCompatActivity {

    int ID,userID;
    String name,picture;
    Float price;
    TextView tv_name,tv_price,tv_buyerID;
    ImageView imageView;
    DBManage manager;
    RetailItem item1;
    int buyerID;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comfirmdetails);

        manager=new DBManage();
        tv_name=findViewById(R.id.detail_name1);
        tv_price=findViewById(R.id.detail_price1);
        tv_buyerID=findViewById(R.id.detail_buyerID);
        imageView=findViewById(R.id.imageView1);

        Intent intent=getIntent();
        ID=intent.getIntExtra("ID",0);
        userID=intent.getIntExtra("userID",0);
        name=intent.getStringExtra("name");
        picture=intent.getStringExtra("picture");
        price=intent.getFloatExtra("Lastprice",0.1f);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                item1=manager.findById_comfirm(ID);

                Message msg = new Message();
                msg.what = 5;
                msg.obj = item1;
                handler.sendMessage(msg);
            }
        });
        thread.start();

        handler=new Handler(){
            public void handleMessage(Message msg) {
                if (msg.what == 5) {

                    item1=(RetailItem)msg.obj;
                    buyerID=item1.getLastbuyerID();
                }
            }
        };


        tv_name.setText(""+name);
        tv_price.setText("¥"+price);
        tv_buyerID.setText("买家账号："+buyerID);

        //设置图片
        StringAndBitmap t=new StringAndBitmap();
        Bitmap bit=t.stringToBitmap(picture);
        int newHeight = 600;
        int newWidth = 600;
        int width = bit.getWidth();
        Log.i("width","width"+width);
        int height = bit.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        Log.i("width","width"+scaleWidth);
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbit = Bitmap.createBitmap(bit, 0, 0, width, height, matrix, true);

        imageView.setImageBitmap(newbit);
    }

    public void back_home(View btn){
        Intent back=new Intent(MysoldActivity.this,HomeActivity.class);
        back.putExtra("userID",userID);
        back.putExtra("page",3);
        startActivity(back);
    }
}
