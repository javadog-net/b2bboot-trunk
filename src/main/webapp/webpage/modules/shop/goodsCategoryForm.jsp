<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品分类管理</title>
	<meta name="decorator" content="ani"/>
	
	
	
	<script type="text/javascript">
		var validateForm;
		var $goodsCategoryTreeTable; // 父页面table表格id
		var $topIndex;//openDialog的 dialog index
		function doSubmit(treeTable, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $goodsCategoryTreeTable = treeTable;
			  $topIndex = index;
			  jh.loading();
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {	
			validateForm = $("#inputForm").validate({
			
				rules: {
					name: {remote: "${ctx}/shop/goodsCategory/checkGoodsCategoryName?id="+encodeURIComponent('${goodsCategory.id}')},
					pageMark: {remote: "${ctx}/shop/goodsCategory/checkGoodsCategoryMarkName?id="+encodeURIComponent('${goodsCategory.id}')}
				},
				messages: {
					name:"分类名称已存在或被包含了该名称",
					pageMark:"栏目标识已存在"
				},
				submitHandler: function(form){
					jh.post("${ctx}/shop/goodsCategory/save",$('#inputForm').serialize(),function(data){
						if(data.success){
							$goodsCategoryTreeTable.jstree("refresh");
							jh.success(data.msg);
						}else {
							jh.error(data.msg);
						}
						jh.close($topIndex);//关闭dialog
					});
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
		<form:form id="inputForm" modelAttribute="goodsCategory" method="post" action="" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">父级分类：</label></td>
					<td class="width-35">
						<sys:treeselect id="parent" name="parent.id" value="${goodsCategory.parent.id}" labelName="parent.name" labelValue="${goodsCategory.parent.name}"
						title="父级分类" url="/shop/goodsCategory/treeData" extId="${goodsCategory.id}" cssClass="form-control " allowClear="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right">分类名称：</label></td>
					<td class="width-35">
						<form:input path="name"  htmlEscape="false"   class="form-control required class_name" />
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">图标路径：</label></td>
					<td class="width-35">
						<form:hidden id="iconUrl" path="iconUrl" htmlEscape="false" maxlength="100" class="form-control"/>
						<sys:webupload input="iconUrl" type="image" multiple="false"/>
					</td>
					<td class="width-15 active"><label class="pull-right">属性品类：</label></td>
					<td class="width-35">
						<form:select path="classId" value="${goodsCategory.classId}" htmlEscape="false"
									 class="form-control required">
							<form:option value="" label="请选择属性品类" selected="true"/>
							<form:options items="${goodsClassList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				
				<tr>
					<td class="width-15 active"><label class="pull-right">栏目模版：</label>
					</td>
					<td class="width-35">
						<form:select path="classTemplate" value="${goodsCategory.classTemplate}" htmlEscape="false"
									 class="form-control ">
							<form:option value="" label="请选择栏目模版" selected="true"/>
							<form:options items="${list}"  htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">商品模版：</label></td>
					<td class="width-35">
						<form:select path="goodsTemplate" value="${goodsCategory.goodsTemplate}" htmlEscape="false"
									 class="form-control">
							<form:option value="" label="请选择商品模版" selected="true"/>
							<form:options items="${productList}" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">页面标识：</label>
					<font color="red">*</font>
					</td>
					<td class="width-35">
						<form:input path="pageMark" htmlEscape="false"  class="form-control required pageMark"/>
					</td>
					<td class="width-15 active"><label class="pull-right">是否展示：</label></td>
					<td class="width-35">
						<div class="col-sm-10">
							<form:radiobuttons path="isShow" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
						</div>
					</td>
				</tr>
				
				<tr>
					<td class="width-15 active"><label class="pull-right">排序：</label></td>
					<td class="width-35">
						<form:input path="sort" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
		</form:form>
</body>
</html>