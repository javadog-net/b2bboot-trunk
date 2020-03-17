<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>位置管理管理</title>
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
					jh.post("${ctx}/cms/position/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="position" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">模型：</label></td>
					<td class="width-35">
						<form:select path="modelId" value="${position.modelId}"  class="form-control ">
							<form:options items="${list}"  itemLabel="name" itemValue="id" htmlEscape="false"></form:options>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">栏目：</label></td>
					<td class="width-35">
						<sys:treeselect id="category" name="categoryId" value="${position.categoryId}" labelName="category.name" labelValue="${position.categoryName}"
										title="栏目" url="/cms/category/treeData" extId="" cssClass="form-control"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>推荐位名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">最大保存条数：</label></td>
					<td class="width-35">
						<form:input path="maxNum" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">图片：</label></td>
					<td class="width-35">
						<form:hidden id="thumb" path="thumb" htmlEscape="false" maxlength="255" class="form-control"/>
						<sys:webupload input="thumb" type="image" multiple="false"/>
					</td>
					<td class="width-15 active"><label class="pull-right">排序：</label></td>
					<td class="width-35">
						<form:input path="sort" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>