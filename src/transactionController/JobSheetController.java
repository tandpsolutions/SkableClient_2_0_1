/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import masterController.CreateSalesAccount;
import model.AccountHead;
import model.JobSheetViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofitAPI.JobSheetAPI;
import retrofitAPI.StartUpAPI;
import skable.Constants;
import skable.SkableHome;
import support.Library;
import support.OurDateChooser;

/**
 *
 * @author bhaumik
 */
public class JobSheetController extends javax.swing.JDialog {

    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;
    Library lb = Library.getInstance();
    private String ref_no = "";
    boolean flag = false;
    JobSheetAPI jobSheetAPI = null;
    private String ac_cd = "";

    /**
     * Creates new form PurchaseController
     */
    public JobSheetController(java.awt.Frame parent, boolean modal, JobSheetAPI jobSheetAPI) {
        super(parent, modal);
        initComponents();

        if (jobSheetAPI == null) {
            jobSheetAPI = lb.getRetrofit().create(JobSheetAPI.class);
        }
        this.jobSheetAPI = jobSheetAPI;
        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
        setUpData();
        lb.setDateChooserPropertyInit(jtxtVouDate);
        flag = true;
        setTitle("Job Sheet");
        SkableHome.zoomTable.setToolTipOn(true);
    }

    private void setUpData() {
        jComboBox1.removeAllItems();
        for (int i = 0; i < Constants.BRANCH.size(); i++) {
            jComboBox1.addItem(Constants.BRANCH.get(i).getBranch_name());
        }
        jComboBox1.setSelectedItem(SkableHome.selected_branch.getBranch_name());

        if (SkableHome.user_grp_cd.equalsIgnoreCase("1")) {
            jComboBox1.setEnabled(true);
        }

        Call<JsonObject> call = jobSheetAPI.getJobType(SkableHome.db_name,SkableHome.selected_year);
        lb.addGlassPane(this);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> rspns) {
                lb.removeGlassPane(JobSheetController.this);
                if (rspns.isSuccessful()) {
                    final JsonObject ob = rspns.body();
                    final String[] json = ob.get("data").getAsString().split(",");
                    jcmbServiceType.removeAllItems();
                    jcmbServiceType.addItem("");
                    for (int i = 0; i < json.length; i++) {
                        jcmbServiceType.addItem(json[i]);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                lb.removeGlassPane(JobSheetController.this);
            }
        });
    }

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    public void setData(String ref_no) {
        this.ref_no = ref_no;


        if (!ref_no.equalsIgnoreCase("")) {
            try {
                JsonObject call;
                call = jobSheetAPI.getJobSheetDetail(ref_no,SkableHome.db_name,SkableHome.selected_year).execute().body();
                if (call != null) {
                    System.out.println(call.toString());
                    JsonObject object = call;

                    JsonArray array = object.get("data").getAsJsonArray();
                    try {
                        for (int i = 0; i < array.size(); i++) {
                            JobSheetController.this.ref_no = array.get(i).getAsJsonObject().get("REF_NO").getAsString();
                            jtxtVoucher.setText(array.get(i).getAsJsonObject().get("INV_NO").getAsString() + "");
                            jtxtVouDate.setText(lb.ConvertDateFormetForDBForConcurrency(array.get(i).getAsJsonObject().get("JOB_DATE").getAsString()));
                            JobSheetController.this.ref_no = array.get(i).getAsJsonObject().get("REF_NO").getAsString();
                            jlblUser.setText(array.get(i).getAsJsonObject().get("USER_ID").getAsString() + "");
                            jlblEditNo.setText(array.get(i).getAsJsonObject().get("EDIT_NO").getAsDouble() + "");
                            jlblTimeStamp.setText(array.get(i).getAsJsonObject().get("TIME_STAMP").getAsString());
                            jlblVday.setText(lb.setDay(jtxtVouDate));
                            jtxtAcName.setText(array.get(i).getAsJsonObject().get("FNAME").getAsString());
                            ac_cd = array.get(i).getAsJsonObject().get("AC_CD").getAsString();
                            jtxtModelName.setText(array.get(i).getAsJsonObject().get("MODEL_CD").getAsString());
                            jtxtImeiNo.setText(array.get(i).getAsJsonObject().get("IMEI_NO").getAsString());
                            jtxtEstimated.setText(array.get(i).getAsJsonObject().get("ESTIMATED_AMT").getAsString());
                            jtxtDeposit.setText(array.get(i).getAsJsonObject().get("DEPOSIT_AMT").getAsString());
                            jTextArea1.setText(array.get(i).getAsJsonObject().get("DEFECT_DESC").getAsString());
                            jcmbServiceType.setSelectedItem(array.get(i).getAsJsonObject().get("JOB_TYPE").getAsString());
                            jComboBox1.setSelectedIndex(array.get(i).getAsJsonObject().get("BRANCH_CD").getAsInt() - 1);

                            String[] items = array.get(i).getAsJsonObject().get("ITEMS").getAsString().split(",");
                            for (int j = 0; j < items.length; j++) {
                                Component[] data = jPanel2.getComponents();
                                for (int k = 0; k < data.length; k++) {
                                    if (((JCheckBox) data[k]).getName().equalsIgnoreCase(items[j])) {
                                        ((JCheckBox) data[k]).setSelected(true);
                                    }
                                }
                            }
                        }
                        
                    } catch (Exception ex) {
                        lb.printToLogFile("Exception", ex);
                    }

                }
            } catch (Exception ex) {
                lb.printToLogFile("Exception at getDataFor PurchaseBill", ex);
            }
        }
        JobSheetController.this.setVisible(true);
    }

    private void saveVoucher() {
        try {
            ArrayList<JobSheetViewModel> detail = new ArrayList<>();
            JobSheetViewModel model = new JobSheetViewModel();
            model.setREFNO(ref_no);
            model.setINVNO(jtxtVoucher.getText());
            model.setJOBDATE(lb.ConvertDateFormetForDB(jtxtVouDate.getText()));
            model.setJOBTYPE(jcmbServiceType.getSelectedItem().toString());
            model.setACCD(ac_cd);
            model.setMODELCD(jtxtModelName.getText());
            model.setDEFECTDESC(jTextArea1.getText());
            model.setIMEINO(jtxtImeiNo.getText());
            model.setESTIMATEDAMT(jtxtEstimated.getText());
            model.setDEPOSITAMT(jtxtDeposit.getText());
            model.setUSERID(SkableHome.user_id);
            Component[] data = jPanel2.getComponents();
            String items = "";
            for (int i = 0; i < data.length; i++) {
                if (((JCheckBox) data[i]).isSelected()) {
                    items += data[i].getName()+",";
                    System.out.println(data[i].getName());
                }

            }
            if(!items.isEmpty()){
                items = items.substring(0, items.length()-1);
            }
            model.setITEMS(items);
            model.setBRANCHCD(jComboBox1.getSelectedIndex() + 1 + "");
            detail.add(model);
            String detailJson = new Gson().toJson(detail);
            JsonObject addUpdaCall = jobSheetAPI.addUpdateJobSheet(detailJson,SkableHome.db_name,SkableHome.selected_year).execute().body();
            lb.addGlassPane(JobSheetController.this);

            lb.removeGlassPane(JobSheetController.this);
            if (addUpdaCall != null) {
                System.out.println(addUpdaCall.toString());
                JsonObject object = addUpdaCall;
                if (object.get("result").getAsInt() == 1) {
                    lb.showMessageDailog("Voucher saved successfully");
                    JobSheetController.this.dispose();
                } else {
                    lb.showMessageDailog(object.get("Cause").getAsString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(JobSheetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean validateVoucher() {
        if (!lb.checkDate(jtxtVouDate)) {
            lb.showMessageDailog("Invalid Date");
            return false;
        }

        if (jtxtAcName.getText().equalsIgnoreCase("")) {
            lb.showMessageDailog("Please select valid account");
            jtxtAcName.requestFocusInWindow();
            return false;
        }
        if (jtxtModelName.getText().equalsIgnoreCase("")) {
            lb.showMessageDailog("Enter valid Model Name");
            jtxtModelName.requestFocusInWindow();
            return false;
        }



        if (jcmbServiceType.getSelectedIndex() == 0) {
            lb.showMessageDailog("Please select Service Type");
            jcmbServiceType.requestFocusInWindow();
            return false;
        }

        return true;
    }

    private void setAccountDetailMobile(String param_cd, String value) {
        try {
            Call<JsonObject> call = lb.getRetrofit().create(StartUpAPI.class).getDataFromServer(param_cd, value.toUpperCase(),SkableHome.db_name,SkableHome.selected_year);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    lb.removeGlassPane(JobSheetController.this);
                    if (response.isSuccessful()) {
                        System.out.println(response.body().toString());
                        AccountHead header = new Gson().fromJson(response.body(), AccountHead.class);
                        if (header.getResult() == 1) {
                            SelectAccount sa = new SelectAccount(null, true);
                            sa.setLocationRelativeTo(null);
                            sa.fillData((ArrayList) header.getAccountHeader());
                            sa.setVisible(true);
                            if (sa.getReturnStatus() == SelectAccount.RET_OK) {
                                int row = sa.row;
                                if (row != -1) {
                                    ac_cd = header.getAccountHeader().get(row).getACCD();
                                    jtxtMobileNo.setText(header.getAccountHeader().get(row).getMOBILE1());
                                    jtxtAcName.setText(header.getAccountHeader().get(row).getFNAME());
                                    jtxtMobileNo.requestFocusInWindow();
                                }
                            } else {
                                jtxtAcName.requestFocusInWindow();
                            }
                        } else {
                            lb.showMessageDailog(response.body().get("Cause").getAsString().toString());
                        }
                    } else {
                        // handle request errors yourself
                        lb.showMessageDailog(response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                    lb.removeGlassPane(JobSheetController.this);
                }
            });
        } catch (Exception ex) {
            lb.printToLogFile("Exception at setData at account master in sales invoice", ex);
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

        cancelButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtxtVoucher = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jtxtVouDate = new javax.swing.JTextField();
        jBillDateBtn = new javax.swing.JButton();
        jlblVday = new javax.swing.JLabel();
        jtxtMobileNo = new javax.swing.JTextField();
        jtxtAcName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtxtModelName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jcmbServiceType = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jtxtImeiNo = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jcb0 = new javax.swing.JCheckBox();
        jcb1 = new javax.swing.JCheckBox();
        jcb3 = new javax.swing.JCheckBox();
        jcb4 = new javax.swing.JCheckBox();
        jcb6 = new javax.swing.JCheckBox();
        jcb2 = new javax.swing.JCheckBox();
        jcb5 = new javax.swing.JCheckBox();
        jcb7 = new javax.swing.JCheckBox();
        jcb8 = new javax.swing.JCheckBox();
        jcb9 = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        jtxtEstimated = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jtxtDeposit = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jlblUser = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jlblEditNo = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jlblTimeStamp = new javax.swing.JLabel();
        jbtnOK = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        cancelButton.setMnemonic('C');
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Voucher No.");

        jtxtVoucher.setEnabled(false);

        jLabel24.setText("Voucher Date");

        jtxtVouDate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtVouDateFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtVouDateFocusLost(evt);
            }
        });
        jtxtVouDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtVouDateKeyPressed(evt);
            }
        });

        jBillDateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBillDateBtnActionPerformed(evt);
            }
        });

        jlblVday.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jtxtMobileNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtMobileNoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtMobileNoFocusLost(evt);
            }
        });
        jtxtMobileNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtMobileNoKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtMobileNoKeyPressed(evt);
            }
        });

        jtxtAcName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtAcNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtAcNameFocusLost(evt);
            }
        });
        jtxtAcName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtAcNameKeyPressed(evt);
            }
        });

        jLabel2.setText("Mobile");

        jLabel4.setText("Problem");

        jLabel5.setText("Model Name");

        jtxtModelName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtModelNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtModelNameFocusLost(evt);
            }
        });
        jtxtModelName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtModelNameKeyPressed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTextArea1);

        jLabel3.setText("Branch");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox1KeyPressed(evt);
            }
        });

        jLabel8.setText("Account ");

        jLabel9.setText("Service Type");

        jcmbServiceType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcmbServiceType.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jcmbServiceTypeKeyPressed(evt);
            }
        });

        jLabel10.setText("IMEI No");

        jtxtImeiNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtImeiNoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtImeiNoFocusLost(evt);
            }
        });
        jtxtImeiNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtImeiNoKeyPressed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Items With Phone", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 0, 0)));

        jcb0.setText("Battery");
        jcb0.setName("0"); // NOI18N

        jcb1.setText("Charger");
        jcb1.setName("1"); // NOI18N

        jcb3.setText("M.M.C");
        jcb3.setName("3"); // NOI18N

        jcb4.setText("H.F");
        jcb4.setName("4"); // NOI18N

        jcb6.setText("Data Cable");
        jcb6.setName("6"); // NOI18N

        jcb2.setText("Back Cover");
        jcb2.setName("2"); // NOI18N

        jcb5.setText("Sim Card");
        jcb5.setName("5"); // NOI18N

        jcb7.setText("Flip Cover");
        jcb7.setName("7"); // NOI18N

        jcb8.setText("USB Dock");
        jcb8.setName("8"); // NOI18N

        jcb9.setText("Car Dock");
        jcb9.setName("9"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcb0)
                    .addComponent(jcb3)
                    .addComponent(jcb6)
                    .addComponent(jcb9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcb7)
                    .addComponent(jcb4)
                    .addComponent(jcb1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcb8, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb5)
                    .addComponent(jcb2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jcb0, jcb3, jcb6, jcb9});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jcb1, jcb4, jcb7});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jcb2, jcb5, jcb8});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcb0)
                    .addComponent(jcb1)
                    .addComponent(jcb2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcb3)
                    .addComponent(jcb4)
                    .addComponent(jcb5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcb6)
                    .addComponent(jcb7)
                    .addComponent(jcb8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcb9)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel11.setText("Estimated Rs");

        jtxtEstimated.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtEstimatedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtEstimatedFocusLost(evt);
            }
        });
        jtxtEstimated.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtEstimatedKeyPressed(evt);
            }
        });

        jLabel14.setText("Deposit Rs");

        jtxtDeposit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtDepositFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtDepositFocusLost(evt);
            }
        });
        jtxtDeposit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtDepositKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtDeposit)
                    .addComponent(jtxtEstimated)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtImeiNo)
                    .addComponent(jtxtModelName)
                    .addComponent(jtxtAcName)
                    .addComponent(jcmbServiceType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtxtMobileNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(jtxtVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtVouDate, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBillDateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlblVday, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(239, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel10, jLabel11, jLabel14, jLabel2, jLabel4, jLabel5, jLabel8, jLabel9});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jComboBox1)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtVouDate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBillDateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlblVday)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jcmbServiceType)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtMobileNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtAcName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtModelName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtImeiNo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtEstimated, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtDeposit, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jBillDateBtn, jLabel1, jLabel24, jcmbServiceType, jlblVday, jtxtAcName, jtxtImeiNo, jtxtMobileNo, jtxtModelName, jtxtVouDate, jtxtVoucher});

        jLabel12.setText("User :");

        jLabel13.setText("Edit NO :");

        jLabel15.setText("Last Updated : ");

        jbtnOK.setMnemonic('O');
        jbtnOK.setText("OK");
        jbtnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlblEditNo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlblTimeStamp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(96, 96, 96)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblEditNo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jlblTimeStamp, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(jbtnOK))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel12, jLabel13, jLabel15, jlblEditNo, jlblTimeStamp, jlblUser});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    private void jtxtVouDateFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtVouDateFocusGained
        // TODO add your handling code here:
        jtxtVouDate.selectAll();
    }//GEN-LAST:event_jtxtVouDateFocusGained

    private void jtxtVouDateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtVouDateFocusLost
        // TODO add your handling code here:
        try {
            if (jtxtVouDate.getText().contains("/")) {
                jtxtVouDate.setText(jtxtVouDate.getText().replace("/", ""));
            }
            if (jtxtVouDate.getText().length() == 8) {
                String temp = jtxtVouDate.getText();
                String setDate = (temp.substring(0, 2)).replace(temp.substring(0, 2), temp.substring(0, 2) + "/") + (temp.substring(2, 4)).replace(temp.substring(2, 4), temp.substring(2, 4) + "/") + temp.substring(4, temp.length());
                jtxtVouDate.setText(setDate);
            }
            //            if ((new SimpleDateFormat("dd/MM/yyyy").format(new Date(jtxtVouDate.getText().trim()))) != null) {
            //                jtxtBillDate.requestFocusInWindow();
            //            }
            jlblVday.setText(lb.setDay(jtxtVouDate));
        } catch (Exception ex) {
            jtxtVouDate.requestFocusInWindow();
        }
    }//GEN-LAST:event_jtxtVouDateFocusLost

    private void jtxtVouDateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtVouDateKeyPressed
        lb.enterFocus(evt, jcmbServiceType);
    }//GEN-LAST:event_jtxtVouDateKeyPressed

    private void jBillDateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBillDateBtnActionPerformed
        // TODO add your handling code here:
        OurDateChooser odc = new OurDateChooser();
        odc.setnextFocus(jtxtVouDate);
        odc.setFormat("dd/MM/yyyy");
        JPanel jp = new JPanel();
        this.add(jp);
        jp.setBounds(jtxtVouDate.getX() - 200, jPanel1.getY() + 150, jtxtVouDate.getX() + odc.getWidth(), jtxtVouDate.getY() + odc.getHeight());
        odc.setLocation(0, 0);
        odc.showDialog(jp, "Select Date");
    }//GEN-LAST:event_jBillDateBtnActionPerformed

    private void jbtnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnOKActionPerformed
        // TODO add your handling code here:
        if (validateVoucher()) {
            saveVoucher();
        }
    }//GEN-LAST:event_jbtnOKActionPerformed

    private void jtxtAcNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtAcNameKeyPressed
        // TODO add your handling code here:
        if (lb.isEnter(evt)) {
            if (lb.isBlank(jtxtAcName)) {
                lb.confirmDialog("Do you want to create new account?");
                if (lb.type) {
                    CreateSalesAccount bmc = new CreateSalesAccount(null, true);
                    bmc.setLocationRelativeTo(null);

                    bmc.setVisible(true);
                    if (bmc.getReturnStatus() == RET_OK) {
                        ac_cd = bmc.ac_cd;
                        jtxtAcName.setText(bmc.account.getFNAME());
                        jtxtModelName.requestFocusInWindow();
                    }
                } else {
                    ac_cd = "";
                }
            } else {
                setAccountDetailMobile("2", jtxtAcName.getText());
            }
        }
    }//GEN-LAST:event_jtxtAcNameKeyPressed

    private void jtxtModelNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtModelNameFocusLost
        // TODO add your handling code here:
        lb.toUpper(evt);
    }//GEN-LAST:event_jtxtModelNameFocusLost

    private void jtxtModelNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtModelNameKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jtxtImeiNo);
    }//GEN-LAST:event_jtxtModelNameKeyPressed

    private void jtxtMobileNoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtMobileNoFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtMobileNoFocusGained

    private void jtxtMobileNoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtMobileNoFocusLost
        // TODO add your handling code here:
        lb.toInteger(evt);
    }//GEN-LAST:event_jtxtMobileNoFocusLost

    private void jtxtMobileNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtMobileNoKeyPressed
        // TODO add your handling code here:
        if (lb.isEnter(evt) && !lb.isBlank(jtxtMobileNo)) {
            lb.addGlassPane(this);
            Call<JsonObject> call = jobSheetAPI.getDataFromServer(jtxtMobileNo.getText(), "23",SkableHome.db_name,SkableHome.selected_year);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> rspns) {
                    lb.removeGlassPane(JobSheetController.this);
                    if (rspns.isSuccessful()) {
                        JsonArray array = rspns.body().getAsJsonArray("data");
                        if (array.size() > 0) {
                            ac_cd = (array.get(0).getAsJsonObject().get("AC_CD").getAsString());
                            jtxtAcName.setText(array.get(0).getAsJsonObject().get("FNAME").getAsString());

                            jtxtModelName.requestFocusInWindow();
                        } else {
                            ac_cd = "";
                            jtxtAcName.setText("");
                            lb.confirmDialog("Mobile does not exist.\nDo you want to create new account?");
                            if (lb.type) {
                                CreateSalesAccount bmc = new CreateSalesAccount(null, true);
                                bmc.setLocationRelativeTo(null);
                                bmc.setMobileNumber(jtxtMobileNo.getText());
                                bmc.setVisible(true);
                                if (bmc.getReturnStatus() == RET_OK) {
                                    ac_cd = bmc.ac_cd;
                                    jtxtAcName.setText(bmc.account.getFNAME());
                                    jtxtModelName.requestFocusInWindow();
                                } else {
                                    jtxtMobileNo.requestFocusInWindow();
                                }
                            } else {
                                ac_cd = "";
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                    lb.removeGlassPane(JobSheetController.this);
                }
            });
        } else {
            if (lb.isEnter(evt)) {
                lb.enterFocus(evt, jtxtAcName);
            }
        }
    }//GEN-LAST:event_jtxtMobileNoKeyPressed

    private void jtxtMobileNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtMobileNoKeyTyped
        // TODO add your handling code here:
        lb.onlyNumber(evt, -1);
    }//GEN-LAST:event_jtxtMobileNoKeyTyped

    private void jtxtAcNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtAcNameFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtAcNameFocusGained

    private void jtxtAcNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtAcNameFocusLost
        // TODO add your handling code here:
        lb.toUpper(evt);
    }//GEN-LAST:event_jtxtAcNameFocusLost

    private void jtxtModelNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtModelNameFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtModelNameFocusGained

    private void jComboBox1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox1KeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jtxtMobileNo);
    }//GEN-LAST:event_jComboBox1KeyPressed

    private void jcmbServiceTypeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcmbServiceTypeKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jtxtMobileNo);
    }//GEN-LAST:event_jcmbServiceTypeKeyPressed

    private void jtxtImeiNoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtImeiNoFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtImeiNoFocusGained

    private void jtxtImeiNoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtImeiNoFocusLost
        // TODO add your handling code here:
        lb.toUpper(evt);
    }//GEN-LAST:event_jtxtImeiNoFocusLost

    private void jtxtImeiNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtImeiNoKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jTextArea1);
    }//GEN-LAST:event_jtxtImeiNoKeyPressed

    private void jtxtEstimatedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtEstimatedFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtEstimatedFocusGained

    private void jtxtEstimatedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtEstimatedFocusLost
        // TODO add your handling code here:
        lb.toDouble(evt);
    }//GEN-LAST:event_jtxtEstimatedFocusLost

    private void jtxtEstimatedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtEstimatedKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jtxtDeposit);
    }//GEN-LAST:event_jtxtEstimatedKeyPressed

    private void jtxtDepositFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtDepositFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtDepositFocusGained

    private void jtxtDepositFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtDepositFocusLost
        // TODO add your handling code here:
        lb.toDouble(evt);
    }//GEN-LAST:event_jtxtDepositFocusLost

    private void jtxtDepositKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtDepositKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jbtnOK);
    }//GEN-LAST:event_jtxtDepositKeyPressed

    private void jTextArea1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea1KeyPressed
        // TODO add your handling code here:
        if (lb.isEnter(evt)) {
            if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
                jtxtEstimated.requestFocusInWindow();
            }
        }
    }//GEN-LAST:event_jTextArea1KeyPressed

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton jBillDateBtn;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton jbtnOK;
    private javax.swing.JCheckBox jcb0;
    private javax.swing.JCheckBox jcb1;
    private javax.swing.JCheckBox jcb2;
    private javax.swing.JCheckBox jcb3;
    private javax.swing.JCheckBox jcb4;
    private javax.swing.JCheckBox jcb5;
    private javax.swing.JCheckBox jcb6;
    private javax.swing.JCheckBox jcb7;
    private javax.swing.JCheckBox jcb8;
    private javax.swing.JCheckBox jcb9;
    private javax.swing.JComboBox jcmbServiceType;
    private javax.swing.JLabel jlblEditNo;
    private javax.swing.JLabel jlblTimeStamp;
    private javax.swing.JLabel jlblUser;
    private javax.swing.JLabel jlblVday;
    private javax.swing.JTextField jtxtAcName;
    private javax.swing.JTextField jtxtDeposit;
    private javax.swing.JTextField jtxtEstimated;
    private javax.swing.JTextField jtxtImeiNo;
    private javax.swing.JTextField jtxtMobileNo;
    private javax.swing.JTextField jtxtModelName;
    private javax.swing.JTextField jtxtVouDate;
    private javax.swing.JTextField jtxtVoucher;
    // End of variables declaration//GEN-END:variables
    private int returnStatus = RET_CANCEL;
}
