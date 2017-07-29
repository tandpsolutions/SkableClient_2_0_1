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
public interface JobSheetAPI {

    @GET("GetJobType")
    Call<JsonObject> getJobType(@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("GetJobSheetView")
    Call<JsonObject> getJobSheetView(@Query("from_date") String from_date, @Query("to_date") String to_date, @Query("job_type") String job_type, @Query("branch_cd") String branch_cd,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("GetDataFromServer")
    Call<JsonObject> getDataFromServer(@Field("value") String param_code, @Field("param_code") String value,@Field("db_name") String db_name, @Field("db_year") String ac_year);

    @GET("GetJobsheetDetail")
    Call<JsonObject> getJobSheetDetail(@Query("ref_no") String ref_no,@Query("db_name") String db_name, @Query("db_year") String db_year);

    
    @FormUrlEncoded
    @POST("AddUpdateJobSheet")
    Call<JsonObject> addUpdateJobSheet(@Field("header") String header,@Field("db_name") String db_name, @Field("db_year") String db_year);
    
    @FormUrlEncoded
    @POST("CloseJobSheet")
    Call<JsonObject> closeJobSheet(@Field("ref_no") String header,@Field("db_name") String db_name, @Field("db_year") String db_year);
    
    @FormUrlEncoded
    @POST("DeleteJobSheet")
    Call<JsonObject> deleteJobSheet(@Field("ref_no") String header,@Field("db_name") String db_name, @Field("db_year") String db_year);
}
