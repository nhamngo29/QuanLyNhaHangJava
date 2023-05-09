/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.KhoHang;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nham Ngo
 */
public class KhoHangDAO extends NhaHangDAO<KhoHang, String>{
    String INSERT_OR_UPDATE_SQL = "{CALL SP_InsertOrUpdateKhoHang(?,?,?,?,?,?,?)}";
    String DELETE_SQL = "DELETE FROM KhoHang WHERE MaHangHoa =?";
    String SELECT_ALL_SQL = "SELECT * FROM KhoHang";
    String SELETE_BY_ID_SQL = "SELECT * FROM KhoHang WHERE MaHangHoa =?";
    @Override
    public void insertOrUpdate(KhoHang entity) {
        try {
            DataProvider.update(INSERT_OR_UPDATE_SQL, entity.getMaHangHoa(), entity.getTenHangHoa(), entity.getNgayNhap(), entity.getDonVi(), entity.getSoLuong(), entity.getChiPhi(), entity.getTongChiPhi());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
    public KhoHang selectById(String id) {
       List<KhoHang> list = this.selectBySql(SELETE_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhoHang> selectAll() {
         return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<KhoHang> selectBySql(String sql, Object... args) {
        List<KhoHang> list = new ArrayList<KhoHang>();
        try {
            ResultSet rs = DataProvider.query(sql, args);
            while (rs.next()) {
                KhoHang enity = new KhoHang();
                enity.setMaHangHoa(rs.getString("MaHangHoa"));
                enity.setTenHangHoa(rs.getString("TenHangHoa"));
                enity.setNgayNhap(rs.getDate("NgayNhap"));
                enity.setDonVi(rs.getString("DonVi"));
                enity.setSoLuong(rs.getInt("SoLuong"));
                enity.setChiPhi(rs.getDouble("ChiPhi"));
                enity.setTongChiPhi(rs.getDouble("TongChiPhi"));
                list.add(enity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
     public List<KhoHang> SearchByNameHH(String TenHangHoa) {
        String sql = "SELECT * FROM KhoHang WHERE TenHangHoa LIKE N'%" + TenHangHoa + "%' ";
        return this.selectBySql(sql);
    }
}
