<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>报名信息表管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					jh.loading();
					form.submit();
                     var index = parent.layer.getFrameIndex(window.name);
                     layer.close(String((+index+1));
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
			
	        $('#signTime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
	        $('#examineTime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
	        $('#attendTime').datetimepicker({
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
				<a class="panelButton" href="${ctx}/wechat/wxMeetingSignup"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="wxMeetingSignup" action="${ctx}/wechat/wxMeetingSignup/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<%-- 
				<div class="form-group">
					<label class="col-sm-2 control-label">报名时间：</label>
					<div class="col-sm-10">
						<p class="input-group">
							<div class='input-group form_datetime' id='signTime'>
			                    <input type='text'  name="signTime" class="form-control"  value="<fmt:formatDate value="${wxMeetingSignup.signTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>						            
			            </p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">审核人：</label>
					<div class="col-sm-10">
						<form:input path="examinePerson" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">审核时间：</label>
					<div class="col-sm-10">
						<p class="input-group">
							<div class='input-group form_datetime' id='examineTime'>
			                    <input type='text'  name="examineTime" class="form-control"  value="<fmt:formatDate value="${wxMeetingSignup.examineTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>						            
			            </p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">参会扫码时间：</label>
					<div class="col-sm-10">
						<p class="input-group">
							<div class='input-group form_datetime' id='attendTime'>
			                    <input type='text'  name="attendTime" class="form-control"  value="<fmt:formatDate value="${wxMeetingSignup.attendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>						            
			            </p>
					</div>
				</div> --%>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>姓名：</label>
					<div class="col-sm-10">
						<form:input path="realName" htmlEscape="false" readonly="true"   class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>手机号：</label>
					<div class="col-sm-10">
						<form:input path="mobile" htmlEscape="false" readonly="true"   class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>公司名称：</label>
					<div class="col-sm-10">
						<form:input path="companyName" htmlEscape="false" readonly="true"   class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>部门：</label>
					<div class="col-sm-10">
						<form:input path="departName" htmlEscape="false" readonly="true"   class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>地址：</label>
					<div class="col-sm-10">
						<form:input path="address" htmlEscape="false" readonly="true"   class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>邮箱：</label>
					<div class="col-sm-10">
						<form:input path="email" htmlEscape="false" readonly="true"   class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">(至多十个字符)</font>车次：</label>
					<div class="col-sm-10">
						<form:input path="carNum" htmlEscape="false" maxlength="10"   class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">(至多十个字符)</font>座号：</label>
					<div class="col-sm-10">
						<form:input path="seatNum" htmlEscape="false"  maxlength="10"  class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">(至多十个字符)</font>桌号：</label>
					<div class="col-sm-10">
						<form:input path="tableNum" htmlEscape="false"  maxlength="10"  class="form-control "/>
					</div>
				</div>
		<c:if test="${fns:hasPermission('wechat:wxMeetingSignup:edit') || isAdd}">
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