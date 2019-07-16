package controller;

import bean.Category;
import bean.Page;
import bean.Product;

import service.CategoryServiceImpl;
import service.ProductService;
import service.ProductServiceImpl;
import utils.FileUploadUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "ProductServlet",urlPatterns = "/ProductServlet")
public class ProductServlet extends HttpServlet {

    ProductService productService = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op) {
            case "addProduct": {
                addProduct(request, response);
                break;
            }
            case "findAllProduct":{
                findAllProduct(request,response);
                break;
            }
            case "searchProduct":{
                searchProduct(request,response);
                break;
            }
            case "findCategoryByUpdate":{
                findCategoryByUpdate(request,response);
                break;
            }
            case "updateProduct":{
                updateProduct(request,response);
                break;
            }
            case "deleteOne":{
                deleteOne(request,response);
                break;
            }
            case "deleteMulti":{
                deleteMulti(request,response);
                break;
            }
            case "byCid":{
                byCid(request,response);
                break;
            }
            case "findProByName":{
                findProByName(request,response);
                break;
            }
            case "findProductById":{
                findProductById(request,response);
                break;
            }
            case "searchHint":{
                searchHint(request,response);
                break;
            }
        }
    }

    private void searchHint(HttpServletRequest request, HttpServletResponse response) {

        try {
            String key = request.getParameter("key");
            String pnames = productService.searchHint(key);
            System.out.println(pnames);
            response.getWriter().println(pnames);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    private void findProductById(HttpServletRequest request, HttpServletResponse response) {
        try {
            String pid = request.getParameter("pid");
            Product product = productService.findProductByPid(pid);
            request.setAttribute("product",product);
            request.getRequestDispatcher("/productdetail.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void findProByName(HttpServletRequest request, HttpServletResponse response) {
        try {
            String pname = request.getParameter("pname");
            List<Product> products = productService.findProductByPname(pname);
            request.setAttribute("products",products);
            request.getRequestDispatcher("/products.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void byCid(HttpServletRequest request, HttpServletResponse response) {
        try{
            String cid = request.getParameter("cid");
            List<Product> products = productService.findProductByCid(cid);
            request.setAttribute("products",products);
            request.getRequestDispatcher("/products.jsp").forward(request,response);
        }  catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void deleteMulti(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] checkbox = request.getParameterValues("pid");
            productService.deleteAll(checkbox);
            response.getWriter().print("成功删除!");
            response.setHeader("refresh", "2;url='" + request.getContextPath() + "/ProductServlet?op=findAllProduct&num=1");
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void deleteOne(HttpServletRequest request, HttpServletResponse response) {
        try {
            String pid = request.getParameter("pid");
            boolean isDelete = productService.deleteOne(pid);
            if (isDelete){
                response.getWriter().print("修改成功！");
                response.setHeader("refresh", "2;url='" + request.getContextPath() + "/ProductServlet?op=findAllProduct&num=1");
            }else {
                response.getWriter().print("修改失败！");
                response.setHeader("refresh", "2;url='" + request.getContextPath() + "/ProductServlet?op=findAllProduct&num=1");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) {
        try {
            Product product = FileUploadUtils.creatProduct(request);

            if (!product.getImgurl().endsWith(".png") && !product.getImgurl().endsWith(".jpg" )
                    && !product.getImgurl().endsWith(".jpeg") && !product.getImgurl().endsWith(".gif")){
                Product productByPid = productService.findProductByPid(product.getPid());
                product.setImgurl(productByPid.getImgurl());
            }
            boolean isUpdate = productService.updateProduct(product);
            if (isUpdate){
                response.getWriter().print("修改成功！");
                response.setHeader("refresh", "2;url='" + request.getContextPath() + "/ProductServlet?op=findAllProduct&num=1");
            }else {
                response.getWriter().print("修改失败！");
                response.setHeader("refresh", "2;url='" + request.getContextPath() + "/ProductServlet?op=findCategoryByUpdate&pid="+product.getPid());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void findCategoryByUpdate(HttpServletRequest request, HttpServletResponse response) {

        try {
            String pid = request.getParameter("pid");
            List<Category> categories = new CategoryServiceImpl().findCategory();
            Product product = productService.getProductByPid(pid);
            int cid = product.getCid();
            for (Category c : categories) {
                if (c.getCid() == cid){
                    product.setCategory(c);
                }
            }
            request.setAttribute("categories",categories);
            request.setAttribute("product",product);

            request.getRequestDispatcher("/admin/product/updateProduct.jsp").forward(request,response);

        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void searchProduct(HttpServletRequest request, HttpServletResponse response) {
        try {
            Page<Product> page = productService.searchProduct(request);
            request.setAttribute("page", page);
            request.getRequestDispatcher("/admin/product/productList.jsp").forward(request, response);

        }  catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findAllProduct(HttpServletRequest request, HttpServletResponse response) {
        try {
            String num = request.getParameter("num");
            Page<Product> page = productService.findAllCategory(num);
            request.setAttribute("page",page);
            request.getRequestDispatcher("admin/product/productList.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) {
        try {
            Product product = FileUploadUtils.creatProduct(request);

            //原来的pid有可能是任意string类型，利用商品名的hashcode值来重新赋值pid
/*            String pname = product.getPname();
            int i = pname.hashCode();
            product.setPid(String.valueOf(i));*/
            int isAdd = productService.addProduct(product);
            if (isAdd ==1){
                response.getWriter().print("添加成功！");
                response.setHeader("refresh", "2;url='" + request.getContextPath() + "/ProductServlet?op=findAllProduct&num=1");
            }else {
                response.getWriter().print("添加失败！输入的参数有误（例如：商品号）");
                response.setHeader("refresh", "2;url='" + request.getContextPath() + "/ProductServlet?op=addProduct");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}