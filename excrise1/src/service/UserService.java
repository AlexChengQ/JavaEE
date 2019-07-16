package service;



import bean.Page;
import bean.User;

import java.net.UnknownHostException;
import java.sql.SQLException;

public interface UserService {
    User CheckName(String username) throws SQLException;

    User CheckNick(String nickname) throws SQLException;

    boolean isRegister(User user) throws SQLException;

    Page<User> getUserList(String num) throws SQLException;

    boolean isRegist(User user) throws UnknownHostException, SQLException;

    int getState(String username) throws SQLException;

    User login(String username) throws SQLException;

    boolean activeUser(String code) throws SQLException;

    boolean updateUser(User user) throws SQLException;
}
