package com.jhmis.common.Enum;
/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.Enum
 * @Author: hdx
 * @CreateTime: 2019-11-08 15:17
 * @Description: Euc异常对应枚举类
 */
public enum EucMsgCode {
    /**
    *@Author: hdx
    *@CreateTime: 10:19 2019/12/23
    *@param:  * @param null
    *@Description:
    
    */
    PARAM_BUSINESS_CODE_ERROR("EUC_ERROR_101","商机编码不能为空"),
    PARAM_BUSINESS_NAME_ERROR("EUC_ERROR_102","商机名称不能为空"),
    PARAM_BUSINESS_SOURCE_ERROR("EUC_ERROR_103","商机来源不能为空"),
    PARAM_BUSINESSTYPE_ERROR("EUC_ERROR_104","商机类型不能为空"),
    PARAM_TOPCUSTOMERABBREVIATION_ERROR("EUC_ERROR_105","top客户简称"),
    PARAM_COMPANYNAMEA_ERROR("EUC_ERROR_106","甲方公司名称"),
    PARAM_CONTACTNAMEA_ERROR("EUC_ERROR_107","甲方联系姓名"),
    PARAM_CONTACTMOBILEA_ERROR("EUC_ERROR_108","甲方联系电话"),
    PARAM_INDUSTRY_ERROR("EUC_ERROR_109","行业不能为空"),
    PARAM_HAIERTHEATER_ERROR("EUC_ERROR_110","海尔战区不能为空"),
    PARAM_CENTER_ERROR("EUC_ERROR_111","中心不能为空"),
    PARAM_SMALLMESH_ERROR("EUC_ERROR_112","网格小微不能为空"),
    PARAM_GRID_ERROR("EUC_ERROR_113","网格不能为空"),
    PARAM_PROVINCE_ERROR("EUC_ERROR_114","省份不能为空"),
    PARAM_PROVINCEID_ERROR("EUC_ERROR_115","省份ID不能为空"),
    PARAM_CITY_ERROR("EUC_ERROR_116","城市不能为空"),
    PARAM_CITYID_ERROR("EUC_ERROR_117","城市ID不能为空"),
    PARAM_DISTRICT_ERROR("EUC_ERROR_118","区县不能为空"),
    PARAM_DISTRICTID_ERROR("EUC_ERROR_119","区县ID不能为空"),
    PARAM_BUSINESSOPPORTUNITYADDRESS_ERROR("EUC_ERROR_120","商机地址不能为空"),
    PARAM_PROJECTMANAGERCODE_ERROR("EUC_ERROR_121","海尔接口人编码不能为空"),
    PARAM_PROJECTMANAGERNAME_ERROR("EUC_ERROR_122","海尔接口人姓名不能为空"),
    PARAM_CONTRACTORCODE_ERROR("EUC_ERROR_123","承接商编码不能为空"),
    PARAM_CONTRACTORNAME_ERROR("EUC_ERROR_124","承接商名称不能为空"),
    PARAM_BUSINESSCHANNEL_ERROR("EUC_ERROR_125","承接商大渠道不能为空"),
    PARAM_COMPETITORS_ERROR("EUC_ERROR_126","竞争对手公司不能为空"),
    PARAM_INDUSTRY_ID_ERROR("EUC_ERROR_127","行业ID不能为空"),
    EUC_MSG_EXIST("EUC_ERROR_128","此需求已存在"),
    PARAM_DEALERCODE_EXIST("EUC_ERROR_129","经销商编码不能为空"),
    PARAM_EUCMSGID_EXIST("EUC_ERROR_130","EUC需求id不能为空"),
    EUC_MSG_NO_EXIST("EUC_ERROR_131","此需求不存在"),
    DEALERCODE_EXIST("EUC_ERROR_132","此需求已有经销商承接"),
    PARAM_DEALERNAME_EXIST("EUC_ERROR_133","经销商名称不能为空"),
    PARAM_UNDERTAKE_EXIST("EUC_ERROR_134","承接方式不能为空"),
    ERROR_CHECK_EUC_FROM("EUC_ERROR_135","此需求还不归属于您,请落实后再操作"),
    ERROR_UNDERTAKE("EUC_ERROR_136","请选择正确的承接方式"),
    ERROR_ABANDON_PROJECT("EUC_ERROR_137","此需求已进入工程系统，无法放弃"),
    ERROR_ABANDON_RETAIL("EUC_ERROR_138","此需求已上传见证性材料，无法放弃"),
    PARAM_ABANDONTYPE_EXIST("EUC_ERROR_139","放弃类型不能为空"),
    ERROR_MISMATCHING("EUC_ERROR_141","放弃类型与订单状态不匹配"),
    ERROR_ABANDONTYPE("EUC_ERROR_140","请选择正确的放弃类型"),
    PARAM_URL_EXIST("EUC_ERROR_142","见证性材料不能为空"),
    PARAM_PROJECT_EXIST("EUC_ERROR_143","您已选择工程单,请勿重复选择"),
    PARAM_RETAIL_EXIST("EUC_ERROR_144","您已选择零售单,请勿重复选择"),
    PARAM_ORDERID_EXIST("EUC_ERROR_145","EUC订单id不能为空"),
    NOT_ORDER_EXIST("EUC_ERROR_146","EUC订单不存在"),
    AL_UNDERTAKE_EXIST("EUC_ERROR_147","您已选择了承接方式"),
    ERROR_NOTVALIAD("EUC_ERROR_148","此商机已被授权，请勿再推送"),
    ERROR_HASPUSH("EUC_ERROR_149","商机已推送过此经销商，请勿再推送"),
    ERROR_NOCHANGE("EUC_ERROR_150","共享商机已有经销商抢到，商机数据不可更新"),
    PARAM_GRID_CODE_ERROR("EUC_ERROR_151","网格编码不能为空"),
    EUC_MSG_NOINPOOL("EUC_ERROR_152","此需求已不在抢单池中,请刷新后重试"),
    PARAM_GRIDCODE_ERROR("EUC_ERROR_153","网格编码不能为空"),
    ERROR_ALREADY_HAVE("EUC_ERROR_154","您已抢到此单，请勿重复处理!"),
    DEALERROBTRADE_SUCCESS("MSG_SUCCESS_001","抢单成功"),
    UNDERTAKE_SUCCESS("MSG_SUCCESS_002","操作成功"),
    ABANDON_SUCCESS("MSG_SUCCESS_003","放弃成功"),
    TO_DO("100"," 待办"),
    WAITING_FOR_DEALER_TO_GRAB("200"," 待经销商抢单"),
    TO_BE_SELECTEDBY_THE_DEALER("300"," 待经销商选择承接方式"),
    AUTHENTICATION_MATERIALS("400"," 待经销商上传鉴证性材料"),
    REVIEWED_BY_CUSTOMER_SERVICE("500"," 待客服审核"),
    WAITING_FOR_INFORMATION("600"," 待经销商完善客单报备信息"),
    RETAIL_CLOSED_LOOP("700"," 零售闭环"),
    NO_DEMAND("800"," 无需求"),
    COMPETITOR_SIGNED("900"," 对手已签约"),
    FUNNEL_ALREADY_EXISTS("1000"," 漏斗已存在"),
    UNDER_FOLLOW_UP("1100"," 跟进中"),
    PROJECT_CLOSED("1200"," 项目关闭"),
    LOST_BID("1300"," 丢标"),
    IN_PERFORMANCE("1400"," 履约中"),
    COMPLETION_OF_PERFORMANCE("1500"," 履约完成"),
    ABANDONTYPE_NO_UNDEDRTAKE("0","未选择承接方式前废弃"),
     ABANDONTYPE_PROJECT("1","工程单放弃"),
    ABANDONTYPE_RETAIL("2","零售单放弃"),
    ABANDONTYPE_RETAIL_RETURN("3","零售单回退"),


    ABANDONREASON_ACTIVE("1","经销商主动放弃"),
    ABANDONREASON_SEVEN_NO("2","超时七天未处理,需求回溯"),
    UNDERTAKE_PROJECT("0","工程"),
    UNDERTAKE_RETAIL("1","零售"),
    UNDERTAKE_CANCEL("2","弃单"),
    ORDER_TYPE_DISPATCHER("0","派单"),
    ORDER_TYPE_GARB("1","抢单"),
    ORDER_PROCESS_CLOSE("1","关闭"),
    ORDER_PROCESS_ON_GOINT("2","正在进行中"),
    ORDER_PROCESS_BIND("3","中标"),
    AUTH_DEFAULT("DEFAULT","待授权"),
    AUTH_AUTHORIZED("AUTHORIZED","授权通过"),
    AUTH_UNAUTHORIZED("UNAUTHORIZED","未获得授权"),
    AUTH_APPEAL_SAVE("APPEAL_SAVE","保存申诉"),
    AUTH_APPEAL_AUDITING("APPEAL_AUDITING","申诉待审核"),
    AUTH_APPEAL_SUCCESS("APPEAL_SUCCESS","申诉通过"),
    AUTH_APPEAL_FAILED("APPEAL_FAILED","申诉未通过"),
    SCREEN_STSTUS_GIRD("0","网格"),
    SCREEN_STSTUS_CENTER("1","中心"),
    SCREEN_STSTUS_ALL("2","全国"),



            ;

    private  String label;
    private  String value;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString()  {
        return this.value;
    }
    EucMsgCode(String label, String value) {
        this.label = label;
        this.value = value;
    }
}
