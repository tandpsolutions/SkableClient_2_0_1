/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author bhaumikshah
 */
public class JobSheetViewModel {

    @SerializedName("REF_NO")
    @Expose
    private String rEFNO;
    @SerializedName("INV_NO")
    @Expose
    private String iNVNO;
    @SerializedName("JOB_DATE")
    @Expose
    private String jOBDATE;
    @SerializedName("JOB_TYPE")
    @Expose
    private String jOBTYPE;
    @SerializedName("JOB_STATUS")
    @Expose
    private String jOBSTATUS;
    @SerializedName("AC_CD")
    @Expose
    private String aCCD;
    @SerializedName("FNAME")
    @Expose
    private String fNAME;
    @SerializedName("MODEL_CD")
    @Expose
    private String mODELCD;
    @SerializedName("IMEI_NO")
    @Expose
    private String iMEINO;
    @SerializedName("DEFECT_DESC")
    @Expose
    private String dEFECTDESC;
    @SerializedName("ITEMS")
    @Expose
    private String iTEMS;
    @SerializedName("ESTIMATED_AMT")
    @Expose
    private String eSTIMATEDAMT;
    @SerializedName("DEPOSIT_AMT")
    @Expose
    private String dEPOSITAMT;
    @SerializedName("BRANCH_CD")
    @Expose
    private String bRANCHCD;
    @SerializedName("EDIT_NO")
    @Expose
    private String eDITNO;
    @SerializedName("USER_ID")
    @Expose
    private String uSERID;
    @SerializedName("TIME_STAMP")
    @Expose
    private String tIMESTAMP;
    @SerializedName("INIT_TIMESTAMP")
    @Expose
    private String iNITTIMESTAMP;

    public String getREFNO() {
        return rEFNO;
    }

    public void setREFNO(String rEFNO) {
        this.rEFNO = rEFNO;
    }

    public String getINVNO() {
        return iNVNO;
    }

    public void setINVNO(String iNVNO) {
        this.iNVNO = iNVNO;
    }

    public String getJOBDATE() {
        return jOBDATE;
    }

    public void setJOBDATE(String jOBDATE) {
        this.jOBDATE = jOBDATE;
    }

    public String getJOBTYPE() {
        return jOBTYPE;
    }

    public void setJOBTYPE(String jOBTYPE) {
        this.jOBTYPE = jOBTYPE;
    }

    public String getJOBSTATUS() {
        return jOBSTATUS;
    }

    public void setJOBSTATUS(String jOBSTATUS) {
        this.jOBSTATUS = jOBSTATUS;
    }

    public String getACCD() {
        return aCCD;
    }

    public void setACCD(String aCCD) {
        this.aCCD = aCCD;
    }

    public String getFNAME() {
        return fNAME;
    }

    public void setFNAME(String fNAME) {
        this.fNAME = fNAME;
    }

    public String getMODELCD() {
        return mODELCD;
    }

    public void setMODELCD(String mODELCD) {
        this.mODELCD = mODELCD;
    }

    public String getIMEINO() {
        return iMEINO;
    }

    public void setIMEINO(String iMEINO) {
        this.iMEINO = iMEINO;
    }

    public String getDEFECTDESC() {
        return dEFECTDESC;
    }

    public void setDEFECTDESC(String dEFECTDESC) {
        this.dEFECTDESC = dEFECTDESC;
    }

    public String getITEMS() {
        return iTEMS;
    }

    public void setITEMS(String iTEMS) {
        this.iTEMS = iTEMS;
    }

    public String getESTIMATEDAMT() {
        return eSTIMATEDAMT;
    }

    public void setESTIMATEDAMT(String eSTIMATEDAMT) {
        this.eSTIMATEDAMT = eSTIMATEDAMT;
    }

    public String getDEPOSITAMT() {
        return dEPOSITAMT;
    }

    public void setDEPOSITAMT(String dEPOSITAMT) {
        this.dEPOSITAMT = dEPOSITAMT;
    }

    public String getBRANCHCD() {
        return bRANCHCD;
    }

    public void setBRANCHCD(String bRANCHCD) {
        this.bRANCHCD = bRANCHCD;
    }

    public String getEDITNO() {
        return eDITNO;
    }

    public void setEDITNO(String eDITNO) {
        this.eDITNO = eDITNO;
    }

    public String getUSERID() {
        return uSERID;
    }

    public void setUSERID(String uSERID) {
        this.uSERID = uSERID;
    }

    public String getTIMESTAMP() {
        return tIMESTAMP;
    }

    public void setTIMESTAMP(String tIMESTAMP) {
        this.tIMESTAMP = tIMESTAMP;
    }

    public String getINITTIMESTAMP() {
        return iNITTIMESTAMP;
    }

    public void setINITTIMESTAMP(String iNITTIMESTAMP) {
        this.iNITTIMESTAMP = iNITTIMESTAMP;
    }
}
