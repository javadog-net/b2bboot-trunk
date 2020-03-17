<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>内容管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="cmsKindTreeList.js" %>
	<%@include file="cmsList.js" %>
	<style>
		#infoKindjsTree>ul{
			width: 100%;
		}
		.jstree-anchor{
			width: 85%;
			overflow: hidden;
			white-space: nowrap;
			text-overflow: ellipsis;
		}
	</style>
</head>
<body>
<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">内容管理</h3>
		</div>
		<div class="panel-body">
			<div class="row">
				<div class="col-sm-4 col-md-3" >
					<div class="form-group">
						<div class="row">
							<div class="col-sm-10" >
								<div class="input-search">
									<button type="submit" class="input-search-btn">
										<i class="fa fa-search" aria-hidden="true"></i></button>
									<input   id="search_q" type="text" class="form-control input-sm" name="" placeholder="查找...">

								</div>
							</div>
						</div>
					</div>
					<div id="infoKindjsTree"></div>
				</div>
				<div  class="col-sm-8 col-md-9 animated fadeInRight">
					<sys:message content="${message}"/>

					<!-- 搜索 -->
					<div class="accordion-group">
						<div id="collapseTwo" class="accordion-body collapse">
							<div class="accordion-inner">
								<form id="searchForm" method="post" action="${ctx}/cms/info/" class="form form-horizontal well clearfix">
									<div class="col-xs-12 col-sm-6 col-md-4">
										<label class="label-item single-overflow pull-left" title="栏目：">栏目：</label>
										<sys:treeselect id="kind" name="category.id" value="${category.id}" disabled="disabled" labelName="category.name" labelValue="${category.name}"
														title="栏目" url="/cms/category/treeData" cssClass="form-control " allowClear="false"/>
									</div>
									<div class="col-xs-4 col-sm-3 col-md-3">
										<label class="label-item single-overflow pull-left" title="标题：">标题：</label>
										<input type="text" name="title" class="form-control">
									</div>
									<div class="col-xs-4 col-sm-3 col-md-4 " style="height: 58px">
										<div style="margin-top:26px">
											<label>状态：</label>
											<c:forEach var="item" items="${fns:getDictList('cms_del_flag')}" varStatus="status">
												<span><input id="delFlag${status.index}" name="delFlag" class="i-checks required" type="radio" value="${item.value}" /><label for="delFlag${status.index}">${item.label}</label></span>
											</c:forEach>
										</div>
									</div>
									<div class="col-xs-4 col-sm-3 col-md-3">
										<div style="margin-top:26px">
											<a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
											<a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>

					<!-- 工具栏 -->
					<div id="toolbarInfo">
						<shiro:hasPermission name="cms:info:edit">
							<a id="addInfo" class="btn btn-primary" onclick="addInfo()"><i class="glyphicon glyphicon-plus"></i> 新建</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="cms:info:edit">
							<button id="editInfo" class="btn btn-success" disabled onclick="editInfo()">
								<i class="glyphicon glyphicon-edit"></i> 修改
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="cms:info:edit">
							<button id="removeInfo" class="btn btn-danger" disabled  onclick="deleteInfoAll()">
								<i class="glyphicon glyphicon-remove"></i> 删除
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="cms:info:edit">
							<button id="htmlInfo" class="btn btn-warning" disabled onclick="infoMakeHtml()">
								<i class="glyphicon glyphicon-edit"></i> 静态化
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="cms:info:edit">
							<button id="moveInfo" class="btn btn-info" disabled onclick="infoMove()">
								<i class="glyphicon glyphicon-edit"></i> 转移
							</button>
						</shiro:hasPermission>
						<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
							<i class="fa fa-search"></i> 检索
						</a>
					</div>

					<!-- 表格 -->
					<table id="bootStrapTable"></table>

					<%-- 右边添加 --%>
					<div class="addright" style="display: none;background-color: #FFFFFF">
						<iframe id="frame" frameborder="0" width="100%" height="1200px" scrolling="no"></iframe>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>