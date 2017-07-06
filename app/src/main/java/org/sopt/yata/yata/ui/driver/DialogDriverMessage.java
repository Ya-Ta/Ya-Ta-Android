package org.sopt.yata.yata.ui.driver;

import android.app.Dialog;
import android.content.Context;

import org.sopt.yata.yata.R;

/**
 * Created by taehyung on 2017-07-04.
 */

public class DialogDriverMessage extends Dialog {
    public DialogDriverMessage(Context context) {
        super(context, R.style.Theme_Dialog);
        setContentView(R.layout.dialog_driver_message);
    }

}
