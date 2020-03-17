package com.jhmis.api.common;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.JWTUtils;
import com.jhmis.core.taskqueue.Task;
import com.jhmis.core.taskqueue.TaskType;
import com.jhmis.core.taskqueue.TaskUtils;
import com.jhmis.modules.shop.service.dealer.DealerMsgService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/common/test")
public class CommonTestController {
    @Autowired
    DealerMsgService dealerMsgService;
    @RequestMapping("/put")
    public AjaxJson put(){
        Map<String,String> params = new HashMap<>();
        params.put("complain_id","sdf");
        dealerMsgService.putMsg("complain","1","1",params);
        return AjaxJson.ok();
    } 

}
