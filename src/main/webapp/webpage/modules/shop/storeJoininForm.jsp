<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>店铺申请管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					jh.loading();
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
	        $('#auditTime').datetimepicker({
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
				<a class="panelButton" href="${ctx}/shop/storeJoinin"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="storeJoinin" action="${ctx}/shop/storeJoinin/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
            <div class="form-group">
                <label class="col-sm-2 control-label">供应商编码：</label>
                <div class="col-sm-10">
                    <span class="lbl">${storeJoinin.companyCode}</span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">供应商名称：</label>
                <div class="col-sm-10">
                    <span class="lbl">${storeJoinin.companyName}</span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">联系人：</label>
                <div class="col-sm-10">
                    <span class="lbl">${storeJoinin.contacts}</span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">电话：</label>
                <div class="col-sm-10">
                    <span class="lbl">${storeJoinin.tel}</span>
                </div>
            </div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>店铺名称：</label>
					<div class="col-sm-10">
						<form:input path="storeName" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>申请时长：</label>
					<div class="col-sm-10">
						<form:input path="applyYear" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注：</label>
					<div class="col-sm-10">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</div>
				</div>
            <div class="form-group">
                <label class="col-sm-2 control-label">审核状态：</label>
                <div class="col-sm-10">
                    <form:radiobuttons path="auditState" items="${fns:getDictList('audit_state')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks " disabled="true"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">审核时间 ：</label>
                <div class="col-sm-10">
                    <form:input path="auditTime" htmlEscape="false"    readonly="true" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">审核人：</label>
                <div class="col-sm-10">
                    <form:input path="auditor" htmlEscape="false"    readonly="true" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">审核意见：</label>
                <div class="col-sm-10">
                    <form:input path="auditDesc" htmlEscape="false"  readonly="true"   class="form-control "/>
                </div>
            </div>
		<c:if test="${fns:hasPermission('shop:storeJoinin:edit') || isAdd}">
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
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