package com.tianjistar.helppersion.jpush;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;


import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.activity.persion.PersionActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by AA on 2017/2/24.
 */

public class TjnlReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    private Bitmap bitmap = null;
    private NotificationManager notifyManager = null;
    private NotificationCompat.Builder notifyBuilder = null;
    private Notification notification = null;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String url = "";
    private int type=1;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
//            if(bitmap!=null){
//                notifyBuilder.setLargeIcon(bitmap);
//            }else{
                notifyBuilder.setSmallIcon(R.drawable.ic_head_defout);
//            }
            notification = notifyBuilder.build();
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            notifyManager.notify(1000, notification);
        }
    };
    @Override
    public void onReceive(final Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//            自定义消息 跳转到消息页面
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);

            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
//            Intent i = new Intent(context, MainActivity.class); // 自定义打开的界面
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }
    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it =  json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " +json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }
    //自定义消息 跳转到消息页面
    private void processCustomMessage(Context context, Bundle bundle){
        notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notifyBuilder = new NotificationCompat.Builder(context);
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        Log.d("BB",title+"标题");
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        Log.d("AA",message+"消息");
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.d("CC",extras);
        //自定义信息： 获取
        if (extras != null) {
            try {
                JSONObject object = new JSONObject(extras);
                type=object.optInt("type");
                Log.d("typetype","返回的type值"+type);
                url = object.optString("src");
//                NewFriendsEvent event=new NewFriendsEvent("newFriends");
//                EventBus.getDefault().post(event);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (type==1){
            Intent i = new Intent(context, PersionActivity.class);
            bundle.putBoolean("push", true);
            i.putExtras(bundle);
            PendingIntent pi = PendingIntent.getActivity(context, 1000, i, PendingIntent.FLAG_UPDATE_CURRENT);
            notifyBuilder.setContentTitle(title);
            notifyBuilder.setContentText(message);
            notifyBuilder.setContentIntent(pi);
            notifyBuilder.setAutoCancel(true);
            notifyBuilder.setSmallIcon(R.drawable.ic_head_defout);
            new Thread(new Runnable() {
                @Override
                public void run() {
//                bitmap = returnBitMap(url);
                    handler.sendEmptyMessage(1);
                }
            }).start();

            handler.sendEmptyMessage(1);
        }

    }



}
