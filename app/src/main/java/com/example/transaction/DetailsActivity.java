package com.example.transaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.data.DBManage;
import com.example.data.DBManager;
import com.example.item.RetailItem;
import com.example.method.StringAndBitmap;

import java.time.LocalDate;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DetailsActivity extends AppCompatActivity {

    int ID,userID,delay;
    String name,picture;
    Float price;
    TextView tv_name,tv_price,tv_date;
    ImageView imageView,imageView2;
    Float bid;
    DBManage manager;
    RetailItem retailItem;
    RetailItem item1;
    Handler handler;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        manager=new DBManage();
        tv_name=findViewById(R.id.detail_name1);
        tv_price=findViewById(R.id.detail_price1);
        tv_date=findViewById(R.id.detail_buyerID);
        imageView=findViewById(R.id.imageView1);

        Intent intent=getIntent();
        ID=intent.getIntExtra("ID",0);
        userID=intent.getIntExtra("userID",0);
        name=intent.getStringExtra("name");
        picture=intent.getStringExtra("picture");
        price=intent.getFloatExtra("Lastprice",0.1f);

        LocalDate nowDate = LocalDate.now();;
        tv_name.setText(""+name);
        tv_price.setText("¥"+price);
        tv_date.setText(""+nowDate);

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

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==1&&resultCode==2){
            Bundle bundle=data.getExtras();

            bid=bundle.getFloat("bid_price",0.1f);

            //预防同一时间另一用户提交更高竞拍价格
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    retailItem=manager.findById_retail(ID);
                    Message msg = new Message();
                    msg.what = 5;
                    msg.obj =retailItem;
                    handler.sendMessage(msg);
                }
            });
            thread.start();

            handler=new Handler(){
                public void handleMessage(Message msg) {
                    if (msg.what == 5) {

                        retailItem = (RetailItem) msg.obj;
                        if (bid>=retailItem.getLastprice()){
                            //将数据库中最后价格和最后竞拍用户ID更新
                            retailItem.setLastbuyerID(userID);
                            retailItem.setLastprice(bid);

                            Thread thread1 = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    manager.update_retail(retailItem);
                                }
                            });

                            TimerTask  task= new TimerTask() {
                                @Override
                                public void run() {

                                    Thread thread2 = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            item1=manager.findById_retail(ID);
                                            manager.add_comfirm(item1);
                                            manager.delete_retail(ID);
                                        }
                                    });
                                    thread2.start();
                                    Log.i("MainActivity","最终竞拍价格"+bid);
                                    Log.i("MainActivity","最终竞拍用户"+userID);

                                }
                            };

                            delay=30000;
                            Timer timer = new Timer();
                            timer.schedule(task, delay);

                            thread1.start();
                            tv_price.setText("price:"+bid);
                        }

                    }
                }
            };

        }

        super.onActivityResult(requestCode,resultCode,data);
    }


    public void auction(View btn){

        Intent auction=new Intent(DetailsActivity.this,AuctionActivity.class);
        //float price=100;
        auction.putExtra("price",price);
        auction.putExtra("userID",userID);
        auction.putExtra("ID",ID);
        //startActivity(auction);
        startActivityForResult(auction,1);

    }

    public void message(View btn){

        Intent message=new Intent(DetailsActivity.this,HomeActivity.class);
        message.putExtra("userID",userID);
        message.putExtra("page",2);
        message.putExtra("ID",ID);
        message.putExtra("name",name);
        message.putExtra("Lastprice",price);
        message.putExtra("picture",picture);
        startActivity(message);
        Toast.makeText(this,"联系客服",Toast.LENGTH_SHORT).show();
    }

    public void collect(View btn){

        //获取物品和用户id加入收藏数据库
        Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
    }

    public void back_home(View btn){
        Intent back=new Intent(DetailsActivity.this,HomeActivity.class);
        back.putExtra("userID",userID);
        back.putExtra("page",1);
        startActivity(back);
    }
}
