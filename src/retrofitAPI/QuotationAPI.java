/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrofitAPI;

import com.google.gson.JsonObject;
import model.PurchaseHead;
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
public interface QuotationAPI {

    @GET("GetQuotationHeader")
    Call<PurchaseHead> getDataHeader(@Query("from_date") String from_date, @Query("to_date") String to_date);

    @GET("GetDataFromServer")
    Call<JsonObject> getBill(@Query("ref_no") String ref_no, @Query("param_code") String param_code, @Query("db_name") String db_name, @Query("ac_year") String ac_year);

    @GET("GetDataFromServer")
    Call<JsonObject> getTagNo(@Query("ref_no") String ref_no, @Query("param_code") String param_code, @Query("db_name") String db_name, @Query("ac_year") String ac_year);

    @GET("DeleteQuotationBill")
    Call<JsonObject> DeleteQuotationBill(@Query("ref_no") String ref_no);

    @GET("GetDataFromServer")
    Call<JsonObject> getTagNoDetail(@Query("tag_list") String ref_no, @Query("param_code") String param_code, @Query("only_stock") boolean flag, @Query("db_name") String db_name, @Query("ac_year") String ac_year);

    @FormUrlEncoded
    @POST("AddUpdateQuotation")
    Call<JsonObject> addUpdatePurchaseBill(@Field("header") String ref_no, @Field("detail") String param_code);
}
