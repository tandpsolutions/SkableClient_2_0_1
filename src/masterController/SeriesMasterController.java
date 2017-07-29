/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package masterController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import masterView.SeriesMasterView;
import model.OPBSrVal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofitAPI.BateryMasterAPI;
import retrofitAPI.CameraAPI;
import retrofitAPI.RamAPI;
import retrofitAPI.SeriesAPI;
import retrofitAPI.StartUpAPI;
import retrofitAPI.SupportAPI;
import skable.Constants;
import skable.SkableHome;
import support.Library;
import support.ReportTable;
import support.SelectDailog;

public class SeriesMasterController extends javax.swing.JDialog {

    public static final int RET_CANCEL = 0;
    public static final int RET_OK = 1;
    boolean formLoad = false;
    Library lb = Library.getInstance();
    String sr_cd = "";
    String model_cd = "";
    String memory_cd = "";
    String color_cd = "";
    String ram_cd = "";
    String camera_cd = "";
    String battery_cd = "";
    SeriesMasterView smv = null;
    private SeriesAPI seriesAPI;
    private ReportTable viewTable = null;
    private ReportTable viewTable1 = null;
    private DefaultTableModel dtmDetail;

    public SeriesMasterController(java.awt.Frame parent, boolean modal, SeriesMasterView mmv) {
        super(parent, modal);
        initComponents();
        dtmDetail = (DefaultTableModel) jTable1.getModel();
        addJtextBox();
        setUpData();

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
        this.smv = mmv;
        seriesAPI = lb.getRetrofit().create(SeriesAPI.class);
        tableForView();
        tableForViewModel();
        jtxtSeriesAlias.requestFocusInWindow();
        if (Constants.params.get("SHOW_MEMORY").toString().equalsIgnoreCase("0")) {
            jtxtMemoryName.setVisible(false);
            jlblMemoryName.setVisible(false);
        }
        jlblLine1.setText(Constants.params.get("LINE1").toString());
        jlblLine2.setText(Constants.params.get("LINE2").toString());
        jlblLine3.setText(Constants.params.get("LINE3").toString());
    }

    public SeriesMasterController(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        dtmDetail = (DefaultTableModel) jTable1.getModel();
        addJtextBox();
        setUpData();

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
        jbtnSave.setVisible(false);
        seriesAPI = lb.getRetrofit().create(SeriesAPI.class);
        tableForView();
        tableForViewModel();
        jtxtSeriesAlias.requestFocusInWindow();
    }

    private void addJtextBox() {
        jPanel1.removeAll();
        jtxtIMEI.setVisible(false);
        jtxtPurRate.setVisible(false);
        jtxtSerialNo.setVisible(false);
        jcmbBranch.setVisible(false);

        jtxtIMEI.setBounds(0, 0, 20, 20);
        jtxtIMEI.setVisible(true);
        jPanel1.add(jtxtIMEI);

        jtxtPurRate.setBounds(0, 0, 20, 20);
        jtxtPurRate.setVisible(true);
        jPanel1.add(jtxtPurRate);

        jtxtSerialNo.setBounds(0, 0, 20, 20);
        jtxtSerialNo.setVisible(true);
        jPanel1.add(jtxtSerialNo);

        jcmbBranch.setBounds(0, 0, 20, 20);
        jcmbBranch.setVisible(true);
        jPanel1.add(jcmbBranch);
        setTable();
    }

    private void setTable() {
        lb.setTable(jTable1, new JComponent[]{null, jtxtIMEI, jtxtSerialNo, jtxtPurRate, jcmbBranch, null, null});
        lb.setTable(jTable1, new JComponent[]{null, null, null, jlblTotal, null, null, null});
    }

    private void setTotal() {
        double total = 0.00;
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            total += lb.isNumber(jTable1.getValueAt(i, 3).toString());
        }
        jlblTotal.setText(lb.Convert2DecFmtForRs(total));
    }

    private void setUpData() {
        jcmbBranch.removeAllItems();
        for (int i = 0; i < Constants.BRANCH.size(); i++) {
            jcmbBranch.addItem(Constants.BRANCH.get(i).getBranch_name());
        }
    }

    private void tableForView() {
        viewTable = new ReportTable();

        viewTable.AddColumn(0, "Item Code", 120, java.lang.String.class, null, false);
        viewTable.AddColumn(1, "Item Name", 120, java.lang.String.class, null, false);
        viewTable.makeTable();
    }

    private void tableForViewModel() {
        viewTable1 = new ReportTable();
        viewTable1.AddColumn(0, "Item Code", 120, java.lang.String.class, null, false);
        viewTable1.AddColumn(1, "Item Name", 120, java.lang.String.class, null, false);
        viewTable1.AddColumn(2, "Brand Name", 120, java.lang.String.class, null, false);
        viewTable1.AddColumn(3, "Type Name", 120, java.lang.String.class, null, false);
        viewTable1.AddColumn(4, "Sub Type Name", 120, java.lang.String.class, null, false);
        viewTable1.AddColumn(5, "Tax Name", 120, java.lang.String.class, null, false);
        viewTable1.makeTable();
    }

    public void setData(final String sr_cd, final String sr_alias, final String sr_name, final String brand_name, final String model_name,
            final String memory_name, final String color_name, final String type, final String sub_type, final String tax_name, final String ram_name, final String camera_name, final String battery_name) {

        Call<JsonObject> call = seriesAPI.getSetUpData(sr_cd);
        lb.addGlassPane(this);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> rspns) {
                lb.removeGlassPane(SeriesMasterController.this);
                if (rspns.isSuccessful()) {
                    if (rspns.body().get("result").getAsInt() == 1) {
                        model_cd = rspns.body().get("MODEL_CD").getAsString();
                        memory_cd = rspns.body().get("MEMORY_CD").getAsString();
                        color_cd = rspns.body().get("COLOUR_CD").getAsString();
                        ram_cd = rspns.body().get("RAM_CD").getAsString();
                        camera_cd = rspns.body().get("CAMERA_CD").getAsString();
                        battery_cd = rspns.body().get("BATTERY_CD").getAsString();
                        SeriesMasterController.this.sr_cd = sr_cd;
                        jtxtItemName.setText(sr_name);
                        jtxtSeriesAlias.setText(sr_alias);
                        jtxtBrandName.setText(brand_name);
                        jtxtModelName.setText(model_name);
                        jtxtMemoryName.setText(memory_name);
                        jtxtColorName.setText(color_name);
                        jlblTypeName.setText(type);
                        jlblSubTypeName.setText(sub_type);
                        jlblTaxName.setText(tax_name);
                        jtxtRam.setText(ram_name);
                        jtxtCamera.setText(camera_name);
                        jtxtBattery.setText(battery_name);
                        jtxtQty.setText(rspns.body().get("OPB_QTY").getAsString());
                        jtxtVal.setText(rspns.body().get("OPB_VAL").getAsString());
                        final String detailJson = rspns.body().get("data").getAsString();
                        TypeToken<List<OPBSrVal>> token = new TypeToken<List<OPBSrVal>>() {
                        };
                        List<OPBSrVal> detail = new Gson().fromJson(detailJson, token.getType());
                        dtmDetail.setRowCount(0);
                        for (int i = 0; i < detail.size(); i++) {
                            Vector row = new Vector();
                            row.add(detail.get(i).getTag_no());
                            row.add(detail.get(i).getImei());
                            row.add(detail.get(i).getSerial());
                            row.add(detail.get(i).getP_rate());
                            jcmbBranch.setSelectedIndex(Integer.parseInt(detail.get(i).getBranch_cd()) - 1);
                            row.add(jcmbBranch.getSelectedItem().toString());
                            row.add(detail.get(i).getRef_no());
                            dtmDetail.addRow(row);
                        }
                        setTotal();
                    }
                } else {
                    lb.showMessageDailog(rspns.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                lb.removeGlassPane(SeriesMasterController.this);
            }
        });
    }

    private void addName() {
        String name = jtxtBrandName.getText() + " " + jtxtModelName.getText() + " ";
        if (Constants.params.get("SHOW_MEMORY").toString().equalsIgnoreCase("1")) {
            name += jtxtMemoryName.getText() + " ";
        } 
        {
            name += jtxtRam.getText() + " " + jtxtCamera.getText() + " " + jtxtBattery.getText() + " " + jtxtColorName.getText();
        }
        jtxtItemName.setText(name);
    }

    private void setModelData(String param_cd, String value) {
        try {
            Call<JsonObject> call = lb.getRetrofit().create(StartUpAPI.class).getDataFromServer(param_cd, value.toUpperCase(),SkableHome.db_name,SkableHome.selected_year);
            lb.addGlassPane(
                    this);
            call.enqueue(
                    new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    lb.removeGlassPane(SeriesMasterController.this);
                    if (response.isSuccessful()) {
                        System.out.println(response.body().toString());
                        if (response.body().get("result").getAsInt() == 1) {
                            final SelectDailog sa = new SelectDailog(null, true);
                            sa.setData(viewTable1);
                            sa.setLocationRelativeTo(null);
                            JsonArray array = response.body().getAsJsonArray("data");
                            sa.getDtmHeader().setRowCount(0);
                            for (int i = 0; i < array.size(); i++) {
                                Vector row = new Vector();
                                row.add(array.get(i).getAsJsonObject().get("MODEL_CD").getAsString());
                                row.add(array.get(i).getAsJsonObject().get("MODEL_NAME").getAsString());
                                row.add(array.get(i).getAsJsonObject().get("BRAND_NAME").getAsString());
                                row.add(array.get(i).getAsJsonObject().get("TYPE_NAME").getAsString());
                                row.add(array.get(i).getAsJsonObject().get("SUB_TYPE_NAME").getAsString());
                                row.add(array.get(i).getAsJsonObject().get("TAX_NAME").getAsString());
                                sa.getDtmHeader().addRow(row);
                            }
                            lb.setColumnSizeForTable(viewTable1, sa.jPanelHeader.getWidth());
                            sa.setVisible(true);
                            if (sa.getReturnStatus() == SelectDailog.RET_OK) {
                                int row = viewTable1.getSelectedRow();
                                if (row != -1) {
                                    model_cd = viewTable1.getValueAt(row, 0).toString();
                                    jtxtModelName.setText(viewTable1.getValueAt(row, 1).toString());
                                    jtxtBrandName.setText(viewTable1.getValueAt(row, 2).toString());
                                    jlblTypeName.setText(viewTable1.getValueAt(row, 3).toString());
                                    jlblSubTypeName.setText(viewTable1.getValueAt(row, 4).toString());
                                    jlblTaxName.setText(viewTable1.getValueAt(row, 5).toString());
                                    if (Constants.params.get("SHOW_MEMORY").toString().equalsIgnoreCase("0")) {
                                        jtxtColorName.requestFocusInWindow();
                                    } else {
                                        jtxtMemoryName.requestFocusInWindow();
                                    }
                                    addName();
                                }
                                sa.dispose();
                            }
                        } else {
                            lb.showMessageDailog(response.body().get("Cause").toString());
                        }
                    } else {
                        // handle request errors yourself
                        lb.showMessageDailog(response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                    lb.removeGlassPane(SeriesMasterController.this);
                }
            });
        } catch (Exception ex) {
            lb.printToLogFile("Exception at setData at account master in sales invoice", ex);

        }

    }

    private void setMemoryMaster(String param_cd, String value) {
        try {
            Call<JsonObject> call = lb.getRetrofit().create(StartUpAPI.class).getDataFromServer(param_cd, value.toUpperCase(),SkableHome.db_name,SkableHome.selected_year);
            lb.addGlassPane(
                    this);
            call.enqueue(
                    new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    lb.removeGlassPane(SeriesMasterController.this);
                    if (response.isSuccessful()) {
                        System.out.println(response.body().toString());
                        if (response.body().get("result").getAsInt() == 1) {
                            final SelectDailog sa = new SelectDailog(null, true);
                            sa.setData(viewTable);
                            sa.setLocationRelativeTo(null);
                            JsonArray array = response.body().getAsJsonArray("data");
                            sa.getDtmHeader().setRowCount(0);
                            for (int i = 0; i < array.size(); i++) {
                                Vector row = new Vector();
                                row.add(array.get(i).getAsJsonObject().get("MEMORY_CD").getAsString());
                                row.add(array.get(i).getAsJsonObject().get("MEMORY_NAME").getAsString());
                                sa.getDtmHeader().addRow(row);
                            }
                            lb.setColumnSizeForTable(viewTable, sa.jPanelHeader.getWidth());
                            sa.setVisible(true);
                            if (sa.getReturnStatus() == SelectDailog.RET_OK) {
                                int row = viewTable.getSelectedRow();
                                if (row != -1) {
                                    memory_cd = viewTable.getValueAt(row, 0).toString();
                                    jtxtMemoryName.setText(viewTable.getValueAt(row, 1).toString());
                                    jtxtColorName.requestFocusInWindow();
                                }
                                sa.dispose();
                            }
                        } else {
                            lb.showMessageDailog(response.body().get("Cause").toString());
                        }
                    } else {
                        // handle request errors yourself
                        lb.showMessageDailog(response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                    lb.removeGlassPane(SeriesMasterController.this);
                }
            });
        } catch (Exception ex) {
            lb.printToLogFile("Exception at setData at account master in sales invoice", ex);

        }

    }

    private void setColorMaster(String param_cd, String value) {
        try {
            Call<JsonObject> call = lb.getRetrofit().create(StartUpAPI.class).getDataFromServer(param_cd, value.toUpperCase(),SkableHome.db_name,SkableHome.selected_year);
            lb.addGlassPane(
                    this);
            call.enqueue(
                    new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    lb.removeGlassPane(SeriesMasterController.this);
                    if (response.isSuccessful()) {
                        System.out.println(response.body().toString());
                        if (response.body().get("result").getAsInt() == 1) {
                            final SelectDailog sa = new SelectDailog(null, true);
                            sa.setData(viewTable);
                            sa.setLocationRelativeTo(null);
                            JsonArray array = response.body().getAsJsonArray("data");
                            sa.getDtmHeader().setRowCount(0);
                            for (int i = 0; i < array.size(); i++) {
                                Vector row = new Vector();
                                row.add(array.get(i).getAsJsonObject().get("COLOUR_CD").getAsString());
                                row.add(array.get(i).getAsJsonObject().get("COLOUR_NAME").getAsString());
                                sa.getDtmHeader().addRow(row);
                            }
                            lb.setColumnSizeForTable(viewTable, sa.jPanelHeader.getWidth());
                            sa.setVisible(true);
                            if (sa.getReturnStatus() == SelectDailog.RET_OK) {
                                int row = viewTable.getSelectedRow();
                                if (row != -1) {
                                    color_cd = viewTable.getValueAt(row, 0).toString();
                                    jtxtColorName.setText(viewTable.getValueAt(row, 1).toString());
                                    jtxtRam.requestFocusInWindow();
                                }
                                sa.dispose();
                            }
                        } else {
                            lb.showMessageDailog(response.body().get("Cause").toString());
                        }
                    } else {
                        // handle request errors yourself
                        lb.showMessageDailog(response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                    lb.removeGlassPane(SeriesMasterController.this);
                }
            });
        } catch (Exception ex) {
            lb.printToLogFile("Exception at setData at account master in sales invoice", ex);
        }

    }

    private void setRamMaster(String param_cd, String value) {
        try {
            Call<JsonObject> call = lb.getRetrofit().create(StartUpAPI.class).getDataFromServer(param_cd, value.toUpperCase(),SkableHome.db_name,SkableHome.selected_year);
            lb.addGlassPane(
                    this);
            call.enqueue(
                    new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    lb.removeGlassPane(SeriesMasterController.this);
                    if (response.isSuccessful()) {
                        System.out.println(response.body().toString());
                        if (response.body().get("result").getAsInt() == 1) {
                            final SelectDailog sa = new SelectDailog(null, true);
                            sa.setData(viewTable);
                            sa.setLocationRelativeTo(null);
                            JsonArray array = response.body().getAsJsonArray("data");
                            sa.getDtmHeader().setRowCount(0);
                            for (int i = 0; i < array.size(); i++) {
                                Vector row = new Vector();
                                row.add(array.get(i).getAsJsonObject().get("RAM_CD").getAsString());
                                row.add(array.get(i).getAsJsonObject().get("RAM_NAME").getAsString());
                                sa.getDtmHeader().addRow(row);
                            }
                            lb.setColumnSizeForTable(viewTable, sa.jPanelHeader.getWidth());
                            sa.setVisible(true);
                            if (sa.getReturnStatus() == SelectDailog.RET_OK) {
                                int row = viewTable.getSelectedRow();
                                if (row != -1) {
                                    ram_cd = viewTable.getValueAt(row, 0).toString();
                                    jtxtRam.setText(viewTable.getValueAt(row, 1).toString());
                                    jtxtCamera.requestFocusInWindow();
                                }
                                sa.dispose();
                            }
                        } else {
                            lb.showMessageDailog(response.body().get("Cause").toString());
                        }
                    } else {
                        // handle request errors yourself
                        lb.showMessageDailog(response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                    lb.removeGlassPane(SeriesMasterController.this);
                }
            });
        } catch (Exception ex) {
            lb.printToLogFile("Exception at setData at account master in sales invoice", ex);
        }

    }

    private void setcameraMaster(String param_cd, String value) {
        try {
            Call<JsonObject> call = lb.getRetrofit().create(StartUpAPI.class).getDataFromServer(param_cd, value.toUpperCase(),SkableHome.db_name,SkableHome.selected_year);
            lb.addGlassPane(
                    this);
            call.enqueue(
                    new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    lb.removeGlassPane(SeriesMasterController.this);
                    if (response.isSuccessful()) {
                        System.out.println(response.body().toString());
                        if (response.body().get("result").getAsInt() == 1) {
                            final SelectDailog sa = new SelectDailog(null, true);
                            sa.setData(viewTable);
                            sa.setLocationRelativeTo(null);
                            JsonArray array = response.body().getAsJsonArray("data");
                            sa.getDtmHeader().setRowCount(0);
                            for (int i = 0; i < array.size(); i++) {
                                Vector row = new Vector();
                                row.add(array.get(i).getAsJsonObject().get("CAMERA_CD").getAsString());
                                row.add(array.get(i).getAsJsonObject().get("CAMERA_NAME").getAsString());
                                sa.getDtmHeader().addRow(row);
                            }
                            lb.setColumnSizeForTable(viewTable, sa.jPanelHeader.getWidth());
                            sa.setVisible(true);
                            if (sa.getReturnStatus() == SelectDailog.RET_OK) {
                                int row = viewTable.getSelectedRow();
                                if (row != -1) {
                                    camera_cd = viewTable.getValueAt(row, 0).toString();
                                    jtxtCamera.setText(viewTable.getValueAt(row, 1).toString());
                                    jtxtBattery.requestFocusInWindow();
                                }
                                sa.dispose();
                            }
                        } else {
                            lb.showMessageDailog(response.body().get("Cause").toString());
                        }
                    } else {
                        // handle request errors yourself
                        lb.showMessageDailog(response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                    lb.removeGlassPane(SeriesMasterController.this);
                }
            });
        } catch (Exception ex) {
            lb.printToLogFile("Exception at setData at account master in sales invoice", ex);
        }

    }

    private void setBatteryMaster(String param_cd, String value) {
        try {
            Call<JsonObject> call = lb.getRetrofit().create(StartUpAPI.class).getDataFromServer(param_cd, value.toUpperCase(),SkableHome.db_name,SkableHome.selected_year);
            lb.addGlassPane(
                    this);
            call.enqueue(
                    new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    lb.removeGlassPane(SeriesMasterController.this);
                    if (response.isSuccessful()) {
                        System.out.println(response.body().toString());
                        if (response.body().get("result").getAsInt() == 1) {
                            final SelectDailog sa = new SelectDailog(null, true);
                            sa.setData(viewTable);
                            sa.setLocationRelativeTo(null);
                            JsonArray array = response.body().getAsJsonArray("data");
                            sa.getDtmHeader().setRowCount(0);
                            for (int i = 0; i < array.size(); i++) {
                                Vector row = new Vector();
                                row.add(array.get(i).getAsJsonObject().get("BATTERY_CD").getAsString());
                                row.add(array.get(i).getAsJsonObject().get("BATTERY_NAME").getAsString());
                                sa.getDtmHeader().addRow(row);
                            }
                            lb.setColumnSizeForTable(viewTable, sa.jPanelHeader.getWidth());
                            sa.setVisible(true);
                            if (sa.getReturnStatus() == SelectDailog.RET_OK) {
                                int row = viewTable.getSelectedRow();
                                if (row != -1) {
                                    battery_cd = viewTable.getValueAt(row, 0).toString();
                                    jtxtBattery.setText(viewTable.getValueAt(row, 1).toString());
                                    jtxtQty.requestFocusInWindow();
                                }
                                sa.dispose();
                            }
                        } else {
                            lb.showMessageDailog(response.body().get("Cause").toString());
                        }
                    } else {
                        // handle request errors yourself
                        lb.showMessageDailog(response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                    lb.removeGlassPane(SeriesMasterController.this);
                }
            });
        } catch (Exception ex) {
            lb.printToLogFile("Exception at setData at account master in sales invoice", ex);
        }

    }

    public int getReturnStatus() {
        return returnStatus;
    }

    private void validateVoucher() {

        boolean flag = true;

        if (sr_cd.equalsIgnoreCase("")) {
            try {
                JsonObject call = lb.getRetrofit().create(SupportAPI.class).validateData("seriesmst", "sr_cd", "SR_ALIAS", jtxtSeriesAlias.getText()).execute().body();
                if (call
                        != null) {
                    if (call.get("result").getAsInt() == 0) {
                        lb.showMessageDailog("Alias already exist");
                        return;
                    } else {
                    }
                } else {
                    lb.showMessageDailog(call.get("message").getAsString());
                }
            } catch (IOException ex) {
                Logger.getLogger(SeriesMasterController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            try {
                JsonObject call = lb.getRetrofit().create(SupportAPI.class).ValidateDataEdit("seriesmst", "sr_cd", "SR_ALIAS", jtxtSeriesAlias.getText(), "sr_cd", sr_cd).execute().body();

                if (call
                        != null) {
                    if (call.get("result").getAsInt() == 0) {
                        lb.showMessageDailog("Alias already exist");
                        return;
                    } else {
                    }
                } else {
                    lb.showMessageDailog(call.get("message").getAsString());
                }
            } catch (IOException ex) {
                Logger.getLogger(SeriesMasterController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (lb.isBlank(jtxtSeriesAlias)) {
            lb.showMessageDailog("Series name can not be left blank");
            jtxtSeriesAlias.requestFocusInWindow();
            return;
        }

        if (lb.isBlank(jtxtItemName)) {
            lb.showMessageDailog("Series name can not be left blank");
            jtxtBrandName.requestFocusInWindow();
            return;
        }

        if (model_cd.equalsIgnoreCase("")) {
            lb.showMessageDailog("Enter valid Model Name");
            return;
        }

        if (memory_cd.equalsIgnoreCase("")) {
            lb.showMessageDailog("Enter valid Memory Name");
            return;
        }

        if (color_cd.equalsIgnoreCase("")) {
            lb.showMessageDailog("Enter valid Color Name");
            return;
        }

        if (ram_cd.equalsIgnoreCase("")) {
            lb.showMessageDailog("Enter valid Ram Name");
            return;
        }

        if (camera_cd.equalsIgnoreCase("")) {
            lb.showMessageDailog("Enter valid Camera Name");
            return;
        }

        if (battery_cd.equalsIgnoreCase("")) {
            lb.showMessageDailog("Enter valid Battery Name");
            return;
        }

        if (jTable1.getRowCount() != 0) {
            if (lb.isNumber(jlblTotal) != lb.isNumber(jtxtVal)) {
                lb.showMessageDailog("Total does not match");
                jtxtIMEI.requestFocusInWindow();
                return;
            }
        }

        addName();

        if (sr_cd.equalsIgnoreCase("")) {
            Call<JsonObject> call = lb.getRetrofit().create(SupportAPI.class).validateData("seriesmst", "sr_cd", "sr_name", jtxtItemName.getText());
            lb.addGlassPane(
                    this);
            call.enqueue(
                    new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> rspns) {
                    lb.removeGlassPane(SeriesMasterController.this);
                    if (rspns.isSuccessful()) {
                        if (rspns.body().get("result").getAsInt() == 0) {
                            lb.showMessageDailog("item already exist");
                            return;
                        } else {
                            saveVoucher();
                        }
                    } else {
                        lb.showMessageDailog(rspns.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                    lb.removeGlassPane(SeriesMasterController.this);
                }
            });
        } else {
            Call<JsonObject> call = lb.getRetrofit().create(SupportAPI.class).ValidateDataEdit("seriesmst", "sr_cd", "sr_name", jtxtSeriesAlias.getText(), "sr_cd", sr_cd);
            lb.addGlassPane(
                    this);
            call.enqueue(
                    new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> rspns) {
                    lb.removeGlassPane(SeriesMasterController.this);
                    if (rspns.isSuccessful()) {
                        if (rspns.body().get("result").getAsInt() == 0) {
                            lb.showMessageDailog("Alias already exist");
                            return;
                        } else {
                            saveVoucher();
                        }
                    } else {
                        lb.showMessageDailog(rspns.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                    lb.removeGlassPane(SeriesMasterController.this);
                }
            });

        }

    }

    private void saveVoucher() {
        addName();
        final ArrayList<OPBSrVal> detail = new ArrayList<>();
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            OPBSrVal data = new OPBSrVal();
            data.setRef_no("");
            data.setSr_cd(sr_cd);
            data.setSr_no(i + 1);
            data.setTag_no(jTable1.getValueAt(i, 0).toString());
            data.setImei(jTable1.getValueAt(i, 1).toString());
            data.setSerial(jTable1.getValueAt(i, 2).toString());
            data.setP_rate(lb.isNumber(jTable1.getValueAt(i, 3).toString()));
            jcmbBranch.setSelectedItem(jTable1.getValueAt(i, 4).toString());
            data.setBranch_cd(Constants.BRANCH.get(jcmbBranch.getSelectedIndex()).getBranch_cd());
            data.setRef_no(jTable1.getValueAt(i, 5).toString());
            detail.add(data);
        }
        String detailJson = new Gson().toJson(detail);
        Call<JsonObject> call = seriesAPI.appUpdateSeriesMaster(sr_cd, jtxtSeriesAlias.getText(), jtxtItemName.getText(),
                "", model_cd, memory_cd, color_cd, SkableHome.user_id, detailJson, (int) lb.isNumber(jtxtQty), lb.isNumber(jtxtVal), SkableHome.selected_year, ram_cd, camera_cd, battery_cd);
        lb.addGlassPane(this);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> rspns) {
                lb.removeGlassPane(SeriesMasterController.this);
                if (rspns.isSuccessful()) {
                    if (rspns.body().get("result").getAsInt() == 1) {
                        lb.showMessageDailog(rspns.body().get("Cause").getAsString());
                        if (smv != null) {
                            smv.getData();
                        }
                        SeriesMasterController.this.dispose();
                    } else {
                        lb.showMessageDailog(rspns.body().get("Cause").getAsString());
                    }
                } else {
                    lb.showMessageDailog(rspns.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                lb.removeGlassPane(SeriesMasterController.this);
            }
        });

    }

    private void clearRow() {
        jtxtIMEI.setText("");
        jtxtSerialNo.setText("");
    }

    private boolean validateRow() {

        if (jTable1.getSelectedRow() == -1) {
            if (lb.isBlank(jtxtIMEI) && lb.isBlank(jtxtSerialNo)) {
                lb.showMessageDailog("Tag is not same as table");
                jtxtIMEI.requestFocusInWindow();
                return false;
            }

            String tag = (lb.isBlank(jtxtIMEI) ? jtxtSerialNo.getText() : jtxtIMEI.getText());
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                if (tag.equalsIgnoreCase(jTable1.getValueAt(i, 0).toString())) {
                    lb.showMessageDailog("Tag already exist in table");
                    jtxtIMEI.requestFocusInWindow();
                    return false;
                }
            }
        }
        if (lb.isNumber(jtxtPurRate) == 0) {
            lb.showMessageDailog("Purchase rate can not be 0");
            jtxtPurRate.requestFocusInWindow();
            return false;
        }
        return true;
    }

    private void setDefaultRamValue() {
        try {
            Call<JsonObject> call = lb.getRetrofit().create(RamAPI.class).getDefaultRamValue();
            lb.addGlassPane(
                    this);
            call.enqueue(
                    new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    lb.removeGlassPane(SeriesMasterController.this);
                    if (response.isSuccessful()) {
                        System.out.println(response.body().toString());
                        if (response.body().get("result").getAsInt() == 1) {
                            JsonArray array = response.body().getAsJsonArray("data");
                            for (int i = 0; i < array.size(); i++) {
                                Vector row = new Vector();
                                ram_cd = (array.get(i).getAsJsonObject().get("RAM_CD").getAsString());
                                jtxtRam.setText(array.get(i).getAsJsonObject().get("RAM_NAME").getAsString());
                                jtxtCamera.requestFocusInWindow();
                            }
                        } else {
                            lb.showMessageDailog(response.body().get("Cause").toString());
                        }
                    } else {
                        // handle request errors yourself
                        lb.showMessageDailog(response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                    lb.removeGlassPane(SeriesMasterController.this);
                }
            });
        } catch (Exception ex) {
            lb.printToLogFile("Exception at setData at account master in sales invoice", ex);
        }

    }

    private void setDefaultCameraValue() {
        try {
            Call<JsonObject> call = lb.getRetrofit().create(CameraAPI.class).getDefaultCameraValue();
            lb.addGlassPane(
                    this);
            call.enqueue(
                    new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    lb.removeGlassPane(SeriesMasterController.this);
                    if (response.isSuccessful()) {
                        System.out.println(response.body().toString());
                        if (response.body().get("result").getAsInt() == 1) {
                            JsonArray array = response.body().getAsJsonArray("data");
                            for (int i = 0; i < array.size(); i++) {
                                Vector row = new Vector();
                                camera_cd = (array.get(i).getAsJsonObject().get("CAMERA_CD").getAsString());
                                jtxtCamera.setText(array.get(i).getAsJsonObject().get("CAMERA_NAME").getAsString());
                                jtxtBattery.requestFocusInWindow();
                            }
                        } else {
                            lb.showMessageDailog(response.body().get("Cause").toString());
                        }
                    } else {
                        // handle request errors yourself
                        lb.showMessageDailog(response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                    lb.removeGlassPane(SeriesMasterController.this);
                }
            });
        } catch (Exception ex) {
            lb.printToLogFile("Exception at setData at account master in sales invoice", ex);
        }

    }

    private void setDefaultBatteryValue() {
        try {
            Call<JsonObject> call = lb.getRetrofit().create(BateryMasterAPI.class).getDefaultBatteryMaster();
            lb.addGlassPane(
                    this);
            call.enqueue(
                    new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    lb.removeGlassPane(SeriesMasterController.this);
                    if (response.isSuccessful()) {
                        System.out.println(response.body().toString());
                        if (response.body().get("result").getAsInt() == 1) {
                            JsonArray array = response.body().getAsJsonArray("data");
                            for (int i = 0; i < array.size(); i++) {
                                battery_cd = (array.get(i).getAsJsonObject().get("BATTERY_CD").getAsString());
                                jtxtBattery.setText(array.get(i).getAsJsonObject().get("BATTERY_NAME").getAsString());
                                jtxtQty.requestFocusInWindow();
                            }
                        } else {
                            lb.showMessageDailog(response.body().get("Cause").toString());
                        }
                    } else {
                        // handle request errors yourself
                        lb.showMessageDailog(response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                    lb.removeGlassPane(SeriesMasterController.this);
                }
            });
        } catch (Exception ex) {
            lb.printToLogFile("Exception at setData at account master in sales invoice", ex);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cancelButton = new javax.swing.JButton();
        jbtnSave = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jtxtSeriesAlias = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jlblMemoryName = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtxtItemName = new javax.swing.JTextField();
        jtxtModelName = new javax.swing.JTextField();
        jtxtMemoryName = new javax.swing.JTextField();
        jtxtColorName = new javax.swing.JTextField();
        jtxtBrandName = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jtxtIMEI = new javax.swing.JTextField();
        jtxtSerialNo = new javax.swing.JTextField();
        jtxtPurRate = new javax.swing.JTextField();
        jcmbBranch = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jtxtQty = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtxtVal = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jlblTotal = new javax.swing.JLabel();
        jlblTypeName = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jlblSubTypeName = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jlblTaxName = new javax.swing.JLabel();
        jlblLine1 = new javax.swing.JLabel();
        jtxtRam = new javax.swing.JTextField();
        jlblLine2 = new javax.swing.JLabel();
        jtxtCamera = new javax.swing.JTextField();
        jlblLine3 = new javax.swing.JLabel();
        jtxtBattery = new javax.swing.JTextField();

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

        jbtnSave.setMnemonic('S');
        jbtnSave.setText("Save");
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

        jLabel10.setText("Series Alias");

        jtxtSeriesAlias.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtSeriesAliasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtSeriesAliasFocusLost(evt);
            }
        });
        jtxtSeriesAlias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtSeriesAliasKeyPressed(evt);
            }
        });

        jLabel9.setText("Brand");

        jLabel3.setText("Model Name");

        jlblMemoryName.setText("Memory Name");

        jLabel5.setText("Colour Name");

        jLabel2.setText("Series Name");

        jtxtItemName.setEnabled(false);

        jtxtModelName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtModelNameFocusLost(evt);
            }
        });
        jtxtModelName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtModelNameKeyPressed(evt);
            }
        });

        jtxtMemoryName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtMemoryNameFocusLost(evt);
            }
        });
        jtxtMemoryName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtMemoryNameKeyPressed(evt);
            }
        });

        jtxtColorName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtColorNameFocusLost(evt);
            }
        });
        jtxtColorName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtColorNameKeyPressed(evt);
            }
        });

        jtxtBrandName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jtxtIMEI.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtIMEIFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtIMEIFocusLost(evt);
            }
        });
        jtxtIMEI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtIMEIKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtIMEIKeyTyped(evt);
            }
        });

        jtxtSerialNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtSerialNoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtSerialNoFocusLost(evt);
            }
        });
        jtxtSerialNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtSerialNoKeyPressed(evt);
            }
        });

        jtxtPurRate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtPurRateFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtPurRateFocusLost(evt);
            }
        });
        jtxtPurRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtPurRateKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtPurRateKeyTyped(evt);
            }
        });

        jcmbBranch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcmbBranch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jcmbBranchKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jtxtIMEI, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtSerialNo, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtPurRate, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcmbBranch, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jcmbBranch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
            .addComponent(jtxtIMEI)
            .addComponent(jtxtSerialNo)
            .addComponent(jtxtPurRate)
        );

        jLabel6.setText("OPB Qty");

        jtxtQty.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtQtyFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtQtyFocusLost(evt);
            }
        });
        jtxtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtQtyKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtQtyKeyPressed(evt);
            }
        });

        jLabel7.setText("OPB Val");

        jtxtVal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtValFocusLost(evt);
            }
        });
        jtxtVal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtValKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtValKeyTyped(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TAG", "IMEI", "SERIAL NO", "RATE", "BRANCH", "REF_NO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setResizable(false);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(1).setResizable(false);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(2).setResizable(false);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(3).setResizable(false);
        jTable1.getColumnModel().getColumn(4).setResizable(false);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(70);
        jTable1.getColumnModel().getColumn(5).setMinWidth(0);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(5).setMaxWidth(0);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        jlblTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlblTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
        );

        jlblTypeName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setText("Type");

        jLabel11.setText("Sub Type");

        jlblSubTypeName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel12.setText("Tax Name");

        jlblTaxName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jlblLine1.setText("Line 1");

        jtxtRam.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtRamFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtRamFocusLost(evt);
            }
        });
        jtxtRam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtRamKeyPressed(evt);
            }
        });

        jlblLine2.setText("Line 2");

        jtxtCamera.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtCameraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtCameraFocusLost(evt);
            }
        });
        jtxtCamera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtCameraKeyPressed(evt);
            }
        });

        jlblLine3.setText("Line 3");

        jtxtBattery.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtBatteryFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtBatteryFocusLost(evt);
            }
        });
        jtxtBattery.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtBatteryKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblMemoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblLine1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblLine2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblLine3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jtxtMemoryName, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtModelName, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jtxtSeriesAlias, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1))
                            .addComponent(jtxtBrandName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtxtColorName, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtItemName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                            .addComponent(jtxtQty, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtVal, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtRam, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtCamera, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtBattery, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 1, Short.MAX_VALUE))
                    .addComponent(jlblTypeName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlblSubTypeName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlblTaxName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, jbtnSave});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtxtSeriesAlias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                            .addComponent(jtxtBrandName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtModelName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlblMemoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtMemoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtColorName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlblLine1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtRam, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlblLine2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlblLine3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtBattery, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtxtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtxtVal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlblTypeName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlblSubTypeName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlblTaxName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jtxtItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(jbtnSave))
                .addGap(49, 49, 49))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jLabel10, jPanel1, jtxtSeriesAlias});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jtxtItemName});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel3, jtxtModelName});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel6, jtxtQty});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel7, jtxtVal});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel8, jlblTypeName});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel11, jlblSubTypeName});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel12, jlblTaxName});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    private void jbtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSaveActionPerformed
        if (Constants.params.get("SHOW_MEMORY").toString().equalsIgnoreCase("0")) {
            memory_cd = "M17G001";
        }
        validateVoucher();
    }//GEN-LAST:event_jbtnSaveActionPerformed

    private void jbtnSaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbtnSaveKeyPressed
        lb.enterClick(evt);
    }//GEN-LAST:event_jbtnSaveKeyPressed

    private void jtxtSeriesAliasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtSeriesAliasFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtSeriesAliasFocusGained

    private void jtxtSeriesAliasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtSeriesAliasFocusLost
        // TODO add your handling code here:
        lb.toUpper(evt);
    }//GEN-LAST:event_jtxtSeriesAliasFocusLost

    private void jtxtSeriesAliasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtSeriesAliasKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jtxtModelName);
    }//GEN-LAST:event_jtxtSeriesAliasKeyPressed

    private void jtxtModelNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtModelNameKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_N) {
            if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
                ModelMasterController smc = new ModelMasterController(null, true, null);
                smc.setLocationRelativeTo(null);
                smc.setData("");
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_E) {
            if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
                ModelMasterController smc = new ModelMasterController(null, true, null);
                smc.setLocationRelativeTo(null);
                smc.setData(model_cd);
            }
        }
        if (lb.isEnter(evt)) {
            setModelData("12", jtxtModelName.getText().toUpperCase());
        }
    }//GEN-LAST:event_jtxtModelNameKeyPressed

    private void jtxtMemoryNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtMemoryNameKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_N) {
            if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
                MemoryMasterController smc = new MemoryMasterController(null, true, null, "", "");
                smc.setLocationRelativeTo(null);
                smc.setVisible(true);
            }
        }
        if (lb.isEnter(evt)) {
            setMemoryMaster("13", jtxtMemoryName.getText().toUpperCase());
        }
    }//GEN-LAST:event_jtxtMemoryNameKeyPressed

    private void jtxtColorNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtColorNameKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_N) {
            if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
                ColorMasterController smc = new ColorMasterController(null, true, null, "", "");
                smc.setLocationRelativeTo(null);
                smc.setVisible(true);
            }
        }
        if (lb.isEnter(evt)) {
            setColorMaster("14", jtxtColorName.getText().toUpperCase());
        }
    }//GEN-LAST:event_jtxtColorNameKeyPressed

    private void jtxtModelNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtModelNameFocusLost
        // TODO add your handling code here:
        addName();
    }//GEN-LAST:event_jtxtModelNameFocusLost

    private void jtxtMemoryNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtMemoryNameFocusLost
        // TODO add your handling code here:
        addName();
    }//GEN-LAST:event_jtxtMemoryNameFocusLost

    private void jtxtColorNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtColorNameFocusLost
        // TODO add your handling code here:
        addName();
    }//GEN-LAST:event_jtxtColorNameFocusLost

    private void jtxtQtyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtQtyFocusLost
        // TODO add your handling code here:
        lb.toInteger(evt);
    }//GEN-LAST:event_jtxtQtyFocusLost

    private void jtxtQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtQtyKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jtxtVal);
    }//GEN-LAST:event_jtxtQtyKeyPressed

    private void jtxtValFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtValFocusLost
        // TODO add your handling code here:
        lb.toDouble(evt);
        if (lb.isNumber(jtxtQty) > 0) {
            lb.confirmDialog("Do you want to split opb Qty?");
            if (lb.type) {
                jtxtIMEI.requestFocusInWindow();
            } else {
                jbtnSave.requestFocusInWindow();
            }
        } else {
            jbtnSave.requestFocusInWindow();
        }
    }//GEN-LAST:event_jtxtValFocusLost

    private void jtxtValKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtValKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jbtnSave);
    }//GEN-LAST:event_jtxtValKeyPressed

    private void jtxtQtyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtQtyFocusGained
        // TODO add your handling code here:
        addName();
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtQtyFocusGained

    private void jtxtQtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtQtyKeyTyped
        // TODO add your handling code here:
        lb.onlyNumber(evt, 3);
    }//GEN-LAST:event_jtxtQtyKeyTyped

    private void jtxtValKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtValKeyTyped
        // TODO add your handling code here:
        lb.onlyNumber(evt, -1);
    }//GEN-LAST:event_jtxtValKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (validateRow()) {
            int selRow = jTable1.getSelectedRow();
            if (selRow == -1) {
                Vector row = new Vector();
                row.add((lb.isBlank(jtxtIMEI) ? jtxtSerialNo.getText() : jtxtIMEI.getText()));
                row.add(jtxtIMEI.getText());
                row.add(jtxtSerialNo.getText());
                row.add(jtxtPurRate.getText());
                row.add(jcmbBranch.getSelectedItem().toString());
                row.add("");
                dtmDetail.addRow(row);
            } else {
                jTable1.setValueAt(jtxtPurRate.getText(), selRow, 3);
                jTable1.setValueAt(jcmbBranch.getSelectedItem().toString(), selRow, 4);
            }
            jTable1.clearSelection();
            clearRow();
            setTotal();
            if (lb.isNumber(jtxtVal) == lb.isNumber(jtxtPurRate)) {
                jbtnSave.requestFocusInWindow();
            } else {
                jtxtIMEI.requestFocusInWindow();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtxtIMEIFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtIMEIFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtIMEIFocusGained

    private void jtxtIMEIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtIMEIKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jtxtSerialNo);
    }//GEN-LAST:event_jtxtIMEIKeyPressed

    private void jtxtSerialNoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtSerialNoFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtSerialNoFocusGained

    private void jtxtIMEIFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtIMEIFocusLost
        // TODO add your handling code here:
        lb.toUpper(evt);
    }//GEN-LAST:event_jtxtIMEIFocusLost

    private void jtxtSerialNoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtSerialNoFocusLost
        // TODO add your handling code here:
        lb.toUpper(evt);
    }//GEN-LAST:event_jtxtSerialNoFocusLost

    private void jtxtSerialNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtSerialNoKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jtxtPurRate);
    }//GEN-LAST:event_jtxtSerialNoKeyPressed

    private void jtxtPurRateFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtPurRateFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtPurRateFocusGained

    private void jtxtPurRateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtPurRateFocusLost
        // TODO add your handling code here:
        lb.toDouble(evt);
    }//GEN-LAST:event_jtxtPurRateFocusLost

    private void jtxtPurRateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtPurRateKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jcmbBranch);
    }//GEN-LAST:event_jtxtPurRateKeyPressed

    private void jtxtPurRateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtPurRateKeyTyped
        // TODO add your handling code here:
        lb.onlyNumber(evt, -1);
    }//GEN-LAST:event_jtxtPurRateKeyTyped

    private void jcmbBranchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcmbBranchKeyPressed
        // TODO add your handling code here:
        lb.enterFocus(evt, jButton1);
    }//GEN-LAST:event_jcmbBranchKeyPressed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        // TODO add your handling code here:
        lb.enterClick(evt);
    }//GEN-LAST:event_jButton1KeyPressed

    private void jtxtIMEIKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtIMEIKeyTyped
        // TODO add your handling code here:
        lb.fixLength(evt, 15);
    }//GEN-LAST:event_jtxtIMEIKeyTyped

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int selRow = jTable1.getSelectedRow();
            if (selRow != -1) {
                jtxtIMEI.setText(jTable1.getValueAt(selRow, 1).toString());
                jtxtSerialNo.setText(jTable1.getValueAt(selRow, 2).toString());
                jtxtPurRate.setText(jTable1.getValueAt(selRow, 3).toString());
                jcmbBranch.setSelectedItem(jTable1.getValueAt(selRow, 4).toString());
                jtxtIMEI.requestFocusInWindow();
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_D) {
            if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
                int row = jTable1.getSelectedRow();
                if (row != -1) {
                    lb.confirmDialog("Do you want to delete this row?");
                    if (lb.type) {
                        dtmDetail.removeRow(row);
                    }
                }
            }
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void jtxtRamFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtRamFocusLost
        // TODO add your handling code here:
        lb.toUpper(evt);
        addName();
    }//GEN-LAST:event_jtxtRamFocusLost

    private void jtxtRamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtRamKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_N) {
            if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
                RamMasterController smc = new RamMasterController(null, true, null, "", "");
                smc.setLocationRelativeTo(null);
                smc.setVisible(true);
            }
        }
        if (lb.isEnter(evt)) {
            if (jtxtRam.getText().isEmpty()) {
                setDefaultRamValue();
            } else {
                setRamMaster("40", jtxtRam.getText());
            }
        }
    }//GEN-LAST:event_jtxtRamKeyPressed

    private void jtxtCameraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtCameraFocusLost
        // TODO add your handling code here:
        lb.toUpper(evt);
        addName();
    }//GEN-LAST:event_jtxtCameraFocusLost

    private void jtxtCameraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtCameraKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_N) {
            if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
                CameraMasterController smc = new CameraMasterController(null, true, null, "", "");
                smc.setLocationRelativeTo(null);
                smc.setVisible(true);
            }
        }
        if (lb.isEnter(evt)) {
            if (jtxtCamera.getText().isEmpty()) {
                setDefaultCameraValue();
            } else {
                setcameraMaster("41", jtxtCamera.getText());
            }
        }
    }//GEN-LAST:event_jtxtCameraKeyPressed

    private void jtxtBatteryFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtBatteryFocusLost
        // TODO add your handling code here:
        lb.toUpper(evt);
        addName();
    }//GEN-LAST:event_jtxtBatteryFocusLost

    private void jtxtBatteryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtBatteryKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_N) {
            if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
                BatteryMasterController smc = new BatteryMasterController(null, true, null, "", "");
                smc.setLocationRelativeTo(null);
                smc.setVisible(true);
            }
        }
        if (lb.isEnter(evt)) {
            if (jtxtBattery.getText().isEmpty()) {
                setDefaultBatteryValue();
            } else {
                setBatteryMaster("42", jtxtBattery.getText());
            }
        }
    }//GEN-LAST:event_jtxtBatteryKeyPressed

    private void jtxtRamFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtRamFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtRamFocusGained

    private void jtxtCameraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtCameraFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtCameraFocusGained

    private void jtxtBatteryFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtBatteryFocusGained
        // TODO add your handling code here:
        lb.selectAll(evt);
    }//GEN-LAST:event_jtxtBatteryFocusGained

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JComboBox jcmbBranch;
    private javax.swing.JLabel jlblLine1;
    private javax.swing.JLabel jlblLine2;
    private javax.swing.JLabel jlblLine3;
    private javax.swing.JLabel jlblMemoryName;
    private javax.swing.JLabel jlblSubTypeName;
    private javax.swing.JLabel jlblTaxName;
    private javax.swing.JLabel jlblTotal;
    private javax.swing.JLabel jlblTypeName;
    private javax.swing.JTextField jtxtBattery;
    private javax.swing.JLabel jtxtBrandName;
    private javax.swing.JTextField jtxtCamera;
    private javax.swing.JTextField jtxtColorName;
    private javax.swing.JTextField jtxtIMEI;
    private javax.swing.JTextField jtxtItemName;
    private javax.swing.JTextField jtxtMemoryName;
    private javax.swing.JTextField jtxtModelName;
    private javax.swing.JTextField jtxtPurRate;
    private javax.swing.JTextField jtxtQty;
    private javax.swing.JTextField jtxtRam;
    private javax.swing.JTextField jtxtSerialNo;
    private javax.swing.JTextField jtxtSeriesAlias;
    private javax.swing.JTextField jtxtVal;
    // End of variables declaration//GEN-END:variables
    private int returnStatus = RET_CANCEL;
}
