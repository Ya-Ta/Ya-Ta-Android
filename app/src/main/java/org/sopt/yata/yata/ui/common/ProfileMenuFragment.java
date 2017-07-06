package org.sopt.yata.yata.ui.common;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.sopt.yata.yata.R;

/**
 * Created by yeonjin on 2017-07-06.
 */

public class ProfileMenuFragment extends Fragment {
    View view;

    public ProfileMenuFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_menu, container, false);

        view.findViewById(R.id.btn_tab1).setOnClickListener(mClickListener);
        view.findViewById(R.id.btn_tab2).setOnClickListener(mClickListener);
        view.findViewById(R.id.btn_tab3).setOnClickListener(mClickListener);
        view.findViewById(R.id.btn_tab4).setOnClickListener(mClickListener);

        Bundle mBundle = new Bundle();
        mBundle = getArguments();
        int menu = mBundle.getInt("menu");
        switch (menu) {
            case 1:
                callFragment(1);
                break;
            case 2:
                callFragment(2);
                break;
            case 3:
                callFragment(3);
                break;
            case 4:
                callFragment(4);
                break;
        }
        return view;
    }


    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_tab1:
                    callFragment(1);
                    break;
                case R.id.btn_tab2:
                    callFragment(2);
                    break;
                case R.id.btn_tab3:
                    callFragment(3);
                    break;
                case R.id.btn_tab4:
                    callFragment(4);
                    break;
            }
        }
    };

    public void callFragment(int fragment_no) {
        switch (fragment_no) {
            case 1:
                view.findViewById(R.id.tab1).setVisibility(View.VISIBLE);
                view.findViewById(R.id.tab2).setVisibility(View.GONE);
                view.findViewById(R.id.tab3).setVisibility(View.GONE);
                view.findViewById(R.id.tab4).setVisibility(View.GONE);
                view.findViewById(R.id.btn_tab1).setBackgroundResource(R.drawable.gray_license);
                view.findViewById(R.id.btn_tab2).setBackgroundResource(R.drawable.white_insurance);
                view.findViewById(R.id.btn_tab3).setBackgroundResource(R.drawable.white_matching);
                view.findViewById(R.id.btn_tab4).setBackgroundResource(R.drawable.white_event);
                break;
            case 2:
                view.findViewById(R.id.tab2).setVisibility(View.VISIBLE);
                view.findViewById(R.id.tab1).setVisibility(View.GONE);
                view.findViewById(R.id.tab3).setVisibility(View.GONE);
                view.findViewById(R.id.tab4).setVisibility(View.GONE);
                view.findViewById(R.id.btn_tab2).setBackgroundResource(R.drawable.gray_insurance);
                view.findViewById(R.id.btn_tab1).setBackgroundResource(R.drawable.white_license);
                view.findViewById(R.id.btn_tab3).setBackgroundResource(R.drawable.white_matching);
                view.findViewById(R.id.btn_tab4).setBackgroundResource(R.drawable.white_event);
                break;
            case 3:
                view.findViewById(R.id.tab3).setVisibility(View.VISIBLE);
                view.findViewById(R.id.tab1).setVisibility(View.GONE);
                view.findViewById(R.id.tab2).setVisibility(View.GONE);
                view.findViewById(R.id.tab4).setVisibility(View.GONE);
                view.findViewById(R.id.btn_tab3).setBackgroundResource(R.drawable.gray_matching);
                view.findViewById(R.id.btn_tab1).setBackgroundResource(R.drawable.white_license);
                view.findViewById(R.id.btn_tab2).setBackgroundResource(R.drawable.white_insurance);
                view.findViewById(R.id.btn_tab4).setBackgroundResource(R.drawable.white_event);
                break;
            case 4:
                view.findViewById(R.id.tab4).setVisibility(View.VISIBLE);
                view.findViewById(R.id.tab1).setVisibility(View.GONE);
                view.findViewById(R.id.tab2).setVisibility(View.GONE);
                view.findViewById(R.id.tab3).setVisibility(View.GONE);
                view.findViewById(R.id.btn_tab4).setBackgroundResource(R.drawable.gray_event);
                view.findViewById(R.id.btn_tab1).setBackgroundResource(R.drawable.white_license);
                view.findViewById(R.id.btn_tab2).setBackgroundResource(R.drawable.white_insurance);
                view.findViewById(R.id.btn_tab3).setBackgroundResource(R.drawable.white_matching);
                break;
        }
    }

}