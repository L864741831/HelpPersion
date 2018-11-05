package com.tianjistar.helppersion.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.api.BaseHttpCallbackListener;
import com.tianjistar.helppersion.api.Define;
import com.tianjistar.helppersion.api.Element;
import com.tianjistar.helppersion.api.MyParams;
import com.tianjistar.helppersion.api.VictorHttpUtil;
import com.tianjistar.helppersion.app.MyApplication;
import com.tianjistar.helppersion.base.Base1Activity;
import com.tianjistar.helppersion.utils.CaptchaTimer1;
import com.tianjistar.helppersion.view.VerifyCodeView;

import butterknife.Bind;

/**
 * 忘记支付密码   获取验证码
 */
public class FogetPayPwdYzmActivity extends Base1Activity {

    @Bind(R.id.tv_regest_phone)
    TextView textViewPhone;
    @Bind(R.id.tv_get_yzm)
    TextView textViewGetYzm;
    @Bind(R.id.vc_code)
    VerifyCodeView code;

    private String phone ="";
    private String token ="";

    @Override
    public int getContentView() {
        return R.layout.activity_foget_pwd_yzm;
    }

    @Override
    public void initView() {
        setTitle("忘记支付密码");
        phone = getIntent().getStringExtra("phone");
        token = getIntent().getStringExtra("token");

        if (!TextUtils.isEmpty(phone)&&phone.length()>=11){
            textViewPhone.setText("+86"+"  "+phone.substring(0,3)+"  "+phone.substring(3,7)+"  "+phone.substring(7,11));
        }
    }

    @Override
    public void initData() {
        /**获取验证码*/
        getYzm();
    }

    @Override
    public void initListener() {
        textViewGetYzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textViewGetYzm.getText().equals("重发验证码")){//重新发送验证码
                    getYzm();
                }
            }
        });
        code.setInputCompleteListener(new VerifyCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                submitData();
            }

            @Override
            public void invalidContent() {
            }
        });
    }

//    phone	string	是	手机号
    private void getYzm() {
        // 获取短信验证码
        MyParams params = new MyParams();
        params.put("phone", phone);
        VictorHttpUtil.doPost(false, mContext, Define.SEND_YZM_URL, params, true, "获取中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);
                        // 成功
                        new CaptchaTimer1(60000L, 1000L, textViewGetYzm).start();
                        MyApplication.showToastLong("验证码已发送到" + phone + "的手机中");
                    }
                });
    }

//    validateCode	string	是	验证码
    private void submitData() {
        MyParams params1 = new MyParams();
        params1.put("validateCode", code.getEditContent());//验证码
        VictorHttpUtil.doPost(false, mContext, Define.CHECK_YZM_IS_PASS_URL, params1, true, "验证码校验中...",
                new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                super.callbackSuccess(url, element);
                Bundle bundle=new Bundle();
                bundle.putString("token",token);
                MyApplication.openActivity(mContext, ForgetPayPwdSetPwdActivity.class,bundle);
            }
        });
    }

}
