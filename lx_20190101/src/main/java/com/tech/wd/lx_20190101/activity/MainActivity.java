package com.tech.wd.lx_20190101.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.tech.wd.lx_20190101.Apis;
import com.tech.wd.lx_20190101.R;
import com.tech.wd.lx_20190101.adapter.ListShowsAdapter;
import com.tech.wd.lx_20190101.bean.ComputerBean;
import com.tech.wd.lx_20190101.bean.EventBusBean;
import com.tech.wd.lx_20190101.precenter.IPrecenterImpl;
import com.tech.wd.lx_20190101.view.IView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IView{

    @BindView(R.id.main_img)
    ImageView mImageView;

    @BindView(R.id.main_rv)
    RecyclerView mRecyclerView;

    private IPrecenterImpl mIPrecenter;

    private Boolean B=true;
    private Boolean changeGrid=true;
    List<ComputerBean.DataBean> data1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mIPrecenter = new IPrecenterImpl(this);
        initData();
    }

    @OnClick(R.id.main_img)
    public void onImgClickListener(){

        if (B){
            B=false;
            if (changeGrid){
                mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.HORIZONTAL));
                changeGrid=false;
            }
            mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
        }else {
            B=true;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
        }

        ListShowsAdapter showsAdapter = new ListShowsAdapter(MainActivity.this, data1,B);
        mRecyclerView.setAdapter(showsAdapter);
        showsAdapter.setOnItemClickListener(new ListShowsAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int layoutPosition, String title, String price) {

                EventBusBean busBean = new EventBusBean(layoutPosition, title, price);

                EventBus.getDefault().postSticky(busBean);
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });
    }

    private void initData() {

        Map<String, String> map = new HashMap<>();
        map.put("keywords","笔记本");
        map.put("page",1+"");
        map.put("sort",0+"");
        mIPrecenter.startRequestData(Apis.URL_COMPUTER_SHOW_LIST,map, ComputerBean.class,1);
    }

    @Override
    public void showData(Object data) {

        ComputerBean computerBean= (ComputerBean) data;
        data1 = computerBean.getData();

        ListShowsAdapter showsAdapter = new ListShowsAdapter(MainActivity.this, data1,B);

        mRecyclerView.setAdapter(showsAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));

        showsAdapter.setOnItemClickListener(new ListShowsAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int layoutPosition, String title, String price) {

                EventBusBean busBean = new EventBusBean(layoutPosition, title, price);

                EventBus.getDefault().postSticky(busBean);

                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });
    }
}
