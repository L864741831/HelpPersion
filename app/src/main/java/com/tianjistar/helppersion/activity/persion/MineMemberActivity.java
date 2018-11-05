package com.tianjistar.helppersion.activity.persion;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;
import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.adapter.ContactsAdapter;
import com.tianjistar.helppersion.api.BaseHttpCallbackListener;
import com.tianjistar.helppersion.api.Define;
import com.tianjistar.helppersion.api.Element;
import com.tianjistar.helppersion.api.MyParams;
import com.tianjistar.helppersion.api.VictorHttpUtil;
import com.tianjistar.helppersion.app.AppSpContact;
import com.tianjistar.helppersion.app.MyApplication;
import com.tianjistar.helppersion.base.Base1Activity;
import com.tianjistar.helppersion.bean.ContactsBean;
import com.tianjistar.helppersion.utils.AppUtil;
import com.tianjistar.helppersion.utils.CollectionUtils;
import com.tianjistar.helppersion.utils.SharedPreferencesHelper;
import com.tianjistar.helppersion.utils.ViewUtil;
import com.tianjistar.helppersion.utils.decoration.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 我的队员列表
 */
public class MineMemberActivity extends Base1Activity {

    @Bind(R.id.rv)
    RecyclerView mRv;

    private ContactsAdapter mAdapter;
    private LinearLayoutManager mManager;
    private List<ContactsBean> mDatas=new ArrayList<>();
    private SuspensionDecoration mDecoration;

    /**
     * 右侧边栏导航区域
     */
    private IndexBar mIndexBar;
    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;

    @Override
    public int getContentView() {
        return R.layout.activity_mine_member;
    }

    @Override
    public void initView() {
        setTitle("队员列表");
    }

    @Override
    public void initData() {
        mRv.setLayoutManager(mManager = new LinearLayoutManager(mActivity));//设置布局管理器
        mAdapter = new ContactsAdapter(R.layout.item_member, mDatas);
        mRv.setAdapter(mAdapter);
        mDecoration = new SuspensionDecoration(mActivity, mDatas);
        mDecoration.setColorTitleBg(mContext.getResources().getColor(R.color.color_bg));
        mDecoration.setColorTitleFont(mContext.getResources().getColor(R.color.color_title));
        mDecoration.setmTitleHeight(ViewUtil.dip2px(mContext,10));
        mDecoration.setTitleFontSize(ViewUtil.dip2px(mContext,3));
        mRv.addItemDecoration(mDecoration);
        //如果add两个，那么按照先后顺序，依次渲染。
        mRv.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST));
        //使用indexBar 大写的a提示器
        mTvSideBarHint = (TextView) findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = (IndexBar) findViewById(R.id.indexBar);//IndexBar
        mIndexBar.setVisibility(View.GONE);
        //indexbar初始化
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(false)//设置需要真实的索引
                .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                .setmSourceDatas(mDatas)//设置数据
        ;
        //获取好友列表
        getfrientslist();
    }

    @Override
    public void initListener() {
    }

    //    app	string	是	yes
//    uuid	string	是	uuid
//    imei	string	是	手机唯一识别号
    private void getfrientslist() {
        MyParams params = new MyParams();
        params.put("uuid", SharedPreferencesHelper.getInstance().getString(AppSpContact.USER_UUID));
        params.put("imei", AppUtil.getPhoneImei(mContext));
        params.put("app","yes");
        VictorHttpUtil.doPost(false, mContext, Define.ALL_HELP_PERSION_URL, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<ContactsBean> temp = JSON.parseArray(element.rows, ContactsBean.class);
                        mDatas.clear();//清空数据
                        if (!CollectionUtils.isEmpty(temp)) {
                            // 有数据
                            mDatas.addAll(temp);
                            mAdapter.setNewData(mDatas);
                            mAdapter.notifyDataSetChanged();
                            mIndexBar.setmSourceDatas(mDatas)//设置数据
                                    .invalidate();
                            mDecoration.setmDatas(mDatas);
                        } else {
                            MyApplication.showToast("您还没有好友");
                        }
                    }
                });
    }



}
