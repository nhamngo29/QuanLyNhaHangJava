/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package View;

import Custom.MyComboBoxRenderer;
import Custom.MyDialog;
import Custom.MyFileChooser;
import Custom.XuLyFileExcel;
import DAO.LoaiThucDonDAO;
import DAO.NhanVienDAO;
import DAO.ThucDonDAO;
import POJO.LoaiThucDon;
import POJO.NhanVien;
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
public class frmNhanVien extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmFood
     */
    NhanVienDAO dao;
    LoaiThucDonDAO ltdDao;
    File fileAnhSP;

    //Clear
    void clearForm() {
        txtMaNV.setText("");
        txtTenNV.setText("");
        grbSex.clearSelection();
        cboChucVu.setSelectedIndex(-1);
        txtSDT.setText("");
        cbActive.setSelected(false);
        txtImg.setIcon(null);
    }

    //Thêm
    void InsertOrUpdate() {
        NhanVien td = getForm();
        dao.insertOrUpdate(td);
        fillToTable(dao.selectAll());
        this.clearForm(); // xóa trắng form
        luuFileAnh();
        MsgBox.alert(this, "Thêm mới,update thành công!");
    }
    //Xóa
    //Sữa

    public frmNhanVien() {
        initComponents();
        tb.getColumnModel().getColumn(6).setWidth(0);
        tb.getColumnModel().getColumn(6).setMinWidth(0);
        tb.getColumnModel().getColumn(6).setMaxWidth(0);
        cboChucVu.setRenderer(new MyComboBoxRenderer("Vui lòng chọn chức vụ"));
        cboChucVu.setSelectedIndex(-1);
        Enable(true);
        EnableText(false);
        dao = new NhanVienDAO();
        ltdDao = new LoaiThucDonDAO();
        fillToTable(dao.selectAll());
        selectTable();
    }

    NhanVien getForm() {
        NhanVien td = new NhanVien();
        td.setMaNV(txtMaNV.getText());
        td.setHoTen(txtTenNV.getText());
        td.setSoDT(txtSDT.getText());
        td.setGioiTinh(rbName.isSelected());
        td.setActive(cbActive.isSelected());
        td.setAvatar(String.valueOf(fileAnhSP.getName()));
        td.setChucVu(cboChucVu.getSelectedItem().toString());
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

    void fillToTable(List<NhanVien> a) {
        DefaultTableModel model = (DefaultTableModel) tb.getModel();
        model.setRowCount(0);
        for (NhanVien p : a) {
            model.addRow(new Object[]{p.getMaNV(), p.getHoTen(), p.getSoDT(), p.getChucVu(), p.isGioiTinh() ? "Nam" : "Nữ", p.isActive(), p.getAvatar()});
        }
    }

    void Enable(Boolean s) {
        btnInsert.setEnabled(s);
        btnUpdate.setEnabled(s);
        btnRemove.setEnabled(s);
        btnSave.setEnabled(!s);
        btnExPortEX.setEnabled(s);
        btnInportEX.setEnabled(s);
        btnResertPass.setEnabled(s);
        btnPhanQuyen.setEnabled(s);
    }

    private ImageIcon loadImage(String tenHinh) {

        ImageIcon ii = new ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\Img\\" + tenHinh);
        return ii;
    }

    boolean checkVal() {
        if (txtMaNV.getText().isEmpty() || txtMaNV.getText().equalsIgnoreCase("")) {
            MsgBox.alert(this, "Bạn chưa nhập mã nhân viên!");
            return false;
        } else if (txtMaNV.getText().length() > 6) {
            MsgBox.alert(this, "Mã nhân viên tối đa chỉ 6 ký tự!");
            return false;
        }
        if (txtTenNV.getText().isEmpty() || txtTenNV.getText().equalsIgnoreCase("")) {
            MsgBox.alert(this, "Bạn chưa nhập tên nhân viên!");
            return false;
        }
        if (!rbName.isSelected() && !rbNu.isSelected()) {
            MsgBox.alert(this, "Bạn chưa chọn giới tính cho nhân viên!");
            return false;
        }
//       
        if (cboChucVu.getSelectedIndex() == -1) {
            MsgBox.alert(this, "Bạn chưa chọn chức vụ!");
            return false;
        }
        if (txtSDT.getText().isEmpty() || txtMaNV.getText().equalsIgnoreCase("")) {
            MsgBox.alert(this, "Bạn chưa nhập sdt cho nhân viên!");
            return false;
        } else if (txtSDT.getText().length() == 9) {
            MsgBox.alert(this, "Số điện thoại chỉ được 10 số!");
            return false;
        }
        if (fileAnhSP == null) {
            MsgBox.alert(this, "Vui lòng chọn hình ảnh cho nhân viên!");
            return false;
        }
        return true;
    }

    void selectTable() {
        tb.setCellSelectionEnabled(true);
        ListSelectionModel select = tb.getSelectionModel();
        select.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = tb.getSelectedRow();
                if (row >= 0) {
                    txtMaNV.setText((String) tb.getValueAt(row, 0));
                    txtTenNV.setText((String) tb.getValueAt(row, 1));
                    txtSDT.setText(String.valueOf(tb.getValueAt(row, 2)));
                    cboChucVu.setSelectedItem(tb.getValueAt(row, 3));
                    Boolean sex = (String.valueOf(tb.getValueAt(row, 4)) == "Nam") ? true : false;
                    rbName.setSelected(sex);
                    rbNu.setSelected(!sex);
                    cbActive.setSelected(((Boolean) tb.getValueAt(row, 5)).booleanValue());
                    int width = 130; // chiều rộng mong muốn của JLabel
                    int height = 117; // chiều cao mong muốn của JLabel
                    Image img = loadImage(String.valueOf(tb.getValueAt(row, 6))).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    fileAnhSP = new File("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\Img\\" + String.valueOf(tb.getValueAt(row, 4)));
                    txtImg.setIcon(new ImageIcon(img));

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

        grbSex = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtTenNV = new javax.swing.JTextField();
        cboChucVu = new javax.swing.JComboBox<>();
        pnImage = new javax.swing.JPanel();
        txtImg = new javax.swing.JLabel();
        btnChooseImage = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb = new javax.swing.JTable();
        btnInsert = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnInportEX = new javax.swing.JButton();
        btnExPortEX = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        rbName = new javax.swing.JRadioButton();
        rbNu = new javax.swing.JRadioButton();
        cbActive = new javax.swing.JCheckBox();
        btnResertPass = new javax.swing.JButton();
        btnPhanQuyen = new javax.swing.JButton();
        txtSDT = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Quản lý sản phẩm");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
        jLabel1.setText("QUẢN LÝ NHÂN VIÊN");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Mã NV:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Tên NV:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Giới tính:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Chức vụ:");

        cboChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Quản lý", "Lễ Tân", "Nhân Viên Phục Vụ" }));

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

        tb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã NV", "Họ Tên NV", "SDT", "Chức vụ", "Giới tính", "Active", "Avata"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Boolean.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tb.setRowSelectionAllowed(false);
        tb.setShowGrid(true);
        jScrollPane1.setViewportView(tb);

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

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Hiệu lực");

        grbSex.add(rbName);
        rbName.setText("Nam");

        grbSex.add(rbNu);
        rbNu.setText("Nữ");

        cbActive.setText("Hiệu lực");

        btnResertPass.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-password-reset-20.png")); // NOI18N
        btnResertPass.setText("Resert password");
        btnResertPass.setPreferredSize(new java.awt.Dimension(84, 27));
        btnResertPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResertPassActionPerformed(evt);
            }
        });

        btnPhanQuyen.setIcon(new javax.swing.ImageIcon("D:\\Learn\\period 2\\Java\\QuanLyNhaHangg\\src\\Assets\\icons\\icons8-network-20.png")); // NOI18N
        btnPhanQuyen.setText("Quyền");
        btnPhanQuyen.setPreferredSize(new java.awt.Dimension(84, 27));
        btnPhanQuyen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhanQuyenActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("SDT:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(134, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 888, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnResertPass, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(btnInportEX, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)
                                .addComponent(btnExPortEX, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnPhanQuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(120, 120, 120))
            .addGroup(layout.createSequentialGroup()
                .addGap(251, 251, 251)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(btnSearch))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbActive)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                    .addComponent(txtTenNV, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                    .addComponent(cboChucVu, 0, 220, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rbName)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rbNu))
                                    .addComponent(txtSDT))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(183, 183, 183)
                                        .addComponent(pnImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(198, 198, 198)
                                        .addComponent(btnChooseImage)))))))
                .addContainerGap(270, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(btnSearch)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChooseImage)
                        .addGap(53, 53, 53))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(rbName)
                            .addComponent(rbNu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cbActive))
                        .addGap(17, 17, 17)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsert)
                    .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInportEX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExPortEX)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPhanQuyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnResertPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        if (dao.FIND_NhanVien(String.valueOf("%" + txtSearch.getText() + "%")) == null)
            MsgBox.alert(this, "Không tộn tại nhân viên");
        else
            fillToTable(dao.FIND_NhanVien(String.valueOf("%" + txtSearch.getText() + "%")));
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
            int row = tb.getRowCount();
            for (int i = 0; i < row; i++) {
                String maNV = tb.getValueAt(i, 0).toString();
                String tenNV = tb.getValueAt(i,1).toString();
                String SDT = tb.getValueAt(i,2).toString();
                String ChucVu=tb.getValueAt(i, 3).toString();
                Boolean GioiTinh = (String.valueOf(tb.getValueAt(i, 4)) == "Nam") ? true : false;
                Boolean Active=((Boolean) tb.getValueAt(i, 5)).booleanValue();
                NhanVien a=new NhanVien(maNV, tenNV, SDT, ChucVu, GioiTinh, fileAnhSP.getName(), Active);
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
        btnChooseImage.setEnabled(s);
        txtMaNV.setEditable(s);
        rbName.setEnabled(s);
        rbNu.setEnabled(s);
        cboChucVu.setEnabled(s);
        txtTenNV.setEditable(s);
        txtSDT.setEnabled(s);
        cbActive.setEnabled(s);
        btnChooseImage.setEnabled(s);
    }
    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (txtMaNV.getText().isEmpty()) {
            MsgBox.alert(this, "Vui lòng chọn hoặc tìm kiếm món bạn muốn sữa?");
            return;
        } else {
            Enable(false);
            EnableText(true);
            txtMaNV.setEnabled(false);
        }

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
         
        if (!txtMaNV.getText().isEmpty()) {
            if (MsgBox.confirm(this, "Bạn có chắc chắn muốn xóa nhân viên " + txtTenNV.getText() + " ra khỏi danh sách không?")) {
                dao.delete(txtMaNV.getText());
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
        xuatFile.xuatExcel(tb);
    }

    private void xuLyNhapFileExcel() {

        XuLyFileExcel nhapFile = new XuLyFileExcel();
        nhapFile.nhapExcel(tb);

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

    private void btnResertPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResertPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnResertPassActionPerformed

    private void btnPhanQuyenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhanQuyenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPhanQuyenActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChooseImage;
    private javax.swing.JButton btnExPortEX;
    private javax.swing.JButton btnInportEX;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnPhanQuyen;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnResertPass;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JCheckBox cbActive;
    private javax.swing.JComboBox<String> cboChucVu;
    private javax.swing.ButtonGroup grbSex;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnImage;
    private javax.swing.JRadioButton rbName;
    private javax.swing.JRadioButton rbNu;
    private javax.swing.JTable tb;
    private javax.swing.JLabel txtImg;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTenNV;
    // End of variables declaration//GEN-END:variables
}
