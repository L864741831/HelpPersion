package com.tianjistar.helppersion.activity.persion;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.activity.LoginActivity;
import com.tianjistar.helppersion.api.BaseHttpCallbackListener;
import com.tianjistar.helppersion.api.Define;
import com.tianjistar.helppersion.api.Element;
import com.tianjistar.helppersion.api.MyParams;
import com.tianjistar.helppersion.api.VictorHttpUtil;
import com.tianjistar.helppersion.app.AppSpContact;
import com.tianjistar.helppersion.app.MyApplication;
import com.tianjistar.helppersion.base.Base1Activity;
import com.tianjistar.helppersion.utils.AppUtil;
import com.tianjistar.helppersion.utils.DisplayUtil;
import com.tianjistar.helppersion.utils.PreferencesUtils;
import com.tianjistar.helppersion.utils.SharedPreferencesHelper;

import butterknife.Bind;

/**
 * 设置界面
 */
public class SetActivity extends Base1Activity implements View.OnClickListener {

    @Bind(R.id.ll_set_about)
    LinearLayout linearLayoutAbout;
    @Bind(R.id.ll_set_pay)
    LinearLayout linearLayoutPay;
    @Bind(R.id.ll_set_update)
    LinearLayout linearLayoutUpdate;
    @Bind(R.id.ll_exit)
    LinearLayout ll_exit;

    private PopupWindow popupWindow;

    @Override
    public int getContentView() {
        return R.layout.activity_set;
    }

    @Override
    public void initView() {
        setTitle("设置");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        linearLayoutAbout.setOnClickListener(this);
        linearLayoutPay.setOnClickListener(this);
        linearLayoutUpdate.setOnClickListener(this);
        ll_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      switch (view.getId()) {
          case R.id.ll_set_update:
              MyApplication.openActivity(mContext,UpdataPwdActivity.class);
              break;
          case R.id.ll_set_about:
              MyApplication.openActivity(mContext,AboutActivity.class);
              break;
          case R.id.ll_set_pay:
              MyApplication.openActivity(mContext,SetPayPswActivity.class);
              break;
          case R.id.ll_exit:
              exit();
              break;
          default:
              break;
      }
    }

    //退出登录
    private void exit() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_exit, null);
        TextView tvCancel = (TextView) view.findViewById(R.id.pop_tv_cancle);
        TextView sure = (TextView) view.findViewById(R.id.pop_tv_sure);

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        DisplayUtil.setAlpha(mActivity, 0.5f);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DisplayUtil.setAlpha(mActivity, 1f);
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2018/3/21 退出登录的逻辑
                withdrawLogin();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void withdrawLogin() {
        MyParams params = new MyParams();
        params.put("app","yes");
        params.put("uuid", SharedPreferencesHelper.getInstance().getString(AppSpContact.USER_UUID));
        params.put("imei", AppUtil.getPhoneImei(mActivity));
        VictorHttpUtil.doPost(false, mContext, Define.WITHDRAW_URL, params, true, "退出中...", new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                super.callbackSuccess(url, element);

                popupWindow.dismiss();
                // 退出成功
                MyApplication.showToast("退出成功");

                SharedPreferencesHelper.getInstance().clear();

                MyApplication.openActivity(mContext, LoginActivity.class);
                finish();
            }

            @Override
            public void callbackError(String url, Element obj) {
                super.callbackError(url, obj);
                popupWindow.dismiss();
            }
        });

    }


}
