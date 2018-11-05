package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate jt=new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public List<Route> findLimit(String cid, String pageNo, String city) {

        String sql="select * from tab_route where cid=? ";

        if(city!=null&&!"".equals(city)){
            sql+="  and  rname like '%"+city+"%'";
        }

        sql+="  limit ? , 5";

        List<Route> query = jt.query(sql, new BeanPropertyRowMapper<>(Route.class), cid, (Integer.parseInt(pageNo)-1)*5);

        return query;
    }

    @Override
    public PageBean findPage(String cid,String city) {
        String sql="select count(*) totalCount,ceil(count(*)/5) totalPage from tab_route where cid=?  ";
        if (city!=null&&city.length()!=0){
            sql+="  and  rname like '%"+city+"%'";
        }

        try {
            PageBean pageBean = jt.queryForObject(sql, new BeanPropertyRowMapper<PageBean>(PageBean.class), cid);
            return pageBean;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public List<RouteImg> findImg(String rid) {
        String sql="select * from tab_route_img where rid=?";
        return jt.query(sql,new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
    }

    @Override
    public Seller findSeller(String sid) {
        String sql="select * from tab_seller where sid =?";
        return jt.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),sid);
    }

    @Override
    public Route findRoute(String rid) {
        String sql="select * from tab_route where rid =?";
        return jt.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }


}
