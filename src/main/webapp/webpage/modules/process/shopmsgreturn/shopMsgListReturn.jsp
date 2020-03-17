<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>需求相关管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="shopMsgListReturn.js" %>
	<link href="${ctxStatic}/plugin/layui/dist/css/layui.css" rel="stylesheet"/>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">400回传相关列表</h3>
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
				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left" title="节点状态：">节点状态：</label>
					<form:select path="msgFlag"  class="form-control m-b">
						<form:option value="" label="请选择节点"/>
						<form:options items="${fns:getDictList('msg_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
	        	<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
		    </div>

	<!-- 表格 -->
	<table id="shopMsgTable"   data-toolbar="#toolbar"></table>

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