package org.sopt.yata.yata.ui.common;

/**
 * Created by taehyung on 2017-06-27.
 */

public class LoginResult {
    public boolean status;
    public String message;
    public Result result;

    public class Result{
        String token;
    }
}
