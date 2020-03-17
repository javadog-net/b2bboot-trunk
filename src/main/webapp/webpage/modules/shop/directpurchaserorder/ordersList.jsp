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
	<script src="${ctxStatic}/plugin/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
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
				<form:form id="searchForm" modelAttribute="order" class="form form-horizontal well clearfix">
					 <div class="col-xs-12 col-sm-6 col-md-4">
						<label class="label-item single-overflow pull-left" title="订单编号：">订单编号：</label>
						 <input id="orderId" name="orderId" maxlength="64"  class=" form-control"/>
					</div>
					<div class="col-xs-12 col-sm-6 col-md-4">
						<label class="label-item single-overflow pull-left" title="用户名：">用户名：</label>
						<input id="customerName" name="customerName" maxlength="64"  class=" form-control"/>
					</div>
					<div class="col-xs-12 col-sm-6 col-md-4">
						<label class="label-item single-overflow pull-left" title="订单状态：">订单状态：</label>
						<select id="orderStateh" name="orderStateh"  class="form-control m-b">
							<option value="" >请选择</option>
							<c:forEach items="${dictValues}" var="a">
								<option value="${a.value}" >${a.label}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-xs-12 col-sm-6 col-md-4">
                        <div class="label-item single-overflow pull-left" title="下单时间：">下单时间：</div>
						<div style="width: 900px;height:35px;float: left ;">
							<input id="createOpeTime" name="createOpeTime" type="text" readonly="readonly" maxlength="20" class="form-control date"
								   style="width: 270px;height:35px;float: left;background: #fff;"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>&nbsp;
							<div style="float: left;padding: 0 15px;line-height:35px" >到</div>
							<input id="createOpeTime2" name="createOpeTime2" type="text" readonly="readonly" maxlength="20" class="form-control date"
								   style="width: 270px;height:35px;float: left;background: #fff;"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
							<div class="col-xs-12 col-sm-6 col-md-4" style="float: left; width: 150px; margin-top:3px;margin-left:10px">
								<div>
									<a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
									<a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
								</div>
							</div>
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