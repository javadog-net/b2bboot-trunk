<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>招投标管理</title>
<meta name="decorator" content="ani" />
<!-- SUMMERNOTE -->
<%@include file="/webpage/include/summernote.jsp"%>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#inputForm")
								.validate(
										{
											submitHandler : function(form) {
												jh.loading();
												debugger
												var projectIntro = $(
														'#projectIntro')
														.summernote('code')
												var projectContent = $(
														'#projectContent')
														.summernote('code')
												var applicationConditions = $(
														'#applicationConditions')
														.summernote('code')
														
														
												/* 		var code = $('#projectIntro').code(),
													  filteredContent = $(code).text().replace(/\s+/g, '');
														if(filteredContent.length > 0) {
													    // content is not empty
														} else {
													    // content is empty
														} */
							
												if ($('#projectIntro').summernote('isEmpty')) {
													jh.error("项目介绍不能为空");
													return false
												}
												
												if ($('#applicationConditions').summernote('isEmpty')) {
													jh.error("资格要求不能为空");
													return false
												}
												if ($('#applicationConditions').summernote('isEmpty')) {
													jh.error("项目内容不能为空");
													return false
												}
											
												if ($("#img").val() == "") {
													jh.error("请上传图片");
													return false;
												}
												$("input[name='projectIntro']")
														.val(
																$(
																		'#projectIntro')
																		.summernote(
																				'code'));//取富文本的值
												$(
														"input[name='projectContent']")
														.val(
																$(
																		'#projectContent')
																		.summernote(
																				'code'));//取富文本的值
												$(
														"input[name='applicationConditions']")
														.val(
																$(
																		'#applicationConditions')
																		.summernote(
																				'code'));//取富文本的值
												form.submit();
											},
											errorContainer : "#messageBox",
											errorPlacement : function(error,
													element) {
												$("#messageBox").text(
														"输入有误，请先更正。");
												if (element.is(":checkbox")
														|| element.is(":radio")
														|| element
																.parent()
																.is(
																		".input-append")) {
													error.appendTo(element
															.parent().parent());
												} else {
													error.insertAfter(element);
												}
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
			/* 			var markupStr ='';
						$('#projectIntro').summernote('code', markupStr);
						$('#applicationConditions').summernote('code', markupStr);
						$('#projectContent').summernote('code', markupStr); */
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
						<form:form id="inputForm" modelAttribute="bidInfo"
							action="${ctx}/bid/bidInfo/save" method="post"
							class="form-horizontal">
							<form:hidden path="id" />
							<sys:message content="${message}" />
							<div class="form-group">
								<input type="hidden" name="isValid" value="1" /> <input
									type="hidden" name="status" value="1" /> <input type="hidden"
									name="source" value="1" /> <input type="hidden" name="addUser"
									value="${purchaserAccount.id}" /> <label
									class="col-sm-2 control-label"><font color="red">*</font>公司名称：</label>
								<div class="col-sm-10">
									<form:input path="companyName" htmlEscape="false"
										value="${purchaser.companyName}" readonly="true"
										class="form-control " />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"><font color="red">*</font>封面LOG：</label>
								<div class="col-sm-10">
									<form:hidden id="img" path="projectLogo" htmlEscape="false"
										maxlength="255" class="form-control " />
									<sys:webupload input="img" type="image" multiple="false" />
									<%-- 						<form:input path="projectLogo" htmlEscape="false"    class="form-control "/>
 --%>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"><font color="red">*</font>行业：</label>
								<div class="col-sm-10">
									<form:select path="industry" htmlEscape="false"
										class="form-control required">
										<form:option value="" label="请选择行业" selected="true" />
										<form:options items="${industryList}" itemLabel="name"
											itemValue="id" htmlEscape="false" />
									</form:select>
								</div>
							</div>
							 <form:hidden path="provinceId"/>
           					 <form:hidden path="cityId"/> 
							<div class="form-group">
								<label class="col-sm-2 control-label"><font color="red">*</font>所在地区：</label>
								<div class="col-sm-10">
									<div class=" input-group" style="width: 100%;">
										<%--  <form:input path="areaInfo" htmlEscape="false"
											value="${purchaser.areaInfo}" readonly="true"
											class="form-control required" /> --%>
                      					 <form:input path="areaInfo" htmlEscape="false"  data-toggle="city-picker" provinceId="provinceId" cityId="cityId" class="form-control required" style="height: 34px;"/>

									</div>
								</div>
							</div>
							<!--             class="" data-toggle="city-picker" provinceId="provinceId" cityId="cityId" districtId="districtId" 
 -->
							<div class="form-group">
								<label class="col-sm-2 control-label"><font color="red">*</font>项目名称：</label>
								<div class="col-sm-10">
									<form:input path="projectName" htmlEscape="false"
										class="form-control required" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"><font color="red">*</font>项目预算：</label>
								<div class="col-sm-10">
									<form:input path="projectBudget" htmlEscape="false"
										class="form-control required isDigits " />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"><font color="red">*</font>项目介绍：</label>
								<div class="col-sm-10">
									<input type="hidden" name="projectIntro" />
									<div id="projectIntro">
										${fns:unescapeHtml(bidInfo.projectIntro)}</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"><font color="red">*</font>项目内容：</label>
								<div class="col-sm-10">
									<input type="hidden" name="projectContent" />
									<div id="projectContent">
										${fns:unescapeHtml(bidInfo.projectContent)}</div>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label"><font color="red">*</font>报名单位资格要求：</label>
								<div class="col-sm-10">
									<input type="hidden" name="applicationConditions" />
									<div id="applicationConditions">
										${fns:unescapeHtml(bidInfo.applicationConditions)}</div>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label"><font color="red">*</font>联系人：</label>
								<div class="col-sm-10">
									<form:input path="bidConcat" htmlEscape="false"
										class="form-control required" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label"><font color="red">*</font>手机：</label>
								<div class="col-sm-10">
									<form:input path="bidConcatPhone" htmlEscape="false"
										class="form-control required isMobile " />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"><font color="red">*</font>邮箱：</label>
								<div class="col-sm-10">
									<form:input path="bidConcatEmail" htmlEscape="false"
										class="form-control required email " />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"><font color="red">*</font>地址：</label>
								<div class="col-sm-10">
									<form:input path="bidConcatAddress" htmlEscape="false"
										class="form-control required" />
								</div>
							</div>
								<div class="col-lg-3"></div>
								<div class="col-lg-6">
									<div class="form-group text-center">
										<div>
											<button class="btn btn-primary btn-block btn-lg btn-parsley"
												data-loading-text="正在提交...">提 交</button>
										</div>
									</div>
								</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>