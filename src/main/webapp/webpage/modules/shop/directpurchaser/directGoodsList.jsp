<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品表管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="../goodsCategoryTreeList.js" %>
	<%@include file="directGoodsList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">关联商品表列表</h3>
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
							<div class="col-sm-2" >
								<button  class="btn btn-default btn-sm"  onclick="jh.openDialog('新建商品目录表', '${ctx}/shop/goodsCategory/form','800px', '500px', $('#goodsCategoryjsTree'))">
									<i class="fa fa-plus"></i>
								</button>
							</div>
						</div>
					</div>
					<div id="goodsCategoryjsTree"></div>
				</div>
				<div  class="col-sm-8 col-md-9 animated fadeInRight">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="goods" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="产品编码：">产品编码：</label>
				<form:input path="code" htmlEscape="false" maxlength="32"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="产品名称：">产品名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64"  class=" form-control"/>
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
			<shiro:hasPermission name="shop:goods:add">
				<button id="add" onclick="savePurchaserGoodsRelAll()" class="btn btn-primary"  title="批量上架商品"><i class="glyphicon glyphicon-plus"></i>批量上架商品</button>
			</shiro:hasPermission>
			 <a id="retlist" class="btn btn-primary" href="${ctx}/shop/directpurchaser/directpurchaser/list" title="返回采购商列表页">返回</a>
			<%--<shiro:hasPermission name="shop:goods:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/shop/goods/import" method="post" enctype="multipart/form-data"
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
	<table id="goodsTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="shop:goods:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="shop:goods:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
	</div>
</div>
<script>

</script>
</body>
</html>