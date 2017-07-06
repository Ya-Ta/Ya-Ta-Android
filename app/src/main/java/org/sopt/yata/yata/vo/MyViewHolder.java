package org.sopt.yata.yata.vo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.sopt.yata.yata.R;

/**
 * Created by taehyung on 2017-07-03.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView VH_main_writer;
    public TextView VH_main_title;
    public TextView VH_main_count; //조회수
    public TextView VH_main_date; //작성시간
    ////////////////메인 리스트////////////////

    public MyViewHolder(View itemView) {
        super(itemView);

        VH_main_writer = (TextView)itemView.findViewById(R.id.MainList_writer);
        VH_main_title = (TextView)itemView.findViewById(R.id.MainList_title);
        VH_main_count = (TextView)itemView.findViewById(R.id.MainList_count); //조회수
        VH_main_date = (TextView)itemView.findViewById(R.id.MainList_date); //작성시간

        ////////////////메인 리스트////////////////
    }
}