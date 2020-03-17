<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>需求相关管理</title>
	<meta name="decorator" content="ani"/>
	<meta http-equiv="pragma"content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires"content="0">
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
	<link href="${ctxStatic}/plugin/layui/formSelects-v4.css" rel="stylesheet"/>
	<script src="${ctxStatic}/plugin/layui/dist/layui.js"></script>
	<script src="${ctxStatic}/plugin/layui/formSelects-v4.min.js"></script>
	<script>
		//更改省份
		$(function(){
			//编辑回显时省市区内容
			var flag = "${isAdd}";
			if(!flag){
				getAreaInfo("${shopMsg.provinceId}",1,true);
				setTimeout(function(){
					getAreaInfo("${shopMsg.cityId}",2,true);
				},500)
			}
			//省份更改时触发
			$(".provinceId").on("change",function(){
				var areaId = $(this).val();
				getAreaInfo(areaId,1);
				$(".provinceName").val($(this).find("option:selected").text())

			})
			//城市更改时触发
			$(".cityId").on("change",function(){
				var areaId = $(this).val();
				getAreaInfo(areaId,2);
				$(".cityName").val($(this).find("option:selected").text())
			});
			//地区更改时触发
			$(".districId").on("change",function(){
				var districId = $(this).val();
				$(".districName").val($(this).find("option:selected").text())
			});
			//输入公司名称确认
			$(".confirm-company").on("click",function(){
				var inputCompany = $(".companyName").val();
				if(!inputCompany){
					jh.alert("请输入需要确认的公司名称");
				}
				$(".companyOrgName").empty();
				jh.get("${ctx}/process/shopmsg/shopMsg/queryEnterpriseList?companyName="+inputCompany, function(data){
					if(data.success){
						var arr = data.body.result;
						var options = "<option value=''>请选择确认公司名</option>";
						for(var i=0; i<arr.length; i++){
							if(arr[i].entName!=""){
								options = options + "<option value='"+arr[i].entName+"' creditNo='"+arr[i].creditNo+"'>"+arr[i].entName+"</option>"
							}
						}
						$(".companyOrgName").append(options);
					}else{
						jh.alert(data.msg);
					}
				})
			});
			//选择确认公司
			$(".companyOrgName").on("change",function(){
				var companyOrgCode = $(this).find("option:selected").attr("creditno");
				$(".companyOrgCode").val(companyOrgCode);
			})
		});

		//获取地区信息
		function getAreaInfo(areaId,type,isEdit){
			var aj = $.ajax({
				type: "POST",
				url: "${ctx}/process/shopmsg/shopMsg/getAreaInfo",
				beforeSend: function(){
					//遮罩层
					index = layer.load(1, {
						shade: [0.1,'#fff'] //0.1透明度的白色背景
					});
				},
				data: {
					areaId: areaId
				},
				success: function (data) {
					var aim = "",optionName="";
					if(1==type){
						$(".cityId").empty();
						$(".districId").empty();
						aim = ".cityId";
						optionName = "请选择城市";
					}else if(2==type){
						$(".districId").empty();
						aim = ".districId";
						optionName = "请选择地区";
					}
					if(data.success){
						var arr = data.body.result;
						var option = "<option value=''>"+optionName+"</option>";
						for(var i=0; i<arr.length; i++){
							option = option + "<option value='"+arr[i].id+"'>"+arr[i].cityName+"</option>"
						}
						$(aim).append(option);
					}
					//回显选中值
					if(isEdit){
						$(".cityId").val("${shopMsg.cityId}");
						$(".districId").val("${shopMsg.districId}");
					}
					//遮罩层隐藏
					layer.close(index);
				},
				error: function (err) {
					layer.alert(err+ "调取失败请联系管理员",{icon:2});
				}
			});
			return aj
		}


	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/process/shopmsg/shopMsg"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="shopMsg" action="${ctx}/process/shopmsg/shopMsg/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>公司：</label>
					<div class="col-sm-4">
						<form:input path="companyName" htmlEscape="false" class="form-control required companyName" style="float: left; width:80%" maxlength="100"/>
						<a class="btn btn-primary confirm-company" style="float: left;">确定</a>
					</div>
					<c:if test="${!isAdd}">
						<label class="col-sm-2 control-label">
							编辑前确认公司名称
						</label>
						<div class="col-sm-4">
							${shopMsg.companyOrgName.replace(","," ")}
						</div>
					</c:if>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>确认公司名：</label>
					<div class="col-sm-4">
							<form:select path="companyOrgName" class="form-control required companyOrgName">
							</form:select>

					</div>
					<label class="col-sm-2 control-label"><font color="red">*</font>组织机构代码证 ：</label>
					<div class="col-sm-4">
						<form:input path="companyOrgCode" htmlEscape="false"  readonly="true"   class="form-control required companyOrgCode"/>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>省份：</label>
					<div class="col-sm-4">
						<form:select path="provinceId" class="form-control required provinceId">
							<form:option value="" label="请选择省份"/>
							<c:forEach items="${areaCodeList}" var="area">
								<form:option value="${area.id}" label="${area.cityName}"/>
							</c:forEach>
						</form:select>
						<form:input path="provinceName" htmlEscape="false"   class="hidden form-control required provinceName"/>
						<form:input path="cityName" htmlEscape="false"   class="hidden form-control required cityName"/>
						<form:input path="districName" htmlEscape="false"   class="hidden form-control required districName"/>
					</div>
					<label class="col-sm-2 control-label"><font color="red">*</font>城市：</label>
					<div class="col-sm-4">
						<form:select path="cityId" class="form-control required cityId">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="cityName" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>地区：</label>
					<div class="col-sm-4">
						<form:select path="districId" class="form-control required districId">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
					<label class="col-sm-2 control-label"><font color="red">*</font>详细地址：</label>
					<div class="col-sm-4">
						<form:input path="addressDetail" htmlEscape="false"    class="form-control required" maxlength="120"/>
					</div>
				</div>
				<div class="form-group">
                    <label class="col-sm-2 control-label"><font color="red">*</font>产品组：</label>
                    <div class="col-sm-4">
                        <!-- 这里的xm-select属性是多选的ID, 如多处使用请保证全局唯一 -->
                        <form:select path="proGroupCode" id="proGroupCode" name="proGroupCode" xm-select="selectId" class="required proGroupCode" style="margin-left: 10px; width: 310px; color:#ccc; background:#f0f2f5">
                            <form:options items="${fns:getDictList('user_product_group')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>

                        <form:input path="proGroup" htmlEscape="false"  class="hidden form-control required proGroup"/>
                    </div>
					<label class="col-sm-2 control-label"><font color="red">*</font>渠道来源：</label>
					<div class="col-sm-4">
						<form:select path="channel" class="form-control required">
							<form:option value="" label="请选择渠道来源"/>
							<form:options items="${fns:getDictList('msg_channel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>联系人：</label>
					<div class="col-sm-4">
						<form:input path="connectName" htmlEscape="false"    class="form-control required" maxlength="30"/>
					</div>
					<label class="col-sm-2 control-label"><font color="red">*</font>联系方式：</label>
					<div class="col-sm-4">
						<form:input path="mobile" htmlEscape="false"    class="form-control required isPhone" maxlength="25"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">邮箱：</label>
					<div class="col-sm-4">
						<form:input path="email" htmlEscape="false"    class="form-control email"/>
					</div>

					<label class="col-sm-2 control-label"><font color="red">*</font>部门：</label>
					<div class="col-sm-4">
						<form:select path="depart" class="form-control required">
							<form:option value="" label="请选择部门"/>
							<form:options items="${fns:getDictList('depart_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">需求详细描述：</label>
				<div class="col-sm-4">
					<form:textarea path="memo" htmlEscape="false"  class="form-control " maxlength="120"/>
				</div>
			</div>


		<c:if test="${fns:hasPermission('process:shopmsg:shopMsg:edit') || isAdd}">
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
<!-- 执行渲染, 把原始select美化~~ -->
<script type="text/javascript">
	var formSelects = layui.formSelects;
	formSelects.render('selectId');
	var productId = '${shopMsg.proGroupCode}';
	if(productId!=""){
		var arr_pro = productId.split(";");
		formSelects.value('selectId', arr_pro);
	}
	formSelects.on('selectId', function(id, vals, val, isAdd, isDisabled){
		var arrSelect = formSelects.value('selectId', 'name');
		var valSelect = formSelects.value('selectId', 'value');
		//如果是选中，则添加
		if(isAdd){
			arrSelect.push(val.name);
		}else{
			//反正弹出数组
			arrSelect.pop(val.name);
		}

		var strSelect = arrSelect.join(';');
		$(".proGroup").val(strSelect);
	});

	$(function(){
		$(".orgId").on("change",function(){
			var orgName = $(this).find("option:selected").text();
			$(".orgName").val(orgName);
		});
	})
</script>
</body>
</html>