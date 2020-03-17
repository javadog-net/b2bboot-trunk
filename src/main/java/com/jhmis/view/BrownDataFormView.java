package com.jhmis.view;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.view.entity.BrownDataForm;
import com.jhmis.view.entity.FLDataDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.view
 * @Author: hdx
 * @CreateTime: 2019-10-28 17:07
 * @Description: 工程版报表视图
 */
@Component
public class BrownDataFormView {
    @Autowired
    @Qualifier("secondJdbcTemplate")
    protected JdbcTemplate jdbcTemplate;
    //根据实体反射进行转换
    public Map<String,Object> getBrownDataForm(BrownDataForm brownDataForm) throws Exception{
        Map<String,Object> map = new HashMap<>();
        //判断必填是否为空
        if(StringUtils.isEmpty(brownDataForm.getDealerCode())){
            throw new Exception("经销商编码不能为空");
        }
        String basic_sql = "select * from view_QYG_BrownDataForm where dealerCode = '" + brownDataForm.getDealerCode() + "'";
        StringBuilder sb = new StringBuilder();
        sb.append(basic_sql);
        //判断BrownCode工程版单号 是否为空
        if(StringUtils.isNotEmpty(brownDataForm.getBrownCode())){
            sb.append(" and brownCode like '%" + brownDataForm.getBrownCode() + "%'");
        }
        //判断projectCode项目编号 是否为空
        if(StringUtils.isNotEmpty(brownDataForm.getProjectCode())){
            sb.append(" and projectCode like '%" + brownDataForm.getProjectCode() + "%'");
        }

        //判断projectName项目名称 是否为空
        if(StringUtils.isNotEmpty(brownDataForm.getProjectName())){
            sb.append(" and projectName like '%" + brownDataForm.getProjectName() + "%'");
        }
        
      //判断verify_status项目状态是否为空
        if(StringUtils.isNotEmpty(brownDataForm.getVerify_status())){
            sb.append(" and verify_status like '%" + brownDataForm.getVerify_status() + "%'");
        }

        //判断productGroupName产品组 是否为空
        if(StringUtils.isNotEmpty(brownDataForm.getProductGroupName())){
            sb.append(" and productGroupName like '%" + brownDataForm.getProductGroupName() + "%'");
        }




        //判断验收倒计时 是否为空
        if(StringUtils.isNotEmpty(brownDataForm.getYanshoudaojishi())){
            if("0".equals(brownDataForm.getYanshoudaojishi())){
                //0-3
                sb.append(" and yanshoudaojishi between 0 and 3");
            }else if("1".equals(brownDataForm.getYanshoudaojishi())){
                //4-7
                sb.append(" and yanshoudaojishi between 4 and 7");
            }else if("2".equals(brownDataForm.getYanshoudaojishi())){
                //7-15
                sb.append(" and yanshoudaojishi between 8 and 15");
            }else if("3".equals(brownDataForm.getYanshoudaojishi())){
                //15-30
                sb.append(" and yanshoudaojishi between 16 and 30");
            }else if("4".equals(brownDataForm.getYanshoudaojishi())){
                //30-60
                sb.append(" and yanshoudaojishi between 31 and 60");
            }else if("5".equals(brownDataForm.getYanshoudaojishi())){
                //60-100
                sb.append(" and yanshoudaojishi between 60 and 100");
            }else if("6".equals(brownDataForm.getYanshoudaojishi())){
                //100-145
                sb.append(" and yanshoudaojishi between 101 and 145");
            }else if("7".equals(brownDataForm.getYanshoudaojishi())){
                //145-180
                sb.append(" and yanshoudaojishi between 146 and 180");
            }
        }
        //判断开始时间间断
        if(StringUtils.isNotEmpty(brownDataForm.getCreatedDateStart())&& StringUtils.isNotEmpty(brownDataForm.getCreatedDateEnd())){
            sb.append(" and (createdDate > '" + brownDataForm.getCreatedDateStart() + "'" + " and createdDate < '" + brownDataForm.getCreatedDateEnd()+ "')");
        }else{
            if(StringUtils.isNotEmpty(brownDataForm.getCreatedDateStart())){
                sb.append(" and (createdDate > '" + brownDataForm.getCreatedDateStart() + "')");
            }
            if(StringUtils.isNotEmpty(brownDataForm.getCreatedDateEnd())){
                sb.append(" and (createdDate < '" + brownDataForm.getCreatedDateEnd() + "')");
            }
        }
        ///判断到期时间间断
        if(StringUtils.isNotEmpty(brownDataForm.getExpireTimeStart())&& StringUtils.isNotEmpty(brownDataForm.getExpireTimeEnd())){
            sb.append(" and (createdDate > '" + brownDataForm.getExpireTimeStart() + "'" + " and createdDate < '" + brownDataForm.getExpireTimeEnd()+ "')");
        }else{
            if(StringUtils.isNotEmpty(brownDataForm.getExpireTimeStart())){
                sb.append(" and (createdDate > '" + brownDataForm.getExpireTimeStart() + "')");
            }
            if(StringUtils.isNotEmpty(brownDataForm.getExpireTimeEnd())){
                sb.append(" and (createdDate < '" + brownDataForm.getExpireTimeEnd() + "')");
            }
        }
        //查询总条数
        List<BrownDataForm> listCount = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper(BrownDataForm.class));
        //分页参数
        int curPage = brownDataForm.getPageNo();
        int pageSize = brownDataForm.getPageSize();
        int startRow = (curPage - 1) * pageSize;

        sb.append(" limit " + startRow + ","+pageSize);
        List<BrownDataForm> listBrownDataForm = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper(BrownDataForm.class));
        map.put("listBrownDataForm",listBrownDataForm);
        if(listCount==null || listCount.size()==0){
            map.put("count",0);
        }else{
            map.put("count",listCount.size());
        }
        return map;
    }
}
