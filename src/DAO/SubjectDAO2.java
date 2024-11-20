/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Subject2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
public class SubjectDAO2 {
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

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    public static void insertSub(Subject2 sb) {
        String sql = "INSERT INTO monhoc (maMon, tenMon, moTa, diemQuaMon) VALUES (?, ?, ?, ?)";

        try (Connection conn = connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sb.getMamon());            
            pstmt.setString(2, sb.getTenmon());          
            pstmt.setString(3, sb.getMota());      
            pstmt.setFloat(4, sb.getDiemQuaMon());  // Sửa lại chỗ này, gán diemQuaMon

            // Thực thi câu lệnh
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Thêm môn học thành công!");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm môn học: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void updateSub(Subject2 sb) {
        String sql = "UPDATE monhoc SET tenMon = ?, moTa = ?, diemQuaMon = ? WHERE maMon = ?";

        try (Connection conn = connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sb.getTenmon());
            pstmt.setString(2, sb.getMota());
            pstmt.setFloat(3, sb.getDiemQuaMon()); // Cập nhật diemQuaMon
            pstmt.setString(4, sb.getMamon());

            // Thực thi câu lệnh
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cập nhật thông tin môn học thành công!");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật thông tin môn học: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void deleteSub(String mamon) {
        String sql = "DELETE FROM monhoc WHERE maMon = ?";

        try (Connection conn = connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, mamon);  // Đặt giá trị maMon cần xóa
            int affectedRows = pstmt.executeUpdate();  // Thực hiện câu lệnh DELETE

            if (affectedRows > 0) {
                System.out.println("Xóa môn học thành công!");
            } else {
                System.out.println("Không tìm thấy môn học để xóa!");
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }
    }
    // lấy dữ liệu từ database lên 
    public static List<Subject2> getAllSubject() {
        List<Subject2> sub = new ArrayList<>();
        String sql = "SELECT maMon, tenMon, moTa, diemQuaMon FROM monhoc";

        try (Connection conn = connection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Subject2 cl = new Subject2();
                cl.setMamon(rs.getString("maMon"));
                cl.setTenmon(rs.getString("tenMon"));
                cl.setMota(rs.getString("moTa"));
                cl.setDiemQuaMon(rs.getFloat("diemQuaMon"));  // Sửa lại chỗ này
                sub.add(cl);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }

        return sub;
    }

    
}
