package com.xpf.xsqlite;

import android.app.Application;

import com.xpf.library.Model;

/**
 * Created by xpf on 2018/1/24 :)
 * Function:
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Model.getInstance().init(this);
    }
}
