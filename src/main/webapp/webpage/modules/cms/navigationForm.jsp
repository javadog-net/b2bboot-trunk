<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>导航管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					jh.loading('正在提交，请稍等...');
					var linkName =$(" input[ name='category.name' ] ").val();
					$("#linkName").val(linkName);
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
		function changeLinkType() {
			var linkType = $("#linkType").val();
			//内容模块
			if(linkType==1){
				$("#categoryDiv").show();
				$("#contentDiv").show();
				$("#linkUrlDiv").hide();
			}
			//外部链接
			else if(linkType==2){
				$("#linkUrlDiv").show();
				$("#categoryDiv").hide();
				$("#contentDiv").hide();
			}else{
				//产品模块
				$("#categoryDiv").show();
				$("#linkUrlDiv").hide();
				$("#contentDiv").hide();

			}
		}
	</script>
</head>
<body>
<div class="wrapper wrapper-content">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary">
				<div class="panel-body">
					<form:form id="inputForm" modelAttribute="navigation" action="${ctx}/cms/navigation/save" method="post" class="form-horizontal">
						<form:hidden path="id"/>
						<sys:message content="${message}"/>
						<div class="form-group">
							<label class="col-sm-2 control-label">上级导航：</label>
							<div class="col-sm-10">
								<sys:treeselect id="navigation" name="parent.id" value="${navigation.parent.id}" labelName="parent.name" labelValue="${navigation.parent.name}"
												title="导航" url="/cms/navigation/treeData" extId="${navigation.id}" cssClass="form-control required"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>导航名称：</label>
							<div class="col-sm-10">
								<form:input path="name" htmlEscape="false"    class="form-control required"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">导航类型：</label>
							<div class="col-sm-10">
								<form:select path="nType" htmlEscape="false"    class="form-control required">
									<form:options items="${fns:getDictList('navigation_type')}" itemLabel="label" itemValue="value" htmlEscape="false"></form:options>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>导航位置：</label>
							<div class="col-sm-10">
								<form:select path="position" htmlEscape="false"    class="form-control required">
									<form:options items="${fns:getDictList('navigation_position')}" itemLabel="label" itemValue="value" htmlEscape="false"></form:options>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">导航图标：</label>
							<div class="col-sm-10">
								<sys:iconselect id="thumb" name="thumb" value="${navigation.thumb}"/>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">导航属性：</label>
							<div class="col-sm-10">
								<form:select path="linkType" htmlEscape="false"    class="form-control required" onchange="changeLinkType()">
									<form:options items="${fns:getDictList('link_type')}" itemLabel="label" itemValue="value" htmlEscape="false"></form:options>
								</form:select>
							</div>
						</div>
						<div id = "categoryDiv">

							<div class="form-group">
								<label class="col-sm-2 control-label">栏目：</label>
								<div class="col-sm-10">
									<sys:treeselect id="category" name="linkId" value="${navigation.linkId}" labelName="category.name" labelValue="${navigation.linkName}"
													title="栏目" url="/cms/category/treeData" extId="" cssClass="form-control"/>
								</div>
							</div>
						</div>
						<div id="contentDiv">
							<div class="form-group">
								<label class="col-sm-2 control-label">链接类型：</label>
								<div class="col-sm-10">
									<form:select path="linkKind" htmlEscape="false"    class="form-control required" >
										<form:options items="${fns:getDictList('link_kind')}" itemLabel="label" itemValue="value" htmlEscape="false"></form:options>
									</form:select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">链接内容：</label>
								<div class="col-sm-10">
									<form:input path="linkName" htmlEscape="false"    class="form-control "/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">内容（栏目）标识：</label>
								<div class="col-sm-10">
									<form:input path="linkMark" htmlEscape="false"    class="form-control "/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">动态扩展栏目：</label>
								<div class="col-sm-10">
									<form:radiobuttons path="isExtend" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
								</div>
							</div>
						</div>
						<div id="linkUrlDiv" style="display: none" >
							<div class="form-group">
								<label class="col-sm-2 control-label">外部链接地址：</label>
								<div class="col-sm-10">
									<form:input path="linkUrl" htmlEscape="false"    class="form-control "/>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">导航栏显示：</label>
							<div class="col-sm-10">
								<form:radiobuttons path="isShow" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">可点：</label>
							<div class="col-sm-10">
								<form:radiobuttons path="isLink" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">在新窗口中打开：</label>
							<div class="col-sm-10">
								<form:radiobuttons path="isTargetBlank" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">描述信息：</label>
							<div class="col-sm-10">
								<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
							</div>
						</div>
						<c:if test="${fns:hasPermission('cms:navigation:edit') || isAdd}">
							<div class="col-lg-3"></div>
							<div class="col-lg-6">
								<div class="form-group text-center">
									<div>
										<shiro:hasPermission name="cms:navigation:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
										<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
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