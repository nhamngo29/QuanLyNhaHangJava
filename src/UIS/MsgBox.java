/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UIS;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author Nham Ngo
 */
public class MsgBox {
//    /**
//     * Hiển thị thông báo cho người dùng
//     * @param parent là cửa sổ chứa thông báo
//     * @param message là thông báo
//     */
    public static void alert(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, 
                "Hệ thống", JOptionPane.INFORMATION_MESSAGE);
    }
//    /**
//     * Hiển thị thông báo và yêu cầu người dùng xác nhận
//     * @param parent là cửa sổ chứa thông báo
//     * @param message là câu hỏi yes/no
//     * @return là kết quả nhận được true/false
//     */    
    public static boolean confirm(Component parent, String message) {
        int result = JOptionPane.showConfirmDialog(parent, message, 
                "Hệ thống", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }
//    /**
//     * Hiển thị thông báo yêu cầu nhập dữ liệu
//     * @param parent là cửa sổ chứa thông báo
//     * @param message là thông báo nhắc nhở nhập
//     * @return là kết quả nhận được từ người sử dụng nhập vào
//     */    
    public static String prompt(Component parent, String message) {
        return JOptionPane.showInputDialog(parent, message, 
                "Hệ thống", JOptionPane.INFORMATION_MESSAGE);
    }
}
