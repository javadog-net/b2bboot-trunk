package com.jhmis.api.purchaser;

import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserMsg;
import com.jhmis.modules.shop.service.purchaser.PurchaserMsgService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Api(value = "ApiPurchaserMsgController", description = "采购商消息管理")
@RestController
@RequestMapping("/api/purchaser/msg")
public class ApiPurchaserMsgController {
    @Autowired
    PurchaserMsgService purchaserMsgService;

    /**
     * 消息列表
     * @param msg
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(notes = "list", httpMethod = "POST", value = "消息列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "消息类型(1,系统消息，10，订单消息，20，投标消息)", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "content", value = "消息内容", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "isRead", value = "是否已读(1已读，0未读)", required = false, paramType = "form", dataType = "String")
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/list")
    public AjaxJson list(PurchaserMsg msg, HttpServletRequest request, HttpServletResponse response) {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if (msg == null) {
            msg = new PurchaserMsg();
        }
        msg.setPurchaserId(currentAccount.getPurchaserId());
        msg.setAccountId(currentAccount.getId());
        Page<PurchaserMsg> page = purchaserMsgService.findPage(new Page<PurchaserMsg>(request, response), msg);
        return AjaxJson.layuiTable(page);
    }

    /**
     * 未读消息数量
     *
     * @return
     */
    @ApiOperation(notes = "unreadcount", httpMethod = "POST", value = "未读消息数量")
    @RequiresRoles("purchaser")
    @RequestMapping("/unReadCount")
    public AjaxJson unReadCount(){
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        //分别取得各种类型的消息数量，以及总数量
        int sysMsgCount = purchaserMsgService.unReadCount(Global.MESSAGE_TYPE_SYS, currentAccount.getId());
        int orderMsgCount = purchaserMsgService.unReadCount(Global.MESSAGE_TYPE_ORDERS, currentAccount.getId());
        int biddingMsgCount = purchaserMsgService.unReadCount(Global.MESSAGE_TYPE_BIDDINGS, currentAccount.getId());
        int totalCount = sysMsgCount + orderMsgCount + biddingMsgCount;
        Map<String,Integer> counts = new HashMap<>();
        counts.put("totalCount",totalCount);
        counts.put("sysMsgCount",sysMsgCount);
        counts.put("orderMsgCount",orderMsgCount);
        counts.put("biddingMsgCount",biddingMsgCount);
        return AjaxJson.ok(counts);
    }

    /**
     * 设为已读
     *
     * @return
     */
    @ApiOperation(notes = "read", httpMethod = "POST", value = "设为已读")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "消息id", required = true, paramType = "form", dataType = "String")
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/read")
    public AjaxJson read(String id) {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isBlank(id)){
            return AjaxJson.fail("参数为空");
        }
        PurchaserMsg msg = purchaserMsgService.get(id);
        if (msg == null) {
            return AjaxJson.fail("无此消息");
        }
        purchaserMsgService.readMsg(id,currentAccount.getId());
        return AjaxJson.ok();
    }

    /**
     * 消息读取(批量)
     *
     * @return
     */
    @ApiOperation(notes = "batchread", httpMethod = "POST", value = "消息读取(批量)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "消息id逗号分隔", required = true, paramType = "form", dataType = "String")
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/batchread")
    public AjaxJson batchread(String ids) {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isBlank(ids)){
            return AjaxJson.fail("参数为空");
        }
        String idArray[] =ids.split(",");
        for(String id : idArray) {
            PurchaserMsg msg = purchaserMsgService.get(id);
            if (msg == null) {
                return AjaxJson.fail("无此消息");
            }
            purchaserMsgService.readMsg(id,currentAccount.getId());
        }
        return AjaxJson.ok();
    }

    /**
     * 全部设为已读
     *
     * @return
     */
    @ApiOperation(notes = "readall", httpMethod = "POST", value = "全部设为已读")
    @RequiresRoles("purchaser")
    @RequestMapping("/readall")
    public AjaxJson readAll() {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        purchaserMsgService.readAllMsg(currentAccount.getId());
        return AjaxJson.ok();
    }

    /**
     * 消息删除
     *
     * @return
     */
    @ApiOperation(notes = "del", httpMethod = "POST", value = "消息删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "消息id", required = true, paramType = "form", dataType = "String")
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/del")
    public AjaxJson del(String id) {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isBlank(id)){
            return AjaxJson.fail("参数为空");
        }
        PurchaserMsg msg = purchaserMsgService.get(id);
        if (msg == null) {
            return AjaxJson.fail("无此消息");
        }
        purchaserMsgService.delete(msg);
        return AjaxJson.ok();
    }

    /**
     * 消息删除(批量)
     *
     * @return
     */
    @ApiOperation(notes = "batchdel", httpMethod = "POST", value = "消息删除(批量)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "消息id逗号分隔", required = true, paramType = "form", dataType = "String")
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/batchdel")
    public AjaxJson batchdel(String ids) {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isBlank(ids)){
            return AjaxJson.fail("参数为空");
        }
        String idArray[] =ids.split(",");
        for(String id : idArray) {
            if(StringUtils.isBlank(id)){
                return AjaxJson.fail("参数为空");
            }
            PurchaserMsg msg = purchaserMsgService.get(id);
            if (msg == null) {
                return AjaxJson.fail("无此消息");
            }
            purchaserMsgService.delete(msg);
        }
        return AjaxJson.ok();
    }
}
