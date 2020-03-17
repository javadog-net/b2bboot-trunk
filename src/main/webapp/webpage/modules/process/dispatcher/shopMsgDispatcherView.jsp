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
				<a class="panelButton" href="${ctx}/process/dispatcher/shopMsgDispatcher"><i class="ti-angle-left"></i> 返回</a>
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
						<td>
								<c:if test="${shopMsg.channel=='0'}">400</c:if>
								<c:if test="${shopMsg.channel=='1'}">400客服</c:if>
								<c:if test="${shopMsg.channel=='2'}">大客户app</c:if>
								<c:if test="${shopMsg.channel=='3'}">E企app商空</c:if>
								<c:if test="${shopMsg.channel=='4'}">企业购官网</c:if>
								<c:if test="${shopMsg.channel=='5'}">住房租赁频道</c:if>
								<c:if test="${shopMsg.channel=='6'}">军工</c:if>
								<c:if test="${shopMsg.channel=='7'}">商空</c:if>
								<c:if test="${shopMsg.channel=='8'}">商空专区</c:if>
								<c:if test="${shopMsg.channel=='9'}">固话</c:if>
								<c:if test="${shopMsg.channel=='10'}">大接待</c:if>
								<c:if test="${shopMsg.channel=='11'}">官网留言</c:if>
								<c:if test="${shopMsg.channel=='12'}">客服</c:if>
								<c:if test="${shopMsg.channel=='13'}">微信</c:if>
								<c:if test="${shopMsg.channel=='14'}">手机端</c:if>
								<c:if test="${shopMsg.channel=='15'}">招标网</c:if>
								<c:if test="${shopMsg.channel=='16'}">海尔b2b</c:if>
								<c:if test="${shopMsg.channel=='17'}">海尔商城</c:if>
								<c:if test="${shopMsg.channel=='18'}">海尔官网</c:if>
								<c:if test="${shopMsg.channel=='19'}">海尔手机端</c:if>
								<c:if test="${shopMsg.channel=='20'}">百度</c:if>
								<c:if test="${shopMsg.channel=='21'}">社群交互</c:if>
								<c:if test="${shopMsg.channel=='22'}">酒店频道留言板</c:if>
						</td>
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
						<td>
							<c:if test="${shopMsg.depart=='0'}">采购</c:if>
							<c:if test="${shopMsg.depart=='1'}">营销</c:if>
							<c:if test="${shopMsg.depart=='2'}">设计</c:if>
							<c:if test="${shopMsg.depart=='3'}">财务</c:if>
							<c:if test="${shopMsg.depart=='4'}">行政</c:if>
							<c:if test="${shopMsg.depart=='5'}">业务</c:if>
							<c:if test="${shopMsg.depart=='6'}">其他</c:if>
						</td>
					</tr>
					<tr>
						<td>产品组:</td>
						<td>${shopMsg.proGroup}</td>
						<td>需求详细描述:</td>
						<td>${shopMsg.memo}</td>
					</tr>
					<tr>
						<td>状态:</td>
						<td>
							<c:if test="${shopMsg.msgFlag=='0'}">平台待审核</c:if>
							<c:if test="${shopMsg.msgFlag=='10'}">平台审核通过</c:if>
							<c:if test="${shopMsg.msgFlag=='11'}">平台审核不通过(废弃)</c:if>
							<c:if test="${shopMsg.msgFlag=='12'}">平台直接派单</c:if>
							<c:if test="${shopMsg.msgFlag=='13'}">商空项目平台审核通过</c:if>
							<c:if test="${shopMsg.msgFlag=='20'}">总监通过</c:if>
							<c:if test="${shopMsg.msgFlag=='21'}">总监审核不通过</c:if>
							<c:if test="${shopMsg.msgFlag=='22'}">总监派单</c:if>
							<c:if test="${shopMsg.msgFlag=='23'}">待派单</c:if>
							<c:if test="${shopMsg.msgFlag=='30'}">抢单成功</c:if>
							<c:if test="${shopMsg.msgFlag=='31'}">经销商选择承接方式前关闭</c:if>
							<c:if test="${shopMsg.msgFlag=='40'}">经销商选择承接方式-工程单</c:if>
							<c:if test="${shopMsg.msgFlag=='41'}">经销商选择承接方式后关闭</c:if>
							<c:if test="${shopMsg.msgFlag=='42'}">经销商选择承接方式-零售</c:if>
							<c:if test="${shopMsg.msgFlag=='60'}">客服关闭</c:if>
							<c:if test="${shopMsg.msgFlag=='61'}">客服派单关闭</c:if>
						</td>
					</tr>
					<tr>
						<td>海尔接口人:</td>
						<td>
							<c:if test="${shopMsg.managerNo == null}">
								未选择
							</c:if>
							<c:if test="${shopMsg.managerNo != null}">
								${shopMsg.managerNo}
								${shopMsg.managerName}
							</c:if>
							</td>
						<td>接口人选择:</td>
						<td>
							<select name="managerNo" id="managerNo" class="form-control required managerNo" style="float: left; width: 80%">
								<option value="">请选择接口人</option>
								<c:forEach items="${listUserDTO}" var="item">
									<option value="${item.code}" name="${item.name}">${item.name} - ${item.code}</option>
								</c:forEach>
							</select>
							<shiro:hasPermission name="process:dispatcher:shopMsgDispatcher:sureHaierPerson">
							<a class="btn btn-primary sure">确定</a>
							</shiro:hasPermission>
						</td>
					</tr>
					<tr class="dis-type">
						<td>是否派单:</td>
						<td>
							<input type="radio"  name="dispaFlag" checked value="1"> 是
							&nbsp;
							&nbsp;
							<input type="radio"  name="dispaFlag" value="0"> 否
						</td>
						<td class="dis-true">选择承接经销商:</td>
						<td class="dis-true">
							<shiro:hasPermission name="process:shopmsg:shopMsg:choose">
							<a class="btn btn-success choose">选择</a>
							</shiro:hasPermission>
						</td>

						<td class="dis-false hide">关闭原因:</td>
						<td class="dis-false hide" >
							<textarea name="" id="" cols="15" rows="5" class="form-control closeReason" style="width: 80%;float: left;"></textarea>
							<shiro:hasPermission name="process:dispatcher:shopMsgDispatcher:dispatclose">
							<a class="btn btn-warning dispatclose">关闭</a>
							</shiro:hasPermission>
						</td>

					</tr>

					<tr class="dis-result" style="display: none">
						<td>操作状态:</td>
						<td class="status"></td>
						<td>操作结果:</td>
						<td class="result"></td>


					</tr>
					</tbody>
				</table>
			</div>
		<form:form id="inputForm" modelAttribute="shopMsg" action="${ctx}/process/shopmsg/shopMsg/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}" type="error"/>

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
		//发送短信
		$(".sendMsg").on("click",function(){
			layer.prompt({
				formType: 2,
				title: '${shopMsg.mobile}-短信推送',
				maxlength: 140, //可输入文本的最大长度，默认500
			}, function(value, index, elem){
				window.location.reload();
				 //进行短信发送
				 jh.post("${ctx}/process/shopmsg/shopMsg/sendShortMsg",{
					'id': "${shopMsg.id}",
				    'content': value
				 },function(data){
					if(data.success){
						jh.success(data.msg);
					}else{
						jh.error(data.msg);
					}
				});
				 layer.close(index);
			});
		});
		//确定
		$(".sure").on("click",function(){

			var managerNo = $(".managerNo").val();
			var managerName = $(".managerNo").find("option:selected").attr("name");
			if(managerNo=="" || managerNo==""){
				jh.info("请选择海尔接口人!");
			}
			jh.post("${ctx}/process/dispatcher/shopMsgDispatcher/sureHaierPerson",{
				'id': "${shopMsg.id}",
				'managerNo': managerNo,
				'managerName':managerName
			},function(data){
				if(data.success){
					layer.alert(data.msg,{icon:1},function(){
						window.location.reload();
					})
				}else{
					jh.error(data.msg);
				}
			});
		});
		//平台选择关闭
		$(".dispatclose").on("click",function(){
			var dispatclose = layer.confirm('确认是否关闭此需求？', {
				btn: ['确定','取消'] //按钮
			}, function(){
				var data = {
					id:'${shopMsgDispatcher.id}',
					closeReason: $(".closeReason").val()
				}
				jh.post("${ctx}/process/dispatcher/shopMsgDispatcher/dispatclose", data, function(data){
					layer.close(dispatclose);
					if(data.success){
						jh.success(data.msg, {icon: 6});
						$(".dis-type").hide();
						$(".dis-result").show();
						$(".status").text("已关闭");
						$(".result").text($(".closeReason").val());
					}else{
						jh.error(data.msg);
					}
				})

			}, function(){
				layer.close(dispatclose);
			});
		})
		//选择是关闭还是通过
		$("input:radio[name='dispaFlag']").on("change",function(){
			var dfVal = $(this).val();
			if(dfVal=="0"){
				$(".dis-true").addClass("hide");
				$(".dis-false").removeClass("hide");
			}else{
				$(".dis-true").removeClass("hide");
				$(".dis-false").addClass("hide");
			}
		})
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
							'dealerId': arrIds[0],
							'dispatcherId':'${shopMsgDispatcher.id}'
						},function(data){
							layer.close(confirm);
							layer.close(open);
							if(data.success){
								jh.success(data.msg, {icon: 6});
								$(".dis-type").hide();
								$(".dis-result").show();
								$(".status").text("已派单");
								$(".result").text(arrNames[0]);
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
<style>
	.hide{
		display: none;
	}
</style>
</body>
</html>