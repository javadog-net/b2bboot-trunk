<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>会议表管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		var validateForm;
		var lAccount;//企业购登录账号
		var $table; // 父页面table表格id
		var $topIndex;//弹出窗口的 index
		
		
		var arr=$("#inputForm input,textarea");
		for(var i=0;i<arr.length;i++){
            var t=$(arr[i]).val();
            if(t==""){
				$(arr[i]).val('该数据不能为空！').css("color","RED");			
			}
		}
		if($("#address").val()=="该数据不能为空！"||$("#address").val()==""){
			$("#address").val('该数据不能为空！').css("color","RED");	
		}
		
		
		
		function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		
		
		for(var i=0;i<arr.length;i++){
             var t=$(arr[i]).val();
			 if(t=="该数据不能为空！"||t==""){
				 alert("请完善信息！")
				return false;
			} 
		}
		if($("#introduce").val().length>1024){
			alert("会议介绍内容过长！")
			return false;
		}
		if($("#guide").val().length>1024){
			alert("会务负责人内容过长！")
			return false;
		}
		
		
		if($("#address").val()=="该数据不能为空！"||$("#address").val()==""){
			alert("请完善信息！")
			return false;
		}	
		if(lAccount==""){
			return false;
		}
		if($("#img").val()=="" || $("#detailImgs").val()=="" || $("#bannerImgs").val()==""){
			alert("请上传图片！")
			return false;
		}
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
					jh.post("${ctx}/wechat/wxMeeting/save",$('#inputForm').serialize(),function(data){
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
	        $('#closingUp').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
            document.getElementById("map").onfocus=function(){
                jh.openDialogMap('选择地点', ctx + '/wechat/wxMeeting/goBmap', '800px', '700px',$("#map"));
            }
             $("#inputForm input,textarea").blur(function(){
				  var val = $(this).val();
				  if(val.length==0){
					  $(this).val('该数据不能为空！').css("color","RED");
				  }
				});
			  $("#inputForm input,textarea").focus(function(){
				  var val = $(this).val();
				  if(val=="该数据不能为空！"){
					  $(this).val('').css("color","black");
				  }
				}); 

			  $(".form-control").blur(function () {
				     $.ajax({
				    	data: {
				    		account:$("#originator").val()
				    	},
				        type : "POST",
				        url : "${ctx}/wechat/wxMeeting/epLoginAccount",
				        dataType : "json",
				        success : function (obj) {
				           if($("#originator").val()=="" || $("#originator").val()=="该数据不能为空！"){
				        	   lAccount="";
				        	   $('.yanzheng').html("请输入正确的企业购账户！").css("color","black");
				           }else if(!obj.success){
				        	   lAccount="";
				        	   $('.yanzheng').html("企业购用户不合法！").css("color","red");
				           }else{
				        	   lAccount=obj.result;
				        	   $('.yanzheng').html("该账户可以使用！").css("color","green");  
				           }
				        }
				    }); 
				    
				});
			  
			  
            
		});
		
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
			$(list+idx).find(".form_datetime").each(function(){
				 $(this).datetimepicker({
					 format: "YYYY-MM-DD HH:mm:ss"
			    });
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}





	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="wxMeeting" action="${ctx}/wechat/wxMeeting/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">会议名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">会议地点：</label></td>
					<td class="width-35">
						<form:input path="site" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">会议开始时间：</label></td>
					<td class="width-35">
						<p class="input-group">
							<div class='input-group form_datetime' id='startTime'>
			                    <input type='text'  name="startTime" class="form-control "  value="<fmt:formatDate value="${wxMeeting.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>						            
			            </p>
					</td>
					<td class="width-15 active"><label class="pull-right">会议结束时间：</label></td>
					<td class="width-35">
						<p class="input-group">
							<div class='input-group form_datetime' id='endTime'>
			                    <input type='text'  name="endTime" class="form-control "  value="<fmt:formatDate value="${wxMeeting.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>						            
			            </p>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">会议介绍：</label></td>
					<td class="width-35">
						<form:textarea path="introduce" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">会务负责人：</label></td>
					<td class="width-35">
						<form:textarea path="guide" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">会议地址：</label></td>
					<td class="width-35">
						<form:input path="address" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">会议地图：</label></td>
					<td class="width-35">
						<form:input path="map" htmlEscape="false" disabled="disabled" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">企业购登录账号：</label></td>
					<td class="width-35">
						<form:input path="originator" htmlEscape="false"    class="form-control"/>
						<p class="yanzheng"></p>
					</td>
					<td class="width-15 active"><label class="pull-right">封面图(尺寸400*400px)：</label></td>
					<td class="width-35">
						<form:hidden id="img" path="img" htmlEscape="false" maxlength="255" class="form-control"/>
						<sys:webupload input="img" type="image" multiple="false"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">会议详情图片(尺寸800*400px)：</label></td>
					<td class="width-35">
						<form:hidden id="detailImgs" path="detailImgs" htmlEscape="false" maxlength="255" class="form-control"/>
						<sys:webupload input="detailImgs" type="image" multiple="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right">会议banner图片(尺寸800*400px)：</label></td>
					<td class="width-35">
						<form:hidden id="bannerImgs" path="bannerImgs" htmlEscape="false" maxlength="255" class="form-control"/>
						<sys:webupload input="bannerImgs" type="image" multiple="false"/>
					</td>
				</tr>
		 	</tbody>
		</table>
<%--		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">会议日程表：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane fade in  active">
			<a class="btn btn-white btn-sm" onclick="addRow('#wxMeetingScheduleList', wxMeetingScheduleRowIdx, wxMeetingScheduleTpl);wxMeetingScheduleRowIdx = wxMeetingScheduleRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>开始时间</th>
						<th>结束时间</th>
						<th>活动内容</th>
						<th>会议日期</th>
						<th>内容类型</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="wxMeetingScheduleList">
				</tbody>
			</table>
			<script type="text/template" id="wxMeetingScheduleTpl">//<!--
				<tr id="wxMeetingScheduleList{{idx}}">
					<td class="hide">
						<input id="wxMeetingScheduleList{{idx}}_id" name="wxMeetingScheduleList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="wxMeetingScheduleList{{idx}}_delFlag" name="wxMeetingScheduleList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td>
						<div class='input-group form_datetime' id="wxMeetingScheduleList{{idx}}_startTime">
		                    <input type='text'  name="wxMeetingScheduleList[{{idx}}].startTime" class="form-control "  value="{{row.startTime}}"/>
		                    <span class="input-group-addon">
		                        <span class="glyphicon glyphicon-calendar"></span>
		                    </span>
		                </div>						            
					</td>
					
					
					<td>
						<div class='input-group form_datetime' id="wxMeetingScheduleList{{idx}}_endTime">
		                    <input type='text'  name="wxMeetingScheduleList[{{idx}}].endTime" class="form-control "  value="{{row.endTime}}"/>
		                    <span class="input-group-addon">
		                        <span class="glyphicon glyphicon-calendar"></span>
		                    </span>
		                </div>						            
					</td>
					
					
					<td>
						<textarea id="wxMeetingScheduleList{{idx}}_content" name="wxMeetingScheduleList[{{idx}}].content" rows="4"    class="form-control ">{{row.content}}</textarea>
					</td>
					
					
					<td>
						<div class='input-group form_datetime' id="wxMeetingScheduleList{{idx}}_conferenceDay">
		                    <input type='text'  name="wxMeetingScheduleList[{{idx}}].conferenceDay" class="form-control "  value="{{row.conferenceDay}}"/>
		                    <span class="input-group-addon">
		                        <span class="glyphicon glyphicon-calendar"></span>
		                    </span>
		                </div>						            
					</td>
					
					
					<td>
						<select id="wxMeetingScheduleList{{idx}}_type" name="wxMeetingScheduleList[{{idx}}].type" data-value="{{row.type}}" class="form-control m-b  ">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#wxMeetingScheduleList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var wxMeetingScheduleRowIdx = 0, wxMeetingScheduleTpl = $("#wxMeetingScheduleTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(wxMeeting.wxMeetingScheduleList)};
					for (var i=0; i<data.length; i++){
						addRow('#wxMeetingScheduleList', wxMeetingScheduleRowIdx, wxMeetingScheduleTpl, data[i]);
						wxMeetingScheduleRowIdx = wxMeetingScheduleRowIdx + 1;
					}
				});
			</script>
			</div>
		</div>
		</div>--%>
		</form:form>
</body>
</html>