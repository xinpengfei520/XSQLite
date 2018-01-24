package com.xpf.xsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xpf.library.Model;
import com.xpf.library.table.GroupInfo;
import com.xpf.library.table.InvitationInfo;
import com.xpf.library.table.UserInfo;
import com.xpf.library.utils.Constant;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAdd, btnDelete, btnUpdate, btnQuery;
    private String hxid = "222";
    private LocalBroadcastManager mLBM;
    private String groupId = "123";
    private String groupName = "交流群";
    private String inviter = "me";
    private String reason = "add";
    private String applicant = "applicant";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnQuery = findViewById(R.id.btnQuery);

        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnQuery.setOnClickListener(this);

        // 获取本地广播管理者的对象
        mLBM = LocalBroadcastManager.getInstance(this);

        Model.getInstance().loginSuccess(new UserInfo("xpf"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                saveGroupInvite();
                break;
            case R.id.btnDelete:
                // 本地数据变化
                Model.getInstance().getDbManager().getContactDao().deleteContactByHxId(hxid);
                // 发送联系人变化的广播
                mLBM.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));
                break;
            case R.id.btnUpdate:
                update();
                break;
            case R.id.btnQuery:
                query();
                break;
        }
    }

    private void query() {
        // 1.本地数据变化
        InvitationInfo invitationInfo = new InvitationInfo();
        // 原因
        invitationInfo.setReason(reason);
        // 联系人
        invitationInfo.setUser(new UserInfo(hxid));
        // 新邀请
        invitationInfo.setStatus(InvitationInfo.InvitationStatus.NEW_INVITE);
        Model.getInstance().getDbManager().getInvitationDao().addInvitation(invitationInfo);
        // 3.发送邀请广播
        mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
    }

    private void update() {
        // 1.保存数据到本地数据库
        InvitationInfo invitationInfo = new InvitationInfo();
        invitationInfo.setGroup(new GroupInfo(groupName, groupId, applicant));
        invitationInfo.setReason(reason);
        invitationInfo.setStatus(InvitationInfo.InvitationStatus.NEW_GROUP_APPLICATION); //新申请
        Model.getInstance().getDbManager().getInvitationDao().addInvitation(invitationInfo);
        // 3.发送群邀请信息变化的广播
        mLBM.sendBroadcast(new Intent(Constant.GROUP_INVITE_CHANGED));
    }

    private void saveGroupInvite() {
        // 1 保存数据到本地数据库
        InvitationInfo invitationInfo = new InvitationInfo();
        // 封装群对象
        invitationInfo.setGroup(new GroupInfo(groupName, groupId, inviter));
        // 邀请信息
        invitationInfo.setReason(reason);
        // 邀请状态
        invitationInfo.setStatus(InvitationInfo.InvitationStatus.NEW_GROUP_INVITE);
        Model.getInstance().getDbManager().getInvitationDao().addInvitation(invitationInfo);
        // 3.发送群邀请信息变化的广播
        mLBM.sendBroadcast(new Intent(Constant.GROUP_INVITE_CHANGED));
    }
}
