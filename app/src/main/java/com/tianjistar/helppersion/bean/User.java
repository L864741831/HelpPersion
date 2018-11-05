package com.tianjistar.helppersion.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/23.
 */

public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    public String  userid;//用户id
    public String  username;//用户昵称
    public String  photo;//头像
    public String background;//个人信息背景
    public int  isopen;//是否公开信息
    public String imei;//唯一设备标识
    public String uniqid;//唯一设备标识

    public static ArrayList<User> parseList(JSONArray jsonArr)
            throws JSONException {
        ArrayList<User> list = new ArrayList<>();
        for (int i = 0; i < jsonArr.length(); i++) {
            list.add(parseItem(jsonArr.getJSONObject(i)));
        }
        return list;
    }

    public static User parseItem(JSONObject json) throws JSONException {
        User loginData = new User();
        loginData.userid=json.optString("userid");
        loginData.username=json.optString("username");
        loginData.photo=json.optString("photo");
        loginData.background=json.optString("background");
        loginData.isopen=json.optInt("isopen");
        loginData.imei=json.optString("imei");
        loginData.uniqid=json.optString("uniqid");

        return loginData;
    }
}
