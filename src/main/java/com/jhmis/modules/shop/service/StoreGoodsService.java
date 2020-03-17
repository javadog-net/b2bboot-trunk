/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import com.jhmis.common.config.Global;
import com.jhmis.common.utils.IdGen;
import com.jhmis.common.utils.MyBeanUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.cms.entity.CmsConfig;
import com.jhmis.modules.cms.service.HtmlService;
import com.jhmis.modules.cms.utils.CmsUtils;
import com.jhmis.modules.cms.utils.Htmlpath;
import com.jhmis.modules.shop.entity.*;
import com.jhmis.modules.shop.mapper.StoreGoodsMapper;
import com.jhmis.modules.shop.mapper.StoreGoodsPriceMapper;
import com.jhmis.modules.shop.mapper.StorePriceGroupMapper;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 店铺商品管理Service
 *
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class StoreGoodsService extends CrudService<StoreGoodsMapper, StoreGoods> {

    @Resource
    StorePriceGroupMapper storePriceGroupMapper;
    @Resource
    private GoodsCategoryService goodsCategoryService;
    @Resource
    private GoodsService goodsService;
    @Resource
    StoreGoodsPriceMapper storeGoodsPriceMapper;

    @Resource
    StoreGoodsMapper storeGoodsMapper;
    @Resource
	private HtmlService htmlService;
    public StoreGoods get(String id) {
        return super.get(id);
    }

    public List<StoreGoods> findList(StoreGoods storeGoods) {
        return super.findList(storeGoods);
    }

    public Page<StoreGoods> findPage(Page<StoreGoods> page, StoreGoods storeGoods) {
        return super.findPage(page,storeGoods);
    }


    public Page<StoreGoods> findPageSearchTag(Page<StoreGoods> page, StoreGoods entity) {
        dataRuleFilter(entity);
        entity.setPage(page);
        page.setList(mapper.selectForSearchTag(entity));
        return page;
    }

    @Transactional(readOnly = false)
    public void save(StoreGoods storeGoods,
    		HttpServletRequest request,GoodsCategory category,Goods goods,
    		String templatePath,String htmlPath) {
        //先插入其附表
        //1.插入store_price_group
        List<StorePriceGroup> storePriceGroupList = storeGoods.getStorePriceGroupList();
        if (null != storePriceGroupList) {
            for (StorePriceGroup storePriceGroup : storePriceGroupList) {
                User user = UserUtils.getUser();
                if (StringUtils.isBlank(user.getId())) {
                    continue;
                }
                storePriceGroup.setId(IdGen.uuid());
                storePriceGroup.setCreateByStr(user.getId());
                storePriceGroup.setCreateDate(new Date());
                storePriceGroup.setUpdateByStr(user.getId());
                storePriceGroup.setUpdateDate(new Date());
                storePriceGroupMapper.insert(storePriceGroup);
                //2.插入store_goods_price
                List<StoreGoodsPrice> storeGoodsPriceList = storePriceGroup.getStoreGoodsPriceList();
                if (null != storeGoodsPriceList) {
                    for (StoreGoodsPrice storeGoodsPrice : storeGoodsPriceList) {
                        storeGoodsPrice.setId(IdGen.uuid());
                        storeGoodsPrice.setCreateByStr(user.getId());
                        storeGoodsPrice.setCreateDate(new Date());
                        storeGoodsPrice.setUpdateByStr(user.getId());
                        storeGoodsPrice.setUpdateDate(new Date());
                        storeGoodsPrice.setStorePriceGroupId(storePriceGroup.getId());
                        storeGoodsPriceMapper.insert(storeGoodsPrice);
                    }
                }

            }
        }
        //设置默认状态上架
        storeGoods.setIsShelf(Global.YES);
        storeGoods.setShelfTime(new Date());
        super.save(storeGoods);
        try {//静态化处理
			htmlService.htmlGoods(request, category, goods, storeGoods, templatePath, 1,htmlPath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
    }

    @Transactional(readOnly = false)
    public void delete(StoreGoods storeGoods) {
        super.delete(storeGoods);
    }

    @Transactional(readOnly = false)
    public StoreGoods selectByCodeOrStoreId(String storeId, String code) {
        return mapper.selectByCodeOrStoreId(storeId, code);
    }

    @Transactional(readOnly = false)
    public int updateStoreGoods(StoreGoods storeGoods,
    		HttpServletRequest request,GoodsCategory category,Goods goods,
    		String templatePath,String htmlPath) {
        //先删后插
        StoreGoods s = mapper.get(storeGoods.getId());
        StorePriceGroup storePriceGroup = new StorePriceGroup();
        storePriceGroup.setCode(s.getCode());
        storePriceGroup.setStoreId(storeGoods.getStoreId());
        List<StorePriceGroup> storePriceGroupList = storePriceGroupMapper.findList(storePriceGroup);
        for (StorePriceGroup spg : storePriceGroupList) {
            StoreGoodsPrice sgp = new StoreGoodsPrice();
            sgp.setStorePriceGroupId(spg.getId());
            storeGoodsPriceMapper.deleteBySPGId(spg.getId());
        }
        storePriceGroupMapper.deleteByStoreIdOrCode(s.getStoreId(), s.getCode());
        //进行保存操作
        List<StorePriceGroup> storePriceGroupList_save = storeGoods.getStorePriceGroupList();
        if (null != storePriceGroupList_save) {
            for (StorePriceGroup storePriceGroupSave : storePriceGroupList_save) {
                StorePriceGroup ssp = storePriceGroupSave;
                User user = UserUtils.getUser();
                if (StringUtils.isBlank(user.getId())) {
                    continue;
                }
                storePriceGroupSave.setId(IdGen.uuid());
                storePriceGroupSave.setCreateByStr(user.getId());
                storePriceGroupSave.setCreateDate(new Date());
                storePriceGroupSave.setCreateByStr(user.getId());
                storePriceGroupSave.setUpdateDate(new Date());
                storePriceGroupMapper.insert(storePriceGroupSave);
                //2.插入store_goods_price
                List<StoreGoodsPrice> storeGoodsPriceList = ssp.getStoreGoodsPriceList();
                if (null != storeGoodsPriceList) {
                    for (StoreGoodsPrice storeGoodsPrice : storeGoodsPriceList) {
                        storeGoodsPrice.setId(IdGen.uuid());
                        storeGoodsPrice.setCreateByStr(user.getId());
                        storeGoodsPrice.setCreateDate(new Date());
                        storeGoodsPrice.setUpdateByStr(user.getId());
                        storeGoodsPrice.setUpdateDate(new Date());
                        storeGoodsPrice.setStorePriceGroupId(storePriceGroupSave.getId());
                        storeGoodsPriceMapper.insert(storeGoodsPrice);
                    }
                }
            }
        }

        try {
            MyBeanUtils.copyBeanNotNull2Bean(storeGoods,s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int  updateInt=mapper.update(s);
        try {//静态化处理
            if(StringUtils.isBlank(htmlPath)){
                htmlPath=s.getOldHtmlPath();
            }
            if(com.jhmis.common.utils.StringUtils.isBlank(htmlPath)) {
                CmsConfig cmsConfig = CmsUtils.getCmsConfig();
                // 获取最大索引号
                Integer num = mapper.selectMaxNum();
                if(null == num){
                    num=0;
                }
                s.setHtmlIndexNum(num+1);
               htmlPath= Htmlpath.goodsPath(cmsConfig, category, s);
            }
			htmlService.htmlGoods(request, category, goods, storeGoods, templatePath, 1,htmlPath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
        return updateInt;
    }


    @Transactional(readOnly = false)
    public int update(StoreGoods storeGoods,HttpServletRequest request,String templatePath) {
        Goods goods=goodsService.findUniqueByProperty("code",storeGoods.getCode());
        GoodsCategory category=goodsCategoryService.findUniqueByProperty("id",storeGoods.getCategoryPid());
        int i=0;
        try {
           i=mapper.update(storeGoods);
            htmlService.htmlGoods(request, category, goods, storeGoods, templatePath, 1,storeGoods.getHtmlpath());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        return i;
    }

    /**
     * 批量下架 lyz
     *
     * @param ids
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public boolean batchDelete(String ids,HttpServletRequest request,String templatePath) {
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            StoreGoods storeGoods = mapper.findUniqueByProperty("id", id);
            GoodsCategory category=goodsCategoryService.findUniqueByProperty("id",storeGoods.getCategoryPid());
            Goods goods=goodsService.findUniqueByProperty("code",storeGoods.getCode());
            try {
                htmlService.htmlGoods(request, category, goods, storeGoods, templatePath, 1,storeGoods.getHtmlpath());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
            storeGoods.setIsShelf(Global.NO);
            int i = storeGoodsMapper.updateStock(storeGoods);
            if (i <= 0) {
                return false;
            }
        }
        return true;
    }

    @Transactional(readOnly = false)
    public int batchUndercarriage(String[] storeGoodsIdArray,HttpServletRequest request,String templatePath) {
        //下架包括批量下架
        for (int i = 0; i < storeGoodsIdArray.length; i++) {
            StoreGoods storeGoods = mapper.findUniqueByProperty("id", storeGoodsIdArray[i]);
            GoodsCategory category=goodsCategoryService.findUniqueByProperty("id",storeGoods.getCategoryPid());
            Goods goods=goodsService.findUniqueByProperty("code",storeGoods.getCode());
            storeGoods.setIsShelf(Global.NO);
            mapper.update(storeGoods);
            try {
                htmlService.htmlGoods(request, category, goods, storeGoods, templatePath, 1,storeGoods.getHtmlpath());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }

        }
        return storeGoodsIdArray.length;
    }

    @Transactional(readOnly = false)
    public int deleteStoreGoods(String[] storeGoodsIdArray) {
        int flag = 0;
        for (int i = 0; i < storeGoodsIdArray.length; i++) {
            StoreGoods storeGoods = mapper.findUniqueByProperty("id", storeGoodsIdArray[i]);
            //逐步删除,先由最底层开始
            StorePriceGroup storePriceGroup = new StorePriceGroup();
            storePriceGroup.setCode(storeGoods.getCode());
            storePriceGroup.setStoreId(storeGoods.getStoreId());
            List<StorePriceGroup> storePriceGroupList = storePriceGroupMapper.findList(storePriceGroup);
            for (StorePriceGroup spg : storePriceGroupList) {
                storeGoodsPriceMapper.deleteBySPGId(spg.getId());
            }
            storePriceGroupMapper.deleteByStoreIdOrCode(storeGoods.getStoreId(), storeGoods.getCode());
            mapper.delete(storeGoods);
            flag++;
        }
        return flag;
    }

    @Transactional(readOnly = false)
    public StoreGoods selectPriceGroup(String storeId, String purchaserId, String code) {
        return mapper.selectPriceGroup(storeId, purchaserId, code);
    }

    @Transactional(readOnly = false)
    public List<StoreGoods> selectForSearchTag(StoreGoods storeGoods) {
        return mapper.selectForSearchTag(storeGoods);
    }

    @Transactional(readOnly = false)
    public Page<StoreGoods> findPageByTag(Page<StoreGoods> page, StoreGoods storeGoods) {
        dataRuleFilter(storeGoods);
        storeGoods.setPage(page);
        page.setList(mapper.selectForSearchTag(storeGoods));
        return page;
    }

    @Transactional(readOnly = false)
    public List<StoreGoods> selectByCategoryPid(StoreGoods storeGoods) {
        return mapper.selectByCategoryPid(storeGoods);
    }

    public List<StoreGoods> findCartStoreGoods(StoreGoods storeGoods) {
        return mapper.findCartStoreGoods(storeGoods);
    }

    public List<StoreGoods> findStoreGoodsAndProPrice(StoreGoods storeGoods) {
        return mapper.findStoreGoodsAndProPrice(storeGoods);
    }
    
    public List<StoreGoods> getStoreGoodsAndProPriceByStoreGoodIds(StoreGoods storeGoods) {
        return mapper.getStoreGoodsAndProPriceByStoreGoodIds(storeGoods);
    }

    /**
     * 通过产品code获取店铺商品集合
     * @param storeGoods
     * @return
     */
    public List<StoreGoods> getStoreGoodsByCodes(StoreGoods storeGoods){
        return mapper.getStoreGoodsByCodes(storeGoods);
    }
    
    /** 
      * @Title: selectMaxNum 
      * @Description: TODO  获取索引号最大值
      * @return 
      * @return Integer
      * @author tc
      * @date 2019年10月23日下午3:16:19
      */
    public Integer selectMaxNum(){
    	return storeGoodsMapper.selectMaxNum();
    }

	/** 
	  * @Title: guess 
	  * @Description: TODO  猜你想要
	  * @param type
	  * @return List<StoreGoods>
	  * @author tc
	  * @date 2019年10月28日下午2:44:18
	  */
	public List<StoreGoods> guess(String type) {
		// TODO Auto-generated method stub
		return storeGoodsMapper.guess(type);
	}
    
}