<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
	<meta name="decorator" content="ani"/>
	<!-- SUMMERNOTE -->
	<%@include file="/webpage/include/summernote.jsp" %>
	<link href="${ctxStatic}/plugin/BootstrapColorpicker/css/bootstrap-colorpicker.css" rel="stylesheet" type="text/css"/>
	<script src="${ctxStatic}/plugin/BootstrapColorpicker/js/bootstrap-colorpicker.js" type="text/javascript"></script>
	<style>
		html,body{
			background-color: #FFF;
		}
		.wrapper-content{
			padding: 0px;
		}
		.tabbar{

		}
		.tabbar ul{
			display: flex;
			padding: 0px;
			border-bottom: 1px solid #CCC;
		}
		.tabbar ul li{
			padding: 8px 12px;
			border-bottom: 1px solid #CCC;
			margin-bottom: -2px;
			background-color: #FFFFFF;
			cursor: pointer;
		}
		.tabbar ul li.active{
			border: 1px solid #CCC;
			border-bottom: none;
		}
		/*#contentBox>div{*/
		/*	 display: none;*/
		/* }*/
		#contentBox>div.active{
			display: block;
		}
		.col-sm-10 span label{
			margin: 0 10px;
		}
		.col-sm-10 .icheckbox_square-blue{
			margin-right: 10px;
		}
		.col-sm-10>label{
			margin-right: 5px;
		}
		.panel-primary .panel-heading>.panel-title{
			text-align: center;
			font-size: 18px;
		}
		input#changecolor {
			cursor: pointer;
			width: 20px;
			height: 20px;
			border: 1px solid #fff;
			border-radius: 4px;
			background-color:#fff;
			text-indent: 20px;;
		}
	</style>
	<script type="text/javascript">

		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					$("input[name='content']").val($('#content').summernote('code'));//取富文本的值
					var color = $("#color").val();
					$("#titleColor").val(color);
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

				//富文本初始化
			$('#content').summernote({
				height: 300,
                lang: 'zh-CN'
            });

			$('.tabbar ul li').click(function(){
				$(this).addClass('active').siblings().removeClass('active');
				var index = $(this).index();
				$("#contentBox>div").eq(index).show().siblings().hide();
			})
			$(".panelButton").click(function() {
				$(".addright",window.parent.document).hide();
				$(".bootstrap-table",window.parent.document).show();
			})
			// 基本实例化:

			$('#changecolor').colorpicker();

			$('#changecolor').on('change', function (event) {
				$('#changecolor').css('background-color', event.color.toString()).val('');
				$("#color").val(event.color.toString());
			});
			setTimeout(function(){
				$("#contentBox>div").hide();
				$("#contentBox>div").eq(0).show();
			},500)
		});
	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-body">
			<div class="tabbar">
				<ul>
					<li class="active">基本</li>
					<li>附件</li>
					<li>图片集</li>
					<li>视频</li>
					<li>高级</li>
					<li>关联商品</li>
				</ul>
			</div>
		<form:form id="inputForm" modelAttribute="info" action="${ctx}/cms/info/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
			<div id="contentBox">
<%--	基本			--%>
				<div class="active">
					<div class="form-group">
						<label class="col-sm-2 control-label"><font color="red">*</font>栏目：</label>
						<div class="col-sm-10">
							<sys:treeselect id="category" name="categoryId" value="${info.category.id}" labelName="category.name" labelValue="${info.category.name}"
											title="栏目" url="/cms/category/treeData" extId="" cssClass="form-control"/>
							<c:if test="${info.category.name=='产品讲坛'}">
							<div class="father-item">
								<form:radiobuttons path="familyLevel2" items="${fns:getDictList('familyLevel2')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks familyLevel2"/><br>
							</div>
								<div class="son-item">
									<form:radiobuttons path="familyLevel3" items="${fns:getDictList('familyLevel3')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks familyLevel3"/>
								</div>
							</c:if>

						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label"><font color="red">*</font>标题：</label>
						<div class="col-sm-10">
							<form:input path="title" htmlEscape="false"    class="form-control required"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">短标题：</label>
						<div class="col-sm-10">
							<form:input path="shortTitle" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">来源：</label>
						<div class="col-sm-10">
							<form:input path="source" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">作者：</label>
						<div class="col-sm-10">
							<form:input path="author" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">摘要：</label>
						<div class="col-sm-10">
							<form:textarea path="description" htmlEscape="false" rows="4" class="form-control "/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">类别：</label>
						<div class="col-sm-10">
							<form:select path="family" class="form-control ">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('family')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">内容：</label>
						<div class="col-sm-10">
							<input type="hidden" name="content"/>
							<div id="content">
									${fns:unescapeHtml(info.content)}
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">信息图片：</label>
						<div class="col-sm-10">
							<form:hidden id="image" path="image" htmlEscape="false" maxlength="500" class="form-control"/>
							<sys:webupload input="image" type="image" multiple="false"/>
						</div>
					</div>
				</div>
<%--	附件			--%>
				<div>
					<div class="form-group">
						<label class="col-sm-2 control-label">附件：</label>
						<div class="col-sm-10">
							<form:hidden id="attchs" path="attchs" htmlEscape="false" maxlength="500" class="form-control"/>
							<sys:webupload input="attchs" type="file" multiple="true"/>
						</div>
					</div>
				</div>
<%--	图片集			--%>
	<div>
		<div class="form-group">
			<label class="col-sm-2 control-label">图片集：</label>
			<div class="col-sm-10">
				<form:hidden id="images" path="images" htmlEscape="false" maxlength="500" class="form-control"/>
				<sys:webupload input="images" type="image" multiple="true"/>
			</div>
		</div>
	</div>
<%--	视频		 --%>
				<div>
					<div class="form-group">
						<label class="col-sm-2 control-label">视频文件地址：</label>
						<div class="col-sm-10">
							<form:hidden id="video" path="video" htmlEscape="false" maxlength="500" class="form-control"/>
							<sys:webupload input="video" type="file" multiple="true"/>
						</div>
					</div>
				</div>
<%--    高级		--%>
				<div>
					<div class="form-group">
						<label class="col-sm-2 control-label">内容模板：</label>
						<div class="col-sm-10">
							<form:select path="contentTemplate" value="${info.contentTemplate}"  class="form-control ">
								<form:option value="">--请选择--</form:option>
								<form:options items="${contentList}" htmlEscape="false"></form:options>
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">标题颜色：</label>
						<div class="col-sm-10">
							<input id="changecolor" type="text"
								<c:choose>
									<c:when test="${info.titleColor != null}">
										style="background-color: ${info.titleColor }"
									</c:when>
									<c:otherwise>
										style="background-color:#ccc"
									</c:otherwise>
								</c:choose>
							/>
							<input id="color" type="hidden"  value="${info.titleColor}">
							<form:hidden path="titleColor" htmlEscape="false" class="form-control"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">标题是否加粗：</label>
						<div class="col-sm-10">
							<form:radiobuttons path="titleBlod" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">关键词：</label>
						<div class="col-sm-10">
							<form:input path="keywords" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">推荐位：</label>
						<div class="col-sm-10">
							<sys:checkbox id="posids" name="posids" items="${positionList}" values="" cssClass="i-checks "/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">发布状态：</label>
						<div class="col-sm-10">
							<sys:checkbox id="status" name="status" items="${fns:getDictList('info_status')}" values="${info.status}" cssClass="i-checks "/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">是否转向连接：</label>
						<div class="col-sm-10">
							<form:radiobuttons path="isLink" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">是否热点：</label>
						<div class="col-sm-10">
							<form:radiobuttons path="isHot" id="isSetDefult"  items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks isHot"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">是否置顶：</label>
						<div class="col-sm-10">
							<form:radiobuttons path="isTop"  id="isSetDefult" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks isTop"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">是否推荐：</label>
						<div class="col-sm-10">
							<form:radiobuttons path="isRecommend" id="isSetDefult" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks isRecommend"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">转向链接地址：</label>
						<div class="col-sm-10">
							<form:input path="url" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">发布至：</label>
						<div class="col-sm-10">
							<form:checkboxes path="releaseType" items="${fns:getDictList('release_type')}"
											 itemLabel="label" itemValue="value" htmlEscape="false"
											 class="i-checks"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">页面标识：</label>
						<div class="col-sm-10">
							<form:input path="pageMark" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">生成的文件名称：</label>
						<div class="col-sm-10">
							<form:input path="fileName" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">是否允许评论：</label>
						<div class="col-sm-10">
							<form:radiobuttons path="allowComment"  items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks demo"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">关键字：</label>
						<div class="col-sm-10">
							<form:input path="tdkKeywords" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">标题：</label>
						<div class="col-sm-10">
							<form:input path="tdkTitle" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">描述：</label>
						<div class="col-sm-10">
							<form:textarea path="tdkDescp" htmlEscape="false" rows="4"    class="form-control "/>
						</div>
					</div>

				</div>
				<%--	关联商品		 --%>
	<div class="tab-pane" id="infoproduct">
		<div class="widget-content nopadding">
			<table class="table-condensed table table-bordered table-striped with-check table-hover" >
				<thead>
				<tr>
					<th><input type="checkbox" id="title-table-checkbox" iewrap="false"  name="title-table-checkbox" /></th>
					<th style="min-width:200px">商品名称</th>
					<th >排序</th>
				</tr>
				</thead>
				<tbody id="infoproductTbody">
				<input type="hidden" name="delOldProduct" id="delOldProduct"/>
				<c:forEach var="bean" items="${infoProductList}" varStatus="status">
					<tr id="infoproducttr${bean.id }">
						<td>
							<input type="checkbox" iewrap="false"  name="infoproducts" value="${bean.id }"/>
						</td>
						<td>
							<input type='hidden' name='oldinfoproductsid${bean.id }' value='${bean.id }'>
							<input type='hidden' name='oldinfoproductsinfoid${bean.id }' value='${bean.infoId }'>
							<input type='hidden' name='oldproductid${bean.id }' value='${bean.productId }'>
							<input type='hidden' name='oldproductname${bean.id }' value='${bean.productName }'>
							<INPUT   type=text name="oldproductname${bean.id}" value="${bean.productName }" disabled="disabled" >
						</td>
						<td>
							<input type="text" name="oldorsort${bean.id }" style="width:60px" oninput=if(!isInt(value))value=''   onpropertychange=if(!isInt(value))execCommand('undo')
								   onkeyup=if(!isInt(value))execCommand('undo') onafterpaste=if(!isInt(value))execCommand('undo') value="${bean.sort }" />
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>

			<div class="navbar" style="width:99%">
				<div class="navbar-inner">
					<input type="button"  onclick="addProduct()" class="btn btn-primary" value="增加关联商品" />
					<input type="button"  onclick="delProduct()"  class="btn btn-danger" value="删除关联商品" />
				</div>
			</div>
		</div>
	</div>
			</div>
			<c:if test="${fns:hasPermission('cms:info:edit') || isAdd}">
				<div class="col-lg-3"></div>
				<div class="col-lg-6">
					<div class="form-group text-center">
						<div>
							<shiro:hasPermission name="cms:info:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
							<a href="${ctx}/cms?nodeId=${info.category.id}"><input id="btnCancel" class="btn" type="button" value="返 回"/></a>
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
<script type="text/javascript">
	//增加产品关联
	function addProduct(){
		var id = Math.uuidFast();
		var str="";
		str+="<tr id='infoproducttr"+id+"'>"
		str+="<td>"
		str+="<input id='infoproducts"+id+"' uniform='true' type='checkbox' iewrap='false'  name='infoproducts' value='"+id+"'/>"
		str+="</td>"
		str+="<input id='productid"+id+"' uniform='true' type='hidden' name='productid"+id+"'  value=''>"
		str+="<td>"
		str+="<input id='productname"+id+"' uniform='true' type='text' name='productname"+id+"' onclick='productSelect(id)' value=''>"
		str+="</td>"
		str+="<td>"
		str+="<input type='text' name='productordernum"+id+"' style='width:60px' oninput=if(!isInt(value))value=''   onpropertychange=if(!isInt(value))execCommand('undo')"
		str+="onkeyup=if(!isInt(value))execCommand('undo') onafterpaste=if(!isInt(value))execCommand('undo') />"
		str+="</td>"
		str+="</tr>"
		$("#infoproductTbody").append(str);
		 // if(!$.browser.msie){
			//ie下调用此方法后会导致文件提交不上去，所以只在非ie下调用
			$("#productname"+id).click();
		 // }
	}

	function productSelect(id){
		//jh.openDialog('选择商品','${ctx}/shop/storeGoods/selectInfoGoods.do?selectid='+id,'800px','500px');
		var open = layer.open({
			type: 2,
			title: '选择商品',
			content: '${ctx}/shop/storeGoods/selectInfoGoods.do?selectid='+id,
			maxmin: true,
			area: ['800px', '80%'],
			btn: ['确定', '取消'],
			yes: function (index, layero) {
				var res = window["layui-layer-iframe" + index].callbackdata();
				var arrIds = res.arrIds;
				var arrNames = res.arrNames;
				if(arrIds.length>1 || arrIds.length==0){
					jh.info("请选择一条信息操作!");
					return;
				}
				var size = $("#infoproductTbody").find("tr").size();
				$("#infoproductTbody").find("tr").eq(size-1).find("td").eq(1).find("input").val(arrNames);
				$("#infoproductTbody").find("tr").eq(size-1).attr("storeGoodId",arrIds);
				$("#infoproductTbody").find("tr").eq(size-1).find("td").eq(1).find("input").attr("name","productname"+arrIds);
				layer.close(open);
			}
		});
	}

	//删除关联商品
	function delProduct(){
		var objs=document.getElementsByName("infoproducts");
		var ids=new Array();
		if(objs!=null && objs.length>0){
			for(var i=0;i<objs.length;i++){
				if(objs[i].checked){
					ids[ids.length]=objs[i].value;
				}
			}
		}
		if(ids.length>0){
			for(var i=0;i<ids.length;i++){
				$("#infoproducttr"+ids[i]).remove();
				$("#delOldProduct").val($("#delOldProduct").val()+ids[i]+";");
			}
		}
	}
	$(function(){
		if("产品讲坛" == $("#categoryName").val()){
			$(".father-item").show();
			$(".son-item").show();
		}else{
			$('.familyLevel2').iCheck('uncheck');
			$(".father-item").hide();
			$(".son-item").hide();
		}
		if(!$("input:radio[name='isTop']").is(':checked')){
			$(".isTop").eq(1).iCheck('check');
		}
		if(!$("input:radio[name='isRecommend']").is(':checked')){
			$(".isRecommend").eq(1).iCheck('check');
		}
		if(!$("input:radio[name='isHot']").is(':checked')){
			$(".isHot").eq(1).iCheck('check');
		}
//
	})
	$('.familyLevel2').on('ifChecked', function(event){
		var val = $(this).val();
		if(val=="0") {
			//如果是产品经
			$(".son-item").show();
			$('.familyLevel3').eq(3).iCheck('check');
		}else{
			$('.familyLevel3').iCheck('uncheck');
			$(".son-item").hide();
		}
	});



	//回调方法
	function fcallback(name){
		//如果等于产品讲坛则展示
		if("产品讲坛" == name){
			$(".father-item").show();
			$(".son-item").show();
			$('.familyLevel2').eq(0).iCheck('check');
		}else{
			$(".father-item").hide();
			$(".son-item").hide();
		}
	}

</script>
</body>
</html>