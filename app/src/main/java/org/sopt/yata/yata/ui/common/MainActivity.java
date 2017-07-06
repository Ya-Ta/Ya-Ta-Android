package org.sopt.yata.yata.ui.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.sopt.yata.yata.R;
import org.sopt.yata.yata.application.ApplicationController;
import org.sopt.yata.yata.network.NetworkService;
import org.sopt.yata.yata.ui.driver.DriverActivity;
import org.sopt.yata.yata.ui.owner.OwnerActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final int OWNER_USER = 20000;
    private final int DRIVER_USER = 20001;

    Context context = this;
    Activity activity = this;

    String token;

    NetworkService networkService;

    OwnerStatusResult.OwnerStatusArray result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        SharedPreferences user_token = activity.getSharedPreferences("usertoken", MODE_PRIVATE);
        token = user_token.getString("token", null);

    }

    @OnClick(R.id.button_owner)
    public void ownerClick(){

        networkService = ApplicationController.getInstance().getNetworkService();
        Call<OwnerStatusResult> requestCurrentListResult = networkService.getOwnerStatus(token);
        requestCurrentListResult.enqueue(new Callback<OwnerStatusResult>() {
            @Override
            public void onResponse(Call<OwnerStatusResult> call, Response<OwnerStatusResult> response) {
                Log.d("KOO", "Signin response.isSuccessful(): " + response.isSuccessful());
                if (response.isSuccessful()) {
                    try{
                        result = response.body().result;

                        SharedPreferences user_type = getSharedPreferences("user_type", MODE_PRIVATE);
                        SharedPreferences.Editor editor = user_type.edit();
                        editor.putInt("user_type", response.body().result.user_type);
                        editor.commit();

                    }catch(NullPointerException ne){
                        ne.printStackTrace();
                    }
                }else {
                    Toast.makeText(context, "response.inSuccessful() 실패", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<OwnerStatusResult> call, Throwable t) {
                Toast.makeText(context, "main->owner response fail !", Toast.LENGTH_SHORT).show();
                Log.i("err", "" + t.getMessage());
            }
        });



        Log.d("taehyungAA", "ownerClick: " + this);
            Intent intent = new Intent(getApplicationContext(), OwnerActivity.class);
            startActivity(intent);
    }

    @OnClick(R.id.button_driver)
    public void driverClick(){
        SharedPreferences user = getSharedPreferences("user_type", MODE_PRIVATE);
        SharedPreferences.Editor editor = user.edit();
        editor.putInt("user_type", DRIVER_USER);
        editor.commit(); //완료

        Intent intent = new Intent(MainActivity.this, DriverActivity.class);
        startActivity(intent);
    }

}
