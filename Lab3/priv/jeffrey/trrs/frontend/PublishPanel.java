package priv.jeffrey.trrs.frontend;

import priv.jeffrey.trrs.backend.PublishHandle;
import priv.jeffrey.trrs.backend.enums.PaperLevel;
import priv.jeffrey.trrs.backend.enums.PaperType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public final class PublishPanel extends JPanel implements ActionListener {
    public final JButton homeButton;
    private final JTextField teacherIdTextField;
    private final JTextField teacherRankingTextField;
    private final JCheckBox correspondingAuthorCheckBox;
    private final JTextField paperIdTextField;
    private final JTextField paperTitleTextField;
    private final JTextField paperSourceTextField;
    private final JTextField paperYearTextField;
    private final JComboBox<PaperType> paperTypeComboBox;
    private final JComboBox<PaperLevel> paperLevelComboBox;
    private final JButton addButton;
    private final JButton deleteButton;
    private final JButton updateButton;
    private final JButton queryButton;
    private String teacherId;
    private int teacherRanking;
    private int correspondingAuthor;
    private int paperId;
    private String paperTitle;
    private String paperSource;
    private int paperYear;
    private int paperType;
    private int paperLevel;

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
        addButton = new JButton("Add Publication");
        deleteButton = new JButton("Delete Publication");
        updateButton = new JButton("Update Publication");
        queryButton = new JButton("Query Publication");

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
        add(addButton);
        add(deleteButton);
        add(updateButton);
        add(queryButton);

        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        updateButton.addActionListener(this);
        queryButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(addButton)) {
            addActionPerformed();
        } else if (e.getSource().equals(deleteButton)) {
            deleteActionPerformed();
        } else if (e.getSource().equals(updateButton)) {
            updateActionPerformed();
        } else if (e.getSource().equals(queryButton)) {
            queryActionPerformed();
        }
    }

    private void addActionPerformed() {
        try {
            getTeacherInformation();
            getPaperInformation();
            PublishHandle.operateAdd(teacherId, teacherRanking, correspondingAuthor, paperId, paperTitle,
                    paperSource, paperYear, paperType, paperLevel);
            JOptionPane.showMessageDialog(this, "Publication Added");
        } catch (IllegalArgumentException e_iae) {
            e_iae.printStackTrace();
        } catch (SQLException e_sql) {
            JOptionPane.showMessageDialog(this, e_sql.getMessage());
            e_sql.printStackTrace();
        }
    }

    private void deleteActionPerformed() {
        try {
            getTeacherId();
            getPaperId();
            PublishHandle.operateDelete(teacherId, paperId);
            JOptionPane.showMessageDialog(this, "Publication Deleted");
        } catch (IllegalArgumentException e_iae) {
            e_iae.printStackTrace();
        } catch (SQLException e_sql) {
            JOptionPane.showMessageDialog(this, e_sql.getMessage());
            e_sql.printStackTrace();
        }
    }

    private void updateActionPerformed() {
        try {
            getTeacherId();
            getPaperId();
            PublishHandle.operateUpdate(teacherId, teacherRanking, correspondingAuthor, paperId, paperTitle,
                    paperSource, paperYear, paperType, paperLevel);
            JOptionPane.showMessageDialog(this, "Publication Updated");
        } catch (IllegalArgumentException e_iae) {
            e_iae.printStackTrace();
        } catch (SQLException e_sql) {
            JOptionPane.showMessageDialog(this, e_sql.getMessage());
            e_sql.printStackTrace();
        }
    }

    private void queryActionPerformed() {
        try{
            getTeacherId();
            PublishHandle.operateQuery(teacherId);
        } catch (IllegalArgumentException e_iae) {
            e_iae.printStackTrace();
        } catch (SQLException e_sql) {
            JOptionPane.showMessageDialog(this, e_sql.getMessage());
            e_sql.printStackTrace();
        }
    }

    private void getTeacherId() throws IllegalArgumentException {
        teacherId = teacherIdTextField.getText();
        if (teacherId.length() > 5) {
            JOptionPane.showMessageDialog(this, "Teacher ID Too Long");
            throw new IllegalArgumentException("Teacher ID Too Long");
        }
    }

    private void getTeacherRanking() throws IllegalArgumentException {
        try {
            teacherRanking = Integer.parseInt(teacherRankingTextField.getText());
        } catch (NumberFormatException e_nfe) {
            JOptionPane.showMessageDialog(this, "Invalid Author Ranking");
            throw new IllegalArgumentException("Invalid Author Ranking");
        }
    }

    private void getTeacherInformation() throws IllegalArgumentException {
        getTeacherId();
        getTeacherRanking();
    }

    private void getPaperId() throws IllegalArgumentException {
        try {
            paperId = Integer.parseInt(paperIdTextField.getText());
        } catch (NumberFormatException e_nfe) {
            JOptionPane.showMessageDialog(this, "Paper ID Not A Number");
            throw new IllegalArgumentException("Paper ID Not A Number");
        }
    }

    private void getPaperTitle() throws IllegalArgumentException {
        paperTitle = paperTitleTextField.getText();
        if (paperTitle.length() > 255) {
            JOptionPane.showMessageDialog(this, "Paper Title Too Long");
            throw new IllegalArgumentException("Paper Title Too Long");
        }
    }

    private void getPaperSource() throws IllegalArgumentException {
        paperSource = paperSourceTextField.getText();
        if (paperSource.length() > 255) {
            JOptionPane.showMessageDialog(this, "Paper Source Too Long");
            throw new IllegalArgumentException("Paper Source Too Long");
        }
    }

    private void getPaperYear() throws IllegalArgumentException {
        try {
            paperYear = Integer.parseInt(paperYearTextField.getText());
            if (paperYear < 0 || paperYear > 2023) {
                JOptionPane.showMessageDialog(this, "Paper Year Too Small Or Too Large");
                throw new IllegalArgumentException("Paper Year Too Small Or Too Large");
            }
        } catch (NumberFormatException e_nfe) {
            JOptionPane.showMessageDialog(this, "Paper Year Not A Number");
            throw new IllegalArgumentException("Paper Year Not A Number");
        }
    }

    private void getPaperInformation() throws IllegalArgumentException {
        getPaperId();
        getPaperTitle();
        getPaperSource();
        getPaperYear();
        paperType = paperTypeComboBox.getItemCount();
        paperLevel = paperLevelComboBox.getItemCount();
        correspondingAuthor = correspondingAuthorCheckBox.isSelected() ? 1 : 0;
    }
}
