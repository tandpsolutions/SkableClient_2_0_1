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
public class BateryMasterModel {

    private String BATTERY_CD;
    private String BATTERY_NAME;
    private String USER_ID;

    public String getBATTERY_CD() {
        return BATTERY_CD;
    }

    public void setBATTERY_CD(String BATTERY_CD) {
        this.BATTERY_CD = BATTERY_CD;
    }

    public String getBATTERY_NAME() {
        return BATTERY_NAME;
    }

    public void setBATTERY_NAME(String BATTERY_NAME) {
        this.BATTERY_NAME = BATTERY_NAME;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

}
