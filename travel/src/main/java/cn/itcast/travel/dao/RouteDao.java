package cn.itcast.travel.dao;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;

import java.util.List;

public interface RouteDao {

    List<Route> findLimit(String cid, String pageNo, String city);

    PageBean findPage(String cid,String city);

    List<RouteImg> findImg(String rid);

    Seller findSeller(String sid);

    Route findRoute(String rid);
}
