package cn.itcast.travel.web.servlet.oldUser;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registCheckUsername")
public class RegistCheckUsername extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户名是否存在判断
        String username = request.getParameter("username");
        UserService userService=new UserServiceImpl();
        boolean msg=userService.checkUsername(username);
        ResultInfo info=new ResultInfo();
        if (msg==true){
            //表示用户名不存在
            info.setFlag(true);
            info.setErrorMsg("账号可用");
        }else{
            //表示存在
            info.setFlag(false);
            info.setErrorMsg("账号已被注册,请更换");
        }
        //序列化为json
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper map = new ObjectMapper();

        map.writeValue(response.getWriter(),info);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
