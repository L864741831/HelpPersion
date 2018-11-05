package com.tianjistar.helppersion.activity.persion;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.base.Base1Activity;

import butterknife.Bind;

/**
 * 各种成功界面
 */
public class RealNameSucceseActivity extends Base1Activity {

    @Bind(R.id.tv_text)
    TextView tvText;
    @Bind(R.id.lin_band_bank)
    LinearLayout linBandBank;
    @Bind(R.id.tv_contacts)
    TextView tvContacts;
    @Bind(R.id.tv_bankcard)
    TextView tvBankcard;
    private String type;
    int isHaveCard;

    @Override
    public int getContentView() {
        return R.layout.activity_succese;
    }

    @Override
    public void initView() {
        /**
         * 去绑定银行卡
         */
        tvBankcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
//                bundle.putString("type", "2");
//                MyApplication.openActivity(mContext, AddCardInputPwdActivity.class, bundle);
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }


}
