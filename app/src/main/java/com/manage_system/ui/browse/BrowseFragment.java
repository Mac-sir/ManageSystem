package com.manage_system.ui.browse;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.manage_system.R;
import com.manage_system.component.ApplicationComponent;
import com.manage_system.net.JanDanApi;
import com.manage_system.ui.adapter.BoredPicAdapter;
import com.manage_system.ui.adapter.FreshNewsAdapter;
import com.manage_system.ui.adapter.JokesAdapter;
import com.manage_system.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * desc: 煎蛋
 * author: Will .
 * date: 2017/9/2 .
 */
public class BrowseFragment extends BaseFragment {

    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private JanDanPagerAdapter mJanDanPagerAdapter;

    public static BrowseFragment newInstance() {
        Bundle args = new Bundle();
        BrowseFragment fragment = new BrowseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_jiandan;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        List<String> strings = new ArrayList<>();
        strings.add("新鲜事");
        strings.add("无聊图");
        strings.add("妹子图");
        strings.add("段子");
        mJanDanPagerAdapter = new JanDanPagerAdapter(getChildFragmentManager(), strings);
        mViewpager.setAdapter(mJanDanPagerAdapter);
        mViewpager.setOffscreenPageLimit(1);
        mViewpager.setCurrentItem(0, false);
        mTabLayout.setupWithViewPager(mViewpager, true);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void onRetry() {

    }

    public class JanDanPagerAdapter extends FragmentStatePagerAdapter {
        private List<String> titles;

        public JanDanPagerAdapter(FragmentManager fm, List<String> titles) {
            super(fm);
            this.titles = titles;
        }

        @Override
        public BaseFragment getItem(int position) {
            switch (position) {
                case 0:
                    return JdDetailFragment.newInstance(JanDanApi.TYPE_FRESH,new FreshNewsAdapter(getActivity(),null));
                case 1:
                    return JdDetailFragment.newInstance(JanDanApi.TYPE_BORED,new BoredPicAdapter(getActivity(),null));
                case 2:
                    return JdDetailFragment.newInstance(JanDanApi.TYPE_GIRLS,new BoredPicAdapter(getActivity(),null));
                case 3:
                    return JdDetailFragment.newInstance(JanDanApi.TYPE_Duan,new JokesAdapter(null));
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }

}
