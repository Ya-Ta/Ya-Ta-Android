package org.sopt.yata.yata.ui.owner;

import android.app.Dialog;
import android.content.Context;

import org.sopt.yata.yata.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by taehyung on 2017-07-07.
 */

public class DialogDriverDetail extends Dialog {
    public DialogDriverDetail(Context context) {
        super(context, R.style.Theme_Dialog);
        setContentView(R.layout.dialog_driver_detail);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.cancel_btn)
    public void cancelClick(){
        this.dismiss();
    }
}
