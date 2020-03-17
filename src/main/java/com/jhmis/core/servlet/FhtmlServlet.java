package com.jhmis.core.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lydia
 * @date 2019/12/11 13:53
 */
public class FhtmlServlet  extends HttpServlet {

    public void init(HttpServletRequest hreq, HttpServletResponse hres) throws ServletException {

        String uri = hreq.getRequestURI().replaceFirst(hreq.getContextPath(), "");
        try {
            if (uri.startsWith("/site/") && uri.endsWith(".fhtml")) {
                //访问静态页面
                //提取站点源文件夹
                String uri1 = uri.replaceFirst("/site/", "");
                //提取模板路径
                String templetPath = uri1.substring(uri1.indexOf("/") + 1).replace(".fhtml", ".html");
                //转向模板处理页面
                hreq.getRequestDispatcher("/templetProFhtml?templetFile=" + templetPath).forward(hreq, hres);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.init(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}