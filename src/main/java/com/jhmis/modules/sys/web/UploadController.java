package com.jhmis.modules.sys.web;

import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.service.UploadService;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.sys.entity.AttInfo;
import com.jhmis.modules.sys.entity.UploadResult;
import com.jhmis.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/upload")
public class UploadController extends BaseController {
    @Autowired
    UploadService uploadService;

    /**
     * 上传图片
     * @param request
     * @param catId
     * @param isScale
     * @param width
     * @param height
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "uploadimage")
    public UploadResult uploadImage(HttpServletRequest request, String catId, @RequestParam(defaultValue="false")Boolean isScale, @RequestParam(defaultValue="0")Integer width, @RequestParam(defaultValue="0")Integer height) {
        UploadResult result = new UploadResult();
        try {
            AjaxJson ret = uploadService.uploadImages(request,catId, Global.USER_TYPE_SYS,UserUtils.getPrincipal().getId(),isScale,width,height);
            if(ret.isSuccess()) {
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
     * @param request
     * @param catId
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "uploadfile")
    public UploadResult uploadFile(HttpServletRequest request,String catId) {
        UploadResult result = new UploadResult();
        try {
            AjaxJson ret = uploadService.uploadFiles(request, catId, Global.USER_TYPE_SYS,UserUtils.getPrincipal().getId());
            if(ret.isSuccess()) {
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
     * @param request
     * @param catId
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "uploadavatar")
    public UploadResult uploadAvatar(HttpServletRequest request,String catId) {
        UploadResult result = new UploadResult();
        try {
            AjaxJson ret = uploadService.uploadAvatars(request, catId, Global.USER_TYPE_SYS,UserUtils.getPrincipal().getId());
            if(ret.isSuccess()) {
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
     * @param request
     * @param catId
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "uploadmedia")
    public UploadResult uploadMedia(HttpServletRequest request,String catId) {
        UploadResult result = new UploadResult();
        try {
            AjaxJson ret = uploadService.uploadMedias(request, catId, Global.USER_TYPE_SYS,UserUtils.getPrincipal().getId());
            if(ret.isSuccess()) {
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
     * @param request
     * @param catId
     * @param isScale
     * @param width
     * @param height
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "uploadimagebase64")
    public UploadResult uploadImageBase64(HttpServletRequest request, String catId, @RequestParam(defaultValue="false")Boolean isScale, @RequestParam(defaultValue="0")Integer width, @RequestParam(defaultValue="0")Integer height) {
        UploadResult result = new UploadResult();
        try {
            AjaxJson ret = uploadService.uploadBase64Images(request,catId, Global.USER_TYPE_SYS,UserUtils.getPrincipal().getId(),isScale,width,height);
            if(ret.isSuccess()) {
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
     * @param request
     * @param catId
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "uploadavatarbase64")
    public UploadResult uploadAvatarBase64(HttpServletRequest request,String catId) {
        UploadResult result = new UploadResult();
        try {
            AjaxJson ret = uploadService.uploadBase64Avatars(request, catId, Global.USER_TYPE_SYS,UserUtils.getPrincipal().getId());
            if(ret.isSuccess()) {
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
