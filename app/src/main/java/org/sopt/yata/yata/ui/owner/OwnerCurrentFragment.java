package org.sopt.yata.yata.ui.owner;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.sopt.yata.yata.R;
import org.sopt.yata.yata.application.ApplicationController;
import org.sopt.yata.yata.network.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static org.sopt.yata.yata.R.id.list_matching;

/**
 * Created by taehyung on 2017-06-28.
 */

public class OwnerCurrentFragment extends Fragment {
    NetworkService networkService;
    ListView receivedList;

    String token;
    int index;

    public Context context;
    public Activity activity;

    ArrayList<org.sopt.yata.yata.ui.owner.MatchingResultListData> matchingResultListDatas;

    org.sopt.yata.yata.ui.owner.DriverListAdapter adapter;

    public OwnerCurrentFragment(Activity activity, Context context){
        this.activity = activity;
        this.context = context;

        adapter = new DriverListAdapter(context, null, 0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_owner_current, container, false);

        SharedPreferences user_token = activity.getSharedPreferences("usertoken", MODE_PRIVATE);
        token = user_token.getString("token", null);
        SharedPreferences user_idx = activity.getSharedPreferences("userIdx", MODE_PRIVATE);
        index = user_idx.getInt("userIdx", 0);
        Log.d("jebal", "onCreateView: " + index);

        search(index);
        //리스트 뿌려주기
        receivedList = (ListView) view.findViewById(R.id.list_matching);
        receivedList.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        ((ListView)view.findViewById(list_matching)).setAdapter(adapter);



    }

    public void search(int index){
        networkService = ApplicationController.getInstance().getNetworkService();

        Call<CurrentDriverListResult> requestCurrentListResult = networkService.getCurrentDriverListResult(index ,token);
        requestCurrentListResult.enqueue(new Callback<CurrentDriverListResult>() {
            @Override
            public void onResponse(Call<CurrentDriverListResult> call, Response<CurrentDriverListResult> response) {
                Log.d("KOO", "getCurrentDriverList response.isSuccessful(): " + response.isSuccessful());
                if (response.isSuccessful()) {
                    try{
                        matchingResultListDatas = response.body().result;
                        Log.d("KOO", "matchingResultListDatas: in Owner: " + matchingResultListDatas);

                        DriverListAdapter resultAdapter = new DriverListAdapter(context, matchingResultListDatas);
                        receivedList.setAdapter(resultAdapter);

                    }catch(NullPointerException ne){
                        ne.printStackTrace();
                    }
                }else {
                    Toast.makeText(context, "response.inSuccessful() 실패", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CurrentDriverListResult> call, Throwable t) {
                Toast.makeText(context, "Fail !", Toast.LENGTH_SHORT).show();
                Log.i("err", "" + t.getMessage());
            }
        });
    }

}
