package com.barry.service;

import com.barry.pojo.Users;
import com.barry.pojo.bo.UserBo;

public interface UserService {
    /**
     * 判断用户是否存在
     */
    public boolean queryUsernameIsExist(String name);

    /**
     * 注册用户
     * @return
     */
    public Users CreateUser(UserBo userBo);
}
