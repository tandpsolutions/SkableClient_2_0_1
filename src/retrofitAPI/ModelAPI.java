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
public interface ModelAPI {

    @GET("GetModelMaster")
    Call<JsonObject> getModelMaster(@Query("model_name") String model_name,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("GetModel")
    Call<JsonObject> GetModel(@Field("model_cd") String model_cd,@Field("db_name") String db_name, @Field("db_year") String db_year);

    @FormUrlEncoded
    @POST("UpdateBulkHSN")
    Call<JsonObject> updateBulkHSN(@Field("detail") String detail,@Field("db_name") String db_name, @Field("db_year") String db_year);
    
    @FormUrlEncoded
    @POST("UpdateBulkGstCode")
    Call<JsonObject> updateBulkGSTCode(@Field("detail") String detail,@Field("db_name") String db_name, @Field("db_year") String db_year);

    @GET("getSetUpData")
    Call<JsonObject> getSetUpData(@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("AppUpdateModelMaster")
    Call<JsonObject> AppUpdateModelMaster(@Field("model_cd") String model_cd, @Field("model_name") String model_name,
            @Field("brand_cd") String brand_cd, @Field("type_cd") String type_cd, @Field("tax_cd") String tax_cd, @Field("user_id") String user_id,
            @Field("sub_type_cd") String sub_type_cd, @Field("AC_YEAR") String ac_year, @Field("hsn_code") String hsn_code, @Field("GST_CD") String gst_cd
            ,@Field("db_name") String db_name, @Field("db_year") String db_year);
}
