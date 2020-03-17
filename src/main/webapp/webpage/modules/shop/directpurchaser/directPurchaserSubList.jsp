<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>关联子账号</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="directPurchaserSubList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">关联子账号</h3>
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
				<form:input path="companyCode" htmlEscape="false" maxlength="30"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="公司名称：">公司名称：</label>
				<form:input path="companyName" htmlEscape="false" maxlength="100"  class=" form-control"/>
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
				<div class="form-group">
					<label class="label-item single-overflow pull-left" title="用户类型：">用户类型：</label>
					<div class="col-xs-12">
						<form:radiobuttons class="i-checks" path="companyType" items="${fns:getDictList('audit_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<button id="add" onclick="savePurchaserRelAll()" class="btn btn-primary"  title="关联子账号"><i class="glyphicon glyphicon-plus"></i>批量关联子账号</button>
				<a id="retlist" class="btn btn-primary" href="${ctx}/shop/directpurchaser/directpurchaser/list" title="返回采购商列表页">返回</a>
		<%--	<shiro:hasPermission name="shop:directpurchaser:directpurchaser:add">
				<a id="add" class="btn btn-primary" href="${ctx}/shop/purchaser/purchaser/form" title="采购商"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="shop:directpurchaser:directpurchaser:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>--%>
			<shiro:hasPermission name="shop:directpurchaser:directpurchaser:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 注销
	        	</button>
			</shiro:hasPermission>
         <shiro:hasPermission name="shop:directpurchaser:directpurchaser:audit">
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
			<%--<shiro:hasPermission name="shop:directpurchaser:directpurchaser:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/shop/purchaser/purchaser/import" method="post" enctype="multipart/form-data"
							 style="padding-left:20px;text-align:center;" ><br/>
							<input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　
							
							
						</form>
				</div>
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