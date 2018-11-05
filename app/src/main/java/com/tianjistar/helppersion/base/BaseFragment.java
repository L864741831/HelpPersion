package com.tianjistar.helppersion.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * 类说明:
 *
 * @author 作者 LUYA, E-mail: 468034043@qq.com
 * @version 创建时间：2015年12月2日 下午4:10:31
 */
public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected Activity mActivity;
    protected int curpage = 1;// 当前页码
    private View rootView;


    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSIONS = 1;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = getActivity();
        this.mContext = getActivity();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
//            Window window = getActivity().getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(R.color.theme_color));
//        }
        //   StatusBarUtil.StatusBarLightMode(mActivity);
        // 注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getContentView(), null);
        // AutoUtils.auto(rootView);// 结合AutoLayout
        // 绑定butterknife
        ButterKnife.bind(this, rootView);

        initView();
        initData();
        initListener();

        return rootView;
    }

    protected View findViewById(int id) {
        return rootView.findViewById(id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 解绑butterknife
        //  unbinder.unbind();
        ButterKnife.unbind(this);
    }

    /**
     * 获取内容布局视图
     *
     * @return
     */
    public abstract int getContentView();

    public abstract void initView();

    public abstract void initData();

    public abstract void initListener();

    @Subscribe
    public void onEventMainThread(String s) {

    }

    @Override
    public void onDestroy() {
        // 取消注册EventBus
        EventBus.getDefault().unregister(this);
        //  PgyCrashManager.unregister();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        // PgyCrashManager.unregister();
    }
}
