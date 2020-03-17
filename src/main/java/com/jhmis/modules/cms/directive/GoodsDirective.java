package com.jhmis.modules.cms.directive;

import com.haier.link.upper.dto.ProductInfo;
import com.haier.link.upper.model.Sign;
import com.haier.link.upper.service.LinkProductUpperReadService;
import com.jhmis.common.utils.Constants;
import com.jhmis.core.web.BaseDirective;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import com.jhmis.modules.shop.entity.*;
import com.jhmis.modules.shop.service.*;
import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title: GoodsDirective
 * </p>
 * <p>
 * Description:
 * </p>
 * 商品详情
 * 
 * @author tc
 * @date 2019年10月22日 上午9:06:08
 */
@FreemarkerComponent("goods")
public class GoodsDirective extends BaseDirective {
	@Autowired
	private StoreGoodsService storeGoodsService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private GoodsEvaluateService goodsEvaluateService;
	@Lazy
	@Autowired
	private LinkProductUpperReadService linkProductUpperReadService;


	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
        long key = Constants.PRODUCTS_CENTER_KEY;
        String secret = Constants.PRODUCTS_CENTER_SECRET;
		// storegood里面的 ID
		String id = getParam(params, "id");
		// 日期格式
		//String dateFormat = getParam(params, "dateFormat");

		Writer out = env.getOut();

		if (body != null) {
			// 设置循环变量
			if (loopVars != null && loopVars.length > 0) {
			//	Map<String, Object> map = new HashMap<>();
				// 查询信息
				StoreGoods storeGoods = null;
				Store store = null;
				ProductInfo productInfo = null;
				Brand brand = new Brand();
				Goods goods = null;
				List<GoodsEvaluate> evaluateList=null;
				if (StringUtils.isNotEmpty(id)) {
					storeGoods = storeGoodsService.findUniqueByProperty("id", id);
                    System.out.println("goodsDirective标签商品详情==：storegoods对象"+storeGoods);
                    try {
                        if (null != storeGoods) {
                            // 拉取产品中心数据
                            Sign s = new Sign(key, secret);
                            productInfo = linkProductUpperReadService.getProductInfoByCode(storeGoods.getCode(), s)
                                    .getResult();
                            store = storeService.findUniqueByProperty("id", storeGoods.getStoreId());
                            GoodsEvaluate entity=new GoodsEvaluate();
                            entity.setStoreId(storeGoods.getStoreId());
                            entity.setGoodsCode(storeGoods.getCode());
                            evaluateList=goodsEvaluateService.findList(entity);
                            goods = goodsService.findByCode(storeGoods.getCode());
                            if (null != goods) {
                            	//产品主图转换一下
								if(StringUtils.isNotEmpty(goods.getMainPicUrl() )){
									if(goods.getMainPicUrl().indexOf("|")>0){
										storeGoods.setMainOnePic(goods.getMainPicUrl().split("\\|")[0]);
									}else{
										storeGoods.setMainOnePic(goods.getMainPicUrl());
									}

								}
                                brand = brandService.findUniqueByProperty("id", goods.getBrand());

                            brand.setEvaluateList(evaluateList);
                            brand.setGoods(goods);
                            brand.setProductInfo(productInfo);
                            brand.setStore(store);
                            brand.setStoreGoods(storeGoods);
							}
                           /* // 店铺
                            map.put("store", store);
                            // 商品基本信息
                            map.put("goods", goods);
                            // 店铺商品信息
                            map.put("storeGoods", storeGoods);
                            // 产品中心信息
                            map.put("productInfo", productInfo);
                            // 商品评价
                            map.put("evaluateList", evaluateList);*/
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("goodsDirective标签失败：");
                    }

                }
                loopVars[0] = new BeanModel(brand, new BeansWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
				body.render(out);

			}
		}


	}




}
