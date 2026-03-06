package com.example.Config;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class MyFilter extends AuthenticatingFilter {

    /**
     * @Summar
     * @Param: [request, response]
     * @Return: org.apache.shiro.authc.AuthenticationToken
     * @Author: TheRaging
     * @Date: 2020/10/26 23:46
     * @Description TODO 创建Token
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("进到了创建TOKEN的方法中");

        return null;
    }

    /**
     * @Summary
     * @Param: [request, response, mappedValue]
     * @Return: boolean
     * @Author: TheRaging
     * @Date: 2020/10/26 23:43
     * @Description 判断是否登录,主要用于权限校验,     false的话就代表未登录，直接进入onAccessDenied方法，
     * 如果为true就代表已经登录过，然后直接访问控制器
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        /*//权限访问逻辑
        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;
        //没有权限访问
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        for (int i = 0; i < rolesArray.length; i++) {
            //若当前用户是rolesArray中的任何一个，则有权限访问
            if (subject.hasRole(rolesArray[i])) {
                return true;
            }
        }*/
        ShiroHttpServletRequest rrr = (ShiroHttpServletRequest)request;
        String s = rrr.getRequestURI();
        System.out.println(s);
        String token =  getRequestToken((HttpServletRequest)request);
        if(StringUtils.isEmpty(token)){
            //没有token的话 就去登录
            System.out.println("tojkren-----------"+token);
            return  false;
        }else {
            //有token的话 选哟解析验证token 验证通过的话就直接权限校验或者直接访问，如果校验不通过的话就拒绝登录
            System.out.println("获取到了TOKEN-----------"+token);
            return  true;
        }

    }

    /**
     * @Summary
     * @Param: [request, response]
     * @Return: boolean
     * @Author: TheRaging
     * @Date: 2020/10/26 23:44
     * @Description 未登录状态进入此方法，
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        System.out.println("未登录状态进入到了onAccessDenied方法中");
        return false;
    }

    /**
     * @Summary
     * @Param: [token, e, request, response]
     * @Return: boolean
     * @Author: TheRaging
     * @Date: 2020/10/26 23:46
     * @Description TODO  登录失败的一个处理
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        System.out.println("进入到了onLoginFailure方法中");
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
           /* R r = R.error(HttpStatus.SC_UNAUTHORIZED, throwable.getMessage());

            String json = new Gson().toJson(r);
            httpResponse.getWriter().print(json);*/
            httpResponse.getWriter().print("2123");
        } catch (IOException e1) {

        }
        return false;
    }

    /**
     * @Summary
     * @Param: [token, subject, request, response]
     * @Return: boolean
     * @Author: TheRaging
     * @Date: 2020/10/26 23:47
     * @Description TODO  登录成功的一个处理
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token,
                                     Subject subject, ServletRequest request, ServletResponse response)
            throws Exception {
        System.out.println("进入到了onLoginSuccess方法中");
        Session session = SecurityUtils.getSubject().getSession();
        /*UserInfo user = (UserInfo) subject.getPrincipal();
        session.setAttribute("userName", user.getUserName());
        session.setAttribute("userId", user.getUserId());*/
        issueSuccessRedirect(request, response);
        return false;
    }


    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String token = httpRequest.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter("token");
        }

        return token;
    }
}