package com.jhmis.api.common;

import com.jhmis.api.app.AppApiUploadController;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.service.UploadService;
import com.jhmis.modules.sys.entity.AttInfo;
import com.jhmis.modules.sys.entity.UploadResult;
import com.jhmis.modules.sys.security.Principal;
import com.jhmis.modules.sys.utils.UserUtils;
import ch.qos.logback.classic.Logger;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

@Api(value = "ApiCommonUploadController", description = "通用文件上传")
@RestController
@RequestMapping("/api/common/upload")
public class ApiCommonUploadController {
	
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
    @ApiOperation(notes = "uploadimage", httpMethod = "POST", value = "上传图片", consumes = "multipart/*")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isScale", value = "是否缩放（true,false)", defaultValue = "false", required = false, paramType = "form", dataType = "boolean"),
            @ApiImplicitParam(name = "width", value = "缩放宽度", defaultValue = "240", required = false, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "height", value = "缩放高度", defaultValue = "240", required = false, paramType = "form", dataType = "int")
    })
    @RequiresRoles(value = {"dealer", "purchaser"}, logical = Logical.OR)
    @PostMapping(value = "uploadimage")
    public UploadResult uploadImage(HttpServletRequest request, @ApiParam(name = "任意", value = "图片上传") MultipartFile file, @RequestParam(defaultValue = "false") Boolean isScale, @RequestParam(defaultValue = "240") Integer width, @RequestParam(defaultValue = "240") Integer height) {
        UploadResult result = new UploadResult();
        try {
            AjaxJson ret = uploadService.uploadImages(request, null, UserUtils.getPrincipal().getUserType(), UserUtils.getPrincipal().getId(), isScale, width, height);
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
     * 上传文件
     *
     * @param request
     * @return
     */
    @ApiOperation(notes = "uploadfile", httpMethod = "POST", value = "上传文件", consumes = "multipart/*")
    @RequiresRoles(value = {"dealer", "purchaser"}, logical = Logical.OR)
    @PostMapping(value = "uploadfile")
    public UploadResult uploadFile(HttpServletRequest request, @ApiParam(name = "任意", value = "文件上传") MultipartFile file) {
        UploadResult result = new UploadResult();
        try {
            AjaxJson ret = uploadService.uploadFiles(request, null, UserUtils.getPrincipal().getUserType(), UserUtils.getPrincipal().getId());
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
     * 上传头像
     *
     * @param request
     * @return
     */
    @ApiOperation(notes = "uploadavatar", httpMethod = "POST", value = "上传头像", consumes = "multipart/*")
    @RequiresRoles(value = {"dealer", "purchaser"}, logical = Logical.OR)
    @PostMapping(value = "uploadavatar")
    public UploadResult uploadAvatar(HttpServletRequest request, @ApiParam(name = "任意", value = "头像上传") MultipartFile file) {
        UploadResult result = new UploadResult();
        try {
            AjaxJson ret = uploadService.uploadAvatars(request, null, UserUtils.getPrincipal().getUserType(), UserUtils.getPrincipal().getId());
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
     * 上传媒体
     *
     * @param request
     * @return
     */
    @ApiOperation(notes = "uploadmedia", httpMethod = "POST", value = "上传媒体", consumes = "multipart/*")
    @RequiresRoles(value = {"dealer", "purchaser"}, logical = Logical.OR)
    @PostMapping(value = "uploadmedia")
    public UploadResult uploadMedia(HttpServletRequest request, @ApiParam(name = "任意", value = "媒体上传") MultipartFile file) {
        UploadResult result = new UploadResult();
        try {
            AjaxJson ret = uploadService.uploadMedias(request, null, UserUtils.getPrincipal().getUserType(), UserUtils.getPrincipal().getId());
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
     * base64上传图片
     *
     * @param request
     * @param isScale
     * @param width
     * @param height
     * @return
     */
    @ApiOperation(notes = "uploadimagebase64", httpMethod = "POST", value = "base64上传图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "base64", value = "base64文件字符串", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "isScale", value = "是否缩放（true,false)", defaultValue = "false", required = false, paramType = "form", dataType = "boolean"),
            @ApiImplicitParam(name = "width", value = "缩放宽度", defaultValue = "240", required = false, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "height", value = "缩放高度", defaultValue = "240", required = false, paramType = "form", dataType = "int")
    })
    @RequiresRoles(value = {"dealer", "purchaser"}, logical = Logical.OR)
    @PostMapping(value = "uploadimagebase64")
    public UploadResult uploadImageBase64(HttpServletRequest request, @RequestParam(defaultValue = "false") Boolean isScale, @RequestParam(defaultValue = "240") Integer width, @RequestParam(defaultValue = "240") Integer height) {
        UploadResult result = new UploadResult();
        try {
            AjaxJson ret = uploadService.uploadBase64Images(request, null, UserUtils.getPrincipal().getUserType(), UserUtils.getPrincipal().getId(), isScale, width, height);
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
     * base64上传头像
     *
     * @param request
     * @return
     */
    @ApiOperation(notes = "uploadavatarbase64", httpMethod = "POST", value = "base64上传头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "base64", value = "base64图片字符串", required = true, paramType = "form", dataType = "String")
    })
    @RequiresRoles(value = {"dealer", "purchaser"}, logical = Logical.OR)
    @PostMapping(value = "uploadavatarbase64")
    public UploadResult uploadAvatarBase64(HttpServletRequest request) {
        UploadResult result = new UploadResult();
        try {
            AjaxJson ret = uploadService.uploadBase64Avatars(request, null, UserUtils.getPrincipal().getUserType(), UserUtils.getPrincipal().getId());
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
     * 上传图片
     *
     * @param request
     * @param isScale
     * @param width
     * @param height
     * @return
     */
    @ApiOperation(notes = "uploadimage", httpMethod = "POST", value = "上传图片", consumes = "multipart/*")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isScale", value = "是否缩放（true,false)", defaultValue = "false", required = false, paramType = "form", dataType = "boolean"),
            @ApiImplicitParam(name = "width", value = "缩放宽度", defaultValue = "240", required = false, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "height", value = "缩放高度", defaultValue = "240", required = false, paramType = "form", dataType = "int")
    })
    @PostMapping(value = "uploadImageNoAuth")
    public UploadResult uploadImageNoAuth(HttpServletRequest request, @ApiParam(name = "任意", value = "图片上传") MultipartFile file, @RequestParam(defaultValue = "false") Boolean isScale, @RequestParam(defaultValue = "240") Integer width, @RequestParam(defaultValue = "240") Integer height) {
        UploadResult result = new UploadResult();
        Principal principal = UserUtils.getPrincipal();
        try {
            AjaxJson ret = uploadService.uploadImages(request, null, UserUtils.getPrincipal().getUserType(), UserUtils.getPrincipal().getId(), isScale, width, height);
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
