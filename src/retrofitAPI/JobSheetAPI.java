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
public interface JobSheetAPI {

    @GET("GetJobType")
    Call<JsonObject> getJobType();

    @GET("GetJobSheetView")
    Call<JsonObject> getJobSheetView(@Query("from_date") String from_date, @Query("to_date") String to_date, @Query("job_type") String job_type, @Query("branch_cd") String branch_cd);
}
