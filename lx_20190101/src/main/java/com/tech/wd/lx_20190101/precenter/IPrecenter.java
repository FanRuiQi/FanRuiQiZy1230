package com.tech.wd.lx_20190101.precenter;

import com.tech.wd.lx_20190101.callback.MyCallBack;

import java.util.Map;

public interface IPrecenter {
    void startRequestData(String url, Map<String, String> map, Class clazz, int z);
}
