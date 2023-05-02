/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import static DAO.DataProvider.update;
import POJO.LoaiThucDon;
import POJO.NhanVien;
import POJO.ThucDon;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nham Ngo
 */
public class ThucDonDAO extends NhaHangDAO<ThucDon, String> {
    String INSERT_OR_UPDATE_SQL = "{CALL SP_InsertOrUpdateThucDon(?,?,?,?,?)}";
    String DELETE_SQL = "DELETE FROM ThucDon WHERE MaMon =?";
    String SELECT_ALL_SQL = "SELECT * FROM ThucDon";
    String SELETE_BY_ID_SQL = "SELECT * FROM ThucDon WHERE MaMon =?";
    String FIND_BY_SQL="{CALL SP_FindThucDon(?)}";
    public List<ThucDon> FIND_ThucDon(String ten)
    {
        List<ThucDon> list = this.selectBySql(FIND_BY_SQL, ten);
        if (list.isEmpty()) {
            
            return null;
        }
        return list;
    }
    @Override
    public void delete(String id) {
        try {
            DataProvider.update(DELETE_SQL, id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public LoaiThucDon GetLoaiByID(int id)
    {
        try {
            LoaiThucDonDAO dao=new LoaiThucDonDAO();
            return dao.selectById(id);
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public ThucDon selectById(String id) {
        List<ThucDon> list = this.selectBySql(SELETE_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
        
    }
    @Override
    public List<ThucDon> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<ThucDon> selectBySql(String sql, Object... args) {
        List<ThucDon> list = new ArrayList<ThucDon>();
        try {
            ResultSet rs = DataProvider.query(sql, args);
            while (rs.next()) {
                ThucDon enity = new ThucDon();
                enity.setMaMon(rs.getString("MaMon"));
                enity.setTenMon(rs.getString("TenMon"));
                enity.setGiaTien(rs.getFloat("GiaTien"));
                enity.setHinhAnh(rs.getString("HinhAnh"));
                enity.setLoai(rs.getInt("LoaiThucDon"));
                list.add(enity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertOrUpdate(ThucDon entity) {
        try {
            DataProvider.update(INSERT_OR_UPDATE_SQL,entity.getMaMon(),entity.getTenMon(),entity.getGiaTien(),entity.getHinhAnh(),entity.getLoai());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
