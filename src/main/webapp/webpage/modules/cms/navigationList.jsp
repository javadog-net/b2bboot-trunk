<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>导航管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="navigationList.js" %>
	<style>
		.jstree-icon:empty{
			vertical-align: middle;
			margin: 0 5px;
		}
	</style>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-body">
		<sys:message content="${message}"/>
		<!-- 搜索 -->
		<div class="accordion-group">
			<div id="collapseTwo" class="accordion-body collapse">
				<div class="accordion-inner">
					<form:form id="searchForm" modelAttribute="navigation" class="form form-horizontal well clearfix">
						<div class="col-xs-12 col-sm-6 col-md-4">
							<label class="label-item single-overflow pull-left" title="导航名称：">导航名称：</label>
							<input path="name" htmlEscape="false" maxlength="64"  class="form-control" id="search_q"/>
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
			<shiro:hasPermission name="cms:cmsModel:add">
				<a id="add" class="btn btn-primary" href="${ctx}/cms/navigation/form"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
				<i class="fa fa-search"></i> 检索
			</a>
		</div>

		<form id="listForm" method="post" class="form-horizontal">
			<table id="treeTable" class="table table-striped table-bordered table-condensed">
				<tr><th>导航名称</th><th>导航位置</th><th>导航属性</th><th title="是否显示">是否显示</th><th>排序</th><th>操作</th></tr>
				<c:forEach items="${list}" var="navigation">
					<tr id="${navigation.id}" pId="${navigation.parent.id ne '1'?navigation.parent.id:'0'}">
						<td class="pipei"><a href="javascript:void(0)" onclick="change('${navigation.id}')"><i class="jstree-icon jstree-themeicon fa fa-file-text-o jstree-themeicon-custom" role="presentation"></i><span>${navigation.name}</span></a></td>

						<td>${fns:getDictLabel(navigation.position,'navigation_position','')}</td>
						<td>${fns:getDictLabel(navigation.linkType,'link_type','')}</td>
						<td>${fns:getDictLabel(navigation.isShow, 'yes_no', '')}</td>
						<td style="text-align:center;">
							<a href="${ctx}/cms/navigation/navigationSort?sortType=up&id=${navigation.id}" rel="tooltip" data-placement="top" title="点击上升">
								<i class="fa fa-long-arrow-up"></i>
							</a>
							<a href="${ctx}/cms/navigation/navigationSort?sortType=down&id=${navigation.id}" rel="tooltip" data-placement="top" title="点击下降">
								<i class="fa fa-long-arrow-down"></i>
							</a>
						</td>
						<td>
							<shiro:hasPermission name="cms:navigation:edit">
								<a href="javascript:void(0)" onclick="edit('${navigation.id}')">修改</a>
								<a href="javascript:void(0)" onclick="deleteNavigation('${navigation.id}')">删除</a>
								<a href="javascript:void(0)" onclick="addList('${navigation.id}')">添加下级导航</a>
							</shiro:hasPermission>
						</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
	</div>
	</div>
</body>
</html>