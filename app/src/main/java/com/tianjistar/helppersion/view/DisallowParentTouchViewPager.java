package com.tianjistar.helppersion.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/4/21 0021 9:11.
 */

public class DisallowParentTouchViewPager extends ViewPager {

    private ViewGroup mView;
    public DisallowParentTouchViewPager(Context context) {
        super(context);
        initView();
    }
    public DisallowParentTouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mView = (ViewGroup) getParent();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        if (this.mView != null && e.getAction() != MotionEvent.ACTION_UP) {
            this.mView.requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (this.mView != null && e.getAction() != MotionEvent.ACTION_UP) {
            this.mView.requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(e);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (this.mView != null && e.getAction() != MotionEvent.ACTION_UP) {
            this.mView.requestDisallowInterceptTouchEvent(true);
        }
        return super.onInterceptTouchEvent(e);
    }
//private int current;
//    /**
//     * 保存position与对于的View
//     */
//    private HashMap<Integer, Integer> maps = new LinkedHashMap<Integer, Integer>();
//
//    public DisallowParentTouchViewPager(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public DisallowParentTouchViewPager(Context context) {
//        super(context);
//    }
//
//    public int getCurrent() {
//        return current;
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int height = 0;
//        // 下面遍历所有child的高度
//        for (int i = 0; i < this.getChildCount(); i++) {
//            View child = getChildAt(i);
//            child.measure(widthMeasureSpec,
//                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//            int h = child.getMeasuredHeight();
//            // 采用最大的view的高度
//            maps.put(i, h);
//
//        }
//        if (getChildCount() > 0) {
//            height = getChildAt(current).getMeasuredHeight();
//
//        }
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
//                MeasureSpec.EXACTLY);
//
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
//
//    public void resetHeight(int current) {
//        this.current = current;
//        if (maps.size() > current) {
//
//            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
//            if (layoutParams == null) {
//                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, maps.get(current));
//            } else {
//                layoutParams.height = maps.get(current);
//            }
//            setLayoutParams(layoutParams);
//        }
//    }
}
