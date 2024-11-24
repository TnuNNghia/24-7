/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package assginmentjava3gd;

import DAO.StudentDAO2;
import Model.Student2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
;
/**
 *
 * @author ACER
 */
public class student2 extends javax.swing.JInternalFrame {
    private final List<Student2> sinhvien = new ArrayList<>();
    /**
     * Creates new form student2
     */
    public student2() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        loadClassNames();
        loadSubjectID();
        fillToTable();
    }
    // Phương thức kết nối cơ sở dữ liệu
    private Connection connect() throws Exception {
        String url = "jdbc:mysql://localhost:3306/assjava3"; // Thay 'ten_database' bằng tên database
        String user = "root"; // Thay username
        String password = "18102007"; // Thay password
        return DriverManager.getConnection(url, user, password);
    }
    public JComboBox<String> getCboLop() {
        return cboLop;
    }
    // Phương thức để load dữ liệu từ cơ sở dữ liệu lên ComboBox
    private void loadClassNames() {
        String query = getSelectClassQuery(); // Gọi câu lệnh SELECT từ phương thức khác
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            cboLop.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox
            while (rs.next()) {
                cboLop.addItem(rs.getString(1)); // Thêm tên lớp vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách lớp.");
        }
    }

    // Phương thức để trả về câu lệnh SELECT
    private String getSelectClassQuery() {
        return "SELECT maLop FROM lophoc"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }

    
    // phần trên là combobox lấy dữ liệu từ database á
    private void loadSubjectID() {
        String query = getSelectSubjectCodeQuery(); // Gọi câu lệnh SELECT từ phương thức khác
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            cboMon.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox
            while (rs.next()) {
                cboMon.addItem(rs.getString(1)); // Thêm tên lớp vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách lớp.");
        }
    }
    private String getSelectSubjectCodeQuery() {
        return "SELECT maMon FROM monhoc"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }
    // 
    public void addStudent2() {
        // Kiểm tra các trường bắt buộc
        if (txtMaSV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sinh viên!");
            return;
        }
        if (txtTenSV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sinh viên!");
            return;
        }
        if (txtTuoi.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tuổi sinh viên!");
            return;
        }
        try {
            int tuoi = Integer.parseInt(txtTuoi.getText());
            if (tuoi <= 0) {
                JOptionPane.showMessageDialog(this, "Tuổi phải lớn hơn 0!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tuổi phải là một số hợp lệ!");
            return;
        }

        // Kiểm tra giới tính
        if (!rdbNam.isSelected() && !rdbNu.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!");
            return;
        }

        // Lấy thông tin từ giao diện
        String maSV = txtMaSV.getText();
        String tenSV = txtTenSV.getText();
        String maMon = (String) cboMon.getSelectedItem();
        String malop = (String) cboLop.getSelectedItem();
        boolean gioiTinh = rdbNam.isSelected(); // Nam: true, Nữ: false
        int tuoi = Integer.parseInt(txtTuoi.getText());

        // Kiểm tra mã sinh viên có trùng hay không
        StudentDAO2 dao = new StudentDAO2();
        if (dao.checkStudentExists(maSV)) {
            JOptionPane.showMessageDialog(this, "Mã sinh viên đã tồn tại, vui lòng nhập mã khác!");
            return; // Kết thúc phương thức nếu mã sinh viên trùng
        }

        // Tạo đối tượng Student2
        Student2 student = new Student2(maSV, tenSV, malop, maMon, gioiTinh, tuoi);

        // Gọi phương thức addStudent để thêm sinh viên vào cơ sở dữ liệu
        boolean result = dao.addStudent(student);

        // Kiểm tra kết quả
        if (result) {
            JOptionPane.showMessageDialog(this, "Thêm sinh viên thành công!");
            fillToTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi thêm sinh viên.");
        }
    }


    //
    public void updateStudent2(){
         // Lấy thông tin từ các trường nhập liệu trên form
        String maSV = txtMaSV.getText();          // Giả sử txtMaSV là JTextField nhập mã sinh viên
        String tenSV = txtTenSV.getText();        // Giả sử txtTenSV là JTextField nhập tên sinh viên

        // Lấy giá trị từ ComboBox (mã lớp và mã môn)
        String maLop = (String) cboLop.getSelectedItem();   // cboMaLop là JComboBox cho mã lớp
        String maMon = (String) cboMon.getSelectedItem();   // cboMaMon là JComboBox cho mã môn

        // Lấy giá trị từ RadioButton (giới tính)
        boolean gioiTinh = rdbNam.isSelected();    // rdNam là JRadioButton cho giới tính Nam

        // Lấy giá trị từ trường tuổi
        int tuoi = Integer.parseInt(txtTuoi.getText());  // Giả sử txtTuoi là JTextField nhập tuổi

        // Tạo đối tượng Student2 từ thông tin nhập vào
        Student2 student = new Student2();
        student.setMasinhvien(maSV);
        student.setTensinhvien(tenSV);
        student.setMalop(maLop);
        student.setMamon(maMon);
        student.setGioitinh(gioiTinh);
        student.setTuoi(tuoi);

        // Tạo đối tượng StudentDAO2 và gọi phương thức updateStudent
        StudentDAO2 studentDAO = new StudentDAO2();
        boolean isUpdated = studentDAO.updateStudent(student);

        if (isUpdated) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            fillToTable(); // Reload lại dữ liệu trong bảng nếu cần
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }
    }
    public void fillToTable(){
        DefaultTableModel model = (DefaultTableModel) tblSV.getModel();
        model.setRowCount(0); // Xóa dữ liệu hiện tại trên bảng

        String query = "SELECT maSV, tenSV, maMon, gioiTinh, tuoi, maLop FROM sinhvien"; // Tùy chỉnh câu lệnh SELECT cho phù hợp với CSDL
        try (Connection conn = connect(); // Kết nối CSDL
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String maSV = rs.getString("maSV");         // Lấy mã sinh viên
                String tenSV = rs.getString("tenSV");       // Lấy tên sinh viên
                String maMon = rs.getString("maMon");       // Lấy mã môn
                boolean gioiTinh = rs.getBoolean("gioiTinh"); // Lấy giới tính (true/false)
                int tuoi = rs.getInt("tuoi");              // Lấy tuổi
                String malop = rs.getString("maLop");          // Lấy lớp

                // Thêm một dòng mới vào JTable
                model.addRow(new Object[]{
                    maSV,
                    tenSV,
                    malop,
                    maMon,
                    gioiTinh ? "Nam" : "Nữ", // Hiển thị Nam hoặc Nữ
                    tuoi,     
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi không xác định.");
        }
    }
    private void clearForm() {
        txtMaSV.setText(""); // Xóa mã sinh viên
        txtTenSV.setText(""); // Xóa tên sinh viên
        cboMon.setSelectedIndex(0); // Reset combo box mã môn về mục đầu tiên
        txtTuoi.setText(""); // Xóa tuổi
        rdbNam.setSelected(true); // Đặt giới tính mặc định là Nam
        cboLop.setSelectedIndex(0); // Reset combo box mã lớp về mục đầu tiên
    }
    private void fillFormTable() {
        int selectedRow = tblSV.getSelectedRow(); // Lấy chỉ số dòng được chọn
        if (selectedRow >= 0) {
            // Lấy thông tin từ bảng và điền vào các trường nhập liệu
            txtMaSV.setText(tblSV.getValueAt(selectedRow, 0).toString());
            txtTenSV.setText(tblSV.getValueAt(selectedRow, 1).toString());
            cboMon.setSelectedItem(tblSV.getValueAt(selectedRow, 2).toString());
            cboLop.setSelectedItem(tblSV.getValueAt(selectedRow, 3).toString());
            boolean isNam = tblSV.getValueAt(selectedRow, 4).toString().equals("Nam");
            rdbNam.setSelected(isNam);
            rdbNu.setSelected(!isNam);
            txtTuoi.setText(tblSV.getValueAt(selectedRow, 5).toString());
        }
    }
    public void removeStudent() {
    int index = tblSV.getSelectedRow(); // Lấy dòng được chọn từ bảng

    if (index != -1) { // Kiểm tra dòng hợp lệ
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa sinh viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            try {
                // Lấy mã sinh viên từ bảng
                String maSinhVien = (String) tblSV.getValueAt(index, 0); // Cột chứa `maSV`

                // Xóa sinh viên khỏi cơ sở dữ liệu
                StudentDAO2.deleteDe(maSinhVien);

                // Xóa sinh viên khỏi danh sách tạm thời (nếu có)
                for (int i = 0; i < sinhvien.size(); i++) {
                    if (sinhvien.get(i).getMasinhvien().equals(maSinhVien)) {
                        sinhvien.remove(i);
                        break;
                    }
                }

                // Cập nhật lại bảng sau khi xóa
                fillToTable();

                JOptionPane.showMessageDialog(this, "Xóa sinh viên thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa sinh viên: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Chưa chọn hàng nào để xóa hoặc dữ liệu không hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
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

        jLabel1 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        btncapnhat = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        txtMaSV = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSV = new javax.swing.JTable();
        txtTenSV = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTuoi = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        rdbNam = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        rdbNu = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnthem = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        cboLop = new javax.swing.JComboBox<>();
        cboMon = new javax.swing.JComboBox<>();
        btnreset = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        btncapnhat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btncapnhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/8726496_upload_icon.png"))); // NOI18N
        btncapnhat.setText("Cập nhật");
        btncapnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncapnhatActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/6296676_excel_microsoft_office_office365_icon.png"))); // NOI18N
        jButton4.setText("Export");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        tblSV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã sinh viên ", "Tên sinh viên ", "Mã môn ", "Giới tính", "Tuổi", "Lớp"
            }
        ));
        tblSV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSV);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Mã sinh viên :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Tên sinh viên :");

        buttonGroup1.add(rdbNam);
        rdbNam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        rdbNam.setText("Nam");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Mã môn :");

        buttonGroup1.add(rdbNu);
        rdbNu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        rdbNu.setText("Nữ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Giới tính :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Tuổi :");

        btnthem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnthem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/299068_add_sign_icon.png"))); // NOI18N
        btnthem.setText("Thêm ");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        btnxoa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnxoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/9004852_trash_delete_bin_remove_icon.png"))); // NOI18N
        btnxoa.setText("Xóa ");
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Lớp :");

        btnreset.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnreset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/9856287_reset_reload_sync_update_icon.png"))); // NOI18N
        btnreset.setText("Reset");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtTenSV, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(cboMon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(rdbNu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(27, 27, 27)
                                    .addComponent(rdbNam, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtTuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cboLop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btncapnhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnxoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnthem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnreset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTuoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(cboMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(txtTenSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdbNam)
                            .addComponent(jLabel7)
                            .addComponent(cboLop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdbNu)
                        .addGap(43, 43, 43)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(33, 33, 33))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(btnthem)
                        .addGap(31, 31, 31)
                        .addComponent(btnxoa)
                        .addGap(26, 26, 26)
                        .addComponent(btncapnhat)
                        .addGap(38, 38, 38)
                        .addComponent(jButton4)
                        .addGap(31, 31, 31)
                        .addComponent(btnreset)
                        .addContainerGap(104, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
       addStudent2();
    }//GEN-LAST:event_btnthemActionPerformed

    private void btncapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncapnhatActionPerformed
        updateStudent2();
    }//GEN-LAST:event_btncapnhatActionPerformed

    private void tblSVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSVMouseClicked
        fillFormTable();
    }//GEN-LAST:event_tblSVMouseClicked

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        removeStudent();
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
        txtMaSV.setText("");
        txtTenSV.setText("");
        txtTuoi.setText("");
        buttonGroup1.clearSelection();
        JOptionPane.showMessageDialog(this,"Đã làm mới!!");
    }//GEN-LAST:event_btnresetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncapnhat;
    private javax.swing.JButton btnreset;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboLop;
    private javax.swing.JComboBox<String> cboMon;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdbNam;
    private javax.swing.JRadioButton rdbNu;
    private javax.swing.JTable tblSV;
    private javax.swing.JTextField txtMaSV;
    private javax.swing.JTextField txtTenSV;
    private javax.swing.JTextField txtTuoi;
    // End of variables declaration//GEN-END:variables

    
}
