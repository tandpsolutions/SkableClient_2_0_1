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
public interface BateryMasterAPI {

    @GET("GetBaterryMaster")
    Call<JsonObject> getBatteryMaster();

    @GET("AddUpdateBatteryMaster")
    Call<JsonObject> addUpdateBatteryMaster(@Query("battery_cd") String brand_cd, @Query("battery_name") String brand_name, @Query("user_id") String user_id, @Query("AC_YEAR") String ac_year);

}