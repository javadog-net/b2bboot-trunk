<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>广告管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
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

			$(".relevantType").on("change",function(){
			    var v = +$(this).val();
			    //因为分为不同大类
			    if(!v){
			        $(".c1").show();
                    $(".c2").hide();
                    $(".c2").find("select").val("");
				}else{
                    $(".c2").show();
                    $(".c1").hide();
                    $(".c1").find("select").val("");
                }
			})
			
		});
	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/shop/advert"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="advert" action="${ctx}/shop/advert/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">关联类型</label>
					<div class="col-sm-10">
						<form:select path="relevantType" class="form-control relevantType">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('association_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
			<div class="form-group c1">
				<label class="col-sm-2 control-label">分类</label>
				<div class="col-sm-10 ">
					<form:select path="relevantId" class="form-control cf required">
						<form:option value="" label=""/>
						<form:options items="${goodsCategoryList}" itemLabel="name" itemValue="id" htmlEscape="false" class="option"/>
					</form:select>
				</div>
			</div>
            <div class="form-group c2">
				<label class="col-sm-2 control-label">分类</label>
				<div class="col-sm-10">
					<form:select path="relevantId" class="form-control cf required">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('recommend_specialtopic')}" itemLabel="label" itemValue="value" htmlEscape="false" class="option"/>
					</form:select>
				</div>
			</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">图片：</label>
					<div class="col-sm-10">
						<form:hidden id="advertImgs" path="advertImgs" htmlEscape="false" maxlength="1000" class="form-control required"/>
						<sys:webupload input="advertImgs" type="file" multiple="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">广告类型：</label>
					<div class="col-sm-10">
						<form:select path="advertType" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('advert_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">广告链接(多个链接用逗号隔开)：</label>
					<div class="col-sm-10">
						<form:input path="advertUrl" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">广告标题：</label>
					<div class="col-sm-10">
						<form:input path="advertTitle" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">广告信息：</label>
					<div class="col-sm-10">
						<form:input path="advertInfo" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">排序：</label>
					<div class="col-sm-10">
						<form:input path="sort" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
		<c:if test="${fns:hasPermission('shop:advert:edit') || isAdd}">
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
<style>
	.c1,.c2{
		display: none;}
</style>
</body>
</html>