package service;

import bean.Category;
import bean.Page;
import dao.CategoryDao;
import dao.CategoryDaoImpl;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    CategoryDao dao = new CategoryDaoImpl();

    @Override
    public int addCategory(Category category) {
        return dao.addCategory(category);
    }

    /**
     * @param num 代表当前的页数
     * */
    @Override
    public Page findPageCategory(String num) {
        //不能简单的直接调用dao层
        //一条sql执行总数量，换算得到总页数
       int totalRecordNum = dao.getTotalRecordNum();
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
        List<Category> categoryList = dao.queryPageCategory(number,offset);
        Page page = new Page();
        page.setCurrentPageNum(currentPage);
        page.setList(categoryList);
        page.setNextPageNum(currentPage + 1);
        page.setPreviousPageNum(currentPage - 1);
        page.setTotalRecordNum(totalRecordNum);
        page.setTotalPageNum(totalPageNum);

        return page;
    }

/*    @Override
    public Page<Category> findAllCategory(String num) throws SQLException {
        int pageNum = 1;
        if(num != null){
            pageNum = Integer.parseInt(num);
        }
        int totalRecordNum = dao.findCategoryNum();
        Page<Category> page = new Page<>(pageNum, totalRecordNum, 5);
        int limit = 5;
        int offset = (pageNum-1)*5;
        List<Category> categories = dao.getCategoryList(limit,offset);
        page.setList(categories);
        return page;
    }*/

    @Override
    public int updateCategory(Category category) {
        return dao.updateCategory(category);
    }

    @Override
    public boolean deleteOne(String cid) throws SQLException {
        return dao.deleteOne(Integer.parseInt(cid));
    }

    @Override
    public boolean deleteAll(String[] checkbox) throws SQLException {
        boolean flag = true;
        for (int i = 0; i < checkbox.length; i++) {

            boolean isDelete = deleteOne(checkbox[i]);
            if (!isDelete){
                flag = isDelete;
            }
        }
        return flag;
    }

    @Override
    public List<Category> findCategory() throws SQLException {
        return dao.findCategory();
    }
}