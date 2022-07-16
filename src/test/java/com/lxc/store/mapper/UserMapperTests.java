package com.lxc.store.mapper;

import com.lxc.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author xc
 * @date 2022/5/23 19:53
 */

@SpringBootTest
//RunWith：表示启动这个单元测试类（单元测试类是不能够运行的），需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class UserMapperTests {

    //这里会报错的原因是：idea有检测的功能，接口是不能直接创建Bean的，但是底层是有动态代理来解决这个问题的
    @Autowired
    private UserMapper userMapper;

    /**
     * 单元测试方法特点：必须被Test注解修饰，返回值必须是void，方法参数列表不指定任何类型，方法的访问修饰符必须是public
     */
    @Test
    public void insert(){
        User user = new User();
        user.setUsername("Tom");
        user.setPassword("123");
        System.out.println(userMapper.insert(user));
    }

    @Test
    public void findByUsername(){
        System.out.println(userMapper.findByUsername("Tom").toString());
    }

    @Test
    public void  findByUid(){
        System.out.println(userMapper.findByUid(1));
    }

    @Test
    public void updatePasswordByUid(){
        User user = userMapper.findByUid(1);
        System.out.println(userMapper.updatePasswordByUid(user.getUid(),"111",user.getUsername(),new Date()));
    }

    @Test
    public void updateInfoByUid(){
        User user = new User();
        user.setUid(7);
        user.setPhone("12345678910");
        user.setEmail("test@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
        //System.out.println(userMapper.findByUid(11));
    }

    @Test
    public void updateAvatarBuUid(){
        User user = userMapper.findByUid(7);
        userMapper.updateAvatarByUid(user.getUid(),"/upload/avatar.png",user.getUsername(),new Date());
    }
}
