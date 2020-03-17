<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>商品表管理</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp" %>
    <%@include file="/webpage/include/treeview.jsp" %>
    <%@include file="goodsCategoryList.js" %>
    <%@include file="goodsList.js" %>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">商品表列表</h3>
        </div>
        <div class="panel-body">

                    <!-- 工具栏 -->
                    <div id="toolbar">

                        <button id="makeHtml" class="btn btn-success"  onclick="doSubmit()">
                            <i class="glyphicon glyphicon-plus"></i> 静态化所选栏目
                        </button>
                        <button id="makeHtml" class="btn btn-success"  onclick="allMakeHtml()">
                            <i class="glyphicon glyphicon-plus"></i> 全栏目静态化
                        </button>
                    </div>

                </div>
            </div>
    <div id="goodsCategoryjsTree"></div>
        </div>
    </div>
</div>
<script>

</script>

</body>
</html>