package com.tianjistar.helppersion.activity.persion;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
 * 实名认证
 */
public class RealNameCheckActivity extends Base1Activity implements View.OnClickListener{

    @Bind(R.id.tv_topbar_title)
    TextView textViewTitle;
    @Bind(R.id.et_realname)
    EditText editTextName;
    @Bind(R.id.et_realname_num)
    EditText editTextNum;
    @Bind(R.id.btn_addcard_phone_next)
    Button buttonSure;

    private String state,actualName,idNumber;

    @Override
    public int getContentView() {
        return R.layout.activity_real_name_check;
    }

    @Override
    public void initView() {
        textViewTitle.setText("实名认证");
        editTextName.addTextChangedListener(editNameWatcher);
        editTextNum.addTextChangedListener(editIdWatcher);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent!=null){
            state = intent.getStringExtra("state");
            actualName = intent.getStringExtra("name");
            idNumber = intent.getStringExtra("id");

            if ("1".equals(state)){
                editTextName.setText(actualName);
                editTextNum.setText(idNumber);
                editTextName.setEnabled(false);
                editTextNum.setEnabled(false);
                buttonSure.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void initListener() {
        buttonSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_addcard_phone_next:
                realName();
                break;
        }
    }

    TextWatcher editNameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int i1, int i2) {
            if (s.toString().contains(" ")) {
                String[] str = s.toString().split(" ");
                String str1 = "";
                for (int i = 0; i < str.length; i++) {
                    str1 += str[i];
                }
                editTextName.setText(str1);
                editTextName.setSelection(start);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 0 && !editable.toString().contains(" ")){
                buttonSure.setAlpha(1);
            }else{
                buttonSure.setAlpha((float) 0.5);
            }
        }
    };

    TextWatcher editIdWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int i1, int i2) {
            if (s.toString().contains(" ")) {
                String[] str = s.toString().split(" ");
                String str1 = "";
                for (int i = 0; i < str.length; i++) {
                    str1 += str[i];
                }
                editTextNum.setText(str1);
                editTextNum.setSelection(start);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private void realName(){
        String name = editTextName.getText().toString().trim();
        String number = editTextNum.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            MyApplication.showToast("请输入您的真实姓名");
            return;
        }
        if (TextUtils.isEmpty(number)){
            MyApplication.showToast("请输入您的身份证号");
            return;
        }


//        idNumber	string	是	身份证号
//        actualName	string	是	真实名字
//        app	string	是	yes
//        uuid	string	是	uuid
//        imei		是	手机唯一识别
        MyParams params = new MyParams();
        params.put("uuid", SharedPreferencesHelper.getInstance().getString(AppSpContact.USER_UUID));
        params.put("imei", AppUtil.getPhoneImei(mContext));

        params.put("actualName", name);
        params.put("idNumber", number);
        params.put("app", "yes");

        VictorHttpUtil.doPost(false, mContext, Define.REAL_NAME_AUTHENTICATION_URL, params, true, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);

                        MyApplication.showToast(element.getMsg());
                        MyApplication.openActivity(mContext, RealNameSucceseActivity.class);
                        finish();
                    }
                });
    }

}
