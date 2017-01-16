package com.example.qyb.testvoconsample;

import android.app.Application;
import android.content.Context;

import com.nuance.sample.voconsample.ssdp.HuaweiSmartGateway;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Created by QYB on 2016/12/26.
 */

public class MyApplication extends Application {

    protected Context appContext;
//    public HuaweiSmartGateway huaweiSmartGateway;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext=getApplicationContext();
        HermesEventBus.getDefault().connectApp(this, "com.tanscorp.robot");
//        mHandler.sendEmptyMessageDelayed(1, 6*1000);
    }

    /*@SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initSmartHome();
                    break;
                default:
                    break;
            }
        }
    };

    *//**
     * 初始化华为智能网关类
     *//*
    private void initSmartHome() {
        huaweiSmartGateway = new HuaweiSmartGateway(getApplicationContext());
        huaweiSmartGateway.init();
        Logger.d(TAG, "初始化华为智能网关类");
    }*/
}
