package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.data.DBManage;
import com.example.item.RetailItem;
import com.example.item.UserItem;
import com.example.transaction.DetailsActivity;
import com.example.transaction.R;


public class MessageFragment extends Fragment {


    TextView tel;
    int userID;
    int ID;
    Handler handler;
    DBManage manager;
    RetailItem item;
    int sellerID;
    Button bt_retail;
    String name,picture;
    Float price;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =getLayoutInflater().inflate(R.layout.frame_message, null);
        tel = view.findViewById(R.id.tel_tv);
        bt_retail=view.findViewById(R.id.retail_message);

        Intent intent = getActivity().getIntent();
        userID = intent.getIntExtra("userID", 0);
        ID = intent.getIntExtra("ID", 0);
        name=intent.getStringExtra("name");
        picture=intent.getStringExtra("picture");
        price=intent.getFloatExtra("Lastprice",0.1f);

        manager=new DBManage();
        item=new RetailItem();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                item=manager.findById_retail(ID);
                sellerID=item.getSellerID();

                Message msg = new Message();
                msg.what = 5;
                msg.obj = sellerID;
                handler.sendMessage(msg);
            }
        });
        thread.start();

        handler=new Handler(){
            public void handleMessage(Message msg) {
                if (msg.what == 5) {
                    sellerID= (int) msg.obj;

                    tel.setText("电话号码："+sellerID);

                    bt_retail.setOnClickListener(new ImageView.OnClickListener(){
                        public void onClick(View v) {

                            Intent detail=new Intent(getActivity(), DetailsActivity.class);
                            detail.putExtra("userID",userID);
                            detail.putExtra("ID",ID);
                            detail.putExtra("name",name);
                            detail.putExtra("Lastprice",price);
                            detail.putExtra("picture",picture);
                            startActivity(detail);
                                }
                            });

                }
            }
        };


        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //TextView tv = (TextView)getView().findViewById(R.id.homeTextView1);
        //tv.setText(" 这是功能页");
    }
}
