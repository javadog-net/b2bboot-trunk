<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>招投标管理</title>
<meta name="decorator" content="ani" />
<!-- SUMMERNOTE -->
<%@include file="/webpage/include/summernote.jsp"%>
<%@include file="bidInfoParticular.js"%>
<style type="text/css">

form:input {
	outline: medium;
}
</style>
<script type="text/javascript">
	$(document).ready(
			function() {
				$("#inputForm").validate(
						{
							submitHandler : function(form) {
								jh.loading();
								$("input[name='projectIntro']").val(
										$('#projectIntro').summernote('code'));//取富文本的值
								$("input[name='projectContent']")
										.val(
												$('#projectContent')
														.summernote('code'));//取富文本的值
								$("input[name='applicationConditions']").val(
										$('#applicationConditions').summernote(
												'code'));//取富文本的值
								form.submit();
							},
							errorContainer : "#messageBox",
							errorPlacement : function(error, element) {
								$("#messageBox").text("输入有误，请先更正。");
								/* 	if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
										error.appendTo(element.parent().parent());
									} else {
										error.insertAfter(element);
									} */
							}
						});

				//富文本初始化
				$('#projectIntro').summernote({
					height : 200,
					lang : 'zh-CN'
				});
				//富文本初始化
				$('#applicationConditions').summernote({
					height : 200,
					lang : 'zh-CN'
				});
				//富文本初始化
				$('#projectContent').summernote({
					height : 200,
					lang : 'zh-CN'
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
							<a class="panelButton" href="${ctx}/bid/bidInfo"><i
								class="ti-angle-left"></i> 返回</a>
						</h3>
					</div>
					<div class="panel-body">
						<form:form id="inputForm" modelAttribute="bidInfo" action="#"
							method="post" class="form-horizontal">
							<form:hidden path="id" />
							<sys:message content="${message}" />
							<h3 class="panel-title">
								<label class="col-sm-0 control-label">招采项目概况：</label>
							</h3>
							<div class="form-group">
								<label class="col-sm-2 control-label">公司名称：</label>
								<div class="col-sm-10">
									<form:input path="companyName" htmlEscape="false"
										value="${fns:unescapeHtml(bidInfo.companyName)}"
										readonly="true" class="form-control"
										style=" height: 34px;border-left:0px;border-top:0px;border-right:0px;border-bottom:1px" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">所属行业：</label>
								<div class="col-sm-10">
									<form:input path="industry" htmlEscape="false"
										value="${fns:unescapeHtml(bidInfo.industry)}" readonly="true"
										class="form-control"
										style="height: 34px;border-left:0px;border-top:0px;border-right:0px;border-bottom:1px" />

								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">所属地区：</label>
								<div class="col-sm-10">
									<form:input path="provinceId" htmlEscape="false"
										value="${fns:unescapeHtml(bidInfo.provinceId)}"
										readonly="true" class="form-control"
										style="height: 34px;border-left:0px;border-top:0px;border-right:0px;border-bottom:1px" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">项目名称：</label>
								<div class="col-sm-10">
									<form:input path="projectName" htmlEscape="false"
										value="${fns:unescapeHtml(bidInfo.projectName)}"
										readonly="true" class="form-control"
										style="height: 34px;border-left:0px;border-top:0px;border-right:0px;border-bottom:1px" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">项目预算：</label>
								<div class="col-sm-10">
									<form:input path="projectBudget" htmlEscape="false"
										value="${fns:unescapeHtml(bidInfo.projectBudget)}"
										readonly="true" class="form-control"
										style="height: 34px;border-left:0px;border-top:0px;border-right:0px;border-bottom:1px" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">项目介绍：</label>
								<div class="col-sm-10">

									<input type="hidden" name="projectIntro" />
									<div id="projectIntro">
										${fns:unescapeHtml(bidInfo.projectIntro)}</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">项目内容：</label>
								<div class="col-sm-10">
									<input type="hidden" name="projectContent" />
									<div id="projectContent">
										${fns:unescapeHtml(bidInfo.projectIntro)}</div>

								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">报名单位资格要求：</label>
								<div class="col-sm-10">
									<input type="hidden" name="applicationConditions" />
									<div id="applicationConditions">
										${fns:unescapeHtml(bidInfo.applicationConditions)}</div>

								</div>
							</div>
							<h3 class="panel-title">
								<label class="col-sm-0 control-label">项目联系人：</label>
							</h3>
							<div class="form-group">
								<label class="col-sm-2 control-label">联系人：</label>
								<div class="col-sm-10">
									<form:input path="bidConcat" htmlEscape="false"
										value="${fns:unescapeHtml(bidInfo.bidConcat)}" readonly="true"
										class="form-control"
										style="height: 34px;border-left:0px;border-top:0px;border-right:0px;border-bottom:1px" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label">手机：</label>
								<div class="col-sm-10">
									<form:input path="bidConcatPhone" htmlEscape="false"
										value="${fns:unescapeHtml(bidInfo.bidConcatPhone)}"
										readonly="true" class="form-control"
										style="height: 34px;border-left:0px;border-top:0px;border-right:0px;border-bottom:1px" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">邮箱：</label>
								<div class="col-sm-10">
									<form:input path="bidConcatEmail" htmlEscape="false"
										value="${fns:unescapeHtml(bidInfo.bidConcatEmail)}"
										readonly="true" class="form-control"
										style="height: 34px;border-left:0px;border-top:0px;border-right:0px;border-bottom:1px" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">地址：</label>
								<div class="col-sm-10">
									<form:input path="bidConcatAddress" htmlEscape="false"
										value="${fns:unescapeHtml(bidInfo.bidConcatAddress)}"
										readonly="true" class="form-control"
										style="height: 34px;border-left:0px;border-top:0px;border-right:0px;border-bottom:1px" />
								</div>
							</div>
							<div id="shenheTitle">
							<h3 class="panel-title" >
								<label class="col-sm-0 control-label">审核记录：</label>
							</h3>
							<table border="0" style="margin-left: 100px">
								<tbody>
									<c:set var="record" value="${recordList}" />
									<c:forEach var="item" items="${record}">
									<div class="form-group">
								<label class="col-sm-2 control-label"><br></label>
							
							</div>
								<div class="form-group">
								<label class="col-sm-2 control-label">审核时间：</label>
								<div class="col-sm-10">
									<form:input path="" htmlEscape="false"
										value="${item.checkDate}"
										readonly="true" class="form-control"
										style="height: 34px;border-left:0px;border-top:0px;border-right:0px;border-bottom:1px" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">审核状态：</label>
								<div class="col-sm-10">
									<form:input path="" htmlEscape="false"
										value="${item.checkStatus}"
										readonly="true" class="form-control"
										style="height: 34px;border-left:0px;border-top:0px;border-right:0px;border-bottom:1px" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">审核备注：</label>
								<div class="col-sm-10">
									<form:input path="" htmlEscape="false"
										value="${item.checkRemark}"
										readonly="true" class="form-control"
										style="height: 34px;border-left:0px;border-top:0px;border-right:0px;border-bottom:1px" />
								</div>
								</div>
									</c:forEach>
								</tbody>
							</table>
							</div>
						</form:form>

						<div class="col-lg-6" style="width:100%">
							<div class="form-group text-center">
								<div id="shenhe">
									<button class="btn btn-primary btn-block btn-lg btn-parsley"
										onclick="audit()">审核</button>
								</div>
							</div>
						</div>
						<div id="auditBox" class="hide">
							<div style="margin: 10px;">
								<form id="auditForm">
									<input type="hidden" id="bidInfoId" name="bidInfoId">
									<div class="form-group">
										<label class="control-label">是否通过 ：</label>
										<div class="radio">
											<label class="radio-inline"> <input type="radio"
												name="checkStatus" id="auditState1" value="1"> 是
											</label> <label class="radio-inline"> <input type="radio"
												name="checkStatus" id="auditState2" value="2"> 否
											</label>
										</div>
									</div>
									<div id="auditDescDiv" class="form-group hide">
										<label class="control-label">原因：</label>
										<div>
											<textarea id="auditDesc" name="checkRemark"
												class="form-control " rows="4"></textarea>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//如果是预览  审核按钮隐藏
		var preview = ${preview};
		if (preview == 1) {
			$("#shenhe").css("display", "none");
		}else{
			$("#shenheTitle").css("display", "none");
		}
	</script>
</body>
</html>