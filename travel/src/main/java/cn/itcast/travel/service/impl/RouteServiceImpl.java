package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao=new RouteDaoImpl();

    @Override
    public List<Route> findLimit(String cid, String pageNo, String city) {
        List<Route> routes = routeDao.findLimit(cid, pageNo,city);
        return routes;
    }

    @Override
    public PageBean findPage(String cid,String city) {
        return routeDao.findPage(cid,city);
    }

    @Override
    public Route findRoute(String rid) {
        Route route = routeDao.findRoute(rid);
        List<RouteImg> img = routeDao.findImg(rid);
        Seller seller = routeDao.findSeller(String.valueOf(route.getSid()));
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("route",route);
//        map.put("img",img);
//        map.put("seller",seller);
        route.setSeller(seller);
        route.setRouteImgList(img);
        return route;
    }
}
