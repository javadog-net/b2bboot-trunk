<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>参与报名抽奖表管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			//change();
 
		/* 	$("#option").each(function(){ //遍历全部option
        var txt = $(this).text(); //获取option的内容
        
		debugger;
    });
		 */	
		var s=$("#option");
		
	     document.getElementById("selectAge")[2].selected=true;
	
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
		<%-- <div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/wechat/cmsSignupDraw"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div> --%>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="cmsSignupDraw" action="${ctx}/wechat/cmsSignupDraw/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">参与会员姓名：</label>
					<div class="col-sm-10">
						<form:input path="userName" htmlEscape="false" readonly="true"  class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">参与会员手机号：</label>
					<div class="col-sm-10">
						<form:input path="userPhone" htmlEscape="false" readonly="true"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">参与会员公司名称：</label>
					<div class="col-sm-10">
						<form:input path="companyName" htmlEscape="false" readonly="true"   class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注：</label>
					<div class="col-sm-10">
						<form:input path="remark" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div>
				     <input type="hidden" id="prizetab" value="${cmsSignupDraw.prizeIdTab }" />
				</div>
			 <div class="form-group">
             <label class="col-sm-4 control-label">设置奖项：</label>
				<div class="col-sm-8">
                     <select name="cmsPrize" id="selectAge">   
						 <c:forEach var="prize" items="${prizelist}">
						 		<c:if test="${prize.id==cmsSignupDraw.prizeIdTab}">
						 		<option id="option" selected="true" value="${prize.id}">${prize.prizeType }</option>
						 		</c:if>
						 		<c:if test="${prize.id!=cmsSignupDraw.prizeIdTab}">
						 		<option id="option" value="${prize.id}">${prize.prizeType }</option>
						 		</c:if>
						 </c:forEach>
	      		     </select>
				</div>
				
      		</div>
				
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label">参与时间：</label>
					<div class="col-sm-10">
						<form:input path="addTime" htmlEscape="false"    class="form-control "/>
					</div>
				</div> --%>
		<c:if test="${fns:hasPermission('wechat:cmsSignupDraw:edit') || isAdd}">
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