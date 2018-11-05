package com.tianjistar.helppersion.api;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.tianjistar.helppersion.app.MyApplication;
import com.tianjistar.helppersion.utils.StringUtil;


/**
 * 回调的简单空实现
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016-3-13 下午9:47:52
 */
public class BaseHttpCallbackListener<T> implements HttpCallbackListener<T> {

    private Context context;

    @Override
    public void callbackNoNetwork(String url) {

    }

    @Override
    public void callbackErrorJSONFormat(String url) {
        Logger.e("verrr", url);// 打印错误
    }

    @Override
    public void callbackError(String url, T obj) {
        if (obj instanceof Element) {
            Element element2 = (Element) obj;
            if (!StringUtil.isEmpty(element2.msg)) {
                MyApplication.showToast(element2.msg);
            }

        }
    }

    @Override
    public void callbackSuccess(String url, T element) {

    }

    @Override
    public void onFaliure(String url, int statusCode, String content, Throwable error) {
        Logger.e(error, url, statusCode);
        MyApplication.showToast("连接超时，请稍后重试");
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
