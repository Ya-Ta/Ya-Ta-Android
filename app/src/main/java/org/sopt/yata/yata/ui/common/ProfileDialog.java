package org.sopt.yata.yata.ui.common;

import android.app.Dialog;
import android.content.Context;

import org.sopt.yata.yata.R;


/**
 * Created by SeungKoo on 2017. 6. 12..
 */

public class ProfileDialog extends Dialog {
    public ProfileDialog(Context context) {
        super(context, R.style.Theme_Dialog);
        setContentView(R.layout.dialog_profile);
    }
}
