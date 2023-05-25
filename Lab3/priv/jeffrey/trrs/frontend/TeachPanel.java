package priv.jeffrey.trrs.frontend;

import priv.jeffrey.trrs.frontend.utilities.MyJCheckBox;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class TeachPanel extends JPanel implements ActionListener , ListSelectionListener {
    public final JButton homeButton;
    String[] words= { "quick", "brown", "hungry", "wild"};
    JList<String> wordList;
    public TeachPanel() {

        homeButton = new JButton("Home");

        wordList = new JList<>(words);
        wordList.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                } else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });
        wordList.setCellRenderer(new MyJCheckBox());
        wordList.addListSelectionListener(this);
        add(homeButton);
        add(wordList);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        for(String s : wordList.getSelectedValuesList())
            System.out.println(s);
    }
}
