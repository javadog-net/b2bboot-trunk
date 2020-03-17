package com.jhmis.view;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.view.entity.CheckDataForm;
import com.jhmis.view.entity.HpsWholeProcessOrderbill;

@Component
public class WholeProcessHps {

	@Autowired
    @Qualifier("secondJdbcTemplate")
    protected JdbcTemplate jdbcTemplate;
	
    //根据实体反射进行转换
    public Map<String,Object> getWholeProcessFromHps(HpsWholeProcessOrderbill hpsWholeProcessOrderbill) throws Exception{

    	Map<String,Object> map = new HashMap<>();
        //判断必填是否为空
        if(StringUtils.isBlank(hpsWholeProcessOrderbill.getContractor_code())){
            throw new Exception("经销商编码不能为空");
        }
      
        String basic_sql = "select * from hps_whole_process_orderbill where contractor_code = '" + hpsWholeProcessOrderbill.getContractor_code() + "'";
        StringBuilder sb = new StringBuilder();
        sb.append(basic_sql);
      //判断project_code项目编码 是否为空
        if(StringUtils.isNotBlank(hpsWholeProcessOrderbill.getProject_code())){
            sb.append(" and project_code like '%" + hpsWholeProcessOrderbill.getProject_code() + "%'");
        }
      //判断project_name项目名称 是否为空
        if(StringUtils.isNotBlank(hpsWholeProcessOrderbill.getProject_name())){
            sb.append(" and project_name like '%" + hpsWholeProcessOrderbill.getProject_name() + "%'");
        } 
      //判断project_status项目状态 是否为空
        if(StringUtils.isNotBlank(hpsWholeProcessOrderbill.getProject_status())){
            sb.append(" and project_status = '" + hpsWholeProcessOrderbill.getProject_status() + "'");
        } 
      //判断funnel_stage项目节点 是否为空
        if(StringUtils.isNotBlank(hpsWholeProcessOrderbill.getFunnel_stage())){
            sb.append(" and funnel_stage = '" + hpsWholeProcessOrderbill.getFunnel_stage() + "'");
        }
      //判断dept_code产品组 是否为空
        if(StringUtils.isNotBlank(hpsWholeProcessOrderbill.getDept_code())){
            sb.append(" and dept_code = '" + hpsWholeProcessOrderbill.getDept_code() + "'");
        }
      //判断industry_home_category行业 是否为空
        if(StringUtils.isNotBlank(hpsWholeProcessOrderbill.getIndustry_home_category())){
            sb.append(" and industry_home_category = '" + hpsWholeProcessOrderbill.getIndustry_home_category() + "'");
        }
      //判断data_source 项目来源是否为空     
        if(StringUtils.isNotBlank(hpsWholeProcessOrderbill.getData_source())){
        	String dateSources = hpsWholeProcessOrderbill.getData_source();
            if (dateSources.endsWith(",")) {
            	dateSources = dateSources.substring(0,dateSources.length() - 1);
            }
        	sb.append(" and data_source in (" + dateSources + ")") ;
        }
      //判断create_date_start//项目开始时间
        if(StringUtils.isNotBlank(hpsWholeProcessOrderbill.getCreate_date_start())){
            sb.append(" and created_date > '" + hpsWholeProcessOrderbill.getCreate_date_start() + "'");
        }
      //判断create_date_end//项目结束时间
        if(StringUtils.isNotBlank(hpsWholeProcessOrderbill.getCreate_date_end())){
            sb.append(" and created_date < '" + hpsWholeProcessOrderbill.getCreate_date_end() + "'");
        }
        
        sb.append(" and deleted = 0 ");
      //查询总条数
        List<HpsWholeProcessOrderbill> listCount = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper(HpsWholeProcessOrderbill.class));
        //分页参数
        int curPage = hpsWholeProcessOrderbill.getPageNo();
        int pageSize = hpsWholeProcessOrderbill.getPageSize();
        int startRow = (curPage - 1) * pageSize;
        sb.append(" limit " + startRow + ","+pageSize);
        List<HpsWholeProcessOrderbill> listWholeProcess = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper(HpsWholeProcessOrderbill.class));
        map.put("listWholeProcess",listWholeProcess);
        if(listCount==null || listCount.size()==0){
            map.put(
            		"count",0);
        }else{
            map.put("count",listCount.size());
        }
        return map;
    	
    }
}
