<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供应商管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="dealerDispatcherList.js" %>
</head>
<body>
<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">供应商列表</h3>
		</div>
		<div class="panel-body">
			<div class="row">
				<div id="left" class="col-sm-12">
					<sys:message content="${message}"/>

					<!-- 搜索 -->
					<div class="accordion-group">
						<div id="collapseTwo" class="accordion-body collapse">
							<div class="accordion-inner">
								<form:form id="searchForm" modelAttribute="dealer" class="form form-horizontal well clearfix">
									<div class="col-xs-12 col-sm-6 col-md-4">
										<label class="label-item single-overflow pull-left" title="工贸编码：">工贸编码：</label>
										<form:input path="gmId" htmlEscape="false" maxlength="64"  class=" form-control"/>
									</div>
									<div class="col-xs-12 col-sm-6 col-md-4">
										<label class="label-item single-overflow pull-left" title="承接区域：">承接区域：</label>
										<form:input path="undertakeArea" htmlEscape="false" maxlength="64"  class=" form-control"/>
									</div>
									<div class="col-xs-12 col-sm-6 col-md-4">
										<label class="label-item single-overflow pull-left" title="承接品类：">承接品类：</label>
										<form:input path="underProduct" htmlEscape="false" maxlength="20"  class=" form-control"/>
									</div>
									<div class="col-xs-12 col-sm-6 col-md-4">
										<label class="label-item single-overflow pull-left" title="公司联系人电话：">联系人电话：</label>
										<form:input path="tel" htmlEscape="false" maxlength="20"  class=" form-control"/>
									</div>
									<div class="col-xs-12 col-sm-6 col-md-4">
										<label class="label-item single-overflow pull-left" title="是否允许派单：">是否允许派单：</label>
										<form:input path="allowDispatch" htmlEscape="false" maxlength="32"  class=" form-control"/>
									</div>
									<div class="col-xs-12 col-sm-6 col-md-4">
										<label class="label-item single-overflow pull-left" title="邮箱：">邮箱：</label>
										<form:input path="email" htmlEscape="false" maxlength="64"  class=" form-control"/>
									</div>
									<div class="col-xs-12 col-sm-6 col-md-4">
										<label class="label-item single-overflow pull-left" title="邮编：">邮编：</label>
										<form:input path="zipCode" htmlEscape="false" maxlength="32"  class=" form-control"/>
									</div>
									<div class="col-xs-12 col-sm-6 col-md-4">
										<label class="label-item single-overflow pull-left" title="审核状态：">审核状态：</label>
										<form:select path="auditState"  class="form-control m-b">
											<form:option value="" label=""/>
											<form:options items="${fns:getDictList('audit_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
										</form:select>
									</div>
									<div class="col-xs-12 col-sm-6 col-md-4">
										<div class="form-group">
											<label class="label-item single-overflow pull-left" title="是否关闭 ：">&nbsp;是否关闭 ：</label>
											<div class="col-xs-12">
												<form:radiobuttons class="i-checks" path="isClosed" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
											</div>
										</div>
									</div>
									<div class="col-xs-12 col-sm-6 col-md-4">
										<div class="form-group">
											<label class="label-item single-overflow pull-left" title="是否开店：">&nbsp;是否开店：</label>
											<div class="col-xs-12">
												<form:radiobuttons class="i-checks" path="isStore" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
											</div>
										</div>
									</div>
									<div class="col-xs-12 col-sm-6 col-md-4">
										<div class="form-group">
											<label class="label-item single-overflow pull-left" title="是否自营：">&nbsp;是否自营：</label>
											<div class="col-xs-12">
												<form:radiobuttons class="i-checks" path="isSelf" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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

					<!-- 表格 -->
					<table id="dealerTable"   data-toolbar="#toolbar"></table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>