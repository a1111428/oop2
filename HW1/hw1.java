import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Hw1 extends JFrame {

    // 1. 預設驗證資料（常數）
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "123456";

    // 2. 使用具備描述性的變數名稱
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public Hw1() {
        // 基本視窗設定
        setTitle("登入系統");
        setSize(320, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 設定關閉動作
        setLocationRelativeTo(null); // 置中顯示

        // 版面配置
        setLayout(new GridLayout(3, 2, 10, 10));

        // 初始化元件
        JLabel usernameLabel = new JLabel("帳號:", SwingConstants.CENTER);
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("密碼:", SwingConstants.CENTER);
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("登入");

        // 將元件加入容器
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel("")); // 排版用空元件
        add(loginButton);

        // 事件處理
        loginButton.addActionListener(e -> performLogin());

        // 3. 所有元件設置完畢後才顯示視窗
        setVisible(true);
    }

    private void performLogin() {
        String inputUser = usernameField.getText().trim();
        char[] inputPass = passwordField.getPassword();

        // 安全地比較密碼 (將常數轉為 char[] 進行比較)
        boolean isUserCorrect = ADMIN_USER.equals(inputUser);
        boolean isPassCorrect = Arrays.equals(ADMIN_PASS.toCharArray(), inputPass);

        if (isUserCorrect && isPassCorrect) {
            JOptionPane.showMessageDialog(this, "登入成功！", "訊息", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "帳號或密碼錯誤！", "錯誤", JOptionPane.ERROR_MESSAGE);
        }

        // 清理記憶體中的密碼敏感資料
        Arrays.fill(inputPass, '0');
    }

    public static void main(String[] args) {
        // 4. 使用 EDT 線程安全的呼叫方式
        SwingUtilities.invokeLater(Hw1::new);
    }
}
