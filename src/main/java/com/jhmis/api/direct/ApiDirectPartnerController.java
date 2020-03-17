package com.jhmis.api.direct;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.shop.entity.MdmCustomersPartner;
import com.jhmis.modules.shop.entity.MdmCustomersSource;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.service.MdmCustomersPartnerService;
import com.jhmis.modules.shop.service.MdmCustomersSourceService;
import com.jhmis.modules.shop.service.dealer.DealerAccountService;
import com.jhmis.modules.shop.service.dealer.DealerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "ApiDirectPartnerController", description = "直采四方关系接口")
@RestController
@RequestMapping("/api/direct/partner")
public class ApiDirectPartnerController {
    protected Logger logger = LoggerFactory.getLogger(ApiDirectPartnerController.class);

    @Autowired
    private MdmCustomersPartnerService mdmCustomersPartnerService;

    /**
     * 拉取四方关系（根据产品编码）
     * @return
     */
    @ApiOperation(notes = "getPartner", httpMethod = "GET", value = "拉取四方关系")
    @ApiImplicitParams({@ApiImplicitParam(name = "cuscode", value = "88码(88码用户)", required = true, paramType = "query", dataType = "String")})
    @RequestMapping("/getPartner")
    public AjaxJson getPartner(String cuscode) {
        logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectPartnerController  /getPartner  拉取四方关系----------接口开始*_*_*_*_*_*_*_*_*_*");
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isEmpty(cuscode)){
            return AjaxJson.fail("88码不能为空");
        }
        MdmCustomersPartner m = new MdmCustomersPartner();
        m.setCustomerNumber(cuscode);
        List<MdmCustomersPartner> mdmCustomersPartnerList = mdmCustomersPartnerService.findList(m);
        if(mdmCustomersPartnerList==null||mdmCustomersPartnerList.size()==0){
            return AjaxJson.fail("未查到相关信息");
        }
        //SH送达方
        List<MdmCustomersPartner> shList = new ArrayList<>();
        //PY付款方
        List<MdmCustomersPartner> pyList = new ArrayList<>();
        //BP开票方
        List<MdmCustomersPartner> bpList = new ArrayList<>();
        //循环将四方关系
        for(MdmCustomersPartner mcp:mdmCustomersPartnerList){
            //SH为送达方 PY付款方 SP售达方 BP开票方)
            if("SH".equals(mcp.getCustPartnerType())){
                shList.add(mcp);
            }
            if("PY".equals(mcp.getCustPartnerType())){
                pyList.add(mcp);
            }
            if("BP".equals(mcp.getCustPartnerType())){
                bpList.add(mcp);
            }
        }
        map.put("shList",shList);
        map.put("pyList",pyList);
        map.put("bpList",bpList);
        return AjaxJson.ok(map);
    }
}
