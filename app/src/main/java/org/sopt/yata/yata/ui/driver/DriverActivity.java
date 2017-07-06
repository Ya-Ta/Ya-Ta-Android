package org.sopt.yata.yata.ui.driver;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.sopt.yata.yata.R;
import org.sopt.yata.yata.network.NetworkService;
import org.sopt.yata.yata.ui.common.ProfileFragment;
import org.sopt.yata.yata.ui.owner.DriverSettingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DriverActivity extends AppCompatActivity {

    public static int userType;
    String token;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    ProfileFragment profile;
    DriverSearchFragment driverSearch;
    DriverCurrentFragment driverCurrent;
    DriverSettingFragment driverSetting;

    ListView layout_list;

    NetworkService networkService;

    final private Activity activity = this;
    final private Context context = this;

    @BindViews({R.id.button_find, R.id.button_current, R.id.button_profile, R.id.button_setting})
    List<Button> tabButtonList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        ButterKnife.bind(this);

        layout_list = (ListView) findViewById(R.id.list_matching);

        SharedPreferences user = getSharedPreferences("user_type", MODE_PRIVATE);
        userType = user.getInt("user_type", 0);
        SharedPreferences user_token = context.getSharedPreferences("usertoken", MODE_PRIVATE);
        token = user_token.getString("token", null);

        driverSearch = new DriverSearchFragment(this);
        driverCurrent = new DriverCurrentFragment(activity, context);
        profile = new ProfileFragment(activity);
        driverSetting = new DriverSettingFragment();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        fragmentList.add(0, driverSearch);
        fragmentList.add(1, driverCurrent);
        fragmentList.add(2, profile);
        fragmentList.add(3, driverSetting);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    @OnClick(R.id.button_find)
    public void findTab(View view) {
        mViewPager.setCurrentItem(0);
        tabSelect(0);
    }


    @OnClick(R.id.button_current)
    public void currentTab(View view) {
        mViewPager.setCurrentItem(1);
        tabSelect(1);
    }


    @OnClick(R.id.button_profile)
    public void profileTab(View view) {
        mViewPager.setCurrentItem(2);
        tabSelect(2);
    }


    @OnClick(R.id.button_setting)
    public void settingTab(View view) {
        mViewPager.setCurrentItem(3);
        tabSelect(3);
    }


    /**
     *
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            try {
                return fragmentList.get(position);
            } catch (Exception e) {
                Log.e("DriverActivity", "PagerAdapter", e);
                return new DriverSearchFragment(activity);
            }
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }

    /**
     * 페이지 이동에 따른 탭 버튼들의 이미지들에 대한 처리
     * @param page
     */
    public void tabSelect(int page){
        mViewPager.setCurrentItem(page);
        for(Button btn : tabButtonList){
            btn.setSelected(false);
        }
        tabButtonList.get(page).setSelected(true);

    }
}
