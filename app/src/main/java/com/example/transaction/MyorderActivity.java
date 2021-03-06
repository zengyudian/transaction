package com.example.transaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import androidx.viewpager.widget.ViewPager;

import com.example.fragment.SoldFragment;
import com.example.fragment.UnconfirmedFragment;


public class MyorderActivity extends FragmentActivity implements View.OnClickListener{

    private TextView page1, page2;
    private ViewPager vp;
    int userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);


        Intent intent=getIntent();
        userID=intent.getIntExtra("userID",0);
        
        initViews();
        MyPageAdapter pageAdapter = new MyPageAdapter(this.getSupportFragmentManager());
        vp.setOffscreenPageLimit(2);//ViewPager的缓存为2帧
        vp.setAdapter(pageAdapter);
        vp.setCurrentItem(0);//初始设置ViewPager选中第一帧
        page1.setBackgroundColor(Color.parseColor("#FFFFFF"));

        //ViewPager的监听事件
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*此方法在页面被选中时调用*/
                changeTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /*此方法是在状态改变的时候调用，其中arg0这个参数有三种状态（0，1，2）。
                arg0==1的时辰默示正在滑动，
                arg0==2的时辰默示滑动完毕了，
                arg0==0的时辰默示什么都没做。*/
            }
        });

    }

    /**
     * 初始化布局View
     */
    private void initViews() {
        page1 = (TextView) findViewById(R.id.page1);
        page2 = (TextView) findViewById(R.id.page2);

        page1.setOnClickListener(this);
        page2.setOnClickListener(this);

        vp = (ViewPager) findViewById(R.id.viewpager);
    }

    /**
     * 点击头部Text 动态修改ViewPager的内容
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page1:
                vp.setCurrentItem(0, true);

                break;
            case R.id.page2:
                vp.setCurrentItem(1, true);

                break;
        }
    }

    public class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return new SoldFragment();
            }else {
                return new UnconfirmedFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    /**
     * 由ViewPager的滑动修改头部导航Text的颜色
     * @param position
     */
    private void changeTextColor(int position) {
        if (position == 0) {
            page2.setBackgroundColor(Color.parseColor("#C6CCCF"));
            page1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else if (position == 1) {
            page1.setBackgroundColor(Color.parseColor("#C6CCCF"));
            page2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    public void back_home(View btn){
        Intent back=new Intent(MyorderActivity.this,HomeActivity.class);
        back.putExtra("userID",userID);
        back.putExtra("page",3);
        startActivity(back);
    }
}
