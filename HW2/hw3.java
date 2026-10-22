import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DiceSimulator extends JFrame {
    private JLabel infoLabel;      // 上方顯示統計數據的標籤
    private JLabel diceLabel;      // 中央顯示點數的標籤
    private JButton rollButton;    // 下方擲骰子按鈕

    private int count = 0;         // 已擲次數 (N)
    private int sum = 0;           // 總和 (M)
    private Random random;

    public DiceSimulator() {
        // 1. 視窗標題「骰子模擬器」，尺寸 400x320，關閉時結束程式，開啟時置中
        setTitle("骰子模擬器");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 開啟時置中
        setLayout(new BorderLayout(10, 10));

        random = new Random();

        // 2. 視窗上方顯示「已擲 N 次，總和 M，平均 X.XX」
        infoLabel = new JLabel("已擲 0 次，總和 0，平均 0.00", SwingConstants.CENTER);
        infoLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
        add(infoLabel, BorderLayout.NORTH);

        // 3. 中央有一個 JLabel 顯示目前點數，字體 60pt
        diceLabel = new JLabel("-", SwingConstants.CENTER);
        diceLabel.setFont(new Font("Serif", Font.BOLD, 60));
        add(diceLabel, BorderLayout.CENTER);

        // 4. 下方一個「擲骰子」按鈕
        rollButton = new JButton("擲骰子");
        rollButton.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
        add(rollButton, BorderLayout.SOUTH);

        // 按鈕事件處理
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollDice();
            }
        });
    }

    private void rollDice() {
        // 每按一次隨機產生 1-6 的點數並更新顯示
        int diceValue = random.nextInt(6) + 1;
        
        // 更新統計數據
        count++;
        sum += diceValue;
        double average = (double) sum / count;

        // 更新視窗上方文字 (平均值保留兩位小數)
        infoLabel.setText(String.format("已擲 %d 次，總和 %d，平均 %.2f", count, sum, average));

        // 直接更新中央點數顯示
        diceLabel.setText(String.valueOf(diceValue));

        // 點數為 6 時文字變綠色，為 1 時變紅色，其餘黑色
        if (diceValue == 6) {
            diceLabel.setForeground(Color.GREEN);
        } else if (diceValue == 1) {
            diceLabel.setForeground(Color.RED);
        } else {
            diceLabel.setForeground(Color.BLACK);
        }
    }

    public static void main(String[] args) {
        // 使用 SwingUtilities 確保 GUI 在事件調度執行緒中建立
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DiceSimulator().setVisible(true);
            }
        });
    }
}
