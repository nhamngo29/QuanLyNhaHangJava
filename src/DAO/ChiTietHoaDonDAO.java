package DAO;

import POJO.ChiTietHoaDon;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChiTietHoaDonDAO extends NhaHangDAO<ChiTietHoaDon, Integer> {

    String INSERT="{CALL InsertCTHD(?,?,?)}";
    String SELECT_CTHD_BAN="{CALL SP_SelectChiTietHoaDonByIdBan(?)}";
    String SELECT_CTHD_BY_HOADON="{CALL SP_SelectChiTietHoaDonByIdHoaDon(?)}";
    String INSERT_OR_UPDATE_SQL = "{CALL SP_InsertOrUpdateChiTietHoaDon(?,?,?,?)}";
    String DELETE_SQL = "DELETE FROM ChiTietHoaDon WHERE MaHDCT =?";
    String SELECT_ALL_SQL = "SELECT * FROM ChiTietHoaDon";
    String UPATE_TRANG_THAI = "{CALL SP_UpdateTrangThaiByID(?)}";
    String SELETE_BY_SOBAN_SQL = "select MaCTHD,MaHD,TD.maTD, tenTD, soluong, giatien from ChiTietHoaDon CTHD inner join ThucDon TD on TD.MaTD = CTHD.MaTD where TD.MaBan= ?select MaHDCT,CTHD.MaHD,TD.MaMon, TenMon, soluong, giatien\n"
            + " from(( \n"
            + "ChiTietHoaDon CTHD\n"
            + "inner join ThucDon TD on TD.MaMon = CTHD.MaMon )\n"
            + "inner join  HoaDon HD on HD.MaHD = CTHD.MaHD) \n"
            + "where HD.MaBan = ?";
    String SELETE_BY_ID_SQL = "select MaCTHD,CTHD.MaHD,TD.maTD, tenTD, soluong, giatien,ba.GhiChu from ChiTietHoaDon CTHD \n"
            + "inner join ThucDon TD on TD.MaTD = CTHD.MaTD \n"
            + "inner join HoaDon HD on HD.MaHD = CTHD.MaHD \n"
            + "inner join BanAn BA on HD.SoBan = BA.SoBan\n"
            + "where hd.MaHD = ?;";

    @Override
    public void delete(Integer id) {
        try {
            DataProvider.update(DELETE_SQL, id);
        } catch (Exception ex) {
            Logger.getLogger(ChiTietHoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List<ChiTietHoaDon> setlectByHoaDon(int id) {
        
        return this.selectBySql(SELECT_CTHD_BY_HOADON,id);
    }
    public void Insert(ChiTietHoaDon cthd) {
        try {
            DataProvider.update(INSERT, cthd.getMaHD(),cthd.getMaMon(),cthd.getSoLuong());
        } catch (Exception ex) {
            Logger.getLogger(ChiTietHoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public ChiTietHoaDon selectById(Integer id) {
        List<ChiTietHoaDon> list = this.selectBySql(SELETE_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<ChiTietHoaDon> selectByHD(Integer id) {
        List<ChiTietHoaDon> list = this.selectBySql(SELETE_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public List<ChiTietHoaDon> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<ChiTietHoaDon> selectBySoban(Integer MaBan) {
        System.out.println("số bàn" + MaBan);
        return this.selectBySql(SELETE_BY_SOBAN_SQL, MaBan);
    }

    public List<ChiTietHoaDon> getchiTietHoaDonbySoBan(Integer MaBan) {

        return this.selectBySql(SELECT_CTHD_BAN, MaBan);
    }
    public void UpdateTrangThai(int id) {
        try {
            DataProvider.update(UPATE_TRANG_THAI, id);
        } catch (Exception ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    protected List<ChiTietHoaDon> selectBySql(String sql, Object... args) {
        List<ChiTietHoaDon> list = new ArrayList<ChiTietHoaDon>();
        try {
            ResultSet rs = DataProvider.query(sql, args);
            while (rs.next()) {
                ChiTietHoaDon enity = new ChiTietHoaDon();
                enity.setMaCTHD(rs.getInt("MaHDCT"));
                enity.setMaMon(rs.getString("MaMon"));
                enity.setMaHD(rs.getInt("MaHD"));
                enity.setSoLuong(rs.getInt("SoLuong"));
                enity.setTenMon(rs.getString("TenMon"));
                enity.setGiatien(rs.getDouble("GiaTien"));
                enity.setMaBan(rs.getInt("MaBan"));
                list.add(enity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void insertOrUpdate(ChiTietHoaDon entity) {
        try {
            DataProvider.update(INSERT_OR_UPDATE_SQL, entity.getMaCTHD(), entity.getMaHD(), entity.getMaMon(), entity.getSoLuong());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Lỗi:" + ex);
        }
    }

}
