package cn.itcast.travel.web.servlet.oldUser;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeServlet")
public class ActiveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        //查询uuid是否存在
        UserService userService = new UserServiceImpl();
        User user=userService.findUUidCode(code);
        if (user!=null){
            user.setStatus("Y");
            userService.updateStatus(user);
            response.setContentType("text/html;charset=utf-8");
            if("男".equals(user.getSex())) {
                response.getWriter().write("注册成功,欢迎你" + user.getName() + "先生  " + "<a href=\"http://localhost/travel/login.html\">点击登入</a>");
            }

            if("女".equals(user.getSex())) {
                response.getWriter().write("注册成功,欢迎你" + user.getName() + "女士  " + "<a href=\"http://localhost/travel/login.html\">点击登入</a>");
            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
