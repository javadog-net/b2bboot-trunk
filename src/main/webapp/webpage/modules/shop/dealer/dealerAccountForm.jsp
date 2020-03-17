<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供应商账号管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		var validateForm;
		var $table; // 父页面table表格id
		var $topIndex;//弹出窗口的 index
		function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
		      //新增时候密码不能为空
              if($('#id').val()==''){
                  if($.trim($('#newPassword').val())==''){
                      jh.error("密码不能为空");
                      return false;
                  }
              }
		      //判断密码校验
              if($('#newPassword').val() != $('#repass').val()){
                jh.error("密码与校验密码不符");
                return false;
              }
              //
			  $table = table;
			  $topIndex = index;
			  jh.loading();
			  $("#inputForm").submit();
			  return true;
		  }

		  return false;
		}

		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					jh.post("${ctx}/shop/dealer/dealer/saveAccount",$('#inputForm').serialize(),function(data){
						if(data.success){
	                    	$table.bootstrapTable('refresh');
	                    	jh.success(data.msg);
	                    	jh.close($topIndex);//关闭dialog

	                    }else{
            	  			jh.error(data.msg);
	                    }
					})
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
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="dealerAccount" class="form-horizontal">
		<form:hidden path="id"/>
            <form:hidden path="dealerId"/>
            <form:hidden path="isAdmin"/>
            <form:hidden path="roleIdList"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>登录名：</label></td>
					<td class="width-35">
						<form:input path="loginName" htmlEscape="false"    class="form-control required" readonly="${empty dealerAccount.id?false:true}"/>
					</td>
                    <td class="width-15 active"><label class="pull-right">姓名：</label></td>
                    <td class="width-35">
                        <form:input path="realName" htmlEscape="false"    class="form-control "/>
                    </td>
				</tr>
                <form:hidden path="passwd"/>
				<tr>
					<td class="width-15 active"><label class="pull-right"><c:if test="${empty dealerAccount.id}"><font color="red">*</font></c:if>登录密码：</label></td>
					<td class="width-35">
						<form:input path="newPassword" htmlEscape="false"    class="form-control ${empty dealerAccount.id?'required':''}"/>
                        <c:if test="${not empty dealerAccount.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
					</td>
					<td class="width-15 active"><label class="pull-right"><c:if test="${empty dealerAccount.id}"><font color="red">*</font></c:if>确认密码：</label></td>
					<td class="width-35">
                        <input id="repass" class="form-control ${empty dealerAccount.id?'required':''}"/>
					</td>
				</tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right">手机：</label></td>
                    <td class="width-35">
                        <form:input path="mobile" htmlEscape="false"    class="form-control isPhone"/>
                    </td>
                    <td class="width-15 active"><label class="pull-right">邮箱：</label></td>
                    <td class="width-35">
                        <form:input path="email" htmlEscape="false"    class="form-control email"/>
                    </td>
                </tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">所在部门：</label></td>
					<td class="width-35">
						<form:input path="departName" htmlEscape="false"    class="form-control "/>
					</td>
                    <td class="width-15 active"><label class="pull-right">是否停用：</label></td>
                    <td class="width-35">
                        <form:radiobuttons path="isClosed" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
                    </td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
                    <td class="width-15 active"></td>
                    <td class="width-35">
                    </td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>