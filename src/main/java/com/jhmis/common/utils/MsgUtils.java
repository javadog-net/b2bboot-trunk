package com.jhmis.common.utils;

import cn.hutool.http.HttpRequest;
import com.jhmis.common.Exception.ShopMsgException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.utils
 * @Author: hdx
 * @CreateTime: 2019-09-25 10:29
 * @Description: 需求相关工具类
 */
@Component
public class MsgUtils {
    /**
     * 生成订单编号 如20190925102763xxx
     * @return
     */

    public String getOrderIdByTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<2;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }


    /**
     * @Title: sendApp
     * @Description: app 推送方法 (传入参数形式如下)
     * @return
     * @throws Exception
     * @return boolean
     * @author tc
     * @date 2019年9月26日上午11:15:17
     */
    public static boolean sendApp(Map<String, String> params)throws ShopMsgException {
        // app推送接口url地址
        String url = Constants.APP_PUSH_URL;
        String urlEncode = "utf-8";
        Boolean flag = false;
        try {
            String result = HttpTools.httpPost(url, params, urlEncode);
            if (result.length() > 0) {
                if ("true".equals(result.substring(1, result.length() - 1))) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            throw new ShopMsgException(e.getMessage());
        }
        return flag;
    }
}
