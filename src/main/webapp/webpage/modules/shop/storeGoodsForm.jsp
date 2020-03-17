<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
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
			
	        $('#shelfTime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
	        $('#auditTime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
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
				<a class="panelButton" href="${ctx}/shop/storeGoods"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="storeGoods" action="${ctx}/shop/storeGoods/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>店铺：</label>
					<div class="col-sm-10">
                        <sys:gridselect url="${ctx}/shop/store/data" id="store" name="storeId" value="${storeGoods.storeId}" labelName="storeName" labelValue="${storeGoods.storeName}"
                                        title="选择店铺" cssClass="form-control required" fieldLabels="店铺名称|等级" fieldKeys="storeName|grade" searchLabels="店铺名称" searchKeys="storeName"></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>产品大类：</label>
					<div class="col-sm-10">
						<form:input path="categoryPid" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>产品子类：</label>
					<div class="col-sm-10">
						<form:input path="categoryId" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>产品编码：</label>
					<div class="col-sm-10">
						<form:input path="code" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>市场价：</label>
					<div class="col-sm-10">
						<form:input path="marketPrice" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>卖价：</label>
					<div class="col-sm-10">
						<form:input path="price" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>库存：</label>
					<div class="col-sm-10">
						<form:input path="stock" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">库存预警：</label>
					<div class="col-sm-10">
						<form:input path="stockWarning" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否推荐：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="isRecommend" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否促销：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="isPromotion" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否上架：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="isShelf" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">申请上架时间：</label>
					<div class="col-sm-10">
						<p class="input-group">
							<div class='input-group form_datetime' id='shelfTime'>
			                    <input type='text'  name="shelfTime" class="form-control"  value="<fmt:formatDate value="${storeGoods.shelfTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>						            
			            </p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">审核时间：</label>
					<div class="col-sm-10">
						<p class="input-group">
							<div class='input-group form_datetime' id='auditTime'>
			                    <input type='text'  name="auditTime" class="form-control"  value="<fmt:formatDate value="${storeGoods.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>						            
			            </p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">审核人：</label>
					<div class="col-sm-10">
						<form:input path="auditor" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">审核状态：</label>
					<div class="col-sm-10">
						<form:select path="auditState" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('audit_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">审核意见：</label>
					<div class="col-sm-10">
						<form:input path="auditDesc" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>浏览数量：</label>
					<div class="col-sm-10">
						<form:input path="goodsClick" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>销售数量：</label>
					<div class="col-sm-10">
						<form:input path="goodsSalenum" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>收藏数量：</label>
					<div class="col-sm-10">
						<form:input path="goodsCollect" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">评论数：</label>
					<div class="col-sm-10">
						<form:input path="goodsCommentNum" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注：</label>
					<div class="col-sm-10">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</div>
				</div>
		<c:if test="${fns:hasPermission('shop:storeGoods:edit') || isAdd}">
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