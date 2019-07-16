package controller;

import bean.Admin;
import bean.Page;
import bean.Product;
import org.apache.commons.beanutils.BeanUtils;
import service.AdminService;
import service.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

@WebServlet(name = "AdminServlet" ,urlPatterns = "/AdminServlet")
public class AdminServlet extends HttpServlet {

    AdminService adminService = new AdminServiceImpl();
    Admin  admin = new Admin();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        System.out.println("op = " + op);
        switch (op){

            //添加
            case "addAdmin":{
                addAdmin(request,response);
                break;
            }

            //
            case "findAllAdmin":{
                findAllAdmin(request ,response);
                break;
            }

            //
            case "deleteOne":{
                deleteOne(request,response);
                break;
            }

            //
            case "deleteMulti":{
                deleteMulti(request,response);
                break;
            }

            case "login":{
                login(request,response);
                break;
            }

            case "lgout":{
                logout(request,response);
            }

            case "updateAdmin":{
                updateAdmin(request,response);
            }
        }
    }

    private void updateAdmin(HttpServletRequest request, HttpServletResponse response) {
        try {
            BeanUtils.populate(admin,request.getParameterMap());
            String password1 = request.getParameter("password1");

            if (admin.getPassword().equals(password1)){
                boolean isUpdate = adminService.updateAdmin(admin);
                if (isUpdate){
                    response.getWriter().print("update success!");
                    response.setHeader("refresh", "2;url='" + request.getContextPath() + "/AdminServlet?op=findAllAdmin&num=1");
                }else {
                    response.getWriter().print("update failed!");
                    response.setHeader("refresh", "2;url='" + request.getContextPath() + "/AdminServlet?op=findAllAdmin&num=1");
                }
            }else {
                response.getWriter().print("password error");
                response.setHeader("refresh", "2;url='" + request.getContextPath() + "/admin/admin/updateAdmin.jsp?username="+admin.getUsername());

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("admin");
        //response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/admin");
        try {
            response.getWriter().print("<script language='javascript'>alert('退出登录');" +
                    "window.location.href='admin/index.jsp';</script>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        try {
            BeanUtils.populate(admin,request.getParameterMap());
            Admin islogin = adminService.islogin(admin);
            if (islogin != null){
                if (islogin.getPassword().equals(admin.getPassword())){
                    HttpSession session = request.getSession();
                    session.setAttribute("admin",islogin);
                    response.getWriter().print("<script language='javascript'>alert('欢迎您：" +admin.getUsername() +
                            "   登录成功!!');window.location.href='admin/main.jsp';</script>");
                }else {
                    response.getWriter().print("<script language='javascript'>alert('密码不正确');" +
                            "window.location.href='admin/index.jsp';</script>");
                }
                }else {
                response.getWriter().print("<script language='javascript'>alert('该账号不存在');" +
                        "window.location.href='admin/index.jsp';</script>");
                }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteMulti(HttpServletRequest request, HttpServletResponse response) {
            try {
                String[] checkbox = request.getParameterValues("checkbox");
                boolean isDeleteAll = adminService.deleteAll(checkbox);
                if (isDeleteAll) {
                    response.getWriter().print("成功删除!");
                    response.setHeader("refresh", "2;url='" + request.getContextPath() + "/AdminServlet?op=findAllAdmin&num=1");
                } else {
                    response.getWriter().print("删除失败!");
                    response.setHeader("refresh", "2;url='" + request.getContextPath() + "/AdminServlet?op=findAllAdmin&num=1");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    private void deleteOne(HttpServletRequest request, HttpServletResponse response) {
        String aid = request.getParameter("aid");
        int deleteOne = adminService.deleteOne(aid);
        try {
            if(deleteOne == 1){
                response.getWriter().println("删除成功");
                response.setHeader("refresh" , "2;url=" + request.getContextPath() + "/AdminServlet?op=findAllAdmin&num=1");
            }else {
                response.getWriter().println("删除失败");
                response.setHeader("refresh","2;url="+ request.getContextPath() + "/AdminServlet?op=findAllAdmin&num=1");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void findAllAdmin(HttpServletRequest request, HttpServletResponse response) {
        try {
            String num = request.getParameter("num");
            Page page = adminService.findAllAdmin(num);
            request.setAttribute("page",page);
            request.getRequestDispatcher("/admin/admin/adminList.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addAdmin(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");
        //两次密码确保一致
        if (!password.equals(password1)) {
            try {
                response.getWriter().println("添加失败！两次密码不一致，请重新添加");
                response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/admin/addAdmin.jsp");
                System.out.println("添加失败，两次密码不一致");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //输入为空
        if (username == "" || password == "" || password1 == "") {
            try {
                response.getWriter().println("添加失败！管理员名或密码不能为空");
                response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/admin/addAdmin.jsp");
                System.out.println("添加失败，管理员名或密码不能为空");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            admin.setUsername(username);
            admin.setPassword(password);
            System.out.println("admin = " + admin.toString());
            int i = adminService.addAdmin(admin);
            try {
                if (i == 1) {
                    response.getWriter().println("成功添加管理员：" + admin.getUsername());
                    response.setHeader("refresh", "2;url='" + request.getContextPath() + "/AdminServlet?op=findAllAdmin&num=1");
                } else {
                    response.getWriter().println("添加失败！该管理员已存在");
                    response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/admin/addAdmin.jsp");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        doPost(request,response);
    }
}
