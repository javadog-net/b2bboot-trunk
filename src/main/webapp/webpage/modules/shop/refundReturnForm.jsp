<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>退款管理</title>
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
				<a class="panelButton" href="${ctx}/shop/refundReturn"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="refundReturn" action="${ctx}/shop/refundReturn/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>订单编号：</label>
					<div class="col-sm-10">
						<form:input path="orderSn" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>申请编号：</label>
					<div class="col-sm-10">
						<form:input path="refundSn" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>店铺ID：</label>
					<div class="col-sm-10">
						<form:input path="storeId" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>店铺名称：</label>
					<div class="col-sm-10">
						<form:input path="storeName" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>采购商ID：</label>
					<div class="col-sm-10">
						<form:input path="purchaserId" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>采购商名称：</label>
					<div class="col-sm-10">
						<form:input path="purchaserName" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>产品编码：</label>
					<div class="col-sm-10">
						<form:input path="code" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>订单商品ID：</label>
					<div class="col-sm-10">
						<form:input path="orderGoodsId" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>商品名称：</label>
					<div class="col-sm-10">
						<form:input path="goodsName" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>商品数量：</label>
					<div class="col-sm-10">
						<form:input path="goodsNum" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">退款金额：</label>
					<div class="col-sm-10">
						<form:input path="refundAmount" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">商品主图地址：</label>
					<div class="col-sm-10">
						<form:input path="mainPicUrl" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">订单商品类型:1默认2抢购商品3组合套装：</label>
					<div class="col-sm-10">
						<form:select path="orderGoodsType" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('order_goods_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">申请类型:1 退款,2 退货,默认为1：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="refundType" items="${fns:getDictList('refund_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">申请状态:1 处理中,2 待管理员处理,3 已完成,默认为1：</label>
					<div class="col-sm-10">
						<form:select path="refundState" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('refund_process_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">采购商申请原因：</label>
					<div class="col-sm-10">
						<form:input path="purchaserDesc" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">退货类型:1不用退货,2需要退货,默认为1：</label>
					<div class="col-sm-10">
						<form:select path="returnType" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('order_return_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">订单锁定类型:1不用锁定,2需要锁定,默认为1：</label>
					<div class="col-sm-10">
						<form:select path="orderLock" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('order_lock')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">物流单号：</label>
					<div class="col-sm-10">
						<form:input path="shipingCode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">申请时间：</label>
					<div class="col-sm-10">
						<form:input path="addTime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">图片：</label>
					<div class="col-sm-10">
						<form:input path="picInfo" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">原因内容：</label>
					<div class="col-sm-10">
						<form:input path="reasonInfo" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">审核时间：</label>
					<div class="col-sm-10">
						<form:input path="auditTime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">审核人：</label>
					<div class="col-sm-10">
						<form:input path="auditor" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">审核状态:1待审核,2同意,3不同意,默认为1：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="auditState" items="${fns:getDictList('audit_state')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">审核备注：</label>
					<div class="col-sm-10">
						<form:input path="auditDesc" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">退款时间：</label>
					<div class="col-sm-10">
						<form:input path="refundTime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">原因ID:0为其它：</label>
					<div class="col-sm-10">
						<form:input path="reasonId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">退款备注：</label>
					<div class="col-sm-10">
						<form:input path="refundDesc" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
		<c:if test="${fns:hasPermission('shop:refundReturn:edit') || isAdd}">
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