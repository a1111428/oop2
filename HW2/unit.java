import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnitConverterApp extends JFrame {

    // 換算類型
    private JComboBox<String> typeComboBox;

    // 來源與目標單位選單
    private JComboBox<String> sourceUnitComboBox;
    private JComboBox<String> targetUnitComboBox;

    // 輸入與結果文字框
    private JTextField inputTextField;
    private JTextField resultTextField;

    // 按鈕
    private JButton convertButton;

    // 單位選項定義
    private final String[] lengthUnits = {"公尺", "公分", "英吋", "英尺"};
    private final String[] weightUnits = {"公斤", "公克", "磅", "盎司"};
    private final String[] tempUnits = {"攝氏", "華氏", "克氏"};

    // 統一設定較大的字型 (微軟正黑體，Plain, 18px)
    private final Font mainFont = new Font("Microsoft JhengHei", Font.PLAIN, 18);
    private final Font boldFont = new Font("Microsoft JhengHei", Font.BOLD, 18);

    public UnitConverterApp() {
        // 1. 視窗設定 (尺寸 480x280，BorderLayout)
        setTitle("單位換算器");
        setSize(480, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 畫面中央顯示
        setLayout(new BorderLayout(10, 10));

        // ----------------------------------------------------
        // 2. NORTH : JComboBox 選換算類型 (長度 / 重量 / 溫度)
        // ----------------------------------------------------
        JPanel northPanel = new JPanel();
        JLabel typeLabel = new JLabel("換算類型：");
        typeLabel.setFont(boldFont);

        String[] types = {"長度", "重量", "溫度"};
        typeComboBox = new JComboBox<>(types);
        typeComboBox.setFont(mainFont);

        northPanel.add(typeLabel);
        northPanel.add(typeComboBox);
        add(northPanel, BorderLayout.NORTH);

        // ----------------------------------------------------
        // 3. CENTER : 兩列，每列一個 JTextField + 一個 JComboBox
        // ----------------------------------------------------
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        // 第一列：來源（輸入金額/數值 + 來源單位）
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        JLabel label1 = new JLabel("數值：");
        label1.setFont(mainFont);

        inputTextField = new JTextField(8);
        inputTextField.setFont(mainFont);

        sourceUnitComboBox = new JComboBox<>(lengthUnits); // 預設長度單位
        sourceUnitComboBox.setFont(mainFont);

        row1.add(label1);
        row1.add(inputTextField);
        row1.add(sourceUnitComboBox);

        // 第二列：目標（結果顯示 + 目標單位）
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        JLabel label2 = new JLabel("結果：");
        label2.setFont(mainFont);

        resultTextField = new JTextField(8);
        resultTextField.setFont(mainFont);
        resultTextField.setEditable(false); // 結果欄位設為不可編輯

        targetUnitComboBox = new JComboBox<>(lengthUnits); // 預設長度單位
        targetUnitComboBox.setFont(mainFont);

        row2.add(label2);
        row2.add(resultTextField);
        row2.add(targetUnitComboBox);

        centerPanel.add(row1);
        centerPanel.add(row2);
        add(centerPanel, BorderLayout.CENTER);

        // ----------------------------------------------------
        // 4. SOUTH : 「換算」按鈕
        // ----------------------------------------------------
        JPanel southPanel = new JPanel();
        convertButton = new JButton("換算");
        convertButton.setFont(boldFont);
        southPanel.add(convertButton);
        add(southPanel, BorderLayout.SOUTH);

        // ----------------------------------------------------
        // 事件監聽 1：切換類型時更新單位選單選項與清空輸入欄位
        // ----------------------------------------------------
        typeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUnitComboBoxes();
            }
        });

        // ----------------------------------------------------
        // 事件監聽 2：點擊「換算」按鈕計算並輸出結果
        // ----------------------------------------------------
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performConversion();
            }
        });
    }

    // 更新單位下拉選單並清空欄位
    private void updateUnitComboBoxes() {
        String selectedType = (String) typeComboBox.getSelectedItem();

        sourceUnitComboBox.removeAllItems();
        targetUnitComboBox.removeAllItems();

        String[] currentUnits;
        if ("重量".equals(selectedType)) {
            currentUnits = weightUnits;
        } else if ("溫度".equals(selectedType)) {
            currentUnits = tempUnits;
        } else { // 長度
            currentUnits = lengthUnits;
        }

        for (String unit : currentUnits) {
            sourceUnitComboBox.addItem(unit);
            targetUnitComboBox.addItem(unit);
        }

        // 切換換算類型時自動清空輸入框與結果框
        inputTextField.setText("");
        resultTextField.setText("");
    }

    // 執行單位換算邏輯
    private void performConversion() {
        String inputText = inputTextField.getText().trim();
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "請輸入要換算的數值！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(inputText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "請輸入有效的數字！", "錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String type = (String) typeComboBox.getSelectedItem();
        String fromUnit = (String) sourceUnitComboBox.getSelectedItem();
        String toUnit = (String) targetUnitComboBox.getSelectedItem();

        double result = 0;

        if ("長度".equals(type)) {
            result = convertLength(inputValue, fromUnit, toUnit);
        } else if ("重量".equals(type)) {
            result = convertWeight(inputValue, fromUnit, toUnit);
        } else if ("溫度".equals(type)) {
            result = convertTemperature(inputValue, fromUnit, toUnit);
        }

        // 格式化顯示結果 (保留最多4位小數)
        resultTextField.setText(String.format("%.4f", result));
    }

    // 長度換算 (先轉為公尺基準)
    private double convertLength(double val, String from, String to) {
        double meters = 0;
        switch (from) {
            case "公尺": meters = val; break;
            case "公分": meters = val / 100.0; break;
            case "英吋": meters = val * 0.0254; break;
            case "英尺": meters = val * 0.3048; break;
        }

        switch (to) {
            case "公尺": return meters;
            case "公分": return meters * 100.0;
            case "英吋": return meters / 0.0254;
            case "英尺": return meters / 0.3048;
            default: return 0;
        }
    }

    // 重量換算 (先轉為公斤基準)
    private double convertWeight(double val, String from, String to) {
        double kg = 0;
        switch (from) {
            case "公斤": kg = val; break;
            case "公克": kg = val / 1000.0; break;
            case "磅":   kg = val * 0.45359237; break;
            case "盎司": kg = val * 0.028349523125; break;
        }

        switch (to) {
            case "公斤": return kg;
            case "公克": return kg * 1000.0;
            case "磅":   return kg / 0.45359237;
            case "盎司": return kg / 0.028349523125;
            default: return 0;
        }
    }

    // 溫度換算
    private double convertTemperature(double val, String from, String to) {
        double celsius = 0;
        // 先轉為攝氏
        switch (from) {
            case "攝氏": celsius = val; break;
            case "華氏": celsius = (val - 32) * 5 / 9; break;
            case "克氏": celsius = val - 273.15; break;
        }

        // 從攝氏轉為目標單位
        switch (to) {
            case "攝氏": return celsius;
            case "華氏": return (celsius * 9 / 5) + 32;
            case "克氏": return celsius + 273.15;
            default: return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UnitConverterApp().setVisible(true);
        });
    }
}
