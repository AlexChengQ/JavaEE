package dao;

import bean.Category;
import bean.Product;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.C3P0Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex_Cheng
 * @date 2019/5/28 23:00
 * @Description TODO
 */
public class ProductDaoImpl implements  ProductDao {

    QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

    @Override
    public int addProduct(Product product) {

        int update = 0;
        if(product.getPid() ==null || product.getPname() == null)return  -1;
        try {
            //pid和pname不可以重复
            update = queryRunner.update("insert into product values(?,?,?,?,?,?,?,?);", product.getPid(), product.getPname(), product.getEstoreprice(), product.getMarkprice(), product.getPnum(), product.getCid(), product.getImgurl(), product.getDescription());
        } catch (SQLException e) {
            e.printStackTrace();
            return  -1;
        }
        return update ;
    }

    @Override
    public int updateProduct(Product product) {
        int update = 0;
        try {
            update = queryRunner.update("update product set pname = ?,estoreprice = ?,markprice = ?,pnum = ?,cid = ?,imgurl = ?,description = ? where pid = ?",
            product.getPname(), product.getEstoreprice(), product.getMarkprice(), product.getPnum(), product.getCid(), product.getImgurl(), product.getDescription(),product.getPid());
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return update ;
    }

    @Override
    public List<Product> queryProduct() {
        return null;
    }
/*
    @Override
    public int updateCategory(Product product) {
        return 0;
    }

    @Override
    public List<Product> queryCategory() {
        return null;
    }*/

    @Override
    public int getTotalRecordNum() {
        Long query = 0L;
        try {
            query = (Long) queryRunner.query("select count(*) from product", new ScalarHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query.intValue();
    }

    @Override
    public List<Product> getProductList(int number, int offset)  {
        List<Product> productList = null;

        try {
            productList = queryRunner.query("select * from product inner join category on product.cid = category.cid limit ? offset ?",
                    new BeanListHandler<Product>(Product.class), number, offset);
            int size = productList.size();
            if (size>0){
                for (int i = 0; i < size; i++) {
                    Product product = productList.get(i);
                    int cid1 = product.getCid();
                    Category category = queryRunner.query("select * from category where cid=?",
                            new BeanHandler<Category>(Category.class),cid1);
                    productList.get(i).setCategory(category);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public int findCategoryNum() throws SQLException {
        return 0;
    }

    @Override
    public List<Product> getCategoryList(int limit, int offset) {
        return null;
    }

    @Override
    public boolean deleteOne(String pid) throws SQLException {
        int update = queryRunner.update("delete from product where pid = ?", pid);
        return update==1?true:false;
    }


    @Override
    public int searchProductCount(String id, String name, String cid, String maxprice, String minprice) {
        String sql = "select count(*) from product where 1=1 ";
        ArrayList param = new ArrayList();

        if (id != null && !id.isEmpty()){
            sql = sql + "and pid =?";
            param.add(id);
        }
        if (name != null && !name.isEmpty()){
            sql = sql + "and pname like ?";
            param.add("%"+name+"%");
        }
        if (cid != null&& !cid.isEmpty()){
            sql = sql + "and cid = ?";
            param.add(cid);
        }
        if (maxprice != null && !maxprice.isEmpty()){
            sql = sql + "and estoreprice <= ?";
        }
        if (minprice != null && !minprice.isEmpty()){
            sql = sql + "and estoreprice >= ?";
        }

        Long query = null;
        try {
            query = (Long)queryRunner.query(sql,new ScalarHandler(),param.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("查询失败");
        }

        return query.intValue();
    }

    @Override
    public List<Product> searchProduct(String id, String name, String cid, String maxprice, String minprice, int limit, int offset) {
        String sql = "select * from product inner join category on product.cid = category.cid where 1=1 ";
        ArrayList param = new ArrayList();

        if (id != null && !id.isEmpty()){
            sql = sql + " and pid =?";
            param.add(id);
        }
        if (name != null && !name.isEmpty()){
            sql = sql + "and pname like ?";
            param.add("%"+name+"%");
        }
        if (cid != null&& !cid.isEmpty()){
            sql = sql + "and category.cid = ?";
            param.add(cid);
        }
        if (maxprice != null && !maxprice.isEmpty()){
            sql = sql + "and estoreprice <= ?";
        }
        if (minprice != null && !minprice.isEmpty()){
            sql = sql + "and estoreprice >= ?";
        }

        sql=sql+" limit ? offset ?";
        param.add(limit);
        param.add(offset);

        List<Product> products = null;
        try {
            products = queryRunner.query(sql, new BeanListHandler<Product>(Product.class), param.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int size = products.size();

        if (size>0){
            for (int i = 0; i < size; i++) {
                sql = "select * from category where cid=?";
                Product product = products.get(i);
                int cid1 = product.getCid();
                Category category = null;
                try {
                    category = queryRunner.query(sql, new BeanHandler<Category>(Category.class),cid1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                products.get(i).setCategory(category);
            }
        }
        return products;
    }

    @Override
    public Product getProductByPid(String pid) {
        Product query = null;
        try {
            query = queryRunner.query("select * from product where pid =?", new BeanHandler<Product>(Product.class), pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    @Override
    public Product findProductByPid(String pid) {
        Product query = null;
        try {
            query = queryRunner.query("select * from product where pid =?", new BeanHandler<Product>(Product.class), pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    @Override
    public List<Product> findProductByCid(String cid) {
        List<Product> query = null;
        try {
            query = queryRunner.query("select * from product where  cid = ?", new BeanListHandler<Product>(Product.class), cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    @Override
    public List<Product> findProductByPname(String pname) {
        List<Product> query = null ;
        try {
             query = queryRunner.query("select * from product where  pname like ? or description like ?",
                     new BeanListHandler<Product>(Product.class), "%" + pname + "%","%" + pname + "%");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    @Override
    public List<Product> searchHint(String key) {
        List<Product> products = null;
        try {
            products = queryRunner.query("select * from product where pname like ? ", new BeanListHandler<Product>(Product.class),"%"+key+"%");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> findProductTop(int i) {
        return null;
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product>  productList = null ;
        try {
            productList = queryRunner.query("select  * from product  ",new BeanListHandler<Product>(Product.class));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return productList;
    }
}
