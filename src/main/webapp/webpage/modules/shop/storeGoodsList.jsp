<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>商品管理</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp" %>
    <%@include file="/webpage/include/treeview.jsp" %>
    <%@include file="storeGoodsList.js" %>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">商品列表</h3>
        </div>
        <div class="panel-body">
            <sys:message content="${message}"/>

            <!-- 搜索 -->
            <div class="accordion-group">
                <div id="collapseTwo" class="accordion-body collapse">
                    <div class="accordion-inner">
                        <form:form id="searchForm" modelAttribute="storeGoods"
                                   class="form form-horizontal well clearfix">
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="label-item single-overflow pull-left" title="店铺：">店铺：</label>
                                <sys:gridselect url="${ctx}/shop/store/data" id="store" name="storeId" value="${storeGoods.storeId}" labelName="storeName" labelValue="${storeGoods.storeName}"
                                                title="选择店铺" cssClass="form-control required" fieldLabels="店铺名称|等级" fieldKeys="storeName|grade" searchLabels="店铺名称" searchKeys="storeName"></sys:gridselect>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="label-item single-overflow pull-left" title="产品编码：">产品编码：</label>
                                <form:input path="code" htmlEscape="false" maxlength="32" class=" form-control"/>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="label-item single-overflow pull-left" title="产品名称：">产品名称：</label>
                                <form:input path="goodsName" htmlEscape="false" maxlength="32" class=" form-control"/>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="label-item single-overflow pull-left" title="产品大类：">产品大类：</label>
                                <form:input path="categoryPid" htmlEscape="false" maxlength="64" class=" form-control"/>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="label-item single-overflow pull-left" title="产品子类：">产品子类：</label>
                                <form:input path="categoryId" htmlEscape="false" maxlength="64" class=" form-control"/>
                            </div>

                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <div class="form-group">
                                    <label class="label-item single-overflow pull-left"
                                           title="是否推荐：">&nbsp;是否推荐：</label>
                                    <div class="col-xs-12">
                                        <form:radiobuttons class="i-checks" path="isRecommend"
                                                           items="${fns:getDictList('yes_no')}" itemLabel="label"
                                                           itemValue="value" htmlEscape="false"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <div class="form-group">
                                    <label class="label-item single-overflow pull-left"
                                           title="是否促销：">&nbsp;是否促销：</label>
                                    <div class="col-xs-12">
                                        <form:radiobuttons class="i-checks" path="isPromotion"
                                                           items="${fns:getDictList('yes_no')}" itemLabel="label"
                                                           itemValue="value" htmlEscape="false"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <div class="form-group">
                                    <label class="label-item single-overflow pull-left"
                                           title="是否上架：">&nbsp;是否上架：</label>
                                    <div class="col-xs-12">
                                        <form:radiobuttons class="i-checks" path="isShelf"
                                                           items="${fns:getDictList('yes_no')}" itemLabel="label"
                                                           itemValue="value" htmlEscape="false"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <div class="form-group">
                                    <label class="label-item single-overflow pull-left"
                                           title="申请上架时间：">&nbsp;申请上架时间：</label>
                                    <div class="col-xs-12">
                                        <div class="col-xs-12 col-sm-5">
                                            <div class='input-group date' id='beginShelfTime' style="left: -10px;">
                                                <input type='text' name="beginShelfTime" class="form-control"/>
                                                <span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-1">
                                            ~
                                        </div>
                                        <div class="col-xs-12 col-sm-5">
                                            <div class='input-group date' id='endShelfTime' style="left: -10px;">
                                                <input type='text' name="endShelfTime" class="form-control"/>
                                                <span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
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
                <%--<shiro:hasPermission name="shop:storeGoods:add">
                    <a id="add" class="btn btn-primary" href="${ctx}/shop/storeGoods/form" title="商品"><i
                            class="glyphicon glyphicon-plus"></i> 新建</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="shop:storeGoods:edit">
                    <button id="edit" class="btn btn-success" disabled onclick="edit()">
                        <i class="glyphicon glyphicon-edit"></i> 修改
                    </button>
                </shiro:hasPermission>--%>
                <shiro:hasPermission name="shop:storeGoods:del">
                    <button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
                        <i class="glyphicon glyphicon-remove"></i> 注销
                    </button>
                </shiro:hasPermission>
               <%-- <shiro:hasPermission name="shop:storeGoods:import">
                    <button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
                    <div id="importBox" class="hide">
                        <form id="importForm" action="${ctx}/shop/storeGoods/import" method="post"
                              enctype="multipart/form-data"
                              style="padding-left:20px;text-align:center;"><br/>
                            <input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　


                        </form>
                    </div>
                </shiro:hasPermission>--%>
                <a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2"
                   href="#collapseTwo">
                    <i class="fa fa-search"></i> 检索
                </a>
            </div>

            <!-- 表格 -->
            <table id="storeGoodsTable" data-toolbar="#toolbar"></table>

            <!-- context menu -->
            <ul id="context-menu" class="dropdown-menu">
                <shiro:hasPermission name="shop:storeGoods:edit">
                    <li data-item="edit"><a>编辑</a></li>
                </shiro:hasPermission>
                <shiro:hasPermission name="shop:storeGoods:del">
                    <li data-item="delete"><a>删除</a></li>
                </shiro:hasPermission>
                <li data-item="action1"><a>取消</a></li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>