<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供应商管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {								
			$("#inputForm").validate({
				submitHandler: function(form){
					jh.loading();
					
					if($("#districtId :selected").val()!=''){
						var provinceName = $("#provinceId :selected").text();
						var cityName = $("#cityId :selected").text();
						var districtName = $("#districtId :selected").text();
						var area = '' + provinceName + '/' + cityName + '/'+ districtName +'';
						$("#areaInfo").val(area);
					}				
					var areacheck = [];//定义一个数组     
		            $('input[name="areacheck"]:checked').each(function(){
		            	areacheck.push($(this).val());
		            });					
					$("#undertakeArea").val(areacheck);
					
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
			
			$.get("${fns:getUrlone()}/api/common/getAreaInfo",
		            function(data) {
		                var obj = data.body.result;
		                var result = "<option value=''>请选择省份</option>";
		                $.each(obj, function (n, value) {
		                    result += "<option value='" + value.id + "'>" + value.cityName + "</option>";
		                });
		                $(".provinceId").empty();
		                $(".provinceId").append(result);
		                var pid = '${dealer.provinceId}';
		                if(pid){
		                    $(".provinceId").val(pid);
		                    var cid = '${dealer.cityId}';
		                    var did = '${dealer.districtId}';
		                    Tree(pid,$(".cityId"),cid);
		                    Tree(cid,$(".districtId"),did);
		                }
		            }, "json");
			
			function Tree(id,aim,childId){
	            $.get("${fns:getUrlone()}/api/common/getAreaInfo?areaId="+id,
	                function(data) {
	                    var obj = data.body.result;
	                    var result = "";
	                    $.each(obj, function (n, value) {
	                        result += "<option value='" + value.id + "'>" + value.cityName + "</option>";
	                    });
	                    aim.append(result);
	                    aim.val(childId);
	                }, "json");
	        }
			
			$(".provinceId").on("change",function(){
	            var id = $(this).val();
	            $.get("${fns:getUrlone()}/api/common/getAreaInfo?areaId="+id,
	                function(data) {
	                    var obj = data.body.result;
	                    var result = "<option value=''>请选择城市</option>";
	                    $.each(obj, function (n, value) {
	                        result += "<option value='" + value.id + "'>" + value.cityName + "</option>";
	                    });
	                    $(".cityId").empty();
	                    $(".districtId").html("<option value=''>请选择地区</option>");
	                    $(".cityId").append(result);
	                }, "json");
	        });
	        $(".cityId").on("change",function(){
	            var id = $(this).val();
	            $.get("${fns:getUrlone()}/api/common/getAreaInfo?areaId="+id,
	                function(data) {
	                    var obj = data.body.result;
	                    var result = "<option value=''>请选择地区</option>";
	                    $.each(obj, function (n, value) {
	                        result += "<option value='" + value.id + "'>" + value.cityName + "</option>";
	                    });
	                    $(".districtId").empty();
	                    $(".districtId").append(result);
	                }, "json");
	        });
	        			
		});
		
		 
	</script>
	<style>
		.cityselect select{
			width: 202px;
			height: 34px;
	   		padding: 6px 12px;
		    font-size: 14px;
		    line-height: 1.42857143;
		    color: #555555;
		    background-color: #fff;
		    background-image: none;
		    border: 1px solid #ccc;
		}
		.col-sm-10 label{
			margin: 0 20px 0 5px;
		}
		.col-sm-10>label{
			margin-bottom: 10px
		}
		input[type=checkbox] {
            width: 20px;
            height: 20px;
            margin-top: 0;
            background-color: #fff;            
            border: 1px solid #c9c9c9;
            border-radius: 2px;
            color: #fff;
            text-align: center;
            line-height: 20px;
            -webkit-appearance:none;
            -moz-appearance:none;
            -ms-appearance:none;
            -o-appearance:none;
            appearance:none;
            outline: none;
            margin-bottom: 10px;
        }

        input[type=checkbox]:hover {
            border-color: blue;
        }

        input[type=checkbox]:checked {
            color: #fff;
            background-color: blue;
            border: 1px solid blue;
        }

        input[type=checkbox]:after {
            content: "✔";
        }
	</style>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/shop/dealer/dealer"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="dealer" action="${ctx}/shop/dealer/dealer/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>供应商编码：</label>
					<div class="col-sm-10">
						<form:input path="companyCode" htmlEscape="false"    class="form-control required" readonly="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>供应商名称：</label>
					<div class="col-sm-10">
						<form:input path="companyName" htmlEscape="false"    class="form-control required" readonly="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">公司人数：</label>
					<div class="col-sm-10">
						<form:input path="companyNum" htmlEscape="false"    class="form-control " readonly="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>联系人：</label>
					<div class="col-sm-10">
						<form:input path="contacts" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">电话：</label>
					<div class="col-sm-10">
						<form:input path="tel" htmlEscape="false"    class="form-control  isPhone"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>手机：</label>
					<div class="col-sm-10">
						<form:input path="mobile" htmlEscape="false"    class="form-control required isMobile"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">邮箱：</label>
					<div class="col-sm-10">
						<form:input path="email" htmlEscape="false"    class="form-control  email"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">邮编：</label>
					<div class="col-sm-10">
						<form:input path="zipCode" htmlEscape="false"    class="form-control  isZipCode"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">所在地区：</label>
					<div class="col-sm-10 cityselect">
						<select class="provinceId" name="provinceId" id="provinceId" value="${dealer.provinceId}">
	                         <option value="">--请选择--</option>
	                     </select>
	                     <select class="cityId" name="cityId" id="cityId">
	                         <option value="">--请选择--</option>
	
	                     </select>
	                     <select class="districtId" name="districtId" id="districtId">
	                         <option value="">--请选择--</option>
	                     </select>
					</div>	
					<div class="col-sm-10" style="display:none">
						<form:input path="areaInfo" htmlEscape="false"   class="form-control "/>
					</div>			
				</div>
						
							
				<div class="form-group">
					<label class="col-sm-2 control-label">详细地址：</label>
					<div class="col-sm-10">
						<form:input path="detailAddress" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">渠道名称：</label>
					<div class="col-sm-10">
						<form:input path="channelName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">税码：</label>
					<div class="col-sm-10">
						<form:input path="taxCode" htmlEscape="false"    class="form-control " readonly="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">快捷通账号：</label>
					<div class="col-sm-10">
						<form:input path="kjtAccount" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">营业执照电子版：</label>
					<div class="col-sm-10">
						<form:hidden id="electronicUrl" path="electronicUrl" htmlEscape="false" maxlength="100" class="form-control"/>
						<sys:webupload input="electronicUrl" type="image" multiple="false"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">身份证电子版：</label>
					<div class="col-sm-10">
						<form:hidden id="idCardUrl" path="idCardUrl" htmlEscape="false" maxlength="100" class="form-control"/>
						<sys:webupload input="idCardUrl" type="image" multiple="false"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">法人姓名：</label>
					<div class="col-sm-10">
						<form:input path="legalPersonName" htmlEscape="false"    class="form-control" readonly="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">法人身份号：</label>
					<div class="col-sm-10">
						<form:input path="legalPersonIdCard" htmlEscape="false"    class="form-control " readonly="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">公司电话：</label>
					<div class="col-sm-10">
						<form:input path="companyTel" htmlEscape="false"    class="form-control  isPhone"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">开户许可证电子版：</label>
					<div class="col-sm-10">
						<form:hidden id="electronicLicense" path="electronicLicense" htmlEscape="false" maxlength="100" class="form-control"/>
						<sys:webupload input="electronicLicense" type="image" multiple="false"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">企业logo地址：</label>
					<div class="col-sm-10">
						<form:hidden id="logoUrl" path="logoUrl" htmlEscape="false" maxlength="100" class="form-control"/>
						<sys:webupload input="logoUrl" type="image" multiple="false"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">所属工贸：</label>
					<div class="col-sm-10">
                         <form:select path="gmId" class="form-control required gmId">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('industryAndTrade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<form:hidden id="gmName" path="gmName" htmlEscape="false" maxlength="100" class="form-control gmName"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>是否关闭 ：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="isClosed" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>是否开店 ：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="isStore" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>是否自营 ：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="isSelf" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">承接区域 ：</label>
					<div class="col-sm-10">
						<div class="area">
							<form:hidden id="undertakeArea" path="undertakeArea" htmlEscape="false"    class="form-control  undertakeArea"/>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">承接品类 ：</label>
					<div class="col-sm-10">
						<sys:checkbox id="underProduct" name="underProduct" items="${fns:getDictList('category')}" values="${dealer.underProduct}" cssClass="i-checks" />
					</div>
				</div>
				
	            <div class="form-group">
	                <label class="col-sm-2 control-label">审核状态 ：</label>
	                <div class="col-sm-10">
	                    <form:radiobuttons path="auditState" items="${fns:getDictList('audit_state')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks " disabled="true"/>
	                </div>
	            </div>
	            <div class="form-group">
	                <label class="col-sm-2 control-label">审核时间 ：</label>
	                <div class="col-sm-10">
	                    <form:input path="auditTime" htmlEscape="false"    readonly="true" class="form-control "/>
	                </div>
	            </div>
	            <div class="form-group">
	                <label class="col-sm-2 control-label">审核人 ：</label>
	                <div class="col-sm-10">
	                    <form:input path="auditor" htmlEscape="false"    readonly="true" class="form-control "/>
	                </div>
	            </div>
	            <div class="form-group">
	                <label class="col-sm-2 control-label">审核意见 ：</label>
	                <div class="col-sm-10">
	                    <form:input path="auditDesc" htmlEscape="false"  readonly="true"   class="form-control "/>
	                </div>
	            </div>
	            <div class="form-group">
					<label class="col-sm-2 control-label">承接品类是否是所有 ：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="underProductIstotal" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">行业类别 ：</label>
					<div class="col-sm-10">
						<form:input path="instruct" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">经销商积分 ：</label>
					<div class="col-sm-10">
						<form:input path="score" htmlEscape="false"    class="form-control digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>是否允许经销商开店 ：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="allowPop" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>是否允许抢派单 ：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="allowDispatch" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>是否允许需求报备 ：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="allowDemand" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</div>
				</div>
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label">经销商类型 ：</label>
					<div class="col-sm-10">
						<form:input path="dealerType" htmlEscape="false"    class="form-control "/>
					</div>
				</div> --%>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注 ：</label>
					<div class="col-sm-10">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</div>
				</div>
				
		<c:if test="${fns:hasPermission('shop:dealer:dealer:edit') || isAdd}">
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
				$("#gmId").on("change",function(){
					var orgName = $(this).find("option:selected").text();
					$("#gmName").val(orgName);
					var center = $("#gmName").val().slice(0, -2);
			    	$.get("${fns:getUrlone()}/api/process/getCityByCenter?centerName="+center,
			    	function(data) {
			    		$(".area").html("");
			    		var undertakeArea;
			    		for(var i=0; i<data.length; i++){
			    			$(".area").append(
					            "<label>"+
					                "<input id='area" + i +"' name='areacheck' type='checkbox' value='"+data[i]+"' >"+data[i]
			                 	+"</label>");
			    		}
			    	},"json");
			    		
				})
			})


			window.onload=function(){
				var centerName = '${dealer.gmName}';
				var lastname = centerName.substring(centerName.length-2,centerName.length);
				if(lastname=="工贸" || lastname=="中心"){
					centerName=centerName.slice(0, -2);
				}
		        $.get("${fns:getUrlone()}/api/process/getCityByCenter?centerName=" + centerName,
	            	function(data) {	        		
	            		var undertakeArea = '${dealer.undertakeArea}';            		
	            		for(var i=0; i<data.length; i++){
	            			if(-1 == undertakeArea.indexOf(data[i])){
	            				$(".area").append(
	    	        		            "<label>"+
	    	        		                "<input id='area" + i +"' name='areacheck' type='checkbox' value='"+data[i]+"' >"+data[i]
	    	                         	+"</label>");
	            			}else{
		            			$(".area").append(
		        		            "<label>"+
		        		                "<input id='area" + i +"' name='areacheck' type='checkbox' checked='checked' value='"+data[i]+"' >"+data[i]
		                         	+"</label>");
	            			}
	            		}
	            	},"json");
	        }

</script>	
</body>
</html>