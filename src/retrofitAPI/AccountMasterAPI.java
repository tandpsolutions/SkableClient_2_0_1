/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrofitAPI;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 *
 * @author bhaumik
 */
public interface AccountMasterAPI {

    @GET("GetAccountMaster")
    Call<JsonObject> getAccountMaster(@Query("AC_NAME") String ac_name,@Query("GRP_NAME") String grp_name,@Query("ZERO") String zero
            ,@Query("db_name") String db_name, @Query("db_year") String db_year, @Query("is_dealer") int is_dealer);

    @GET("GetAccountMasterCode")
    Call<JsonObject> getAccountMasterCode(@Query("ac_cd") String ac_cd,@Query("db_name") String db_name, @Query("db_year") String db_year);
    

    @FormUrlEncoded
    @POST("UpdateGstNo")
    Call<JsonObject> updateGstNo(@Field("detail") String ac_model,@Field("db_name") String db_name, @Field("db_year") String db_year);
    
    @FormUrlEncoded
    @POST("AddUpdateAccountMaster")
    Call<JsonObject> AddUpdateAccountMaster(@Field("ac_model") String ac_model, @Field("AC_YEAR") String ac_year,@Field("db_name") String db_name, @Field("db_year") String db_year);

}
