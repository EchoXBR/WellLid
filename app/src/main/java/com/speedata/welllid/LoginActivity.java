package com.speedata.welllid;

import android.content.Intent;
import android.support.design.widget.Snackbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.johdan.paint.R;
import com.speedata.welllid.bean.DialogShowMsg;
import com.speedata.welllid.bean.JgTrouble;
import com.speedata.welllid.bean.Result;
import com.speedata.welllid.bean.User;
import com.speedata.welllid.callback.ResultInter;
import com.speedata.welllid.net.Global;
import com.speedata.welllid.net.NetManager;
import com.speedata.welllid.utils.EditTextClearTools;

import java.util.List;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements ResultInter, View.OnClickListener {

    private EditText mEtUsername;
    private EditText mEtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        registerEventBus();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
//    on


    private Button btnLogin;

    /**
     * .
     */
    private void initView() {
        mEtUsername = (EditText) findViewById(R.id.et_username);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        com.speedata.welllid.utils.EditTextClearTools.addClearListener(mEtUsername, (ImageView) findViewById(R.id.iv_del_username));
        EditTextClearTools.addClearListener(mEtPwd, (ImageView) findViewById(R.id.iv_del_pwd));
        mEtUsername.setText("speedata");
        mEtPwd.setText("123456");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                submit();
                break;
            default:
                break;
        }
    }

    /**
     * .
     */
    private void submit() {
        String username = mEtUsername.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, getString(R.string.please_input_username), Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = mEtPwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, getString(R.string.please_input_pwd), Toast.LENGTH_SHORT).show();
            return;
        }
//        mProgressDialog.setMessage("正在登录");
        showDialogMsg(new DialogShowMsg
                ("正在登录", Global.REQUEST_PREPARE));
        NetManager.getLogin(username, pwd, this);

    }

    @Override
    public void getResult(Result loginResult) {
        if (loginResult.isSuccess()) {
            Map<String, Object> data = loginResult.getData();
            Object list = (data.get("user"));
            System.out.println("===list=" + list);
            String jsonString = String.valueOf(list);
            User user = JSON.toJavaObject(JSON.parseObject(jsonString), User.class);
            System.out.println("login user=="+user.toString());
            AppSpd.getInstance().setUser(user);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {
            Snackbar.make(btnLogin, "登录失败：" + loginResult.getMessage(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
    }
}

