package priv.jeffrey.trrs.publish;

import priv.jeffrey.trrs.enums.PaperLevel;
import priv.jeffrey.trrs.enums.PaperType;
import priv.jeffrey.trrs.utilities.SubPanel;
import priv.jeffrey.trrs.utilities.MyBox;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Vector;

public class PublishPanel extends SubPanel implements ActionListener {
    static MyBox paperIdBox;
    static MyBox paperTitleBox;
    static MyBox paperSourceBox;
    static MyBox paperYearBox;
    static MyBox paperTypeBox;
    static MyBox paperLevelBox;
    static MyBox paperCorrespondingAuthorBox;

    public PublishPanel() {
        super(new PublishHandler(), " 排名");
        paperIdBox = new MyBox("论文序号");
        mainBox.add(paperIdBox);
        panelInfoComponentVector.add(paperIdBox.textField);
        paperTitleBox = new MyBox("论文标题");
        mainBox.add(paperTitleBox);
        panelInfoComponentVector.add(paperTitleBox.textField);
        paperSourceBox = new MyBox("论文来源");
        mainBox.add(paperSourceBox);
        panelInfoComponentVector.add(paperSourceBox.textField);
        paperYearBox = new MyBox("发表年份");
        mainBox.add(paperYearBox);
        panelInfoComponentVector.add(paperYearBox.textField);
        paperTypeBox = new MyBox("论文类型", new JComboBox<>(PaperType.values()));
        mainBox.add(paperTypeBox);
        panelInfoComponentVector.add(paperTypeBox.textField);
        paperLevelBox = new MyBox("论文等级", new JComboBox<>(PaperLevel.values()));
        mainBox.add(paperLevelBox);
        panelInfoComponentVector.add(paperLevelBox.textField);
        paperCorrespondingAuthorBox = new MyBox("通讯作者");
        mainBox.add(paperCorrespondingAuthorBox);
        panelInfoComponentVector.add(paperCorrespondingAuthorBox.textField);
    }

    @Override
    protected Vector<Vector<String>> getPanelInfo(boolean isAddUpdate) {
        Vector<Vector<String>> result = new Vector<>();
        Vector<String> paperInfo = new Vector<>();
        String paperId = PublishPanel.paperIdBox.textField.getText().strip();
        if (paperId.length() > 255 || paperId.length() < 1) {
            throw new IllegalArgumentException("论文序号长度不合法。");
        }
        try{
            Integer.parseInt(paperId);
        }catch (NumberFormatException e){
            throw new IllegalArgumentException("论文序号不合法。");
        }
        if (!isAddUpdate) {
            paperInfo.add(paperId);
            result.add(paperInfo);
            return result;
        }
        String paperTitle = PublishPanel.paperTitleBox.textField.getText();
        if (paperTitle.length() > 255 || paperTitle.length() < 1) {
            throw new IllegalArgumentException("论文标题长度不合法。");
        }
        String paperSource = PublishPanel.paperSourceBox.textField.getText();
        if (paperSource.length() > 255 || paperSource.length() < 1) {
            throw new IllegalArgumentException("论文来源长度不合法。");
        }
        int paperYear;
        try {
            paperYear = Integer.parseInt(PublishPanel.paperYearBox.textField.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("发表年份不合法。");
        }
        if (paperYear < 0 || paperYear > 9999) {
            throw new IllegalArgumentException("发表年份不合法。");
        }
        int paperType = PublishPanel.paperTypeBox.comboBox.getSelectedIndex();
        int paperLevel = PublishPanel.paperLevelBox.comboBox.getSelectedIndex();
        String correspondingAuthor = PublishPanel.paperCorrespondingAuthorBox.textField.getText();
        if (correspondingAuthor.length() > 5 || correspondingAuthor.length() < 1) {
            throw new IllegalArgumentException("通讯作者工号长度不合法。");
        }
        paperInfo.add(paperId);
        paperInfo.add(paperTitle);
        paperInfo.add(paperSource);
        paperInfo.add(String.valueOf(paperYear));
        paperInfo.add(String.valueOf(paperType));
        paperInfo.add(String.valueOf(paperLevel));
        paperInfo.add(correspondingAuthor);
        result.add(paperInfo);
        Vector<String> teacherIdList = new Vector<>();
        Vector<String> teacherRankList = new Vector<>();
        boolean flag=false;
        for (int i = 0; i < teacherCount - 1; i++) {
            String teacherId = teacherIdTextFieldVector.get(i).getText();
            if (teacherId.length() > 5 || teacherId.length() < 1) {
                throw new IllegalArgumentException("教师工号长度不合法。");
            }
            teacherIdList.add(teacherId);
            int teacherRank;
            try {
                teacherRank = Integer.parseInt(teacherNo2TextFieldVector.get(i).getText());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("教师作者排名不合法。");
            }
            if (teacherRank < 0) {
                throw new IllegalArgumentException("教师作者排名不合法。");
            }
            if(teacherId.equals(correspondingAuthor)){
                flag=true;
            }
            teacherRankList.add(String.valueOf(teacherRank));
        }
        if(!flag){
            throw new IllegalArgumentException("通讯作者不在填写的教师中。");
        }
        result.add(teacherIdList);
        result.add(teacherRankList);
        return result;
    }

    @Override
    protected void showSearchResult(Vector<Vector<Object>> searchResult) {
        if(searchResult.size() == 0){
            JOptionPane.showMessageDialog(this, "未找到相关论文。", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }else{
            PublishPanel.paperIdBox.textField.setText(searchResult.get(0).get(0).toString());
            PublishPanel.paperTitleBox.textField.setText(searchResult.get(0).get(1).toString());
            PublishPanel.paperSourceBox.textField.setText(searchResult.get(0).get(2).toString());
            PublishPanel.paperYearBox.textField.setText(searchResult.get(0).get(3).toString());
            PublishPanel.paperTypeBox.comboBox.setSelectedIndex(Integer.parseInt(searchResult.get(0).get(4).toString()));
            PublishPanel.paperLevelBox.comboBox.setSelectedIndex(Integer.parseInt(searchResult.get(0).get(5).toString()));
            PublishPanel.paperCorrespondingAuthorBox.textField.setText(searchResult.get(0).get(6).toString());
        }
        while (teacherCount > 1) {
            deleteTeacherComponent();
        }
        for (int i = 1; i < searchResult.size(); i++) {
            createTeacherComponent(fieldNo2);
            teacherIdTextFieldVector.get(i - 1).setText(searchResult.get(i).get(0).toString());
            teacherNo2TextFieldVector.get(i - 1).setText(searchResult.get(i).get(1).toString());
        }
        JOptionPane.showMessageDialog(this,"查询成功！","提示",JOptionPane.INFORMATION_MESSAGE);
    }
}
