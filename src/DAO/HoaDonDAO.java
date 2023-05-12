/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.HoaDon;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nham Ngo
 */
public class HoaDonDAO extends NhaHangDAO<HoaDon, Integer>{
    String INSERT_SQL = "INSERT INTO HoaDon(NgayTao, MaNV, MaBan, TongTien,TrangThai)VALUES(?,?,?,?,?)";
    String DELETE_SQL = "DELETE FROM HoaDon WHERE MaHD =?";
    String SELECT_ALL_SQL = "SELECT * FROM HoaDon";
    String SELETE_BY_ID_SQL = "SELECT * FROM HoaDon WHERE MaHD =?";
    String select_Poc_TrangThai = "{CALL sp_NVbyHD(?)}";
    String select_Poc_TrangThai_MABan = "{CALL sp_NVSoBanbyHD(?,?)}";
    String select_Poc_3 = "{CALL sp_SoSanhNgay(?)}";
    String UPDATE_TT = "UPDATE HoaDon SET TrangThai=?, TongTien =? where MaHD = ?";
    String Chart = "select sum(tongtien) as 'tongTien'\n"
            + "from hoadon\n"
            + "where month(ngaytao)= ? and YEAR(NgayTao) = ?";
    String FIND_BY_SQL = "{CALL SP_XuatHoaDonTheoNgay (? , ?) }";
    public List<HoaDon> FIND_TongTien(Date TuNgay,Date DenNgay){
         List<HoaDon> list =  this.selectBySql(FIND_BY_SQL, TuNgay, DenNgay);
        if (list.isEmpty()) {
            
            return null;
        }
        return list;
    }

    @Override
    public void insertOrUpdate(HoaDon entity) {
        try {
            DataProvider.update(INSERT_SQL, entity.getNgayTao(), entity.getMaNV(), entity.getMaBan(), entity.getTongTien(), entity.getTrangThai());
        } catch (Exception ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            DataProvider.update(DELETE_SQL, id);
        } catch (Exception ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public HoaDon selectById(Integer id) {
        List<HoaDon> list = this.selectBySql(SELETE_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoaDon> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<HoaDon> selectValueChar(int Thang, String Nam) {
        return this.selectBySqlChart(Chart, Thang, Nam);
    }

    public List<HoaDon> selectAllProc(String TrangThai) {
        return this.selectBySqll(select_Poc_TrangThai, TrangThai);
    }

    public List<HoaDon> selectAllProcL2(String TrangThai, int MaBan) {
        return this.selectBySql(select_Poc_TrangThai_MABan, TrangThai, MaBan);
    }

    public List<HoaDon> selectAllProcL3(String ngayHT) {
        return this.selectBySql(select_Poc_3, ngayHT);
    }

    
    public List<HoaDon> selectByTang() {
        String sql = "select MaHD, hd.MaNV , HoTen, NgayTao, TongTien, MaBan,TrangThai\n"
                + "from HoaDon HD inner join NHANVIEN NV on HD.MaNV = NV.MaNV order by NgayTao desc";
        return this.selectBySql(sql);
    }

    public List<HoaDon> selectByGiam() {
        String sql = "select MaHD, hd.MaNV , HoTen, NgayTao, TongTien, MaBan,TrangThai\n" +
"from HoaDon HD inner join NHANVIEN NV on HD.MaNV = NV.MaNV order by NgayTao asc";
        return this.selectBySql(sql);
    }

    public void updateTT(HoaDon entity) {
        try {
            DataProvider.update(UPDATE_TT, entity.getTrangThai(), entity.getTongTien(), entity.getMaHD());
        } catch (Exception ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<HoaDon>();
        try {
            ResultSet rs = DataProvider.query(sql, args);
            while (rs.next()) {
                HoaDon enity = new HoaDon();
                enity.setMaHD(rs.getInt("MaHD"));
                enity.setNgayTao(rs.getString("NgayTao"));
                enity.setMaNV(rs.getString("MaNV"));
                enity.setMaBan(rs.getInt("MaBan"));
                enity.setTongTien(rs.getFloat("TongTien"));
                enity.setTrangThai(rs.getString("TrangThai"));
                list.add(enity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected List<HoaDon> selectBySqll(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<HoaDon>();
        try {
            ResultSet rs = DataProvider.query(sql, args);
            while (rs.next()) {
                HoaDon enity = new HoaDon();
                enity.setMaHD(rs.getInt("MaHD"));
                enity.setMaBan(rs.getInt("MaBan"));
                enity.setTongTien(rs.getFloat("TongTien"));
                enity.setMaNV(rs.getString("MaNV"));
                enity.setTenNV(rs.getString("HoTen"));
                enity.setNgayTao(rs.getString("NgayTao")); //xem lại
                enity.setTrangThai(rs.getString("TrangThai"));
                list.add(enity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected List<HoaDon> selectBySqlChart(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<HoaDon>();
        try {
            ResultSet rs = DataProvider.query(sql, args);
            while (rs.next()) {
                HoaDon enity = new HoaDon();

                enity.setTongTien(rs.getFloat("tongTien"));

                list.add(enity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
