package dao;

import bean.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {

    //添加，返回1则添加成功
    int addCategory(Category category);

    //int deleteCategory(Category category);

    //更新，但是未实现更改前后类名一致也应该提示修改错误
    int updateCategory(Category category);

    //List<Category> queryCategory();

    //最大记录数
    int getTotalRecordNum();

    //分页展示的起始位置和展示记录数
    List<Category> queryPageCategory(int number, int offset);

    //得到有多少记录数
    int findCategoryNum() throws SQLException;

    //select * from category limt ? offset ?起始位置和展示几条记录
    List<Category> getCategoryList(int limit, int offset);

    //删除
    boolean deleteOne(int cid) throws SQLException;

    List<Category> findCategory() throws SQLException;
}
