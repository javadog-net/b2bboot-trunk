package com.jhmis.api.PC;

import com.jhmis.common.annotation.SysLog;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.entity.CmsIslike;
import com.jhmis.modules.cms.entity.Comment;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.cms.service.CmsIslikeService;
import com.jhmis.modules.cms.service.CommentService;
import com.jhmis.modules.cms.service.InfoService;
import com.jhmis.modules.cms.service.SensitiveService;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @program: b2bboot
 * @description: 添加评论
 * @author: T.c
 * @create: 2019-12-06 15:02
 **/
@RequestMapping("/api/pc/comment")
@RestController
public class CmsInfoToPcController extends BaseController {
    @Resource
    private CommentService commentService;
    @Resource
    private InfoService infoService;
@Resource
private CmsIslikeService cmsIslikeService;
@Resource
    private SensitiveService sensitiveService;
    /**
     *@Description 添加评论
     *@Param id 信息的id  必传
     *      category_id 信息栏目的id  必传
     *      parent_id 回复给谁的id   有则传，没有不传
     *      reply_to 回复给人的 loginName  有则传，没有不传
     *      content 评论内容 必传
     *@Return
     *@Author t.c
     *@Date 2019-11-14
     */
    @RequestMapping(value="/addcommentinfo")
    public AjaxJson addCommentInfo(@RequestParam(value="id")String id,
                                   @RequestParam(value="category_id")String category_id,
                                   @RequestParam(value="parent_id",required = false)String parent_id,
                                   @RequestParam(value="reply_to",required = false)String reply_to,
                                   @RequestParam(value="content")String content){

        try {
            PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
            if (account == null) {
                return AjaxJson.fail("401", "请重新登录！");
            }
            Info info= infoService.get(id);
            if (info==null){
                return AjaxJson.fail("抱歉，未查询到该信息！");
            }
            Comment comment=new Comment();
            comment.setContentId(id);
            comment.setTitle(info.getTitle());
            if(null!=account.getAvatar()&&!"".equals(account.getAvatar())) {
                comment.setAvatar(account.getAvatar());
            }
            // 敏感词处理
            comment.setContent(sensitiveService.replace(content));
            comment.setCreateDate(new Date());
            comment.setUserName(account.getLoginName());
            comment.setPurchaserId(account.getId());
            comment.setCategoryId(category_id);
            if(StringUtils.isNotBlank(reply_to)) {
                comment.setReplyTo(reply_to);
            }
            if(StringUtils.isNotBlank(parent_id)) {
                comment.setParentId(parent_id);
            }
            commentService.save(comment);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return AjaxJson.ok("保存评论成功！");
    }


    @RequestMapping("/getInfoById")
    @SysLog(desc = "查看info详情")
    public AjaxJson getInfoById(@RequestParam(value = "id") String id) {
        Info info = null;
        try {
            info = infoService.get(id);
            if (info == null) {
                return AjaxJson.fail("参数有误：" + id);
            }
            //添加评论List
            Comment c = new Comment();
            c.setContentId(info.getId());
            info.setListComment(commentService.findList(c));

            //添加评论数量
            Integer i = commentService.selectCountById(info.getId());
            info.setCommentNum(i);
            //添加点赞数量
            Integer t = cmsIslikeService.selectCountById(info.getId());
            info.setIsLikeNum(t);
            //修改浏览量
            infoService.updateHitsById(id, (info.getHits() + 1));
            //查看是否登录，若登录则判断该文章是否进行了点赞
            PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
            if (null!=account) {
                CmsIslike cmsIslike = new CmsIslike();
                cmsIslike.setInfoId(id);
                cmsIslike.setUserId(account.getId());
                List<CmsIslike> list = cmsIslikeService.findList(cmsIslike);
                if (list != null && list.size() > 0) {//说明 点赞了，添加点赞标识
                    info.setIsLike("1");
                }
            }else {
                info.setIsLike("0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("info 查看详情信息，出现异常：" + e.getMessage());
        }

        return AjaxJson.ok(info);

    }


    /**
    *@Description 工程新闻 接口
    *@Param category_id 工程新闻id
    *@Return
    *@Date 2019-12-27
    */
    @RequestMapping("/gcinfo")
    @SysLog(desc = "工程新闻")
    public AjaxJson gcInfo(
            @RequestParam(value = "category_id") String category_id,
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize){

        Info info = new Info();
        info.setCategoryId(category_id);
        Page<Info> page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        Page<Info> listPageFinal = infoService.findPage(page, info);
        return AjaxJson.ok(listPageFinal);
    }


    @RequestMapping("/getById")
    @SysLog(desc = "查看info详情")
    public AjaxJson getById(@RequestParam(value = "id") String id) {
        Info info = null;
        try {
            info = infoService.get(id);
            if (info == null) {
                return AjaxJson.fail("参数有误：" + id);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("info 查看详情信息，出现异常：" + e.getMessage());
        }

        return AjaxJson.ok(info);

    }


}