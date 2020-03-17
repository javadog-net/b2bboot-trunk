<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理详情</title>
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
				<a class="panelButton" href="${ctx}/process/shopproject/shopProject"><i class="ti-angle-left"></i> 返回</a>
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
					</tbody>
				</table>
                <c:if test="${!empty listShopMsgDispatcher}">
                    <table class="table table-striped table-hover table-bordered" style="margin-top: 20px">
                        <h3 style="font-size: 17px; font-weight: bold; margin-bottom: 10px;">派单相关信息</h3>
                        <tbody>

                        <c:forEach var="shopMsgDispatcher" items="${listShopMsgDispatcher}">
                            <tr class="active">
                                <td>派单时间:</td>
                                <td>
                                    <fmt:formatDate value="${shopMsgDispatcher.dispaDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <td>派单人:</td>
                                <td>
                                    ${shopMsgDispatcher.dispaUser}
                                </td>
                            </tr>
                            <tr >
                                <td>直派经销商:</td>
                                <td>
                                    ${shopMsgDispatcher.custcode}
                                </td>
                                <td>创建时间:</td>
                                <td>
                                    <fmt:formatDate value="${shopMsgDispatcher.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                            </tr>

                            <tr class="active">
                                <td>派单区分:</td>
                                <td>
                                    <c:if test="${shopMsgDispatcher.dispaType=='0'}">平台派单</c:if>
                                    <c:if test="${shopMsgDispatcher.dispaType=='1'}">总监派单</c:if>
                                </td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                </c:if>

				<table class="table table-striped table-hover table-bordered" style="margin-top: 20px">
					<h3 style="font-size: 17px; font-weight: bold; margin-bottom: 10px;">经销商订单相关信息</h3>
					<tbody>
					<tr class="active">
						<td>经销商编码:</td>
						<td>
							${shopMsgCustcodeOrder.custCode}
						</td>
						<td>经销商名称:</td>
						<td>
							${shopMsgCustcodeOrder.custName}
						</td>
					</tr>
					<tr class="active">
						<td>订单来源:</td>
						<td>
							<c:if test="${shopMsgCustcodeOrder.fromSource=='0'}">抢单</c:if>
							<c:if test="${shopMsgCustcodeOrder.fromSource=='1'}">平台派单</c:if>
							<c:if test="${shopMsgCustcodeOrder.fromSource=='2'}">总监派单</c:if>
						</td>
						<td>生成时间:</td>
						<td>

							<fmt:formatDate value="${shopMsgCustcodeOrder.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
					<tr class="active">
						<td>状态:</td>
						<td>
							<c:if test="${shopMsgCustcodeOrder.cancelFlag=='0'}">跟进中</c:if>
							<c:if test="${shopMsgCustcodeOrder.cancelFlag=='1'}">未选择承接方式前废弃</c:if>
							<c:if test="${shopMsgCustcodeOrder.cancelFlag=='2'}">零售关闭</c:if>
							<c:if test="${shopMsgCustcodeOrder.cancelFlag=='3'}">工程关闭</c:if>
						</td>
						<td>承接方式:</td>
						<td>
							<c:if test="${shopMsgCustcodeOrder.underTake==''}">未选择</c:if>
							<c:if test="${shopMsgCustcodeOrder.underTake=='0'}">工程单</c:if>
							<c:if test="${shopMsgCustcodeOrder.underTake=='1'}">零售</c:if>
						</td>
					</tr>
					</tbody>
				</table>

                <table class="table table-striped table-hover table-bordered" style="margin-top: 20px">
                    <h3 style="font-size: 17px; font-weight: bold; margin-bottom: 10px;">HPS漏斗项目状态</h3>
                    <tbody>
                    <tr class="active">
                        <td>项目编码:</td>
                        <td>
                            ${shopProject.projectcode}
                        </td>
                        <td>项目信息:</td>
                        <td>
                            ${shopProject.projectname}
                        </td>
                    </tr>
                    <tr class="active">
                        <td>甲方公司名称:</td>
                        <td>
                            ${shopProject.companyname}
                        </td>
                        <td>工贸名称:</td>
                        <td>
                            ${shopProject.orgname}
                        </td>
                    </tr>
                    <tr class="active">
                        <td>工贸负责人:</td>
                        <td>
                            ${shopProject.orgidcharge}
                        </td>
                        <td>承接经销商:</td>
                        <td>
                            ${shopProject.distributor}
                        </td>
                    </tr>
                    <tr class="active">
                        <td>项目状态:</td>
                        <td>
                            ${shopProject.projectstate}
                        </td>
                        <td>节点名称:</td>
                        <td>
                            ${shopProject.nodename}
                        </td>
                    </tr>
                    </tbody>
                </table>
			</div>
		<sys:message content="${message}"/>
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
</body>
</html>