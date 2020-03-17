<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>app版本管理</title>
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
			
	        $('#updateTime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
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
				<a class="panelButton" href="${ctx}/wechat/appVersion"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="appVersion" action="${ctx}/wechat/appVersion/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">版本号：</label>
					<div class="col-sm-10">
						<form:input path="version" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>下载地址：</label>
					<div class="col-sm-10">
						<form:hidden id="address" path="address" htmlEscape="false" maxlength="255" class="form-control"/>
						<sys:webupload input="address" type="file" multiple="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">系统：</label>
					<div class="col-sm-10">
						<form:select path="type" value="${AppVersion.type}" readonly="true" htmlEscape="false" class="form-control dn required remo">
                        	<form:option value="" label="请选择系统类型" selected="true"/>
                        	<form:options items="${fns:getDictList('app_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>更新内容：</label>
					<div class="col-sm-10">
						<form:textarea path="content" htmlEscape="false" rows="4"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否更新：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="update" items="${fns:getDictList('isYes')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">更新时间：</label>
					<div class="col-sm-10">
						<p class="input-group">
							<div class='input-group form_datetime' id='updateTime'>
			                    <input type='text'  name="updateTime" class="form-control"  value="<fmt:formatDate value="${appVersion.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>						            
			            </p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否强制更新：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="mustUpdate" items="${fns:getDictList('isYes')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</div>
				</div>
		<c:if test="${fns:hasPermission('wechat:appVersion:edit') || isAdd}">
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