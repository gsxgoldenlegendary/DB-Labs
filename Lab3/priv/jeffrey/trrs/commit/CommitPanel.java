package priv.jeffrey.trrs.commit;

import javax.swing.*;
import java.awt.*;

public class CommitPanel extends JPanel {
    static JPanel mainPanel;
    static CardLayout cardLayout;
    static HomePanel homePanel;
    static AddUpdatePanel addUpdatePanel;
    static DeleteQueryPanel deleteQueryPanel;

    public CommitPanel() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel();
        mainPanel.setLayout(cardLayout);
        add(mainPanel);
        homePanel = new HomePanel();
        mainPanel.add(homePanel, "Home");
        addUpdatePanel = new AddUpdatePanel();
        mainPanel.add(addUpdatePanel, "AddUpdate");
        deleteQueryPanel = new DeleteQueryPanel();
        mainPanel.add(deleteQueryPanel, "DeleteQuery");
        cardLayout.show(mainPanel, "Home");
    }
}
