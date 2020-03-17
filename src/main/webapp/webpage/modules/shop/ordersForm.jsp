<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
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
				<a class="panelButton" href="${ctx}/shop/orders"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="orders" action="${ctx}/shop/orders/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>订单编号：</label>
					<div class="col-sm-10">
						<form:input path="orderSn" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">店铺名称：</label>
					<div class="col-sm-10">
						<form:input path="storeName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">供应商：</label>
					<div class="col-sm-10">
						<form:input path="dealerName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">快捷通账号：</label>
					<div class="col-sm-10">
						<form:input path="kjtAccount" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>支付单号：</label>
					<div class="col-sm-10">
						<form:input path="paySn" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>采购商名称：</label>
					<div class="col-sm-10">
						<form:input path="purchaserName" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>采购商账号名称：</label>
					<div class="col-sm-10">
						<form:input path="purchaserLoginName" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">采购商手机：</label>
					<div class="col-sm-10">
						<form:input path="purchaserPhone" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>支付方式：</label>
					<div class="col-sm-10">
						<form:select path="paymentCode" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('payment_kind')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">付款（支付）时间：</label>
					<div class="col-sm-10">
						<form:input path="paymentDate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>商品总价格：</label>
					<div class="col-sm-10">
						<form:input path="goodsAmount" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>订单总价格：</label>
					<div class="col-sm-10">
						<form:input path="orderAmount" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">评价状态：</label>
					<div class="col-sm-10">
						<form:select path="evaluationState" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('evaluation_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>追加评价状态：</label>
					<div class="col-sm-10">
						<form:select path="evaluationAgainState" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('evaluation_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>订单状态：</label>
					<div class="col-sm-10">
						<form:select path="orderState" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('order_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">退款状态：</label>
					<div class="col-sm-10">
						<form:select path="refundState" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('order_refund_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">在线支付动作时间：</label>
					<div class="col-sm-10">
						<form:input path="apiPayDate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">外部交易订单号：</label>
					<div class="col-sm-10">
						<form:input path="tradeNo" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">采购商地址：</label>
					<div class="col-sm-10">
						<form:input path="addressInfo" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">运单信息：</label>
					<div class="col-sm-10">
						<form:input path="shippingInfo" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">快递名称：</label>
					<div class="col-sm-10">
						<form:input path="expresseName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">申请开发票的状态：</label>
					<div class="col-sm-10">
						<form:input path="isApplyInvoice" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否已开发票：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="isInvoiceFinished" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">确认收货时间：</label>
					<div class="col-sm-10">
						<form:input path="deliveryTime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
		<c:if test="${fns:hasPermission('shop:orders:edit') || isAdd}">
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