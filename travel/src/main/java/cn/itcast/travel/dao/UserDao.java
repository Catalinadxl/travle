package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.User;

import java.sql.Date;

public interface UserDao {

    boolean checkUsername(String username);

    int saveUser(User user);

    User findUUidCode(String code);

    void updateStatus(User user);

    User findUserByUsernameAndPassword(String username, String password);

    Favorite findFavorite(String rid, String uid);

    void saveFavorite(String rid, String uid, String date);
}
