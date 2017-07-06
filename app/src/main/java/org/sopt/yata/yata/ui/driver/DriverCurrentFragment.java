package org.sopt.yata.yata.ui.driver;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Created by SeungKoo on 2017. 6. 12..
 */

public class DriverCurrentFragment extends Fragment {

    NetworkService networkService;
    ListView matchingList;

    //추가
    ArrayList<MatchingResultListData> matchingResultListDatas;
    ListView layout_list;

    org.sopt.yata.yata.ui.driver.MatchingListAdapter adapter;

    //SharedPreferences 사용 변수
    String token;
    String sloc;
    String eloc;
    int index;

    public Context context;
    public Activity activity;

    public DriverCurrentFragment(Activity activity, Context mContext) {
        this.activity = activity;
        this.context = mContext;

        adapter = new MatchingListAdapter(context, null, 0);
        Log.d("KOO", "DriverCurrentFragment: " + context);
    }
    public DriverCurrentFragment(){ }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_current, container, false);

        SharedPreferences user_token = activity.getSharedPreferences("usertoken", MODE_PRIVATE);
        token = user_token.getString("token", null);
        SharedPreferences user_loc = activity.getSharedPreferences("userSloc", MODE_PRIVATE);
        sloc = user_loc.getString("sloc", null);
        SharedPreferences user_eoc = activity.getSharedPreferences("userEloc", MODE_PRIVATE);
        eloc = user_eoc.getString("eloc", null);
        SharedPreferences user_idx = activity.getSharedPreferences("userIdx", MODE_PRIVATE);
        index = user_idx.getInt("userIdx", 0);
        Log.d("KOO", "onCreateView: INDEX: " + index);

        Log.d("KOO", "search: " + sloc + " / " + eloc);

        search(index);
        //리스트 뿌려주는 코드
        matchingList = (ListView) view.findViewById(R.id.list_matching);
        matchingList.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ((TextView)view.findViewById(R.id.section_label)).setText("current");
        ((ListView)view.findViewById(list_matching)).setAdapter(adapter);
    }

    public void search(int index){
        Log.d("KOO", "차주 현황 리스트 리퀘스트");
        networkService = ApplicationController.getInstance().getNetworkService();

        Call<CurrentOwnerListResult> requestCurrentListResult = networkService.getCurrentOwnerListResult(token);
        requestCurrentListResult.enqueue(new Callback<CurrentOwnerListResult>() {
            @Override
            public void onResponse(Call<CurrentOwnerListResult> call, Response<CurrentOwnerListResult> response) {
                Log.d("KOO", "Signin response.isSuccessful(): " + response.isSuccessful());
                if (response.isSuccessful()) {
                    try{
                        Toast.makeText(context, "currentOwnerListResult response 받음", Toast.LENGTH_SHORT).show();

                        matchingResultListDatas = response.body().result;
                        Log.d("KOO", "matchingResultListDatas: in Driver: " + matchingResultListDatas);

                        OwnerListAdapter resultAdapter = new OwnerListAdapter(context, matchingResultListDatas); // 마지막 0 은 쓰레기값 : 생상자 오버로딩을 위해
                        matchingList.setAdapter(resultAdapter);

                    }catch(NullPointerException ne){
                        ne.printStackTrace();
                    }
                }else {
                    Toast.makeText(context, "response.inSuccessful() 실패", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CurrentOwnerListResult> call, Throwable t) {
                Toast.makeText(context, "Fail !", Toast.LENGTH_SHORT).show();
                Log.i("err", "" + t.getMessage());
            }
        });


    }

}
