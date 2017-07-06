package org.sopt.yata.yata.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import org.sopt.yata.yata.R;

import butterknife.ButterKnife;


public class SplashActivity extends AppCompatActivity {

    private final int MESSAGEID_MAIN = 10000;
    private final int MESSAGEID_LOGIN = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        DelayHandler delayHandler = new DelayHandler();
        int messageID;
        boolean isTokenExist = false;
        if (isTokenExist) {
            messageID = MESSAGEID_MAIN;
        } else {
            messageID = MESSAGEID_LOGIN;
        }
        delayHandler.sendEmptyMessageDelayed(messageID, getResources().getInteger(R.integer.splash_delaytime));
    }


    class DelayHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = null;
            switch (msg.what) {
                case MESSAGEID_LOGIN:
                    intent = new Intent(SplashActivity.this, StartingActivity.class);
                    break;
                case MESSAGEID_MAIN:
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                    break;
            }
            startActivity(intent);
            setResult(RESULT_OK);
            finish();
        }
    }
}
