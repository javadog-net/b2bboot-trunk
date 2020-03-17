<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	
	
	<title>用户管理</title>
	<meta name="decorator" content="ani"/>
	<!-- <script src="https://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
    <link href="https://cdn.bootcss.com/multiple-select/1.2.0/multiple-select.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/multiple-select/1.2.0/multiple-select.min.js"></script>
	 -->
	 <style type="text/css">
   select, ul { height: 100px; overflow: auto; width: 100%; border: 1px solid #000; }
   ul { list-style-type: none; margin: 0; padding: 0; overflow-x: hidden; }
   li { margin: 0; padding: 0; }
   label { display: block; color: WindowText; background-color: Window; margin: 0; padding: 0; width: 100%; }
   label:hover { background-color: Highlight; color: HighlightText; }
  </style>
	<script type="text/javascript">

	function changeisSysadmin(e){
		if(e==1){
			$("#checkul").show();
			$("#gminfo").hide();
			changeIsAdmin();
		}else{
			$("#gminfo").show();
			$("#checkul").hide();
			changeIsAdmin();
		}
	}
function changeIsAdmin(){
    if($('#isnoadmin').is(':checked')&&!$('#isadmin').is(':checked')){
    	$("#loginFlag1").prop("selected",false);
		$("#loginFlag1").prop("disabled","disabled");
		$("#loginFlag2").prop("selected",true);
    }else{
    	$("#loginFlag2").prop("selected",false);
		$("#loginFlag1").removeAttr('disabled');
		$("#loginFlag1").prop("selected",true);
		
    }
    
    
}
	var validateForm;
	var $table; // 父页面table表格id
	var $topIndex;//弹出窗口的 index
	function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		if($("#isadmin").is(':checked')){
			obj = document.getElementsByName("checkli");
		    check_val = [];
		    for(k in obj){
		        if(obj[k].checked)
		            check_val.push(obj[k].id);
		    }
		    $("#checkbox").val(check_val);
		}
		/* if($("#isadmin").is(':checked')&&$("#isnoadmin").is(':checked')){
			
		} */
		if($("input:radio[name='gmId']").is(':checked')==false&&
				$("input:checkbox[name='checkli']").is(':checked')==false){
			jh.alert("请确认用户角色！"); 
			return false;
		}
		if($("input:checkbox[name='isSysadmin']").is(':checked')==false){
			jh.alert("请确认用户角色！"); 
			return false;
		}
		
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
		$("#no").focus();
		validateForm = $("#inputForm").validate({
			rules: {
					loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				},
			messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
			submitHandler : function(form) {
				jh.post("${ctx}/sys/user/save",$('#inputForm').serialize(),function(data){
	                    if(data.success){
	                    	$table.bootstrapTable('refresh');
	                    	jh.success(data.msg);
            	  			
	                    }else{	
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
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered">
		   <tbody>
		      <tr>
		         <td class="width-15 active">	<label class="pull-right"><font color="red">*</font>头像：</label></td>
		         <td class="width-35"><form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
						<sys:webupload input="nameImage" type="thumb" multiple="false" maxWidth="100" maxHeight="100"/>
				 </td>
		         <td  class="width-15 active">	<label class="pull-right"><font color="red">*</font>归属公司:</label></td>
		         <td class="width-35"><sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
						title="公司" url="/sys/office/treeData?type=1" allowClear="true" cssClass="form-control required"/></td>
		      </tr>
		      
		      <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>归属部门:</label></td>
		         <td><sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
					 allowClear="true" title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" notAllowSelectParent="true"/></td>
		         <td class="active"><label class="pull-right"><font color="red">*</font>工号:</label></td>
		         <td><form:input path="no" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		      </tr>
		      
		      <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>姓名:</label></td>
		         <td><form:input path="name" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		         <td class="active"><label class="pull-right"><font color="red">*</font>登录名:</label></td>
		         <td><input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
					 <form:input path="loginName" htmlEscape="false" maxlength="50" class="form-control required userName"/></td>
		      </tr>
		      
		      
		      <tr>
		         <td class="active"><label class="pull-right"><c:if test="${empty user.id}"><font color="red">*</font></c:if>密码:密码要求，大写+小写+数字+字符（至少包含2种），且至少8位</label></td>
		         <td><input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="form-control ${empty user.id?'required':''}"/>
					<c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if></td>
		         <td class="active"><label class="pull-right"><c:if test="${empty user.id}"><font color="red">*</font></c:if>确认密码:</label></td>
		         <td><input id="confirmNewPassword" name="confirmNewPassword" type="password"  class="form-control ${empty user.id?'required':''}" value="" maxlength="50" minlength="3" equalTo="#newPassword"/></td>
		      </tr>
		      
		       <tr>
		         <td class="active"><label class="pull-right">邮箱:</label></td>
		         <td><form:input path="email" htmlEscape="false" maxlength="100" class="form-control email"/></td>
		         <td class="active"><label class="pull-right">电话:</label></td>
		         <td><form:input path="phone" htmlEscape="false" maxlength="100" class="form-control"/></td>
		      </tr>
		      
		      <tr>
		         <td class="active"><label class="pull-right">手机:</label></td>
		         <td><form:input path="mobile" htmlEscape="false" maxlength="100" class="form-control"/></td>
		         <td class="active"><label class="pull-right">是否允许登录:</label></td>
		         <td>
		          <form:select path="loginFlag"  class="form-control" >
					<form:options items="${fns:getDictList('yes_no')}" id="loginFlag" itemLabel="label" itemValue="value" htmlEscape="false"/>
				  </form:select> 
				</td>
		      </tr>
		      
		      <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>用户角色:</label></td>
		         <td>
		         	<%-- <form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" cssClass="i-checks required"/>
		         	<label id="roleIdList-error" class="error" for="roleIdList"></label>
		         	 --%>
		         	 <%-- <c:forEach var="bean" items="${allRoles}" varStatus="status">
							<tr>
								<td><input type="checkbox" name="roleIdList" value="${bean.id }" id="${bean.id }" rolename="${bean.name }"}/></td>
								<td>${bean.name }</td>
							</tr>
           			</c:forEach> --%>
           			<input id="checkbox" type="hidden" name="roleIdList" />
           			<input type="checkbox" name="isSysadmin" id="isadmin"
           			<c:if test="${user!=null&&user.isSysadmin=='1'}">checked="checked"</c:if> 
           			value="1" onclick="changeisSysadmin(1)"  ><a href="#" onclick="changeisSysadmin(1)" >系统管理员</a>
           			<input type="checkbox" name="isSysadmin" id="isnoadmin"
           			<c:if test="${user!=null&&user.isSysadmin=='0'||user.gmId!=null&&user.gmId!=''}">checked="checked"</c:if>
           			 value="0" onclick="changeisSysadmin(0)" >
           			<a href="#" onclick="changeisSysadmin(0)" >工贸管理员</a> 
           			 
           			
           			
           			<ul id="checkul" style="display:${user.isSysadmin==1?'block':'none'}">
           			    
					    <c:forEach var="bean" items="${allRoles}" varStatus="status">
						    <c:if test="${bean.istab==1}" >
						   
						        <li style="padding-left: 10px;">
									<label for="${bean.id }">
									    <input type="checkbox"  checked
									    name="checkli" id="${bean.id }">${bean.name }
									</label>
								</li>
							</c:if>
							
					      </c:forEach>
					     <c:forEach var="bean" items="${allRoles}" varStatus="status">
				            <c:if test="${bean.istab!=1}" >
							     <li style="padding-left: 10px;">
									<label for="${bean.id }">
									    <input type="checkbox"  
									    name="checkli" id="${bean.id }">${bean.name }
									</label>
								</li>
							</c:if>
						 </c:forEach>
					 </ul>
					 
           			<ul id="gminfo" style="display:${user.isSysadmin==0?'block':'none'}"> 
           			     <c:forEach var="bean" items="${gmInfoList}" varStatus="status">
				            <c:if test="${bean.gmtab==1}" >
							     <li style="padding-left: 10px;">
									<label for="${bean.branchCode }">
									    <input type="radio" checked
									    name="gmId" id="${bean.branchCode }" value="${bean.branchCode }">${bean.gmName }
									</label>
								</li>
							</c:if>
							<c:if test="${bean.gmtab!=1}" >
							     <li style="padding-left: 10px;">
									<label for="${bean.branchCode }">
									    <input type="radio" 
									    name="gmId" id="${bean.branchCode }" value="${bean.branchCode }">${bean.gmName }
									</label>
								</li>
							</c:if>
						 </c:forEach>
					 </ul>
			      
		          </td>
		         <td class="active"><label class="pull-right">备注:</label></td>
		         <td><form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control"/></td>
		      </tr>
		      
		      <c:if test="${not empty user.id}">
		       <tr>
		         <td class=""><label class="pull-right">创建时间:</label></td>
		         <td><span class="lbl"><fmt:formatDate value="${user.createDate}" type="both" dateStyle="full"/></span></td>
		         <td class=""><label class="pull-right">最后登陆:</label></td>
		         <td><span class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></span></td>
		      </tr>
		     </c:if>
		      </tbody>
		      
		      </table>
	</form:form>
	
	
	
</body>


</html>