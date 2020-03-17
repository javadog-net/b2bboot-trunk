<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>需求相关管理</title>
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
	<link href="//raw.githack.com/hnzzmsf/layui-formSelects/master/dist/formSelects-v4.css" rel="stylesheet"/>
	<link href="${ctxStatic}/plugin/layui/dist/css/layui.css" rel="stylesheet"/>
	<script src="${ctxStatic}/plugin/layui/dist/layui.js"></script>
	<script src="//raw.githack.com/hnzzmsf/layui-formSelects/master/dist/formSelects-v4.min.js"></script>
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

			<div class="">
				<table class="table table-striped table-hover table-bordered">
					<h3 style="font-size: 17px; font-weight: bold; margin-bottom: 10px;">需求相关信息</h3>
					<tbody>
					<tr class="active">
						<td>公司名称:</td>
						<td>${shopMsg.companyName}</td>
						<td>公司鹰眼确认:</td>
						<td>
							<c:if test="${shopMsg.companyOrgName!=null}">
							已确认
							</c:if>
							<c:if test="${shopMsg.companyOrgName==null}">
								未确认
							</c:if>
						</td>
					</tr>
					<tr>
						<td>确认公司名称:</td>
						<td>${shopMsg.companyOrgName}</td>
						<td>组织机构代码证:</td>
						<td>${shopMsg.companyOrgCode}</td>
					</tr>
					<tr>
						<td>省份:</td>
						<td>${shopMsg.provinceName}</td>
						<td>城市:</td>
						<td>${shopMsg.cityName}</td>
					</tr>
					<tr>
						<td>地区:</td>
						<td>${shopMsg.districName}</td>
						<td>详细地址:</td>
						<td>${shopMsg.addressDetail}</td>
					</tr>
					<tr>
						<td>工贸名称:</td>
						<td>${shopMsg.orgName}</td>
						<td>渠道来源:</td>
						<td>${shopMsg.channel}</td>
					</tr>
					<tr>
						<td>联系人:</td>
						<td>${shopMsg.connectName}</td>
						<td>联系方式:</td>
						<td>${shopMsg.mobile}</td>
					</tr>
					<tr>
						<td>邮箱:</td>
						<td>${shopMsg.email}</td>
						<td>部门:</td>
						<td>${shopMsg.depart}</td>
					</tr>
					<tr>
						<td>产品组:</td>
						<td>${shopMsg.proGroup}</td>
						<td>需求详细描述:</td>
						<td>${shopMsg.memo}</td>
					</tr>
					<tr>
						<td>选择承接经销商:</td>
						<td class="td-choose">
							<shiro:hasPermission name="process:shopmsg:shopMsg:choose">
							<a class="btn btn-success choose">选择</a>
							</shiro:hasPermission>
						</td>
						<td>指派经销商:</td>
						<td class="cus"></td>
					</tr>
					</tbody>
				</table>
			</div>
		<form:form id="inputForm" modelAttribute="shopMsg" action="${ctx}/process/shopmsg/shopMsg/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>

		</form:form>
		</div>
	</div>
	</div>
</div>
</div>
<style>
	table tr td:nth-of-type(odd){
		width: 10%;
		font-weight: bold;
	}
	table tr td:nth-of-type(even){
		width: 30%;
	}
</style>
<script>
	$(function(){
		//选取经销商
		$(".choose").on("click",function(){
			var id = '${shopMsg.id}';
			var open = layer.open({
				type: 2,
				title: '选取经销商',
				content: '${ctx}/process/shopmsg/shopMsg/choose?msgId='+id,
				maxmin: true,
				area: ['800px', '80%'],
				btn: ['确定', '取消'],
				yes: function (index, layero) {var res = window["layui-layer-iframe" + index].callbackdata();
					var arrIds = res.arrIds;
					var arrNames = res.arrNames;
					if(arrIds.length>1 || arrIds.length==0){
						jh.info("请选择一条经销商信息操作!");
						return;
					}
					var confirm = jh.confirm("确定直派经销商？",function(){
						//进行派单接口
						jh.post("${ctx}/process/shopmsg/shopMsg/dispatcherOrder",{
							'id': '${shopMsg.id}',
							'dealerId': arrIds[0]
						},function(data){
							layer.close(confirm);
							layer.close(open);
							if(data.success){
								jh.success(data.msg, {icon: 6});
								$(".cus").text(arrNames[0]);
								$(".td-choose").empty();
								$(".td-choose").text("已派单");
							}else{
								jh.error(data.msg);
							}
						});
					})
					//复制

				}
			});
		})
	});
</script>
</body>
</html>