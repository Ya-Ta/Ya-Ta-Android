package org.sopt.yata.yata.ui.common;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.sopt.yata.yata.R;

/**
 * Created by yeonjin on 2017-07-02.
 */

public class CustomAdapter extends PagerAdapter {
    LayoutInflater inflater;

    public CustomAdapter(LayoutInflater inflater){
        //전달받은 Layoutinflater를 멤버변수로 전달
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return 3; //그림 3개
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;

        view = inflater.inflate(R.layout.viewpager_childview, null); //새로운 view객체 생성
        ImageView img = (ImageView)view.findViewById(R.id.img_viewpager); //만들어진 view 안의 이미지객체 참조
        img.setImageResource(R.drawable.viewpager_01+position); //현재 position에 해당하는 이미지 세팅

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

}