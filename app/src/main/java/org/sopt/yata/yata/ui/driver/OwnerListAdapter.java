package org.sopt.yata.yata.ui.driver;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

public class OwnerListAdapter extends BaseAdapter {
    private Context context;
    private List<MatchingResultListData> dataList;

    String token;

    String sAddr;
    String eAddr;

    String sloc;
    String eloc;
    String user_name;
    int age;
    float average;
    int companion;
    String car_info;
    String string_message;
    int matching_idx;
    int applying_idx;

    int input_companion;
    String input_msg;

    NetworkService networkService;

    public OwnerListAdapter(Context context){
        this.context = context;
    }

    public OwnerListAdapter(Context context, List<MatchingResultListData> dataList){
        this.context = context;
        this.dataList = dataList;

        Log.d("TEST", "MatchingListAdapter: " + context);
    }

    @Override
    public int getCount() {
        if(dataList == null){
            return 0;
        }else{
            return dataList.size();
        }
    }

    @Override
    public MatchingResultListData getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = inflate(context, R.layout.cell_matching_list, null);
        }

        SharedPreferences user_token = context.getSharedPreferences("usertoken", MODE_PRIVATE);
        token = user_token.getString("token", null);
        Log.d("taehyungDD", "getView: token: " + token);

        ImageView userImage = (ImageView) convertView.findViewById(R.id.image_profile);
        TextView userName = (TextView) convertView.findViewById(R.id.text_user_name);
        TextView sLoc = (TextView) convertView.findViewById(R.id.text_sloc);
        final TextView eLoc = (TextView) convertView.findViewById(R.id.text_eloc);
        final TextView time = (TextView) convertView.findViewById(R.id.text_matching_time);
        final TextView created_time = (TextView) convertView.findViewById(R.id.text_created_time);
        final TextView message = (TextView) convertView.findViewById(R.id.text_matching_message);

        final MatchingResultListData curData = getItem(position);

        userName.setText(curData.user_name);
        sLoc.setText(curData.matching_saddr);
        eLoc.setText(curData.matching_eaddr);
        message.setText(curData.matching_message);
        created_time.setText(curData.matching_time);

        convertView.setTag(position);
        convertView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                final OwnerDetailDialog dialog = new OwnerDetailDialog(context);
                sloc = curData.matching_saddr;
                eloc = curData.matching_eaddr;
                user_name = curData.user_name;
                age = 27;
                average = (float)3.5;
                companion = curData.matching_companion;
                car_info = "SUV";
                string_message = curData.matching_message;
                matching_idx = curData.matching_idx;

                TextView text_start = (TextView) dialog.findViewById(R.id.owner_detail_start);
                TextView text_end = (TextView) dialog.findViewById(R.id.owner_detail_end);
                TextView text_userName = (TextView) dialog.findViewById(R.id.owner_detail_name);
                TextView text_age = (TextView) dialog.findViewById(R.id.owner_detail_age);
                TextView text_average = (TextView) dialog.findViewById(R.id.owner_detail_point);
                TextView text_companion = (TextView) dialog.findViewById(R.id.owner_detail_with);
                TextView text_carInfo = (TextView) dialog.findViewById(R.id.owner_detail_car);
                TextView text_message = (TextView) dialog.findViewById(R.id.owner_detail_message);
                Button matchingCancel = (Button) dialog.findViewById(R.id.owner_detail_cancel);


                /*
                TODO 작업 후 "받은 출발지" -> sloc 으로 변경해야 함
                        "받은 목적지" -> eloc 으로 변경해야 함
                */
                text_start.setText("받은 출발지");
                text_end.setText("받은 도착지");
                text_userName.setText(user_name);
                text_age.setText(String.valueOf(age));
                text_average.setText(String.valueOf(average));
                text_companion.setText(String.valueOf(companion));
                text_carInfo.setText(car_info);
                text_message.setText(string_message);
                matchingCancel.setText("매칭 취소");

                dialog.show();
                dialog.getWindow().setGravity(Gravity.LEFT);

                matchingCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


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
        this.dataList = dataList;
        notifyDataSetChanged();
    }

}
