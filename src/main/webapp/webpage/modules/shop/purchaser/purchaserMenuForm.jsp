<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="ani"/>
    <link href="${ctxStatic}/plugin/layui/dist/css/layui-icon.css" rel="stylesheet" />
	<script type="text/javascript">
		var validateForm;
		var $purchaserMenuTreeTable; // 父页面table表格id
		var $topIndex;//openDialog的 dialog index
		function doSubmit(treeTable, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $purchaserMenuTreeTable = treeTable;
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
						jh.post("${ctx}/shop/purchaser/purchaserMenu/save",$('#inputForm').serialize(),function(data){
		                    if(data.success){
		                    	var current_id = data.body.purchaserMenu.id;
		                    	var target = $purchaserMenuTreeTable.get(current_id);
		                    	var old_parent_id = target.attr("pid") == undefined?'1':target.attr("pid");
		                    	var current_parent_id = data.body.purchaserMenu.parentId;
		                    	var current_parent_ids = data.body.purchaserMenu.parentIds;
		                    	
		                    	if(old_parent_id == current_parent_id){
		                    		if(current_parent_id == '1'){
		                    			$purchaserMenuTreeTable.refreshPoint(-1);
		                    		}else{
		                    			$purchaserMenuTreeTable.refreshPoint(current_parent_id);
		                    		}
		                    	}else{
		                    		$purchaserMenuTreeTable.del(current_id);//刷新删除旧节点
		                    		$purchaserMenuTreeTable.initParents(current_parent_ids, "1");
		                    	}
		                    	
		                    	jh.success(data.msg);
		                    }else {
	            	  			jh.error(data.msg);
		                    }
		                    
		                        jh.close($topIndex);//关闭dialog
		            });;
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
		<form:form id="inputForm" modelAttribute="purchaserMenu" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>上级菜单：</label></td>
					<td class="width-35">
						<sys:treeselect id="parent" name="parent.id" value="${purchaserMenu.parent.id}" labelName="parent.name" labelValue="${purchaserMenu.parent.name}"
						title="上级菜单" url="/shop/purchaser/purchaserMenu/treeData" extId="${purchaserMenu.id}" cssClass="form-control required" allowClear="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">链接：</label></td>
					<td class="width-35">
						<form:input path="href" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">目标：</label></td>
					<td class="width-35">
						<form:input path="target" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">图标：</label></td>
					<td class="width-35">
                        <sys:layericonselect id="icon" name="icon" value="${purchaserMenu.icon}"/>
					</td>
                    <td class="width-15 active"><label class="pull-right">菜单类型：</label></td>
                    <td class="width-35">
                        <form:radiobuttons path="menuType" items="${fns:getDictList('menu_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
                        <span class="help-inline"></span>
                    </td>
                </tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right"><font color="red">*</font>显示：</label></td>
                    <td class="width-35">
                        <form:radiobuttons path="isShow" items="${fns:getDictList('show_hide')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
                        <span class="help-inline">该菜单或操作是否显示到系统菜单中</span>
                    </td>
                    <td class="width-15 active"><label class="pull-right">权限标识：</label></td>
                    <td class="width-35">
                        <form:input path="permission" htmlEscape="false"    class="form-control "/>
                        <span class="help-inline">控制器中定义的权限标识，如：@Requires Permissions("权限标识")</span>
                    </td>

                </tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right">备注信息：</label></td>
                    <td class="width-35">
                        <form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
                    </td>
                    <td  class="active"></td>
                    <td></td>
                </tr>
		 	</tbody>
		</table>
		</form:form>
</body>
</html>