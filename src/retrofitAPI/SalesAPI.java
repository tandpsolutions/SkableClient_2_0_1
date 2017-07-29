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
public interface SalesAPI {

    @GET("GetSalesDetail")
    Call<PurchaseHead> getDataHeader(@Query("from_date") String from_date, @Query("to_date") String to_date, @Query("v_type") String v_type, @Query("branch_cd") String branch_cd, @Query("tax_type") String tax_type,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("GetSalesDetailOLD")
    Call<PurchaseHead> getDataHeaderOLD(@Query("from_date") String from_date, @Query("to_date") String to_date, @Query("v_type") String v_type, @Query("branch_cd") String branch_cd, @Query("tax_type") String tax_type,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("GetDataFromServer")
    Call<JsonObject> GetDataFromServer(@Query("VALUE") String ref_no, @Query("param_code") String param_code, @Query("db_name") String db_name, @Query("db_year") String ac_year);

    @GET("GetDataFromServer")
    Call<JsonObject> getTagNo(@Query("ref_no") String ref_no, @Query("param_code") String param_code, @Query("db_name") String db_name, @Query("db_year") String ac_year);

    @GET("GetPurchaseRateByTag")
    Call<JsonObject> GetPurchaseRateByTag(@Query("tag_no") String tag_no,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("DeleteSalesBill")
    Call<JsonObject> DeleteSalesBill(@Query("ref_no") String ref_no,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("GetDataFromServer")
    Call<JsonObject> GetDataFromServer(@Query("tag_list") String ref_no, @Query("param_code") String param_code, @Query("only_stock") boolean flag, @Query("db_name") String db_name, @Query("db_year") String ac_year);

    @GET("GetDataFromServer")
    Call<JsonObject> getTagNoDetailSales(@Query("tag_list") String ref_no, @Query("param_code") String param_code, @Query("only_stock") boolean flag,
            @Query("loc") String loc, @Query("godown") String godown, @Query("db_name") String db_name, @Query("ac_year") String ac_year);

    @GET("GetDataFromServer")
    Call<JsonObject> getTagNoDetailSales(@Query("tag_list") String ref_no, @Query("param_code") String param_code, @Query("only_stock") boolean flag, @Query("loc") String loc, @Query("db_name") String db_name, @Query("db_year") String ac_year);

    @GET("GetSalesBillPrint")
    Call<JsonObject> GetSalesBillPrint(@Query("ref_no") String ref_no,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("GetBulkSalesPrint")
    Call<JsonObject> GetBulkSalesBillPrint(@Query("ref_no") String ref_no,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("GetSalesBillTaxPrint")
    Call<JsonObject> GetSalesBillTaxPrint(@Query("ref_no") String ref_no,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("AddUpdateSalesBill")
    Call<JsonObject> addUpdateSalesBill(@Field("header") String ref_no, @Field("detail") String param_code,@Field("db_name") String db_name, @Field("db_year") String db_year);

    @GET("SalesTrack")
    Call<PurchaseHead> SalesTrack(@Query("from_date") String from_date, @Query("to_date") String to_date, @Query("branch_cd") String branch_cd,
            @Query("imei") String imei, @Query("name") String name, @Query("mobile") String mobile, @Query("bill_no") String bill_no,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("GetInsuranceBill")
    Call<JsonObject> GetInsuranceBill(@Query("ref_no") String ref_no,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("GetDataFromServer")
    Call<JsonObject> getTagNoDetailSales(@Query("tag_list") String ref_no, @Query("param_code") String param_code, @Query("only_stock") boolean flag, @Query("db_name") String db_name, @Query("db_year") String ac_year);
}
