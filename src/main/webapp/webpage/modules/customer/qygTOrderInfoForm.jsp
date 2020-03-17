<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>巨商会订单info管理</title>
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
				<a class="panelButton" href="${ctx}/customer/qygTOrderInfo"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="qygTOrderInfo" action="${ctx}/customer/qygTOrderInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">订单号：</label>
					<div class="col-sm-10">
						<form:input path="orderNo" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">预计到货时间：</label>
					<div class="col-sm-10">
						<form:input path="planInDate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">创建订单时间：</label>
					<div class="col-sm-10">
						<form:input path="orderDate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">创建时间：</label>
					<div class="col-sm-10">
						<form:input path="createdTime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">一次物流时间：</label>
					<div class="col-sm-10">
						<form:input path="sapDn1date" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">二次物流时间：</label>
					<div class="col-sm-10">
						<form:input path="sapDn5date" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">中心收货时间：</label>
					<div class="col-sm-10">
						<form:input path="sapCenterRecieveDate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">ptd计划发货时间：</label>
					<div class="col-sm-10">
						<form:input path="sapPtdSendDate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">评审时间：</label>
					<div class="col-sm-10">
						<form:input path="sapJudgeDate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">评审状态：</label>
					<div class="col-sm-10">
						<form:input path="sapJudgeStatus" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">评审拒绝原因：</label>
					<div class="col-sm-10">
						<form:input path="sapJudgeReason" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">返单时间：</label>
					<div class="col-sm-10">
						<form:input path="sapReorderDate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">标记是否一次物流已发货（初始0，是1）：</label>
					<div class="col-sm-10">
						<form:input path="sapSended1" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">标记是否二次物流已发货（初始0，是1，拒绝2）：</label>
					<div class="col-sm-10">
						<form:input path="sapSended5" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">标记是否已评审（初始0，是1，拒绝2）：</label>
					<div class="col-sm-10">
						<form:input path="sapJudged" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">标记是否已返单（初始0，是1）：</label>
					<div class="col-sm-10">
						<form:input path="sapReorderd" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">标记是否已开发票（初始0，是1）：</label>
					<div class="col-sm-10">
						<form:input path="sapInvoiced" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否制定发货计划（初始0，是1）：</label>
					<div class="col-sm-10">
						<form:input path="sapPtded" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否中心收货（初始0，是1）：</label>
					<div class="col-sm-10">
						<form:input path="sapCenterRecieved" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
		<c:if test="${fns:hasPermission('customer:qygTOrderInfo:edit') || isAdd}">
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