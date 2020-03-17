<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="decorator" content="ani" />
<!-- SUMMERNOTE -->
<%@include file="/webpage/include/summernote.jsp"%>
</head>

<body>
	<div class="wrapper wrapper-content">
		<div class="panel panel-primary">
			<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/tools/email/cmsEmailInfo"><i class="ti-angle-left"></i> 返回</a>
			</h3>
			</div>
			<input type="hidden" id="cityname" value="${emailInfo.cityname }">
			<input type="hidden" id="cityids" value="${emailInfo.cityid }">
			<div class="panel-body">
				<div class="row">

					<div class="col-sm-12 animated fadeInRight">
						<div class="mail-box-header">
							<div class="pull-right tooltip-demo">
								<!-- <button type="button" class="btn btn-white  btn-sm"
									onclick="sendLetter()">
									<i class="fa fa-pencil"></i> 保存邮件
								</button> -->
							</div>
							<h2>写邮件</h2>
						</div>

						<div class="mail-box">
              <sys:message content="${message}"/>	

							<div class="mail-body">
								<form:form id="inputForm" modelAttribute="cmsEmailInfo"
									action="${ctx}/tools/email/cmsEmailInfo/save" method="post"
									class="form-horizontal">
									<div class="form-group">
									<input  name="id" type="hidden" value="${cmsEmailInfo.id }" />
										<label class="col-sm-2 control-label"><font
											color="red">*</font>邮件类型：</label>
										<div class="col-sm-8">
											<select name="emailtype" id="emailtype">
												<option value=""
													<c:if test='${cmsEmailInfo.emailtype eq " " }'>selected="true"</c:if>>--请选择--</option>
												<option value="营销邮件"
													<c:if test='${cmsEmailInfo.emailtype eq "营销邮件"}'>selected="true"</c:if>>营销邮件</option>
												<option value="系统邮件"
													<c:if test='${cmsEmailInfo.emailtype eq "系统邮件"}'>selected="true"</c:if>>系统邮件</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"><font
											color="red">*</font>发送类型：</label> <label style="float: left"><input
											type="radio" onchange="changetype(0)" name="sendtype"
											value="1"
											<c:if test="${cmsEmailInfo==null || cmsEmailInfo.sendtype==null || cmsEmailInfo.sendtype==1}">checked="checked"</c:if>>群发</label>
										<label style="float: left"><input type="radio" id="oneto"
											onchange="changetype(1)" name="sendtype" value="0"
											<c:if test="${cmsEmailInfo.sendtype==0}">checked="checked"</c:if>>单发</label>

									</div>
									<div class="form-group" id="receptiontype" style="display:${'0'==cmsEmailInfo.sendtype?'none':'block' }">
										<label class="col-sm-2 control-label"><font
											color="red">*</font>收件人类型：</label>
										<div class="controls" style="margin-left: 190px;">
											<label style="float: left"><input type="radio"
												name="rectype" id="rectypeAll" value="全部" 
												${"全部"==cmsEmailInfo.rectype?"checked":"" }
												onclick="showType('全部');" />全部</label> <label style="float: left"><input
												type="radio" name="rectype" id="rectypeCity" value="按地域"
												${"按地域"==cmsEmailInfo.rectype?"checked":"" }
												onclick="showType('按地域');" />按地域</label> <label style="float: left"><input
												type="radio" name="rectype" id="rectypeDealer" value="经销商"
												${"经销商"==cmsEmailInfo.rectype?"checked":"" }
												onclick="showType('经销商');" />经销商</label> <label style="float: left"><input
												type="radio" name="rectype" id="rectypePurchaser"
												value="采购商" ${"采购商"==cmsEmailInfo.rectype?"checked":"" }
												onclick="showType('采购商');" />采购商</label> <label style="float: left"><input
												type="radio" name="rectype" class="rectypeIsOk"
												value="已激活用户" ${"已激活用户"==cmsEmailInfo.rectype?"checked":"" }
												onclick="showType('已激活用户');" />已激活用户</label> <label
												style="float: left"><input type="radio"
												name="rectype" class="rectypeIsOk" value="未激活用户"
												${"未激活用户"==cmsEmailInfo.rectype?"checked":"" }
												onclick="showType('未激活用户');" />未激活用户</label>
										</div>
									</div>
									<div id="provinceDiv" class="form-group" 
									style="display:<c:if test='${cmsEmailInfo==null||cmsEmailInfo.provincename==null || cmsEmailInfo.provincename==""}'>none</c:if>
                                      <c:if test='${cmsEmailInfo!=null&&cmsEmailInfo.provincename!=null && cmsEmailInfo.provincename!=""}'>block</c:if>">
										<label class="col-sm-2 control-label"><font
											color="red">*</font>请输入发送城市：</label>
										<div class="col-sm-8">
											<input type="text" id="areacity" name="provincename"
											placeholder="请输入城市名称或省份名称，例如：山东省 或输入 青岛市  （写全称省、市不可忽略）"
												class="form-control" value="${cmsEmailInfo.provincename }" />
										</div>
									</div>
									<div class="form-group" id="oneSendTO"
										style="display:<c:if test='${cmsEmailInfo==null||cmsEmailInfo.sendtype==null || cmsEmailInfo.sendtype==1}'>none</c:if>
                                      <c:if test='${cmsEmailInfo!=null&&cmsEmailInfo.sendtype!=null && cmsEmailInfo.sendtype==0}'>block</c:if>">
										<label class="col-sm-2 control-label"><font
											color="red">*</font>发送到：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="输入多个邮件地址请用英文符号;隔开"
												id="emailAddress" name="email" class="form-control"
												value="${cmsEmailInfo.email}">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"><font
											color="red">*</font>主题：</label>

										<div class="col-sm-8">
											<input type="text" id="title" name="title"
												class="form-control" value="${cmsEmailInfo.title }">
										</div>
									</div>
									 <input type="hidden" id="content" name="content">
									<!-- 内容 -->
									<div id="content">
			                        </div> 
			                        
			          <%--               <div class="form-group">
					<label class="col-sm-2 control-label">内容：</label>
					<div class="col-sm-10">
                        <input type="hidden" name="content"/>
						<div id="content">
                          ${fns:unescapeHtml(cmsEmailInfo.content)}
                        </div>
					</div>
				</div> --%>
			                        
								</form:form>
							</div>

							<div class="mail-text h-200">

								<div class="summernote">${fns:unescapeHtml(cmsEmailInfo.content)}</div>
								<div class="clearfix"></div>
							</div>
							<div class="mail-body text-right tooltip-demo">

								<button type="button" class="btn btn-primary  btn-sm"
									onclick="sendLetter()" style="display:<c:if test='${cmsEmailInfo.issendemail==0}'>block</c:if>
									<c:if test='${cmsEmailInfo.issendemail==1}'>none</c:if>" >
									<i class="fa fa-reply"></i> 保存
								</button> 
							</div>
							<div class="clearfix"></div>



						</div>

					</div>

				</div>
			</div>

		</div>
	</div>
	<script>
		$(document).ready(function() {
			//富文本初始化
			$('.summernote').summernote({
				height : 300,
				lang : 'zh-CN',
				focus : true
			});

		});
		
		function sendLetter() {
			if($("#emailtype").val()==""){
				jh.alert("请选择邮件类型");
				return;
			}  
			if ($("#title").val() == '') {
				jh.alert('标题不能为空！');
				return;
			}
			if($("#areacity").val()=="" && $("#rectypeCity").is(':checked')&&!$("#oneto").is(':checked')){
				jh.alert("收件人城市不能为空！");
				return;
			}
			if ($("#emailAddress").val() == ''&& $("#oneto").is(':checked')) {
				jh.alert('收件人不能为空！');
				return;
			}
			
			if($("#oneto").is(':checked')){
        		$("input:radio[name='rectype']").prop("checked","");
        		$("#rectypeAll").prop("checked",true);
        		$("#areacity").val("");
        	}
			
			$("#content").val($('.summernote').summernote("code"));
			jh.loading();
			$("#inputForm").submit();
		}

		function changetype(a) {
			if (a == '1') {
				$("#receptiontype").hide();
				$("#oneSendTO").show();
				$("#provinceDiv").hide();
			} else {
				$("#receptiontype").show();
				$("#oneSendTO").hide();
				if($("#rectypeCity").is(':checked')){
				  $("#provinceDiv").show();
				}
			}

		}

		/*页面加载就开始执行*/
		/* $(document).ready(function () {
			var cityname = $("#cityname").val();
			var cityid = $("#cityids").val();
			if(cityname!=""){
				var result ="<option selected=selected value="+cityid+">"+cityname+"</option>";
				 $("#cityid").html('');
		         $("#cityid").append(result);
			}
			$("#provinceid").change(function (){
		        $.get("/admin/shop/getCityByProvinceId/"+$("#provinceid").val()+".do",function(data){
		            if(data.status){
		            	$("#cityid").html('');
		                var result = "<option value=''>选择城市</option>";
		                $.each(data.obj,function(n,value){
		                   		 result +="<option value='"+value.id+"'>"+value.name+"</option>";
		                });
		                $("#cityid").html('');
		                $("#cityid").append(result);
		            }
		        },"json");
			});
		});
		function getCityList() {
			$.get("/admin/shop/getCityByProvinceId/" + $("#provinceid").val()
					+ ".do", function(data) {
				if (data.status) {
					$("#cityid").html('');
					var result = "";
					$.each(data.obj, function(n, value) {
						result += "<option value='"+value.id+"'>" + value.name
								+ "</option>";
					});
					$("#cityid").html('');
					$("#cityid").append(result);
				}
			}, "json");
		} */

		function showType(type) {
			if (type == "按地域") {
				$("#provinceDiv").show();
			} else {
				$("#provinceDiv").hide();
			}
		}
	</script>

</body>

</html>