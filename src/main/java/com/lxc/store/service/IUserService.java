package com.lxc.store.service;

import com.lxc.store.entity.User;

/**
 * 用户模块业务层接口
 * @author xc
 * @date 2022/5/23 21:00
 */
public interface IUserService {
    /**
     *  用户注册方法
     * @param user
     */
    void reg(User user);

    /**
     * 登录方法
     * @param username
     * @param password
     * @return 当前匹配的用户数据，没有的话返回null，为了登录以后数据的展示
     */
    User login(String username,String password);

    /**
     * 修改密码
     * @param uid
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    void changePassword(Integer uid,String username,String oldPassword,String newPassword);

    /**
     * 根据用户的id查询用户的数据
     * @param uid
     * @return
     */
    User getByUid(Integer uid);

    /**
     * 更新用户数据
     * @param uid
     * @param username
     * @param user
     */
    void changeInfo(Integer uid,String username,User user);


    Integer changeAvatar(Integer uid,String avatar,String username);
}
