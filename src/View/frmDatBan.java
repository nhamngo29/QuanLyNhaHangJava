/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package View;

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
import DAO.NhanVienDAO;
import POJO.DatBan;
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
public class frmDatBan extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmFood
     */
    //Xóa
    //Sữa
    DatBanDAO dao;
    NhanVienDAO daoNV;

    public frmDatBan() {
        initComponents();
        dao = new DatBanDAO();
        daoNV=new NhanVienDAO();
        if(dao.selectAll()!=null)
        {
            fillToTable(dao.selectAll());
        }
        Enable(true);
        EnableText(false);
        selectTable();
    }

    DatBan getForm() {
        DatBan td = new DatBan();
        td.setHoTenKH(txtTenKH.getText());
        td.setSDTKH(txtSDT.getText());
        td.setNgayDatBan(txtDataDat.getDate());
        td.setGioDat(txtTGDat.getText());
        td.setSoNguoi(Integer.parseInt(txtSoNguoi.getText()));
        td.setGhiChu(txtGhiChu.getText());
        td.setMaNV(Auth.user.getMaNV());
        td.setMaDatBan(Integer.parseInt(txtMaBan.getText()));
        return td;
    }

    void InsertOrUpdate() {
        DatBan td = getForm();
        System.out.println("MaNV"+td.getMaNV());
        dao.insertOrUpdate(td);
        fillToTable(dao.selectAll());
        this.clearForm(); // xóa trắng form
        MsgBox.alert(this, "Thêm mới,update thành công!");
    }
    void selectTable() {
        tblDatBan.setCellSelectionEnabled(true);
        ListSelectionModel select = tblDatBan.getSelectionModel();
        select.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = tblDatBan.getSelectedRow();
                if (row >= 0) {
                    txtTenKH.setText((String)tblDatBan.getValueAt(row, 1));

                        txtDataDat.setDate((Date) tblDatBan.getModel().getValueAt(row, 3));
                   
                    
                    txtTGDat.setText((String)tblDatBan.getValueAt(row, 4));
                    txtSDT.setText(String.valueOf(tblDatBan.getValueAt(row, 2)));
                    txtMaBan.setText(tblDatBan.getValueAt(row, 0)+"");
                    txtSoNguoi.setText(tblDatBan.getValueAt(row, 5)+"");
                    txtGhiChu.setText((String)tblDatBan.getValueAt(row, 6));
                }
            }
        });
    }
    void clearForm() {
        txtMaBan.setText("");
        txtTenKH.setText("");
        txtSoNguoi.setText("");
        txtSDT.setText("");
        txtTGDat.setText("");
        txtDataDat.cleanup();
    }

    void Enable(Boolean s) {
        btnInsert.setEnabled(s);
        btnUpdate.setEnabled(s);
        btnSave.setEnabled(!s);
    }

    void EnableText(Boolean s) {
        txtMaBan.setEnabled(s);
        txtTenKH.setEnabled(s);
        txtSoNguoi.setEnabled(s);
        txtSDT.setEnabled(s);
        txtTGDat.setEnabled(s);
        txtDataDat.setEnabled(s);
    }

    boolean checkVal() {

        if (txtTenKH.getText().isEmpty() || txtTenKH.getText().equalsIgnoreCase("")) {
            MsgBox.alert(this, "Bạn chưa nhập tên khách hàng!");
            return false;
        }
        if (txtTGDat.getText().isEmpty() || txtTGDat.getText().equalsIgnoreCase("")) {
            MsgBox.alert(this, "Bạn chưa nhập giờ!");
            return false;
        }
        if (txtSDT.getText().isEmpty() || txtSDT.getText().equalsIgnoreCase("")) {
            MsgBox.alert(this, "Bạn chưa nhập giờ gian!");
            return false;
        }
        if (txtSoNguoi.getText().isEmpty() || txtSoNguoi.getText().equalsIgnoreCase("")) {
            MsgBox.alert(this, "Bạn chưa nhập giờ gian!");
            return false;
        }
        if(txtSDT.getText().isEmpty())
        {
             MsgBox.alert(this, "Bạn chưa nhập sdt khách hàng!");
            return false;
        }
        else if(txtSDT.getText().length()==9)
        {
            MsgBox.alert(this, "SDT phải là 9 số");
            return false;
        }
        
        return true;
    }
    private void xuLyXuatFileExcel() {
        XuLyFileExcel xuatFile = new XuLyFileExcel();
        xuatFile.xuatExcel(tblDatBan);
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
        cboNgayDat = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDatBan = new javax.swing.JTable();
        btnExportEx = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDataDat = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        txtTGDat = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtSoNguoi = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtMaBan = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();

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

        cboNgayDat.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cboNgayDat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ngày đặt", "Mới nhất ↓", "Cũ nhất  ↑" }));
        cboNgayDat.setBorder(null);
        cboNgayDat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboNgayDatItemStateChanged(evt);
            }
        });
        cboNgayDat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNgayDatActionPerformed(evt);
            }
        });

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách đặt bàn"));
        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane2MouseClicked(evt);
            }
        });

        tblDatBan.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tblDatBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã đặt bàn", "Tên Khách Hàng", "SDT", "Ngày Đặt", "Giờ Đặt", "Số người", "Ghi chú", "Nhân viên"
            }
        ));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int x=0;x<8;x++){
            tblDatBan.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
        }

        tblDatBan.setRowHeight(30);

        tblDatBan.getColumnModel().getColumn(0).setPreferredWidth(3);
        tblDatBan.getColumnModel().getColumn(3).setPreferredWidth(3);
        tblDatBan.getColumnModel().getColumn(5).setPreferredWidth(3);
        tblDatBan.getColumnModel().getColumn(7).setPreferredWidth(3);
        tblDatBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatBanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDatBan);

        btnExportEx.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-export-csv-20.png")); // NOI18N
        btnExportEx.setText("Xuất ex");
        btnExportEx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportExActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1107, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cboNgayDat, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnExportEx, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboNgayDat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExportEx, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Danh sách", jPanel2);

        jLabel2.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-customer-30.png")); // NOI18N

        txtTenKH.setText("Tên khách hàng");

        jLabel3.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-calendar-26-30.png")); // NOI18N

        txtDataDat.setDateFormatString("dd-MM-yyyy");

        jLabel4.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-time-30.png")); // NOI18N

        txtTGDat.setText("Giờ đặt");

        jLabel5.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-phone-30.png")); // NOI18N

        txtSDT.setText("Số điện thoại");

        jLabel6.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-code-30.png")); // NOI18N

        txtSoNguoi.setText("Số người");

        jLabel7.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-users-30.png")); // NOI18N

        txtMaBan.setText("Mã bàn");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Ghi chú"));

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addContainerGap())
        );

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(129, 129, 129)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDataDat, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(111, 111, 111)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTGDat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(129, 129, 129)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtMaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(110, 110, 110)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtSoNguoi, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(282, 282, 282)
                        .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(163, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataDat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(txtTGDat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(txtSoNguoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsert)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(105, Short.MAX_VALUE))
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

    private void cboNgayDatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNgayDatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboNgayDatActionPerformed
    void fillToTable(List<DatBan> a) {
        DefaultTableModel model = (DefaultTableModel) tblDatBan.getModel();
        model.setRowCount(0);
        for (DatBan p : a) {
            
            model.addRow(new Object[]{p.getMaDatBan(), p.getHoTenKH(), p.getSDTKH(), p.getNgayDatBan(), p.getGioDat(), p.getSoNguoi(), p.getGhiChu(), daoNV.selectById(p.getMaNV())});
            System.out.println(p.getMaNV());
        }
    }
    private void tblDatBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatBanMouseClicked
        // TODO add your handling code here:
        if (SwingUtilities.isRightMouseButton(evt)) {
            pmenu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tblDatBanMouseClicked

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        clearForm();
        Enable(false);
        EnableText(true);
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (txtMaBan.getText().isEmpty()) {
            MsgBox.alert(this, "Vui lòng chọn hoặc tìm kiếm max đặt bàn bạn muốn sữa?");
            return;
        } else {
            Enable(false);
            EnableText(true);
            txtMaBan.setEnabled(false);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

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

    private void pmenuRemoveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pmenuRemoveMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_pmenuRemoveMouseClicked

    private void pmenuRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pmenuRemoveActionPerformed
        // TODO add your handling code here:
        int row = tblDatBan.getSelectedRow();
        String TenKH=tblDatBan.getValueAt(row, 1).toString();
        int MaKH=Integer.parseInt(tblDatBan.getValueAt(row, 0).toString());
        if(MsgBox.confirm(this, "Bạn có chắn muốn xóa đắt bàn của khách hàng "+TenKH))
        {
            dao.delete(MaKH);
            fillToTable(dao.selectAll());
        }
    }//GEN-LAST:event_pmenuRemoveActionPerformed

    private void pmenuEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pmenuEditActionPerformed
        // TODO add your handling code here:
        btnUpdate.doClick();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_pmenuEditActionPerformed

    private void cboNgayDatItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboNgayDatItemStateChanged
        // TODO add your handling code here:
        if(evt.getStateChange()==java.awt.event.ItemEvent.SELECTED)
        {
            if(cboNgayDat.getSelectedIndex()==0)
                fillToTable(dao.selectByTang());
            else
                fillToTable(dao.selectByGiam());
        }
        
    }//GEN-LAST:event_cboNgayDatItemStateChanged

    private void btnExportExActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportExActionPerformed
       xuLyXuatFileExcel();
    }//GEN-LAST:event_btnExportExActionPerformed

    private void jScrollPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportEx;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboNgayDat;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPopupMenu pmenu;
    private javax.swing.JMenuItem pmenuEdit;
    private javax.swing.JMenuItem pmenuRemove;
    private javax.swing.JTable tblDatBan;
    private com.toedter.calendar.JDateChooser txtDataDat;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaBan;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSoNguoi;
    private javax.swing.JTextField txtTGDat;
    private javax.swing.JTextField txtTenKH;
    // End of variables declaration//GEN-END:variables
}
