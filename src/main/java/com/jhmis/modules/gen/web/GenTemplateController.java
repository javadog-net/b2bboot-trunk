package com.jhmis.modules.gen.web;

import com.jhmis.modules.gen.entity.GenTemplate;
import com.jhmis.modules.gen.service.GenTemplateService;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/**
 * 代码模板管理
 * @author tity
 *
 */
@Controller
@RequestMapping({"${adminPath}/gen/genTemplate"})
public class GenTemplateController extends BaseController {
    @Autowired
    private GenTemplateService genTemplateService;

    public GenTemplateController() {
    }

    @ModelAttribute
    public GenTemplate a1(@RequestParam(required = false) String id) {
        return StringUtils.isNotBlank(id) ? this.genTemplateService.a2(id) : new GenTemplate();
    }

    @RequiresPermissions({"gen:genTemplate:view"})
    @RequestMapping({"list", ""})
    public String b(GenTemplate b, HttpServletRequest request, HttpServletResponse response, Model model) {
        User a1 = UserUtils.getUser();
        if (!a1.isAdmin()) {
            b.setCreateBy(a1);
        }

        Page<GenTemplate> c1 = this.genTemplateService.a(new Page<GenTemplate>(request, response), b);
        model.addAttribute("page", c1);
        return "modules/gen/genTemplateList";
    }

    @RequiresPermissions({"gen:genTemplate:view"})
    @RequestMapping({"form"})
    public String c(GenTemplate a1, Model model) {
        model.addAttribute("genTemplate", a1);
        return "modules/gen/genTemplateForm";
    }

    @RequiresPermissions({"gen:genTemplate:edit"})
    @RequestMapping({"save"})
    public String d(GenTemplate a1, Model model, RedirectAttributes redirectAttributes) {
        if (!this.beanValidator(model, a1, new Class[0])) {
            return this.c(a1, model);
        } else {
            this.genTemplateService.a1(a1);
            this.addMessage(redirectAttributes, new String[]{"保存代码模板'" + a1.getName() + "'成功"});
            return "redirect:" + this.adminPath + "/gen/genTemplate/?repage";
        }
    }

    @RequiresPermissions({"gen:genTemplate:edit"})
    @RequestMapping({"delete"})
    public String e(GenTemplate a, RedirectAttributes redirectAttributes) {
        this.genTemplateService.a(a);
        this.addMessage(redirectAttributes, new String[]{"删除代码模板成功"});
        return "redirect:" + this.adminPath + "/gen/genTemplate/?repage";
    }
}
