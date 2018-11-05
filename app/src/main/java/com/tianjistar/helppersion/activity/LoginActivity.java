package com.tianjistar.helppersion.activity;

import android.Manifest;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.api.BaseHttpCallbackListener;
import com.tianjistar.helppersion.api.Define;
import com.tianjistar.helppersion.api.Element;
import com.tianjistar.helppersion.api.MyParams;
import com.tianjistar.helppersion.api.VictorHttpUtil;
import com.tianjistar.helppersion.app.AppSpContact;
import com.tianjistar.helppersion.app.MyApplication;
import com.tianjistar.helppersion.base.Base1Activity;
import com.tianjistar.helppersion.bean.LoginBean;
import com.tianjistar.helppersion.utils.AppUtil;
import com.tianjistar.helppersion.utils.SharedPreferencesHelper;
import com.tianjistar.helppersion.view.widget.ClearEditText;

import java.util.ArrayList;

import butterknife.Bind;

public class LoginActivity extends Base1Activity implements View.OnClickListener {

    @Bind(R.id.et_username)
    ClearEditText EditTextName;
    @Bind(R.id.et_pwd)
    ClearEditText EditTextPwd;
    @Bind(R.id.iv_hide)
    ImageView ImageViewHind;
    @Bind(R.id.btn_login)
    Button buttonSure;
    @Bind(R.id.tv_foget)
    TextView TextViewForget;
    private boolean isHidden=false;

    @Override
    public int getContentView() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        ImageViewHind.setOnClickListener(this);
        buttonSure.setOnClickListener(this);
        TextViewForget.setOnClickListener(this);
        EditTextPwd.addTextChangedListener(textWatcher);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_hide://隐藏与可见
                if (isHidden) {
                    //设置EditText文本为可见的
                    EditTextPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ImageViewHind.setImageResource(R.drawable.ic_see);
                } else {
                    //设置EditText文本为隐藏的
                    ImageViewHind.setImageResource(R.drawable.ic_hint);
                    EditTextPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                EditTextPwd.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = EditTextPwd.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.btn_login://登录
                String phone = EditTextName.getText().toString().trim();
                String pwd = EditTextPwd.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    MyApplication.showToast("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(pwd)){
                    MyApplication.showToast("请输入密码");
                    return;
                }

                loginAuthority(phone,pwd);
                break;
            case R.id.tv_foget://忘记密码
                MyApplication.openActivity(mContext,ForgetPwdActivity.class);
                break;

        }
    }

    //    username	string	是	账号
//    password	string	是	密码
//    mobileLogin	string	是	true
//    app	string	是	true
//    imei	string	是	手机唯一识别号
//    registrationID	string	是	极光推送id
    private void login(String userName, String psw) {
        MyParams params = new MyParams();
        params.put("username", userName);
        params.put("password", psw);

        params.put("mobileLogin", "true");
        params.put("app", "true");
        params.put("imei", AppUtil.getPhoneImei(mContext));
        params.put("registrationID", "");
        VictorHttpUtil.doPost(false, mContext, Define.LOGIN_URL, params, true, "获取中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);

                        SharedPreferencesHelper.getInstance().putBoolean(AppSpContact.SP_FIRST_LAUCH,true);

                        LoginBean loginBean = JSON.parseObject(element.rows,LoginBean.class);
                        SharedPreferencesHelper.getInstance().putString(AppSpContact.USER_UUID,loginBean.getNo());
                        SharedPreferencesHelper.getInstance().putString(AppSpContact.USER_ID,loginBean.getId());
                        //用户名
                        SharedPreferencesHelper.getInstance().putString(AppSpContact.USER_NAME,loginBean.getName());
                        //头像
                        SharedPreferencesHelper.getInstance().putString(AppSpContact.USER_AVATAR,loginBean.getPhoto());
                        //手机号码
                        SharedPreferencesHelper.getInstance().putString(AppSpContact.USER_PHONE,loginBean.getMobile());


                        MyApplication.openActivity(mContext,MainActivity.class);
                        finish();
                    }
                });
    }

    private void loginAuthority(final String phone,final String pwd){
        new TedPermission(MyApplication.CONTEXT)
                .setPermissions(Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setDeniedMessage("请在设置中允许本应用使用手机状态权限")
                .setDeniedCloseButtonText("取消")
                .setGotoSettingButtonText("设置")
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        login(phone,pwd);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                    }
                }).check();
    }

    TextWatcher textWatcher = new TextWatcher() {
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
    };


}
