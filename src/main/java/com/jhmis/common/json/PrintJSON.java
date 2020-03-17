/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.common.json;

import com.jhmis.core.mapper.JsonMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PrintJSON {

	public static void write(HttpServletResponse response,Object object) {
		String content = "";
		if(object instanceof  String){
			content = (String) object;
		} else {
			content = JsonMapper.toJsonString(object);
		}
		response.reset();
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		response.setCharacterEncoding("UTF-8");
		try {
			//TODO 经常在此以前已经获取过write
			PrintWriter pw=response.getWriter();
			pw.write(content);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
