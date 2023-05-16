/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.BanAn;
import POJO.LoaiThucDon;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nham Ngo
 */
public class BanAnDAO extends NhaHangDAO<BanAn, Integer>{

    String INSERT_OR_UPDATE_SQL = "{CALL SP_InsertOrUpdateBanAn(?,?,?)}";
    String DELETE_SQL = "DELETE FROM BanAn WHERE MaBanAn =?";
    String SELECT_ALL_SQL = "SELECT * FROM BanAn";
    String SELETE_BY_ID = "SELECT * FROM BanAn WHERE MaBanAn =?";
    @Override
    public void insertOrUpdate(BanAn entity) {
       try {
            DataProvider.update(INSERT_OR_UPDATE_SQL, entity.getMaBan(),entity.getLoaiBan(),entity.getTrangThai());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public BanAnDAO() {
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
    public BanAn selectById(Integer id) {
        List<BanAn> list = this.selectBySql(SELETE_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<BanAn> selectAll() {
       return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<BanAn> selectBySql(String sql, Object... args) {
        List<BanAn> list = new ArrayList<>();
        try {
            ResultSet rs = DataProvider.query(sql, args);
            while (rs.next()) {
                BanAn enity = new BanAn();
                enity.setMaBan(rs.getInt("MaBan"));
                enity.setLoaiBan(rs.getInt("LoaiBan"));
                enity.setTrangThai(rs.getString("TrangThai"));
                list.add(enity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
