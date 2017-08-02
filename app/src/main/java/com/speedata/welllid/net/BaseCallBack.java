package com.speedata.welllid.net;



import okhttp3.Response;
import xyz.reginer.http.callback.Callback;


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
 * @author Reginer on  2016/8/22 22:20.
 *         Description:接口回调 参数clazz是类类型 用来实现Json解析
 */
public abstract class BaseCallBack<T> extends Callback<T> {
    private Class<T> clazz;

    protected BaseCallBack(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();


        System.out.println("parseNetworkResponse: " + string);


        return GsonUtil.json2Bean(string, clazz);
    }
}
