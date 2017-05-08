/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurchaseHeader {

    @SerializedName("REF_NO")
    @Expose
    private String REFNO;
    @SerializedName("INV_NO")
    @Expose
    private Integer INVNO;
    @SerializedName("V_TYPE")
    @Expose
    private Integer VTYPE;
    @SerializedName("AC_NAME")
    @Expose
    private String ACNAME;
    @SerializedName("V_DATE")
    @Expose
    private String VDATE;
    @SerializedName("BILL_NO")
    @Expose
    private String BILLNO;
    @SerializedName("NET_AMT")
    @Expose
    private double NETAMT;

    @SerializedName("IMEI_NO")
    @Expose
    private String IMEINO;

    @SerializedName("SR_NAME")
    @Expose
    private String ITEMNAME;

    @SerializedName("SERAIL_NO")
    @Expose
    private String SERAILNO;

    @SerializedName("REMARK")
    @Expose
    private String REMARK;

    @SerializedName("CASH_AMT")
    @Expose
    private double CASHAMT;

    @SerializedName("BANK_AMT")
    @Expose
    private double BANKAMT;

    @SerializedName("CARD_AMT")
    @Expose
    private double CARDAMT;

    @SerializedName("BAJAJ_AMT")
    @Expose
    private double BAJAJAMT;

    @SerializedName("SFID")
    @Expose
    private String SFID;

    @SerializedName("bank_charges")
    @Expose
    private String BankCharges;

    @SerializedName("BUY_BACK_AMT")
    @Expose
    private String BUY_BACK_AMT;

    @SerializedName("INS_AMT")
    @Expose
    private String INS_AMT;

    @SerializedName("AC_CD")
    @Expose
    private String ACCD;

    @SerializedName("PART_NO")
    @Expose
    private String PARTNO;

    @SerializedName("CARD_NAME")
    @Expose
    private String CARD_NAME;

    @SerializedName("MOBILE1")
    @Expose
    private String MOBILE;

    @SerializedName("BRANCH_CD")
    @Expose
    private int BRANCHCD;

    public int getBRANCHCD() {
        return BRANCHCD;
    }

    public void setBRANCHCD(int BRANCHCD) {
        this.BRANCHCD = BRANCHCD;
    }

    public String getACCD() {
        return ACCD;
    }

    public void setACCD(String ACCD) {
        this.ACCD = ACCD;
    }

    public String getBUY_BACK_AMT() {
        return BUY_BACK_AMT;
    }

    public void setBUY_BACK_AMT(String BUY_BACK_AMT) {
        this.BUY_BACK_AMT = BUY_BACK_AMT;
    }

    public String getINS_AMT() {
        return INS_AMT;
    }

    public void setINS_AMT(String INS_AMT) {
        this.INS_AMT = INS_AMT;
    }

    public String getBankCharges() {
        return BankCharges;
    }

    public void setBankCharges(String BankCharges) {
        this.BankCharges = BankCharges;
    }

    public String getIMEINO() {
        return IMEINO;
    }

    public void setIMEINO(String IMEINO) {
        this.IMEINO = IMEINO;
    }

    public String getITEMNAME() {
        return ITEMNAME;
    }

    public void setITEMNAME(String ITEMNAME) {
        this.ITEMNAME = ITEMNAME;
    }

    public String getSERAILNO() {
        return SERAILNO;
    }

    public void setSERAILNO(String SERAILNO) {
        this.SERAILNO = SERAILNO;
    }

    /**
     *
     * @return The REFNO
     */
    public String getREFNO() {
        return REFNO;
    }

    /**
     *
     * @param REFNO The REF_NO
     */
    public void setREFNO(String REFNO) {
        this.REFNO = REFNO;
    }

    /**
     *
     * @return The INVNO
     */
    public Integer getINVNO() {
        return INVNO;
    }

    /**
     *
     * @param INVNO The INV_NO
     */
    public void setINVNO(Integer INVNO) {
        this.INVNO = INVNO;
    }

    /**
     *
     * @return The VTYPE
     */
    public Integer getVTYPE() {
        return VTYPE;
    }

    /**
     *
     * @param VTYPE The V_TYPE
     */
    public void setVTYPE(Integer VTYPE) {
        this.VTYPE = VTYPE;
    }

    /**
     *
     * @return The ACNAME
     */
    public String getACNAME() {
        return ACNAME;
    }

    /**
     *
     * @param ACNAME The AC_NAME
     */
    public void setACNAME(String ACNAME) {
        this.ACNAME = ACNAME;
    }

    /**
     *
     * @return The VDATE
     */
    public String getVDATE() {
        return VDATE;
    }

    /**
     *
     * @param VDATE The V_DATE
     */
    public void setVDATE(String VDATE) {
        this.VDATE = VDATE;
    }

    /**
     *
     * @return The BILLNO
     */
    public String getBILLNO() {
        return BILLNO;
    }

    /**
     *
     * @param BILLNO The BILL_NO
     */
    public void setBILLNO(String BILLNO) {
        this.BILLNO = BILLNO;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public double getNETAMT() {
        return NETAMT;
    }

    public void setNETAMT(double NETAMT) {
        this.NETAMT = NETAMT;
    }

    public double getCASHAMT() {
        return CASHAMT;
    }

    public void setCASHAMT(double CASHAMT) {
        this.CASHAMT = CASHAMT;
    }

    public double getBANKAMT() {
        return BANKAMT;
    }

    public void setBANKAMT(double BANKAMT) {
        this.BANKAMT = BANKAMT;
    }

    public double getCARDAMT() {
        return CARDAMT;
    }

    public void setCARDAMT(double CARDAMT) {
        this.CARDAMT = CARDAMT;
    }

    public double getBAJAJAMT() {
        return BAJAJAMT;
    }

    public void setBAJAJAMT(double BAJAJAMT) {
        this.BAJAJAMT = BAJAJAMT;
    }

    public String getSFID() {
        return SFID;
    }

    public void setSFID(String SFID) {
        this.SFID = SFID;
    }

    public String getPARTNO() {
        return PARTNO;
    }

    public void setPARTNO(String PARTNO) {
        this.PARTNO = PARTNO;
    }

    public String getCARD_NAME() {
        return CARD_NAME;
    }

    public void setCARD_NAME(String CARD_NAME) {
        this.CARD_NAME = CARD_NAME;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

}
