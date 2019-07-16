package dao;



import bean.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    User checkNick(String nickname) throws SQLException;

    User checkName(String username) throws SQLException;

    boolean register(User user) throws SQLException;

    int findUserNum() throws SQLException;

    List<User> getUser(int limit, int offset) throws SQLException;

    int getState(String username) throws SQLException;


    boolean activeUser(String code) throws SQLException;

    boolean updateUser(User user) throws SQLException;

    User getUserByUid(int uid) throws SQLException;
}
