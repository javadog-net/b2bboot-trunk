<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>old管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="oldShopMsgList.js" %>
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
			<form:form id="searchForm" modelAttribute="oldShopMsg" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="id：">id：</label>
				<form:input path="id" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="provinceid：">provinceid：</label>
				<form:input path="provinceid" htmlEscape="false" maxlength="100"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="provincename：">provincename：</label>
				<form:input path="provincename" htmlEscape="false" maxlength="100"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="cityid：">cityid：</label>
				<form:input path="cityid" htmlEscape="false" maxlength="100"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="cityname：">cityname：</label>
				<form:input path="cityname" htmlEscape="false" maxlength="100"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="工贸编码：">工贸编码：</label>
				<form:input path="orgid" htmlEscape="false" maxlength="40"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="orgname：">orgname：</label>
				<form:input path="orgname" htmlEscape="false" maxlength="60"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="产品组：">产品组：</label>
				<form:input path="product" htmlEscape="false" maxlength="500"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="count：">count：</label>
				<form:input path="count" htmlEscape="false" maxlength="500"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="金额：">金额：</label>
				<form:input path="money" htmlEscape="false"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="留言时间：">留言时间：</label>
				<form:input path="createtime" htmlEscape="false"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="渠道来源：">渠道来源：</label>
				<form:input path="channel" htmlEscape="false" maxlength="50"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="公司：">公司：</label>
				<form:input path="companyname" htmlEscape="false" maxlength="400"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="公司规模：">公司规模：</label>
				<form:input path="companysize" htmlEscape="false" maxlength="40"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="keypersion：">keypersion：</label>
				<form:input path="keypersion" htmlEscape="false" maxlength="40"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="keymobile：">keymobile：</label>
				<form:input path="keymobile" htmlEscape="false" maxlength="50"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="reservemobile：">reservemobile：</label>
				<form:input path="reservemobile" htmlEscape="false" maxlength="50"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="adress：">adress：</label>
				<form:input path="adress" htmlEscape="false" maxlength="100"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="行业：">行业：</label>
				<form:input path="indus" htmlEscape="false" maxlength="100"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="提交留言后返回的编号：">提交留言后返回的编号：</label>
				<form:input path="infcode" htmlEscape="false" maxlength="60"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="抢单使用的字段（是否分抢单 Y：是，N：否）：">抢单使用的字段（是否分抢单 Y：是，N：否）：</label>
				<form:input path="flag" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="经销商编码(Flag=Y时,经销商编码不能为空)：">经销商编码(Flag=Y时,经销商编码不能为空)：</label>
				<form:input path="custcode" htmlEscape="false" maxlength="40"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="历史单号：">历史单号：</label>
				<form:input path="oldcode" htmlEscape="false" maxlength="40"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="留言是否有效 0 有效 1无效：">留言是否有效 0 有效 1无效：</label>
				<form:input path="msgvalid" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="memberid：">memberid：</label>
				<form:input path="memberid" htmlEscape="false" maxlength="50"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="提交时间：">提交时间：</label>
				<form:input path="subtime" htmlEscape="false"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="报价信息：">报价信息：</label>
				<form:input path="quotainfo" htmlEscape="false" maxlength="200"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="留言是否已提交 defalut 0  0 未提交 1已提交：">留言是否已提交 defalut 0  0 未提交 1已提交：</label>
				<form:input path="msgflag" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="备注：">备注：</label>
				<form:input path="memo" htmlEscape="false" maxlength="1000"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="approtime：">approtime：</label>
				<form:input path="approtime" htmlEscape="false"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="isqd：">isqd：</label>
				<form:input path="isqd" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="qq：">qq：</label>
				<form:input path="qq" htmlEscape="false" maxlength="40"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="email：">email：</label>
				<form:input path="email" htmlEscape="false" maxlength="60"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="平台审核人：">平台审核人：</label>
				<form:input path="ptchecker" htmlEscape="false" maxlength="60"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="平台审核是否通过：">平台审核是否通过：</label>
				<form:input path="ptispass" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="平台审核时间：">平台审核时间：</label>
				<form:input path="ptchecktime" htmlEscape="false"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="平台审核备注：">平台审核备注：</label>
				<form:input path="ptdescp" htmlEscape="false" maxlength="200"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="是否派单：">是否派单：</label>
				<form:input path="isdispatch" htmlEscape="false" maxlength="255"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="APP 审核人：">APP 审核人：</label>
				<form:input path="appchecker" htmlEscape="false" maxlength="60"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="app审核原因：">app审核原因：</label>
				<form:input path="appdescp" htmlEscape="false" maxlength="200"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="平台审核是否通过：">平台审核是否通过：</label>
				<form:input path="appispass" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="直单、客单：">直单、客单：</label>
				<form:input path="ordertype" htmlEscape="false" maxlength="20"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="抢单时间：">抢单时间：</label>
				<form:input path="grabtime" htmlEscape="false"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="直单凭证：">直单凭证：</label>
				<form:input path="mimgurl" htmlEscape="false" maxlength="100"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="直单凭证上传时间：">直单凭证上传时间：</label>
				<form:input path="uploadtime" htmlEscape="false"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="直单审核是否通过：">直单审核是否通过：</label>
				<form:input path="stracheck" htmlEscape="false" maxlength="255"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="直单审核人：">直单审核人：</label>
				<form:input path="strachecker" htmlEscape="false" maxlength="60"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="直单审核时间：">直单审核时间：</label>
				<form:input path="strachecktime" htmlEscape="false"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="留言是否关闭 0 否 1是：">留言是否关闭 0 否 1是：</label>
				<form:input path="msgisclose" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="是否有承接经销商 0 没有  1  有：">是否有承接经销商 0 没有  1  有：</label>
				<form:input path="haveundertake" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="商空机型 (0小,1大,2全部)：">商空机型 (0小,1大,2全部)：</label>
				<form:input path="skmodel" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="商空产品详细信息：">商空产品详细信息：</label>
				<form:input path="skproductd" htmlEscape="false" maxlength="100"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="提交至ACG返回的项目信息：">提交至ACG返回的项目信息：</label>
				<form:input path="acgprojectid" htmlEscape="false" maxlength="20"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="项目编码：">项目编码：</label>
				<form:input path="projectcode" htmlEscape="false" maxlength="40"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="部门：">部门：</label>
				<form:input path="depart" htmlEscape="false" maxlength="100"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="小机的项目ID：">小机的项目ID：</label>
				<form:input path="smallprojectid" htmlEscape="false" maxlength="11"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="需求分类（询价、代理、邀标、资料获取、品牌合作、文化交流）：">需求分类（询价、代理、邀标、资料获取、品牌合作、文化交流）：</label>
				<form:input path="category" htmlEscape="false" maxlength="50"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="是否反馈，1是，0否：">是否反馈，1是，0否：</label>
				<form:input path="isback" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="资料地址：">资料地址：</label>
				<form:input path="dataurl" htmlEscape="false" maxlength="1000"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="未读消息条数：">未读消息条数：</label>
				<form:input path="unreadmsg" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="合作交流：">合作交流：</label>
				<form:input path="cooperationandexchange" htmlEscape="false" maxlength="100"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="节点状态：">节点状态：</label>
				<form:input path="nodetag" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="未读0  已读1：">未读0  已读1：</label>
				<form:input path="isread" htmlEscape="false" maxlength="2"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="projectname：">projectname：</label>
				<form:input path="projectname" htmlEscape="false" maxlength="255"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="指派人编号：">指派人编号：</label>
				<form:input path="managerno" htmlEscape="false" maxlength="255"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="指派人名称：">指派人名称：</label>
				<form:input path="managername" htmlEscape="false" maxlength="255"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="商空会员类型：">商空会员类型：</label>
				<form:input path="conditionermembertype" htmlEscape="false" maxlength="255"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="总监备注：">总监备注：</label>
				<form:input path="remarks" htmlEscape="false" maxlength="1000"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="总监备注填写时间：">总监备注填写时间：</label>
				<form:input path="remarkstime" htmlEscape="false"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="填写备注的总监编号：">填写备注的总监编号：</label>
				<form:input path="remarksperson" htmlEscape="false" maxlength="255"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="hps系统对应省份id：">hps系统对应省份id：</label>
				<form:input path="hpsprovinceid" htmlEscape="false" maxlength="255"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="hps系统对应城市id：">hps系统对应城市id：</label>
				<form:input path="hpscityid" htmlEscape="false" maxlength="255"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="hps系统对应地区id：">hps系统对应地区id：</label>
				<form:input path="hpsdistrictid" htmlEscape="false" maxlength="255"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="项目负责人编号：">项目负责人编号：</label>
				<form:input path="firstcompanyorgcode" htmlEscape="false" maxlength="255"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="项目负责人名称：">项目负责人名称：</label>
				<form:input path="firstcompanyorgname" htmlEscape="false" maxlength="255"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="详细地址	utf8：">详细地址	utf8：</label>
				<form:input path="areadetails" htmlEscape="false" maxlength="255"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="是否已成功发送短信，（1-已成功发送）：">是否已成功发送短信，（1-已成功发送）：</label>
				<form:input path="sendmsg" htmlEscape="false" maxlength="2"  class=" form-control"/>
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
			<shiro:hasPermission name="old:oldShopMsg:add">
				<a id="add" class="btn btn-primary" href="${ctx}/old/oldShopMsg/form" title="old"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="old:oldShopMsg:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="old:oldShopMsg:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="old:oldShopMsg:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/old/oldShopMsg/import" method="post" enctype="multipart/form-data"
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
	<table id="oldShopMsgTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="old:oldShopMsg:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="old:oldShopMsg:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>