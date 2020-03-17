/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.service.shopmsgorder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jhmis.common.Enum.ProcessCode;
import com.jhmis.common.Enum.ShopMsgCode;
import com.jhmis.common.Exception.ShopMsgException;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.core.service.UploadService;
import com.jhmis.modules.process.entity.dispatcher.CmsOperlogs;
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;
import com.jhmis.modules.process.entity.shopmsgorder.ShopMsgCustcodeOrder;
import com.jhmis.modules.process.entity.shopmsgstatus.ShopMsgStatus;
import com.jhmis.modules.process.mapper.shopmsgorder.ShopMsgCustcodeOrderMapper;
import com.jhmis.modules.process.service.dispatcher.CmsOperlogsService;
import com.jhmis.modules.process.service.shopmsg.ShopMsgService;
import com.jhmis.modules.process.service.shopmsgstatus.ShopMsgStatusService;
import com.jhmis.modules.sys.entity.AttInfo;
import com.jhmis.modules.sys.entity.UploadResult;

/**
 * 经销商订单表Service
 * @author hdx
 * @version 2019-09-26
 */
@Service
@Transactional(readOnly = true)
public class ShopMsgCustcodeOrderService extends CrudService<ShopMsgCustcodeOrderMapper, ShopMsgCustcodeOrder> {

	@Autowired
	private CmsOperlogsService cmsOperLogsService;
	@Autowired
	private ShopMsgStatusService shopMsgStatusService;
	@Autowired
	private ShopMsgService shopMsgService;
	@Autowired
	private UploadService uploadService;
	public ShopMsgCustcodeOrder get(String id) {
		return super.get(id);
	}
	
	public List<ShopMsgCustcodeOrder> findList(ShopMsgCustcodeOrder shopMsgCustcodeOrder) {
		return super.findList(shopMsgCustcodeOrder);
	}

	public List<ShopMsgCustcodeOrder> findListTask(ShopMsgCustcodeOrder shopMsgCustcodeOrder) {
		return mapper.findList(shopMsgCustcodeOrder);
	}
	
	/*public Page<ShopMsgCustcodeOrder> findPage(Page<ShopMsgCustcodeOrder> page, ShopMsgCustcodeOrder shopMsgCustcodeOrder) {
		return super.findPage(page, shopMsgCustcodeOrder);
	}*/
	
	@Transactional(readOnly = false)
	public void save(ShopMsgCustcodeOrder shopMsgCustcodeOrder) {
		super.save(shopMsgCustcodeOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopMsgCustcodeOrder shopMsgCustcodeOrder) {
		super.delete(shopMsgCustcodeOrder);
	}

	public List<Integer> findbycustcodeAndStatus(String companyCode) {
		// TODO Auto-generated method stub
		return mapper.findbycustcodeAndStatus(companyCode);
	}
	
	/** 
	  * @Title: deliver 
	  * @Description: TODO  app物流出库
	  * @param orderid
	  * @param delivernum
	  * @return
	  * @throws ShopMsgException 
	  * @return Map<String,Object>
	  * @author tc
	  * @date 2019年9月29日上午11:44:50
	  */
	@Transactional(readOnly = false)
	public Map<String, Object> outWarehouse(String orderid, String delivernum) throws ShopMsgException{
		logger.info("/deliver *****app物流出库接口*****");
		boolean flag = false;
		Map<String, Object> map = new HashMap<>();
		if ("".equals(orderid) || "".equals(delivernum)) {
			logger.info("app物流出库接口,参数存在空值");
		 throw new ShopMsgException("app物流出库接口,参数存在空值,orderid/delivernum");
			
		}
		try {
			// 如果参数不为空,符合要求
			ShopMsgCustcodeOrder msgCustcodeOrder = mapper.get(orderid);
			if (msgCustcodeOrder == null) {
				logger.info("app物流出库接口异常,msgCustcodeOrder为空");
				map.put("flag", flag);
				map.put("msg", "app物流出库接口异常,根据orderid查询不到所对应的订单信息");
				CmsOperlogs cmsOperlogs=new CmsOperlogs();
				cmsOperlogs.setContent("app物流出库接口异常,根据orderid查询不到所对应的订单信息");
				cmsOperlogs.setOpertime(new Date());
				cmsOperLogsService.save(cmsOperlogs);
				return map;
			}
			// 出库时间，出库状态，出库物流单号必填
			// 更新出库状态
			msgCustcodeOrder.setIsDeliver(ProcessCode.ORDER_OUT_WAREHOUSE.getLabel());
			// 更新出库状态物流单号
			msgCustcodeOrder.setDeliverNum(delivernum);
			// 更新出库状态时间
			msgCustcodeOrder.setDeliverTime(new Date());
			//更新shopMsgCustCodeOrder表
			mapper.update(msgCustcodeOrder);
			// 新增shopmsgstates信息
			ShopMsgStatus sms = new ShopMsgStatus();
			sms.setStatusName(ProcessCode.ORDER_OUT_WAREHOUSE.getValue());// 1
			sms.setStatusNode("3");
			sms.setContent(delivernum);
			sms.setOperator("haierb2b");
			sms.setMsgId(msgCustcodeOrder.getMsgId());
			shopMsgStatusService.save(sms);
			// 更新msg信息
			ShopMsg msgInfo = shopMsgService.get(msgCustcodeOrder.getMsgId());
			if(msgInfo==null){
				throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR.getDesc());
			}
			String t = msgInfo.getUnreadMsg();
			if(StringUtils.isBlank(t)){
				t="0";
			}
			msgInfo.setUnreadMsg(String.valueOf(Integer.parseInt(t) + 1));
			msgInfo.setMsgFlag("3");
			shopMsgService.updateMsg(msgInfo);
			map.put("flag", true);
			map.put("msg", "出库成功");
		} catch (Exception e) {
			logger.info("app物流出库接口异常,delivernum:"+delivernum+",orderid:"+orderid+e.getMessage());
			e.printStackTrace();
			map.put("flag", flag);
			CmsOperlogs cmsOperlogs=new CmsOperlogs();
			cmsOperlogs.setContent("app物流出库接口异常,delivernum:"+delivernum+",orderid:"+orderid+"."+e.getMessage());
			cmsOperlogs.setOpertime(new Date());
			cmsOperLogsService.save(cmsOperlogs);
			return map;
		}
		return map;
	}

	
	
	/** 
	  * @Title: sign 
	  * @Description: TODO  提供给app 物流签收 接口
	  * @param orderid
	  * @param signurl
	  * @param request
	  * @param response
	  * @return 
	  * @return Map<String,Object>
	  * @author tc
	  * @date 2019年9月29日下午3:12:39
	  */
	public Map<String, Object> sign(String orderid,MultipartFile signurl, HttpServletRequest request,
			boolean isScale,Integer width,Integer height
			) throws ShopMsgException {
		logger.info("/sign *****app物流签收接口*****");
		boolean flag = false;
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isBlank(orderid)) {
			logger.info("app物流签收接口,参数存在空值");
			map.put("flag", flag);
			map.put("msg", "app物流签收接口,参数存在空值");
			CmsOperlogs log=new CmsOperlogs();
			log.setContent("app物流签收接口参数 存在空值");
			log.setOpertime(new Date());
			cmsOperLogsService.save(log);
			return map;
		}
		try {
			// 如果参数不为空,符合要求
			ShopMsgCustcodeOrder msgCustcodeOrder = mapper.get(orderid);
			if (msgCustcodeOrder == null) {
				logger.info("app物流签收接口,根据 orderid:"+orderid+"。查找的 msgCustcodeOrder为空");
				map.put("flag", flag);
				map.put("msg", "app物流签收接口,根据 orderid:"+orderid+"。查找的 msgCustcodeOrder为空");
				CmsOperlogs log=new CmsOperlogs();
				log.setContent("app物流签收接口,根据 orderid:"+orderid+"。查找的 msgCustcodeOrder为空");
				log.setOpertime(new Date());
                cmsOperLogsService.save(log);
				return map;
			}
			String imgUrl = "";
			if (signurl != null) {
				UploadResult result = new UploadResult();
				try {
					logger.info("111111");
					AjaxJson ret = uploadService.uploadImages(request, null, "1", "2", isScale, width, height);
					logger.info("ret" + ret);
					if (ret.isSuccess()) {
						logger.info("ret.isSuccess()" + ret.isSuccess());
						List<AttInfo> attInfos = (List<AttInfo>) ret.getResult();
						if (attInfos != null && attInfos.size() > 0) {
							AttInfo info = attInfos.get(0);
							result.setSuccess(true);
							result.setState("SUCCESS");
							result.setExt(info.getAttExt());
							result.setTitle(info.getAttName());
							result.setSize(info.getAttSize());
							result.setUrl(info.getAttUrl());
							result.setThumb(info.getAttThumb());
							result.setOriginal(info.getAttName());
						} else {
							logger.info("上传失败");
							result.setState("上传失败");
							result.setSuccess(false);
							result.setMessage("上传失败");
							throw new ShopMsgException(result);
						}
					} else {
							logger.info("ret.getMsg()=" + ret.getMsg());
						result.setState(ret.getMsg());
						result.setSuccess(false);
						result.setMessage(ret.getMsg());
						throw new ShopMsgException(result);
					}
				} catch (Exception e) {
						logger.info("BrownException=" + e.getMessage());
					result.setState(e.getMessage());
					result.setSuccess(false);
					result.setMessage(e.getMessage());
					throw new ShopMsgException(result);
				}
				
				imgUrl=result.getUrl();
		}
			//签收凭证
			msgCustcodeOrder.setSignUrl(imgUrl);
			// 更新出库状态
			msgCustcodeOrder.setIsSign("1");
			// 更新出库状态时间
			msgCustcodeOrder.setSignDate(new Date());
			// 更新订单信息表
			mapper.update(msgCustcodeOrder);
			// 新增shopmsgstates信息
			ShopMsgStatus sms = new ShopMsgStatus();
			sms.setStatusName("已签收");
			sms.setContent(msgCustcodeOrder.getSignUrl());
			sms.setOperator("haierb2b");
			sms.setCreateDate(new Date());
			sms.setMsgId(msgCustcodeOrder.getMsgId());
			shopMsgStatusService.save(sms);
			// 更新msg信息
			ShopMsg msgInfo = shopMsgService.get(msgCustcodeOrder.getMsgId());
			msgInfo.setMsgFlag("5");
			String t = msgInfo.getUnreadMsg();
			if(StringUtils.isBlank(t)){
				t="0";
			}
			msgInfo.setUnreadMsg(String.valueOf(Integer.parseInt(t) + 1));
			shopMsgService.updateMsg(msgInfo);
			map.put("flag", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("app物流签收接口错误信息" + e.getMessage());
			throw new ShopMsgException("app物流签收接口错误信息" + e.getMessage());
		}
		return map;
	}
	
	
}