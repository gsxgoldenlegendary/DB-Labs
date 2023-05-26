package priv.jeffrey.trrs.frontend.publish;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static priv.jeffrey.trrs.frontend.publish.DeleteAction.deleteActionPerformed;
import static priv.jeffrey.trrs.frontend.publish.QueryAction.queryActionPerformed;

public class QueryPanel extends JPanel implements ActionListener {
    Box mainBox;
    public JLabel teacherIdLabel;
    public static JTextField teacherIdTextField;
    Box teacherIdBox;
    private static JButton backButton;
    private static JButton queryButton;
    public QueryPanel() {
        mainBox = Box.createVerticalBox();
        add(mainBox);
        backButton = new JButton();
        backButton.setText("返回上一页面");
        backButton.addActionListener(this);
        mainBox.add(backButton);

        createTeacherIdComponent();

        queryButton = new JButton();
        queryButton.setText("查询");
        queryButton.addActionListener(this);
        mainBox.add(queryButton);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(backButton)) {
            PublishPanel.cardLayout.show(PublishPanel.mainPanel, "Home");
        } else if (e.getSource().equals(queryButton)){
            queryActionPerformed(this);
        }
    }
    private void createTeacherIdComponent() {
        teacherIdLabel = new JLabel();
        teacherIdLabel.setText("教师工号：");
        teacherIdTextField = new JTextField();
        teacherIdTextField.setColumns(20);

        teacherIdBox = Box.createHorizontalBox();
        teacherIdBox.add(teacherIdLabel);
        teacherIdBox.add(Box.createHorizontalStrut(150));
        teacherIdBox.add(teacherIdTextField);

        mainBox.add(teacherIdBox);
    }
}
