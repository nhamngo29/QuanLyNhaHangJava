/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author Nham Ngo
 */
public class LoaiThucDon {
   int MaLoaiTD;
   String Ten;

    public int getMaLoaiTD() {
        return MaLoaiTD;
    }

    public void setMaLoaiTD(int MaLoaiTD) {
        this.MaLoaiTD = MaLoaiTD;
    }

    public String getTen() {
        return Ten;
    }

    @Override
    public String toString() {
        return Ten;
    }

    public void setTen(String Ten) {
        this.Ten = Ten;
    }

    public LoaiThucDon(int MaLoaiTD, String Ten) {
        this.MaLoaiTD = MaLoaiTD;
        this.Ten = Ten;
    }

    public LoaiThucDon() {
    }
   
}
