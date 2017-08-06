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
public interface AccountAPI {

    @GET("GetGroupSummary")
    Call<JsonObject> GetGroupSummary(@Query("grp_cd") String sr_cd, @Query("mode") int mode, @Query("greater_then") double greater_then, @Query("less_then") double less_then,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("UpdateOLDB2_4")
    Call<JsonObject> UpdateOLDB2_4(@Query("unpaid_amt") String unpaid_amt, @Query("doc_ref_no") String doc_ref_no, @Query("ac_cd") String ac_cd,
            @Query("sr_no") String sr_no,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("GetGeneralLedgerSummary")
    Call<JsonObject> GetGeneralLedgerSummary(@Query("ac_cd") String ac_cd,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("GenralLedger")
    Call<JsonObject> GenralLedger(@Field("ac_cd") String ac_cd, @Field("from_date") String from_date, @Field("to_date") String to_date,@Field("db_name") String db_name, @Field("db_year") String db_year);

    @FormUrlEncoded
    @POST("BankBook")
    Call<JsonObject> BankBook(@Field("ac_cd") String ac_cd, @Field("from_date") String from_date, @Field("to_date") String to_date, @Field("rec_date") boolean rec_date
            ,@Field("db_name") String db_name, @Field("db_year") String db_year);

    @FormUrlEncoded
    @POST("SetReconsilationDate")
    Call<JsonObject> SetReconsilationDate(@Field("ref_no") String ref_no, @Field("rec_date") String rec_date,@Field("db_name") String db_name, @Field("db_year") String db_year);

    @FormUrlEncoded
    @POST("GetInsuranceRegister")
    Call<JsonObject> GetInsuranceRegister(@Field("from_date") String from_date, @Field("to_date") String to_date, @Field("branch_cd") String branch_cd,@Field("db_name") String db_name
            , @Field("db_year") String db_year);

    @FormUrlEncoded
    @POST("DailySalesStatement")
    Call<JsonObject> DailySalesStatement(@Field("mode") int mode, @Field("from_date") String from_date, @Field("to_date") String to_date,
            @Field("branch_cd") String branch_cd,@Field("db_name") String db_name, @Field("db_year") String db_year);

    @FormUrlEncoded
    @POST("BranchWisePendingCollecionReport")
    Call<JsonObject> BranchWisePendingCollecionReport(@Field("branch_cd") String branch_cd, @Field("v_type") int v_type, @Field("ref_cd") String ref_cd, @Field("from_date") String from_date
            , @Field("to_date") String to_date,@Field("db_name") String db_name, @Field("db_year") String db_year);

    @FormUrlEncoded
    @POST("DailyCashSummary")
    Call<JsonObject> DailyCashStatement(@Field("from_date") String from_date, @Field("to_date") String to_date, @Field("branch_cd") int branch_cd
            ,@Field("db_name") String db_name, @Field("db_year") String db_year);

    @FormUrlEncoded
    @POST("DailyBankSummary")
    Call<JsonObject> DailyBankSummary(@Field("from_date") String from_date, @Field("to_date") String to_date, @Field("branch_cd") int branch_cd
            ,@Field("db_name") String db_name, @Field("db_year") String db_year);

    @GET("GetCardDetail")
    Call<JsonObject> GetCardDetail(@Query("from_date") String type_name, @Query("to_date") String user_id, @Query("branch_cd") String branch_cd,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("DailyCashStatementDetail")
    Call<JsonObject> DailyCashStatementDetail(@Field("from_date") String from_date, @Field("to_date") String to_date, @Field("branch_cd") String branch_cd,@Field("db_name") String db_name, @Field("db_year") String db_year);

    @FormUrlEncoded
    @POST("DailyBankStatementDetail")
    Call<JsonObject> DailyBankStatementDetail(@Field("from_date") String from_date, @Field("to_date") String to_date, @Field("branch_cd") String branch_cd,@Field("db_name") String db_name, @Field("db_year") String db_year);

    @FormUrlEncoded
    @POST("DailySalesStatementDetail")
    Call<JsonObject> DailySalesStatementDetail(@Field("mode") int mode, @Field("from_date") String from_date, @Field("to_date") String to_date,
            @Field("branch_cd") String branch_cd,@Field("db_name") String db_name, @Field("db_year") String db_year);

    @FormUrlEncoded
    @POST("EOD")
    Call<JsonObject> EOD(@Field("mode") int mode, @Field("from_date") String from_date, @Field("to_date") String to_date,
            @Field("branch_cd") String branch_cd,@Field("db_name") String db_name, @Field("db_year") String db_year);

    @FormUrlEncoded
    @POST("GetDenomation")
    Call<JsonObject> GetDenomation(@Field("v_date") String v_date,
            @Field("branch_cd") String branch_cd,@Field("db_name") String db_name, @Field("db_year") String db_year);

    @FormUrlEncoded
    @POST("updateDenomation")
    Call<JsonObject> updateDenomation(@Field("v_date") String v_date, @Field("branch_cd") String branch_cd, @Field("note_cd") int note_cd, @Field("qty") int qty
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("TypeWiseSales")
    Call<JsonObject> TypeWiseSales(@Field("type_cd") String type_cd, @Field("from_date") String from_date, @Field("to_date") String to_date,
            @Field("ac_cd") String ac_cd, @Query("sr_cd") String sr_cd, @Query("brnad_cd") String brnad_cd, @Query("model_cd") String model_cd,
            @Field("branch_cd") String branch_cd, @Field("v_type") int v_type, @Field("sub_type_cd") String sub_type_cd, @Field("scheme_cd") String scheme_cd);

    @FormUrlEncoded
    @POST("TypeWisePurchase")
    Call<JsonObject> TypeWisePurchase(@Field("type_cd") String type_cd, @Field("from_date") String from_date, @Field("to_date") String to_date,
            @Field("ac_cd") String ac_cd, @Query("sr_cd") String sr_cd, @Query("brnad_cd") String brnad_cd, @Query("model_cd") String model_cd,
            @Field("branch_cd") String branch_cd, @Field("sub_type_cd") String sub_type_cd, @Field("scheme_cd") String scheme_cd
            ,@Field("db_name") String db_name, @Field("db_year") String db_year);

    @FormUrlEncoded
    @POST("TypeWiseSalesDetail")
    Call<JsonObject> TypeWiseSalesDetail(@Field("type_cd") String type_cd, @Field("from_date") String from_date, @Field("to_date") String to_date,
            @Field("ac_cd") String ac_cd, @Query("sr_cd") String sr_cd, @Query("brnad_cd") String brnad_cd,
            @Query("model_cd") String model_cd, @Field("branch_cd") String branch_cd, @Field("v_type") int v_type,
            @Field("sub_type_cd") String sub_type_cd, @Field("sm_cd") String sm_cd, @Field("sales_return") boolean sales_return, @Field("scheme_cd") String scheme_cd
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("Phonebook")
    Call<JsonObject> Phonebook(@Field("grp_cd") String grp_cd, @Field("from_date") String from_date, @Field("to_date") String to_date,
            @Field("v_type") int v_type, @Field("sales") boolean sales,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("WithoutTagSalesReport")
    Call<JsonObject> WithoutTagSalesReport(@Field("type_cd") String type_cd, @Field("from_date") String from_date, @Field("to_date") String to_date,
            @Field("ac_cd") String ac_cd, @Query("sr_cd") String sr_cd, @Query("brnad_cd") String brnad_cd, @Query("model_cd") String model_cd, @Field("sub_type_cd") String sub_type_cd
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("TypeWisePurchaseDetail")
    Call<JsonObject> TypeWisePurchaseDetail(@Field("type_cd") String type_cd, @Field("from_date") String from_date, @Field("to_date") String to_date,
            @Field("ac_cd") String ac_cd, @Query("sr_cd") String sr_cd, @Query("brnad_cd") String brnad_cd, @Query("model_cd") String model_cd,
            @Field("branch_cd") String branch_cd, @Field("sub_type_cd") String sub_type_cd, @Field("day") String day, @Field("scheme_cd") String scheme_cd
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("MrpToRateReport")
    Call<JsonObject> MrpToRateReport(@Field("type_cd") String type_cd, @Field("from_date") String from_date, @Field("to_date") String to_date,
            @Field("ac_cd") String ac_cd, @Query("sr_cd") String sr_cd, @Query("brnad_cd") String brnad_cd, @Query("model_cd") String model_cd,
            @Field("branch_cd") String branch_cd, @Field("sub_type_cd") String sub_type_cd, @Field("on_hand") boolean on_hand, @Field("type") int type
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("BuyBackRegister")
    Call<JsonObject> BuyBackRegister(@Field("type_cd") String type_cd, @Field("from_date") String from_date, @Field("to_date") String to_date,
            @Field("ac_cd") String ac_cd, @Query("sr_cd") String sr_cd, @Query("brnad_cd") String brnad_cd, @Query("model_cd") String model_cd
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("BuyBackTrack")
    Call<JsonObject> BuyBackTrack(@Field("from_date") String from_date, @Field("to_date") String to_date, @Field("jv_from_date") String jv_from_date, @Field("jv_to_date") String jv_to_date, @Field("dc_from_date") String dc_from_date, @Field("dc_to_date") String dc_to_date,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("DCRegister")
    Call<JsonObject> DCRegister(@Field("type_cd") String type_cd, @Field("from_date") String from_date, @Field("to_date") String to_date,
            @Field("ac_cd") String ac_cd, @Query("sr_cd") String sr_cd, @Query("brnad_cd") String brnad_cd, @Query("model_cd") String model_cd, @Query("issue") boolean issue,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("CreditNoteRegister")
    Call<JsonObject> CreditNoteRegister(@Field("type_cd") String type_cd, @Field("from_date") String from_date, @Field("to_date") String to_date,
            @Field("ac_cd") String ac_cd, @Query("sr_cd") String sr_cd, @Query("brnad_cd") String brnad_cd, @Query("model_cd") String model_cd,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("CreditNoteReport")
    Call<JsonObject> StockReportCreditNote(@Field("type_cd") String type_cd, @Field("model_cd") String model_cd,
            @Field("from_date") String from_date, @Field("to_date") String to_date, @Field("ac_cd") String ac_cd,
            @Field("memory_cd") String memory_cd, @Field("sales") boolean sales, @Field("brand_cd") String brand_cd,
            @Field("branch_cd") String branch_cd,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("UpdatePrize")
    Call<JsonObject> UpdatePrize(@Field("detail") String model_cd,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("SalesRegister")
    Call<JsonObject> SalesRegister(@Field("pmt_mode") int pmt_mode, @Field("mode") int mode, @Field("branch_cd") int branch_cd,
            @Field("from_date") String from_date, @Field("to_date") String to_date,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("SalesReport")
    Call<JsonObject> SalesReport(@Field("from_date") String from_date, @Field("to_date") String to_date,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("SalesReportTax")
    Call<JsonObject> SalesReportTax(@Field("from_date") String from_date, @Field("to_date") String to_date,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("SalesReturnReportTax")
    Call<JsonObject> SalesReturnReportTax(@Field("from_date") String from_date, @Field("to_date") String to_date,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("BajajReport")
    Call<JsonObject> BajajReport(@Field("from_date") String from_date, @Field("to_date") String to_date, @Field("branch_cd") String branch_cd, @Field("ac_cd") String ac_cd
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("PurchaseReturnTax")
    Call<JsonObject> PurchaseReturnTax(@Field("from_date") String from_date, @Field("to_date") String to_date
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("CardWiseSalesStatement")
    Call<JsonObject> SalesRegisterCardWise(@Field("card") boolean sales
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("SalesRegisterDetail")
    Call<JsonObject> SalesRegisterDetail(@Field("pmt_mode") int pmt_mode, @Field("mode") int mode, @Field("branch_cd") int branch_cd,
            @Field("from_date") String from_date, @Field("to_date") String to_date, @Field("ac_cd") String ac_cd
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("CardWiseSalesDetailStatement")
    Call<JsonObject> CardWiseSalesDetailStatement(@Field("ac_cd") String ac_cd
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("SalesRegisterDetailAccount")
    Call<JsonObject> SalesRegisterDetailAccount(@Field("pmt_mode") int pmt_mode, @Field("mode") int mode, @Field("branch_cd") int branch_cd,
            @Field("from_date") String from_date, @Field("to_date") String to_date, @Field("ac_cd") String ac_cd
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("PurchaseRegisterDetailAccount")
    Call<JsonObject> PurchaseRegisterDetailAccount(@Field("pmt_mode") int pmt_mode, @Field("mode") int mode, @Field("branch_cd") int branch_cd,
            @Field("from_date") String from_date, @Field("to_date") String to_date, @Field("ac_cd") String ac_cd
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("SalesReturnRegisterDetailAccount")
    Call<JsonObject> SalesReturnRegisterDetailAccount(@Field("pmt_mode") int pmt_mode, @Field("branch_cd") int branch_cd,
            @Field("from_date") String from_date, @Field("to_date") String to_date, @Field("ac_cd") String ac_cd
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("PurchaseReturnRegisterAccount")
    Call<JsonObject> PurchaseReturnRegisterAccount(@Field("pmt_mode") int pmt_mode, @Field("branch_cd") int branch_cd,
            @Field("from_date") String from_date, @Field("to_date") String to_date, @Field("ac_cd") String ac_cd
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("PurchaseRegister")
    Call<JsonObject> PurchaseRegister(@Field("pmt_mode") int pmt_mode, @Field("mode") int mode, @Field("branch_cd") int branch_cd,
            @Field("from_date") String from_date, @Field("to_date") String to_date,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("SalesReturnRegister")
    Call<JsonObject> SalesReturnRegister(@Field("pmt_mode") int pmt_mode, @Field("branch_cd") int branch_cd,
            @Field("from_date") String from_date, @Field("to_date") String to_date,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("PurchaseReturnRegister")
    Call<JsonObject> PurchaseReturnRegister(@Field("pmt_mode") int pmt_mode, @Field("branch_cd") int branch_cd,
            @Field("from_date") String from_date, @Field("to_date") String to_date,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("PurchaseRegisterDetail")
    Call<JsonObject> PurchaseRegisterDetail(@Field("pmt_mode") int pmt_mode, @Field("mode") int mode, @Field("branch_cd") int branch_cd,
            @Field("from_date") String from_date, @Field("to_date") String to_date, @Field("ac_cd") String ac_cd,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("SalesReturnRegisterDetail")
    Call<JsonObject> SalesReturnRegisgerDetail(@Field("pmt_mode") int pmt_mode, @Field("branch_cd") int branch_cd,
            @Field("from_date") String from_date, @Field("to_date") String to_date, @Field("ac_cd") String ac_cd,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("PurchaseReturnRegisterDetail")
    Call<JsonObject> PurchaseReturnRegisterDetail(@Field("pmt_mode") int pmt_mode, @Field("branch_cd") int branch_cd,
            @Field("from_date") String from_date, @Field("to_date") String to_date, @Field("ac_cd") String ac_cd,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("MarginReport")
    Call<JsonObject> MarginReport(@Query("from_date") String type_name, @Query("to_date") String user_id, @Query("mode") String mode,
            @Query("sr_cd") String sr_cd, @Query("brnad_cd") String brnad_cd, @Query("model_cd") String model_cd,
            @Query("type_cd") String type_cd, @Query("v_type") int voucher_type, @Query("branch_cd") String branch_cd,
            @Query("bank_charges") boolean bank_charges, @Query("high_profit") boolean high_profit,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("MarginReportSummary")
    Call<JsonObject> MarginReportSummary(@Query("from_date") String type_name, @Query("to_date") String user_id, @Query("mode") String mode,
            @Query("sr_cd") String sr_cd, @Query("brnad_cd") String brnad_cd, @Query("model_cd") String model_cd,
            @Query("type_cd") String type_cd, @Query("v_type") int voucher_type, @Query("branch_cd") String branch_cd,
            @Query("bank_charges") boolean bank_charges, @Query("high_profit") boolean high_profit,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("MarginReportMonthWiseSummary")
    Call<JsonObject> MarginReportMonthWiseSummary(@Query("from_date") String type_name, @Query("to_date") String user_id, @Query("mode") String mode,
            @Query("sr_cd") String sr_cd, @Query("brnad_cd") String brnad_cd, @Query("model_cd") String model_cd,
            @Query("type_cd") String type_cd, @Query("v_type") int voucher_type, @Query("branch_cd") String branch_cd,
            @Query("bank_charges") boolean bank_charges, @Query("high_profit") boolean high_profit,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("MarginReportModelWise")
    Call<JsonObject> MarginReportMOdelWise(@Query("from_date") String type_name, @Query("to_date") String user_id, @Query("mode") String mode,
            @Query("brnad_cd") String brnad_cd, @Query("model_cd") String model_cd,
            @Query("type_cd") String type_cd, @Query("v_type") int voucher_type, @Query("branch_cd") String branch_cd,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("AveragePurchaseReport")
    Call<JsonObject> AveragePurchaseReport(@Query("from_date") String type_name, @Query("to_date") String user_id, @Query("mode") String mode,
            @Query("brnad_cd") String brnad_cd, @Query("model_cd") String model_cd,
            @Query("type_cd") String type_cd, @Query("v_type") int voucher_type, @Query("branch_cd") String branch_cd, @Query("ac_cd") String ac_cd,
            @Query("sub_type_cd") String sub_type_cd,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("AverageSalesReport")
    Call<JsonObject> AverageSalesReport(@Query("from_date") String type_name, @Query("to_date") String user_id, @Query("mode") String mode,
            @Query("brnad_cd") String brnad_cd, @Query("model_cd") String model_cd,
            @Query("type_cd") String type_cd, @Query("v_type") int voucher_type, @Query("branch_cd") String branch_cd, @Query("ac_cd") String ac_cd,
            @Query("sub_type_cd") String sub_type_cd,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("SnapShots")
    Call<JsonObject> SnapShots(@Query("from_date") String from_date, @Query("to_date") String to_date, @Query("branch_cd") String branch_cd
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("TypeWiseProftStatement")
    Call<JsonObject> TypeWiseProftStatement(@Query("from_date") String type_name, @Query("to_date") String user_id,
            @Query("type_cd") String type_cd, @Query("branch_cd") String branch_cd, @Query("model_cd") String model_cd, @Query("sub_type_cd") String sub_type_cd
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("TypeWiseBrandWiseProfitStatement")
    Call<JsonObject> TypeWiseBrandWiseProfitStatement(@Query("from_date") String type_name, @Query("to_date") String user_id,
            @Query("type_cd") String type_cd, @Query("branch_cd") String branch_cd, @Query("brand_cd") String brand_cd, @Query("sub_type_cd") String sub_type_cd
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("MarginReportByTag")
    Call<JsonObject> MarginReportByTag(@Query("tag_no") String tag_no,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("PurchaseRateByTag")
    Call<JsonObject> PurchaseRateByTag(@Query("tag_no") String tag_no,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("IMEWisePS")
    Call<JsonObject> IMEWisePS(@Query("from_date") String type_name, @Query("to_date") String user_id,
            @Query("model_cd") String model_cd, @Query("ac_cd") String ac_cd,
            @Query("type_cd") String type_cd, @Query("brand_cd") String brand_cd, @Query("bill_no") String bill_no,
            @Query("sub_type_cd") String sub_type_cd0, @Query("sr_cd") String sr_cd, @Query("branch_cd") String branch_cd
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("IMEWisePSSales")
    Call<JsonObject> IMEWisePSSales(@Query("from_date") String type_name, @Query("to_date") String user_id,
            @Query("model_cd") String model_cd, @Query("ac_cd") String ac_cd, @Query("type_cd") String type_cd,
            @Query("include") boolean include, @Query("brand_cd") String brand_cd, @Query("bill_no") String bill_no,
            @Query("sub_type_cd") String sub_type_cd, @Query("sr_cd") String sr_cd, @Query("branch_cd") String branch_cd
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("GetStockAdjustmentRegister")
    Call<JsonObject> GetStockAdjustmentRegister(@Field("type_cd") String type_cd, @Field("from_date") String from_date, @Field("to_date") String to_date,
            @Query("sr_cd") String sr_cd, @Query("brnad_cd") String brnad_cd, @Query("model_cd") String model_cd
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("ListBills")
    Call<JsonObject> ListBills(@Query("ac_cd") String ac_cd, @Query("sales") boolean sales,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @GET("ListBillsAdjsted")
    Call<JsonObject> ListBillsAdjsted(@Query("ac_cd") String ac_cd,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("UpdateBill")
    Call<JsonObject> UpdateBill(@Field("dr_doc_ref_no") String dr_doc_ref_no, @Field("cr_doc_ref_no") String cr_doc_ref_no,
            @Field("DR_DOC_CD") String DR_DOC_CD, @Field("CR_DOC_CD") String CR_DOC_CD,
            @Field("DR_INV_NO") String DR_INV_NO, @Field("CR_INV_NO") String CR_INV_NO,
            @Field("DR_AMT") String DR_AMT, @Field("CR_AMT") String CR_AMT,
            @Field("DR_SR_NO") String DR_SR_NO, @Field("CR_SR_NO") String CR_SR_NO, @Field("ac_cd") String ac_cd,@Query("db_name") String db_name, @Query("db_year") String db_year);

    @FormUrlEncoded
    @POST("ReverseBill")
    Call<JsonObject> ReverseBill(@Field("doc_ref_no") String doc_ref_no,@Field("db_name") String db_name, @Field("db_year") String db_year);

    @GET("GetModelwiseMonthWiseSalesStatement")
    Call<JsonObject> GetModelwiseMonthWiseSalesStatement(@Query("code") String code, @Query("mode") String mode, @Query("type_cd") String type_cd,
            @Query("GD_CD") String GD_CD, @Query("before_date") String before_date,
            @Query("after_date") String after_date, @Query("equal_date") String equal_date,
            @Query("date_mode") String date_mode, @Query("before_rate") String before_rate,
            @Query("after_rate") String after_rate, @Query("rate_mode") String rate_mode, @Query("branch_cd") String branch_cd, @Query("sub_type_cd") String sub_type_cd
            ,@Query("db_name") String db_name, @Query("db_year") String db_year);

}
