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
public interface ContraAPI {

    @GET("GetContraVoucher")
    Call<JsonObject> GetContraVoucher(@Query("from_date") String from_date, @Query("to_date") String to_date, @Query("branch_cd") String branch_cd);

    @GET("GetDataFromServer")
    Call<JsonObject> getContraVoucher(@Query("value") String value, @Query("param_code") String param_code, @Query("db_name") String db_name, @Query("ac_year") String ac_year);

    @FormUrlEncoded
    @POST("AddUpdateContraVoucher")
    Call<JsonObject> AddUpdateContraVoucher(@Field("detail") String param_code);
}
