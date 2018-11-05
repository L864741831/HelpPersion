package com.tianjistar.helppersion.fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.adapter.QuickAdapter;
import com.tianjistar.helppersion.api.Define;
import com.tianjistar.helppersion.api.Element;
import com.tianjistar.helppersion.api.MyParams;
import com.tianjistar.helppersion.api.RefreshLoadmoreCallbackListener;
import com.tianjistar.helppersion.api.VictorHttpUtil;
import com.tianjistar.helppersion.app.MyApplication;
import com.tianjistar.helppersion.base.BaseFragment;
import com.tianjistar.helppersion.bean.HelpBean;
import com.tianjistar.helppersion.db.DataBase;
import com.tianjistar.helppersion.utils.CollectionUtils;
import com.tianjistar.helppersion.utils.PtrHelper;
import com.tianjistar.helppersion.view.widget.SelectDialog2;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;


/**
 * 救援任务
 */
public class HelpFragment extends BaseFragment {

    private HelpAdapter mAdapter;
    private RecyclerView mRecyclerView;
    @Bind(R.id.pcfl)
    PtrClassicFrameLayout mPtrFrame;
    private List<HelpBean> mData;
    private PtrHelper<HelpBean> mPtrHelper;
    private String type;
    private int state;//状态 1等待出发 2等待接单

    private List<String> mList ;

    @Override
    public int getContentView() {
        return R.layout.fragment_helpre;
    }

    @Override
    public void initView() {
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_wishes_list);
    }

    @Override
    public void initData() {
        mData = new ArrayList<>();
        mList = new ArrayList<>();
        String[] channel = {"娱乐", "财经", "军事", "足球", "篮球", "体育", "电影", "房产",
                "电台", "汽车", "手机", "时尚", "论坛", "彩票", "暴雪", "社会社会", "情感", "教育", "家居", "游戏",
                "科技", "数码", "旅游", "周杰伦", "亲情", "段炼", "Android", "IOS", "移动", "星座", "风水"};

        for (int i = 0; i < channel.length; i++) {
            mList.add(channel[i]);
        }

        Bundle bundle = getArguments();
        if (bundle!=null){
            type = bundle.getString("type");
        }

        /****************** 设置XRecyclerView属性 **************************/
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));//设置布局管理器
//        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
//                .sizeResId(R.dimen.common_divider_dp)
//                .colorResId(R.color.divider)
//                .build());//添加分隔线
        mAdapter = new HelpAdapter(R.layout.item_helpfragment, mData);
        mRecyclerView.setAdapter(mAdapter);

        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mData);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多
        mPtrHelper.autoRefresh(true);
    }

    @Override
    public void initListener() {
        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                _reqData(pullToRefresh, curpage, pageSize);
            }
        });
    }

    private void _reqData(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
        params.put("page", curpage);
        params.put("userid","Z9o%2FLfQxDLmubr2oPTcscLavVLXNnZYXt2JzaFzVs%2BhojL0DZEElxwEONeKNdRKFoIAcvqp5Avt6%0APs3BFsy47xFu9DhUwuwvp66kRbRh1cRiJrGaH9h1qu9EYXAMAiN1H75qJ24foUn4%2FqqIj83B3di2%0ARzjbIym47kqk%2FkmK%2B61fAKxkPqDtF1PcqyBmmg9oqvVxgZfY7cVmivmKYlBLXvnbYJaLBfP96kaG%0Aue7m13TcuvnrBo5S9xkSP%2BN5AV6PAybOaKGOoIm%2FQrKHASlsVo7Y2dR7DYPTT8BelM%2BzSLxGgzWn%0AqjxYgqLRA7%2F5nRXZid7%2FeJJxka%2FHUIinlLIx4g%3D%3D%0A");
        params.put("imei","867007034124682");

        Log.i("info",type+"---params-2--"+params);
        // TODO: 2017/11/16
        VictorHttpUtil.doPost(false, getActivity(), Define.URL_wishes, params, false, null,
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper)  {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        Log.i("info",url+"---callbackSuccess---"+element.toString());
                        List<HelpBean> temp = JSON.parseArray(element.data, HelpBean.class);
                        if (pullToRefresh) {// 下拉刷新
                            mData.clear();//清空数据
                            mAdapter.setNewData(mData);
                            mAdapter.notifyDataSetChanged();
                            if (CollectionUtils.isEmpty(temp)) {
                                // 无数据
                                View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                                TextView textView=common_no_data.findViewById(R.id.tv_tip);
                                textView.setText("您还没有相关救援");
                                mPtrHelper.setEmptyView(common_no_data);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mData.addAll(temp);
                                mAdapter.setNewData(mData);
                                mAdapter.notifyDataSetChanged();
                                if (CollectionUtils.getSize(temp) < pageSize) {
                                    // 上拉加载无更多数据
                                    mPtrHelper.loadMoreEnd();
                                }
                            }
                            mPtrHelper.refreshComplete();
                        } else {// 加载更多
                            mPtrHelper.processLoadMore(temp);
                        }
                    }
                });
    }

    /**
     * 适配器
     */
    private class HelpAdapter extends QuickAdapter<HelpBean> {

        public HelpAdapter(int layoutResId, List<HelpBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final HelpBean item) {
            TextView textView=helper.getView(R.id.tv_type1);
            TextView textViewType1=helper.getView(R.id.tv_tv1);
            TextView textViewType2=helper.getView(R.id.tv_tv2);

            TextView tv_no = helper.getView(R.id.tv_no1);
            if ("接单".equals(type)&&state==1){
                textView.setText("等待出发");
                textViewType1.setVisibility(View.GONE);
                textViewType2.setVisibility(View.VISIBLE);
                textViewType2.setText("确认出发");
            }else if ("接单".equals(type)&&state==2){
                textView.setText("等待接单");
                textViewType1.setVisibility(View.GONE);
                textViewType2.setVisibility(View.VISIBLE);
                textViewType2.setText("确认接单");
            }else if ("施救中".equals(type)){
                textView.setText("施救中");
            }else if ("救援途中".equals(type)){
                textView.setText("已到达救援地点");
                textViewType1.setVisibility(View.VISIBLE);
                textViewType2.setVisibility(View.VISIBLE);
                textViewType1.setText("拍摄");
                textViewType2.setText("确认到达");
            }else if ("已完成".equals(type)){
                textView.setText("完成");
                textView.setText("已到达救援地点");
                textViewType1.setVisibility(View.VISIBLE);
                textViewType2.setVisibility(View.VISIBLE);
                textViewType1.setText("上传报告");
                textViewType2.setText("上传回访");
            }

            tv_no.setText(item.getTitle());

            //查看地图
            helper.getView(R.id.tv_map).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyApplication.showToast("查看地图");
                }
            });

            //
            helper.getView(R.id.tv_tv2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final SelectDialog2 selectDialog= new SelectDialog2(mContext,mList);
//                    selectDialog.setTitle("提示");
//                    selectDialog.setMessage("确定退出应用?");
                    selectDialog.setYesOnclickListener(new SelectDialog2.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            Toast.makeText(mActivity,"点击了--确定--按钮",Toast.LENGTH_LONG).show();

                            List<String> mList = getDataInfo();
                            if (mList.size()>0){
                                for (int i = 0; i < mList.size(); i++) {
                                    Log.i("info",type+"---mList-2--"+mList.get(i));
                                }
                            }else {
                                Toast.makeText(mActivity,"没有数据",Toast.LENGTH_LONG).show();
                            }

                            DataBase.getInstances(mContext).deleteTable();
                            selectDialog.dismiss();
                        }
                    });
                    selectDialog.setNoOnclickListener(new SelectDialog2.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            Toast.makeText(mActivity,"点击了--取消--按钮", Toast.LENGTH_LONG).show();

                            selectDialog.dismiss();
                        }
                    });

                    selectDialog.setOnSelectclickListener(new SelectDialog2.onSelectclickListener() {
                        @Override
                        public void onSelectClick(String type) {
                            Log.i("info","--onSelectClick--"+type);
                        }
                    });
                    selectDialog.show();
                }
            });

        }
    }



    /**
     * 通过查找数据库,拿到里面的数据
     */
    public List<String> getDataInfo() {
        List<String> list = new ArrayList<>();
        Cursor query = DataBase.getInstances(mContext).query();
        if (query.moveToFirst()) {
            do {
                String channel = query.getString(query.getColumnIndex("channel"));
                list.add(channel);
            } while (query.moveToNext());
        }
        //关闭查询游标
        query.close();
        return list;
    }

}
