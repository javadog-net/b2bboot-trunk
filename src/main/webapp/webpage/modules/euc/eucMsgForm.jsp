<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>euc系统相关需求管理</title>
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
				<a class="panelButton" href="${ctx}/euc/eucMsg"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="eucMsg" action="${ctx}/euc/eucMsg/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">需求id：</label>
					<div class="col-sm-10">
						<form:input path="msgId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">商机编码：</label>
					<div class="col-sm-10">
						<form:input path="businessCode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">商机名称：</label>
					<div class="col-sm-10">
						<form:input path="businessName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">商机来源：</label>
					<div class="col-sm-10">
						<form:input path="businessSource" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">商机类型：</label>
					<div class="col-sm-10">
						<form:input path="businessType" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">top客户简称：</label>
					<div class="col-sm-10">
						<form:input path="topCustomerAbbreviation" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">甲方公司名称：</label>
					<div class="col-sm-10">
						<form:input path="companyNameA" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">甲方联系姓名：</label>
					<div class="col-sm-10">
						<form:input path="contactNameA" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">甲方联系电话：</label>
					<div class="col-sm-10">
						<form:input path="contactMobileA" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">行业：</label>
					<div class="col-sm-10">
						<form:input path="industry" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">状态：</label>
					<div class="col-sm-10">
						<form:input path="state" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">海尔战区：</label>
					<div class="col-sm-10">
						<form:input path="haierTheater" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">中心：</label>
					<div class="col-sm-10">
						<form:input path="center" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">网格小微：</label>
					<div class="col-sm-10">
						<form:input path="smallMesh" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">网格：</label>
					<div class="col-sm-10">
						<form:input path="grid" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">省份：</label>
					<div class="col-sm-10">
						<form:input path="province" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">省份id：</label>
					<div class="col-sm-10">
						<form:input path="provinceId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">城市	：</label>
					<div class="col-sm-10">
						<form:input path="city" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">城市id ：</label>
					<div class="col-sm-10">
						<form:input path="cityId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">区县：</label>
					<div class="col-sm-10">
						<form:input path="district" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">区县_id：</label>
					<div class="col-sm-10">
						<form:input path="districtId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">商机地址：</label>
					<div class="col-sm-10">
						<form:input path="businessOpportunityAddress" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">最近处理时间：</label>
					<div class="col-sm-10">
						<form:input path="lastProcessingTime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">录入日期：</label>
					<div class="col-sm-10">
						<form:input path="entryDate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">海尔接口人编码：</label>
					<div class="col-sm-10">
						<form:input path="projectManagerCode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">海尔接口人姓名：</label>
					<div class="col-sm-10">
						<form:input path="projectManagerName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">承接商编码：</label>
					<div class="col-sm-10">
						<form:input path="contractorCode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">承接商名称：</label>
					<div class="col-sm-10">
						<form:input path="contractorName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">承接商大渠道：</label>
					<div class="col-sm-10">
						<form:input path="businessChannel" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">竞争对手公司：</label>
					<div class="col-sm-10">
						<form:input path="competitors" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注：</label>
					<div class="col-sm-10">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否成功传输hps 0否 1是：</label>
					<div class="col-sm-10">
						<form:input path="isSendHps" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">项目编码(hps 回传)：</label>
					<div class="col-sm-10">
						<form:input path="projectCode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
		<c:if test="${fns:hasPermission('euc:eucMsg:edit') || isAdd}">
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