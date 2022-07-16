package com.lxc.store;

import com.lxc.store.entity.User;
import com.lxc.store.service.IUserService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
@MapperScan("com.lxc.store.mapper") //指定当前项目中Mapper接口路径位置，在项目启动的时候自动加载所有的接口文件
class StoreApplicationTests {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private IUserService userService;

    @Test
    void contextLoads() {
    }

    /**
     * 数据库连接池
     * 1.DBCP
     * 2.C3P0
     * 3.Hikari 号称最快的数据库连接池   管理数据库的连接对象
     * HikariProxyConnection@554510956 wrapping com.mysql.cj.jdbc.ConnectionImpl@3bec5821
     *
     * @throws SQLException
     */

    @Test
    void getConnection() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void changePassword(){
        userService.changePassword(9,"572016301","14819397960lxc","123");
    }

    @Test
    public void findByUid(){
        System.out.println(userService.getByUid(7));

    }
    @Test
    public void changeInfo(){
        User user = new User();
        user.setPhone("12345678910");
        user.setEmail("lxc@rr.com");
        user.setGender(0);
        userService.changeInfo(7,"管理员",user);
    }

    @Test
    public void changeAvatar(){
        userService.changeAvatar(7,"9090","5720163015");
    }

}
