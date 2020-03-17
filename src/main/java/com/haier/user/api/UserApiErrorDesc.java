package com.haier.user.api;

import java.util.HashMap;
import java.util.Map;

/**
 * 海尔用户中心接口错误code转描述
 * tity
 * 2018.7.31
 */
public class UserApiErrorDesc {
    public static class RegisterError{
        private static Map<String,String> errors = new HashMap<>();
        static {
            errors.put("1001","Token为空");
            errors.put("1002","Token参数格式不合法");
            errors.put("1003","Token验证不通过，服务器端未找到");
            errors.put("1004","验证码的code校验不通过");
            errors.put("1005","密码为空");
            errors.put("1006","密码强度不符合");
            errors.put("1007","userName为空或已注册");
            errors.put("1008","手机和邮箱必填一个");
            errors.put("1009","手机格式不对");
            errors.put("1010","邮箱格式不对");
            errors.put("1011","手机已被注册");
            errors.put("1012","邮箱已被注册");
            errors.put("2001","其它异常");
        }

        /**
         * 返回错误描述
         * @param code
         * @return
         */
        public static String getDesc(String code){
            String desc = errors.get(code);
            if(desc==null){
                desc = "未知错误";
            }
            return desc;
        }
    }

    public static class LoginError{
        private static Map<String,String> errors = new HashMap<>();
        static {
            errors.put("1001","Token为空");
            errors.put("1002","Token参数格式不合法");
            errors.put("1003","Token验证不通过，服务器端未找到");
            errors.put("1004","用户不存在");//Account参数为空
            errors.put("1005","密码错误");//用户已注册
        }
        /**
         * 返回错误描述
         * @param code
         * @return
         */
        public static String getDesc(String code){
            String desc = errors.get(code);
            if(desc==null){
                desc = "未知错误";
            }
            return desc;
        }
    }
}
