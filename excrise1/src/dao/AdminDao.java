package dao;

import bean.Admin;

import java.util.List;

public interface AdminDao {

    int addAdmin(Admin admin);

    int getTotalRecordNum();

    List<Admin> queryPageAdmin(int number, int offset);

    int deleteOne(String aid);

    Admin islogin(Admin admin);

    boolean updateAdmin(Admin admin);

}
