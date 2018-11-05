//package com.tianjistar.helppersion.adapter;
//
//import android.view.View;
//import android.widget.TextView;
//
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.tianjistar.helppersion.R;
//import com.tianjistar.helppersion.bean.HelpBean;
//
//import java.util.List;
//
///**
// * Created by Administrator on 2018/4/19.
// */
//
//public class HelpAdapter extends QuickAdapter<HelpBean> {
//
//    private String mType;
//
//    public HelpAdapter(int layoutResId, List<HelpBean> data,String type) {
//        super(layoutResId, data);
//        this.mType = type;
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, final HelpBean item) {
//        TextView textView=helper.getView(R.id.tv_type1);
//        TextView textViewType1=helper.getView(R.id.tv_tv1);
//        TextView textViewType2=helper.getView(R.id.tv_tv2);
//        if ("接单".equals(mType) && state == 1){
//            textView.setText("等待出发");
//            textViewType1.setVisibility(View.GONE);
//            textViewType2.setVisibility(View.VISIBLE);
//            textViewType2.setText("确认出发");
//        }else if ("接单".equals(mType)&&state==2){
//            textView.setText("等待接单");
//            textViewType1.setVisibility(View.GONE);
//            textViewType2.setVisibility(View.VISIBLE);
//            textViewType2.setText("确认接单");
//        }else if ("施救中".equals(mType)){
//            textView.setText("施救中");
//        }else if ("救援途中".equals(mType)){
//            textView.setText("已到达救援地点");
//            textViewType1.setVisibility(View.VISIBLE);
//            textViewType2.setVisibility(View.VISIBLE);
//            textViewType1.setText("拍摄");
//            textViewType2.setText("确认到达");
//        }else if ("已完成".equals(mType)){
//            textView.setText("完成");
//            textView.setText("已到达救援地点");
//            textViewType1.setVisibility(View.VISIBLE);
//            textViewType2.setVisibility(View.VISIBLE);
//            textViewType1.setText("上传报告");
//            textViewType2.setText("上传回访");
//        }
//
//        //查看地图
//        helper.getView(R.id.tv_map).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//    }
//
//
//
//}
