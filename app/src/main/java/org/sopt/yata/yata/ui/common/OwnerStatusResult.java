package org.sopt.yata.yata.ui.common;

/**
 * Created by taehyung on 2017-07-06.
 */

public class OwnerStatusResult {
    OwnerStatusArray result;

    class OwnerStatusArray{
        int matching_idx;
        String user_name;
        int user_type;
    }
}
