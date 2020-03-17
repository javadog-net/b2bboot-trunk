package com.jhmis.core.service;

import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.FileUtils;
import com.jhmis.common.utils.ImageUtil;
import com.jhmis.common.utils.fastdfs.FastDfsUtils;
import com.jhmis.common.utils.time.DateFormatUtil;
import com.jhmis.modules.sys.entity.AttCat;
import com.jhmis.modules.sys.entity.AttInfo;
import com.jhmis.modules.sys.entity.Attachment;
import com.jhmis.modules.sys.service.AttInfoService;
import com.jhmis.modules.sys.service.AttachmentService;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class UploadService {
    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);
    private static final String ERROR_FILE_ISEMPTY = "请选择文件";
    private static final String ERROR_EXCEED_SIZE = "文件超过最大长度:%d byte";
    private static final String ERROR_NOTALLOWED_TYPE = "上传文件扩展名是不允许的扩展名。\n只允许%s格式。";
    private static final String ERROR_FILE_UPLOAD_FAILD = "文件上传失败";
    private static final String ERROR_DIRECTORY_NOT_ALLOWED = "访问拒绝";
    private HashMap<String, AllowUpload> extMap = new HashMap();
    private int avatarWidth;
    private int avatarHeight;
    /**
     * 上传方式(local,fastdfs)
     */
    private String uploadKind;

    public UploadService() {
        //初始化允许的文件类型和大小
        String avatarExt = Global.getConfig("userfiles.avatar.ext");
        String imageExt = Global.getConfig("userfiles.image.ext");
        String mediaExt = Global.getConfig("userfiles.media.ext");
        String fileExt = Global.getConfig("userfiles.file.ext");
        fileExt = imageExt + "," + mediaExt + "," + fileExt;
        extMap.put(Global.ATT_AVATAR, new AllowUpload(avatarExt, Global.getConfigAsLong("userfiles.avatar.size")));
        extMap.put(Global.ATT_IMAGE, new AllowUpload(imageExt, Global.getConfigAsLong("userfiles.image.size")));
        extMap.put(Global.ATT_MEDIA, new AllowUpload(mediaExt, Global.getConfigAsLong("userfiles.media.size")));
        extMap.put(Global.ATT_FILE, new AllowUpload(fileExt, Global.getConfigAsLong("userfiles.file.size")));
        avatarWidth = Global.getConfigAsInt("userfiles.avatar.width", 120);
        avatarHeight = Global.getConfigAsInt("userfiles.avatar.height", 120);
        //初始化文件上传方式
        uploadKind = Global.getConfig("userfiles.uploadkind");
        if (uploadKind == null || !"fastdfs".equalsIgnoreCase(uploadKind)) {
            uploadKind = "local";
        }
    }

    private static class AllowUpload {
        private List<String> extList;
        private String ext;
        private Long size;

        public AllowUpload(String ext, Long size) {
            this.ext = ext;
            this.size = size;
            if(ext!=null) {
                this.extList = Arrays.asList(ext.split(","));
            } else {
                this.extList = new ArrayList<>();
            }
        }

        public String getExt() {
            return ext;
        }

        public void setExt(String ext) {
            this.ext = ext;
        }

        public Long getSize() {
            return size;
        }

        public void setSize(Long size) {
            this.size = size;
        }

        /**
         * 检查是否允许的扩展
         *
         * @param fileExt
         * @return
         */
        public boolean checkExt(String fileExt) {
            if (extList.contains(fileExt)) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Autowired
    private MultipartResolver multipartResolver;
    @Autowired
    private AttInfoService attInfoService;
    @Autowired
    private AttachmentService attachmentService;

    private String userType2Dir(String userType) {
        switch (userType) {
            case Global.USER_TYPE_SYS:
                return "sys";
            case Global.USER_TYPE_DEALER:
                return "dealer";
            case Global.USER_TYPE_PURCHASER:
                return "purchaser";
            case Global.USER_TYPE_AVATAR:
            	return "avatar";
            case Global.USER_TYPE_ENCLOSURE:
            	return "enclosure";
            default:
                return "sys";
        }
    }

    private String attType2Dir(String attType) {
        switch (attType) {
            case Global.ATT_AVATAR:
                return "avatars";
            case Global.ATT_IMAGE:
                return "images";
            case Global.ATT_MEDIA:
                return "medias";
            case Global.ATT_FILE:
                return "files";
            default:
                return "files";
        }
    }

    /**
     * 上传头像
     *
     * @param request
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @Transactional(readOnly = false)
    public AjaxJson uploadAvatars(HttpServletRequest request, String catId, String userType, String userId) throws IOException, NoSuchAlgorithmException {
        return this.upload(request, Global.ATT_AVATAR, catId, userType, userId, true, avatarWidth, avatarHeight);
    }
    /**
     * app上传头像
     *
     * @param request
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @Transactional(readOnly = false)
    public AjaxJson uploadAvatarsApp(HttpServletRequest request, String catId, String userType, String userId) throws IOException, NoSuchAlgorithmException {
        return this.uploadAvatarsApp(request, Global.ATT_AVATAR, catId, userType, userId, true, avatarWidth, avatarHeight);
    }

    /**
     * 上传图片
     *
     * @param request
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @Transactional(readOnly = false)
    public AjaxJson uploadImages(HttpServletRequest request, String catId, String userType, String userId, boolean isScale, int width, int height) throws IOException, NoSuchAlgorithmException {
        return this.upload(request, Global.ATT_IMAGE, catId, userType, userId, isScale, width, height);
    }

    /**
     * 上传头像
     *
     * @param request
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @Transactional(readOnly = false)
    public AjaxJson uploadBase64Avatars(HttpServletRequest request, String catId, String userType, String userId) throws IOException, NoSuchAlgorithmException {
        return this.uploadBase64(request, Global.ATT_AVATAR, catId, userType, userId, true, avatarWidth, avatarHeight);
    }

    /**
     * 上传图片
     *
     * @param request
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @Transactional(readOnly = false)
    public AjaxJson uploadBase64Images(HttpServletRequest request, String catId, String userType, String userId, boolean isScale, int width, int height) throws IOException, NoSuchAlgorithmException {
        return this.uploadBase64(request, Global.ATT_IMAGE, catId, userType, userId, isScale, width, height);
    }

    /**
     * 上传媒体
     *
     * @param request
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @Transactional(readOnly = false)
    public AjaxJson uploadMedias(HttpServletRequest request, String catId, String userType, String userId) throws IOException, NoSuchAlgorithmException {
        return this.upload(request, Global.ATT_MEDIA, catId, userType, userId, false, 0, 0);
    }

    /**
     * 上传文件
     *
     * @param request
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @Transactional(readOnly = false)
    public AjaxJson uploadFiles(HttpServletRequest request, String catId, String userType, String userId) throws IOException, NoSuchAlgorithmException {
        return this.upload(request, Global.ATT_FILE, catId, userType, userId, false, 0, 0);
    }

    /**
     * 上传文件
     *
     * @param request
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    private AjaxJson upload(HttpServletRequest request, String attType, String catId, String userType, String userId, boolean isScale, int width, int height) throws IOException, NoSuchAlgorithmException {
        AjaxJson ret = new AjaxJson();
        List<AttInfo> attInfos = new ArrayList<>();
        // 虚拟路径
        String urlPath = "";
        // 文件保存路径
        String filePath = "";
        // 缩略图虚拟路径
        String thumbUrlPath = "";
        // 缩略图保存路径
        String thumbFilePath = "";
        if ("local".equalsIgnoreCase(uploadKind)) {
            String basePath = Global.getUserfilesBaseDir();
            String monthday = DateFormatUtil.formatDate("yyyyMM", new Date());
            urlPath = Global.USERFILES_BASE_URL + userType2Dir(userType) + "/"
                    + userId + "/" + attType2Dir(attType) + "/" + monthday + "/";
            // 文件保存路径
            filePath = basePath + urlPath;
            // 创建文件夹
            FileUtils.createDirectory(filePath);
            if (Global.ATT_IMAGE.equals(attType) && isScale) {
                thumbUrlPath = urlPath + "thumb/";
                thumbFilePath = basePath + thumbUrlPath;
                // 创建文件夹
                FileUtils.createDirectory(thumbFilePath);
            }
        }
        AllowUpload allowUpload = extMap.get(attType);
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            for (Iterator<String> iterator = multiRequest.getFileNames(); iterator.hasNext(); ) {
                String key = iterator.next();
                MultipartFile multipartFile = multiRequest.getFile(key);
                if (!multipartFile.isEmpty()) {
                    String md5 = FileUtils.calcMd5(multipartFile.getInputStream());
                    //检查附件信息表中是否存在此文件
                    AttInfo info = new AttInfo();
                    info.setAttMd5(md5);
                    info = attInfoService.get(info);
                    if (info != null) {
                        Attachment attachment = new Attachment();
                        attachment.setAttName(info.getAttName());
                        attachment.setAttSize(info.getAttSize());
                        attachment.setAttMd5(md5);
                        attachment.setAttExt(info.getAttExt());
                        attachment.setAttUrl(info.getAttUrl());
                        attachment.setAttType(attType);
                        attachment.setUserType(userType);
                        attachment.setUserId(userId);
                        AttCat cat = new AttCat();
                        cat.setId(catId);
                        attachment.setAttCat(cat);
                        attachmentService.save(attachment);
                        attInfos.add(info);
                        continue;
                    }
                    AttInfo attinfo = new AttInfo();
                    String attName = multipartFile.getOriginalFilename();
                    String attExt = FilenameUtils.getExtension(attName).toLowerCase();
                    String attThumb=null;
                    long attSize = multipartFile.getSize();
                    if (!allowUpload.checkExt(attExt)) {
                        ret.setSuccess(false);
                        ret.setMsg(String.format(ERROR_NOTALLOWED_TYPE, allowUpload.getExt()));
                        return ret;
                    }
                    if (attSize > allowUpload.size) {
                        ret.setSuccess(false);
                        ret.setMsg(String.format(ERROR_EXCEED_SIZE, allowUpload.getSize()));
                        return ret;
                    }
                    attinfo.setAttName(attName);
                    attinfo.setAttSize(attSize);
                    attinfo.setAttMd5(md5);
                    attinfo.setAttExt(attExt);
                    //如果是fastdfs上传
                    if ("fastdfs".equalsIgnoreCase(this.uploadKind)) {
                        if (Global.ATT_AVATAR.equalsIgnoreCase(attType)) {
                            byte[] content = ImageUtil.scale(multipartFile.getInputStream(), width, height);
                            String attUrl = FastDfsUtils.getInstance().upload(content, attExt);
                            attinfo.setAttUrl(attUrl);
                        } else {
                            String attUrl = FastDfsUtils.getInstance().upload(multipartFile.getBytes(), attExt);
                            attinfo.setAttUrl(attUrl);
                            if (Global.ATT_IMAGE.equals(attType) && isScale) {
                                //处理缩略图
                                byte[] content = ImageUtil.scale(multipartFile.getInputStream(), width, height);
                                attThumb = FastDfsUtils.getInstance().upload(multipartFile.getBytes(), attExt);
                                attinfo.setAttThumb(attThumb);
                            }
                        }
                    } else {
                        String uuid = UUID.randomUUID().toString();
                        String newFileName = uuid + "." + attExt;
                        String attUrl = urlPath + newFileName;
                        String attFile = filePath + newFileName;
                        if (Global.ATT_AVATAR.equalsIgnoreCase(attType)) {
                            ImageUtil.scale(multipartFile.getInputStream(), width, height, attFile);
                            attinfo.setAttUrl(attUrl);
                        }else {
                        	
                            File destFile = new File(attFile);
                            multipartFile.transferTo(destFile);
                            attinfo.setAttUrl(attUrl);
                            //处理缩略图                          
                            if(Global.USER_TYPE_ENCLOSURE.equalsIgnoreCase(userType) && Global.ATT_IMAGE.equals(attType) ){                          	
                            	BufferedImage bufferedImg = ImageIO.read(destFile);
                            	int imgWidth = bufferedImg.getWidth();
                            	int imgHeight = bufferedImg.getHeight();
               
                            	System.out.println("attThumb" + attThumb);
                            	if(imgWidth > imgHeight){
                            		imgWidth=imgWidth*90/imgHeight;
                            		attThumb = filePath + uuid + "_" + imgWidth +"x" +"90."+ attExt;
                            		ImageUtil.scaleHeight(bufferedImg,90,attThumb);
                            		attThumb = urlPath+ uuid + "_" + imgWidth +"x" +"90."+ attExt;
                            		
                            	}else{
                            		imgHeight=imgHeight*90/imgWidth;
                            		attThumb = filePath + uuid + "_90x" +imgHeight +"."+ attExt;
                            		ImageUtil.scaleWidth(bufferedImg,90,attThumb);
                            		attThumb = urlPath+ uuid + "_90x" +imgHeight +"."+ attExt;
                            		System.out.println("attThumb" + attThumb);
                            	}
                            	attinfo.setAttThumb(attThumb);
                            }else if (Global.ATT_IMAGE.equals(attType) && isScale) {
                                String thumbFileName = uuid + "s." + attExt;
                                attThumb = thumbUrlPath + thumbFileName;
                                String attThumbFile = thumbFilePath + thumbFileName;
                                ImageUtil.scale(destFile, width, height, attThumbFile);
                                attinfo.setAttThumb(attThumb);
                            }
                        }
                    }
                    //存储到附件表
                    attInfoService.save(attinfo);
                    Attachment attachment = new Attachment();
                    attachment.setAttName(attName);
                    attachment.setAttSize(attSize);
                    attachment.setAttMd5(md5);
                    attachment.setAttExt(attExt);
                    if(attThumb!=null){
                    	attachment.setAttThumb(attThumb);
                    }                   
                    attachment.setAttUrl(attinfo.getAttUrl());
                    attachment.setAttType(attType);
                    attachment.setUserType(userType);
                    attachment.setUserId(userId);
                    AttCat cat = new AttCat();
                    cat.setId(catId);
                    attachment.setAttCat(cat);
                    attachmentService.save(attachment);
                    //
                    attInfos.add(attinfo);
                }
            }

        } else {
            ret.setSuccess(false);
            ret.setMsg(ERROR_FILE_ISEMPTY);
        }
        ret.setResult(attInfos);
        return ret;
    }

    
    /**
     * 上传头像
     *
     * @param request
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    private AjaxJson uploadAvatarsApp(HttpServletRequest request, String attType, String catId, String userType, String userId, boolean isScale, int width, int height) throws IOException, NoSuchAlgorithmException {
        AjaxJson ret = new AjaxJson();
        List<AttInfo> attInfos = new ArrayList<>();
        // 虚拟路径
        String urlPath = "";
        // 文件保存路径
        String filePath = "";
        // 缩略图虚拟路径
        String thumbUrlPath = "";
        // 缩略图保存路径
        String thumbFilePath = "";
        if ("local".equalsIgnoreCase(uploadKind)) {
            String basePath = Global.getUserfilesBaseDir();
            String monthday = DateFormatUtil.formatDate("yyyyMM", new Date());
            urlPath = Global.USERFILES_BASE_URL + userType2Dir(userType) + "/"
                    + userId + "/" + attType2Dir(attType) + "/" + monthday + "/";
            // 文件保存路径
            filePath = basePath + urlPath;
            // 创建文件夹
            FileUtils.createDirectory(filePath);
            if (Global.ATT_IMAGE.equals(attType) && isScale) {
                thumbUrlPath = urlPath + "thumb/";
                thumbFilePath = basePath + thumbUrlPath;
                // 创建文件夹
                FileUtils.createDirectory(thumbFilePath);
            }
        }
        AllowUpload allowUpload = extMap.get(attType);
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            for (Iterator<String> iterator = multiRequest.getFileNames(); iterator.hasNext(); ) {
                String key = iterator.next();
                MultipartFile multipartFile = multiRequest.getFile(key);
                if (!multipartFile.isEmpty()) {
                    String md5 = FileUtils.calcMd5(multipartFile.getInputStream());
                    //检查附件信息表中是否存在此文件
                    AttInfo info = new AttInfo();
                    info.setAttMd5(md5);
                    /*info = attInfoService.get(info);
                    if (info != null) {
                        Attachment attachment = new Attachment();
                        attachment.setAttName(info.getAttName());
                        attachment.setAttSize(info.getAttSize());
                        attachment.setAttMd5(md5);
                        attachment.setAttExt(info.getAttExt());
                        attachment.setAttUrl(info.getAttUrl());
                        attachment.setAttThumb(info.getAttThumb());
                        attachment.setAttType(attType);
                        attachment.setUserType(userType);
                        attachment.setUserId(userId);
                        AttCat cat = new AttCat();
                        cat.setId(catId);
                        attachment.setAttCat(cat);
                        attachmentService.save(attachment);
                        attInfos.add(info);
                        continue;
                    }*/
                    AttInfo attinfo = new AttInfo();
                    String attName = multipartFile.getOriginalFilename();
                    String attExt = FilenameUtils.getExtension(attName).toLowerCase();
                    logger.info("attExt="+attExt);
                    long attSize = multipartFile.getSize();
                    if (!allowUpload.checkExt(attExt)) {
                        ret.setSuccess(false);
                        ret.setMsg(String.format(ERROR_NOTALLOWED_TYPE, allowUpload.getExt()));
                        return ret;
                    }
                    if (attSize > allowUpload.size) {
                        ret.setSuccess(false);
                        ret.setMsg(String.format(ERROR_EXCEED_SIZE, allowUpload.getSize()));
                        return ret;
                    }
                    attinfo.setAttName(attName);
                    attinfo.setAttSize(attSize);
                    attinfo.setAttMd5(md5);
                    attinfo.setAttExt(attExt);
                    //将MultipartFile转换为File
                  //在根目录下创建一个tempfileDir的临时文件夹
                   /* String contextpath =filePath+"tempfileDir/";
                    //将MultipartFile文件转换，即写入File新文件中，返回File文件 
                    File File = new File(contextpath);
                    
                    File newFile = new File(contextpath+UUID.randomUUID().toString().replaceAll("-", "")+"."+attExt);
                    
                    System.out.println(contextpath+UUID.randomUUID().toString().replaceAll("-", "")+"."+attExt);
                    multipartFile.transferTo(newFile);*/
                    //如果是fastdfs上传
                    if ("fastdfs".equalsIgnoreCase(this.uploadKind)) {
                        if (Global.ATT_AVATAR.equalsIgnoreCase(attType)) {
                            byte[] content = ImageUtil.scale(multipartFile.getInputStream(), width, height);
                            String attUrl = FastDfsUtils.getInstance().upload(content, attExt);
                            attinfo.setAttUrl(attUrl);
                        }
                    } else {
                        String uuid = UUID.randomUUID().toString();
                        String newFileName = uuid + "." + attExt;
                        String attUrl = urlPath + newFileName;
                        String attFile = filePath + newFileName;
                        if (Global.ATT_AVATAR.equalsIgnoreCase(attType)) {
                        	File destFile = new File(attFile);
                            multipartFile.transferTo(destFile);
                        	BufferedImage bufferedImg = ImageIO.read(destFile);
                        	int imgWidth = bufferedImg.getWidth();
                        	int imgHeight = bufferedImg.getHeight();
                        	if(imgWidth > imgHeight){
                        		ImageUtil.scaleHeightS(destFile,120,urlPath);
                        	}else{
                        		ImageUtil.scaleWidthS(destFile,120,urlPath);
                        	}
                            attinfo.setAttUrl(attUrl);
                        	
                        	
                        }
                    }
                    
                    //存储到附件表
                    attInfoService.save(attinfo);
                    Attachment attachment = new Attachment();
                    attachment.setAttName(attName);
                    attachment.setAttSize(attSize);
                    attachment.setAttMd5(md5);
                    attachment.setAttExt(attExt);
                    attachment.setAttUrl(attinfo.getAttUrl());
                    attachment.setAttType(attType);
                    attachment.setUserType(userType);
                    attachment.setUserId(userId);
                    AttCat cat = new AttCat();
                    cat.setId(catId);
                    attachment.setAttCat(cat);
                    attachmentService.save(attachment);
                    //
                    attInfos.add(attinfo);
                }
            }

        } else {
            ret.setSuccess(false);
            ret.setMsg(ERROR_FILE_ISEMPTY);
        }
        ret.setResult(attInfos);
        return ret;
    }
    
    
    
    /**
     * 上传base64图片
     *
     * @param request
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    private AjaxJson uploadBase64(HttpServletRequest request, String attType, String catId, String userType, String userId, boolean isScale, int width, int height) throws IOException, NoSuchAlgorithmException {
        AjaxJson ret = new AjaxJson();
        List<AttInfo> attInfos = new ArrayList<>();
        // 虚拟路径
        String urlPath = "";
        // 文件保存路径
        String filePath = "";
        // 缩略图虚拟路径
        String thumbUrlPath = "";
        // 缩略图保存路径
        String thumbFilePath = "";
        if ("local".equalsIgnoreCase(uploadKind)) {
            String basePath = Global.getUserfilesBaseDir();
            String monthday = DateFormatUtil.formatDate("yyyyMM", new Date());
            urlPath = Global.USERFILES_BASE_URL + userType2Dir(userType) + "/"
                    + userId + "/" + attType2Dir(attType) + "/" + monthday + "/";
            // 文件保存路径
            filePath = basePath + urlPath;
            // 创建文件夹
            FileUtils.createDirectory(filePath);
            if (Global.ATT_IMAGE.equals(attType) && isScale) {
                thumbUrlPath = urlPath + "thumb/";
                thumbFilePath = basePath + thumbUrlPath;
                // 创建文件夹
                FileUtils.createDirectory(thumbFilePath);
            }
        }
        AllowUpload allowUpload = extMap.get(attType);
        Enumeration<String> params = request.getParameterNames();

        while (params.hasMoreElements()) {
            String key = params.nextElement();
            String fileStr = request.getParameter(key);
            if (fileStr != null && !"".equals(fileStr)) {
                int index = fileStr.indexOf("base64");
                if (index > 0) {
                    String preStr = fileStr.substring(0, index + 7);
                    String attExt = preStr.substring(preStr.indexOf("/") + 1, preStr.indexOf(";")).toLowerCase();
                    if (!allowUpload.checkExt(attExt)) {
                        ret.setSuccess(false);
                        ret.setMsg(String.format(ERROR_NOTALLOWED_TYPE, allowUpload.getExt()));
                        return ret;
                    }
                    fileStr = fileStr.substring(fileStr.indexOf(",") + 1);
                    byte[] ins = Base64.getDecoder().decode(fileStr);
                    long attSize = ins.length;
                    if (attSize > allowUpload.size) {
                        ret.setSuccess(false);
                        ret.setMsg(String.format(ERROR_EXCEED_SIZE, allowUpload.getSize()));
                        return ret;
                    }
                    for (int i = 0; i < ins.length; ++i) {
                        if (ins[i] < 0) {// 调整异常数据
                            ins[i] += 256;
                        }
                    }
                    String md5 = FileUtils.calcMd5(ins);
                    //检查附件信息表中是否存在此文件
                    AttInfo info = new AttInfo();
                    info.setAttMd5(md5);
                    info = attInfoService.get(info);
                    if (info != null) {
                        Attachment attachment = new Attachment();
                        attachment.setAttName(info.getAttName());
                        attachment.setAttSize(info.getAttSize());
                        attachment.setAttMd5(md5);
                        attachment.setAttExt(info.getAttExt());
                        attachment.setAttUrl(info.getAttUrl());
                        attachment.setAttType(attType);
                        attachment.setUserType(userType);
                        attachment.setUserId(userId);
                        AttCat cat = new AttCat();
                        cat.setId(catId);
                        attachment.setAttCat(cat);
                        attachmentService.save(attachment);
                        attInfos.add(info);
                        continue;
                    }
                    AttInfo attinfo = new AttInfo();
                    String uuid = UUID.randomUUID().toString();
                    String attName = uuid + "." + attExt;
                    if (!allowUpload.checkExt(attExt)) {
                        ret.setSuccess(false);
                        ret.setMsg(String.format(ERROR_NOTALLOWED_TYPE, allowUpload.getExt()));
                        return ret;
                    }
                    if (attSize > allowUpload.size) {
                        ret.setSuccess(false);
                        ret.setMsg(String.format(ERROR_EXCEED_SIZE, allowUpload.getSize()));
                        return ret;
                    }
                    attinfo.setAttName(attName);
                    attinfo.setAttSize(attSize);
                    attinfo.setAttMd5(md5);
                    attinfo.setAttExt(attExt);
                    //如果是fastdfs上传
                    if ("fastdfs".equalsIgnoreCase(this.uploadKind)) {
                        if (Global.ATT_AVATAR.equalsIgnoreCase(attType)) {
                            byte[] content = ImageUtil.scale(ins, width, height);
                            String attUrl = FastDfsUtils.getInstance().upload(content, attExt);
                            attinfo.setAttUrl(attUrl);
                        } else {
                            String attUrl = FastDfsUtils.getInstance().upload(ins, attExt);
                            attinfo.setAttUrl(attUrl);
                            if (Global.ATT_IMAGE.equals(attType) && isScale) {
                                //处理缩略图
                                byte[] content = ImageUtil.scale(ins, width, height);
                                String attThumb = FastDfsUtils.getInstance().upload(content, attExt);
                                attinfo.setAttThumb(attThumb);
                            }
                        }
                    } else {
                        String attUrl = urlPath + attName;
                        String attFile = filePath + attName;
                        if (Global.ATT_AVATAR.equalsIgnoreCase(attType)) {
                            ImageUtil.scale(ins, width, height, attFile);
                            attinfo.setAttUrl(attUrl);
                        } else {
                            OutputStream out = null;
                            try {
                                out = new FileOutputStream(attFile);
                            } finally {
                                out.close();
                            }
                            out.write(ins);
                            out.flush();
                            out.close();
                            attinfo.setAttUrl(attUrl);
                            //处理缩略图
                            if (Global.ATT_IMAGE.equals(attType) && isScale) {
                                String thumbFileName = uuid + "_s." + attExt;
                                String attThumb = thumbUrlPath + thumbFileName;
                                String attThumbFile = thumbFilePath + thumbFileName;
                                ImageUtil.scale(ins, width, height, attThumbFile);
                                attinfo.setAttThumb(attThumb);
                            }
                        }
                    }
                    //存储到附件表
                    attInfoService.save(attinfo);
                    Attachment attachment = new Attachment();
                    attachment.setAttName(attName);
                    attachment.setAttSize(attSize);
                    attachment.setAttMd5(md5);
                    attachment.setAttExt(attExt);
                    attachment.setAttUrl(attinfo.getAttUrl());
                    attachment.setAttType(attType);
                    attachment.setUserType(userType);
                    attachment.setUserId(userId);
                    AttCat cat = new AttCat();
                    cat.setId(catId);
                    attachment.setAttCat(cat);
                    attachmentService.save(attachment);
                    attachmentService.save(attachment);
                    //
                    attInfos.add(attinfo);
                }
            }

        }
        ret.setResult(attInfos);
        return ret;
    }

}
