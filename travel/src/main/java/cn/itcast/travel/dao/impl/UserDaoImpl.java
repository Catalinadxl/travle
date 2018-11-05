package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jt=new JdbcTemplate(JDBCUtils.getDataSource());


    /**
     * 异步请求判断用户名是否存在
     * @param username
     * @return
     */
    @Override
    public boolean checkUsername(String username) {
        User user=null;
        try {
            String sql="select * from tab_user where username=?";
            user = jt.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username);
        } catch (DataAccessException e) {
            e.printStackTrace();
            //查询不出来
            return true;
        }
        //查询出结果
        return false;
    }

    @Override
    public int saveUser(User user) {
        String sql="insert into tab_user values(null,?,?,?,?,?,?,?,?,?)";
        int update = jt.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode());
        return update;
    }

    @Override
    public User findUUidCode(String code) {
        User user=null;
        try {
            String sql="select * from tab_user where code=?";
           user  =  jt.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),code);
        } catch (DataAccessException e) {
            e.printStackTrace();

        }
      return user;
    }

    @Override
    public void updateStatus(User user) {
        String sql="update tab_user set status=? where uid=?";
        jt.update(sql,user.getStatus(),user.getUid());
    }

    /**
     * 通过账号密码查询用户信息
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        User user=null;
        try {
            String sql="select * from tab_user where username=? and password=?";
             user= jt.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username,password);
        } catch (DataAccessException e) {

        }

        return user;
    }

    @Override
    public Favorite findFavorite(String rid, String uid) {
        String sql="select * from tab_favorite where rid=? and uid=?";
        Favorite favorite=null;
        try {
            favorite = jt.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return favorite;
    }

    @Override
    public void saveFavorite(String rid, String uid, String date) {
        String sql="insert into tab_favorite values(?,?,?)";
        jt.update(sql,rid,date,uid);
    }


}
