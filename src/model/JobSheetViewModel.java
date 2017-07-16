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
    @SerializedName("JOB_TYPE")
    @Expose
    private String jOBTYPE;
    @SerializedName("AC_NAME")
    @Expose
    private Object aCNAME;
    @SerializedName("JOB_DATE")
    @Expose
    private String jOBDATE;
    @SerializedName("JOB_STATUS")
    @Expose
    private String jOBSTATUS;
    @SerializedName("AC_CD")
    @Expose
    private String aCCD;
    @SerializedName("MODEL_CD")
    @Expose
    private String mODELCD;
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

    public String getJOBTYPE() {
        return jOBTYPE;
    }

    public void setJOBTYPE(String jOBTYPE) {
        this.jOBTYPE = jOBTYPE;
    }

    public Object getACNAME() {
        return aCNAME;
    }

    public void setACNAME(Object aCNAME) {
        this.aCNAME = aCNAME;
    }

    public String getJOBDATE() {
        return jOBDATE;
    }

    public void setJOBDATE(String jOBDATE) {
        this.jOBDATE = jOBDATE;
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

    public String getMODELCD() {
        return mODELCD;
    }

    public void setMODELCD(String mODELCD) {
        this.mODELCD = mODELCD;
    }

    public String getINITTIMESTAMP() {
        return iNITTIMESTAMP;
    }

    public void setINITTIMESTAMP(String iNITTIMESTAMP) {
        this.iNITTIMESTAMP = iNITTIMESTAMP;
    }
}
