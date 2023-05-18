/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package View;

import Custom.MyComboBoxRenderer;
import DAO.BanAnDAO;
import DAO.ChiTietHoaDonDAO;
import DAO.HoaDonDAO;
import DAO.LoaiThucDonDAO;
import DAO.ThucDonDAO;
import POJO.BanAn;
import POJO.ChiTietHoaDon;
import POJO.HoaDon;
import POJO.LoaiThucDon;
import POJO.ThucDon;
import UIS.Auth;
import UIS.MsgBox;
import static View.QuanLy.frmQLHangHoa.tb;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Nham Ngo
 */
public class frmOrder extends javax.swing.JInternalFrame implements ActionListener {

    /**
     * Creates new form frmOrder
     */
    HoaDonDAO dao;
    BanAnDAO daoBanAn;
    LoaiThucDonDAO daoLoaiThucDon;
    ThucDonDAO daoThucDon;
    ChiTietHoaDonDAO daoCTHD;
    static int MaBA = 0;
    private List<JButton> lButton;

    public frmOrder() {

        initComponents();
        cboBanAn.setRenderer(new MyComboBoxRenderer("Vui lòng chọn bàn"));
        cboBanAn.setSelectedIndex(-1);
        dao = new HoaDonDAO();
        daoLoaiThucDon = new LoaiThucDonDAO();
        daoThucDon = new ThucDonDAO();
        daoBanAn = new BanAnDAO();
        daoCTHD = new ChiTietHoaDonDAO();
        cboLoaiThucDon.setRenderer(new MyComboBoxRenderer("Vui lòng chọn loại món ăn"));

        cboLoaiThucDon.setSelectedIndex(-1);
        cboThucDon.setRenderer(new MyComboBoxRenderer("Vui lòng chọn món ăn"));
        cboThucDon.setSelectedIndex(-1);
        SpinnerNumberModel model = new SpinnerNumberModel(1, -100, 100, 1);
        spinSoLuong.setModel(model);
        order();
        loadTable();
        loadLoaiMonAn();
        XuKienSelectcboLoaiThucDon();
        XuKienSelectcboThucDon();
        selectTable();
        tblCacMonDaGoi.getColumnModel().getColumn(4).setWidth(0);
        tblCacMonDaGoi.getColumnModel().getColumn(4).setMinWidth(0);
        tblCacMonDaGoi.getColumnModel().getColumn(4).setMaxWidth(0);
    }

    public void loadLoaiMonAn() {
        DefaultComboBoxModel<LoaiThucDon> def = (DefaultComboBoxModel) cboLoaiThucDon.getModel();
        def.removeAllElements();
        try {
            for (LoaiThucDon ltd : daoLoaiThucDon.selectAll()) {
                def.addElement(ltd);
            }
        } catch (Exception e) {
        }

        cboLoaiThucDon.setSelectedIndex(-1);
    }

    private ImageIcon loadImage(String tenHinh) {

        ImageIcon ii = new ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\Img\\" + tenHinh);
        return ii;
    }

    void XuKienSelectcboLoaiThucDon() {
        cboLoaiThucDon.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    LoaiThucDon selectedItem = (LoaiThucDon) event.getItem();
                    loadMonAnByLoaiMA(selectedItem.getMaLoaiTD());

                }
            }
        });
    }

    void XuKienSelectcboThucDon() {
        cboThucDon.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED && event.getItem() != null) {
                    ThucDon selectedItem = (ThucDon) event.getItem();
                    int width = 130; // chiều rộng mong muốn của JLabel
                    int height = 117; // chiều cao mong muốn của JLabel
                    Image img = loadImage(selectedItem.getHinhAnh()).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    txtImg.setIcon(new ImageIcon(img));

                }
            }
        });
    }

    void selectTable() {

        tblCacMonDaGoi.setCellSelectionEnabled(true);
        ListSelectionModel select = tblCacMonDaGoi.getSelectionModel();
        select.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = tblCacMonDaGoi.getSelectedRow();
                if (row >= 0) {
                    spinSoLuong.setValue(tblCacMonDaGoi.getValueAt(row, 2));
//                   cboThucDon.setSelectedItem();
                    ThucDon select = (ThucDon) tblCacMonDaGoi.getValueAt(row, 0);
                    cboLoaiThucDon.setSelectedItem(daoLoaiThucDon.selectById(select.getLoai()));
                    cboLoaiThucDon.setSelectedIndex(select.getLoai() - 1);
                    cboThucDon.setSelectedItem(select);

                }
            }
        });
    }

    public void loadMonAnByLoaiMA(int id) {
        DefaultComboBoxModel def = (DefaultComboBoxModel) cboThucDon.getModel();
        def.removeAllElements();
        try {
            for (ThucDon ltd : daoThucDon.selectByLoaiMA(id)) {
                def.addElement(ltd);
            }
        } catch (Exception e) {
        }
        cboThucDon.setSelectedIndex(-1);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        pnBanAn = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCacMonDaGoi = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        btnThanhToan = new javax.swing.JButton();
        btnGoiMon = new javax.swing.JButton();
        btnXoaMon = new javax.swing.JButton();
        cboLoaiThucDon = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cboThucDon = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        spinSoLuong = new javax.swing.JSpinner();
        btnGopBan = new javax.swing.JButton();
        btnChuyenBan = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        lblSoBan = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblTrangThaiBan = new javax.swing.JLabel();
        txtImg = new javax.swing.JLabel();
        cboBanAn = new javax.swing.JComboBox<>();

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

        setTitle("Order");
        setToolTipText("");

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bàn ăn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(0, 0, 0))); // NOI18N

        javax.swing.GroupLayout pnBanAnLayout = new javax.swing.GroupLayout(pnBanAn);
        pnBanAn.setLayout(pnBanAnLayout);
        pnBanAnLayout.setHorizontalGroup(
            pnBanAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 509, Short.MAX_VALUE)
        );
        pnBanAnLayout.setVerticalGroup(
            pnBanAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(pnBanAn);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Gọi món", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(0, 0, 0))); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Các món ăn đã gọi"));

        tblCacMonDaGoi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tên món", "Đơn giá", "Số lượng", "Thành tiền", "ID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblCacMonDaGoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblCacMonDaGoiMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblCacMonDaGoi);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
        );

        jLabel1.setText("Tổng tiền:");

        lblTongTien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTongTien.setForeground(new java.awt.Color(204, 0, 0));
        lblTongTien.setText("0 VNĐ");

        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(0, 51, 255));
        btnThanhToan.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-pay-30.png")); // NOI18N
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThanhToanMouseClicked(evt);
            }
        });
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnGoiMon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGoiMon.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-restaurant-30.png")); // NOI18N
        btnGoiMon.setText("Gọi món");
        btnGoiMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoiMonActionPerformed(evt);
            }
        });

        btnXoaMon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoaMon.setText("Sửa đổi");

        cboLoaiThucDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboLoaiThucDon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLoaiThucDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiThucDonActionPerformed(evt);
            }
        });
        cboLoaiThucDon.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cboLoaiThucDonPropertyChange(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Chọn loại:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Chọn món:");

        cboThucDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboThucDon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboThucDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboThucDonMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Số lượng:");

        spinSoLuong.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        spinSoLuong.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        spinSoLuong.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btnGopBan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGopBan.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-merge-horizontal-30.png")); // NOI18N
        btnGopBan.setText("Gộp bàn");
        btnGopBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGopBanActionPerformed(evt);
            }
        });

        btnChuyenBan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnChuyenBan.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-swap-30.png")); // NOI18N
        btnChuyenBan.setText("Chuyển bàn");
        btnChuyenBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChuyenBanActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Số bàn:");

        lblSoBan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSoBan.setForeground(new java.awt.Color(255, 0, 51));
        lblSoBan.setText("0");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Trạng thái:");

        lblTrangThaiBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTrangThaiBan.setText("Sẵn sàng");
        lblTrangThaiBan.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                lblTrangThaiBanPropertyChange(evt);
            }
        });

        txtImg.setForeground(new java.awt.Color(255, 102, 255));

        cboBanAn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTongTien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThanhToan))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblSoBan, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTrangThaiBan))
                            .addComponent(spinSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cboLoaiThucDon, 0, 219, Short.MAX_VALUE)
                                .addComponent(cboThucDon, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtImg, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnGoiMon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoaMon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboBanAn, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(btnChuyenBan, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGopBan)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboLoaiThucDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cboThucDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel5)
                            .addComponent(spinSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblTrangThaiBan)
                            .addComponent(jLabel7)
                            .addComponent(lblSoBan)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoaMon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGoiMon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtImg, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnGopBan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnChuyenBan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboBanAn, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblTongTien)
                    .addComponent(jLabel1)
                    .addComponent(btnThanhToan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGoiMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoiMonActionPerformed
        // TODO add your handling code here:
        if (this.lblSoBan.getText().trim() == "0") {
            MsgBox.alert(this, "Phải chọn bàn để gọi món.!");
            return;
        } else if (cboThucDon.getSelectedIndex() == -1) {
            MsgBox.alert(this, "Vui lòng chọn món ăn.!");
            return;
        }
        goiMon();
    }//GEN-LAST:event_btnGoiMonActionPerformed

    void clear() {
        cboLoaiThucDon.setSelectedIndex(-1);
        cboThucDon.setSelectedIndex(-1);
        cboBanAn.setSelectedIndex(-1);
        
        spinSoLuong.setValue(1);
        lblSoBan.setText(0 + "");
    }

    void goiMon() {
        int idHD;
        int idBan = Integer.parseInt(lblSoBan.getText());
        if (lblTrangThaiBan.getText().equals("Bàn trống")) {
            dao.Insert(Auth.user.getMaNV(), idBan);
            idHD = dao.SelectLastRow().getMaHD();
            lblTrangThaiBan.setText("Bàn có người");
        } else {
            idHD = dao.selectByIDBan(idBan).getMaHD();
        }

        insertMonAn(idHD);
        fillToTable(daoCTHD.getchiTietHoaDonbySoBan(idBan));
        loadTable();
    }

    void insertMonAn(int idHD) {
        ThucDon MonAn = (ThucDon) cboThucDon.getSelectedItem();
        int SL = (int) spinSoLuong.getValue();
        daoCTHD.Insert(new ChiTietHoaDon(MonAn.getMaMon(), idHD, SL));

    }

    void order() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tblCacMonDaGoi.getModel());
        tblCacMonDaGoi.setRowSorter(sorter);
        tblCacMonDaGoi.getTableHeader().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = tblCacMonDaGoi.columnAtPoint(e.getPoint());
                sorter.toggleSortOrder(column);
                sorter.sort();
            }
        });
    }

    void loadTable() {
        DefaultComboBoxModel<BanAn> def = (DefaultComboBoxModel) cboBanAn.getModel();
        def.removeAllElements();

        pnBanAn.removeAll();
        pnBanAn.revalidate();
        pnBanAn.repaint();
        pnBanAn.setLayout(new GridLayout(daoBanAn.selectAll().size() / 3, 3));
        lButton = new ArrayList<>();
        for (BanAn ba : daoBanAn.selectAll()) {
            def.addElement(ba);
            JButton button = new JButton("Bàn " + ba.getMaBan());
            lButton.add(button);
            button.setPreferredSize(new Dimension(40, 40));
            if (ba.getTrangThai().equals("Hoạt Động")) {
                button.setIcon(new ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\ico_trong.png"));
            } else if (ba.getTrangThai().equals("Hư")) {
                button.setIcon(new ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\ico_hu.png"));
                button.setEnabled(false);
            }
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (ba.getTrangThai().equals("Hoạt Động")) {
                        lblTrangThaiBan.setText("Bàn có người");
                    } else if (ba.getTrangThai().equals("Hư")) {
                        lblTrangThaiBan.setText("Bàn hư");
                    } else {
                        lblTrangThaiBan.setText("Bàn trống");
                    }
                    if (e.getSource() == button) {

                        lblSoBan.setText(button.getText().substring(4));
                    } else {
                        button.setBackground(null);
                    }
                }
            });
            cboBanAn.setSelectedIndex(-1);
            button.addActionListener(this);
            pnBanAn.add(button);
        }
    }

    public void actionPerformed(ActionEvent e) {
        // Thiết lập màu nền cho button được click
        for (JButton button : lButton) {
            if (e.getSource() == button) {
                button.setBackground(Color.red);
                int mb = Integer.parseInt(button.getText().substring(4));
                lblSoBan.setText(mb + "");
                lblTongTien.setText(0 + " VND");
                fillToTable(daoCTHD.getchiTietHoaDonbySoBan(mb));

                HoaDon hoadon = dao.selectByIDBan(mb);
                if (hoadon == null) {
                    lblTongTien.setText("0 VND");
                } else {
                    DecimalFormat x = new DecimalFormat("###,###,###");
                    lblTongTien.setText(x.format(hoadon.getTongTien()) + " VND");
                }

            } else {
                button.setBackground(null);
            }

        }
    }

    void fillToTable(List<ChiTietHoaDon> lcthd) {
        DefaultTableModel model = (DefaultTableModel) tblCacMonDaGoi.getModel();
        model.setRowCount(0);
        for (ChiTietHoaDon cthd : lcthd) {
            DecimalFormat x = new DecimalFormat("###,###,### VND");
            model.addRow(new Object[]{daoThucDon.selectById(cthd.getMaMon()), x.format(cthd.getGiatien()), cthd.getSoLuong(), x.format((cthd.getGiatien() * cthd.getSoLuong())), cthd.getMaCTHD()});
        }
    }

    void loadBillByIDBan(int id) {

    }
    private void cboLoaiThucDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiThucDonActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cboLoaiThucDonActionPerformed

    private void cboLoaiThucDonPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cboLoaiThucDonPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_cboLoaiThucDonPropertyChange

    private void cboThucDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboThucDonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cboThucDonMouseClicked

    private void btnChuyenBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChuyenBanActionPerformed
        // TODO add your handling code here:
        if (checkBan()) {
            if (MsgBox.confirm(this, "Bạn chắc chắc muốn chuyên bàn " + lblSoBan.getText() + " không ?")) {
                int tb1 = Integer.parseInt(lblSoBan.getText());
                BanAn a = (BanAn) cboBanAn.getSelectedItem();
                int tb2 = a.getMaBan();
                dao.SwithTable(tb1, tb2);
                loadTable();
                clear();
            }
        }

    }//GEN-LAST:event_btnChuyenBanActionPerformed

    private void pmenuRemoveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pmenuRemoveMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_pmenuRemoveMouseClicked

    private void pmenuRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pmenuRemoveActionPerformed
        // TODO add your handling code here:
        int row = tblCacMonDaGoi.getSelectedRow();
        String TenMon = tblCacMonDaGoi.getValueAt(row, 0).toString();
        int MaHang = Integer.parseInt(tblCacMonDaGoi.getValueAt(row, 4) + "");
        if (MsgBox.confirm(this, "Bạn có chắn muốn xóa " + TenMon + " ra khỏi hóa đơn không ?")) {
            daoCTHD.delete(MaHang);
            fillToTable(daoCTHD.getchiTietHoaDonbySoBan(Integer.parseInt(lblSoBan.getText().toString())));
        }
    }//GEN-LAST:event_pmenuRemoveActionPerformed

    private void pmenuEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pmenuEditActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_pmenuEditActionPerformed

    private void tblCacMonDaGoiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCacMonDaGoiMousePressed
        // TODO add your handling code here:
        if (SwingUtilities.isRightMouseButton(evt))
            pmenu.show(evt.getComponent(), evt.getX(), evt.getY());
    }//GEN-LAST:event_tblCacMonDaGoiMousePressed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        if (!lblSoBan.getText().equals("0") || !lblTrangThaiBan.getText().equals("Bàn trống")) {
            if (MsgBox.confirm(this, "Bạn có chắn chắn xuất hóa đơn cho bàn " + lblSoBan.getText() + " không ?")) {
                System.out.println(lblSoBan.getText());
                HoaDon a = dao.selectByIDBan(Integer.parseInt(lblSoBan.getText()));
                if (a != null) {
                    Dlg_XuatHoaDon frm = new Dlg_XuatHoaDon(a);
                    frm.setLocationRelativeTo(null);
                    frm.pack();
                    frm.getContentPane().setPreferredSize(new Dimension(100, 100));
                    frm.setVisible(true);
                    frm.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            System.out.println("123");
                            loadTable();
                        }
                    });
                }
            }
        } else {
            MsgBox.alert(this, "Bàn không có hóa đơn!");
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void lblTrangThaiBanPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_lblTrangThaiBanPropertyChange
        // TODO add your handling code here:
        if (evt.getPropertyName().equals(""));
    }//GEN-LAST:event_lblTrangThaiBanPropertyChange

    private void btnThanhToanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThanhToanMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_btnThanhToanMouseClicked
    private boolean checkBan() {
        
        BanAn a = (BanAn) cboBanAn.getSelectedItem();
        if(a==null)
        {
            MsgBox.alert(this, "Vui lòng chọn bàn để chuyển");
            return false;
        }
        System.out.println(a.getMaBan() + "");
        if (a.getMaBan() == Integer.parseInt(lblSoBan.getText())) {
            MsgBox.alert(this, "Bàn chuyển,gộp không được trùng nhau");
            return false;
        }
        if (lblTrangThaiBan.getText().equals("Bàn trống")) {
            MsgBox.alert(this, "Vui lòng chọn lại bàn chuyển,gộp khác");
            return false;
        }
        return true;
    }
    private void btnGopBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGopBanActionPerformed
        // TODO add your handling code here:
        if (checkBan()) {
            if (MsgBox.confirm(this, "Bạn chắc chắc muốn gộp bàn " + lblSoBan.getText() + " không ?")) {
                int tb1 = Integer.parseInt(lblSoBan.getText());
                BanAn a = (BanAn) cboBanAn.getSelectedItem();
                int tb2 = a.getMaBan();
                dao.GopTable(tb1, tb2);
                loadTable();
                clear();
            }
        }


    }//GEN-LAST:event_btnGopBanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChuyenBan;
    private javax.swing.JButton btnGoiMon;
    private javax.swing.JButton btnGopBan;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnXoaMon;
    private javax.swing.JComboBox<String> cboBanAn;
    private javax.swing.JComboBox<String> cboLoaiThucDon;
    private javax.swing.JComboBox<String> cboThucDon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblSoBan;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lblTrangThaiBan;
    private javax.swing.JPopupMenu pmenu;
    private javax.swing.JMenuItem pmenuEdit;
    private javax.swing.JMenuItem pmenuRemove;
    private javax.swing.JPanel pnBanAn;
    private javax.swing.JSpinner spinSoLuong;
    private javax.swing.JTable tblCacMonDaGoi;
    private javax.swing.JLabel txtImg;
    // End of variables declaration//GEN-END:variables
}
