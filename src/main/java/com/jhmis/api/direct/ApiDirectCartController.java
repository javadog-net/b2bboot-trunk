package com.jhmis.api.direct;

import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.shop.entity.*;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.mapper.StoreGoodsMapper;
import com.jhmis.modules.shop.mapper.StoreMapper;
import com.jhmis.modules.shop.service.*;
import com.jhmis.modules.shop.utils.DealerUtils;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Api(value = "ApiDirectCartController", description = "直采购物车管理")
@RestController
@RequestMapping("/api/direct/cart")
public class ApiDirectCartController {
    @Autowired
    private DirectCartService directCartService;
    @Autowired
    private StoreGoodsService storeGoodsService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private  GoodsService goodsService;
    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private StoreGoodsMapper storeGoodsMapper;


    /**
     * 购物车列表
     * @return
     */
    @ApiOperation(notes = "list", httpMethod = "GET", value = "购物车列表")
    @RequestMapping("list")
    public AjaxJson list(){
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        //需要按照漏斗产品组分类
        List<DirectCart> listDC = directCartService.getGroupProduct(currentAccount.getId());
        if(listDC==null || listDC.size()==0){
            return AjaxJson.fail("购物车为空");
        }
        List<DirectCartVo> listDirectCart = new ArrayList<>();
        //购物车按照产品组分类
        for(DirectCart directCart:listDC){
            //声明直采购物车VO
            DirectCartVo dcv = new DirectCartVo();
            //放入产品组编码
            dcv.setProductGroupCode(directCart.getProductGroupCode());
            //放入产品组名称
            dcv.setProductGroupName(directCart.getProductGroupName());
            DirectCart d = new DirectCart();
            //放入供应商编码
            d.setDealerAccountId(currentAccount.getId());
            d.setProductGroupCode(directCart.getProductGroupCode());
            //根据产品组编码查询List
            List<DirectCart> dcList = directCartService.findList(d);
            //放入list
            dcv.setListDirectCart(dcList);
            //根据不同分组进行获取
            listDirectCart.add(dcv);
        }
        return AjaxJson.ok(listDirectCart);
    }

    /**
     * 加入购物车
     * @param cart
     * @param request
     * @return
     */
    @ApiOperation(notes = "saveToCart", httpMethod = "POST", value = "加入购物车")
    @ApiImplicitParams({@ApiImplicitParam(name = "storeGoodsId", value = "店铺商品ID", required = true, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "chooseNum", value = "商品数量", required = true, paramType = "query",dataType = "int")
    })
    @RequestMapping("saveToCart")
    public AjaxJson saveToCart(DirectCart cart, HttpServletRequest request){
        //判断参数是否合法
        if(cart == null ||  (cart.getChooseNum() != null && cart.getChooseNum() ==0)){
            return AjaxJson.fail("参数错误！");
        }
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        if(cart == null || StringUtils.isEmpty(cart.getStoreGoodsId())){
            return AjaxJson.fail("参数错误");
        }
        cart.setDealerAccountId(currentAccount.getId());
        StoreGoods storeGoods = new StoreGoods();
        storeGoods.setId(cart.getStoreGoodsId());

        List<StoreGoods> storeGoodsList =  storeGoodsService.findList(storeGoods);
        if(storeGoodsList.size() ==0){
            return AjaxJson.fail("商品不存在");
        }
        //判断经销商商品是否是上架
        storeGoods = storeGoodsList.get(0);
        if(storeGoods != null){
            if(!Global.YES.equals(storeGoods.getIsShelf()) && !Global.YES.equals(storeGoods.getAuditState())){
                return AjaxJson.fail("商品未上架");
            }
        }
        cart.setMainPicUrl(storeGoods.getMainPicUrl());
        cart.setGoodsName(storeGoods.getGoodsName());
        cart.setStoreId(storeGoods.getStoreId());
        cart.setStoreName(storeGoods.getStoreName());
        cart.setGoodsCode(storeGoods.getCode());
        cart.setDelFlag(Global.NO);
        //判断同一个店铺下，同一个下单人，同一个商品是否存在，存在的话，修改数量
        DirectCart c = new DirectCart();
        c.setStoreId(cart.getStoreId());
        c.setGoodsCode(cart.getGoodsCode());
        c.setDealerAccountId(cart.getDealerAccountId());
        List<DirectCart> cartList = directCartService.findList(c);
        if(cartList.size()> 0){
            DirectCart ca = cartList.get(0);
            ca.setChooseNum(ca.getChooseNum()+cart.getChooseNum());
            directCartService.save(ca);
        }else{
            //产品组编码和名称
            Goods gd = goodsService.findByCode(storeGoods.getCode());
            cart.setProductGroupCode(gd.getProductGroupCode());
            cart.setProductGroupName(gd.getProductGroupName());
            cart.setStoreName(storeGoods.getStoreName());
            directCartService.save(cart);
        }
        return AjaxJson.ok("加入购物车成功");
    }

    /**
     * 从购物车批量删除
     * @param ids
     * @return
     */
    @ApiOperation(notes = "delete", httpMethod = "POST", value = "从购物批量车删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "ids", value = "购物车的id，多个id 用英文逗号分割", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("delete")
    public  AjaxJson delete(String  ids){
        //判断参数是否合法
        if(StringUtils.isEmpty(ids)){
            return AjaxJson.fail("参数错误");
        }
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        String idArray [] = ids.split(",");
        DirectCart cart = new DirectCart();
        cart.setDealerAccountId(currentAccount.getId());
        for(String id:idArray){
            cart = directCartService.get(id);
            directCartService.delete(cart);
        }
        return AjaxJson.ok("删除成功");
    }

    /**
     * 修改购物车中商品数量
     * @param cart
     * @return
     */
    @ApiOperation(notes = "updateCart", httpMethod = "POST", value = "修改购物车数量")
    @ApiImplicitParams({@ApiImplicitParam(name = "goodsCode", value = "商品编码", required = true, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "chooseNum", value = "商品数量", required = true, paramType = "query",dataType = "int")
    })
    @RequestMapping("updateCart")
    public AjaxJson updateCart(DirectCart cart){
        //判断参数是否合法
        if(cart == null || StringUtils.isEmpty(cart.getGoodsCode()) || (cart.getChooseNum() != null && cart.getChooseNum() ==0)){
            return AjaxJson.fail("参数错误！");
        }
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        cart.setDealerAccountId(currentAccount.getId());
        DirectCart c = new DirectCart();
        c.setStoreId("-1");
        c.setGoodsCode(cart.getGoodsCode());
        List<DirectCart> cartList = directCartService.findList(c);
        if(cartList.size()> 0){
            DirectCart ca = cartList.get(0);
            ca.setChooseNum(cart.getChooseNum());
            directCartService.save(ca);
            return AjaxJson.ok("修改成功！");
        }
        return AjaxJson.fail("修改失败！");
    }

    /**
     * 获取购物车总件数
     * @param
     * @return
     */
    @ApiOperation(notes = "getCartGoodsCount", httpMethod = "GET", value = "获取购物车总件数")
    @RequestMapping("getCartGoodsCount")
    public AjaxJson  getCartGoodsCount(){
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        DirectCart cart = new DirectCart();
        cart.setDealerAccountId(currentAccount.getId());
        int count = directCartService.getCartCount(cart);
        return AjaxJson.ok(count);
    }



    /**
     * 根据购物车id查询确认订单信息
     * @param
     * @return
     */
    @ApiOperation(notes = "balance", httpMethod = "POST", value = "购物车节算")
    @ApiImplicitParams({@ApiImplicitParam(name = "cartIds", value = "购物车ID，多个ID之间逗号分割", required = true, paramType = "query",dataType = "String")
    })
    @RequestMapping("balance")
    public AjaxJson  balance(String cartIds){
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        if(StringUtils.isEmpty(cartIds)){
            return AjaxJson.fail("参数错误");
        }
        List<String> cartIdList = new ArrayList<>();
        if(cartIds.indexOf(",") > 0){
            cartIdList = Arrays.asList(cartIds.split(","));
        }else{
            cartIdList.add(cartIds);
        }
        List<DirectCart> listDirectCart = new ArrayList<>();
        //循环查找购物车信息
        for(String id:cartIdList){
            DirectCart directCart = directCartService.get(id);
            listDirectCart.add(directCart);
        }
        //获取String产品组
        List<String> groups_code = new ArrayList<>();
        List<String> groups_name = new ArrayList<>();
        //循环获取所有产品组
        for(DirectCart directCart:listDirectCart){
            if(!groups_code.contains(directCart.getProductGroupCode())){
                groups_code.add(directCart.getProductGroupCode());
                groups_name.add(directCart.getProductGroupName());
            }
        }
        List<DirectCartVo> listDirectCartVo = new ArrayList<>();
        for(int i=0; i<groups_code.size(); i++){
            DirectCartVo dv = new DirectCartVo();
            dv.setProductGroupCode(groups_code.get(i));
            dv.setProductGroupName(groups_name.get(i));
            List<DirectCart> dc = new ArrayList<>();
            for(DirectCart d:listDirectCart){
                if(groups_code.get(i).equals(d.getProductGroupCode())){
                    dc.add(d);
                }
            }
            dv.setListDirectCart(dc);
            listDirectCartVo.add(dv);
        }
        return AjaxJson.ok(listDirectCartVo);
    }

}
