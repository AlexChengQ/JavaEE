package dao;

import bean.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.C3P0Utils;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
    @Override
    public User checkNick(String nickname) throws SQLException {


        User query = queryRunner.query("select * from user where nickname = ?", new BeanHandler<User>(User.class), nickname);
        return query;
    }

    @Override
    public User checkName(String username) throws SQLException {


        User query = queryRunner.query("select * from user where username = ?", new BeanHandler<User>(User.class), username);
        return query;
    }

    @Override
    public boolean register(User user) throws SQLException {

        int update = queryRunner.update("insert into user values(null,?,?,?,?,?,?,?,?,?)", user.getUsername()
                , user.getNickname(), user.getPassword(), user.getEmail(), user.getBirthday(), user.getUpdatetime(),
                user.getState(), user.getActivecode(),user.getHeadicon());

        return update == 1?true:false;
    }

    @Override
    public int findUserNum() throws SQLException {

        Long update = (Long)queryRunner.query("select count(*) from user", new ScalarHandler());
        return update.intValue();
    }

    @Override
    public List<User> getUser(int limit, int offset) throws SQLException {

        List<User> users =queryRunner.query("select * from user limit ? offset ?",new BeanListHandler<User>(User.class),limit,offset);
        return users;
    }

    @Override
    public int getState(String username) throws SQLException {

        int query = (int) queryRunner.query("select state  = 1 or state = 0 from user where username = ?",new ScalarHandler(),username);
        return query;
    }

    @Override
    public boolean activeUser(String code) throws SQLException {

        int update = queryRunner.update("update user set state = 1 where activecode = ?", code);

        return update ==1? true:false;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {

        int update = queryRunner.update("update user set nickname = ?,password = ?,email = ?,birthday = ? where uid = ?",user.getNickname(), user.getPassword(),
                user.getEmail(), user.getBirthday(),user.getUid());

        return update == 1?true:false;

    }

    @Override
    public User getUserByUid(int uid) throws SQLException {

        User query = queryRunner.query("select * from user where uid = ?", new BeanHandler<User>(User.class), uid);
        return query;
    }


}
