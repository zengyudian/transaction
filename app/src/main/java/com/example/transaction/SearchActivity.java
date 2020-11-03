package com.example.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.adapter.MyHomeAdapter;
import com.example.data.DBManage;
import com.example.data.DBManager;
import com.example.item.RetailItem;
import com.example.item.UserItem;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class SearchActivity extends FragmentActivity {

    SearchView mSearchView;

    DBManage manager;

    String name;
    int userID;
    ListView listview;
    MyHomeAdapter myHomeAdapter;
    List<RetailItem> list;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        Intent intent = getIntent();
        userID = intent.getIntExtra("userID", 0);
        name=intent.getStringExtra("text");
        Log.i(TAG, "search:"+name);

        listview = findViewById(R.id.homelist);
        final TextView tv = findViewById(R.id.nodata);


        manager = new DBManage();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                list=new ArrayList<RetailItem>();
                list = manager.findByName_retail(name);
                Log.i(TAG, "search"+list);

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
                    list= (List<RetailItem>) msg.obj;

                    myHomeAdapter = new MyHomeAdapter(SearchActivity.this,
                            R.layout.frame_home, (ArrayList<RetailItem>) list);
                    listview.setAdapter(myHomeAdapter);
                    listview.setEmptyView(tv);
                    listview.setOnItemClickListener(new SearchActivity.ClickEvent());
                }
            }
        };

        mSearchView = (SearchView) findViewById(R.id.searchView);
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setFocusable(true);
        mSearchView.setFocusableInTouchMode(true);
        mSearchView.onActionViewExpanded();
        mSearchView.setIconifiedByDefault(true);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //点击搜索按钮时触发
            @Override
            public boolean onQueryTextSubmit(String query) {
                //此处添加查询开始后的具体时间和方法
                Toast.makeText(SearchActivity.this,"you choose:" + query,Toast.LENGTH_SHORT).show();
                Intent search=new Intent(SearchActivity.this,SearchActivity.class);
                search.putExtra("userID",userID);
                search.putExtra("text",query);
                startActivity(search);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //如果newText长度不为0
                if (TextUtils.isEmpty(newText)){
                    listview.clearTextFilter();
                }else{
                    listview.setFilterText(newText);
//          adapter.getFilter().filter(newText.toString());//替换成本句后消失黑框！！！
                }
                return true;
            }
        });
    }


    public class ClickEvent implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {

            Log.i(TAG, "点击");
            Object itemAtPosition = listview.getItemAtPosition(position);
            RetailItem item = (RetailItem) itemAtPosition;
            int id1 = item.getID();
            String name=item.getName();
            Float price=item.getLastprice();
            String picture=item.getPicture();
            Log.i(TAG, "onItemClick: titleStr=" + id1);

            Intent detail=new Intent(SearchActivity.this,DetailsActivity.class);
            detail.putExtra("userID",userID);
            detail.putExtra("ID",id1);
            detail.putExtra("name",name);
            detail.putExtra("Lastprice",price);
            detail.putExtra("picture",picture);
            startActivity(detail);
        }
    }

    public void back_home(View btn){
        Intent back=new Intent(SearchActivity.this,HomeActivity.class);
        back.putExtra("userID",userID);
        back.putExtra("page",1);
        startActivity(back);
    }
}