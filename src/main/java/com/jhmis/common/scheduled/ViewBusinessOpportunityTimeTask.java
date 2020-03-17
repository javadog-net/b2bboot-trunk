package com.jhmis.common.scheduled;

import com.jhmis.core.web.BaseController;
import com.jhmis.modules.customer.entity.*;
import com.jhmis.modules.customer.service.ViewBusinessOpportunityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.scheduled
 * @Author: hdx
 * @CreateTime: 2020-02-13 20:01
 * @Description: hps履约相关
 */

@Component
@Configurable
@EnableScheduling
public class ViewBusinessOpportunityTimeTask extends BaseController {

    @Autowired
    @Qualifier("secondJdbcTemplate")
    protected JdbcTemplate jdbcTemplate1;

    @Autowired
    protected ViewBusinessOpportunityService viewBusinessOpportunityService;


    //每个小时执行一次
    //@Scheduled(cron = "0 0 */1 * * ?")
    //一分钟执行一次
    //@Scheduled(cron = "0 */1 * * * ?")
    //每天执行一次
    @Scheduled(cron = "0 0 1 * * ? ")
    public void viewBusinessOpportunityUpdate(){
        logger.error("hps履约相关 定时任务开始" + new Date());
        try{
            //查询view_QYG_project 视图
            String view_business_opportunity_select_sql = "select * from view_business_opportunity";
            List<ViewBusinessOpportunity> listViewBusinessOpportunity = viewBusinessOpportunityService.findTaskList(new ViewBusinessOpportunity());
            //如果不存在则全部更新
            if(null==listViewBusinessOpportunity||listViewBusinessOpportunity.size()==0){
                List<ViewBusinessOpportunity> viewList = jdbcTemplate1.query(view_business_opportunity_select_sql, new BeanPropertyRowMapper(ViewBusinessOpportunity.class));
                for(ViewBusinessOpportunity viewBusinessOpportunity:viewList){
                    //全部入库
                    viewBusinessOpportunityService.save(viewBusinessOpportunity);
                }
            }else{
                DateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式化一下时间
                Date dNow = new Date(); //当前时间
                Date dBefore = new Date();
                Calendar calendar = Calendar.getInstance(); //得到日历
                calendar.setTime(dNow);//把当前时间赋给日历
                calendar.add(Calendar.DAY_OF_MONTH, -100); //设置为前一天
                dBefore = calendar.getTime(); //得到前一天的时间
                String defaultStartDate = dateFmt.format(dBefore); //格式化前一天
                defaultStartDate = defaultStartDate.substring(0,10)+" 00:00:00";

                Calendar endcalendar = Calendar.getInstance(); //得到日历

                endcalendar.setTime(dNow);//把当前时间赋给日历

                Date dEnd = endcalendar.getTime(); //得到前一天的时间

                String defaultEndDate = dateFmt.format(dEnd);
                //定量更新
                String view_business_opportunity_update_sql = "select * from view_business_opportunity where lastModifiedDateBrown BETWEEN '"+ defaultStartDate+"' and '" + defaultEndDate+ "'";
                List<ViewBusinessOpportunity> viewList = jdbcTemplate1.query(view_business_opportunity_update_sql, new BeanPropertyRowMapper(ViewBusinessOpportunity.class));
                for(ViewBusinessOpportunity viewBusinessOpportunity:viewList){
                    //全部入库
                    viewBusinessOpportunityService.save(viewBusinessOpportunity);
                }
            }
        }catch (Exception e){
         logger.error("hps履约相关定时任务错误=" + e.getMessage());
        }
        logger.error("hps履约相关 定时任务结束" + new Date());
    }

    public static void main(String[] args) {
        DateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式化一下时间

        Date dNow = new Date(); //当前时间

        Date dBefore = new Date();

        Calendar calendar = Calendar.getInstance(); //得到日历

        calendar.setTime(dNow);//把当前时间赋给日历

        calendar.add(Calendar.DAY_OF_MONTH, -100); //设置为前一天

        dBefore = calendar.getTime(); //得到前一天的时间

        String defaultStartDate = dateFmt.format(dBefore); //格式化前一天

        defaultStartDate = defaultStartDate.substring(0,10)+" 00:00:00";

        Calendar endcalendar = Calendar.getInstance(); //得到日历

        endcalendar.setTime(dNow);//把当前时间赋给日历

        Date dEnd = endcalendar.getTime(); //得到前一天的时间

        String defaultEndDate = dateFmt.format(dEnd);

    }
}
