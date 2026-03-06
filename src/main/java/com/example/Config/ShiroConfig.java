package com.example.Config;


import com.example.shiro.CustomRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @Author yuanhaoyue swithaoy@gmail.com
 * @Description shiro 配置
 * @Date 2018-03-28
 * @Time 17:21
 */
@Configuration
public class ShiroConfig {


    @Autowired
    private CustomRealm customRealm;


    @Bean
    public DefaultWebSecurityManager getDefaultSecurityManager(@Qualifier("sessionManager") SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //自定义的shiro session 缓存管理器
        securityManager.setSessionManager(sessionManager);

        //自定义的身份认证和权限认证
        securityManager.setRealm(customRealm);

        return securityManager;
    }




    /**
     * 过滤器默认权限表 {anon=anon, authc=authc, authcBasic=authcBasic, logout=logout,
     * noSessionCreation=noSessionCreation, perms=perms, port=port,
     * rest=rest, roles=roles, ssl=ssl, user=user}
     * <p>
     * anon, authc, authcBasic, user 是第一组认证过滤器
     * perms, port, rest, roles, ssl 是第二组授权过滤器
     * <p>
     * user 和 authc 的不同：当应用开启了rememberMe时, 用户下次访问时可以是一个user, 但绝不会是authc,
     * 因为authc是需要重新认证的, user表示用户不一定已通过认证, 只要曾被Shiro记住过登录状态的用户就可以正常发起请求,比如rememberMe
     * 以前的一个用户登录时开启了rememberMe, 然后他关闭浏览器, 下次再访问时他就是一个user, 而不会authc
     *
     * @param securityManager 初始化 ShiroFilterFactoryBean 的时候需要注入 SecurityManager
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(DefaultWebSecurityManager securityManager) {



        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        RoleOrFilter roleOrFilter = new RoleOrFilter(); //自定义权限拦截器
        //MyFilter myFilter=new MyFilter();//登陆状态拦截器
        //设置自定义filter---
        Map<String, Filter> filtersMap = new LinkedHashMap<>();
        //这里很重要：在springboot中 如何使用自定义的拦截器
        filtersMap.put("e-perms",roleOrFilter);//可以配置RoleOrFilter的Bean
        //filtersMap.put("myfilter",myFilter);//配置登陆状态拦截器



        shiroFilterFactoryBean.setFilters(filtersMap);


        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        shiroFilterFactoryBean.setLoginUrl("/User/login");
        // 设置无权限时跳转的 url;
        shiroFilterFactoryBean.setUnauthorizedUrl("/User/tologin");




        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();


        //放开图片拦截
        filterChainDefinitionMap.put("/problemimg/**", "anon");
        filterChainDefinitionMap.put("/yanghuimg/**", "anon");
        filterChainDefinitionMap.put("/outadmin/**", "anon");
        filterChainDefinitionMap.put("/outfinish/**", "anon");
        filterChainDefinitionMap.put("/sgxcadmin/**", "anon");
        filterChainDefinitionMap.put("/sgxcuser/**", "anon");



        //开放登陆接口
        filterChainDefinitionMap.put("/User/login", "anon");
        //退出登陆接口
        filterChainDefinitionMap.put("/User/logout", "anon");
        //第一次登录修改密码
        filterChainDefinitionMap.put("User/updatePwd","anon");

        //消息系统
        filterChainDefinitionMap.put("/Message/**", "e-perms[巡检人员|养护人员|管理人员]");

        //超级管理员注册用户和管理员
        filterChainDefinitionMap.put("/SVIP/**","perms[超级管理员]");

        //上传excel文件
        filterChainDefinitionMap.put("/myExcel/excelimport","perms[超级管理员]");
        //打印外来任务单表和目录表
        filterChainDefinitionMap.put("/file/createword","e-perms[超级管理员|管理人员]");
        //创建施工现场
        filterChainDefinitionMap.put("/SGXC/createSGXC", "e-perms[超级管理员|管理人员]");
        //查询所有施工现场
        filterChainDefinitionMap.put("/SGXC/getGcname", "e-perms[超级管理员|管理人员|巡检人员]");
        //根据施工现场工程名称获取上传的图片
        filterChainDefinitionMap.put("/SGXC/getPicBygcname", "e-perms[超级管理员|管理人员]");
        //施工现场批量下载
        filterChainDefinitionMap.put("/SGXC/patchdownload", "e-perms[超级管理员|管理人员]");
        //根据任务号查询上报的问题
        filterChainDefinitionMap.put("/Problem/getProBytaskcode", "e-perms[超级管理员|管理人员]");
        //查询信息，需要权限“管理人员”
        filterChainDefinitionMap.put("/Info/**", "anon");
        //任务下发
        filterChainDefinitionMap.put("/Task/xiafa","e-perms[超级管理员|管理人员]");
        //任务分派
        filterChainDefinitionMap.put("/Task/specialtask","e-perms[超级管理员|管理人员]");
        //外来任务下发
        filterChainDefinitionMap.put("/Task/outTask","e-perms[超级管理员|管理人员]");
        //外来任务查询
        filterChainDefinitionMap.put("/Task/showOuttask","e-perms[超级管理员|管理人员]");
        //外来任务审核
        filterChainDefinitionMap.put("/Task/shenheoutTask","e-perms[超级管理员|管理人员]");
        //外来任务驳回
        filterChainDefinitionMap.put("/Task/nopassWLTask","e-perms[超级管理员|管理人员]");
        //实时巡检轨迹
        filterChainDefinitionMap.put("/Info/realtrace","e-perms[超级管理员|管理人员]");
        //查询与定位养护信息
        filterChainDefinitionMap.put("/Yanghu/selectandposition","e-perms[超级管理员|管理人员]");
        //修改养护信息
        filterChainDefinitionMap.put("/Yanghu/updateinfo","e-perms[超级管理员|管理人员]");
        //删除养护信息
        filterChainDefinitionMap.put("/Yanghu/deleteinfo","e-perms[超级管理员|管理人员]");
        //管理员评分
        filterChainDefinitionMap.put("/Yanghu/score","e-perms[超级管理员|管理人员]");
        //任务获取（根据任务性质查询）
        filterChainDefinitionMap.put("/Task/showTask","e-perms[超级管理员|管理人员]");
        //筛选巡检待审核
        filterChainDefinitionMap.put("/Task/showXunjianTask","e-perms[超级管理员|管理人员]");
        //根据type和character查询任务
        filterChainDefinitionMap.put("/Task/showTaskbyBoth","e-perms[超级管理员|管理人员]");
        //计划管理 （制定新计划）
        filterChainDefinitionMap.put("/Plan/addplan","e-perms[超级管理员|管理人员]");
        //计划管理 （查询计划）
        filterChainDefinitionMap.put("/Plan/showplan","e-perms[超级管理员|管理人员]");
        //计划管理 （修改计划）
        filterChainDefinitionMap.put("/Plan/updateplan","e-perms[超级管理员|管理人员]");
        //计划管理 （删除计划）
        filterChainDefinitionMap.put("/Plan/deleteplan","e-perms[超级管理员|管理人员]");
        //问题上报查询
        filterChainDefinitionMap.put("/Problem/showproblem","e-perms[超级管理员|管理人员]");
        //问题上报结果返回
        filterChainDefinitionMap.put("/Problem/returnproblem","e-perms[超级管理员|管理人员]");
        //管理员修改任务状态（任务已完成）
        filterChainDefinitionMap.put("/Task/finishTask","e-perms[超级管理员|管理人员]");
        //巡检养护批量审核通过
        filterChainDefinitionMap.put("/Task/passTask","e-perms[超级管理员|管理人员]");
        //巡检养护批量审核驳回
        filterChainDefinitionMap.put("/Task/nopassTask","e-perms[超级管理员|管理人员]");
        //养护任务驳回
        filterChainDefinitionMap.put("/Task/nopassYHTask","e-perms[超级管理员|管理人员]");
        //管理人员填写处理结果
        filterChainDefinitionMap.put("/Task/CLJG","e-perms[超级管理员|管理人员]");
        //管理人员直接结束属于但不要处理的外来任务(admin)
        filterChainDefinitionMap.put("/Task/JSoutTask","e-perms[超级管理员|管理人员]");
        //将任务状态改为已查看
        filterChainDefinitionMap.put("/Task/changestatue","e-perms[超级管理员|管理人员]");
        //将根据前端信息查询任务
        filterChainDefinitionMap.put("/Task/selectOutTaskInweb","e-perms[超级管理员|管理人员]");

        //***************以下是APP端接口拦截**********************
        //********以下是巡检人员接口
        //施工现场上报
        filterChainDefinitionMap.put("/SGXC/reportSGXC","perms[巡检人员]");
        //获取施工现场
        filterChainDefinitionMap.put("/Xunjian/start","perms[巡检人员]");
        //问题上报
        filterChainDefinitionMap.put("/Problem/reportproblem","perms[巡检人员]");
        //上报记录
        filterChainDefinitionMap.put("/Problem/record","perms[巡检人员]");
        //暂停巡检任务
        filterChainDefinitionMap.put("/Task/pauseTask","perms[巡检人员]");
        //结束巡检任务
        filterChainDefinitionMap.put("/Task/finishXunjianTask","perms[巡检人员]");
        //巡检人员直接上报外来任务
        filterChainDefinitionMap.put("/Task/reportWLTask","perms[巡检人员]");
        //********以下是养护人员接口
        //获取未接收的外来任务
        filterChainDefinitionMap.put("/Task/getOutTask","e-perms[巡检人员|养护人员]");
        //搜索外来任务
        filterChainDefinitionMap.put("/Task/selectOutTaskByroad","e-perms[巡检人员|养护人员]");
        //接取外来任务
        filterChainDefinitionMap.put("/Task/receiveOutTask","e-perms[巡检人员|养护人员]");
        //获取已接收的外来任务
        filterChainDefinitionMap.put("/Task/getMyOutTask","e-perms[巡检人员|养护人员]");
        //完成外来任务
        filterChainDefinitionMap.put("/Task/finishOutTask","e-perms[巡检人员|养护人员]");
        //删除外来任务
        filterChainDefinitionMap.put("/Task/deleteOutTask","e-perms[巡检人员|养护人员]");
        //历史外来任务
        filterChainDefinitionMap.put("/Task/historyOutTask","e-perms[巡检人员|养护人员]");
        //养护结果上报
        filterChainDefinitionMap.put("/Yanghu/problem", "perms[养护人员]");
        //养护记录
        filterChainDefinitionMap.put("/Yanghu/record", "perms[养护人员]");
        //根据id查询经纬度
        filterChainDefinitionMap.put("/Task/getYHtask", "perms[养护人员]");
        //********以下是巡检人员和养护人员共用接口
        //我的任务
        filterChainDefinitionMap.put("/Task/myTask","e-perms[巡检人员|养护人员]");
        //获取任务
        filterChainDefinitionMap.put("/Task/showTaskstatue","e-perms[巡检人员|养护人员]");
        //任务接取（主动接取）
        filterChainDefinitionMap.put("/Task/receive", "e-perms[巡检人员|养护人员]");
        //任务删除
        filterChainDefinitionMap.put("/Task/deletetask", "e-perms[巡检人员|养护人员]");


        //filterChainDefinitionMap.put("/**","anon");




        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterChainDefinitionMap.put("/**", "authc");//"myfilter"

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //设置自定义拦截器
        shiroFilterFactoryBean.setFilters(filtersMap);
        System.out.println("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }




    /**
     * 自定义的 shiro session 缓存管理器，用于跨域等情况下使用 token 进行验证，不依赖于sessionId
     * @return
     */
    @Bean(name="sessionManager")
    public SessionManager sessionManager(){
        //将我们继承后重写的shiro session 注册
        ShiroSession shiroSession = new ShiroSession();
        //如果后续考虑多tomcat部署应用，可以使用shiro-redis开源插件来做session 的控制，或者nginx 的负载均衡

        shiroSession.setSessionDAO(new EnterpriseCacheSessionDAO());

        shiroSession.setSessionIdUrlRewritingEnabled(false);
        return shiroSession;
    }


}