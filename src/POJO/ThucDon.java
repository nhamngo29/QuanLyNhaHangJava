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
    private float GiaTien;
    private String HinhAnh;
    private int Loai;

    public ThucDon(String MaMon, String TenMon, float GiaTien, String HinhAnh, int Loai) {
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

    public float getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(float GiaTien) {
        this.GiaTien = GiaTien;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }

    public int getLoai() {
        return Loai;
    }

    public void setLoai(int Loai) {
        this.Loai = Loai;
    }
    

}
