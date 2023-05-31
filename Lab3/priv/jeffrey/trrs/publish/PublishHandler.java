package priv.jeffrey.trrs.publish;

import priv.jeffrey.trrs.utilities.DatabaseConnector;

import java.sql.*;
import java.util.Vector;

public class PublishHandler extends DatabaseConnector {
    private static final String PAPER_ADD_ROUTINE = "CALL paperAdd(?,?,?,?,?,?)";
    private static final String PUBLISH_ADD_ROUTINE = "CALL publishAdd(?,?,?,?,?)";
    private static final String PUBLISH_DELETE_ROUTINE = "CALL publishDelete(?)";
    private static final String PAPER_UPDATE_ROUTINE = "CALL paperUpdate(?,?,?,?,?,?)";
    private static final String PUBLISH_UPDATE_ROUTINE = "CALL publishUpdate(?,?,?,?,?)";
    private static final String PAPER_SEARCH_ROUTINE = "SELECT * FROM paper WHERE id = ?";
    private static final String PUBLISH_SEARCH_ROUTINE = "SELECT * FROM publish WHERE publish.paper_id = ?";

    @Override
    public void insert(Vector<Vector<String>> panelInfo) throws SQLException {
        actionAddUpdate(
                Integer.parseInt(panelInfo.get(0).get(0)),
                panelInfo.get(0).get(1),
                panelInfo.get(0).get(2),
                Integer.parseInt(panelInfo.get(0).get(3)),
                Integer.parseInt(panelInfo.get(0).get(4)),
                Integer.parseInt(panelInfo.get(0).get(5)),
                panelInfo.get(0).get(6),
                panelInfo.get(1),
                panelInfo.get(2),
                true
        );
    }

    @Override
    public void update(Vector<Vector<String>> panelInfo) throws SQLException {
        actionAddUpdate(
                Integer.parseInt(panelInfo.get(0).get(0)),
                panelInfo.get(0).get(1),
                panelInfo.get(0).get(2),
                Integer.parseInt(panelInfo.get(0).get(3)),
                Integer.parseInt(panelInfo.get(0).get(4)),
                Integer.parseInt(panelInfo.get(0).get(5)),
                panelInfo.get(0).get(6),
                panelInfo.get(1),
                panelInfo.get(2),
                false
        );
    }
    @Override
    public void delete(Vector<Vector<String>> panelInfo) throws SQLException {
        try {
            setConnection();
            preparedStatement = connection.prepareStatement(PUBLISH_DELETE_ROUTINE);
            preparedStatement.setString(1, panelInfo.get(0).get(0));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }
@Override
    public Vector<Vector<Object>> search(Vector<Vector<String>> panelInfo) throws SQLException {
        Vector<Vector<Object>> result = new Vector<>();
        try {
            setConnection();
            preparedStatement = connection.prepareStatement(PAPER_SEARCH_ROUTINE);
            preparedStatement.setString(1, panelInfo.get(0).get(0));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Vector<Object> temp = new Vector<>();
                temp.add(resultSet.getString("id"));
                temp.add(resultSet.getString("title"));
                temp.add(resultSet.getString("source"));
                temp.add(resultSet.getString("year"));
                temp.add(resultSet.getString("type"));
                temp.add(resultSet.getString("level"));
                result.add(temp);
            }
            preparedStatement.close();
            preparedStatement = connection.prepareStatement(PUBLISH_SEARCH_ROUTINE);
            preparedStatement.setString(1, panelInfo.get(0).get(0));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Vector<Object> temp = new Vector<>();
                temp.add(resultSet.getString("teacher_id"));
                temp.add(resultSet.getString("ranking"));
                if(resultSet.getInt("is_corresponding_author") == 1) {
                    result.get(0).add(temp.get(0));
                }
                result.add(temp);
            }
            preparedStatement.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return result;
    }
    public static void actionAddUpdate(
            int paperId,
            String paperTitle,
            String paperSource,
            int paperYear,
            int paperType,
            int paperLevel,
            String correspondingAuthor,
            Vector<String> teacherIdList,
            Vector<String> teacherRankList,
            boolean isAdd
    ) throws SQLException {
        try {
            setConnection();
            if (isAdd) {
                preparedStatement = connection.prepareStatement(PAPER_ADD_ROUTINE);
            } else {
                preparedStatement = connection.prepareStatement(PAPER_UPDATE_ROUTINE);
            }
            preparedStatement.setInt(1, paperId);
            preparedStatement.setString(2, paperTitle);
            preparedStatement.setString(3, paperSource);
            preparedStatement.setInt(4, paperYear);
            preparedStatement.setInt(5, paperType);
            preparedStatement.setInt(6, paperLevel);
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            for (int i = 0; i < teacherIdList.size(); i++) {
                if (isAdd) {
                    preparedStatement = connection.prepareStatement(PUBLISH_ADD_ROUTINE);
                } else {
                    preparedStatement = connection.prepareStatement(PUBLISH_UPDATE_ROUTINE);
                }
                preparedStatement.setInt(1, paperId);
                preparedStatement.setString(2, teacherIdList.get(i));
                preparedStatement.setInt(3, Integer.parseInt(teacherRankList.get(i)));
                preparedStatement.setInt(4, correspondingAuthor.equals(teacherIdList.get(i)) ? 1 : 0);
                preparedStatement.setInt(5, i);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
            closeConnection();
        }
    }
}
