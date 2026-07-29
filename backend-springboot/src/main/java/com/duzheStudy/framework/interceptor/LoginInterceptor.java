package com.duzheStudy.framework.interceptor;

import com.duzheStudy.common.utils.JwtUtils;
import com.duzheStudy.common.utils.UserUtils;
import com.duzheStudy.modules.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    /*
     * 在请求处理之前进行调用(Controller方法调用之前)
     * 若返回true请求将会继续执行后面的操作
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        // 如果不是映射到方法不拦截 直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //验证token
        if (null == token || "".equals(token) || !JwtUtils.verify(token)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(401);
            try (PrintWriter writer = response.getWriter()) {
                writer.print("login fail");
            } catch (Exception e) {
                logger.error("login token error is {}", e.getMessage());
            }
            return false;
        }
        //若token验证成功，把用户信息存储在ThreadLocal
        User user = JwtUtils.getUserByToken(token);
        UserUtils.setLoginUser(user);
        return true;
    }
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (!(handler instanceof HandlerMethod)) {
//            logger.debug("Request not mapped to a method, bypassing interceptor: {}", request.getRequestURI());
//            return true;
//        }
//
//        String token = request.getHeader("Authorization");
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7);
//            logger.debug("Extracted token: {}", token);
//        } else {
//            logger.warn("No valid Bearer token found in Authorization header");
//            sendErrorResponse(response, 401, "Login failed: Missing or invalid Authorization header");
//            return false;
//        }
//
//        if (null == token || "".equals(token.trim())) {
//            logger.warn("Token is null or empty");
//            sendErrorResponse(response, 401, "Login failed: Token is missing or empty");
//            return false;
//        }
//
//        if (!JwtUtils.verify(token)) {
//            sendErrorResponse(response, 401, "Login failed: Invalid or expired token");
//            return false;
//        }
//
//        User user = JwtUtils.getUserByToken(token);
//        if (user == null) {
//            logger.error("Failed to extract user from token: {}", token);
//            sendErrorResponse(response, 401, "Login failed: Unable to retrieve user from token");
//            return false;
//        }
//
//        UserUtils.setLoginUser(user);
//        logger.debug("User set in ThreadLocal: {}", user.getUserName());
//        return true;
//    }
//    private void sendErrorResponse(HttpServletResponse response, int status, String message) {
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json; charset=utf-8");
//        response.setStatus(status);
//        try (PrintWriter writer = response.getWriter()) {
//            writer.print("{\"code\": " + status + ", \"message\": \"" + message + "\"}");
//        } catch (Exception e) {
//            logger.error("Error writing response: {}", e.getMessage(), e);
//        }
//    }
    /***
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("执行了拦截器的postHandle方法");
    }

    /***
     * 整个请求结束之后被调用，也就是在DispatchServlet渲染了对应的视图之后执行（主要用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清除线程变量
        UserUtils.removeUser();
    }

}
