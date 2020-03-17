/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.common.config;

import com.google.common.collect.Maps;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.io.PropertiesUtils;
import com.jhmis.core.persistence.UseUrl;
import com.jhmis.core.web.Servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.DefaultResourceLoader;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 全局配置类
 *
 * @author jhmis
 * @version 2017-06-25
 */

public class Global {


    /**
     * 当前对象实例
     */
    private static Global global = new Global();

    /**
     * 当前站点根路径
     */
    public static final String BASE_URL = "http://b2b.haier.com/shop";

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = Maps.newHashMap();

    /**
     * 属性文件加载对象
     * 属性加载器PropertiesUtils处理，读取yaml文件
     */
    //private static PropertiesLoader loader = new PropertiesLoader("/properties/config.properties");

    /**
     * 显示/隐藏
     */
    public static final String SHOW = "1";
    public static final String HIDE = "0";

    /**
     * 是/否
     */
    public static final String YES = "1";
    public static final String NO = "0";

    /**
     * 对/错
     */
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    /**
     * 附件类型
     */
    public static final String ATT_AVATAR = "1";
    public static final String ATT_IMAGE = "2";
    public static final String ATT_MEDIA = "3";
    public static final String ATT_FILE = "4";

    //订单商品中的申请换货状态
    public static final String GOODS_EXCHANGE_APPLY = "1"; // 已申请，待审核
    public static final String GOODS_EXCHANGE_APPLY_PASS = "2"; // 已通过
    public static final String GOODS_EXCHANGE_APPLY_UNPASS = "3"; // 拒绝

    /**
     * 用户类型
     */
    public static final String USER_TYPE_SYS = "1";
    public static final String USER_TYPE_DEALER = "2";
    public static final String USER_TYPE_PURCHASER = "3";
    public static final String USER_TYPE_AVATAR = "4"; //app头像
    public static final String USER_TYPE_ENCLOSURE = "5"; //app聊天上传图片，文件等附件
    public static final String USER_TYPE_PROCESS = "6"; //抢派单流程相关

    /**
     * 消息类型
     */
    public static final String MESSAGE_TYPE_SYS = "1";
    public static final String MESSAGE_TYPE_ORDERS = "10";
    public static final String MESSAGE_TYPE_BIDDINGS = "20";

    /**
     * 审核状态
     */
    public static final String AUDIT_STATE_UNSUBMIT = "-1";//待提交
    public static final String AUDIT_STATE_WAIT = "0";//待审核
    public static final String AUDIT_STATE_OK = "1";//审核通过
    public static final String AUDIT_STATE_NO = "2";//审核未通过
    /**
     * 订单状态
     */
    public static final int ORDER_STATE_CANCEL = 0; //取消订单
    public static final int ORDER_STATE_NEW = 10; //默认（待支付）
    public static final int ORDER_STATE_PAY_FINISHED = 20; //已支付
    public static final int ORDER_STATE_SHIPPED = 30; //已发货
    public static final int ORDER_STATE_RECEIVED = 40; //已收货
    public static final int ORDER_STATE_REFUNDED = 50;//已退款

    public static String KJT_GATEWAY_NEW = "https://gateway.kjtpay.com/recv.do";// 正式
    //异步通知地址
    public static final String KJT_NOTIFY_URL = "/payment/kjt/notify.do";
    //合作者ID
    public static final String KJT_PARTNER = "200002533804"; //正式
//	public static final String KJT_PARTNER = "200000156505"; //测试

    public static final double KJT_CHARGE = 10.00;
    //担保交易接口
    public static final String ENSURE_TRADE = "ensure_trade";
    //交易达成网关接口
    public static final String TRADE_SETTLE = "trade_settle";
    //退款网关接口
    public static final String TRADE_REFUND = "trade_refund";

    public static String INPUT_CHARSET = "UTF-8";
    //快捷通支付状态
    public static String KJT_TRADE_STATUS_SUCCESS = "TRADE_SUCCESS";
    public static String KJT_TRADE_STATUS_FINISHED = "TRADE_FINISHED";
    public static String KJT_PAY_STATUS_FINISHED = "PAY_FINISHED";
    public static String KJT_TRADE_STATUS_CLOSED = "TRADE_CLOSED";
    public static String KJT_TRANSFER_SUCCESS = "TRANSFER_SUCCESS";

    //支付成功标志
    public static int KJT_API_PAY_STATE_SUCCESS = 1;  //买家付款
    public static int KJT_API_ROYALTY_STATE_SUCCESS = 1; //网关达成，即分账

    /**
     * 上传文件基础虚拟路径
     */
    public static final String USERFILES_BASE_URL = "/userfiles/";
    /**
     * HPS 返回成功的时候的信息
     */
    public static final String HPS_RESPONSE_OK = "OK";

    /**
     * 获取当前对象实例
     */
    public static Global getInstance() {
        return global;
    }

    /**
     * 获取配置
     *
     * @see ${fns:getConfig('adminPath')}
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null) {
            value = PropertiesUtils.getInstance().getProperty(key);
            //value = loader.getProperty(key);
            map.put(key, value != null ? value : StringUtils.EMPTY);
        }
        return value;
    }

    /**
     * 获取配置,代默认值
     *
     * @see ${fns:getConfig('adminPath')}
     */
    public static String getConfig(String key, String defaultValue) {
        String value = getConfig(key);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    public static Integer getConfigAsInt(String key) {
        String value = getConfig(key);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return Integer.parseInt(value);
    }

    public static int getConfigAsInt(String key, int defaultValue) {
        String value = getConfig(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    public static Long getConfigAsLong(String key) {
        String value = getConfig(key);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return Long.parseLong(value);
    }

    public static long getConfigAsLong(String key, long defaultValue) {
        String value = getConfig(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return Long.parseLong(value);
    }

    public static Boolean getConfigAsBoolean(String key) {
        String value = getConfig(key);
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return new Boolean(value.trim());
    }

    public static boolean getConfigAsBoolean(String key, boolean defaultValue) {
        String value = getConfig(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return new Boolean(value.trim());
    }

    /**
     * 获取管理端根路径
     */
    public static String getAdminPath() {
        return getConfig("adminPath");
    }

    /**
     * 获取网站图片访问路径
     */
    public static String getURLPath() {
        return getConfig("UseUrl.baseUrl");
    }

    /**
     * 根路径
     */
    public static String getBaseUrl() {
        return UseUrl.baseUrl;
    }
    
    public static String getUrlone() {
        return UseUrl.urlone;
    }

    /**
     * 获取前端根路径
     */
    public static String getFrontPath() {
        return getConfig("frontPath");
    }

    /**
     * 获取URL后缀
     */
    public static String getUrlSuffix() {
        return getConfig("urlSuffix");
    }

    /**
     * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
     */
    public static Boolean isDemoMode() {
        String dm = getConfig("demoMode");
        return "true".equals(dm) || "1".equals(dm);
    }

    /**
     * 页面获取常量
     *
     * @see ${fns:getConst('YES')}
     */
    public static Object getConst(String field) {
        try {
            return Global.class.getField(field).get(null);
        } catch (Exception e) {
            // 异常代表无配置，这里什么也不做
        }
        return null;
    }

    /**
     * 获取上传文件的根目录
     *
     * @return
     */
    public static String getUserfilesBaseDir() {
        String dir = getConfig("userfiles.basedir");
        if (StringUtils.isBlank(dir)) {
            try {
                dir = Servlets.getRequest().getServletContext().getRealPath("/");
            } catch (Exception e) {
                return "";
            }
        }
        if (!dir.endsWith("/")) {
            dir += "/";
        }
//		System.out.println("userfiles.basedir: " + dir);
        return dir;
    }

    /**
     * 获取工程路径
     *
     * @return
     */
    public static String getProjectPath() {
        // 如果配置了工程路径，则直接返回，否则自动获取。
        String projectPath = Global.getConfig("projectPath");
        if (StringUtils.isNotBlank(projectPath)) {
            return projectPath;
        }
        try {
            File file = new DefaultResourceLoader().getResource("").getFile();
            if (file != null) {
                while (true) {
                    File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
                    if (f == null || f.exists()) {
                        break;
                    }
                    if (file.getParentFile() != null) {
                        file = file.getParentFile();
                    } else {
                        break;
                    }
                }
                projectPath = file.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projectPath;
    }

    /**
     * 获取上传文件host头
     *
     * @return
     */
    public static String getUploadHost() {
        String uploadkind = Global.getConfig("userfiles.uploadkind");
        String host = "";
        if (uploadkind != null && "fastdfs".equalsIgnoreCase(uploadkind)) {
            host = Global.getConfig("fdfs.fileHost");
        } else {
            host = Global.getConfig("userfiles.fileHost");
            if (StringUtils.isBlank(host)) {
                HttpServletRequest request = Servlets.getRequest();
                int port = request.getServerPort();
                String portStr = "";
                if (port != 80 && port != 443) {
                    portStr = ":".concat(String.valueOf(port));
                }
                host = request.getScheme() + "://" + request.getServerName() + portStr + request.getContextPath();
            }
        }
        return host;
        //TODO 检查nginx代理后的url
    }
}
