package com.jhmis.modules.gen.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jhmis.modules.gen.entity.GenScheme;
import com.jhmis.modules.gen.service.CgAutoListService;
import com.jhmis.modules.gen.service.GenSchemeService;
import com.jhmis.modules.gen.template.FreemarkerHelper;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
/**
 * 动态列表
 * @author tity
 *
 */
@Controller
@RequestMapping({"${adminPath}/gen/cgAutoList"})
public class CgAutoListController extends BaseController {
    private static Logger log = Logger.getLogger(CgAutoListController.class);
    @Autowired
    private GenSchemeService genSchemeService;
    @Autowired
    private CgAutoListService cgAutoListService;

    public CgAutoListController() {
    }

    @ModelAttribute
    public GenScheme get(@RequestParam(required = false) String id) {
        return StringUtils.isNotBlank(id) ? this.genSchemeService.a(id) : new GenScheme();
    }

    @RequestMapping({"list"})
    public void list(GenScheme genScheme, HttpServletRequest request, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        new FreemarkerHelper();
        String html = this.cgAutoListService.generateCode(genScheme);
        this.cgAutoListService.generateListCode(genScheme);

        try {
            response.setContentType("text/html");
            response.setHeader("Cache-Control", "no-store");
            PrintWriter writer = response.getWriter();
            writer.println(html);
            writer.flush();
        } catch (IOException var12) {
            var12.printStackTrace();
        }

        long end = System.currentTimeMillis();
        log.debug("动态列表生成耗时：" + (end - start) + " ms");
    }

    @RequestMapping({"test", ""})
    public ModelAndView list(GenScheme genScheme, HttpServletRequest request, HttpServletResponse response, Model model) {
        return new ModelAndView("com/jhmis/modules/gen/template/viewList");
    }
}
