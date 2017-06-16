/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package masterController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import masterView.AccountMasterView;
import model.AccountMasterModel;
import model.GroupMasterModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofitAPI.AccountMasterAPI;
import retrofitAPI.GroupMasterAPI;
import skable.Constants;
import skable.SkableHome;
import support.Library;

/**
 *
 * @author bhaumik
 */
public class AccountMasterController extends javax.swing.JDialog {

    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;
    Library lb = Library.getInstance();
    public String ac_cd = "";
    AccountMasterView acv;
    private AccountMasterAPI api;
    public ArrayList<GroupMasterModel> detail;
    private GroupMasterAPI groupAPI;
    private AccountMasterModel ac;
    private DefaultTableModel dtm = null;

    /**
     * Creates new form CreateAccount
     */
    public AccountMasterController(java.awt.Frame parent, boolean modal, AccountMasterView acv) {
        super(parent, modal);
        initComponents();
        dtm = (DefaultTableModel) jTable1.getModel();
        this.acv = acv;
        api = lb.getRetrofit().create(AccountMasterAPI.class);
        groupAPI = lb.getRetrofit().create(GroupMasterAPI.class);
        getData();

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
    }

    private void setUpData() {
        jComboBox2.removeAllItems();
        jComboBox2.addItem("");
        for (int i = 0; i < Constants.REFERAL.size(); i++) {
            jComboBox2.addItem(Constants.REFERAL.get(i).getREF_NAME());
        }

    }

    private void getData() {
        Call<JsonObject> call = groupAPI.GetGroupMaster();
        lb.addGlassPane(this);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> rspns) {
                lb.removeGlassPane(AccountMasterController.this);
                if (rspns.isSuccessful()) {
                    JsonObject result = rspns.body();
                    if (result.get("result").getAsInt() == 1) {
                        TypeToken<List<GroupMasterModel>> token = new TypeToken<List<GroupMasterModel>>() {
                        };
                        detail = new Gson().fromJson(result.get("data"), token.getType());
                        setData();
                        ac_cd = ac.getAC_CD();
                        getAccountMaster();
                    } else {
                        lb.showMessageDailog(rspns.body().get("Cause").getAsString());
                    }
                } else {
                    lb.showMessageDailog(rspns.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                lb.removeGlassPane(AccountMasterController.this);
            }
        });
    }

    private void getAccountMaster() {
        Call<JsonObject> call = api.getAccountMasterCode(ac_cd);
        lb.addGlassPane(this);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> rspns) {
                lb.removeGlassPane(AccountMasterController.this);
                if (rspns.isSuccessful()) {
                    JsonObject result = rspns.body();
                    if (result.get("result").getAsInt() == 1) {
                        TypeToken<List<AccountMasterModel>> token = new TypeToken<List<AccountMasterModel>>() {
                        };
                        ArrayList<AccountMasterModel> detailAc = new Gson().fromJson(result.get("data"), token.getType());
                        dtm.setRowCount(0);
                        for (int i = 0; i < detailAc.size(); i++) {
                            final AccountMasterModel ac = detailAc.get(i);
                            ac_cd = ac.getAC_CD();
                            jtxtName.setText(ac.getFNAME());
                            jcmbHeadGroup.setSelectedItem(ac.getGROUP_NAME());
                            jtxtMobile.setText(ac.getMOBILE1());
                            jtxtCST.setText(ac.getCST());
                            jtxtTin.setText(ac.getTIN());
                            jtxtEmail.setText(ac.getEMAIL());
                            jtxtRefBy.setText(ac.getREF_BY());
                            jtxtCardNo.setText(ac.getCARD_NO());
                            jtxtBal.setText(lb.Convert2DecFmt(ac.getOPB_AMT()));
                            jcmbEffect.setSelectedIndex(ac.getOPB_EFF());
                            Vector row = new Vector();
                            row.add(i + 1);
                            row.add(ac.getADD1());
                            dtm.addRow(row);
                        }
                    } else {
                        lb.showMessageDailog(rspns.body().get("Cause").getAsString());
                    }
                } else {
                    lb.showMessageDailog(rspns.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                lb.removeGlassPane(AccountMasterController.this);
            }
        });
    }

    private void setData() {
        jcmbHeadGroup.removeAllItems();
        for (int i = 0; i < detail.size(); i++) {
            jcmbHeadGroup.addItem(detail.get(i).getGROUP_NAME());
        }
    }

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    public void getData(AccountMasterModel ac) {
        this.ac = ac;

    }

    private boolean validateForm() {
        boolean flag = true;
        if (lb.isBlank(jtxtName)) {
            jtxtName.requestFocusInWindow();
            JOptionPane.showMessageDialog(this, "Customer name can not be left blank", "Customer", JOptionPane.OK_OPTION);
            return false;
        }

        if (lb.isBlank(jtxtMobile)) {
            jtxtMobile.requestFocusInWindow();
            JOptionPane.showMessageDialog(this, "Mobile nulber can not be left blank", "Customer", JOptionPane.OK_OPTION);
            return false;
        }

        if (jTable1.getRowCount() == 0) {
            jtxtAddress.requestFocusInWindow();
            JOptionPane.showMessageDialog(this, "Address can not be left blank", "Customer", JOptionPane.OK_OPTION);
            return false;
        }

//        if (!jtxtTin.getText().equalsIgnoreCase("")) {
//            if (!lb.isBlank(jtxtTin)) {
//                if (jtxtTin.getText().trim().length() != 16) {
//                    JOptionPane.showMessageDialog(null, "Card number must be length of 16");
//                    jtxtTin.requestFocusInWindow();
//                    return false;
//                }
//            }
//        }
        return flag;
    }

    private void saveAccount() {
        if (!jtxtName.getText().isEmpty()) {
            AccountMasterModel account = new AccountMasterModel();
            account.setAC_CD(ac_cd);
            account.setFNAME(jtxtName.getText());
            final ArrayList<String> address = new ArrayList<>();
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                address.add(jTable1.getValueAt(i, 1).toString());
            }
            account.setAddress(address);
            account.setEMAIL(jtxtEmail.getText());
            account.setMOBILE1(jtxtMobile.getText());
            account.setGRP_CD(detail.get(jcmbHeadGroup.getSelectedIndex()).getGRP_CD());
            account.setCST(jtxtCST.getText());
            account.setTIN(jtxtTin.getText());
            account.setCARD_NO(jtxtCardNo.getText());
            account.setOPB_AMT(lb.isNumber(jtxtBal));
            account.setOPB_EFF(jcmbEffect.getSelectedIndex());
            account.setREF_BY(jtxtRefBy.getText());
            if (jComboBox2.getSelectedIndex() > 0) {
                account.setRef_cd(Constants.REFERAL.get(jComboBox2.getSelectedIndex() - 1).getREF_CD());
            } else {
                account.setRef_cd("");

            }
            saveVoucher(account);
        }
    }

    private void saveVoucher(AccountMasterModel acc) {
        Call<JsonObject> call = api.AddUpdateAccountMaster(new Gson().toJson(acc), SkableHome.selected_year);
        lb.addGlassPane(this);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> rspns) {
                lb.addGlassPane(AccountMasterController.this);
                if (rspns.isSuccessful()) {
                    if (rspns.body().get("result").getAsInt() == 1) {
                        lb.showMessageDailog("Voucher Save Successfully");
                        if (acv != null) {
                            acv.getData();
                        }
                        AccountMasterController.this.dispose();
                    } else {
                    }
                } else {
                    lb.showMessageDailog(rspns.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                lb.addGlassPane(AccountMasterController.this);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtxtName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtxtMobile = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtEmail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtxtCST = new javax.swing.JTextField();
        jtxtTin = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jcmbHeadGroup = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jtxtCardNo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jtxtBal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jcmbEffect = new javax.swing.JComboBox();
        okButton3 = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jtxtRefBy = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtxtAddress = new javax.swing.JTextArea();
        jbtnAdd = new javax.swing.JButton();
        jbtnSave = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jLabel1.setText("Name");

        jtxtName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtNameFocusLost(evt);
            }
        });
        jtxtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtNameKeyPressed(evt);
            }
        });

        jLabel3.setText("Mobile");

        jtxtMobile.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtMobileFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtMobileFocusLost(evt);
            }
        });
        jtxtMobile.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtMobileKeyPressed(evt);
            }
        });

        jLabel4.setText("Email");

        jtxtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtEmailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtEmailFocusLost(evt);
            }
        });
        jtxtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtEmailKeyPressed(evt);
            }
        });

        jLabel5.setText("CST");

        jtxtCST.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtCSTFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtCSTFocusLost(evt);
            }
        });
        jtxtCST.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtCSTKeyPressed(evt);
            }
        });

        jtxtTin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtTinFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtTinFocusLost(evt);
            }
        });
        jtxtTin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtTinKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtTinKeyPressed(evt);
            }
        });

        jLabel6.setText("Tin No");

        jLabel7.setText("Group");

        jcmbHeadGroup.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcmbHeadGroup.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcmbHeadGroupItemStateChanged(evt);
            }
        });
        jcmbHeadGroup.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jcmbHeadGroupFocusGained(evt);
            }
        });
        jcmbHeadGroup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jcmbHeadGroupKeyPressed(evt);
            }
        });

        jLabel8.setText("Card No");

        jtxtCardNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtCardNoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtCardNoFocusLost(evt);
            }
        });
        jtxtCardNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtCardNoKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtCardNoKeyPressed(evt);
            }
        });

        jLabel11.setText("Balance");

        jtxtBal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtBalFocusLost(evt);
            }
        });
        jtxtBal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtBalKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtBalKeyPressed(evt);
            }
        });

        jLabel12.setText("Effect");

        jcmbEffect.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DR", "CR" }));
        jcmbEffect.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jcmbEffectKeyPressed(evt);
            }
        });

        okButton3.setText("Cancel");
        okButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButton3ActionPerformed(evt);
            }
        });

        jLabel31.setText("Ref Name");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jComboBox2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox2KeyPressed(evt);
            }
        });

        jLabel9.setText("Ref By");

        jtxtRefBy.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtRefByFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtRefByFocusLost(evt);
            }
        });
        jtxtRefBy.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtRefByKeyPressed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "sr_no", "Address"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(0);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
        }

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jtxtAddress.setColumns(20);
        jtxtAddress.setRows(5);
        jtxtAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtAddressKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jtxtAddress);

        jbtnAdd.setText("Add");
        jbtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAddActionPerformed(evt);
            }
        });
        jbtnAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbtnAddKeyPressed(evt);
            }
        });

        jbtnSave.setText("OK");
        jbtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSaveActionPerformed(evt);
            }
        });
        jbtnSave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbtnSaveKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(83, 83, 83)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jtxtName, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jcmbEffect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbtnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jtxtCardNo, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtxtBal, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtxtCST, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtxtTin, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jcmbHeadGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtxtMobile, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtxtRefBy, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(okButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel11, jLabel12, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jcmbHeadGroup, jtxtBal, jtxtCST, jtxtCardNo, jtxtEmail, jtxtMobile, jtxtName, jtxtTin});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcmbHeadGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtRefBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtMobile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtCST, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtCardNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtxtBal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jcmbEffect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtnAdd)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                        .addGap(59, 59, 59))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(okButton3)
                            .addComponent(jbtnSave))
                        .addContainerGap())))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jtxtName});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel6, jtxtTin});

        

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    private void jtxtNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtNameFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtNameFocusGained

    private void jtxtNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtNameFocusLost
        // TODO add your handling code here:
        lb.toUpper(evt);
    }//GEN-LAST:event_jtxtNameFocusLost

    private void jtxtNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtNameKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jcmbHeadGroup);
    }//GEN-LAST:event_jtxtNameKeyPressed

    private void jtxtMobileFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtMobileFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtMobileFocusGained

    private void jtxtMobileFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtMobileFocusLost
        // TODO add your handling code here:
        lb.toUpper(evt);
    }//GEN-LAST:event_jtxtMobileFocusLost

    private void jtxtMobileKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtMobileKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jtxtEmail);
    }//GEN-LAST:event_jtxtMobileKeyPressed

    private void jtxtEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtEmailFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtEmailFocusGained

    private void jtxtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtEmailFocusLost
        // TODO add your handling code here:
        lb.toUpper(evt);
    }//GEN-LAST:event_jtxtEmailFocusLost

    private void jtxtEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtEmailKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jtxtCST);
    }//GEN-LAST:event_jtxtEmailKeyPressed

    private void jtxtCSTFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtCSTFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtCSTFocusGained

    private void jtxtCSTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtCSTFocusLost
        // TODO add your handling code here:
        lb.toUpper(evt);
    }//GEN-LAST:event_jtxtCSTFocusLost

    private void jtxtCSTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtCSTKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jtxtTin);
    }//GEN-LAST:event_jtxtCSTKeyPressed

    private void jtxtTinFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtTinFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtTinFocusGained

    private void jtxtTinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtTinFocusLost
        // TODO add your handling code here:
        lb.toUpper(evt);
    }//GEN-LAST:event_jtxtTinFocusLost

    private void jtxtTinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtTinKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jtxtCardNo);
    }//GEN-LAST:event_jtxtTinKeyPressed

    private void jtxtTinKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtTinKeyTyped
        // TODO add your handling code here:
        lb.fixLength(evt, 16);
    }//GEN-LAST:event_jtxtTinKeyTyped

    private void jcmbHeadGroupItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcmbHeadGroupItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_jcmbHeadGroupItemStateChanged

    private void jcmbHeadGroupFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jcmbHeadGroupFocusGained
        // TODO add your handling code here:
        jcmbHeadGroupItemStateChanged(null);
    }//GEN-LAST:event_jcmbHeadGroupFocusGained

    private void jcmbHeadGroupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcmbHeadGroupKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jtxtRefBy);
    }//GEN-LAST:event_jcmbHeadGroupKeyPressed

    private void jtxtCardNoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtCardNoFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtCardNoFocusGained

    private void jtxtCardNoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtCardNoFocusLost
        // TODO add your handling code here:
        lb.toUpper(evt);
    }//GEN-LAST:event_jtxtCardNoFocusLost

    private void jtxtCardNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtCardNoKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jtxtBal);
    }//GEN-LAST:event_jtxtCardNoKeyPressed

    private void jtxtCardNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtCardNoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCardNoKeyTyped

    private void jtxtBalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtBalFocusLost
        // TODO add your handling code here:
        lb.toDouble(evt);
    }//GEN-LAST:event_jtxtBalFocusLost

    private void jtxtBalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtBalKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jcmbEffect);
    }//GEN-LAST:event_jtxtBalKeyPressed

    private void jtxtBalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtBalKeyTyped
        // TODO add your handling code here:
        lb.onlyNumber(evt, 10);
    }//GEN-LAST:event_jtxtBalKeyTyped

    private void jcmbEffectKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcmbEffectKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jbtnSave);
    }//GEN-LAST:event_jcmbEffectKeyPressed

    private void okButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButton3ActionPerformed
        // TODO add your handling code here:
        doClose(RET_CANCEL);
    }//GEN-LAST:event_okButton3ActionPerformed

    private void jComboBox2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2KeyPressed

    private void jtxtRefByFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtRefByFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtRefByFocusGained

    private void jtxtRefByFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtRefByFocusLost
        // TODO add your handling code here:
        lb.toUpper(evt);
    }//GEN-LAST:event_jtxtRefByFocusLost

    private void jtxtRefByKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtRefByKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jtxtMobile);
    }//GEN-LAST:event_jtxtRefByKeyPressed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = jTable1.getSelectedRow();
            if (row != -1) {
                jtxtAddress.setText(jTable1.getValueAt(row, 1).toString());
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jbtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAddActionPerformed
        // TODO add your handling code here:
        int row = jTable1.getSelectedRow();
        if (row != -1) {
            jTable1.setValueAt(jtxtAddress.getText(), row, 1);
        } else {
            Vector row1 = new Vector();
            row1.add(jTable1.getRowCount()+1);
            row1.add(jtxtAddress.getText().toUpperCase());
            dtm.addRow(row1);
        }
        lb.confirmDialog("do you want to add address?");
        if (lb.type) {
            jtxtAddress.setText("");
            jtxtAddress.requestFocusInWindow();
        } else {
            jbtnSave.requestFocusInWindow();
        }
    }//GEN-LAST:event_jbtnAddActionPerformed

    private void jtxtAddressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtAddressKeyPressed
        // TODO add your handling code here:
        if (lb.isEnter(evt)) {
            if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
                jbtnAdd.requestFocusInWindow();
            }
        }
    }//GEN-LAST:event_jtxtAddressKeyPressed

    private void jbtnAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbtnAddKeyPressed
        // TODO add your handling code here:
        lb.enterClick(evt);
    }//GEN-LAST:event_jbtnAddKeyPressed

    private void jbtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSaveActionPerformed
        // TODO add your handling code here:
        doClose(RET_OK);
    }//GEN-LAST:event_jbtnSaveActionPerformed

    private void jbtnSaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbtnSaveKeyPressed
        // TODO add your handling code here:
        lb.enterClick(evt);
    }//GEN-LAST:event_jbtnSaveKeyPressed

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        if (returnStatus == RET_OK) {
            if (validateForm()) {
                saveAccount();
                setVisible(false);

            }
        } else {
            setVisible(false);
            dispose();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton jbtnAdd;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JComboBox jcmbEffect;
    private javax.swing.JComboBox jcmbHeadGroup;
    private javax.swing.JTextArea jtxtAddress;
    private javax.swing.JTextField jtxtBal;
    public javax.swing.JTextField jtxtCST;
    public javax.swing.JTextField jtxtCardNo;
    public javax.swing.JTextField jtxtEmail;
    public javax.swing.JTextField jtxtMobile;
    public javax.swing.JTextField jtxtName;
    public javax.swing.JTextField jtxtRefBy;
    public javax.swing.JTextField jtxtTin;
    private javax.swing.JButton okButton3;
    // End of variables declaration//GEN-END:variables

    private int returnStatus = RET_CANCEL;
}
