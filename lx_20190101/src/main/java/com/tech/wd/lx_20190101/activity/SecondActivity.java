package com.tech.wd.lx_20190101.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tech.wd.lx_20190101.R;
import com.tech.wd.lx_20190101.bean.EventBusBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondActivity extends AppCompatActivity{
    private String mTitle;
    private String mPrice;

    @BindView(R.id.second_title)
    TextView mTextView_title;

    @BindView(R.id.second_price)
    TextView mTextView_price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        mTextView_title.setText(mTitle);
        mTextView_price.setText(mPrice);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void sjx(EventBusBean bus){
         mTitle = bus.getTitle();
         mPrice = bus.getPrice();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
