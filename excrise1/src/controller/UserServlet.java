package controller;

import bean.Page;
import bean.User;
import bean.UserFormBean;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

@WebServlet(name = "UserServlet",urlPatterns = "/UserServlet")
public class UserServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op){
            case "adduser":{
                adduser(request,response);
                break;
            }
            case "findAllUser":{
                findAllUser(request,response);
                break;
            }
            case "login":{
                login(request,response);
                break;
            }
            case "regist":{
                regist(request,response);
                break;
            }
            case "lgout":{
                logout(request,response);
                break;
            }
            case "activeCode":{
                activeCode(request,response);
                break;
            }
            case "personal":{
                personal(request,response);
                break;
            }
            case "updateUser":{
                updateUser(request,response);
                break;
            }
            case "checkUsername":{
                checkUsername(request,response);
                break;
            }
            case "checkNickname":{
                checkNickname(request,response);
                break;
            }
        }
    }

    private void checkNickname(HttpServletRequest request, HttpServletResponse response) {
        try{
            String nickname = request.getParameter("nickname");
            User user = userService.CheckNick(nickname);
            if (user != null){
                response.getWriter().print("true");
            }else {
                response.getWriter().print("false");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void checkUsername(HttpServletRequest request, HttpServletResponse response) {
        try {
            String username = request.getParameter("username");
            User user = userService.CheckName(username);

            if (user != null){
                response.getWriter().print("true");
            }else {
                response.getWriter().print("false");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            String nickname = request.getParameter("nickname");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String birthday = request.getParameter("birthday");
            User user = (User) request.getSession().getAttribute("user");
            user.setNickname(nickname);
            user.setPassword(password);
            user.setEmail(email);
            user.setBirthday(birthday);
            boolean isUpdate = userService.updateUser(user);

            if (isUpdate){
                request.getSession().setAttribute("user",user);
                request.getRequestDispatcher("/index.jsp").forward(request,response);
            }else {
                personal(request,response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void personal(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/user/personal.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void activeCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            String code = request.getParameter("code");
            boolean isActive = userService.activeUser(code);
            if (isActive){
                response.getWriter().print("激活成功，1秒后转到主页");
                request.getRequestDispatcher("/index.jsp").forward(request,response);
            }else {
                response.getWriter().print("激活失败，请重新激活，1秒后转到主页");
                request.getRequestDispatcher("/index.jsp").forward(request,response);
            }
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void logout(HttpServletRequest request, HttpServletResponse response) {
       try {
           HttpSession session = request.getSession();
           session.removeAttribute("user");
           request.getRequestDispatcher("/index.jsp").forward(request,response);
       } catch (ServletException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    private void regist(HttpServletRequest request, HttpServletResponse response) {

        try {
            UserFormBean userFormBean = UserFormBean.class.getDeclaredConstructor().newInstance();
            BeanUtils.populate(userFormBean,request.getParameterMap());
            boolean realText = userFormBean.isRealText();
            if (!realText){
                request.setAttribute("msg",userFormBean);
                request.getRequestDispatcher("/user/regist.jsp").forward(request, response);
                return;
            }
            User user = addUsers(userFormBean);

            boolean isRegister = userService.isRegist(user);
            if (isRegister){
                response.getWriter().write("注册成功,1秒后跳到主页");
                response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/index.jsp");
            }else {
                response.getWriter().write("注册失败,1秒后跳到注册页面");
                response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/user/regist.jsp");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private User addUsers(UserFormBean userFormBean) throws IllegalAccessException, InvocationTargetException, SQLException {
        User user = new User();

        ConvertUtils.register(new DateLocaleConverter(), Date.class);
        BeanUtils.copyProperties(user,userFormBean);
        String uuid = UUID.randomUUID().toString();
        int state = 0;
        user.setState(state);
        user.setActivecode(uuid);
        Date date = new Date();
        user.setUpdatetime(date.toString());
        return user;

    }


    private void login(HttpServletRequest request, HttpServletResponse response) {
        try{
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String verifycode = request.getParameter("verifycode");
            String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");
            int state = userService.getState(username);
            if(username == null || password == null){
                request.setAttribute("msg", "用户名或密码不能为空");
                request.getRequestDispatcher("/user/login.jsp").forward(request, response);
                return ;
            }
            if (!checkcode_session.equals(verifycode)){
                request.setAttribute("msg", "验证码不正确");
                request.getRequestDispatcher("/user/login.jsp").forward(request, response);
                return ;
            }
            if (state != 1){
                request.setAttribute("msg", "该用户未激活");
                request.getRequestDispatcher("/user/login.jsp").forward(request, response);
                return ;
            }
            User user = userService.login(username);
            if(user != null){
                if (user.getPassword().equals(password)){
                    request.getSession().setAttribute("user", user);
                    request.getRequestDispatcher("/index.jsp").forward(request,response);
                    return ;
                }else {
                    request.setAttribute("username", username);
                    request.setAttribute("password", password);
                    request.setAttribute("msg", "用户名或密码错误");
                    request.getRequestDispatcher("/user/login.jsp").forward(request,response);
                    return;
                }
            }else{
                request.setAttribute("msg", "该账号不存在");
                request.getRequestDispatcher("/user/login.jsp").forward(request, response);
                return ;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findAllUser(HttpServletRequest request, HttpServletResponse response) {
        try{
            String num = request.getParameter("num");
            Page<User> page = userService.getUserList(num);
            request.setAttribute("page",page);
            request.getRequestDispatcher("/admin/user/userList.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void adduser(HttpServletRequest request, HttpServletResponse response) {
        try{
            UserFormBean userFormBean = UserFormBean.class.getDeclaredConstructor().newInstance();
            BeanUtils.populate(userFormBean,request.getParameterMap());
            boolean realText = userFormBean.isRealText();

            if (!realText){
                request.setAttribute("msg",userFormBean);
                request.getRequestDispatcher("/admin/user/addUser.jsp").forward(request, response);
                return;
            }

            User user = addUsers(userFormBean);

            boolean isRegister = userService.isRegister(user);

            if(isRegister){
                response.getWriter().write("添加成功,1秒后跳到查看列表");
                response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/UserServlet?op=findAllUser&num=1");
            }else{
                response.getWriter().write("添加失败,1秒后回到添加页面");
                response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/admin/user/addUser.jsp");
            }


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
