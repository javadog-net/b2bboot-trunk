package com.jhmis.modules.cms.utils;

import com.haier.http.client.utils.DateUtil;
import com.jhmis.common.utils.SpringContextHolder;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.entity.Htmlquartz;
import com.jhmis.modules.cms.service.CategoryService;
import com.jhmis.modules.cms.service.CmsConfigService;
import com.jhmis.modules.cms.service.HtmlService;
import com.jhmis.modules.cms.service.HtmlquartzService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.*;

/**
 * <p>Title: HtmlJob.java</p>
 *
 * <p>Description: 静态化任务</p>
 *
 * <p>Date: Jan 24, 2013</p>
 *
 * <p>Time: 7:31:03 PM</p>
 *
 * <p>Copyright: 2013</p>
 *
 * <p>Company: b2b</p>
 *
 * @author tity
 * @version 1.0
 *
 *
 * <p>============================================</p>
 * <p>Modification History
 * <p>Mender: </p>
 * <p>Date: </p>
 * <p>Reason: </p>
 * <p>============================================</p>
 */
//TODO  不用本系统中的Task  因为有线程问题
@Component
public class HtmlJob {

    @Resource
    private HtmlquartzService htmlquartzService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private CmsConfigService cmsConfigService;
    @Resource
    private HtmlService htmlService;
    private static ServletContext context = SpringContextHolder.getBean(ServletContext.class);

    public void work() {
        //查询所有没有下次执行时间的任务
        Htmlquartz htmlquartz = new Htmlquartz();
        htmlquartz.setNextextTimeFlag("1");
        List<Htmlquartz> list = htmlquartzService.findList(htmlquartz);
        if (list != null && list.size() > 0) {
            for (Htmlquartz htmlquartz2 : list) {
                //计算下次执行时间
                setNextexetime(htmlquartz2);
            }
        }
        //查询所有有下次执行时间并且小于当前时间的任务
        htmlquartz = new Htmlquartz();
        htmlquartz.setNextextTimeFlag("2");

        list = htmlquartzService.findList(htmlquartz);
        if (list != null && list.size() > 0) {
            Map<String, Object> data = new HashMap<>();
            data.put("contextPath", BaseController.getContextPathNoReplaceStr(context.getContextPath()) + "/");
            data.put("contextPathNo", BaseController.getContextPathNoReplaceStr(context.getContextPath()));
            data.put("webpath", context.getRealPath("/"));
            Category category = null;
            //先更新执行时间，防止重复执行
            for (Htmlquartz htmlquartz2 : list) {
                //记录最后一次执行时间
                htmlquartz2.setLastexeTime(new Date());
                //计算下次执行时间
                setNextexetime(htmlquartz2);
            }
            for (Htmlquartz htmlquartz2 : list) {
                //执行任务
                if (StringUtils.isNotEmpty(htmlquartz2.getCategoryId())) {
                    //栏目静态化
                    try {
                        category = categoryService.findUniqueByProperty("id", category.getId());
                        data.put("currCategory", category);
                        htmlService.htmlCategory(data, category, 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (CmsEnum.HTML_QUARTZ.getCode().equals(htmlquartz2.getObjtype())) {
                    //全站静态化
                    try {
                        htmlService.htmlAllDo(context, data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //首页静态化
                    try {
                        htmlService.htmlIndex(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //记录最后一次执行时间
                htmlquartz2.setLastexeTime(new Date());
                //计算下次执行时间
                setNextexetime(htmlquartz2);
            }
        }
    }

    /**
     * 计算下次执行时间
     *
     * @param htmlquartz
     */
    public void setNextexetime(Htmlquartz htmlquartz) {
        Date nextexetime = new Date();
        if (StringUtils.isNotEmpty(htmlquartz.getType())) {
            if ("0".equals(htmlquartz.getType())) {
                //定时生成
                //生成今天应该执行的时间
                nextexetime = DateUtil.parse(
                        DateUtil.format(new Date(), "yyyy-MM-dd") + " "
                                + (htmlquartz.getExetimeHour() > 9 ? "" : "0") + htmlquartz.getExetimeHour() + ":"
                                + (htmlquartz.getExetimeMin() > 9 ? "" : "0") + htmlquartz.getExetimeMin() + ":00", "yyyy-MM-dd HH:mm:ss");
                if (new Date().getTime() > nextexetime.getTime()) {
                    //今天已过，生成明天应该执行的时间
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(nextexetime);
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    nextexetime = calendar.getTime();
                }
            } else if ("1".equals(htmlquartz.getType())) {
                //间隔重复生成
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(nextexetime);
                if ("0".equals(htmlquartz.getIntervalType())) {
                    //小时
                    calendar.add(Calendar.HOUR, htmlquartz.getIntervalHour());
                } else if ("1".equals(htmlquartz.getIntervalType())) {
                    //分钟
                    calendar.add(Calendar.MINUTE, htmlquartz.getIntervalMin());
                }
                nextexetime = calendar.getTime();
            }
            //更新到数据库
            htmlquartz.setNextexeTime(nextexetime);
            htmlquartzService.save(htmlquartz);
        } else {
            //一次性任务 直接删除调度
            htmlquartzService.delete(htmlquartz);
        }
    }
}
