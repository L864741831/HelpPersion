package com.tianjistar.helppersion.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.activity.persion.SuccessActivity;
import com.tianjistar.helppersion.app.MyApplication;
import com.tianjistar.helppersion.base.Base1Activity;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

public class UpdatePayPwdSurePwdActivity extends Base1Activity {

    @Bind(R.id.et_upsure_pwd)
    EditText editTextPwd;
    @Bind(R.id.bt_upsure_next)
    Button buttonSure;
    String pwd;

    @Override
    public int getContentView() {
        return R.layout.activity_update_pay_pwd_sure_pwd;
    }

    @Override
    public void initView() {
        setTitle("设置支付密码");
        pwd=getIntent().getStringExtra("pwd");
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

                // TODO: 2018/4/3  两次密码一样 给后台接口成功后发广播关闭设置密码页面和本页面
                EventBus.getDefault().post("close");
                Bundle bundle=new Bundle();
                bundle.putString("type","2");
                MyApplication.openActivity(mContext, SuccessActivity.class,bundle);
                finish();

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
