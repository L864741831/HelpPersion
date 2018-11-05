package com.tianjistar.helppersion.activity.persion;

import android.content.Intent;
import android.text.TextUtils;
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
import com.tianjistar.helppersion.utils.AppUtil;
import com.tianjistar.helppersion.utils.FlockUtil;
import com.tianjistar.helppersion.utils.SharedPreferencesHelper;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;


/**
 *
 */
public class AddCardInputPwdActivity extends Base1Activity {

    @Bind(R.id.tv_topbar_title)
    TextView textViewTitle;
    @Bind(R.id.bt_addcardpwd_next)
    Button buttonNext;
    @Bind(R.id.et_addcardpwd_pwd)
    EditText editTextPwd;

    @Bind(R.id.tv_pay_check_hint)
    TextView tv_pay_check_hint;

    private String request_type;
    String bankcard;
    private String type;

    @Override
    public int getContentView() {
        return R.layout.activity_add_card_input_pwd;
    }

    @Override
    public void initView() {
        setTitle("身份验证");
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent!=null){
            type = intent.getStringExtra("type");
            if ("1".equals(type)){//解绑银行卡
                textViewTitle.setText("解绑银行卡");
                bankcard=getIntent().getStringExtra("bankcard");
                request_type="1";
                buttonNext.setText("确认解绑");
            }else if("2".equals(type)){//添加银行卡
                textViewTitle.setText("添加银行卡");
                request_type="0";
                buttonNext.setText("下一步");
            }else if ("3".equals(type)){//修改支付密码验证
                textViewTitle.setText("修改支付密码");
                request_type="0";
                buttonNext.setText("下一步");
            }
        }
    }

    @Override
    public void initListener() {
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editTextPwd.getText().toString().trim())){
                    MyApplication.showToast("请输入您的天机卡支付密码");
                    return;
                }
                next();
            }
        });
    }


    //    newpass	string	是	支付密码
//    app	string	是	yes
//    uuid	string	是	用户uuid
//    imei	string	是	手机imei
    private void next(){
        MyParams params = new MyParams();
        params.put("uuid", SharedPreferencesHelper.getInstance().getString(AppSpContact.USER_UUID));
        params.put("imei", AppUtil.getPhoneImei(mContext));
        params.put("app", "yes");
        params.put("newpass",editTextPwd.getText().toString().trim());
        if ("1".equals(type)){
//            params.put("bankcard", FlockUtil.jiami(bankcard));
        }

        VictorHttpUtil.doPost(false, mContext, Define.CHECK_PAY_IS_PASS_URL, params, true, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);
                        MyApplication.showToast(element.msg);
                        if ("2".equals(type)){//绑卡
                            MyApplication.openActivity(mContext,AddCardInputCardNumActivity.class);
                        } else if ("1".equals(type)){//解卡

                        }else if ("3".equals(type)){//设置支付密码
                            try {
                                JSONObject jsonObject = new JSONObject(element.rows);
                                String token = jsonObject.getString("token");

                                Intent intent = new Intent(mContext,SetNewPayPwdActivity.class);
                                intent.putExtra("token",token);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        finish();
                    }
                });
    }


}
