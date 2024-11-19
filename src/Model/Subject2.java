/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class Subject2 extends JPanel {
    private JTable jtable;

    public Subject2() {
        setLayout(new BorderLayout());
        jtable = new JTable();
        JScrollPane scrollPane = new JScrollPane(jtable);
        add(scrollPane, BorderLayout.CENTER);

        // Tải dữ liệu từ database
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        try {
            // Kết nối tới database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/assjava3", "root", "18102007");
            String sql = "SELECT * FROM lophoc"; // Truy vấn lấy dữ liệu
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Đưa dữ liệu vào DefaultTableModel
            DefaultTableModel model = new DefaultTableModel();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Thêm tiêu đề cột
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            // Thêm dữ liệu vào model
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }

            // Gán model vào JTable
            jtable.setModel(model);

            // Đóng kết nối
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
