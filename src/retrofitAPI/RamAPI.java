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
public interface RamAPI {

    @GET("GetRamMaster")
    Call<JsonObject> getRamMaster(@Query("db_name") String db_name, @Query("db_year") String db_year);
    
    @GET("GetDefaultRamValue")
    Call<JsonObject> getDefaultRamValue(@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("AddUpdateRamMaster")
    Call<JsonObject> addUpdateRamMaster(@Query("ram_cd") String ram_cd, @Query("ram_name") String ram_name, @Query("user_id") String user_id, @Query("AC_YEAR") String ac_year,@Query("db_name") String db_name, @Query("db_year") String db_year);

}
