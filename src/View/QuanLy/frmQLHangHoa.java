/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package View.QuanLy;

import View.*;
import View.QuanLy.*;
import Custom.MyComboBoxRenderer;
import Custom.MyDialog;
import Custom.MyFileChooser;
import Custom.XuLyFileExcel;
import DAO.DatBanDAO;
import DAO.LoaiThucDonDAO;
import DAO.ThucDonDAO;
import POJO.HoaDon;
import DAO.HoaDonDAO;
import DAO.KhoHangDAO;
import DAO.NhanVienDAO;
import POJO.DatBan;
import POJO.KhoHang;
import POJO.LoaiThucDon;
import POJO.NhanVien;
import POJO.ThucDon;
import UIS.Auth;
import UIS.MsgBox;
import java.awt.Color;
import static java.awt.Desktop.getDesktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Nham Ngo
 */
public class frmQLHangHoa extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmFood
     */
    //Xóa
    //Sữa
    KhoHangDAO dao;
    

    public frmQLHangHoa() {
        initComponents();
        dao = new KhoHangDAO();
        
        if(dao.selectAll()!=null)
        {
            fillToTable(dao.selectAll());
        }
        Enable(true);
        EnableText(false);
        selectTable();
        txtTongTien.setEnabled(false);
    }

    KhoHang getForm() {
        KhoHang td = new KhoHang();
        td.setMaHangHoa(txtMaHH.getText());
        td.setTenHangHoa(txtTenHH.getText());
        td.setDonVi(cboDonVi.getSelectedItem().toString());
        td.setNgayNhap(txtNgayNhap.getDate());
        td.setSoLuong(Integer.parseInt(txtSL.getText()));
        td.setChiPhi(Double.parseDouble(txtChiPhi.getText()));
        td.setTongChiPhi(td.getSoLuong()*td.getChiPhi());
        return td;
    }

    void InsertOrUpdate() {
        KhoHang td = getForm();
        dao.insertOrUpdate(td);
        fillToTable(dao.selectAll());
        this.clearForm(); // xóa trắng form
        MsgBox.alert(this, "Thêm mới,update thành công!");
    }
    void selectTable() {
        tb.setCellSelectionEnabled(true);
        ListSelectionModel select = tb.getSelectionModel();
        select.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = tb.getSelectedRow();
                if (row >= 0) {
                    txtMaHH.setText(tb.getValueAt(row, 0).toString());
                    txtTenHH.setText(tb.getValueAt(row, 1).toString());
                    txtNgayNhap.setDate((Date) tb.getModel().getValueAt(row, 2));
                    cboDonVi.setSelectedItem(tb.getValueAt(row, 3));
                    txtSL.setText(tb.getValueAt(row, 4).toString());
                    txtChiPhi.setText(tb.getValueAt(row, 5).toString());
                    txtTongTien.setText(tb.getValueAt(row, 6).toString());
                }
            }
        });
    }
    void clearForm() {
        txtMaHH.setText("");
        txtTenHH.setText("");
        txtChiPhi.setText("");
        txtSL.setText("");
        txtTongTien.setText("");
        txtNgayNhap.cleanup();
        cboDonVi.setSelectedIndex(-1);
    }

    void Enable(Boolean s) {
        btnInsert.setEnabled(s);
        btnUpdate.setEnabled(s);
        btnSave.setEnabled(!s);
    }

    void EnableText(Boolean s) {
        txtMaHH.setEnabled(s);
        txtTenHH.setEnabled(s);
        txtNgayNhap.setEnabled(s);
        txtSL.setEnabled(s);
        cboDonVi.setEnabled(s);
        txtChiPhi.setEnabled(s);
    }

    boolean checkVal() {
        if (txtMaHH.getText().isEmpty() || txtMaHH.getText().equalsIgnoreCase("")) {
            MsgBox.alert(this, "Bạn chưa nhập mã hàng hóa!");
            return false;
        }
        if (txtTenHH.getText().isEmpty() || txtTenHH.getText().equalsIgnoreCase("")) {
            MsgBox.alert(this, "Bạn chưa nhập tên hàng hóa!");
            return false;
        }
        if (txtNgayNhap.getDate()==null) {
            MsgBox.alert(this, "Bạn chưa nhập ngày nhập!");
            return false;
        }
        if (txtSL.getText().isEmpty() || txtSL.getText().equalsIgnoreCase("")) {
            MsgBox.alert(this, "Bạn chưa nhập số lượng!");
            return false;
        }
        if (txtChiPhi.getText().isEmpty() || txtChiPhi.getText().equalsIgnoreCase("")) {
            MsgBox.alert(this, "Bạn chưa nhập chi phí!");
            return false;
        }
        if(cboDonVi.getSelectedIndex()==-1)
        {
             MsgBox.alert(this, "Bạn chưa chọn đơn vị tính");
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pmenu = new javax.swing.JPopupMenu();
        pmenuRemove = new javax.swing.JMenuItem();
        pmenuEdit = new javax.swing.JMenuItem();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        btnExportEx = new javax.swing.JButton();
        btnExportEx1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtTenHH = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNgayNhap = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        txtSL = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtMaHH = new javax.swing.JTextField();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        cboDonVi = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtChiPhi = new javax.swing.JTextField();
        txtTongTien = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        pmenuRemove.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-remove-20.png")); // NOI18N
        pmenuRemove.setText("               Xóa");
        pmenuRemove.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pmenuRemoveMouseClicked(evt);
            }
        });
        pmenuRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pmenuRemoveActionPerformed(evt);
            }
        });
        pmenu.add(pmenuRemove);

        pmenuEdit.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-edit-20.png")); // NOI18N
        pmenuEdit.setText("               Sửa");
        pmenuEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pmenuEditActionPerformed(evt);
            }
        });
        pmenu.add(pmenuEdit);

        setClosable(true);
        setTitle("Quản lý sản phẩm");

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Quảng lý hàng hóa"));

        tb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã hàng hóa", "Tên hàng hóa", "Ngày nhập", "Đơn vị", "Số lượng", "Chi phí", "Tổng chi phí"
            }
        ));
        tb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tb);

        txtSearch.setPreferredSize(new java.awt.Dimension(104, 27));

        jLabel8.setText("Tìm kiếm");

        btnSearch.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-magnifying-glass-tilted-left-20.png")); // NOI18N
        btnSearch.setText("Tìm Kiếm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnExportEx.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-export-csv-20.png")); // NOI18N
        btnExportEx.setText("Xuất ex");
        btnExportEx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportExActionPerformed(evt);
            }
        });

        btnExportEx1.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-export-csv-20.png")); // NOI18N
        btnExportEx1.setText("Xuất hàng hóa");
        btnExportEx1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportEx1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1119, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnSearch)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnExportEx1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExportEx, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(btnSearch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnExportEx1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExportEx, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Danh sách", jPanel2);

        jLabel2.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-customer-30.png")); // NOI18N

        txtTenHH.setText("Tên hàng hóa");

        jLabel3.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-calendar-26-30.png")); // NOI18N

        txtNgayNhap.setDateFormatString("dd-MM-yyyy");

        jLabel5.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-unit-30.png")); // NOI18N

        txtSL.setText("Số lượng");

        jLabel6.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-code-30.png")); // NOI18N

        txtMaHH.setText("Mã hàng hóa");

        btnInsert.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-plus-20.png")); // NOI18N
        btnInsert.setText("Thêm");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-edit-20.png")); // NOI18N
        btnUpdate.setText("Sửa");
        btnUpdate.setPreferredSize(new java.awt.Dimension(84, 27));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-save-20.png")); // NOI18N
        btnSave.setText("Lưu");
        btnSave.setPreferredSize(new java.awt.Dimension(84, 27));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        cboDonVi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thùng", "Cái", "Chiếc", "Két", "Lon", "Gói", "KG" }));

        jLabel9.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-request-money-30.png")); // NOI18N

        jLabel10.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-decline-30.png")); // NOI18N

        txtChiPhi.setText("Chi phí");
        txtChiPhi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtChiPhiKeyReleased(evt);
            }
        });

        txtTongTien.setText("Tổng tiền");

        jLabel11.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-money-bag-30.png")); // NOI18N

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel4.setText("Tên hàng hóa");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel12.setText("Mã hàng hóa");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel13.setText("Ngày nhập");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel14.setText("Đơn vị tính");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel15.setText("Số lượng kiểm kê");

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel16.setText("Chi phí");

        jLabel17.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel17.setText("Tổng chi phí");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(217, 217, 217)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11)
                    .addComponent(jLabel6))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel12))
                        .addGap(71, 71, 71)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                            .addComponent(txtChiPhi, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                            .addComponent(txtSL)
                            .addComponent(cboDonVi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNgayNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTenHH)
                            .addComponent(txtMaHH))
                        .addGap(203, 203, 203))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(txtMaHH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(txtTenHH, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel13)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNgayNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel14)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboDonVi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel10)
                    .addComponent(txtSL, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel9)
                    .addComponent(jLabel16)
                    .addComponent(txtChiPhi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel11))
                .addGap(57, 57, 57)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsert)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(97, 97, 97))
        );

        jTabbedPane1.addTab("Cập nhật", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    void fillToTable(List<KhoHang> a) {
        DefaultTableModel model = (DefaultTableModel) tb.getModel();
        model.setRowCount(0);
        for (KhoHang p : a) {
            
            model.addRow(new Object[]{p.getMaHangHoa(), p.getTenHangHoa(), p.getNgayNhap(), p.getDonVi(), p.getSoLuong(), p.getChiPhi(), p.getTongChiPhi()});
        }
    }
    private void pmenuRemoveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pmenuRemoveMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_pmenuRemoveMouseClicked

    private void pmenuRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pmenuRemoveActionPerformed
        // TODO add your handling code here:
        int row = tb.getSelectedRow();
        String TenHangHoa=tb.getValueAt(row, 1).toString();
        String MaHang=tb.getValueAt(row, 0).toString();
        if(MsgBox.confirm(this, "Bạn có chắn muốn xóa hàng hóa "+TenHangHoa))
        {
            dao.delete(MaHang);
            fillToTable(dao.selectAll());
        }
    }//GEN-LAST:event_pmenuRemoveActionPerformed

    private void pmenuEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pmenuEditActionPerformed
        // TODO add your handling code here:
        btnUpdate.doClick();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_pmenuEditActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (!checkVal()) {
            return;
        } else {
            InsertOrUpdate();

        }
        Enable(true);
        EnableText(false);
        fillToTable(dao.selectAll());
    }//GEN-LAST:event_btnSaveActionPerformed
    private void xuLyXuatFileExcel() {
        XuLyFileExcel xuatFile = new XuLyFileExcel();
        xuatFile.xuatExcel(tb);
    }
    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (txtMaHH.getText().isEmpty()) {
            MsgBox.alert(this, "Vui lòng chọn hoặc tìm kiếm max đặt bàn bạn muốn sữa?");
            return;
        } else {
            Enable(false);
            EnableText(true);
            txtMaHH.setEnabled(false);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        clearForm();
        Enable(false);
        EnableText(true);
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String ten=txtSearch.getText().toString();
        if(ten.isEmpty())
        {
            MsgBox.alert(this, "Vui lòng nhập tên hàng hóa bạn muốn tìm");
        }
        else
        {
            if(dao.SearchByNameHH(ten)==null||dao.SearchByNameHH(ten).size()<=0)
            {
                 MsgBox.alert(this, "không tộn tài hàng hóa bạn muốn tìm");
                 return;
            }
            else
            {
                fillToTable(dao.SearchByNameHH(ten));
            }
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnExportExActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportExActionPerformed
        xuLyXuatFileExcel();
    }//GEN-LAST:event_btnExportExActionPerformed

    private void tbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMouseClicked
        // TODO add your handling code here:
        if (SwingUtilities.isRightMouseButton(evt)) {
            pmenu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tbMouseClicked

    private void txtChiPhiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtChiPhiKeyReleased
        // TODO add your handling code here:
        if(!txtSL.getText().toString().isEmpty())
            txtTongTien.setText((Integer.parseInt((txtSL.getText().toString()))*Double.parseDouble(txtChiPhi.getText().toString()))+"");
    }//GEN-LAST:event_txtChiPhiKeyReleased

    private void btnExportEx1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportEx1ActionPerformed
        // TODO add your handling code here:
        if(!checkVal())
            return;
        Dlg_XuatHangHoa xuatHangHoa=new Dlg_XuatHangHoa(getForm());
        xuatHangHoa.setVisible(true);
        
    }//GEN-LAST:event_btnExportEx1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportEx;
    private javax.swing.JButton btnExportEx1;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboDonVi;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPopupMenu pmenu;
    private javax.swing.JMenuItem pmenuEdit;
    private javax.swing.JMenuItem pmenuRemove;
    private javax.swing.JTable tb;
    private javax.swing.JTextField txtChiPhi;
    private javax.swing.JTextField txtMaHH;
    private com.toedter.calendar.JDateChooser txtNgayNhap;
    private javax.swing.JTextField txtSL;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTenHH;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
