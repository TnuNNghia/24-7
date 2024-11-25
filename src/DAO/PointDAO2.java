/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import static DAO.StudentDAO2.connection;
import Model.Point2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author ACER
 */
public class PointDAO2 {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/qlsv"; // Đổi theo cơ sở dữ liệu của bạn
    private static final String USER = "root";
    private static final String PASSWORD = "tranhainam123"; // Đổi mật khẩu của bạn nếu cần

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
    
    public boolean addPoint(Point2 point) {
    // Lấy điểm qua môn từ bảng MonHoc
    String sqlGetDiemQuaMon = "SELECT diemQuaMon FROM MonHoc WHERE maMon = ?";
    String sqlInsert = "INSERT INTO diem (maDiem, maSV, maMon, diemThuongXuyen, diemLab, diemAssignment, xepLoai, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = connection();
         PreparedStatement psGetDiemQuaMon = conn.prepareStatement(sqlGetDiemQuaMon);
         PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {

        // Lấy điểm qua môn
        psGetDiemQuaMon.setString(1, point.getMaMon());
        ResultSet rs = psGetDiemQuaMon.executeQuery();
        double diemQuaMon = 0;
        if (rs.next()) {
            diemQuaMon = rs.getDouble("diemQuaMon");
        }

        // Tính điểm trung bình
        double diemTrungBinh = (point.getDiemThuongXuyen() * 2 + point.getDiemLab() * 3 + point.getDiemAssignment() * 5) / 10;

        // Xác định trạng thái
        String trangThai = diemTrungBinh >= diemQuaMon ? "Đạt" : "Rớt";

        // Thêm dữ liệu vào bảng `diem`
        psInsert.setString(1, point.getMaDiem());
        psInsert.setString(2, point.getMaSV());
        psInsert.setString(3, point.getMaMon());
        psInsert.setDouble(4, point.getDiemThuongXuyen());
        psInsert.setDouble(5, point.getDiemLab());
        psInsert.setDouble(6, point.getDiemAssignment());
        psInsert.setString(7, point.getXepLoai());
        psInsert.setString(8, trangThai);

        return psInsert.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}


    // hàm cập nhật 
 public boolean updatePoint(Point2 point) {
    // Kiểm tra thông tin hợp lệ
    if (point.getMaDiem() == null || point.getMaDiem().isEmpty() ||
        point.getMaSV() == null || point.getMaSV().isEmpty() ||
        point.getMaMon() == null || point.getMaMon().isEmpty() ||
        point.getDiemThuongXuyen() < 0 || point.getDiemThuongXuyen() > 10 || 
        point.getDiemLab() < 0 || point.getDiemLab() > 10 || 
        point.getDiemAssignment() < 0 || point.getDiemAssignment() > 10) {
        System.out.println("Thông tin không hợp lệ!");
        return false;
    }

    // Câu lệnh SQL để cập nhật thông tin điểm (không cập nhật diemTrungBinh)
    String sql = "UPDATE diem SET maSV = ?, maMon = ?, diemThuongXuyen = ?, diemLab = ?, diemAssignment = ?, " +
                 "xepLoai = ?, trangThai = ? WHERE maDiem = ?";

    try (Connection conn = connection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        // Gán giá trị cho các tham số trong câu lệnh SQL
        ps.setString(1, point.getMaSV());
        ps.setString(2, point.getMaMon());
        ps.setDouble(3, point.getDiemThuongXuyen());
        ps.setDouble(4, point.getDiemLab());
        ps.setDouble(5, point.getDiemAssignment());

        ps.setString(6, point.getXepLoai());
        ps.setString(7, point.getTrangThai());
        ps.setString(8, point.getMaDiem());

        // Thực thi câu lệnh và trả về kết quả
        return ps.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Trả về false nếu có lỗi
    }
}


   // hàm xóa 
    public boolean deletePoint(String madiem) {
        // Kiểm tra xem mã điểm có tồn tại trong cơ sở dữ liệu không
        if (!checkPointExists(madiem)) {
            System.out.println("Không tìm thấy mã điểm để xóa: " + madiem);
            return false; // Mã điểm không tồn tại
        }

        String sql = "DELETE FROM diem WHERE maDiem = ?";

        try (Connection conn = connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Đặt giá trị mã điểm cần xóa
            pstmt.setString(1, madiem);

            // Thực hiện câu lệnh DELETE và nhận số dòng bị ảnh hưởng
            int affectedRows = pstmt.executeUpdate();

            // Kiểm tra số dòng bị ảnh hưởng để xác nhận xóa thành công
            if (affectedRows > 0) {
                System.out.println("Xóa điểm của mã " + madiem + " thành công.");
                return true; // Xóa thành công
            } else {
                System.out.println("Không tìm thấy điểm của mã " + madiem + " để xóa.");
                return false; // Không có dữ liệu bị xóa
            }

        } catch (SQLException e) {
            // Xử lý ngoại lệ và thông báo chi tiết lỗi
            System.err.println("Lỗi khi xóa điểm với mã " + madiem + ": " + e.getMessage());
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }


     // đếm mã để xét điều kiện 
      public boolean checkPointExists(String maDiem) {
        String sql = "SELECT COUNT(*) FROM diem WHERE maDiem = ?";
        try (Connection conn = connection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDiem);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra mã điểm : " + e.getMessage());
        }
        return false; // Không tồn tại hoặc lỗi
    }
     public List<Point2> getAllPoints() {
    List<Point2> list = new ArrayList<>();
    String sql = "SELECT maDiem, maSV, maMon, diemTrungBinh, xepLoai, trangThai FROM diem";

    try (Connection conn = connection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            Point2 point = new Point2();
            point.setMaDiem(rs.getString("maDiem"));
            point.setMaSV(rs.getString("maSV"));
            point.setMaMon(rs.getString("maMon"));
            point.setDiemTrungBinh(rs.getDouble("diemTrungBinh"));
            point.setXepLoai(rs.getString("xepLoai"));
            point.setTrangThai(rs.getString("trangThai"));
            list.add(point);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}

      public Map<String, Double> getDetailPoints(String maDiem) {
        Map<String, Double> detailPoints = new HashMap<>();
        String query = "SELECT diemThuongXuyen, diemLab, diemAssignment FROM diem WHERE maDiem = ?";
        try (Connection conn = connection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, maDiem);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    detailPoints.put("diemThuongXuyen", rs.getDouble("diemThuongXuyen"));
                    detailPoints.put("diemLab", rs.getDouble("diemLab"));
                    detailPoints.put("diemAssignment", rs.getDouble("diemAssignment"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detailPoints;
    }

    public double getDiemQuaMon(String maMon) throws Exception {
    String query = "SELECT diemQuaMon FROM monhoc WHERE maMon = ?";
    try (Connection conn = connection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, maMon);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("diemQuaMon");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 5.0; // Trả về mặc định là 5.0 nếu không tìm thấy hoặc có lỗi
}
}
