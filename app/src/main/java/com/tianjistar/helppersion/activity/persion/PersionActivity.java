package com.tianjistar.helppersion.activity.persion;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.app.AppSpContact;
import com.tianjistar.helppersion.app.MyApplication;
import com.tianjistar.helppersion.base.Base1Activity;
import com.tianjistar.helppersion.utils.PicassoUtils;
import com.tianjistar.helppersion.utils.SharedPreferencesHelper;
import com.tianjistar.helppersion.utils.StringUtils;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人中心
 * */
public class PersionActivity extends Base1Activity implements View.OnClickListener {

    @Bind(R.id.rl_persion_menber)
    RelativeLayout relativeLayoutMenber;//我的队员
    @Bind(R.id.rl_persion_money)
    RelativeLayout relativeLayoutMoney;//我的钱包
    @Bind(R.id.rl_persion_set)
    RelativeLayout relativeLayoutSet;//我的钱包

    @Bind(R.id.iv_persion_head)
    CircleImageView circleImageViewHead;
    @Bind(R.id.tv_persion_name)
    TextView tv_persion_name;

    @Bind(R.id.linearLayout)
    LinearLayout linearLayoutInfo;//基本信息
    @Bind(R.id.ll_persion_share)
    LinearLayout linearLayoutShare;

    private SharedPreferencesHelper preferencesHelper;

    @Override
    public int getContentView() {
        return R.layout.activity_persion;
    }

    @Override
    public void initView() {
        setTitle("个人中心");
    }

    @Override
    public void initData() {
        preferencesHelper = SharedPreferencesHelper.getInstance();
    }

    @Override
    public void initListener() {
        relativeLayoutMenber.setOnClickListener(this);
        relativeLayoutMoney.setOnClickListener(this);
        relativeLayoutSet.setOnClickListener(this);
        linearLayoutInfo.setOnClickListener(this);
        linearLayoutShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linearLayout://基本信息
                MyApplication.openActivity(mContext,PersionInfoActivity.class);
                break;
            case R.id.rl_persion_money://钱包
                MyApplication.openActivity(mContext,MineWalletctivity.class);
                break;
            case R.id.rl_persion_menber://队员
                MyApplication.openActivity(mContext,MineMemberActivity.class);
                break;
            case R.id.rl_persion_set://设置
                MyApplication.openActivity(mContext,SetActivity.class);
                break;
            case R.id.ll_persion_share://分享
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String userName =  preferencesHelper.getString(AppSpContact.USER_NAME);
        String userAvatar =  preferencesHelper.getString(AppSpContact.USER_AVATAR);
        if (StringUtils.isNotBlank(userAvatar)){
            PicassoUtils.loadHeadImage(mContext,userAvatar,circleImageViewHead);
        }
        if (StringUtils.isNotBlank(userName)){
            tv_persion_name.setText(userName);
        }

    }
}
