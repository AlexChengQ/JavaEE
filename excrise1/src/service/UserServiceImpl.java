package service;



import bean.Page;
import bean.User;
import dao.UserDao;
import dao.UserDaoImpl;
import utils.JMailUtils;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;


public class UserServiceImpl implements UserService {

    UserDao userDao = new UserDaoImpl();
    @Override
    public User CheckName(String username) throws SQLException {
        return userDao.checkName(username);
    }

    @Override
    public User CheckNick(String nickname) throws SQLException {
        return userDao.checkNick(nickname);
    }

    @Override
    public boolean isRegister(User user) throws SQLException {
        user.setState(1);
        return userDao.register(user);
    }

    @Override
    public Page<User> getUserList(String num) throws SQLException {
        int pageNum = 1;
        if (num != null){
            pageNum = Integer.parseInt(num);
        }
        int totalRecordSNum = userDao.findUserNum();
        Page<User> page = new Page<>(pageNum,totalRecordSNum,10);
        int limit = 10;
        int offset = (pageNum-1)*10;

        List<User> users = userDao.getUser(limit,offset);
        page.setList(users);

        return page;
    }

    @Override
    public boolean isRegist(User user) throws UnknownHostException, SQLException {
        user.setHeadicon("/files/icon.png");
        boolean register = userDao.register(user);
        if (register){
            String url =Inet4Address.getLocalHost().getHostAddress()+"/UserServlet?op=activeCode&code="+user.getActivecode();
            url = "<a href=\"http:"+url+"\">点我激活</a>";
            JMailUtils.sendMail(user.getEmail(),url);
        }

        return register;
    }

    @Override
    public int getState(String username) throws SQLException {
        return userDao.getState(username);
    }

    @Override
    public User login(String username) throws SQLException {
        return userDao.checkName(username);
    }

    @Override
    public boolean activeUser(String code) throws SQLException {
        return userDao.activeUser(code);
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        return userDao.updateUser(user);
    }
}
