package com.xpf.library.core;

import android.content.Context;

import com.xpf.library.dao.ContactDao;
import com.xpf.library.dao.InvitationDao;

/**
 * Created by xpf on 2016/11/2 :)
 * Function:数据库的管理者类
 */

public class DBManager {

    private final DBHelper dbHelper;
    private final ContactDao contactDao;
    private final InvitationDao invitationDao;

    public DBManager(Context context, String name) {
        dbHelper = new DBHelper(context, name);
        //初始化操作类
        contactDao = new ContactDao(dbHelper);
        invitationDao = new InvitationDao(dbHelper);
    }

    //获取联系人表的操作类对象
    public ContactDao getContactDao() {
        return contactDao;
    }

    // 获取邀请信息表的操作类对象
    public InvitationDao getInvitationDao() {
        return invitationDao;
    }

    public void close() {
        dbHelper.close();
    }
}
