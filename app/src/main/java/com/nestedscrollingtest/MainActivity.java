package com.nestedscrollingtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tools.Tools;
import com.view.ExpandableTextView;
import com.widght.StickyNavLayout;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private FragmentPagerAdapter mAdapter;
    private String[] mTitle = new String[]{"测试一","测试二"};
    private ArrayList<Fragment> fragments;

//    private RecyclerRefreshLayout recyclerRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        StickyNavLayout stickyNavLayout = (StickyNavLayout) findViewById(R.id.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.a_main_viewpager);
        stickyNavLayout.mViewPager = viewPager;
        stickyNavLayout.mTopViewHeight = Tools.dp2px(this,200);
        stickyNavLayout.mTopView = findViewById(R.id.a_main_head);
        stickyNavLayout.mTitleView = findViewById(R.id.a_main_title);

        ExpandableTextView friend_info = (ExpandableTextView) findViewById(R.id.expandableTextview);
        friend_info.setText("顶部的关闭按钮和发文章的关闭按钮大小左边距统一。\n" +
                "2、内容与推按距离缩小至40px\n" +
                "3、删除图片的关闭按钮用文章里面的关闭按钮。\n" +
                "4、上传图片的图标也用编辑文章的那个图标");

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                findViewById(R.id.a_main_title).setVisibility(View.GONE);
                return fragments.get(position);
            }
            @Override
            public int getCount() {
                return mTitle.length;
            }
        };
        fragments = new ArrayList<>();
        fragments.add(new MainPager());
        fragments.add(new MainPager());
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);

//        recyclerRefreshLayout = (RecyclerRefreshLayout) findViewById(R.id.refresh_layout);
//        recyclerRefreshLayout.setOnRefreshListener(new RecyclerRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        recyclerRefreshLayout.setRefreshing(false);
//                    }
//                },1000);
//            }
//        });
    }
}
