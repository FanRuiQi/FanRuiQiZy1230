package com.tech.wd.lx_20190101.model;

import com.google.gson.Gson;
import com.tech.wd.lx_20190101.callback.MyCallBack;
import com.tech.wd.lx_20190101.netutil.RetrofitManager;

import java.util.Map;

public class IModelImpl implements IModel{

    @Override
    public void requestData(String url, Map<String, String> map, final Class clazz, int z, final MyCallBack myCallBack) {

        RetrofitManager.getInstance().post(url,map).result(new RetrofitManager.HttpListener() {
            @Override
            public void onFail() {

            }

            @Override
            public void onResponse(String json) {

                Object o = new Gson().fromJson(json, clazz);
                myCallBack.setData(o);
            }
        });
    }
}
