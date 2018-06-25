package com.googleplay;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.googleplay.base.BaseActivity;
import com.googleplay.core.util.string.StringUtils;
import com.googleplay.fragment.FragmentList;

import static com.googleplay.R.id.viewpager;


public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {

    private ViewPager mViewPager;
    private String[] titles = StringUtils.getStringArray(R.array.pager_titles);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initData();
        initViewpager();
        initTabView();
    }

    private void initTabView() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        // 让TabLayout和ViewPager关联在一起
        tabLayout.setupWithViewPager(mViewPager);
        // 自定义tab样式
        int tabCount = tabLayout.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            TabLayout.Tab tabAt = tabLayout.getTabAt(i);
            tabAt.setCustomView(R.layout.tab_item);
            if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                tabAt.getCustomView().findViewById(R.id.tab_text).setSelected(true);//第一个tab被选中
            }
            TextView textView = (TextView) tabAt.getCustomView().findViewById(R.id.tab_text);
            textView.setText(titles[i]);//设置tab上的文字
        }
        tabLayout.addOnTabSelectedListener(this);
    }

    private void initData() {

    }

    private void initViewpager() {
        mViewPager = (ViewPager) findViewById(viewpager);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        // 每次选中时都去重新加载数据
        FragmentList.getFragment(tab.getPosition()).loadMore();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentList.getFragment(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
        /*
            TODO: 2018/6/24
            注意,使用ViewPager + Fragment时,不能继承多余的方法,例如上面实现FragmentStatePagerAdapter,就不能继承
            isViewFromObject()/instantiateItem()/destroyItem()这些属于PagerAdapter的方法,否则Fragment不显示
         */
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
