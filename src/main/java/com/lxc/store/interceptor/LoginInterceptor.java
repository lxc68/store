package com.lxc.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义一个拦截器
 * @author xc
 * @date 2022/5/24 21:28
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 检测全局session对象中是否有uid数据，有的话则放行，没有的话重定向到登录页面
     * @param request
     * @param response
     * @param handler  处理器(url+Controller：映射)
     * @return  true 放行当前的请求 false 则拦截所有的请求
     * @throws Exception
     */
    //在调用所有处理请求的方法之前被自动调用执行的方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object obj = request.getSession().getAttribute("uid");
        if(obj==null){
            response.sendRedirect("/web/login.html");
            //结束后序的调用
            return false;
        }
        return true;
    }
}
