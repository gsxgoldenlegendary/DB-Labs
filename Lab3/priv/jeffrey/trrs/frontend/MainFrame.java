package priv.jeffrey.trrs.frontend;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainFrame extends JFrame implements ActionListener{
    HomePanel homePanel;
    PublishPanel publishPanel;


    public static void main(String[] args) {
        new MainFrame();
    }

    public MainFrame() {
        super("Teaching & Researching Registration System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        homePanel = new HomePanel();
        getContentPane().add(homePanel, BorderLayout.CENTER);
        homePanel.publishButton.addActionListener(this);

        setSize(960, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Publish")){
            getContentPane().remove(homePanel);
            publishPanel = new PublishPanel();
            getContentPane().add(publishPanel, BorderLayout.CENTER);
            publishPanel.homeButton.addActionListener(this);
            SwingUtilities.updateComponentTreeUI(this);
        }else if(e.getActionCommand().equals("Home")){
            getContentPane().removeAll();
            getContentPane().add(homePanel, BorderLayout.CENTER);
            SwingUtilities.updateComponentTreeUI(this);
        }
    }
}
