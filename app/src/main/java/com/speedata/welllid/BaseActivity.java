package com.speedata.welllid;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bugtags.library.Bugtags;
import com.johdan.paint.R;
import com.speedata.welllid.bean.DialogShowMsg;
import com.speedata.welllid.net.Global;
import com.speedata.welllid.widget.CustomProgressDialog;
import com.speedata.welllid.widget.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * ----------Dragon be here!----------/
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 *
 * @author Reginer on  2016/10/9 9:15.
 *         Description:Activity基类
 */
public class BaseActivity extends AppCompatActivity {

    protected TextView mBarTitle;
    private CustomProgressDialog mProgressDialog;
    protected ImageView mBarLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.theme));
        StatusBarUtil.setTranslucent(this);
    }

    protected void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    public void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    /**
     * 这是导航
     *
     * @param left  左侧图标
     * @param title 标题
     */
    protected void setNavigation(int left, int title) {
        mBarTitle = (TextView) findViewById(R.id.tv_bar_title);
        if (title != 0) {
            mBarTitle.setText(title);
        }
    }

    /**
     * 这是导航
     *
     * @param left  左侧图标
     * @param title 标题
     */
    protected void setNavigation(int left, String title) {
        mBarTitle = (TextView) findViewById(R.id.tv_bar_title);
        mBarLeft = (ImageView) findViewById(R.id.iv_left);
        if (!TextUtils.isEmpty(title)) {
            mBarTitle.setText(title);
        }
        mBarLeft.setVisibility(left == 0 ? View.GONE : View.VISIBLE);
    }

    /**
     * 用这个函数来接收消息弹窗
     *
     * @param event event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showDialogMsg(DialogShowMsg event) {
        switch (event.getTag()) {
            case Global.REQUEST_PREPARE:
                if (mProgressDialog == null) {
                    mProgressDialog = new CustomProgressDialog(this);
                }
                mProgressDialog.setMessage(event.getMsg());
                mProgressDialog.show();
                break;
            case Global.REQUEST_ERROR:
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
                break;
            case Global.REQUEST_SUCCESS:
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
                break;
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        Bugtags.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
        Bugtags.onPause(this);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

}