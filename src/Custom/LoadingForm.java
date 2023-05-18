/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Custom;

import javax.swing.JInternalFrame;
import javax.swing.JProgressBar;
import javax.swing.Timer;

/**
 *
 * @author Nham Ngo
 */
public class LoadingForm extends JInternalFrame {
    private JProgressBar progressBar;
    private Timer timer;
    private int count = 0;
    private void startProgress() {
        progressBar.setValue(0); // Thiết lập giá trị ban đầu cho progress bar
        timer.start(); // Khởi động timer để tăng giá trị của progress bar trong mỗi chu kỳ
    }

    private void stopProgress() {
        timer.stop(); // Dừng timer
        count = 0; // Đặt lại giá trị của biến count
        progressBar.setValue(0); // Thiết lập giá trị cho progress bar
        setVisible(false); // Ẩn form chuyển đổi
        dispose(); // Đóng form chuyển đổi
    }

    public void run() {
        startProgress(); // Khởi động quá trình chuyển đổi
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        run(); // Gọi phương thức run() để khởi động quá trình chuyển đổi khi form được hiển thị
    }
}
