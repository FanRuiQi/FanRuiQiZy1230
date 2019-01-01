package com.tech.wd.lx_20190101.netutil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager {

    private static RetrofitManager mRetrofitManager;
    private final String BASE_URL="http://www.zhaoapi.cn";
    private final OkHttpClient mOkHttpClient;
    private final BaseApis mBaseApis;

    public RetrofitManager() {

         mOkHttpClient = new OkHttpClient.Builder()
                 .readTimeout(5000, TimeUnit.MILLISECONDS)
                 .writeTimeout(5000,TimeUnit.MILLISECONDS)
                 .connectTimeout(5000,TimeUnit.MILLISECONDS)
                 .retryOnConnectionFailure(true)
                 .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(mOkHttpClient)
                .build();

         mBaseApis = retrofit.create(BaseApis.class);
    }

    public static synchronized RetrofitManager getInstance(){

        if (mRetrofitManager==null){
            mRetrofitManager=new RetrofitManager();
        }
        return mRetrofitManager;
    }

    private Observer mObserver=new Observer<ResponseBody>(){
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(ResponseBody responseBody) {

            try {
                String json = responseBody.string();

                if (mHttpListener!=null){
                    mHttpListener.onResponse(json);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public interface HttpListener{
        void onFail();
        void onResponse(String json);
    }

    private HttpListener mHttpListener;

    public void result(HttpListener httpListener){
        mHttpListener=httpListener;
    }


    public RetrofitManager get(String url){
        mBaseApis.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
        return mRetrofitManager;
    }

    public RetrofitManager post(String url, Map<String,String> map){
        if (map==null){
            map = new HashMap<>();
        }

        mBaseApis.post(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
        return mRetrofitManager;
    }

}
