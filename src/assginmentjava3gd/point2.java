/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package assginmentjava3gd;

import DAO.PointDAO2;
import static DAO.PointDAO2.connection;
import Model.Point2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class point2 extends javax.swing.JInternalFrame {

    /**
     * Creates new form point2
     */
    public point2() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        loadClassID();
        loadSubjectID();
        loadStudentID();
        fillToTable();
    }
    // đẩy dữ liệu từ database lên combobox
    // Phương thức kết nối cơ sở dữ liệu
    private Connection connect() throws Exception {
        String url = "jdbc:mysql://localhost:3306/assjava3"; // Thay 'ten_database' bằng tên database
        String user = "root"; // Thay username
        String password = "18102007"; // Thay password
        return DriverManager.getConnection(url, user, password);
    }
    // load dữ liệu lên combobox
     private void loadClassID() {
        String query = getSelectClassQuery(); // Gọi câu lệnh SELECT từ phương thức khác
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            coboboxlop.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox
            while (rs.next()) {
                coboboxlop.addItem(rs.getString(1)); // Thêm tên lớp vào ComboBox
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

            coboboxmon.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox
            while (rs.next()) {
                coboboxmon.addItem(rs.getString(1)); // Thêm tên lớp vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách lớp.");
        }
    }
    // Phương thức để trả về câu lệnh SELECT
    private String getSelectSubjectCodeQuery() {
        return "SELECT maMon FROM monhoc"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }
    private void loadStudentID() {
        String query = getSelectStudentCodeQuery(); // Gọi câu lệnh SELECT từ phương thức khác
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            conbbxmasinhvien.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox
            while (rs.next()) {
                conbbxmasinhvien.addItem(rs.getString(1)); // Thêm tên lớp vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách lớp.");
        }
    }
    // Phương thức để trả về câu lệnh SELECT
    private String getSelectStudentCodeQuery() {
        return "SELECT maSV FROM sinhvien"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }
    // code trên đã code vui lòng không được đụng vào 
    public void addpoint() {
    // Kiểm tra các trường bắt buộc
    if (txtmadiem.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã điểm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (txtdiemlab.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm Lab!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (txtdiemthxuyen.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm Thường Xuyên!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (txtdiemass.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm Assignment!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Kiểm tra định dạng điểm (0 - 10)
    double diemLab, diemThuongXuyen, diemAssignment;
    try {
        diemLab = Double.parseDouble(txtdiemlab.getText().trim());
        diemThuongXuyen = Double.parseDouble(txtdiemthxuyen.getText().trim());
        diemAssignment = Double.parseDouble(txtdiemass.getText().trim());

        if (diemLab < 0 || diemLab > 10) {
            JOptionPane.showMessageDialog(this, "Điểm Lab phải nằm trong khoảng 0 - 10!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (diemThuongXuyen < 0 || diemThuongXuyen > 10) {
            JOptionPane.showMessageDialog(this, "Điểm Thường Xuyên phải nằm trong khoảng 0 - 10!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (diemAssignment < 0 || diemAssignment > 10) {
            JOptionPane.showMessageDialog(this, "Điểm Assignment phải nằm trong khoảng 0 - 10!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Điểm nhập vào phải là số hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Kiểm tra các combobox
    if (conbbxmasinhvien.getSelectedIndex() == 0) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn mã sinh viên!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (coboboxmon.getSelectedIndex() == 0) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn môn học!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (coboboxlop.getSelectedIndex() == 0) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp học!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Lấy dữ liệu từ giao diện
    String maDiem = txtmadiem.getText().trim();
    String maSV = (String) conbbxmasinhvien.getSelectedItem();
    String maMon = (String) coboboxmon.getSelectedItem();
    String malop = (String) coboboxlop.getSelectedItem();
    String trangThai = txttrangthai.getText().trim();

    // Tính điểm trung bình (không truyền vào SQL)
    double diemTrungBinh = (diemLab + diemThuongXuyen + diemAssignment) / 3;

    // Xếp loại
    String xepLoai;
    if (diemTrungBinh >= 8) {
        xepLoai = "Giỏi";
    } else if (diemTrungBinh >= 6.5) {
        xepLoai = "Khá";
    } else if (diemTrungBinh >= 5) {
        xepLoai = "Trung Bình";
    } else {
        xepLoai = "Yếu";
    }

    // Kiểm tra mã điểm có tồn tại hay không
    PointDAO2 dao = new PointDAO2();
    if (dao.checkPointExists(maDiem)) {
        JOptionPane.showMessageDialog(this, "Mã điểm đã tồn tại, vui lòng nhập mã khác!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Tạo đối tượng Point2
    Point2 point = new Point2(maDiem, maSV, maMon, diemThuongXuyen, diemLab, diemAssignment, diemTrungBinh, xepLoai, trangThai);

    // Gọi phương thức thêm điểm
    boolean result = dao.addPoint(point);

    // Kiểm tra kết quả
    if (result) {
        JOptionPane.showMessageDialog(this, "Thêm điểm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        fillToTable(); // Cập nhật bảng
        clearForm();   // Xóa dữ liệu trong form
    } else {
        JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi thêm điểm!", "Thông báo", JOptionPane.ERROR_MESSAGE);
    }
}


// Hàm clearForm()
    private void clearForm() {
        txtmadiem.setText("");
        txtdiemlab.setText("");
        txtdiemthxuyen.setText("");
        txtdiemass.setText("");
        txttrangthai.setText("");
        coboboxlop.setSelectedIndex(0);
        coboboxmon.setSelectedIndex(0);
        conbbxmasinhvien.setSelectedIndex(0);
}


    public void updatepoint() {
    // Kiểm tra các trường bắt buộc
    if (txtmadiem.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã điểm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (txtdiemlab.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm Lab!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (txtdiemthxuyen.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm Thường Xuyên!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (txtdiemass.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm Assignment!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Kiểm tra định dạng điểm (0 - 10)
    double diemLab, diemThuongXuyen, diemAssignment;
    try {
        diemLab = Double.parseDouble(txtdiemlab.getText().trim());
        diemThuongXuyen = Double.parseDouble(txtdiemthxuyen.getText().trim());
        diemAssignment = Double.parseDouble(txtdiemass.getText().trim());

        if (diemLab < 0 || diemLab > 10) {
            JOptionPane.showMessageDialog(this, "Điểm Lab phải nằm trong khoảng 0 - 10!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (diemThuongXuyen < 0 || diemThuongXuyen > 10) {
            JOptionPane.showMessageDialog(this, "Điểm Thường Xuyên phải nằm trong khoảng 0 - 10!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (diemAssignment < 0 || diemAssignment > 10) {
            JOptionPane.showMessageDialog(this, "Điểm Assignment phải nằm trong khoảng 0 - 10!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Điểm nhập vào phải là số hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Lấy dữ liệu từ giao diện
    String maDiem = txtmadiem.getText().trim();
    String maSV = (String) conbbxmasinhvien.getSelectedItem();
    String maMon = (String) coboboxmon.getSelectedItem();
    String malop = (String) coboboxlop.getSelectedItem();
    String trangThai = txttrangthai.getText().trim();

    // Tính điểm trung bình (không truyền vào SQL)
    double diemTrungBinh = (diemLab + diemThuongXuyen + diemAssignment) / 3;

    // Xếp loại
    String xepLoai;
    if (diemTrungBinh >= 8) {
        xepLoai = "Giỏi";
    } else if (diemTrungBinh >= 6.5) {
        xepLoai = "Khá";
    } else if (diemTrungBinh >= 5) {
        xepLoai = "Trung Bình";
    } else {
        xepLoai = "Yếu";
    }

    // Cập nhật điểm
    Point2 point = new Point2(maDiem, maSV, maMon, diemThuongXuyen, diemLab, diemAssignment, diemTrungBinh, xepLoai, trangThai);

    // Gọi phương thức cập nhật điểm
    PointDAO2 dao = new PointDAO2();
    boolean result = dao.updatePoint(point);

    // Kiểm tra kết quả
    if (result) {
        JOptionPane.showMessageDialog(this, "Cập nhật điểm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        fillToTable(); // Cập nhật bảng
        clearForm();   // Xóa dữ liệu trong form
    } else {
        JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi cập nhật điểm!", "Thông báo", JOptionPane.ERROR_MESSAGE);
    }
}

    public void deletepoint() {
    String maDiem = txtmadiem.getText().trim();

    if (maDiem.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã điểm để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    PointDAO2 dao = new PointDAO2();
    boolean result = dao.deletePoint(maDiem);

    if (result) {
        JOptionPane.showMessageDialog(this, "Xóa điểm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        fillToTable(); // Cập nhật bảng
        clearForm();   // Xóa dữ liệu trong form
    } else {
        JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi xóa điểm!", "Thông báo", JOptionPane.ERROR_MESSAGE);
    }
}

   private void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblpoint.getModel(); // tblPoints là JTable
        model.setRowCount(0); // Xóa toàn bộ dữ liệu cũ trên bảng

        // Tương tác với DAO để lấy danh sách điểm
        PointDAO2 dao = new PointDAO2();
        List<Point2> points = dao.getAllPoints(); // Giả định rằng `getAllPoints()` trả về danh sách điểm từ DB

        // Duyệt qua danh sách và thêm vào bảng
        for (Point2 point : points) {
            Object[] row = new Object[]{
                point.getMaDiem(),         // Mã điểm
                point.getMaSV(),           // Mã sinh viên
                point.getMaMon(),          // Mã môn
                point.getDiemTrungBinh(),  // Điểm trung bình
                point.getXepLoai(),        // Xếp loại
                point.getTrangThai()       // Trạng thái (di chuyển xuống cuối)
            };
            model.addRow(row);
        }
    }
   // click hiện lên cái textfield
    private void fillFormTable() {
        int selectedRow = tblpoint.getSelectedRow(); // Lấy chỉ số dòng được chọn trong bảng
        if (selectedRow >= 0) {
            try {
                // Lấy thông tin từ bảng
                String maDiem = tblpoint.getValueAt(selectedRow, 0).toString(); // Mã điểm
                String maSinhVien = tblpoint.getValueAt(selectedRow, 1).toString(); // Mã sinh viên
                String maMonHoc = tblpoint.getValueAt(selectedRow, 2).toString(); // Mã môn học
                String trangThai = tblpoint.getValueAt(selectedRow, 5).toString(); // Trạng thái

                // Điền dữ liệu vào các trường
                txtmadiem.setText(maDiem);
                conbbxmasinhvien.setSelectedItem(maSinhVien);
                coboboxmon.setSelectedItem(maMonHoc);
                txttrangthai.setText(trangThai);

                // Sử dụng PointDAO2 để lấy chi tiết điểm
                PointDAO2 pointDao = new PointDAO2();
                Map<String, Double> detailPoints = pointDao.getDetailPoints(maDiem);

                // Điền các điểm chi tiết nếu tồn tại
                if (detailPoints != null && !detailPoints.isEmpty()) {
                    txtdiemthxuyen.setText(String.valueOf(detailPoints.getOrDefault("diemThuongXuyen", 0.0)));
                    txtdiemlab.setText(String.valueOf(detailPoints.getOrDefault("diemLab", 0.0)));
                    txtdiemass.setText(String.valueOf(detailPoints.getOrDefault("diemAssignment", 0.0)));
                } else {
                    // Làm trống nếu không có dữ liệu
                    txtdiemthxuyen.setText("");
                    txtdiemlab.setText("");
                    txtdiemass.setText("");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi điền dữ liệu: " + e.getMessage());
            }
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

        jLabel3 = new javax.swing.JLabel();
        btnxoa = new javax.swing.JButton();
        txtmadiem = new javax.swing.JTextField();
        txtdiemass = new javax.swing.JTextField();
        txtdiemthxuyen = new javax.swing.JTextField();
        btncapnhat = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpoint = new javax.swing.JTable();
        coboboxlop = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        txttrangthai = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtdiemlab = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnthem = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        coboboxmon = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        conbbxmasinhvien = new javax.swing.JComboBox<>();

        jLabel3.setText("Mã môn :");

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

        jLabel9.setText("Mã lớp :");

        jLabel7.setText("Trạng Thái :");

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/6296676_excel_microsoft_office_office365_icon.png"))); // NOI18N
        jButton4.setText("Export");

        tblpoint.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã điểm ", "Mã sinh viên ", "Mã môn ", "Mã lớp ", "Điểm trung bình ", "Xếp loại", "Trạng thái "
            }
        ));
        tblpoint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpointMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblpoint);

        jLabel1.setText("Mã điểm :");

        jLabel4.setText("Điểm thường xuyên :");

        jLabel2.setText("Mã sinh viên :");

        btnthem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnthem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/299068_add_sign_icon.png"))); // NOI18N
        btnthem.setText("Thêm");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        jLabel5.setText("Điểm lab :");

        jLabel6.setText("Điểm assginments :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(txtmadiem, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(conbbxmasinhvien, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(coboboxmon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtdiemthxuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtdiemass, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtdiemlab, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(coboboxlop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txttrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(218, 218, 218)
                .addComponent(btnthem)
                .addGap(62, 62, 62)
                .addComponent(btnxoa)
                .addGap(76, 76, 76)
                .addComponent(btncapnhat)
                .addGap(46, 46, 46)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(coboboxlop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtmadiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(47, 47, 47)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(conbbxmasinhvien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(coboboxmon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(47, 47, 47)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(txtdiemthxuyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(txtdiemlab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(47, 47, 47)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(txtdiemass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txttrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthem)
                    .addComponent(btnxoa)
                    .addComponent(btncapnhat)
                    .addComponent(jButton4))
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
       addpoint();
    }//GEN-LAST:event_btnthemActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
       deletepoint();
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btncapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncapnhatActionPerformed
        updatepoint();
    }//GEN-LAST:event_btncapnhatActionPerformed

    private void tblpointMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpointMouseClicked
       fillFormTable();
    }//GEN-LAST:event_tblpointMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncapnhat;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JComboBox<String> coboboxlop;
    private javax.swing.JComboBox<String> coboboxmon;
    private javax.swing.JComboBox<String> conbbxmasinhvien;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblpoint;
    private javax.swing.JTextField txtdiemass;
    private javax.swing.JTextField txtdiemlab;
    private javax.swing.JTextField txtdiemthxuyen;
    private javax.swing.JTextField txtmadiem;
    private javax.swing.JTextField txttrangthai;
    // End of variables declaration//GEN-END:variables
}
