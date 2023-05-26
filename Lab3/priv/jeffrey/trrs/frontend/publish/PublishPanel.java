package priv.jeffrey.trrs.frontend.publish;

import javax.swing.*;
import java.awt.*;

public class PublishPanel extends JPanel {
    static JPanel mainPanel;
    static CardLayout cardLayout;
    static HomePanel homePanel;
    static AddPanel addPanel;
    static DeletePanel deletePanel;
    static UpdatePanel updatePanel;

    public PublishPanel() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel();
        mainPanel.setLayout(cardLayout);
        add(mainPanel);
        homePanel = new HomePanel();
        mainPanel.add(homePanel, "Home");
        addPanel = new AddPanel();
        mainPanel.add(addPanel, "Add");
        deletePanel = new DeletePanel();
        mainPanel.add(deletePanel, "Delete");
        updatePanel = new UpdatePanel();
        mainPanel.add(updatePanel, "Update");
        cardLayout.show(mainPanel, "Home");
    }
}
