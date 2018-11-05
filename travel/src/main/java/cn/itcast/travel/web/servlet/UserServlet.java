package cn.itcast.travel.web.servlet;


import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet{

    public void registUserServlet(HttpServletRequest request, HttpServletResponse response) {

        //获取验证码判断
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String realCheck = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        ResultInfo info = new ResultInfo();
        if (realCheck == null || !realCheck.equalsIgnoreCase(check)) {
            //验证码错误
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
        }else{
            //验证码正确
            Map<String, String[]> map = request.getParameterMap();
            User user = new User();
            try {
                BeanUtils.populate(user, map);
                UserServiceImpl userService = new UserServiceImpl();
                boolean b=userService.saveUser(user);
                if (b){
                    info.setFlag(b);
                    info.setErrorMsg("注册成功");
                }else{
                    info.setFlag(b);
                    info.setErrorMsg("注册失败");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }


            writeValue(info,response);




    }
    public void registCheckUsername(HttpServletRequest request, HttpServletResponse response){
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
         writeValue(info,response);

    }

    public void findUserByUsernameServlet(HttpServletRequest request, HttpServletResponse response){

        String check = request.getParameter("check");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");

        if(checkcode_server!=null&&checkcode_server.length()>0&&checkcode_server.equalsIgnoreCase(check)){


        }else{

            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            writeValue(info,response);
            return;
        }


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
           writeValue(info,response);
        } catch (IllegalAccessException e) {

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void findLoginName(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        ObjectMapper map = new ObjectMapper();
        ResultInfo info = new ResultInfo();
        if (user!=null) {
            info.setFlag(true);
            info.setErrorMsg(user.getName());
            info.setData(user);
            writeValue(info,response);
        }
    }

    public void exitServlet(HttpServletRequest request, HttpServletResponse response){
        request.getSession().invalidate();
        try {
            response.sendRedirect(request.getContextPath()+"/login.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void activeServlet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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


    public void findFavorite(HttpServletRequest request,HttpServletResponse response){
        String rid = request.getParameter("rid");
        String uid = request.getParameter("uid");
        UserService userService = new UserServiceImpl();
        Favorite favorite=userService.findFavorite(rid,uid);
        writeValue(favorite,response);
    }


    public void collectFavorite(HttpServletRequest request,HttpServletResponse response){
        String rid = request.getParameter("rid");
        String uid = request.getParameter("uid");


        UserService userService = new UserServiceImpl();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        userService.saveFavorite(rid,uid, date);
    }


}
