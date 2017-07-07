package org.sopt.yata.yata.ui.owner;

import java.util.ArrayList;

/**
 * Created by taehyung on 2017-07-07.
 */

public class CurrentDriverListDetailResult {
    public ArrayList<DriverInfo> result;

    class DriverInfo{
        public float rating_star;
        public String applying_message;
        public String user_name;
        public int user_age;
        public int user_career;
        public int applying_companion;
    }
}
