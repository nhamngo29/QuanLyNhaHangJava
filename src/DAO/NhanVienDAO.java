/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Custom.MD5;
import POJO.NhanVien;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nham Ngo
 */
public class NhanVienDAO extends NhaHangDAO<NhanVien, String>{
    String INSERT_OR_UPDATE_SQL = "{Call SP_InsertOrUpdateNhanVien(?,?,?,?,?,?,?)}";
    String DELETE_SQL = "DELETE FROM NHANVIEN WHERE MaNV =?";
    String SELECT_ALL_SQL = "SELECT * FROM NHANVIEN";
    String SELETE_BY_ID_SQL = "SELECT * FROM NHANVIEN WHERE MaNV =?";
    String SP_LOGIN="{CALL SP_Login(?,?)}";
    String FIND_BY_SQL="{CALL SP_FindNhanVien(?)}";
    public List<NhanVien> FIND_NhanVien(String ten)
    {
        List<NhanVien> list = this.selectBySql(FIND_BY_SQL, ten);
        if (list.isEmpty()) {
            
            return null;
        }
        return list;
    }
    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }
    public NhanVien Login(String MNV,String Password)
    {
         List<NhanVien> lnd=selectBySql(SP_LOGIN, MNV,MD5.getMd5(Password));//Password đã được mã hóa
        if(lnd.size()==0)
        {
            return null;
        }
        return lnd.get(0);
    }
    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<NhanVien>();
        try {
            ResultSet rs = DataProvider.query(sql, args);
            while (rs.next()) {
                NhanVien enity = new NhanVien();
                enity.setMaNV(rs.getString("MaNV"));
                enity.setMatKhau(rs.getString("Password"));
                enity.setHoTen(rs.getString("HoTen"));
                enity.setSoDT(rs.getString("SoDT"));
                enity.setChucVu(rs.getString("ChucVu"));
                enity.setGioiTinh(rs.getBoolean("GioiTinh"));
                enity.setAvatar(rs.getString("Avatar"));
                enity.setActive(rs.getBoolean("Active"));
                list.add(enity);
                System.out.println("Chức danh "+enity.getChucVu());
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<NhanVien> selectByOrderD() {
        String sql = "SELECT * FROM NHANVIEN ORDER BY HoTen desc";
        return this.selectBySql(sql);
    }

    public List<NhanVien> selectByOrderA() {
        String sql = "SELECT * FROM NHANVIEN ORDER BY HoTen ASC";
        return this.selectBySql(sql);
    }

    public List<NhanVien> selectByPosition(String position) {
        String sql = "SELECT * FROM NHANVIEN WHERE ChucVu like ?";
        return this.selectBySql(sql, "%" + position + "%");
    }

    public List<NhanVien> timNhanVienTheoTenNV(String TenNhanVien) {
        String sql = "SELECT * FROM NhanVien WHERE HoTen LIKE N'%" + TenNhanVien + "%' ";
        return this.selectBySql(sql);
    }

    @Override
    public void delete(String id) {
        try {
            DataProvider.update(DELETE_SQL, id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = this.selectBySql(SELETE_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void insertOrUpdate(NhanVien entity) {
        try {
            DataProvider.update(INSERT_OR_UPDATE_SQL, entity.getMaNV(),entity.getHoTen(),entity.getSoDT(),entity.getChucVu(),entity.isGioiTinh(),entity.getAvatar(),entity.isActive());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
