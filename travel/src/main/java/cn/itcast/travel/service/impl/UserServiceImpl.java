package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

import java.sql.Date;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();


    /**
     * 异步请求判断用户名是否存在
     *
     * @param username
     * @return
     */
    @Override
    public boolean checkUsername(String username) {
        boolean msg = userDao.checkUsername(username);
        return msg;
    }

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    @Override
    public boolean saveUser(User user) {
        user.setStatus("N");
        user.setCode(UuidUtil.getUuid());
        int i = userDao.saveUser(user);
        if (i > 0) {
            String code = user.getCode();
            String content="<a href=\"http://localhost/travel/user/activeServlet?code="+code+"\">点击激活黑马旅游网</a>";
            String email = user.getEmail();
            MailUtils.sendMail(email,content,"注册激活");
            return true;

        } else {
            return false;
        }

    }

    /**
     * 查询UUid是否存在
     * @param code
     * @return
     */

    @Override
    public User findUUidCode(String code) {
        return  userDao.findUUidCode(code);

    }

    /**
     * 用户注册改变状态
     * @param user
     */
    @Override
    public void updateStatus(User user) {
        userDao.updateStatus(user);
    }

    /**
     * 通过账号密码查询账户
     * @param user
     * @return
     */
    @Override
    public User findUserByUsername(User user) {
        return userDao.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public Favorite findFavorite(String rid, String uid) {
        return userDao.findFavorite(rid,uid);

    }

    @Override
    public void saveFavorite(String rid, String uid, String date) {
        userDao.saveFavorite(rid,uid,date);

    }


}
