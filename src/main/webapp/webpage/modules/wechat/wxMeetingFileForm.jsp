<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>上传会议附件管理</title>
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
		<form:form id="inputForm" modelAttribute="wxMeetingFile" action="${ctx}/wechat/wxMeetingFile/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}" type="${error}"/>	
				<div class="form-group" hidden>
					<label class="col-sm-2 control-label" >会议id：</label>
					<div class="col-sm-10">
						<form:input path="meetingId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">文件内容：</label>
					<div class="col-sm-10">
						<form:hidden id="textUrl" path="textUrl" htmlEscape="false" maxlength="255" class="form-control"/>
						<sys:webupload input="textUrl" type="file" multiple="true"/>
					</div>
				</div>
				
		<c:if test="${fns:hasPermission('wechat:wxMeetingFile:edit') || isAdd}">
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                 	<form:hidden name="remark" id="fileName" path="remark" htmlEscape="false" maxlength="255" class="form-control"/>
		                     <button onclick='$("#fileName").val($(".filelist").find("li").eq(0).find(".title").text());'  class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
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