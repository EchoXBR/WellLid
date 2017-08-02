package com.speedata.welllid;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.johdan.paint.R;
import com.speedata.welllid.bean.DialogShowMsg;
import com.speedata.welllid.bean.JgTrouble;
import com.speedata.welllid.bean.Result;
import com.speedata.welllid.callback.ResultInter;
import com.speedata.welllid.net.Global;
import com.speedata.welllid.net.NetManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MalfunctionDetialActivity extends BaseActivity implements ResultInter {

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private EditText edvDescription;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malfunction_detial);
        tv1 = findViewById(R.id.textView1);
        tv2 = findViewById(R.id.textView2);
        tv3 = findViewById(R.id.textView3);
        tv4 = findViewById(R.id.textView4);
        tv5 = findViewById(R.id.textView5);
        tv6 = findViewById(R.id.textView6);
        edvDescription = findViewById(R.id.edv_remark);
        final JgTrouble trouble = AppSpd.getInstance().getTrouble();
        tv1.setText(trouble.getTroubleCode());
        tv2.setText(trouble.getAddress());
        tv3.setText(trouble.getType() + "");
        tv4.setText(trouble.getTroubleTypeName());
        tv5.setText(trouble.getCompanyName());
//        tv6.setText(trouble.getCreateTime().toString());
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogMsg(new DialogShowMsg
                        ("正在提交", Global.REQUEST_PREPARE));
                NetManager.commitRepair(trouble.getTroubleCode(), edvDescription.getText().toString(),
                        AppSpd.getInstance().getUser().getLoginName(), MalfunctionDetialActivity.this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerEventBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
    }

    /**
     * 用这个函数来接收消息弹窗
     *
     * @param trouble
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
//    @Subscribe()
    public void onEventPost(JgTrouble trouble) {
        Snackbar.make(tv1, "get：" + trouble.toString(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();


    }

    @Override
    public void getResult(Result loginResult) {
        if (loginResult.isSuccess()) {
            Snackbar.make(fab, "提交成功", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
