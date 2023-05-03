/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author Nham Ngo
 */
public class NhanVien {
    String MaNV;
    String MatKhau;
    String HoTen;
    String SoDT;
    String ChucVu;
    boolean GioiTinh;
    String Avatar;
    boolean Active;

    public NhanVien(String MaNV, String HoTen, String SoDT, String ChucVu, boolean GioiTinh, String Avatar, boolean Active) {
        this.MaNV = MaNV;
        this.HoTen = HoTen;
        this.SoDT = SoDT;
        this.ChucVu = ChucVu;
        this.GioiTinh = GioiTinh;
        this.Avatar = Avatar;
        this.Active = Active;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean Active) {
        this.Active = Active;
    }

    public NhanVien(String MaNV, String MatKhau, String HoTen, String SoDT, String ChucVu, boolean GioiTinh, String Avatar, boolean Active) {
        this.MaNV = MaNV;
        this.MatKhau = MatKhau;
        this.HoTen = HoTen;
        this.SoDT = SoDT;
        this.ChucVu = ChucVu;
        this.GioiTinh = GioiTinh;
        this.Avatar = Avatar;
        this.Active = Active;
    }
    
    public NhanVien(String MaNV, String MatKhau, String HoTen, String SoDT, String ChucVu, boolean GioiTinh, String Avatar) {
        this.MaNV = MaNV;
        this.MatKhau = MatKhau;
        this.HoTen = HoTen;
        this.SoDT = SoDT;
        this.ChucVu = ChucVu;
        this.GioiTinh = GioiTinh;
        this.Avatar = Avatar;
    }

    public NhanVien() {
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getSoDT() {
        return SoDT;
    }

    public void setSoDT(String SoDT) {
        this.SoDT = SoDT;
    }

    public String getChucVu() {
        return ChucVu;
    }

    public void setChucVu(String ChucVu) {
        this.ChucVu = ChucVu;
    }

    public boolean isGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(boolean GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }
}
