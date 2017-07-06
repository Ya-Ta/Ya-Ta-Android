package org.sopt.yata.yata.ui.common;

import android.app.Dialog;
import android.content.Context;

import org.sopt.yata.yata.R;

/**
 * Created by taehyung on 2017-06-28.
 */

public class DialogMessage extends Dialog {
    public DialogMessage(Context context) {
        super(context, R.style.Theme_Dialog);
        setContentView(R.layout.dialog_message);
    }
}
