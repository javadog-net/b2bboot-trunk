<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>栏目管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="categoryList.js" %>
	<style>
		.jstree-icon:empty{
			vertical-align: middle;
			margin: 0 5px;
		}
		.fa-long-arrow-up,.fa-long-arrow-down{
			color: #3ca2e0;
			cursor: pointer;
		}
		.fa-long-arrow-up:hover,.fa-long-arrow-down:hover{
			color: #1c7ab3;
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
					<form:form id="searchForm" modelAttribute="category" class="form form-horizontal well clearfix">
						<div class="col-xs-12 col-sm-6 col-md-4">
							<label class="label-item single-overflow pull-left" title="模型名称：">栏目名称：</label>
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
			<shiro:hasPermission name="cms:category:edit">
				<a id="add" class="btn btn-primary" href="${ctx}/cms/category/form"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="cms:category:edit">
				<a id="static" class="btn btn-warning" onclick="categoryHtml() "><i class="glyphicon glyphicon-edit"></i> 静态化</a>
			</shiro:hasPermission>
			<%--<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
				<i class="fa fa-search"></i> 检索
			</a>--%>
		</div>

	<form id="listForm" method="post" class="form-horizontal">
		<table id="treeTable" class="table table-striped table-bordered table-condensed">
			<tr>
				<th>栏目名称</th>
				<th>栏目标识</th>
				<th>内容标识</th>
				<th title="是否在导航中显示该栏目">是否在导航中显示</th>
				<th>排序</th>
				<th>操作</th></tr>
			<c:forEach items="${list}" var="category">
				<tr id="${category.id}" pId="${category.parent.id ne '1'?category.parent.id:'0'}">
					<td class="pipei"><a href="javascript:void(0)" onclick="previewCategory('${category.id}')"><i class="jstree-icon jstree-themeicon fa fa-file-text-o jstree-themeicon-custom" role="presentation"></i><span>${category.name}</span></a></td>
					<td>${category.categoryMark}</td>
					<td>${category.contentTemplate}</td>
					<td>${fns:getDictLabel(category.isShow, 'yes_no', '隐藏')}</td>
					<td style="text-align:center;">
						<a href="${ctx}/cms/category/categorySort.do?sortType=up&id=${category.id}" rel="tooltip" data-placement="top" title="点击上升">
							<i class="fa fa-long-arrow-up"></i>
						</a>
						<a href="${ctx}/cms/category/categorySort.do?sortType=down&id=${category.id}" rel="tooltip" data-placement="top" title="点击下降">
							<i class="fa fa-long-arrow-down"></i>
						</a>
					</td>
					<td>
						<shiro:hasPermission name="cms:category:edit">
							<a href="javascript:void(0)" onclick="edit('${category.id}')">修改</a>
							<a href="javascript:void(0)" onclick="deleteCategory('${category.id}')">删除</a>
							<a href="javascript:void(0)" onclick="addList('${category.id}')">添加下级栏目</a>
							<a href="javascript:void(0)" onclick="changeCategory('${category.id}')">转移</a>
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