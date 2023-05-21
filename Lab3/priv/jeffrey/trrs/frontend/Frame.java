package priv.jeffrey.trrs.frontend;

import priv.jeffrey.trrs.main.Interface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Frame {
    public static void main(String[] args) {
        createWindow();
    }

    private static void createWindow() {
        JFrame frame = new JFrame("Teaching & Researching Registration System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createUI(frame);
        frame.setSize(960, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void createUI(final JFrame frame) {
        JPanel panel = new JPanel();
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);

        JButton okButton = new JButton("query");
        JTextField textField = new JTextField(20);
//        JButton cancelButton = new JButton("取消");
//        cancelButton.setEnabled(false);
//        JButton submitButton = new JButton("提交");

        okButton.addActionListener(e -> {
            String id = textField.getText();
            //JOptionPane.showMessageDialog(frame, "点击了`确定`按钮");

            JOptionPane.showMessageDialog(frame, Interface.query(id));
        });

//        submitButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "点击了`提交`按钮"));

        //frame.getRootPane().setDefaultButton(submitButton);

        panel.add(okButton);
        panel.add(textField);
//        panel.add(cancelButton);
//        panel.add(submitButton);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }
}


