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
public class DatBan {

    String MaDatBan;
    String HoTenKH;
    String SDTKH;
    Date NgayDatBan;
    String GioDat;
    int SoNguoi;
    String GhiChu;
    String MaNV;

    public DatBan(String MaDatBan, String HoTenKH, String SDTKH, Date NgayDatBan, String GioDat, int SoNguoi, String GhiChu, String MaNV) {
        this.MaDatBan = MaDatBan;
        this.HoTenKH = HoTenKH;
        this.SDTKH = SDTKH;
        this.NgayDatBan = NgayDatBan;
        this.GioDat = GioDat;
        this.SoNguoi = SoNguoi;
        this.GhiChu = GhiChu;
        this.MaNV = MaNV;
    }

    public DatBan() {
    }

    public String getMaDatBan() {
        return MaDatBan;
    }

    public void setMaDatBan(String MaDatBan) {
        this.MaDatBan = MaDatBan;
    }

    public String getHoTenKH() {
        return HoTenKH;
    }

    public void setHoTenKH(String HoTenKH) {
        this.HoTenKH = HoTenKH;
    }

    public String getSDTKH() {
        return SDTKH;
    }

    public void setSDTKH(String SDTKH) {
        this.SDTKH = SDTKH;
    }

    public Date getNgayDatBan() {
        return NgayDatBan;
    }

    public void setNgayDatBan(Date NgayDatBan) {
        this.NgayDatBan = NgayDatBan;
    }

    public String getGioDat() {
        return GioDat;
    }

    public void setGioDat(String GioDat) {
        this.GioDat = GioDat;
    }

    public int getSoNguoi() {
        return SoNguoi;
    }

    public void setSoNguoi(int SoNguoi) {
        this.SoNguoi = SoNguoi;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    
   

}
