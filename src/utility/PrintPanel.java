/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import net.sf.jasperreports.engine.data.JsonDataSource;
import retrofitAPI.BankAPI;
import retrofitAPI.CashPRAPI;
import retrofitAPI.DCAPI;
import retrofitAPI.DNCNApi;
import retrofitAPI.JobSheetAPI;
import retrofitAPI.OrderBookAPI;
import retrofitAPI.PurchaseReturnAPI;
import retrofitAPI.QuotationAPI;
import retrofitAPI.SalesAPI;
import retrofitAPI.SalesReturnAPI;
import retrofitAPI.StkTrAPI;
import retrofitAPI.StkTrOutAPI;
import skable.Constants;
import skable.SkableHome;
import support.AmountInWords;
import support.Library;

/**
 *
 * @author Bhaumik Shah
 */
public class PrintPanel extends javax.swing.JDialog {

    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;
    Library lb = Library.getInstance();

    /**
     * Creates new form PrintPanel
     */
    public PrintPanel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

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
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(10, 10, (screenSize.width) - 50, (screenSize.height) - 50);
        this.setLocationRelativeTo(null);
    }

    public void getSalesBillPrint(String ref_no, String type) {
        try {
            SalesAPI salesAPI = lb.getRetrofit().create(SalesAPI.class);
            JsonObject call = salesAPI.GetSalesBillPrint(ref_no, SkableHome.db_name, SkableHome.selected_year).execute().body();
            JsonObject call1 = salesAPI.GetSalesBillTaxPrint(ref_no, SkableHome.db_name, SkableHome.selected_year).execute().body();

            if (call != null) {
                JsonObject result = call;
                if (result.get("result").getAsInt() == 1) {
                    JsonArray array = call.getAsJsonArray("data");
                    JsonArray array1 = call1.getAsJsonArray("data");
                    if (array != null) {
                        try {
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "file1.txt");
                            FileWriter file2 = new FileWriter(System.getProperty("user.dir") + File.separator + "file2.txt");
                            file.write(array.toString());
                            file2.write(array1.toString());
                            file.close();
                            file2.close();
                            File jsonFile = new File(System.getProperty("user.dir") + File.separator + "file1.txt");
                            File jsonFile1 = new File(System.getProperty("user.dir") + File.separator + "file2.txt");
                            JsonDataSource dataSource = new JsonDataSource(jsonFile);
                            JsonDataSource dataSource1 = new JsonDataSource(jsonFile1);
                            HashMap params = new HashMap();
                            params.put("dir", System.getProperty("user.dir"));
                            params.put("comp_name", Constants.COMPANY_NAME);
                            params.put("cst_no", (array.get(0).getAsJsonObject().get("COMPANY_CST").getAsString()));
                            params.put("add1", SkableHome.selected_branch.getAddress1());
                            if (array.get(0).getAsJsonObject().get("tax_type").getAsInt() == 0) {
                                params.put("tax_title", "Vat");
                                params.put("add_tax_title", "Add Vat");
                                params.put("tin_no", "Tin No : " + (array.get(0).getAsJsonObject().get("COMPANY_TIN").getAsString()));
                                if (array.get(0).getAsJsonObject().get("V_TYPE").getAsInt() == 0) {
                                    params.put("bill_type", "Retail Invoice");
                                    params.put("inv_type", "");

                                } else {
                                    params.put("bill_type", "Tax Invoice");
                                    params.put("inv_type", "TI");
                                }
                            } else if (array.get(0).getAsJsonObject().get("tax_type").getAsInt() == 1) {
                                params.put("tax_title", "State GST");
                                params.put("bill_type", "Tax Invoice");
                                params.put("add_tax_title", "Central GST");
                                params.put("tin_no", "GST No : " + (array.get(0).getAsJsonObject().get("COMPANY_GST_NO").getAsString()));
                                params.put("inv_type", "SI");
                            } else {
                                params.put("tax_title", "IGST");
                                params.put("add_tax_title", "");
                                params.put("bill_type", "Tax Invoice");
                                params.put("tin_no", "GST No : " + (array.get(0).getAsJsonObject().get("COMPANY_GST_NO").getAsString()));
                                params.put("inv_type", "SI");
                            }
                            params.put("add2", SkableHome.selected_branch.getAddress2());
                            params.put("add3", SkableHome.selected_branch.getAddress3());
                            params.put("email", SkableHome.selected_branch.getEmail());
                            params.put("mobile", SkableHome.selected_branch.getPhone());
                            params.put("tax_data", dataSource1);
                            if (Constants.params.get("BILL_HEADER").toString().equalsIgnoreCase("0")) {
                                if (type.equalsIgnoreCase("0")) {
                                    lb.reportGenerator(Constants.params.get("FILE_NAME").toString() + ".jasper", params, dataSource, jPanel1);
                                } else {
                                    lb.reportGenerator(Constants.params.get("FILE_NAME").toString() + "WoCalc.jasper", params, dataSource, jPanel1);
                                }
                            } else if (Constants.params.get("BILL_HEADER").toString().equalsIgnoreCase("1")) {
                                if (type.equalsIgnoreCase("0")) {
                                    lb.reportGenerator(Constants.params.get("FILE_NAME").toString() + "PDF.jasper", params, dataSource, jPanel1);
                                } else {
                                    lb.reportGenerator(Constants.params.get("FILE_NAME").toString() + "PDFWoCalc.jasper", params, dataSource, jPanel1);
                                }
                            } else {
                                lb.confirmDialog("Do you want to print sales Bill with header?");
                                if (lb.type) {
                                    if (type.equalsIgnoreCase("0")) {
                                        lb.reportGenerator(Constants.params.get("FILE_NAME").toString() + "PDF.jasper", params, dataSource, jPanel1);
                                    } else {
                                        lb.reportGenerator(Constants.params.get("FILE_NAME").toString() + "PDFWoCalc.jasper", params, dataSource, jPanel1);
                                    }
                                } else {
                                    if (type.equalsIgnoreCase("0")) {
                                        lb.reportGenerator(Constants.params.get("FILE_NAME").toString() + ".jasper", params, dataSource, jPanel1);
                                    } else {
                                        lb.reportGenerator(Constants.params.get("FILE_NAME").toString() + "WoCalc.jasper", params, dataSource, jPanel1);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                        }
                    }
                } else {
                    lb.showMessageDailog(call.get("Cause").getAsString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PrintPanel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getQuoatePrint(String ref_no) {
        try {
            QuotationAPI salesAPI = lb.getRetrofit().create(QuotationAPI.class);
            JsonObject call = salesAPI.getQuotationBill(ref_no, SkableHome.db_name, SkableHome.selected_year).execute().body();

            if (call != null) {
                JsonObject result = call;
                if (result.get("result").getAsInt() == 1) {
                    JsonArray array = call.getAsJsonArray("data");
                    if (array != null) {
                        try {
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "file1.txt");
                            file.write(array.toString());
                            file.close();
                            File jsonFile = new File(System.getProperty("user.dir") + File.separator + "file1.txt");
                            JsonDataSource dataSource = new JsonDataSource(jsonFile);
                            HashMap params = new HashMap();
                            params.put("dir", System.getProperty("user.dir"));
                            params.put("comp_name", Constants.COMPANY_NAME);
                            params.put("add1", SkableHome.selected_branch.getAddress1());
                            params.put("tin_no", "GST No : " + (array.get(0).getAsJsonObject().get("COMPANY_GST_NO").getAsString()));
                            params.put("add2", SkableHome.selected_branch.getAddress2());
                            params.put("add3", SkableHome.selected_branch.getAddress3());
                            params.put("email", SkableHome.selected_branch.getEmail());
                            params.put("mobile", SkableHome.selected_branch.getPhone());
                            if (Constants.params.get("BILL_HEADER").toString().equalsIgnoreCase("0")) {
                                lb.reportGenerator(Constants.params.get("QUOATE_FILE").toString() + ".jasper", params, dataSource, jPanel1);
                            } else if (Constants.params.get("BILL_HEADER").toString().equalsIgnoreCase("1")) {
                                lb.reportGenerator(Constants.params.get("QUOATE_FILE").toString() + "PDF.jasper", params, dataSource, jPanel1);
                            } else {
                                lb.confirmDialog("Do you want to print quoatation with header?");
                                if (lb.type) {
                                    lb.reportGenerator(Constants.params.get("QUOATE_FILE").toString() + "PDF.jasper", params, dataSource, jPanel1);
                                } else {
                                    lb.reportGenerator(Constants.params.get("QUOATE_FILE").toString() + ".jasper", params, dataSource, jPanel1);
                                }
                            }
                        } catch (Exception ex) {
                        }
                    }
                } else {
                    lb.showMessageDailog(call.get("Cause").getAsString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PrintPanel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getJobSheetPrint(String ref_no) {
        try {
            JobSheetAPI salesAPI = lb.getRetrofit().create(JobSheetAPI.class);
            JsonObject call = salesAPI.getJobSheetDetail(ref_no, SkableHome.db_name, SkableHome.selected_year).execute().body();

            if (call != null) {
                JsonObject result = call;
                if (result.get("result").getAsInt() == 1) {
                    JsonArray array = call.getAsJsonArray("data");
                    if (array != null) {
                        try {
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "file1.txt");
                            file.write(array.toString());
                            file.close();
                            File jsonFile = new File(System.getProperty("user.dir") + File.separator + "file1.txt");
                            JsonDataSource dataSource = new JsonDataSource(jsonFile);
                            HashMap params = new HashMap();
                            params.put("dir", System.getProperty("user.dir"));
                            params.put("comp_name", Constants.COMPANY_NAME);
                            params.put("add1", SkableHome.selected_branch.getAddress1());
                            params.put("tin_no", "GST No : " + (array.get(0).getAsJsonObject().get("COMPANY_GST_NO").getAsString()));
                            params.put("add2", SkableHome.selected_branch.getAddress2());
                            params.put("add3", SkableHome.selected_branch.getAddress3());
                            params.put("email", SkableHome.selected_branch.getEmail());
                            params.put("mobile", SkableHome.selected_branch.getPhone());
                            if (Constants.params.get("BILL_HEADER").toString().equalsIgnoreCase("0")) {
                                lb.reportGenerator(Constants.params.get("JOB_FILE").toString() + ".jasper", params, dataSource, jPanel1);
                            } else if (Constants.params.get("BILL_HEADER").toString().equalsIgnoreCase("1")) {
                                lb.reportGenerator(Constants.params.get("JOB_FILE").toString() + "PDF.jasper", params, dataSource, jPanel1);
                            } else {
                                lb.confirmDialog("Do you want to print Job Sheet with header?");
                                if (lb.type) {
                                    lb.reportGenerator(Constants.params.get("JOB_FILE").toString() + "PDF.jasper", params, dataSource, jPanel1);
                                } else {
                                    lb.reportGenerator(Constants.params.get("JOB_FILE").toString() + ".jasper", params, dataSource, jPanel1);
                                }
                            }
                        } catch (Exception ex) {
                        }
                    }
                } else {
                    lb.showMessageDailog(call.get("Cause").getAsString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PrintPanel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getJobSheetPrintWithoutPreview(String ref_no) {
        try {
            JobSheetAPI salesAPI = lb.getRetrofit().create(JobSheetAPI.class);
            JsonObject call = salesAPI.getJobSheetDetail(ref_no, SkableHome.db_name, SkableHome.selected_year).execute().body();

            if (call != null) {
                JsonObject result = call;
                if (result.get("result").getAsInt() == 1) {
                    JsonArray array = call.getAsJsonArray("data");
                    if (array != null) {
                        try {
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "file1.txt");
                            file.write(array.toString());
                            file.close();
                            File jsonFile = new File(System.getProperty("user.dir") + File.separator + "file1.txt");
                            JsonDataSource dataSource = new JsonDataSource(jsonFile);
                            HashMap params = new HashMap();
                            params.put("dir", System.getProperty("user.dir"));
                            params.put("comp_name", Constants.COMPANY_NAME);
                            params.put("add1", SkableHome.selected_branch.getAddress1());
                            params.put("tin_no", "GST No : " + (array.get(0).getAsJsonObject().get("COMPANY_GST_NO").getAsString()));
                            params.put("add2", SkableHome.selected_branch.getAddress2());
                            params.put("add3", SkableHome.selected_branch.getAddress3());
                            params.put("email", SkableHome.selected_branch.getEmail());
                            params.put("mobile", SkableHome.selected_branch.getPhone());
                            if (Constants.params.get("BILL_HEADER").toString().equalsIgnoreCase("0")) {
                                lb.reportPrinter(Constants.params.get("JOB_FILE").toString() + ".jasper", params, dataSource);
                            } else if (Constants.params.get("BILL_HEADER").toString().equalsIgnoreCase("1")) {
                                lb.reportPrinter(Constants.params.get("JOB_FILE").toString() + "PDF.jasper", params, dataSource);
                            } else {
                                lb.confirmDialog("Do you want to print Job Sheet with header?");
                                if (lb.type) {
                                    lb.reportPrinter(Constants.params.get("JOB_FILE").toString() + "PDF.jasper", params, dataSource);
                                } else {
                                    lb.reportPrinter(Constants.params.get("JOB_FILE").toString() + ".jasper", params, dataSource);
                                }
                            }
                        } catch (Exception ex) {
                        }
                    }
                } else {
                    lb.showMessageDailog(call.get("Cause").getAsString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PrintPanel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getBulkSalesBillPrint(String ref_no, String type) {
        try {
            SalesAPI salesAPI = lb.getRetrofit().create(SalesAPI.class);
            JsonObject call = salesAPI.GetBulkSalesBillPrint(ref_no, SkableHome.db_name, SkableHome.selected_year).execute().body();
            JsonObject call1 = salesAPI.GetSalesBillTaxPrint(ref_no, SkableHome.db_name, SkableHome.selected_year).execute().body();
            if (call != null) {
                JsonObject result = call;
                if (result.get("result").getAsInt() == 1) {
                    JsonArray array = call.getAsJsonArray("data");
                    JsonArray array1 = call1.getAsJsonArray("data");
                    if (array != null) {
                        try {
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "file1.txt");
                            FileWriter file2 = new FileWriter(System.getProperty("user.dir") + File.separator + "file2.txt");
                            file.write(array.toString());
                            file2.write(array1.toString());
                            file.close();
                            file2.close();
                            File jsonFile = new File(System.getProperty("user.dir") + File.separator + "file1.txt");
                            File jsonFile1 = new File(System.getProperty("user.dir") + File.separator + "file2.txt");
                            JsonDataSource dataSource = new JsonDataSource(jsonFile);
                            JsonDataSource dataSource1 = new JsonDataSource(jsonFile1);
                            HashMap params = new HashMap();
                            params.put("dir", System.getProperty("user.dir"));
                            params.put("comp_name", Constants.COMPANY_NAME);
                            params.put("cst_no", (array.get(0).getAsJsonObject().get("COMPANY_CST").getAsString()));
                            params.put("add1", SkableHome.selected_branch.getAddress1());
                            if (array.get(0).getAsJsonObject().get("tax_type").getAsInt() == 0) {
                                params.put("tax_title", "Vat");
                                params.put("add_tax_title", "Add Vat");
                                params.put("tin_no", "Tin No : " + (array.get(0).getAsJsonObject().get("COMPANY_TIN").getAsString()));
                                if (array.get(0).getAsJsonObject().get("V_TYPE").getAsInt() == 0) {
                                    params.put("bill_type", "Retail Invoice");
                                } else {
                                    params.put("bill_type", "Tax Invoice");
                                }
                            } else if (array.get(0).getAsJsonObject().get("tax_type").getAsInt() == 1) {
                                params.put("tax_title", "State GST");
                                params.put("bill_type", "Tax Invoice");
                                params.put("add_tax_title", "Central GST");
                                params.put("tin_no", "GST No : " + (array.get(0).getAsJsonObject().get("COMPANY_GST_NO").getAsString()));
                            } else {
                                params.put("tax_title", "IGST");
                                params.put("add_tax_title", "");
                                params.put("bill_type", "Tax Invoice");
                                params.put("tin_no", "GST No : " + (array.get(0).getAsJsonObject().get("COMPANY_GST_NO").getAsString()));
                            }
                            params.put("add2", SkableHome.selected_branch.getAddress2());
                            params.put("add3", SkableHome.selected_branch.getAddress3());
                            params.put("email", SkableHome.selected_branch.getEmail());
                            params.put("mobile", SkableHome.selected_branch.getPhone());
                            params.put("tax_data", dataSource1);
                            if (Constants.params.get("BILL_HEADER").toString().equalsIgnoreCase("0")) {
                                if (type.equalsIgnoreCase("0")) {
                                    lb.reportGenerator(Constants.params.get("FILE_NAME").toString() + "Bulk.jasper", params, dataSource, jPanel1);
                                } else {
                                    lb.reportGenerator(Constants.params.get("FILE_NAME").toString() + "Bulkwocalc.jasper", params, dataSource, jPanel1);
                                }
                            } else if (Constants.params.get("BILL_HEADER").toString().equalsIgnoreCase("1")) {
                                if (type.equalsIgnoreCase("0")) {
                                    lb.reportGenerator(Constants.params.get("FILE_NAME").toString() + "BulkPDF.jasper", params, dataSource, jPanel1);
                                } else {
                                    lb.reportGenerator(Constants.params.get("FILE_NAME").toString() + "BulkPDFwocalc.jasper", params, dataSource, jPanel1);
                                }
                            } else {
                                lb.confirmDialog("Do you want to print sales Bill with header?");
                                if (lb.type) {
                                    if (type.equalsIgnoreCase("0")) {
                                        lb.reportGenerator(Constants.params.get("FILE_NAME").toString() + "BulkPDF.jasper", params, dataSource, jPanel1);
                                    } else {
                                        lb.reportGenerator(Constants.params.get("FILE_NAME").toString() + "BulkPDFwocalc.jasper", params, dataSource, jPanel1);
                                    }
                                } else {
                                    if (type.equalsIgnoreCase("0")) {
                                        lb.reportGenerator(Constants.params.get("FILE_NAME").toString() + "Bulk.jasper", params, dataSource, jPanel1);
                                    } else {
                                        lb.reportGenerator(Constants.params.get("FILE_NAME").toString() + "Bulkwocalc.jasper", params, dataSource, jPanel1);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                        }
                    }
                } else {
                    lb.showMessageDailog(call.get("Cause").getAsString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PrintPanel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getSalesReturnBillPrint(String ref_no) {
        try {
            SalesReturnAPI salesAPI = lb.getRetrofit().create(SalesReturnAPI.class);
            JsonObject call = salesAPI.GetSalesReturnPrint(ref_no, SkableHome.db_name, SkableHome.selected_year).execute().body();
            JsonObject call1 = salesAPI.GetSalesReturnTaxPrint(ref_no, SkableHome.db_name, SkableHome.selected_year).execute().body();
            if (call
                    != null) {
                JsonObject result = call;
                if (result.get("result").getAsInt() == 1) {
                    JsonArray array = call.getAsJsonArray("data");
                    JsonArray array1 = call1.getAsJsonArray("data");
                    if (array != null) {
                        try {
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "file1.txt");
                            FileWriter file2 = new FileWriter(System.getProperty("user.dir") + File.separator + "file2.txt");
                            file.write(array.toString());
                            file2.write(array1.toString());
                            file.close();
                            file2.close();
                            File jsonFile = new File(System.getProperty("user.dir") + File.separator + "file1.txt");
                            File jsonFile1 = new File(System.getProperty("user.dir") + File.separator + "file2.txt");
                            JsonDataSource dataSource = new JsonDataSource(jsonFile);
                            JsonDataSource dataSource1 = new JsonDataSource(jsonFile1);
                            HashMap params = new HashMap();
                            if (array.get(0).getAsJsonObject().get("TAX_TYPE").getAsInt() == 0) {
                                params.put("tax_title", "Vat");
                                params.put("add_tax_title", "Add Vat");
                                params.put("tin_no", "Tin No : " + (array.get(0).getAsJsonObject().get("COMPANY_TIN").getAsString()));
                            } else if (array.get(0).getAsJsonObject().get("TAX_TYPE").getAsInt() == 1) {
                                params.put("tax_title", "State GST");
                                params.put("add_tax_title", "Central GST");
                                params.put("tin_no", "GST No : " + (array.get(0).getAsJsonObject().get("COMPANY_GST_NO").getAsString()));
                            } else {
                                params.put("tax_title", "IGST");
                                params.put("add_tax_title", "");
                                params.put("tin_no", "GST No : " + (array.get(0).getAsJsonObject().get("COMPANY_GST_NO").getAsString()));
                            }
                            params.put("dir", System.getProperty("user.dir"));
                            params.put("comp_name", Constants.COMPANY_NAME);
                            params.put("cst_no", (array.get(0).getAsJsonObject().get("COMPANY_CST").getAsString()));
                            params.put("add1", SkableHome.selected_branch.getAddress1());
                            params.put("add2", SkableHome.selected_branch.getAddress2());
                            params.put("add3", SkableHome.selected_branch.getAddress3());
                            params.put("email", SkableHome.selected_branch.getEmail());
                            params.put("mobile", SkableHome.selected_branch.getPhone());
                            params.put("tax_data", dataSource1);
                            lb.confirmDialog("Do you want to print Bill with header?");
                            if (lb.type) {
                                lb.reportGenerator("SalesReturnPDF.jasper", params, dataSource, jPanel1);
                            } else {
                                lb.reportGenerator("SalesReturn.jasper", params, dataSource, jPanel1);
                            }
                        } catch (Exception ex) {
                        }
                    }
                } else {
                    lb.showMessageDailog(call.get("Cause").getAsString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PrintPanel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getPurchaseReturnBillPrint(String ref_no, String type) {
        try {
            PurchaseReturnAPI salesAPI = lb.getRetrofit().create(PurchaseReturnAPI.class);
            JsonObject call = salesAPI.GetPurchaseReturnPrint(ref_no, SkableHome.db_name, SkableHome.selected_year).execute().body();
            JsonObject call1 = salesAPI.GetPurchaseReturnTaxPrint(ref_no, SkableHome.db_name, SkableHome.selected_year).execute().body();
            if (call
                    != null) {
                JsonObject result = call;
                if (result.get("result").getAsInt() == 1) {
                    JsonArray array = call.getAsJsonArray("data");
                    JsonArray array1 = call1.getAsJsonArray("data");
                    if (array != null) {
                        try {
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "file1.txt");
                            FileWriter file2 = new FileWriter(System.getProperty("user.dir") + File.separator + "file2.txt");
                            file.write(array.toString());
                            file2.write(array1.toString());
                            file.close();
                            file2.close();
                            File jsonFile = new File(System.getProperty("user.dir") + File.separator + "file1.txt");
                            File jsonFile1 = new File(System.getProperty("user.dir") + File.separator + "file2.txt");
                            JsonDataSource dataSource = new JsonDataSource(jsonFile);
                            JsonDataSource dataSource1 = new JsonDataSource(jsonFile1);
                            HashMap params = new HashMap();
                            params.put("dir", System.getProperty("user.dir"));
                            params.put("comp_name", Constants.COMPANY_NAME);
                            params.put("bill_type", "Purchase Return");
                            params.put("inv_type", "PR");
                            if (array.get(0).getAsJsonObject().get("tax_type").getAsInt() == 0) {
                                params.put("tax_title", "Vat");
                                params.put("add_tax_title", "Add Vat");
                                params.put("tin_no", "Tin No : " + (array.get(0).getAsJsonObject().get("COMPANY_TIN").getAsString()));
                            } else if (array.get(0).getAsJsonObject().get("tax_type").getAsInt() == 1) {
                                params.put("tax_title", "State GST");
                                params.put("add_tax_title", "Central GST");
                                params.put("tin_no", "GST No : " + (array.get(0).getAsJsonObject().get("COMPANY_GST_NO").getAsString()));
                            } else {
                                params.put("tax_title", "IGST");
                                params.put("add_tax_title", "");
                                params.put("tin_no", "GST No : " + (array.get(0).getAsJsonObject().get("COMPANY_GST_NO").getAsString()));
                            }
                            params.put("add1", SkableHome.selected_branch.getAddress1());
                            params.put("words", new AmountInWords().convertToWords((int) lb.isNumber(array.get(0).getAsJsonObject().get("NET_AMT").getAsString())));
                            params.put("add2", SkableHome.selected_branch.getAddress2());
                            params.put("add3", SkableHome.selected_branch.getAddress3());
                            params.put("email", SkableHome.selected_branch.getEmail());
                            params.put("mobile", SkableHome.selected_branch.getPhone());
                            params.put("tax_data", dataSource1);
                            if (Constants.params.get("BILL_HEADER").toString().equalsIgnoreCase("0")) {
                                if (type.equalsIgnoreCase("0")) {
                                    lb.reportGenerator(Constants.params.get("PUR_RETURN_FILE_NAME").toString() + ".jasper", params, dataSource, jPanel1);
                                } else {
                                    lb.reportGenerator(Constants.params.get("PUR_RETURN_FILE_NAME").toString() + "WoCalc.jasper", params, dataSource, jPanel1);
                                }
                            } else if (Constants.params.get("BILL_HEADER").toString().equalsIgnoreCase("1")) {
                                if (type.equalsIgnoreCase("0")) {
                                    lb.reportGenerator(Constants.params.get("PUR_RETURN_FILE_NAME").toString() + "PDF.jasper", params, dataSource, jPanel1);
                                } else {
                                    lb.reportGenerator(Constants.params.get("PUR_RETURN_FILE_NAME").toString() + "PDFWoCalc.jasper", params, dataSource, jPanel1);
                                }
                            } else {
                                lb.confirmDialog("Do you want to print sales Bill with header?");
                                if (lb.type) {
                                    if (type.equalsIgnoreCase("0")) {
                                        lb.reportGenerator(Constants.params.get("PUR_RETURN_FILE_NAME").toString() + "PDF.jasper", params, dataSource, jPanel1);
                                    } else {
                                        lb.reportGenerator(Constants.params.get("PUR_RETURN_FILE_NAME").toString() + "PDFWoCalc.jasper", params, dataSource, jPanel1);
                                    }
                                } else {
                                    if (type.equalsIgnoreCase("0")) {
                                        lb.reportGenerator(Constants.params.get("PUR_RETURN_FILE_NAME").toString() + ".jasper", params, dataSource, jPanel1);
                                    } else {
                                        lb.reportGenerator(Constants.params.get("PUR_RETURN_FILE_NAME").toString() + "WoCalc.jasper", params, dataSource, jPanel1);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                        }
                    }
                } else {
                    lb.showMessageDailog(call.get("Cause").getAsString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PrintPanel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateDummyPrint(String data, String to, String mobile, String imei, String rs, String days, String item) {
        HashMap params = new HashMap();
        params.put("dir", System.getProperty("user.dir"));
        params.put("comp_name", Constants.COMPANY_NAME);
        params.put("add1", SkableHome.selected_branch.getAddress1());
        params.put("add2", SkableHome.selected_branch.getAddress2());
        params.put("city", SkableHome.selected_branch.getAddress3());
        params.put("pin", "");
        params.put("data", data);
        params.put("for", to);
        params.put("mobile", mobile);
        params.put("imei", imei);
        params.put("cash", rs);
        params.put("days", days);
        params.put("item", item);

        lb.reportGenerator("DummyPrint.jasper", params, null, jPanel1);
    }

    public void printDCVoucher(final String ref_no) {
        DCAPI dcAPI = lb.getRetrofit().create(DCAPI.class);
        if (!ref_no.equalsIgnoreCase(
                "")) {
            try {
                JsonObject call = dcAPI.getBill(ref_no, SkableHome.db_name, SkableHome.selected_year).execute().body();

                if (call != null) {
                    System.out.println(call.toString());
                    JsonObject object = call;
                    JsonArray array = object.get("data").getAsJsonArray();
                    if (array != null) {
                        try {
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "file1.txt");
                            file.write(array.toString());
                            file.close();
                            File jsonFile = new File(System.getProperty("user.dir") + File.separator + "file1.txt");
                            JsonDataSource dataSource = new JsonDataSource(jsonFile);
                            HashMap params = new HashMap();
                            params.put("dir", System.getProperty("user.dir"));
                            lb.confirmDialog("Do you want to print with Header?");
                            if (lb.type) {
                                lb.reportGenerator("DCPrintPDF.jasper", params, dataSource, jPanel1);
                            } else {
                                lb.reportGenerator("DCPrint.jasper", params, dataSource, jPanel1);
                            }
                        } catch (Exception ex) {
                        }
                    }
                }
            } catch (Exception ex) {
                lb.printToLogFile("Exception at getDataFor PurchaseBill", ex);
            }
        } else {
        }

    }

    public void getCashreceiptPrint(String ref_no, final int type) {
        try {
            CashPRAPI cashPRAPI = lb.getRetrofit().create(CashPRAPI.class);
            JsonObject call;
            if (type
                    == 0) {
                call = cashPRAPI.getCashDetail(ref_no, "9", SkableHome.db_name, SkableHome.selected_year).execute().body();
            } else {
                call = cashPRAPI.getCashDetail(ref_no, "28", SkableHome.db_name, SkableHome.selected_year).execute().body();
            }
            if (call
                    != null) {
                JsonObject result = call;
                if (result.get("result").getAsInt() == 1) {
                    JsonArray array = call.getAsJsonArray("data");
                    if (array != null) {
                        try {
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "file1.txt");
                            file.write(array.toString());
                            file.close();
                            File jsonFile = new File(System.getProperty("user.dir") + File.separator + "file1.txt");
                            JsonDataSource dataSource = new JsonDataSource(jsonFile);
                            HashMap params = new HashMap();
                            params.put("dir", System.getProperty("user.dir"));
                            params.put("comp_name", Constants.COMPANY_NAME);
                            params.put("tin_no", (array.get(0).getAsJsonObject().get("COMPANY_TIN").getAsString()));
                            params.put("cst_no", (array.get(0).getAsJsonObject().get("COMPANY_CST").getAsString()));
                            params.put("add1", SkableHome.selected_branch.getAddress1());
                            params.put("add2", SkableHome.selected_branch.getAddress2());
                            params.put("add3", SkableHome.selected_branch.getAddress3());
                            params.put("email", SkableHome.selected_branch.getEmail());
                            params.put("mobile", SkableHome.selected_branch.getPhone());
                            lb.reportGenerator("cashReport.jasper", params, dataSource, jPanel1);
                        } catch (Exception ex) {
                        }
                    }
                } else {
                    lb.showMessageDailog(call.get("Cause").getAsString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PrintPanel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getDnCnPrint(String ref_no, final int type) {
        try {
            DNCNApi cashPRAPI = lb.getRetrofit().create(DNCNApi.class);
            JsonObject call;
            if (type
                    == 0) {
                call = cashPRAPI.getBankDetail(ref_no, "30", SkableHome.db_name, SkableHome.selected_year).execute().body();
            } else {
                call = cashPRAPI.getBankDetail(ref_no, "31", SkableHome.db_name, SkableHome.selected_year).execute().body();
            }
            if (call
                    != null) {
                JsonObject result = call;
                if (result.get("result").getAsInt() == 1) {
                    JsonArray array = call.getAsJsonArray("data");
                    if (array != null) {
                        try {
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "file1.txt");
                            file.write(array.toString());
                            file.close();
                            File jsonFile = new File(System.getProperty("user.dir") + File.separator + "file1.txt");
                            JsonDataSource dataSource = new JsonDataSource(jsonFile);
                            HashMap params = new HashMap();
                            params.put("dir", System.getProperty("user.dir"));
                            params.put("comp_name", Constants.COMPANY_NAME);
                            if (type == 0) {
                                params.put("title", "Debit Note");
                            } else {
                                params.put("title", "Credit Note");
                            }
                            params.put("tin_no", (array.get(0).getAsJsonObject().get("COMPANY_TIN").getAsString()));
                            params.put("cst_no", (array.get(0).getAsJsonObject().get("COMPANY_CST").getAsString()));
                            params.put("add1", SkableHome.selected_branch.getAddress1());
                            params.put("add2", SkableHome.selected_branch.getAddress2());
                            params.put("add3", SkableHome.selected_branch.getAddress3());
                            params.put("email", SkableHome.selected_branch.getEmail());
                            params.put("mobile", SkableHome.selected_branch.getPhone());
                            lb.reportGeneratorPDF("DebitNoteReport.jasper", params, dataSource, ref_no);
                        } catch (Exception ex) {
                        }
                    }
                } else {
                    lb.showMessageDailog(call.get("Cause").getAsString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PrintPanel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getBankreceiptPrint(String ref_no, final int type) {
        try {
            BankAPI bankAPI = lb.getRetrofit().create(BankAPI.class);
            JsonObject call;
            if (type
                    == 0) {
                call = bankAPI.getBankDetail(ref_no, "10", SkableHome.db_name, SkableHome.selected_year).execute().body();
            } else {
                call = bankAPI.getBankDetail(ref_no, "29", SkableHome.db_name, SkableHome.selected_year).execute().body();
            }
            if (call
                    != null) {
                JsonObject result = call;
                if (result.get("result").getAsInt() == 1) {
                    JsonArray array = call.getAsJsonArray("data");
                    if (array != null) {
                        try {
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "file1.txt");
                            file.write(array.toString());
                            file.close();
                            File jsonFile = new File(System.getProperty("user.dir") + File.separator + "file1.txt");
                            JsonDataSource dataSource = new JsonDataSource(jsonFile);
                            HashMap params = new HashMap();
                            params.put("dir", System.getProperty("user.dir"));
                            params.put("comp_name", Constants.COMPANY_NAME);
                            params.put("tin_no", (array.get(0).getAsJsonObject().get("COMPANY_TIN").getAsString()));
                            params.put("cst_no", (array.get(0).getAsJsonObject().get("COMPANY_CST").getAsString()));
                            params.put("add1", SkableHome.selected_branch.getAddress1());
                            params.put("add2", SkableHome.selected_branch.getAddress2());
                            params.put("add3", SkableHome.selected_branch.getAddress3());
                            params.put("email", SkableHome.selected_branch.getEmail());
                            params.put("mobile", SkableHome.selected_branch.getPhone());
                            lb.reportGenerator("bankReport.jasper", params, dataSource, jPanel1);
                        } catch (Exception ex) {
                        }
                    }
                } else {
                    lb.showMessageDailog(call.get("Cause").getAsString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PrintPanel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void printDCVoucherWithoutAmt(final String ref_no) {
        DCAPI dcAPI = lb.getRetrofit().create(DCAPI.class);
        if (!ref_no.equalsIgnoreCase(
                "")) {
            try {
                JsonObject call = dcAPI.getBill(ref_no, SkableHome.db_name, SkableHome.selected_year).execute().body();

                if (call != null) {
                    System.out.println(call.toString());
                    JsonObject object = call;

                    JsonArray array = object.get("data").getAsJsonArray();
                    if (array != null) {
                        try {
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "file1.txt");
                            file.write(array.toString());
                            file.close();
                            File jsonFile = new File(System.getProperty("user.dir") + File.separator + "file1.txt");
                            JsonDataSource dataSource = new JsonDataSource(jsonFile);
                            HashMap params = new HashMap();
                            params.put("dir", System.getProperty("user.dir"));
                            lb.reportGenerator("DCPrintWoAmt.jasper", params, dataSource, jPanel1);
                        } catch (Exception ex) {
                        }
                    }

                }
            } catch (Exception ex) {
                lb.printToLogFile("Exception at getDataFor PurchaseBill", ex);
            }
        } else {
        }

    }

    public void generateStocktransfer(String ref_no) {
        try {
            StkTrOutAPI salesAPI = lb.getRetrofit().create(StkTrOutAPI.class);
            JsonObject call = salesAPI.GetStockTransferPrint(ref_no, SkableHome.db_name, SkableHome.selected_year).execute().body();

            if (call != null) {
                JsonObject result = call;
                if (result.get("result").getAsInt() == 1) {
                    JsonArray array = call.getAsJsonArray("data");
                    if (array != null) {
                        try {
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "file1.txt");
                            file.write(array.toString());
                            file.close();
                            File jsonFile = new File(System.getProperty("user.dir") + File.separator + "file1.txt");
                            JsonDataSource dataSource = new JsonDataSource(jsonFile);
                            HashMap params = new HashMap();
                            params.put("dir", System.getProperty("user.dir"));
                            params.put("comp_name", Constants.COMPANY_NAME);
                            params.put("add1", SkableHome.selected_branch.getAddress1());
                            params.put("add2", SkableHome.selected_branch.getAddress2());
                            params.put("add3", SkableHome.selected_branch.getAddress3());
                            params.put("email", SkableHome.selected_branch.getEmail());
                            params.put("mobile", SkableHome.selected_branch.getPhone());
                            params.put("from_loc", SkableHome.selected_branch.getBranch_name());
                            params.put("to_loc", Constants.branchMap.get(array.get(0).getAsJsonObject().get("to_loc").getAsString()).getBranch_name());
                            lb.reportGenerator("stockTransferOUT.jasper", params, dataSource, jPanel1);
                        } catch (Exception ex) {
                        }
                    }
                } else {
                    lb.showMessageDailog(call.get("Cause").getAsString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PrintPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateStocktransferPrint(String ref_no, boolean flag) {
        try {
            StkTrAPI salesAPI = lb.getRetrofit().create(StkTrAPI.class);
            JsonObject call = salesAPI.getBill(ref_no, SkableHome.db_name, SkableHome.selected_year).execute().body();

            if (call != null) {
                JsonObject result = call;
                if (result.get("result").getAsInt() == 1) {
                    JsonArray array = call.getAsJsonArray("data");
                    if (array != null) {
                        try {
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "file1.txt");
                            file.write(array.toString());
                            file.close();
                            File jsonFile = new File(System.getProperty("user.dir") + File.separator + "file1.txt");
                            JsonDataSource dataSource = new JsonDataSource(jsonFile);
                            HashMap params = new HashMap();
                            params.put("dir", System.getProperty("user.dir"));
                            params.put("comp_name", Constants.COMPANY_NAME);
                            params.put("add1", SkableHome.selected_branch.getAddress1());
                            params.put("add2", SkableHome.selected_branch.getAddress2());
                            params.put("add3", SkableHome.selected_branch.getAddress3());
                            params.put("email", SkableHome.selected_branch.getEmail());
                            params.put("mobile", SkableHome.selected_branch.getPhone());
                            params.put("from_loc", "Godown");
                            params.put("to_loc", SkableHome.selected_branch.getBranch_name());
                            if (flag) {
                                lb.reportPrinter("stockTransfer.jasper", params, dataSource);
                            } else {
                                lb.reportGenerator("stockTransfer.jasper", params, dataSource, jPanel1);
                            }
                        } catch (Exception ex) {
                        }
                    }
                } else {
                    lb.showMessageDailog(call.get("Cause").getAsString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PrintPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void printOrderVoucher(final String ref_no) {
        OrderBookAPI orderBookAPI = lb.getRetrofit().create(OrderBookAPI.class);
        if (!ref_no.equalsIgnoreCase("")) {
            try {
                JsonObject call = orderBookAPI.getOrderBookDetail(ref_no, SkableHome.db_name, SkableHome.selected_year).execute().body();

                if (call != null) {
                    System.out.println(call.toString());
                    JsonObject object = call;
                    JsonArray array = object.get("data").getAsJsonArray();
                    if (array != null) {
                        try {
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "file1.txt");
                            file.write(array.toString());
                            file.close();
                            File jsonFile = new File(System.getProperty("user.dir") + File.separator + "file1.txt");
                            JsonDataSource dataSource = new JsonDataSource(jsonFile);
                            HashMap params = new HashMap();
                            params.put("dir", System.getProperty("user.dir"));
                            params.put("comp_name", Constants.COMPANY_NAME);
                            params.put("tin_no", "GST No : " + (array.get(0).getAsJsonObject().get("COMPANY_GST_NO").getAsString()));
                            params.put("add1", SkableHome.selected_branch.getAddress1());
                            params.put("add2", SkableHome.selected_branch.getAddress2());
                            params.put("add3", SkableHome.selected_branch.getAddress3());
                            params.put("email", SkableHome.selected_branch.getEmail());
                            params.put("mobile", SkableHome.selected_branch.getPhone());
                            lb.confirmDialog("Do you want to print Bill with header?");
                            if (lb.type) {
                                lb.reportGenerator("OrderBook.jasper", params, dataSource, jPanel1);
                            } else {
                                lb.reportGenerator("OrderBook.jasper", params, dataSource, jPanel1);
                            }
                        } catch (Exception ex) {
                        }
                    }
                }
            } catch (Exception ex) {
                lb.printToLogFile("Exception at getDataFor PurchaseBill", ex);
            }
        } else {
        }

    }

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
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

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    private int returnStatus = RET_CANCEL;
}
