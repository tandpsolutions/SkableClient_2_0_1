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
public interface SalesReturnAPI {

    @GET("GetSalesReturnHeader")
    Call<PurchaseHead> getDataHeader(@Query("from_date") String from_date, @Query("to_date") String to_date, @Query("v_type") String v_type, @Query("branch_cd") String branch_cd);

    @GET("GetSalesReturnBill")
    Call<JsonObject> getBill(@Query("ref_no") String ref_no);

    @GET("GetSalesRateByTag")
    Call<JsonObject> GetSalesRateByTag(@Query("tag_no") String tag_no);

    @GET("GetDataFromServer")
    Call<JsonObject> GetDataFromServer(@Query("VALUE") String ref_no, @Query("param_code") String param_code);

    @GET("GetDataFromServer")
    Call<JsonObject> getTagNo(@Query("ref_no") String ref_no, @Query("param_code") String param_code);

    @GET("GetDataFromServer")
    Call<JsonObject> getTagNoDetailSales(@Query("tag_list") String ref_no, @Query("param_code") String param_code, @Query("only_stock") boolean flag);

    @GET("GetSalesReturnPrint")
    Call<JsonObject> GetSalesReturnPrint(@Query("ref_no") String ref_no);

    @GET("GetSalesReturnTaxPrint")
    Call<JsonObject> GetSalesReturnTaxPrint(@Query("ref_no") String ref_no);

    @FormUrlEncoded
    @POST("AddUpdateSalesReturnBill")
    Call<JsonObject> addUpdateSalesBill(@Field("header") String ref_no, @Field("detail") String param_code);

    @FormUrlEncoded
    @POST("DeleteSalesReturn")
    Call<JsonObject> DeleteSalesReturn(@Field("ref_no") String param_code);
}
