<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/webpage/include/summernote.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
            if($("#link").val()){
                $('#linkBody').show();
                $('#url').attr("checked", true);
            }
			$("#title").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
                    if ($("#categoryId").val()==""){
                        $("#categoryName").focus();
                        jh.warning('请选择归属栏目');
                    }else{
                        jh.loading('正在提交，请稍等...');
                        form.submit();
                    }
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
			$('#weightDate').datetimepicker({
				 format: "YYYY-MM-DD"
		    });
			$('#createDate').datetimepicker({
				 format: "YYYY-MM-DD"
		    });
			
		});
	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
<div class="col-md-12">
<div class="panel panel-primary">
<div class="panel-body">
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/article/?category.id=${article.category.id}">文章列表</a></li>
		<li class="active"><a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章<shiro:hasPermission name="cms:article:edit">${not empty article.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:article:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="article" action="${ctx}/cms/article/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="form-group">
			<label class="col-sm-2 control-label">归属栏目:</label>
			<div class="col-sm-6">
                <sys:treeselect id="category" name="category.id" value="${article.category.id}" labelName="category.name" labelValue="${article.category.name}"
					title="栏目" url="/cms/category/treeData" module="article" notAllowSelectRoot="false" notAllowSelectParent="true" cssClass="form-control required"/>&nbsp;
           </div>
           <div class="col-sm-4">
                <span>
                    <input id="url" type="checkbox" onclick="if(this.checked){$('#linkBody').show()}else{$('#linkBody').hide()}$('#link').val()"><label for="url">外部链接</label>
                </span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">标题:</label>
			<div class="col-sm-8">
				<form:input path="title" htmlEscape="false" maxlength="200" class="form-control input-xxlarge measure-input required"/>
			</div>
			<div class="col-sm-2">
				<label class="col-sm-4 control-label">颜色:</label>
				<div class="col-sm-8">
				<form:select path="color" class="form-control input-mini">
					<form:option value="" label="默认"/>
					<form:options items="${fns:getDictList('color')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				</div>
			</div>
		</div>
        <div id="linkBody" class="form-group" style="display:none">
            <label class="col-sm-2 control-label">外部链接:</label>
            <div class="col-sm-10">
                <form:input path="link" htmlEscape="false" maxlength="200" class="form-control input-xlarge"/>
                <span class="help-inline">绝对或相对地址。</span>
            </div>
        </div>
		<div class="form-group">
			<label class="col-sm-2 control-label">关键字:</label>
			<div class="col-sm-10">
				<form:input path="keywords" htmlEscape="false" maxlength="200" class="form-control input-xlarge"/>
				<span class="help-inline">多个关键字，用空格分隔。</span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">权重:</label>
			<div class="col-sm-4">
				<div class="col-sm-4">
				<form:input path="weight" htmlEscape="false" maxlength="200" class="form-control input-mini required digits"/>&nbsp;
				</div>
				<div class="col-sm-8">
				<span>
					<input id="weightTop" type="checkbox" class="i-checks" onclick="$('#weight').val(this.checked?'999':'0')"><label for="weightTop">置顶</label>
				</span>
				</div>
			</div>
			<div class="col-sm-6">
				<label class="col-sm-6 control-label">过期时间：</label>
				<div class="col-sm-6">
				<div class='input-group date' id='weightDate' style="left: -10px;" >
                   <input type='text'  name="beginDate" class="form-control" value="${article.weightDate}" />
                   <span class="input-group-addon">
                       <span class="glyphicon glyphicon-calendar"></span>
                   </span>
				</div>	
				<span class="help-inline">数值越大排序越靠前，过期时间可为空，过期后取消置顶。</span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">摘要:</label>
			<div class="col-sm-10">
				<form:textarea path="description" htmlEscape="false" rows="4" maxlength="200" class="form-control input-xxlarge"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">缩略图:</label>
			<div class="col-sm-10">
                <input type="hidden" id="image" name="image" value="${article.imageSrc}" />
				<sys:webupload input="image" type="thumb" multiple="false" />
				<span><font color="red" size="1">图片建议尺寸 650px * 480px</font></span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">正文:</label>
			<div class="col-sm-10">
				<form:textarea id="content" htmlEscape="true" path="articleData.content" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">来源:</label>
			<div class="col-sm-10">
				<form:input path="articleData.copyfrom" htmlEscape="false" maxlength="200" class="form-control input-xlarge"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">相关文章:</label>
			<div class="col-sm-10">
				<form:hidden id="articleDataRelation" path="articleData.relation" htmlEscape="false" maxlength="200" class="form-control input-xlarge"/>
				<ol id="articleSelectList"></ol>
				<a id="relationButton" href="javascript:" class="btn">添加相关</a>
				<script type="text/javascript">
					var articleSelect = [];
					function articleSelectAddOrDel(id,title){
						var isExtents = false, index = 0;
						for (var i=0; i<articleSelect.length; i++){
							if (articleSelect[i][0]==id){
								isExtents = true;
								index = i;
							}
						}
						if(isExtents){
							articleSelect.splice(index,1);
						}else{
							articleSelect.push([id,title]);
						}
						articleSelectRefresh();
					}
					function articleSelectRefresh(){
						$("#articleDataRelation").val("");
						$("#articleSelectList").children().remove();
						for (var i=0; i<articleSelect.length; i++){
							$("#articleSelectList").append("<li>"+articleSelect[i][1]+"&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"articleSelectAddOrDel('"+articleSelect[i][0]+"','"+articleSelect[i][1]+"');\">×</a></li>");
							$("#articleDataRelation").val($("#articleDataRelation").val()+articleSelect[i][0]+",");
						}
					}
					$.getJSON("${ctx}/cms/article/findByIds",{ids:$("#articleDataRelation").val()},function(data){
						for (var i=0; i<data.length; i++){
							articleSelect.push([data[i][1],data[i][2]]);
						}
						articleSelectRefresh();
					});
					$("#relationButton").click(function(){
						jh.openParent({
						    type: 2, 
						    area: ['800px', '400px'],
						    title:"添加关联文章",
						    content:"${ctx}/cms/article/selectList?pageSize=8",
						    btn:['确定']	
						}); 
					});
				</script>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">是否允许评论:</label>
			<div class="col-sm-10">
				<form:radiobuttons path="articleData.allowComment" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">推荐位:</label>
			<div class="col-sm-10">
				<form:checkboxes path="posidList" items="${fns:getDictList('cms_posid')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">发布时间:</label>
			<div class="col-sm-10">
				<div class='input-group date' id='createDate' style="left: -10px;" >
                   <input type='text'  name="beginDate" class="form-control" value="${article.createDate}" />
                   <span class="input-group-addon">
                       <span class="glyphicon glyphicon-calendar"></span>
                   </span>
				</div>
			</div>
		</div>
		<shiro:hasPermission name="cms:article:audit">
			<div class="form-group">
				<label class="col-sm-2 control-label">发布状态:</label>
				<div class="col-sm-10">
					<form:radiobuttons path="delFlag" items="${fns:getDictList('cms_del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					<span class="help-inline"></span>
				</div>
			</div>
		</shiro:hasPermission>
		<shiro:hasPermission name="cms:category:edit">
            <div class="form-group">
                <label class="col-sm-2 control-label">自定义内容视图:</label>
                <div class="col-sm-10">
                      <form:select path="customContentView" class="form-control input-medium">
                          <form:option value="" label="默认视图"/>
                          <form:options items="${contentViewList}" htmlEscape="false"/>
                      </form:select>
                      <span class="help-inline">自定义内容视图名称必须以"${article_DEFAULT_TEMPLATE}"开始</span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">自定义视图参数:</label>
                <div class="col-sm-10">
                      <form:input path="viewConfig" htmlEscape="true" class="form-control"/>
                      <span class="help-inline">视图参数例如: {count:2, title_show:"yes"}</span>
                </div>
            </div>
		</shiro:hasPermission>
		<c:if test="${not empty article.id}">
			<div class="form-group">
				<label class="col-sm-2 control-label">查看评论:</label>
				<div class="col-sm-10">
					<input id="btnComment" class="btn" type="button" value="查看评论" onclick="viewComment('${ctx}/cms/comment/?module=article&contentId=${article.id}&status=0')"/>
					<script type="text/javascript">
						function viewComment(href){
							jh.openDialogView('查看评论',href,'800','400');
							return false;
						}
					</script>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="cms:article:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</div>
</div>
</div>
</div>
</div>
</body>
</html>