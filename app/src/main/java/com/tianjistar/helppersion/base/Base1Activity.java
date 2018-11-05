package com.tianjistar.helppersion.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.utils.AbActivityManager;
import com.tianjistar.helppersion.utils.StringUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;


public abstract class Base1Activity extends AutoLayoutActivity {

    protected Context mContext;
    protected Activity mActivity;
    public String TAG = Base1Activity.class.getSimpleName();
    //    public List<ContactsBean>beanList=new ArrayList<>();

    protected int screenWidth;// 屏幕宽度
    protected int screenHeight;// 屏幕高度
    protected float density;// 屏幕密度
    protected int densityDpi;// dpi

    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSIONS = 1;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 2;

    @Override
    public void setContentView(int layoutResID) {

        super.setContentView(layoutResID);
//        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
//            setStatusBarColor(R.color.white);
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.white));
        }
        //设置白底黑字
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        Window window = getWindow();
//默认API 最低19
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            ViewGroup contentView = window.getDecorView().findViewById(Window.ID_ANDROID_CONTENT);
//            contentView.getChildAt(0).setFitsSystemWindows(false);
//        }
        //StatusBarUtil.StatusBarLightMode(mActivity);
        // 绑定ButterKnife
        ButterKnife.bind(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   AbActivityManager.getInstance().addActivity(this);

        mContext = this;
        mActivity = this;
        TAG = getClass().getSimpleName();

        // 注册EventBus
        EventBus.getDefault().register(this);

        getWindowInfo();

        setContentView(getContentView());
        initView();
        initData();
        initListener();
    }

    /**
     * 获取内容布局视图
     */
    public abstract int getContentView();

    /**
     * 初始化布局，子类重写
     */
    public abstract void initView();

    public abstract void initData();

    public abstract void initListener();


    /**
     * 设置状态栏颜色
     *
     * @param colorResId
     */
    protected void setStatusBarColor(int colorResId) {
        // StatusBarCompat.setStatusBarColor(this, getResources().getColor(colorResId));
    }

    /**
     * 点击空白区域，关闭键
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null
                && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE)
                && view instanceof EditText
                && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 设置标题栏标题
     *
     * @param title
     */
    protected void setTitle(String title) {
        TextView tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        if (tv_topbar_title != null) {
            tv_topbar_title.setText(StringUtil.isEmpty(title) ? "" : title);
        }
    }

    /**
     * 标题栏的右边的完成按钮
     *
     * @param view
     */
    public void topbarDone(View view) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        // PgyCrashManager.unregister();
    }

    @Override
    protected void onDestroy() {
        // 取消注册EventBus
        EventBus.getDefault().unregister(this);

        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        // PgyCrashManager.unregister();

        super.onDestroy();
    }

    /**
     * 标题上的返回按钮
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }

    /**
     * 描述：Activity结束.
     *
     * @see Activity#finish()
     */
    @Override
    public void finish() {
        AbActivityManager.getInstance().removeActivity(this);
        super.finish();
    }

    /**
     * 关闭该窗口
     *
     * @param clazz
     */
    @Subscribe
    public void onCloseActivity(Class<?> clazz) {
        if (getClass().getSimpleName().equalsIgnoreCase(clazz.getSimpleName())) {
            finish();
        }
    }

    private void getWindowInfo() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels; // 屏幕宽度（像素）
        screenHeight = metric.heightPixels; // 屏幕高度（像素）
        density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
    }

}
