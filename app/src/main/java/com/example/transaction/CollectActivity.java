package com.example.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adapter.MyHomeAdapter;
import com.example.data.DBManage;
import com.example.data.DBManager;
import com.example.item.RetailItem;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CollectActivity extends AppCompatActivity {

    ListView listview;
    MyHomeAdapter myHomeAdapter;
    DBManage manager;
    int userID;
    List<RetailItem> list,list1;
    Handler handler;
    TextView tv;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);


        listview=findViewById(R.id.collectlist);
        tv = findViewById(R.id.tv_collect);

        userID=getIntent().getIntExtra("userID",0);
        //tv.setText("123"+listview);


        /*List list =new ArrayList<HashMap<String,String>>();
        for(int i = 1;i <=10;i++)
        {
            HashMap<String,String> map=new HashMap<String,String>();
            map.put("ID",""+i);//标题文字
            list.add(map);
        }*/
        manager=new DBManage();

        list=new ArrayList<RetailItem>();
        list1=new ArrayList<RetailItem>();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                list = manager.listAll_retail();

                Message msg = new Message();
                msg.what = 5;
                msg.obj = list;
                handler.sendMessage(msg);
            }
        });
        thread.start();

        handler=new Handler(){
            public void handleMessage(Message msg) {
                if (msg.what == 5) {

                    list1 = (List<RetailItem>) msg.obj;
                    myHomeAdapter = new MyHomeAdapter(CollectActivity.this,
                            R.layout.activity_collect, (ArrayList<RetailItem>) list1);
                    listview.setAdapter(myHomeAdapter);
                    listview.setEmptyView(tv);
                    listview.setOnItemClickListener(new ClickEvent());
                }
            }
        };


}

public class ClickEvent implements AdapterView.OnItemClickListener {
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {

        Log.i(TAG, "点击");
        Object itemAtPosition = listview.getItemAtPosition(position);
        RetailItem item = (RetailItem) itemAtPosition;
        int id1 = item.getID();
        Log.i(TAG, "onItemClick: titleStr=" + id1);
        String name=item.getName();
        Float price=item.getLastprice();
        String picture=item.getPicture();

        Intent detail=new Intent(CollectActivity.this,DetailsActivity.class);
        detail.putExtra("ID",id1);
        detail.putExtra("userID",userID);
        detail.putExtra("name",name);
        detail.putExtra("Lastprice",price);
        detail.putExtra("picture",picture);
        startActivity(detail);
    }
}
}
