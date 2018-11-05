package com.tianjistar.helppersion.activity.persion;

import android.widget.TextView;

import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.base.Base1Activity;

import butterknife.Bind;

/**
 * 绑卡成功页面
 * */
public class SuccessActivity extends Base1Activity {
    @Bind(R.id.tv_text)
    TextView textView;
    String type;

    @Override
    public int getContentView() {
        return R.layout.activity_success;
    }

    @Override
    public void initView() {
        type=getIntent().getStringExtra("type");
        if ("1".equals(type)){//银行卡绑定成功
            setTitle("添加银行卡");
            textView.setText("绑定成功！");
        }else if ("2".equals(type)){//修改支付密码
            setTitle("修改支付密码");
            textView.setText("修改成功！");
        }else if ("3".equals(type)){//忘记支付密码
            setTitle("忘记支付密码");
            textView.setText("设置成功！");
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
