package com.jhmis.config;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.customer.entity.*;
import com.jhmis.modules.customer.service.*;
import com.jhmis.modules.shop.entity.MdmCustomersPartner;
import com.jhmis.modules.shop.entity.MdmCustomersSource;
import com.jhmis.modules.shop.service.MdmCustomersPartnerService;
import com.jhmis.modules.shop.service.MdmCustomersSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author：hdx
 * @Description: 客单定时
 * @Date: Created in 9:30 2019/3/26
 * @Modified By
 */



@Component
@Configurable
@EnableScheduling

public class CustomerTimeTask {
    @Autowired
    @Qualifier("secondJdbcTemplate")
    protected JdbcTemplate jdbcTemplate1;

    @Autowired
    protected CustomerProjectInfoService customerProjectInfoService;

    @Autowired
    protected CustomerProjectProductService customerProjectProductService;

    @Autowired
    protected CustomerProjectProductDetailService customerProjectProductDetailService;

    @Autowired
    protected ViewQygBrownItemService viewQygBrownItemService;

    @Autowired
    protected ViewQygBrownService viewQygBrownService;

    @Autowired
    protected ViewQygBrownInfoService viewQygBrownInfoService;
    
    @Autowired
    protected ViewQygBrownExceptionInfoService viewQygBrownExceptionInfoService;
	

    //0 0 1 * * ? 每天1点执行一次---漏斗项目数据视图
   //生产时间，暂时 注解了  
    @Scheduled(cron = "0 0 1 * * ? ")
    //@Scheduled(cron = "*/15 * * * * ?")
    @Transactional
    public void getView_QYG_project(){
        //查询view_QYG_project 视图
        String view_QYG_project_select_sql = "select * from view_QYG_project";
        String view_QYG_project_delete_sql = "delete from customer_project_info where projectcode like '%P%'";
        //先删后插
        customerProjectInfoService.executeDeleteSql(view_QYG_project_delete_sql);
        //根据实体反射进行转换
        List<CustomerProjectInfo> listCustomerProjectInfo = jdbcTemplate1.query(view_QYG_project_select_sql, new BeanPropertyRowMapper(CustomerProjectInfo.class));
        if(listCustomerProjectInfo!=null && listCustomerProjectInfo.size()>0){
            //证明有效逐条插入
            for(CustomerProjectInfo customerProjectInfo:listCustomerProjectInfo){
                customerProjectInfoService.save(customerProjectInfo);
            }
        }
        //处理商品相关信息
        //view_QYG_product_group 视图
        String view_QYG_product_group_select_sql = "select * from view_QYG_product_group";
        String view_QYG_product_group_delete_sql = "delete from customer_project_product where projectcode like '%P%'";
        //先删后插
        customerProjectProductService.executeDeleteSql(view_QYG_product_group_delete_sql);
        //根据实体反射进行转换
        List<CustomerProjectProduct> listCustomerProjectProduct = jdbcTemplate1.query(view_QYG_product_group_select_sql, new BeanPropertyRowMapper(CustomerProjectProduct.class));
        if(listCustomerProjectProduct!=null && listCustomerProjectProduct.size()>0){
            //证明有效逐条插入
            for(CustomerProjectProduct customerProjectProduct:listCustomerProjectProduct){
                customerProjectProductService.save(customerProjectProduct);
            }
        }

        //处理商品详情相关信息
        //view_QYG_project_quote_product 视图
        String view_QYG_project_quote_product_select_sql = "select * from view_QYG_project_quote_product";
        String view_QYG_project_quote_product_delete_sql = "delete from customer_project_product_detail where projectcode like '%P%'";
        //先删后插
        customerProjectProductDetailService.executeDeleteSql(view_QYG_project_quote_product_delete_sql);
        //根据实体反射进行转换
        List<CustomerProjectProductDetail> listCustomerProjectProductDetail = jdbcTemplate1.query(view_QYG_project_quote_product_select_sql, new BeanPropertyRowMapper(CustomerProjectProductDetail.class));
        if(listCustomerProjectProductDetail!=null && listCustomerProjectProductDetail.size()>0){
            //证明有效逐条插入
            for(CustomerProjectProductDetail customerProjectProductDetail:listCustomerProjectProductDetail){
                customerProjectProductDetailService.save(customerProjectProductDetail);
            }
        }
    }



    //0 0 1 * * ? 每天1点执行一次---获取hps工程板详情数据
    //@Scheduled(cron = "0 0 1 * * ? ")
   
  //  暂时注销  生产 时间     
    @Scheduled(cron = "*/30 * * * * ?")
    @Transactional
    public void getviewQYGBrownItem(){
        //查询view_QYG_project 视图
        String view_QYG_brown_item_delete_sql = "delete from view_QYG_brown_item";
        //获取当前系统时间
        long tEnd = System.currentTimeMillis();
        //获取前30分钟时间
        long tStart = System.currentTimeMillis()-1000*60*30;
        String tStartStr = getDate(tStart);
        String tEndStr = getDate(tEnd);
        //查询view_QYG_project 视图
        String view_QYG_brown_item_select_sql = "select * from view_QYG_brown_item where last_modified_date between '"+ tStartStr +"' and '"+ tEndStr +"'";
        //查询全部内容
        String view_QYG_brown_item_select_all_sql = "select * from view_QYG_brown_item";
        //首先判断本地库中是否有值
        try{
            ViewQygBrownItem viewQygBrownItem = new ViewQygBrownItem();
            List<ViewQygBrownItem> listViewQygBrownItem = viewQygBrownItemService.findByMine(viewQygBrownItem);
            //判断list是否有值
            if(listViewQygBrownItem==null||listViewQygBrownItem.size()==0){
                //证明没有数据则全部拉入
                List<ViewQygBrownItem> viewQygBrownItemListByHps = jdbcTemplate1.query(view_QYG_brown_item_select_all_sql, new BeanPropertyRowMapper(ViewQygBrownItem.class));
                saveListItemViewQyg(viewQygBrownItemListByHps);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //根据实体反射进行转换
        List<ViewQygBrownItem> listItemViewQygBrown = jdbcTemplate1.query(view_QYG_brown_item_select_sql, new BeanPropertyRowMapper(ViewQygBrownItem.class));
        if(listItemViewQygBrown!=null && listItemViewQygBrown.size()>0){
            //证明有效逐条插入，判断更新时间是更新还是
            for(ViewQygBrownItem viewQygBrownItem:listItemViewQygBrown){
                //如果存在则更新先删后插，不存在则插入
                ViewQygBrownItem vib = viewQygBrownItemService.get(viewQygBrownItem.getId());
                if(vib==null){
                    //不存在
                    viewQygBrownItemService.save(viewQygBrownItem);
                }else{
                    //存在先删后插
                    String view_qyg_brown_item_delete_sql = "delete from view_QYG_brown_item where id='"+ vib.getId() +"'";
                    //先删后插
                    viewQygBrownItemService.executeDeleteSql(view_qyg_brown_item_delete_sql);
                    //插入
                    viewQygBrownItemService.save(vib);
                }
            }
        }

    }

    //0 0 1 * * ? 每天1点执行一次---获取hps工程板详情数据
    //@Scheduled(cron = "0 0 1 * * ? ")
    //*/30 * * * * ? 每隔30分钟执行一次
    // 生产时间 暂时注解 
    @Scheduled(cron = "*/30 * * * * ?")
    @Transactional
    public void getviewQYGBrown(){
        //获取当前系统时间
        long tEnd = System.currentTimeMillis();
     //获取前30分钟时间
     long tStart = System.currentTimeMillis()-1000*60*30;

     String tStartStr = getDate(tStart);
     String tEndStr = getDate(tEnd);
        //查询view_QYG_project 视图
        String view_qyg_brown_select_sql = "select * from view_QYG_brown where last_modified_date between '"+ tStartStr +"' and '"+ tEndStr +"'";
        //查询全部内容
        String view_qyg_brown_select_all_sql = "select * from view_QYG_brown";
        //首先判断本地库中是否有值
        try{
            ViewQygBrown viewQygBrown = new ViewQygBrown();
            List<ViewQygBrown> listViewQygBrown = viewQygBrownService.findByMine(viewQygBrown);
            //判断list是否有值
            if(listViewQygBrown==null||listViewQygBrown.size()==0){
                //证明没有数据则全部拉入
                List<ViewQygBrown> viewQygBrownListByHps = jdbcTemplate1.query(view_qyg_brown_select_all_sql, new BeanPropertyRowMapper(ViewQygBrown.class));
                saveListViewQyg(viewQygBrownListByHps);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //根据实体反射进行转换
        List<ViewQygBrown> listViewQygBrown = jdbcTemplate1.query(view_qyg_brown_select_sql, new BeanPropertyRowMapper(ViewQygBrown.class));
        if(listViewQygBrown!=null && listViewQygBrown.size()>0){
            //证明有效逐条插入，判断更新时间是更新还是
            for(ViewQygBrown viewQygBrown:listViewQygBrown){
                //如果存在则更新先删后插，不存在则插入
                ViewQygBrown vb = viewQygBrownService.get(viewQygBrown.getBrownId());
                if(vb==null){
                    //不存在
                    viewQygBrownService.save(viewQygBrown);
                }else{
                    //存在先删后插
                    String view_qyg_brown_delete_sql = "delete from view_qyg_brown where brown_id='"+ vb.getBrownId() +"'";
                    //先删后插
                    viewQygBrownService.executeDeleteSql(view_qyg_brown_delete_sql);
                    //插入
                    viewQygBrownService.save(vb);
                }
            }
        }
    }


    //0 0 * * * ? 每天整点执行---获取hps工程版可验收列表
 	@Scheduled(cron = "0 0 * * * ?")
    @Transactional
    public void getviewQYGBrownInfo(){
		//获取当前系统时间
	     long tEnd = System.currentTimeMillis();
	     //获取前1天时间
	     long tStart = System.currentTimeMillis()-1000*60*60*24L;
	
	     String tStartStr = getDate(tStart);
	     String tEndStr = getDate(tEnd);
        //查询view_QYG_project 视图
        String view_qyg_brown_info_select_sql = "select * from view_QYG_brown_info where last_modified_date between '"+ tStartStr +"' and '"+ tEndStr +"'";
        
        //根据实体反射进行转换
        List<ViewQygBrownInfo> listViewQygBrownInfo = jdbcTemplate1.query(view_qyg_brown_info_select_sql, new BeanPropertyRowMapper(ViewQygBrownInfo.class));
        if(listViewQygBrownInfo!=null && listViewQygBrownInfo.size()>0){
            //证明有效逐条插入
            for(ViewQygBrownInfo viewQygBrownInfo:listViewQygBrownInfo){
            	ViewQygBrownInfo brownInfo = viewQygBrownInfoService.get(viewQygBrownInfo.getId());           	
            	if(brownInfo==null){
            		viewQygBrownInfoService.insert(viewQygBrownInfo);
            	}else{
            		viewQygBrownInfoService.update(viewQygBrownInfo);
            	}
            }
        }
    
    }
 	
    //0 0 2 * * ? 每天2点执行一次---获取hps工程版可验收列表
//生产时间 暂时注解 
 	@Scheduled(cron = "0 30 2 * * ?")
    @Transactional
    public void getviewQYGBrownInfoTwo(){
		
        //查询view_QYG_project 视图
        String view_qyg_brown_info_select_sql = "select * from view_QYG_brown_info order by last_modified_date desc";
        
        //根据实体反射进行转换
        List<ViewQygBrownInfo> listViewQygBrownInfo = jdbcTemplate1.query(view_qyg_brown_info_select_sql, new BeanPropertyRowMapper(ViewQygBrownInfo.class));
        if(listViewQygBrownInfo!=null && listViewQygBrownInfo.size()>0){
            //证明有效逐条插入
            for(ViewQygBrownInfo viewQygBrownInfo:listViewQygBrownInfo){
            	ViewQygBrownInfo brownInfo = viewQygBrownInfoService.get(viewQygBrownInfo.getId());           	
            	if(brownInfo==null){
            		viewQygBrownInfoService.insert(viewQygBrownInfo);
            	}else{
            		viewQygBrownInfoService.update(viewQygBrownInfo);
            	}
            }
        }
    
    }
 	
 	@Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void getviewQYGBrownExceptionInfo(){

        String view_qyg_brownexception_info_select_sql = "select * from view_QYG_brownException_info";
        String view_qyg_brownexception_info_delete_sql = "delete from view_qyg_brownexception_info";
        //先删后插
        viewQygBrownExceptionInfoService.executeDeleteSql(view_qyg_brownexception_info_delete_sql);
        //根据实体反射进行转换
        List<ViewQygBrownExceptionInfo> listViewQygBrownExceptionInfo = jdbcTemplate1.query(view_qyg_brownexception_info_select_sql, new BeanPropertyRowMapper(ViewQygBrownExceptionInfo.class));
        if(listViewQygBrownExceptionInfo!=null && listViewQygBrownExceptionInfo.size()>0){
            //证明有效逐条插入
            for(ViewQygBrownExceptionInfo viewQygBrownExceptionInfo:listViewQygBrownExceptionInfo){
            	viewQygBrownExceptionInfoService.insert(viewQygBrownExceptionInfo);
            }
        }
    }


    /**
     * @Author：hdx
     * @Description: 毫秒数转化时间日期
     * @Date: Created in 9:30 2019/3/26
     * @Modified By
     */
    public static String getDate(long currentTime){
        Date date2=new Date();
        date2.setTime(currentTime);
        System.err.println(date2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fmDate=simpleDateFormat.format(date2);
        return fmDate;
    }
    /**
     * @Author：hdx
     * @Description: 循环存入数据
     * @Date: Created in 9:30 2019/3/26
     * @Modified By
     */
    public void saveListViewQyg(List<ViewQygBrown> listViewQygBrown){
        for(ViewQygBrown viewQygBrown:listViewQygBrown){
            viewQygBrownService.save(viewQygBrown);
        }
    }

    /**
     * @Author：hdx
     * @Description: 循环存入数据
     * @Date: Created in 9:30 2019/3/26
     * @Modified By
     */
    public void saveListItemViewQyg(List<ViewQygBrownItem> listItemViewQygBrown){
        for(ViewQygBrownItem viewQygBrownItem:listItemViewQygBrown){
            viewQygBrownItemService.save(viewQygBrownItem);
        }
    }

}
