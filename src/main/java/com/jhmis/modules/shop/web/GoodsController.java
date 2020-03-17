/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.haier.link.upper.dto.ProductInfo;
import com.haier.link.upper.model.Sign;
import com.haier.link.upper.service.LinkProductUpperReadService;
import com.jhmis.common.utils.Constants;
import com.jhmis.modules.cms.entity.CmsConfig;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.cms.service.TemplateService;
import com.jhmis.modules.cms.utils.CmsUtils;
import com.jhmis.modules.cms.utils.Htmlpath;
import com.jhmis.modules.shop.entity.*;
import com.jhmis.modules.shop.service.*;

import freemarker.template.TemplateException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;

/**
 * 商品表Controller
 *
 * @author titydata
 * @version 2018-07-23
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/goods")
public class GoodsController extends BaseController {

    @Autowired
    private GoodsService goodsService;
@Autowired
private TemplateService templateService;
    @Autowired
    private GoodsCategoryService goodsCategoryService;

    private GoodsExtService goodsExtService;


    @Lazy
    @Autowired
    private LinkProductUpperReadService linkProductUpperReadService;
    @Autowired
    private BrandService brandService;

    long key = Constants.PRODUCTS_CENTER_KEY;
    String secret = Constants.PRODUCTS_CENTER_SECRET;

    @ModelAttribute
    public Goods get(@RequestParam(required = false) String id) {
        Goods entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = goodsService.get(id);
        }
        if (entity == null) {
            entity = new Goods();
        }
        return entity;
    }

    /**
     * 商品表列表页面
     */
    @RequiresPermissions("shop:goods:list")
    @RequestMapping(value = {"list", ""})
    public String list() {
        return "modules/shop/goodsList";
    }



    /**
     * 商品表列表数据
     */
    @ResponseBody
    @RequiresPermissions("shop:goods:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(Goods goods, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Goods> page = goodsService.findPage(new Page<Goods>(request, response), goods);
        page.setOrderBy("a.update_date desc");
        return getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑商品表表单页面
     */
    @RequiresPermissions(value = {"shop:goods:view", "shop:goods:add", "shop:goods:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(Goods goods, Model model) {
        if (StringUtils.isBlank(goods.getCode())) {//如果ID是空为添加
            model.addAttribute("isAdd", true);
            GoodsCategory goodsCategory = goodsCategoryService.get(goods.getCategoryId());
            GoodsCategory goodsParCategory = goodsCategoryService.get(goods.getCategoryPid());
            model.addAttribute("goodsCategory", goodsCategory);
            model.addAttribute("goodsParCategory", goodsParCategory);
            goods.setCat(goodsCategory);
        }else{
            goods = goodsService.get(goods);
            model.addAttribute("isAdd", false);
            if(null != goods.getMainPicUrl()){
                String []imgList = goods.getMainPicUrl().split("\\|");
                model.addAttribute("imgList", imgList);
                if("".equals(imgList[0])){
                    model.addAttribute("imgList", "");
                }
            }
        }
        List<String> goodsTemplate = templateService.getTemplateList(templatePath, "product_", false);
        model.addAttribute("goodsTemplate",goodsTemplate);
        //分类下拉列表
        Brand brand = new Brand();
        List<Brand> brandList = brandService.findList(brand);
        model.addAttribute("goods", goods);
        model.addAttribute("brandList", brandList);
        return "modules/shop/goodsForm";
    }

    /**
     * 拉取产品中心数据
     */
    @ResponseBody
    @RequestMapping(value = "sendProCode")
    public AjaxJson sendProCode(String code, RedirectAttributes redirectAttributes) throws Exception {
        //非空验证
        if("".equals(code)){
            logger.info("产品编码code为空");
            return AjaxJson.fail("产品编码code为空");
        }
        Goods goods = new Goods();
        goods.setCode(code);
        Goods isGoods = goodsService.get(goods);
        //验证是否已经存在
        if(isGoods!=null){
            logger.info(goods+ "此产品编码已存在");
            return AjaxJson.fail(" 此产品编码已存在");
        }
        Sign s = new Sign(key, secret);
        try{
            ProductInfo productInfo = linkProductUpperReadService.getProductInfoByCode(code, s).getResult();
            if(productInfo==null){
                return AjaxJson.fail("接口异常,产品中心数据查询为空" );
            }
        }catch (Exception e){
            return AjaxJson.fail("接口异常,异常原因:" + e.getMessage());
        }
        addMessage(redirectAttributes, "调取产品中心成功");
        return AjaxJson.ok("查询成功",linkProductUpperReadService.getProductInfoByCode(code, s).getResult());
    }



    /**
     * 批量导入一期数据
     */
    @ResponseBody
    @RequestMapping(value = "importTranche")
    public AjaxJson importTranche(RedirectAttributes redirectAttributes) throws Exception {
        AjaxJson j = new AjaxJson();
        Sign s = new Sign(key, secret);
        try{
            goodsService.importTranche(s,linkProductUpperReadService);
        }catch(Exception e){
            j.setSuccess(false);
            j.setMsg("导入一期商品失败！失败信息：" + e.getMessage());
        }
        return AjaxJson.ok("导入成功");
    }

    /**
     * 保存商品表
     */
    @RequiresPermissions(value = {"shop:goods:add", "shop:goods:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(Goods goods, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request,
                       String isToIndexYanxuan,String isToIndexBrand,String isToIndexZhengcai
    ) throws Exception {
        if (!beanValidator(model, goods)) {
            return form(goods, model);
        }
        //新增或编辑表单保存
        goodsService.save(goods,linkProductUpperReadService,key,secret);//保存
        addMessage(redirectAttributes, "保存商品表成功");
      //  redirectAttributes.addFlashAttribute("goods",goods);
/*
       //保存完毕后，判断是否发布至首页

        if(StringUtils.isNotBlank(isToIndexYanxuan) && ("1").equals(isToIndexYanxuan)){
            //发布到首页的 企业购严选

        }
        StoreGoods storeGoods=new StoreGoods();
        GoodsCategory category = null;
        Goods goods2 = null;
        String htmlPath = "";
        goods2 = goodsService.findByCode(goods.getCode());
        if (goods2 == null) {
            addMessage(redirectAttributes, "发布商品失败，请联系管理员，查看后台的商品！");
            return "redirect:" + Global.getAdminPath() + "/shop/goods/?repage";
        }
        storeGoods.setCode(goods.getCode());
        storeGoods.setMarketPrice(goods2.getMarketPrice());
        category = goodsCategoryService.findUniqueByProperty("parent_id", goods.getCategoryPid());
        if (category == null) {
            addMessage(redirectAttributes, "发布商品失败，请联系管理员，查看后台的品类！");
            return "redirect:" + Global.getAdminPath() + "/shop/goods/?repage";
        }
        storeGoods.setCategoryPid(goods2.getCategoryPid());
        storeGoods.setCategoryId(goods2.getCategoryId());
        // 查询此店铺信息
        Store store = storeService.selectByDealerId(account.getDealerId());
        if (store == null) {
            logger.info(
                    "*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/publishProduct  商品发布----------未查到此店铺或店铺信息异常,请重新登录或联系管理员 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("未查到此店铺或店铺信息异常,请重新登录或联系管理员");
        }
        // StoreGoods sg =
        // storeGoodsService.selectByCodeOrStoreId(account.getDealerId(),storeGoods.getCode());
        storeGoods.setStoreId(store.getId());
        try {
            if (null != storeGoods.getId()) {
                // 更新
                logger.info(
                        "*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/publishProduct  商品发布----------更新 *_*_*_*_*_*_*_*_*_*");
                storeGoods.setUpdateById(account.getId());
                storeGoodsService.updateStoreGoods(storeGoods, request, category, goods, templatePath,
                        storeGoods.getHtmlpath());
            } else {
                // 保存
                logger.info(
                        "*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/publishProduct  商品发布----------保存 *_*_*_*_*_*_*_*_*_*");
                storeGoods.setCreateById(account.getId());
                storeGoods.setCreateDate(new Date());
                // 获取最大索引号
                Integer num = storeGoodsService.selectMaxNum();
                storeGoods.setHtmlIndexNum(num);
                CmsConfig cmsConfig = CmsUtils.getCmsConfig();
                // 获取静态化的路径
                htmlPath = Htmlpath.goodsPath(cmsConfig, category, storeGoods);
                storeGoods.setHtmlpath(htmlPath);
                storeGoodsService.save(storeGoods, request, category, goods, templatePath, htmlPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(
                    "*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/publishProduct  商品发布----------发布产品失败,原因: "
                            + e.getMessage() + " *_*_*_*_*_*_*_*_*_*");
        }
        logger.info(
                "*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/publishProduct  商品发布----------接口结束 *_*_*_*_*_*_*_*_*_*");
        logger.info(
                "*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/publishProduct  商品发布----------静态化商品详情接口开始 *_*_*_*_*_*_*_*_*_*");

*/





        return "redirect:" + Global.getAdminPath() + "/shop/goods/?repage";
    }

    
	@RequiresPermissions(value={"shop:goods:add","shop:goods:view"},logical=Logical.OR)
	@RequestMapping(value = "confirmHtml")
	public String confirmHtml(Goods goods, Model model) {
		model.addAttribute("goods", goods);
		if(StringUtils.isBlank(goods.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/goodsMakeHtml";
	}
    
    
    
    
    /**
     * 删除商品表
     */
    @ResponseBody
    @RequiresPermissions("shop:goods:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(Goods goods, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        goodsService.delete(goods);
        j.setMsg("删除商品表成功");
        return j;
    }

    /**
     * 批量删除商品表
     */
    @ResponseBody
    @RequiresPermissions("shop:goods:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            goodsService.delete(goodsService.get(id));
        }
        j.setMsg("删除商品表成功");
        return j;
    }

    /**
     * 导出excel文件
     */
    @ResponseBody
    @RequiresPermissions("shop:goods:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public AjaxJson exportFile(Goods goods, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        try {
            String fileName = "商品表" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<Goods> page = goodsService.findPage(new Page<Goods>(request, response, -1), goods);
            new ExportExcel("商品表", Goods.class).setDataList(page.getList()).write(response, fileName).dispose();
            j.setSuccess(true);
            j.setMsg("导出成功！");
            return j;
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导出商品表记录失败！失败信息：" + e.getMessage());
        }
        return j;
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("shop:goods:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<Goods> list = ei.getDataList(Goods.class);
            for (Goods goods : list) {
                try {
                    goodsService.save(goods);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条商品表记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条商品表记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入商品表失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/shop/goods/?repage";
    }

    /**
     * 下载导入商品表数据模板
     */
    @RequiresPermissions("shop:goods:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "商品表数据导入模板.xlsx";
            List<Goods> list = Lists.newArrayList();
            new ExportExcel("商品表数据", Goods.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/shop/goods/?repage";
    }

    @RequestMapping("goodsCategoryMakeHtml")
	public String goodsCategoryMakeHtml(){
		return "modules/shop/goodsCategoryListMake";
	}
	
    
    
}