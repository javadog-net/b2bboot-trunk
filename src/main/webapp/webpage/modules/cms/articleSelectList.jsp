<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>选择文章</title>
	<meta name="decorator" content="default"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			$("input[name=id]").each(function(){
				var cmsMainFrame = parent.cmsMainFrame;
				var	articleSelect = cmsMainFrame.articleSelect;
				for (var i=0; i<articleSelect.length; i++){
					if (articleSelect[i][0]==$(this).val()){
						this.checked = true;
					}
				}
				$(this).click(function(){
					var id = $(this).val(), title = $(this).attr("title");
					cmsMainFrame.articleSelectAddOrDel(id, title);
				});
			});
		});
		function view(href){
			jh.openDialogView('查看文章',href,'800','400');
			return false;
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
<div class="wrapper wrapper-content">
<div class="panel panel-primary">
	<div class="panel-body">
	<form:form id="searchForm" modelAttribute="article" action="${ctx}/cms/article/selectList" method="post" class="form form-horizontal well clearfix">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div class="col-xs-4 col-sm-4 col-md-4">
		<label>栏目：</label>
		<sys:treeselect id="category" name="category.id" value="${article.category.id}" labelName="category.name" labelValue="${article.category.name}"
					title="栏目" url="/cms/category/treeData" module="article" cssClass="form-control"/>
		</div>
		<div class="col-xs-4 col-sm-4 col-md-4">
		<label>标题：</label><form:input path="title" htmlEscape="false" maxlength="50" class="form-control"/>&nbsp;
		</div>
		<div class="col-xs-4 col-sm-4 col-md-4">
		<div style="margin-top:26px">
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
		</div>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th style="text-align:center;">选择</th><th>栏目</th><th>标题</th><th>权重</th><th>点击数</th><th>发布者</th><th>更新时间</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="article">
			<tr>
				<td style="text-align:center;"><input type="checkbox" name="id" value="${article.id}" title="${fns:abbr(article.title,40)}" /></td>
				<td><a href="javascript:" onclick="$('#categoryId').val('${article.category.id}');$('#categoryName').val('${article.category.name}');$('#searchForm').submit();return false;">${article.category.name}</a></td>
				<td><a href="${ctx}/cms/article/form?id=${article.id}" title="${article.title}" onclick="return view(this.href);">${fns:abbr(article.title,40)}</a></td>
				<td>${article.weight}</td>
				<td>${article.hits}</td>
				<td>${article.createBy.name}</td>
				<td><fmt:formatDate value="${article.updateDate}" type="both"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
${page}
	<div class="clearfix"></div>
</div>
</div>
</div>
</body>
</html>