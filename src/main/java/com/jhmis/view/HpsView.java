package com.jhmis.view;

import com.jhmis.modules.customer.entity.CustomerProjectInfo;
import com.jhmis.modules.customer.entity.CustomerProjectProduct;
import com.jhmis.modules.customer.entity.CustomerProjectProductDetail;
import com.jhmis.modules.customer.entity.ViewQygBrownItem;
import com.jhmis.modules.customer.service.CustomerProjectInfoService;
import com.jhmis.modules.customer.service.CustomerProjectProductDetailService;
import com.jhmis.modules.customer.service.CustomerProjectProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author：hdx
 * @Description: hps定时任务
 * @Date: Created in 9:30 2019/3/26
 * @Modified By
 */
@Component
public class HpsView {
    @Autowired
    @Qualifier("secondJdbcTemplate")
    protected JdbcTemplate jdbcTemplate1;

    @Autowired
    protected CustomerProjectInfoService customerProjectInfoService;

    @Autowired
    protected CustomerProjectProductService customerProjectProductService;

    @Autowired
    protected CustomerProjectProductDetailService customerProjectProductDetailService;


    public void getViewQYGbrown(){
        //查询ViewQYGbrown 视图
        String view_QYG_project_select_sql = "select * from view_QYG_project";
        String view_QYG_project_delete_sql = "delete from customer_project_info";
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
        String view_QYG_product_group_delete_sql = "delete from customer_project_product";
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
        String view_QYG_project_quote_product_delete_sql = "delete from customer_project_product_detail";
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

    /**
     * @Author：hdx
     * @Description: 获取hps工程板详情数据(参数brown_id)
     * @Date: Created in 9:30 2019/3/26
     * @Modified By
     */
    public List<ViewQygBrownItem> getviewQYGBrownItem(String brown_id){
        String view_QYG_brown_item_sql = "select * from view_QYG_brown_item where brown_id ='" + brown_id + "'";
        List<ViewQygBrownItem> listViewQygBrownItem = jdbcTemplate1.query(view_QYG_brown_item_sql, new BeanPropertyRowMapper(ViewQygBrownItem.class));
        return listViewQygBrownItem;
    }


}
