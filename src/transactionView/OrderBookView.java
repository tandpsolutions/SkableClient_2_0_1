/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionView;

import account.GeneralLedger1;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.awt.BorderLayout;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import retrofitAPI.OrderBookAPI;
import skable.SkableHome;
import support.Library;
import support.SmallNavigation;
import transactionController.OrderBookController;
import utility.PrintPanel;

/**
 *
 * @author bhaumik
 */
public class OrderBookView extends javax.swing.JInternalFrame {

    /**
     * Creates new form PurchaseView
     */
    private Library lb = Library.getInstance();
    public SmallNavigation navLoad = null;
    public TransactionFilterView filter = null;
    public String ref_no = "";
    private DefaultTableModel dtm = null;
    private TableRowSorter<TableModel> rowSorter;
    private JTextField jtfFilter = new JTextField();
    private OrderBookAPI orderBookAPI;

    public OrderBookView(int formCd) {
        initComponents();
        orderBookAPI = lb.getRetrofit().create(OrderBookAPI.class);
        dtm = (DefaultTableModel) jTable1.getModel();
        searchOnTextFields();
        connectNavigation();
        addViewFilter();
        navLoad.setFormCd(formCd);
        setPopUp();
        filter.viewClick();
    }

    private void setPopUp() {
        final JPopupMenu popup = new JPopupMenu();
        ActionListener menuListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                popup.setVisible(false);
                int row = jTable1.getSelectedRow();
                int column = jTable1.getSelectedColumn();
                if (row != -1 && column != -1) {
                    String ac_cd = jTable1.getValueAt(row, 5).toString();
                    String ac_name = jTable1.getValueAt(row, 2).toString();
                    GeneralLedger1 gl = new GeneralLedger1(ac_cd, ac_name);
                    SkableHome.addOnScreen(gl, "General Ledger");
                }
            }
        };
        final JMenuItem item;
        popup.add(item = new JMenuItem("Open Ledger"));
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(menuListener);
        popup.setLocation(MouseInfo.getPointerInfo().getLocation());
        jTable1.setComponentPopupMenu(popup);
    }

    private void setData(String from_date, String to_date, String branch_cd) {
        try {
            lb.addGlassPane(this);
            JsonObject call = orderBookAPI.GetOrderBookHeader(from_date, to_date, branch_cd, "", "", "", SkableHome.db_name, SkableHome.selected_year).execute().body();
            lb.removeGlassPane(this);
            if (call != null) {
                if (call.get("result").getAsInt() == 1) {
                    JsonArray array = call.get("data").getAsJsonArray();
                    dtm.setRowCount(0);
                    for (int i = 0; i < array.size(); i++) {
                        Vector row = new Vector();
                        row.add(array.get(i).getAsJsonObject().get("REF_NO").getAsString());
                        row.add(lb.ConvertDateFormetForDisplay(array.get(i).getAsJsonObject().get("VDATE").getAsString()));
                        row.add(array.get(i).getAsJsonObject().get("FNAME").getAsString());
                        row.add(array.get(i).getAsJsonObject().get("BAL").getAsString());
                        row.add(array.get(i).getAsJsonObject().get("REMARK").getAsString());
                        row.add(array.get(i).getAsJsonObject().get("AC_CD").getAsString());
                        row.add(array.get(i).getAsJsonObject().get("MODEL_NAME").getAsString());
                        dtm.addRow(row);
                    }
                } else {
//                        lb.showMessageDailog(response.body().getd("Cause").getAsString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(OrderBookView.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private void addOrderController() {
        OrderBookController pc = new OrderBookController(null, true, this);
        pc.setLocationRelativeTo(null);
        pc.setData(orderBookAPI, ref_no);
    }

    private void addViewFilter() {
        class Filter extends TransactionFilterView {

            @Override
            public void onViewClick(String from_date, String to_date, String branch_cd) {
                setData(from_date, to_date, branch_cd);
            }
        }

        filter = new Filter();
        jPanel1.add(filter);
        filter.setVisible(true);
    }

    private void connectNavigation() {
        class navigation extends SmallNavigation {

            @Override
            public void callNew() {
                if (navLoad.getModel().getADDS().equalsIgnoreCase("1")) {
                    ref_no = "";
                    jTable1.clearSelection();
                    addOrderController();
                } else {
                    lb.showMessageDailog("You don't have rights to perform this action");
                }
            }

            @Override
            public void callEdit() {
                if (navLoad.getModel().getEDITS().equalsIgnoreCase("1")) {
                    int row = jTable1.getSelectedRow();
                    if (row != -1) {
                        ref_no = jTable1.getValueAt(row, 0).toString();
                        addOrderController();
                    }
                } else {
                    lb.showMessageDailog("You don't have rights to perform this action");
                }
            }

            @Override
            public void callDelete() {
                if (navLoad.getModel().getEDITS().equalsIgnoreCase("1")) {
                    final int row = jTable1.getSelectedRow();
                    if (row != -1) {
                        lb.confirmDialog("Do you want to delete this voucher?");
                        if (lb.type) {
                            try {
                                String ref_no = jTable1.getValueAt(row, 0).toString();
                                lb.addGlassPane(OrderBookView.this);
                                JsonObject object = orderBookAPI.DeleteOrderBookEntry(ref_no, SkableHome.db_name, SkableHome.selected_year).execute().body();

                                lb.removeGlassPane(OrderBookView.this);

                                if (object != null) {
                                    if (object.get("result").getAsInt() == 1) {
                                        lb.showMessageDailog("Delete successfull");
                                        dtm.removeRow(row);
                                    } else {
                                        lb.showMessageDailog(object.get("Cause").getAsString());
                                    }
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(OrderBookView.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                } else {
                    lb.showMessageDailog("You don't have rights to perform this action");
                }
            }

            @Override
            public void callClose() {
                close();
            }

            @Override
            public void callPrint() {
                int row = jTable1.getSelectedRow();
                if(row!= -1){
                    callPrint(jTable1.getValueAt(row, 0).toString());
                }
            }
        }
        navLoad = new navigation();

        jpanelNav.add(navLoad);

        navLoad.setVisible(
                true);
    }
    
    public void callPrint(String ref_no){
        PrintPanel pp = new PrintPanel(null, true);
        pp.printOrderVoucher(ref_no);
        pp.setVisible(true);
    }

    private void close() {
        this.dispose();
    }

    @Override
    public void dispose() {
        try {
            SkableHome.removeFromScreen(SkableHome.tabbedPane.getSelectedIndex());
            super.dispose();
        } catch (Exception ex) {
//            lb.printToLogFile("Exception at dispose at codeBinding", ex);
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
        jpanelNav = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        panel = new javax.swing.JPanel();

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.BorderLayout());

        jpanelNav.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpanelNav.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Voucher", "Date", "Name", "Balance", "Remark", "AC Cd", "Model Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
        jTable1.getColumnModel().getColumn(1).setResizable(false);
        jTable1.getColumnModel().getColumn(2).setResizable(false);
        jTable1.getColumnModel().getColumn(3).setResizable(false);
        jTable1.getColumnModel().getColumn(4).setResizable(false);
        jTable1.getColumnModel().getColumn(5).setMinWidth(0);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(5).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(6).setResizable(false);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpanelNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1027, Short.MAX_VALUE)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(jpanelNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            navLoad.callEdit();
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        if (lb.isEnter(evt)) {
            navLoad.callEdit();
        }
    }//GEN-LAST:event_jTable1KeyPressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel jpanelNav;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
