package com.tianjistar.helppersion.app;

import android.os.Environment;

import java.io.File;

/**
 * Author:
 * Version    V1.0
 * Date:
 * Description: APP SharedPreferences KEY 常量工具类
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 14-11-22                  1.0                    1.0
 * Why & What is modified:
 */
public class AppSpContact {
    public static final String FILE_DATA_ROOT_PATH = Environment.getExternalStorageDirectory().getPath() + "/Tjnla/";
    public static final String FILE_DATA_PICTURE_PATH = FILE_DATA_ROOT_PATH + "picture/";
    public static final String FILE_DATA_AUDIO_PATH = FILE_DATA_ROOT_PATH + "audio/";
    public static final String FILE_DATA_AVATAR_PATH = FILE_DATA_ROOT_PATH + "avatar/";
    public static final String ENCODE_TYPE = "UTF-8";
    public static final String HTTP_INPUT_TYPE = "application/json";

    public static final String USERINFO = "userInfo";/*保存用户信息*/
    public static final String SP_FIRST_LOGIN ="first_login";//是否是第一次登录
    public static final String SP_UID = "uid";//用户uid

    public static final String SP_DEVICEID = "deviceId";//设备id
    public static final String JUMP_LOGIN= "jump_login";//是否公开信息


    public static final String SP_FIRST_LAUCH ="first_launch";//是否是第一次启动

    public static final String USER_UUID = "uuid";//uuid
    public static final String USER_ID = "user_id";//用户id
    public static final String USER_AVATAR = "user_avatar";//用户头像
    public static final String USER_SEX= "user_sex";//用户性别
    public static final String USER_BLOOD = "user_blood";//血型
    public static final String USER_BIRTHDAY = "user_birthday";//生日
    public static final String USER_PHONE = "user_phone";//联系方式
    public static final String USER_NAME = "user_name";//用户昵称


    /**
     * HTTP请求服务错误的状态码
     */
    public static final int HTTP_SERVER_ERROR_CODE = 500;
    /**
     * 编码格式
     */

    public final static File ROOT_FILE = new File(Environment.getExternalStorageDirectory(), "/tjkg");/*文件存储根目录*/



}
