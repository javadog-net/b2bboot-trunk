package com.jhmis.api.mobiles;

import com.jhmis.common.annotation.SysLog;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.entity.*;
import com.jhmis.modules.cms.mapper.CommentMapper;
import com.jhmis.modules.cms.service.*;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.StoreGoodsService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @program: b2bboot
 * @description: 移动端info接口
 * @author: T.c
 * @create: 2019-11-06 10:51
 **/
@RestController
@RequestMapping("/api/mobiles/cmsinfo")
public class MobilesApiCmsInfoController extends BaseController {
    @Autowired
    private InfoService infoService;
    @Autowired
    private CmsIslikeService cmsIslikeService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CategoryService categoryService;
    @Resource
    private SensitiveService sensitiveService;
    @Resource
    private InfoProductService infoProductService;
    @Resource
    private StoreGoodsService storeGoodsService;

    /**
     * @Description 提供给 产品讲坛 和 新闻 列表接口
     * @Param family_level2 产品讲坛 1级分类
     * family_level3 产品讲坛中的产品经 分类
     * family 新闻的分类
     * release_type 查询平台 （移动端、所有平台） 默认移动、PC
     * @Return
     * @Author t.c
     * @Date 2019-11-06
     */
    @RequestMapping("/findListInfo")
    @SysLog(desc = "提供给 产品讲坛 和 新闻 列表接口")
    public AjaxJson findListInfo(
            @RequestParam(value = "category_id") String category_id,
            @RequestParam(value = "family", required = false) String family,
            @RequestParam(value = "family_level2", required = false) String family_level2,
            @RequestParam(value = "family_level3", required = false) String family_level3,
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "release_type", required = false, defaultValue = "3") String release_type) {
        Page<Info> listPageFinal = null;
        Info info = new Info();
        try {
            if (StringUtils.isNotEmpty(category_id)) {
                info.setCategoryId(category_id);
            }
            if (StringUtils.isNotEmpty(family)) {
                info.setFamily(family);
            }
            if (StringUtils.isNotEmpty(family_level2)) {
                info.setFamilyLevel2(family_level2);
            }
            if (StringUtils.isNotEmpty(family_level3)) {
                info.setFamilyLevel3(family_level3);
            }
            if (StringUtils.isNotEmpty(release_type)) {
                info.setReleaseType(release_type);
            }
            List<Info> listInfomationTop = null;
            //如果是第一页 并且是 新闻的 information 就要查询固顶的
            if (pageNo == 1 && ("1fc60747-8b96-4099-b3c3-bd47d0b7767a").equals(category_id)) {
                Page<Info> page = new Page();
                page.setOrderBy("is_top desc");
                page.setPageSize(3);//只查询前三条 当作banner图处理
                page.setPageNo(1);
                Page<Info> listPage = infoService.findPage(page, info);
                listInfomationTop = listPage.getList();
               /* //推荐  2条 按照设计图来的，没有规律可言，以后肯定会再释放此处代码。
                Page<Info> page2 = new Page();
                page2.setOrderBy("is_recommend desc");
                info.setIsTop("0");//只查询非置顶的
                page2.setPageSize(2);//只查询前2条 当作推荐处理
                page2.setPageNo(1);
                Page<Info> listPageRecommend = infoService.findPage(page2, info);
                listInfomationTop.addAll(listPageRecommend.getList());//将推荐的 放在固顶的屁股后面*/

            }
            Page<Info> page = new Page();
            page.setPageNo(pageNo);
            page.setPageSize(pageSize);
            info.setIsTop("0");//只查询非置顶的
            listPageFinal = infoService.findPage(page, info);
            //将 固顶的 放在最前面
            if (listInfomationTop != null && listInfomationTop.size() > 0) {
                for (int i = 0; i < listInfomationTop.size(); i++) {
                    listPageFinal.getList().add(i, listInfomationTop.get(i));
                }
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
     * @Date 2019-11-06
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
            if (null != account) {
                CmsIslike cmsIslike = new CmsIslike();
                cmsIslike.setInfoId(id);
                cmsIslike.setUserId(account.getId());
                List<CmsIslike> list = cmsIslikeService.findList(cmsIslike);
                if (list != null && list.size() > 0) {//说明 点赞了，添加点赞标识
                    info.setIsLike("1");
                }
            } else {
                info.setIsLike("0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("info 查看详情信息，出现异常：" + e.getMessage());
        }

        return AjaxJson.ok(info);

    }

    /**
     * @Description 提供给移动端案例列表查询
     * @Param category_id 案例栏目的id
     * release_type 发布类型 默认是 3 移动、PC
     * @Return
     * @Author t.c
     * @Date 2019-11-07
     */
    @RequestMapping("/getlistcasebycategoryid")
    @SysLog(desc = "案例列表查询")
    public AjaxJson getListCaseByCategoryId(
            @RequestParam(value = "category_id") String category_id,
            @RequestParam(value = "industry", required = false) String industry,
            @RequestParam(value = "case_products", required = false) String case_products,
            @RequestParam(value = "release_type", required = false, defaultValue = "3") String release_type,
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Page<Info> listPageFinal = null;
        try {
            Info info = new Info();
            if (StringUtils.isNotEmpty(release_type)) {
                info.setReleaseType(release_type);
            }
            if (StringUtils.isNotEmpty(category_id)) {
                info.setCategoryId(category_id);
            }
            if (StringUtils.isNotEmpty(industry)) {
                info.setIndustry(industry);
            }
            if (StringUtils.isNotEmpty(case_products)) {
                info.setCaseProducts(case_products);
            }
            Page<Info> page = new Page();
            page.setPageNo(pageNo);
            page.setPageSize(pageSize);
            listPageFinal = infoService.findPage(page, info);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());

        }
        return AjaxJson.ok(listPageFinal);

    }

    /**
     * @Description info信息页的点赞和取消点赞
     * @Param id 信息页的id  还要传入token
     * @Return
     * @Author t.c
     * @Date 2019-11-07
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
                msg = "抱歉系统异常，找不到该文章信息！";
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
            logger.info("信息详情页 点赞或取消点赞异常:" + e.getMessage());
        }

        return AjaxJson.ok(msg);
    }

    /**
     * @Description 商空博闻
     * @Param 商空博闻 栏目id category_id=4c724913-e272-4dd7-bae4-15ae73124b6a
     * 实战案例：family=1，暖通百科：family=2，市场解读：family=3
     * @Return
     * @Author t.c
     * @Date 2019-11-07
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
     * @Description 添加评论
     * @Param id 信息的id  必传
     * category_id 信息栏目的id  必传
     * parent_id 回复给谁的id   有则传，没有不传
     * reply_to 回复给人的 loginName  有则传，没有不传
     * content 评论内容 必传
     * @Return
     * @Author t.c
     * @Date 2019-11-14
     */
    @RequestMapping(value = "/addcommentinfo")
    public AjaxJson addCommentInfo(@RequestParam(value = "id") String id,
                                   @RequestParam(value = "category_id") String category_id,
                                   @RequestParam(value = "parent_id", required = false) String parent_id,
                                   @RequestParam(value = "reply_to", required = false) String reply_to,
                                   @RequestParam(value = "content") String content) {

        try {
            PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
            if (account == null) {
                return AjaxJson.fail("401", "请重新登录！");
            }
            Info info = infoService.get(id);
            if (info == null) {
                return AjaxJson.fail("抱歉，未查询到该信息！");
            }
            Comment comment = new Comment();
            comment.setContentId(id);
            comment.setTitle(info.getTitle());
            if (null != account.getAvatar() && !"".equals(account.getAvatar())) {
                comment.setAvatar(account.getAvatar());
            }
            comment.setContent(sensitiveService.replace(content));
            comment.setCreateDate(new Date());
            comment.setUserName(account.getLoginName());
            comment.setPurchaserId(account.getId());
            comment.setCategoryId(category_id);
            if (StringUtils.isNotBlank(reply_to)) {
                comment.setReplyTo(reply_to);
            }
            if (StringUtils.isNotBlank(parent_id)) {
                comment.setParentId(parent_id);
            }
            commentService.save(comment);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return AjaxJson.ok("保存评论成功！");
    }



   /* @RequestMapping("/skboweninfo")
    @SysLog(desc = "商空博闻")
    public AjaxJson getCategoryInfo(
            @RequestParam(value = "category_id") String category_id,
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize{
        Category category1=new Category();
        category1.setId(category_id);
        Category category=new Category();
        category.setParent(category1);
          List<Category> list=categoryService.findList(category);
          if(null != list){

          }


        Info info = new Info();
        info.setCategoryId(category_id);

            Page<Info> page = new Page();
            page.setPageNo(pageNo);
            page.setPageSize(pageSize);
            Page<Info> listPageFinal = infoService.findPage(page, info);

            return AjaxJson.ok(listPageFinal);


    }
*/

    /**
     * @Description 提供给移动端的 相关产品推荐的接口
     * @Param id 文章的id
     * @Return
     * @Author t.c
     * @Date 2019-12-13
     */
    @RequestMapping("/findinfoproduct")
    public AjaxJson findInfo_Product(String id) {
        if (StringUtils.isBlank(id)) {
            return AjaxJson.fail("参数有误！！");
        }
        Info info = infoService.get(id);
        if (null == info) {
            return AjaxJson.ok("文章有误，请重新尝试！");
        }
        InfoProduct infoProduct = new InfoProduct();
        infoProduct.setInfoId(info.getId());
        List<InfoProduct> list = infoProductService.findList(infoProduct);
        String goodsMainUrl = "";
        for (InfoProduct ip : list) {
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(ip.getMainPicUrl())) {
                if (ip.getMainPicUrl().indexOf("|") > 0) {
                    goodsMainUrl = ip.getMainPicUrl().split("\\|")[0];
                    ip.setMainPicUrl(goodsMainUrl);
                }
            }
        }
        return AjaxJson.ok(list);

    }


}