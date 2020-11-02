package com.example.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AuctionActivity extends AppCompatActivity {

    TextView tv_price;
    EditText tv_bid;
    Float price;
    int userID,ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction);

        Intent intent=getIntent();
        price=intent.getFloatExtra("price",0.0f);
        ID=intent.getIntExtra("ID",0);
        userID=intent.getIntExtra("userID",0);

        tv_price=findViewById(R.id.textView6);
        tv_bid=findViewById(R.id.editTextTextPersonName);

        tv_price.setText(""+price);

    }

    public void bid(View btn){

        Intent intent=getIntent();
        String str_bid=tv_bid.getText().toString();
        Float bid_price=Float.parseFloat(str_bid);

        if(bid_price>=price){
            Toast.makeText(this,"竞价成功",Toast.LENGTH_SHORT).show();
            Bundle bd=new Bundle();
            bd.putFloat("bid_price",bid_price);
            bd.putInt("userID",userID);
            intent.putExtras(bd);
            setResult(2,intent);
            finish();
        }else{
            Toast.makeText(this,"竞拍价格过低",Toast.LENGTH_SHORT).show();
        }
    }
}
