/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package assginmentjava3gd;

import Model.Class2;
import java.awt.BorderLayout;
import java.util.List; // phai sai util
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import assginmentjava3gd.student2;
import DAO.ClassDAO2;

/**
 *
 * @author ACER
 */
public class class2 extends javax.swing.JInternalFrame {
    private final List<Class2> lop = new ArrayList<>();
    private DefaultTableModel table;
    private final student2 st;

    public class2() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        st = new student2();
        fillTable();
    }
    
//    public void fillTable() {
//        DefaultTableModel model = (DefaultTableModel) btnTablelop.getModel();
//        model.setRowCount(0);      
//          st.getCboLop().removeAllItems();
//        for (Class2 dp : lop) {
//            Object[] row = new Object[]{dp.malop, dp.tenlop, dp.mota};
//            model.addRow(row);         
//            st.getCboLop().addItem(dp.tenlop);
//        }
//    }
    
     public void addClass() {
    if (txtMa.getText().equals("") && txtTen.getText().equals("")) {
        JOptionPane.showMessageDialog(this, "Chưa nhập tên và mã lớp", "Error", JOptionPane.WARNING_MESSAGE);
                JOptionPane.showMessageDialog(this, "Thêm thất bại", "Error", JOptionPane.WARNING_MESSAGE);

         return;
    } 
    if (txtTen.getText().equals("")) {
    JOptionPane.showMessageDialog(this, "Chưa nhập tên", "Error", JOptionPane.WARNING_MESSAGE);
                JOptionPane.showMessageDialog(this, "Thêm thất bại", "Error", JOptionPane.WARNING_MESSAGE);

    return;
    } 
    if (txtMa.getText().equals("")) {
    JOptionPane.showMessageDialog(this, "Chưa nhập mã lớp", "Error", JOptionPane.WARNING_MESSAGE);
                JOptionPane.showMessageDialog(this, "Thêm thất bại", "Error", JOptionPane.WARNING_MESSAGE);

    return;
    }
        Class2 dp = new Class2();
            dp.malop =  txtMa.getText();
            dp.tenlop = txtTen.getText();
            dp.mota = txtMota.getText();
            lop.add(dp);
            st.getCboLop().addItem(dp.tenlop);
            st.getCboLop().revalidate();
            st.getCboLop().repaint();
            ClassDAO2.insertDe(dp);
            fillTable();
            JOptionPane.showMessageDialog(this, "Thêm thành công");
     }
     
    public void updateClass() {
        int index = btnTablelop.getSelectedRow();
        if (index == -1 || index >= lop.size()) {
            JOptionPane.showMessageDialog(this, "Chưa chọn hàng nào để cập nhật!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }    
        if (txtMa.getText().equals("") && txtTen.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên và mã lớp", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        } 

        if (txtTen.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        } 

        if (txtMa.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập mã lớp", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Class2 dp = lop.get(index);
        dp.malop = txtMa.getText();
        dp.tenlop = txtTen.getText();
        dp.mota = txtMota.getText();
        st.getCboLop().removeItemAt(index);
        st.getCboLop().insertItemAt(dp.tenlop, index);
        st.getCboLop().revalidate();
        st.getCboLop().repaint();
        ClassDAO2.updateDe(dp);
        fillTable();
        JOptionPane.showMessageDialog(this, "Cập nhật thành công");
}
    public void removeclass() {
        int index = btnTablelop.getSelectedRow(); // Lấy dòng được chọn từ bảng

        if (index != -1 && index < lop.size()) { // Kiểm tra dòng hợp lệ
            int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa lớp học này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                // Lấy lớp học cần xóa từ danh sách
                Class2 dp = lop.get(index);

                // Xóa lớp học khỏi cơ sở dữ liệu
                ClassDAO2.deleteDe(dp.getMalop());

                // Xóa lớp học khỏi danh sách `lop` và combobox
                lop.remove(index);
                st.getCboLop().removeItemAt(index);

                // Cập nhật lại bảng và giao diện
                fillTable();

                JOptionPane.showMessageDialog(this, "Xóa thành công!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chưa chọn hàng nào để xóa hoặc dữ liệu không hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
}
    
    
    // đây là code để đẩy cái dữ liệu ở database lên table 
    public void fillTable() {
    // Lấy dữ liệu từ cơ sở dữ liệu
        List<Class2> classes = ClassDAO2.getAllClasses();
        lop.clear(); // Xóa danh sách cũ
        lop.addAll(classes); // Cập nhật danh sách mới

        // Cập nhật JTable
        DefaultTableModel model = (DefaultTableModel) btnTablelop.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        for (Class2 dp : lop) {
            Object[] row = new Object[]{dp.malop, dp.tenlop, dp.mota};
            model.addRow(row); // Thêm dữ liệu vào bảng
        }

        // Cập nhật combobox (nếu cần)
        st.getCboLop().removeAllItems();
        for (Class2 dp : lop) {
            st.getCboLop().addItem(dp.tenlop);
        }
}
    
    
    
    // cái này là để click vào table để nó hiện lên mấy cái textfield á nha 
    public void clickHere(){
        int row = btnTablelop.getSelectedRow();  // Lấy chỉ số dòng được chọn
    
    // Kiểm tra xem có dòng nào được chọn không
        if (row != -1) {
        // Lấy dữ liệu từ bảng và điền vào các trường nhập liệu
        String maLop = btnTablelop.getValueAt(row, 0).toString();  // Lấy mã lớp từ cột 1
        String tenLop = btnTablelop.getValueAt(row, 1).toString();  // Lấy tên lớp từ cột 2
        String moTa = btnTablelop.getValueAt(row, 2).toString();    // Lấy mô tả từ cột 3
        
        // Cập nhật các trường nhập liệu
        txtMa.setText(maLop);
        txtTen.setText(tenLop);
        txtMota.setText(moTa);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        btnTablelop = new javax.swing.JTable();
        btnthem = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();
        btncapnhat = new javax.swing.JButton();
        btnreset = new javax.swing.JButton();
        txtTen = new javax.swing.JTextField();
        txtMa = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMota = new javax.swing.JTextArea();

        btnTablelop.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã lớp", "Tên lớp", "Mô tả"
            }
        ));
        btnTablelop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTablelopMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(btnTablelop);

        btnthem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnthem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/299068_add_sign_icon.png"))); // NOI18N
        btnthem.setText("Thêm");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        btnxoa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnxoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/9004852_trash_delete_bin_remove_icon.png"))); // NOI18N
        btnxoa.setText("Xóa");
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        btncapnhat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btncapnhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/8726496_upload_icon.png"))); // NOI18N
        btncapnhat.setText("Cập nhật");
        btncapnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncapnhatActionPerformed(evt);
            }
        });

        btnreset.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnreset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/9856287_reset_reload_sync_update_icon.png"))); // NOI18N
        btnreset.setText("Reset");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Mã lớp :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Tên lớp :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Mô tả :");

        txtMota.setColumns(20);
        txtMota.setRows(5);
        jScrollPane2.setViewportView(txtMota);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btncapnhat, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                            .addComponent(btnreset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(118, 118, 118)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnxoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnthem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(65, 65, 65))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(btnthem)
                        .addGap(27, 27, 27)
                        .addComponent(btnxoa)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(btncapnhat)
                        .addGap(32, 32, 32)
                        .addComponent(btnreset)
                        .addGap(156, 156, 156))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        addClass();
    }//GEN-LAST:event_btnthemActionPerformed

    private void btncapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncapnhatActionPerformed
        updateClass();
    }//GEN-LAST:event_btncapnhatActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        removeclass();
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnTablelopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTablelopMouseClicked
          clickHere();
    }//GEN-LAST:event_btnTablelopMouseClicked

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
        txtTen.setText("");
        txtMa.setText("");
        txtMota.setText("");
    }//GEN-LAST:event_btnresetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable btnTablelop;
    private javax.swing.JButton btncapnhat;
    private javax.swing.JButton btnreset;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextArea txtMota;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
