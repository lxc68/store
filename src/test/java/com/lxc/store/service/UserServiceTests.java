package com.lxc.store.service;

import com.lxc.store.entity.User;
import com.lxc.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xc
 * @date 2022/5/23 21:14
 */
@SpringBootTest
//RunWith：表示启动这个单元测试类（单元测试类是不能够运行的），需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class UserServiceTests {

    //这里会报错的原因是：idea有检测的功能，接口是不能直接创建Bean的，但是底层是有动态代理来解决这个问题的
    @Autowired
    private IUserService userService;

    /**
     * 单元测试方法特点：必须被Test注解修饰，返回值必须是void，方法参数列表不指定任何类型，方法的访问修饰符必须是public
     */
    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("mimimi");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("reg OK");
        } catch (ServiceException e) {
            //获取类的对象，再获取类的名称
            System.out.println(e.getClass().getSimpleName());
            //获取异常的具体描述信息
            System.out.println(e.getMessage());
        } finally {
        }
    }

    @Test
    public void login(){
        User user = userService.login("5720163015","14819397960lxc");
        System.out.println(user.toString());
    }
}
