<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>old管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="oldShopMsgCustcodeOrderList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">old列表</h3>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="oldShopMsgCustcodeOrder" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="id：">id：</label>
				<form:input path="id" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="code：">code：</label>
				<form:input path="code" htmlEscape="false" maxlength="60"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="msgid：">msgid：</label>
				<form:input path="msgid" htmlEscape="false" maxlength="10"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="产品信息：">产品信息：</label>
				<form:input path="product" htmlEscape="false" maxlength="200"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="工贸编号：">工贸编号：</label>
				<form:input path="orgid" htmlEscape="false" maxlength="60"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="工贸名称：">工贸名称：</label>
				<form:input path="orgname" htmlEscape="false" maxlength="60"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="公司名称：">公司名称：</label>
				<form:input path="companyname" htmlEscape="false" maxlength="200"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="订单来源  抢单   派单：">订单来源  抢单   派单：</label>
				<form:input path="fromsource" htmlEscape="false" maxlength="40"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="订单是否废弃订单是否废弃(0未处理，1跟进中,2零售关闭，3工程关闭)：">订单是否废弃订单是否废弃(0未处理，1跟进中,2零售关闭，3工程关闭)：</label>
				<form:input path="cancelfalg" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="废弃人：">废弃人：</label>
				<form:input path="canceler" htmlEscape="false" maxlength="60"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="废弃时间：">废弃时间：</label>
				<form:input path="canceltime" htmlEscape="false"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="废弃原因：">废弃原因：</label>
				<form:input path="cancelreason" htmlEscape="false" maxlength="400"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="经销商名称：">经销商名称：</label>
				<form:input path="custname" htmlEscape="false" maxlength="100"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="经销商编号：">经销商编号：</label>
				<form:input path="custcode" htmlEscape="false" maxlength="60"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="地址：">地址：</label>
				<form:input path="address" htmlEscape="false" maxlength="100"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="承接方式：">承接方式：</label>
				<form:input path="undertake" htmlEscape="false" maxlength="20"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="备注信息：">备注信息：</label>
				<form:input path="memo" htmlEscape="false" maxlength="150"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="预计订单金额：">预计订单金额：</label>
				<form:input path="totalcount" htmlEscape="false"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="生成订单时间：">生成订单时间：</label>
				<form:input path="addtime" htmlEscape="false"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="交易是否结束 0 未结束  1 结束：">交易是否结束 0 未结束  1 结束：</label>
				<form:input path="isend" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="是否中标 0 ,未中标，1中标，2 废标 10丢标：">是否中标 0 ,未中标，1中标，2 废标 10丢标：</label>
				<form:input path="isbind" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="选择承接方式是否超时,0 未超时 1 超时：">选择承接方式是否超时,0 未超时 1 超时：</label>
				<form:input path="timeoutflag" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="超时原因：">超时原因：</label>
				<form:input path="timeoutreason" htmlEscape="false" maxlength="500"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="中标时间：">中标时间：</label>
				<form:input path="bindtime" htmlEscape="false"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="提交留言后返回的编号：">提交留言后返回的编号：</label>
				<form:input path="infcode" htmlEscape="false" maxlength="60"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="是否安装：">是否安装：</label>
				<form:input path="isinstall" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="安装人：">安装人：</label>
				<form:input path="installpersion" htmlEscape="false" maxlength="100"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="安装时间：">安装时间：</label>
				<form:input path="installtime" htmlEscape="false"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="是否出库：">是否出库：</label>
				<form:input path="isdeliver" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="出库/物流  单号：">出库/物流  单号：</label>
				<form:input path="delivernum" htmlEscape="false" maxlength="100"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="出库时间/录入物流单号时间：">出库时间/录入物流单号时间：</label>
				<form:input path="delivertime" htmlEscape="false"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="是否签收：">是否签收：</label>
				<form:input path="issign" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="上传签收凭证：">上传签收凭证：</label>
				<form:input path="signurl" htmlEscape="false" maxlength="200"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="签收时间：">签收时间：</label>
				<form:input path="signtime" htmlEscape="false"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="是否晒单：">是否晒单：</label>
				<form:input path="issunburn" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="是否已回传400   0默认   1已回传：">是否已回传400   0默认   1已回传：</label>
				<form:input path="isbackpass" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="直单客单区分标识：">直单客单区分标识：</label>
				<form:input path="orderstatus" htmlEscape="false" maxlength="50"  class=" form-control"/>
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
			<shiro:hasPermission name="old:oldShopMsgCustcodeOrder:add">
				<a id="add" class="btn btn-primary" href="${ctx}/old/oldShopMsgCustcodeOrder/form" title="old"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="old:oldShopMsgCustcodeOrder:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="old:oldShopMsgCustcodeOrder:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="old:oldShopMsgCustcodeOrder:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/old/oldShopMsgCustcodeOrder/import" method="post" enctype="multipart/form-data"
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
	<table id="oldShopMsgCustcodeOrderTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="old:oldShopMsgCustcodeOrder:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="old:oldShopMsgCustcodeOrder:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>