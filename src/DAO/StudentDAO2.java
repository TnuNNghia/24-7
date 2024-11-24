/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Student2;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author ACER
 */
public class StudentDAO2 {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/assjava3"; // Đổi theo cơ sở dữ liệu của bạn
    private static final String USER = "root";
    private static final String PASSWORD = "18102007"; // Đổi mật khẩu của bạn nếu cần

    static {
        try {
            // Đăng ký driver của MySQL
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to register MySQL driver", e);
        }
    }

    // Phương thức kết nối
    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    // Phương thức thêm sinh viên
    public boolean addStudent(Student2 student) {
        String sql = "INSERT INTO sinhvien (maSV, tenSV,maLop, maMon,gioiTinh, tuoi) VALUES (?, ?, ?, ?, ?,?)";

       if (student.getMasinhvien().isEmpty() || 
            student.getTensinhvien().isEmpty() || 
            student.getMamon().isEmpty() || 
            student.getMalop().isEmpty() || 
            student.getTuoi() <= 0) {
            System.out.println("Thông tin không hợp lệ!");
        return false;
    }


        try (Connection conn = connection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

           ps.setString(1, student.getMasinhvien());
            ps.setString(2, student.getTensinhvien());
            ps.setString(3, student.getMalop());  // maLop
            ps.setString(4, student.getMamon());  // maMon
            ps.setBoolean(5, student.getGioitinh());
            ps.setInt(6, student.getTuoi());



            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
}

public boolean updateStudent(Student2 student) {
    // Kiểm tra mã lớp tồn tại trong bảng lophoc
    if (!isMaLopExist(student.getMalop())) {
        System.out.println("Mã lớp không tồn tại!");
        return false;
    }

    // Kiểm tra thông tin hợp lệ
    if (student.getTensinhvien() == null || student.getTensinhvien().isEmpty() ||
        student.getMamon() == null || student.getMamon().isEmpty() ||
        student.getMalop() == null || student.getMalop().isEmpty() ||
        student.getMasinhvien() == null || student.getMasinhvien().isEmpty()) {
        System.out.println("Thông tin không hợp lệ!");
        return false;
    }

    String sql = "UPDATE sinhvien SET tenSV = ?, maMon = ?, maLop = ?, gioiTinh = ?, tuoi = ? WHERE maSV = ?";
    try (Connection conn = connection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, student.getTensinhvien());
        ps.setString(2, student.getMamon());
        ps.setString(3, student.getMalop());
        ps.setBoolean(4, student.getGioitinh());
        ps.setInt(5, student.getTuoi());
        ps.setString(6, student.getMasinhvien());

        return ps.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Trả về false nếu có lỗi
    }
}
// đếm xem có bao nhiêu mã lớp để xét điều kiện 
public boolean isMaLopExist(String maLop) {
        String sql = "SELECT COUNT(*) FROM lophoc WHERE maLop = ?";
        try (Connection conn = connection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maLop);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
// Kiểm tra sinh viên có tồn tại không
    public boolean checkStudentExists(String maSV) {
        String sql = "SELECT COUNT(*) FROM sinhvien WHERE maSV = ?";
        try (Connection conn = connection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSV);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra mã sinh viên: " + e.getMessage());
        }
        return false; // Không tồn tại hoặc lỗi
    }

  public static void deleteDe(String maSinhVien) {
    String sql = "DELETE FROM sinhvien WHERE maSV = ?";
    
    try (Connection conn = connection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, maSinhVien); // Đặt giá trị mã sinh viên cần xóa
        int affectedRows = pstmt.executeUpdate(); // Thực hiện câu lệnh DELETE
        
        if (affectedRows > 0) {
            System.out.println("Xóa sinh viên thành công!");
        } else {
            System.out.println("Không tìm thấy sinh viên để xóa!");
        }

    } catch (SQLException e) {
        System.err.println("SQL Exception: " + e.getMessage());
    }
}
}
