package org.sopt.yata.yata.ui.driver;

import android.app.Dialog;
import android.content.Context;

import org.sopt.yata.yata.R;

/**
 * Created by taehyung on 2017-07-08.
 */

public class Dialog_Driver_Contact extends Dialog {
    public Dialog_Driver_Contact(Context context) {
        super(context, R.style.Theme_Dialog);
        setContentView(R.layout.fragment_owner_contact);
    }
}
