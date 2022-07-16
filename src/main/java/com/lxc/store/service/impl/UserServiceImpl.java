package com.lxc.store.service.impl;

import com.lxc.store.entity.User;
import com.lxc.store.mapper.UserMapper;
import com.lxc.store.service.IUserService;
import com.lxc.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/**
 * @author xc
 * @date 2022/5/23 21:02
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    /**
     * 调用mapper的方法把user传下去
     * @param user
     */
    @Override
    public void reg(User user) {
        //调用findByUsername(username)判断用户名是否被注册过
        String username = user.getUsername();
        User result = userMapper.findByUsername(user.getUsername());
        if(result!=null){
            //抛出异常
            throw new UsernameDuplicatedException("用户名被占用");
        }

        // 密码加密处理的实现：md5算法
        // （串  + password + 串） ----md5算法进行加密  连续加密三次
        // 盐值  + password + 盐值 ----盐值就是一个随机的字符串
        String oldPassword = user.getPassword();
        //生成一个盐值
        String salt = UUID.randomUUID().toString().toUpperCase();
        //将密码和盐值作为一个整体进行加密,忽略原有密码的强度提升了数据的安全性
        String md5password = getMd5(oldPassword,salt);
        user.setPassword(md5password);
        user.setSalt(salt);
        //补全数据
        user.setIsDelete(0);
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        //执行注册
        Integer rows = userMapper.insert(user);
        //判断是否注册成功
        if(rows!=1){
            throw new InsertException("用户注册过程中产生了未知的异常");
        }
    }

    @Override
    public User login(String username, String password) {

        //根据用户名称查询用户是否存在，若不存在的话抛出异常
        User result = userMapper.findByUsername(username);
        if (result==null){
            throw new UserNotFoundException("用户数据不存在");
        }
        //查看是否被删除了
        if(result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        //检测密码是否匹配
        String rightPassword = result.getPassword();
        String salt = result.getSalt();
        String md5Password = getMd5(password,salt);
        if (!md5Password.equals(rightPassword)) {
            throw new PasswordNotMatchException("密码错误");
        }
        //原因是数据的体积变小，提升数据性能（调优，中转）
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User user = userMapper.findByUid(uid);
        if(user==null||user.getIsDelete()==1) {
            throw new UserNotFoundException("用户不存在");
        }
        //密码比较
        String oldMd5Password = getMd5(oldPassword,user.getSalt());
        if(!oldMd5Password.equals(user.getPassword())){
            throw new PasswordNotMatchException("原密码不正确");
        }
        //设置新的密码
        String newMd5Password = getMd5(newPassword,user.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid,newMd5Password,username,new Date());
        if(rows!=1){
            throw new UpdateException("更新数据时产生未知异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {

        User user = userMapper.findByUid(uid);
        if(user==null||user.getIsDelete()==1){
            throw new UserNotFoundException("用户没有找到");
        }
        //为了性能
        User user1 = new User();
        user1.setUsername(user.getUsername());
        user1.setPhone(user.getPhone());
        user1.setEmail(user.getEmail());
        user1.setGender(user.getGender());
        return user1;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if(result==null||result.getIsDelete()==1) {
            throw new UserNotFoundException("用户没有找到");
        }
        user.setUid(uid);
        //user.setUsername(username);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        if(rows!=1){
            throw new UpdateException("更新发生未知的异常");
        }
    }

    @Override
    public Integer changeAvatar(Integer uid, String avatar, String username) {
        //查询当前的用户数据是否存在
        User result = userMapper.findByUid(uid);
        if(result==null||result.getIsDelete()==1)
            throw new UserNotFoundException("用户数据不存在");
        Integer rows = userMapper.updateAvatarByUid(uid,avatar,username,new Date());
        if(rows!=1){
            throw new UpdateException("更新用户头像失败");
        }
        return null;
    }

    /** 定义一个md5算法的加密处理 */
    private String getMd5(String password,String salt){
        for(int i = 0;i<3;++i){
            //md5加密算法方法的调用
            password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
