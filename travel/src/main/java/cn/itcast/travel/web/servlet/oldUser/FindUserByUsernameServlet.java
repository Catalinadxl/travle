package cn.itcast.travel.web.servlet.oldUser;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/findUserByUsernameServlet")
public class FindUserByUsernameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        ResultInfo info = new ResultInfo();
        try {
            BeanUtils.populate(user,map);
            UserService userService = new UserServiceImpl();
            User user1=userService.findUserByUsername(user);

            if (user1==null){
                //代表用户名或密码错误
                info.setFlag(false);
                info.setErrorMsg("用户名或密码错误");
            }

            if(user1!=null && "Y".equals(user1.getStatus())){
                //用户账号密码正确并且账号已激活
                info.setFlag(true);
                request.getSession().setAttribute("user",user1);
            }

            if (user1!=null&&!"Y".equals(user1.getStatus())){
                //用户账号密码正确但是账号未激活
                info.setFlag(false);
                info.setErrorMsg("账号未激活,请先激活后再使用");
            }
            response.setContentType("application/json;charset=utf-8");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(),info);
        } catch (IllegalAccessException e) {

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
