<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>文章关联商品</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp"%>
    <%@include file="/webpage/include/treeview.jsp" %>
    <%@include file="goodsCategoryTreeList.js" %>
    <%@include file="infoGoods.js" %>
    <script type="text/javascript">

        var validateForm;
        var $table; // 父页面table表格id
        var $topIndex;//弹出窗口的 index
        function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
            if(validateForm.form()){
                $table = table;
                $topIndex = index;
                $("#inputForm").submit();
                return true;
            }

            return false;
        }
    </script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">店铺商品列表</h3>
        </div>
        <div class="panel-body">
            <div class="row">
                <div class="col-sm-4 col-md-3" >
                    <div class="form-group">
                        <div class="row">
                            <div class="col-sm-10" >
                                <div class="input-search">
                                    <button type="submit" class="input-search-btn">
                                        <i class="fa fa-search" aria-hidden="true"></i></button>
                                    <input   id="search_q" type="text" class="form-control input-sm" name="" placeholder="查找...">

                                </div>
                            </div>

                        </div>
                    </div>
                    <div id="goodsCategoryjsTree"></div>
                </div>
                <div  class="col-sm-8 col-md-9 animated fadeInRight">
                    <sys:message content="${message}"/>

                    <!-- 搜索 -->
                    <div class="accordion-group">
                        <div id="collapseTwo" class="accordion-body collapse">
                            <div class="accordion-inner">
                                <form:form id="searchForm" modelAttribute="storeGoods" class="form form-horizontal well clearfix">
                                    <div class="col-xs-12 col-sm-6 col-md-4">
                                        <label class="label-item single-overflow pull-left" title="店铺：">店铺：</label>
                                        <sys:gridselect url="${ctx}/shop/store/data" id="store" name="storeId" value="${storeGoods.storeId}" labelName="storeName" labelValue="${storeGoods.storeName}"
                                                        title="选择店铺" cssClass="form-control required" fieldLabels="店铺名称|等级" fieldKeys="storeName|grade" searchLabels="店铺名称" searchKeys="storeName"></sys:gridselect>
                                    </div>
                                   <%-- <div class="col-xs-12 col-sm-6 col-md-4">
                                        <label class="label-item single-overflow pull-left" title="产品编码：">产品编码：</label>
                                        <form:input path="code" htmlEscape="false" maxlength="32"  class=" form-control"/>
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-4">
                                        <label class="label-item single-overflow pull-left" title="产品名称：">产品名称：</label>
                                        <form:input path="name" htmlEscape="false" maxlength="64"  class=" form-control"/>
                                    </div>--%>
                                    <div class="col-xs-12 col-sm-6 col-md-4">
                                        <div style="margin-top:26px">
                                            <a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
                                            <a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                        </div>
                    </div>
                    <!-- 工具栏 -->
                    <div id="toolbar">
                        <a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
                            <i class="fa fa-search"></i> 检索
                        </a>
                    </div>

                    <!-- 表格 -->
                    <table id="infogoodsTable"   data-toolbar="#toolbar"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    //回调函数
    var callbackdata = function () {
        var arrIds = getIdSelections();
        var arrNames = getGoodsNameSelections();
        return {
            arrIds:arrIds,
            arrNames:arrNames
        };
    }
    //筛选选中id
    function getIdSelections() {
        return $.map($("#infogoodsTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
    //筛选选中name
    function getGoodsNameSelections() {
        return $.map($("#infogoodsTable").bootstrapTable('getSelections'), function (row) {
            return row.goodsName
        });
    }
</script>
</body>
</html>