<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@include file="wxBannerList.js" %>
<html>
<head>
	<title>banner管理</title>
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
			var opt=$("#type").val();
			if(opt=='1'){
				$('#lianjie').show();
				$('#feihuiyi').hide();
				$('#huiyi').show();
			}else if(opt=='2'){
				$('#lianjie').show();
				$('#huiyi').hide();
				$('#feihuiyi').show();
			}else{
				$('#huiyi').hide();
				$('#feihuiyi').hide();
			}
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
				<a class="panelButton" href="${ctx}/wechat/wxBanner"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="wxBanner" action="${ctx}/wechat/wxBanner/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">banner类型：</label>
					<div class="col-sm-10">
						<form:select path="classify" value="${wxBanner.classify}" readonly="true" htmlEscape="false" class="form-control dn required remo">
                        	<form:option value="" label="请选择banner分类" selected="true"/>
                        	<form:options items="${fns:getDictList('banner_classify')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
					</div>
				</div>
				<div class="form-group" id="oneone">
					<label class="col-sm-2 control-label" id="lableval">图片
						<c:if test="${wxBanner.classify=='1'}">(尺寸750*351)</c:if>:
					</label>
					<div class="col-sm-10">
						<form:hidden id="url" path="url" htmlEscape="false" maxlength="255" class="form-control"/>
						<sys:webupload input="url" type="image" multiple="false"/>
					</div>
				</div>
				<div class="form-group" id="ormeeting" >
					<label class="col-sm-2 control-label">是否会议：</label>
					<div class="col-sm-10">
						<form:select path="type" value="${wxBanner.type}" readonly="true" htmlEscape="false" class="form-control dn required remo"  >
                        	<form:option value="" label="请选择类型" selected="true"/>
                        	<form:options items="${fns:getDictList('isMeeting')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
					</div>
				</div>
			
				<div class="form-group" id="lianjie" hidden>
						<label class="col-sm-2 control-label" >链接：</label>
						<div class="col-sm-10 " id="feihuiyi">					
							 <form:input path="link" id="inputfei" htmlEscape="false" class="form-control"/>  
						</div>
					 <div class="col-sm-10 " id="huiyi" style="display:none">
					 <input type="hidden" value="${wxBanner.link}" >
						<select id="meetingName" name="link" class="form-control dn required remo">						
                        	<option value="" label="请选择会议"/>
                        	
                        	<c:forEach items="${meetinglist}" var="meet">                     	
                        		
	                        	<c:if test="${meet.id==wxBanner.link}">
	                        		<option value="${meet.id}" label="${meet.name}" selected="true" />
	                        	</c:if>
	                     <c:if test="${meet.id!=wxBanner.link}">
	                           <option value="${meet.id}" label="${meet.name}" />
	                     </c:if>
                        	</c:forEach>
                        	
                        </select>
					</div>
				</div>
		<c:if test="${fns:hasPermission('wechat:wxBanner:edit') || isAdd}">
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

<script>
$(function(){
	
	$("#type").change(function(){		
		var opt=$("#type").val();
		if(opt=='1'){
			//alert(opt);
			$('#lianjie').show();
			$('#feihuiyi').hide();
			$('#huiyi').show();
		}else if(opt=='2'){
			$('#lianjie').show();
			$('#huiyi').hide();
			$('#feihuiyi').show();
		}else{
			$('#huiyi').hide();
			$('#feihuiyi').hide();
		}
	});
	$("#classify").change(function(){		
		var opt=$("#classify").val();
	   // alert(opt);
	    if(opt=="2"||opt=="3"){
	    	$("#ormeeting").css("display", "none"); 
	    	$("#huiyi").css("display", "none");
	    	$("#oneone").css("display", "block");
	    	$("#lianjie").css("display", "block");
	    	$("#feihuiyi").css("display", "block");	    	
	    	$("#lableval").text("图片:");
	    }else if(opt=="4"||opt=="5"){
	    	$("#ormeeting").css("display", "block");
    	
	    	$("#lableval").text("图片(尺寸1920*1080):");
	    }else{
	    	$("#lianjie").css("display", "none");
	    	$("#ormeeting").css("display", "block"); 
	    	$("#oneone").css("display", "block");
	    	$("#lableval").text("图片(尺寸750*351):");
	    }
	});
})



/* $(function(){
	$("#huiyi").click(function(){
		  $.ajax({
		        type: "get",
		        url: "${ctx}/wechat/wxMeeting/bannerlist",
		        data: {},
		        dataType: "json",
		        success: function (data)
		        {
		        	var meeting = data.body.result;
		        	console.log(meeting);
		        	console.log(meeting.length);
		         	for(var i=0; i<meeting.length; i++){
		         		$("#meetingName").append("<option>" + meeting[i].name + "</option>");
		         	}
		        		
		        },
		        error:function() {
		            alert("请求失败！");
		        }
			  })		 
	})
}) */


</script>
</body>
</html>