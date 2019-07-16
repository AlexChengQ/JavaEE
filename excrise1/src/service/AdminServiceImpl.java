package service;

import bean.Admin;
import bean.Category;
import bean.Page;
import bean.Product;
import dao.AdminDao;
import dao.AdminDaoImpl;

import java.util.List;

/**
 * @author Alex_Cheng
 * @date 2019/5/31 22:21
 * @Description TODO
 */
public class AdminServiceImpl implements  AdminService {

    AdminDao adminDao = new AdminDaoImpl();

    @Override
    public int addAdmin(Admin admin) {
        return adminDao.addAdmin(admin);
    }

    @Override
    public Page findAllAdmin(String num) {
        int totalRecordNum = adminDao.getTotalRecordNum();
        int totalPageNum = 0;
        if(totalRecordNum % Page.PAGE_SIZE == 0){
            totalPageNum = totalRecordNum / Page.PAGE_SIZE;
        }else {
            totalPageNum = totalRecordNum / Page.PAGE_SIZE + 1;
        }
        // double ceil = Math.ceil(totalRecordNum / (Page.PAGE_SIZE * 1.0));
        //当前页内的数据 limit num(PAGE_SIZE) offset offset (num - 1) * PAGE_SIZE
        int number = Page.PAGE_SIZE;
        int currentPage = Integer.parseInt(num);
        int offset = (currentPage - 1) * Page.PAGE_SIZE;
        List<Admin> adminList = adminDao.queryPageAdmin(number,offset);
        Page page = new Page();
        page.setCurrentPageNum(currentPage);
        page.setList(adminList);
        page.setNextPageNum(currentPage + 1);
        page.setPreviousPageNum(currentPage - 1);
        page.setTotalRecordNum(totalRecordNum);
        page.setTotalPageNum(totalPageNum);

        return page;
    }


    @Override
    public int deleteOne(String aid) {
        return adminDao.deleteOne(aid);
    }

    @Override
    public boolean deleteAll(String[] checkbox) {
        boolean flag = true;
        for (int i = 0; i < checkbox.length; i++) {

            int isDelete = deleteOne(checkbox[i]);
            if (isDelete ==1){
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public Admin islogin(Admin admin) {
        return adminDao.islogin(admin);
    }

    @Override
    public boolean updateAdmin(Admin admin) {
        return adminDao.updateAdmin(admin);
    }
}
