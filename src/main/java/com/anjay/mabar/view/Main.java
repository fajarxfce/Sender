package com.anjay.mabar.view;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{
    private JPanel root;
    private JTable table1;
    private JTable table2;
    private JTable table3;
    private JTable table4;
    private JTable table5;
    private JTable table6;
    private JPanel leftPanel;

    public Main(){
        setContentPane(root);

    }

    public static void main(String[] args) {
        Main frame = new Main();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100,1000);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
