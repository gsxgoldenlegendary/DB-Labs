package priv.jeffrey.trrs.frontend;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainFrame extends JFrame implements ActionListener{
    HomePanel homePanel;
    PublishPanel publishPanel;
    CommitPanel commitPanel;
    TeachPanel teachPanel;
    QueryPanel queryPanel;

    public MainFrame() {
        super("Teaching & Researching Registration System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        createPanels();
        getContentPane().add(homePanel, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(this);
    }
    public void createPanels(){
    	homePanel = new HomePanel();
        publishPanel = new PublishPanel();
        commitPanel = new CommitPanel();
        teachPanel = new TeachPanel();
        queryPanel = new QueryPanel();
        homePanel.publishButton.addActionListener(this);
        publishPanel.homeButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Publish")){
            getContentPane().remove(homePanel);
            getContentPane().add(publishPanel, BorderLayout.CENTER);
            SwingUtilities.updateComponentTreeUI(this);
        }else if(e.getActionCommand().equals("Home")){
            getContentPane().removeAll();
            getContentPane().add(homePanel, BorderLayout.CENTER);
            SwingUtilities.updateComponentTreeUI(this);
        }
    }
}
