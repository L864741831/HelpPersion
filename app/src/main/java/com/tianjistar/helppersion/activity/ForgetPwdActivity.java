package com.tianjistar.helppersion.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.api.BaseHttpCallbackListener;
import com.tianjistar.helppersion.api.Define;
import com.tianjistar.helppersion.api.Element;
import com.tianjistar.helppersion.api.MyParams;
import com.tianjistar.helppersion.api.VictorHttpUtil;
import com.tianjistar.helppersion.app.MyApplication;
import com.tianjistar.helppersion.base.Base1Activity;
import com.tianjistar.helppersion.utils.CaptchaTimer;
import com.tianjistar.helppersion.utils.StringUtil;
import com.tianjistar.helppersion.view.widget.ClearEditText;

import butterknife.Bind;

public class ForgetPwdActivity extends Base1Activity implements View.OnClickListener {
    @Bind(R.id.tv_forget_get_yzm)
    TextView textViewGetYzm;//获取验证码
    @Bind(R.id.et_forget_yzm)
    ClearEditText clearEditTextYzm;
    @Bind(R.id.et_forget_phone)
    ClearEditText clearEditTextPhone;
    @Bind(R.id.btn_forget_sure)
    Button buttonSure;

    @Override
    public int getContentView() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    public void initView() {
        setTitle("忘记密码");
        setListener();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    private void setListener() {
        buttonSure.setOnClickListener(this);
        textViewGetYzm.setOnClickListener(this);
        clearEditTextYzm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()>0&&!editable.toString().contains(" ")){
                    buttonSure.setAlpha(1);
                }else{
                    buttonSure.setAlpha((float) 0.5);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_get_yzm:
                getYzm();

                break;
            case R.id.btn_forget_sure:
                String phone=clearEditTextPhone.getText().toString().trim();
                String yzm=clearEditTextYzm.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    MyApplication.showToast("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(yzm)){
                    MyApplication.showToast("请输入验证码");
                    return;
                }

                break;
        }
    }
    /**
     * 获取验证码
     */

    public void getYzm() {
        final String phone = clearEditTextPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
            MyApplication.showToast("手机号不能为空");
            clearEditTextPhone.requestFocus();
            return;
        }
        if (!StringUtil.isPhoneNumber(phone)){
            MyApplication.showToast("手机号格式不正确");
            clearEditTextPhone.requestFocus();
            return;
        }
        MyParams params = new MyParams();
        params.put("mobile",phone);
        params.put("type",2);
        VictorHttpUtil.doPost(false,this, Define.URL_FORGET_GET_YZM,params,false,"",new BaseHttpCallbackListener<Element>(){
            @Override
            public void callbackSuccess(String url, Element element) {
//                    time.start();//开始计时
                new CaptchaTimer(60000L, 1000L, textViewGetYzm).start();
                MyApplication.showToastLong("验证码已发送到" + phone + "的手机中");
                // 验证码框获得焦点
                clearEditTextYzm.requestFocus();

            }
        });
    }
}
