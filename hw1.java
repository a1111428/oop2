import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class hw1 extends JFrame {
    private JTextField t1;
    private JPasswordField t2;

    public hw1() {
        setTitle("登入");
        setSize(300, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 點擊關閉按鈕時完全結束程式
        setLocationRelativeTo(null); // 視窗顯示於螢幕中央

        // 使用 3x2 的網格版面配置
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel l1 = new JLabel("帳號:", SwingConstants.CENTER);
        t1 = new JTextField();
        JLabel l2 = new JLabel("密碼:", SwingConstants.CENTER);
        t2 = new JPasswordField(); // 密碼欄位使用遮罩

        JButton btn = new JButton("登入");

        // 將元件加入視窗中
        add(l1);
        add(t1);
        add(l2);
        add(t2);
        add(new JLabel("")); // 佔位空組件以維持排版
        add(btn);

        // 按鈕事件處理
        btn.addActionListener((ActionEvent e) -> {
            String username = t1.getText();
            String password = new String(t2.getPassword());

            // 使用 .equals() 正確比較字串內容
            if ("admin".equals(username) && "123456".equals(password)) {
                JOptionPane.showMessageDialog(this, "登入成功！");
                System.out.println("登入成功");
            } else {
                JOptionPane.showMessageDialog(this, "帳號或密碼錯誤", "錯誤", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 放在最後確保所有元件均已加入後再顯示視窗
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new hw1());
    }
}