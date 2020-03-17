package com.jhmis.core.web;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/*
自定义springboot的错误控制器
 */
@RestController
@RequestMapping("/error")
public class CustomerErrorController {

    /**
     * 未知资源
     * @return
     */
    @RequestMapping(value ="/404" )
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AjaxJson error404() {
        return AjaxJson.fail(Constants.ERROR_CODE_404_ERROR,Constants.ERROR_DESC_404_ERROR);
    }

    /**
     * 登录异常
     * @return
     */
    @RequestMapping(value ="/400" )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AjaxJson error400(Exception e) {
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
     * 登录异常
     * @return
     */
    @RequestMapping(value ="/401" )
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public AjaxJson error401() {
        return AjaxJson.fail(Constants.ERROR_CODE_AUTH_ERROR,Constants.ERROR_DESC_AUTH_ERROR);
    }

    /**
     * 没有权限
     * @return
     */
    @RequestMapping(value ="/403" )
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AjaxJson error403() {
        return AjaxJson.fail(Constants.ERROR_CODE_ROLE_ERROR,Constants.ERROR_DESC_ROLE_ERROR);
    }

    /**
     * 内部错误
     * @return
     */
    @RequestMapping(value ="/500" )
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AjaxJson error500() {
        return AjaxJson.fail(Constants.ERROR_CODE_500_ERROR,Constants.ERROR_DESC_500_ERROR);
    }
}
