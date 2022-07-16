package com.lxc.store.controller;

import com.lxc.store.controller.ex.*;
import com.lxc.store.entity.BaseEntity;
import com.lxc.store.entity.User;
import com.lxc.store.service.IUserService;
import com.lxc.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author xc
 * @date 2022/5/23 21:44
 */
//@Controller
@RestController  //    @RestController  ==  @Controller  +  @ResponseBody
@RequestMapping("users")
public class UserController extends BaseEntity {

    @Autowired
    private IUserService userService;

    /**
     * 数据接收方式1：请求方法的参数列表设置为pojo类型来接收前端的数据
     * SpringBoot会将前端url地址中参数名与pojo类的属性名进行比较注入，一致则将值注入到pojo类对应的属性上
     * */

    @RequestMapping("reg")
    //@ResponseBody //表示此方法的响应结果以json格式进行数据的响应到前端
    public JsonResult<Void> reg(User user){
        //创建响应结果对象
        JsonResult<Void> result = new JsonResult<>();
        userService.reg(user);
        result.setState(200);
        result.setMessage("用户注册成功");
        return result;
    }

    /**
     * SpringBoot的《约定大于配置的开发理念》   省略大量的配置或注解
     *
     * 数据接收方式1：请求方法的参数列表设置为非pojo类型来接收前端的数据
     * SpringBoot会将前端url地址中参数名与方法参数名进行比较注入，一致则将自动完成值的依赖注入
     * */

    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        User user = userService.login(username,password);
        //向Session对象中绑定数据（该session是全局的）
        session.setAttribute("uid",user.getUid());
        session.setAttribute("username",user.getUsername());
        return new JsonResult<User>(200,user);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword,String newPassword,HttpSession session){
        Integer id = (Integer) session.getAttribute("uid");
        String username = (String)session.getAttribute("username");
        userService.changePassword(id,username,oldPassword,newPassword);
        return new JsonResult<Void>(200);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        User user = userService.getByUid((Integer) session.getAttribute("uid"));
        return new JsonResult<>(200,user);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(HttpSession session,User user){
        Integer uid = (Integer) session.getAttribute("uid");
        String username = (String) session.getAttribute("username");
        userService.changeInfo(uid,username,user);
        return new JsonResult<Void>(200);
    }

    //上传文件的最大值
    public static final int AVATAR_MAX_SIZE = 10*10*1024;
    //上传文件的类型
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    static {
        AVATAR_TYPE.add("images/jpeg");
        AVATAR_TYPE.add("images/png");
        AVATAR_TYPE.add("images/bmp");
        AVATAR_TYPE.add("images/gif");
    }

    /**
     * MultipartFile接口是SpringMVC提供的一个接口，为我们包装了获取文件类型的数据（任何类型的file文件都可以接收）
     * SpringBoot整合了SpringMVC，只需要在处理请求的方法参数列表上声明一个参数类型为MultipartFile的参数，然后SpringBoot
     * 自动将传递给服务器的文件数据赋值给这个参数
     *
     * @RequestParam("参数") 表示请求中的参数，将请求中的参数注入请求处理方法的某个参数上，如果名称不一致则可以使用
     * @param session
     * @param file
     * @return
     */
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,@RequestParam("file") MultipartFile file){
        if(file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }
        if(file.getSize() > AVATAR_MAX_SIZE){
            throw new FileSizeException("文件大小超出限制");
        }
        String contentType = file.getContentType();
        if( AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("不支持此文件类型");
        }
        //上传的文件 .../upload/文件.png
        String parent = session.getServletContext().getRealPath("upload");
        //File对象指向这个路径，File是否存在
        File dir = new File(parent);
        if(!dir.exists()){ // 检测目录是否存在
            dir.mkdirs(); //创建当前的目录
        }
        //获取文件名称，使用UUID工具生成一个新的字符串作为文件名
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename:"+originalFilename);
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String filename = UUID.randomUUID().toString().toUpperCase()+suffix;
        //创建一个空文件 拷贝上传的文件
        File dest = new File(dir,filename);
        //拷贝
        try {
            file.transferTo(dest);
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
            }catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }
        Integer uid = (Integer)session.getAttribute("uid");
        String username = (String)session.getAttribute("username");
        //返回头像的路径/upload/test.png
        String avatar = "/upload/" + filename;
        userService.changeAvatar(uid,avatar,username);

        //返回用户头像的路径给前端页面，用于以后的头像展示使用
        return new JsonResult<>(200,avatar);
    }

/*
    @RequestMapping("reg")
    //@ResponseBody //表示此方法的响应结果以json格式进行数据的响应到前端
    public JsonResult<Void> reg(User user){
        //创建响应结果对象
        JsonResult<Void> result = new JsonResult<>();
        try {
            userService.reg(user);
            result.setState(200);
            result.setMessage("用户注册成功");
        } catch (UsernameDuplicatedException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        } catch (InsertException e){
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }
        return result;
    }

 */
}
