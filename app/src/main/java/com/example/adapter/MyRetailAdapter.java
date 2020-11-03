package com.example.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.data.DBManage;
import com.example.data.DBManager;
import com.example.item.RetailItem;
import com.example.method.StringAndBitmap;
import com.example.transaction.R;

import java.util.ArrayList;
import java.util.List;

public class MyRetailAdapter extends ArrayAdapter {
    private static final String TAG = "MyAdapter";
    Button button;
    DBManage manager;

    public MyRetailAdapter(Context context,
                           int resource,
                           ArrayList<RetailItem> list) {
        super(context, resource, list);

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.myretail_item,
                    parent,
                    false);
        }

        final RetailItem item=(RetailItem)getItem(position);

        TextView name = (TextView) itemView.findViewById(R.id.retail_name);
        TextView price = (TextView) itemView.findViewById(R.id.retail_price);
        ImageView imageView=itemView.findViewById(R.id.imageView4);

        final int ID=item.getID();
        final String n=item.getName();
        Float p=item.getLastprice();
        String picture=item.getPicture();

        button=itemView.findViewById(R.id.retail_button);
        button.setOnClickListener(new ImageView.OnClickListener(){
            public void onClick(View v) {

                Log.i(TAG, "点击按钮"+n);
                //数据存入完成订单数据库，并从未完成数据库删除
                manager=new DBManage();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        manager.add_comfirm(item);
                        manager.delete_retail(ID);

                    }
                });
                thread.start();

                Toast.makeText(getContext(),"订单已完成",Toast.LENGTH_SHORT).show();
            }
        });

        Bitmap bit;
        StringAndBitmap t=new StringAndBitmap();
        bit=t.stringToBitmap(picture);

        int newHeight = 350;
        int newWidth = 350;
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
        name.setText("name:"+n);
        price.setText("lastprice:"+p);
        return itemView;
    }


}