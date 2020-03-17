<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="ordersList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">订单列表</h3>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="orders" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="订单编号：">订单编号：</label>
				<form:input path="orderSn" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="店铺名称：">店铺名称：</label>
				<form:input path="storeName" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="供应商：">供应商：</label>
				<form:input path="dealerName" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="支付单号：">支付单号：</label>
				<form:input path="paySn" htmlEscape="false" maxlength="20"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="采购商：">采购商：</label>
				<form:input path="purchaserName" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="采购商账号：">采购商账号：</label>
				<form:input path="purchaserLoginName" htmlEscape="false" maxlength="50"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="支付方式：">支付方式：</label>
				<form:select path="paymentCode"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('payment_kind')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="评价状态：">评价状态 ：</label>
				<form:select path="evaluationState"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('evaluation_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
			<%-- <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="追加评价状态 0未评价，1已评价，2已过期未评价：">追加评价状态 0未评价，1已评价，2已过期未评价：</label>
				<form:select path="evaluationAgainState"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('evaluation_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>--%>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="订单状态：">订单状态：</label>
				<form:select path="orderState"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
			<%--
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="退款状态：">退款状态：</label>
				<form:select path="refundState"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_refund_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="订单来源：">订单来源：</label>
				<form:input path="orderFrom" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>--%>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="外部交易订单号：">外部交易订单号：</label>
				<form:input path="tradeNo" htmlEscape="false" maxlength="50"  class=" form-control"/>
			</div>
			<%-- <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="运单号：">运单号：</label>
				<form:input path="shippingCode" htmlEscape="false" maxlength="100"  class=" form-control"/>
			</div>--%>

			 <div class="col-xs-12 col-sm-6 col-md-4">
				<div class="form-group">
					<label class="label-item single-overflow pull-left" title="是否已开发票：">&nbsp;是否已开发票：</label>
					<div class="col-xs-12">
						<form:select path="isInvoiceFinished"  class="form-control m-b">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
			</div>
			<%-- <div class="col-xs-12 col-sm-6 col-md-4">
				<div class="form-group">
					<label class="label-item single-overflow pull-left" title="订单锁定类型:1不用锁定,2需要锁定,默认为1：">&nbsp;订单锁定类型:1不用锁定,2需要锁定,默认为1：</label>
					<div class="col-xs-12">
						<form:radiobuttons class="i-checks" path="orderLock" items="${fns:getDictList('order_lock')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</div>
				</div>
			</div>--%>
		 <div class="col-xs-12 col-sm-6 col-md-4">
			<div style="margin-top:26px">
			  <a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
			  <a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
			 </div>
	    </div>	
	</form:form>
	</div>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div id="toolbar">
			<shiro:hasPermission name="shop:orders:add">
				<a id="add" class="btn btn-primary" href="${ctx}/shop/orders/form" title="订单"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="shop:orders:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="shop:orders:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<%--<shiro:hasPermission name="shop:orders:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/shop/orders/import" method="post" enctype="multipart/form-data"
							 style="padding-left:20px;text-align:center;" ><br/>
							<input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　
							
							
						</form>
				</div>
			</shiro:hasPermission>--%>
	        	<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
<%--
			<shiro:hasPermission name="shop:orders:export">
--%>
				<%--<button id="btnExport" class="btn btn-info"  onclick="exportOrder()">
					导出
				</button>--%>
		<%--	</shiro:hasPermission>--%>
		    </div>
		
	<!-- 表格 -->
	<table id="ordersTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="shop:orders:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="shop:orders:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>