package com.jhmis.api.app;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haier.webservices.client.shortmsg.SendMsgApi;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.wechat.entity.SkMv;
import com.jhmis.modules.wechat.entity.SkMvComment;
import com.jhmis.modules.wechat.entity.SkMvDetails;
import com.jhmis.modules.wechat.entity.SkMvLike;
import com.jhmis.modules.wechat.service.SkMvCommentService;
import com.jhmis.modules.wechat.service.SkMvDetailsService;
import com.jhmis.modules.wechat.service.SkMvLikeService;
import com.jhmis.modules.wechat.service.SkMvService;

@RestController
@RequestMapping(value = "/api/app/skmv")
public class ApiAppMvController extends BaseController {

	@Autowired
	private SkMvService skMvService;
	@Autowired
	private SkMvDetailsService skMvDetailsService;
	@Autowired
	private SkMvCommentService skMvCommentService;
	@Autowired
	private SkMvLikeService skMvLikeService;

	/**
	 * @Title: findMV
	 * @Description: TODO 查询所有的视频
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年5月27日下午5:00:23
	 */
	@RequestMapping("/find")
	public AjaxJson findMV() {
		List<SkMv> list = skMvService.findAll();
		return AjaxJson.ok(list);
	}

	/** 
	  * @Title: findMVById 
	  * @Description: TODO  查看视频详情
	  * @param id
	  * @return 
	  * @return AjaxJson
	  * @author tc
	  * @date 2019年5月29日下午7:35:28
	  */
	@RequestMapping("/findDetailsById")
	public AjaxJson findMVById(@RequestParam(value = "id", required = true) String id) {
		 Map<String,Object> map = new HashMap<String,Object>();
		 SkMvLike sml=null;
		List<SkMv> list = skMvService.findById(id);
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount != null) {
			sml = skMvLikeService.getByMidAndUid(id, purchaserAccount.getId());
		}
		map.put("skMv", list.get(0));
		map.put("sml", sml);
		return AjaxJson.ok(map);
	}   
	/** 
	  * @Title: findCommentById 
	  * @Description: TODO  查询视频评论
	  * @param id
	  * @return 
	  * @return AjaxJson
	  * @author tc
	  * @date 2019年5月29日下午7:35:15
	  */
	@RequestMapping("/findCommentById")
	public AjaxJson findCommentById(@RequestParam(value = "id", required = true) String id) {

		
		
		 List<SkMvComment> listcomment=skMvCommentService.findListById(id);
    
		
		return AjaxJson.ok(listcomment);
	}  
	
	/**
	 * @Title: addVisits
	 * @Description: TODO 浏览量+1
	 * @param id
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年5月27日下午5:27:46
	 */
	@RequestMapping(value = "/addVisits")
	public AjaxJson addVisits(@RequestParam(value = "id", required = true) String id) {
		try {

			logger.info("视频的id" + id);
			SkMvDetails skMvDetails = skMvDetailsService.getSkMvById(id);
			logger.info("skMvDetails" + skMvDetails);
			int count = Integer.parseInt(skMvDetails.getSkMvVisits());
			logger.info("count" + count);
			String i = String.valueOf(count + 1);
			skMvDetails.setSkMvVisits(i);
			skMvDetailsService.updateVisits(i, skMvDetails.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AjaxJson.ok("ok");

	}

	/**
	 * @Title: addComment
	 * @Description: TODO 添加评论
	 * @param id
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年5月28日上午9:28:04
	 */
	@RequestMapping(value = "/addComment")
	public AjaxJson addComment(@RequestParam(value = "id", required = true) String id ,
			@RequestParam(value = "text", required = true)String text) {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}

		try {
			logger.info("视频的id" + id);
			SkMvDetails skMvDetails = skMvDetailsService.getSkMvById(id);
			logger.info("skMvDetails" + skMvDetails);
			SkMvComment skMvComment = new SkMvComment();
			skMvComment.setSkMvId(id);
			skMvComment.setSkMvName(skMvDetails.getSkMvName());
			skMvComment.setUserId(purchaserAccount.getId());
			skMvComment.setUserName(purchaserAccount.getNickName());
			skMvComment.setSkMvComment(text);
			skMvComment.setSkMvCommentTime(new Date());
			skMvComment.setSkMvIspass("0");
			skMvComment.setImage(purchaserAccount.getAvatar());
			logger.info("保存评论前");
			skMvCommentService.save(skMvComment);
			logger.info("保存评论后");
			int count = Integer.parseInt(skMvDetails.getSkMvCommentCount().trim());
			logger.info("comment  count" + count);
			String i = String.valueOf(count + 1);
			skMvDetails.setSkMvCommentCount(i);
			skMvDetailsService.updateComment(i, skMvDetails.getId());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return AjaxJson.ok("ok");
	}

	/**
	 * @Title: addLike
	 * @Description: TODO 增加喜欢 或者取消喜欢
	 * @param id
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年5月28日上午9:28:16
	 */
	@RequestMapping(value = "/addLike")
	public AjaxJson addLike(@RequestParam(value = "id", required = true) String id) {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}

		try {

			SkMvLike sml = skMvLikeService.getByMidAndUid(id, purchaserAccount.getId());
			if (sml == null) {
				logger.info("视频的id" + id);
				SkMvDetails skMvDetails = skMvDetailsService.getSkMvById(id);
				logger.info("skMvDetails" + skMvDetails);
				SkMvLike skMvLike = new SkMvLike();
				skMvLike.setSkMvId(id);
				skMvLike.setSkMvName(skMvDetails.getSkMvName());
				skMvLike.setUserId(purchaserAccount.getId());
				skMvLike.setUserName(purchaserAccount.getNickName());
				skMvLike.setSkMvLikeTime(new Date());
				skMvLike.setSkMvIslike("1");
				logger.info("保存喜欢前");
				skMvLikeService.save(skMvLike);
				logger.info("保存喜欢后");
				int count = Integer.parseInt(skMvDetails.getSkMvLike().trim());
				logger.info("comment  count" + count);
				String i = String.valueOf(count + 1);
				skMvDetails.setSkMvLike(i);
				skMvDetailsService.updateLike(i, skMvDetails.getId());
			}
			if (sml != null && sml.getSkMvIslike().equals("1")) {
				logger.info("已经点赞了 准备删除 点赞前");
				skMvLikeService.deleteByMidAndUid(id, purchaserAccount.getId());
				logger.info("已经点赞了，准备删除点赞后");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AjaxJson.ok("ok");
	}

	
	
	
}
