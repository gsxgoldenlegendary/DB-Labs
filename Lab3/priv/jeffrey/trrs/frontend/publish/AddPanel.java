package priv.jeffrey.trrs.frontend.publish;

import priv.jeffrey.trrs.backend.enums.PaperLevel;
import priv.jeffrey.trrs.backend.enums.PaperType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static priv.jeffrey.trrs.frontend.publish.AddAction.addActionPerformed;


public class AddPanel extends JPanel implements ActionListener {
    Box mainBox;
    public JLabel teacherIdLabel;
    public static JTextField teacherIdTextField;
    Box teacherIdBox;
    public JLabel teacherRankingLabel;
    public static JTextField teacherRankingTextField;
    Box teacherRankingBox;
    public JLabel correspondingAuthorLabel;
    public static JCheckBox correspondingAuthorCheckBox;
    Box correspondingAuthorBox;
    public JLabel paperIdLabel;
    public static JTextField paperIdTextField;
    Box paperIdBox;
    public JLabel paperTitleLabel;
    public static JTextField paperTitleTextField;
    Box paperTitleBox;
    public JLabel paperSourceLabel;
    public static JTextField paperSourceTextField;
    Box paperSourceBox;
    public JLabel paperYearLabel;
    public static JTextField paperYearTextField;
    Box paperYearBox;
    public JLabel paperTypeLabel;
    public static JComboBox<PaperType> paperTypeComboBox;
    Box paperTypeBox;
    public JLabel paperLevelLabel;
    public static JComboBox<PaperLevel> paperLevelComboBox;
    Box paperLevelBox;
    private static JButton backButton;
    private static JButton addButton;


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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(backButton)) {
            PublishPanel.cardLayout.show(PublishPanel.mainPanel, "Home");
        } else if (e.getSource().equals(addButton)){
            addActionPerformed(this);
        }
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

}
