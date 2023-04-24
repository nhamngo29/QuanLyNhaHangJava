/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author Nham Ngo
 */
public class ThucDon {

    private String MaMon;
    private String TenMon;
    private double GiaTien;
    private String HinhAnh;
    private String Loai;

    public ThucDon(String MaMon, String TenMon, double GiaTien, String HinhAnh, String Loai) {
        this.MaMon = MaMon;
        this.TenMon = TenMon;
        this.GiaTien = GiaTien;
        this.HinhAnh = HinhAnh;
        this.Loai = Loai;
    }

    public ThucDon() {
      
    }

    public String getMaMon() {
        return MaMon;
    }

    public void setMaMon(String MaMon) {
        this.MaMon = MaMon;
    }

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String TenMon) {
        this.TenMon = TenMon;
    }

    public double getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(double GiaTien) {
        this.GiaTien = GiaTien;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String Loai) {
        this.Loai = Loai;
    }
    

}
