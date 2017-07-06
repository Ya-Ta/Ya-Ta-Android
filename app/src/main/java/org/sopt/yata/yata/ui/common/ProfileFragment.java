package org.sopt.yata.yata.ui.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.sopt.yata.yata.R;
import org.sopt.yata.yata.application.ApplicationController;
import org.sopt.yata.yata.network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by SeungKoo on 2017. 6. 12..
 */

public class ProfileFragment extends Fragment {
    ProfileResult profileResult;

    Activity activity;
    Context mContext;

    String token;

    NetworkService networkService;

    public ProfileFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getContext();
        return inflater.inflate(R.layout.myprofile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences user_token = mContext.getSharedPreferences("usertoken", MODE_PRIVATE);
        token = user_token.getString("token", null);

        networkService = ApplicationController.getInstance().getNetworkService();

        final TextView text_Email = (TextView) view.findViewById(R.id.profile_email);
        final TextView text_phone = (TextView) view.findViewById(R.id.profile_phone);
        final TextView text_name = (TextView) view.findViewById(R.id.profile_name);
        final TextView text_id = (TextView) view.findViewById(R.id.profile_id);
        final TextView text_pwd = (TextView) view.findViewById(R.id.profile_pw);

        Call<ProfileResult> requestProfile = networkService.getProfileFragment(token);
        Log.d("dipAngry", "onClick: networkService: " + requestProfile);
        requestProfile.enqueue(new Callback<ProfileResult>(){

            @Override
            public void onResponse(Call<ProfileResult> call, Response<ProfileResult> response) {
                Log.d("dipAngry", "profile response.isSuccessful(): " + response.isSuccessful());
                if (response.isSuccessful()) {
                    try{
                        //profile 부분에 자신의 정보 넣어주기
                        Toast.makeText(activity, "profileResult response 받음", Toast.LENGTH_SHORT).show();
                        Log.d("dipAngry", "onResponse: response.body(): " + response.body());
                        profileResult = response.body();

                        profileResult.user_email = response.body().user_email;
                        profileResult.user_id = response.body().user_id;
                        profileResult.user_name = response.body().user_name;
                        profileResult.user_phone = response.body().user_phone;
                        Log.d("dipAngry", "onResponse123: " + profileResult.user_email);
                        Log.d("dipAngry", "onResponse123: " + profileResult.user_id);
                        Log.d("dipAngry", "onResponse123: " + profileResult.user_name);
                        Log.d("dipAngry", "onResponse123: " + profileResult.user_phone);

                        try {
                            Log.d("dipAngry", "onResponse123: in try");
                            text_Email.setText(profileResult.user_email);
                            Log.d("dipAngry", "onResponse: getEmail : " + text_Email.getText());
                            text_pwd.setText("●●●●●●");
                            text_name.setText(profileResult.user_name);
                            text_id.setText(profileResult.user_id);
                            text_phone.setText(profileResult.user_phone);

                        }catch(Exception e){
                            Log.d("dipAngry", "onResponse123: Exception :" );
                            e.printStackTrace();
                        }

                    }catch(NullPointerException ne){
                        ne.printStackTrace();
                    }
                }else {
                    Toast.makeText(activity, "response.inSuccessful() 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResult> call, Throwable t) {
                Toast.makeText(activity, "Fail!", Toast.LENGTH_SHORT).show();
                Log.i("err", "" + t.getMessage());
            }
        });

    }
}
