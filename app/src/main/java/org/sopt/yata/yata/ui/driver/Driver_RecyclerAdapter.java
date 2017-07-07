package org.sopt.yata.yata.ui.driver;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.sopt.yata.yata.R;
import org.sopt.yata.yata.vo.MyViewHolder;

import java.util.ArrayList;

/**
 * Created by taehyung on 2017-07-03.
 */

public class Driver_RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<DriverSearchListData> searchDatas;  //댓글을 받아오려면 어떠한 객체를 리스트로 가지는 데이터가 필요할까요~
    private View.OnClickListener onClickListener;

    public Driver_RecyclerAdapter(ArrayList<DriverSearchListData> searchDatas, View.OnClickListener onClickListener) {
        this.searchDatas = searchDatas;
        this.onClickListener = onClickListener;
    }

    public void setAdapter(ArrayList<DriverSearchListData> searchDatas) {
        this.searchDatas = searchDatas;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_search_list, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        view.setOnClickListener(onClickListener);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.VH_main_title.setText(searchDatas.get(position).user_name);
    }

    @Override
    public int getItemCount() {
        return searchDatas != null ? searchDatas.size() : 0;
    }
}
