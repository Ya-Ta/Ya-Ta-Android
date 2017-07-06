package org.sopt.yata.yata.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import org.sopt.yata.yata.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartingActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        ButterKnife.bind(this);

        ViewPager pager=(ViewPager)findViewById(R.id.pager_login_info);
        CustomAdapter adapter = new CustomAdapter(getLayoutInflater());
        pager.setAdapter(adapter);
    }

    @OnClick(R.id.button_login)
    public void loginClick(){
        Intent intent = new Intent(StartingActivity.this, SigninActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_signup)
    public void signUpClick(){
        Intent intent = new Intent(StartingActivity.this, SignupActivity.class);
        startActivity(intent);
    }


}