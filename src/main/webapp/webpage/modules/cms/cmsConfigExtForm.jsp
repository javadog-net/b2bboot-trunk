<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>内容管理-水印功能管理</title>
	<meta name="decorator" content="ani"/>
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
				<li><a href="${ctx}/cms/cmsConfig/form">配置</a></li>
				<li class="active"><a href="${ctx}/cms/cmsConfigExt/form">水印</a></li>
			</ul>
		<form:form id="inputForm" modelAttribute="cmsConfigExt" action="${ctx}/cms/cmsConfigExt/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">水印类型：</label>
					<div class="col-sm-10">
						<form:checkboxes path="types" items="${fns:getDictList('watermark_type')}" itemLabel="label" itemValue="value" htmlEscape="false"
										 class="i-checks"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">图片最小宽度：</label>
					<div class="col-sm-10">
						<form:input path="imgMinWidth" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">图片最小高度：</label>
					<div class="col-sm-10">
						<form:input path="imgMinHeight" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">图片路径：</label>
					<div class="col-sm-10">
						<form:input path="imgPath" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">图片水印位置：</label>
					<div class="col-sm-10">
						<form:input path="imgLocation" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">水平偏移：</label>
					<div class="col-sm-10">
						<form:input path="imgLeft" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">垂直偏移：</label>
					<div class="col-sm-10">
						<form:input path="imgTop" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">透明度：</label>
					<div class="col-sm-10">
						<form:input path="imgAlpha" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">添加文本水印的最小图片宽度：</label>
					<div class="col-sm-10">
						<form:input path="txtMinWidth" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">添加文本水印的最小图片高度：</label>
					<div class="col-sm-10">
						<form:input path="txtMinHeight" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">文本水印内容：</label>
					<div class="col-sm-10">
						<form:input path="txt" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">文本水印位置：</label>
					<div class="col-sm-10">
						<form:input path="txtLocation" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">文本水印水平偏移：</label>
					<div class="col-sm-10">
						<form:input path="txtLeft" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">文本水印垂直偏移：</label>
					<div class="col-sm-10">
						<form:input path="txtTop" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">文本水印透明度：</label>
					<div class="col-sm-10">
						<form:input path="txtAlpha" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">字体大小：</label>
					<div class="col-sm-10">
						<form:input path="txtFontSize" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">字体颜色：</label>
					<div class="col-sm-10">
						<form:input path="txtFontColor" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">字体名称：</label>
					<div class="col-sm-10">
						<form:input path="txtFontName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
		<c:if test="${fns:hasPermission('cms:cmsConfigExt:edit') || isAdd}">
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
</div>
</body>
</html>