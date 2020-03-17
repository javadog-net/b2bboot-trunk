<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>商品类型管理</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp" %>
    <%@include file="/webpage/include/treeview.jsp" %>
    <%@include file="goodsClassList.js" %>
    <%@include file="goodsClassPropertiesList.js" %>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">商品类型列表</h3>
        </div>
        <div class="panel-body">
            <sys:message content="${message}"/>

            <div class="row">
                <div id="left" class="col-sm-12">
                    <!-- 搜索 -->
                    <div class="accordion-group">
                        <div id="collapseTwo" class="accordion-body collapse">
                            <div class="accordion-inner">
                                <form:form id="searchForm" modelAttribute="goodsClass"
                                           class="form form-horizontal well clearfix">
                                    <div class="col-xs-12 col-sm-6 col-md-4">
                                        <label class="label-item single-overflow pull-left" title="品类名称：">品类名称：</label>
                                        <form:input path="name" htmlEscape="false" maxlength="64"
                                                    class=" form-control"/>
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-4">
                                        <div style="margin-top:26px">
                                            <a id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i
                                                    class="fa fa-search"></i> 查询</a>
                                            <a id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i
                                                    class="fa fa-refresh"></i> 重置</a>
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                        </div>
                    </div>

                    <!-- 工具栏 -->
                    <div id="toolbar">
                            <shiro:hasPermission name="shop:goodsClass:add">
                                <a id="add" class="btn btn-primary" onclick="add()"><i class="glyphicon glyphicon-plus"></i> 新建</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="shop:goodsClass:edit">
                                <button id="edit" class="btn btn-success" disabled onclick="edit()">
                                    <i class="glyphicon glyphicon-edit"></i> 修改
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="shop:goodsClass:del">
                                <button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
                                    <i class="glyphicon glyphicon-remove"></i> 删除
                                </button>
                            </shiro:hasPermission>
                           <%-- <shiro:hasPermission name="shop:goodsClass:import">
                                <button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
                                <div id="importBox" class="hide">
                                        <form id="importForm" action="${ctx}/shop/goodsClass/import" method="post" enctype="multipart/form-data"
                                             style="padding-left:20px;text-align:center;" ><br/>
                                            <input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　


                                        </form>
                                </div>
                            </shiro:hasPermission>--%>
                                <a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
                                    <i class="fa fa-search"></i> 检索
                                </a>
                            </div>

                    <!-- 表格 -->
                    <table id="goodsClassTable" data-toolbar="#toolbar"></table>

                    <!-- context menu -->
                    <ul id="context-menu" class="dropdown-menu">
                        <shiro:hasPermission name="shop:goodsClass:edit">
                            <li data-item="edit"><a>编辑</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="shop:goodsClass:del">
                            <li data-item="delete"><a>删除</a></li>
                        </shiro:hasPermission>
                        <li data-item="action1"><a>取消</a></li>
                    </ul>
                </div>
                <div  id="right" class="panel panel-default col-sm-6" style="display:none">
                    <div class="panel-heading">
                        <h3 class="panel-title"><label>商品属性列表，所属品类: </label> <span class="inc-class"></span> <input
                                type="hidden" class="goodsClassId"></h3>
                    </div>
                    <div class="panel-body">
                        <!-- 搜索 -->
                        <div class="accordion-group">
                            <div id="collapseThree" class="accordion-body collapse">
                                <div class="accordion-inner">
                                   <form:form id="searchForm_Properties"
                                               class="form form-horizontal well clearfix">
                                      <%-- <div class="col-xs-12 col-sm-6 col-md-4">
                                            <label class="label-item single-overflow pull-left" title="商品品类：">商品品类：</label>
                                           <input type="text" maxlength="64" class=" form-control" name="classId">
                                        </div>--%>
                                        <div class="col-xs-12 col-sm-6 col-md-4">
                                            <label class="label-item single-overflow pull-left" title="属性名称：">属性名称：</label>
                                            <input type="text" maxlength="64" class=" form-control" name="name">
                                        </div>
                                        <div class="col-xs-12 col-sm-6 col-md-4">
                                            <label class="label-item single-overflow pull-left" title="属性值：">属性值：</label>
                                            <input type="text" maxlength="64" class=" form-control" name="value">
                                        </div>
                                        <div class="col-xs-12 col-sm-6 col-md-4">
                                            <div style="margin-top:26px">
                                                <a id="search-child" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i
                                                        class="fa fa-search"></i> 查询</a>
                                                <a id="reset-child" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i
                                                        class="fa fa-refresh"></i> 重置</a>
                                            </div>
                                        </div>
                                    </form:form>
                                </div>
                            </div>
                        </div>

                        <!-- 工具栏 -->
                        <div id="toolbar-child">
                            <shiro:hasPermission name="shop:goodsClassProperties:add">
                                <a id="add" class="btn btn-primary" onclick="addgoodsClassProperties()"><i class="glyphicon glyphicon-plus"></i> 新建</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="shop:goodsClassProperties:edit">
                                <button id="editgoodsClassProperties" class="btn btn-success" disabled onclick="editgoodsClassProperties()">
                                    <i class="glyphicon glyphicon-edit"></i> 修改
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="shop:goodsClassProperties:del">
                                <button id="removegoodsClassProperties" class="btn btn-danger" disabled onclick="deleteAllgoodsClassProperties()">
                                    <i class="glyphicon glyphicon-remove"></i> 删除
                                </button>
                            </shiro:hasPermission>
                            <a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2"
                               href="#collapseThree">
                                <i class="fa fa-search"></i> 检索
                            </a>
                        </div>

                        <!-- 表格 -->
                        <table id="goodsClassPropertiesTable" data-toolbar="#toolbar-child"></table>

                        <!-- context menu -->
                        <ul id="context-menu" class="dropdown-menu">
                            <shiro:hasPermission name="shop:goodsClassProperties:edit">
                                <li data-item="edit"><a>编辑</a></li>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="shop:goodsClassProperties:del">
                                <li data-item="delete"><a>删除</a></li>
                            </shiro:hasPermission>
                            <li data-item="action1"><a>取消</a></li>
                        </ul>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>