<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>链接管理管理</title>
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
					jh.post("${ctx}/cms/link/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="link" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<%--<td class="width-15 active"><label class="pull-right">链接分类：</label></td>
					<td class="width-35">
						<form:input path="linkGroup" htmlEscape="false"    class="form-control "/>
					</td>--%>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>链接名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>链接类型：</label></td>
					<td class="width-35">
						<form:select path="linkType" class="form-control required" onchange="changeLinkType(this.value)">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('link_model_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>

					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>链接地址：</label></td>
					<td class="width-35">
						<form:input path="url" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">排序：</label></td>
					<td class="width-35">
						<form:input path="sort" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">Logo：</label></td>
					<td class="width-35">
						<form:hidden path="logo" htmlEscape="false" maxlength="255" class="form-control input-xlarge"/>
						<sys:webupload input="logo" type="file" multiple="false" maxWidth="480" maxHeight="480"/>
					</td>
					<td class="width-15 active"><label class="pull-right">描述：</label></td>
					<td class="width-35">
						<form:textarea path="descp" htmlEscape="false" rows="2"    class="form-control "/>
					</td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>