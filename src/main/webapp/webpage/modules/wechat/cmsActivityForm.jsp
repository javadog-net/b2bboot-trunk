<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>活动表管理</title>
<meta name="decorator" content="ani" />
<script type="text/javascript">
	$(function() {
		//复制表单组
		$("#addSaltIpGrpBtn").on("click", addSaltIpGrp);
	})
	
	$(document).ready(
			function() {
				$("#inputForm")
						.validate(
								{
									submitHandler : function(form) {
										jh.loading();
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									}
								});

				$('#startTime').datetimepicker({
					format : "YYYY-MM-DD HH:mm:ss"
				});
				
			}	
	);
/* 
	$(document)
			.ready(
					function() {
						var MaxInputs = 5; //maximum input boxes allowed
						var InputsWrapper = $("#InputsWrapper"); //Input boxes wrapper ID
						var AddButton = $("#AddMoreFileBox"); //Add button ID
						var x = InputsWrapper.length; //initlal text box count
						var FieldCount = 1; //to keep track of text box added
						$(AddButton)
								.click(
										function(e) //on add input button click
										{
											if (x <= MaxInputs) //max input box allowed
											{
												FieldCount++; //text box added increment
												//add input box
												$(InputsWrapper)
														.append(
																'<div><lable id="jiangxiang">奖项：</lable><input type="text" name="mytext[]" id="field_'+ FieldCount +'" value="Text '+ FieldCount +'"/><a href="#" rel="external nofollow" rel="external nofollow" rel="external nofollow" class="removeclass"><input type="button" value="删除"></a></div>');
												x++; //text box increment
											}
											return false;
										});

						$("body").on("click", ".removeclass", function(e) { //user click on remove text
							if (x > 1) {
								$(this).parent('div').remove(); //remove text box
								x--; //decrement textbox
								$("#jiangxiang")[0].html("22");
							}
							return false;
						})
					});
 */
 	function imgUpload(i){
	 $("input[name='filesType"+ i + "']").change(function(e){
			var that = $(this);
			   //图片上传
				//$('.dianpuzhuangxiu .addmokuai .block .shuoming1 .pic .pic1').children().remove();
		   		 // 根据这个 <input> 获取文件的 HTML5 js 对象
			    var files = event.target.files, file;        
			    if (files && files.length > 0) {
			      // 获取目前上传的文件
			      file = files[0];
			      // 来在控制台看看到底这个对象是什么
			      console.log(file);
			      // 那么我们可以做一下诸如文件大小校验的动作
			      if(file.size > 1024 * 1024 * 2) {
			        alert('图片大小不能超过 2MB!');
			        return false;
			      }
			      // !!!!!!
			      // 下面是关键的关键，通过这个 file 对象生成一个可用的图像 URL
			      // 获取 window 的 URL 工具     
			      var URL = window.URL || window.webkitURL;     
			      // 通过 file 生成目标 url
			      var imgURL = URL.createObjectURL(file);
			      // 用这个 URL 产生一个 <img> 将其显示出来
			      $('.img'+i).attr('src', imgURL);
			      // 使用下面这句可以在内存中释放对此 url 的伺服，跑了之后那个 URL 就无效了
			      	 //URL.revokeObjectURL(imgURL);
			       var formFile = new FormData();
		           formFile.append("file", files[0]); //加入文件对象
			      //上传图片
		           $.ajax({
		               url : "${fns:getBaseUrl()}/api/app/upload/uploadImage",
		               type : 'POST',
		               data : formFile,
		               // 告诉jQuery不要去处理发送的数据
		               processData : false,
		               // 告诉jQuery不要去设置Content-Type请求头
		               contentType : false,
		               async : true,
		               success : function(ret) {
		                   if(ret.success){
		                	   //上传成功
		                       var url = ret.url;
		                       that.next().next().attr("value",url);
		                       $("#imgSeccess"+i).css("display","block");
		                   }
		                   else{
		                	   $("#imgSeccess"+i).css("display","block");
		                   }
		               }
		           });
		   		 }		
		});
 	}
	function addSaltIpGrp() {
		//alert("点击事件ok！");
		var size = $(".saltIp").size();
		var i = size;/* ['+i+'] */	
		   html ='<div class="input-group saltIp" style="width:70%;padding:0 0 1px 150px;">'
				+'<table border="1" cellspacing="10">'
				+	'<tr>'
				+		'<th rowspan="2" class="input-group-addon jiang">奖项'+(i+1)+'</th>'
				+		'<th class="input-group-addon type">奖项类型</th>'
				+		'<th style="width:150px"><input type="text" class="form-control" id="saltIp" name="listcmsPrize['+ i+ '].prizeType" style="width:100%;" ></th>'
				+		'<th class="input-group-addon number">奖项数量</th>'
				+		'<th style="width:100px"><input type="text" class="form-control" id="apiPort" name="listcmsPrize['+ i+ '].prizeNum"></th>'
				+	'</tr>'
				+	'<tr style="height:90px">'
				+		'<th class="input-group-addon remarks">奖品图片</th>'
				+		'<th colspan="3"><div style="position:relative;"><input type="file" class="form-control" id="" onclick="imgUpload('+i+')" value="" name="filesType'+ i+ '" style="max-width:95px;">'
				+ 			'<img class="img'+i+'"/>'
				+ 			'<input type="hidden"  id="apiPort" value="" name="listcmsPrize['+ i + '].prizeName" style="max-width:90px;"><div id="apiPort"  name="listcmsPrize['+ i + '].prizeName" style="max-width:90px;"/><span id="imgSeccess'+ i + '" style="display:none;position:absolute;top:45px;left:20px;">上传成功</span><span id="imgFalse'+ i + '" style="display:none;position:absolute;top:45px;left:20px;">上传失败</span></div></th>'
				+	'</tr>'
				+'</table>'
				+'</div>';
				
				
		$("#xiaokuai").append(html);	
 }
	
	function del(obj) {
		$(obj).parent().parent().remove();
		var size = $(".saltIp").size(), arrSalt = $(".saltIp");
		for (var i = 0; i < size; i++) {
			$(".saltIp").eq(i).find(".jiang").text("奖项：" + (i+1));
		}
	}
</script>

<style type="text/css">

table{border:1px solid #ccc} 
.center {
	padding: 0 400px;
}

.center>label {
	width: auto !important;
}

.center>div {
	width: calc(100% - 90px);
}

#saltIpGroup {
	margin-bottom: 15px;
	margin-left: 150px;
}

div#peizhi, span#addSaltIpGrpBtn {
	border: 1px solid #00b7ee;
	background-color: #00b7ee;
	color: white;
	border-radius: 4px;
}

span#addSaltIpGrpBtn {
	display: block;
	height: 28px;
	width: 90px;
	font-size: 14px;
	text-align: center;
	line-height: 28px;
	margin-left: 15px;
	cursor: pointer;
}

#xiaokuai {
	margin-left: 150px;
}

.jiang {
	width: 70px;
	padding: 0;
	text-align: center;
	background-color: #00b7ee;
	color: white;
	border: 1px solid #00b7ee;
	border-right: 1px solid #ccc !important;
}

.type, .number, .remarks {
	background-color: #00b7ee;
	color: white;
	border: 1px solid #ccc;
}

.submit {
	margin-top: 50px;
}

.submit>button {
	background-color: #00b7ee;
	border-radius: 10px;
}

.webUploaderContainer,.statusBar {
	height: 110px !important;
	border: none !important;
	padding: 0 0 !important;
	line-height: 0 !important;	
	position: static !important;
}

</style>

</head>
<body>

	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<%-- <h3 class="panel-title">
							<a class="panelButton" href="${ctx}/wechat/cmsActivity"><i
								class="ti-angle-left"></i> 返回</a>
						</h3> --%>
					</div>
					<div class="panel-body">
						<form:form id="inputForm" modelAttribute="cmsActivity"
							action="${ctx}/wechat/cmsActivity/save" method="post"
							class="form-horizontal">
							<form:hidden path="id" />
							<sys:message content="${message}" />
							<%-- <div class="form-group">
								<label class="col-sm-2 control-label">活动banner：</label>
								<div class="col-sm-10">
									<form:hidden id="actvBanner" path="actvBanner"
										htmlEscape="false" maxlength="255" class="form-control" />
									<sys:webupload input="actvBanner" type="image" multiple="false" />
								</div>
							</div>
							 --%>
							 <div class="form-group center">
								<label class="col-sm-2 control-label">会议名称：</label>
								<div class="col-sm-10">
									<form:input path="meetingName" readonly="true"
										htmlEscape="false" class="form-control " />
								</div>


							</div>
							<div class="form-group center" hidden>
								<label class="col-sm-2 control-label">会议Id：</label>
								<div class="col-sm-10">
									<form:input path="meetingId" readonly="true" htmlEscape="false"
										class="form-control " />
								</div>
							</div>
							<div class="form-group center">
								<label class="col-sm-2 control-label">活动名称：</label>
								<div class="col-sm-10">
									<form:input path="actvName" htmlEscape="false"
										class="form-control " />
								</div>
							</div>


				<div class="form-group center">
					<label class="col-sm-2 control-label">开始时间：</label>
					<div class="col-sm-10">
						<p class="input-group">
							<div class='input-group form_datetime' id='startTime'>
			                    <input type='text'  name="startTime" class="form-control"  value="<fmt:formatDate value="${cmsActivity.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>						            
			            </p>
					</div>
				</div>
							<%-- <div class="form-group">
					<label class="col-sm-2 control-label">活动是否有效（0：是。1：否）：</label>
					<div class="col-sm-10">
						<form:input path="actvTab" htmlEscape="false"    class="form-control "/>
					</div>
				</div> --%>
							<div class="form-group center">
								<label class="col-sm-2 control-label">活动备注：</label>
								<div class="col-sm-10">
									<form:input path="remark" htmlEscape="false"
										class="form-control " />
								</div>
							</div>
			

							<div class="input-group" id="saltIpGroup">
								<!-- <div class="input-group-addon" id="peizhi">奖项设置:</div> -->
								<span id="addSaltIpGrpBtn">新增奖项设置</span>
							</div>
							<div id="xiaokuai">
								<c:if test="${!empty cmsActivity.listcmsPrize}">
									<c:forEach var="cmsPrize" items="${cmsActivity.listcmsPrize}"
										varStatus="i">
										<div class="input-group saltIp" style="width: 70%; padding: 0 0 1px 150px;">						
											<%-- <label class="input-group-addon jiang">奖项 ${i.count }</label>
											<label class="input-group-addon type">奖项类型:</label> 
											<input
												value="${cmsPrize.prizeType}" type="text"
												class="form-control" id="saltIp"
												name="listcmsPrize[${i.index}].prizeType"
												style="max-width: 145px;"> 
											<label class="input-group-addon number">奖项数量:</label> 
											<input
												type="text" class="form-control"
												value="${cmsPrize.prizeNum }" id="apiPort"
												name="listcmsPrize[${i.index}].prizeNum"
												style="max-width: 90px;"> 
											<label class="input-group-addon remarks">奖品图片:</label> 
											<input
												type="file" class="form-control"
												value="${cmsPrize.prizeName }" id=""
												name="listcmsPrize[${i.index}].prizeName"
												style="max-width: 90px;"> --%>
											<table border="1" cellspacing="10">
												<tr>
													<th rowspan="2" class="input-group-addon jiang">奖项 ${i.count }</th>
													<th class="input-group-addon type">奖项类型</th>
													<th style="width:150px"><input
															value="${cmsPrize.prizeType}" type="text"
															class="form-control" id="saltIp"
															name="listcmsPrize[${i.index}].prizeType"
															style="width:100%;"> </th>
													<th class="input-group-addon number">奖项数量</th>
													<th style="width:100px"><input
															type="text" class="form-control"
															value="${cmsPrize.prizeNum }" id="apiPort"
															name="listcmsPrize[${i.index}].prizeNum"
															style="width:100%;"> </th>
												</tr>
												<tr style="height:90px">
													<th class="input-group-addon remarks">奖品图片</th>
													<th colspan="3">
													<div style="position:relative;"><input
															type="file" class="form-control"
															value="${cmsPrize.prizeName }" id=""
															name="filesType${i.index}"
															style="max-width: 90px;" onclick="imgUpload(${i.index})">	
															<img class="img${i.index}" src="${fns:getBaseUrl()}/${cmsPrize.prizeName }"/>				
															<input type="hidden"  id="apiPort" value="${cmsPrize.prizeName }" name="listcmsPrize[${i.index}].prizeName" style="max-width:95px;">
															<span id="imgSeccess${i.index}" style="display:none;position:absolute;top:45px;left:20px;">上传成功</span><span id="imgFalse${i.index}" style="display:none;position:absolute;top:45px;left:20px;">上传失败</span>
													</div>
													</th>
												</tr>
											</table>	
												
										
											<%-- <div hidden>
						<input type="text" class="form-control" value="${cmsPrize.id }" id="apiPort" name="listcmsPrize[${i.index}].id" style="max-width:90px;">								
			            <input type="text" class="form-control" value="${cmsPrize.actvId }" id="apiPort" name="listcmsPrize[${i.index}].actvId" style="max-width:90px;">								
			            <input type="text" class="form-control" value="${cmsPrize.meetingId }" id="apiPort" name="listcmsPrize[${i.index}].meetingId" style="max-width:90px;">								
			            <input type="text" class="form-control" value="${cmsPrize.prizeTab }" id="apiPort" name="listcmsPrize[${i.index}].prizeTab" style="max-width:90px;">								
			            <input type="text" class="form-control" value="${cmsPrize.addTime }" id="apiPort" name="listcmsPrize[${i.index}].addTime" style="max-width:90px;">								
			        </div> --%>
										</div>
									</c:forEach>
								</c:if>
							</div>

							<c:if
								test="${fns:hasPermission('wechat:cmsActivity:edit') || isAdd}">
								<div class="col-lg-3"></div>
								<div class="col-lg-6">
									<div class="form-group text-center">
										<div class="submit">
											<button class="btn btn-primary btn-block btn-lg btn-parsley"
												data-loading-text="正在提交...">提 交</button>
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
	<style>
	img{
	height:80px}
	</style>
</body>


</html>