<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>分类推荐管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					jh.loading();
					form.submit();
				},
                rules : {
                    "sort" : {
                        required : true,
                        number : true//期望的是true,如果为false则展示提示信息
                    }
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
				<a class="panelButton" href="${ctx}/shop/recommendCat"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="recommendCat" action="${ctx}/shop/recommendCat/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>


				<div class="form-group">
					<label class="col-sm-2 control-label">操作：</label>
					<div class="col-sm-10">
						<sys:gridselect  url="${ctx}/shop/storeGoods/data?isShelf=1" id="openStoreGoods"
										 name="openStoreGoods" value="选择店铺商品" labelName="openStoreGoods"
										 labelValue="选择店铺商品"
										 title="选择店铺产品" cssClass="form-control"
										 fieldLabels="店铺名称|分类大类|产品编码|产品名称"
										 fieldKeys="storeName|categoryPname|code|goodsName"
										 searchLabels="店铺名称|分类大类|产品编码"
										 searchKeys="storeName|categoryPname|code" cssStyle="width:110px;border-color: #5cb85c; cursor:pointer; color: #fff;  background-color: #5cb85c;"></sys:gridselect>
					</div>
				</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">产品大类名称：</label>
				<div class="col-sm-10">
					<form:select path="categoryId" value="${recommendCat.categoryId}" readonly="true"  htmlEscape="false"
								 class="form-control required categoryPid remo">
						<form:option value="" label="请选择产品大类" selected="true"/>
						<form:options items="${goodsCategoryList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
					</form:select>
					<input type="hidden" name="categoryName" value="${recommendCat.categoryName}" class="categoryName" >
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">产品编码：</label>
				<div class="col-sm-10">
					<form:input path="goodsCode" htmlEscape="false" readonly="true"
								class="form-control goodsCode remo"/>
					<input type="hidden" name="goodsId" class="goodsId">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">产品名称：</label>
				<div class="col-sm-10">
					<form:input path="goodsName" htmlEscape="false" readonly="true"
								class="form-control goodsName remo"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">店铺名称：</label>
				<div class="col-sm-10">
					<form:select path="storeId" value="${recommendCat.storeId}" readonly="true"
								 htmlEscape="false"
								 class="form-control required storeId remo">
						<form:option value="" label="请选择店铺" selected="true"/>
						<form:options items="${storeList}" itemLabel="storeName" itemValue="id"
									  htmlEscape="false"/>
					</form:select>
				</div>
			</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">排序：</label>
					<div class="col-sm-10">
						<form:input path="sort" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
		<c:if test="${fns:hasPermission('shop:recommendCat:edit') || isAdd}">
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