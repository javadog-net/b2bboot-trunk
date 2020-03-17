<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>邮件管理</title>
	<meta name="decorator" content="ani"/>
	<!-- SUMMERNOTE -->
	<%@include file="/webpage/include/summernote.jsp" %>
	<script type="text/javascript">

		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					jh.loading();
					$("input[name='content']").val($('#content').summernote('code'));//取富文本的值
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
			$('#content').summernote({
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
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/tools/cmsEmailInfo"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="cmsEmailInfo" action="${ctx}/tools/cmsEmailInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>邮件主题：</label>
					<div class="col-sm-10">
						<form:input path="title" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>邮件类型（系统、营销）：</label>
					<div class="col-sm-10">
						<form:input path="emailtype" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>创建时间：</label>
					<div class="col-sm-10">
						<form:input path="createtime" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">收件人类型 （全部、经销商、采购商、按照地域、未激活、已激活）：</label>
					<div class="col-sm-10">
						<form:input path="rectype" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">省份名称：</label>
					<div class="col-sm-10">
						<form:input path="provincename" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">城市名称：</label>
					<div class="col-sm-10">
						<form:input path="cityname" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">content：</label>
					<div class="col-sm-10">
                        <input type="hidden" name="content"/>
						<div id="content">
                          ${fns:unescapeHtml(cmsEmailInfo.content)}
                        </div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否发送邮件 0 未发送 1 发送：</label>
					<div class="col-sm-10">
						<form:input path="issendemail" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">发送时间：</label>
					<div class="col-sm-10">
						<form:input path="sendtime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">发件人：</label>
					<div class="col-sm-10">
						<form:input path="sender" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">1群发，0单发：</label>
					<div class="col-sm-10">
						<form:input path="sendtype" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">邮箱：</label>
					<div class="col-sm-10">
						<form:input path="email" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
		<c:if test="${fns:hasPermission('tools:cmsEmailInfo:edit') || isAdd}">
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