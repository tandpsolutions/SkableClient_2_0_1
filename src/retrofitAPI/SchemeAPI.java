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
public interface SchemeAPI {

    @GET("GetSchemeMaster")
    Call<JsonObject> getSchemeMaster();

    @GET("GetSchemeMaster")
    Call<JsonObject> getSchemeMaster(@Query("type_cd") String type_cd);

    @GET("AddUpdateSchemeMaster")
    Call<JsonObject> addUpdateSchemeMaster(@Query("scheme_cd") String scheme_cd, @Query("scheme_name") String scheme_name, @Query("type_cd") String type_cd, @Query("user_id") String user_id);

}
