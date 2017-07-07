package org.sopt.yata.yata.network;


import org.sopt.yata.yata.ui.common.LoginInfo;
import org.sopt.yata.yata.ui.common.LoginResult;
import org.sopt.yata.yata.ui.common.OwnerStatusResult;
import org.sopt.yata.yata.ui.common.ProfileResult;
import org.sopt.yata.yata.ui.common.RegisterInfo;
import org.sopt.yata.yata.ui.common.RegisterResult;
import org.sopt.yata.yata.ui.driver.CurrentOwnerListResult;
import org.sopt.yata.yata.ui.driver.DriverApplyOwnerResult;
import org.sopt.yata.yata.ui.driver.Message;
import org.sopt.yata.yata.ui.owner.CurrentDriverListDetailResult;
import org.sopt.yata.yata.ui.owner.CurrentDriverListResult;
import org.sopt.yata.yata.ui.owner.OwnerInfo;
import org.sopt.yata.yata.ui.owner.OwnerResult;
import org.sopt.yata.yata.vo.OwnerLocationListVO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by taehyung on 2017-06-27.
 */

public interface NetworkService {
    @GET("user/login")
    Call<LoginResult> getLoginResult(@Body LoginResult loginResult);

    @GET("user/logout")
    Call<LoginResult> getLogoutResult();


//    @GET("/user/{user_idx}")
//    Call<> getUserIdx(@Path("user_idx") int user_idx);

    @POST("user/login")
    Call<LoginResult> getLoginResult(@Body LoginInfo loginInfo);

    @POST("user/register")
    Call<RegisterResult> getRegisterResult(@Body RegisterInfo registerInfo);

    @POST("owner/match/register")
    Call<OwnerResult> getOwnerResult(@Body OwnerInfo ownerInfo, @Header("token") String token);

    @GET("driver/apply/search/{sloc}/{eloc}")
    Call<OwnerLocationListVO> getDriverResult(@Path("sloc") String sloc, @Path("eloc") String eloc, @Header("token") String token);

    //운전자 -> 차주 매칭 신청
    @POST("driver/apply/{matching_idx}")
    Call<DriverApplyOwnerResult> getDriverApplyOwnerResult(@Path("matching_idx") int matching_idx, @Body Message message, @Header("token") String token);

    @GET("profile")
    Call<ProfileResult> getProfileFragment(@Header("token") String token);

    //운전자 -> 내가 신청한 차주 목록
    @GET("driver/apply/list/")
    Call<CurrentOwnerListResult> getCurrentOwnerListResult(@Header("token") String token);

    //차주 -> 나에게 신청한 운전자 목록
    @GET("owner/match/list/{matching_idx}")
    Call<CurrentDriverListResult> getCurrentDriverListResult(@Path("matching_idx") int matching_idx,@Header("token") String token);

    @GET("owner")
    Call<OwnerStatusResult> getOwnerStatus(@Header("token") String token);

    @GET("owner/match/detail/{applying_idx}")
    Call<CurrentDriverListDetailResult> getCurrentDriverDetailResult(@Path("applying_idx") int applying_idx, @Header("token") String token);

}
