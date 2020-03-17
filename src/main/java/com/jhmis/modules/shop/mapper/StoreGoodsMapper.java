/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.StoreGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 店铺商品管理MAPPER接口
 *
 * @author tity
 * @version 2018-07-22
 */
@MyBatisMapper
public interface StoreGoodsMapper extends BaseMapper<StoreGoods> {
    //根据店铺id及商品编码查询店铺商品信息
    StoreGoods selectByCodeOrStoreId(String storeId, String code);

    //根据店铺id及商品编码删除店铺商品信息
    int deleteByStoreIdOrCode(String storeId, String code);

    //根据参数查询价格分组
    StoreGoods selectPriceGroup(String storeId, String purchaserId, String code);

    //根据属性值进行查询
    List<StoreGoods> selectForSearchTag(StoreGoods storeGoods);

    //根据分类父级ID进行商品查询
    List<StoreGoods> selectByCategoryPid(StoreGoods storeGoods);


    /**
     * 查询购物车中的产品信息
     *
     * @param storeGoods
     * @return
     */
    public List<StoreGoods> findCartStoreGoods(StoreGoods storeGoods);

    /**
     * 根据storeGoodsId 查询促销价格
     *
     * @param storeGoods
     * @return
     */
    public List<StoreGoods> findStoreGoodsAndProPrice(StoreGoods storeGoods);

    public int updateStock(StoreGoods storeGoods);


    /**
     * 查询商品和价格信息
     * @param storeGoods
     * @return
     */
    public List<StoreGoods> getStoreGoodsAndProPriceByStoreGoodIds(StoreGoods storeGoods);

    /**
     * 通过产品code获取店铺商品集合
     * @param storeGoods
     * @return
     */
    public List<StoreGoods> getStoreGoodsByCodes(StoreGoods storeGoods);

    
    /** 
      * @Title: selectMaxNum 
      * @Description: TODO 获取索引号的最大值
      * @return 
      * @return Integer
      * @author tc
      * @date 2019年10月23日下午3:14:24
      */
    public Integer selectMaxNum();

	/** 
	  * @Title: guess 
	  * @Description: TODO  猜你想要
	  * @param type
	  * @return 
	  * @return List<StoreGoods>
	  * @author tc
	  * @date 2019年10月28日下午2:49:18
	  */
	List<StoreGoods> guess(String type);

}