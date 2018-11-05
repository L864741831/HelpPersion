package com.tianjistar.helppersion.activity.persion;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.activity.ForgetPayPwdActivity;
import com.tianjistar.helppersion.app.MyApplication;
import com.tianjistar.helppersion.base.Base1Activity;

import butterknife.Bind;

/**
 * 修改支付密码
 * 忘记支付密码
 */
public class SetPayPswActivity extends Base1Activity implements View.OnClickListener{

    @Bind(R.id.rl_modify_psw)
    RelativeLayout rl_modify_psw;
    @Bind(R.id.rl_forget_psw)
    RelativeLayout rl_forget_psw;

    @Override
    public int getContentView() {
        return R.layout.activity_set_pay_psw;
    }

    @Override
    public void initView() {
        setTitle("支付密码");
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        rl_modify_psw.setOnClickListener(this);
        rl_forget_psw.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_modify_psw:
                Intent intent = new Intent(mContext,AddCardInputPwdActivity.class);
                intent.putExtra("type","3");
                startActivity(intent);
                break;
            case R.id.rl_forget_psw:
                MyApplication.openActivity(mContext,ForgetPayPwdActivity.class);
                break;
        }
    }


}
