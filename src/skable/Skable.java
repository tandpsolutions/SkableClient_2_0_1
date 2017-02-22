/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import login.Login;
import model.BranchMasterModel;
import model.RefModel;
import model.SalesManMasterModel;
import retrofitAPI.RefralAPI;
import retrofitAPI.SalesmanAPI;
import retrofitAPI.UpdateInterface;
import static skable.Constants.FOLDER;
import support.Library;
import utility.SwingFileDownloadHTTP;

/**
 *
 * @author bhaumik
 */
public class Skable {

    /**
     */
    public static String ver = "2";

    public static void main(String[] args) {
        // TODO code application logic here
        try {
            Properties property = new Properties();
            property.put("logoString", "");
            McWinLookAndFeel.setTheme(property);
            javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, e.getCause().getMessage());
        }
        final Library lb = Library.getInstance();
//
        try {
            UpdateInterface update = lb.getUpdateRetrofit().create(UpdateInterface.class);
            JsonObject data = update.getUpdateVersion(ver).execute().body();
            JsonArray array = data.getAsJsonArray("update");

            if (array.size() > 0) {
                if (!array.get(0).getAsJsonObject().get("UPDATE_VER").getAsString().equalsIgnoreCase(ver)) {
                    lb.confirmDialog("New Update has been found.\n Do you want to update it now?");
                    if (lb.type) {
                        final String ver = array.get(0).getAsJsonObject().get("UPDATE_VER").getAsString();
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                SwingFileDownloadHTTP sf = new SwingFileDownloadHTTP("http://www.tandpsolutions.in/update_ipearl/" + ver + ".zip");
                                sf.setTitle("Downloading...  Ver " + ver);
                                sf.setVisible(true);
                                sf.buttonDownloadActionPerformed();
                            }
                        });
                    } else {
                        System.exit(0);
                    }

                }
            } else {
                final UpdateInterface update1 = lb.getRetrofit().create(UpdateInterface.class);
                final RefralAPI refralAPI = lb.getRetrofit().create(RefralAPI.class);
                final SalesmanAPI salesmanAPI = lb.getRetrofit().create(SalesmanAPI.class);

                final JsonObject branchMaster = update1.GetBranchMaster().execute().body();
                final JsonObject refmaster = refralAPI.GetSalesmanMaster().execute().body();
                final JsonObject salesMan = salesmanAPI.GetSalesmanMaster().execute().body();

                final JsonArray branchArray = branchMaster.getAsJsonArray("data");
                final JsonArray refMaster = refmaster.getAsJsonArray("data");
                final JsonArray salesmanMaster = salesMan.getAsJsonArray("data");

                if (branchArray.size() > 0) {
                    for (int i = 0; i < branchArray.size(); i++) {
                        BranchMasterModel model = new Gson().fromJson(branchArray.get(i).getAsJsonObject().toString(), BranchMasterModel.class);
                        Constants.BRANCH.add(model);
                    }
                }
                if (refMaster.size() > 0) {
                    for (int i = 0; i < refMaster.size(); i++) {
                        RefModel model = new Gson().fromJson(refMaster.get(i).getAsJsonObject().toString(), RefModel.class);
                        Constants.REFERAL.add(model);
                    }
                }

                if (salesmanMaster.size() > 0) {
                    for (int i = 0; i < salesmanMaster.size(); i++) {
                        SalesManMasterModel model = new Gson().fromJson(salesmanMaster.get(i).getAsJsonObject().toString(), SalesManMasterModel.class);
                        Constants.SALESMAN.add(model);
                    }
                }
                startApplication();
            }
        } catch (IOException ex) {
            if (ex instanceof ConnectException) {
                try {
                    Constants.BASE_URL = "http://" + Constants.HOST2 + "/" + FOLDER + "/";
                    Library.getInstance().makeConnection();
                    final UpdateInterface update1 = lb.getRetrofit().create(UpdateInterface.class);
                    final RefralAPI refralAPI = lb.getRetrofit().create(RefralAPI.class);
                    final JsonObject branchMaster = update1.GetBranchMaster().execute().body();
                    final JsonObject refmaster = refralAPI.GetSalesmanMaster().execute().body();
                    final JsonArray branchArray = branchMaster.getAsJsonArray("data");
                    final JsonArray refMaster = refmaster.getAsJsonArray("data");
                    if (branchArray.size() > 0) {
                        for (int i = 0; i < branchArray.size(); i++) {
                            BranchMasterModel model = new Gson().fromJson(branchArray.get(i).getAsJsonObject().toString(), BranchMasterModel.class);
                            Constants.BRANCH.add(model);
                        }
                    }
                    if (refMaster.size() > 0) {
                        for (int i = 0; i < refMaster.size(); i++) {
                            RefModel model = new Gson().fromJson(refMaster.get(i).getAsJsonObject().toString(), RefModel.class);
                            Constants.REFERAL.add(model);
                        }
                    }
                    startApplication();
                } catch (IOException ex2) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    private static void startApplication() {
        Login lg = new Login();
        lg.setLocationRelativeTo(null);
        lg.setVisible(true);
    }

}
