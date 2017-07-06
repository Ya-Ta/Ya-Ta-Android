package org.sopt.yata.yata.ui.owner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.sopt.yata.yata.R;

/**
 * Created by taehyung on 2017-07-01.
 */

public class OwnerSettingFragment extends Fragment {
    public OwnerSettingFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

    }
}
