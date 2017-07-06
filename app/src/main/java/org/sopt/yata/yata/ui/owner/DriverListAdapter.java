package org.sopt.yata.yata.ui.owner;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.sopt.yata.yata.R;
import org.sopt.yata.yata.network.NetworkService;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.inflate;

/**
 * Created by SeungKoo on 2017. 7. 1..
 */

public class DriverListAdapter extends BaseAdapter {
    private Context context;
    private List<MatchingResultListData> dataList;
    private List<MatchingResultListData> resultDataList;

    String token;

    String applying_created_at;
    String applying_message;
    String user_name;
    String user_img;

    NetworkService networkService;

    public DriverListAdapter(Context context){
        this.context = context;
    }

    public DriverListAdapter(Context context, List<MatchingResultListData> dataList){
        this.context = context;
        this.resultDataList = dataList;

        Log.d("TEST", "DriverListAdapter: " + context);
    }

    public DriverListAdapter(Context context, List<MatchingResultListData> resultDataList, int trash){
        this.context = context;
        this.resultDataList = resultDataList;

        Log.d("TEST", "DriverListAdapter: " + context);
    }

    @Override
    public int getCount() {
        if(resultDataList == null){
            return 0;
        }else{
            return resultDataList.size();
        }
    }

    @Override
    public MatchingResultListData getItem(int position) {
        return resultDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(convertView == null){
            convertView = inflate(context, R.layout.owner_driverlist, null);
        }

        SharedPreferences user_token = context.getSharedPreferences("usertoken", MODE_PRIVATE);
        token = user_token.getString("token", null);
        Log.d("taehyungDD", "getView: token: " + token);

        ImageView userImage = (ImageView) convertView.findViewById(R.id.image_profile);
        TextView userName = (TextView) convertView.findViewById(R.id.text_user_name);
        final TextView created_time = (TextView) convertView.findViewById(R.id.text_created_time);
        final TextView message = (TextView) convertView.findViewById(R.id.text_matching_message);

        final MatchingResultListData curData = getItem(position);

        userName.setText(curData.user_name);


        convertView.setTag(position);
        convertView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
//                final DialogDriverDetail dialog = new DialogDriverDetail(context);

                user_name =curData.user_name;
                applying_created_at = curData.applying_created_at;
                applying_message = curData.applying_message;


                SharedPreferences user_idx = context.getSharedPreferences("userIdx", MODE_PRIVATE);
                SharedPreferences.Editor editor = user_idx.edit();


                //통신 또 필요 url:  /owner/match/detail/:applying_idx

//                TextView text_name = (TextView) dialog.findViewById(R.id.driver_detail_name);
//                TextView text_age = (TextView) dialog.findViewById(R.id.driver_detail_age);
//                TextView text_point = (TextView) dialog.findViewById(R.id.driver_detail_point);
//                TextView text_companion = (TextView) dialog.findViewById(R.id.driver_detail_with);
//                TextView text_career = (TextView) dialog.findViewById(R.id.driver_detail_career);
//                Button button_matching_reject = (Button) dialog.findViewById(R.id.owner_detail_cancel);


//                text_name.setText(user_name);
//
//                dialog.show();
//                dialog.getWindow().setGravity(Gravity.LEFT);
//
//                Button matchingBtn = (Button) dialog.findViewById(R.id.owner_detail_cancel);

            }
        });


//        ((Button)convertView.findViewById(R.id.button_matching_cancel)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Todo 매칭 취소에 해당하는 리퀘스트. 리스폰스에서 리스트의 갱신이 필요하다.
//            }
//        });

        return convertView;
    }


    public void setDataList(List<MatchingResultListData> dataList){
        this.resultDataList = dataList;
        notifyDataSetChanged();
    }

}
