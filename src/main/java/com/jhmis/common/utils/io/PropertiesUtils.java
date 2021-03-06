/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jhmis.common.utils.io;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.base.ObjectUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Properties工具类， 可载入多个properties、yml文件，
 * 相同的属性在最后载入的文件中的值将会覆盖之前的值， 
 * 取不到从System.getProperty()获取。
 * @version 2017-12-30
 */
public class PropertiesUtils {
	
	// 默认加载的文件，可通过继承覆盖（若有相同Key，优先加载后面的）
	public static final String[] DEFAULT_CONFIG_FILE = new String[]{
			"classpath:config/application.yml",
			"classpath:application.yml"};

	private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
	
	private final Properties properties = new Properties();
	
	/**
	 * 当前类的实例持有者（静态内部类，延迟加载，懒汉式，线程安全的单例模式）
	 */
	private static final class PropertiesLoaderHolder {
		private static PropertiesUtils INSTANCE;
		static {
			releadInstance();
		}
		public static void releadInstance(){
			Set<String> configFiles = new LinkedHashSet<String>();
			/*Resource[] resources = ResourceUtils.getResources("classpath*:/config/b2b-*.*");
			for(Resource resource : resources){
				configFiles.add("classpath:/config/"+resource.getFilename());
			}*/
			for (String configFile : DEFAULT_CONFIG_FILE){
				configFiles.add(configFile);
			}
			logger.debug("Loading config: {}", configFiles);
			INSTANCE = new PropertiesUtils(configFiles.toArray(new String[configFiles.size()]));
		}
	}

    /**
     * 读取单个配置文件
     * @param location
     */
	public void loadConfigFile(String location){
        try {
            Resource resource = ResourceUtils.getResource(location);
            if (resource.exists()){
                String ext = FilenameUtils.getExtension(location);
                if ("properties".equals(ext)){
                    InputStreamReader is = null;
                    try {
                        is = new InputStreamReader(resource.getInputStream(), "UTF-8");
                        properties.load(is);
                    } catch (IOException ex) {
                        logger.error("Load " + location + " failure. ", ex);
                    } finally {
                        IOUtils.closeQuietly(is);
                    }
                }
                else if ("yml".equals(ext)){
                    YamlPropertiesFactoryBean bean = new YamlPropertiesFactoryBean();
                    bean.setResources(resource);
                    for (Map.Entry<Object,Object> entry : bean.getObject().entrySet()){
                        properties.put(ObjectUtil.toPrettyString(entry.getKey()),
                                ObjectUtil.toPrettyString(entry.getValue()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Load " + location + " failure. ", e);
        }
    }
	/**
	 * 载入多个文件，路径使用Spring Resource格式，相同的属性在最后载入的文件中的值将会覆盖之前的值。
	 */
	public PropertiesUtils(String... configFiles) {
		for (String location : configFiles) {
            loadConfigFile(location);
		}
		//读取当前环境配置文件
        String active = properties.getProperty("spring.profiles.active");
		if(StringUtils.isNotBlank(active)){
		    String activeConfigFile = "classpath:application-"+active+".yml";
            loadConfigFile(activeConfigFile);
        }
	}
	
	/**
	 * 获取当前加载的属性
	 */
	public Properties getProperties() {
		return properties;
	}
	
	/**
	 * 当前类实例
	 */
	public static PropertiesUtils getInstance(){
		return PropertiesLoaderHolder.INSTANCE;
	}
	
	/**
	 * 重新加载实例（重新实例化，以重新加载属性文件数据）
	 */
	public static void releadInstance(){
		PropertiesLoaderHolder.releadInstance();
	}
	
	// 正则表达式预编译
	private static Pattern p1 = Pattern.compile("\\$\\{.*?\\}");

	/**
	 * 获取属性值，取不到从System.getProperty()获取，都取不到返回null
	 */
	public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value != null){
        	// 支持嵌套取值的问题  key=${xx}/yy
	    	Matcher m = p1.matcher(value);
	        while(m.find()) {
	            String g = m.group();
	            String keyChild = g.replaceAll("\\$\\{", "").replaceAll("\\}", "");
	            value = value.replace(g, getProperty(keyChild));
	        }
	        return value;
	    }else{
	    	String systemProperty = System.getProperty(key);
			if (systemProperty != null) {
				return systemProperty;
			}
	    }
		return null;
	}

	/**
	 * 取出String类型的Property，但以System的Property优先，如果都为null则返回defaultValue值
	 */
	public String getProperty(String key, String defaultValue) {
		String value = getProperty(key);
		return value != null ? value : defaultValue;
	}

}
