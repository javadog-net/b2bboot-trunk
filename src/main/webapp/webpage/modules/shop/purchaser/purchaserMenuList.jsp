<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="ani"/>
    <link href="${ctxStatic}/plugin/layui/dist/css/layui-icon.css" rel="stylesheet" />
	<%@include file="purchaserMenuList.js" %>
</head>
<body>

	<div class="wrapper wrapper-content">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">菜单列表  </h3>
			</div>
			
			<div class="panel-body">
			<sys:message content="${message}"/>
	
			<!-- 工具栏 -->
			<div class="row">
			<div class="col-sm-12">
				<div class="pull-left treetable-bar">
					<shiro:hasPermission name="shop:purchaser:purchaserMenu:add">
						<a id="add" class="btn btn-primary" onclick="jh.openDialog('新建菜单', '${ctx}/shop/purchaser/purchaserMenu/form','800px', '500px', $purchaserMenuTreeTable)"><i class="glyphicon glyphicon-plus"></i> 新建菜单</a><!-- 增加按钮 -->
					</shiro:hasPermission>
			       <button class="btn btn-default" data-toggle="tooltip" data-placement="left" onclick="refresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
				
				</div>
			</div>
			</div>
			<table id="purchaserMenuTreeTable" class="table table-hover">
				<thead>
                    <tr>
                        <th>名称</th>
                        <th>链接</th>
                        <th>排序</th>
                        <th>显示/隐藏</th>
                        <th>权限标识</th>
                        <th>操作</th>
                    </tr>
				</thead>
				<tbody id="purchaserMenuTreeTableList"></tbody>
			</table>
			<br/>
			</div>
			</div>
	</div>
</body>
</html>