package com.jhmis.core.web;

import com.drew.lang.StringUtil;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.Exceptions;
import com.jhmis.core.service.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * RestController统一异常处理
 */
@RestControllerAdvice
public class RestExceptionController {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    // 捕捉AuthenticationException,UnauthorizedException 未登录
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AuthenticationException.class,UnauthenticatedException.class})
    public AjaxJson handleUnauthenticated(Exception e) {
        //LOGGER.debug(e.getMessage());
        LOGGER.debug(Exceptions.getStackTraceAsString(e));
        return AjaxJson.fail(Constants.ERROR_CODE_AUTH_ERROR,Constants.ERROR_DESC_AUTH_ERROR);
    }

    // AuthorizationException,UnauthorizedException 未授权
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({AuthorizationException.class,UnauthorizedException.class})
    public AjaxJson handleUnauthorized(Exception e) {
        //LOGGER.debug(e.getMessage());
        LOGGER.debug(Exceptions.getStackTraceAsString(e));
        return AjaxJson.fail(Constants.ERROR_CODE_ROLE_ERROR,Constants.ERROR_DESC_ROLE_ERROR);
    }

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ShiroException.class)
    public AjaxJson handleShiroException(ShiroException e) {
        //LOGGER.debug(e.getMessage());
        LOGGER.debug(Exceptions.getStackTraceAsString(e));
        return AjaxJson.fail(Constants.ERROR_CODE_500_ERROR,Constants.ERROR_DESC_500_ERROR);
    }

    /**
     * 参数绑定异常
     */
    @ExceptionHandler({BindException.class, ConstraintViolationException.class, ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AjaxJson bindException(Exception e) {
        String errorMsg = Constants.ERROR_DESC_400_ERROR;
        if(e instanceof ConstraintViolationException) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            for (ConstraintViolation violation : ((ConstraintViolationException)e).getConstraintViolations()) {
                if(i>0) sb.append("\n");
                sb.append(violation.getMessage());
                i++;
            }
            errorMsg = sb.toString();
        } else if(e instanceof BindException){
            BindingResult result = ((BindException)e).getBindingResult();
            if(result.hasErrors()){
                StringBuilder sb = new StringBuilder();
                int i = 0;
                for (ObjectError error : result.getAllErrors()) {
                    if(i>0) sb.append("\n");
                    sb.append(error.getDefaultMessage());
                    i++;
                }
                errorMsg = sb.toString();
            }
        }
        return AjaxJson.fail(Constants.ERROR_CODE_400_ERROR,errorMsg);
    }


    /**
     * 处理业务逻辑异常
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public AjaxJson handleServiceException(ServiceException e) {
        String errorMsg = Constants.ERROR_DESC_EXCEPTION_ERROR;
        if(StringUtils.isNotBlank(e.getMessage())){
            errorMsg = e.getMessage();
        }
        return AjaxJson.fail(Constants.ERROR_CODE_EXCEPTION_ERROR,errorMsg);
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AjaxJson globalException(HttpServletRequest request, Exception e) {
        //LOGGER.debug(e.getMessage());
        LOGGER.debug(Exceptions.getStackTraceAsString(e));
        //return AjaxJson.fail(getStatus(request).toString(), e.getMessage());
        return AjaxJson.fail(getStatus(request).toString(), "系统错误");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}

