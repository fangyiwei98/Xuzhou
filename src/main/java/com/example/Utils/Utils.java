package com.example.Utils;

import javax.servlet.http.HttpServletResponse;

public class Utils {

    //请求跨域方法
    public static HttpServletResponse settingCrossDomain(
            HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "7200");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        return response;
    }
}
