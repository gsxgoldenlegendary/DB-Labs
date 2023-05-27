package priv.jeffrey.trrs.frontend.commit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static priv.jeffrey.trrs.frontend.commit.Action.*;

public class DeleteQueryPanel extends JPanel implements ActionListener {
    Box mainBox;
    public JLabel projectIdLabel;
    public static JTextField projectIdTextField;
    Box projectIdBox;
    private static JButton backButton;
    private static JButton deleteButton;
    private static JButton queryButton;

    public DeleteQueryPanel() {
        mainBox = Box.createVerticalBox();
        add(mainBox);
        backButton = new JButton();
        backButton.setText("返回上一页面");
        backButton.addActionListener(this);
        mainBox.add(backButton);

        createProjectIdComponent();

        deleteButton = new JButton();
        deleteButton.setText("删除");
        deleteButton.addActionListener(this);
        mainBox.add(deleteButton);
        queryButton = new JButton();
        queryButton.setText("查询");
        queryButton.addActionListener(this);
        mainBox.add(queryButton);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(backButton)) {
            CommitPanel.cardLayout.show(CommitPanel.mainPanel, "Home");
        } else if (e.getSource().equals(deleteButton)){
            deleteActionPerformed(this);
        }else if (e.getSource().equals(queryButton)) {
            queryActionPerformed(this);
        }
    }


    private void createProjectIdComponent() {
        projectIdLabel = new JLabel();
        projectIdLabel.setText("项目号：");
        projectIdTextField = new JTextField();
        projectIdTextField.setColumns(20);

        projectIdBox = Box.createHorizontalBox();
        projectIdBox.add(projectIdLabel);
        projectIdBox.add(Box.createHorizontalStrut(150));
        projectIdBox.add(projectIdTextField);

        mainBox.add(projectIdBox);
    }

}

