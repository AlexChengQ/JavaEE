package dao;


import bean.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    //添加
    int addProduct(Product product);

    //更新
    int updateProduct(Product product);

    //查，得到所有
    List<Product> queryProduct();

    int getTotalRecordNum();

    List<Product> getProductList(int number, int offset) throws SQLException;

    int findCategoryNum() throws SQLException;

    List<Product> getCategoryList(int limit, int offset);

    boolean deleteOne(String pid) throws SQLException;

    int searchProductCount(String id, String name, String cid, String maxprice, String minprice);

    List<Product> searchProduct(String id, String name, String cid, String maxprice, String minprice, int limit, int offset);

    Product getProductByPid(String pid);

    Product findProductByPid(String pid);

    List<Product> findProductByCid(String cid);

    List<Product> findProductByPname(String pname);

    List<Product> searchHint(String key);

    List<Product> findProductTop(int i);

    List<Product> getAllProduct();
}
