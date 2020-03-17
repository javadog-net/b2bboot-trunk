package com.jhmis.api.app;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.service.UploadService;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.sys.entity.AttInfo;
import com.jhmis.modules.sys.entity.UploadResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/app/upload")
public class AppApiUploadController extends BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(AppApiUploadController.class);

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
	@ApiOperation(notes = "uploadImage", httpMethod = "POST", value = "上传图片", consumes = "multipart/*")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "isScale", value = "是否缩放（true,false)", defaultValue = "false", required = false, paramType = "form", dataType = "boolean"),
			@ApiImplicitParam(name = "width", value = "缩放宽度", defaultValue = "240", required = false, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "height", value = "缩放高度", defaultValue = "240", required = false, paramType = "form", dataType = "int")
	})
	@PostMapping(value = "uploadImage")
	public UploadResult uploadImageByWx(HttpServletRequest request, @ApiParam(name = "任意", value = "图片上传") MultipartFile file, @RequestParam(defaultValue = "false") Boolean isScale, @RequestParam(defaultValue = "240") Integer width, @RequestParam(defaultValue = "240") Integer height) {
		logger.info("uploadImage开始**************");
		UploadResult result = new UploadResult();
		try {
			logger.info("111111");
			AjaxJson ret = uploadService.uploadImages(request, null, "1", "appImage", isScale, width, height);
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
				}
			} else {
					logger.info("ret.getMsg()=" + ret.getMsg());
				result.setState(ret.getMsg());
				result.setSuccess(false);
				result.setMessage(ret.getMsg());
			}
		} catch (Exception e) {
				logger.info("BrownException=" + e.getMessage());
			result.setState(e.getMessage());
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}
		System.out.println("上传图片成功："+result.getUrl());
		return result;
	}

	
	
	/**
     * 上传头像
     *
     * @param request
     * @return
     */
    @ApiOperation(notes = "uploadavatar", httpMethod = "POST", value = "上传头像", consumes = "multipart/*")
    @PostMapping(value = "uploadavatar")
    public UploadResult uploadAvatar(HttpServletRequest request, @ApiParam(name = "任意", value = "头像上传") MultipartFile file) {
        UploadResult result = new UploadResult();
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null){
        	result.setState("上传失败");
			result.setSuccess(false);
			result.setMessage("请您重新登录！");
			return result;
        }
        try {
            AjaxJson ret = uploadService.uploadAvatarsApp(request, null, Global.USER_TYPE_AVATAR, purchaserAccount.getId());
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
    
    
    
    /**
     * base64上传头像
     *
     * @param request
     * @return
     */
    @ApiOperation(notes = "uploadavatarbase64", httpMethod = "POST", value = "base64上传头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "base64", value = "base64图片字符串", required = true, paramType = "form", dataType = "String")
    })
    @PostMapping(value = "uploadavatarbase64")
    public UploadResult uploadAvatarBase64(HttpServletRequest request) {
        UploadResult result = new UploadResult();
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null){
        	result.setState("上传失败");
			result.setSuccess(false);
			result.setMessage("请您重新登录！");
			return result;
        }
        try {
            AjaxJson ret = uploadService.uploadBase64Avatars(request, null,Global.USER_TYPE_AVATAR, purchaserAccount.getId());
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
                    result.setOriginal(info.getAttName());
                } else {
                    result.setState("上传失败");
                    result.setSuccess(false);
                    result.setMessage("图片文件有误");
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
    
    
    
    

	/**
	 * 聊天传输图片信息
	 *
	 * @param request
	 * @param isScale
	 * @param width
	 * @param height
	 * @return
	 */
	@ApiOperation(notes = "imageMessage", httpMethod = "POST", value = "上传图片", consumes = "multipart/*")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "isScale", value = "是否缩放（true,false)", defaultValue = "false", required = false, paramType = "form", dataType = "boolean"),
			@ApiImplicitParam(name = "width", value = "缩放宽度", defaultValue = "240", required = false, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "height", value = "缩放高度", defaultValue = "240", required = false, paramType = "form", dataType = "int")
	})
	@PostMapping("/imageMessage")
	public UploadResult imageMessage(HttpServletRequest request, @ApiParam(name = "任意", value = "图片上传") MultipartFile file, @RequestParam(defaultValue = "false") Boolean isScale, @RequestParam(defaultValue = "240") Integer width, @RequestParam(defaultValue = "240") Integer height) {
		UploadResult result = new UploadResult();
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null){
        	result.setState("上传失败");
			result.setSuccess(false);
			result.setMessage("请您重新登录！");
			return result;
        }
		try {
			AjaxJson ret = uploadService.uploadImages(request, null, Global.USER_TYPE_ENCLOSURE, purchaserAccount.getId(), isScale, width, height);
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

    
    /**
     * 聊天传输文件信息
     *
     * @param request
     * @return
     */
    @ApiOperation(notes = "fileMessage", httpMethod = "POST", value = "上传文件", consumes = "multipart/*")
    @RequiresRoles(value = {"dealer", "purchaser"}, logical = Logical.OR)
    @PostMapping("/fileMessage")
    public UploadResult fileMessage(HttpServletRequest request, @ApiParam(name = "任意", value = "文件上传") MultipartFile file) {
    	UploadResult result = new UploadResult();
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		
        if(purchaserAccount == null){
        	result.setState("上传失败");
			result.setSuccess(false);
			result.setMessage("请您重新登录！");
			return result;
        }
        try {
            AjaxJson ret = uploadService.uploadFiles(request, null, Global.USER_TYPE_ENCLOSURE, purchaserAccount.getId());
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
