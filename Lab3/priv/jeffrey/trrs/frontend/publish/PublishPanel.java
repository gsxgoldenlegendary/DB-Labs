package priv.jeffrey.trrs.frontend.publish;

import javax.swing.*;
import java.awt.*;

public class PublishPanel extends JPanel {
    public static JPanel mainPanel;
    public static CardLayout cardLayout;
    public static HomePanel homePanel;
    public static AddPanel addPanel;

    public PublishPanel() {

        cardLayout = new CardLayout();
        mainPanel = new JPanel();
        mainPanel.setLayout(cardLayout);
        add(mainPanel);
        homePanel = new HomePanel();
        mainPanel.add(homePanel, "Home");
        addPanel = new AddPanel();
        mainPanel.add(addPanel, "Add");
        cardLayout.show(mainPanel, "Home");
    }
}
