package com.tianjistar.helppersion.activity.persion;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tianjistar.helppersion.R;
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

import butterknife.Bind;

/**
 * 设置支付密码
 */
public class SetNewPayPwdActivity extends Base1Activity implements View.OnClickListener{

    @Bind(R.id.tv_topbar_title)
    TextView textViewTitle;
    @Bind(R.id.bt_addcardpwd_next)
    Button buttonNext;
    @Bind(R.id.et_addcardpwd_pwd)
    EditText editTextPwd;

    @Bind(R.id.tv_pay_check_hint)
    TextView tv_pay_check_hint;
    private String newPsw,newPsw2;
    private String mToken;

    @Override
    public int getContentView() {
        return R.layout.activity_add_card_input_pwd;
    }

    @Override
    public void initView() {
        setTitle("设置支付密码");
        tv_pay_check_hint.setText("请设置天佑新支付密码");
        buttonNext.setText("下一步");
    }

    @Override
    public void initData() {
        mToken = getIntent().getStringExtra("token");
    }

    @Override
    public void initListener() {
        buttonNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_addcardpwd_next:
                if ("下一步".equals(buttonNext.getText().toString())){
                    if (TextUtils.isEmpty(editTextPwd.getText().toString().trim())){
                        MyApplication.showToast("请输入您的天机卡支付密码");
                        return;
                    }
                    next();
                }else if ("确认修改".equals(buttonNext.getText().toString())){
                    if (TextUtils.isEmpty(editTextPwd.getText().toString().trim())){
                        MyApplication.showToast("请输入您的天机卡支付密码");
                        return;
                    }

                    if (!newPsw.equals(editTextPwd.getText().toString().trim())){
                        MyApplication.showToast("和上次输入不一致，请重新输入");
                        return;
                    }
                    submit();
                }
                break;
        }
    }

    private void next(){
        newPsw = editTextPwd.getText().toString().trim();
        tv_pay_check_hint.setText("请再次填写确认天佑支付密码");
        editTextPwd.setText("");
        editTextPwd.setHint("请您输入天机卡支付密码");
        buttonNext.setText("确认修改");
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
                        Intent intent = new Intent(mContext,SuccessActivity.class);
                        intent.putExtra("type","2");
                        startActivity(intent);
                        finish();
                    }
                });
    }


}
