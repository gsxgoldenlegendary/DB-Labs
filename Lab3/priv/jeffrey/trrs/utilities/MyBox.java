package priv.jeffrey.trrs.utilities;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class MyBox extends Box {
    public JLabel label;
    public JTextField textField;
    public JComboBox comboBox;

    public MyBox(String labelText) {
        super(BoxLayout.X_AXIS);
        label = new JLabel(labelText);
        add(label);
        add(Box.createHorizontalStrut(50));
        textField = new JTextField("点击添加" + labelText);
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1)
                    textField.setText("");
            }
        });
        add(textField);
    }

    public MyBox(String labelText, JComboBox c) {
        super(BoxLayout.X_AXIS);
        textField = new JTextField("");
        label = new JLabel(labelText);
        add(label, Component.LEFT_ALIGNMENT);
        add(Box.createHorizontalStrut(50));
        comboBox = c;
        add(comboBox, Component.RIGHT_ALIGNMENT);
    }
}
