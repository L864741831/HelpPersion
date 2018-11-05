package com.tianjistar.helppersion.adapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;
import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.activity.persion.MemberInfoActivity;
import com.tianjistar.helppersion.app.MyApplication;
import com.tianjistar.helppersion.bean.ContactsBean;
import com.tianjistar.helppersion.utils.FlockUtil;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by lichaofan .
 * Date: 2017-9-20
 */
public class ContactsAdapter extends QuickAdapter<ContactsBean> {
    private List<ContactsBean> data=new ArrayList<>();

    public ContactsAdapter(int layoutResId, List<ContactsBean> data) {
        super(layoutResId, data);
        this.data=data;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder helper, final int positions) {
        final ContactsBean cityBean = data.get(positions);
        if (cityBean != null) {
            ImageView imageViewType=helper.getView(R.id.iv_head);
            ImageView imageViewPhone=helper.getView(R.id.iv_phone);
            RelativeLayout relativeLayout=helper.getView(R.id.rl_member);
            helper.setText(R.id.tv_name, FlockUtil.jiemi(cityBean.getUsername()));
            Picasso.with(mContext).load(cityBean.getPhoto())
                    .error(R.drawable.ic_head_defout)
                    .placeholder(R.drawable.ic_head_defout)
                    .into((ImageView) helper.getView(R.id.iv_head));
            imageViewPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent Intent =  new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:" + "15738776423"));//跳转到拨号界面，同时传递电话号码
                    mContext.startActivity(Intent);
                }
            });
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle=new Bundle();
                    bundle.putString("id",cityBean.getUserid());
                    MyApplication.openActivity(mContext, MemberInfoActivity.class,bundle);
                }
            });

        }
    }
    @Override
    protected void convert(BaseViewHolder helper, ContactsBean item) {

    }




}
