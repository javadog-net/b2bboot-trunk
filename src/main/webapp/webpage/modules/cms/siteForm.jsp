<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>站点管理</title>
	<meta name="decorator" content="default"/>
	<!-- SUMMERNOTE -->
	<%@include file="/webpage/include/summernote.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					jh.loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			//富文本初始化
			$('#copyright').summernote({
				height: 300,                
                lang: 'zh-CN'
            });
		});
	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
<div class="col-md-12">
<div class="panel panel-primary">
<div class="panel-body">
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/site/">站点列表</a></li>
		<li class="active"><a href="${ctx}/cms/site/form?id=${site.id}">站点<shiro:hasPermission name="cms:site:edit">${not empty site.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:site:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="site" action="${ctx}/cms/site/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="form-group">
			<label class="col-sm-2 control-label">站点名称:</label>
			<div class="col-sm-10">
				<form:input path="name" htmlEscape="false" maxlength="200" class="form-control input-xlarge required"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">站点标题:</label>
			<div class="col-sm-10">
				<form:input path="title" htmlEscape="false" maxlength="200" class="form-control input-xlarge required"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">站点Logo:</label>
			<div class="col-sm-10">
				<form:hidden path="logo" htmlEscape="false" maxlength="255" class="form-control input-xlarge"/>
				<sys:webupload input="logo" type="thumb" multiple="false" maxWidth="100" maxHeight="145"/>
				<span class="help-inline">建议Logo大小：100 × 145（像素）</span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">描述:</label>
			<div class="col-sm-10">
				<form:textarea path="description" htmlEscape="false" rows="4" maxlength="200" class="form-control input-xxlarge"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">关键字:</label>
			<div class="col-sm-10">
				<form:input path="keywords" htmlEscape="false" maxlength="200" class="form-control" />
				<span class="help-inline">填写描述及关键字，有助于搜索引擎优化</span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">默认主题:</label>
			<div class="col-sm-10">
				<form:select path="theme" class="form-control input-medium">
					<form:options items="${fns:getDictList('cms_theme')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">版权信息:</label>
			<div class="col-sm-10">
				<input type="hidden" name="copyright"/>
				<div id="copyright">
                   ${fns:unescapeHtml(copyright)}
                </div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">自定义首页视图:</label>
			<div class="col-sm-10">
				<form:input path="customIndexView" htmlEscape="false" maxlength="200" class="form-control"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cms:site:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</div>
</div>
</div>
</div>
</div>
</body>
</html>