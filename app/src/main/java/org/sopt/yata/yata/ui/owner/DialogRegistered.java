package org.sopt.yata.yata.ui.owner;

import android.app.Dialog;
import android.content.Context;

import org.sopt.yata.yata.R;

/**
 * Created by taehyung on 2017-07-06.
 */

public class DialogRegistered extends Dialog {
    public DialogRegistered(Context context) {
        super(context, R.style.Theme_Dialog);
        setContentView(R.layout.dialog_registered);
    }

}