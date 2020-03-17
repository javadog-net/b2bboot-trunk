package com.jhmis.api.PC;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.modules.cms.entity.Navigation;
import com.jhmis.modules.cms.service.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: b2bboot
 * @description: 提供给商品列表导航栏使用的
 * @author: T.c
 * @create: 2019-12-09 16:03
 **/
@RestController
@RequestMapping("api/daohang")
public class DaohangUserController{
    @Autowired
    private NavigationService navigationService;

    @RequestMapping("/getdaohang")
    public AjaxJson getDaoHang(String parid){
      List<Navigation> navigationList = navigationService.findByPar(parid, "top","");
      return AjaxJson.ok(navigationList);
    }

}