/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import com.mysql.cj.xdevapi.Statement;
import com.sun.jdi.connect.spi.Connection;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;


/**
 *
 * @author ACER
 */
public class Class2 extends JPanel  {
    
    public  String malop;
    public String tenlop;
    public String mota;

    public Class2() {
    }
    
    public Class2(String malop, String tenlop, String mota) {
        this.malop = malop;
        this.tenlop = tenlop;
        this.mota = mota;
    }
    

    

}
