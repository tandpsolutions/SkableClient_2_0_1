/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import model.BranchMasterModel;
import model.DBYearModel;
import retrofitAPI.UpdateInterface;
import skable.Constants;
import support.Library;

/**
 *
 * @author bhaumik
 */
public class SelectIP extends javax.swing.JFrame {

    Library lb = Library.getInstance();
    public static String password = "";
    private String test_cmp = null;

    /**
     * Creates new form Login
     */
    public SelectIP() {
        initComponents();

        File f = new File("System.properties");
        Properties properties = null;
        try {
            if (f.exists()) {
                properties = new Properties();
                properties.load(new FileReader(f));
                String ip = properties.getProperty("ip");
                Constants.COMPANY_NAME = properties.getProperty("company_name");
                Constants.MAIN_DB = properties.getProperty("main_db","skablemain");
                Constants.LOGIN_DB = properties.getProperty("login_db","skablelogindb");
                Constants.FOLDER_NEW = properties.getProperty("server_name","SkableServer2.0.1_2");
                
                jlblCmpName.setText(Constants.COMPANY_NAME);
                
                String[] ipList = ip.split(",");
                jComboBox3.removeAllItems();
                for (int i = 0; i < ipList.length; i++) {
                    jComboBox3.addItem(ipList[i]);
                }
            } else {
                System.out.println("system.properties not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validateLogin() {
        Constants.HOST1 = jComboBox3.getSelectedItem().toString();
        Constants.BASE_URL = "http://" + Constants.HOST1 + "/" + Constants.FOLDER_NEW + "/";
        Library.getInstance().makeConnection();
        try {
            setUpBaseData();
            SelectIP.this.dispose();
            Login lg = new Login();
            lg.setLocationRelativeTo(null);
            lg.setVisible(true);

        } catch (IOException ex) {
            Logger.getLogger(SelectIP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setUpBaseData() throws IOException {

        final UpdateInterface update1 = lb.getRetrofit().create(UpdateInterface.class);

        final JsonObject branchMaster = update1.GetBranchMaster(test_cmp).execute().body();
        final JsonObject systemVariables = update1.getStartUpData().execute().body();

        final JsonArray branchArray = branchMaster.getAsJsonArray("data");
        final JsonArray yearArray = branchMaster.getAsJsonArray("year");
        final JsonArray variables = systemVariables.getAsJsonArray("data");

        if (branchArray.size() > 0) {
            for (int i = 0; i < branchArray.size(); i++) {
                BranchMasterModel model = new Gson().fromJson(branchArray.get(i).getAsJsonObject().toString(), BranchMasterModel.class);
                Constants.BRANCH.add(model);
                Constants.branchMap.put(model.getBranch_cd(), model);
            }
        }

        if (yearArray.size() > 0) {
            for (int i = 0; i < yearArray.size(); i++) {
                DBYearModel model = new Gson().fromJson(yearArray.get(i).getAsJsonObject().toString(), DBYearModel.class);
                Constants.DBYMS.add(model);
            }
        }

        if (variables.size() > 0) {
            for (int i = 0; i < variables.size(); i++) {
                Constants.params.put(variables.get(i).getAsJsonObject().get("PARAM_NAME").getAsString(), variables.get(i).getAsJsonObject().get("PARAM_VALUE").getAsString());
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose(); //To change body of generated methods, choose Tools | Templates.
    }

    private void changeCompany() {
        test_cmp = "TEST";
        jlblCmpName.setText(test_cmp);
        try {
            Properties property = new Properties();
            property.put("logoString", "");
            AcrylLookAndFeel.setTheme(property);
            javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, e.getCause().getMessage());
        }
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
        jPanel2 = new javax.swing.JPanel();
        jbtnLogin = new javax.swing.JButton();
        jbtnExit = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jlblCmpName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jbtnLogin.setMnemonic('L');
        jbtnLogin.setText("Start");
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
        jbtnExit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbtnExitKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jbtnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(jbtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnExit)
                    .addComponent(jbtnLogin))
                .addContainerGap())
        );

        jLabel5.setText("IP");

        jComboBox3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox3KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jlblCmpName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlblCmpName, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblCmpName, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLoginActionPerformed
        // TODO add your handling code here:
        jbtnLogin.setEnabled(false);
        validateLogin();
    }//GEN-LAST:event_jbtnLoginActionPerformed

    private void jbtnLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbtnLoginKeyPressed
        // TODO add your handling code here:
        lb.enterClick(evt);
        if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
            if (evt.getKeyCode() == KeyEvent.VK_T) {
                changeCompany();
            }
        }
    }//GEN-LAST:event_jbtnLoginKeyPressed

    private void jbtnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExitActionPerformed
        // TODO add your handling code here:
        lb.confirmDialog("Do you want to exit from system?");
        if (lb.type) {
            System.exit(0);
        }
    }//GEN-LAST:event_jbtnExitActionPerformed

    private void jComboBox3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox3KeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jbtnLogin);
        if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
            if (evt.getKeyCode() == KeyEvent.VK_T) {
                changeCompany();
            }
        }
    }//GEN-LAST:event_jComboBox3KeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
            if (evt.getKeyCode() == KeyEvent.VK_T) {
                changeCompany();
            }
        }
    }//GEN-LAST:event_formKeyPressed

    private void jbtnExitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbtnExitKeyPressed
        // TODO add your handling code here:
        if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
            if (evt.getKeyCode() == KeyEvent.VK_T) {
                changeCompany();
            }
        }
    }//GEN-LAST:event_jbtnExitKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jbtnExit;
    public javax.swing.JButton jbtnLogin;
    private javax.swing.JLabel jlblCmpName;
    // End of variables declaration//GEN-END:variables
}
