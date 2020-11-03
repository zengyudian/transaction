package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.data.DBManager;
import com.example.adapter.MyRetailAdapter;
import com.example.transaction.MyuncomfirmActivity;
import com.example.transaction.R;
import com.example.item.RetailItem;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class myUnconfirmedFragment extends Fragment {


    ListView listview3;
    DBManager manager;
    int userID;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = getLayoutInflater().inflate(R.layout.mydetail, null);
        listview3 = view.findViewById(R.id.mydetaillist);
        TextView tv1 = view.findViewById(R.id.nodata2);

        userID=getActivity().getIntent().getIntExtra("userID",0);

        manager=new DBManager(getActivity());
        List<RetailItem> list =manager.findBySellerId_retail(userID);

        MyRetailAdapter myRetailAdapter;
        myRetailAdapter = new MyRetailAdapter(getActivity(),
                R.layout.mydetail, (ArrayList<RetailItem>) list);
        listview3.setAdapter(myRetailAdapter);
        listview3.setEmptyView(tv1);
        listview3.setOnItemClickListener(new ClickEvent());

        //return inflater.inflate(R.layout.myorder_sold, container, false);

        return view;
    }


    public class ClickEvent implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {

            Log.i(TAG, "点击");
            Object itemAtPosition = listview3.getItemAtPosition(position);
            RetailItem item = (RetailItem) itemAtPosition;
            int id1 = item.getID();
            Log.i(TAG, "onItemClick: titleStr=" + id1);
            String name=item.getName();
            Float price=item.getLastprice();
            String picture=item.getPicture();

            Intent detail = new Intent(getActivity(), MyuncomfirmActivity.class);
            detail.putExtra("ID", id1);
            detail.putExtra("userID",userID);
            detail.putExtra("name",name);
            detail.putExtra("Lastprice",price);
            detail.putExtra("picture",picture);
            startActivity(detail);
        }

    }


    }
