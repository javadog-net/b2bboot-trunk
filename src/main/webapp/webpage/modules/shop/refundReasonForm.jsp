<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>退货原因管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		var validateForm;
		var $table; // 父页面table表格id
		var $topIndex;//弹出窗口的 index
		function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
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
					jh.post("${ctx}/shop/refundReason/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="refundReason" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">退货方式：</label></td>
					<td class="width-35">
						<form:radiobuttons path="refundType" items="${fns:getDictList('refund_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>原因内容：</label></td>
					<td class="width-35">
						<form:input path="reasonInfo" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">排序：</label></td>
					<td class="width-35">
						<form:input path="sort" htmlEscape="false"    class="form-control "/>
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