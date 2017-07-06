package org.sopt.yata.yata.ui.owner;

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
import android.widget.Button;

import org.sopt.yata.yata.R;
import org.sopt.yata.yata.network.NetworkService;
import org.sopt.yata.yata.ui.common.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OwnerActivity extends AppCompatActivity {

    private OwnerActivity.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    final private Activity activity = this;
    final private Context context = this;

    String token;
    int type;

    ProfileFragment profile;
    OwnerSearchFragment ownerSearch;
    OwnerCurrentFragment ownerCurrent;
    OwnerSettingFragment ownerSetting;

    NetworkService networkService;

    @BindViews({R.id.button_find, R.id.button_current, R.id.button_profile, R.id.button_setting})
    List<Button> tabButtonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
        ButterKnife.bind(this);

        SharedPreferences user_token = context.getSharedPreferences("usertoken", MODE_PRIVATE);
        token = user_token.getString("token", null);
        SharedPreferences user_type = context.getSharedPreferences("user_type", MODE_PRIVATE);
        type = user_type.getInt("user_type", 0);

        ownerSearch = new OwnerSearchFragment(activity);
        ownerCurrent = new OwnerCurrentFragment(activity, context);
        profile = new ProfileFragment(activity);
        ownerSetting = new OwnerSettingFragment(activity);

        mSectionsPagerAdapter = new OwnerActivity.SectionsPagerAdapter(getSupportFragmentManager());
        fragmentList.add(0, ownerSearch);
        fragmentList.add(1, ownerCurrent);
        fragmentList.add(2, profile);
        fragmentList.add(3, ownerSetting);

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
    public void findTab(){
        Log.d("user_type", "findTab: " + type);
            mViewPager.setCurrentItem(0);
            tabSelect(0);
    }


    @OnClick(R.id.button_current)
    public void currentTab(){
        mViewPager.setCurrentItem(1);
        tabSelect(1);
    }


    @OnClick(R.id.button_profile)
    public void profileTab(){
        mViewPager.setCurrentItem(2);
        tabSelect(2);
    }


    @OnClick(R.id.button_setting)
    public void settingTab(){
        mViewPager.setCurrentItem(3);
        tabSelect(3);
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            try {
                return fragmentList.get(position);
            }catch (Exception e){
                Log.e("DriverActivity","PagerAdapter",e);
                return new OwnerSearchFragment(activity);
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
