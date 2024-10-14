package Test;

import javax.swing.*;
import java.awt.*;

public class QuanLyDatBan_Ui extends JFrame {

    private final JButton btnThem;
    private final JButton btnXoa;
    private final JButton btnSua;
    private final JTextField txtReserch;

    public QuanLyDatBan_Ui(){
        setTitle("Quan ly dat ban");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());
        btnThem = new JButton("Them");
        btnXoa = new JButton("Xoa");
        btnSua = new JButton("Sua");

        //Phan chuc nang
        JPanel pNorth = new JPanel();
        pNorth.setPreferredSize(new Dimension(450, 50));
        this.add(pNorth);

        JPanel pChucNang = new JPanel();
        pChucNang.setBackground(Color.BLUE);
        //pChucNang.setLayout(new GridLayout());
        pChucNang.add(btnThem);
        pChucNang.add(btnXoa);
        pChucNang.add(btnSua);
        pNorth.add(pChucNang);

        JPanel pResearch = new JPanel();

        pResearch.setPreferredSize(new Dimension(450, 30));
        pResearch.add(new JLabel("Tim kiem:"));
        txtReserch = new JTextField(30);
        pResearch.add(txtReserch);
        pNorth.add(pResearch);








    }

    public static void main(String[] args) {
        new QuanLyDatBan_Ui().setVisible(true);
    }
}
