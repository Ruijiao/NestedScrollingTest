package com.nestedscrollingtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Adapter.AdapterRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Fang Ruijiao on 2016/9/29.
 */

public class MainPager extends Fragment{

    private RecyclerView recyclerView;
    private AdapterRecyclerView adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_main_page,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.a_main_page_list);
        List<Map<String,String>> list = new ArrayList<>();
        Map<String,String> map;
        for(int i = 0; i < 100; i++){
            map = new HashMap<>();
            map.put("name","haha:" + i);
            list.add(map);
        }
        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterRecyclerView(getActivity(),list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

    private void onPagerChange(){

    }
}
