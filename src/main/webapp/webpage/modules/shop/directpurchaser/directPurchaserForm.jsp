<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>直购商管理</title>
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
				<a class="panelButton" href="${ctx}/shop/directpurchaser/directpurchaser"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="purchaser" action="${ctx}/shop/directpurchaser/directpurchaser/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">公司代码：</label>
					<div class="col-sm-10">
						<form:input path="companyCode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">公司名称：</label>
					<div class="col-sm-10">
						<form:input path="companyName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">公司人数：</label>
					<div class="col-sm-10">
						<form:input path="companyNum" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
            <form:hidden path="provinceId"/>
            <form:hidden path="cityId"/>
            <form:hidden path="districtId"/>
            <div class="form-group">
                <label class="col-sm-2 control-label">所在地区：</label>
                <div class="col-sm-10">
                    <div class=" input-group" style=" width: 100%;">
                        <form:input path="areaInfo" htmlEscape="false"  class="" data-toggle="city-picker" provinceId="provinceId" cityId="cityId" districtId="districtId" style="height: 34px;"/>
                    </div>
                </div>
            </div>
				<div class="form-group">
					<label class="col-sm-2 control-label">详细地址：</label>
					<div class="col-sm-10">
						<form:input path="detailAddress" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">行业：</label>
					<div class="col-sm-10">
                        <form:select path="industryId"  htmlEscape="false" class="form-control">
                            <form:option value="" label="请选择行业" selected="true"/>
                            <form:options items="${industryList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                        </form:select>
					</div>
				</div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">公司电话：</label>
                    <div class="col-sm-10">
                        <form:input path="companyTel" htmlEscape="false"    class="form-control "/>
                    </div>
                </div>
				<div class="form-group">
					<label class="col-sm-2 control-label">联系人：</label>
					<div class="col-sm-10">
						<form:input path="contacts" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">手机：</label>
					<div class="col-sm-10">
						<form:input path="mobile" htmlEscape="false"    class="form-control  isPhone"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">邮箱：</label>
					<div class="col-sm-10">
						<form:input path="email" htmlEscape="false"    class="form-control email"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">公司性质：</label>
					<div class="col-sm-10">
						<form:input path="companyNature" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">邀请码：</label>
					<div class="col-sm-10">
						<form:input path="invitationCode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注：</label>
					<div class="col-sm-10">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</div>
				</div>
				<%-- <div class="form-group">
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
				</div> --%>
		<c:if test="${fns:hasPermission('shop:directpurchaser:directpurchaser:edit') || isAdd}">
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