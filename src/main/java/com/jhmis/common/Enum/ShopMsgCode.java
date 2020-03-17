package com.jhmis.common.Enum;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.Enum
 * @Author: hdx
 * @CreateTime: 2019-09-02 15:11
 * @Description: 发布需求中对应枚举类
 */
public enum ShopMsgCode {
    /**
     发布需求中对应枚举类
    */
    PARAM_MOBILE_ERROR("MSG_ERROR_101","手机号不能为空"),
    PARAM_PROVICEID_ERROR("MSG_ERROR_102","省份编码不能为空"),
    PARAM_CITYID_ERROR("MSG_ERROR_103","城市编码不能为空"),
    PARAM_DISTRICID_ERROR("MSG_ERROR_104","区域编码不能为空"),
    PARAM_PROVICENAME_ERROR("MSG_ERROR_105","省份不能为空"),
    PARAM_CITYNAME_ERROR("MSG_ERROR_106","城市不能为空"),
    PARAM_DISTRICNAME_ERROR("MSG_ERROR_107","区域不能为空"),
    PARAM_AREADETAILS_ERROR("MSG_ERROR_108","详细地区不能为空"),
    PARAM_COMPANYNAME_ERROR("MSG_ERROR_109","公司名称不能为空"),
    PARAM_CONNECTNAME_ERROR("MSG_ERROR_110","联系人不能为空"),
    PARAM_EMAIL_ERROR("MSG_ERROR_111","邮箱不能为空"),
    PARAM_PROGROUP_ERROR("MSG_ERROR_112","产品组不能为空"),
    PARAM_CODE_ERROR("MSG_ERROR_113","验证码不能为空"),
    PARAM_CONTENT_ERROR("MSG_ERROR_113","短信内容不能为空"),
    PARAM_SERVICE_CHECK_ERROR("MSG_ERROR_114","客服审核状态不能为空"),
    PARAM_ORGID_ERROR("MSG_ERROR_115","所属工贸不能为空"),
    PARAM_DIRECTORNUMBER_ERROR("MSG_ERROR_116","总监账号不能为空"),
    PARAM_MANAGERNO_ERROR("MSG_ERROR_117","负责人编号不能为空"),
    PARAM_MANAGERNAME_ERROR("MSG_ERROR_118","负责人名称不能为空"),
    PARAM_MSGID_ERROR("MSG_ERROR_119","需求id不能为空"),
    PARAM_REMARK_ERROR("MSG_ERROR_120","总监备注不能为空"),
    PARAM_REMARKSPERSON_ERROR("MSG_ERROR_121","总监备注人不能为空"),
    PARAM_APPISPASS_ERROR("MSG_ERROR_122","总监审核结果不能为空"),
    PARAM_DIRECTORNO_ERROR("MSG_ERROR_123","总监账号不能为空"),
    PARAM_DEALERCODE_ERROR("MSG_ERROR_124","经销商编码不能为空"),
    PARAM_CANCELREASON_ERROR("MSG_ERROR_125","弃单原因不能为空"),
    PARAM_DEALERNUMBER_ERROR("MSG_ERROR_126","经销商账号不能为空"),
    PARAM_MSGID_ISNULL_ERROR("MSG_ERROR_127","需求ID不能为空"),
    PARAM_ORDERID_ISNULL_ERROR("MSG_ERROR_128","经销商订单ID不能为空"),
    PARAM_UNDERTAKE_ISNULL_ERROR("MSG_ERROR_129","承接方式不能为空"),
    PARAM_CANCELER_ERROR("MSG_ERROR_130","废弃人不能为空"),
    PARAM_CANCELFLAG_ERROR("MSG_ERROR_131","废弃类型不能为空"),
    PARAM_CANCELRESON_ERROR("MSG_ERROR_132","废弃原因不能为空"),
    PARAM_CANCELDESC_ERROR("MSG_ERROR_133","废弃描述不能为空"),
    PARAM_ZYKCID_ERROR("MSG_ERROR_134","零售单ID不能为空"),
    PARAM_ISCHECK_ERROR("MSG_ERROR_135","审核结果不能为空"),
    PARAM_ZYKCURL_ERROR("MSG_ERROR_136","零售单上传见证性材料不能为空"),
    PARAM_TRADECOUNT_ERROR("MSG_ERROR_137","零售单交易金额不能为空"),
    PARAM_DISPATID_ERROR("MSG_ERROR_138","派单id不能为空"),
    PARAM_CLOESEREASON_ERROR("MSG_ERROR_139","关闭理由不能为空"),
    PARAM_DELIVERNUM_ERROR("MSG_ERROR_140","物流单号不能为空"),
    PARAM_INSTALLMAN_ERROR("MSG_ERROR_141","安装人不能为空"),
    PARAM_SKILLGRADE_ERROR("MSG_ERROR_142","技术评分不能为空"),
    PARAM_ATTITUDEGRADE_ERROR("MSG_ERROR_143","态度评分不能为空"),
    PARAM_SUBUSER_ERROR("MSG_ERROR_144","评价提交人不能为空"),
    PARAM_TIMEOUT_ERROR("MSG_ERROR_145","承接方式超时原因不能为空"),
    REG_MOBILE_ERROR("MSG_REG_ERROR_201","手机号格式不正确"),
    REG_EMAIL_ERROR("MSG_REG_ERROR_202","邮箱格式不正确"),
    REG_UNDERTAKE_ERROR("MSG_REG_ERROR_203","承接方式格式填写不正确"),
    REG_PRODUCT_ERROR("MSG_REG_ERROR_204","产品编码格式不正确"),
    FOUR_ACCOUNT_ERROR("MSG_REG_ERROR_205","400接口验证用户名不正确"),
    FOUR_PASSWORD_ERROR("MSG_REG_ERROR_206","400接口验证密码不正确"),
    SIX_ACCOUNT_ERROR("MSG_REG_ERROR_207","690接口验证用户名不正确"),
    SIX_PASSWORD_ERROR("MSG_REG_ERROR_208","690接口验证密码不正确"),
    ALREG_ERROR("MSG_REG_ERROR_301","手机号已注册，请直接登录"),
    CLOSE_SHOPMSG_ERROR("MSG_REG_ERROR_302","此需求已在流程中，不可进行此操作!"),
    MSG_NOTEXIST_ERROR("MSG_ERROR_303","此需求不存在"),
    DEALER_NOTEXIST_ERROR("MSG_ERROR_304","此经销商不存在"),
    DIS_NOTCHOOS_ERROR("MSG_ERROR_305","未选择派单人"),
    SYSUSER_NOTEXIST_ERROR("MSG_ERROR_306","系统用户不存在"),
    ROLES_NOTEXIST_ERROR("MSG_ERROR_307","用户权限不存在"),
    OPEARTROLES_NOTEXIST_ERROR("MSG_ERROR_308","用户无此权限进行操作"),
    SYSUSER_NOTLOGIN_ERROR("MSG_ERROR_309","账户已过期，请重新登录"),
    DIRECT_NOTEXIST_ERROR("MSG_ERROR_310","此工贸下总监不存在"),
    DELEARGARB_NOTEXIST_ERROR("MSG_ERROR_311","可抢单的经销商不存在"),
    OVERTIME_NOTEXIST_ERROR("MSG_ERROR_312","不存在超过两小时待处理的需求"),
    USERDIRECTROLES_NOTEXIST_ERROR("MSG_ERROR_313","总监权限不存在"),
    ORDER_NOTEXIST_ERROR("MSG_ERROR_314","经销商订单不存在"),
    MSGFLAG_NOINPROCESS_ERROR("MSG_ERROR_315","此需求节点状态与操作不符,请刷新后重试"),
    ORDER_ZYKC_ERROR("MSG_ERROR_316","经销商零售单不存在"),
    ORDER_NODE_NOTEXIST_ERROR("MSG_ERROR_317","此节点状态经销商订单不存在"),
    DISPAT_NOTEXIST_ERROR("MSG_ERROR_318","派单信息不存在"),
    DEALER_NOTUNDERTAKE_ERROR("MSG_ERROR_319","经销商无承接此产品的权限"),
    CHANGE_MSGFLAG_ERROR("MSG_ERROR_320","此单已被其他经销商抢到或已不存在"),
    SENDMSG_ERROR("MSG_REG_ERROR_901","短信发送失败，请稍后再试"),
    SUBSHOPMSG_SUCCESS("MSG_SUCCESS_001","发布需求成功"),
    CLOSE_SHOPMSG_SUCCESS("MSG_SUCCESS_002","此需求关闭成功"),
    OPEN_SHOPMSG_SUCCESS("MSG_SUCCESS_003","开启需求成功"),
    SEND_SHORTMSG_SUCCESS("MSG_SUCCESS_004","发送短信成功"),
    OPERATION_SUCCESS("MSG_SUCCESS_005","操作成功"),
    DISPATCHER_SUCCESS("MSG_SUCCESS_006","派单成功"),
    REMARK_SUCCESS("MSG_SUCCESS_007","总监未处理原因备注成功"),
    DIRECT_DISPATCHER_SUCCESS("MSG_SUCCESS_008","总监派单成功"),
    NOUNDERTAKE_DISCARD_SUCCESS("MSG_SUCCESS_009","弃单成功"),
    DEALER_GARB_SUCCESS("MSG_SUCCESS_010","抢单成功"),
    UPDATE_MSG_EXCEPTION("MSG_ERROR_501","需求更新失败"),
    APPCHECK_EXCEPTION("MSG_ERROR_503","总监审核异常"),
    INSERT_DISPATCHER_EXCEPTION("MSG_ERROR_502","派单信息保存失败"),
    NODE_EXCEPTION("MSG_ERROR_504","此流程节点已变更，请核对后再处理"),
    DISPATCHER_EXCEPTION("MSG_ERROR_505","此需求单派单数据异常，请核对后再处理"),
    UPDATE_DISPATCHER_EXCEPTION("MSG_ERROR_506","派单管理中派单信息保存失败"),
    ORDER_INSERT_EXCEPTION("MSG_ERROR_507","订单生成失败,请联系管理员处理"),
    DEALER_EXIS_EXCEPTION("MSG_ERROR_508","供应商信息异常,请联系管理员"),
    SHOPPRODUCT_EXIS_EXCEPTION("MSG_ERROR_509","产品组信息异常,请联系管理员"),
    SELECT_DEALER_EXCEPTION("MSG_ERROR_510","拉取经销商失败"),
    ORG_CHECK_EXCEPTION("MSG_ERROR_511","请先完成统一信用认证后再审核"),
    HAS_EVALUATE_EXCEPTION("MSG_ERROR_512","此需求已评价"),
    WEBSERVICE_CHANNEL_EXCEPTION("MSG_ERROR_513","渠道来源异常,请核对后重试"),
    ;

    // 普通方法
    public static String getName(String value) {
        for (ShopMsgCode shopMsgCode : ShopMsgCode.values()) {
            if (shopMsgCode.getValue() == value) {
                return shopMsgCode.desc;
            }
        }
        return null;
    }

    private String value;
    private String desc;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString()  {
        return this.desc;
    }

    ShopMsgCode(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
