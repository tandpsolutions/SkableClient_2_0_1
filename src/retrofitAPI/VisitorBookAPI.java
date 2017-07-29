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
public interface VisitorBookAPI {

    @GET("GetVisitorBookHeader")
    Call<JsonObject> GetVisitorBookHeader(@Query("from_date") String from_date, @Query("to_date") String to_date,
            @Query("branch_cd") String branch_cd, @Query("db_name") String db_name, @Query("ac_year") String ac_year);

    @GET("GetVisitorBookHeader")
    Call<JsonObject> GetVisitorBookHeader(@Query("from_date") String from_date, @Query("to_date") String to_date,
            @Query("branch_cd") String branch_cd, @Query("is_del") int is_del, @Query("db_name") String db_name, @Query("ac_year") String ac_year);

    @GET("GetDataFromServer")
    Call<JsonObject> getVisitorBookDetail(@Query("value") String value, @Query("param_code") String param_code, @Query("db_name") String db_name, @Query("ac_year") String ac_year);

    @FormUrlEncoded
    @POST("AddUpdateVisitorBookVoucher")
    Call<JsonObject> AddUpdateOrderBookVoucher(@Field("detail") String param_code, @Field("db_name") String db_name, @Field("ac_year") String ac_year);

    @GET("DeleteVoucherBook")
    Call<JsonObject> DeleteOrderBookEntry(@Query("ref_no") String ref_no, @Query("db_name") String db_name, @Query("ac_year") String ac_year);
}
