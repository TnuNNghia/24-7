/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import javax.swing.JPanel;



/**
 *
 * @author ACER
 */
public class Class2 extends JPanel  {
    public String malop;
    public String tenlop;
    public String mota;

    public Class2() {
    }

    public Class2(String malop, String tenlop, String mota) {
        this.malop = malop;
        this.tenlop = tenlop;
        this.mota = mota;
    }
    
    public String getMalop() {
        return malop;
    }

    public void setMalop(String malop) {
        this.malop = malop;
    }

    public String getTenlop() {
        return tenlop;
    }

    public void setTenlop(String tenlop) {
        this.tenlop = tenlop;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
