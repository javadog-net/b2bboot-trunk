<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>招投标管理</title>
<meta name="decorator" content="ani" />
<!-- SUMMERNOTE -->
<%@include file="/webpage/include/summernote.jsp"%>
<style type="text/css">
#shenhe {
	width: 20%;
	margin-left: 400px;
}

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
	
	  function fanhui(){
		  var  id = $("#bidInfoId").val();
		  window.location = "${ctx}/bid/bidSignup/lists?bidInfoId=" + id;
	  }
</script>
</head>
<body>
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">报名信息详情
						 <a class="panelButton" style="float:right" onclick="fanhui()"><i class="ti-angle-left"></i> 返回</a>
						</h3>
					</div>
					<div class="panel-body">
						<form:form id="inputForm" modelAttribute="bidSignup" action="#"
							method="post" class="form-horizontal">
							<form:hidden path="id" />
							<form:hidden path="bidInfoId"/>
							<sys:message content="${message}" />
							<table border="0" style="margin: auto">
							   <tbody >
							<tr>
							
								<td align="right"  class="width-20"><label
									>公司名称：</label></td>
								<td class="width-35">
									<div class="col-sm-0">
										${fns:unescapeHtml(bidSignup.companyName)}</div>
								</td>
								<td  align="right" class="width-20 "><label
									>联系人：</label></td>
								<td class="width-35">
									<div class="col-sm-0">
										${fns:unescapeHtml(bidSignup.signupConcat)}</div>
								</td>
							</tr>
							<tr>
								<td  align="right" class="width-20"><label
									>职务：</label></td>
								<td class="width-35">
									<div class="col-sm-0">
										${fns:unescapeHtml(bidSignup.signupConcatJob)}</div>
								</td>
								<td   align="right"class="width-20 "><label
									>邮箱：</label></td>
								<td class="width-35">
									<div class="col-sm-0">
										${fns:unescapeHtml(bidSignup.signupConcatEmail)}</div>
								</td>
							</tr> 
							<tr>
								<td  align="right" class="width-20"><label>手机：</label></td>
								<td class="width-35">
									<div class="col-sm-0">
										${fns:unescapeHtml(bidSignup.signupConcatMobile)}</div>
								</td>
								<td   align="right"class="width-20 "><label>报名时间：</label></td>
								<td class="width-35">
									<div class="col-sm-0">
										${fns:unescapeHtml(bidSignup.addDate)}</div>
								</td>
							</tr> 
							<tr>
								<td  align="right" class="width-20">
								<label>留言内容：</label></td>
								<td  colspan="3"  width="400">
								${fns:unescapeHtml(bidSignup.message)}
								</td>
							</tr> 
							</tbody>
							</table  > 
							</tbody>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>