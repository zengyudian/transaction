package com.example.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.item.RetailItem;
import com.example.method.StringAndBitmap;
import com.example.transaction.R;

import java.util.ArrayList;

public class MyHomeAdapter extends ArrayAdapter {
    private static final String TAG = "MyAdapter";

    public MyHomeAdapter(Context context,
                         int resource,
                         ArrayList<RetailItem> list) {
        super(context, resource, list);

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.home_item,
                    parent,
                    false);
        }

        RetailItem item=(RetailItem)getItem(position);

        TextView name = (TextView) itemView.findViewById(R.id.tv_name);
        TextView price = (TextView) itemView.findViewById(R.id.tv_price);
        ImageView imageView=itemView.findViewById(R.id.imageView4);

        Bitmap bit;

        int ID=item.getID();
        Log.i("radioGroup", "id：" + ID);
        String n=item.getName();
        Float p=item.getPrice();
        String picture=item.getPicture();
        Float p1=item.getLastprice();
        Log.i("radioGroup", "lastprice：" +p1);


        StringAndBitmap t=new StringAndBitmap();
        bit=t.stringToBitmap(picture);
//
//        ViewGroup.LayoutParams para;
//        para = imageView.getLayoutParams();
//        Log.d(TAG, "layout height0: " + para.height);
//        Log.d(TAG, "layout width0: " + para.width);
        int newHeight = 300;
        int newWidth = 300;
        int width = bit.getWidth();
        int height = bit.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbit = Bitmap.createBitmap(bit, 0, 0, width, height, matrix, true);

        name.setText("name:"+n);
        price.setText("price:"+p1);
        imageView.setImageBitmap(newbit);
        return itemView;
    }



}