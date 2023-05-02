/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.LoaiThucDon;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nham Ngo
 */
public class LoaiThucDonDAO extends NhaHangDAO<LoaiThucDon, Integer>{
    String INSERT_OR_UPDATE_SQL = "{CALL SP_InsertOrUpdateThucDon(?,?,?,?,?)}";
    String DELETE_SQL = "DELETE FROM LoaiThucDon WHERE MaLoaiTD =?";
    String SELECT_ALL_SQL = "SELECT * FROM LoaiThucDon";
    String SELETE_BY_ID = "SELECT * FROM LoaiThucDon WHERE MaLoaiTD =?";
    String SELETE_BY_NAME = "SELECT * FROM LoaiThucDon WHERE Ten=?";
    @Override
    public void insertOrUpdate(LoaiThucDon entity) {
        try {
            DataProvider.update(INSERT_OR_UPDATE_SQL, entity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
       try {
            DataProvider.update(DELETE_SQL, id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public LoaiThucDon selectById(Integer id) {
        List<LoaiThucDon> list = this.selectBySql(SELETE_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    public LoaiThucDon selectByName(String ten)
    {
        List<LoaiThucDon> list = this.selectBySql(SELETE_BY_NAME, ten);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    public LoaiThucDonDAO() {
    }
   
    @Override
    public List<LoaiThucDon> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<LoaiThucDon> selectBySql(String sql, Object... args) {
        List<LoaiThucDon> list = new ArrayList<>();
        try {
            ResultSet rs = DataProvider.query(sql, args);
            while (rs.next()) {
                LoaiThucDon enity = new LoaiThucDon();
                enity.setMaLoaiTD(rs.getInt("MaLoaiTD"));
                enity.setTen(rs.getString("Ten"));
                list.add(enity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        LoaiThucDonDAO dao=new LoaiThucDonDAO();
        System.out.println(dao.selectById(1).getTen());
    }
}
