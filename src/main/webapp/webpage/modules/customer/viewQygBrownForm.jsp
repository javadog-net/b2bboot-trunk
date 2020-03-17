<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>工程版信息视图管理</title>
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
				<a class="panelButton" href="${ctx}/customer/viewQygBrown"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="viewQygBrown" action="${ctx}/customer/viewQygBrown/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>brown_id：</label>
					<div class="col-sm-10">
						<form:input path="brownId" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>project_id：</label>
					<div class="col-sm-10">
						<form:input path="projectId" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">project_code：</label>
					<div class="col-sm-10">
						<form:input path="projectCode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">project_name：</label>
					<div class="col-sm-10">
						<form:input path="projectName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">contract_id：</label>
					<div class="col-sm-10">
						<form:input path="contractId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">brown_code：</label>
					<div class="col-sm-10">
						<form:input path="brownCode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">product_group_id：</label>
					<div class="col-sm-10">
						<form:input path="productGroupId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">product_group_code：</label>
					<div class="col-sm-10">
						<form:input path="productGroupCode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">product_group_name：</label>
					<div class="col-sm-10">
						<form:input path="productGroupName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">dealer_id：</label>
					<div class="col-sm-10">
						<form:input path="dealerId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">dealer_code：</label>
					<div class="col-sm-10">
						<form:input path="dealerCode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">dealer_name：</label>
					<div class="col-sm-10">
						<form:input path="dealerName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">center：</label>
					<div class="col-sm-10">
						<form:input path="center" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">type：</label>
					<div class="col-sm-10">
						<form:input path="type" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">domain_code：</label>
					<div class="col-sm-10">
						<form:input path="domainCode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">open_system：</label>
					<div class="col-sm-10">
						<form:input path="openSystem" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">brown_count：</label>
					<div class="col-sm-10">
						<form:input path="brownCount" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">expire_time：</label>
					<div class="col-sm-10">
						<form:input path="expireTime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">be_valid：</label>
					<div class="col-sm-10">
						<form:input path="beValid" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">is_check：</label>
					<div class="col-sm-10">
						<form:input path="isCheck" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">effect_time：</label>
					<div class="col-sm-10">
						<form:input path="effectTime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">is_success：</label>
					<div class="col-sm-10">
						<form:input path="isSuccess" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">rrs_be_success：</label>
					<div class="col-sm-10">
						<form:input path="rrsBeSuccess" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">rrs_success_time：</label>
					<div class="col-sm-10">
						<form:input path="rrsSuccessTime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">cdepcode：</label>
					<div class="col-sm-10">
						<form:input path="cdepcode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">caddress：</label>
					<div class="col-sm-10">
						<form:input path="caddress" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">gvs_be_success：</label>
					<div class="col-sm-10">
						<form:input path="gvsBeSuccess" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">gvs_time：</label>
					<div class="col-sm-10">
						<form:input path="gvsTime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">gvs_message：</label>
					<div class="col-sm-10">
						<form:input path="gvsMessage" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">最后更新时间：</label>
					<div class="col-sm-10">
						<form:input path="lastModifiedDate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">申请总数量：</label>
					<div class="col-sm-10">
						<form:input path="orderQuantitySum" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">到期日期(审批通过后会将该字段更新到expire_time上)：</label>
					<div class="col-sm-10">
						<form:input path="expireTimeBeforeApprove" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">类型(审批通过后会将该字段更新到type上)：</label>
					<div class="col-sm-10">
						<form:input path="typeBeforeApprove" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">基础岗位ID：</label>
					<div class="col-sm-10">
						<form:input path="basePostId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">基础岗位编码：</label>
					<div class="col-sm-10">
						<form:input path="basePostCode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">基础岗位名称：</label>
					<div class="col-sm-10">
						<form:input path="basePostName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">帐套：</label>
					<div class="col-sm-10">
						<form:input path="gmzt" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">数据源：</label>
					<div class="col-sm-10">
						<form:input path="infoSourse" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">客户地址：</label>
					<div class="col-sm-10">
						<form:input path="cusAddress" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">联系人：</label>
					<div class="col-sm-10">
						<form:input path="custContact" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">电话：</label>
					<div class="col-sm-10">
						<form:input path="ctel" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">移动电话：</label>
					<div class="col-sm-10">
						<form:input path="cmobile" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">工程单位-R1甲方名称：</label>
					<div class="col-sm-10">
						<form:input path="cproName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">工程单位地址-R项目地址：</label>
					<div class="col-sm-10">
						<form:input path="cproAddress" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">R2联系人：</label>
					<div class="col-sm-10">
						<form:input path="cproContact" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">R2联系方式：</label>
					<div class="col-sm-10">
						<form:input path="cproTel" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">移动电话2：</label>
					<div class="col-sm-10">
						<form:input path="cproMobile" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">R2行业门类编码：</label>
					<div class="col-sm-10">
						<form:input path="sysId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">R2行业门类名称：</label>
					<div class="col-sm-10">
						<form:input path="sysName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">政策兑现形式：</label>
					<div class="col-sm-10">
						<form:input path="backWard" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">整单金额：</label>
					<div class="col-sm-10">
						<form:input path="iallMoney" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">单据类型：</label>
					<div class="col-sm-10">
						<form:input path="billFlag" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">创建人ID：</label>
					<div class="col-sm-10">
						<form:input path="createdById" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">最后修改人ID：</label>
					<div class="col-sm-10">
						<form:input path="lastModifiedById" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否删除：</label>
					<div class="col-sm-10">
						<form:input path="deleted" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">创建人：</label>
					<div class="col-sm-10">
						<form:input path="createdBy" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">创建时间：</label>
					<div class="col-sm-10">
						<form:input path="createdDate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">最后修改人名称：</label>
					<div class="col-sm-10">
						<form:input path="lastModifiedBy" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">创建来源信息：</label>
					<div class="col-sm-10">
						<form:input path="creProId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">修改来源信息：</label>
					<div class="col-sm-10">
						<form:input path="modProId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">批处理时间：</label>
					<div class="col-sm-10">
						<form:input path="batchDate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
		<c:if test="${fns:hasPermission('customer:viewQygBrown:edit') || isAdd}">
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