package com.tech.wd.lx_20190101.precenter;

import com.tech.wd.lx_20190101.callback.MyCallBack;
import com.tech.wd.lx_20190101.model.IModelImpl;
import com.tech.wd.lx_20190101.view.IView;

import java.util.Map;

public class IPrecenterImpl implements IPrecenter{

    IView mIView;
    private IModelImpl mIModel;

    public IPrecenterImpl(IView IView) {
        mIView = IView;
        mIModel = new IModelImpl();
    }

    @Override
    public void startRequestData(String url, Map<String, String> map, Class clazz, int z) {
        mIModel.requestData(url, map, clazz, z, new MyCallBack() {
            @Override
            public void setData(Object data) {
                mIView.showData(data);
            }
        });
    }

    public void onDetach(){  //销毁-避免内存泄漏
        if (mIModel!=null){
            mIModel=null;
        }

        if (mIView!=null){
            mIView=null;
        }
    }
}
