package com.xpf.library;

import android.content.Context;

import com.xpf.library.core.DBManager;
import com.xpf.library.dao.UserAccountDao;
import com.xpf.library.table.UserInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xpf on 2016/11/1 :)
 * Function:模型层全局类
 */

public class Model {

    private Context mContext;
    private static final Model model = new Model();

    //创建全局线程池
    private ExecutorService executor = Executors.newCachedThreadPool();
    private UserAccountDao mUserAccountDao;
    private DBManager dbManager;

    private Model() {
    }

    public static Model getInstance() {
        return model;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        //初始化用户账号数据库操作类
        mUserAccountDao = new UserAccountDao(context);
    }

    /**
     * 获取全局线程池
     *
     * @return
     */
    public ExecutorService getGlobalThreadPool() {
        return executor;
    }

    /**
     * 获取用户账号数据库操作类
     *
     * @return
     */
    public UserAccountDao getUserAccountDao() {
        return mUserAccountDao;
    }

    /**
     * 登陆成功后处理的事情
     *
     * @param userInfo
     */
    public void loginSuccess(UserInfo userInfo) {
        // 校验
        if (userInfo == null) {
            return;
        }
        if (dbManager != null) {
            dbManager.close();
        }
        dbManager = new DBManager(mContext, userInfo.getName());
    }

    /**
     * 获取数据库的管理者对象
     *
     * @return
     */
    public DBManager getDbManager() {
        return dbManager;
    }

    public Context getContext() {
        return mContext;
    }
}
