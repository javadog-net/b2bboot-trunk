package com.jhmis.api.store;

import com.haier.link.upper.service.LinkProductUpperReadService;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.wsClient.entity.BatchsaleTemplate;
import com.jhmis.common.wsClient.entity.BatchsaleTemplateItem;
import com.jhmis.common.wsClient.entity.MDMCustomer;
import com.jhmis.common.wsClient.service.WsClientList;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.entity.CmsConfig;
import com.jhmis.modules.cms.service.CategoryService;
import com.jhmis.modules.cms.service.HtmlService;
import com.jhmis.modules.cms.utils.CmsUtils;
import com.jhmis.modules.cms.utils.Htmlpath;
import com.jhmis.modules.shop.entity.*;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.service.*;
import com.jhmis.modules.shop.service.dealer.DealerService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.shop.utils.DealerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 所有的api控制器类名命名为API+目录名+模块名+Controller 主要是可能会与后台的控制器重名 商品控制器
 */
@Api(value = "ApiDealerGoodsController", description = "店铺商品管理")
@RestController
@RequestMapping("/api/store/goods")
public class ApiStoreGoodsController extends BaseController {
	protected Logger logger = LoggerFactory.getLogger(ApiStoreGoodsController.class);

	@Autowired
	GoodsCategoryService goodsCategoryService;

	@Autowired
	GoodsService goodsService;

	@Autowired
	GoodsExtService goodsExtService;
	@Lazy
	@Autowired
	private LinkProductUpperReadService linkProductUpperReadService;

	@Autowired
	StoreGoodsService storeGoodsService;

	@Autowired
	StoreService storeService;

	@Autowired
	StorePriceGroupService storePriceGroupService;

	@Autowired
	StoreGoodsPriceService storeGoodsPriceService;

	@Autowired
	DealerService dealerService;

	@Autowired
	GoodsClassPropertiesService goodsClassPropertiesService;

	@Autowired
	BrandService brandService;

	@Autowired
	PurchaserService purchaserService;

	@Autowired
	private GuessRecommendService guessRecommendService;
	@Resource
	private HtmlService htmlService;
	@Resource
	private CategoryService categoryService;

	/**
	 * 商品列表
	 *
	 * @return
	 */
	@ApiOperation(notes = "list", httpMethod = "POST", value = "商品列表", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "产品名称", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "categoryPid", value = "产品一级目录", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "categoryId", value = "产品二级目录", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "pageNo", value = "页码", required = false, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, paramType = "form", dataType = "int") })
	@RequestMapping("/list")
	public AjaxJson list(Goods goods, HttpServletRequest request, HttpServletResponse response) {
		logger.info(
				"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/goods  商品列表----------接口开始*_*_*_*_*_*_*_*_*_*");
		DealerAccount account = DealerUtils.getDealerAccount();
		// 如果无此账户
		if (account == null) {
			logger.info(
					"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/goods  账户异常,或账户不存在*_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("账户异常,或账户不存在");
		}
		// 查询此店铺信息
		Store store = storeService.selectByDealerId(account.getDealerId());
		if (store == null) {
			logger.info(
					"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/goods  未查到此店铺或店铺信息异常,请重新登录或联系管理员*_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("未查到此店铺或店铺信息异常,请重新登录或联系管理员");
		}
		goods.setState("0");
		goods.setStoreId(store.getId());
		Page<Goods> pageGoods = goodsService.findNoPublishPage(new Page<Goods>(request, response), goods);
		logger.info("*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/goods  接口调用结束*_*_*_*_*_*_*_*_*_*");
		return AjaxJson.layuiTable(pageGoods);
	}

	/**
	 * 商品详情
	 *
	 * @return
	 */
	@ApiOperation(notes = "detail", httpMethod = "GET", value = "商品详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "code", value = "商品编码", required = true, paramType = "query", dataType = "String") })
	@RequestMapping("/detail")
	public AjaxJson detail(String code) {
		logger.info("*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/detail  商品详情----------接口开始 code=" + code
				+ "*_*_*_*_*_*_*_*_*_*");
		/*DealerAccount account = DealerUtils.getDealerAccount();
		// 如果无此账户
		if (account == null) {
			logger.info(
					"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/detail  账户异常,或账户不存在*_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("账户异常,或账户不存在");
		}*/

		Goods goods = null;
		try {
			if (StringUtils.isEmpty(code)) {
				// 参数chenck
				logger.info("*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/detail  必填参数不能为空*_*_*_*_*_*_*_*_*_*");
				return AjaxJson.fail("必填参数不能为空");
			}
			// 商品基本信息
			goods = goodsService.findByCode(code);
			// 商品扩展情况
			GoodsExt goodsExt = goodsExtService.findUniqueByProperty("code", code);
			// 查询产品分类
			GoodsCategory goodsCategory = goodsCategoryService.get(goods.getCategoryId());
			// 查询品牌
			Brand brand = brandService.get(goods.getBrand());
			// 将产品分类放入
			goods.setCat(goodsCategory);
			goods.setGoodsExt(goodsExt);
			// 放入品牌信息
			goods.setBrandName(brand.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/goods  接口调用结束*_*_*_*_*_*_*_*_*_*");
		return AjaxJson.ok(goods);
	}

	/**
	 * 商品父级分类
	 *
	 * @return
	 */
	@ApiOperation(notes = "categoryList", httpMethod = "GET", value = "商品分类(id为空则是查找一级分类,如果不为空则查询其子类)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "分类", required = false, paramType = "query", dataType = "String") })
	@RequestMapping("/categoryList")
	public AjaxJson categoryList(GoodsCategory g) {
		logger.info(
				"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/categoryList  商品父级分类----------商品父级分类接口开始 *_*_*_*_*_*_*_*_*_*");
		GoodsCategory goodsCategory = new GoodsCategory();
		if (null == g.getId()) {
			// 如果为空则查询父类分类
			g.setId("0");
			logger.info(
					"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/goods  商品父级分类----------查询父级分类*_*_*_*_*_*_*_*_*_*");
		}
		goodsCategory.setParent(g);
		List<GoodsCategory> list = goodsCategoryService.findList(goodsCategory);
		logger.info(
				"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/goods  商品父级分类----------商品父级分类接口结束 *_*_*_*_*_*_*_*_*_*");
		return AjaxJson.layuiTable(list);
	}

	/**
	 * 商品发布 并 静态化处理
	 *
	 * @return
	 */
	@ApiOperation(notes = "publishProduct", httpMethod = "POST", value = "商品发布(表单提交需要嵌套,例子在index.jsp),如果带着id则是更新", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "categoryPid", value = "产品大类", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "categoryId", value = "产品子类", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "code", value = "产品编码", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "marketPrice", value = "市场价", required = true, paramType = "form", dataType = "Double"),
			@ApiImplicitParam(name = "price", value = "卖价", required = true, paramType = "form", dataType = "Double"),
			@ApiImplicitParam(name = "stock", value = "库存", required = false, paramType = "form", dataType = "Integer"),
			@ApiImplicitParam(name = "stockWarning", value = "库存预警", required = false, paramType = "form", dataType = "Integer"),
			@ApiImplicitParam(name = "isRecommend", value = "是否推荐", required = true, paramType = "form", dataType = "Char"),
			@ApiImplicitParam(name = "isPromotion", value = "是否促销", required = true, paramType = "form", dataType = "Char") })
	@RequestMapping("/publishProduct")
	public AjaxJson publishProduct(StoreGoods storeGoods, HttpServletRequest request) {
		logger.info(
				"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/publishProduct  商品发布----------接口开始 *_*_*_*_*_*_*_*_*_*");
		DealerAccount account = DealerUtils.getDealerAccount();
		GoodsCategory category = null;
		Goods goods = null;
		String htmlPath = "";
		Store store = null;
		try {
			// 如果无此账户
			if (account == null) {
				logger.info(
						"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/publishProduct  商品发布----------账户异常,或账户不存在 *_*_*_*_*_*_*_*_*_*");
				return AjaxJson.fail("账户异常,或账户不存在");
			}
			goods = goodsService.findByCode(storeGoods.getCode());
			if (goods == null) {
				return AjaxJson.fail("发布商品失败，请联系管理员，查看后台的商品！");
			}
			category = goodsCategoryService.findUniqueByProperty("id", storeGoods.getCategoryPid());
			if (category == null) {
				return AjaxJson.fail("商品发布失败，请联系管理员");
			}
			// 查询此店铺信息
			store = storeService.selectByDealerId(account.getDealerId());
			if (store == null) {
				logger.info(
						"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/publishProduct  商品发布----------未查到此店铺或店铺信息异常,请重新登录或联系管理员 *_*_*_*_*_*_*_*_*_*");
				return AjaxJson.fail("未查到此店铺或店铺信息异常,请重新登录或联系管理员");
			}
		} catch (Exception e) {
			e.printStackTrace();
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
				storeGoodsService.updateStoreGoods(storeGoods, request, category, goods, CmsUtils.getTemplateWholePath(templatePath),
						storeGoods.getHtmlpath());
			} else {
				// 保存
				logger.info(
						"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/publishProduct  商品发布----------保存 *_*_*_*_*_*_*_*_*_*");
				storeGoods.setCreateById(account.getId());
				storeGoods.setCreateDate(new Date());
				// 获取最大索引号
				Integer num = storeGoodsService.selectMaxNum();
				if(null == num){
					num=0;
				}
				storeGoods.setHtmlIndexNum(num+1);
				CmsConfig cmsConfig = CmsUtils.getCmsConfig();
				// 获取静态化的路径
				htmlPath = Htmlpath.goodsPath(cmsConfig, category, storeGoods);
				storeGoods.setHtmlpath(htmlPath);
				storeGoodsService.save(storeGoods, request, category, goods, CmsUtils.getTemplateWholePath(templatePath), htmlPath);
				//判断是否是自营店铺发布到首页 严选 或者是 品牌专区
				if("0".equals(account.getDealerId())) {//判断自营店铺
					Map<String, Object> data = getData(request);
					if (StringUtils.isNotBlank(storeGoods.getIsToIndexYanxuan())
							&& "1".equals(storeGoods.getIsToIndexYanxuan()) ||
							StringUtils.isNotBlank(storeGoods.getIsToIndexBrand())
									&& "1".equals(storeGoods.getIsToIndexBrand())
					) {
						//静态化首页

						htmlService.htmlIndex(data);

					}
					if(StringUtils.isNotBlank(storeGoods.getIsToIndexZhengcai())){//政采栏目 静态化
					 Category cate =categoryService.get("42c684ac71164e119c0499fe1857306a");
						htmlService.htmlCategory(data, cate, 1);
					}
					if(StringUtils.isNotBlank(storeGoods.getIsToIndexYiqing())){//疫情专区 静态化
						Category cate =categoryService.get("42c684ac71164e119c0499fe1857306a");
						htmlService.htmlCategory(data, cate, 1);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(
					"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/publishProduct  商品发布----------发布产品失败,原因: "
							+ e.getMessage() + " *_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("发布产品失败,原因: " + e.getMessage());
		}
		logger.info(
				"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/publishProduct  商品发布----------接口结束 *_*_*_*_*_*_*_*_*_*");
		logger.info(
				"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/publishProduct  商品发布----------静态化商品详情接口开始 *_*_*_*_*_*_*_*_*_*");

		return AjaxJson.ok("发布产品成功，");
	}

	/**
	 * 商品上架
	 *
	 * @return
	 */
	@ApiOperation(notes = "grounding", httpMethod = "GET", value = "商品上架")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "storeGoodsId", value = "店铺商品id", required = true, paramType = "query", dataType = "String") })
	@RequestMapping("/grounding")
	public AjaxJson grounding(String storeGoodsId,HttpServletRequest request) {
		logger.info(
				"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/grounding  商品上架----------接口开始 *_*_*_*_*_*_*_*_*_*");
		DealerAccount account = DealerUtils.getDealerAccount();
		// 如果无此账户
		if (account == null) {
			logger.info(
					"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/grounding  商品上架----------账户异常 *_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("账户异常,请重新登录或联系管理员");
		}
		// 参数check
		if (StringUtils.isEmpty(storeGoodsId)) {
			logger.info(
					"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/grounding  商品上架----------参数异常,必填参数storeGoodsId不能为空 *_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("参数异常,必填参数不能为空");
		}
		Dealer dealer = dealerService.get(account.getDealerId());

		if (null != dealer) {
			// 根据经销商编码和名称查询
			StoreGoods storeGoods = storeGoodsService.findUniqueByProperty("id", storeGoodsId);
			// 如果是自营店则不需要进行销售样表验证
			if ("0".equals(account.getDealerId())) {
				// 查询此店铺信息
				Store store = storeService.findUniqueByProperty("id", dealer.getId());
				StoreGoods sg = storeGoodsService.selectByCodeOrStoreId(store.getId(), storeGoods.getCode());
				sg.setIsShelf(Global.YES);
				sg.setShelfTime(new Date());
				int temp = storeGoodsService.update(sg,request,CmsUtils.getTemplateWholePath(templatePath));
				if (temp > 0) {
					// 可以上架
					logger.info(
							"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/grounding  商品上架----------上架成功 *_*_*_*_*_*_*_*_*_*");
					return AjaxJson.ok("上架成功");

				} else {
					logger.info(
							"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/grounding  商品上架----------上架失败 *_*_*_*_*_*_*_*_*_*");
					return AjaxJson.fail("上架失败,请联系管理员");
				}
			}
			// 此段是注释掉上架需要经过产品中心(到时候直接那注释打开然后把上面删除)
			List<MDMCustomer> list = WsClientList.getMDMCustomersList(dealer.getCompanyCode(), "");
			if (list.size() == 0) {
				logger.info(
						"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/grounding  商品上架----------供应商信息异常,上架失败 *_*_*_*_*_*_*_*_*_*");
				return AjaxJson.fail("供应商信息异常,上架失败");
			}
			// 根据code查询销售样表
			BatchsaleTemplate batchsaleTemplate = WsClientList.getBatchsaleTemplate(storeGoods.getCode());
			if ("S".equals(batchsaleTemplate.getRetCode()) && null != list) {
				if (checkBatchsaleTemplate(batchsaleTemplate, list)) {
					// 查询此店铺信息
					Store store = storeService.findUniqueByProperty("id", dealer.getId());
					StoreGoods sg = storeGoodsService.selectByCodeOrStoreId(store.getId(), storeGoods.getCode());
					sg.setIsShelf(Global.YES);
					sg.setShelfTime(new Date());
					int temp = storeGoodsService.update(sg,request,CmsUtils.getTemplateWholePath(templatePath));
					if (temp > 0) {
						// 可以上架
						logger.info(
								"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/grounding  商品上架----------上架成功 *_*_*_*_*_*_*_*_*_*");
						return AjaxJson.ok("上架成功");

					} else {
						logger.info(
								"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/grounding  商品上架----------上架失败 *_*_*_*_*_*_*_*_*_*");
						return AjaxJson.fail("上架失败,请联系管理员");
					}

				} else {
					logger.info(
							"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/grounding  商品上架----------商品不符合销售样表,不允许上架 *_*_*_*_*_*_*_*_*_*");
					return AjaxJson.fail("商品不符合销售样表,不允许上架");
				}
			} else {
				return AjaxJson.fail("数据异常,请联系管理员");
			}
		}
		logger.info(
				"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/grounding  商品上架----------接口结束 *_*_*_*_*_*_*_*_*_*");
		return AjaxJson.ok();
	}

	/**
	 * 商品下架
	 *
	 * @return
	 */
	@ApiOperation(notes = "undercarriage", httpMethod = "GET", value = "商品下架(可进行批量下架，编码由逗号隔开)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "storeGoodsId", value = "店铺商品id", required = true, paramType = "query", dataType = "String") })
	@RequestMapping("/undercarriage")
	public AjaxJson undercarriage(String storeGoodsId, HttpServletRequest request) {
		logger.info(
				"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/undercarriage  商品下架----------接口开始 *_*_*_*_*_*_*_*_*_*");
		DealerAccount account = DealerUtils.getDealerAccount();
		// 如果无此账户
		if (account == null) {
			logger.info(
					"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/undercarriage  商品下架----------账户异常,请重新登录或联系管理员 *_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("账户异常,请重新登录或联系管理员");
		}
		// 参数check
		if (StringUtils.isEmpty(storeGoodsId)) {
			logger.info(
					"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/undercarriage  商品下架----------参数异常,必填参数storeGoodsId不能为空 *_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("参数异常,必填参数不能为空");
		}
		// 查询此店铺信息
		Store store = storeService.selectByDealerId(account.getDealerId());
		if (store == null) {
			logger.info(
					"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/undercarriage  商品下架----------未查到此店铺或店铺信息异常,请重新登录或联系管理员 *_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("未查到此店铺或店铺信息异常,请重新登录或联系管理员");
		}
		// 拆分code编码
		String[] storeGoodsIdArray = storeGoodsId.split(",");
		try {
			int num = storeGoodsService.batchUndercarriage(storeGoodsIdArray,request,CmsUtils.getTemplateWholePath(templatePath));
			if (num == storeGoodsIdArray.length) {
				logger.info("*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/undercarriage  商品下架----------成功下架"
						+ num + "件产品 *_*_*_*_*_*_*_*_*_*");
				//静态化


				return AjaxJson.ok("成功下架" + num + "件产品");
			} else {
				logger.info(
						"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/undercarriage  商品下架----------下架商品失败,请联系管理员 *_*_*_*_*_*_*_*_*_*");
				return AjaxJson.fail("下架商品失败,请联系管理员");
			}
		} catch (Exception e) {
			logger.info(
					"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/undercarriage  商品下架----------下架商品失败,失败原因: "
							+ e.getMessage() + "*_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("下架商品失败,失败原因: " + e.getMessage());
		}
	}

	/**
	 * 获取发布产品的详情
	 *
	 * @return
	 */
	@ApiOperation(notes = "publishProductDetail", httpMethod = "POST", value = "发布产品详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "storeGoodsId", value = "店铺商品id", required = true, paramType = "query", dataType = "String") })
	@RequestMapping("/publishProductDetail")
	public AjaxJson publishProductDetail(String storeGoodsId) {
		logger.info(
				"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/publishProductDetail  获取发布产品的详情----------获取发布产品的详情 *_*_*_*_*_*_*_*_*_*");
		DealerAccount account = DealerUtils.getDealerAccount();
		// 如果无此账户
		if (account == null) {
			return AjaxJson.fail("账户异常,请重新登录或联系管理员");
		}
		// 参数check
		if (StringUtils.isEmpty(storeGoodsId)) {
			return AjaxJson.fail("参数异常,必填参数不能为空");
		}
		// 查询此店铺信息
		Store store = storeService.selectByDealerId(account.getDealerId());
		if (store == null) {
			return AjaxJson.fail("未查到此店铺或店铺信息异常,请重新登录或联系管理员");
		}
		// 通过storeId和code查询店铺商品
		StoreGoods storeGoods = storeGoodsService.findUniqueByProperty("id", storeGoodsId);
		// 查询阶梯价格
		StorePriceGroup storePriceGroup = new StorePriceGroup();
		storePriceGroup.setCode(storeGoods.getCode());
		storePriceGroup.setStoreId(storeGoods.getStoreId());
		logger.info(
				"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/publishProductDetail  获取发布产品的详情----------code="
						+ storeGoods.getCode() + "storeId= " + storeGoods.getStoreId() + " *_*_*_*_*_*_*_*_*_*");
		List<StorePriceGroup> listStorePriceGroup = storePriceGroupService.findList(storePriceGroup);
		// 具体的采购商信息
		for (StorePriceGroup sg : listStorePriceGroup) {
			StoreGoodsPrice storeGoodsPrice = new StoreGoodsPrice();
			storeGoodsPrice.setStorePriceGroupId(sg.getId());
			List<StoreGoodsPrice> storeGoodsPriceList = storeGoodsPriceService.findList(storeGoodsPrice);
			for (StoreGoodsPrice sgpBean : storeGoodsPriceList) {
				Purchaser purchaser = purchaserService.findUniqueByProperty("id", sgpBean.getPurchaserId());
				if (purchaser != null) {
					sgpBean.setPurchaser(purchaser);
				}
			}
			sg.setStoreGoodsPriceList(storeGoodsPriceList);
		}
		storeGoods.setStorePriceGroupList(listStorePriceGroup);
		// 品牌相关信息
		Goods g = goodsService.findUniqueByProperty("code", storeGoods.getCode());
		// 查询品牌名称
		Brand b = brandService.findUniqueByProperty("id", g.getBrand());
		// 品牌信息
		storeGoods.setBrands(b);
		// 商品名称
		storeGoods.setGoodsName(g.getName());
		logger.info(
				"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/publishProductDetail  商品下架----------接口结束 *_*_*_*_*_*_*_*_*_*");
		return AjaxJson.ok(storeGoods);
	}

	/**
	 * 发布产品搜索
	 *
	 * @return
	 */
	@ApiOperation(notes = "storeGoodsSearch", httpMethod = "POST", value = "店铺产品搜索(列表与搜索调用一直接口)", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "code", value = "商品编码", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "goodsName", value = "商品名称", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "isShelf", value = "是否上架", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "categoryPid", value = "产品大类", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "categoryId", value = "产品子类", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "pageNo", value = "页码", required = false, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, paramType = "form", dataType = "int") })
	@RequestMapping("/storeGoodsSearch")
	public AjaxJson storeGoodsSearch(StoreGoods storeGoods, HttpServletRequest request, HttpServletResponse response) {
		logger.info(
				"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/storeGoodsSearch  发布产品搜索----------接口开始 *_*_*_*_*_*_*_*_*_*");
		/*DealerAccount account = DealerUtils.getDealerAccount();
		// 如果无此账户
		if (account == null) {
			logger.info(
					"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/storeGoodsSearch  发布产品搜索----------account账户异常,请重新登录或联系管理员 *_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("账户异常,请重新登录或联系管理员");
		}*/
		// 参数check
		if (StringUtils.isEmpty(storeGoods.getIsShelf())) {
			logger.info(
					"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/storeGoodsSearch  发布产品搜索----------参数异常,必填参数不能为空 *_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("参数异常,必填参数不能为空");
		}
		// 查询此店铺信息
		Store store = storeService.selectByDealerId("0");
		if (store == null) {
			logger.info(
					"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/storeGoodsSearch  发布产品搜索----------未查到此店铺或店铺信息异常,请重新登录或联系管理员 *_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("未查到此店铺或店铺信息异常,请重新登录或联系管理员");
		}
		storeGoods.setStoreId(store.getId());
		Page<StoreGoods> pageStoreGoods = storeGoodsService.findPage(new Page<StoreGoods>(request, response),
				storeGoods);
		logger.info(
				"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/storeGoodsSearch  发布产品搜索----------接口结束 *_*_*_*_*_*_*_*_*_*");
		return AjaxJson.layuiTable(pageStoreGoods);
	}

	/**
	 * 删除发布的商品
	 *
	 * @return
	 */
	@ApiOperation(notes = "delStoreGoods", httpMethod = "GET", value = "删除商品(可进行多删,code用逗号隔开)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "storeGoodsId", value = "店铺商品Id", required = true, paramType = "query", dataType = "String") })
	@RequestMapping("/delStoreGoods")
	public AjaxJson delStoreGoods(String storeGoodsId) {
		logger.info(
				"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/delStoreGoods  删除发布的商品----------接口开始 *_*_*_*_*_*_*_*_*_*");
		DealerAccount account = DealerUtils.getDealerAccount();
		// 如果无此账户
		if (account == null) {
			logger.info(
					"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/delStoreGoods  删除发布的商品----------account账户异常,请重新登录或联系管理员 *_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("账户异常,请重新登录或联系管理员");
		}
		// 参数check
		if (StringUtils.isEmpty(storeGoodsId)) {
			logger.info(
					"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/delStoreGoods  删除发布的商品----------storeGoodsId参数异常,必填参数不能为空 *_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("参数异常,必填参数不能为空");
		}
		int num = 0;
		// 查询此店铺信息
		Store store = storeService.selectByDealerId(account.getDealerId());
		if (store == null) {
			logger.info(
					"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/delStoreGoods  删除发布的商品----------未查到此店铺或店铺信息异常,请重新登录或联系管理员 *_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("未查到此店铺或店铺信息异常,请重新登录或联系管理员");
		}
		if (!"".equals(storeGoodsId)) {
			String storeGoodsIdArray[] = storeGoodsId.split(",");
			num = storeGoodsService.deleteStoreGoods(storeGoodsIdArray);
			if (num == storeGoodsIdArray.length) {
				logger.info(
						"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/delStoreGoods  删除发布的商品----------已成功删除"
								+ num + "件产品 *_*_*_*_*_*_*_*_*_*");
				return AjaxJson.ok("已成功删除" + num + "件产品");
			} else {
				logger.info(
						"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/delStoreGoods  删除发布的商品----------删除产品失败,请联系管理员 *_*_*_*_*_*_*_*_*_*");
				return AjaxJson.fail("删除产品失败,请联系管理员");
			}
		}
		logger.info(
				"*_*_*_*_*_*_*_*_*_* ApiDealerGoodsController  /api/store/delStoreGoods  删除发布的商品----------删除产品结束 *_*_*_*_*_*_*_*_*_*");
		return AjaxJson.ok("已成功删除" + num + "件产品");
	}

	/**
	 * 发布商品查重
	 *
	 * @return
	 */
	@ApiOperation(notes = "storeGoodsCheck", httpMethod = "GET", value = "发布商品查重")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "code", value = "商品编码", required = true, paramType = "query", dataType = "String"), })
	@RequestMapping("/storeGoodsCheck")
	public AjaxJson storeGoodsCheck(String code) {
		DealerAccount account = DealerUtils.getDealerAccount();
		// 如果无此账户
		if (account == null) {
			return AjaxJson.fail("账户异常,请重新登录或联系管理员");
		}
		// 查询此店铺信息
		Store store = storeService.selectByDealerId(account.getDealerId());
		if (store != null) {
			StoreGoods sg = storeGoodsService.selectByCodeOrStoreId(store.getId(), code);
			if (sg != null) {
				return AjaxJson.fail("此商品已存在,请勿重复发布");
			} else {
				return AjaxJson.ok();
			}
		} else {
			return AjaxJson.fail("店铺信息异常,请重新登录或联系管理员");
		}

	}

	/**
	 * 商品是否推荐切换
	 *
	 * @return
	 */
	@ApiOperation(notes = "isRecommendTab", httpMethod = "GET", value = "商品推荐切换(调取接口则反转状态)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "storeGoodsId", value = "店铺商品Id", required = true, paramType = "query", dataType = "String"), })
	@RequestMapping("/isRecommendTab")
	public AjaxJson isRecommendTab(String storeGoodsId,HttpServletRequest request) {
		DealerAccount account = DealerUtils.getDealerAccount();
		// 如果无此账户
		if (account == null) {
			return AjaxJson.fail("账户异常,请重新登录或联系管理员");
		}
		// 参数check
		if (StringUtils.isEmpty(storeGoodsId)) {
			return AjaxJson.fail("参数异常,必填参数不能为空");
		}
		// 查询此店铺信息
		Store store = storeService.selectByDealerId(account.getDealerId());
		if (store == null) {
			return AjaxJson.fail("未查到此店铺或店铺信息异常,请重新登录或联系管理员");
		}
		String flag = Global.YES;
		StoreGoods sg = storeGoodsService.findUniqueByProperty("id", storeGoodsId);
		String recommend = sg.getIsRecommend();
		// 切换状态
		if (Global.YES.equals(recommend)) {
			sg.setIsRecommend(Global.NO);
			flag = Global.NO;
		} else {
			sg.setIsRecommend(Global.YES);
			flag = Global.YES;
		}
		int i = storeGoodsService.update(sg,request,CmsUtils.getTemplateWholePath(templatePath));
		if (i == 0) {
			return AjaxJson.fail("操作异常,请重新登录或联系管理员");
		}
		return AjaxJson.ok("操作成功", flag);
	}

	/**
	 * 商品促销切换
	 *
	 * @return
	 */
	@ApiOperation(notes = "isPromotionTab", httpMethod = "GET", value = "商品促销切换(调取接口则反转状态)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "storeGoodsId", value = "店铺商品Id", required = true, paramType = "query", dataType = "String"), })
	@RequestMapping("/isPromotionTab")
	public AjaxJson isPromotionTab(String storeGoodsId,HttpServletRequest request) {
		DealerAccount account = DealerUtils.getDealerAccount();
		// 如果无此账户
		if (account == null) {
			return AjaxJson.fail("账户异常,请重新登录或联系管理员");
		}
		// 参数check
		if (StringUtils.isEmpty(storeGoodsId)) {
			return AjaxJson.fail("参数异常,必填参数不能为空");
		}
		// 查询此店铺信息
		Store store = storeService.selectByDealerId(account.getDealerId());
		if (store == null) {
			return AjaxJson.fail("未查到此店铺或店铺信息异常,请重新登录或联系管理员");
		}
		String flag = Global.YES;
		StoreGoods sg = storeGoodsService.findUniqueByProperty("id", storeGoodsId);
		String promotion = sg.getIsPromotion();
		// 切换状态
		if (Global.YES.equals(promotion)) {
			sg.setIsPromotion(Global.NO);
			flag = Global.NO;
		} else {
			sg.setIsPromotion(Global.YES);
			flag = Global.YES;
		}
		int i = storeGoodsService.update(sg,request,CmsUtils.getTemplateWholePath(templatePath));
		if (i == 0) {
			return AjaxJson.fail("操作异常,请重新登录或联系管理员");
		}
		return AjaxJson.ok("操作成功", flag);
	}

	/**
	 * @Title: isNewTab
	 * @Description: TODO 商品新品切换
	 * @param storeGoodsId
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年10月10日下午4:45:40
	 */
	@ApiOperation(notes = "isNewTab", httpMethod = "GET", value = "商品新品切换(调取接口则反转状态)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "storeGoodsId", value = "店铺商品Id", required = true, paramType = "query", dataType = "String"), })
	@RequestMapping("/isNewTab")
	public AjaxJson isNew(String storeGoodsId,HttpServletRequest request) {
		DealerAccount account = DealerUtils.getDealerAccount();
		// 如果无此账户
		if (account == null) {
			return AjaxJson.fail("账户异常,请重新登录或联系管理员");
		}
		// 参数check
		if (StringUtils.isEmpty(storeGoodsId)) {
			return AjaxJson.fail("参数异常,必填参数不能为空");
		}
		// 查询此店铺信息
		Store store = storeService.selectByDealerId(account.getDealerId());
		if (store == null) {
			return AjaxJson.fail("未查到此店铺或店铺信息异常,请重新登录或联系管理员");
		}
		String flag = Global.YES;
		StoreGoods sg = storeGoodsService.findUniqueByProperty("id", storeGoodsId);
		String isNew = sg.getIsNew();
		// 切换状态
		if (Global.YES.equals(isNew)) {
			sg.setIsNew(Global.NO);
			flag = Global.NO;
		} else {
			sg.setIsNew(Global.YES);
			flag = Global.YES;
		}
		int i = storeGoodsService.update(sg,request,CmsUtils.getTemplateWholePath(templatePath));
		if (i == 0) {
			return AjaxJson.fail("操作异常,请重新登录或联系管理员");
		}
		return AjaxJson.ok("操作成功", flag);
	}

	/**
	 * @Title: isTopTab
	 * @Description: TODO 置顶切换
	 * @param storeGoodsId
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年10月10日下午4:49:59
	 */
	@ApiOperation(notes = "isTopTab", httpMethod = "GET", value = "商品置顶切换(调取接口则反转状态)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "storeGoodsId", value = "店铺商品Id", required = true, paramType = "query", dataType = "String"), })
	@RequestMapping("/isTopTab")
	public AjaxJson isTopTab(String storeGoodsId,HttpServletRequest request) {
		DealerAccount account = DealerUtils.getDealerAccount();
		// 如果无此账户
		if (account == null) {
			return AjaxJson.fail("账户异常,请重新登录或联系管理员");
		}
		// 参数check
		if (StringUtils.isEmpty(storeGoodsId)) {
			return AjaxJson.fail("参数异常,必填参数不能为空");
		}
		// 查询此店铺信息
		Store store = storeService.selectByDealerId(account.getDealerId());
		if (store == null) {
			return AjaxJson.fail("未查到此店铺或店铺信息异常,请重新登录或联系管理员");
		}
		String flag = Global.YES;
		StoreGoods sg = storeGoodsService.findUniqueByProperty("id", storeGoodsId);
		String isTop = sg.getIsTop();
		// 切换状态
		if (Global.YES.equals(isTop)) {
			sg.setIsTop(Global.NO);
			flag = Global.NO;
		} else {
			sg.setIsTop(Global.YES);
			flag = Global.YES;
		}
		int i = storeGoodsService.update(sg,request,CmsUtils.getTemplateWholePath(templatePath));
		if (i == 0) {
			return AjaxJson.fail("操作异常,请重新登录或联系管理员");
		}
		return AjaxJson.ok("操作成功", flag);
	}

	@ApiOperation(notes = "isHotSaleTab", httpMethod = "GET", value = "商品热卖切换(调取接口则反转状态)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "storeGoodsId", value = "店铺商品Id", required = true, paramType = "query", dataType = "String"), })
	@RequestMapping("/isHotSaleTab")
	public AjaxJson isHotSaleTab(String storeGoodsId,HttpServletRequest request) {
		DealerAccount account = DealerUtils.getDealerAccount();
		// 如果无此账户
		if (account == null) {
			return AjaxJson.fail("账户异常,请重新登录或联系管理员");
		}
		// 参数check
		if (StringUtils.isEmpty(storeGoodsId)) {
			return AjaxJson.fail("参数异常,必填参数不能为空");
		}
		// 查询此店铺信息
		Store store = storeService.selectByDealerId(account.getDealerId());
		if (store == null) {
			return AjaxJson.fail("未查到此店铺或店铺信息异常,请重新登录或联系管理员");
		}
		String flag = Global.YES;
		StoreGoods sg = storeGoodsService.findUniqueByProperty("id", storeGoodsId);
		String isHotSale = sg.getIsHotSale();
		// 切换状态
		if (Global.YES.equals(isHotSale)) {
			sg.setIsHotSale(Global.NO);
			flag = Global.NO;
		} else {
			sg.setIsHotSale(Global.YES);
			flag = Global.YES;
		}
		int i = storeGoodsService.update(sg,request,CmsUtils.getTemplateWholePath(templatePath));
		if (i == 0) {
			return AjaxJson.fail("操作异常,请重新登录或联系管理员");
		}
		return AjaxJson.ok("操作成功", flag);
	}

	/**
	 * 销售样表check(如果允许上架必须满足1.销售组织(中心工贸) 2.大渠道 3.小渠道 4.客户号 (也就是经销商编码))
	 *
	 * @return
	 */
	public static boolean checkBatchsaleTemplate(BatchsaleTemplate batchsaleTemplate, List<MDMCustomer> list) {
		// 因前期返回list后期调整基础方法未改动
		MDMCustomer m = list.get(0);
		// 中心工贸
		String orgid = m.getOrgName();
		// 大渠道
		String parent_value_low = m.getParent_value_low();
		// 小渠道
		String kind = m.getKind();
		// 经销商编码
		String cusCode = m.getCustcode();
		// 定义上架与否
		boolean flag = false;
		if (orgid == null || parent_value_low == null || kind == null || cusCode == null) {
			return flag;
		}
		boolean organizationFlag = false;
		boolean channelFlag = false;
		boolean channel2Flag = false;
		boolean codeFlag = false;
		List<BatchsaleTemplateItem> batchsaleTemplateItemList = batchsaleTemplate.getBatchsaleTemplateList();
		if (batchsaleTemplateItemList.size() > 0) {
			for (BatchsaleTemplateItem batchsaleTemplateItem : batchsaleTemplateItemList) {
				// 销售组织也就是中心工贸
				String sales_organization = batchsaleTemplateItem.getSales_organization();
				// 大渠道
				String sales_channel = batchsaleTemplateItem.getSales_channel();
				// 小渠道
				String sales_channel2 = batchsaleTemplateItem.getSales_channel2();
				// 客户号
				String customer_code = batchsaleTemplateItem.getCustomer_code();

				// 中心匹配判断
				if ("*".equals(sales_organization)) {
					organizationFlag = true;
				} else {
					// 是否包含此中心工贸
					if (orgid.indexOf(sales_organization) != -1) {
						organizationFlag = true;
					} else {
						organizationFlag = false;
					}
				}
				// 大渠道匹配判断
				if ("*".equals(sales_channel)) {
					channelFlag = true;
				} else {
					// 是否是此大渠道
					if (parent_value_low.equals(sales_channel)) {
						channelFlag = true;
					} else {
						channelFlag = false;
					}
				}
				// 小渠道匹配判断
				if ("*".equals(sales_channel2)) {
					channel2Flag = true;
				} else {
					// 是否是小渠道
					if (kind.equals(sales_channel2)) {
						channel2Flag = true;
					} else {
						channel2Flag = false;
					}
				}
				// 经销商编码匹配判断
				if ("*".equals(customer_code)) {
					codeFlag = true;
				} else {
					if (customer_code.equals(cusCode)) {
						codeFlag = true;
					} else {
						codeFlag = false;
					}
				}
				if (organizationFlag && channelFlag && channel2Flag && codeFlag) {
					return true;
				}
			}
		}
		return flag;
	}

	/**
	 * @Title: guessYouWant
	 * @Description: TODO 推荐想要
	 * @param select1
	 * @param select2
	 * @param select3
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年10月28日下午3:21:21
	 */
	@RequestMapping(value="/guessWant",method=RequestMethod.POST)
	public AjaxJson guessYouWant(String select1, String select2, String select3) {
		AjaxJson ajaxJson = new AjaxJson();
		String type = "";
		if (StringUtils.isNotEmpty(select1) && StringUtils.isNotEmpty(select2) || StringUtils.isNotEmpty(select3)) {
			// 如果是选教育-洗衣机
			if ("教育".equals(select1) && "洗衣机".equals(select2)) {
				type = "jx";
			} else if ("高端".equals(select3)) {
				if ("冰箱".equals(select2)) {
					type = "gdbx";
				}
				if ("空调".equals(select2)) {
					type = "gdkt";
				}
				if ("洗衣机".equals(select2)) {
					type = "gdxyj";
				}
				if ("彩电".equals(select2)) {
					type = "gdds";
				}
				if ("厨电".equals(select2)) {
					type = "gdcd";
				}
				if ("热水器".equals(select2)) {
					type = "gdrsq";
				}
			} else if ("冰箱".equals(select2)) {
				type = "bx";
			} else if ("空调".equals(select2)) {
				type = "kt";
			} else if ("洗衣机".equals(select2)) {
				type = "xyj";
			} else if ("彩电".equals(select2)) {
				type = "ds";
			} else if ("厨电".equals(select2)) {
				type = "cd";
			} else if ("热水器".equals(select2)) {
				type = "rsq";
			}

			List<StoreGoods> list = storeGoodsService.guess(type);
			if (list!=null && list.size() > 0) {
				ajaxJson.setSuccess(true);
				ajaxJson.setResult(list);
				/*推荐成功，修改推荐人数*/
				guessRecommendService.updateRecommendNum();

			} else {
				String msg="很遗憾，暂时没有为您推荐的商品。";
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg(msg);
			}

		} else {// 未选择， 为空
			String msg = "请选择您想筛选的产品";
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg(msg);
		}

		return ajaxJson;

	}

}
