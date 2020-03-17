<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>邮件管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="cmsEmailInfoList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">邮件列表</h3>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	
	
	<!-- 工具栏 -->
	<div id="toolbar">
				<a id="add" class="btn btn-primary" href="${ctx}/tools/email/cmsEmailInfo/form" title="邮件"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
				<button id="sendAll" class="btn btn-success" disabled onclick="sendAll()">
	            	<i class="glyphicon glyphicon-edit"></i> 发送
	        	</button>
	        	<!-- <a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a> -->
		    </div>
		
	<!-- 表格 -->
	<table id="cmsEmailInfoTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
        <li data-item="edit"><a>编辑</a></li>
        <li data-item="delete"><a>删除</a></li>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>