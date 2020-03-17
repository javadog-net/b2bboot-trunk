<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>模型管理</title>
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
					jh.post("${ctx}/cms/cmsModel/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="cmsModel" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>模型名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>表名：</label></td>
					<td class="width-35">
						<form:input path="tableName" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">描述：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>是否可用：</label></td>
					<td class="width-35">
						<form:radiobuttons path="disabled" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">栏目模板：</label></td>
					<td class="width-35">
						<form:select path="categoryTemplate" value="${cmsModel.categoryTemplate}"  class="form-control ">
							<form:option value="">--请选择--</form:option>
							<form:options items="${indexList}" htmlEscape="false"></form:options>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">列表页模板：</label></td>
					<td class="width-35">
						<form:select path="listTemplate" value="${cmsModel.listTemplate}"  class="form-control ">
                            <form:option value="">--请选择--</form:option>
							<form:options items="${list}"  htmlEscape="false"></form:options>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">文章内容模板：</label></td>
					<td class="width-35">
						<form:select path="contentTemplate" value="${cmsModel.contentTemplate}"  class="form-control ">
                            <form:option value="">--请选择--</form:option>
                            <form:options items="${contentList}" htmlEscape="false"></form:options>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">手机栏目模板：</label></td>
					<td class="width-35">
						<form:select path="mcategoryTemplate" value="${cmsModel.mcategoryTemplate}"  class="form-control ">
							<form:option value="">--请选择--</form:option>
							<form:options items="${mobileIndexList}" htmlEscape="false"></form:options>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">手机列表模板：</label></td>
					<td class="width-35">
						<form:select path="mlistTemplate" value="${cmsModel.mlistTemplate}"  class="form-control ">
							<form:option value="">--请选择--</form:option>
							<form:options items="${mobileList}" htmlEscape="false"></form:options>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">手机内容模板：</label></td>
					<td class="width-35">
						<form:select path="mcontentTemplate" value="${cmsModel.mcontentTemplate}"  class="form-control ">
							<form:option value="">--请选择--</form:option>
							<form:options items="${mobileContentList}" htmlEscape="false"></form:options>
						</form:select>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>