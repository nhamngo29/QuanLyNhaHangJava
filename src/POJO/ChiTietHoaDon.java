/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author Nham Ngo
 */
public class ChiTietHoaDon {
    private int maCTHD;
    private String TenMon;
    private String MaMon;
    private int maHD;
    private int soLuong;
    private double giatien;
    private double tongTien;
    private String ghiChu;
    private int MaBan;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(int maCTHD, String TenMon, String MaMon, int maHD, int soLuong, double giatien, double tongTien, String ghiChu, int MaBan) {
        this.maCTHD = maCTHD;
        this.TenMon = TenMon;
        this.MaMon = MaMon;
        this.maHD = maHD;
        this.soLuong = soLuong;
        this.giatien = giatien;
        this.tongTien = tongTien;
        this.ghiChu = ghiChu;
        this.MaBan = MaBan;
    }

    public int getMaCTHD() {
        return maCTHD;
    }

    public void setMaCTHD(int maCTHD) {
        this.maCTHD = maCTHD;
    }

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String TenMon) {
        this.TenMon = TenMon;
    }

    public String getMaMon() {
        return MaMon;
    }

    public void setMaMon(String MaMon) {
        this.MaMon = MaMon;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiatien() {
        return giatien;
    }

    public void setGiatien(double giatien) {
        this.giatien = giatien;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getMaBan() {
        return MaBan;
    }

    public void setMaBan(int MaBan) {
        this.MaBan = MaBan;
    }
}
