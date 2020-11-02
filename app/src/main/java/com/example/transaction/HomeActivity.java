package com.example.transaction;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.adapter.MyHomeAdapter;
import com.example.data.DBManager;
import com.example.item.RetailItem;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class HomeActivity extends FragmentActivity {
    private Fragment mFragments[];
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rbtHome, rbtMessage, rbtMypage;
    DBManager manager;
    SearchView mSearchView;


    int userID;
    ListView listview;
    MyHomeAdapter myHomeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        userID = intent.getIntExtra("userID", 0);

        mFragments = new Fragment[3];
        fragmentManager = getSupportFragmentManager();
        mFragments[0] = fragmentManager.findFragmentById(R.id.fragment_home);
        mFragments[1] = fragmentManager.findFragmentById(R.id.fragment_message);
        mFragments[2] = fragmentManager.findFragmentById(R.id.fragment_mypage);

        fragmentTransaction = fragmentManager.beginTransaction()
                .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
        fragmentTransaction.show(mFragments[0]).commit();

        rbtHome = (RadioButton) findViewById(R.id.radioHome);
        rbtMessage = (RadioButton) findViewById(R.id.radioMessage);
        rbtMypage = (RadioButton) findViewById(R.id.radioMypage);
        radioGroup = (RadioGroup) findViewById(R.id.bottomGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i("radioGroup", "checkId=" + checkedId);
                fragmentTransaction = fragmentManager.beginTransaction()
                        .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
                switch (checkedId) {
                    case R.id.radioHome:
                        fragmentTransaction.show(mFragments[0]).commit();
                        break;
                    case R.id.radioMessage:
                        fragmentTransaction.show(mFragments[1]).commit();
                        break;
                    case R.id.radioMypage:
                        fragmentTransaction.show(mFragments[2]).commit();
                        break;
                    default:
                        break;
                }
            }
        });


        initView();

        listview = findViewById(R.id.homelist);
        final TextView tv = findViewById(R.id.nodata);
        //tv.setText("123"+listview);

        manager = new DBManager(this);
        List<RetailItem> list = manager.listAll_retail();
        Log.i("radioGroup", "所有商品：" + list);

        myHomeAdapter = new MyHomeAdapter(HomeActivity.this,
                R.layout.frame_home, (ArrayList<RetailItem>) list);
        listview.setAdapter(myHomeAdapter);
        listview.setEmptyView(tv);
        listview.setOnItemClickListener(new ClickEvent());

        //View view =getLayoutInflater().inflate(R.layout.frame_mypage, null);
        TextView username = findViewById(R.id.username);
        username.setText("用户" + userID);

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
                Toast.makeText(HomeActivity.this,"you choose:" + query,Toast.LENGTH_SHORT).show();
                Intent search=new Intent(HomeActivity.this,SearchActivity.class);
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

            Intent detail=new Intent(HomeActivity.this,DetailsActivity.class);
            detail.putExtra("userID",userID);
            detail.putExtra("ID",id1);
            detail.putExtra("name",name);
            detail.putExtra("Lastprice",price);
            detail.putExtra("picture",picture);
            startActivity(detail);
        }
    }

    public void myorder(View btn){
        Intent order=new Intent(HomeActivity.this,MyorderActivity.class);
        order.putExtra("userID",userID);
        startActivity(order);
    }

    public void mydetail(View btn){
        Intent mydetail=new Intent(HomeActivity.this,MydetailActivity.class);
        mydetail.putExtra("userID",userID);
        startActivity(mydetail);
    }


    public void mycollect(View btn){
        Intent mycollect=new Intent(HomeActivity.this,CollectActivity.class);
        mycollect.putExtra("userID",userID);
        startActivity(mycollect);
    }



    public void search(){
        //数据库实现查找功能
        SearchView searchView;
        searchView=findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            //输入完成后，提交时触发的方法，一般情况是点击输入法中的搜索按钮才会触发，表示现在正式提交了
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    Toast.makeText(HomeActivity.this, "请输入查找内容111！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this, query, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            //在输入时触发的方法，当字符真正显示到searchView中才触发，像是拼音，在输入法组词的时候不会触发
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    Toast.makeText(HomeActivity.this, "请输入查找内容222！", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(HomeActivity.this, newText, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }


    public void logout(View btn){
        Intent mycollect=new Intent(HomeActivity.this,MainActivity.class);
        Toast.makeText(this,"注销成功",Toast.LENGTH_SHORT).show();
        startActivity(mycollect);
    }


    private void initView() {
        //定义底部标签图片大小和位置
        Drawable drawable_news = getResources().getDrawable(R.drawable.home);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_news.setBounds(0, 0, 50, 50);
        //设置图片在文字的哪个方向
        rbtHome.setCompoundDrawables(null, drawable_news, null, null);

        //定义底部标签图片大小和位置
        Drawable drawable_live = getResources().getDrawable(R.drawable.message);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_live.setBounds(0, 0, 50, 50);
        //设置图片在文字的哪个方向
        rbtMessage.setCompoundDrawables(null, drawable_live, null, null);

        //定义底部标签图片大小和位置
        Drawable drawable_tuijian = getResources().getDrawable(R.drawable.mypage);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_tuijian.setBounds(0, 0, 50, 50);
        //设置图片在文字的哪个方向
        rbtMypage.setCompoundDrawables(null, drawable_tuijian, null, null);

    }



}