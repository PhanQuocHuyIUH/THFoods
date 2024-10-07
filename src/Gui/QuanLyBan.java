package Gui;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.ImageIcon;

public class QuanLyBan extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private static int lastTableNumber = 16; // Lưu lại số bàn cuối cùng vừa tạo

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    QuanLyBan frame = new QuanLyBan();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public QuanLyBan() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1030, 611);
        contentPane = new JPanel();
        contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Quản lý bàn");
        lblNewLabel.setBounds(474, 0, 148, 37);
        lblNewLabel.setForeground(new Color(0, 0, 255));
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 25));
        contentPane.add(lblNewLabel);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setBounds(10, 45, 612, 518);
        contentPane.add(panel);
        panel.setLayout(null);

        //su dung vong lap de tao cac button bàn
        for (int i = 1; i <= lastTableNumber; i++) {
            JButton btnTable = new JButton("Bàn " + i);
            btnTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
            btnTable.setBackground(new Color(2, 134, 2));
            btnTable.setForeground(Color.BLACK);
            btnTable.setBounds(10 + ((i - 1) % 6) * 98, 11 + ((i - 1) / 6) * 100, 80, 80);
            panel.add(btnTable);
        }


        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setBounds(632, 45, 372, 518);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Số bàn:");
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 18));
        lblNewLabel_1.setBounds(10, 87, 80, 18);
        panel_1.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Số ghế:");
        lblNewLabel_1_1.setFont(new Font("Arial", Font.BOLD, 18));
        lblNewLabel_1_1.setBounds(10, 128, 80, 20);
        panel_1.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("Mã bàn:");
        lblNewLabel_1_2.setFont(new Font("Arial", Font.BOLD, 18));
        lblNewLabel_1_2.setBounds(10, 44, 80, 18);
        panel_1.add(lblNewLabel_1_2);

        textField = new JTextField();
        textField.setBounds(91, 45, 263, 20);
        panel_1.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(91, 87, 263, 20);
        panel_1.add(textField_1);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(91, 130, 263, 20);
        panel_1.add(textField_2);

        JButton btnAdd = new JButton("Thêm");
        btnAdd.setIcon(new ImageIcon("D:\\HK1_2024_2025_Nam3\\PhatTrienUngDung\\THFOODS\\icon\\thêm16.png"));
        btnAdd.setFont(new Font("Arial", Font.BOLD, 18));
        btnAdd.setBounds(10, 211, 111, 33);
        panel_1.add(btnAdd);

        JButton btnDelete = new JButton("Xóa");
        btnDelete.setIcon(new ImageIcon("D:\\HK1_2024_2025_Nam3\\PhatTrienUngDung\\THFOODS\\icon\\xóa16.png"));
        btnDelete.setFont(new Font("Arial", Font.BOLD, 18));
        btnDelete.setBounds(131, 211, 111, 33);
        panel_1.add(btnDelete);

        JButton btnUpdate = new JButton("Sửa");
        btnUpdate.setIcon(new ImageIcon("D:\\HK1_2024_2025_Nam3\\PhatTrienUngDung\\THFOODS\\icon\\fix.png"));
        btnUpdate.setFont(new Font("Arial", Font.BOLD, 18));
        btnUpdate.setBounds(252, 211, 102, 33);
        panel_1.add(btnUpdate);
    }
}
