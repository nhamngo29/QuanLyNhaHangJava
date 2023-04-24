/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author Nham Ngo
 */
public class BanAn {
    private int MaBan;
    private int LoaiBan;
    private String TrangThai;
    private String GhiChu;
    public BanAn() {
    }

    public BanAn(int MaBan, int LoaiBan, String TrangThai, String GhiChu) {
        this.MaBan = MaBan;
        this.LoaiBan = LoaiBan;
        this.TrangThai = TrangThai;
        this.GhiChu = GhiChu;
    }

    public int getMaBan() {
        return MaBan;
    }

    public void setMaBan(int MaBan) {
        this.MaBan = MaBan;
    }

    public int getLoaiBan() {
        return LoaiBan;
    }

    public void setLoaiBan(int LoaiBan) {
        this.LoaiBan = LoaiBan;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }
}
