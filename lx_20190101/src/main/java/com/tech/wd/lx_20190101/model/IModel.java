package com.tech.wd.lx_20190101.model;

import com.tech.wd.lx_20190101.callback.MyCallBack;

import java.util.Map;

public interface IModel {
    void requestData(String url, Map<String,String> map, Class clazz, int z, MyCallBack myCallBack);
}
