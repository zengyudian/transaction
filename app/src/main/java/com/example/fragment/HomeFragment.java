package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.transaction.R;


public class HomeFragment extends Fragment {

    ListView listview;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frame_home, container);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //TextView tv = (TextView)getView().findViewById(R.id.homeTextView1);
        //tv.setText(" 这是功能页");

        listview=getView().findViewById(R.id.homelist);
        TextView tv = (TextView)getView().findViewById(R.id.nodata);
        //tv.setText("123"+listview);

    }

}
