/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author bhaumik
 */
public class SchemeMasterModel {

    private String SCHEME_CD;
    private String SCHEME_NAME;
    private String TYPE_CD;
    private String USER_ID;

    public String getSCHEME_CD() {
        return SCHEME_CD;
    }

    public void setSCHEME_CD(String SCHEME_CD) {
        this.SCHEME_CD = SCHEME_CD;
    }

    public String getSCHEME_NAME() {
        return SCHEME_NAME;
    }

    public void setSCHEME_NAME(String SCHEME_NAME) {
        this.SCHEME_NAME = SCHEME_NAME;
    }

    public String getTYPE_CD() {
        return TYPE_CD;
    }

    public void setTYPE_CD(String TYPE_CD) {
        this.TYPE_CD = TYPE_CD;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

}
