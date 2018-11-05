package cn.itcast.travel.web.servlet.oldUser;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/findLoginName")
public class FindLoginName extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper map = new ObjectMapper();
        ResultInfo info = new ResultInfo();
        if (user!=null) {
            info.setFlag(true);
            info.setErrorMsg(user.getName());
            map.writeValue(response.getWriter(), info);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
