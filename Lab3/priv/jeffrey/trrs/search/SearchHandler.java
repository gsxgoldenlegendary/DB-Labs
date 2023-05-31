package priv.jeffrey.trrs.search;

import priv.jeffrey.trrs.utilities.DatabaseConnector;

import java.io.*;
import java.sql.SQLException;
import java.util.Vector;

public class SearchHandler extends DatabaseConnector {
    private static final String TEACHER_SEARCH_ROUTINE = "SELECT * FROM teacher WHERE id = ?";
    private static final String TEACH_SEARCH_ROUTINE =
            "SELECT * FROM (teach JOIN course ON teach.course_id = course.id) WHERE teacher_id = ?";
    private static final String PUBLISH_SEARCH_ROUTINE =
            "SELECT * FROM (publish JOIN paper ON publish.paper_id = paper.id) WHERE teacher_id = ?";
    private static final String COMMIT_SEARCH_ROUTINE =
            "SELECT * FROM (commit JOIN project ON commit.project_id = project.id) WHERE teacher_id = ?";
    private static Vector<String> teacherInfoVector;
    private static Vector<Vector<String>> teachInfoVector;
    private static Vector<Vector<String>> publishInfoVector;
    private static Vector<Vector<String>> commitInfoVector;

    public static void main(String[] args) {
        try {
            action("1", 0, 2019);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void output(int startYear, int endYear) {

        try {
            File file = new File("output.md");
            OutputStream outputStream = new FileOutputStream(file, true);
            String title = "# 教师教学科研工作统计（" + startYear + "-" + endYear + "）\n";
            outputStream.write(title.getBytes());
            String teacherInfo = "## 教师基本信息\n" +
                    "| 教师编号 | 教师姓名 | 教师性别 | 教师职称 |\n" +
                    "| :------: | :------: | :------: | :------: |\n" +
                    "| " + teacherInfoVector.get(0) + " | " + teacherInfoVector.get(1) + " | "
                    + teacherInfoVector.get(2) + " | " + teacherInfoVector.get(3) + " |\n";
            outputStream.write(teacherInfo.getBytes());
            String teachInfo = "## 教师授课信息\n" +
                    "| 课程编号 | 课程名称 | 课程总学时 | 课程性质 | 开课年份 | 开课学期 | 教师授课学时 |\n" +
                    "| :------: | :------: | :--------: | :------: | :------: | :------: | :----------: |\n";
            outputStream.write(teachInfo.getBytes());
            for (Vector<String> vector : teachInfoVector) {
                String temp = "| " + vector.get(0) + " | " + vector.get(1) + " | " + vector.get(2) + " | "
                        + vector.get(3) + " | " + vector.get(4) + " | " + vector.get(5) + " | " + vector.get(6) + " |\n";
                outputStream.write(temp.getBytes());
            }
            String publishInfo = "## 教师发表论文信息\n" +
                    "| 论文编号 | 论文名称 | 论文来源 | 发表年份 | 论文类型 | 论文级别 | 作者排名 | 是否为通讯作者 |\n" +
                    "| :------: | :------: | :------: | :------: | :------: | :------: | :------: | :--------------: " +
                    "|\n";
            outputStream.write(publishInfo.getBytes());
            for (Vector<String> vector : publishInfoVector) {
                String temp = "| " + vector.get(0) + " | " + vector.get(1) + " | " + vector.get(2) + " | "
                        + vector.get(3) + " | " + vector.get(4) + " | " + vector.get(5) + " | " + vector.get(6)
                        + " | " + vector.get(7) + " |\n";
                outputStream.write(temp.getBytes());
            }
            String commitInfo = "## 教师承担项目信息\n" +
                    "| 项目编号 | 项目名称 | 项目来源 | 项目级别 | 开始时间 | 结束时间 | 总经费 | 承担经费 |\n" +
                    "| :------: | :------: | :------: | :------: | :------: | :------: | :------: | :----------: |\n";
            outputStream.write(commitInfo.getBytes());
            for (Vector<String> vector : commitInfoVector) {
                String temp = "| " + vector.get(0) + " | " + vector.get(1) + " | " + vector.get(2) + " | "
                        + vector.get(3) + " | " + vector.get(4) + " | " + vector.get(5) + " | " + vector.get(6)
                        + " | " + vector.get(7) + " |\n";
                outputStream.write(temp.getBytes());
            }
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void action(
            String teacherId,
            int startYear,
            int endYear
    ) throws SQLException {
        try {
            setConnection();
            preparedStatement = connection.prepareStatement(TEACHER_SEARCH_ROUTINE);
            preparedStatement.setString(1, teacherId);
            resultSet = preparedStatement.executeQuery();
            teacherInfoVector = new Vector<>();
            while (resultSet.next()) {
                teacherInfoVector.add(resultSet.getString("id"));
                teacherInfoVector.add(resultSet.getString("name"));
                teacherInfoVector.add(resultSet.getString("gender"));
                teacherInfoVector.add(resultSet.getString("title"));
            }
            preparedStatement.close();
            preparedStatement = connection.prepareStatement(TEACH_SEARCH_ROUTINE);
            preparedStatement.setString(1, teacherId);
            resultSet = preparedStatement.executeQuery();
            teachInfoVector = new Vector<>();
            while (resultSet.next()) {
                Vector<String> temp = new Vector<>();
                temp.add(resultSet.getString("course_id"));
                temp.add(resultSet.getString("name"));
                temp.add(resultSet.getString("hours"));
                temp.add(resultSet.getString("property"));
                temp.add(resultSet.getString("year"));
                temp.add(resultSet.getString("semester"));
                temp.add(resultSet.getString("commit_hours"));
                if (Integer.parseInt(temp.get(3)) >= startYear && Integer.parseInt(temp.get(3)) <= endYear)
                    teachInfoVector.add(temp);
            }
            preparedStatement.close();
            preparedStatement = connection.prepareStatement(PUBLISH_SEARCH_ROUTINE);
            preparedStatement.setString(1, teacherId);
            resultSet = preparedStatement.executeQuery();
            publishInfoVector = new Vector<>();
            while (resultSet.next()) {
                Vector<String> temp = new Vector<>();
                temp.add(resultSet.getString("paper_id"));
                temp.add(resultSet.getString("title"));
                temp.add(resultSet.getString("source"));
                temp.add(resultSet.getString("year"));
                temp.add(resultSet.getString("type"));
                temp.add(resultSet.getString("level"));
                temp.add(resultSet.getString("ranking"));
                temp.add(resultSet.getString("is_corresponding_author"));
                if (Integer.parseInt(temp.get(3)) >= startYear && Integer.parseInt(temp.get(3)) <= endYear)
                    publishInfoVector.add(temp);
            }
            preparedStatement.close();
            preparedStatement = connection.prepareStatement(COMMIT_SEARCH_ROUTINE);
            preparedStatement.setString(1, teacherId);
            resultSet = preparedStatement.executeQuery();
            commitInfoVector = new Vector<>();
            while (resultSet.next()) {
                Vector<String> temp = new Vector<>();
                temp.add(resultSet.getString("project_id"));
                temp.add(resultSet.getString("name"));
                temp.add(resultSet.getString("source"));
                temp.add(resultSet.getString("type"));
                temp.add(resultSet.getString("funding"));
                temp.add(resultSet.getString("start_year"));
                temp.add(resultSet.getString("end_year"));
                temp.add(resultSet.getString("commit_funding"));
                if (Integer.parseInt(temp.get(6)) >= startYear || Integer.parseInt(temp.get(5)) <= endYear)
                    commitInfoVector.add(temp);
            }
            preparedStatement.close();
            output(startYear, endYear);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }
}
