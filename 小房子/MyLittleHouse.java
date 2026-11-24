import java.awt.*;
import javax.swing.*;

public class MyLittleHouse extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1. 天空與草地：用 fillRect 把畫面上半部塗成天藍色、下半部塗成綠色
        g.setColor(new Color(135, 206, 235)); // 天藍色
        g.fillRect(0, 0, 500, 260);
        g.setColor(new Color(106, 176, 76));  // 草地綠色
        g.fillRect(0, 260, 500, 140);

        // 2. 太陽：用 fillOval 畫黃色正圓，並用 drawLine 畫至少 8 條光芒
        int sunX = 70, sunY = 60, sunSize = 50;
        int centerX = sunX + sunSize / 2;
        int centerY = sunY + sunSize / 2;

        g.setColor(Color.YELLOW);
        g.fillOval(sunX, sunY, sunSize, sunSize);

        // 使用 for 迴圈搭配 Math.cos、Math.sin 畫出 12 條光芒
        int rays = 12;
        int innerR = 30;
        int outerR = 45;
        for (int i = 0; i < rays; i++) {
            double angle = 2 * Math.PI * i / rays;
            int x1 = (int) (centerX + innerR * Math.cos(angle));
            int y1 = (int) (centerY + innerR * Math.sin(angle));
            int x2 = (int) (centerX + outerR * Math.cos(angle));
            int y2 = (int) (centerY + outerR * Math.sin(angle));
            g.drawLine(x1, y1, x2, y2);
        }

        // 3. 雲朵：用至少 3 個互相重疊的白色 fillOval 組成
        g.setColor(Color.WHITE);
        g.fillOval(280, 50, 50, 35);
        g.fillOval(310, 40, 60, 45);
        g.fillOval(350, 50, 50, 35);
        g.fillOval(315, 60, 50, 30);

        // 4. 房屋主體：用 fillRect + drawRect
        g.setColor(new Color(255, 224, 178)); // 牆壁顏色（淺橘/米黃色）
        g.fillRect(160, 180, 180, 130);
        g.setColor(Color.BLACK);
        g.drawRect(160, 180, 180, 130);

        // 5. 屋頂：只能用 drawLine 畫出三角形（三條線段）
        g.setColor(new Color(183, 28, 28)); // 深紅色線條
        g.drawLine(145, 180, 250, 100); // 左斜邊
        g.drawLine(250, 100, 355, 180); // 右斜邊
        g.drawLine(145, 180, 355, 180); // 底部橫線

        // 6. 門：用 fillRoundRect，門把用小的 fillOval
        g.setColor(new Color(121, 85, 72)); // 咖啡色木門
        g.fillRoundRect(230, 235, 40, 75, 10, 10);
        g.setColor(Color.YELLOW);           // 金黃色門把
        g.fillOval(260, 270, 6, 6);

        // 7. 窗戶：至少 2 扇，用 fillRect + drawRect，並用 drawLine 畫十字窗框
        // 左邊窗戶
        drawWindow(g, 180, 205, 35, 35);
        // 右邊窗戶
        drawWindow(g, 285, 205, 35, 35);

        // 8. 用 g.drawString("學號 姓名", x, y) 在畫面角落簽名
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString("A1111428 許祐銓", 360, 385);
    }

    // 輔助方法：繪製單扇窗戶與十字窗框
    private void drawWindow(Graphics g, int x, int y, int width, int height) {
        g.setColor(new Color(224, 247, 250)); // 玻璃淺藍色
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        // 十字窗框
        g.drawLine(x + width / 2, y, x + width / 2, y + height); // 垂直線
        g.drawLine(x, y + height / 2, x + width, y + height / 2); // 水平線
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("回家作業：我的小房子");
        MyLittleHouse panel = new MyLittleHouse();
        
        // 設定畫布大小為 500x400
        panel.setPreferredSize(new Dimension(500, 400));
        
        frame.add(panel);
        frame.pack(); // 自動調整視窗大小以符合 500x400 的畫布
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // 視窗置中顯示
        frame.setVisible(true);
    }
}
