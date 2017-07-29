/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrofitAPI;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 * @author bhaumik
 */
public interface TidApi {

    @GET("GetTIDMaster")
    Call<JsonObject> GetTIDMaster(@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("AddUpdateTidMaster")
    Call<JsonObject> addUpdateTidMaster(@Query("tid_cd") String brand_cd, @Query("tid_name") String brand_name, @Query("user_id") String user_id, @Query("AC_YEAR") String ac_year,@Query("db_name") String db_name, @Query("db_year") String db_year);

}
