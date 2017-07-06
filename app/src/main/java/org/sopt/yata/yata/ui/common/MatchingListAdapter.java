package org.sopt.yata.yata.ui.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.sopt.yata.yata.R;

import static android.view.View.inflate;

/**
 * Created by SeungKoo on 2017. 6. 20..
 */

public class MatchingListAdapter extends BaseAdapter {

    private Context context;
    public MatchingListAdapter(Context context){
        this.context = context;
    }


    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
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


        return convertView;
    }
}
