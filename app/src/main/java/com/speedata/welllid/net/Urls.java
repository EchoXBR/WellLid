package com.speedata.welllid.net;

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
 * @author :Reginer in  2017/3/28 10:35.
 *         联系方式:QQ:282921012
 *         功能描述:接口网址
 */
class Urls {
    private static final String BASE_URL = "http://192.168.0.111:8080/jgweb";//"http://192.168.1.167:8080/jgweb/a";
    //登录
    static final String LOGIN = BASE_URL + "/api/user/login";

    //激活
    static final String ACTIVE = BASE_URL + "/api/device/active";

    //repailist
    static final String WAIT_REPAIR=BASE_URL+"/api/trouble/getlist";
    //提交维修单
    static final String COMMIT_REPAIR = BASE_URL + "/api/repair/repairsave";
    //上传照片
    static final String UPLOAD_PIC = BASE_URL + "/api/device/upload";
    //读卡
    static final String FIND_CARD = BASE_URL + "FindCard";
    //兑换积分
    static final String AWARD_CARD = BASE_URL + "AwardCard";
    //获取社区
    static final String GET_COMMUNITY_INFO = BASE_URL + "CommunityInfo";
    //获取小区
    static final String GET_DISTRICT_INFO = BASE_URL + "DistrictInfo";
    //获取补卡原因
    static final String GET_SUPPLE_CARD_REASON = BASE_URL + "SuppleCardReason";
    //获取原卡片信息
    static final String QUERY_INFO = BASE_URL + "QueryInfo";
    //补卡
    static final String SUPPLE_CARD = BASE_URL + "SuppleCard";

    private static Urls mInstance = null;
    private String GET_VERSION = "http://218.247.237.138/speedataApps/trace/version.xml";

    public static Urls getInstance() {
        if (mInstance == null) {
            mInstance = new Urls();
        }
        return mInstance;
    }
    String getGET_VERSION(){return  GET_VERSION;}
}
