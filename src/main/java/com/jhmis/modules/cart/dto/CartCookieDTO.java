package com.jhmis.modules.cart.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * cookies中存储的购物车数据
 * @author wzh
 * @data 2017/4/5
 */
public class CartCookieDTO implements Serializable {

    private static final long serialVersionUID = 788675974689780304L;

    /**
     * 存储到cookies中的购物车数据
     * key:storeUuid value:购物车商品数据
     */
    private Map<String,List<CartCookieProductDTO>> cookieCartMap = new HashMap<String,List<CartCookieProductDTO>>();

    public Map<String, List<CartCookieProductDTO>> getCookieCartMap() {
        return cookieCartMap;
    }

    public void setCookieCartMap(Map<String, List<CartCookieProductDTO>> cookieCartMap) {
        this.cookieCartMap = cookieCartMap;
    }
}

