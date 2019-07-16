package service;

import bean.Page;
import bean.Product;
import dao.ProductDao;
import dao.ProductDaoImpl;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Alex_Cheng
 * @date 2019/5/28 23:01
 * @Description TODO
 */
public class ProductServiceImpl implements  ProductService {

    ProductDao productDao = new ProductDaoImpl();
    @Override
    public Page<Product> findAllCategory(String num) throws SQLException {
        int totalRecordNum = productDao.getTotalRecordNum();
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
        List<Product> productList = productDao.getProductList(number,offset);
        Page page = new Page();
        page.setCurrentPageNum(currentPage);
        page.setList(productList);
        page.setNextPageNum(currentPage + 1);
        page.setPreviousPageNum(currentPage - 1);
        page.setTotalRecordNum(totalRecordNum);
        page.setTotalPageNum(totalPageNum);
        return page;
    }

    @Override
    public int addProduct(Product product) {
        return productDao.addProduct(product);
    }

    @Override
    public String searchHint(String key) {
        List<Product> productList =  productDao.searchHint(key);
        String s ="";
        for (int i = 0; i < productList.size(); i++) {
            if (i == productList.size()-1){
                s = s + productList.get(i).getPname();
            }else {
                s = s + productList.get(i).getPname()+",";
            }
        }
        return s;
    }

    @Override
    public Product findProductByPid(String pid) {
        return productDao.findProductByPid(pid);
    }

    @Override
    public List<Product> findProductByPname(String pname) {
        return productDao.findProductByPname(pname);
    }

    @Override
    public List<Product> findProductByCid(String cid) {
        return productDao.findProductByCid(cid);
    }

    @Override
    public void deleteAll(String[] checkbox) throws SQLException {
        for (int i = 0; i < checkbox.length; i++) {
            deleteOne(checkbox[i]);
        }
    }

    @Override
    public boolean deleteOne(String pid) throws SQLException {
        return productDao.deleteOne(pid);
    }

    @Override
    public boolean updateProduct(Product product) {
        int i = productDao.updateProduct(product);
        if (i == 1)return true;
        else
            return  false;
    }

    @Override
    public Product getProductByPid(String pid) {
        return productDao.getProductByPid(pid);
    }

    @Override
    public Page<Product> searchProduct(HttpServletRequest request) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String cid = request.getParameter("cid");
        String maxprice = request.getParameter("maxprice");
        String minprice = request.getParameter("minprice");
        String num = request.getParameter("num");

        int pageNum = 1;
        if (num != null){
            pageNum = Integer.parseInt(num);
        }

        int totalRecordsNum = productDao.searchProductCount(id, name, cid, maxprice, minprice);
        Page<Product> page = new Page<>(pageNum, totalRecordsNum, 10);

        int limit = 10;
        int offset = (pageNum-1)*5;

        List<Product> products = (List<Product>) productDao.searchProduct(id,name,cid,maxprice,minprice,limit,offset);

        page.setList(products);
        return page;
    }

    @Override
    public List<Product> findProductTop(int a) {
        List<Product> productList = new ArrayList<>();
        List<Product> products = productDao.getAllProduct();

        int size = products.size();
        if (a>size){
            a = size;
        }

        int[] diffNO = randomArray(0, size,a);
        if (diffNO.length!=0){
            for (int i = 0; i < diffNO.length; i++) {
                productList.add(products.get(diffNO[i]));
            }
        }
        return productList;

    }

    private int[] randomArray(int min, int max, int n) {
        int len = max-min;

        if(max < min || n > len){
            return null;
        }
        int[] source = new int[len];
        for (int i = min; i < min+len; i++){
            source[i-min] = i;
        }

        int[] result = new int[n];
        Random rd = new Random();
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            index = Math.abs(rd.nextInt() % len--);
            result[i] = source[index];
            source[index] = source[len];
        }
        return result;
    }
}
