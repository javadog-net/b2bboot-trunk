<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>客单需求管理</title>
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
			
	        $('#estimatedTimeBid').datetimepicker({
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
				<a class="panelButton" href="${ctx}/customer/customerMsg"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="customerMsg" action="${ctx}/customer/customerMsg/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
			<h3 style="margin-bottom: 30px;">企业购订单信息</h3>
				<div class="form-group">
					<label class="col-sm-2 control-label">客单需求订单号：</label>
					<div class="col-sm-4">
						${customerMsg.msgId}
					</div>
					<label class="col-sm-2 control-label">项目名称：</label>
					<div class="col-sm-4">
							${customerMsg.projectName}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">甲方公司名称：</label>
					<div class="col-sm-4">
							${customerMsg.firstCompanyName}
					</div>
					<label class="col-sm-2 control-label">统一社会信用代码：</label>
					<div class="col-sm-4">
							${customerMsg.firstCompanyOrgCode}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">地址（省份）：</label>
					<div class="col-sm-4">
							${customerMsg.addressProvinceName}
					</div>
					<label class="col-sm-2 control-label">地址(城市)：</label>
					<div class="col-sm-4">
							${customerMsg.addressCityName}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">地址(地区)：</label>
					<div class="col-sm-4">
							${customerMsg.addressCountyName}
					</div>
					<label class="col-sm-2 control-label">详细地址：</label>
					<div class="col-sm-4">
							${customerMsg.addressDetail}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">项目录入人编码：</label>
					<div class="col-sm-4">
							${customerMsg.projectCreaterCode}
					</div>
					<label class="col-sm-2 control-label">项目录入姓名：</label>
					<div class="col-sm-4">
							${customerMsg.projectCreaterName}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">项目经理编码：</label>
					<div class="col-sm-4">
							${customerMsg.projectManagerCode}
					</div>
					<label class="col-sm-2 control-label">项目经理名称：</label>
					<div class="col-sm-4">
							${customerMsg.projectManagerName}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">添加时间：</label>
					<div class="col-sm-4">
						<fmt:formatDate value="${customerMsg.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
					<label class="col-sm-2 control-label">产品组（多个以逗号隔开）：</label>
					<div class="col-sm-4">
							${customerMsg.productGroup}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">客户编码：</label>
					<div class="col-sm-4">
							${customerMsg.contractorCode}
					</div>
					<label class="col-sm-2 control-label">承接商名称：</label>
					<div class="col-sm-4">
							${customerMsg.contractorName}
					</div>
				</div>
				<c:if test="${customerMsg.status=='0'}">
					<div class="form-group">
						<label class="col-sm-2 control-label">是否有效：</label>
						<div class="col-sm-4">
							<c:if test="${customerMsg.status=='1'}">是</c:if>
							<c:if test="${customerMsg.status=='0'}">否</c:if>
						</div>
						<label class="col-sm-2 control-label">失败原因：</label>
						<div class="col-sm-4">
								${customerMsg.errorMsg}
						</div>
					</div>
				</c:if>

			<c:if test="${customerMsg.status=='1'}">
				<div class="form-group">
					<label class="col-sm-2 control-label">是否有效：</label>
					<div class="col-sm-4">
						<c:if test="${customerMsg.status=='1'}">是</c:if>
						<c:if test="${customerMsg.status=='0'}">否</c:if>
					</div>
					<label class="col-sm-2 control-label">漏斗项目回传id：</label>
					<div class="col-sm-4">
							${customerMsg.projectId}
					</div>
				</div>
			</c:if>
				<div class="form-group">
					<label class="col-sm-2 control-label">预计投标日期：</label>
					<div class="col-sm-4">
						<fmt:formatDate value="${customerMsg.estimatedTimeBid}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
					<label class="col-sm-2 control-label">甲方联系人姓名：</label>
					<div class="col-sm-4">
							${customerMsg.firstContactName}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">职位：</label>
					<div class="col-sm-4">
							${customerMsg.position}
					</div>
					<label class="col-sm-2 control-label">phone：</label>
					<div class="col-sm-4">
							${customerMsg.phone}
					</div>
				</div>
			<h3 style="margin-bottom: 30px;">漏斗订单信息</h3>
			<div class="form-group">
				<label class="col-sm-2 control-label">甲方公司名称：</label>
				<div class="col-sm-4">
						${customerMsg.customerProjectInfo.companyname}
				</div>
				<label class="col-sm-2 control-label">创建时间：</label>
				<div class="col-sm-4">
						<fmt:formatDate value="${customerMsg.customerProjectInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">地址：</label>
				<div class="col-sm-4">
						${customerMsg.customerProjectInfo.address}
				</div>
				<label class="col-sm-2 control-label">预计总金额：</label>
				<div class="col-sm-4">
						${customerMsg.customerProjectInfo.allmoney}
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">创建人：</label>
				<div class="col-sm-4">
						${customerMsg.customerProjectInfo.creater}
				</div>
				<label class="col-sm-2 control-label">需求编号：</label>
				<div class="col-sm-4">
						${customerMsg.customerProjectInfo.msgId}
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">节点名称：</label>
				<div class="col-sm-4">
						${customerMsg.customerProjectInfo.nodename}
				</div>
				<label class="col-sm-2 control-label">工贸名称：</label>
				<div class="col-sm-4">
						${customerMsg.customerProjectInfo.orgname}
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label">项目编码：</label>
				<div class="col-sm-4">
						${customerMsg.customerProjectInfo.projectcode}
				</div>
				<label class="col-sm-2 control-label">项目名称：</label>
				<div class="col-sm-4">
						${customerMsg.customerProjectInfo.projectname}
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label">项目来源：</label>
				<div class="col-sm-4">
						${customerMsg.customerProjectInfo.projectsourse}
				</div>
				<label class="col-sm-2 control-label">投标进展：</label>
				<div class="col-sm-4">
						${customerMsg.customerProjectInfo.bidprogress}
				</div>
			</div>
			<h3 style="margin-bottom: 30px;">产品组信息</h3>
			<table class="projectProduct">
				<thead>
					<tr>
						<th>产品组名称</th>
						<th>产品组编码</th>
						<th>项目编号</th>
						<th>地址</th>
					</tr>
				</thead>
				</thead>
				<c:forEach var="s" items="${customerMsg.listCustomerProjectProduct}" >
				<tr>
					<td>${s.name}</td>
					<td>${s.code}</td>
					<td>${s.projectcode}</td>
					<td>${s.address}</td>
				</tr>
				</c:forEach>
			</table>
		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>
<style>
	.form-horizontal .control-label{
		padding-top: 0px!important;
	}

	.projectProduct {
		margin-left: 156px;
	}
	.projectProduct tr{
		height: 50px;
		line-height: 50px;
	}
	.projectProduct tr td{
		width: 20%;
	}
</style>
</body>
</html>