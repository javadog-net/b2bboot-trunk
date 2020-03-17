<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>cms配置管理</title>
	<meta name="decorator" content="ani"/>
	<style>
		.col-sm-10 span label{
			margin: 0 10px;
		}
		.col-sm-10 .icheckbox_square-blue{
			margin-right: 10px;
		}
	</style>
	<script type="text/javascript">

		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					jh.loading();
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
				<li class="active"><a href="${ctx}/cms/cmsConfig/form">配置</a></li>
				<li><a href="${ctx}/cms/cmsConfigExt/form">水印</a></li>
			</ul>
		<form:form id="inputForm" modelAttribute="cmsConfig" action="${ctx}/cms/cmsConfig/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>系统名称：</label>
					<div class="col-sm-10">
						<form:input path="name" htmlEscape="false" value="b2b" disabled="true"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>模板风格：</label>
					<div class="col-sm-10">
						<form:select path="templetStyle" class="form-control required" >
							<form:options items="${templateStyleMap}"></form:options>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>logo：</label>
					<div class="col-sm-10">
						<form:hidden path="logo" htmlEscape="false" maxlength="255"
									 class="form-control input-xlarge"/>
						<sys:webupload input="logo" type="file" multiple="false" maxWidth="480"
									   maxHeight="480"/>
						<span><font color="red" size="1">图片建议尺寸</font></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>首页生成规则：</label>
					<div class="col-sm-10">
						<form:hidden path="oldIndexPath" value="${cmsConfig.indexPath}"></form:hidden>
						<form:input path="indexPath" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>栏目页生成规则：</label>
					<div class="col-sm-10">
						<form:input path="cmsCategoryPath" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>内容页生成规则：</label>
					<div class="col-sm-10">
						<form:input path="infoPath" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">商品分类页生成规则：</label>
					<div class="col-sm-10">
						<form:input path="categoryPath" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">商品页生产规则：</label>
					<div class="col-sm-10">
						<form:input path="productPath" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">移动页生成文件夹名：</label>
					<div class="col-sm-10">
						<form:input path="mobilePath" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否允许评论：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="allowComment" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否匿名评论：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="isAnonymous" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks"/>
					</div>
				</div>
		<c:if test="${fns:hasPermission('cms:cmsConfig:edit') || isAdd}">
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
		</c:if>
		</form:form>
	</div>
	</div>
</div>
</div>
</body>
</html>