package com.jhmis.api.app;

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
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: b2bboot
 * @description: 提供给app的info信息接口
 * @author: T.c
 * @create: 2019-11-15 14:58
 **/
@RestController
@RequestMapping("/api/app/cmsinfo")
public class AppApiInfoController extends BaseController {

    @Autowired
    private InfoService infoService;
    @Autowired
    private CmsIslikeService cmsIslikeService;
    @Autowired
    private CommentService commentService;


    @RequestMapping("/findListInfo")
    @SysLog(desc = "提供给商空产品讲坛的接口")
    public AjaxJson findListInfo (
            @RequestParam(value = "category_id") String category_id,
            @RequestParam(value = "family_level2", required = false) String family_level2,
            @RequestParam(value = "family_level3", required = false) String family_level3,
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Page<Info> listPageFinal = new Page<>();
        Info info = new Info();
        try {
            if (StringUtils.isNotEmpty(category_id)) {
                info.setCategoryId(category_id);
            }
            if (StringUtils.isNotEmpty(family_level2)) {
                info.setFamilyLevel2(family_level2);
            }
            if (StringUtils.isNotEmpty(family_level3)) {
                info.setFamilyLevel3(family_level3);
            }
            //规定 pageNo =0 的时候 说明是 大讲堂的列表页，则，查询：精选推荐，购机必读，产品百科，热议话题 分别一个置顶的
            // 和 值得购 分页
            if (pageNo == 0) {
            List<Info> listTop = new ArrayList<>();
                for(int i=0;i<4;i++) {
                    Page<Info> page = new Page();
                    page.setOrderBy("is_top desc");
                    info.setFamilyLevel2("0");
                    info.setFamilyLevel3(String.valueOf(i));
                    page.setPageSize(1);//只查询第1条 当作banner图处理
                    page.setPageNo(1);
                    Page<Info> listPage = infoService.findPage(page, info);
                    listTop.addAll(listPage.getList());
                }

                Page<Info> page = new Page();
                info.setFamilyLevel2("2");//值得够
                page.setPageSize(100);//
                page.setPageNo(1);
                Page<Info> listPage = infoService.findPage(page, info);
                listTop.addAll(listPage.getList());
                listPageFinal.setList(listTop);
            }else {
                Page<Info> page = new Page();
                page.setPageNo(pageNo);
                page.setPageSize(pageSize);
                listPageFinal = infoService.findPage(page, info);
            }
            //添加评论数量
            for (Info in : listPageFinal.getList()) {
                Integer i = commentService.selectCountById(in.getId());
                in.setCommentNum(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("移动端 提供给 产品讲坛 和 新闻 列表接口,出现异常信息：" + e.getMessage());
        }
        return AjaxJson.ok(listPageFinal);
    }


    /**
     * @Description 查看Info 详情
     * @Param id
     * @Return
     * @Author t.c
     * @Date 2019-11-15
     */
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
     * @Description info信息页的点赞和取消点赞
     * @Param id 信息页的id  还要传入token
     * @Return
     * @Author t.c
     * @Date 2019-11-015
     */
    @RequestMapping("/islike")
    @SysLog(desc = "info信息页的点赞和取消点赞")
    public AjaxJson isLike(@RequestParam(value = "id") String id) {
        String msg = "";
        try {
            PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
            if (account == null) {
                return AjaxJson.fail("401", "请重新登录！");
            }
            Info info = infoService.get(id);
            if (info == null) {
                msg="抱歉系统异常，找不到该文章信息！";
                return AjaxJson.fail(msg);
            }
            CmsIslike cmsIslike = new CmsIslike();
            cmsIslike.setInfoId(id);
            cmsIslike.setUserId(account.getId());
            List<CmsIslike> list = cmsIslikeService.findList(cmsIslike);
            if (list != null && list.size() > 0) {//说明 要取消了点赞
                cmsIslikeService.delete(list.get(0));
                msg = "已取消点赞！";
            } else {//没有点赞，添加点赞信息
                cmsIslike.setCreateTime(new Date());
                cmsIslike.setInfoIslike("1");
                cmsIslike.setInfoTitle(info.getTitle());
                cmsIslike.setUserName(account.getLoginName());
                cmsIslikeService.save(cmsIslike);
                msg = "点赞成功，感谢支持！";
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("信息详情页 点赞或取消点赞异常:"+e.getMessage());
        }

        return AjaxJson.ok(msg);
    }


    /**
     *@Description 商空博闻
     *@Param 商空博闻 栏目id category_id=4c724913-e272-4dd7-bae4-15ae73124b6a
     *      实战案例：family=1，暖通百科：family=2，市场解读：family=3
     *@Return
     *@Author t.c
     *@Date 2019-11-07
     */
    @RequestMapping("/skboweninfo")
    @SysLog(desc = "商空博闻")
    public AjaxJson skBoWenInfo(
            @RequestParam(value = "category_id") String category_id,
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "family", required = false) String family) {

        Info info = new Info();
        info.setCategoryId(category_id);
        if (StringUtils.isNotEmpty(family)) {
            info.setFamily(family);
        }
        if (pageNo == 0 && ("4c724913-e272-4dd7-bae4-15ae73124b6a").equals(category_id)) {
            Page<Info> page = new Page();
            page.setOrderBy("is_top desc");
            page.setPageSize(1);//只查询1条 当作banner图处理
            page.setPageNo(1);
            Page<Info> listPage = infoService.findPage(page, info);
            List<Info> list = listPage.getList();
            //推荐  2条
            Page<Info> page2 = new Page();
            page2.setOrderBy("is_recommend desc");
            page2.setPageSize(2);//只查询前2条 当作推荐处理
            page2.setPageNo(1);
            info.setIsTop("0");
            Page<Info> listPageRecommend = infoService.findPage(page2, info);
            list.addAll(listPageRecommend.getList());//将推荐的 放在固顶的屁股后面
            for (Info in : list) {//放入评论数量
                Integer i = commentService.selectCountById(in.getId());
                in.setCommentNum(i);
            }
            return AjaxJson.ok(list);
        } else {
            Page<Info> page = new Page();
            page.setPageNo(pageNo);
            page.setPageSize(pageSize);
            Page<Info> listPageFinal = infoService.findPage(page, info);
            for (Info in : listPageFinal.getList()) {//放入评论数量
                Integer i = commentService.selectCountById(in.getId());
                in.setCommentNum(i);
            }
            return AjaxJson.ok(listPageFinal);

        }
    }

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
            comment.setContent(content);
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





}