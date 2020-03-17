/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import com.haier.link.upper.dto.ProductInfo;
import com.haier.link.upper.model.ProductMaterial;
import com.haier.link.upper.model.ProductSpecification;
import com.haier.link.upper.model.Sign;
import com.haier.link.upper.model.SimpleProduct;
import com.haier.link.upper.service.LinkProductUpperReadService;
import com.jhmis.common.config.Global;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.cms.mapper.CategoryMapper;
import com.jhmis.modules.cms.service.HtmlService;
import com.jhmis.modules.shop.entity.*;
import com.jhmis.modules.shop.mapper.*;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 商品表Service
 * @author tity
 * @version 2018-07-23
 */
@Service
@Transactional(readOnly = true)
public class GoodsService extends CrudService<GoodsMapper, Goods> {

	@Resource
	private GoodsExtMapper goodsExtMapper;

	@Resource
	private GoodsCategoryMapper goodsCategoryMapper;

   @Resource
    private TrancheProductMapper trancheProductMapper;

    @Resource
	private GoodsPropertiesMapper goodsPropertiesMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private GoodsClassMapper goodsClassMapper;
	@Autowired
	private HtmlService htmlService;


	public Goods get(String id) {
		return super.get(id);
	}

	public Goods findByCode(String code) {
		return super.mapper.findByCode(code);
	}
	
	public List<Goods> findList(Goods goods) {
		return super.findList(goods);
	}
	
	public Page<Goods> findPage(Page<Goods> page, Goods goods) {
		return super.findPage(page, goods);
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param goods
	 * @return
	 */
	public Page<Goods> findNoPublishPage(Page<Goods> page, Goods goods) {
		dataRuleFilter(goods);
		goods.setPage(page);
		page.setList(mapper.findNoPublishList(goods));
		return page;
	}


	
	@Transactional(readOnly = false)
	public void save(Goods goods,LinkProductUpperReadService linkProductUpperReadService,long key,String secret) {
		if(mapper.findByCode(goods.getCode())!=null){
			//更新时先删后插
			goodsExtMapper.deleteByCode(goods.getCode());
			goods.setUpdateDate(new Date());
			//处理图片全路径
			if(goods.getMainPicUrl()!=null && (!"".equals(goods.getMainPicUrl()))){
				//证明有值则判断是否是来自用户总新的
				String arr[] = goods.getMainPicUrl().split("\\|");
				if(arr.length>0){
					String endMainPicUrl = "";
					String endMainPicUrlStr = "";
					for(int i=0; i<arr.length; i++){
						if(arr[i].contains("http://file.c.haier.net")){
							endMainPicUrlStr = arr[i];
						}else if(arr[i].contains(Global.getURLPath())){
							endMainPicUrlStr = arr[i];
						}
						else{
							endMainPicUrlStr = Global.getURLPath() + arr[i];
						}
						endMainPicUrl = endMainPicUrl + endMainPicUrlStr + "|";
					}
					String endStr = endMainPicUrl.substring(endMainPicUrl.length()-1,endMainPicUrl.length());
					if("|".equals(endStr)){
						endMainPicUrl = endMainPicUrl.substring(0,endMainPicUrl.length()-1);
					}
					if(StringUtils.isNotBlank(endMainPicUrl)&&endMainPicUrl.startsWith("|")){
						String str=endMainPicUrl;
						goods.setMainPicUrl(endMainPicUrl.substring(1,str.length()));
					}else {
						goods.setMainPicUrl(endMainPicUrl);
					}
				}
			}
			mapper.update(goods);
		}else{
			//进行属性赋值
			Sign s = new Sign(key, secret);
			ProductInfo productInfo = linkProductUpperReadService.getProductInfoByCode(goods.getCode(), s).getResult();
			List<ProductSpecification> productSpecificationList = productInfo.getProductSpecifications();
			List<ProductMaterial> productPicturesList = productInfo.getProductPictures();
			//图片赋值
			if(productPicturesList!=null){
				String imgurl = "";
				for(ProductMaterial productMaterial:productPicturesList){
					imgurl +=productMaterial.getUrl()+ "|";
				}
				if(!"".equals(imgurl)){
				    //并且没有初始化拉去的值
				    if("".equals(goods.getMainPicUrl())){
                        goods.setMainPicUrl(imgurl.substring(0,imgurl.length()-1));
                    }
				}
			}else{
				//如果存在自己录入值
				//处理图片全路径
				if(goods.getMainPicUrl()!=null&& (!"".equals(goods.getMainPicUrl()))){
					//证明有值则判断是否是来自用户总新的
					String arr[] = goods.getMainPicUrl().split("\\|");
					if(arr.length>0){
                        String endMainPicUrl = "";
                        String endMainPicUrlStr = "";
                        for(int i=0; i<arr.length; i++){
                            if(arr[i].contains("http://file.c.haier.net")){
                                endMainPicUrlStr = arr[i];
                            }else if(arr[i].contains(Global.getURLPath())){
                                endMainPicUrlStr = arr[i];
                            }
                            else{
                                endMainPicUrlStr = Global.getURLPath() + arr[i];
                            }
                            endMainPicUrl = endMainPicUrl + endMainPicUrlStr + "|";
                        }
                        String endStr = endMainPicUrl.substring(endMainPicUrl.length()-1,endMainPicUrl.length());
                        if("|".equals(endStr)){
                            endMainPicUrl = endMainPicUrl.substring(0,endMainPicUrl.length()-1);
                        }
                        goods.setMainPicUrl(endMainPicUrl);
					}
				}
			}
			//属性赋值
			if(productSpecificationList!=null){
				//属性值不为空,进行循环赋值
				for(ProductSpecification productSpecification:productSpecificationList){
					GoodsProperties goodsProperties = new GoodsProperties();
					goodsProperties.setGoodsCode(goods.getCode());
					goodsProperties.setTag(productSpecification.getTag());
					goodsProperties.setName(productSpecification.getName());
					goodsProperties.setValue(productSpecification.getValue());
					User user = UserUtils.getUser();
					if (StringUtils.isNotBlank(user.getId())){
						goodsProperties.setCreateBy(user);
					}
					goodsProperties.setCreateDate(new Date());
					goodsPropertiesMapper.insert(goodsProperties);
				}
			}
			super.save(goods);
		}
		GoodsCategory goodsCategory = goodsCategoryMapper.get(goods.getCat().getId());
		//附加属性插入
		GoodsExt goodsExt = new GoodsExt();
		goodsExt.setCategoryId(goodsCategory.getId());
		goodsExt.setCategoryPid(goodsCategory.getParentId());
		goodsExt.setCode(goods.getCode());
		goodsExt.setMarketPrice(goods.getMarketPrice());
		goodsExt.setState(goods.getState());
		goodsExt.setCreateBy(new User());
		goodsExt.setCreateDate(new Date());
		goodsExtMapper.insert(goodsExt);

	}
	
	@Transactional(readOnly = false)
	public void delete(Goods goods) {
		goodsExtMapper.deleteByCode(goods.getCode());
		super.delete(goods);
	}
    /**
     * 导入一期产品
     */
    @Transactional(readOnly = false)
	public void importTranche(Sign s,LinkProductUpperReadService linkProductUpperReadService){
        TrancheProduct t = new TrancheProduct();
        List<TrancheProduct> trancheProduct_list = trancheProductMapper.findList(t);
        for(TrancheProduct trancheProduct:trancheProduct_list){
        	if(trancheProduct.getProductCode()!=null){
        		Goods hasGoods = mapper.findUniqueByProperty("code",trancheProduct.getProductCode());
        		if(hasGoods!=null){
        			continue;
				}
			}
            Goods good = new Goods();
            String code = trancheProduct.getProductCode();
			ProductInfo productInfo = linkProductUpperReadService.getProductInfoByCode(code, s).getResult();
            if(productInfo!=null){
                SimpleProduct simpleProduct = productInfo.getSimpleProduct();
				List<ProductMaterial> productPicturesList = productInfo.getProductPictures();
				List<ProductSpecification> productSpecificationList = productInfo.getProductSpecifications();
				//图片赋值
				String imgurl = "";
				if(productPicturesList!=null){
					for(ProductMaterial productMaterial:productPicturesList){
						imgurl +=productMaterial.getUrl()+ "|";
					}
					if(!"".equals(imgurl)){
						good.setMainPicUrl(imgurl.substring(0,imgurl.length()-1));
					}
				}
				//属性赋值
				if(productSpecificationList!=null){
					//属性值不为空,进行循环赋值
					for(ProductSpecification productSpecification:productSpecificationList){
						GoodsProperties goodsProperties = new GoodsProperties();
						goodsProperties.setGoodsCode(code);
						goodsProperties.setTag(productSpecification.getTag());
						goodsProperties.setName(productSpecification.getName());
						goodsProperties.setValue(productSpecification.getValue());
						User user = UserUtils.getUser();
						if (StringUtils.isNotBlank(user.getId())){
							goodsProperties.setCreateBy(user);
						}
						goodsProperties.setCreateDate(new Date());
						goodsPropertiesMapper.insert(goodsProperties);
					}
				}
                //在产品中心存在,证明查到了
                trancheProduct.setProductIscheck("0");
                good.setCode(simpleProduct.getCode());
                good.setMainPicUrl(imgurl);
                good.setName(simpleProduct.getProductGroupName()+simpleProduct.getName());
                super.save(good);
            }else{
                //在产品中心不存在,证明未查到了
                trancheProduct.setProductIscheck("1");
				trancheProductMapper.update(trancheProduct);
            }

        }
    }

	/**
	 * 通过产品编码集合获取产品集合
	 * @param goods
	 * @return
	 */
	public List<Goods> findByCodeList(Goods goods){
		return mapper.findByCodeList(goods);
	}

	

}