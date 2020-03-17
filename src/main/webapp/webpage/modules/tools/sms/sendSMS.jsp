<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="decorator" content="ani"/>
</head>

<body>
    <div class="wrapper wrapper-content">
        <div class="row">

            <div class="col-sm-12 animated fadeInRight">
            <div class="panel panel-primary">
	            <div class="panel-heading">
					<h3 class="panel-title"> 
					<a class="panelButton" href="${ctx}/tools/sms/cmsShortMsg"><i class="ti-angle-left"></i> 返回</a>
				</h3>					
				</div>
			</div>
                <%-- <div class="mail-box-header">
                    <div class="pull-right tooltip-demo">
                      <!--  <button type="button" class="btn btn-white  btn-sm" onclick="sendSMS()"> <i class="fa fa-pencil"></i> 发送短信</button> -->
                       <a class="panelButton" href="${ctx}/tools/cmsShortMsg"><i class="ti-angle-left"></i> 返回</a>
                    </div>
                </div> --%>
               
                <div class="mail-box">


                    <div class="mail-body">
					<form:form id="inputForm" modelAttribute="mailBox" action="${ctx}/tools/sms/cmsShortMsg/save" method="post" class="form-horizontal">
                                  <div class="form-group">
									<input  name="id" type="hidden" value="${cmsShortMsg.id }" />
										<label class="col-sm-2 control-label"><font
											color="red">*</font>短信类型：</label>
										<div class="col-sm-8">
											<select name="msgtype" id="msgtype">
												<option value=""
													<c:if test='${cmsShortMsg.msgtype eq " " }'>selected="true"</c:if>>--请选择--</option>
												<option value="营销短信"
													<c:if test='${cmsShortMsg.msgtype eq "营销短信"}'>selected="true"</c:if>>营销短信</option>
												<option value="系统短信"
													<c:if test='${cmsShortMsg.msgtype eq "系统短信"}'>selected="true"</c:if>>系统短信</option>
											</select>
										</div>
									</div> 
									
									<div class="form-group">
										<label class="col-sm-2 control-label"><font
											color="red">*</font>发送类型：</label> <label style="float: left"><input
											type="radio" onchange="changetype(0)" name="sendtype"
											value="1"
											<c:if test="${cmsShortMsg==null || cmsShortMsg.sendtype==null || cmsShortMsg.sendtype==1}">checked="checked"</c:if>>群发</label>
										<label style="float: left"><input type="radio" id="oneto"
											onchange="changetype(1)" name="sendtype" value="0"
											<c:if test="${cmsShortMsg.sendtype==0}">checked="checked"</c:if>>单发</label>

									</div>
									
									
									<div class="form-group" id="receptiontype" style="display:${'0'==cmsShortMsg.sendtype?'none':'block' }">
										<label class="col-sm-2 control-label"><font
											color="red">*</font>收件人类型：</label>
										<div class="controls" style="margin-left: 190px;">
											<label style="float: left"><input type="radio"
												name="membertype" id="rectypeAll" value="全部" 
												${"全部"==cmsShortMsg.membertype?"checked":"" }
												onclick="showType('全部');" />全部</label> 
												<label style="float: left"><input type="radio" 
												name="membertype" id="rectypeCity" value="按地域"
												${"按地域"==cmsShortMsg.membertype?"checked":"" }
												onclick="showType('按地域');" />按地域</label> <label style="float: left"><input
												type="radio" name="membertype" id="rectypeDealer" value="经销商"
												${"经销商"==cmsShortMsg.membertype?"checked":"" }
												onclick="showType('经销商');" />经销商</label> <label style="float: left"><input
												type="radio" name="membertype" id="rectypePurchaser"
												value="采购商" ${"采购商"==cmsShortMsg.membertype?"checked":"" }
												onclick="showType('采购商');" />采购商</label>
										</div>
									</div> 
								<div id="provinceDiv" class="form-group" 
									style="display:<c:if test='${cmsShortMsg==null||cmsShortMsg.areaname==null || cmsShortMsg.areaname==""}'>none</c:if>
                                      <c:if test='${cmsShortMsg!=null&&cmsShortMsg.areaname!=null && cmsShortMsg.areaname!=""}'>block</c:if>">
										<label class="col-sm-2 control-label"><font
											color="red">*</font>请输入发送城市：</label>
										<div class="col-sm-8">
											<input type="text" id="areacity" name="areaname"
											placeholder="请输入城市名称或省份名称，例如：山东省 或输入 青岛市  （写全称省、市不可忽略）"
											class="form-control" value="${cmsShortMsg.areaname }" />
										</div>
								</div>
									
                           <div class="form-group" id="oneSendTO"
										style="display:<c:if test='${cmsShortMsg==null||cmsShortMsg.sendtype==null || cmsShortMsg.sendtype==1}'>none</c:if>
                                      <c:if test='${cmsShortMsg!=null&&cmsShortMsg.sendtype!=null && cmsShortMsg.sendtype==0}'>block</c:if>">
										<label class="col-sm-2 control-label"><font
											color="red">*</font>发送到：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="输入多个手机号请用英文符号,隔开"
												id="emailAddress" name="mobilephone" class="form-control"
												value="${cmsShortMsg.mobilephone}">
										</div>
							</div>
                            
                             <div class="form-group">
                                <label class="col-sm-2 control-label"><font color="red">*</font>短信内容：</label>

                                <div class="col-sm-8">
                                  	   <textarea id="content"   name="content"  class="form-control" required="" aria-required="true">
                                  	   ${cmsShortMsg.content}
                                  	   </textarea>
                                  	   
                                </div>
                            </div>
                            
                                  	 
 					</form:form>	
                    </div>

                    <div class="mail-body text-right tooltip-demo">
                    	
                    	 <button type="button" class="btn btn-primary  btn-sm"
                    	  onclick="sendSMS()" style="display:<c:if test='${cmsShortMsg.isshortmsg==0}'>block</c:if>
                    	  <c:if test='${cmsShortMsg.isshortmsg==1}'>none</c:if>" > <i class="fa fa-reply"></i> 保存</button>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
    </div>

    <script>
        $(document).ready(function () {
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });

        });
        function sendSMS(){
        	if($("#msgtype").val()==""){
				jh.alert("请选择短信类型");
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
        		$("input:radio[name='membertype']").prop("checked","");
        		$("#rectypeAll").prop("checked",true);
        		$("#areacity").val("");
        	}
        	
            /* if($("#tels").val()==''){
            	top.layer.alert('手机号不能为空！', {icon: 0});
            	return;
            } */
			var index = layer.load(1, {
			    shade: [0.3,'#fff'] //0.1透明度的白色背景
			});
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