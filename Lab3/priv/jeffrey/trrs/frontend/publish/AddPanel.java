package priv.jeffrey.trrs.frontend.publish;

import priv.jeffrey.trrs.backend.PublishHandle;
import priv.jeffrey.trrs.backend.enums.PaperLevel;
import priv.jeffrey.trrs.backend.enums.PaperType;
import priv.jeffrey.trrs.frontend.utilities.MyJCheckBox;
import priv.jeffrey.trrs.frontend.utilities.MyJComboBox;
import priv.jeffrey.trrs.frontend.utilities.MyJTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class AddPanel extends JPanel implements ActionListener {
    Box mainBox;
    public JLabel teacherIdLabel;
    JTextField teacherIdTextField;
    Box teacherIdBox;
    public JLabel teacherRankingLabel;
    JTextField teacherRankingTextField;
    Box teacherRankingBox;
    public JLabel correspondingAuthorLabel;
    JCheckBox correspondingAuthorCheckBox;
    Box correspondingAuthorBox;
    public JLabel paperIdLabel;
    JTextField paperIdTextField;
    Box paperIdBox;
    public JLabel paperTitleLabel;
    JTextField paperTitleTextField;
    Box paperTitleBox;
    public JLabel paperSourceLabel;
    JTextField paperSourceTextField;
    Box paperSourceBox;
    public JLabel paperYearLabel;
    JTextField paperYearTextField;
    Box paperYearBox;
    public JLabel paperTypeLabel;
    JComboBox<PaperType> paperTypeComboBox;
    Box paperTypeBox;
    public JLabel paperLevelLabel;
    JComboBox<PaperLevel> paperLevelComboBox;
    Box paperLevelBox;
    public JButton backButton;
    JButton addButton;
    private String teacherId;
    private int teacherRanking;
    private int correspondingAuthor;
    private int paperId;
    private String paperTitle;
    private String paperSource;
    private int paperYear;
    private int paperType;
    private int paperLevel;

    public AddPanel() {
        mainBox = Box.createVerticalBox();
        add(mainBox);
        backButton = new JButton();
        backButton.setText("返回上一页面");
        backButton.addActionListener(this);
        mainBox.add(backButton);

        createTeacherIdComponent();
        createTeacherRankingComponent();
        createCorrespondingAuthorComponent();
        createPaperIdComponent();
        createPaperTitleComponent();
        createPaperSourceComponent();
        createPaperYearComponent();
        createPaperTypeComponent();
        createPaperLevelComponent();

        addButton = new JButton();
        addButton.setText("添加");
        addButton.addActionListener(this);
        mainBox.add(addButton);
    }
    private void createCorrespondingAuthorComponent() {
        correspondingAuthorLabel = new JLabel();
        correspondingAuthorLabel.setText("是否为通讯作者：");
        correspondingAuthorCheckBox = new JCheckBox();

        correspondingAuthorBox = Box.createHorizontalBox();
        correspondingAuthorBox.add(correspondingAuthorLabel);
        correspondingAuthorBox.add(Box.createHorizontalStrut(150));
        correspondingAuthorBox.add(correspondingAuthorCheckBox);

        mainBox.add(correspondingAuthorBox);
    }
    private void createPaperLevelComponent() {
        paperLevelLabel = new JLabel();
        paperLevelLabel.setText("论文级别：");
        paperLevelComboBox = new JComboBox<>();
        paperLevelComboBox.setModel(new DefaultComboBoxModel<>(PaperLevel.values()));

        paperLevelBox = Box.createHorizontalBox();
        paperLevelBox.add(paperLevelLabel);
        paperLevelBox.add(Box.createHorizontalStrut(150));
        paperLevelBox.add(paperLevelComboBox);

        mainBox.add(paperLevelBox);
    }

    private void createPaperTypeComponent() {
        paperTypeLabel = new JLabel();
        paperTypeLabel.setText("论文类型：");
        paperTypeComboBox = new JComboBox<>();
        paperTypeComboBox.setModel(new DefaultComboBoxModel<>(PaperType.values()));

        paperTypeBox = Box.createHorizontalBox();
        paperTypeBox.add(paperTypeLabel);
        paperTypeBox.add(Box.createHorizontalStrut(150));
        paperTypeBox.add(paperTypeComboBox);

        mainBox.add(paperTypeBox);
    }
    private void createPaperYearComponent() {
        paperYearLabel = new JLabel();
        paperYearLabel.setText("发表年份：");
        paperYearTextField = new JTextField();

        paperYearBox = Box.createHorizontalBox();
        paperYearBox.add(paperYearLabel);
        paperYearBox.add(Box.createHorizontalStrut(150));
        paperYearBox.add(paperYearTextField);

        mainBox.add(paperYearBox);
    }

    private void createPaperSourceComponent() {
        paperSourceLabel = new JLabel();
        paperSourceLabel.setText("论文来源：");
        paperSourceTextField = new JTextField();

        paperSourceBox = Box.createHorizontalBox();
        paperSourceBox.add(paperSourceLabel);
        paperSourceBox.add(Box.createHorizontalStrut(150));
        paperSourceBox.add(paperSourceTextField);

        mainBox.add(paperSourceBox);
    }

    private void createPaperTitleComponent() {
        paperTitleLabel = new JLabel();
        paperTitleLabel.setText("论文标题：");
        paperTitleTextField = new JTextField();

        paperTitleBox = Box.createHorizontalBox();
        paperTitleBox.add(paperTitleLabel);
        paperTitleBox.add(Box.createHorizontalStrut(150));
        paperTitleBox.add(paperTitleTextField);

        mainBox.add(paperTitleBox);
    }

    private void createPaperIdComponent() {
        paperIdLabel = new JLabel();
        paperIdLabel.setText("论文序号：");
        paperIdTextField = new JTextField();

        paperIdBox = Box.createHorizontalBox();
        paperIdBox.add(paperIdLabel);
        paperIdBox.add(Box.createHorizontalStrut(150));
        paperIdBox.add(paperIdTextField);

        mainBox.add(paperIdBox);
    }

    private void createTeacherRankingComponent() {
        teacherRankingLabel = new JLabel();
        teacherRankingLabel.setText("作者排名：");
        teacherRankingTextField = new JTextField();

        teacherRankingBox = Box.createHorizontalBox();
        teacherRankingBox.add(teacherRankingLabel);
        teacherRankingBox.add(Box.createHorizontalStrut(150));
        teacherRankingBox.add(teacherRankingTextField);

        mainBox.add(teacherRankingBox);
    }

    private void createTeacherIdComponent() {
        teacherIdLabel = new JLabel();
        teacherIdLabel.setText("教师工号：");
        teacherIdTextField = new JTextField();

        teacherIdBox = Box.createHorizontalBox();
        teacherIdBox.add(teacherIdLabel);
        teacherIdBox.add(Box.createHorizontalStrut(150));
        teacherIdBox.add(teacherIdTextField);

        mainBox.add(teacherIdBox);
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

//    private void deleteActionPerformed() {
//        try {
//            getTeacherId();
//            getPaperId();
//            PublishHandle.operateDelete(teacherId, paperId);
//            JOptionPane.showMessageDialog(this, "Publication Deleted");
//        } catch (IllegalArgumentException e_iae) {
//            e_iae.printStackTrace();
//        } catch (SQLException e_sql) {
//            JOptionPane.showMessageDialog(this, e_sql.getMessage());
//            e_sql.printStackTrace();
//        }
//    }
//
//    private void updateActionPerformed() {
//        try {
//            getTeacherId();
//            getPaperId();
//            PublishHandle.operateUpdate(teacherId, teacherRanking, correspondingAuthor, paperId, paperTitle,
//                    paperSource, paperYear, paperType, paperLevel);
//            JOptionPane.showMessageDialog(this, "Publication Updated");
//        } catch (IllegalArgumentException e_iae) {
//            e_iae.printStackTrace();
//        } catch (SQLException e_sql) {
//            JOptionPane.showMessageDialog(this, e_sql.getMessage());
//            e_sql.printStackTrace();
//        }
//    }
//
//    private void queryActionPerformed() {
//        try{
//            getTeacherId();
//            Vector<Vector<String>>queryResult=PublishHandle.operateQuery(teacherId);
//            new JDialog(this,"Query Result",true);
//        } catch (IllegalArgumentException e_iae) {
//            e_iae.printStackTrace();
//        } catch (SQLException e_sql) {
//            JOptionPane.showMessageDialog(this, e_sql.getMessage());
//            e_sql.printStackTrace();
//        }
//    }

    private void getTeacherId() throws IllegalArgumentException {
        teacherId = teacherIdTextField.getText();
        if (teacherId.length() > 5 || teacherId.length() < 1) {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(backButton)) {
            PublishPanel.cardLayout.show(PublishPanel.mainPanel, "返回上一页面");
        } else if (e.getSource().equals(addButton)){
            addActionPerformed();
        }
    }
}
