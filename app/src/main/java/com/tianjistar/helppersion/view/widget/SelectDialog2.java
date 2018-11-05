package com.tianjistar.helppersion.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.adapter.GridViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/20.
 *
 * https://blog.csdn.net/dl10210950/article/details/54847716
 */
public class SelectDialog2 extends Dialog {

    private Context mContext;
    private TextView tv_sure;//确定按钮
    private ImageView iv_cancel;//取消按钮

    private RadioGroup rg_task;

    private GridView gridView;
    private GridViewAdapter mGridViewAdapter;
    private List<String> mList = new ArrayList<>();

    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器
    private onSelectclickListener onSelectclickListener;//选择是否公开按钮

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener(onNoOnclickListener onNoOnclickListener) {
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(onYesOnclickListener onYesOnclickListener) {
        this.yesOnclickListener = onYesOnclickListener;
    }

    public void setOnSelectclickListener(onSelectclickListener onSelectclickListener) {
        this.onSelectclickListener = onSelectclickListener;
    }

    public SelectDialog2(Context context,List<String> list) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_dialog_layout2);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(true);

        //初始化界面控件
        initView();
        //
        initData();
        //初始化界面控件的事件
        initEvent();
    }

    private void initData() {
        mGridViewAdapter = new GridViewAdapter(mContext, mList);
        gridView.setAdapter(mGridViewAdapter);

        //GridView的item的点击事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //将当前点击的position传递过去做相应的状态改变
                mGridViewAdapter.choiceState(position);
            }
        });
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick();
                }
            }
        });

        rg_task.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (radioGroup.getId()) {
                    case R.id.rg_task:
                        if (onSelectclickListener != null) {
                            if (i == R.id.rb_no) {
                                onSelectclickListener.onSelectClick("2");
                            } else {
                                onSelectclickListener.onSelectClick("1");
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        tv_sure = findViewById(R.id.tv_sure);
        iv_cancel = findViewById(R.id.iv_cancel);
        gridView = findViewById(R.id.gridView);
        rg_task = findViewById(R.id.rg_task);
    }


    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        void onYesClick();
    }

    public interface onNoOnclickListener {
        void onNoClick();
    }

    public interface onSelectclickListener {
        void onSelectClick(String type);
    }
}
