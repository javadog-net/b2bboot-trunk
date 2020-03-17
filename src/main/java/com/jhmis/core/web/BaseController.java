/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.core.web;

import com.jhmis.common.beanvalidator.BeanValidators;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.core.mapper.JsonMapper;
import com.jhmis.core.persistence.Page;
import com.jhmis.modules.cms.utils.CmsUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 控制器支持类
 * @author jhmis
 * @version 2016-3-23
 */
public abstract class BaseController {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 管理基础路径
	 */
	@Value("${adminPath}")
	protected String adminPath;

	/**
	 * 前端基础路径
	 */
	@Value("${frontPath}")
	protected String frontPath;

	/**
	 * 前端URL后缀
	 */
	@Value("${urlSuffix}")
	protected String urlSuffix;
	/**
	 * cms 模板路径
	 */
	@Value("${cms.templates.path}")
	protected  String templatePath;
	/**
	 * cms静态文件路径
	 */
	@Value("${cms.static.path}")
	protected  String staticPath;
	/**
	 * 验证Bean实例对象
	 */
	@Autowired
	protected Validator validator;
	@Autowired
	private ServletContext servletContext;


	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
	 */
	protected boolean beanValidator(Model model, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(model, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}

	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
	 */
	protected boolean beanValidator(RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(redirectAttributes, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}

	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组，不传入此参数时，同@Valid注解验证
	 * @return 验证成功：继续执行；验证失败：抛出异常跳转400页面。
	 */
	protected void beanValidator(Object object, Class<?>... groups) {
		BeanValidators.validateWithException(validator, object, groups);
	}

	/**
	 * 添加Model消息
	 * @param
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		model.addAttribute("message", sb.toString());
	}

	/**
	 * 添加Flash消息
	 * @param
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
		
	}

	/**
	 * 客户端返回JSON字符串
	 * @param response
	 * @param object
	 * @return
	 */
	protected String renderString(HttpServletResponse response, Object object) {
		return renderString(response, JsonMapper.toJsonString(object));
	}

	/**
	 * 客户端返回字符串
	 * @param response
	 * @param string
	 * @return
	 */
	protected String renderString(HttpServletResponse response, String string) {
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 参数绑定异常
	 */
	@ExceptionHandler({BindException.class, ConstraintViolationException.class, ValidationException.class})
	public String bindException() {
		return "error/400";
	}

	/**
	 * 授权登录异常
	 */
	@ExceptionHandler({AuthenticationException.class})
	public String authenticationException() {
		return "error/403";
	}

	/**
	 *系统登录异常
	 */
	@ExceptionHandler({Exception.class})
	public String exception() {
		return "error/500";
	}

	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
//			@Override
//			public String getAsText() {
//				Object value = getValue();
//				return value != null ? DateUtils.formatDateTime((Date)value) : "";
//			}
		});
	}


	/**
	 * 获取bootstrap data分页数据
	 * @param page
	 * @return map对象
	 */
	@SuppressWarnings("rawtypes")
	public <T> Map<String, Object> getBootstrapData(Page page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", page.getList());
		map.put("total", page.getCount());
		return map;
	}
	/**
	 *  将表情转码
	 * @param
	 * @return map对象
	 */
	public static String filterEmoji(String nick_name) {
		//nick_name 所获取的用户昵称
		if (nick_name == null) {
			return nick_name;
		}
		Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
				Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		Matcher emojiMatcher = emoji.matcher(nick_name);
		if (emojiMatcher.find()) {
			//将所获取的表情转换为*
			nick_name = emojiMatcher.replaceAll("*");
			return nick_name;
		}
		return nick_name;
	}
    public Map<String,Object> getData(HttpServletRequest request){
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("contextPath", getContextPathNoReplaceStr(request.getContextPath()) + "/");
        data.put("contextPathNo",getContextPathNoReplaceStr(request.getContextPath()));//服务器地址
        data.put("cmsConfig", CmsUtils.getCmsConfig());
        return data;
    }
	public ServletContext getServletContext() {
		return servletContext;
	}

	public String getContextPathNo(HttpServletRequest request) {
		return request.getContextPath();
	}
	public static String getContextPathNoReplaceStr(String path) {
		String subPath = "";
		String npath = "";
		if(path.contains("shop")){
			npath = path.replace("shop","");
			subPath =npath. replaceAll("/", "");
		}
		return "http://b2b.haier.com";
	}


}
