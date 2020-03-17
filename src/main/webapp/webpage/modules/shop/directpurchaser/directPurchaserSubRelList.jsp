<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>直购商管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="directPurchaserSubRelList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">已关联子账号列表</h3>
	</div>
	<div class="panel-body">
        <div class="row">
            <div id="left" class="col-sm-12">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="purchaser" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="公司代码：">公司代码：</label>
				<form:input path="companyCode" htmlEscape="false" maxlength="30"  class=" form-control acerr"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="公司名称：">公司名称：</label>
				<form:input path="companyName" htmlEscape="false" maxlength="100"  class=" form-control acerr"/>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<div class="form-group">
					<label class="label-item single-overflow pull-left" title="审核状态：">审核状态：</label>
					<div class="col-xs-12">
						<form:radiobuttons class="i-checks" path="auditState" items="${fns:getDictList('audit_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<button id="edit" class="btn btn-success"  onclick="toSetPurchaserRel()" >
	            	<i class="glyphicon glyphicon-edit"></i> 关联子账号
	            	
	        </button>
	        <button id="remove" class="btn btn-danger" onclick="deletePurchaserRelAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 批量移除子账号
	        </button>
	        <a id="retlist" class="btn btn-primary" href="${ctx}/shop/directpurchaser/directpurchaser/list" title="返回采购商列表页">返回</a>
	        
	        
		<%--	<shiro:hasPermission name="shop:directpurchaser:directpurchaser:add">
				<a id="add" class="btn btn-primary" href="${ctx}/shop/purchaser/purchaser/form" title="采购商"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="shop:directpurchaser:directpurchaser:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>--%>
	        	<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
		    </div>
		
	<!-- 表格 -->
	<table id="directPurchaserTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="shop:directpurchaser:directpurchaser:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="shop:directpurchaser:directpurchaser:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>
            </div>

            <div  id="right" class="panel panel-default col-sm-4" style="display:none">
                <div class="panel-heading">
                    <h3 class="panel-title"><a href="#" id="subfadeout" title="收起"><i class="fa fa-angle-double-left"></i> </a><label>子账号列表，所属采购商: </label><font id="purchaserLabel"></font><input type="hidden" id="purchaserId"/></h3>
                </div>
                <div class="panel-body">
                    <div id="purchaserAccoutToolbar">
                        <button id="purchaserAccountButton"  class="btn btn-outline btn-primary" title="添加子账号"><i class="fa fa-plus-circle"></i> 添加</button>
                    </div>
                    <!-- 表格 -->
                    <table id="purchaserAccoutTable"
                           data-toolbar="#purchaserAccoutToolbar"
                           data-id-field="id">
                    </table>

                </div>
            </div>
	</div>
	</div>
	</div>
</body>
</html>