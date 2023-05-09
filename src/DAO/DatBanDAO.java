/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.DatBan;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nham Ngo
 */
public class DatBanDAO  extends NhaHangDAO<DatBan, Integer>{
    String INSERT_OR_UPDATE_SQL = "{CALL SP_InsertOrUpdateDatBan(?,?,?,?,?,?,?,?)}";
    String DELETE_SQL = "DELETE FROM DatBan WHERE MaDatBan =?";
    String SELECT_ALL_SQL = "SELECT * FROM DatBan";
    String SELETE_BY_ID_SQL = "SELECT * FROM DatBan WHERE MaDatBan =?";
    @Override
    public void insertOrUpdate(DatBan entity) {
       try {
            DataProvider.update(INSERT_OR_UPDATE_SQL, entity.getMaDatBan(), entity.getHoTenKH(), entity.getSDTKH(), entity.getNgayDatBan(), entity.getGioDat(), entity.getSoNguoi(), entity.getGhiChu(), entity.getMaNV());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Lỗi:" + ex);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            DataProvider.update(DELETE_SQL, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Lỗi:" + ex);
        }
    }

    @Override
    public DatBan selectById(Integer id) {
        List<DatBan> list = this.selectBySql(SELETE_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<DatBan> selectAll() {
         return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<DatBan> selectBySql(String sql, Object... args) {
        List<DatBan> list = new ArrayList<DatBan>();
        try {
            ResultSet rs = DataProvider.query(sql, args);
            while (rs.next()) {
                DatBan entity = new DatBan();
                entity.setMaDatBan(rs.getInt("MaDatBan"));
                entity.setHoTenKH(rs.getString("TenKH"));
                entity.setSDTKH(rs.getString("SDTKH"));
                entity.setNgayDatBan(rs.getDate("NgayDatBan"));
                entity.setGioDat(rs.getString("GioDat"));
                entity.setSoNguoi(rs.getInt("SoNguoi"));
                entity.setGhiChu(rs.getString("GhiChu"));
                entity.setMaNV(rs.getString("MaNV"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<DatBan> selectByTang() {
        String sql = "select * from DATBAN order by NgayDatBan desc";
        return this.selectBySql(sql);
    }

    public List<DatBan> selectByGiam() {
        String sql = "select * from DATBAN order by NgayDatBan asc";
        return this.selectBySql(sql);
    }
}
