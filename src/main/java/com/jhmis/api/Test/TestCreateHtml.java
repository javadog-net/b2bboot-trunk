package com.jhmis.api.Test;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.entity.CmsConfig;
import com.jhmis.modules.cms.service.HtmlService;
import com.jhmis.modules.cms.utils.CmsUtils;
import com.jhmis.modules.cms.utils.Htmlpath;
import com.jhmis.modules.shop.entity.Goods;
import com.jhmis.modules.shop.entity.GoodsCategory;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.service.GoodsCategoryService;
import com.jhmis.modules.shop.service.GoodsService;
import com.jhmis.modules.shop.service.StoreGoodsService;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @program: b2bboot
 * @description: 手动调用接口静态化
 * @author: T.c
 * @create: 2019-12-09 18:23
 **/
@Controller
@RequestMapping("/api/test/creatrehtml")
public class TestCreateHtml extends BaseController {
    @Resource
    private StoreGoodsService storeGoodsService;
    @Resource
    private GoodsCategoryService goodsCategoryService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private HtmlService htmlService;

    @ResponseBody
    @RequestMapping("/goodscreatehtml")
    public AjaxJson goodsCreateHtml(HttpServletRequest request) {

        List<StoreGoods> goodsList = storeGoodsService.findList(new StoreGoods());
        for (StoreGoods storeGoods : goodsList) {

            Goods goods = goodsService.findByCode(storeGoods.getCode());
            GoodsCategory category = goodsCategoryService.findUniqueByProperty("id", storeGoods.getCategoryPid());
            CmsConfig cmsConfig = CmsUtils.getCmsConfig();
            if(StringUtils.isBlank(storeGoods.getHtmlpath())){
                // 获取最大索引号
                Integer num = storeGoodsService.selectMaxNum();
                if(null == num){
                    num=0;
                }
                storeGoods.setHtmlIndexNum(num+1);
                // 获取静态化的路径
                String htmlPath = Htmlpath.goodsPath(cmsConfig, category, storeGoods);
                storeGoods.setHtmlpath(htmlPath);
            }
            storeGoodsService.update(storeGoods,request,CmsUtils.getTemplateWholePath(templatePath));


        }
        return AjaxJson.ok("全部商品静态化成功！");
    }


}