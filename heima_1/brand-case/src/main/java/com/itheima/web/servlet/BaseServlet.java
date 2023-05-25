package com.itheima.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *  替换HttpServlet,根据请求的最后一段路径来进行方法分发
 */
public class BaseServlet extends HttpServlet {
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        //1.获取请求路径
        String url = req.getRequestURI();
        //2.获取最后一段路径，方法名
        int index = url.lastIndexOf('/');
        String methodName = url.substring(index + 1);
        //3。执行方法
        //3.1 获取BrandServlet/UserServlet字节码对象
        Class<? extends  BaseServlet> cls = this.getClass();
        //3.2 获取方法Method对象
        try {
            Method method = cls.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //3.3 执行方法
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


    }
}