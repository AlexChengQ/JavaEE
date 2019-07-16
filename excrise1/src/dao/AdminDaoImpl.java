package dao;

import bean.Admin;
import bean.Category;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.C3P0Utils;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Alex_Cheng
 * @date 2019/5/31 22:20
 * @Description TODO
 */
public class AdminDaoImpl implements AdminDao {

    Admin admin = new Admin();
    QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

    @Override
    public int addAdmin(Admin admin) {
        int update = 0;
        if(admin.getUsername()== null || admin.getPassword() == null )return  -1;
        try {
            update = queryRunner.update("insert into admin values(null,?,?);",admin.getUsername(),admin.getPassword() );
        } catch (SQLException e) {
            e.printStackTrace();
            return  -1;
        }
        return update ;
    }

    @Override
    public int getTotalRecordNum() {
        Long query = 0L;
        try {
            query = (Long) queryRunner.query("select count(aid) from admin", new ScalarHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query.intValue();
    }

    @Override
    public List<Admin> queryPageAdmin(int number, int offset) {
        List<Admin> adminList = null;
        try {
            adminList = queryRunner.query("select * from admin limit ? offset ?",
                    new BeanListHandler<Admin>(Admin.class), number, offset);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminList;
    }



    @Override
    public int deleteOne(String aid) {
        int i = 0 ;
        try {
             i = queryRunner.update("delete  from admin where aid = ?", aid);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return i;

    }

    @Override
    public Admin islogin(Admin admin) {
        Admin islogin = null;
        try {
            islogin = queryRunner.query("select * from `admin` where username = ?;", new BeanHandler<Admin>(Admin.class), admin.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return islogin;
    }

    @Override
    public boolean updateAdmin(Admin admin) {
        int update = 0;
        try {
            update = queryRunner.update("update `admin` set password = ? where username = ?;", admin.getPassword(), admin.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
            return  false;
        }
        return update == 1 ? true : false;
    }
}
