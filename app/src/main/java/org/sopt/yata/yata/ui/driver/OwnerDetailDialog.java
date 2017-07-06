package org.sopt.yata.yata.ui.driver;
import android.app.Dialog;
import android.content.Context;

import org.sopt.yata.yata.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yeonjin on 2017-07-03.
 */


public class OwnerDetailDialog extends Dialog {
    public OwnerDetailDialog(Context context) {
        super(context, R.style.Theme_Dialog);
        setContentView(R.layout.dialog_owner_detail);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.cancel_btn)
    public void cancelClick(){
        this.dismiss();
    }
}