/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.api.wechat;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.service.UploadService;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.sys.entity.AttInfo;
import com.jhmis.modules.sys.entity.UploadResult;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.wechat.service.WxMeetingService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 会议报名表Controller
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@RestController
@RequestMapping("/api/wechat/upload")
public class ApiUploadController extends BaseController {

	@Autowired
	UploadService uploadService;

	/**
	 * 上传图片
	 *
	 * @param request
	 * @param isScale
	 * @param width
	 * @param height
	 * @return
	 */
	@ApiOperation(notes = "uploadImageByWx", httpMethod = "POST", value = "上传图片", consumes = "multipart/*")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "isScale", value = "是否缩放（true,false)", defaultValue = "false", required = false, paramType = "form", dataType = "boolean"),
			@ApiImplicitParam(name = "width", value = "缩放宽度", defaultValue = "240", required = false, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "height", value = "缩放高度", defaultValue = "240", required = false, paramType = "form", dataType = "int")
	})
	@PostMapping(value = "uploadImageByWx")
	public UploadResult uploadImageByWx(HttpServletRequest request, @ApiParam(name = "任意", value = "图片上传") MultipartFile file, @RequestParam(defaultValue = "false") Boolean isScale, @RequestParam(defaultValue = "240") Integer width, @RequestParam(defaultValue = "240") Integer height) {
		UploadResult result = new UploadResult();
		try {
			AjaxJson ret = uploadService.uploadImages(request, null, "1", "wxRegister", isScale, width, height);
			if (ret.isSuccess()) {
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
					result.setState("上传失败");
					result.setSuccess(false);
					result.setMessage("上传失败");
				}
			} else {
				result.setState(ret.getMsg());
				result.setSuccess(false);
				result.setMessage(ret.getMsg());
			}
		} catch (Exception e) {
			result.setState(e.getMessage());
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	

}