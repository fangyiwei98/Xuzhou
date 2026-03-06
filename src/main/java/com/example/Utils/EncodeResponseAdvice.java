//package com.example.Utils;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
///**
// * @author monkey
// * @desc 返回数据加密
// * @date 2018/10/25 20:17
// */
//@ControllerAdvice(basePackages = "com.example.Controller")
//public class EncodeResponseAdvice implements ResponseBodyAdvice{
//    private final static Logger logger = LoggerFactory.getLogger(EncodeResponseAdvice.class);
//
//    @Override
//    public boolean supports(MethodParameter methodParameter, Class aClass) {
//        return true;
//    }
//
//    @Override
//    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
//        boolean encode = false;
//        if (methodParameter.getMethod().isAnnotationPresent(SecretAnnotation.class)) {
//            //获取注解配置的包含和去除字段
//            SecretAnnotation serializedField = methodParameter.getMethodAnnotation(SecretAnnotation.class);
//            //出参是否需要加密
//            encode = serializedField.outEncode();
//        }
//        if (encode) {
//            logger.info("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行加密");
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
//                return AESUtil.encrypt(result);
//            } catch (Exception e) {
//                e.printStackTrace();
//                logger.error("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行解密出现异常："+e.getMessage());
//            }
//        }
//        return body;
//    }
//}
//
