package com.tianjistar.helppersion.activity.persion;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.tianjistar.helppersion.app.SpContact;
import com.tianjistar.helppersion.base.Base1Activity;
import com.tianjistar.helppersion.utils.AppUtil;
import com.tianjistar.helppersion.utils.SharedPreferencesHelper;

import butterknife.Bind;


/**
 * 修改登录密码
 * */
public class UpdataPwdActivity extends Base1Activity {

    @Bind(R.id.bt_updatepassword)
    Button bt_updatepassword;
    @Bind(R.id.et_tel_num)
    EditText et_tel_num;//原密码
    @Bind(R.id.et_password)
    EditText et_password;//新密码
    @Bind(R.id.et_newpassword)
    EditText et_newpassword;//确定新密码
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.tv_topbar_title)
    TextView tvTopbarTitle;

    private SharedPreferencesHelper preferencesHelper;

    @Override
    public int getContentView() {
        return R.layout.activity_updata_pwd;
    }

    @Override
    public void initView() {
        setTitle("修改登录密码");
    }

    @Override
    public void initData() {
        preferencesHelper = SharedPreferencesHelper.getInstance();
        bt_updatepassword.setOnClickListener(new View.OnClickListener() {//完成修改密码
            @Override
            public void onClick(View view) {
                upData();
            }
        });
        //支付密码6位数字监听
//        setListener();
    }

    @Override
    public void initListener() {
//        if (getIntent().getStringExtra("type").equals("2")){
//            et_tel_num.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//                    if (editable.length() > 6) {
//                        MyApplication.showToast( "支付密码由6位数字组成");
//                        editable.delete(6, editable.length());
//                    }
//                }
//            });
//            et_password.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//                    if (editable.length() > 6) {
//                        MyApplication.showToast( "支付密码由6位数字组成");
//                        editable.delete(6, editable.length());
//                    }
//                }
//            });
        et_newpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()>0&&!editable.toString().contains(" ")){
                    bt_updatepassword.setAlpha(1);
                }else{
                    bt_updatepassword.setAlpha((float) 0.5);
                }
            }
        });
    }

    private void upData() {
        if (TextUtils.isEmpty(et_tel_num.getText().toString()) ||
                TextUtils.isEmpty(et_password.getText().toString()) ||
                TextUtils.isEmpty(et_newpassword.getText().toString())){
            MyApplication.showToast("修改信息不能为空");
            return;
        }
        if (!et_password.getText().toString().equals(et_newpassword.getText().toString())){
            MyApplication.showToast("两次输入密码不一致");
            return;
        }

        MyParams params = new MyParams();
        params.put("uuid", preferencesHelper.getString(AppSpContact.USER_UUID));
        params.put("imei", AppUtil.getPhoneImei(mContext));
        params.put("app","yes");

        params.put("mobile",preferencesHelper.getString(AppSpContact.USER_PHONE));
        params.put("password",et_tel_num.getText().toString());
        params.put("newPassword",et_password.getText().toString());

        VictorHttpUtil.doPost(false,mContext,Define.UPDATE_PASSWORD_URL,params,true,null,
                new BaseHttpCallbackListener<Element>(){
            @Override
            public void callbackSuccess(String url, Element element) {
                super.callbackSuccess(url, element);
                MyApplication.showToast("修改成功,请重新登录");

                preferencesHelper.clear();
                MyApplication.openActivity(mContext, LoginActivity.class);
                finish();
            }
        });

    }
}

