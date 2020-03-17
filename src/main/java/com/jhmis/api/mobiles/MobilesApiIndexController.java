package com.jhmis.api.mobiles;

import com.jhmis.common.annotation.SysLog;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.cms.service.CategoryService;
import com.jhmis.modules.cms.service.InfoService;
import com.jhmis.modules.shop.entity.GuessRecommend;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.service.GuessRecommendService;
import com.jhmis.modules.shop.service.StoreGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: b2bboot
 * @description: 移动端 首页接口
 * @author: T.c
 * @create: 2019-11-08 16:19
 **/
@RestController
@RequestMapping("api/mobiles/index")
public class MobilesApiIndexController extends BaseController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private InfoService infoService;
    @Autowired
    private GuessRecommendService guessRecommendService;
    @Autowired
    private StoreGoodsService storeGoodsService;
    /**
    *@Description 首页banner查询
    *@Param
    *@Return
    *@Author t.c
    *@Date 2019-11-11
    */
    @RequestMapping("/getbanner")
    public AjaxJson getBannerIndex(@RequestParam("categroy_id")String categroy_id){
        if(StringUtils.isBlank(categroy_id)){
            return AjaxJson.fail("categroy_id参数为空！");
        }
        List<Category> list=null;
        try {
            Category category=new Category();
            category.setParentIds(categroy_id);
            list= categoryService.findList(category);
            if(list==null || list.size()==0){
                return AjaxJson.fail("未查询到首页banner，categroy_id"+categroy_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("首页获取banner 异常"+e.getMessage());
        }

        return AjaxJson.ok(list);

    }
    /**
    *@Description 首页头条查询
    *@Param
    *@Return
    *@Author t.c
    *@Date 2019-11-11
    */
    @RequestMapping("/gettoutiao")
    public AjaxJson getTouTiao(@RequestParam("categroy_id")String categroy_id){
        if(StringUtils.isBlank(categroy_id)){
            return AjaxJson.fail("categroy_id参数为空！");
        }
        List<Info> list=null;
        try {
            Info info=new Info();
            info.setCategoryId(categroy_id);
            list=infoService.findList(info);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("获取首页头条信息异常："+e.getMessage());
        }
        return AjaxJson.ok(list);
    }
    /**
    *@Description 首页 品牌查询
    *@Param
    *@Return
    *@Author t.c
    *@Date 2019-11-11
    */
    @RequestMapping("/getbrand")
    public AjaxJson getBrandIndex(@RequestParam("categroy_id")String categroy_id){
        if(StringUtils.isBlank(categroy_id)){
            return AjaxJson.fail("categroy_id参数为空！");
        }
        List<Category> list=null;
        try {
            Category category=new Category();
            category.setParentIds(categroy_id);
            list= categoryService.findList(category);
            if(list==null || list.size()==0){
                return AjaxJson.fail("未查询到首页品牌 brand，categroy_id"+categroy_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("首页获取品牌brand 异常"+e.getMessage());
        }

        return AjaxJson.ok(list);

    }
    /**
    *@Description 首页案例查询
    *@Param
    *@Return
    *@Author t.c
    *@Date 2019-11-11
    */
    @RequestMapping("/getcase")
    public AjaxJson getCaseIndex(@RequestParam("categroy_id")String categroy_id){
        if(StringUtils.isBlank(categroy_id)){
            return AjaxJson.fail("categroy_id参数为空！");
        }
        List<Info> list=null;
        try {
            Info info=new Info();
            info.setCategoryId(categroy_id);
            list= infoService.findList(info);
            if(list==null || list.size()==0){
                return AjaxJson.fail("未查询到首页品牌 brand，categroy_id"+categroy_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("首页获取品牌brand 异常"+e.getMessage());
        }

        return AjaxJson.ok(list);

    }
    /**
    *@Description 移动端首页 产品经
    *@Param  由于是产品经，二级分类是0，即 FamilyLevel2=0
    *@Return
    *@Author t.c
    *@Date 2019-11-11
    */
    @RequestMapping("/findinfonewsindex")
    @SysLog(desc = "提供给移动端首页 产品讲坛 ")
    public AjaxJson findListInfoNewsindex(
            @RequestParam(value = "category_id") String category_id,
            @RequestParam(value = "family_level3", required = false) String family_level3) {
        List<Info> list = null;
        Info info = new Info();
        try {
            if (StringUtils.isNotEmpty(category_id)) {
                info.setCategoryId(category_id);
            }
                info.setFamilyLevel2("0");
            if (StringUtils.isNotEmpty(family_level3)) {
                info.setFamilyLevel3(family_level3);
            }
            Page<Info> page2 = new Page();
            page2.setOrderBy("is_recommend desc");
            page2.setPageSize(2);//只查询前2条 当作推荐处理
            page2.setPageNo(1);
            Page<Info> listpage = infoService.findPage(page2, info);
            list=listpage.getList();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("移动端首页 提供给 产品讲坛,出现异常信息：" + e.getMessage());
        }
        return AjaxJson.ok(list);
    }

    /**
    *@Description 提供给移动端首页 新闻行业 查询前三条 is_top 当banner .非置顶的 前4条放banner下
    *@Param
    *@Return
    *@Author t.c
    *@Date 2019-11-11
    */
    @RequestMapping("/findinfoinformationindex")
    @SysLog(desc = "提供给移动端首页 新闻行业 ")
    public AjaxJson findListInfoInformationindex(
            @RequestParam(value = "category_id") String category_id){
        List<Info> list = null;
        Info info = new Info();
        try {
            if (StringUtils.isNotEmpty(category_id)) {
                info.setCategoryId(category_id);
            }
            Page<Info> page2 = new Page();
            page2.setPageSize(3);//只查询前3条 当作banner处理
            page2.setPageNo(1);
            info.setIsRecommend("1");// 推荐 加 固顶的
            info.setIsTop("1");
            Page<Info> listpage = infoService.findPage(page2, info);
            list=listpage.getList();
            Info info1=new Info();
            if (StringUtils.isNotEmpty(category_id)) {
                info1.setCategoryId(category_id);
            }
            Page<Info> page = new Page();
            page.setPageSize(4);//只查询前4条 放在banner后处理
            page.setPageNo(1);
            info1.setIsTop("0");//只查询非置顶的
            Page<Info> listisnottop = infoService.findPage(page, info1);
            //将 固顶的 放在最前面
            list.addAll(listisnottop.getList());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("移动端首页 提供给 新闻行业,出现异常信息：" + e.getMessage());
        }
        return AjaxJson.ok(list);
    }
    /**
    *@Description 移动端首页 智能推荐的人数
    *@Param
    *@Return
    *@Author t.c
    *@Date 2019-11-15
    */
    @RequestMapping(value="/getguessnum")
    public AjaxJson getGuessNum(){
           GuessRecommend guessRecommend= guessRecommendService.get("418a747ff49c4fiu9fd9603a656506ds");
           return AjaxJson.ok(guessRecommend);
    }

    /**
    *@Description 首页精选推荐
    *@Param storeId, 店铺id
     *      categoryId, 分类id
     *      categoryPid, 分类父级id
     *      isToIndexYanxuan 企业购严选 1 是 ，0 否。
    *@Return
    *@Author t.c
    *@Date 2019-11-25
    */
    @RequestMapping(value="/getjingxuan")
    public AjaxJson getJingXuan(StoreGoods storeGoods){
        //必须是上架的产品
        storeGoods.setIsShelf(Global.YES);
        storeGoods.setStoreId("0");
        //冰箱：1ee13e105f4b4a7d8627d8fed674bd77
        //中央空调：4c5563fece494b8296ed4150154adff4
        //空调（家用空调）：515c435eab2c4cd9bda5dc2107283876
        //洗衣机：87828af2fd9644848dd53a6b979e957c
        //热水器：d286e61286f5400fb0765be9b0fded48
        String str="1ee13e105f4b4a7d8627d8fed674bd77,4c5563fece494b8296ed4150154adff4,515c435eab2c4cd9bda5dc2107283876," +
                "87828af2fd9644848dd53a6b979e957c,d286e61286f5400fb0765be9b0fded48";
        List<StoreGoods> storeGoodsList=new ArrayList<>();
        String[] arr=str.split(",");
        for(int i=0;i<arr.length;i++) {
            storeGoods.setCategoryPid(arr[i]);
            Page p = new Page();
            p.setPageNo(1);
            p.setPageSize(2);
            Page<StoreGoods> pageList = storeGoodsService.findPage(p, storeGoods);
            storeGoodsList.addAll(pageList.getList());
        }

        return AjaxJson.layuiTable(storeGoodsList);
    }

    @RequestMapping("/getbannertocategoryid")
    public AjaxJson getBannerCategory(@RequestParam("categroy_id")String categroy_id){
        if(StringUtils.isBlank(categroy_id)){
            return AjaxJson.fail("categroy_id参数为空！");
        }
        Category category=null;
        try {
            category= categoryService.get(categroy_id);
            if(category==null ){
                return AjaxJson.fail("未查询到首页banner，categroy_id"+categroy_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("首页获取banner 异常"+e.getMessage());
        }

        return AjaxJson.ok(category);

    }
/**
*@Description移动端关于疫情专区的
*@Param
*@Return
*@Author t.c
*@Date 2020-02-07
*/
    @RequestMapping(value="/getyiqing")
    public AjaxJson getYiqing(StoreGoods storeGoods){
        //必须是上架的产品
        storeGoods.setIsShelf(Global.YES);
        storeGoods.setStoreId("0");
            Page p = new Page();
            p.setPageNo(1);
            p.setPageSize(4);
            Page<StoreGoods> pageList = storeGoodsService.findPage(p, storeGoods);

        return AjaxJson.layuiTable(pageList);
    }


    
}