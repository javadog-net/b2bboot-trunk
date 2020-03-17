package com.jhmis.common.scheduled;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.haier.mdm.khzj.Process;
import com.jhmis.common.Enum.EucMsgCode;
import com.jhmis.common.Enum.MsgFlagCode;
import com.jhmis.common.Enum.ProcessCode;
import com.jhmis.common.Enum.ShopMsgCode;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.customer.entity.CustomerProjectInfo;
import com.jhmis.modules.customer.service.CustomerProjectInfoService;
import com.jhmis.modules.euc.entity.EucLog;
import com.jhmis.modules.euc.entity.EucMsg;
import com.jhmis.modules.euc.entity.EucMsgOrder;
import com.jhmis.modules.euc.entity.EucReturnBody;
import com.jhmis.modules.euc.mapper.EucMsgMapper;
import com.jhmis.modules.euc.mapper.EucMsgOrderMapper;
import com.jhmis.modules.euc.service.EucLogService;
import com.jhmis.modules.euc.service.EucMsgOrderService;
import com.jhmis.modules.euc.service.EucMsgService;
import com.jhmis.modules.process.entity.dispatcher.ShopMsgDispatcher;
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;
import com.jhmis.modules.process.entity.shopmsgorder.ShopMsgCustcodeOrder;
import com.jhmis.modules.process.service.dispatcher.ShopMsgDispatcherService;
import com.jhmis.modules.process.service.shopmsg.ShopMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.scheduled
 * @Author: hdx
 * @CreateTime: 2019-09-27 10:14
 * @Description: 需求相关定时任务
 */
@Component
@Configurable
@EnableScheduling
public class EucMsgTimeTask {

    Logger log = LoggerFactory.getLogger(EucMsgTimeTask.class);

    @Autowired
    EucMsgOrderService eucMsgOrderService;

    @Autowired
    private EucMsgService eucMsgService;

    @Autowired
    private EucMsgMapper eucMsgMapper;

    @Autowired
    EucMsgOrderMapper eucMsgOrderMapper;

    @Autowired
    CustomerProjectInfoService customerProjectInfoService;

    @Autowired
    EucLogService eucLogService;


    /**
     * @Author: hdx
     * @CreateTime: 10:18 2019/9/27
     * @param: * @param
     * @Description: 超过七天未处理
     */
    //每个小时执行一次
    @Scheduled(cron = "0 0 */1 * * ?")
    //一分钟执行一次
    //@Scheduled(cron = "0 */1 * * * ?")
    public void overtimeNoDeal() {
        //获取当前时间
        long now = System.currentTimeMillis();
        //七天基数
        long sevenDay = 60 * 60 * 7 * 24 * 1000;
        //获取所有未放弃的单子，并且排除进入hps和零售上传见证性材料的
        EucMsgOrder eucMsgOrder = new EucMsgOrder();
        //放弃为未放弃
        eucMsgOrder.setIsAbandon(ProcessCode.NO.getLabel());
        //查询
        List<EucMsgOrder> listEucMsgOrder = eucMsgOrderService.findListOver(eucMsgOrder);
        if (listEucMsgOrder != null && listEucMsgOrder.size() > 0) {
            //循环此订单
            for (EucMsgOrder emo : listEucMsgOrder) {
                //如果已经返回了工程项目编码则跳过
                if (StringUtils.isNotEmpty(emo.getProjectCode())) {
                    continue;
                }
                //如果已经返回了零售上传见证性材料
                if (StringUtils.isNotEmpty(emo.getImageUrl())) {
                    continue;
                }
                //获取当前项目更新时间
                if (emo.getUpdateTime() != null) {
                    long updateTime = emo.getUpdateTime().getTime();
                    //判断时间已经超出7天的
                    if ((updateTime + sevenDay) < now) {
                        //超过七天后进行主动回溯
                        emo.setIsAbandon(ProcessCode.YES.getLabel());
                        //废弃时间
                        emo.setAbandonTime(new Date());
                        //废弃原因
                        emo.setAbandonReason(EucMsgCode.ABANDONREASON_SEVEN_NO.getValue());
                        //更新订单表
                        eucMsgOrderMapper.update(emo);
                        //将EUC主表数据中经销商清空
                        EucMsg eucMsg = eucMsgMapper.getOwnById(emo.getEucId());
                        if(null!=eucMsg){
                            //更新时间
                            eucMsg.setUpdateDate(new Date());
                            //共享数量减一
                            //EUC需求共享单-1
                            int count = 0;
                            //共享数量不为空
                            if(null!=eucMsg.getShareCount()){
                                //共享数量
                                count = eucMsg.getShareCount();
                                //如果等于0则共享数量标记为0
                                if(count == 0){
                                    eucMsg.setShareCount(0);
                                }else{
                                    //如果不等于0则减一
                                    eucMsg.setShareCount(count-1);
                                }
                            }
                        }
                        //更新需求
                        eucMsgMapper.update(eucMsg);
                    }
                }
            }
        }
    }

    /**
     * @Author: hdx
     * @CreateTime: 10:18 2019/9/27
     * @param: * @param
     * @Description: 工程单是否中标
     */
    //每个小时执行一次
    //@Scheduled(cron = "0 0 */1 * * ?")
    //一分钟执行一次
    //@Scheduled(cron = "0 */1 * * * ?")
    //每天执行一次
    @Scheduled(cron = "0 0 1 * * ? ")
    @Transactional
    public void isBind() {

        CustomerProjectInfo customerProjectInfo = new CustomerProjectInfo();
        //来源是客单提报的
        customerProjectInfo.setProjectsSource("GN_PRJ_11");
        List<CustomerProjectInfo> listCustomerProjectInfo = customerProjectInfoService.findListTask(customerProjectInfo);
        for (CustomerProjectInfo cp : listCustomerProjectInfo) {
            //如果hps状态大于R5则视为中标
            if ("5".equals(cp.getNodestate()) || "6".equals(cp.getNodestate()) || "7".equals(cp.getNodestate()) || "8".equals(cp.getNodestate()) || "9".equals(cp.getNodestate())) {
                //需求Id是否为空
                if (StringUtils.isNotEmpty(cp.getMsgId())) {
                    EucMsg eucMsg = new EucMsg();
                    //需求id
                    eucMsg.setMsgId(cp.getMsgId());
                    List<EucMsg> listEucMsg = eucMsgService.findListOver(eucMsg);
                    if (listEucMsg != null && listEucMsg.size() > 0) {
                        EucMsg em = listEucMsg.get(0);
                        //设置中标单子更新
                        //设置项目编码
                        em.setProjectCode(cp.getProjectcode());
                        //设置失效
                        em.setIsValid(ProcessCode.NO.getLabel());
                        //设置更新时间
                        em.setUpdateDate(new Date());
                        //更新需求
                        eucMsgMapper.update(em);
                        EucMsgOrder emo = new EucMsgOrder();
                        //EUC的id
                        emo.setEucId(eucMsg.getId());
                        //EUC订单未放弃
                        emo.setIsAbandon(ProcessCode.NO.getLabel());
                        //中标后将所有其余单子改为已结单
                        List<EucMsgOrder> listEucMsgOrder = eucMsgOrderService.findListOver(emo);
                        //存在EUC订单
                        if (listEucMsgOrder != null && listEucMsgOrder.size() > 0) {
                            for(EucMsgOrder emoOther:listEucMsgOrder){
                                if(emoOther.getProjectCode().equals(cp.getProjectcode())){
                                    emoOther.setUpdateTime(new Date());
                                    emoOther.setIsBind(ProcessCode.YES.getLabel());
                                    eucMsgOrderMapper.update(emoOther);
                                    continue;
                                }
                                //设置放弃
                                emoOther.setIsAbandon(ProcessCode.YES.getLabel());
                                //放弃类型
                                if("".equals(emoOther.getUndertake())){
                                    emoOther.setAbandonType(EucMsgCode.ABANDONTYPE_NO_UNDEDRTAKE.getLabel());
                                }else if(EucMsgCode.UNDERTAKE_PROJECT.getLabel().equals(emoOther.getUndertake())){
                                    emoOther.setAbandonType(EucMsgCode.ABANDONTYPE_PROJECT.getLabel());
                                }else if(EucMsgCode.UNDERTAKE_RETAIL.getLabel().equals(emoOther.getUndertake())){
                                    emoOther.setAbandonType(EucMsgCode.ABANDONTYPE_RETAIL.getLabel());
                                }
                                //放弃原因
                                emoOther.setAbandonReason("已结单");
                                //放弃时间
                                emoOther.setAbandonTime(new Date());
                                //经销商放弃备注原因
                                emoOther.setAbandonRemark(emoOther.getBusinessName() + emoOther.getBusinessCode() +"已在"+ new Date().toLocaleString() + "，已中标");
                                eucMsgOrderMapper.update(emoOther);
                            }
                        }
                    }
                }
            }

        }
    }

    /**
     *@Description 给euc推送修改状态
     *@Param
     *@Return
     *@Author t.c
     *@Date 2020-03-06
     */
    public static String updateToEuc(EucReturnBody eucReturnBody){
        JSONObject param = JSONUtil.createObj();
        //商机编码
        param.put("businessCode", eucReturnBody.getBusinessCode());
        //状态
        param.put("status", eucReturnBody.getStatus());
        //操作人
        param.put("person", eucReturnBody.getPerson());
        //操作人编码
        param.put("personCode", eucReturnBody.getPersonCode());
        //操作人备注
        param.put("remark", eucReturnBody.getRemark());
        //项目编码
        param.put("projectCode", eucReturnBody.getProjectCode());
        param.put("winBidCompany",eucReturnBody.getWinBidCompany());
        param.put("performanceW",eucReturnBody.getPerformanceWay());
        Map<String,String> map = new HashMap<String,String>();
        map.put("Content-Type", "application/json;charset=utf-8");
        String  httpStr = HttpRequest.post(Constants.EUC_URL+"/bussiness/updateStatusHaier").addHeaders(map).body(param).execute().body();
        com.alibaba.fastjson.JSONObject jo = JSON.parseObject(httpStr);
        String fFlag = "1";
        if(null!=jo){
            fFlag = jo.get("code")+"";
        }
        return fFlag;
    }

    /**
    *@Author: hdx
    *@CreateTime: 13:33 2020/3/13
    *@param:  * @param null
    *@Description:
        回传EUC状态 -- 已废弃此接口
    */
    public static String returnEuc(String businessCode, String status, String person, String personCode, String remark, String projectCode) {
        JSONObject param = JSONUtil.createObj();
        //商机编码
        param.put("businessCode", businessCode);
        //状态
        param.put("status", status);
        //操作人
        param.put("person", person);
        //操作人编码
        param.put("personCode", personCode);
        //操作人备注
        param.put("remark", remark);
        //项目编码
        param.put("projectCode", projectCode);
        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json;charset=utf-8");
        String httpStr = HttpRequest.post(Constants.EUC_URL + "/bussiness/updateStatusHaier").addHeaders(map).body(param).execute().body();
        return httpStr;
    }

    /**
     *@Author: hdx
     *@CreateTime: 15:12 2020/3/6
     *@param:  code 是否回传EUC成功
     *@param:  status 回传状态
     *@param:  eucMsg EUC共享基础数据
     *@param:  eucMsgOrder 经销商订单数据
     *@param:  EucReturnBody 回传euc字段
     *@Description: EUC日志存储
     */
    private void eucSaveLog(String code, String status, EucReturnBody eucReturnBody, EucMsg eucMsg, EucMsgOrder eucMsgOrder){
        EucLog eucLog = new EucLog();
        //回传内容
        String content = "";
        //成功失败判别
        String flag = "失败";
        //返回状态
        if("0".equals(code)){
            flag = "成功";
        }
        switch (status){
            case "1500":
                content = "履约完成" + flag + eucReturnBody.toString();
                break;
        }
        //需求Id
        eucLog.setMsgId(eucMsg.getMsgId());
        //商机编码
        eucLog.setBusinessCode(eucMsg.getBusinessCode());
        //商机名称
        eucLog.setBusinessName(eucMsg.getBusinessName());
        //创建时间
        eucLog.setAddTime(new Date());
        //状态
        eucLog.setStatus(status);
        //内容
        eucLog.setContent(content);
        //保存
        eucLogService.save(eucLog);
    }


    /**
     * @Author: hdx
     * @CreateTime: 14:10 2020/3/9
     * @param:
     * @Description: 七天未处理的将筛选归算
     * 1.网格七天则扩展到中心
     * 2.中心七天则扩展到全国
     */
    //每个小时执行一次
    //@Scheduled(cron = "0 0 */1 * * ?")
    //一分钟执行一次
    @Scheduled(cron = "0 */1 * * * ?")
    public void punctureScreening() {
        //获取当前时间
        long now = System.currentTimeMillis();
        //七天基数
        long sevenDay = 60 * 60 * 7 * 24 * 1000;
        //获取所有EUC推送的且没有经销商共享
        List<EucMsg> listEucMsg = eucMsgService.findAllNoShare();
        //判断是否存在列表数据
        if (null != listEucMsg && listEucMsg.size() > 0) {
            for (EucMsg eucMsg : listEucMsg) {
                //进行更新时间判断是否七天
                if (eucMsg.getUpdateDate() != null) {
                    //获取当前项目更新时间
                    long updateTime = eucMsg.getUpdateDate().getTime();
                    //判断时间已经超出7天的
                    if ((updateTime + sevenDay) < now) {
                        //如果处于网格
                        if(EucMsgCode.SCREEN_STSTUS_GIRD.getLabel().equals(eucMsg.getScreenStstus())){
                            eucMsg.setScreenStstus(EucMsgCode.SCREEN_STSTUS_CENTER.getLabel());
                        }else if(EucMsgCode.SCREEN_STSTUS_CENTER.getLabel().equals(eucMsg.getScreenStstus())){
                            eucMsg.setScreenStstus(EucMsgCode.SCREEN_STSTUS_ALL.getLabel());
                        }
                        //更新时间
                        eucMsg.setUpdateDate(new Date());
                        //更新数据
                        eucMsgService.save(eucMsg);
                    }
                }
            }
        }
    }

}
