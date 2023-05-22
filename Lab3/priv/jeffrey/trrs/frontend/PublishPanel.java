package priv.jeffrey.trrs.frontend;

import priv.jeffrey.trrs.backend.PublishHandle;
import priv.jeffrey.trrs.backend.trans.PaperLevel;
import priv.jeffrey.trrs.backend.trans.PaperType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Objects;

public class PublishPanel extends JPanel implements ActionListener {
    JButton homeButton;
    JTextField teacherIdTextField;
    JTextField teacherRankingTextField;
    JCheckBox correspondingAuthorCheckBox;
    JTextField paperIdTextField;
    JTextField paperTitleTextField;
    JTextField paperSourceTextField;
    JTextField paperYearTextField;
    JComboBox<PaperType> paperTypeComboBox;
    JComboBox<PaperLevel> paperLevelComboBox;
    JButton confirmButton;

    public PublishPanel() {

        homeButton = new JButton("Home");

        teacherIdTextField = new JTextField("Teacher ID");
        teacherRankingTextField = new JTextField("Teacher Ranking");
        correspondingAuthorCheckBox = new JCheckBox("Corresponding Author");
        paperIdTextField = new JTextField("Paper ID");
        paperTitleTextField = new JTextField("Paper Title");
        paperSourceTextField = new JTextField("Paper Source");
        paperYearTextField = new JTextField("Paper Year");
        paperTypeComboBox = new JComboBox<>(PaperType.values());
        paperLevelComboBox = new JComboBox<>(PaperLevel.values());
        confirmButton = new JButton("Confirm");

        add(homeButton);

        add(teacherIdTextField);
        add(teacherRankingTextField);
        add(correspondingAuthorCheckBox);
        add(paperIdTextField);
        add(paperTitleTextField);
        add(paperSourceTextField);
        add(paperYearTextField);
        add(paperTypeComboBox);
        add(paperLevelComboBox);
        add(confirmButton);

        confirmButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Confirm")) {
            System.out.println("Confirm");
            try {
                String teacherId = teacherIdTextField.getText();
                int teacherRanking = Integer.parseInt(teacherRankingTextField.getText());
                int correspondingAuthor = correspondingAuthorCheckBox.isSelected() ? 1 : 0;
                String paperId = paperIdTextField.getText();
                String paperTitle = paperTitleTextField.getText();
                String paperSource = paperSourceTextField.getText();
                int paperYear = Integer.parseInt(paperYearTextField.getText());
                int paperType = paperTypeComboBox.getItemCount();
                int paperLevel = paperLevelComboBox.getItemCount();
                new PublishHandle().operate(teacherId, teacherRanking, correspondingAuthor, paperId, paperTitle,
                        paperSource, paperYear, paperType, paperLevel);
            } catch (SQLException e_sql) {
                JOptionPane.showMessageDialog(this, e_sql.getMessage());
                e_sql.printStackTrace();
            } catch (NumberFormatException e_nfe) {
                JOptionPane.showMessageDialog(this, "Invalid Input");
            }
        }
    }

}
