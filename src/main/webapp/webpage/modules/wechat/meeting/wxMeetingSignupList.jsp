<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>会议报名表管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="wxMeetingSignupList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">会议报名表列表</h3>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="wxMeetingSignup" class="form form-horizontal well clearfix">
			<%--  <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="0-待审核; 1-审核通过; 2-审核拒绝 ：">0-待审核; 1-审核通过; 2-审核拒绝 ：</label>
				<form:select path="signStatus"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div> --%>
			<!-- 
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<div class="form-group">
					<label class="label-item single-overflow pull-left" title="手机号：">&nbsp;手机号：</label>
					<div class="col-xs-12">
						<div class='input-group date' id='phone' >
			                   <input type='text'  name="phone" class="form-control"  />
			                   <span class="input-group-addon">
			                       <span class="glyphicon glyphicon-calendar"></span>
			                   </span>
			            </div>	
					</div>
				</div>
			</div> -->
			
			 <%-- <div class="col-xs-12 col-sm-6 col-md-4">
				<div class="form-group">
					<label class="label-item single-overflow pull-left" title="公司名：">&nbsp;公司名：</label>
					<div class="col-xs-12">
						<div class='input-group date' id='companyName' >
			                   <input type='text'  name="companyName" class="form-control"  />
			                   <span class="input-group-addon">
			                       <span class="glyphicon glyphicon-calendar"></span>
			                   </span>
			            </div>	
					</div>
				</div>
			</div> --%> 
			
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="姓名：">姓名：</label>
				<form:input path="realName" htmlEscape="false" maxlength="100"  class=" form-control"/>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="手机号：">手机号：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="100"  class=" form-control"/>
			</div> 
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="公司名：">公司名：</label>
				<form:input path="companyName" htmlEscape="false" maxlength="100"  class=" form-control"/>
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
			<shiro:hasPermission name="wechat:wxMeetingSignup:add">
				<a id="add" class="btn btn-primary" onclick="add()"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="wechat:wxMeetingSignup:edit">
			    <button id="remove" class="btn btn-success" disabled onclick="audit()">
	            	<i class="glyphicon glyphicon-remove"></i> 审核
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="wechat:wxMeetingSignup:edit">
			    <button id="xingcheng" class="btn btn-success" disabled onclick="auditandschedule()">
	            	<i class="glyphicon glyphicon-remove"></i> 审核并收集行程
	        	</button>
			</shiro:hasPermission>
			
		<shiro:hasPermission name="wechat:wxMeetingSignup:edit">
			<button id="jujue" class="btn btn-success" disabled onclick="reverseAudit()">
				<i class="glyphicon glyphicon-remove"></i> 拒绝
			</button>
		</shiro:hasPermission>
			<shiro:hasPermission name="wechat:wxMeetingSignup:edit">
				<button id="qiandao" class="btn btn-success" disabled onclick="signupAll()">
	            	<i class="glyphicon glyphicon-edit"></i> 签到
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="wechat:wxMeetingSignup:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
				<button id="edit" class="btn btn-danger" onclick="send()">
					<i class="glyphicon glyphicon-edit"></i> 会前通知短信
				</button>
			<shiro:hasPermission name="wechat:wxMeetingSignup:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/wechat/wxMeetingSignup/import" method="post" enctype="multipart/form-data"
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
	<table id="wxMeetingSignupTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="wechat:wxMeetingSignup:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="wechat:wxMeetingSignup:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>