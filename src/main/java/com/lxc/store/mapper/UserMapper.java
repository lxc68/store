package com.lxc.store.mapper;

import com.lxc.store.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 用户模块的持久层接口
 * @author xc
 * @date 2022/5/23 16:46
 */
//@Mapper
public interface UserMapper {
    /**
     * 插入用户数据
     * @param user
     * @return 影响的行数作为返回值，根据返回值判断sql语句是否执行成功
     */
    Integer insert(User user);

    /**
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 根据用户id修改密码
     * @param uid
     * @param password
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updatePasswordByUid(Integer uid, String password, String modifiedUser, Date modifiedTime);

    /**
     * 根据用户的id查询用户的数据
     * @param uid
     * @return
     */
    User findByUid(Integer uid);
    /**
     * 更新用户的信息
     * @param user 用户数据
     * @return 返回受影响的行数
     */
    Integer updateInfoByUid(User user);

    /**
     * @Param("参数") 参数指的是SQL映射文件中#{}占位符的变量名，
     *      解决的问题，当SQL语句的占位符和映射的接口方法参数名不一致时，需要将某个参数强行注入到某个占位符变量上时，可以使用@Param这个注解进行标注映射的关系
     * 根据用户id上传用户的头像
     * @param uid
     * @param avatar
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateAvatarByUid(@Param("uid") Integer uid, @Param("avatar") String avatar, @Param("modifiedUser")String modifiedUser, @Param("modifiedTime")Date modifiedTime);
}
