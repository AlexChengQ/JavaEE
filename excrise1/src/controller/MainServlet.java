package controller;

import bean.Category;
import bean.Product;
import service.CategoryService;
import service.CategoryServiceImpl;
import service.ProductService;
import service.ProductServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "MainServlet",urlPatterns = "/MainServlet" ,loadOnStartup = 1)
public class MainServlet extends HttpServlet {

    ProductService productService = new ProductServiceImpl();
    CategoryService categoryService = new CategoryServiceImpl();
    Category category = new Category();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        doPost(request,response);

    }

    private void findProByName(HttpServletRequest request, HttpServletResponse response) {
        String pname = request.getParameter("pname");
        List<Product> productTop =productService .findProductByPname(pname);
        request.setAttribute("productTop",productTop);
        try {
            request.getRequestDispatcher("/index.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProductService productService = new ProductServiceImpl();
        List<Category> categories = null;
        try {
            categories = new CategoryServiceImpl().findCategory();
            int a = 3;
            int b = 6;

            List<Product> productOnTop = productService.findProductTop(a);

            List<Product> hotProducts = productService.findProductTop(b);

            if (productOnTop.size() != 0){
                request.getSession().setAttribute("productTop", productOnTop);
            }
            if (hotProducts.size() != 0){
                request.getSession().setAttribute("hotProducts", hotProducts);
            }
            ServletContext servletContext = request.getSession().getServletContext();

            servletContext.setAttribute("categories",categories);

            request.getRequestDispatcher("/index.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
