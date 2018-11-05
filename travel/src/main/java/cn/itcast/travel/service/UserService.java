package cn.itcast.travel.service;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.User;

import java.sql.Date;

public interface UserService {

    boolean checkUsername(String username);

    boolean saveUser(User user);

    User findUUidCode(String code);

    void updateStatus(User user);

    User findUserByUsername(User user);

    Favorite findFavorite(String rid, String uid);

    void saveFavorite(String rid, String uid, String date);
}
