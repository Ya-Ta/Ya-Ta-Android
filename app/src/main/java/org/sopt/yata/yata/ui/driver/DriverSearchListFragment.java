package org.sopt.yata.yata.ui.driver;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.sopt.yata.yata.R;
import org.sopt.yata.yata.ui.common.DialogMiniDetail;

import java.util.ArrayList;

/**
 * Created by taehyung on 2017-07-03.
 */
public class DriverSearchListFragment extends Fragment {
    Context listContext;

    private ArrayList<DriverSearchListData> driverListData;
    private Driver_RecyclerAdapter mAdapter;

    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_driver_search_list);
//
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_list);
//
//        driverListData = new ArrayList<DriverSearchListData>();
//        mAdapter = new Driver_RecyclerAdapter(driverListData, clickEvent);
//        recyclerView.setAdapter(mAdapter);
//
//        mLayoutManager = new LinearLayoutManager(listContext);
//        Log.d("taehyungCC", "onViewCreated: mLayoutManager :" + mLayoutManager);
//        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(mLayoutManager);
//
//    }

    public DriverSearchListFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        listContext = getContext();
        return inflater.inflate(R.layout.driver_search_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        driverListData = new ArrayList<DriverSearchListData>();
        mAdapter = new Driver_RecyclerAdapter(driverListData, clickEvent);
        recyclerView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(listContext);
        Log.d("taehyungCC", "onViewCreated: mLayoutManager :" + mLayoutManager);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            String tempSloc = driverListData.get(itemPosition).matching_sloc;

            final DialogMiniDetail miniDetail = new DialogMiniDetail(listContext);
            miniDetail.show();

        }
    };
}