package com.tianjistar.helppersion.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.ImageView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.tianjistar.helppersion.R;
import com.tianjistar.helppersion.activity.persion.PersionActivity;
import com.tianjistar.helppersion.app.MyApplication;
import com.tianjistar.helppersion.base.Base1Activity;
import com.tianjistar.helppersion.fragment.HelpFragment;
import com.tianjistar.helppersion.view.DisallowParentTouchViewPager;

import butterknife.Bind;

/**
 * 任务
 * */
public class MainActivity extends Base1Activity {
    @Bind(R.id.iv_left_persion)
    ImageView imageViewPersion;
    @Bind(R.id.indicator)
    SmartTabLayout indicator;
    @Bind(R.id.pager)
    DisallowParentTouchViewPager pager;
    private FragmentPagerAdapter adapter;
    private static final String[] CONTENT = new String[]{"接单", "救援途中","施救中", "已完成"};

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        setTitle("救援任务");

        adapter = new GoogleMusicAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(4);
        indicator.setViewPager(pager);
        imageViewPersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.openActivity(mContext, PersionActivity.class);
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    class GoogleMusicAdapter extends FragmentPagerAdapter {

        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            Bundle bundle = new Bundle();
            switch (position) {
                case 0:
                    fragment = new HelpFragment();
                    bundle.putString("type","接单");
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment = new HelpFragment();
                    bundle.putString("type","救援途中");
                    fragment.setArguments(bundle);
                    break;
                case 2:
                    fragment = new HelpFragment();
                    bundle.putString("type","施救中");
                    fragment.setArguments(bundle);
                    break;
                case 3:
                    fragment = new HelpFragment();
                    bundle.putString("type","已完成");
                    fragment.setArguments(bundle);
                    break;
                default:
                    fragment = new HelpFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }

}
