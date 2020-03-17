<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>经销商订单管理</title>
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
				<a class="panelButton" href="${ctx}/process/shopmsgorder/shopMsgCustcodeOrder"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="shopMsgCustcodeOrder" action="${ctx}/process/shopmsgorder/shopMsgCustcodeOrder/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">需求ID：</label>
					<div class="col-sm-10">
						<form:input path="msgId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">工贸编号：</label>
					<div class="col-sm-10">
						<form:input path="orgId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">工贸名称：</label>
					<div class="col-sm-10">
						<form:input path="orgName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">公司名称：</label>
					<div class="col-sm-10">
						<form:input path="companyName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">订单来源  抢单   派单：</label>
					<div class="col-sm-10">
						<form:input path="fromSource" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">订单是否废弃订单是否废弃(0未处理，1跟进中,2零售关闭，3工程关闭)：</label>
					<div class="col-sm-10">
						<form:input path="cancelFlag" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">废弃人：</label>
					<div class="col-sm-10">
						<form:input path="canceler" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">废弃时间：</label>
					<div class="col-sm-10">
						<form:input path="cancelDate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">废弃原因：</label>
					<div class="col-sm-10">
						<form:input path="cancelReason" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">经销商名称：</label>
					<div class="col-sm-10">
						<form:input path="custName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">经销商编号：</label>
					<div class="col-sm-10">
						<form:input path="custCode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">地址：</label>
					<div class="col-sm-10">
						<form:input path="address" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">承接方式：</label>
					<div class="col-sm-10">
						<form:input path="underTake" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注信息：</label>
					<div class="col-sm-10">
						<form:input path="memo" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">预计订单金额：</label>
					<div class="col-sm-10">
						<form:input path="totalCount" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">交易是否结束 0 未结束  1 结束：</label>
					<div class="col-sm-10">
						<form:input path="isEnd" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否中标 0 ,未中标，1中标，2 废标 10丢标：</label>
					<div class="col-sm-10">
						<form:input path="isBind" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>选择承接方式是否超时,0 未超时 1 超时：</label>
					<div class="col-sm-10">
						<form:input path="timeoutFlag" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">超时原因：</label>
					<div class="col-sm-10">
						<form:input path="timeoutReason" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">中标时间：</label>
					<div class="col-sm-10">
						<form:input path="bindTime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否安装：</label>
					<div class="col-sm-10">
						<form:input path="isInstall" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">安装人：</label>
					<div class="col-sm-10">
						<form:input path="installPersion" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">安装时间：</label>
					<div class="col-sm-10">
						<form:input path="installDate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否出库：</label>
					<div class="col-sm-10">
						<form:input path="isDeliver" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">出库/物流  单号：</label>
					<div class="col-sm-10">
						<form:input path="deliverNum" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">出库时间/录入物流单号时间：</label>
					<div class="col-sm-10">
						<form:input path="deliverTime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否签收：</label>
					<div class="col-sm-10">
						<form:input path="isSign" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">上传签收凭证：</label>
					<div class="col-sm-10">
						<form:input path="signUrl" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">签收时间：</label>
					<div class="col-sm-10">
						<form:input path="signDate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否晒单：</label>
					<div class="col-sm-10">
						<form:input path="isSunburn" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否已回传400   0默认   1已回传：</label>
					<div class="col-sm-10">
						<form:input path="isBackpass" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
		<c:if test="${fns:hasPermission('process:shopmsgorder:shopMsgCustcodeOrder:edit') || isAdd}">
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