<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>需求相关管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="shopMsgList.js" %>
	<link href="${ctxStatic}/plugin/layui/dist/css/layui.css" rel="stylesheet"/>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">需求相关列表</h3>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="shopMsg" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="公司名称：">公司名称：</label>
				<form:input path="companyName" htmlEscape="false" maxlength="200"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="渠道来源：">渠道来源：</label>
				<form:select path="channel"  class="form-control m-b">
					<form:option value="" label="请选择渠道来源"/>
					<form:options items="${fns:getDictList('msg_channel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			 </div>
				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left" title="是否已读：">是否已读：</label>
					<form:select path="isRead"  class="form-control m-b">
						<form:option value="" label="请选择状态"/>
						<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left" title="部门：">部门：</label>
					<form:select path="depart"  class="form-control m-b">
						<form:option value="" label="请选择需求部门"/>
						<form:options items="${fns:getDictList('depart_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="工贸名称：">工贸名称：</label>
				 <form:select path="orgId"  class="form-control m-b">
					 <form:option value="" label="请选择工贸"/>
					 <form:options items="${fns:getDictList('industryAndTrade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				 </form:select>
			</div>

				<c:if test="${empty isSk}">
				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left" title="产品：">产品：</label>
					<form:input path="proGroup" htmlEscape="false" maxlength="60"  class=" form-control"/>
				</div>
				</c:if>

				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left" title="节点状态：">节点状态：</label>
					<form:select path="msgFlag"  class="form-control m-b">
						<%--姜广涛提议节点--%>
						<form:option value="" label="请选择节点"/>
						<form:option value="0" label="平台待审核"/>
						<form:option value="60" label="平台关闭"/>
						<form:option value="10" label="总监待审核"/>
						<form:option value="21" label="总监关闭"/>
						<%--<form:options items="${fns:getDictList('msg_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
					</form:select>
				</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="联系人：">联系人：</label>
				<form:input path="connectName" htmlEscape="false" maxlength="60"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="联系方式：">联系方式：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="50"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="邮箱：">邮箱：</label>
				<form:input path="email" htmlEscape="false" maxlength="60"  class=" form-control"/>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<div class="form-group">
					<label class="label-item single-overflow pull-left" title="生成时间：">&nbsp;生成时间：</label>
					<div class="col-xs-12">
						<div class="col-xs-12 col-sm-5">
							<div class='input-group date' id='beginCreateDate' style="left: -10px;" >
								<input type='text'  name="beginCreateDate" class="form-control"  />
								<span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
							</div>
						</div>
						<div class="col-xs-12 col-sm-1">
							~
						</div>
						<div class="col-xs-12 col-sm-5">
							<div class='input-group date' id='endCreateDate' style="left: -10px;" >
								<input type='text'  name="endCreateDate" class="form-control" />
								<span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
							</div>
						</div>
					</div>
				</div>
			</div>
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
			<shiro:hasPermission name="process:shopmsg:shopMsg:add">
				<a id="add" class="btn btn-primary" href="${ctx}/process/shopmsg/shopMsg/form" title="需求相关"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="process:shopmsg:shopMsg:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="process:shopmsg:shopMsg:closeMsg">
				<button id="close" class="btn btn-danger" disabled onclick="closeMsg()">
					<i class="glyphicon glyphicon-edit"></i> 关闭
				</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="process:shopmsg:shopMsg:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="process:shopmsg:shopMsg:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/process/shopmsg/shopMsg/import" method="post" enctype="multipart/form-data"
							 style="padding-left:20px;text-align:center;" ><br/>
							<input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　
							
							
						</form>
				</div>
			</shiro:hasPermission>
		<button id="btnExport" class="btn btn-info" onclick="exportData();"><i class="fa fa-folder-open-o"></i> 导出</button>



	        	<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
		    </div>
		
	<!-- 表格 -->
	<table id="shopMsgTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="process:shopmsg:shopMsg:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="process:shopmsg:shopMsg:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
	<div class="check" id="check" hidden>

		<form class="layui-form" action="" style="padding-top: 20px;">

			<div class="layui-form-item">
				<label class="layui-form-label">通过:</label>
				<div class="layui-input-block">
					<input type="radio" name="isCheckRadio" value="1" title="是" class="isCheck" checked lay-filter="isCheckRadio">
					<input type="radio" name="isCheckRadio" value="0" title="否" class="isCheck" lay-filter="isCheckRadio">

					<input type="hidden" name="isCheck" value="0" title="否" class="isCheck">
				</div>
			</div>
			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">备注:</label>
				<div class="layui-input-block">
					<textarea name="ptDescp" placeholder="请输入内容" class="layui-textarea ptDescp" style="width: 80%" maxlength="100"></textarea>
				</div>
			</div>



		</form>
	</div>
	<script src="${ctxStatic}/plugin/layui/dist/layui.js"></script>
	<script>
		layui.use('form', function(){
			var form = layui.form;
			form.on('radio(isCheckRadio)', function(data){
				//被点击的radio的value值
				$(".isCheck").val(data.value);
			});
		});

	</script>
</body>
</html>