package com.aiidayang.main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.aiidayang.R;
import com.aiidayang.base.BaseActivity;
import com.aiidayang.moudle.b.BFragment;
import com.aiidayang.moudle.a.AFragment;
import com.aiidayang.moudle.me.MeFragment;
import com.aiidayang.moudle.news.NewsFragment;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class MainActivity extends BaseActivity {
    private String TAG = this.getClass().getSimpleName();
    private AFragment mAFragment;
    private BFragment mBFragment;
    private NewsFragment mNewsFragment;
    private MeFragment mMeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSwipeBackEnable(false);
        initView();
    }

    private void initView() {
        BottomNavigationBar navigationBar = (BottomNavigationBar) findViewById(R.id.main_navigation_bar);
        //模式跟背景的设置都要在添加tab前面，不然不会有效果。
        navigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .setActiveColor(R.color.white)
                .setBarBackgroundColor(R.color.white)
                .setMode(BottomNavigationBar.MODE_FIXED)
                .addItem(new BottomNavigationItem(R.mipmap.navigation1_normal, "a")
                        .setInactiveIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.navigation1_select)))
                .addItem(new BottomNavigationItem(R.mipmap.navigation2_normal, "b")
                        .setInactiveIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.navigation2_select)))
                .addItem(new BottomNavigationItem(R.mipmap.navigation3_normal, "news")
                        .setInactiveIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.navigation3_select)))
                .addItem(new BottomNavigationItem(R.mipmap.navigation4_normal, "me")
                        .setInactiveIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.navigation4_select)))
                .initialise();
        setDefaultFragment();
        navigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                Log.d(TAG, "onTabSelected() called with: " + "position = [" + position + "]");
                FragmentManager fm = getFragmentManager();
                //开启事务
                FragmentTransaction transaction = fm.beginTransaction();
                switch (position) {
                    case 0:
                        if (mAFragment == null) {
                            mAFragment = new AFragment();
                        }
                        transaction.replace(R.id.container, mAFragment);
                        break;
                    case 1:
                        if (mBFragment == null) {
                            mBFragment = new BFragment();
                        }
                        transaction.replace(R.id.container, mBFragment);
                        break;
                    case 2:
                        if (mNewsFragment == null) {
                            mNewsFragment = new NewsFragment();
                        }
                        transaction.replace(R.id.container, mNewsFragment);
                        break;
                    case 3:
                        if (mMeFragment == null) {
                            mMeFragment = new MeFragment();
                        }
                        transaction.replace(R.id.container, mMeFragment);
                        break;
                    default:
                        break;
                }
                // 事务提交
                transaction.commit();
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (mAFragment == null) {
            mAFragment = new AFragment();
        }
        transaction.replace(R.id.container, mAFragment);
        transaction.commit();
    }

}
