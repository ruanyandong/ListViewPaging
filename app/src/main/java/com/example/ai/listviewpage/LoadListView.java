package com.example.ai.listviewpage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by AI on 2017/11/14.
 */

public class LoadListView extends ListView implements AbsListView.OnScrollListener{

    View footer;//底部布局
    int totalItmCount;//总数量
    int lastVisibleItem;//最后一个可见的Item
    boolean isLoading;//正在加载
    LoadListenner loadListenner;

    public LoadListView(Context context) {
        super(context);
        initView(context);
    }

    public LoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 添加底部加载提示布局到listview
     * @param context
     */
    private void initView(Context context){
        LayoutInflater inflater=LayoutInflater.from(context);
        footer=inflater.inflate(R.layout.footer_layout,null);
        //刚进入listview时底部加载是隐藏的
        footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
        this.addFooterView(footer);
        this.setOnScrollListener(this);
    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if(totalItmCount==lastVisibleItem
                &&scrollState==SCROLL_STATE_IDLE){//滚动停止了
            //加载更多数据
            if(!isLoading){
                isLoading=true;
                footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
                loadListenner.onLoad();
            }

        }

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int VisibleItemCount, int TotalItemCount) {

        this.lastVisibleItem=firstVisibleItem+VisibleItemCount;
        this.totalItmCount=TotalItemCount;
    }

    //加载更多数据的回调接口
    public interface LoadListenner{
        public void onLoad();
    }

    public void setInterface(LoadListenner loadListenner){
        this.loadListenner=loadListenner;
    }
    //加载完毕
    public void loadComplete(){
        isLoading=true;//这里设置为false为无限循环，设置为true则只加载完添加的数据就停止
        footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
    }
}
