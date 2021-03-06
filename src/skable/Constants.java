    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skable;

import java.util.ArrayList;
import java.util.HashMap;
import model.BranchMasterModel;
import model.DBYearModel;
import model.RefModel;
import model.SalesManMasterModel;
import model.TaxMasterModel;

/**
 *
 * @author bhaumikc
 */
public class Constants {

    public static String HOST1 = "";    
//    public static final String HOST1 = "127.0.0.1:8084";
    public static final String FOLDER = "SkableServer2.0.1";
//    public static final String FOLDER = "SkableServer2.0.1_Git";
    public static String FOLDER_NEW ;
//    public static final String FOLDER_NEW = "SkableServer2.0.1_2_Git";
    public static final String UPDATE_FOLDER = "Skable_ipearl";
    public static final String UPDATE_host = "tandpsolutions.in";
    public static final String VER = "2";
    public static String BASE_URL = "";
    public static String COMPANY_NAME = "";
    public static String MAIN_DB = "";
    public static String LOGIN_DB = "";
    public static String UPDATE_BASE_URL = "http://" + UPDATE_host + "/" + COMPANY_NAME + "/";
    public static final ArrayList<TaxMasterModel> TAX = new ArrayList<TaxMasterModel>();
    public static final ArrayList<BranchMasterModel> BRANCH = new ArrayList<BranchMasterModel>();
    public static final ArrayList<DBYearModel> DBYMS = new ArrayList<DBYearModel>();
    public static final ArrayList<RefModel> REFERAL = new ArrayList<RefModel>();
    public static final ArrayList<SalesManMasterModel> SALESMAN = new ArrayList<SalesManMasterModel>();
    public static final HashMap params = new HashMap();
    public static final HashMap<String,BranchMasterModel> branchMap = new HashMap();
}
