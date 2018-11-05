package com.tianjistar.helppersion.activity.persion;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.app.MyApplication;
import com.tianjistar.helppersion.base.Base1Activity;

import butterknife.Bind;

/**
 *添加银行卡
 */
public class AddCardActivity extends Base1Activity {

    @Bind(R.id.ll_add_bank_card)
    LinearLayout ll_add_bank_card;
    @Bind(R.id.tv_bank_name)
    TextView tv_bank_name;

    private int type;

    @Override
    public int getContentView() {
        return R.layout.activity_add_card;
    }

    @Override
    public void initView() {
        setTitle("添加银行卡");
    }

    @Override
    public void initData() {
        type = getIntent().getIntExtra("type",0);
        if (type==1){
            setTitle("添加银行卡");
        }else if (type==2){//提现的添加银行卡
            setTitle("提现申请");
        }
    }

    @Override
    public void initListener() {
        tv_bank_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type","2");
                MyApplication.openActivity(mContext,AddCardInputPwdActivity.class,bundle);
            }
        });
    }

}
