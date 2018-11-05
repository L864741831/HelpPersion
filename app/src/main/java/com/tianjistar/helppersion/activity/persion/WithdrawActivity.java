package com.tianjistar.helppersion.activity.persion;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;
import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.activity.ForgetPayPwdActivity;
import com.tianjistar.helppersion.activity.ForgetPwdActivity;
import com.tianjistar.helppersion.api.BaseHttpCallbackListener;
import com.tianjistar.helppersion.api.Define;
import com.tianjistar.helppersion.api.Element;
import com.tianjistar.helppersion.api.MyParams;
import com.tianjistar.helppersion.api.VictorHttpUtil;
import com.tianjistar.helppersion.app.AppSpContact;
import com.tianjistar.helppersion.app.MyApplication;
import com.tianjistar.helppersion.base.Base1Activity;
import com.tianjistar.helppersion.bean.BankDetailBean;
import com.tianjistar.helppersion.utils.AppUtil;
import com.tianjistar.helppersion.utils.DisplayUtil;
import com.tianjistar.helppersion.utils.FlockUtil;
import com.tianjistar.helppersion.utils.SharedPreferencesHelper;
import com.tianjistar.helppersion.view.widget.ClearEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;

/**
 * 提現
 */
public class WithdrawActivity extends Base1Activity implements View.OnClickListener {

    @Bind(R.id.tv_topbar_title)
    TextView textViewTitle;
    @Bind(R.id.tv_withdraw_bank)
    TextView textViewBank;
    @Bind(R.id.tv_withdraw_money)
    TextView textViewMoney;
    @Bind(R.id.tv_withdraw_tixian)
    TextView textViewTiXian;
    @Bind(R.id.btn_withdraw_sure)
    Button buttonSure;
    @Bind(R.id.et_money)
    ClearEditText inputMoney;
    //    double money;

    //银行名字，银行卡号
    private String bankName,bankcardNum;

    //    private RedWardsDialog redWardsDialog;//输入密码对话框

//    private EditText edinputpwd;
    private String num;

    private String money;

    @Override
    public int getContentView() {
        return R.layout.activity_withdraw;
    }

    @Override
    public void initView() {
        textViewTitle.setText("提现申请");
//        EdittextUtil.showSoftInputFromWindow(WithdrawActivity.this,inputMoney);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent!=null){
            money =  intent.getStringExtra("money");
            String mBank =  intent.getStringExtra("bank");
            String mBank_no =  intent.getStringExtra("bank_no");

            textViewBank.setText(mBank+
                    "("+mBank_no.substring(mBank_no.length()-4,mBank_no.length())+")");
            textViewMoney.setText("可用余额"+money+"元");
        }

        //获取余额
//        getData();

        //获取银行卡信息
//        getBankmessage();
    }

    @Override
    public void initListener() {
        buttonSure.setOnClickListener(this);
        textViewTiXian.setOnClickListener(this);
        inputMoney.addTextChangedListener(textWatcher);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_withdraw_sure://确定
                //先去支付密码
                num = inputMoney.getText().toString().trim();
                if (TextUtils.isEmpty(num)){
                    MyApplication.showToast("请输入金额");
                    return;
                }
                if (Double.parseDouble(num)>Double.parseDouble(money)){
                    MyApplication.showToast("您的余额不足");
                    return;
                }

                payPswDialog();
                break;
            case R.id.tv_withdraw_tixian://全部提现
                inputMoney.setText(money);
                break;
        }
    }

    /**
     * 提现
     */
//    applyMoney	string	是	金额
//    bankName	string	是	银行名字
//    bankCard	string	是	卡号
//    token		是	token
    private void tixian(String mToken) {
        MyParams params=new MyParams();
        params.put("bankName",bankName);//银行名字
        params.put("bankCard",bankcardNum);//银行卡号
        params.put("applyMoney",num);//提现金额
        params.put("token",mToken);

        VictorHttpUtil.doPost(false, mContext, Define.GET_MONET_URL, params, true, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);
                        MyApplication.showToast(element.msg);

                        Bundle bundle=new Bundle();
                        bundle.putString("type","提现成功");
                        MyApplication.openActivity(mContext,SuccessActivity.class,bundle);
                        finish();
                    }
                });
    }

    private PopupWindow popupWindow;
    /**
     * 弹出输入支付密码
     */
    private void payPswDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popwin_pwd, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        DisplayUtil.setAlpha(WithdrawActivity.this, 0.5f);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DisplayUtil.setAlpha(WithdrawActivity.this, 1f);
            }
        });

        TextView textView = view.findViewById(R.id.tv_foget_pwd);
        ImageView ivCancel = view.findViewById(R.id.iv_close);
        Button sure = view.findViewById(R.id.btn_pwd_sure);

        final EditText clearEditText = view.findViewById(R.id.ce_pwd);
        clearEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        clearEditText.setFocusable(true);

        InputMethodManager imm = (InputMethodManager)WithdrawActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);    //InputMethodManager.SHOW_FORCED

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.openActivity(mContext, ForgetPayPwdActivity.class);
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2018/3/21 验证支付密码通知后台 即提现申请
                setPswPass(clearEditText);
            }
        });

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }

    private void setPswPass(EditText clearEditText) {
        String payPsw = clearEditText.getText().toString();
        if (TextUtils.isEmpty(payPsw)){
            MyApplication.showToast("请输入您的支付密码");
            return ;
        }

        MyParams params = new MyParams();
        params.put("uuid", SharedPreferencesHelper.getInstance().getString(AppSpContact.USER_UUID));
        params.put("imei", AppUtil.getPhoneImei(mContext));
        params.put("app", "yes");
        params.put("newpass",payPsw);

        VictorHttpUtil.doPost(false, mContext, Define.CHECK_PAY_IS_PASS_URL, params, true, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);

                        popupWindow.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(element.rows);
                            String token = jsonObject.getString("token");
                            tixian(token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }


    private void getData() {
//        MyParams params = new MyParams();
//        VictorHttpUtil.doPost(false, mContext, Define.FAMILY_URL, params, false, null,
//                new BaseHttpCallbackListener<Element>() {
//                    @Override
//                    public void callbackSuccess(String url, Element element) {
//                        super.callbackSuccess(url, element);
//                        try {
//                            JSONObject jsonObject = new JSONObject(element.getData());
//                            money = Double.parseDouble(jsonObject.optString("CurrentMoney").replaceAll(",","").trim());
//                            textViewMoney.setText("可用余额"+jsonObject.optString("CurrentMoney")+ "元");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
    }

    //获取银行卡信息
    private void getBankmessage() {
        MyParams params = new MyParams();
        VictorHttpUtil.doPost(false, mContext,Define.GET_BANK_INFO_URL, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);
                        List<BankDetailBean> bankDetailBeen = JSON.parseArray(element.getData(), BankDetailBean.class);
                        BankDetailBean bankDetailBean = bankDetailBeen.get(0);
                        bankName = bankDetailBean.getBank();
                        String cardnum = FlockUtil.jiemi(bankDetailBean.getBankcard());
                        textViewBank.setText(bankDetailBean.getCard_name()+
                                "("+cardnum.substring(cardnum.length()-4,cardnum.length())+")");
                        bankcardNum = FlockUtil.jiemi(bankDetailBean.getBankcard());
                    }
                });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {
            if (s.toString().contains(".")) {
                if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                    s = s.toString().subSequence(0,
                            s.toString().indexOf(".") + 3);
                    inputMoney.setText(s);
                    inputMoney.setSelection(s.length());
                }
            }
            if (s.toString().trim().substring(0).equals(".")) {
                s = "0" + s;
                inputMoney.setText(s);
                inputMoney.setSelection(2);
            }

            if (s.toString().startsWith("0")
                    && s.toString().trim().length() > 1) {
                if (!s.toString().substring(1, 2).equals(".")) {
                    inputMoney.setText(s.subSequence(0, 1));
                    inputMoney.setSelection(1);
                    return;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length()>0){
                buttonSure.setAlpha(1);
            }else{
                buttonSure.setAlpha((float) 0.5);
            }
        }
    };


}
