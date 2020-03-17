<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>行程信息管理</title>
    <meta name="decorator" content="ani"/>
    <script type="text/javascript">

        $(document).ready(function () {
            $("#inputForm").validate({
                submitHandler: function (form) {
                    jh.loading();
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            $('#startTime').datetimepicker({
                format: "YYYY-MM-DD HH:mm:ss"
            });
            $('#returnTime').datetimepicker({
                format: "YYYY-MM-DD HH:mm:ss"
            });
        });
    </script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <a class="panelButton" href="${ctx}/wechat/wxTravel"><i class="ti-angle-left"></i> 返回</a>
                    </h3>
                </div>
                <div class="panel-body">
                    <form:form id="inputForm" modelAttribute="wxTravel" action="${ctx}/wechat/wxTravel/save"
                               method="post" class="form-horizontal">
                        <form:hidden path="id"/>
                        <sys:message content="${message}"/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label"
                                   style="font-size: 20px; text-align: left; font-weight: bold;">基本信息</label>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">联系人：</label>
                            <div class="col-sm-4">
                                <form:input path="concat" htmlEscape="false" class="form-control required" readonly="true"/>
                            </div>
                            <label class="col-sm-2 control-label">手机号：</label>
                            <div class="col-sm-4">
                                <form:input path="mobile" htmlEscape="false" class="form-control required" readonly="true"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">证件类型：</label>
                            <div class="col-sm-4" style="margin-top: 5px;">
                                <form:radiobuttons path="certificateType" items="${fns:getDictList('certificate_type')}"
                                                   itemLabel="label" itemValue="value" htmlEscape="false"
                                                   class="i-checks " disabled="true"/>
                            </div>
                            <label class="col-sm-2 control-label">证件号码：</label>
                            <div class="col-sm-4">
                                <form:input path="certificateNo" htmlEscape="false" class="form-control required" readonly="true"/>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-3 control-label"
                                   style="font-size: 20px; text-align: left; font-weight: bold;">出发行程</label>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">交通工具：</label>
                            <div class="col-sm-4" style="margin-top: 5px;">
                                <form:radiobuttons path="startVehicle" items="${fns:getDictList('startVehicle_type')}"
                                                   itemLabel="label" itemValue="value" htmlEscape="false"
                                                   class="i-checks "/>
                            </div>
                            <label class="col-sm-2 control-label">出发日期：</label>
                            <div class="col-sm-4">
                                <p class="input-group">
                                <div class='input-group form_datetime' id='startTime'>
                                    <input type='text' name="startTime" class="form-control required"
                                           value="<fmt:formatDate value="${wxTravel.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
                                </div>
                                </p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">出发站：</label>
                            <div class="col-sm-4">
                                <form:input path="startStand" htmlEscape="false"    class="form-control required"/>
                            </div>
                            <label class="col-sm-2 control-label">到达站：</label>
                            <div class="col-sm-4">
                                <form:input path="startStandEnd" htmlEscape="false"    class="form-control required"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">航班号/车次号：</label>
                            <div class="col-sm-4">
                                <form:input path="startVehicleNo" htmlEscape="false" class="form-control required"/>
                            </div>
                            <label class="col-sm-2 control-label">是否接站 ：</label>
                            <div class="col-sm-4" style="margin-top: 5px;">
                                <form:radiobuttons path="startReceive" items="${fns:getDictList('startReceive_type')}"
                                                   itemLabel="label" itemValue="value" htmlEscape="false"
                                                   class="i-checks required" disabled="true"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">出票状态</label>
                            <div class="col-sm-4" style="margin-top: 5px;">
                                <form:radiobuttons path="startTicket" items="${fns:getDictList('ticket_type')}"
                                                   itemLabel="label" itemValue="value" htmlEscape="false"
                                                   class="i-checks required"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label"
                                   style="font-size: 20px; text-align: left; font-weight: bold;">返回行程</label>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">返程交通工具：</label>
                            <div class="col-sm-4" style="margin-top: 5px;">
                                <form:radiobuttons path="returnVehicle" items="${fns:getDictList('startVehicle_type')}"
                                                   itemLabel="label" itemValue="value" htmlEscape="false"
                                                   class="i-checks required"/>
                            </div>
                            <label class="col-sm-2 control-label">返程时间：</label>
                            <div class="col-sm-4">
                                <p class="input-group">
                                <div class='input-group form_datetime' id='returnTime'>
                                    <input type='text' name="returnTime" class="form-control required"
                                           value="<fmt:formatDate value="${wxTravel.returnTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
                                </div>
                                </p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">出发站：</label>
                            <div class="col-sm-4">
                                <form:input path="returnStand" htmlEscape="false" class="form-control required"/>
                            </div>
                            <label class="col-sm-2 control-label">到达站：</label>
                            <div class="col-sm-4">
                                <form:input path="returnStandEnd" htmlEscape="false" class="form-control required"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">航班号/车次号：</label>
                            <div class="col-sm-4">
                                <form:input path="returnVehicleNo" htmlEscape="false" class="form-control required"/>
                            </div>
                            <label class="col-sm-2 control-label">出票状态：</label>
                            <div class="col-sm-4" style="margin-top: 5px;">
                                <form:radiobuttons path="returnTicket" items="${fns:getDictList('ticket_type')}"
                                                   itemLabel="label" itemValue="value" htmlEscape="false"
                                                   class="i-checks "/>
                            </div>
                        </div>
                        <c:if test="${fns:hasPermission('wechat:wxTravel:edit') || isAdd }">
                            <div class="col-lg-3"></div>
                            <div class="col-lg-6">
                                <div class="form-group text-center">
                                    <div>
                                        <c:if test="${view}">
                                        <button class="btn btn-primary btn-block btn-lg btn-parsley"
                                                data-loading-text="正在提交...">提 交
                                        </button>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>