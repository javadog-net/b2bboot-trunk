<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>菜单管理</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <script type = "text/javascript">
        var validateForm;
        var $table;
        var $topIndex;
        function doSubmit(table, index) {
            if (validateForm.form()) {
                $table = table;
                $topIndex = index;
                jh.loading();
                $("#inputForm").submit();
                return true
            }
            return false
        }
        $(document).ready(function () {
            $("#name").focus();
            validateForm = $("#inputForm").validate({
                submitHandler: function (form) {
                    $.ajax({
                        url: ctx + "/gen/genScheme/createMenu",
                        method: "POST",
                        data: $('#inputForm').serialize(),
                        error: function (data) {
                            jh.error('操作失败!')
                        },
                        success: function (data) {
                            if (data.success) {
                                $table.bootstrapTable('refresh');
                                jh.success(data.msg)
                            } else {
                                jh.error(data.msg)
                            }
                            jh.close($topIndex)
                        }
                    })
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent())
                    } else {
                        error.insertAfter(element)
                    }
                }
            })
        });

    </script>

</head>
<body id="" class="bg-white"  style="">
<form:form id="inputForm" modelAttribute="menu" action="${ctx}/gen/genScheme/createMenu" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <input type="hidden" name="gen_table_id" value="${gen_table_id}"/>
    <input type="hidden" name="genTableType" value="${genTableType}">

    <table class="table table-bordered">
        <tbody>
        <tr>
            <td  class="width-15 active"><label class="pull-right">上级菜单:</label></td>
            <td class="width-35" >
                <sys:treeselect id="menu" name="parent.id" value="${menu.parent.id}" labelName="parent.name" labelValue="${menu.parent.name}"
                                                  title="菜单" url="/sys/menu/treeData" extId="${menu.id}" allowClear="true" allowSearch="true" cssClass="form-control required"/>
            </td>
        </tr>
        <tr>
            <td  class="width-15 active"><label class="pull-right"><font color="red">*</font> 名称:</label></td>
            <td  class="width-35" >
                <form:input path="name" htmlEscape="false" maxlength="50" class="required form-control "/>
            </td>
        </tr>

        <tr>
            <td  class="width-15 active"><label class="pull-right">图标:</label></td>
            <td class="width-35" >
                <sys:iconselect id="icon" name="icon" value="${menu.icon}"/>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
