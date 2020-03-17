<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>店铺申请管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="storeJoininList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">店铺申请列表</h3>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="storeJoinin" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="公司编码：">公司编码：</label>
				<form:input path="companyCode" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>
                <div class="col-xs-12 col-sm-6 col-md-4">
                    <label class="label-item single-overflow pull-left" title="公司名称：">公司名称：</label>
                    <form:input path="companyName" htmlEscape="false" maxlength="64"  class=" form-control"/>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4">
                    <label class="label-item single-overflow pull-left" title="联系人：">联系人：</label>
                    <form:input path="contacts" htmlEscape="false" maxlength="64"  class=" form-control"/>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4">
                    <label class="label-item single-overflow pull-left" title="电话：">电话：</label>
                    <form:input path="tel" htmlEscape="false" maxlength="64"  class=" form-control"/>
                </div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="店铺名称：">店铺名称：</label>
				<form:input path="storeName" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="审核状态：">审核状态：</label>
				<form:select path="auditState"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('audit_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
			<shiro:hasPermission name="shop:storeJoinin:add">
				<a id="add" class="btn btn-primary" href="${ctx}/shop/storeJoinin/form" title="店铺申请"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="shop:storeJoinin:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="shop:storeJoinin:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
        <shiro:hasPermission name="shop:storeJoinin:audit">
            <button id="audit" class="btn btn-sucess" disabled onclick="audit()"><i class="fa fa-check-square-o"></i> 审核</button>
            <div id="auditBox" class="hide">
                <div style="margin:10px;">
                    <form id="auditForm">
                        <input type="hidden" id="auditId" name="id">
                        <div class="form-group">
                            <label class="control-label">是否通过 ：</label>
                            <div class="radio">
                                <label class="radio-inline">
                                    <input type="radio" name="auditState" id="auditState1" value="1" > 是
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="auditState" id="auditState2" value="2"> 否
                                </label>
                            </div>
                        </div>
                        <div id="auditDescDiv" class="form-group hide">
                            <label class="control-label">原因：</label>
                            <div>
                                <textarea id="auditDesc" name="auditDesc" class="form-control " rows="4"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </shiro:hasPermission>
			<shiro:hasPermission name="shop:storeJoinin:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/shop/storeJoinin/import" method="post" enctype="multipart/form-data"
							 style="padding-left:20px;text-align:center;" ><br/>
							<input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　
							
							
						</form>
				</div>
			</shiro:hasPermission>
	        	<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
		    </div>
		
	<!-- 表格 -->
	<table id="storeJoininTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="shop:storeJoinin:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="shop:storeJoinin:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>