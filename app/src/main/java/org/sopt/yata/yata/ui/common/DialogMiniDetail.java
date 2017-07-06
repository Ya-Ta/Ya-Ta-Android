package org.sopt.yata.yata.ui.common;

import android.app.Dialog;
import android.content.Context;

import org.sopt.yata.yata.R;

/**
 * Created by taehyung on 2017-07-03.
 */

public class DialogMiniDetail extends Dialog {
    public DialogMiniDetail(Context context) {
        super(context, R.style.Theme_Dialog);
        setContentView(R.layout.dialog_owner_detail);
    }
}
