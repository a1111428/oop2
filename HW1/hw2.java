import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class hw2 extends JFrame {
    private int count = 0;       // 擲骰次數 N
    private int totalSum = 0;    // 點數總和 M
    private JLabel statusLabel;  // 上方統計訊息
    private JLabel diceLabel;    // 中央點數顯示
    private JButton rollButton;  // 下方擲骰子按鈕
    private Random random;

    public hw2() {
        // 1. 視窗標題與基本設定
        setTitle("骰子模擬器");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 開啟時畫面置中
        setLayout(new BorderLayout());

        random = new Random();

        // 2. 視窗上方統計訊息 JLabel
        statusLabel = new JLabel("已擲 0 次，總和 0，平均 0.00", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        add(statusLabel, BorderLayout.NORTH);

        // 3. 中央點數 JLabel (字體 60pt)
        diceLabel = new JLabel("-", SwingConstants.CENTER);
        diceLabel.setFont(new Font("SansSerif", Font.BOLD, 60));
        add(diceLabel, BorderLayout.CENTER);

        // 4. 下方「擲骰子」按鈕
        rollButton = new JButton("擲骰子");
        rollButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        add(rollButton, BorderLayout.SOUTH);

        // 5. 按鈕點擊事件處理
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 隨機產生 1-6 的點數
                int diceValue = random.nextInt(6) + 1;
                
                count++;
                totalSum += diceValue;
                double average = (double) totalSum / count;

                // 更新中央點數與文字顏色 (6變綠色, 1變紅色, 其餘黑色)
                diceLabel.setText(String.valueOf(diceValue));
                if (diceValue == 6) {
                    diceLabel.setForeground(Color.GREEN);
                } else if (diceValue == 1) {
                    diceLabel.setForeground(Color.RED);
                } else {
                    diceLabel.setForeground(Color.BLACK);
                }

                // 更新上方統計訊息「已擲 N 次，總和 M，平均 X.XX」
                statusLabel.setText(String.format("已擲 %d 次，總和 %d，平均 %.2f", count, totalSum, average));
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new hw2().setVisible(true);
            }
        });
    }
}