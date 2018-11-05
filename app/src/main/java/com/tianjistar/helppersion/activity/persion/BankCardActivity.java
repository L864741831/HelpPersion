package com.tianjistar.helppersion.activity.persion;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;
import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.api.BaseHttpCallbackListener;
import com.tianjistar.helppersion.api.Define;
import com.tianjistar.helppersion.api.Element;
import com.tianjistar.helppersion.api.MyParams;
import com.tianjistar.helppersion.api.VictorHttpUtil;
import com.tianjistar.helppersion.app.MyApplication;
import com.tianjistar.helppersion.base.Base1Activity;
import com.tianjistar.helppersion.bean.BankDetailBean;
import com.tianjistar.helppersion.utils.FlockUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * 銀行卡
 */
public class BankCardActivity extends Base1Activity implements View.OnClickListener {

    @Bind(R.id.tv_topbar_title)
    TextView textViewTitle;
    @Bind(R.id.iv_bank)
    ImageView imageViewBank;
    @Bind(R.id.iv_logo)
    ImageView imageViewLogo;
    @Bind(R.id.tv_bank_type)
    TextView textViewBankType;//银行
    @Bind(R.id.tv_bank_type_detail)
    TextView textViewCardType;//卡类型
    @Bind(R.id.tv_bank_card_num)
    TextView textViewCardNum;//卡号
    @Bind(R.id.tv_bank_card_jiebang)
    TextView textViewJieBang;//解绑
    @Bind(R.id.rl_change_card)
    RelativeLayout relativeLayoutChangeCard;//更换银行卡

    List<BankDetailBean> mData = new ArrayList<>();
    String num;


    @Override
    public int getContentView() {
        return R.layout.activity_bank;
    }

    @Override
    public void initView() {
        textViewTitle.setText("银行卡");
        getBankData();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        textViewJieBang.setOnClickListener(this);
        relativeLayoutChangeCard.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_bank_card_jiebang://解绑
                Bundle bundle=new Bundle();
                bundle.putString("type","1");
                bundle.putString("bankcard",num);
                MyApplication.openActivity(mContext,AddCardInputPwdActivity.class,bundle);
                finish();
                break;
            case R.id.rl_change_card://更换银行卡
                MyApplication.showToast("请先解绑您绑定的银行卡");
                break;
        }
    }

    private void getBankData() {
        MyParams params = new MyParams();
        VictorHttpUtil.doPost(false, mContext, Define.GET_BANK_INFO_URL, params, true, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);

                        List<BankDetailBean> bankDetailBean = JSON.parseArray(element.getData(),BankDetailBean.class);
                        if (bankDetailBean.size()>0){
                            mData.clear();
                            mData.addAll(bankDetailBean);

                            num = FlockUtil.jiemi(mData.get(0).getBankcard());

                            if (num.length()>4){
                                textViewCardNum.setText(num.substring(num.length()-4,num.length()));
                            }

                            textViewBankType.setText(mData.get(0).getCard_name());
                            textViewCardType.setText(mData.get(0).getCard_type());
                            if (!TextUtils.isEmpty(mData.get(0).getBank_photo())){
                                Picasso.with(mContext).load(mData.get(0).getBank_backGround()).into(imageViewBank);
                                Picasso.with(mContext).load(mData.get(0).getBank_log()).into(imageViewLogo);
                            }
                        }
                    }
                });
    }

}
