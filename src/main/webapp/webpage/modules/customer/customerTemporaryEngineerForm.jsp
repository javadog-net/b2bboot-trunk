<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>临时工程商管理</title>
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
				<a class="panelButton" href="${ctx}/customer/customerTemporaryEngineer"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="customerTemporaryEngineer" action="${ctx}/customer/customerTemporaryEngineer/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">工程商名称：</label>
					<div class="col-sm-4">
						${customerTemporaryEngineer.cusName}
					</div>
					<label class="col-sm-2 control-label">税务单号：</label>
					<div class="col-sm-4">
							${customerTemporaryEngineer.tax}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">工程商负责人：</label>
					<div class="col-sm-4">
							${customerTemporaryEngineer.cusHeadPerson}
					</div>
					<label class="col-sm-2 control-label">网格省份：</label>
					<div class="col-sm-4">
							${customerTemporaryEngineer.wgProviceName}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">网格城市名称：</label>
					<div class="col-sm-4">
							${customerTemporaryEngineer.wgCityName}
					</div>
					<label class="col-sm-2 control-label">网格区名称：</label>
					<div class="col-sm-4">
							${customerTemporaryEngineer.areaName}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">所属工贸：</label>
					<div class="col-sm-4">
							${customerTemporaryEngineer.orgName}
					</div>
					<label class="col-sm-2 control-label">负责业务：</label>
					<div class="col-sm-4">
							<c:if test="${customerTemporaryEngineer.responsibleBusiness=='0'}">小机</c:if>
							<c:if test="${customerTemporaryEngineer.responsibleBusiness=='1'}">大机</c:if>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">固话：</label>
					<div class="col-sm-4">
							${customerTemporaryEngineer.cusTel}
					</div>
					<label class="col-sm-2 control-label">手机号：</label>
					<div class="col-sm-4">
							${customerTemporaryEngineer.cusMobile}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">网格分类：</label>
					<div class="col-sm-4">
								<c:if test="${customerTemporaryEngineer.wgClassify=='0'}">专业客户</c:if>
								<c:if test="${customerTemporaryEngineer.wgClassify=='1'}">普通客户</c:if>
					</div>
					<label class="col-sm-2 control-label">详细地址：</label>
					<div class="col-sm-4">
							${customerTemporaryEngineer.addressDetails}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">添加时间：</label>
					<div class="col-sm-4">
								<fmt:formatDate value="${customerTemporaryEngineer.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
					<label class="col-sm-2 control-label">状态：</label>
					<div class="col-sm-4">
						<c:if test="${customerTemporaryEngineer.status=='0'}">未审核</c:if>
						<c:if test="${customerTemporaryEngineer.status=='1'}">已审核</c:if>
						<c:if test="${customerTemporaryEngineer.status=='2'}">审核拒绝</c:if>
					</div>
				</div>
		<c:if test="${fns:hasPermission('customer:customerTemporaryEngineer:edit') || isAdd}">
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
<style>
	.col-sm-4{
		margin-top: 7px;
	}
</style>
</body>
</html>