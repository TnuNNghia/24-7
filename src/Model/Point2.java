/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ACER
 */
public class Point2 {
    private String maDiem;
    private String maSV;
    private String maMon;
    private Double diemThuongXuyen;
    private Double diemLab;
    private Double diemAssignment;
    private Double diemTrungBinh;
    private String xepLoai;
    private String trangThai;

    // Constructor không tham số
    public Point2() {
    }

    // Constructor có tham số
    public Point2(String maDiem, String maSV, String maMon, Double diemThuongXuyen, Double diemLab, 
                  Double diemAssignment, Double diemTrungBinh, String xepLoai, String trangThai) {
        this.maDiem = maDiem;
        this.maSV = maSV;
        this.maMon = maMon;
        this.diemThuongXuyen = diemThuongXuyen;
        this.diemLab = diemLab;
        this.diemAssignment = diemAssignment;
        this.diemTrungBinh = diemTrungBinh;
        this.xepLoai = xepLoai;
        this.trangThai = trangThai;
    }

    // Getter và Setter
    public String getMaDiem() {
        return maDiem;
    }

    public void setMaDiem(String maDiem) {
        this.maDiem = maDiem;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public Double getDiemThuongXuyen() {
        return diemThuongXuyen;
    }

    public void setDiemThuongXuyen(Double diemThuongXuyen) {
        this.diemThuongXuyen = diemThuongXuyen;
    }

    public Double getDiemLab() {
        return diemLab;
    }

    public void setDiemLab(Double diemLab) {
        this.diemLab = diemLab;
    }

    public Double getDiemAssignment() {
        return diemAssignment;
    }

    public void setDiemAssignment(Double diemAssignment) {
        this.diemAssignment = diemAssignment;
    }

    public Double getDiemTrungBinh() {
        return diemTrungBinh;
    }

    public void setDiemTrungBinh(Double diemTrungBinh) {
        this.diemTrungBinh = diemTrungBinh;
    }

    public String getXepLoai() {
        return xepLoai;
    }

    public void setXepLoai(String xepLoai) {
        this.xepLoai = xepLoai;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
