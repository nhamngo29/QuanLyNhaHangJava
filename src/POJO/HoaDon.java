/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author Nham Ngo
 */
public class HoaDon {

    int MaHD;
    String NgayTao;
    String TenKH;
    String MaNV;
    int MaBan;
    double TongTien;
    String TrangThai;
    private String tenNV;

    public HoaDon(int MaHD, String NgayTao, String TenKH, String MaNV, int MaBan, double TongTien, String TrangThai, String tenNV) {
        this.MaHD = MaHD;
        this.NgayTao = NgayTao;
        this.TenKH = TenKH;
        this.MaNV = MaNV;
        this.MaBan = MaBan;
        this.TongTien = TongTien;
        this.TrangThai = TrangThai;
        this.tenNV = tenNV;
    }

    public HoaDon() {
    }

    public int getMaHD() {
        return MaHD;
    }

    public void setMaHD(int MaHD) {
        this.MaHD = MaHD;
    }

    public String getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(String NgayTao) {
        this.NgayTao = NgayTao;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String TenKH) {
        this.TenKH = TenKH;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public int getMaBan() {
        return MaBan;
    }

    public void setMaBan(int MaBan) {
        this.MaBan = MaBan;
    }

    public double getTongTien() {
        return TongTien;
    }

    public void setTongTien(double TongTien) {
        this.TongTien = TongTien;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }


  

  
}

