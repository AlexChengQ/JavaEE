package service;

import bean.Category;
import bean.Page;
import java.sql.SQLException;
import java.util.List;

//该层就是讲dao层处理完的数据库数据转发给servlet（controller层），进一步增强业务逻辑
public interface CategoryService {

    int addCategory(Category category);

    Page findPageCategory(String num);

    //Page<Category> findAllCategory(String num) throws SQLException;

    int updateCategory(Category category);

    boolean deleteOne(String cid) throws SQLException;

    boolean deleteAll(String[] checkbox) throws SQLException;

    List<Category> findCategory() throws SQLException;
}
