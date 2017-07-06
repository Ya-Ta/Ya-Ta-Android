package org.sopt.yata.yata.ui.driver;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.sopt.yata.yata.R;
import org.sopt.yata.yata.application.ApplicationController;
import org.sopt.yata.yata.network.NetworkService;
import org.sopt.yata.yata.vo.MatchingData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.inflate;
import static org.sopt.yata.yata.R.id.textView_companion;

/**
 * Created by SeungKoo on 2017. 7. 1..
 */

public class MatchingListAdapter extends BaseAdapter {
    private Context context;
    private List<MatchingData> dataList;
    private List<MatchingResultListData> resultDataList;

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

    int input_companion;
    String input_msg;

    NetworkService networkService;

    public MatchingListAdapter(Context context){
        this.context = context;
    }

    public MatchingListAdapter(Context context, List<MatchingData> dataList){
        this.context = context;
        this.dataList = dataList;

        Log.d("TEST", "MatchingListAdapter: " + context);
    }

    public MatchingListAdapter(Context context, List<MatchingResultListData> resultDataList, int trash){
        this.context = context;
        this.resultDataList = resultDataList;

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
    public MatchingData getItem(int position) {
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

        final MatchingData curData = getItem(position);

        userName.setText(curData.user_name);
        sLoc.setText(curData.matching_saddr);
        eLoc.setText(curData.matching_eaddr);
        message.setText(curData.matching_message);
        created_time.setText(curData.matching_created_at);

        convertView.setTag(position);
        convertView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final OwnerDetailDialog dialog = new OwnerDetailDialog(context);

                sloc = curData.matching_saddr;
                eloc = curData.matching_eaddr;
                user_name =curData.user_name;
                age = 27;
                average = (float)3.5;
                companion = curData.matching_companion;
                car_info = "SUV";
                string_message = curData.matching_message;
                matching_idx = curData.matching_idx;

                SharedPreferences user_idx = context.getSharedPreferences("userIdx", MODE_PRIVATE);
                SharedPreferences.Editor editor = user_idx.edit();
                editor.putInt("userIdx",matching_idx);
                editor.commit();

                Log.d("TEST", "onClick: matching_idx: " + matching_idx);

                TextView text_start = (TextView) dialog.findViewById(R.id.owner_detail_start);
                TextView text_end = (TextView) dialog.findViewById(R.id.owner_detail_end);
                TextView text_userName = (TextView) dialog.findViewById(R.id.owner_detail_name);
                TextView text_age = (TextView) dialog.findViewById(R.id.owner_detail_age);
                TextView text_average = (TextView) dialog.findViewById(R.id.owner_detail_point);
                TextView text_companion = (TextView) dialog.findViewById(R.id.owner_detail_with);
                TextView text_carInfo = (TextView) dialog.findViewById(R.id.owner_detail_car);
                TextView text_message = (TextView) dialog.findViewById(R.id.owner_detail_message);


                /*
                TODO 작업 후 "받은 출발지" -> sloc 으로 변경해야 함
                        "받은 목적지" -> eloc 으로 변경해야 함
                */
                text_start.setText("받은 도착지");
                text_end.setText("받은 도착지");
                text_userName.setText(user_name);
                text_age.setText(String.valueOf(age));
                text_average.setText(String.valueOf(average));
                text_companion.setText(String.valueOf(companion));
                text_carInfo.setText(car_info);
                text_message.setText(string_message);

                dialog.show();
                dialog.getWindow().setGravity(Gravity.LEFT);

                Button matchingBtn = (Button) dialog.findViewById(R.id.owner_detail_cancel);
                matchingBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        input_companion = 0;

                        final DialogDriverMessage dialogDriverMessage = new DialogDriverMessage(context);
                        dialogDriverMessage.show();

                        final TextView text_input_companion = (TextView) dialogDriverMessage.findViewById(textView_companion);
                        final EditText edit_input_msg = (EditText) dialogDriverMessage.findViewById(R.id.edit_msg);
                        Button minusBtn = (Button) dialogDriverMessage.findViewById(R.id.minus_btn);
                        Button plusBtn = (Button) dialogDriverMessage.findViewById(R.id.plus_btn);
                        Button driver_registerBtn = (Button) dialogDriverMessage.findViewById(R.id.driver_register_btn);
                        final TextView text_companion = (TextView) dialogDriverMessage.findViewById(R.id.textView_companion);

                        plusBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String temp;
                                if(input_companion < 4) {
                                    input_companion += 1;
                                    temp = String.valueOf(input_companion);
                                    text_input_companion.setText(temp);
                                }

                            }
                        });

                        minusBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(input_companion > 0) {
                                    input_companion -= 1;
                                    String temp = String.valueOf(input_companion);
                                    text_input_companion.setText(temp);
                                }
                            }
                        });

                        driver_registerBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialogDriverMessage.dismiss();

                                final DialogInsurance  dialogInsurance = new DialogInsurance(context);
                                dialogInsurance.show();
                                dialogInsurance.getWindow().setGravity(Gravity.CENTER);

                                Button insurance_applyBtn = (Button)dialogInsurance.findViewById(R.id.insurance_btn);

                                insurance_applyBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogInsurance.dismiss();

                                        input_msg = edit_input_msg.getText().toString();
                                        Log.d("taehyungDD", "onClick: input_msg: " + input_msg);

                                        final Message message = new Message();
                                        message.message = input_msg;
                                        message.companion = input_companion;

                                        input_companion = Integer.parseInt(text_companion.getText().toString());

                                        Log.d("taehyungDD", "onClick: send_Message: " + message.message);
                                        Log.d("taehyungDD", "onClick: input_Companion: " + input_companion);

                                        networkService = ApplicationController.getInstance().getNetworkService();
                                        Log.d("taehyungDD", "onClick: networkService :" + networkService );

                                        //┌> networkService.getDriverApplyOwnerResult(matching_idx, message, token) 첫번 째 매개변수 matching_idx 받아와서 넣어야함.
                                        Call<DriverApplyOwnerResult> requestDriverApplyOwner = networkService.getDriverApplyOwnerResult(matching_idx, message, token);
                                        requestDriverApplyOwner.enqueue(new Callback<DriverApplyOwnerResult>() {
                                            @Override
                                            public void onResponse(Call<DriverApplyOwnerResult> call, Response<DriverApplyOwnerResult> response) {
                                                Log.d("taehyungDD", "DriverApplyOwnerResult response.isSuccessful(): " + response.isSuccessful());
                                                if (response.isSuccessful()) {
                                                    Log.d("taehyungDD", "DriverApplyOwnerResult response.body().message: " + response.body().message);
                                                    try {
                                                        if (response.body().message.equals("success")) {
                                                            //성공시 로딩페이지 잠시 보여주면됨
                                                            dialogInsurance.dismiss();
                                                            final DialogMatching dialogMatching = new DialogMatching(context);
                                                            dialogMatching.show();
                                                            Handler handler = new Handler();
                                                            handler.postDelayed(new Runnable(){
                                                                @Override
                                                                public void run() {
                                                                    dialogMatching.dismiss();
                                                                }
                                                            }, 1000);

                                                            Toast.makeText(context, "response 받음", Toast.LENGTH_SHORT).show();



//                                                            //current tab 으로 넘어가는 코드
//                                                            ((DriverActivity)context).finish(); //  일단 기존꺼 닫고
//                                                            Intent i = new Intent(context, DriverActivity.class);
//                                                            i.putExtra("index", "1".toString()); // 뒤에 넘어가는 1은 탭인덱스
//                                                            ((DriverActivity)context).startActivity(i);
                                                            ((DriverActivity)context).tabSelect(1);
                                                        }
                                                    }catch(NullPointerException ne){
                                                        ne.printStackTrace();
                                                    }
                                                }else {
                                                    Toast.makeText(context, "등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            @Override
                                            public void onFailure(Call<DriverApplyOwnerResult> call, Throwable t) {
                                                Toast.makeText(context, "Fail !", Toast.LENGTH_SHORT).show();

                                                Log.i("err", "" + t.getMessage());
                                            }
                                        });



                                    }
                                });
                            }
                        });

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


    public void setDataList(List<MatchingData> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }

}
