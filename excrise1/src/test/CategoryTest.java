package test;

import bean.Category;
import dao.CategoryDao;
import dao.CategoryDaoImpl;
import org.junit.Test;

import java.util.List;

public class CategoryTest {

    CategoryDao dao = new CategoryDaoImpl();

    @Test
    public void testAdd(){
        Category category = new Category();
        category.setCname("adidas");
        dao.addCategory(category);
    }

    @Test
    public void testCount(){
        int totalRecordNum = dao.getTotalRecordNum();
        System.out.println(totalRecordNum);
    }

    @Test
    public void testPageRecord(){
        List<Category> categoryList = dao.queryPageCategory(5, 5);
        for (Category category : categoryList) {
            System.out.println(category);
        }
    }
}
