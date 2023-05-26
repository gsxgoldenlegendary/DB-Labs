package priv.jeffrey.trrs.frontend.publish;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static priv.jeffrey.trrs.frontend.publish.DeleteAction.deleteActionPerformed;

public class DeletePanel extends JPanel implements ActionListener {
    Box mainBox;
    public JLabel teacherIdLabel;
    public static JTextField teacherIdTextField;
    Box teacherIdBox;
    public JLabel paperIdLabel;
    public static JTextField paperIdTextField;
    Box paperIdBox;
    private static JButton backButton;
    private static JButton deleteButton;

    public DeletePanel() {
        mainBox = Box.createVerticalBox();
        add(mainBox);
        backButton = new JButton();
        backButton.setText("返回上一页面");
        backButton.addActionListener(this);
        mainBox.add(backButton);

        createTeacherIdComponent();
        createPaperIdComponent();

        deleteButton = new JButton();
        deleteButton.setText("删除");
        deleteButton.addActionListener(this);
        mainBox.add(deleteButton);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(backButton)) {
            PublishPanel.cardLayout.show(PublishPanel.mainPanel, "Home");
        } else if (e.getSource().equals(deleteButton)){
           deleteActionPerformed(this);
        }
    }


    private void createPaperIdComponent() {
        paperIdLabel = new JLabel();
        paperIdLabel.setText("论文序号：");
        paperIdTextField = new JTextField();
        paperIdTextField.setColumns(20);

        paperIdBox = Box.createHorizontalBox();
        paperIdBox.add(paperIdLabel);
        paperIdBox.add(Box.createHorizontalStrut(150));
        paperIdBox.add(paperIdTextField);

        mainBox.add(paperIdBox);
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
}
