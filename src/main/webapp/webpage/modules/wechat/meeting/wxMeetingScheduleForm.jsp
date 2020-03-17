<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>会议日程表管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		var validateForm;
		var $table; // 父页面table表格id
		var $topIndex;//弹出窗口的 index
		function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。

			if(validateForm.form()){
			  $table = table;
			  $topIndex = index;
			  jh.loading();
			  $("#inputForm").submit();
			  return true;
		  }

		  return false;
		}

		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					jh.post("${ctx}/wechat/wxMeetingSchedule/save",$('#inputForm').serialize(),function(data){
						if(data.success){
	                    	$table.bootstrapTable('refresh');
	                    	jh.success(data.msg);
	                    	jh.close($topIndex);//关闭dialog

	                    }else{
            	  			jh.error(data.msg);
	                    }
					})
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
			
	        $('#startTime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
	        $('#endTime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
	        $('#conferenceDay').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
	        var opt=$("#type").val();
			if(opt=='5'){
				$('#myType1').show();
				$('#myType').show();
			}else{
				$('#myType1').hide();
				$('#myType').hide();
			}
	
		});
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="wxMeetingSchedule" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
		   		<input  name="meetingId" type="hidden" value="${wxMeetingSchedule.meetingId}">
				<tr>
					<td class="width-15 active"><label class="pull-right">开始时间：</label></td>
					<td class="width-35">
						<p class="input-group">
							<div class='input-group form_datetime' id='startTime'>
			                    <input type='text'  name="startTime" class="form-control required"  value="<fmt:formatDate value="${wxMeetingSchedule.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>
			            </p>
					</td>
					<td class="width-15 active"><label class="pull-right">结束时间：</label></td>
					<td class="width-35">
						<p class="input-group">
							<div class='input-group form_datetime' id='endTime'>
			                    <input type='text'  name="endTime" class="form-control required"  value="<fmt:formatDate value="${wxMeetingSchedule.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>
			            </p>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">会议内容：</label></td>
					<td class="width-35">
						<form:textarea path="content" htmlEscape="false" rows="4"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">会议日期：</label></td>
					<td class="width-35">
						<p class="input-group">
							<div class='input-group form_datetime' id='conferenceDay'>
			                    <input type='text'  name="conferenceDay" class="form-control required"  value="<fmt:formatDate value="${wxMeetingSchedule.conferenceDay}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>
			            </p>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">内容类型：</label></td>
					<td class="width-35">
						<form:select path="type" class="form-control required">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('schedule_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active" id="myType1" style="display:none"><label class="pull-right" >自定义内容：</label></td>
		   			<td class="width-35" id="myType" style="display:none">
		   				<form:textarea path="myType" htmlEscape="false" rows="1"    class="form-control required"/>
		   			</td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>

<script>
$(function(){
	$("#type").change(function(){		
		var opt=$("#type").val();
		if(opt=='5'){
			$('#myType1').show();
			$('#myType').show();
		}else{
			$('#myType1').hide();
			$('#myType').hide();
		}
	})
})
</script>
</body>
</html>