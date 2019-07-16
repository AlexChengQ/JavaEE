package dao;

import bean.Category;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.C3P0Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());

    @Override
    public int addCategory(Category category) {
        int i = 0;
        if(category.getCname() == null)return -1;
        try {
             i = runner.update("insert into category values (null,?)", category.getCname());
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return i;
    }

/*    @Override
    public int deleteCategory(Category category) {
        int update = 0;
        try {
             update = runner.update("delete from category where cid = ?", category.getCid());
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return update;
    }*/

    @Override
    public int updateCategory(Category category) {
        int update = 0;
        //如果和之前的类名cname一致也会报错，未实现
        if(category.getCname() == null)return -1;
        try {
             update = runner.update("update category set cname = ? where cid = ?", category.getCname(), category.getCid());
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return update;
    }

/*    @Override
    public List<Category> queryCategory() {
        List<Category> list = null;
        try {
             list = runner.query("select * from category", new BeanListHandler<Category>(Category.class));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }*/

    @Override
    public int getTotalRecordNum() {
        Long query = 0L;
        try {
             query = (Long) runner.query("select count(cid) from category", new ScalarHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query.intValue();
    }

    @Override
    public List<Category> queryPageCategory(int number, int offset) {
        List<Category> categoryList = null;
        try {
             categoryList = runner.query("select * from category limit ? offset ?",
                    new BeanListHandler<Category>(Category.class), number, offset);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    @Override
    public int findCategoryNum() throws SQLException {
        Long query = (Long) runner.query("select count(*) from category", new ScalarHandler());
        return query.intValue();
    }

    @Override
    public List<Category> getCategoryList(int limit, int offset) {
        List<Category> list = null;
        try {
            list = runner.query("select * from category limt ? offset ?",new BeanListHandler<Category>(Category.class),limit,offset);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteOne(int cid) throws SQLException {
        int update = runner.update("delete from category where cid = ?", cid);
        return update == 1 ? true : false;
    }

    @Override
    public List<Category> findCategory() throws SQLException {
        List<Category> query = runner.query("select * from category", new BeanListHandler<Category>(Category.class));
        return query;
    }
}
