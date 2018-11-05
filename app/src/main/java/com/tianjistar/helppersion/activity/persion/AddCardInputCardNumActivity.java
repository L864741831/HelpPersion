package com.tianjistar.helppersion.activity.persion;

import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.api.BaseHttpCallbackListener;
import com.tianjistar.helppersion.api.Define;
import com.tianjistar.helppersion.api.Element;
import com.tianjistar.helppersion.api.MyParams;
import com.tianjistar.helppersion.api.VictorHttpUtil;
import com.tianjistar.helppersion.app.AppSpContact;
import com.tianjistar.helppersion.app.MyApplication;
import com.tianjistar.helppersion.base.Base1Activity;
import com.tianjistar.helppersion.bean.RealNameInfo;
import com.tianjistar.helppersion.utils.FlockUtil;
import com.tianjistar.helppersion.utils.SharedPreferencesHelper;
import com.tianjistar.helppersion.utils.StringUtil;
import com.tianjistar.helppersion.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;

/**
 * 添加银行卡之输入卡号
 */
public class AddCardInputCardNumActivity extends Base1Activity {

    @Bind(R.id.tv_topbar_title)
    TextView textViewTitle;
    @Bind(R.id.btn_addcard_card_next)
    Button buttonNext;//下一步
    @Bind(R.id.tv_addcard_card_name)
    TextView textViewCardName;//持卡人
    @Bind(R.id.et_addcard_card_num)
    EditText editTextCardNum;//卡号
    @Bind(R.id.tv_addcard_phone_type)
    TextView textViewCardType;//卡类型
    @Bind(R.id.et_addcard_phone_num)
    EditText editTextPhone;//预留手机号
    String CardNum;//身份证号

    @Override
    public int getContentView() {
        return R.layout.activity_add_card_input_card_num;
    }

    @Override
    public void initView() {
        textViewTitle.setText("添加银行卡");
        //获取持卡人
        getNetdata();
        bankCardNumAddSpace(editTextCardNum);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        seteditText();
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editTextCardNum.getText().toString().trim())){
                    MyApplication.showToast("请输入您的的银行卡号");
                    editTextCardNum.requestFocus();
                    return;
                }
                if (Tools.checkBankCard(editTextCardNum.getText().toString().trim().replaceAll(" ",""))==false){
                    MyApplication.showToast("请输入有效的银行卡号");
                    editTextCardNum.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(editTextPhone.getText().toString().trim())){
                    MyApplication.showToast("请输入您的手机号");
                    editTextPhone.requestFocus();
                    return;
                }
                if (!StringUtil.isPhoneNumber(editTextPhone.getText().toString().trim())) {
                    MyApplication.showToast("手机号格式不正确");
                    editTextPhone.requestFocus();
                    return;
                }
                MyParams params = new MyParams();
//                params.put("uniqid", SharedPreferencesHelper.getInstance().getString(AppSpContact.UNIQID));
                params.put("imei", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_DEVICEID));
                params.put("userid", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_UID));
                params.put("mobile", FlockUtil.jiami(editTextPhone.getText().toString().trim()));
                params.put("bankcard", FlockUtil.jiami(editTextCardNum.getText().toString().trim().replaceAll(" ","")));
                params.put("identity",FlockUtil.jiami(CardNum));
                params.put("realName",FlockUtil.jiami(textViewCardName.getText().toString().trim()));
                VictorHttpUtil.doPost(false, mContext, Define.URL_add_bank, params, true, null,
                        new BaseHttpCallbackListener<Element>() {
                            @Override
                            public void callbackSuccess(String url, Element element) {
                                super.callbackSuccess(url, element);
                                MyApplication.showToast(element.getMsg());
                                Bundle bundle=new Bundle();
                                bundle.putString("phone",editTextPhone.getText().toString().trim());
                                bundle.putString("card",editTextCardNum.getText().toString().trim().replaceAll(" ",""));
                                MyApplication.openActivity(mContext,AddCardCheckYzmActivity.class,bundle);
                                finish();
                            }
                        });

            }
        });
    }

    private void seteditText() {
        editTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()>0){
                    buttonNext.setAlpha(1);
                }else{
                    buttonNext.setAlpha((float) 0.5);
                }
            }
        });
        editTextCardNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length()>0&& !TextUtils.isEmpty(editable)){
                    buttonNext.setAlpha(1);
                    getCardType();
                }else if (editable.length()<=0){
                    buttonNext.setAlpha((float) 0.5);
                }
            }
        });
//        editTextCardNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){//获取到焦点
//
//                }else{//失去焦点
//                    editTextPhone.requestFocus();
//                    getCardType();
//                }
//            }
//        });
    }

    //获取银行卡持卡人姓名
    private void getNetdata() {
        MyParams params = new MyParams();
        params.put("imei", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_DEVICEID));
        params.put("userid", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_UID));
        params.put("return_data", "1");
        VictorHttpUtil.doPost(false, mContext, Define.URL_if_real_name, params, true, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);
                        RealNameInfo info= JSON.parseObject(element.getData(),RealNameInfo.class);
                        if (info!=null&&info.getIs_realname().equals("1")){
                            String name= FlockUtil.jiemi(info.getRealname());
                            CardNum= FlockUtil.jiemi(info.getIdcard());
                            textViewCardName.setText(name);
                        }

                    }
                });
    }
    //银行卡号码的格式
    public void bankCardNumAddSpace(final EditText mEditText) {
        mEditText.addTextChangedListener(new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3
                        || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0){
                    buttonNext.setAlpha(1);
                }else{
                    buttonNext.setAlpha((float) 0.5);
                }
                if (isChanged) {
                    location = mEditText.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 4 || index == 9 || index == 14 || index == 19)) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }

                    mEditText.setText(str);
                    Editable etable = mEditText.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });
    }
    private void getCardType() {
        MyParams params=new MyParams();
        params.put("userid", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_UID));
        params.put("imei", SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_DEVICEID));
        params.put("card", FlockUtil.jiami(editTextCardNum.getText().toString().trim().replaceAll(" ","")));
        VictorHttpUtil.doPost(false, mContext, Define.URL_get_cardType, params, false, null, new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                super.callbackSuccess(url, element);
                try {
                    JSONObject json=new JSONObject(element.getData());
                    String type=json.optString("bankName");
                    textViewCardType.setText(type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

