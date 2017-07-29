/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import masterController.CreateSalesAccount;
import masterController.SeriesMasterController;
import model.AccountHead;
import model.PurchaseControllerDetailModel;
import model.PurchaseControllerHeaderModel;
import model.SeriesHead;
import model.SeriesMaster;
import model.SeriesMasterModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofitAPI.PurchaseAPI;
import retrofitAPI.QuotationAPI;
import retrofitAPI.SeriesAPI;
import retrofitAPI.StartUpAPI;
import skable.Constants;
import skable.SkableHome;
import support.Library;
import support.OurDateChooser;
import support.ReportTable;
import support.SelectDailog;
import transactionView.QuoatationView;

/**
 *
 * @author bhaumik
 */
public class QuotationController extends javax.swing.JDialog {

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
    private String ref_no = "";
    private javax.swing.JTextField jtxtQty = null, jtxtRate = null, jtxtAmount = null;
    private javax.swing.JTextField jtxtDiscPer = null, jtxtMRP = null;
    javax.swing.JTextField jtxtItem = null;
    JLabel jlblTotQty;
    JLabel jlblTotAmt;
    private TableRowSorter<TableModel> rowSorter;
    private JTextField jtfFilter = new JTextField();
    DefaultTableModel dtm = null;
    int type = -1;
    boolean flag = false;
    QuotationAPI purchaseAPI = null;
    private ReportTable viewTable = null;
    private ReportTable viewTableSummary = null;
    private String sr_cd = "";
    private String item_name = "";
    private String sku_code = "";
    private HashMap<String, String> itemCode = new HashMap<String, String>();
    private QuoatationView pbv;
    private HashMap<String, Integer> data;

    /**
     * Creates new form PurchaseController
     */
    public QuotationController(java.awt.Frame parent, boolean modal, QuoatationView pbv) {
        super(parent, modal);
        initComponents();
        dtm = (DefaultTableModel) jTable1.getModel();
        this.pbv = pbv;

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
        this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());

        lb.setDateChooserPropertyInit(jtxtBillDate);
        lb.setDateChooserPropertyInit(jtxtVouDate);
        addJtextBox();
        addJLabel();
        tableForView();
        searchOnTextFields();
        flag = true;
        setUpData();
        if (SkableHome.user_grp_cd.equalsIgnoreCase("1")) {
            jComboBox1.setEnabled(true);
        }
        setTitle("Quotation Bill");
        setPopUp();
        SkableHome.zoomTable.setToolTipOn(true);
        final Container zoomIFrame = this;
        jTable1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

            @Override
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                SkableHome.zoomTable.zoomInToolTipForTable(jTable1, jScrollPane1, zoomIFrame, evt);
            }
        });
    }

    private void setPopUp() {
        final JPopupMenu popup = new JPopupMenu();
        ActionListener menuListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                popup.setVisible(false);
                int row = jTable1.getSelectedRow();
                int column = jTable1.getSelectedColumn();
                if (row != -1 && column != -1) {
                    String selection = jTable1.getValueAt(row, column).toString();
                    StringSelection data = new StringSelection(selection);
                    Clipboard clipboard
                            = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(data, data);
                }
            }
        };
        final JMenuItem item;
        popup.add(item = new JMenuItem("COPY"));
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(menuListener);
        popup.setLocation(MouseInfo.getPointerInfo().getLocation());
        jTable1.setComponentPopupMenu(popup);
    }

    private void setUpData() {
        jComboBox1.removeAllItems();
        for (int i = 0; i < Constants.BRANCH.size(); i++) {
            jComboBox1.addItem(Constants.BRANCH.get(i).getBranch_name());
        }
        jComboBox1.setSelectedItem(SkableHome.selected_branch.getBranch_name());
    }

    private void tableForView() {
        viewTable = new ReportTable();
        viewTableSummary = new ReportTable();

        viewTable.AddColumn(0, "Item Code", 120, java.lang.String.class, null, false);
        viewTable.AddColumn(1, "Item Name", 120, java.lang.String.class, null, false);
        viewTable.AddColumn(2, "Tax Code", 0, java.lang.String.class, null, false);
        viewTable.AddColumn(3, "Tax Name", 120, java.lang.String.class, null, false);
        viewTable.AddColumn(4, "SKU", 120, java.lang.String.class, null, false);
        viewTable.makeTable();

        viewTableSummary.AddColumn(0, "Item Name", 120, java.lang.String.class, null, false);
        viewTableSummary.AddColumn(1, "Count", 120, java.lang.String.class, null, false);
        viewTableSummary.makeTable();
    }

    private void addJLabel() {
        jlblTotQty = new javax.swing.JLabel("0");
        jlblTotAmt = new javax.swing.JLabel("0.00");
        jlblTotQty.setHorizontalAlignment(SwingConstants.RIGHT);
        jlblTotAmt.setHorizontalAlignment(SwingConstants.RIGHT);

        jlblTotQty.setBounds(0, 0, 20, 20);
        jlblTotQty.setVisible(true);
        jPanel4.add(jlblTotQty);

        jlblTotAmt.setBounds(0, 0, 20, 20);
        jlblTotAmt.setVisible(true);
        jPanel4.add(jlblTotAmt);

        lb.setTable(jTable1, new JComponent[]{null, null, jlblTotQty, null, null, null, jlblTotAmt, null});
    }

    private void searchOnTextFields() {
        this.rowSorter = new TableRowSorter<>(jTable1.getModel());
        jTable1.setRowSorter(rowSorter);
        panel.add(new JLabel("Specify a word to match:"),
                BorderLayout.WEST);
        panel.add(jtfFilter, BorderLayout.CENTER);

//        setLayout(new BorderLayout());
//        add(panel, BorderLayout.SOUTH);
//        add(new JScrollPane(jTable1), BorderLayout.CENTER);
        jtfFilter.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
    }

    private void addJtextBox() {
        jtxtItem = new javax.swing.JTextField();
        jtxtItem.addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                lb.selectAll(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                lb.toUpper(e);
            }

        });

        jtxtItem.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_N) {
                    if (e.getModifiers() == KeyEvent.CTRL_MASK) {
                        SeriesMasterController smc = new SeriesMasterController(null, true, null);
                        smc.setLocationRelativeTo(null);
                        smc.setVisible(true);
                    }
                }
                if (lb.isEnter(e)) {
                    if (lb.validateInput(jtxtItem.getText())) {
                        setSeriesData("3", jtxtItem.getText().toUpperCase());
                    }
                }
            }

        });

        jtxtQty = new javax.swing.JTextField();
        jtxtQty.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                lb.selectAll(e);
                calculation();
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                lb.toInteger(e);
            }
        });

        jtxtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                lb.enterFocus(e, jtxtDiscPer);
            }
        });

        jtxtRate = new javax.swing.JTextField();

        jtxtRate.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                lb.selectAll(e);
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                calculation();
                lb.toDouble(e);
            }
        });

        jtxtRate.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                lb.enterFocus(e, jtxtDiscPer);
            }
        });

        jtxtDiscPer = new javax.swing.JTextField();

        jtxtDiscPer.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                lb.selectAll(e);
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                lb.toDouble(e);
                calculation();
            }
        });

        jtxtDiscPer.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                lb.enterFocus(e, jbtnAdd);
            }
        });

        jtxtMRP = new javax.swing.JTextField();

        jtxtMRP.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                lb.selectAll(e);
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                lb.toDouble(e);
            }
        });

        jtxtMRP.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                lb.enterFocus(e, jbtnAdd);
            }
        });

        jtxtAmount = new javax.swing.JTextField();

        jtxtAmount.addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                lb.selectAll(e);
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                lb.toDouble(e);
                calculation();
            }
        });

        jtxtAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                lb.enterFocus(e, jbtnAdd);
            }
        });

        jtxtAmount.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentMoved(java.awt.event.ComponentEvent e) {
                lb.setTable(jTable1, new JComponent[]{jtxtItem, null, jtxtRate, jtxtQty, jtxtDiscPer, jtxtMRP, jtxtAmount, null});
                lb.setTable(jTable1, new JComponent[]{null, null, jlblTotQty, null, null, null, jlblTotAmt, null});
            }
        });

        jtxtItem.setBounds(0, 0, 20, 20);
        jtxtItem.setVisible(true);
        jPanel3.add(jtxtItem);

        jtxtQty.setBounds(0, 0, 20, 20);
        jtxtQty.setVisible(true);
        jPanel3.add(jtxtQty);

        jtxtRate.setBounds(0, 0, 20, 20);
        jtxtRate.setVisible(true);
        jPanel3.add(jtxtRate);

        jtxtDiscPer.setBounds(0, 0, 20, 20);
        jtxtDiscPer.setVisible(true);
        jPanel3.add(jtxtDiscPer);

        jtxtMRP.setBounds(0, 0, 20, 20);
        jtxtMRP.setVisible(true);
        jPanel3.add(jtxtMRP);

        jtxtAmount.setBounds(0, 0, 20, 20);
        jtxtAmount.setVisible(true);
        jPanel3.add(jtxtAmount);

        lb.setTable(jTable1, new JComponent[]{jtxtItem, null, jtxtRate, jtxtQty, jtxtDiscPer, jtxtMRP, jtxtAmount, null});
    }

    private void setSeriesData(String param_cd, String value) {
        try {
            Call<JsonObject> call = lb.getRetrofit().create(StartUpAPI.class).getDataFromServer(param_cd, value.toUpperCase(),SkableHome.db_name,SkableHome.selected_year);
            lb.addGlassPane(this);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    lb.removeGlassPane(QuotationController.this);
                    if (response.isSuccessful()) {
                        System.out.println(response.body().toString());
                        SeriesHead header = (SeriesHead) new Gson().fromJson(response.body(), SeriesHead.class);
                        if (header.getResult() == 1) {
                            final SelectDailog sa = new SelectDailog(null, true);
                            sa.setData(viewTable);
                            sa.setLocationRelativeTo(null);
                            ArrayList<SeriesMaster> series = (ArrayList<SeriesMaster>) header.getAccountHeader();
                            sa.getDtmHeader().setRowCount(0);
                            for (int i = 0; i < series.size(); i++) {
                                Vector row = new Vector();
                                row.add(series.get(i).getSRCD());
                                row.add(series.get(i).getSRNAME());
                                row.add(series.get(i).getTAXCD());
                                row.add(series.get(i).getTAXNAME());
                                row.add(series.get(i).getSRALIAS());
                                sa.getDtmHeader().addRow(row);
                            }
                            lb.setColumnSizeForTable(viewTable, sa.jPanelHeader.getWidth());
                            sa.setVisible(true);
                            if (sa.getReturnStatus() == SelectDailog.RET_OK) {
                                int row = viewTable.getSelectedRow();
                                if (row != -1) {
                                    sr_cd = viewTable.getValueAt(row, 0).toString();
                                    item_name = viewTable.getValueAt(row, 1).toString();
                                    sku_code = viewTable.getValueAt(row, 4).toString();
                                    jtxtItem.setText(viewTable.getValueAt(row, 1).toString());
                                    getLastRate();
                                }
                                sa.dispose();
                            } else {
                                jtxtItem.requestFocusInWindow();
                            }
                        } else {
                            lb.showMessageDailog(header.getCause());
                        }
                    } else {
                        // handle request errors yourself
                        lb.showMessageDailog(response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                    lb.removeGlassPane(QuotationController.this);
                }
            }
            );
        } catch (Exception ex) {
            lb.printToLogFile("Exception at setData at account master in sales invoice", ex);
        }

    }

    private void getLastRate() {
        try {
            Call<JsonObject> call = lb.getRetrofit().create(StartUpAPI.class).GetDataFromServer("38", sr_cd, "",SkableHome.db_name,SkableHome.selected_year);
            lb.addGlassPane(this);
            call.enqueue(new Callback<JsonObject>() {

                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    lb.removeGlassPane(QuotationController.this);
                    if (response.isSuccessful()) {
                        System.out.println(response.body().toString());
                        JsonObject header = (JsonObject) response.body();
                        if (header.get("result").getAsInt() == 1) {
                            if (header.get("data").getAsJsonArray().size() != 0) {
                                jtxtMRP.setText(header.get("data").getAsJsonArray().get(0).getAsJsonObject().get("rate").getAsString());
                            } else {
                                jtxtMRP.setText("0.00");
                            }
                            jtxtQty.requestFocusInWindow();
                        } else {
                            lb.showMessageDailog(response.body().get("Cause").getAsString());
                        }
                    } else {
                        // handle request errors yourself
                        lb.showMessageDailog(response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                    lb.removeGlassPane(QuotationController.this);

                }
            }
            );
        } catch (Exception ex) {
            lb.printToLogFile("Exception at setData at account master in sales invoice", ex);
        }
    }

    private void calculation() {
        double qty = lb.isNumber(jtxtQty);
        double mrp = lb.isNumber(jtxtMRP);
        double disc = lb.isNumber(jtxtDiscPer);
        double rate = mrp - (mrp * disc / 100);

        jtxtRate.setText(lb.Convert2DecFmtForRs(rate));
        jtxtAmount.setText(lb.Convert2DecFmtForRs(rate * qty));

    }

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    public void setData(QuotationAPI purchaseAPI, String ref_no) {
        this.ref_no = ref_no;
        if (purchaseAPI == null) {
            purchaseAPI = lb.getRetrofit().create(QuotationAPI.class);
        }
        this.purchaseAPI = purchaseAPI;

        if (!ref_no.equalsIgnoreCase("")) {
            try {
                jComboBox1.setEnabled(false);
                Call<JsonObject> call = purchaseAPI.getBill(ref_no, "39",SkableHome.db_name,SkableHome.selected_year);
                lb.addGlassPane(this);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        lb.removeGlassPane(QuotationController.this);
                        if (response.isSuccessful()) {
                            System.out.println(response.body().toString());
                            JsonObject object = response.body();
                            if (object.get("result").getAsInt() == 1) {

                                JsonArray array = object.get("data").getAsJsonArray();
                                try {
                                    for (int i = 0; i < array.size(); i++) {
                                        jtxtVoucher.setText(array.get(i).getAsJsonObject().get("INV_NO").getAsInt() + "");
                                        jtxtVouDate.setText(lb.ConvertDateFormetForDBForConcurrency(array.get(i).getAsJsonObject().get("V_DATE").getAsString()));
                                        jtxtDueDate.setText(lb.ConvertDateFormetForDBForConcurrency(array.get(i).getAsJsonObject().get("DUE_DATE").getAsString()));
                                        jlblVday.setText(lb.setDay(jtxtVouDate));
                                        jlblBillDay1.setText(lb.setDay(jtxtDueDate));
                                        jlblBillDay.setText(lb.setDay(jtxtBillDate));
                                        jComboBox1.setSelectedIndex(array.get(i).getAsJsonObject().get("BRANCH_CD").getAsInt() - 1);
                                        ac_cd = array.get(i).getAsJsonObject().get("AC_CD").getAsString();
                                        jtxtName.setText(array.get(i).getAsJsonObject().get("FNAME").getAsString());
                                        QuotationController.this.ref_no = array.get(i).getAsJsonObject().get("REF_NO").getAsString();
                                        jlblTotal.setText(array.get(i).getAsJsonObject().get("NET_AMT").getAsDouble() + "");
                                        jlblUser.setText(array.get(i).getAsJsonObject().get("USER_ID").getAsString() + "");
                                        jlblEditNo.setText(array.get(i).getAsJsonObject().get("EDIT_NO").getAsDouble() + "");
                                        jlblTimeStamp.setText(array.get(i).getAsJsonObject().get("TIME_STAMP").getAsString());
                                        if (!array.get(i).getAsJsonObject().get("REMARK").isJsonNull()) {
                                            jTextArea1.setText(array.get(i).getAsJsonObject().get("REMARK").getAsString());
                                        }
                                        jlblVday.setText(lb.setDay(jtxtVouDate));
                                        jlblBillDay.setText(lb.setDay(jtxtBillDate));

                                        Vector row = new Vector();
                                        row.add(array.get(i).getAsJsonObject().get("SR_NAME").getAsString());
                                        row.add(array.get(i).getAsJsonObject().get("SR_ALIAS").getAsString());
                                        row.add(array.get(i).getAsJsonObject().get("QTY").getAsInt());
                                        row.add(array.get(i).getAsJsonObject().get("RATE").getAsString());
                                        row.add(array.get(i).getAsJsonObject().get("DISC_RATE").getAsString());
                                        row.add(array.get(i).getAsJsonObject().get("MRP").getAsString());
                                        row.add(array.get(i).getAsJsonObject().get("AMT").getAsString());
                                        row.add(array.get(i).getAsJsonObject().get("SR_CD").getAsString());
                                        dtm.addRow(row);

                                    }
                                    setTotal();
                                } catch (Exception ex) {
                                    lb.printToLogFile("Exception", ex);
                                }
                                QuotationController.this.setVisible(true);
                            } else {
                                lb.showMessageDailog(response.body().get("Cause").toString());
                            }
                        } else {
                            lb.showMessageDailog(response.body().get("Cause").toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                        lb.removeGlassPane(QuotationController.this);
                    }
                });
            } catch (Exception ex) {
                lb.printToLogFile("Exception at getDataFor PurchaseBill", ex);
            }
        } else {
            QuotationController.this.setVisible(true);
        }

    }

    private void setAccountDetailMobile(String param_cd, String value) {
        try {
            Call<JsonObject> call = lb.getRetrofit().create(StartUpAPI.class).getDataFromServer(param_cd, value.toUpperCase(),SkableHome.db_name,SkableHome.selected_year);
            call.enqueue(new Callback<JsonObject>() {

                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    lb.removeGlassPane(QuotationController.this);
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
//                                    jtxtMobile.setText(response.body().getAccountHeader().get(row).getMOBILE1());
                                    jtxtName.setText(header.getAccountHeader().get(row).getFNAME());
//                                    jtxtAddress.setText(response.body().getAccountHeader().get(row).getADD1());

                                    jtxtItem.requestFocusInWindow();
                                }
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
                    lb.removeGlassPane(QuotationController.this);
                }
            });
        } catch (Exception ex) {
            lb.printToLogFile("Exception at setData at account master in sales invoice", ex);
        }

    }

    private void setTotal() {
        double tot = 0.00;
        int tot_qty = 0;
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            tot += lb.isNumber2(jTable1.getValueAt(i, 6).toString());
            tot_qty += (int) lb.isNumber2(jTable1.getValueAt(i, 2).toString());
        }
        jlblTotal.setText(lb.Convert2DecFmtForRs(tot));
        jlblTotQty.setText((tot_qty) + "");
    }

    private void clear() {
        jtxtItem.setText("");
        sr_cd = "";
        item_name = "";
        jtxtQty.setText("");
        jtxtRate.setText("");
        jtxtDiscPer.setText("");
        jtxtMRP.setText("");
        jtxtAmount.setText("");
        jtxtAmount.setText("");
    }

    public boolean setBulkData(ArrayList<PurchaseControllerDetailModel> models) {
        boolean flag = true;
        for (int i = 0; i < models.size() && flag; i++) {
            PurchaseControllerDetailModel model = models.get(i);
            item_name = model.getSR_NAME();
            jtxtRate.setText(lb.Convert2DecFmtForRs(model.getRATE()));
            jtxtAmount.setText(lb.Convert2DecFmtForRs(model.getRATE()));
            jtxtQty.setText("1");
            jtxtDiscPer.setText(lb.Convert2DecFmtForRs(model.getDISC_PER()));
            jtxtMRP.setText(lb.Convert2DecFmtForRs(model.getMRP()));
            sr_cd = model.getSR_CD();
            clear();
        }

        setTotal();
        return flag;
    }

    private void addRow(String tag) {
        Vector row = new Vector();
        row.add(item_name);
        row.add(sku_code);
        row.add(jtxtQty.getText());
        row.add(lb.isNumber2(jtxtRate.getText()));
        row.add(lb.isNumber2(jtxtDiscPer.getText()));
        row.add(lb.isNumber2(jtxtMRP.getText()));
        row.add(lb.isNumber2(jtxtAmount.getText()));
        row.add(sr_cd);
        dtm.addRow(row);
    }

    public boolean validateRow(String tag) {
        boolean flag = true;

        if (sr_cd.equalsIgnoreCase("")) {
            lb.showMessageDailog("Invalid Product Name");
            jtxtItem.requestFocusInWindow();
            flag = false;
        }

        if (lb.isNumber(jtxtQty) <= 0) {
            lb.showMessageDailog("Qty greater than 0");
            jtxtQty.requestFocusInWindow();
            return false;
        }

        if (lb.isNumber(jtxtAmount) <= 0) {
            lb.showMessageDailog("Amt greater than 0");
            jtxtQty.requestFocusInWindow();
            return false;
        }

        return flag;
    }

    private boolean validateVoucher() {

        if (!lb.checkDate(jtxtVouDate)) {
            lb.showMessageDailog("Invalid Date");
            return false;
        }

        if (lb.ConvertDateFormetForDB(jtxtBillDate.getText()).equalsIgnoreCase("")) {
            lb.showMessageDailog("Please Enter valid Voucher Date");
            return false;
        }

        if (lb.ConvertDateFormetForDB(jtxtDueDate.getText()).equalsIgnoreCase("")) {
            lb.showMessageDailog("Please Enter Due Date");
            return false;
        }

        if (jTable1.getRowCount() == 0) {
            lb.showMessageDailog("Voucher can not be empty");
            return false;
        }
        return true;
    }

    private void saveVoucher() {
        final PurchaseControllerHeaderModel header = new PurchaseControllerHeaderModel();
        header.setRef_no(ref_no);
        header.setREMARK(jTextArea1.getText());
        header.setAC_CD(ac_cd);
        header.setDUE_DATE(lb.ConvertDateFormetForDB(jtxtDueDate.getText()));
        header.setNET_AMT(lb.isNumber(jlblTotal));
        header.setAc_name(jtxtName.getText());
        header.setBRANCH_CD(jComboBox1.getSelectedIndex() + 1);
        header.setUSER_ID(SkableHome.user_id);
        header.setV_DATE(lb.ConvertDateFormetForDB(jtxtVouDate.getText()));

        final ArrayList<PurchaseControllerDetailModel> detail = new ArrayList<PurchaseControllerDetailModel>();
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            PurchaseControllerDetailModel model = new PurchaseControllerDetailModel();
            model.setSR_CD(jTable1.getValueAt(i, 7).toString());
            model.setQTY((int) lb.isNumber2(jTable1.getValueAt(i, 2).toString()));
            model.setRATE(lb.isNumber2(jTable1.getValueAt(i, 3).toString()));
            model.setDISC_PER(lb.isNumber2(jTable1.getValueAt(i, 4).toString()));
            model.setMRP(lb.isNumber2(jTable1.getValueAt(i, 5).toString()));
            model.setAMT(lb.isNumber2(jTable1.getValueAt(i, 6).toString()));
            detail.add(model);
        }

        String headerJson = new Gson().toJson(header);
        String detailJson = new Gson().toJson(detail);
        Call<JsonObject> addUpdaCall = purchaseAPI.addUpdatePurchaseBill(headerJson, detailJson,SkableHome.db_name,SkableHome.selected_year);
        lb.addGlassPane(this);
        addUpdaCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                lb.removeGlassPane(QuotationController.this);
                if (response.isSuccessful()) {
                    System.out.println(response.body().toString());
                    JsonObject object = response.body();
                    if (object.get("result").getAsInt() == 1) {
                        lb.showMessageDailog("Voucher saved successfully");
                        QuotationController.this.dispose();
                        if (pbv != null) {
                            pbv.setData();
                        }
                    } else {
                        lb.showMessageDailog(object.get("Cause").getAsString());
                    }
                } else {
                    lb.showMessageDailog(response.body().get("Cause").toString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                lb.removeGlassPane(QuotationController.this);
            }
        });
    }

    private void setDueDate() {
        Date dt = lb.ConvertDateFromString(jtxtVouDate.getText());
        int numOfDays = (int) lb.isNumber(jtxtPmtDays);
        Calendar cal = Calendar.getInstance();
        if (dt != null) {
            cal.setTime(dt);
            cal.add(Calendar.DATE, numOfDays);
            jtxtDueDate.setText(((cal.get(Calendar.DATE) > 9) ? cal.get(Calendar.DATE) : "0" + cal.get(Calendar.DATE)) + "/" + (((cal.get(Calendar.MONTH) + 1) > 9) ? (cal.get(Calendar.MONTH) + 1) : "0" + (cal.get(Calendar.MONTH) + 1)) + "/" + cal.get(Calendar.YEAR));
        }
    }

    public void getData(final String data) {
        Call<JsonObject> call = lb.getRetrofit().create(SeriesAPI.class).getSeriesMaster(data, "",SkableHome.db_name,SkableHome.selected_year);
        lb.addGlassPane(this);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> rspns) {
                lb.removeGlassPane(QuotationController.this);
                if (rspns.isSuccessful()) {
                    JsonObject result = rspns.body();
                    if (result.get("result").getAsInt() == 1) {
                        TypeToken<List<SeriesMasterModel>> token = new TypeToken<List<SeriesMasterModel>>() {
                        };
                        ArrayList<SeriesMasterModel> detail = new Gson().fromJson(result.get("data"), token.getType());
                        for (int i = 0; i < detail.size(); i++) {
                            SeriesMasterController smc = new SeriesMasterController(null, true);
                            smc.setLocationRelativeTo(null);
                            smc.setData(detail.get(i).getSR_CD(), detail.get(i).getSR_ALIAS(), detail.get(i).getSR_NAME(), detail.get(i).getBRAND_NAME(), detail.get(i).getMODEL_NAME(), detail.get(i).getMEMORY_NAME(),
                                    detail.get(i).getCOLOUR_NAME(), detail.get(i).getTYPE_NAME(), detail.get(i).getSUB_TYPE_NAME(), detail.get(i).getTAX_NAME(),
                                    detail.get(i).getRAM_NAME(), detail.get(i).getCAMERA_NAME(), detail.get(i).getBATTERY_NAME());
                            smc.setVisible(true);
                            break;
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
                lb.removeGlassPane(QuotationController.this);
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

        cancelButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtxtVoucher = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jtxtVouDate = new javax.swing.JTextField();
        jBillDateBtn = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jtxtBillDate = new javax.swing.JTextField();
        jBillDateBtn1 = new javax.swing.JButton();
        jlblVday = new javax.swing.JLabel();
        jlblBillDay = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtxtName = new javax.swing.JTextField();
        jbtnAdd = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jtxtDueDate = new javax.swing.JTextField();
        jBillDateBtn2 = new javax.swing.JButton();
        jlblBillDay1 = new javax.swing.JLabel();
        jlblPmtDays = new javax.swing.JLabel();
        jtxtPmtDays = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        panel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jlblTotal = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jlblUser = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jlblEditNo = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jlblTimeStamp = new javax.swing.JLabel();
        jbtnOK = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();

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

        jLabel26.setText("Bill Date");

        jtxtBillDate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtBillDateFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtBillDateFocusLost(evt);
            }
        });
        jtxtBillDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtBillDateKeyPressed(evt);
            }
        });

        jBillDateBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBillDateBtn1ActionPerformed(evt);
            }
        });

        jlblVday.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jlblBillDay.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setText("Name");

        jtxtName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtNameFocusLost(evt);
            }
        });
        jtxtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtNameKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtNameKeyPressed(evt);
            }
        });

        jbtnAdd.setText("ADD");
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

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox1KeyPressed(evt);
            }
        });

        jLabel3.setText("Branch");

        jLabel27.setText("Due Date");

        jtxtDueDate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtDueDateFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtDueDateFocusLost(evt);
            }
        });
        jtxtDueDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtDueDateKeyPressed(evt);
            }
        });

        jBillDateBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBillDateBtn2ActionPerformed(evt);
            }
        });

        jlblBillDay1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jlblPmtDays.setText("Days");

        jtxtPmtDays.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtPmtDaysFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtPmtDaysFocusLost(evt);
            }
        });
        jtxtPmtDays.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtPmtDaysKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtPmtDaysKeyTyped(evt);
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
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPmtDays, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtPmtDays, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtBillDate, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtVouDate, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtnAdd))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jBillDateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlblVday, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jBillDateBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlblBillDay, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtDueDate, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBillDateBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlblBillDay1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 368, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(186, 186, 186)
                .addComponent(jtxtName, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel24, jLabel26, jlblPmtDays});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbtnAdd)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBillDateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtVouDate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jlblVday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlblBillDay1)
                            .addComponent(jBillDateBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtDueDate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlblBillDay)
                            .addComponent(jtxtBillDate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBillDateBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtxtPmtDays)
                            .addComponent(jlblPmtDays, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jBillDateBtn, jComboBox1, jLabel1, jLabel24, jLabel3, jlblVday, jtxtVouDate, jtxtVoucher});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jBillDateBtn1, jLabel26, jlblBillDay, jtxtBillDate});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jBillDateBtn2, jLabel27, jlblBillDay1, jtxtDueDate});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel4, jtxtName});

        jPanel2.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        jTable1.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "SKU Code", "Rate", "Qty", "Discount", "MRP", "Amount", "sr_cd"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(300);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(7).setMinWidth(0);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(0);
        }

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setPreferredSize(new java.awt.Dimension(1060, 25));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 849, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 21, Short.MAX_VALUE)
        );

        panel.setLayout(new java.awt.BorderLayout());

        jLabel14.setText("Net Amount");

        jlblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblTotal.setText("0.00");
        jlblTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        jLabel16.setText("Remark");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(4);
        jTextArea1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea1KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTextArea1);

        jLabel5.setText("SELECT ROW AND PRESS CTRL+ E  FOR OPEN ITEM MASTER ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1217, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jlblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jlblEditNo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlblTimeStamp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane2)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                        .addGap(22, 22, 22)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblTimeStamp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblEditNo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(jbtnOK))
                .addContainerGap())
        );

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
        lb.enterFocus(evt, jtxtBillDate);
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

    private void jtxtBillDateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtBillDateFocusLost
        // TODO add your handling code here:
        try {
            if (jtxtBillDate.getText().contains("/")) {
                jtxtBillDate.setText(jtxtBillDate.getText().replace("/", ""));
            }
            if (jtxtBillDate.getText().length() == 8) {
                String temp = jtxtBillDate.getText();
                String setDate = (temp.substring(0, 2)).replace(temp.substring(0, 2), temp.substring(0, 2) + "/") + (temp.substring(2, 4)).replace(temp.substring(2, 4), temp.substring(2, 4) + "/") + temp.substring(4, temp.length());
                jtxtBillDate.setText(setDate);
            }
            //            if ((new SimpleDateFormat("dd/MM/yyyy").format(new Date(jtxtBillDate.getText().trim()))) != null) {
            //                jtxtDueDate.requestFocusInWindow();
            //            }
            jlblBillDay.setText(lb.setDay(jtxtBillDate));
        } catch (Exception ex) {
            jtxtBillDate.requestFocusInWindow();
        }
    }//GEN-LAST:event_jtxtBillDateFocusLost

    private void jtxtBillDateFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtBillDateFocusGained
        // TODO add your handling code here:
        jtxtBillDate.selectAll();
    }//GEN-LAST:event_jtxtBillDateFocusGained

    private void jtxtBillDateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtBillDateKeyPressed
        lb.enterFocus(evt, jtxtPmtDays);
    }//GEN-LAST:event_jtxtBillDateKeyPressed

    private void jBillDateBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBillDateBtn1ActionPerformed
        // TODO add your handling code here:
        OurDateChooser odc = new OurDateChooser();
        odc.setnextFocus(jtxtBillDate);
        odc.setFormat("dd/MM/yyyy");
        JPanel jp = new JPanel();
        this.add(jp);
        jp.setBounds(jtxtBillDate.getX() - 200, jPanel1.getY() + 170, jtxtBillDate.getX() + odc.getWidth(), jtxtBillDate.getY() + odc.getHeight());
        odc.setLocation(0, 0);
        odc.showDialog(jp, "Select Date");
    }//GEN-LAST:event_jBillDateBtn1ActionPerformed

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
        if (lb.isEnter(evt)) {
            if (lb.isBlank(jtxtName)) {
                lb.confirmDialog("Do you want to create new account?");
                if (lb.type) {
                    CreateSalesAccount bmc = new CreateSalesAccount(null, true);
                    bmc.setLocationRelativeTo(null);
                    bmc.setVisible(true);
                    if (bmc.getReturnStatus() == RET_OK) {
                        ac_cd = bmc.ac_cd;
                        jtxtName.setText(bmc.account.getFNAME());
                        jtxtItem.requestFocusInWindow();
                    }
                } else {
                    ac_cd = "";
                }
            } else {
                if (lb.validateInput(jtxtName.getText())) {
                    setAccountDetailMobile("2", jtxtName.getText());
                }
            }
        }
    }//GEN-LAST:event_jtxtNameKeyPressed

    private void jtxtNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtNameKeyTyped
        // TODO add your handling code here:
        lb.fixLength(evt, 255);
    }//GEN-LAST:event_jtxtNameKeyTyped

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            {
                int index = jTable1.getSelectedRow();
                int is_del = Integer.parseInt(jTable1.getValueAt(index, 7).toString());
                if (index != -1 && is_del == 0) {
                    evt.consume();
                    item_name = jTable1.getValueAt(index, 0).toString();
                    sr_cd = jTable1.getValueAt(index, 7).toString();
                    sku_code = jTable1.getValueAt(index, 1).toString();
                    jtxtItem.setText(jTable1.getValueAt(index, 0).toString());
                    jtxtRate.setText(jTable1.getValueAt(index, 2).toString());
                    jtxtQty.setText(jTable1.getValueAt(index, 3).toString());
                    jtxtDiscPer.setText(jTable1.getValueAt(index, 4).toString());
                    jtxtMRP.setText(jTable1.getValueAt(index, 5).toString());
                    jtxtAmount.setText(jTable1.getValueAt(index, 6).toString());
                }
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        int index = jTable1.getSelectedRow();
        int is_del = Integer.parseInt(jTable1.getValueAt(index, 7).toString());
        int is_main = Integer.parseInt(jTable1.getValueAt(index, 16).toString());
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            if (index != -1 && is_del == 0 && is_main == 1) {
                lb.confirmDialog("Do you want to delete this row?");
                if (lb.type) {
                    dtm.removeRow(index);
                    setTotal();
                }
            }
        }

        if (evt.getKeyCode() == KeyEvent.VK_D) {
            if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
                if (index != -1 && is_del == 0 && is_main == 1) {
                    lb.confirmDialog("Do you want to delete this row?");
                    if (lb.type) {
                        dtm.removeRow(index);
                        setTotal();
                    }
                }
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_E) {
            if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
                final String sr_cd = jTable1.getValueAt(index, 1).toString();
                getData(sr_cd);
            }
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void jbtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAddActionPerformed
        // TODO add your handling code here:
        String tag = "";
        if (validateRow(tag)) {
            int index = jTable1.getSelectedRow();
            if (index == -1) {
                Vector row = new Vector();
                row.add(item_name);
                row.add(sku_code);
                row.add(lb.isNumber2(jtxtRate.getText()));
                row.add(jtxtQty.getText());
                row.add(lb.isNumber2(jtxtDiscPer.getText()));
                row.add(lb.isNumber2(jtxtMRP.getText()));
                row.add(lb.isNumber2(jtxtAmount.getText()));
                row.add(sr_cd);
                dtm.addRow(row);
            } else {
                jTable1.setValueAt(item_name, index, 0);
                jTable1.setValueAt(sku_code, index, 1);
                jTable1.setValueAt(lb.isNumber2(jtxtRate.getText()), index, 5);
                jTable1.setValueAt((int) lb.isNumber2(jtxtQty.getText()), index, 4);
                jTable1.setValueAt(lb.isNumber2(jtxtDiscPer.getText()), index, 12);
                jTable1.setValueAt(lb.isNumber2(jtxtMRP.getText()), index, 14);
                jTable1.setValueAt(lb.isNumber2(jtxtAmount.getText()), index, 15);
                jTable1.clearSelection();
            }

            jTable1.scrollRectToVisible(jTable1.getCellRect(jTable1.getRowCount() - 1, 0, true));
            lb.confirmDialog("Do you want to add another item?");
            if (lb.type) {
                jtxtItem.requestFocusInWindow();
            } else {
                clear();
                jTextArea1.requestFocusInWindow();
            }
        }

        setTotal();
    }//GEN-LAST:event_jbtnAddActionPerformed

    private void jbtnAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbtnAddKeyPressed
        // TODO add your handling code here:
        lb.enterClick(evt);
    }//GEN-LAST:event_jbtnAddKeyPressed

    private void jbtnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnOKActionPerformed
        // TODO add your handling code here:
        jtfFilter.setText("");
        if (validateVoucher()) {
            lb.confirmDialog("Do you want to save this voucher?");
            if (lb.type) {
                saveVoucher();
            }
        }
    }//GEN-LAST:event_jbtnOKActionPerformed

    private void jTextArea1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea1KeyPressed
        // TODO add your handling code here:
        if (lb.isEnter(evt)) {
            if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
                jbtnOK.requestFocusInWindow();
            }
        }
    }//GEN-LAST:event_jTextArea1KeyPressed

    private void jComboBox1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox1KeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jtxtVouDate);
    }//GEN-LAST:event_jComboBox1KeyPressed

    private void jtxtDueDateFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtDueDateFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtDueDateFocusGained

    private void jtxtDueDateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtDueDateFocusLost
        // TODO add your handling code here:
        try {
            if (jtxtDueDate.getText().contains("/")) {
                jtxtDueDate.setText(jtxtDueDate.getText().replace("/", ""));
            }
            if (jtxtDueDate.getText().length() == 8) {
                String temp = jtxtDueDate.getText();
                String setDate = (temp.substring(0, 2)).replace(temp.substring(0, 2), temp.substring(0, 2) + "/") + (temp.substring(2, 4)).replace(temp.substring(2, 4), temp.substring(2, 4) + "/") + temp.substring(4, temp.length());
                jtxtDueDate.setText(setDate);
            }
            if ((new SimpleDateFormat("dd/MM/yyyy").format(new Date(jtxtDueDate.getText().trim()))) == null) {
                jtxtDueDate.setText(jtxtVouDate.getText());
            }
            jlblBillDay1.setText(lb.setDay(jtxtDueDate));
        } catch (Exception ex) {
            jtxtDueDate.setText(jtxtVouDate.getText());
        }
    }//GEN-LAST:event_jtxtDueDateFocusLost

    private void jtxtDueDateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtDueDateKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jtxtName);
    }//GEN-LAST:event_jtxtDueDateKeyPressed

    private void jBillDateBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBillDateBtn2ActionPerformed
        // TODO add your handling code here:
        OurDateChooser odc = new OurDateChooser();
        odc.setnextFocus(jtxtDueDate);
        odc.setFormat("dd/MM/yyyy");
        JPanel jp = new JPanel();
        this.add(jp);
        jp.setBounds(jtxtDueDate.getX() - 200, jPanel1.getY() + 170, jtxtDueDate.getX() + odc.getWidth(), jtxtDueDate.getY() + odc.getHeight());
        odc.setLocation(0, 0);
        odc.showDialog(jp, "Select Date");
    }//GEN-LAST:event_jBillDateBtn2ActionPerformed

    private void jtxtPmtDaysFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtPmtDaysFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtPmtDaysFocusGained

    private void jtxtPmtDaysFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtPmtDaysFocusLost
        // TODO add your handling code here:
        lb.toInteger(evt);
        setDueDate();
    }//GEN-LAST:event_jtxtPmtDaysFocusLost

    private void jtxtPmtDaysKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtPmtDaysKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jtxtDueDate);
    }//GEN-LAST:event_jtxtPmtDaysKeyPressed

    private void jtxtPmtDaysKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtPmtDaysKeyTyped
        // TODO add your handling code here:
        lb.onlyNumber(evt, 2);
    }//GEN-LAST:event_jtxtPmtDaysKeyTyped

    private void doClose(int retStatus) {
        lb.confirmDialog("Do you want to discard this voucher?");
        if (lb.type) {
            returnStatus = retStatus;
            setVisible(false);
            dispose();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton jBillDateBtn;
    private javax.swing.JButton jBillDateBtn1;
    private javax.swing.JButton jBillDateBtn2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton jbtnAdd;
    private javax.swing.JButton jbtnOK;
    private javax.swing.JLabel jlblBillDay;
    private javax.swing.JLabel jlblBillDay1;
    private javax.swing.JLabel jlblEditNo;
    private javax.swing.JLabel jlblPmtDays;
    private javax.swing.JLabel jlblTimeStamp;
    private javax.swing.JLabel jlblTotal;
    private javax.swing.JLabel jlblUser;
    private javax.swing.JLabel jlblVday;
    private javax.swing.JTextField jtxtBillDate;
    private javax.swing.JTextField jtxtDueDate;
    public javax.swing.JTextField jtxtName;
    private javax.swing.JTextField jtxtPmtDays;
    public javax.swing.JTextField jtxtVouDate;
    private javax.swing.JTextField jtxtVoucher;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables

    private int returnStatus = RET_CANCEL;
}
