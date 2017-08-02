package com.speedata.welllid;

import android.app.Application;
import android.text.TextUtils;

import com.speedata.utils.SPUtils;
import com.speedata.welllid.bean.JgTrouble;
import com.speedata.welllid.bean.User;
import com.speedata.welllid.net.Global;
import com.speedata.welllid.net.NetManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import xyz.reginer.http.RHttp;

/**
 *
 */
public class AppSpd extends Application {

    private static AppSpd instance;
    private User user;
    private JgTrouble trouble;

    public JgTrouble getTrouble() {
        return trouble;
    }

    public void setTrouble(JgTrouble trouble) {
        this.trouble = trouble;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        init();
        checkVersion();
    }

    //检测新版本
    private void checkVersion() {
//        NetManager.getNewVersion();
    }

    private void init() {
        String spUrl = (String) SPUtils.get(this, Global.URL_CONTENT, "", Global.URL_FILE);
//        if (!TextUtils.isEmpty(spUrl))
//            Urls.getInstance().setBASE_URL(spUrl);

        //设置网络连接超时时间为6S
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(6000L, TimeUnit.MILLISECONDS)
                .readTimeout(6000L, TimeUnit.MILLISECONDS)
                .build();

        RHttp.initClient(okHttpClient);


//        Bugtags.start("c748b44d1ef37cc52470cd9c2257c7b4", this, Bugtags.BTGInvocationEventNone);
    }


    public static AppSpd getInstance() {
        return instance;
    }
}