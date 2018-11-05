package com.tianjistar.helppersion.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.activity.persion.SuccessActivity;
import com.tianjistar.helppersion.api.BaseHttpCallbackListener;
import com.tianjistar.helppersion.api.Define;
import com.tianjistar.helppersion.api.Element;
import com.tianjistar.helppersion.api.MyParams;
import com.tianjistar.helppersion.api.VictorHttpUtil;
import com.tianjistar.helppersion.app.AppSpContact;
import com.tianjistar.helppersion.app.MyApplication;
import com.tianjistar.helppersion.base.Base1Activity;
import com.tianjistar.helppersion.utils.AppUtil;
import com.tianjistar.helppersion.utils.SharedPreferencesHelper;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

/**
 * 忘记支付密码之再次设置支付密码
 */
public class ForgetPayPwdSurePwdActivity extends Base1Activity {

    @Bind(R.id.et_sure_pwd)
    EditText editTextPwd;
    @Bind(R.id.bt_sure_next)
    Button buttonSure;

    private String pwd;
    private String mToken;

    @Override
    public int getContentView() {
        return R.layout.activity_forget_pay_pwd_sure_pwd;
    }

    @Override
    public void initView() {
        setTitle("设置支付密码");
    }

    @Override
    public void initData() {
        pwd = getIntent().getStringExtra("pwd");
        mToken = getIntent().getStringExtra("token");
    }

    @Override
    public void initListener() {
        buttonSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(pwd)){
                    MyApplication.showToast("请输入密码");
                    return;
                }
                if (!pwd.equals(editTextPwd.getText().toString().trim())){
                    MyApplication.showToast("两次输入密码不一样");
                    return;
                }
                submit();

            }
        });
    }

    //    token	string	是	token
//    newpay	string	是	新密码
//    app	string	是	yes
//    uuid	string	是	用户uuid
//    imei	string	是	手机唯一识别号
    private void submit(){
        MyParams params = new MyParams();
        params.put("uuid", SharedPreferencesHelper.getInstance().getString(AppSpContact.USER_UUID));
        params.put("imei", AppUtil.getPhoneImei(mContext));
        params.put("app", "yes");

        params.put("newpay",editTextPwd.getText().toString().trim());
        params.put("token",mToken);

        VictorHttpUtil.doPost(false, mContext, Define.SET_NEW_PAY_PSW_URL, params, true, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);
                        MyApplication.showToast(element.msg);
                        // TODO: 2018/4/3  两次密码一样 给后台接口成功后发广播关闭设置密码页面和本页面
                        EventBus.getDefault().post("close");
                        Bundle bundle=new Bundle();
                        bundle.putString("type","3");
                        MyApplication.openActivity(mContext, SuccessActivity.class,bundle);
                        finish();
                    }
                });
    }



}
