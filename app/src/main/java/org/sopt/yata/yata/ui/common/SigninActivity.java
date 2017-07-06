package org.sopt.yata.yata.ui.common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.sopt.yata.yata.R;
import org.sopt.yata.yata.application.ApplicationController;
import org.sopt.yata.yata.network.NetworkService;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {
    LoginInfo loginInfo;
    LoginResult loginResult;


    EditText edit_id;
    EditText edit_passwd;

    Button backdoor;

    NetworkService networkService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //서비스 객체 초기화
//        networkService = ApplicationController.getInstance().getNetworkService();

        ButterKnife.bind(this);

        //뷰 객체 초기화
        edit_id = (EditText) findViewById(R.id.edit_id);
        edit_passwd = (EditText) findViewById(R.id.edit_passwd);
        backdoor = (Button) findViewById(R.id.backdoor_button);

    }

    @OnClick(R.id.backdoor_button)
    public void backdoorClick(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @OnClick(R.id.login_button)
    public void loginClick(){
        loginInfo = new LoginInfo();
        loginInfo.id = edit_id.getText().toString();
        loginInfo.pw = edit_passwd.getText().toString();

        Log.d("taehyungAA", "loginClick: " + ApplicationController.getInstance());

        networkService = ApplicationController.getInstance().getNetworkService();
        Log.d("taehyungAA", "loginClick networkService: " + networkService);

        Call<LoginResult> requestLogin = networkService.getLoginResult(loginInfo);
        requestLogin.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                Log.d("taehyungAA", "Signin response.isSuccessful(): " + response.isSuccessful());
                if (response.isSuccessful()) {
                    Log.d("taehyungAA", "Signin response.body().message: " + response.body().message);
                    try {
                        if (response.body().message.equals("success")) {
                            SharedPreferences user_token = getSharedPreferences("usertoken", MODE_PRIVATE);
                            SharedPreferences.Editor editor = user_token.edit();
                            editor.putString("token", response.body().result.token);
                            editor.commit();

                            Toast.makeText(SigninActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }catch(NullPointerException ne){
                        ne.printStackTrace();
                    }
                }else {
                    Toast.makeText(SigninActivity.this, "ID 혹은 PASSWORD가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Toast.makeText(SigninActivity.this, "Fail !", Toast.LENGTH_SHORT).show();
                Log.i("err", "" + t.getMessage());
            }
        });
    }

}
