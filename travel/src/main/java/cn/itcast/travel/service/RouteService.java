package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;
import java.util.Map;

public interface RouteService {
    List<Route> findLimit(String cid, String pageNo, String city);

    PageBean findPage(String cid,String city);

    Route findRoute(String rid);
}
