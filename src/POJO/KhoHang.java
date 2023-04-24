/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import java.util.Date;

/**
 *
 * @author Nham Ngo
 */
public class KhoHang {
    String MaHangHoa;
    String TenHangHoa;
    Date NgayNhap;
    String DonVi;
    double SoLuong;
    double ChiPhi;
    double TongChiPhi;

    public KhoHang(String MaHangHoa, String TenHangHoa, Date NgayNhap, String DonVi, double SoLuong, double ChiPhi, double TongChiPhi) {
        this.MaHangHoa = MaHangHoa;
        this.TenHangHoa = TenHangHoa;
        this.NgayNhap = NgayNhap;
        this.DonVi = DonVi;
        this.SoLuong = SoLuong;
        this.ChiPhi = ChiPhi;
        this.TongChiPhi = TongChiPhi;
    }

    public KhoHang() {
    }

    public String getMaHangHoa() {
        return MaHangHoa;
    }

    public void setMaHangHoa(String MaHangHoa) {
        this.MaHangHoa = MaHangHoa;
    }

    public String getTenHangHoa() {
        return TenHangHoa;
    }

    public void setTenHangHoa(String TenHangHoa) {
        this.TenHangHoa = TenHangHoa;
    }

    public Date getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(Date NgayNhap) {
        this.NgayNhap = NgayNhap;
    }

    public String getDonVi() {
        return DonVi;
    }

    public void setDonVi(String DonVi) {
        this.DonVi = DonVi;
    }

    public double getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(double SoLuong) {
        this.SoLuong = SoLuong;
    }

    public double getChiPhi() {
        return ChiPhi;
    }

    public void setChiPhi(double ChiPhi) {
        this.ChiPhi = ChiPhi;
    }

    public double getTongChiPhi() {
        return getChiPhi() * getSoLuong();
    }

    public void setTongChiPhi(double TongChiPhi) {
        this.TongChiPhi = TongChiPhi;
    }
}
