package com.tianjistar.helppersion.activity.persion;

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

public class AddCardCheckYzmActivity extends Base1Activity {
    @Bind(R.id.tv_regest_phone)
    TextView textViewPhone;
    @Bind(R.id.tv_get_yzm)
    TextView textViewGetYzm;
    @Bind(R.id.vc_code)
    VerifyCodeView code;
    String phone="";


    @Override
    public int getContentView() {
        return R.layout.activity_add_card_check_yzm;
    }

    @Override
    public void initView() {
        setTitle("绑定银行卡");
        phone=getIntent().getStringExtra("phone");

        if (!TextUtils.isEmpty(phone)&&phone.length()>=11){
            textViewPhone.setText("+86"+"  "+phone.substring(0,3)+"  "+phone.substring(3,7)+"  "+phone.substring(7,11));
        }
        /**获取验证码*/
        getYzm();
        setListener();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    private void getYzm() {
        // 获取短信验证码
        MyParams params = new MyParams();
        params.put("mobile", "15738776423");//用户名或手机号
        params.put("type", 2);
        VictorHttpUtil.doPost(false, mContext, Define.URL_GET_CAPTCHA, params, true, "获取中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);
                        // 成功
                        new CaptchaTimer1(60000L, 1000L, textViewGetYzm).start();
                        MyApplication.showToastLong("验证码已发送到" + "15738776423" + "的手机中");

                    }
                });
    }

    private void setListener() {
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
                // 发送验证验证码功能
                MyParams params1 = new MyParams();
                params1.put("imei","867007034124682");
                params1.put("mobile", "15738776423");//用户名或手机号
                params1.put("code", code.getEditContent());//验证码
                params1.put("userid","kGHgFzTHLyA%2B%2BZDqR4gcJXXYQWTDkqq%2B3%2FZZC9s9Q9GZxTfZE5etjLLlgX0wt8FKKJ9gNN2cqW6C%0AZjpJOBAo5Bp%2FwjnVXeaiCn%2BF6Fbs0MkogKcboq48rA13RS4JamOanx2qcgB5K%2Fh8FJGFNPKERPnk%0Aalw728kOZdWbXs1NzqHTbw4gUZ3Us0nSpewnpe9eQQ0222TvbcbZfY6lVMP3hm5KO%2FkXsT7sDTWY%0A5Ow%2FUf7UqlHzYBpAPlsCX1bF1WLBxDNlVaUdgzNqYpWs%2Bn1IaZGqgq8VuJg4Z1yCUjceMxWDMWTr%0A57tkUhP4ezj71HOf7oKBcO09%2BovsDBzanmEf7A%3D%3D%0A");
                VictorHttpUtil.doPost(false, mContext, Define.URL_FORGET_CHECK_YZM, params1, true, "验证码校验中...", new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);
                        Bundle bundle = new Bundle();
                        bundle.putString("type","1");
                        MyApplication.openActivity(mContext, SuccessActivity.class, bundle);
                        finish();
                    }
                });
            }

            @Override
            public void invalidContent() {

            }
        });
    }

}
