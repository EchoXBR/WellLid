package com.speedata.welllid.net;

import android.graphics.Bitmap;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.speedata.welllid.callback.ActiveDevInter;
import com.speedata.welllid.callback.ResultInter;
import com.speedata.welllid.bean.DialogShowMsg;
import com.speedata.welllid.bean.JgDevice;
import com.speedata.welllid.bean.PostParamsModel;
import com.speedata.welllid.bean.Result;
import com.speedata.welllid.bean.User;
import com.speedata.welllid.utils.ToolToast;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import xyz.reginer.http.RHttp;

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
 * @author :Reginer in  2017/3/15 15:53.
 *         联系方式:QQ:282921012
 *         功能描述:
 */
public class NetManager {
    private static final String TAG = "Reginer";

    /**
     * 登录.
     *
     * @param username   账号
     * @param pwd        密码
     * @param loginInter loginInter
     */
    public static void getLogin(String username, String pwd, final ResultInter loginInter) {
        Map<String, String> params = getPrivateParams();
        PostParamsModel postParamsModel = new PostParamsModel();

        User user = new User();
        user.setPassword(pwd);
        user.setUsername(username);
        String p = JSON.toJSONString(user);
        params.put("postData", p);
        System.out.println("login rece send " + params.toString());
        RHttp.postString().url(Urls.LOGIN).
                content(new Gson().toJson(params)).mediaType(MediaType.parse
                (Global.SET_JSON)).build().execute(new BaseCallBack<Result>(Result.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                System.out.println("login==Exception==" + e.getMessage());
                EventBus.getDefault().post(new DialogShowMsg
                        (e.getMessage(), Global.REQUEST_ERROR));
                ToolToast.toastShort(e.toString());
            }

            @Override
            public void onResponse(Result response, int id) {
                System.out.println("login====" + response.toString());
                EventBus.getDefault().post(new DialogShowMsg
                        (response.isSuccess() + "", Global.REQUEST_SUCCESS));
                loginInter.getResult(response);
            }
        });
    }


    /**
     * 激活设备
     * @param device
     * @param loginInter
     */
    public static void activeDev(JgDevice device, final ResultInter loginInter) {
        Map<String, String> params = getPrivateParams();
        String p = JSON.toJSONString(device);
        params.put("postData", p);
        System.out.println("activeDev rece send " + params.toString());
        RHttp.postString().url(Urls.ACTIVE).
                content(new Gson().toJson(params)).mediaType(MediaType.parse
                (Global.SET_JSON)).build().execute(new BaseCallBack<Result>(Result.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                System.out.println("activeDev==Exception==" + e.getMessage());
                EventBus.getDefault().post(new DialogShowMsg
                        (e.getMessage(), Global.REQUEST_ERROR));
                ToolToast.toastShort(e.toString());
            }

            @Override
            public void onResponse(Result response, int id) {
                System.out.println("activeDev====" + response.toString());
                EventBus.getDefault().post(new DialogShowMsg
                        (response.isSuccess() + "", Global.REQUEST_SUCCESS));
                if (response.isSuccess()) {
//                    JgDevice jgDevice = JSON.toJavaObject(JSON.parseObject(response.getMessage()), JgDevice.class);
                    loginInter.getResult(response);
                }else{
                    EventBus.getDefault().post(new DialogShowMsg
                            (response.getMessage(), Global.REQUEST_ERROR));
                    ToolToast.toastShort(response.getMessage());
                }
            }
        });
    }

    /**
     * 获取带维修列表
     * @param loginInter
     */
    public static void getWaitRepair(final ResultInter loginInter) {
        Map<String, String> params = getPrivateParams();
        Map<String, String> temp = getPrivateParams();
        temp.put("deviceCode","1000001");
        params.put("postData", new Gson().toJson(temp));
        System.out.println("getWaitRepair rece send " + params.toString());
        RHttp.postString().url(Urls.WAIT_REPAIR).
                content(new Gson().toJson(params)).mediaType(MediaType.parse
                (Global.SET_JSON)).build().execute(new BaseCallBack<Result>(Result.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                System.out.println("getWaitRepair==Exception==" + e.getMessage());
                EventBus.getDefault().post(new DialogShowMsg
                        (e.getMessage(), Global.REQUEST_ERROR));
                ToolToast.toastShort(e.toString());
            }

            @Override
            public void onResponse(Result response, int id) {
                System.out.println("getWaitRepair====" + response.toString());
                EventBus.getDefault().post(new DialogShowMsg
                        (response.isSuccess() + "", Global.REQUEST_SUCCESS));
                if (response.isSuccess()) {
//                    JgDevice jgDevice = JSON.toJavaObject(JSON.parseObject(response.getMessage()), JgDevice.class);
                    loginInter.getResult(response);
                }else{
                    EventBus.getDefault().post(new DialogShowMsg
                            (response.getMessage(), Global.REQUEST_ERROR));
                    ToolToast.toastShort(response.getMessage());
                }
            }
        });
    }


    /**
     * 提交维修申请
     * @param loginInter
     */
    public static void commitRepair(String troubleCode,String description,String repairUser,final ResultInter loginInter) {
        Map<String, String> params = getPrivateParams();
        Map<String, String> temp = getPrivateParams();
        temp.put("troubleCode",troubleCode);
        temp.put("description",description);
        temp.put("repairUser",repairUser);
        params.put("postData", new Gson().toJson(temp));
        System.out.println("commitRepair  send " + params.toString());
        RHttp.postString().url(Urls.COMMIT_REPAIR).
                content(new Gson().toJson(params)).mediaType(MediaType.parse
                (Global.SET_JSON)).build().execute(new BaseCallBack<Result>(Result.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                System.out.println("commitRepair==Exception==" + e.getMessage());
                EventBus.getDefault().post(new DialogShowMsg
                        (e.getMessage(), Global.REQUEST_ERROR));
                ToolToast.toastShort(e.toString());
            }

            @Override
            public void onResponse(Result response, int id) {
                System.out.println("commitRepair====" + response.toString());
                EventBus.getDefault().post(new DialogShowMsg
                        (response.isSuccess() + "", Global.REQUEST_SUCCESS));
                if (response.isSuccess()) {
//                    JgDevice jgDevice = JSON.toJavaObject(JSON.parseObject(response.getMessage()), JgDevice.class);
                    loginInter.getResult(response);
                }else{
                    EventBus.getDefault().post(new DialogShowMsg
                            (response.getMessage(), Global.REQUEST_ERROR));
                    ToolToast.toastShort(response.getMessage());
                }
            }
        });
    }


    public static void commitPic(Bitmap bitmap, final ResultInter loginInter) {
        Map<String, String> params = getPrivateParams();
        Map<String, String> temp = new HashMap<>();
        temp.put("file",new Gson().toJson(bitmap));
        params.put("postData", new Gson().toJson(temp));
        System.out.println("commitPic  send " + params.toString());
        RHttp.postString().url(Urls.UPLOAD_PIC).
                content(new Gson().toJson(params)).mediaType(MediaType.parse
                (Global.SET_JSON)).build().execute(new BaseCallBack<Result>(Result.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                System.out.println("commitPic==Exception==" + e.getMessage());
                EventBus.getDefault().post(new DialogShowMsg
                        (e.getMessage(), Global.REQUEST_ERROR));
                ToolToast.toastShort(e.toString());
            }

            @Override
            public void onResponse(Result response, int id) {
                System.out.println("commitPic====" + response.toString());
                EventBus.getDefault().post(new DialogShowMsg
                        (response.isSuccess() + "", Global.REQUEST_SUCCESS));
                if (response.isSuccess()) {
//                    JgDevice jgDevice = JSON.toJavaObject(JSON.parseObject(response.getMessage()), JgDevice.class);
                    loginInter.getResult(response);
                }else{
                    EventBus.getDefault().post(new DialogShowMsg
                            (response.getMessage(), Global.REQUEST_ERROR));
                    ToolToast.toastShort(response.getMessage());
                }
            }
        });
    }
    /**
     * 获取信息头
     *
     * @return
     */
    private static Map<String, String> getPrivateParams() {
        Map<String, String> params = new HashMap<>();
        params.put("id", "11");
        params.put("signature", "123");
        params.put("nonce", "1232");
        return params;
    }


//    public static void getNewVersion() {
//        RHttp.get().url(Urls.getInstance().getGET_VERSION()).build().execute(new BaseCallBack<Version>(Version.class) {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//            }
//
//            @Override
//            public void onResponse(Version response, int id) {
//                if (response.getData().getVersion() > ToolCommon.getVerCode(AppSpd.getInstance())) {
//                    ToolCommon.downloadApk(AppSpd.getInstance(), response.getData().getPath());
//                }
//            }
//        });
//    }

}
