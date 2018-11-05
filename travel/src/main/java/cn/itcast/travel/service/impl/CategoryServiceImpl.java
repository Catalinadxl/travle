package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao=new CategoryDaoImpl();
    private Jedis jedis= JedisUtil.getJedis();


    @Override
    public List<Category> findAll() {

        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);

        if (categorys==null||categorys.size()==0){
            //代表redis没有缓存数据
          List<Category> list = categoryDao.findAll();
            //存入redis
            for (Category category : list) {
                jedis.zadd("category",category.getCid(),category.getCname());
            }

            jedis.close();

            System.out.println("查询数据库");

            return list;
        }else{
            //因为要返回list集合所以要遍历
            ArrayList<Category> list = new ArrayList<>();
            System.out.println("查询缓存");
            for (Tuple tuple : categorys) {
                Category category1 = new Category();
                category1.setCname(tuple.getElement());
                category1.setCid((int) tuple.getScore());
                list.add(category1);
            }

            return list;
        }



    }
}
