package service;

import bean.Admin;
import bean.Page;
import bean.Product;

public interface AdminService {
    //添加
    int addAdmin(Admin admin);

    Page findAllAdmin(String num);

    int deleteOne(String aid);

    boolean deleteAll(String[] checkbox);

    Admin islogin(Admin admin);

    boolean updateAdmin(Admin admin);

}
