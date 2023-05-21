package priv.jeffrey.trrs.frontend;

import javax.swing.*;
import java.awt.*;

public class QueryPanel {
}
//    private void createUI(final JFrame frame) {
//        JPanel panel = new JPanel();
//        LayoutManager layout = new FlowLayout();
//        panel.setLayout(layout);
//
//        JButton okButton = new JButton("query");
//        JTextField textField = new JTextField(20);
//        okButton.addActionListener(e -> {
//            JOptionPane.showMessageDialog(frame, Interface.query(textField.getText()));
//        });
//
//
//        JScrollPane scrollPane = new JScrollPane(
//                panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        JButton addButton = new JButton("add");
//        addButton.addActionListener(e -> {
//            scrollPane.add(new JTextField(20));
//            SwingUtilities.updateComponentTreeUI(this);
//        });
//
//
//
//        panel.add(okButton);
//        panel.add(textField);
//        panel.add(addButton);
//        this.add(scrollPane);
//
//        frame.getContentPane().add(panel, BorderLayout.CENTER);
//    }