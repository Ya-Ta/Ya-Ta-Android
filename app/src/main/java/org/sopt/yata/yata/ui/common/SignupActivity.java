package org.sopt.yata.yata.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.sopt.yata.yata.R;
import org.sopt.yata.yata.application.ApplicationController;
import org.sopt.yata.yata.network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yeonjin on 2017-06-27.
 */

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText id_edit, name_edit, pw_edit, pwre_edit, mail_edit;
    private Button join_btn, login_btn;

    NetworkService networkService;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        id_edit = (EditText)findViewById(R.id.id_edit);
        name_edit = (EditText)findViewById(R.id.name_edit);
        pw_edit = (EditText)findViewById(R.id.pw_edit);
        pwre_edit = (EditText)findViewById(R.id.pwre_edit);
        mail_edit = (EditText)findViewById(R.id.mail_edit);

        join_btn = (Button)findViewById(R.id.join_btn);
        login_btn = (Button)findViewById(R.id.login_btn);
        join_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.join_btn:
                if(id_edit.length()==0) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                    id_edit.requestFocus();

                }
                else if(name_edit.length()==0) {
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    name_edit.requestFocus();

                }
                else if(pw_edit.length()==0||pwre_edit.length()==0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    pw_edit.requestFocus();
                }
                else if(pwre_edit.length()==0) {
                    Toast.makeText(getApplicationContext(), "비밀번호 확인을 입력하세요.", Toast.LENGTH_SHORT).show();
                    pwre_edit.requestFocus();
                }
                else if(mail_edit.length()==0) {
                    Toast.makeText(getApplicationContext(), "메일을 입력하세요.", Toast.LENGTH_SHORT).show();
                    mail_edit.requestFocus();
                }
                else if(!pw_edit.getText().toString().equals(pwre_edit.getText().toString())){
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    pwre_edit.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(mail_edit.getText().toString()).matches()){
                    Toast.makeText(getApplicationContext(), "올바른 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                    mail_edit.requestFocus();
                }else{ // 모든 항목 입력 성공시
                    RegisterInfo registerInfo = new RegisterInfo();
                    registerInfo.id = id_edit.getText().toString();
                    registerInfo.pw1 = pw_edit.getText().toString();
                    registerInfo.pw2 = pwre_edit.getText().toString();
                    registerInfo.name = name_edit.getText().toString();
                    registerInfo.email = mail_edit.getText().toString();
//                    registerInfo.phone = phone_edit.getText().toString();

                    networkService = ApplicationController.getInstance().getNetworkService();
                    Log.d("Taehyung", "networkService: " + networkService);

                    Call<RegisterResult> requestRegister = networkService.getRegisterResult(registerInfo);
                    Log.d("Taehyung", "requestRegister: " + requestRegister);
                    requestRegister.enqueue(new Callback<RegisterResult>() {
                        @Override
                        public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {
                            Log.d("Taehyung", "response.isSuccessful(): " + response.isSuccessful());
                            if (response.isSuccessful()) {
                                Log.d("Taehyung", "reponse.body: " + response.body().message);
                                if (response.body().message.equals("success")) {
                                    Toast.makeText(SignupActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(SignupActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterResult> call, Throwable t) {
                            Log.i("err", t.getMessage());
                        }
                    });
                }
                break;

            case R.id.login_btn:
                Intent intent = new Intent(this, SigninActivity.class);
                startActivity(intent);
                break;
        }

    }
}