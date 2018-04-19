package com.example.ai.listviewpage;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 *listView实现分页
 */

public class MainActivity extends AppCompatActivity implements LoadListView.LoadListenner{
    private List<String> data=new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private LoadListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        adapter=new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,data);

        listView=(LoadListView) findViewById(R.id.list_view);
        listView.setInterface(this);

        listView.setAdapter(adapter);


    }

    private void initData(){
        for(int i=0;i<20;i++){
            data.add(""+i);
        }
    }
    private void getMoredata(){
        for(int i=20;i<30;i++){
            data.add(""+i);
        }
    }

    @Override
    public void onLoad() {

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //显示更多数据
                getMoredata();
                //更新listView显示
                adapter.notifyDataSetChanged();
                //通知listView加载完毕
                listView.loadComplete();
            }
        },2000);

    }
}
