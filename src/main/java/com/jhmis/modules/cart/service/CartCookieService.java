package com.jhmis.modules.cart.service;

import com.alibaba.fastjson.JSON;
import com.jhmis.modules.cart.dto.CartCookieDTO;
import com.jhmis.modules.cart.dto.CartCookieProductDTO;
import com.jhmis.modules.shop.entity.Goods;
import com.jhmis.modules.shop.service.GoodsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车cookie 提供基础查询,新增，移除方法BaseServiceImpl
 * @author  wzh
 * @company  全网数商研发事业部
 * @date   2017-05-05 15:40:03
 */
@Service
@Transactional(readOnly = true)
public class CartCookieService {



    @Autowired
    private GoodsService goodsService;

    /**
     * 购物车cookies购物车对象
     */
    public static final String COOKIE_CART_NAME = "cartManager";


    /**
     * 获取购物车数据
     * @param cookies
     * @return
     */
    public List<CartCookieProductDTO> getCartFromCookies(Cookie[] cookies) {
        CartCookieDTO cookieCart = getCookieCart_P2(cookies);
        if (cookieCart != null) {
            return genCartManager_P2(cookieCart);
        }
        return null;
    }

    /**
     * 新增购物车商品数量
     * @param cookies
     * @param goodsList
     * @return
     */
    public CartCookieDTO addCartManagerDetail(Cookie[] cookies,List<Goods> goodsList) {
        CartCookieDTO cookieCart = getCookieCart_P2(cookies);
        //判断同一个店铺下，同一个下单人，同一个商品是否存在，存在的话，修改数量

        synchronized(this){
            List<CartCookieProductDTO> list = new ArrayList<CartCookieProductDTO>();
            if(CollectionUtils.isNotEmpty(goodsList)){
                for(Goods goods : goodsList){
                    CartCookieProductDTO cart = new CartCookieProductDTO();
                    cart.setMainPicUrl(goods.getMainPicUrl());
                    cart.setGoodsName(goods.getName());
                    cart.setSkuNo(goods.getCode());
                    cart.setProductUuid(goods.getStoreGoodsId());
                    cart.setChooseNum(goods.getChooseNum());
                    cart.setPrice(goods.getExclusivePrice());
                    cart.setTotalPrice(goods.getExclusivePrice() * goods.getChooseNum());
                    cart.setStoreGoodsId(goods.getCode());
                    cart.setStock(goods.getStock());
                    if (cookieCart == null) {
                        // 新建购物车cookies 信息
                        cookieCart = new CartCookieDTO();
                        //产品组编码和名称
                        cart.setProductGroupCode(goods.getProductGroupCode());
                        cart.setProductGroupName(goods.getProductGroupName());
                        list.add(cart);
                        cookieCart.getCookieCartMap().put(COOKIE_CART_NAME,list);
                    } else {
                        // 商户购物车存在
                        if (cookieCart.getCookieCartMap().containsKey(COOKIE_CART_NAME)) {
                            list = cookieCart.getCookieCartMap().get(COOKIE_CART_NAME);
                            // 判断是否存在SKUNo
                            boolean updateFlag = true;
                            int position = 0;
                            if (list != null && list.size() > 0) {
                                for (CartCookieProductDTO detial : list) {
                                    if (detial.getStoreGoodsId().equals(cart.getStoreGoodsId())) {
                                        // 修改原来的数据
                                        detial.setChooseNum(detial.getChooseNum()+cart.getChooseNum());
                                        detial.setTotalPrice(detial.getChooseNum() * detial.getPrice());
                                        updateFlag = false;
                                    }
                                    position = detial.getPosition();
                                }
                            }
                            if (updateFlag) {
                                cart.setPosition(position + 1);
                                list.add(cart);
                            }
                        } else {
                            // 商户购物车不存在
                            list.add(cart);
                        }
                        cookieCart.getCookieCartMap().put(COOKIE_CART_NAME, list);
                    }
                }
            }
        }
        return cookieCart;
    }

    /**
     * 批量删除购物车商品
     * @param cookies
     * @param storeGoodsIds
     * @return
     */
    public CartCookieDTO removeProductFromCart(Cookie[] cookies, String storeGoodsIds) {
        CartCookieDTO cookieCart = getCookieCart_P2(cookies);
        String[] idArray = storeGoodsIds.split(",");
        if (idArray != null && idArray.length > 0 && cookieCart != null
                && cookieCart.getCookieCartMap() != null && cookieCart.getCookieCartMap().size() > 0) {
            for (String storeId : cookieCart.getCookieCartMap().keySet()) {
                List<CartCookieProductDTO> productModels = cookieCart.getCookieCartMap().get(storeId);
                List<CartCookieProductDTO> resList = new ArrayList<CartCookieProductDTO>();
                if (productModels != null && productModels.size() > 0) {
                    for(CartCookieProductDTO p : productModels){
                        if(storeGoodsIds.indexOf(p.getStoreGoodsId()) == -1){
                            resList.add(p);
                        }
                    }
                    if (resList.size() > 0) {
                        cookieCart.getCookieCartMap().put(storeId, resList);
                    } else {
                        // 移除购物车数据
                        cookieCart.getCookieCartMap().remove(storeId);
                    }
                }
            }
            return cookieCart;
        }

        return null;
    }

    /**
     * 修改购物车商品数量
     * @param cookies
     * @param storeGoodsId
     * @param chooseNum
     * @return
     */
    public CartCookieDTO changeCartProductNum(Cookie[] cookies, String storeGoodsId,int chooseNum) {
        CartCookieDTO cookieCart = getCookieCart_P2(cookies);
        if (cookieCart != null && cookieCart.getCookieCartMap() != null && cookieCart.getCookieCartMap().size() > 0) {
            for (String storeId : cookieCart.getCookieCartMap().keySet()) {
                List<CartCookieProductDTO> productModels = cookieCart.getCookieCartMap().get(storeId);
                if (productModels != null && productModels.size() > 0) {
                    for(CartCookieProductDTO p : productModels){
                        if(StringUtils.isNotBlank(p.getStoreGoodsId())
                                && StringUtils.isNotBlank(storeGoodsId)
                                && p.getStoreGoodsId().equals(storeGoodsId)){
                            p.setChooseNum(chooseNum);
                            p.setTotalPrice(chooseNum * p.getPrice());
                        }
                    }
                    cookieCart.getCookieCartMap().put(storeId,productModels);
                }
            }
            return cookieCart;
        }

        return null;
    }
    public CartCookieDTO cartCheckedProduct(Cookie[] cookies, String storeGoodsIds,int checked) {
        CartCookieDTO cookieCart = getCookieCart_P2(cookies);
        String[] idArray = storeGoodsIds.split(",");
        if (idArray != null && idArray.length > 0 && cookieCart != null
                && cookieCart.getCookieCartMap() != null && cookieCart.getCookieCartMap().size() > 0) {
            for (String storeId : cookieCart.getCookieCartMap().keySet()) {
                List<CartCookieProductDTO> productModels = cookieCart.getCookieCartMap().get(storeId);
                List<CartCookieProductDTO> resList = new ArrayList<CartCookieProductDTO>();
                if (productModels != null && productModels.size() > 0) {
                    for(CartCookieProductDTO p : productModels){
                        if(storeGoodsIds.indexOf(p.getStoreGoodsId()) != -1){
                            p.setChecked(checked);
                        }
                    }
                    cookieCart.getCookieCartMap().put(storeId,productModels);
                }
            }
            return cookieCart;
        }

        return null;
    }

    /**
     * 查询购物车中产品件数
     * @param cookies
     * @return
     */
    public int getCartCount(Cookie[] cookies){
        int num = 0;
        CartCookieDTO cookieCart = getCookieCart_P2(cookies);
        if (cookieCart != null && cookieCart.getCookieCartMap() != null && cookieCart.getCookieCartMap().size() > 0) {
            for (String storeId : cookieCart.getCookieCartMap().keySet()) {
                List<CartCookieProductDTO> productModels = cookieCart.getCookieCartMap().get(storeId);
                if (productModels != null && productModels.size() > 0) {
                    for(CartCookieProductDTO p : productModels){
                        num += p.getChooseNum();
                    }
                }
            }
            return num;
        }

        return num;
    }

    public List<CartCookieProductDTO> getCartToBalance(Cookie[] cookies){
        CartCookieDTO cookieCart = getCookieCart_P2(cookies);
        List<CartCookieProductDTO> listCartCookieProductDTO = new ArrayList<CartCookieProductDTO>();
        if (cookieCart != null && cookieCart.getCookieCartMap() != null && cookieCart.getCookieCartMap().size() > 0) {
            for (String storeId : cookieCart.getCookieCartMap().keySet()) {
                List<CartCookieProductDTO> productModels = cookieCart.getCookieCartMap().get(storeId);
                if (productModels != null && productModels.size() > 0) {
                    for(CartCookieProductDTO p : productModels){
                        if(p.getChecked() == 1){
                            listCartCookieProductDTO.add(p);
                        }
                    }

                }
            }
            return listCartCookieProductDTO;
        }
        return null;
    }

    /**
     * 获取购物车信息
     * @param cookies
     * @return
     */
    public Map<String,List<CartCookieProductDTO>> getCartByStoreGoodsIds(Cookie[] cookies){
        CartCookieDTO cookieCart = getCookieCart_P2(cookies);
        Map<String,List<CartCookieProductDTO>> map = new HashMap<String,List<CartCookieProductDTO>>();
        if (cookieCart != null && cookieCart.getCookieCartMap() != null && cookieCart.getCookieCartMap().size() > 0) {
            for (String storeId : cookieCart.getCookieCartMap().keySet()) {
                List<CartCookieProductDTO> listCartCookieProductDTO = new ArrayList<CartCookieProductDTO>();
                List<CartCookieProductDTO> productModels = cookieCart.getCookieCartMap().get(storeId);
                if (productModels != null && productModels.size() > 0) {
                    for(CartCookieProductDTO p : productModels){
                        if(p.getChecked() == 1){
                            listCartCookieProductDTO.add(p);
                        }
                    }
                    if(CollectionUtils.isNotEmpty(listCartCookieProductDTO)){
                        map.put(storeId,listCartCookieProductDTO);
                    }
                }
            }
            return map;
        }
        return null;
    }



    /**
     * 从cookie 中获取购物车信息
     *
     * @param cookies cookie 信息
     *
     * @return cookie中购物信息
     */
    public CartCookieDTO getCookieCart_P2(Cookie[] cookies) {
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(COOKIE_CART_NAME)) {
                    String cookieValue = "";
                    try {
                        cookieValue = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    if (StringUtils.isEmpty(cookieValue)) {
                        continue;
                    }
                    // 1从cookies中获取购物车中的数据，转换成对象，发送到页面
                    CartCookieDTO cookieCart = JSON.parseObject(cookieValue, CartCookieDTO.class);
                    // 2返回JSON转换后的对象KEY=CartStore,VALUE=List<CartCookieProductDTO>
                    return cookieCart;
                }
            }
        }
        return null;
    }

    /**
     * 根据cookies 数据组装购物车数据
     *
     * @param cookieCart cook 中的购物侧数据
     *
     * @return 购物车信息集合
     */
    public List<CartCookieProductDTO> genCartManager_P2(CartCookieDTO cookieCart) {
        List<CartCookieProductDTO> list = new ArrayList<CartCookieProductDTO>();
        if (cookieCart != null && cookieCart.getCookieCartMap() != null && cookieCart.getCookieCartMap().size() > 0) {
            for (String storeId : cookieCart.getCookieCartMap().keySet()) {
                List<CartCookieProductDTO> productModels = cookieCart.getCookieCartMap().get(storeId);
                if (productModels != null && productModels.size() > 0) {
                    list.addAll(productModels);
                }
            }
        }
        return list;
    }






}
