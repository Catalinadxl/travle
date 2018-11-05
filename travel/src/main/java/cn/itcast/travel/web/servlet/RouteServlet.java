package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();


    public void findLimit(HttpServletRequest request, HttpServletResponse response){
        String cid = request.getParameter("cid");
        String pageNo = request.getParameter("pageNo");
        String city = request.getParameter("city");

        List<Route> limit = routeService.findLimit(cid, pageNo,city);

        PageBean pageBean=routeService.findPage(cid,city);

        pageBean.setList(limit);

        writeValue(pageBean,response);


    }

    public void findDetail(HttpServletRequest request,HttpServletResponse response){

        String rid = request.getParameter("rid");

//        List<T> list=routeService.findDetail(rid);

        Route route = routeService.findRoute(rid);

        writeValue(route,response);

    }


}
