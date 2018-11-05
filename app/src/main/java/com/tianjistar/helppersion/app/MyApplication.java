package com.tianjistar.helppersion.app;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.tianjistar.helppersion.base.BaseApplication;
import com.tianjistar.helppersion.bean.User;
import com.tianjistar.helppersion.utils.GsonUtil;
import com.tianjistar.helppersion.utils.PreferencesUtils;
import com.tianjistar.helppersion.utils.SharedPreferencesHelper;
import com.tianjistar.helppersion.utils.SharedPreferencesUtil;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Administrator on 2017/5/22.
 */

public class MyApplication extends BaseApplication {

    private static Context sContext;
    private static String registrationID;
    public static SharedPreferencesUtil spUtil;
    public static User CURRENT_USER;// 当前用户
    public static final boolean DEBUG = true;// 是否debug， 开发和测试阶段使用
//    private static DisplayImageOptions options;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        setUpSharedPreferencesHelper(getApplicationContext());//初始化SharedPreferences
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        registrationID = JPushInterface.getRegistrationID(this);

        //必须调用初始化
        OkGo.init(this);
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(3)
                    //如果不想让框架管理cookie（或者叫session的保持）,以下不需要
                    .setCookieStore(new PersistentCookieStore());      //cookie持久化存储，如果cookie不过期，则一直有效
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取Application 上下文
     *
     * @return
     */
    public static Context getContext() {

        return sContext;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 初始化SharedPreferences
     *
     * @param context 上下文
     */
    private void setUpSharedPreferencesHelper(Context context) {
        SharedPreferencesHelper.getInstance().Builder(context);
    }
    /**
     *   动态添加权限
     */
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 保存用户
     *
     * @param user
     */
    public static void saveUser(User user) {
        MyApplication.CURRENT_USER = user;
        // spUtil.setObject("CURRENT_USER", CURRENT_USER);
        PreferencesUtils.putString(getContext(), AppSpContact.USERINFO, GsonUtil.GsonString(user));
    }
    /**
     * 读取用户
     *
     * @return
     */
    public static User getUser() {
        User current_user = GsonUtil.GsonToBean(PreferencesUtils.getString(getContext(), AppSpContact.USERINFO), User.class);
        return current_user ;
    }


    /**
     * 跳到查看大图
     *
     * @param activity
     * @param isDelete
     * @param imageUrlList
     */
    public static void goGalleryUrlActivity(Activity activity, boolean isDelete, int point, ArrayList<String> imageUrlList) {
//        Intent intent = new Intent(activity, ViewPagerActivity.class);
//        intent.putExtra("isDelete", isDelete);
//        intent.putExtra("point", point);
//        intent.putStringArrayListExtra("imageUrlList", imageUrlList);
//        activity.startActivity(intent);
    }

    /**
     * @param activity
     * @param forward_msg_id
     * @param imagePath
     */
    public static void goForwardMessageActivity(Activity activity, String forward_msg_id, String imagePath) {
//        Intent intent = new Intent(activity, ForwardMessageActivity.class);
//        intent.putExtra("forward_msg_id", forward_msg_id);
//        intent.putExtra("imagePath", imagePath);
//        activity.startActivity(intent);
    }

//    public static DisplayImageOptions getOptions() {
//        return options;
//    }
//    private void openSealDBIfHasCachedToken() {
//        SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
//        //  String cachedToken = sp.getString("loginToken", "");
//        String cachedToken = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_TOKEN);
//        if (!TextUtils.isEmpty(cachedToken)) {
//            String current = getCurProcessName(this);
//            String mainProcessName = getPackageName();
//            if (mainProcessName.equals(current)) {
//                SealUserInfoManager.getInstance().openDB();
//            }
//        }
//    }
}
