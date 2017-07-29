/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StockLedger.java
 *
 * Created on Oct 16, 2012, 12:58:30 PM
 */
package inventory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.TagHead;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofitAPI.InventoryAPI;
import retrofitAPI.StartUpAPI;
import skable.Constants;
import skable.SkableHome;
import support.Library;
import support.ReportTable;
import support.SelectDailog;

/**
 *
 * @author nice
 */
public class TagTrack extends javax.swing.JInternalFrame {

    Library lb = Library.getInstance();
    private DefaultTableModel dtm = null;
    private ReportTable viewTable = null;

    String Syspath = System.getProperty("user.dir");

    /**
     * Creates new form StockLedger
     */
    public TagTrack() {
        initComponents();
        initOther();
    }

    public TagTrack(String tag_name) {
        initComponents();
        initOther();
        jtxtTagNo.setText(tag_name);
        jbtnView.doClick();
    }

    private void initOther() {
        dtm = (DefaultTableModel) jTable1.getModel();
        tableForView();
        registerShortKeys();
        setPopUp();
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

    @Override
    public void dispose() {
        try {
            SkableHome.removeFromScreen(SkableHome.tabbedPane.getSelectedIndex());
            super.dispose();
        } catch (Exception ex) {
            lb.printToLogFile("Exception at dispose at codeBinding", ex);
        }
    }

    public class StatusColumnCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

            //Cells are by default rendered as a JLabel.
            JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            l.setHorizontalAlignment(SwingConstants.RIGHT);
            Double d = null;
            try {
                d = Double.parseDouble(lb.getDeCustomFormat((l.getText().equalsIgnoreCase("") ? "0.00" : l.getText())));
            } catch (Exception ex) {
                d = 0.00;
                lb.printToLogFile("Error at getTableCell in stock ledger", ex);
            }
            l.setText(lb.Convert2DecFmt(d));
            return l;

        }
    }

    private void registerShortKeys() {
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                jbtnClose.doClick();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);
    }

    private void tableForView() {
        viewTable = new ReportTable();
        viewTable.AddColumn(0, "Tag Code", 120, java.lang.String.class, null, false);
        viewTable.makeTable();
    }

    private void setSeriesData(String param_cd, String value) {
        try {
            JsonObject call = lb.getRetrofit().create(StartUpAPI.class).getDataFromServer(param_cd, value.toUpperCase(),SkableHome.db_name,SkableHome.selected_year).execute().body();
            if (call != null) {
                System.out.println(call.toString());
                TagHead header = (TagHead) new Gson().fromJson(call, TagHead.class);
                if (header.getResult() == 1) {
                    final SelectDailog sa = new SelectDailog(null, true);
                    sa.setLocationRelativeTo(null);
                    sa.setData(viewTable);
                    sa.getDtmHeader().setRowCount(0);
                    for (int i = 0; i < header.getTags().size(); i++) {
                        Vector row = new Vector();
                        row.add(header.getTags().get(i));
                        sa.getDtmHeader().addRow(row);
                    }
                    lb.setColumnSizeForTable(viewTable, sa.jPanelHeader.getWidth());
                    sa.setVisible(true);
                    if (sa.getReturnStatus() == SelectDailog.RET_OK) {
                        int row = viewTable.getSelectedRow();
                        if (row != -1) {
                            final String tag = viewTable.getValueAt(row, 0).toString();
                            jtxtTagNo.setText(tag);
                            jbtnView.requestFocusInWindow();
                        }
                        sa.dispose();
                    }
                } else {
                    lb.showMessageDailog(header.getCause());
                }
            }
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jtxtTagNo = new javax.swing.JTextField();
        jbtnView = new javax.swing.JButton();
        jbtnPreview = new javax.swing.JButton();
        jbtnClose = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Stock Ledger");

        jPanel1.setLayout(new java.awt.CardLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Voucher No", "Book", "Party Name", "Issue", "Reciept", "Balance", "TAG", "ref_no", "Branch Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(7).setResizable(false);
            jTable1.getColumnModel().getColumn(8).setMinWidth(0);
            jTable1.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(9).setResizable(false);
        }

        jPanel1.add(jScrollPane1, "card2");

        jLabel2.setText("Tag Number");

        jtxtTagNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtTagNoKeyPressed(evt);
            }
        });

        jbtnView.setText("View Result");
        jbtnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnViewActionPerformed(evt);
            }
        });
        jbtnView.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbtnViewKeyPressed(evt);
            }
        });

        jbtnPreview.setText("Preview");
        jbtnPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnPreviewActionPerformed(evt);
            }
        });
        jbtnPreview.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbtnPreviewKeyPressed(evt);
            }
        });

        jbtnClose.setText("Close");
        jbtnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCloseActionPerformed(evt);
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
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtTagNo, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 369, Short.MAX_VALUE)
                        .addComponent(jbtnView, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbtnClose, jbtnPreview, jbtnView});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbtnView)
                        .addComponent(jbtnPreview)
                        .addComponent(jbtnClose))
                    .addComponent(jtxtTagNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jtxtTagNo});

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jbtnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnViewActionPerformed
    InventoryAPI inventoryAPI = lb.getRetrofit().create(InventoryAPI.class
    );
    Call<JsonObject> call = inventoryAPI.GetTagTrack(jtxtTagNo.getText().trim(),SkableHome.db_name,SkableHome.selected_year);

    lb.addGlassPane(this);
    call.enqueue(new Callback<JsonObject>() {

        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> rspns) {
            lb.removeGlassPane(TagTrack.this);
            if (rspns.isSuccessful()) {
                JsonObject result = rspns.body();
                if (result.get("result").getAsInt() == 1) {
                    JsonArray array = rspns.body().getAsJsonArray("data");
                    dtm.setRowCount(0);
                    double tot = 0;
                    for (int i = 0; i < array.size(); i++) {
                        if (i == 0) {
                            tot = array.get(i).getAsJsonObject().get("opb").getAsDouble();
                        }
                        Vector row = new Vector();
                        row.add(lb.ConvertDateFormetForDisplay(array.get(i).getAsJsonObject().get("doc_date").getAsString()));
                        if (array.get(i).getAsJsonObject().get("inv_no").isJsonNull()) {
                            row.add(array.get(i).getAsJsonObject().get("doc_ref_no").getAsString());
                        } else {
                            row.add(array.get(i).getAsJsonObject().get("inv_no").getAsString());
                        }
                        row.add(array.get(i).getAsJsonObject().get("doc_cd").getAsString());
                        row.add(array.get(i).getAsJsonObject().get("ac_name").getAsString());
                        row.add(array.get(i).getAsJsonObject().get("issue").getAsDouble());
                        row.add(array.get(i).getAsJsonObject().get("receipt").getAsDouble());
                        tot += array.get(i).getAsJsonObject().get("receipt").getAsDouble();
                        tot -= array.get(i).getAsJsonObject().get("issue").getAsDouble();
                        row.add(tot);
                        row.add(array.get(i).getAsJsonObject().get("SR_NAME").getAsString());
                        row.add(array.get(i).getAsJsonObject().get("doc_ref_no").getAsString());
                        if (!array.get(i).getAsJsonObject().get("branch_cd").isJsonNull()) {
                            row.add(Constants.BRANCH.get(array.get(i).getAsJsonObject().get("branch_cd").getAsInt() - 1).getBranch_name());
                        } else {
                            row.add("");
                        }
                        dtm.addRow(row);
                    }
                    lb.setColumnSizeForTable(jTable1, jPanel1.getWidth());
                    lb.removeGlassPane(TagTrack.this);
                } else {
                    lb.showMessageDailog(rspns.body().get("Cause").getAsString());
                    lb.removeGlassPane(TagTrack.this);
                }
            } else {
                lb.showMessageDailog(rspns.message());
            }
        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
            lb.removeGlassPane(TagTrack.this);

        }
    }
    );

}//GEN-LAST:event_jbtnViewActionPerformed

private void jbtnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCloseActionPerformed
// TODO add your handling code here:
    this.dispose();
}//GEN-LAST:event_jbtnCloseActionPerformed

private void jbtnViewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbtnViewKeyPressed
// TODO add your handling code here:
    lb.enterClick(evt);

}//GEN-LAST:event_jbtnViewKeyPressed

private void jbtnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPreviewActionPerformed
// TODO add your handling code here:
    javax.swing.SwingUtilities.invokeLater(new Runnable() {

        public void run() {
        }
    });
}//GEN-LAST:event_jbtnPreviewActionPerformed

private void jbtnPreviewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbtnPreviewKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
            }
        });
    }
}//GEN-LAST:event_jbtnPreviewKeyPressed

    private void jtxtTagNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtTagNoKeyPressed
        // TODO add your handling code here:
        if (lb.isEnter(evt)) {
            jtxtTagNo.setText(lb.checkTag(jtxtTagNo.getText()));
            setSeriesData("35", jtxtTagNo.getText());
        }
    }//GEN-LAST:event_jtxtTagNoKeyPressed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = jTable1.getSelectedRow();
            if (row != -1) {
                lb.openVoucherBook(jTable1.getValueAt(row, 8).toString());
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton jbtnClose;
    private javax.swing.JButton jbtnPreview;
    private javax.swing.JButton jbtnView;
    private javax.swing.JTextField jtxtTagNo;
    // End of variables declaration//GEN-END:variables
}
