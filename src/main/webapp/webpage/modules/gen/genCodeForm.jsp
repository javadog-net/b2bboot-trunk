<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>生成代码</title>
    <meta name="decorator" content="ani"/>
    <script type="text/javascript">
        var validateForm;
        var $table;
        var $topIndex;

        function doSubmit(a, b) {
            if (validateForm.form()) {
                $table = a;
                $topIndex = b;
                jh.loading();
                $("#inputForm").submit();
                return true
            }
            return false
        }

        function selectFolder() {
            jh.openDialog('选择目录', ctx + '/gen/genScheme/fileTree', '800px', '500px', $("#projectPath"));
            return false
        }

        $(document).ready(function () {
            $("#name").focus();
            validateForm = $("#inputForm").validate({
                submitHandler: function (b) {
                    jh.post(ctx + "/gen/genTable/genCode", $('#inputForm').serialize(), function (a) {
                        if (a.success) {
                            $table.bootstrapTable('refresh');
                            jh.alert(a.msg)
                        } else {
                            jh.error(a.msg)
                        }
                        jh.close($topIndex)
                    })
                },
                errorContainer: "#messageBox",
                errorPlacement: function (a, b) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (b.is(":checkbox") || b.is(":radio") || b.parent().is(".input-append")) {
                        a.appendTo(b.parent().parent())
                    } else {
                        a.insertAfter(b)
                    }
                }
            })
        });
    </script>
</head>
<body id="" class="bg-white" style="">
<form:form id="inputForm" modelAttribute="genScheme" action="${ctx}/gen/genTable/genCode" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="genTable.id"/>
    <sys:message content="${message}"/>
    提示：代码生成会覆盖路径中已经存在的同名文件，请做好备份或选择空白目录生成代码。
    <br/>
    <br/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>生成路径:</label></td>
            <td colspan="3">
                <div class="row">
                    <div class="col-sm-10">
                        <input type="text" id="projectPath" value="${genScheme.projectPath}" name="projectPath"
                               class="form-control required">
                    </div>
                    <div class="col-sm-2"><a class="btn btn-default" href="#" onclick="selectFolder()">选择生成目录</a></div>
                </div>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>代码风格:</label></td>
            <td>
                <form:select path="category" class="required form-control">
                    <form:options items="${config.categoryList}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>表单风格:</label></td>
            <td>
                <span>
                    <form:radiobuttons path="formStyle" items="${fns:getDictList('form_style')}" itemLabel="label"
                                       itemValue="value" class="required i-checks"/>
                </span>
                <br/>
                <span class="help-inline">form风格在移动端体验更佳</span>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>生成包路径:</label></td>
            <td>
                <form:input path="packageName" htmlEscape="false" class="form-control required" maxlength="500"/>
                <span class="help-inline">建议模块包：com.jhmis.modules</span>
            </td>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>生成模块名:</label></td>
            <td>
                <form:input path="moduleName" htmlEscape="false" class="form-control required" maxlength="500"/>
                <span class="help-inline">可理解为子系统名，例如 sys</span>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">生成子模块名:</label></td>
            <td>
                <form:input path="subModuleName" htmlEscape="false" class="form-control" maxlength="500"/>
                <span class="help-inline">可选，分层下的文件夹，例如 </span>
            </td>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>生成功能描述:</label></td>
            <td>
                <form:input path="functionName" htmlEscape="false" class="form-control" maxlength="500"/>
                <span class="help-inline">将设置到类描述</span>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>生成功能名:</label></td>
            <td>
                <form:input path="functionNameSimple" htmlEscape="false" class="form-control required" maxlength="500"/>
                <span class="help-inline">用作功能提示，如：保存“某某”成功</span>
            </td>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>生成功能作者:</label></td>
            <td>
                <form:input path="functionAuthor" htmlEscape="false" class="form-control required" maxlength="500"/>
                <span class="help-inline">功能开发者</span>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>

</body>
</html>
