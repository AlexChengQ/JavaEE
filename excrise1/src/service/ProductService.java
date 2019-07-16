package service;

import bean.Page;
import bean.Product;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public interface ProductService {

    Page<Product> findAllCategory(String num) throws SQLException;

    int addProduct(Product product);

    String searchHint(String key);

    Product findProductByPid(String pid);

    List<Product> findProductByPname(String pname);

    List<Product> findProductByCid(String cid);

    void deleteAll(String[] checkbox) throws SQLException;

    boolean deleteOne(String pid) throws SQLException;

    boolean updateProduct(Product product);

    Product getProductByPid(String pid);

    Page<Product> searchProduct(HttpServletRequest request);

    List<Product> findProductTop(int a);
}
