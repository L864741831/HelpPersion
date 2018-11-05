package com.tianjistar.helppersion.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;

/**
 * 忘记支付密码之输入个人信息
 * */
public class ForgetPayPwdActivity extends Base1Activity implements View.OnClickListener{

    @Bind(R.id.et_forgetpaypwd_phone)
    EditText editTextPhone;
    @Bind(R.id.et_forgetpaypwd_name)
    EditText editTextName;
    @Bind(R.id.et_forgetpaypwd_num)
    EditText editTextNo;
    @Bind(R.id.btn_next)
    Button buttonNext;

    @Override
    public int getContentView() {
        return R.layout.activity_forget_pay_pwd;
    }

    @Override
    public void initView() {
        setTitle("忘记支付密码");
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        buttonNext.setOnClickListener(this);
        editTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()>0&&!editable.toString().contains(" ")){
                    buttonNext.setAlpha(1);
                }else{
                    buttonNext.setAlpha((float) 0.5);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                checkInfo();
                break;
        }
    }

    private void checkInfo() {
        String name = editTextName.getText().toString().trim();
        String no = editTextNo.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            MyApplication.showToast("请输入完整的姓名");
            return;
        }
        if (TextUtils.isEmpty(no)){
            MyApplication.showToast("请输入证件号");
            return;
        }
        if (TextUtils.isEmpty(phone)){
            MyApplication.showToast("请输入银行预留手机号");
            return;
        }

        submitData(name,no,phone);
    }


//    name	string	是	姓名
//    idNumber	string	是	身份证号
//    mobile	string	是	手机号

//    app	string	是	yes
//    uuid	string	是	用uuid
//    imei		是	手机唯一识别号
    private void submitData(String name ,String no,final String mobile){
        MyParams params = new MyParams();
        params.put("uuid", SharedPreferencesHelper.getInstance().getString(AppSpContact.USER_UUID));
        params.put("imei", AppUtil.getPhoneImei(mContext));
        params.put("app", "yes");

        params.put("name",name);
        params.put("idNumber",no);
        params.put("mobile",mobile);

        VictorHttpUtil.doPost(false, mContext, Define.CHECK_PERSION_INFO_URL, params, true, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);
                        MyApplication.showToast(element.msg);

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(element.rows);
                            String token = jsonObject.getString("token");
                            Intent intent = new Intent(mContext,FogetPayPwdYzmActivity.class);
                            intent.putExtra("token",token);
                            intent.putExtra("phone",mobile);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


}
