package com.jhmis.modules.sys.security;

import com.jhmis.common.utils.JWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTFilter extends BasicHttpAuthenticationFilter {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取请求的token
     */
    private String getRequestToken(ServletRequest request){

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        //从header中获取token
        String token = httpRequest.getHeader(AUTHORIZATION_HEADER);

        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter(AUTHORIZATION_HEADER);
        }
        /*
        TODO 考虑是否要采用Bearer Token 形式
        if (authorizationHeader == null) {
            return null;
        }
        String[] authTokens = authorizationHeader.split(" ");
        if (authTokens == null || authTokens.length < 2) {
            return null;
        }*/
        return token;
    }

    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        String authorization = getRequestToken(request);
        return StringUtils.isNotBlank(authorization);
    }

    /**
     * 执行登录
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        String authorization = getRequestToken(request);
        JWTToken token = new JWTToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        //登录成功后把当前用户id,type放入request
        String userId = JWTUtils.getUserId(token.getToken());
        String userType = JWTUtils.getUserType(token.getToken());
        request.setAttribute("current_user_id", userId);
        request.setAttribute("current_user_type", userType);
        return true;
    }

    /**
     * 是否允许访问
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                return executeLogin(request, response);
            } catch (Exception e) {
                //LOGGER.debug(Exceptions.getStackTraceAsString(e));
                LOGGER.debug(e.getMessage());
                /*try {
                    WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }*/
                response401(request,response);
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
        //永远返回不拒绝，靠上面的是否允许来处理
        //当拒绝后，原本的代码输出response头部标识为401
    }

    /**
     * 在访问过来的时候检测是否为OPTIONS请求，如果是就直接返回true
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            setHeader(httpRequest,httpResponse);
            return true;
        }
        return super.preHandle(request,response);
    }

    /**
     * 为response设置header，实现跨域
     */
    private void setHeader(HttpServletRequest request,HttpServletResponse response){
        //跨域的header设置
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", request.getMethod());
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        //防止乱码，适用于传输JSON数据
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
    }

    /**
     * 将非法请求跳转到 /401
     */
    private void response401(ServletRequest req, ServletResponse resp) {
        try {
            HttpServletResponse response = (HttpServletResponse) resp;
            HttpServletRequest request = (HttpServletRequest) req;
            //response.sendRedirect("/error/401");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error/401");
            //调用forward()方法，转发请求
            requestDispatcher.forward(request,response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
