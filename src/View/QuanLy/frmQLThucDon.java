/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package View.QuanLy;

import Custom.MyComboBoxRenderer;
import Custom.MyDialog;
import Custom.MyFileChooser;
import Custom.XuLyFileExcel;
import DAO.LoaiThucDonDAO;
import DAO.ThucDonDAO;
import POJO.LoaiThucDon;
import POJO.ThucDon;
import UIS.MsgBox;
import static java.awt.Desktop.getDesktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Nham Ngo
 */
public class frmQLThucDon extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmQLThucDon
     */
    ThucDonDAO dao;
    LoaiThucDonDAO ltdDao;
    File fileAnhSP;

    //Clear
    void clearForm() {
        txtMaMon.setText("");
        txtTenMon.setText("");
        txtGia.setText("");
        cboLoai.setSelectedIndex(0);

    }

    //Thêm
    void InsertOrUpdate() {
        ThucDon td = getForm();

        dao.insertOrUpdate(td);
        fillToTable(dao.selectAll());
        this.clearForm(); // xóa trắng form
        luuFileAnh();
        MsgBox.alert(this, "Thêm mới,update thành công!");

    }
    //Xóa
    //Sữa

    public frmQLThucDon() {
        initComponents();
        tbMenu.getColumnModel().getColumn(4).setWidth(0);
        tbMenu.getColumnModel().getColumn(4).setMinWidth(0);
        tbMenu.getColumnModel().getColumn(4).setMaxWidth(0);
        cboLoai.setRenderer(new MyComboBoxRenderer("Vui lòng chọn loại đồ ăn"));
        cboLoai.setSelectedIndex(-1);
        Enable(true);
        EnableText(false);
        dao = new ThucDonDAO();
        ltdDao = new LoaiThucDonDAO();
        fillToTable(dao.selectAll());
        loadToCbo();
        selectTable();
    }

    ThucDon getForm() {
        ThucDon td = new ThucDon();
        td.setMaMon(txtMaMon.getText());
        td.setTenMon(txtTenMon.getText());
        td.setGiaTien(Float.parseFloat(txtGia.getText()));
        LoaiThucDon a = (LoaiThucDon) cboLoai.getSelectedItem();
        td.setLoai(a.getMaLoaiTD());
        td.setHinhAnh(String.valueOf(fileAnhSP.getName()));
        return td;
    }

    private ImageIcon getAnhSP(String src) {
        src = src.trim().equals("") ? "default.png" : src;
        //Xử lý ảnh
        BufferedImage img = null;
        File fileImg = new File(src);

        if (!fileImg.exists()) {
            src = "default.png";
            fileImg = new File("image/SanPham/" + src);
        }

        try {
            img = ImageIO.read(fileImg);
            fileAnhSP = new File(src);
        } catch (IOException e) {
            fileAnhSP = new File("imgs/anhthe/avatar.jpg");
        }

        if (img != null) {
            Image dimg = img.getScaledInstance(130, 117, Image.SCALE_SMOOTH);
            return new ImageIcon(dimg);
        }

        return null;
    }

    private void luuFileAnh() {
        BufferedImage bImage = null;
        try {
            File initialImage = new File(fileAnhSP.getPath());
            bImage = ImageIO.read(initialImage);
            ImageIO.write(bImage, "png", new File("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\Img\\" + fileAnhSP.getName()));
        } catch (IOException e) {
            System.out.println("Exception occured :" + e.getMessage());
        }
    }

    private void OpenFile(String file) {
        try {
            File path = new File(file);
            getDesktop().open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void xuLyChonAnh() {
        JFileChooser fileChooser = new MyFileChooser("image/SanPham/");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Tệp hình ảnh", "jpg", "png", "jpeg");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            fileAnhSP = fileChooser.getSelectedFile();
            txtImg.setIcon(getAnhSP(fileAnhSP.getPath()));
        }
    }

    void fillToTable(List<ThucDon> a) {
        DefaultTableModel model = (DefaultTableModel) tbMenu.getModel();
        model.setRowCount(0);
        for (ThucDon p : a) {
            model.addRow(new Object[]{p.getMaMon(), p.getTenMon(), p.getGiaTien(), ltdDao.selectById(p.getLoai()), p.getHinhAnh()});
        }
    }

    void loadToCbo() {
        DefaultComboBoxModel def = (DefaultComboBoxModel) cboLoai.getModel();
        def.removeAllElements();
        try {
            for (LoaiThucDon ls : ltdDao.selectAll()) {

                def.addElement(ls);
            }
        } catch (Exception e) {
        }
    }

    void Enable(Boolean s) {
        btnInsert.setEnabled(s);
        btnUpdate.setEnabled(s);
        btnRemove.setEnabled(s);
        btnSave.setEnabled(!s);
        btnExPortEX.setEnabled(s);
        btnInportEX.setEnabled(s);
    }

    private ImageIcon loadImage(String tenHinh) {

        ImageIcon ii = new ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\Img\\" + tenHinh);
        return ii;
    }

    boolean checkVal() {
        if (txtMaMon.getText().isEmpty() || txtMaMon.getText().equalsIgnoreCase("")) {
            MsgBox.alert(this, "Bạn chưa nhập mã món ăn!");
            return false;
        } else if (txtMaMon.getText().length() > 6) {
            MsgBox.alert(this, "Mã món ăn tối đa chỉ 6 ký tự!");
            return false;
        }
        if (txtTenMon.getText().isEmpty() || txtTenMon.getText().equalsIgnoreCase("")) {
            MsgBox.alert(this, "Bạn chưa nhập tên món ăn!");
            return false;
        }
        if (txtGia.getText().isEmpty() || txtGia.getText().equalsIgnoreCase("")) {
            MsgBox.alert(this, "Bạn chưa nhập giá món ăn!");
            return false;
        }
        try {
            Float.valueOf(txtGia.getText());
        } catch (Exception e) {
            MsgBox.alert(this, "Giá món ăn không đúng định dạng");
            return false;
        }
        if (cboLoai.getSelectedIndex() == 0) {
            MsgBox.alert(this, "Bạn chưa chọn loại món ăn!");
            return false;
        }
        return true;
    }

    void selectTable() {
        tbMenu.setCellSelectionEnabled(true);
        ListSelectionModel select = tbMenu.getSelectionModel();
        select.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = tbMenu.getSelectedRow();
                if (row >= 0) {
                    txtMaMon.setText((String) tbMenu.getValueAt(row, 0));
                    txtTenMon.setText((String) tbMenu.getValueAt(row, 1));
                    txtGia.setText(String.valueOf(tbMenu.getValueAt(row, 2)));
                    LoaiThucDon a = (LoaiThucDon) tbMenu.getValueAt(row, 3);

                    int width = 130; // chiều rộng mong muốn của JLabel
                    int height = 117; // chiều cao mong muốn của JLabel
                    Image img = loadImage(String.valueOf(tbMenu.getValueAt(row, 4))).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    fileAnhSP = new File("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\Img\\" + String.valueOf(tbMenu.getValueAt(row, 4)));
                    txtImg.setIcon(new ImageIcon(img));
                    cboLoai.setSelectedItem(a);
                    cboLoai.setSelectedIndex(a.getMaLoaiTD() - 1);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMaMon = new javax.swing.JTextField();
        txtTenMon = new javax.swing.JTextField();
        txtGia = new javax.swing.JTextField();
        cboLoai = new javax.swing.JComboBox<>();
        pnImage = new javax.swing.JPanel();
        txtImg = new javax.swing.JLabel();
        btnChooseImage = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbMenu = new javax.swing.JTable();
        btnInsert = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnInportEX = new javax.swing.JButton();
        btnExPortEX = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();

        setClosable(true);
        setTitle("Quản lý sản phẩm");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
        jLabel1.setText("QUẢN LÝ THỰC ĐƠN");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Mã món:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Tên món:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Giá thành:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Loại:");

        pnImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout pnImageLayout = new javax.swing.GroupLayout(pnImage);
        pnImage.setLayout(pnImageLayout);
        pnImageLayout.setHorizontalGroup(
            pnImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnImageLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtImg, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnImageLayout.setVerticalGroup(
            pnImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtImg, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
        );

        btnChooseImage.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-pictures-folder-20.png")); // NOI18N
        btnChooseImage.setText("Chọn ảnh");
        btnChooseImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseImageActionPerformed(evt);
            }
        });

        jLabel3.setText("Tìm kiếm");

        txtSearch.setPreferredSize(new java.awt.Dimension(104, 27));

        btnSearch.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-magnifying-glass-tilted-left-20.png")); // NOI18N
        btnSearch.setText("Tìm Kiếm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setRowHeaderView(null);

        tbMenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã món", "Tên món", "Giá thành", "Loại", "Title 5"
            }
        ));
        tbMenu.setRowSelectionAllowed(false);
        tbMenu.setShowGrid(true);
        jScrollPane1.setViewportView(tbMenu);

        btnInsert.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-plus-20.png")); // NOI18N
        btnInsert.setText("Thêm");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnRemove.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-cancel-20.png")); // NOI18N
        btnRemove.setText("Xóa");
        btnRemove.setPreferredSize(new java.awt.Dimension(84, 27));
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
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

        btnInportEX.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-import-csv-20.png")); // NOI18N
        btnInportEX.setText("Nhập");
        btnInportEX.setPreferredSize(new java.awt.Dimension(84, 27));
        btnInportEX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInportEXActionPerformed(evt);
            }
        });

        btnExPortEX.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-export-csv-20.png")); // NOI18N
        btnExPortEX.setText("Xuất");
        btnExPortEX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExPortEXActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 888, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(btnInportEX, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)
                                .addComponent(btnExPortEX, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(357, 357, 357)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(btnSearch))
                            .addComponent(jLabel1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(231, 231, 231)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(63, 63, 63)
                                .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(28, 28, 28)
                                    .addComponent(txtGia))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTenMon, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(36, 36, 36)
                                    .addComponent(txtMaMon, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(183, 183, 183)
                                .addComponent(pnImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(198, 198, 198)
                                .addComponent(btnChooseImage)))))
                .addGap(151, 151, 151))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(btnSearch)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChooseImage))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMaMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTenMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsert)
                    .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInportEX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExPortEX)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        if (dao.FIND_ThucDon(String.valueOf("%" + txtSearch.getText() + "%")) == null)
            MsgBox.alert(this, "Không tộn tại thực đơn");
        else
            fillToTable(dao.FIND_ThucDon(String.valueOf("%" + txtSearch.getText() + "%")));
    }//GEN-LAST:event_btnSearchActionPerformed
//    private ThucDon getThucDonByText()
//    {
//        ThucDon a=new ThucDon();
//        
//    }
    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        clearForm();
        Enable(false);
        EnableText(true);
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnChooseImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseImageActionPerformed
        // TODO add your handling code here:
        xuLyChonAnh();
    }//GEN-LAST:event_btnChooseImageActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (btnInportEX.isEnabled()) {
            MyDialog dlg = new MyDialog("Dữ liệu cũ sẽ bị thay dỗi, tiếp tục?", MyDialog.WARNING_DIALOG);
            if (dlg.getAction() != MyDialog.OK_OPTION) {
                return;
            }
            int row = tbMenu.getRowCount();
            for (int i = 0; i < row; i++) {
                String MaMon = tbMenu.getValueAt(i, 0) + "";
                String TenMon = tbMenu.getValueAt(i,1) + "";
                Float GiaThanh = Float.valueOf(tbMenu.getValueAt(i, 2) + "");
                LoaiThucDon at = ltdDao.selectByName(tbMenu.getValueAt(i, 3).toString());
                int Loai = at.getMaLoaiTD();
                String Img = tbMenu.getValueAt(i, 4) + "";
                ThucDon a = new ThucDon(MaMon, TenMon, GiaThanh, Img, Loai);
                if (a != null) {
                    dao.insertOrUpdate(a);
                }
            }
            new MyDialog("Đã lưu vào CSDL thành công!", MyDialog.SUCCESS_DIALOG);
        } else {
            if (!checkVal()) {
                return;
            } else {
                InsertOrUpdate();
                
            }
        }
        Enable(true);
    }//GEN-LAST:event_btnSaveActionPerformed
    void EnableText(Boolean s) {
        txtMaMon.setEditable(s);
        txtGia.setEditable(s);
        txtTenMon.setEditable(s);
        txtGia.setEditable(s);
        btnChooseImage.setEnabled(s);
    }
    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (txtMaMon.getText().isEmpty()) {
            MsgBox.alert(this, "Vui lòng chọn hoặc tìm kiếm món bạn muốn sữa?");
            return;
        } else {
            Enable(false);
            EnableText(true);
            txtMaMon.setEnabled(false);
        }

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        // TODO add your handling code here:
        if (!txtMaMon.getText().isEmpty()) {
            if (MsgBox.confirm(this, "Bạn có chắc chắn muốn xóa món " + txtTenMon.getText() + " ra khỏi thức đơn không?")) {
                dao.delete(txtMaMon.getText());
                fillToTable(dao.selectAll());
                this.clearForm(); // xóa trắng form
            }
        } else {
            MsgBox.alert(this, "Vui lòng chọn hoặc tìm kiếm món bạn muốn sữa?");
            return;
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnInportEXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInportEXActionPerformed
        // TODO add your handling code here:
        xuLyNhapFileExcel();
        EnableText(false);
        Enable(false);
        btnInportEX.setEnabled(true);

    }//GEN-LAST:event_btnInportEXActionPerformed
    private void xuLyXuatFileExcel() {
        XuLyFileExcel xuatFile = new XuLyFileExcel();
        xuatFile.xuatExcel(tbMenu);
    }

    private void xuLyNhapFileExcel() {

        XuLyFileExcel nhapFile = new XuLyFileExcel();
        nhapFile.nhapExcel(tbMenu);

    }
    private void btnExPortEXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExPortEXActionPerformed
//        xuLyXuatFileExcel();        // TODO add your handling code here:
//        try {
//            JFileChooser jFileChooser=new JFileChooser();
//            jFileChooser.showSaveDialog(this);
//            File saveFile=jFileChooser.getSelectedFile();
//            if(saveFile!=null)
//            {
//                saveFile=new File(saveFile.toString()+".xlsx");
//                Workbook wb = new XSSFWorkbook();
//                Sheet sheet=wb.createSheet("Thực đơn");
//                Row rowCol=sheet.createRow(0);
//                for (int i = 0; i < tbMenu.getColumnCount(); i++) {
//                    Cell cell=rowCol.createCell(i);
//                    cell.setCellValue(tbMenu.getColumnName(i));
//                }
//                for(int i=0;i<tbMenu.getRowCount();i++)
//                {
//                    Row row=sheet.createRow(i+1);
//                    for (int j = 0; j < tbMenu.getColumnCount(); j++) {
//                        Cell cell=row.createCell(j);
//                        if(tbMenu.getValueAt(i, j)!=null)
//                        {
//                            cell.setCellValue(tbMenu.getValueAt(i, j).toString());
//                        }
//                    }
//                }
//                FileOutputStream out=new FileOutputStream(new File(saveFile.toString()));
//                wb.write(out);
//                wb.close();
//                out.close();
//                OpenFile(saveFile.toString());
//            }
//            else
//            {
//                MsgBox.alert(this, "Error");
//            }
//        } catch (FileNotFoundException e) {
//        }catch(IOException ex){}
        xuLyXuatFileExcel();
    }//GEN-LAST:event_btnExPortEXActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChooseImage;
    private javax.swing.JButton btnExPortEX;
    private javax.swing.JButton btnInportEX;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboLoai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnImage;
    private javax.swing.JTable tbMenu;
    private javax.swing.JTextField txtGia;
    private javax.swing.JLabel txtImg;
    private javax.swing.JTextField txtMaMon;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTenMon;
    // End of variables declaration//GEN-END:variables
}
