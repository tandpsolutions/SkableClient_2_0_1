/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import model.RefModel;
import model.SalesManMasterModel;
import model.TaxMasterModel;
import retrofit2.Call;
import retrofitAPI.RefralAPI;
import retrofitAPI.SalesmanAPI;
import retrofitAPI.StartUpAPI;
import skable.Constants;
import skable.SkableHome;
import support.Library;

/**
 *
 * @author bhaumik
 */
public class Login extends javax.swing.JFrame {

    Library lb = Library.getInstance();
    public static String password = "";

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        setUpData();
    }

    private void setUpData() {
        jComboBox1.removeAllItems();
        jComboBox2.removeAllItems();
        for (int i = 0; i < Constants.BRANCH.size(); i++) {
            jComboBox1.addItem(Constants.BRANCH.get(i).getBranch_name());
        }

        for (int i = 0; i < Constants.DBYMS.size(); i++) {
            jComboBox2.addItem(Constants.DBYMS.get(i).getDb_year());
        }
    }

    private void validateLogin() {
        Call<JsonObject> call = lb.getRetrofit().create(StartUpAPI.class).validateLogin(jtxtUserName.getText(), jtxtPassword.getText(), Constants.BRANCH.get(jComboBox1.getSelectedIndex()).getBranch_cd());
        try {
            jbtnLogin.setEnabled(true);
            JsonObject data = call.execute().body();
            System.out.println(data.toString());
            int status = data.get("result").getAsInt();
            if (status == 1) {
                password = jtxtPassword.getText();
                SkableHome.user_id = data.get("USER_ID").getAsString();
                SkableHome.user_name = jtxtUserName.getText().toUpperCase();
                SkableHome.user_grp_cd = data.get("USER_GRP_CD").getAsString();
                SkableHome.selected_branch = Constants.BRANCH.get(jComboBox1.getSelectedIndex());
                SkableHome.selected_year = jComboBox2.getSelectedItem().toString();
                SkableHome.db_name =Constants.DBYMS.get(jComboBox2.getSelectedIndex()).getDb_name();
                if (jComboBox2.getSelectedIndex() != 0) {
                    Constants.BASE_URL = "http://" + Constants.HOST1 + "/" + Constants.FOLDER + "/";
                } else {
                    Constants.BASE_URL = "http://" + Constants.HOST1 + "/" + Constants.FOLDER_NEW + "/";
                }
                jtxtPassword.setText("");
                Library.getInstance().makeConnection();
                SkableHome home = new SkableHome();
                setUpBaseData();
                home.setVisible(true);
                Login.this.dispose();
                
            } else {
                lb.showMessageDailog(data.get("Cause").getAsString());
                jbtnLogin.setEnabled(true);
            }
        } catch (IOException ex) {
            jbtnLogin.setEnabled(true);
            lb.showMessageDailog(ex.getMessage());
        }
    }

    private void setUpBaseData() throws IOException {

        final RefralAPI refralAPI = lb.getRetrofit().create(RefralAPI.class);
        final SalesmanAPI salesmanAPI = lb.getRetrofit().create(SalesmanAPI.class);

        final JsonObject refmaster = refralAPI.getReferalMaster(SkableHome.db_name,SkableHome.selected_year).execute().body();
        final JsonObject salesMan = salesmanAPI.GetSalesmanMaster(SkableHome.db_name,SkableHome.selected_year).execute().body();

        final JsonArray refMaster = refmaster.getAsJsonArray("data");
        final JsonArray salesmanMaster = salesMan.getAsJsonArray("data");

       

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
        getTaxMaster();
    }
    
    private void getTaxMaster() {
        Call<JsonObject> call = lb.getRetrofit().create(StartUpAPI.class).getDataFromServer("7",SkableHome.db_name,SkableHome.selected_year);
        try {
            JsonObject data = call.execute().body();
            System.out.println(data.toString());
            int status = data.get("result").getAsInt();
            if (status == 1) {
                Constants.TAX.clear();
                JsonArray array = data.getAsJsonArray("data");
                for (int i = 0; i < array.size(); i++) {
                    TaxMasterModel taxMasterModel = new Gson().fromJson(array.get(i), TaxMasterModel.class);
                    Constants.TAX.add(taxMasterModel);
                }
            } else {
            }
        } catch (Exception ex) {
            lb.showMessageDailog(ex.getMessage());
        }
    }

    @Override
    public void dispose() {
        super.dispose(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jtxtPassword = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtxtUserName = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jbtnLogin = new javax.swing.JButton();
        jbtnExit = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jtxtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtPasswordFocusGained(evt);
            }
        });
        jtxtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtPasswordKeyPressed(evt);
            }
        });

        jLabel1.setText("User Name");

        jLabel2.setText("Password");

        jtxtUserName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtUserNameFocusGained(evt);
            }
        });
        jtxtUserName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtUserNameKeyPressed(evt);
            }
        });

        jbtnLogin.setMnemonic('L');
        jbtnLogin.setText("Login");
        jbtnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnLoginActionPerformed(evt);
            }
        });
        jbtnLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbtnLoginKeyPressed(evt);
            }
        });

        jbtnExit.setMnemonic('E');
        jbtnExit.setText("Exit");
        jbtnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(jbtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnLogin)
                    .addComponent(jbtnExit))
                .addContainerGap())
        );

        jLabel3.setText("Branch");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox1KeyPressed(evt);
            }
        });

        jLabel4.setText("YEAR");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox2KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jtxtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jComboBox1, jLabel3});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtPasswordFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtPasswordFocusGained

    private void jtxtPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtPasswordKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jComboBox1);
    }//GEN-LAST:event_jtxtPasswordKeyPressed

    private void jtxtUserNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtUserNameFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtUserNameFocusGained

    private void jtxtUserNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtUserNameKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jtxtPassword);
    }//GEN-LAST:event_jtxtUserNameKeyPressed

    private void jbtnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLoginActionPerformed
        // TODO add your handling code here:
        jbtnLogin.setEnabled(false);
        validateLogin();
    }//GEN-LAST:event_jbtnLoginActionPerformed

    private void jbtnLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbtnLoginKeyPressed
        // TODO add your handling code here:
        lb.enterClick(evt);
    }//GEN-LAST:event_jbtnLoginKeyPressed

    private void jbtnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExitActionPerformed
        // TODO add your handling code here:
        lb.confirmDialog("Do you want to exit from system?");
        if (lb.type) {
            System.exit(0);
        }
    }//GEN-LAST:event_jbtnExitActionPerformed

    private void jComboBox1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox1KeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jComboBox2);
    }//GEN-LAST:event_jComboBox1KeyPressed

    private void jComboBox2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox2KeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jbtnLogin);
    }//GEN-LAST:event_jComboBox2KeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jbtnExit;
    public javax.swing.JButton jbtnLogin;
    private javax.swing.JPasswordField jtxtPassword;
    private javax.swing.JTextField jtxtUserName;
    // End of variables declaration//GEN-END:variables
}
