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
public interface StkAdjAPI {

    @GET("GetStkAdjHeader")
    Call<JsonObject> getDataHeader(@Query("from_date") String from_date, @Query("to_date") String to_date, @Query("v_type") String v_type, @Query("branch_cd") String branch_cd,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("GetStkAdjBill")
    Call<JsonObject> getBill(@Query("ref_no") String ref_no,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("DeleteStkAdjBill")
    Call<JsonObject> DeleteStkAdjBill(@Query("ref_no") String ref_no,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("GetDataFromServer")
    Call<JsonObject> getTagNo(@Query("ref_no") String ref_no, @Query("param_code") String param_code,@Query("db_name") String db_name, @Query("db_year") String ac_year);

    @GET("GetDataFromServer")
    Call<JsonObject> getTagNoDetailSales(@Query("tag_list") String ref_no, @Query("param_code") String param_code, @Query("only_stock") boolean flag,@Query("db_name") String db_name, @Query("db_year") String ac_year);

    @FormUrlEncoded
    @POST("AddUpdateStkAdjDetail")
    Call<JsonObject> AddUpdateStkAdjBill(@Field("detail") String param_code,@Field("db_name") String db_name, @Field("db_year") String db_year);
}
