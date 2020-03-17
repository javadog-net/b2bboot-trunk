package com.jhmis.api.customer;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jhmis.modules.customer.entity.CustomerMsg;
import com.jhmis.modules.customer.entity.ViewQygBrownInfo;
import com.jhmis.modules.customer.service.ViewQygBrownInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "ApiHpsViewUpdateController", description = "客单同步hps相关视图")
@RestController
@RequestMapping("/api/customer")
public class ApiHpsViewUpdateController {
    @Autowired
    @Qualifier("secondJdbcTemplate")
    protected JdbcTemplate jdbcTemplate1;

    @Autowired
    protected ViewQygBrownInfoService viewQygBrownInfoService;
    
    /**
     * 经销商报备需求
     * @return
     */
    @ApiOperation(notes = "getviewQYGBrownInfo", httpMethod = "GET", value = "经销商报备需求", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping("/getviewQYGBrownInfo")
    public void getviewQYGBrownInfo(CustomerMsg customerMsg){
    	
       //查询view_QYG_project 视图
       String view_qyg_brown_info_select_sql = "select * from view_QYG_brown_info";
       
       //根据实体反射进行转换
       List<ViewQygBrownInfo> listViewQygBrownInfo = jdbcTemplate1.query(view_qyg_brown_info_select_sql, new BeanPropertyRowMapper(ViewQygBrownInfo.class));
       if(listViewQygBrownInfo!=null && listViewQygBrownInfo.size()>0){
           //证明有效逐条插入
           for(ViewQygBrownInfo viewQygBrownInfo:listViewQygBrownInfo){
           	ViewQygBrownInfo brownInfo = viewQygBrownInfoService.get(viewQygBrownInfo.getId());           	
           	if(brownInfo==null){
           		viewQygBrownInfoService.insert(viewQygBrownInfo);
           	}else{
           		viewQygBrownInfoService.update(viewQygBrownInfo);
           	}
           }
       }
    }
    
    
}
