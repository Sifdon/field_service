package com.agit.bp.mechanicbp.services;

import com.agit.bp.mechanicbp.models.RequestStatusTasklist;
import com.agit.bp.mechanicbp.models.RequestStatusVOC;
import com.agit.bp.mechanicbp.models.RequestStatusWO;
import com.agit.bp.mechanicbp.models.RequestUserMechanic;
import com.agit.bp.mechanicbp.models.ResponseNewWO;
import com.agit.bp.mechanicbp.models.ResponseStatusApproval;
import com.agit.bp.mechanicbp.models.ResponseStatusTasklist;
import com.agit.bp.mechanicbp.models.ResponseStatusWO;
import com.agit.bp.mechanicbp.models.ResponseUserMechanic;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by erwin on 6/14/16.
 */
public interface RESTService {

    String php = "";
    //server java
    @Headers("TOKEN-ID: 000")
    @GET("tasklist/getNewWorkOrder"+php)
    Observable<ResponseNewWO> getNew(@Query("mechanicid") String mechanicid);

    //get all WO
    @Headers("TOKEN-ID: 000")
    @GET("tasklist/getWorkOrder"+php)
    Observable<ResponseNewWO> getAllWO(@Query("mechanicid") String mechanicid);

    //@FormUrlEncoded
    @Headers("TOKEN-ID: 000")
    @POST("login/requestLoginMobile"+php)
    Observable<ResponseUserMechanic> login(
            @Body RequestUserMechanic data
    );
    //server java login : http:localhost:8888/binapertiwi/rest/login/requestLoginMobile

    @Headers("TOKEN-ID: 000")
    @POST("login/logoutMobile"+php)
    Observable<ResponseUserMechanic> logout(
            @Body RequestUserMechanic data
    );
    //server java : http:localhost:8888/binapertiwi/rest/login/logoutMobile

    //@FormUrlEncoded
    @Headers("TOKEN-ID: 000")
    @POST("tasklist/{path}"+php)
    Observable<ResponseStatusWO> sentrequest(
            @Path("path") String path,
            @Body RequestStatusWO data
    );

    //new
    //@FormUrlEncoded
    @Headers("TOKEN-ID: 000")
    @POST("tasklist/{path}"+php)
    Observable<ResponseStatusTasklist> sentrequestTasklist(
            @Path("path") String path,
            @Body RequestStatusTasklist data
    );

    @Headers("TOKEN-ID: 000")
    @POST("tasklist/{path}"+php)
    Observable<ResponseStatusWO> sentrequestFeedback(
            @Path("path") String path,
            @Body RequestStatusVOC data
    );

    @Headers("TOKEN-ID: 000")
    @GET("tasklist/getAprrovalOrder"+php)
    Observable<ResponseStatusApproval> updateApprovalStatus(@Query("mechanicid") String mechanicid);

    @Headers("TOKEN-ID: 000")
    @GET("tasklist/getDetailWO"+php)
    Observable<ResponseNewWO> getDetailWO(@Query("mechanicid") String mechanicid, @Query("orderHeaderId") String orderHeaderId, @Query("orderAssignmentId") String orderAssignmentId);
}