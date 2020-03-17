package com.jhmis.modules.cms.directive;

import com.jhmis.core.web.BaseDirective;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.StringUtil;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * 
 * <p>
 * Title: JsEscapeDirective.java
 * </p>
 * Description: 将文字转化为json标准字符
 */
@FreemarkerComponent("jsonEscape")
public class JsonEscapeDirective extends BaseDirective {
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {

		// 检查参数是否传入
		if (!params.isEmpty()) {
			throw new TemplateModelException("This directive doesn't allow parameters.");
		}
		if (loopVars.length != 0) {
			throw new TemplateModelException("This directive doesn't allow loop variables.");
		}
		// 是否有非空的嵌入内容
		if (body != null) {
			//
			body.render(new JsonEscapeFilterWriter(env.getOut()));
		} else {
			throw new RuntimeException("missing body");
		}
	}

	/**
	 * {@link Writer}将文字转化为json标准字符， 而且把它发送到另外一个{@link Writer}中。
	 */
	private static class JsonEscapeFilterWriter extends Writer {
		private final Writer out;

		JsonEscapeFilterWriter(Writer out) {
			this.out = out;
		}

		public void write(char[] cbuf, int off, int len) throws IOException {
			String escapedStr = StringUtil.jsonStringEnc(new String(cbuf, off, len));
			char[] transformedCbuf = escapedStr.toCharArray();
			out.write(transformedCbuf);
		}

		public void flush() throws IOException {
			out.flush();
		}

		public void close() throws IOException {
			out.close();

		}
	}
}
