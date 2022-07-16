package com.lxc.store.controller;

import com.lxc.store.controller.ex.*;
import com.lxc.store.service.ex.*;
import com.lxc.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * 表示控制层类的基类，主要用来进行异常的捕获处理
 * @author xc
 * @date 2022/5/23 21:54
 */
public class BaseController {
    public static final int OK = 200;

    //请求处理方法 这个方法的返回值就是需要传递给前端的数据
    //自动将异常对象传递给此方法的参数列表上
    //当项目中产生了异常，被统一拦截到此方法中，这个方法此时就是充当请求处理方法，方法的返回值直接给到前端
    @ExceptionHandler({ServiceException.class,FileUploadException.class})  //用于统一处理抛出的异常
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if(e instanceof UsernameDuplicatedException){
            result.setState(4000);
            result.setMessage("用户名被占用");
        }else if(e instanceof InsertException){
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }else if(e instanceof UserNotFoundException){
            result.setState(5001);
            result.setMessage("用户未注册的异常");
        }else if(e instanceof PasswordNotMatchException){
            result.setState(5002);
            result.setMessage("该用户密码错误的异常");
        }else if(e instanceof UpdateException){
            result.setState(5003);
            result.setMessage("更新数据失败的异常");
        }else if (e instanceof FileEmptyException) {
            result.setState(6000);
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
        } else if (e instanceof FileStateException) {
            result.setState(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
        } else if (e instanceof AddressCountLimitException) {
            result.setState(4003);
        }
        return result;
    }

    /**
     * 获取session对象中的uid
     * @param session
     * @return
     */
    protected final Integer getuidFromSession(HttpSession session){
        return  Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取session对象中的uid
     * @param session
     * @return
     */
    protected final String getusernameFromSession(HttpSession session){
        return  session.getAttribute("username").toString();
    }
}
