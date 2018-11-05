package com.tianjistar.helppersion.activity.persion;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.tianjistar.helppersion.bean.UserInfoBean;
import com.tianjistar.helppersion.utils.AppUtil;
import com.tianjistar.helppersion.utils.SharedPreferencesHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.Bind;

/**
 * 我的钱包
 */
public class MineWalletctivity extends Base1Activity implements View.OnClickListener{

    @Bind(R.id.topbar_right)
    TextView topbar_right;

    @Bind(R.id.tv_money)
    TextView tv_money;

    @Bind(R.id.tv_content)
    TextView tv_content;
    //去查看
    @Bind(R.id.tv_look)
    TextView textViewLook;

    @Bind(R.id.tv_pay_money)
    TextView tv_pay_money;

    private String money = "",mBank,mContent,mBankcard;
    //是否綁定過卡
    private String mIsBinding = "1";


    @Override
    public int getContentView() {
        return R.layout.activity_mine_walletctivity;
    }

    @Override
    public void initView() {
        setTitle("我的钱包");
        topbar_right.setText("银行卡");
    }

    @Override
    public void initData() {
        textViewLook.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        textViewLook.getPaint().setAntiAlias(true);//抗锯齿
        getData();
    }

    @Override
    public void initListener() {
        topbar_right.setOnClickListener(this);
        textViewLook.setOnClickListener(this);
        tv_pay_money.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.topbar_right:
//                if ("0".equals(mIsBinding)){
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("type",2);
//                    MyApplication.openActivity(mContext,AddCardActivity.class,bundle);
//                }else if ("1".equals(mIsBinding)){
//                    MyApplication.openActivity(mContext,BankCardActivity.class);
//                }
                MyApplication.openActivity(mContext,BankCardActivity.class);
                break;
            case R.id.tv_look:
                break;
            case R.id.tv_pay_money:
//                if ("0".equals(mIsBinding)){
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("type",2);
//                    MyApplication.openActivity(mContext,AddCardActivity.class,bundle);
//                }else if ("1".equals(mIsBinding)){
//                    MyApplication.openActivity(mContext,WithdrawActivity.class);
//                }

                Bundle bundle = new Bundle();
                bundle.putString("money","100.00");
                bundle.putString("bank","中国银行");
                bundle.putString("bank_no","123456789123456");
//                bundle.putString("money",money);
//                bundle.putString("bank",mBank);
//                bundle.putString("bank_no",mBankcard);
                MyApplication.openActivity(mContext,WithdrawActivity.class,bundle);
                break;
        }
    }

    //    app	string	是	yes
//    uuid	string	是	uuid
//    imei	string	是	手机唯一识别
    private void getData() {
        MyParams params = new MyParams();
        params.put("uuid", SharedPreferencesHelper.getInstance().getString(AppSpContact.USER_UUID));
        params.put("imei", AppUtil.getPhoneImei(mContext));
        params.put("app","yes");
        VictorHttpUtil.doPost(false, mContext, Define.CHECK_MONEY_URL, params, false, "",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);

                    // {"msg":"成功","code":"2","rows":{"bank":"","isBinding":"0","money":"0.00","content":"无消息","bankcard":""}}
                        try {
                            JSONObject jsonObject = new JSONObject(element.rows);

                            money = jsonObject.getString("money");
                            mIsBinding = jsonObject.getString("isBinding");
                            mBank = jsonObject.getString("bank");
                            mContent = jsonObject.getString("content");
                            mBankcard = jsonObject.getString("bankcard");

                            tv_money.setText(money);
                            tv_content.setText(mContent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }


}
