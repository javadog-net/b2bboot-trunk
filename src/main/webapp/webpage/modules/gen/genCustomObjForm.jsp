<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>自定义对象管理</title>
    <meta name="decorator" content="ani"/>
    <script type="text/javascript">
        $(document).ready(function () {
            validateForm = $("#inputForm").validate({
                submitHandler: function (form) {
                    jh.loading();
                    form.submit()
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
<body id="" class="" style="">

<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <a class="panelButton" href="${ctx}/gen/genCustomObj"><i class="ti-angle-left"></i> 返回</a>
                    </h3>
                </div>
                <div class="panel-body">
                    <form:form id="inputForm" modelAttribute="genCustomObj" action="${ctx}/gen/genCustomObj/save"
                               method="post"
                               class="form-horizontal">
                        <form:hidden path="id"/>
                        <sys:message content="${message}"/>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>标签：</label>
                            <div class="col-sm-10">
                                <form:input path="label" htmlEscape="false" class="required form-control "/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>完整类名：</label>
                            <div class="col-sm-10">
                                <form:input path="value" htmlEscape="false" class="required form-control "/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>物理表名：</label>
                            <div class="col-sm-10">
                                <form:input path="tableName" htmlEscape="false" class="required form-control "/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>数据接口：</label>
                            <div class="col-sm-10">
                                <form:input path="dataUrl" htmlEscape="false" class="required form-control "/>
                            </div>
                        </div>

                        <div class="col-lg-3"></div>
                        <div class="col-lg-6">
                            <div class="form-group text-center">
                                <label></label>

                                <div>
                                    <button class="btn btn-primary btn-block btn-lg btn-parsley"
                                            data-loading-text="正在提交...">提 交
                                    </button>
                                </div>
                            </div>
                        </div>

                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
