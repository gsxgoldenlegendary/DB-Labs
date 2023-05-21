package priv.jeffrey.trrs.frontend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class HomePanel extends JPanel implements ActionListener{
    JButton publishButton;
    JButton commitButton;
    JButton teachButton;
    JButton queryButton;

    public HomePanel() {
        publishButton = new JButton("Publish");
        commitButton = new JButton("Commit");
        teachButton = new JButton("Teach");
        queryButton = new JButton("Query");

        add(publishButton);
        add(commitButton);
        add(teachButton);
        add(queryButton);

        publishButton.addActionListener(this);
        commitButton.addActionListener(this);
        teachButton.addActionListener(this);
        queryButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
