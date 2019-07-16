package controller;

import bean.Category;
import bean.Page;
import service.CategoryService;
import service.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CategoryServlet", urlPatterns = "/CategoryServlet")
public class CategoryServlet extends HttpServlet {

    CategoryService service = new CategoryServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //全局变量
        Category category = new Category();

        String op = request.getParameter("op");
        System.out.println("op = " + op);

        String cname = request.getParameter("cname");
        System.out.println("cname = " + cname);

        String cid = request.getParameter("cid");
        System.out.println("cid = " + cid);

        //通过OP值来分别处理servlet响应jsp页面
        switch (op) {
            //添加分类
            case "addCategory": {
                //校验add的值是否是我们需要的值，添加成功，可以提示，然后跳转至显示category的界面（categoryList）界面
                int add = addCategory(cname);
                if (add == 1) {
                    response.getWriter().print("成功添加分类： " + cname + " !");
                    //这个时候直接去categoryList页面无法显示到数据，需要先去servlet取出数据，然后转发给list页面
                    response.setHeader("refresh", "2;url='" + request.getContextPath() + "/CategoryServlet?op=findPageCategory&num=1");
                } else {
                    response.getWriter().print("添加失败!");
                    response.setHeader("refresh", "2;url='" + request.getContextPath() + "/admin/category/addCategory.jsp");
                }
                break;
            }

            //查找全部category，但是servlet已经失效（或更改），丢弃
            /*case "findAllCategory":{
                Page<Category> page = null;
                try {
                    String num = request.getParameter("num");
                    page = service.findAllCategory(num);
                    request.setAttribute("page",page);
                    request.getRequestDispatcher("/admin/category/categoryList.jsp").forward(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }*/

            //更新category
            case "updateCategory": {
                int update = updateCategory(cid, cname);
                if (update == 1) {
                    response.getWriter().print("成功修改分类： " + category.getCname() + " !");
                    response.setHeader("refresh", "2;url='" + request.getContextPath() + "/CategoryServlet?op=findPageCategory&num=1");
                } else {
                    response.getWriter().print("修改失败 !");
                    response.setHeader("refresh", "2;url='" + request.getContextPath() + "/admin/category/updateCategory.jsp?cid=" + category.getCid() + "&cname=" + category.getCname());
                }
                break;
            }

            //删除一个（要么是后面的链接操作删除要么是勾选check根据cid来删除）
            case "deleteOne": {
                try {
                    boolean isDeleteOne = service.deleteOne(cid);
                    if (isDeleteOne) {
                        response.getWriter().print("成功删除!");
                        response.setHeader("refresh", "2;url='" + request.getContextPath() + "/CategoryServlet?op=findPageCategory&num=1");
                    } else {
                        response.getWriter().print("删除失败!");
                        response.setHeader("refresh", "2;url='" + request.getContextPath() + "/CategoryServlet?op=findPageCategory&num=1");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }

            //删除全部（根据checkbox）
            case "deleteMulti": {
                try {
                    String[] checkbox = request.getParameterValues("checkbox");
                    boolean isDeleteAll = service.deleteAll(checkbox);
                    if (isDeleteAll) {
                        response.getWriter().print("成功删除!");
                        response.setHeader("refresh", "2;url='" + request.getContextPath() + "/CategoryServlet?op=findPageCategory&num=1");
                    } else {
                        response.getWriter().print("删除失败!");
                        response.setHeader("refresh", "2;url='" + request.getContextPath() + "/CategoryServlet?op=findPageCategory&num=1");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }

            //核心业务逻辑（从数据库获取数据并且分页展示）
            case "findPageCategory": {
                //处理分页的数据
                try {
                    String num = request.getParameter("num");
                    Integer.parseInt(num);
                } catch (Exception e) {
                    response.getWriter().println("参数有误");
                    return;
                }
                String num = request.getParameter("num");
                Page page = findPageCategory(num);
                request.setAttribute("page", page);
                request.getRequestDispatcher("/admin/category/categoryList.jsp").forward(request, response);
            }
            break;

            case "findCategory":{
                findCategory(request,response);
                break;
            }
            case "findCategorise":{
                findCategorise(request,response);
                break;
            }

        }


        }

    private void findCategorise(HttpServletRequest request, HttpServletResponse response) {

        try {
            List<Category> categories = service.findCategory();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/admin/product/searchProduct.jsp").forward(request, response);
        }  catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void findCategory(HttpServletRequest request, HttpServletResponse response) {

        try {
            List<Category> categories = service.findCategory();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/admin/product/addProduct.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Page findPageCategory(String num) {
            return service.findPageCategory(num);
        }

        /*private List<Category> findAllCategory(String num) {
            return null;
        }*/

        private int updateCategory(String cid, String cname) {
            Category category = new Category(Integer.parseInt(cid) ,cname);
            return service.updateCategory(category);
        }

        private int addCategory(String cname) {
            Category category = new Category();
            category.setCname(cname);
            return service.addCategory(category);
        }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request,response);
        }
}
