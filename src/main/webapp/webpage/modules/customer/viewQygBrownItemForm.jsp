<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>工程版信息详情视图管理</title>
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
				<a class="panelButton" href="${ctx}/customer/viewQygBrownItem"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="viewQygBrownItem" action="${ctx}/customer/viewQygBrownItem/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">brown_id：</label>
					<div class="col-sm-10">
						<form:input path="brownId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">product_id：</label>
					<div class="col-sm-10">
						<form:input path="productId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">product_code：</label>
					<div class="col-sm-10">
						<form:input path="productCode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">product_name：</label>
					<div class="col-sm-10">
						<form:input path="productName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">order_quantity：</label>
					<div class="col-sm-10">
						<form:input path="orderQuantity" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">price：</label>
					<div class="col-sm-10">
						<form:input path="price" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">actual_order_quantity：</label>
					<div class="col-sm-10">
						<form:input path="actualOrderQuantity" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">izhikou：</label>
					<div class="col-sm-10">
						<form:input path="izhikou" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">ihoufan：</label>
					<div class="col-sm-10">
						<form:input path="ihoufan" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">itaifan：</label>
					<div class="col-sm-10">
						<form:input path="itaifan" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">最后修改时间：</label>
					<div class="col-sm-10">
						<form:input path="lastModifiedDate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">产品单位：</label>
					<div class="col-sm-10">
						<form:input path="cinvmUnit" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">供价：</label>
					<div class="col-sm-10">
						<form:input path="iinvrCost" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">参照平均政策-本型号台阶限价：</label>
					<div class="col-sm-10">
						<form:input path="avePolicy" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">当前参照最大政策-型号基准价：</label>
					<div class="col-sm-10">
						<form:input path="curPolicy" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">本型号利润审批价-基准价：</label>
					<div class="col-sm-10">
						<form:input path="profit" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">创建人ID：</label>
					<div class="col-sm-10">
						<form:input path="createdById" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">最后修改人ID：</label>
					<div class="col-sm-10">
						<form:input path="lastModifiedById" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否删除：</label>
					<div class="col-sm-10">
						<form:input path="deleted" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">创建人：</label>
					<div class="col-sm-10">
						<form:input path="createdBy" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">创建时间：</label>
					<div class="col-sm-10">
						<form:input path="createdDate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">最后修改人名称：</label>
					<div class="col-sm-10">
						<form:input path="lastModifiedBy" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">创建来源信息：</label>
					<div class="col-sm-10">
						<form:input path="creProId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">修改来源信息：</label>
					<div class="col-sm-10">
						<form:input path="modProId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">批处理时间：</label>
					<div class="col-sm-10">
						<form:input path="batchDate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
		<c:if test="${fns:hasPermission('customer:viewQygBrownItem:edit') || isAdd}">
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
</body>
</html>