package priv.jeffrey.trrs.frontend.utilities;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class MyBox extends Box {
    JLabel label;
    JTextField textField;

    public MyBox(String labelText) {
        super(BoxLayout.X_AXIS);
        label = new JLabel(labelText);
        add(label, Component.LEFT_ALIGNMENT);
        add(Box.createHorizontalStrut(50));
        textField = new JTextField("点击添加" + labelText);
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1)
                    textField.setText("");
            }
        });
        add(textField, Component.RIGHT_ALIGNMENT);
    }

}
