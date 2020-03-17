<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>直采商詳情</title>
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
				<a class="panelButton" href="${ctx}/shop/directpurchaser/directpurchaser/list"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
				<ul>
				 <li>账号基础信息</li>
				</ul>
				<table class="table table-bordered">
				   <tbody>
						<tr>
							<td class="width-15 active"><label class="pull-right">用户名：</label></td>
							<td class="width-35">
							${purchaserAccount.loginName}
							</td>
							<td class="width-15 active"><label class="pull-right">账户类型：</label></td>
							<td class="width-35">
								<c:if test="${purchaser.isAdmin eq '1'}">
								主账号
								</c:if>
								<c:if test="${purchaser.isAdmin eq '0'}">
								 子账号
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">账号状态：</label></td>
							<td class="width-35">
								<c:if test="${purchaser.auditState eq '0'}">
								未审核
								</c:if>
								<c:if test="${purchaser.auditState eq '1'}">
								 审核通过
								</c:if>
								<c:if test="${purchaser.auditState eq '2'}">
								 审核不通过
								</c:if>
							</td>
							<td class="width-15 active"><label class="pull-right">企业名称：</label></td>
							<td class="width-35">
								${purchaser.companyName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">联系人姓名：</label></td>
							<td class="width-35">
								${purchaser.contacts}
							</td>
							<td class="width-15 active"><label class="pull-right">用户类型：</label></td>
				   			<td class="width-35">
				   				<c:if test="${purchaser.companyType eq '1'}">
								战略
								</c:if>
								<c:if test="${purchaser.companyType eq '0'}">
								 散户
								</c:if>
				   			</td>
				  		</tr>
				  		<tr>
							<td class="width-15 active"><label class="pull-right">公司电话：</label></td>
							<td class="width-35">
							${purchaser.companyTel}
							</td>
							<td class="width-15 active"><label class="pull-right">邮箱：</label></td>
				   			<td class="width-35">
				   			${purchaser.email}
				   			</td>
				  		</tr>
				  		<tr>
							<td class="width-15 active"><label class="pull-right">信用额度：</label></td>
							<td class="width-35">
							${purchaser.creditLineStr}
							</td>
							<td class="width-15 active"><label class="pull-right">注册时间：</label></td>
				   			<td class="width-35">
				   			<fmt:formatDate value="${purchaser.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				   			</td>
				  		</tr>
				  		<tr>
							<td class="width-15 active"><label class="pull-right">审核通过时间：</label></td>
							<td class="width-35">
							<fmt:formatDate value="${purchaser.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="width-15 active"><label class="pull-right"></label></td>
				   			<td class="width-35">
				   			</td>
				  		</tr>
				 	</tbody>
				</table>
				
				<ul><li>企业信息</li></ul>
				<table class="table table-bordered">
				   <tbody>
						<tr>
							<td class="width-15 active"><label class="pull-right">公司人数：</label></td>
				   			<td class="width-35">
				   			${purchaser.companyNum}
				   			</td>
				   			<td class="width-15 active"><label class="pull-right">公司性质：</label></td>
				   			<td class="width-35">
				   			${purchaser.companyNature}
				   			</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">手机：</label></td>
				   			<td class="width-35">
				   			${purchaser.mobile}
				   			</td>
				   			<td class="width-15 active"><label class="pull-right">行业：</label></td>
				   			<td class="width-35">
				   			 ${purchaser.industryId}
				   			</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">所在地区：</label></td>
				   			<td class="width-35">
				   			 ${purchaser.areaInfo}
				   			</td>
				   			<td class="width-15 active"><label class="pull-right">详细地址：</label></td>
				   			<td class="width-35">
				   			${purchaser.detailAddress}
				   			</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">营业执照：</label></td>
				   			<td class="width-35">
				   			 <img alt="" src="${pageContext.request.contextPath}${purchaser.licenceUrl}">
				   			</td>
				   			<td class="width-15 active"><label class="pull-right">企业logo：</label></td>
				   			<td class="width-35">
								<img src="${pageContext.request.contextPath}${purchaser.logoUrl}">
				   			</td>
						</tr>
				 	</tbody>
				</table>
						
				 <input type="hidden" name="provinceId">
				 <input type="hidden" name="cityId">
				 <input type="hidden" name="districtId">
				<ul><li>企业银行账户信息</li></ul>
				<table class="table table-bordered">
				   <tbody>
						<tr>
							<td class="width-15 active"><label class="pull-right">开户银行：</label></td>
				   			<td class="width-35">
				   			${purchaser.bankName}
				   			</td>
				   			<td class="width-15 active"><label class="pull-right">开户账号：</label></td>
				   			<td class="width-35">
				   			${purchaser.bankAccount}
				   			</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">开户地址：</label></td>
				   			<td class="width-35">
				   			${purchaser.bankAddress}
				   			</td>
				   			<td class="width-15 active"></td>
				   			<td class="width-35">
				   			</td>
						</tr>
					 	</tbody>
				</table>
				<ul><li>关联子账号</li></ul>
				<table class="table table-hover table-striped">
				   <tbody>
				   		<c:forEach var="item" items="${purchaserSubList}">
						<tr>
							<td ><label >用户名：</label></td>
				   			<td >
				   			${item.loginName}
				   			</td>
				   			<td ><label >企业88码：</label></td>
				   			<td >
				   			${item.loginNum}
				   			</td>
				   			<td ><label >企业名称：</label></td>
				   			<td >
				   			${item.companyName}
				   			</td>
				   			<td ><label >联系人姓名：</label></td>
				   			<td >
				   			${item.contacts}
				   			</td>
				   			<td ><label >手机号：</label></td>
				   			<td >
				   			${item.companyTel}
				   			</td>
						</tr>
						</c:forEach>
					 	</tbody>
				</table>
				<ul><li>审核记录</li></ul>
				<table class="table table-hover table-striped">
				   <tbody>
						<tr>
							<td ><label >审核人：</label></td>
				   			<td >
				   			${purchaser.auditor}
				   			</td>
				   			<td ><label >审核状态：</label></td>
				   			<td >
				   				<c:if test="${purchaser.auditState eq '0'}">
								未审核
								</c:if>
								<c:if test="${purchaser.auditState eq '1'}">
								 审核通过
								</c:if>
								<c:if test="${purchaser.auditState eq '2'}">
								 审核不通过
								</c:if>
				   			</td>
				   			<td ><label >审核意见：</label></td>
				   			<td >
				   			${purchaser.auditDesc}
				   			</td>
				   			<td ><label >审核时间 ：</label></td>
				   			<td >
				   			<fmt:formatDate value="${purchaser.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				   			</td>
				   			<td ><label >&nbsp;</label></td>
				   			<td >
				   			</td>
						</tr>
					</tbody>
				</table>
		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>