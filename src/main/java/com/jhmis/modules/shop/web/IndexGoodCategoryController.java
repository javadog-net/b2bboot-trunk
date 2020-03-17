package com.jhmis.modules.shop.web;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.service.CategoryService;
import com.jhmis.modules.shop.entity.*;
import com.jhmis.modules.shop.service.GoodsCategoryService;
import com.jhmis.modules.shop.service.StoreGoodsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: b2bboot
 * @description: 提供给前端伪静态化产品列表接口
 * @author: T.c
 * @create: 2019-11-25 20:05
 **/
@Controller
@RequestMapping(value = "index/to")
public class IndexGoodCategoryController extends BaseController{

    @Resource
    private GoodsCategoryService goodsCategoryService;
    @Resource
    private StoreGoodsService storeGoodsService;
//@ResponseBody
@RequestMapping(value="/goodscategory/goodList")
    public String getGoodsCategory(Model model){

        String zhongyangkongtiao="4c5563fece494b8296ed4150154adff4";
        String jiayongkongtiao="515c435eab2c4cd9bda5dc2107283876";
        String lenggui ="8313d77be9904a18b3f758045681007d";
        String reshuiqi="d286e61286f5400fb0765be9b0fded48";
        String xiyiji="87828af2fd9644848dd53a6b979e957c";
        String dianshi="b710c457f11541c7a5cbe568a47bf0fa";
        String bingxiang="1ee13e105f4b4a7d8627d8fed674bd77";
        String chudian="0dcb2376ed0e41089f854b849cafbb76";
        String zhinengjiadian="499eec3771c048f2b55dd334e2742e2e";//智能家电栏目
        String jingshui="037356d99ba74df7b4f76fdecb634f93";
      /*  String str=zhongyangkongtiao+","+jiayongkongtiao+","+lenggui+
                ","+reshuiqi+","+xiyiji+","+dianshi+","+bingxiang+
                ","+chudian+","+zhinengdianshi+","+jingshui;*/
        List<String> list= new ArrayList<>();
       list.add(zhongyangkongtiao);
       list.add(jiayongkongtiao);
       list.add(lenggui);
       list.add(reshuiqi);
       list.add(xiyiji);
       list.add(dianshi);
       list.add(bingxiang);
       list.add(chudian);
       list.add(zhinengjiadian);
       list.add(jingshui);
        Map<String,Object> map=new HashMap<>();
        GoodsCategory goodsCategory1= goodsCategoryService.get(zhongyangkongtiao);
        GoodsCategory goodsCategory2= goodsCategoryService.get(jiayongkongtiao);
        GoodsCategory goodsCategory3= goodsCategoryService.get(lenggui);
        GoodsCategory goodsCategory4= goodsCategoryService.get(reshuiqi);
        GoodsCategory goodsCategory5= goodsCategoryService.get(xiyiji);
        GoodsCategory goodsCategory6= goodsCategoryService.get(dianshi);
        GoodsCategory goodsCategory7= goodsCategoryService.get(bingxiang);
        GoodsCategory goodsCategory8= goodsCategoryService.get(chudian);
        GoodsCategory goodsCategory9= goodsCategoryService.get(zhinengjiadian);
        GoodsCategory goodsCategory10= goodsCategoryService.get(jingshui);
        map.put("zhongyangkongtiao",goodsCategory1);
        map.put("jiayongkongtiao",goodsCategory2);
        map.put("lenggui",goodsCategory3);
        map.put("reshuiqi",goodsCategory4);
        map.put("xiyiji",goodsCategory5);
        map.put("dianshi",goodsCategory6);
        map.put("bingxiang",goodsCategory7);
        map.put("chudian",goodsCategory8);
        map.put("zhinengjiadian",goodsCategory9);
        map.put("jingshui",goodsCategory10);

        Page page=new Page();
        page.setPageSize(8);
        page.setPageNo(1);
        for(int i=0;i<list.size();i++) {
        StoreGoods storeGoods=new StoreGoods();
        storeGoods.setIsShelf("1");
        storeGoods.setCategoryPid(list.get(i));
            Page<StoreGoods> pageList=new Page<StoreGoods>();
            System.out.println("循环次数"+i+"============");
            System.out.println("storeGoods:"+storeGoods);
            pageList= storeGoodsService.findPage(page, storeGoods);
            map.put("goodsList"+i,pageList.getList());
        }
   /* storeGoodsService.findPage(page, storeGoods);
        StoreGoods storeGoods=new StoreGoods();
        storeGoods.setIsShelf("1");
        storeGoods.setCategoryPid(zhongyangkongtiao);
        map.put("zhongyangkongtiaolist",);
   */
   model.addAttribute("map",map);
        return "to_index/appliance";


    }

@RequestMapping(value="/tocategorylist")
    public String toCategoryList(StoreGoods storeGoods, HttpServletRequest request,
                                 HttpServletResponse response,Model model){
    storeGoods.setIsShelf("1");
    String pId=request.getParameter("categoryPid");
    storeGoods.setCategoryPid(pId);
    Page<StoreGoods> pageList=storeGoodsService.findPage(new Page<StoreGoods>(request, response),storeGoods);
      model.addAttribute("goodList",pageList);
      model.addAttribute("category",goodsCategoryService.get(storeGoods.getCategoryPid()));
      GoodsCategory goodsCategory=new GoodsCategory();
      goodsCategory.setParentIds(storeGoods.getCategoryPid());
      List<GoodsCategory> listGoodsCategory=goodsCategoryService.findList(goodsCategory);
      model.addAttribute("listGoodsCategory",listGoodsCategory);
      return "to_index/refrigerator";
    }


    @ResponseBody
    @RequestMapping(value="/tocategorylisttwo")
    public AjaxJson toCategoryListTwo(HttpServletRequest request,
                                 HttpServletResponse response,Map map){
        StoreGoods storeGoods=new StoreGoods();
        storeGoods.setIsShelf("1");
        String categoryId=request.getParameter("categoryId");
        String key=request.getParameter("key");
        String categoryPid=request.getParameter("categoryPid");
        String goodsSalenum=request.getParameter("goodsSalenum");//销量
        String goodsCollect=request.getParameter("goodsCollect");//收藏量
        String isPrice=request.getParameter("isPrice");//价格
        String goodsClick=request.getParameter("goodsClick");//浏览量
        String sql="";
        if(StringUtils.isNotBlank(goodsSalenum)&&("1").equals(goodsSalenum)){
            sql="goods_salenum,";
        }
        if(StringUtils.isNotBlank(goodsSalenum)&&("0").equals(goodsSalenum)){
            sql="goods_salenum desc,";
        }
        if(StringUtils.isNotBlank(goodsCollect)&&("0").equals(goodsCollect)){
            sql=sql+"goods_collect desc,";
        }
        if(StringUtils.isNotBlank(goodsCollect)&&("1").equals(goodsCollect)){
            sql=sql+"goods_collect,";
        }
        if(StringUtils.isNotBlank(goodsClick)&&("0").equals(goodsClick)){
            sql=sql+"goods_click desc,";
        }
        if(StringUtils.isNotBlank(goodsClick)&&("1").equals(goodsClick)){
            sql=sql+"goods_click,";
        }
        if(StringUtils.isNotBlank(isPrice)&&("0").equals(isPrice)){
            sql=sql+"market_price desc,";
        }
        if(StringUtils.isNotBlank(isPrice)&&("1").equals(isPrice)){
            sql=sql+"market_price,";
        }
        if(!("").equals(sql)&&sql.length()>0&&sql.endsWith(",")){
          sql=sql.substring(0,sql.length()-1);
        }
        if(StringUtils.isNotBlank(categoryId)) {
            storeGoods.setCategoryId(categoryId);
        }
        if(StringUtils.isNotBlank(categoryPid)) {
            storeGoods.setCategoryPid(categoryPid);
        }
        if(StringUtils.isNotBlank(key)){
            storeGoods.setGoodsName(key);
        }
        Page page=new Page<StoreGoods>(request, response);
        page.setOrderBy(sql);
        Page<StoreGoods> pageList=storeGoodsService.findPage(page,storeGoods);
        map.put("goodList",pageList);
        map.put("category",goodsCategoryService.get(storeGoods.getCategoryPid()));
        GoodsCategory goodsCategory=new GoodsCategory();
        goodsCategory.setParentIds(storeGoods.getCategoryPid());
        List<GoodsCategory> listGoodsCategory=goodsCategoryService.findList(goodsCategory);
        map.put("listGoodsCategory",listGoodsCategory);
        return AjaxJson.ok(map);
    }




    @ResponseBody
    @RequestMapping(value="/tocategorylist2")
    public AjaxJson toCategoryList2(StoreGoods storeGoods, HttpServletRequest request,
                                   HttpServletResponse response, Model model){
        storeGoods.setIsShelf("1");
        Page<StoreGoods> pageList=storeGoodsService.findPage(new Page<StoreGoods>(request, response),storeGoods);
        model.addAttribute("goodList",pageList);
        GoodsCategory goodsCategory=new GoodsCategory();
        goodsCategory.setParentIds(storeGoods.getCategoryPid());
        List<GoodsCategory> listGoodsCategory=goodsCategoryService.findList(goodsCategory);
        model.addAttribute("listGoodsCategory",listGoodsCategory);
        Map map=new HashMap<String ,Object>();
        map.put("goodList",pageList);
        map.put("listGoodsCategory",listGoodsCategory);
        return AjaxJson.ok(map);
    }


    public static void main(String[] args) {

    String str="600000MT4'," +
            "'000dc1f8-f579-4f30-b700-a86eda629b2d000dc1f8-f579-4f30-b700-a86eda629b2d'," +
            "'00beb1b1-1835-4f32-ab7d-30b24f55e31700beb1b1-1835-4f32-ab7d-30b24f55e317'," +
            "'0109200c-3b9e-43a6-9944-17ba3c2a31ca0109200c-3b9e-43a6-9944-17ba3c2a31ca'," +
            "'021433b8-ad46-4434-9af9-0eeba78131aa021433b8-ad46-4434-9af9-0eeba78131aa'," +
            "'02dd2e46-7d8b-4bae-9d64-615b5ab3416202dd2e46-7d8b-4bae-9d64-615b5ab34162'," +
            "'04ab3877-93c0-47e2-b24d-2a4633fae1a704ab3877-93c0-47e2-b24d-2a4633fae1a7'," +
            "'0505ff6d-2aa8-44a8-97dd-9bf27e769d6e0505ff6d-2aa8-44a8-97dd-9bf27e769d6e'," +
            "'05732b37-256c-4e68-a88e-5335cff256a405732b37-256c-4e68-a88e-5335cff256a4'," +
            "'0573912b-8229-494e-aee5-f00bb584e6d50573912b-8229-494e-aee5-f00bb584e6d5'," +
            "'05a322c7-ed08-475a-a7d5-2c775d05700905a322c7-ed08-475a-a7d5-2c775d057009'," +
            "'05aed090-6f6c-40eb-a3ab-f99ba6e99b6205aed090-6f6c-40eb-a3ab-f99ba6e99b62'," +
            "'05f4632d-8af8-4fdb-a300-b8e7aff6ff0405f4632d-8af8-4fdb-a300-b8e7aff6ff04'," +
            "'068a437b-f7a2-40bf-bfb8-b727d0103eea068a437b-f7a2-40bf-bfb8-b727d0103eea'," +
            "'06948de6-d08e-4ad5-aeaa-1565f6b7a70106948de6-d08e-4ad5-aeaa-1565f6b7a701'," +
            "'071b873d-75b0-45d1-a305-1a410ee4d3f6071b873d-75b0-45d1-a305-1a410ee4d3f6'," +
            "'071dd31c-024f-476b-8f03-2b86f0649b3c071dd31c-024f-476b-8f03-2b86f0649b3c'," +
            "'07a4344e-6544-4921-b8bc-46d8df7e660607a4344e-6544-4921-b8bc-46d8df7e6606'," +
            "'08a62895-14ef-44c9-814f-0024583323a608a62895-14ef-44c9-814f-0024583323a6'," +
            "'093678ff-8946-491f-853b-ce7ee7583208093678ff-8946-491f-853b-ce7ee7583208'," +
            "'093d28ee-cab5-4319-b0d0-5acaf940e88d093d28ee-cab5-4319-b0d0-5acaf940e88d'," +
            "'09ea6c26-fd83-4989-8fb1-8433932ef93109ea6c26-fd83-4989-8fb1-8433932ef931'," +
            "'0acafe37-a4db-409b-b54d-083bb335e4e50acafe37-a4db-409b-b54d-083bb335e4e5'," +
            "'0acfe320-8ce0-4dd9-bb13-11d67da174e40acfe320-8ce0-4dd9-bb13-11d67da174e4'," +
            "'0be3c76f-9d1b-445f-9256-69eddf5a33ec0be3c76f-9d1b-445f-9256-69eddf5a33ec'," +
            "'0cbeb1af-aa5a-4468-8910-12cba41a71be0cbeb1af-aa5a-4468-8910-12cba41a71be'," +
            "'0d1fd18c-8828-45fc-b1f9-281fda952e2c0d1fd18c-8828-45fc-b1f9-281fda952e2c'," +
            "'0dfc3a66-34e3-4028-9ad6-3eb05d4dc0760dfc3a66-34e3-4028-9ad6-3eb05d4dc076'," +
            "'0fb7b8bf-baed-4de1-8d73-55abede219820fb7b8bf-baed-4de1-8d73-55abede21982'," +
            "'0fe0ad33-fd8c-412f-8721-2e19ab60c2200fe0ad33-fd8c-412f-8721-2e19ab60c220'," +
            "'109b1fdf-9a19-40b9-997d-73783277353b109b1fdf-9a19-40b9-997d-73783277353b'," +
            "'114374cd-bf78-4a2b-ae70-48a11b175b5d114374cd-bf78-4a2b-ae70-48a11b175b5d'," +
            "'134e46b7-6ba9-4abc-b278-93a7eee0ca42134e46b7-6ba9-4abc-b278-93a7eee0ca42'," +
            "'137ba906-0321-4961-9c53-44d13d465ac3137ba906-0321-4961-9c53-44d13d465ac3'," +
            "'14227b28-4fa5-4e2e-83cc-4636131638d514227b28-4fa5-4e2e-83cc-4636131638d5'," +
            "'144b499b-9d4d-4f7c-9c51-d847a22fdf0e144b499b-9d4d-4f7c-9c51-d847a22fdf0e'," +
            "'149ff7a8-ad7b-43e4-a54a-29c67e019a3f149ff7a8-ad7b-43e4-a54a-29c67e019a3f'," +
            "'15b9d7a1-09cc-49e4-8672-da91b94c6f2e15b9d7a1-09cc-49e4-8672-da91b94c6f2e'," +
            "'1628dbf1-a7f8-49ef-a2e4-0fbce710fbf81628dbf1-a7f8-49ef-a2e4-0fbce710fbf8'," +
            "'1642b3f4-ee8e-4a16-8a2f-e947013e9e671642b3f4-ee8e-4a16-8a2f-e947013e9e67'," +
            "'16d6d31c-dbf0-403e-9718-cce8b78126f816d6d31c-dbf0-403e-9718-cce8b78126f8'," +
            "'173d0f28-0bc4-4c75-ad8b-81d0fd1a130a173d0f28-0bc4-4c75-ad8b-81d0fd1a130a'," +
            "'174f2269-7708-4017-a991-e1017227c14e174f2269-7708-4017-a991-e1017227c14e'," +
            "'18207114-b690-46a6-a566-01beb33f42fd18207114-b690-46a6-a566-01beb33f42fd'," +
            "'18371542-7d16-4017-9046-5fa86223ca0618371542-7d16-4017-9046-5fa86223ca06'," +
            "'1845b8ae-098a-4f00-be36-e1a1498516451845b8ae-098a-4f00-be36-e1a149851645'," +
            "'187f34a2-490d-401d-b204-8bf1a2d871b8187f34a2-490d-401d-b204-8bf1a2d871b8'," +
            "'18b510a8-1275-4aec-ab87-a3dd12c17f1d18b510a8-1275-4aec-ab87-a3dd12c17f1d'," +
            "'1a159e6d-7bcd-48b5-ab71-f7494ef67ff31a159e6d-7bcd-48b5-ab71-f7494ef67ff3'," +
            "'1af691c2-4d6a-4d8a-bb9e-614cd44ac8b21af691c2-4d6a-4d8a-bb9e-614cd44ac8b2'," +
            "'1b264bf1-62cf-4b84-916f-b2a28033997f1b264bf1-62cf-4b84-916f-b2a28033997f'," +
            "'1bcaafeb-35b4-41a6-9bac-e2637efb1b6f1bcaafeb-35b4-41a6-9bac-e2637efb1b6f'," +
            "'1be5f77f-f6f0-4fe2-b8e1-8487b0a2102a1be5f77f-f6f0-4fe2-b8e1-8487b0a2102a'," +
            "'1c47eb48-3058-4ffa-b394-e24a6a577f1c1c47eb48-3058-4ffa-b394-e24a6a577f1c'," +
            "'1c6e0a53-1070-45cb-9262-d820e02e197d1c6e0a53-1070-45cb-9262-d820e02e197d'," +
            "'1cb2354da4d04e52b064f626a770b27d1cb2354da4d04e52b064f626a770b27d'," +
            "'1e0cca3f-ea39-4ae9-8769-e6fcb81e006b1e0cca3f-ea39-4ae9-8769-e6fcb81e006b'," +
            "'1e20d5f6-eab3-45a1-b025-53eaf40bbf261e20d5f6-eab3-45a1-b025-53eaf40bbf26'," +
            "'20143801-3c90-4cf7-ac74-488e9b81511f20143801-3c90-4cf7-ac74-488e9b81511f'," +
            "'20677d50-a4fb-42b9-86ee-6d932296758120677d50-a4fb-42b9-86ee-6d9322967581'," +
            "'20963403-966d-4160-ab05-1243223e9fe720963403-966d-4160-ab05-1243223e9fe7'," +
            "'20bfc518-9081-4e61-9264-4977eaf7483920bfc518-9081-4e61-9264-4977eaf74839'," +
            "'20de6673-ee86-4ce0-ab05-dc8d8bf553e120de6673-ee86-4ce0-ab05-dc8d8bf553e1'," +
            "'211a2866-2433-443f-ab8e-e6b3ee3c597a211a2866-2433-443f-ab8e-e6b3ee3c597a'," +
            "'21c0a7fe-16fb-45ba-b7fd-3451d160004e21c0a7fe-16fb-45ba-b7fd-3451d160004e'," +
            "'21e9bbdd-5f4d-4d04-9f20-f2a4271d116b21e9bbdd-5f4d-4d04-9f20-f2a4271d116b'," +
            "'2218357e-aaa2-4dca-9be6-f7f13d4a3ecf2218357e-aaa2-4dca-9be6-f7f13d4a3ecf'," +
            "'222a1c6f-86a9-4750-a949-17d447febf88222a1c6f-86a9-4750-a949-17d447febf88'," +
            "'223ad32d-302b-4617-a416-7285a3b2ea54223ad32d-302b-4617-a416-7285a3b2ea54'," +
            "'22cd3f38-8683-4aee-95b3-c098de0a9e1822cd3f38-8683-4aee-95b3-c098de0a9e18'," +
            "'237a8cf3-1f68-474e-9824-7e53a99c263c237a8cf3-1f68-474e-9824-7e53a99c263c'," +
            "'23ce5243-7d9c-49d8-b721-12d30c72245023ce5243-7d9c-49d8-b721-12d30c722450'," +
            "'240b40e7-fe6b-4fd4-9f2b-b1948b1663d4240b40e7-fe6b-4fd4-9f2b-b1948b1663d4'," +
            "'241eecce-bfd1-4754-907b-6d4aac629142241eecce-bfd1-4754-907b-6d4aac629142'," +
            "'2497a927-7d72-4e4d-9c1a-31dc976bfa6b2497a927-7d72-4e4d-9c1a-31dc976bfa6b'," +
            "'24c143bf-da67-44c1-9b3e-7b8847f100f424c143bf-da67-44c1-9b3e-7b8847f100f4'," +
            "'250730ef-11b7-4a52-a8aa-cfdb3591f157250730ef-11b7-4a52-a8aa-cfdb3591f157'," +
            "'268be474-da97-46a3-8664-a7aa95eef421268be474-da97-46a3-8664-a7aa95eef421'," +
            "'26d98073-d281-4e9c-b5ba-b1444e749ac726d98073-d281-4e9c-b5ba-b1444e749ac7'," +
            "'27a0f573-6bf6-4ad4-8f09-1b3a28ce7da027a0f573-6bf6-4ad4-8f09-1b3a28ce7da0'," +
            "'28632f51-4205-4a88-948c-98295926aac228632f51-4205-4a88-948c-98295926aac2'," +
            "'28dc3572-0392-4177-8d63-267f286fa62f28dc3572-0392-4177-8d63-267f286fa62f'," +
            "'28f16818-1751-420c-a550-83dc0afdd99828f16818-1751-420c-a550-83dc0afdd998'," +
            "'296ff031-bdd2-4dc2-8d1e-8b787a9ac1d7296ff031-bdd2-4dc2-8d1e-8b787a9ac1d7'," +
            "'29a51fab-ecfe-436d-b219-122a08e335f329a51fab-ecfe-436d-b219-122a08e335f3'," +
            "'29ec3fb2-4c8b-4271-b0ba-aeee03d2862029ec3fb2-4c8b-4271-b0ba-aeee03d28620'," +
            "'2a5366e8-eaea-4189-8c2a-12dbd059b5c62a5366e8-eaea-4189-8c2a-12dbd059b5c6'," +
            "'2b50426c-5744-44a2-875b-f8621aed927d2b50426c-5744-44a2-875b-f8621aed927d'," +
            "'2cbe26fb-cc1b-4475-88d5-837a232c14b72cbe26fb-cc1b-4475-88d5-837a232c14b7'," +
            "'2ced2a28-15ae-461c-aa04-d0b8c9b6b52e2ced2a28-15ae-461c-aa04-d0b8c9b6b52e'," +
            "'2d6a39d9-3200-49be-aaf0-a3d22f37a8ba2d6a39d9-3200-49be-aaf0-a3d22f37a8ba'," +
            "'2d847304-5752-427a-97c7-19e77aa51a192d847304-5752-427a-97c7-19e77aa51a19'," +
            "'2d90a66f-95b1-4977-9ee2-f828c29fdc4e2d90a66f-95b1-4977-9ee2-f828c29fdc4e'," +
            "'2dbf0936-b790-416b-966e-76b1bbf100962dbf0936-b790-416b-966e-76b1bbf10096'," +
            "'2dd15886-c0e8-4d7d-a1ab-40304dbe3d882dd15886-c0e8-4d7d-a1ab-40304dbe3d88'," +
            "'2e1f9b9e-c7ce-496d-a185-8fc56131156f2e1f9b9e-c7ce-496d-a185-8fc56131156f'," +
            "'2e353063-5c6d-40f0-8a80-27db533460d42e353063-5c6d-40f0-8a80-27db533460d4'," +
            "'2e93dfc7-a3c0-416e-993f-8ebffa227bf62e93dfc7-a3c0-416e-993f-8ebffa227bf6'," +
            "'2eda37e3-75db-46d4-8501-13e23df70c5c2eda37e3-75db-46d4-8501-13e23df70c5c'," +
            "'2f5bf0e8-25bf-4b91-8a99-6b80df2989e42f5bf0e8-25bf-4b91-8a99-6b80df2989e4'," +
            "'31930556-8e9b-4d28-9646-96dbbf73f49831930556-8e9b-4d28-9646-96dbbf73f498'," +
            "'3196b06e-3bb1-4147-ab51-a60abdb038f23196b06e-3bb1-4147-ab51-a60abdb038f2'," +
            "'31bd001eec6d40f5bcac3a1baf40de3131bd001eec6d40f5bcac3a1baf40de31'," +
            "'31c5b224-2e16-43b2-b1e1-32d9d150750331c5b224-2e16-43b2-b1e1-32d9d1507503'," +
            "'342da7af-e424-46ca-b6e3-f4f840559ee1342da7af-e424-46ca-b6e3-f4f840559ee1'," +
            "'3492421f-5953-457a-8fbe-47ac146e800b3492421f-5953-457a-8fbe-47ac146e800b'," +
            "'34a854d7-7688-434f-8856-88c56569d7f834a854d7-7688-434f-8856-88c56569d7f8'," +
            "'34f75b7b-02e8-483d-ad79-e5c93b48847e34f75b7b-02e8-483d-ad79-e5c93b48847e'," +
            "'35088976-508b-4548-81fb-32cd8dd4187435088976-508b-4548-81fb-32cd8dd41874'," +
            "'3546202d-98bd-439b-bbb3-af2f99e7fc493546202d-98bd-439b-bbb3-af2f99e7fc49'," +
            "'356cc866-9f81-449e-92f2-68a07192b198356cc866-9f81-449e-92f2-68a07192b198'," +
            "'35b9b312-3ded-41c9-a88f-04c21f4a81b135b9b312-3ded-41c9-a88f-04c21f4a81b1'," +
            "'374331a5-f80e-4b50-9b94-b968b2beee32374331a5-f80e-4b50-9b94-b968b2beee32'," +
            "'375ed2ae-2a29-452d-94d3-57fad001fafb375ed2ae-2a29-452d-94d3-57fad001fafb'," +
            "'37bc1075-3c2e-4dcb-91b7-48ff5761520a37bc1075-3c2e-4dcb-91b7-48ff5761520a'," +
            "'38159490-4011-41ab-8f8b-c43cb80b954438159490-4011-41ab-8f8b-c43cb80b9544'," +
            "'382f7850-d441-406a-bde7-65cf7f5b4e60382f7850-d441-406a-bde7-65cf7f5b4e60'," +
            "'38a70e76-d2db-4a2c-8b37-72eb6b5aa51e38a70e76-d2db-4a2c-8b37-72eb6b5aa51e'," +
            "'38ad349b-60c2-46bc-b8fd-3ae1110f19d338ad349b-60c2-46bc-b8fd-3ae1110f19d3'," +
            "'38d72579-a8aa-4b2c-b819-2a974302c9e138d72579-a8aa-4b2c-b819-2a974302c9e1'," +
            "'39326b34-175c-4c40-b75e-7e247632e2e139326b34-175c-4c40-b75e-7e247632e2e1'," +
            "'39a4f0ad-524f-42f5-9a35-c00d554df28c39a4f0ad-524f-42f5-9a35-c00d554df28c'," +
            "'39bfdcdc-b48f-46b7-9d6c-7f68d8d228fa39bfdcdc-b48f-46b7-9d6c-7f68d8d228fa'," +
            "'39eded37-451a-45f8-a4c0-48658baf6fd239eded37-451a-45f8-a4c0-48658baf6fd2'," +
            "'39fdc7a3-83af-4f89-8f09-2ff198bfe11c39fdc7a3-83af-4f89-8f09-2ff198bfe11c'," +
            "'3a58c802-822d-4f39-89ea-e1e5b63af8393a58c802-822d-4f39-89ea-e1e5b63af839'," +
            "'3b03c06c-5e15-481c-8e12-a6d8a8ad90533b03c06c-5e15-481c-8e12-a6d8a8ad9053'," +
            "'3b1756a6-75da-4098-8dd9-27072a4a21c23b1756a6-75da-4098-8dd9-27072a4a21c2'," +
            "'3b3b4827-22ac-439a-83a3-c25ae22921b23b3b4827-22ac-439a-83a3-c25ae22921b2'," +
            "'3b5189f2-64fa-47a0-85ec-99b15a1ceaa93b5189f2-64fa-47a0-85ec-99b15a1ceaa9'," +
            "'3b6087d5-4c15-4a3a-a8e0-948eb749cdb03b6087d5-4c15-4a3a-a8e0-948eb749cdb0'," +
            "'3c4a0023-8bdc-4fb1-b8e8-5a82736d7c013c4a0023-8bdc-4fb1-b8e8-5a82736d7c01'," +
            "'3c9c0e80-2422-4eff-9715-39315e22f0133c9c0e80-2422-4eff-9715-39315e22f013'," +
            "'3d79cfe0-909f-4f27-aa54-b21b3ef399f13d79cfe0-909f-4f27-aa54-b21b3ef399f1'," +
            "'3de8c693-6e7a-44f5-96c5-8e186ae914dd3de8c693-6e7a-44f5-96c5-8e186ae914dd'," +
            "'3e80d9db-7453-4fe1-b76a-bfdfd7b28e703e80d9db-7453-4fe1-b76a-bfdfd7b28e70'," +
            "'3f6b87f0-8766-43eb-bda9-b48bd07a2e743f6b87f0-8766-43eb-bda9-b48bd07a2e74'," +
            "'40d4780c-d22d-4349-8a89-991e751dc64e40d4780c-d22d-4349-8a89-991e751dc64e'," +
            "'40e1b9ff-9882-47b2-bbeb-5d18cd03636b40e1b9ff-9882-47b2-bbeb-5d18cd03636b'," +
            "'41722c9d-c7af-4c32-9224-5d1ed082648741722c9d-c7af-4c32-9224-5d1ed0826487'," +
            "'41ebf863-191f-4a17-b04b-5fad0bebd7de41ebf863-191f-4a17-b04b-5fad0bebd7de'," +
            "'427028ac-210a-471f-a7cd-1f1698101d09427028ac-210a-471f-a7cd-1f1698101d09'," +
            "'42d048ec-dfd5-445d-9341-ede50f9b904142d048ec-dfd5-445d-9341-ede50f9b9041'," +
            "'44b1ed67-2094-4366-8953-ca8ab262fd5444b1ed67-2094-4366-8953-ca8ab262fd54'," +
            "'44e5f619-eb98-4854-94b9-3578e044903844e5f619-eb98-4854-94b9-3578e0449038'," +
            "'453cfb52-b642-46d3-938a-613bba84906b453cfb52-b642-46d3-938a-613bba84906b'," +
            "'4580e881-6fa3-4218-a3a6-932def03306b4580e881-6fa3-4218-a3a6-932def03306b'," +
            "'464cc689-c642-42e3-993c-7b854ccc9580464cc689-c642-42e3-993c-7b854ccc9580'," +
            "'4665570e-4826-4aca-8656-e7c9363cc3f24665570e-4826-4aca-8656-e7c9363cc3f2'," +
            "'4670cf9a-b25f-474b-ad52-4ebea6b204344670cf9a-b25f-474b-ad52-4ebea6b20434'," +
            "'473fa4c6-c1b4-45d2-b91e-02aa0c342f98473fa4c6-c1b4-45d2-b91e-02aa0c342f98'," +
            "'475c4fce-5502-4f92-a649-110f26623b47475c4fce-5502-4f92-a649-110f26623b47'," +
            "'48a27ca0-517e-4624-beec-ab5ba8f2113048a27ca0-517e-4624-beec-ab5ba8f21130'," +
            "'49297f82-08b0-4727-b3a6-82122bff5c1a49297f82-08b0-4727-b3a6-82122bff5c1a'," +
            "'4b542050-e57f-40dc-85b9-55320f325c0d4b542050-e57f-40dc-85b9-55320f325c0d'," +
            "'4b728865-67ac-4708-bc84-a9175853bb644b728865-67ac-4708-bc84-a9175853bb64'," +
            "'4baf0ba2-460c-47a5-9fa8-ac459295e6ea4baf0ba2-460c-47a5-9fa8-ac459295e6ea'," +
            "'4cc3836d-11aa-427b-8533-7fa03bc50e2b4cc3836d-11aa-427b-8533-7fa03bc50e2b'," +
            "'4ccb96aa-83a2-4d80-b115-5f39f4c9b76e4ccb96aa-83a2-4d80-b115-5f39f4c9b76e'," +
            "'4d164845-a21b-4a75-be46-b4fcced022a04d164845-a21b-4a75-be46-b4fcced022a0'," +
            "'4dfa7490-ad10-4c02-9df0-ba47094d64d64dfa7490-ad10-4c02-9df0-ba47094d64d6'," +
            "'4fd4fd32-4146-4bf6-8517-c3a071977b7c4fd4fd32-4146-4bf6-8517-c3a071977b7c'," +
            "'4fedfc7118af439280bd47334bb216884fedfc7118af439280bd47334bb21688'," +
            "'506eb1dd-a84d-42f8-8950-2eadbf0c4acd506eb1dd-a84d-42f8-8950-2eadbf0c4acd'," +
            "'50b722fc-823c-4129-8a13-97f194e10db150b722fc-823c-4129-8a13-97f194e10db1'," +
            "'5185fbbc-c173-41a4-ab42-17c4efb0a6ff5185fbbc-c173-41a4-ab42-17c4efb0a6ff'," +
            "'519666c4-954a-4650-aa7b-078d753d39f5519666c4-954a-4650-aa7b-078d753d39f5'," +
            "'523fbef1-9666-40c8-a2d1-a572be18def2523fbef1-9666-40c8-a2d1-a572be18def2'," +
            "'5241101b-d96e-41da-a87d-ef7771052a3d5241101b-d96e-41da-a87d-ef7771052a3d'," +
            "'52bc8211-9091-4827-a307-2dd2755d659552bc8211-9091-4827-a307-2dd2755d6595'," +
            "'52bf815e-7dc0-42b6-8de8-ed58ba2d978852bf815e-7dc0-42b6-8de8-ed58ba2d9788'," +
            "'53cc5f13-eb5f-47ca-8be6-b3ab22dac0ab53cc5f13-eb5f-47ca-8be6-b3ab22dac0ab'," +
            "'54139584820a4d0f9c8003b6b81e23ef54139584820a4d0f9c8003b6b81e23ef'," +
            "'54575512-bfd8-4786-8b99-3b47d64bcd0154575512-bfd8-4786-8b99-3b47d64bcd01'," +
            "'54f0ff43-6efc-45ea-9078-8699932f5c5154f0ff43-6efc-45ea-9078-8699932f5c51'," +
            "'55b8ec3d-ea8d-43ef-8022-f2517ca56ce255b8ec3d-ea8d-43ef-8022-f2517ca56ce2'," +
            "'55f4aa8b-1850-4186-8411-6335f0be91eb55f4aa8b-1850-4186-8411-6335f0be91eb'," +
            "'561122a3-ed41-407c-97a6-4c4e0d16f314561122a3-ed41-407c-97a6-4c4e0d16f314'," +
            "'56509de6-c6a2-443d-91ee-47ff09c7434c56509de6-c6a2-443d-91ee-47ff09c7434c'," +
            "'567ae9d6-89ec-4c30-b8c3-69d8bdb20647567ae9d6-89ec-4c30-b8c3-69d8bdb20647'," +
            "'573b04f7-1d47-4cc9-9c0d-13d0d57921af573b04f7-1d47-4cc9-9c0d-13d0d57921af'," +
            "'585d879a-578a-4b3e-b8d0-4062ffcaf6cb585d879a-578a-4b3e-b8d0-4062ffcaf6cb'," +
            "'588398e6-f77e-4d4b-b30e-d5eab07ee5f4588398e6-f77e-4d4b-b30e-d5eab07ee5f4'," +
            "'58d74755-91f5-423d-a1fb-23282884799258d74755-91f5-423d-a1fb-232828847992'," +
            "'58eeec23-8314-4dd5-9551-51439f56823458eeec23-8314-4dd5-9551-51439f568234'," +
            "'5a3437f5-467e-4f4f-a814-ed378e1fc70a5a3437f5-467e-4f4f-a814-ed378e1fc70a'," +
            "'5a4e48ad-5747-41a7-b46e-a482c3cb0c0e5a4e48ad-5747-41a7-b46e-a482c3cb0c0e'," +
            "'5aeed325-498e-4619-bd85-24c555cf021e5aeed325-498e-4619-bd85-24c555cf021e'," +
            "'5af672f8-03ed-4881-b57f-1e839afb2ee25af672f8-03ed-4881-b57f-1e839afb2ee2'," +
            "'5b294c20-9145-45da-ae85-e349daf8f2e95b294c20-9145-45da-ae85-e349daf8f2e9'," +
            "'5b8cdd20-e607-4f92-b1d2-ce16ee629a1e5b8cdd20-e607-4f92-b1d2-ce16ee629a1e'," +
            "'5ba26a49-8530-4d53-afd5-6ca35d3208635ba26a49-8530-4d53-afd5-6ca35d320863'," +
            "'5cb9ea9b-9260-4868-8ee1-e7eded8647a15cb9ea9b-9260-4868-8ee1-e7eded8647a1'," +
            "'5d58e7a4-e2d8-4454-890c-1f9282ee941e5d58e7a4-e2d8-4454-890c-1f9282ee941e'," +
            "'5daea5af-454a-49a2-a9a2-a0331b7725e05daea5af-454a-49a2-a9a2-a0331b7725e0'," +
            "'5db8c891-e80d-4379-9587-deafc6daf8315db8c891-e80d-4379-9587-deafc6daf831'," +
            "'5de35953-8b9a-4500-8f3d-9e3f6c424a2c5de35953-8b9a-4500-8f3d-9e3f6c424a2c'," +
            "'5def2fcc-e7b9-4bb7-bb0b-5ae4c4f271875def2fcc-e7b9-4bb7-bb0b-5ae4c4f27187'," +
            "'5e8905aa-a0dc-49ef-98ba-bc0d80297ab75e8905aa-a0dc-49ef-98ba-bc0d80297ab7'," +
            "'5ea78972-89b2-461b-afbc-83cafc4b40e75ea78972-89b2-461b-afbc-83cafc4b40e7'," +
            "'5f5f3fce-2f00-464a-92ce-a68054ed594d5f5f3fce-2f00-464a-92ce-a68054ed594d'," +
            "'5fa9a1b8-0dcb-41a3-a777-6193b13daa905fa9a1b8-0dcb-41a3-a777-6193b13daa90'," +
            "'5fd33f07-1b7f-46e1-80bf-fd5e475c87405fd33f07-1b7f-46e1-80bf-fd5e475c8740'," +
            "'5fdc9355-d368-4b48-9c41-fbb4dfe73a815fdc9355-d368-4b48-9c41-fbb4dfe73a81'," +
            "'5ff6e999-cd3e-4c55-8d9a-77c37c7e25715ff6e999-cd3e-4c55-8d9a-77c37c7e2571'," +
            "'60382f17-65d3-4385-9ae9-b12f015cbd8f60382f17-65d3-4385-9ae9-b12f015cbd8f'," +
            "'60398564-dcce-47c0-9afd-3322caea38fe60398564-dcce-47c0-9afd-3322caea38fe'," +
            "'60502420-8e7d-466c-99fe-563f3cd3929360502420-8e7d-466c-99fe-563f3cd39293'," +
            "'61284535-8429-43ee-a193-74d32f5237e661284535-8429-43ee-a193-74d32f5237e6'," +
            "'615d2ff9-c2c0-45ab-a55e-f3490fc67d5d615d2ff9-c2c0-45ab-a55e-f3490fc67d5d'," +
            "'61b8bcf0-0202-41aa-b3fc-eb331dec989661b8bcf0-0202-41aa-b3fc-eb331dec9896'," +
            "'6266f6cf-0ab7-4a2f-930b-17c4e01ad4556266f6cf-0ab7-4a2f-930b-17c4e01ad455'," +
            "'62822341-ca29-4a2c-96b7-e6a58beb762962822341-ca29-4a2c-96b7-e6a58beb7629'," +
            "'63f60887-0068-4081-9056-8a92234e339563f60887-0068-4081-9056-8a92234e3395'," +
            "'641f0646-b3c6-4c3a-8277-aa9d9006107a641f0646-b3c6-4c3a-8277-aa9d9006107a'," +
            "'64278f6c-53af-4704-b800-b8a0889a825364278f6c-53af-4704-b800-b8a0889a8253'," +
            "'650d69ba-b59d-4c59-a2d0-587586f41960650d69ba-b59d-4c59-a2d0-587586f41960'," +
            "'650d823e-defe-42d7-b92b-d11447900cb1650d823e-defe-42d7-b92b-d11447900cb1'," +
            "'657707b8-98c7-4e65-b42e-497683207eaf657707b8-98c7-4e65-b42e-497683207eaf'," +
            "'666d9458-0a7a-464c-b701-041f795d160d666d9458-0a7a-464c-b701-041f795d160d'," +
            "'66fe3ca4-fe23-4c26-bbfd-8db01f78decd66fe3ca4-fe23-4c26-bbfd-8db01f78decd'," +
            "'67a578c7-767e-4f7e-a58c-a6ad52d4b7e167a578c7-767e-4f7e-a58c-a6ad52d4b7e1'," +
            "'67b984f5-b9cd-4cd2-b91a-80686238ccdf67b984f5-b9cd-4cd2-b91a-80686238ccdf'," +
            "'6821feae-6a23-45e9-9343-4b4d3c6fd2ac6821feae-6a23-45e9-9343-4b4d3c6fd2ac'," +
            "'6909a81d-e509-4998-b8dc-61b637f1bbf16909a81d-e509-4998-b8dc-61b637f1bbf1'," +
            "'6a7ec5e0-e663-4ef7-8857-a292c2bf0d336a7ec5e0-e663-4ef7-8857-a292c2bf0d33'," +
            "'6b0754da-971b-46aa-b4ca-bce68a70a7c06b0754da-971b-46aa-b4ca-bce68a70a7c0'," +
            "'6b1d5287-ecda-42d9-aa13-dab359e2bf6f6b1d5287-ecda-42d9-aa13-dab359e2bf6f'," +
            "'6b2db581-c601-4850-bfad-d63bd5fd355b6b2db581-c601-4850-bfad-d63bd5fd355b'," +
            "'6b464a5d-9244-4e25-89c4-f14d283c5d866b464a5d-9244-4e25-89c4-f14d283c5d86'," +
            "'6baf7161-3833-4390-9155-295d3c28fbd96baf7161-3833-4390-9155-295d3c28fbd9'," +
            "'6c38221d-22b7-4e7d-9710-082dc21690f56c38221d-22b7-4e7d-9710-082dc21690f5'," +
            "'6c79b20a-a524-4eac-9563-4d7a3fdd50f56c79b20a-a524-4eac-9563-4d7a3fdd50f5'," +
            "'6ca71bc1-49f3-464e-b41e-224022584f216ca71bc1-49f3-464e-b41e-224022584f21'," +
            "'6d020b92-83c2-4ce5-9469-e3b7691b810a6d020b92-83c2-4ce5-9469-e3b7691b810a'," +
            "'6d68f20e-9491-4a7c-af4e-f09feaec1e576d68f20e-9491-4a7c-af4e-f09feaec1e57'," +
            "'6db6b615-a99f-43d8-b5e1-dab9d6854ea56db6b615-a99f-43d8-b5e1-dab9d6854ea5'," +
            "'6ea07613-a6e3-41c6-be41-f55c312d7a7e6ea07613-a6e3-41c6-be41-f55c312d7a7e'," +
            "'6f2be965-cbb2-485b-aa33-cd212e51c1566f2be965-cbb2-485b-aa33-cd212e51c156'," +
            "'70b5f950-f288-4549-81c1-75fa6fbe845170b5f950-f288-4549-81c1-75fa6fbe8451'," +
            "'714d4a42-52da-4ecd-80f8-7e25ad916e97714d4a42-52da-4ecd-80f8-7e25ad916e97'," +
            "'71eaecc6-3289-40c0-bdea-5a729dd5d45571eaecc6-3289-40c0-bdea-5a729dd5d455'," +
            "'727b38c8-72a3-418d-8419-6b50df25e3c0727b38c8-72a3-418d-8419-6b50df25e3c0'," +
            "'72b79d44-cb52-418d-8fdc-c0b74361017072b79d44-cb52-418d-8fdc-c0b743610170'," +
            "'740f217d-8808-42ec-a351-12131b76e04e740f217d-8808-42ec-a351-12131b76e04e'," +
            "'7658b57b-a97c-4695-b8dd-4fc1ae506f0e7658b57b-a97c-4695-b8dd-4fc1ae506f0e'," +
            "'7687501e-0723-4860-a7b9-e3fbb78cb5097687501e-0723-4860-a7b9-e3fbb78cb509'," +
            "'7698691e-d908-476a-97da-dd0d676a71887698691e-d908-476a-97da-dd0d676a7188'," +
            "'77233280-399a-44fc-a033-1de6d60da4c677233280-399a-44fc-a033-1de6d60da4c6'," +
            "'77515745-acb9-41e0-a5b9-f0a1a82db91377515745-acb9-41e0-a5b9-f0a1a82db913'," +
            "'775c495c-c351-4804-853f-84086663c0de775c495c-c351-4804-853f-84086663c0de'," +
            "'777604ea-4059-4d06-b313-292aa2b04047777604ea-4059-4d06-b313-292aa2b04047'," +
            "'77945897-1e0b-4dca-b011-e533ec152ad677945897-1e0b-4dca-b011-e533ec152ad6'," +
            "'786c4540-6074-4ee7-b6bc-9287bd22748e786c4540-6074-4ee7-b6bc-9287bd22748e'," +
            "'7accaf59-a237-4d74-9003-48dc027f679b7accaf59-a237-4d74-9003-48dc027f679b'," +
            "'7ad1205f-e1f0-48a2-9239-2685ab686bdc7ad1205f-e1f0-48a2-9239-2685ab686bdc'," +
            "'7cd50d41-34d8-41b4-bb2f-aacab2fac2677cd50d41-34d8-41b4-bb2f-aacab2fac267'," +
            "'7cd9f59e-1b27-46a7-adba-c4ff57fc7dc27cd9f59e-1b27-46a7-adba-c4ff57fc7dc2'," +
            "'7cfb12e1-44e1-4723-a0be-37ebd08bf02e7cfb12e1-44e1-4723-a0be-37ebd08bf02e'," +
            "'7e9da5ff-4518-48a1-8423-fc6b495f83fa7e9da5ff-4518-48a1-8423-fc6b495f83fa'," +
            "'7eac62f8-398b-43e1-ae4c-afe437ec7cfd7eac62f8-398b-43e1-ae4c-afe437ec7cfd'," +
            "'7edcced4-ce56-4454-ab27-22237d3aaa467edcced4-ce56-4454-ab27-22237d3aaa46'," +
            "'7f505ecf-74bc-4ad2-8264-05c0851bb3c37f505ecf-74bc-4ad2-8264-05c0851bb3c3'," +
            "'7f89f29b-1c50-4f80-954a-b163f0731ff37f89f29b-1c50-4f80-954a-b163f0731ff3'," +
            "'8094f26e-e31c-4f2f-b6e8-a1b1e6ecf8218094f26e-e31c-4f2f-b6e8-a1b1e6ecf821'," +
            "'811aebfd-a78c-4e74-82ff-83f9d9ce636d811aebfd-a78c-4e74-82ff-83f9d9ce636d'," +
            "'81d5ce08-3632-4f14-8544-5f13117ac93881d5ce08-3632-4f14-8544-5f13117ac938'," +
            "'81f6c8e9-0818-4175-b7c7-c743e14547da81f6c8e9-0818-4175-b7c7-c743e14547da'," +
            "'832cdf04-aaba-462f-bd68-7103d16484af832cdf04-aaba-462f-bd68-7103d16484af'," +
            "'837d3dea-36ad-4928-a098-bf61176dca31837d3dea-36ad-4928-a098-bf61176dca31'," +
            "'842e41e2-6ed3-4e09-90db-2f48b7973a74842e41e2-6ed3-4e09-90db-2f48b7973a74'," +
            "'84683913-150f-4142-890b-89cc4f3af67f84683913-150f-4142-890b-89cc4f3af67f'," +
            "'84ccc0c8-84a5-42cb-889f-195bb6b6e5cd84ccc0c8-84a5-42cb-889f-195bb6b6e5cd'," +
            "'853c7c30-8c13-43da-9551-03e18f1c888b853c7c30-8c13-43da-9551-03e18f1c888b'," +
            "'856a58c5-a1c3-455f-b835-541d256f1d73856a58c5-a1c3-455f-b835-541d256f1d73'," +
            "'85ead843-c3b0-4558-b904-35652aed784785ead843-c3b0-4558-b904-35652aed7847'," +
            "'86470dc5-9e3d-4c49-8461-3b632c476d1986470dc5-9e3d-4c49-8461-3b632c476d19'," +
            "'866b7568-2d1c-482d-b416-9ad3d4dff2d1866b7568-2d1c-482d-b416-9ad3d4dff2d1'," +
            "'86de0cb4e2284f90aa6f945f6d932d7086de0cb4e2284f90aa6f945f6d932d70'," +
            "'86eb99c4-19f0-43a7-961c-fb50fd9a54f486eb99c4-19f0-43a7-961c-fb50fd9a54f4'," +
            "'875dd3cf-3119-4aea-927a-98afee4297ee875dd3cf-3119-4aea-927a-98afee4297ee'," +
            "'88e826ce-2b77-466e-9b04-70d8e0dfd47188e826ce-2b77-466e-9b04-70d8e0dfd471'," +
            "'899b685b-a6f0-42de-a834-61e99cedc106899b685b-a6f0-42de-a834-61e99cedc106'," +
            "'89d2d5e7-4b9e-499f-833b-ab087ae7ae4f89d2d5e7-4b9e-499f-833b-ab087ae7ae4f'," +
            "'89ee0688-4ce7-467d-9435-36f97347751a89ee0688-4ce7-467d-9435-36f97347751a'," +
            "'8a3bc3a3-862d-413e-a19b-fa533d08034e8a3bc3a3-862d-413e-a19b-fa533d08034e'," +
            "'8ae59737-a2e8-46fa-9d7e-c30c6f806b5f8ae59737-a2e8-46fa-9d7e-c30c6f806b5f'," +
            "'8b12adaa-11b9-4542-94b0-20385e08124a8b12adaa-11b9-4542-94b0-20385e08124a'," +
            "'8b313c19-d2b0-4ef5-8e15-d4b2c60dd72e8b313c19-d2b0-4ef5-8e15-d4b2c60dd72e'," +
            "'8cd61d77-f8e9-4657-8ab5-eb324a39532b8cd61d77-f8e9-4657-8ab5-eb324a39532b'," +
            "'8d76d8e5-fb6f-4a15-b0fa-6f292fb764478d76d8e5-fb6f-4a15-b0fa-6f292fb76447'," +
            "'8dd3e7d3-a046-4ec3-9fc4-5d8d09ffc0578dd3e7d3-a046-4ec3-9fc4-5d8d09ffc057'," +
            "'8e458f2a-d4a5-41da-903e-30206e7cf3818e458f2a-d4a5-41da-903e-30206e7cf381'," +
            "'8f0176ee-6518-43b7-bc07-148b2762d22e8f0176ee-6518-43b7-bc07-148b2762d22e'," +
            "'8f1e7c52-2268-4dc3-9283-e3e3be32b5be8f1e7c52-2268-4dc3-9283-e3e3be32b5be'," +
            "'8f5ffbae-2aab-454e-b5bf-380087b304c38f5ffbae-2aab-454e-b5bf-380087b304c3'," +
            "'9022dee3-9ef2-4c94-affa-b5086a1d88c29022dee3-9ef2-4c94-affa-b5086a1d88c2'," +
            "'90772299-940d-4c3d-a4ed-d9acbf25661d90772299-940d-4c3d-a4ed-d9acbf25661d'," +
            "'90e27381-230d-43c1-beee-6b32023b2dcb90e27381-230d-43c1-beee-6b32023b2dcb'," +
            "'91854537-10d8-4108-ab19-1abfb072a68491854537-10d8-4108-ab19-1abfb072a684'," +
            "'91de9f45-b816-4936-8035-463c8b5a65e491de9f45-b816-4936-8035-463c8b5a65e4'," +
            "'9251d013-28a4-4005-9be7-f1246867093c9251d013-28a4-4005-9be7-f1246867093c'," +
            "'948855eb-22de-419b-bd3b-0c6ae861b445948855eb-22de-419b-bd3b-0c6ae861b445'," +
            "'95253684-9143-4bf6-8ed5-e6bdb49845c795253684-9143-4bf6-8ed5-e6bdb49845c7'," +
            "'95ad17b7-b936-47b6-8109-726a7e3ac6b495ad17b7-b936-47b6-8109-726a7e3ac6b4'," +
            "'95e7dd29-a802-4e39-b887-0eeee4c356f795e7dd29-a802-4e39-b887-0eeee4c356f7'," +
            "'970340d5-4074-46af-89d8-746bf538d9f6970340d5-4074-46af-89d8-746bf538d9f6'," +
            "'97d2f9ca-4d10-4f27-a4c4-e40d0fcde99f97d2f9ca-4d10-4f27-a4c4-e40d0fcde99f'," +
            "'9811f22e-b734-41b3-bbff-0bab60711b0a9811f22e-b734-41b3-bbff-0bab60711b0a'," +
            "'9890313d-988e-4845-a327-945b659f22d39890313d-988e-4845-a327-945b659f22d3'," +
            "'9891fd0b-4654-4edb-bd5e-0f5f80d2dbae9891fd0b-4654-4edb-bd5e-0f5f80d2dbae'," +
            "'98e91f2b-0ca2-4d41-b670-fdd0185e1e0498e91f2b-0ca2-4d41-b670-fdd0185e1e04'," +
            "'9a82d8cd-2e0d-498b-bf8a-9c2fc4c92f319a82d8cd-2e0d-498b-bf8a-9c2fc4c92f31'," +
            "'9bd215bf-1d80-49de-b75f-ec4ecb75fdd89bd215bf-1d80-49de-b75f-ec4ecb75fdd8'," +
            "'9c2be5ab-a725-44a0-bb7b-37fd6ef1f8e89c2be5ab-a725-44a0-bb7b-37fd6ef1f8e8'," +
            "'9c8decd3-679a-4fc7-a8cf-7024123f3e369c8decd3-679a-4fc7-a8cf-7024123f3e36'," +
            "'9ca0e6ad-b68a-455d-916e-419fd3fd15c09ca0e6ad-b68a-455d-916e-419fd3fd15c0'," +
            "'9cc1c3f4-803f-45c5-b194-b934eb4f6f749cc1c3f4-803f-45c5-b194-b934eb4f6f74'," +
            "'9cd7242d-0995-426c-9f14-3aa9f00ff0de9cd7242d-0995-426c-9f14-3aa9f00ff0de'," +
            "'9ce9e5c6-87ac-44b7-98aa-dd9f181edbd09ce9e5c6-87ac-44b7-98aa-dd9f181edbd0'," +
            "'9d58eed9-166a-494d-8073-cf8bd463ce8d9d58eed9-166a-494d-8073-cf8bd463ce8d'," +
            "'9d71aa62-3804-417c-a984-3a22451872cf9d71aa62-3804-417c-a984-3a22451872cf'," +
            "'9d913a3b-6441-4063-9186-f6966986093a9d913a3b-6441-4063-9186-f6966986093a'," +
            "'9e3e72d9-5334-40da-b1df-d19154a016809e3e72d9-5334-40da-b1df-d19154a01680'," +
            "'9e3f51a6-e02c-4ed0-ae32-b0d50f105a2e9e3f51a6-e02c-4ed0-ae32-b0d50f105a2e'," +
            "'9e78542d-1d09-42af-a5d7-964d43e45e1a9e78542d-1d09-42af-a5d7-964d43e45e1a'," +
            "'9f04a62b-7446-4efa-b99e-e572d0f9d7b29f04a62b-7446-4efa-b99e-e572d0f9d7b2'," +
            "'a02d0e67-8e78-4a90-96c4-d76c558685eaa02d0e67-8e78-4a90-96c4-d76c558685ea'," +
            "'a2510458-9329-4b07-988c-9dfb2a77aafda2510458-9329-4b07-988c-9dfb2a77aafd'," +
            "'a2bd556e-a979-4b89-8bf1-25bce8c593d0a2bd556e-a979-4b89-8bf1-25bce8c593d0'," +
            "'a308351a-5357-4d84-8037-be9380ad9e2da308351a-5357-4d84-8037-be9380ad9e2d'," +
            "'a42b3dba-2ed7-4c02-ae44-8bc9aae69575a42b3dba-2ed7-4c02-ae44-8bc9aae69575'," +
            "'a4642a3d-a31b-4fcd-b6c8-65abfb0896aca4642a3d-a31b-4fcd-b6c8-65abfb0896ac'," +
            "'a467f51d-ecb8-4674-a135-66af28135f9ca467f51d-ecb8-4674-a135-66af28135f9c'," +
            "'a537ea27-302b-4e6e-bc50-aece4eb210b8a537ea27-302b-4e6e-bc50-aece4eb210b8'," +
            "'a67cb24b-922a-4144-8022-9ae9fd3debc4a67cb24b-922a-4144-8022-9ae9fd3debc4'," +
            "'a6880292-80ef-42ee-8db6-93963df6bf91a6880292-80ef-42ee-8db6-93963df6bf91'," +
            "'a68cb00b-7415-4ba9-b3a8-4791f14536e4a68cb00b-7415-4ba9-b3a8-4791f14536e4'," +
            "'a69a9fd4-117f-433e-962d-638e052848f2a69a9fd4-117f-433e-962d-638e052848f2'," +
            "'a6ada6ee-8a82-4a64-8a60-3efad3649d9ca6ada6ee-8a82-4a64-8a60-3efad3649d9c'," +
            "'a6c0da37-c13c-4bf4-9c70-51d848fc2da3a6c0da37-c13c-4bf4-9c70-51d848fc2da3'," +
            "'a713bf91-24dd-4e27-99c8-34ff32802f17a713bf91-24dd-4e27-99c8-34ff32802f17'," +
            "'a7c03718-b17b-40ed-ae9e-1b7900d97d79a7c03718-b17b-40ed-ae9e-1b7900d97d79'," +
            "'a7c0af3d-5676-4a38-a51a-ce9d7823e6b2a7c0af3d-5676-4a38-a51a-ce9d7823e6b2'," +
            "'a7e404fd-bc02-48db-b5dc-485c18ac130aa7e404fd-bc02-48db-b5dc-485c18ac130a'," +
            "'a84aa76e-3990-4b77-8c28-f330c0a4b6b5a84aa76e-3990-4b77-8c28-f330c0a4b6b5'," +
            "'a89f0750-7b8e-42ff-b036-00ed4c04a8fda89f0750-7b8e-42ff-b036-00ed4c04a8fd'," +
            "'a9de0905-b86b-4d78-90d8-e554acf372f5a9de0905-b86b-4d78-90d8-e554acf372f5'," +
            "'aa078bd5-61c0-403b-a48c-7dab5fb6ca6caa078bd5-61c0-403b-a48c-7dab5fb6ca6c'," +
            "'aa73091c-edfc-4529-879d-c24095a1212faa73091c-edfc-4529-879d-c24095a1212f'," +
            "'aa868678-a294-42ae-87d9-54019583acf1aa868678-a294-42ae-87d9-54019583acf1'," +
            "'aa98ee55-0b69-492c-abfd-2f77e374ca51aa98ee55-0b69-492c-abfd-2f77e374ca51'," +
            "'abba3739-6734-4ba1-8c23-fcf61d073ca1abba3739-6734-4ba1-8c23-fcf61d073ca1'," +
            "'abe15905-88cd-449f-9f38-92db0e7be16babe15905-88cd-449f-9f38-92db0e7be16b'," +
            "'ac85f854-ea3f-4b7d-a059-1706c3591eb6ac85f854-ea3f-4b7d-a059-1706c3591eb6'," +
            "'ac9671e0-4bb8-4a6a-9d28-85ab54ebf5d1ac9671e0-4bb8-4a6a-9d28-85ab54ebf5d1'," +
            "'acd85ede-03a3-43a0-920f-96abe05fbacfacd85ede-03a3-43a0-920f-96abe05fbacf'," +
            "'acfc6a5b99854a09b0e19b6e420fdf63acfc6a5b99854a09b0e19b6e420fdf63'," +
            "'ad0d2365b9434b90ab3a41a59b597104ad0d2365b9434b90ab3a41a59b597104'," +
            "'ad124989-4a3c-4862-b6c1-76b820f90428ad124989-4a3c-4862-b6c1-76b820f90428'," +
            "'ad2916c6-ca6c-4db3-8fa0-ea4325d6bc2bad2916c6-ca6c-4db3-8fa0-ea4325d6bc2b'," +
            "'ad5ede05-7952-41b9-87df-66b613cc0856ad5ede05-7952-41b9-87df-66b613cc0856'," +
            "'ada31dd5-38be-43b7-83c5-ea0f6634af29ada31dd5-38be-43b7-83c5-ea0f6634af29'," +
            "'ae2b822da78d4ed3a9bff7cd877cb167ae2b822da78d4ed3a9bff7cd877cb167'," +
            "'b09b80d1-f926-41dc-b624-7e8d3f211299b09b80d1-f926-41dc-b624-7e8d3f211299'," +
            "'b0c186a4-eeee-4e8f-9fa9-59c5f15b6c56b0c186a4-eeee-4e8f-9fa9-59c5f15b6c56'," +
            "'b184cfc4-3d18-4c82-825f-bc7eff9e5faab184cfc4-3d18-4c82-825f-bc7eff9e5faa'," +
            "'b24b6885-8737-425d-9259-bd17411e42dcb24b6885-8737-425d-9259-bd17411e42dc'," +
            "'b2df138d-d282-44d6-bde1-7cf58edd50d2b2df138d-d282-44d6-bde1-7cf58edd50d2'," +
            "'b310d6da-a181-47e0-87a9-917e8a488202b310d6da-a181-47e0-87a9-917e8a488202'," +
            "'b4784ed8-d161-4d19-84cc-8c634b03c76cb4784ed8-d161-4d19-84cc-8c634b03c76c'," +
            "'b4a5156a-54cb-4ae6-b2c1-54463d64ed6cb4a5156a-54cb-4ae6-b2c1-54463d64ed6c'," +
            "'b5660c77-f23c-4a3e-860b-923be8817a5cb5660c77-f23c-4a3e-860b-923be8817a5c'," +
            "'b5f81880-520f-44c9-ab11-eb1d27367022b5f81880-520f-44c9-ab11-eb1d27367022'," +
            "'b632d37c-6870-49c1-9496-723cca700c88b632d37c-6870-49c1-9496-723cca700c88'," +
            "'b6828aec-98e7-4754-a70d-bc686b5bcb38b6828aec-98e7-4754-a70d-bc686b5bcb38'," +
            "'b6b49e68-1e7d-4c45-9643-6a2989c619b7b6b49e68-1e7d-4c45-9643-6a2989c619b7'," +
            "'b70106dc-838b-47bc-8cac-38ba183df418b70106dc-838b-47bc-8cac-38ba183df418'," +
            "'b71e3cc9-b89c-429c-acd4-61a3130812f7b71e3cc9-b89c-429c-acd4-61a3130812f7'," +
            "'b7aeac56-7c89-4a82-a8b6-151e3d61057eb7aeac56-7c89-4a82-a8b6-151e3d61057e'," +
            "'b7e6ac75-3752-4a98-80ed-03c86729c2a1b7e6ac75-3752-4a98-80ed-03c86729c2a1'," +
            "'b90e0fbb-09f7-4e03-aca1-9929acd5bf65b90e0fbb-09f7-4e03-aca1-9929acd5bf65'," +
            "'b916837d-61d7-4c35-a601-5f612d28190fb916837d-61d7-4c35-a601-5f612d28190f'," +
            "'b928a8ff-b7e2-47bb-81fd-d8936b76a425b928a8ff-b7e2-47bb-81fd-d8936b76a425'," +
            "'b98a9616-f329-4467-9305-0c1c98801318b98a9616-f329-4467-9305-0c1c98801318'," +
            "'b9d4ceaf-9886-4803-b835-bdd7e1a6757eb9d4ceaf-9886-4803-b835-bdd7e1a6757e'," +
            "'ba05c6ca-0896-4215-b191-62d84ae8854eba05c6ca-0896-4215-b191-62d84ae8854e'," +
            "'ba3d3a40-f4fb-40e4-aba2-e7ccdabb8373ba3d3a40-f4fb-40e4-aba2-e7ccdabb8373'," +
            "'ba541f1c-378c-4b40-9c90-335622c4bc15ba541f1c-378c-4b40-9c90-335622c4bc15'," +
            "'baec5b27-861d-4763-b923-16b6b585e16dbaec5b27-861d-4763-b923-16b6b585e16d'," +
            "'bb0812a5-1dc5-4b77-b5ca-5b77d44192b8bb0812a5-1dc5-4b77-b5ca-5b77d44192b8'," +
            "'bb7b741e-06a9-4abc-b786-3393e4e7387ebb7b741e-06a9-4abc-b786-3393e4e7387e'," +
            "'bbd97373-1198-4843-9215-9ce80df7ee9fbbd97373-1198-4843-9215-9ce80df7ee9f'," +
            "'bc4ecbd7-c9e6-4591-a13f-008e01735940bc4ecbd7-c9e6-4591-a13f-008e01735940'," +
            "'bcb93cc0-6e47-4312-8733-25226bcadf89bcb93cc0-6e47-4312-8733-25226bcadf89'," +
            "'bccf3522-2786-466f-803b-aab0b33ceec1bccf3522-2786-466f-803b-aab0b33ceec1'," +
            "'bcd4e83a-d8a6-4dee-bdf5-238775dacfb4bcd4e83a-d8a6-4dee-bdf5-238775dacfb4'," +
            "'bcd82cb9-ecf4-4267-94b7-27fef0adfd1bbcd82cb9-ecf4-4267-94b7-27fef0adfd1b'," +
            "'bceeac68-6c0e-4d09-b174-0a82a461d6a6bceeac68-6c0e-4d09-b174-0a82a461d6a6'," +
            "'bd1778a9-7bef-4b9c-8369-4c699bbf3e21bd1778a9-7bef-4b9c-8369-4c699bbf3e21'," +
            "'bd7fdfea-b0b9-46c3-9ba0-824b03dcda03bd7fdfea-b0b9-46c3-9ba0-824b03dcda03'," +
            "'bd8bf965-932c-4d6d-b102-e8efbaff96c3bd8bf965-932c-4d6d-b102-e8efbaff96c3'," +
            "'bdcab8a9-c66a-41d9-bb1f-021e714d5dbcbdcab8a9-c66a-41d9-bb1f-021e714d5dbc'," +
            "'be5cfb82-1e27-4645-bf5d-6f0a6761e9a8be5cfb82-1e27-4645-bf5d-6f0a6761e9a8'," +
            "'be7644bd-e6db-4c72-b012-ce0f49ed1ef4be7644bd-e6db-4c72-b012-ce0f49ed1ef4'," +
            "'bfdaeb2e-c7af-4e19-ad6b-6246477ed54ebfdaeb2e-c7af-4e19-ad6b-6246477ed54e'," +
            "'c0086c29-064a-434d-b532-c0b6ed126200c0086c29-064a-434d-b532-c0b6ed126200'," +
            "'c06093ad-a494-4856-a97e-8997dc51df08c06093ad-a494-4856-a97e-8997dc51df08'," +
            "'c072c249-6f1c-4328-8643-36be9ea03140c072c249-6f1c-4328-8643-36be9ea03140'," +
            "'c115039098fb44dc91f1d4efd72724adc115039098fb44dc91f1d4efd72724ad'," +
            "'c1683f4c79cc4fd1ad6898b1845bee08c1683f4c79cc4fd1ad6898b1845bee08'," +
            "'c1faa2a8-ddd2-4f00-b5cc-f08a2a471599c1faa2a8-ddd2-4f00-b5cc-f08a2a471599'," +
            "'c2621627-02e9-4621-8d60-8de99faec1edc2621627-02e9-4621-8d60-8de99faec1ed'," +
            "'c2748550-0f12-46ba-bedf-78080cb3ea05c2748550-0f12-46ba-bedf-78080cb3ea05'," +
            "'c2f7bd3c-61fe-48f5-ab94-1e22abaaf240c2f7bd3c-61fe-48f5-ab94-1e22abaaf240'," +
            "'c313910a-6f96-4414-b7a0-758ad8cb9071c313910a-6f96-4414-b7a0-758ad8cb9071'," +
            "'c39d8a6b-5e91-4d96-a17e-67ac184c817fc39d8a6b-5e91-4d96-a17e-67ac184c817f'," +
            "'c3cedda2-25f3-4495-bf74-9286c1862b32c3cedda2-25f3-4495-bf74-9286c1862b32'," +
            "'c3e6a60571954ec1bff9929db0146b50c3e6a60571954ec1bff9929db0146b50'," +
            "'c550b0e6-8e58-40ff-8200-f776ce72347cc550b0e6-8e58-40ff-8200-f776ce72347c'," +
            "'c64e284c-20af-40c7-be53-eb81bfac7941c64e284c-20af-40c7-be53-eb81bfac7941'," +
            "'c6daa03a-765b-4337-9101-ff67dbcd924ac6daa03a-765b-4337-9101-ff67dbcd924a'," +
            "'c74562b0-c263-474a-b6f1-993fc4c03f95c74562b0-c263-474a-b6f1-993fc4c03f95'," +
            "'c74977f9-36d0-4232-938d-320a04132990c74977f9-36d0-4232-938d-320a04132990'," +
            "'c774eaff-6c9b-424d-bfa5-b6159a99d63ac774eaff-6c9b-424d-bfa5-b6159a99d63a'," +
            "'c7e266c5-82ba-4ba7-b943-3402e321127dc7e266c5-82ba-4ba7-b943-3402e321127d'," +
            "'c8bdb70c-d749-4f5b-83cc-989aebbad836c8bdb70c-d749-4f5b-83cc-989aebbad836'," +
            "'ca25b42e-9c05-4db7-b4aa-a13a012f5163ca25b42e-9c05-4db7-b4aa-a13a012f5163'," +
            "'ca36182c-e986-4e07-91d4-cb0e9a15801bca36182c-e986-4e07-91d4-cb0e9a15801b'," +
            "'cbab2e8d-45a2-4743-bc4b-4142ef6a9d2acbab2e8d-45a2-4743-bc4b-4142ef6a9d2a'," +
            "'cc8bf983-a15f-442b-8707-da81bad1f351cc8bf983-a15f-442b-8707-da81bad1f351'," +
            "'cc963c92-c98c-4be6-a6ed-3d3ea16b56a9cc963c92-c98c-4be6-a6ed-3d3ea16b56a9'," +
            "'ccb39307-aba3-4dbb-89b0-fa48aa07e366ccb39307-aba3-4dbb-89b0-fa48aa07e366'," +
            "'cd4d29c2-1901-4503-aabf-e84fc25b3fa3cd4d29c2-1901-4503-aabf-e84fc25b3fa3'," +
            "'cdd13f13-977b-454e-a0cf-d12bc0e85c38cdd13f13-977b-454e-a0cf-d12bc0e85c38'," +
            "'ce2e9211-078d-4a6b-8d49-a3aa3fe9add9ce2e9211-078d-4a6b-8d49-a3aa3fe9add9'," +
            "'cea8e1dd-2fc9-45a2-a9ce-c6e7f88eed38cea8e1dd-2fc9-45a2-a9ce-c6e7f88eed38'," +
            "'cfb032d2-34c3-4767-b9b5-3cffe4a1cd68cfb032d2-34c3-4767-b9b5-3cffe4a1cd68'," +
            "'d12a1724-0bd8-4673-9e76-652ccff32ff8d12a1724-0bd8-4673-9e76-652ccff32ff8'," +
            "'d17b8b92-fbc4-45d0-9563-7e30d76afe5ad17b8b92-fbc4-45d0-9563-7e30d76afe5a'," +
            "'d1eac166-4611-484a-948b-04c2704ed47bd1eac166-4611-484a-948b-04c2704ed47b'," +
            "'d240b5ad-e1e3-4596-b0ca-ac52ef0c83e9d240b5ad-e1e3-4596-b0ca-ac52ef0c83e9'," +
            "'d2d0838d-d31e-4bf7-8581-186fef20b951d2d0838d-d31e-4bf7-8581-186fef20b951'," +
            "'d31c9380-704c-4934-97a2-6844e0e63fe6d31c9380-704c-4934-97a2-6844e0e63fe6'," +
            "'d32662ed-2be2-499e-8cbb-dd9b51ab52efd32662ed-2be2-499e-8cbb-dd9b51ab52ef'," +
            "'d4c0b1a4a41f476689b555f8e43b9616d4c0b1a4a41f476689b555f8e43b9616'," +
            "'d5cd350b-cc5f-4091-b2f4-0e7658ec5e60d5cd350b-cc5f-4091-b2f4-0e7658ec5e60'," +
            "'d5d52e5c-12f1-4b16-8063-45741c2a627fd5d52e5c-12f1-4b16-8063-45741c2a627f'," +
            "'d624fd4c-00a9-49fd-93f5-1af09bacbf9bd624fd4c-00a9-49fd-93f5-1af09bacbf9b'," +
            "'d6304ad5-c1c0-4afa-8608-a28c82e3b86ed6304ad5-c1c0-4afa-8608-a28c82e3b86e'," +
            "'d69f2469-0f0a-4120-bd01-42086bdba3d2d69f2469-0f0a-4120-bd01-42086bdba3d2'," +
            "'d6df8178-4d9f-43cb-8eff-4c402947608ed6df8178-4d9f-43cb-8eff-4c402947608e'," +
            "'d71e8a09-9efa-4080-a2e6-b751e75ac819d71e8a09-9efa-4080-a2e6-b751e75ac819'," +
            "'d788d2ff-61c5-41e0-afa7-165d5669c8fdd788d2ff-61c5-41e0-afa7-165d5669c8fd'," +
            "'d8626c02-d673-43e4-8148-4010edf160cbd8626c02-d673-43e4-8148-4010edf160cb'," +
            "'d892e72f-c80e-447d-a1c2-57712d80438cd892e72f-c80e-447d-a1c2-57712d80438c'," +
            "'d909fb9c-acc5-4075-bfd9-d2aaacd8bb38d909fb9c-acc5-4075-bfd9-d2aaacd8bb38'," +
            "'d9bf14b0-0c4f-4dad-b21e-8e4ebfe5ee61d9bf14b0-0c4f-4dad-b21e-8e4ebfe5ee61'," +
            "'db1e0a80-fabd-4a67-97ba-74df1b4f16ebdb1e0a80-fabd-4a67-97ba-74df1b4f16eb'," +
            "'dc143925-b8ef-49a3-9775-92a12cfa5a35dc143925-b8ef-49a3-9775-92a12cfa5a35'," +
            "'dcdfcc11-bfe9-4fa7-be5f-2d0f7e65ae48dcdfcc11-bfe9-4fa7-be5f-2d0f7e65ae48'," +
            "'dd73de42-e6d0-43f5-8515-bb0a5cf777f1dd73de42-e6d0-43f5-8515-bb0a5cf777f1'," +
            "'dfcb25d9-4d34-4088-87ef-10603e4304badfcb25d9-4d34-4088-87ef-10603e4304ba'," +
            "'e02032f6-e718-4a3b-91e0-d274963ef645e02032f6-e718-4a3b-91e0-d274963ef645'," +
            "'e026859a-98e4-422e-891e-843db4205f44e026859a-98e4-422e-891e-843db4205f44'," +
            "'e03b373a-83e6-4dea-b8a3-38bc19cb5d69e03b373a-83e6-4dea-b8a3-38bc19cb5d69'," +
            "'e03bffa5-79fe-47e4-941d-8fefa5687946e03bffa5-79fe-47e4-941d-8fefa5687946'," +
            "'e076590b-1bee-4f6f-9c2f-a5c4298b51e2e076590b-1bee-4f6f-9c2f-a5c4298b51e2'," +
            "'e0db489d-6006-423a-8e88-15009619781be0db489d-6006-423a-8e88-15009619781b'," +
            "'e1361844-8085-44ec-8113-4dba9d277fbbe1361844-8085-44ec-8113-4dba9d277fbb'," +
            "'e28192dd-422b-4d38-985b-39f5139bd762e28192dd-422b-4d38-985b-39f5139bd762'," +
            "'e3d92eee-f3a6-4307-9ea4-b8b7b0513dfee3d92eee-f3a6-4307-9ea4-b8b7b0513dfe'," +
            "'e3db3887-8b83-4fa8-bebf-4dea43511818e3db3887-8b83-4fa8-bebf-4dea43511818'," +
            "'e41bae42-fa1c-4cf8-a86b-758d89dd83e6e41bae42-fa1c-4cf8-a86b-758d89dd83e6'," +
            "'e4469cef-d542-45bd-908e-d841143e459ae4469cef-d542-45bd-908e-d841143e459a'," +
            "'e471ac17-93cb-428d-ac1e-79d6aa992052e471ac17-93cb-428d-ac1e-79d6aa992052'," +
            "'e5518794-8a27-484c-b094-d9734540a8c3e5518794-8a27-484c-b094-d9734540a8c3'," +
            "'e5c34be5-c350-408f-8f3a-ae05bf9e64ebe5c34be5-c350-408f-8f3a-ae05bf9e64eb'," +
            "'e5cafeeb-c2bf-4c73-9322-072ac04023aee5cafeeb-c2bf-4c73-9322-072ac04023ae'," +
            "'e6763444-93a0-4d1a-a6d7-b59b5a177d5ce6763444-93a0-4d1a-a6d7-b59b5a177d5c'," +
            "'e6fd7e16-8310-4556-ba2a-15bb3cbad1e0e6fd7e16-8310-4556-ba2a-15bb3cbad1e0'," +
            "'e77d4762-2b97-4176-bc24-086f368b39d7e77d4762-2b97-4176-bc24-086f368b39d7'," +
            "'e7972081-1337-4f3e-9339-b2f14aff64b3e7972081-1337-4f3e-9339-b2f14aff64b3'," +
            "'e819be3c-51d7-42c3-b486-ca545689732ee819be3c-51d7-42c3-b486-ca545689732e'," +
            "'e8882fdc-e084-4a65-b200-0e5158483f8de8882fdc-e084-4a65-b200-0e5158483f8d'," +
            "'e914cab0-9d06-49b6-9b8d-fd4d804d5b9ce914cab0-9d06-49b6-9b8d-fd4d804d5b9c'," +
            "'e9289244-9422-4ec7-98cd-29359835c2fae9289244-9422-4ec7-98cd-29359835c2fa'," +
            "'e97ff780-ace2-4260-a307-3da093f8a5d1e97ff780-ace2-4260-a307-3da093f8a5d1'," +
            "'e9dbf1f5-9f9c-47ed-949b-db2b4399019ee9dbf1f5-9f9c-47ed-949b-db2b4399019e'," +
            "'ea3f6312-9e0a-40d9-b67b-00c02f0cb2d0ea3f6312-9e0a-40d9-b67b-00c02f0cb2d0'," +
            "'ea4b2ccf-f4d4-4071-90d0-99441d992962ea4b2ccf-f4d4-4071-90d0-99441d992962'," +
            "'ea64b71b-baf4-4a02-8f72-ad78f61745d5ea64b71b-baf4-4a02-8f72-ad78f61745d5'," +
            "'ea78657d-6530-4e0b-a985-a7f09ea63659ea78657d-6530-4e0b-a985-a7f09ea63659'," +
            "'eab3bb14-f46c-4ccf-b6cb-021bff3233e5eab3bb14-f46c-4ccf-b6cb-021bff3233e5'," +
            "'eae6c7ef-110d-47d9-b0e0-a9a63cb32203eae6c7ef-110d-47d9-b0e0-a9a63cb32203'," +
            "'eb9ef32b-b7d8-4677-8d2c-bcf2c64ddec4eb9ef32b-b7d8-4677-8d2c-bcf2c64ddec4'," +
            "'ebcf397a-8b3f-44c3-abce-42ba3d9a3b3febcf397a-8b3f-44c3-abce-42ba3d9a3b3f'," +
            "'ebd94126-bcf3-420d-8c76-69ad70e6978debd94126-bcf3-420d-8c76-69ad70e6978d'," +
            "'ecbcd163-09b8-4cd3-bfd7-4fff7c3bc178ecbcd163-09b8-4cd3-bfd7-4fff7c3bc178'," +
            "'ed15293f-1edd-4445-b4a0-07861dd93f69ed15293f-1edd-4445-b4a0-07861dd93f69'," +
            "'ed7bb54b-8ac2-4a34-b1a0-25b0286f1f11ed7bb54b-8ac2-4a34-b1a0-25b0286f1f11'," +
            "'edd38fe3-2423-4901-90fd-03e8eec7d9c5edd38fe3-2423-4901-90fd-03e8eec7d9c5'," +
            "'edd6f9f4-a9b1-403d-bb5e-ed6e7c64b693edd6f9f4-a9b1-403d-bb5e-ed6e7c64b693'," +
            "'ee061f03-b5a1-45b8-a8f5-967efd6fc077ee061f03-b5a1-45b8-a8f5-967efd6fc077'," +
            "'ee6ce853-eda4-48e5-b0f8-b940dfb9adaaee6ce853-eda4-48e5-b0f8-b940dfb9adaa'," +
            "'ee919cfe-cbcc-4875-96fc-063d4f0c32c8ee919cfe-cbcc-4875-96fc-063d4f0c32c8'," +
            "'eeaf7ffc-a15b-4f70-b097-01bb78fcedc4eeaf7ffc-a15b-4f70-b097-01bb78fcedc4'," +
            "'eeb36e65-cb85-4684-835f-29e64129d8e5eeb36e65-cb85-4684-835f-29e64129d8e5'," +
            "'eeb3fd40-bd2a-4da1-9085-708e1e7a9971eeb3fd40-bd2a-4da1-9085-708e1e7a9971'," +
            "'f08e79ba-9e39-4be7-814d-cdc176c0df01f08e79ba-9e39-4be7-814d-cdc176c0df01'," +
            "'f1188f78-349e-4202-9571-0dec7270553ef1188f78-349e-4202-9571-0dec7270553e'," +
            "'f148a2bc-981a-47db-8f9c-b658d3723afbf148a2bc-981a-47db-8f9c-b658d3723afb'," +
            "'f161c60f-2f39-4edb-a8ef-20419997f66af161c60f-2f39-4edb-a8ef-20419997f66a'," +
            "'f19875d8-5256-4c80-89bd-ce5f90e405def19875d8-5256-4c80-89bd-ce5f90e405de'," +
            "'f2a34097-1e59-4f6b-ac15-6e252e11179cf2a34097-1e59-4f6b-ac15-6e252e11179c'," +
            "'f3a325bc-2f79-44da-98f6-dd0ed85cbd66f3a325bc-2f79-44da-98f6-dd0ed85cbd66'," +
            "'f3d5875fdce44c84939186b1883c0c73f3d5875fdce44c84939186b1883c0c73'," +
            "'f422fa1660b44ba8988c68549455fddbf422fa1660b44ba8988c68549455fddb'," +
            "'f428faf2-27cf-40a9-80a1-90e65ce011ecf428faf2-27cf-40a9-80a1-90e65ce011ec'," +
            "'f4526aac-880f-4f04-acd4-579259314f2ff4526aac-880f-4f04-acd4-579259314f2f'," +
            "'f465bf91-f1f9-4cd4-95a0-f3adb44b69b8f465bf91-f1f9-4cd4-95a0-f3adb44b69b8'," +
            "'f494bdb6-ab0e-4283-a4e0-9406539635e1f494bdb6-ab0e-4283-a4e0-9406539635e1'," +
            "'f5185eed-904c-48bb-acab-65b00c14b0c7f5185eed-904c-48bb-acab-65b00c14b0c7'," +
            "'f572e8d0-91c1-4053-aa75-11aa72576af0f572e8d0-91c1-4053-aa75-11aa72576af0'," +
            "'f60c5ec5-0e60-473b-a4be-3509c8391f33f60c5ec5-0e60-473b-a4be-3509c8391f33'," +
            "'f64a8db5-957a-4e35-865a-986773a72b45f64a8db5-957a-4e35-865a-986773a72b45'," +
            "'f6b366d8-2a05-452a-900b-91c4cff7265ff6b366d8-2a05-452a-900b-91c4cff7265f'," +
            "'f80d02ef-5f39-4235-b357-35a03121d5dcf80d02ef-5f39-4235-b357-35a03121d5dc'," +
            "'f8be72dd-9702-4b8d-9d91-d191ebb21453f8be72dd-9702-4b8d-9d91-d191ebb21453'," +
            "'f8f3fa2a-2816-4c34-9d95-9586abe58b0df8f3fa2a-2816-4c34-9d95-9586abe58b0d'," +
            "'fbf6d6b5-4169-4c44-8e02-b6b29cf1e8f6fbf6d6b5-4169-4c44-8e02-b6b29cf1e8f6'," +
            "'fc1dd338-2774-47e6-b15e-c4def47c68b7fc1dd338-2774-47e6-b15e-c4def47c68b7'," +
            "'fc3ec1ec-66d9-44fc-9bba-90d34b94d98afc3ec1ec-66d9-44fc-9bba-90d34b94d98a'," +
            "'fca005a6-9175-435a-b59f-7664be8e370ffca005a6-9175-435a-b59f-7664be8e370f'," +
            "'fd118505-4752-4476-a8a6-38dc491aef7dfd118505-4752-4476-a8a6-38dc491aef7d'," +
            "'fd907841-2724-43b6-9efc-1548bb62f50bfd907841-2724-43b6-9efc-1548bb62f50b'," +
            "'fdec5598-370d-41b4-acbe-2f9371274f94fdec5598-370d-41b4-acbe-2f9371274f94'," +
            "'fe427bfb-1678-4d6f-a8eb-6a6f7f8fd75dfe427bfb-1678-4d6f-a8eb-6a6f7f8fd75d'," +
            "'fe4a9134-3fbe-4db1-927f-d817e9b2c410fe4a9134-3fbe-4db1-927f-d817e9b2c410'," +
            "'fe4cbd32-cf93-4afe-a6bd-030f242f7e63fe4cbd32-cf93-4afe-a6bd-030f242f7e63'," +
            "'fefe838a-9fe5-445b-8490-12a87bf86d9dfefe838a-9fe5-445b-8490-12a87bf86d9d'," +
            "'ff8067c9-751f-4546-b769-e18847cb3893ff8067c9-751f-4546-b769-e18847cb3893'," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "''," +
            "'' ";


    }



}