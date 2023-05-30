package priv.jeffrey.trrs.frontend.utilities;

import priv.jeffrey.trrs.backend.DBConnector;

import javax.swing.*;

public interface Action {
    void addActionPerformed(JPanel panel, DBConnector connector);
    void updateActionPerformed(JPanel panel, DBConnector connector);
    void deleteActionPerformed(JPanel panel, DBConnector connector);
    void searchActionPerformed(JPanel panel, DBConnector connector);
}
