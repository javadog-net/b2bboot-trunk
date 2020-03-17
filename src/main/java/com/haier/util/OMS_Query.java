package com.haier.util;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.haier.oms.client.model.qiyegou.QiYeGouQueryStockInputModel;
import com.haier.oms.client.model.qiyegou.QiYeGouQueryStockOutputModel;
import com.haier.oms.client.service.qiyegou.QiYeGouQueryStockClient;
import com.jhmis.modules.directpurchaser.service.DirectGoodsService;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 调用海尔dubbo注意：
 *      1.bubbo的版本不要太高，推荐使用下面的依赖
 *      2.调用时reference.setVersion("1.0")，否则提示远过程调用失败
 *      3.采用API调用模式，省去xml配置
 *      4.zookeeper地址没有写成配置文件
 * <dependency>
 *       <groupId>com.101tec</groupId>
 *       <artifactId>zkclient</artifactId>
 *       <version>0.9</version>
 *     </dependency>
 *
 *     <dependency>
 *       <groupId>org.apache.zookeeper</groupId>
 *       <artifactId>zookeeper</artifactId>
 *       <version>3.4.9</version>
 *     </dependency>
 *
 *     <dependency>
 *       <groupId>com.alibaba</groupId>
 *       <artifactId>dubbo</artifactId>
 *       <version>2.5.3</version>
 *       <exclusions>
 *         <exclusion>
 *           <artifactId>spring</artifactId>
 *           <groupId>org.springframework</groupId>
 *         </exclusion>
 *       </exclusions>
 *     </dependency>
 *
 *     <dependency>
 *       <groupId>io.netty</groupId>
 *       <artifactId>netty-all</artifactId>
 *       <version>4.1.6.Final</version>
 *     </dependency>
 */
public class OMS_Query {
    /**
     * 查询商品库存
     * @param salesFactory      销售工厂
     * @param mainStockCode     主仓编码
     * @param productCode       型号编码
     * @return                  result为SUCCESS成功
     *      *                   result为FAILURE失败，msg为失败信息
     */
    public static Map<String,Object> query(String salesFactory,String mainStockCode,String productCode){
    	
    	try {
    		System.out.println("ooooooooooooooooooooooooo");
    		 Map<String,Object> map = new HashMap<String,Object>();
    	        QiYeGouQueryStockInputModel inputModel = new QiYeGouQueryStockInputModel();
    	        System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvv");
    	        inputModel.setSalesFactory(salesFactory);
    	        inputModel.setMainStockCode(mainStockCode);
    	        inputModel.setProductCode(productCode);
    	        ApplicationConfig application = new ApplicationConfig();
    	        application.setName("query");
    	        RegistryConfig registry = new RegistryConfig();
//    	        registry.setAddress("zookeeper://10.135.6.66:2181");
    	        registry.setAddress("zookeeper://10.135.6.210:2181");
    	        ReferenceConfig<QiYeGouQueryStockClient> reference = new ReferenceConfig<QiYeGouQueryStockClient>();
    	        System.out.println("cccccccccccc");
    	        reference.setApplication(application);
    	        reference.setRegistry(registry);
    	        reference.setVersion("1.0");
    	        reference.setInterface(QiYeGouQueryStockClient.class);
    	        System.out.println("bbbbbbbbbbbb");
    	        QiYeGouQueryStockClient client = reference.get();
    	        System.out.println("nnnnnnnnnnnnn");
    	        QiYeGouQueryStockOutputModel outputModel = client.queryStockInfo(inputModel);
    	        System.out.println("mmmmmmmmmmmmmmmmmmm"+outputModel);
    	        if("S".equals(outputModel.getState())){
    	            map.put("result","SUCCESS");
    	            map.put("detail", outputModel.getDetail());
    	        }else{
    	            map.put("result","FAILURE");
    	        }
    	        map.put("msg",outputModel.getMsg());
    	        map.put("detail",outputModel.getDetail());
    	        System.out.println(map);
    	        System.out.println("map========="+map);
    	        return map;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
       return null;
      
    }

    public static void main(String[] args) {
        Map map = query("9050", "DB", "BH0330090");
        System.out.println(map);
    }
}
