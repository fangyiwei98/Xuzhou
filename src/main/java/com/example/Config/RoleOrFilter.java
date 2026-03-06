package com.example.Config;


import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class RoleOrFilter extends AuthorizationFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = this.getSubject(request, response);
        String[] perms = (String[]) ((String[]) mappedValue);
        String token =  getRequestToken((HttpServletRequest)request);

        boolean isPermitted = true;



        if(StringUtils.isEmpty(token)){
            //没有token的话 就去登录
            System.out.println("tojkren-----------"+token);
            return  false;
            //isPermitted=false;
        }else {
            //有token的话 选哟解析验证token 验证通过的话就直接权限校验或者直接访问，如果校验不通过的话就拒绝登录
            System.out.println("获取到了TOKEN-----------"+token);

            if (perms != null && perms.length > 0) {
                if (perms.length == 1) {
                    if (!isOneOfPermitted(perms[0], subject)) {
                        isPermitted = false;
                    }
                } else if (!isAllPermitted(perms,subject)) {
                    isPermitted = false;
                }


            }


            //return  true;
            //isPermitted=true;
            return isPermitted;
        }




        //return isPermitted;

    }

    /**
     * 以“，”分割的权限为并列关系的权限控制，分别对每个权限字符串进行“|”分割解析
     * 若并列关系的权限有一个不满足则返回false
     *
     * @param permStrArray 以","分割的权限集合
     * @param subject      当前用户的登录信息
     * @return 是否拥有该权限
     */
    private boolean isAllPermitted(String[] permStrArray, Subject subject) {
        boolean isPermitted = true;
        for (int index = 0, len = permStrArray.length; index < len; index++) {
            if (!isOneOfPermitted(permStrArray[index], subject)) {
                isPermitted = false;
            }
        }
        return isPermitted;
    }

    /**
     * 判断以“|”分割的权限有一个满足的就返回true，表示权限的或者关系
     *
     * @param permStr 权限数组种中的一个字符串
     * @param subject 当前用户信息
     * @return 是否有权限
     */
    private boolean isOneOfPermitted(String permStr, Subject subject) {
        boolean isPermitted = false;
        String[] permArr = permStr.split("\\|");
        if (permArr.length > 0) {
            for (int index = 0, len = permArr.length; index < len; index++) {
                if (subject.isPermitted(permArr[index])) {
                    isPermitted = true;
                }
            }
        }


        return isPermitted;
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



    /**
     * @Summary
     * @Param: [request, response]
     * @Return: boolean
     * @Author: TheRaging
     * @Date: 2020/10/26 23:44
     * @Description 未登录状态进入此方法，
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {

        System.out.println("未登录状态进入到了onAccessDenied方法中");
        return false;
    }

}


