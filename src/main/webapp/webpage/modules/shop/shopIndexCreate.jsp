<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>创建索引</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp"%>
    <%@include file="/webpage/include/treeview.jsp" %>
    <%@include file="goodsCategoryList.js" %>
    <style>
        .jstree-icon:empty{
            vertical-align: middle;
            margin: 0 5px;
        }
        .fa-long-arrow-up,.fa-long-arrow-down{
            color: #3ca2e0;
            cursor: pointer;
        }
        .fa-long-arrow-up:hover,.fa-long-arrow-down:hover{
            color: #1c7ab3;
        }
    </style>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-body">
            <sys:message content="${message}"/>
            <!-- 搜索 -->
            <div class="accordion-group">
                <div id="collapseTwo" class="accordion-body collapse">
                    <div class="accordion-inner">
                        <form:form id="searchForm" modelAttribute="goodsCategoryList" class="form form-horizontal well clearfix">
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="label-item single-overflow pull-left" title="模型名称：">栏目名称：</label>
                                <input path="name" htmlEscape="false" maxlength="64"  class="form-control" id="search_q"/>
                            </div>
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
                <button id="edit" class="btn btn-success"  disabled onclick="tijiao()">
                    <i class="glyphicon glyphicon-edit"></i> 确认添加所选索引
                </button>
                <button id="del" class="btn btn-success"  disabled onclick="delindex()">
                    <i class="glyphicon glyphicon-edit"></i> 删除所选索引
                </button>
                <button id="all_select" class="btn btn-success" value="0"  onclick="all_select(this)">
                    <i class="glyphicon glyphicon-edit"></i>全选
                </button>

            </div>
            <div class="panel-heading">
                <table>
                    <tr>
                        <th class="panel-title">商品分类名称</th>
                    </tr>
                </table>
            </div>
            <form id="listForm" method="post" class="form-horizontal">
                <table id="treeTable" class="table table-striped table-bordered table-condensed">
                    <tr>

                    </tr>
                  <%--  <c:forEach items="${goodsCategoryList}" var="category">
                        <tr id="${category.id}" pId="${category.parent.id ne '1'?category.parent.id:'0'}">
                            <td class="pipei"><span>${category.name}</span></td>
                        </tr>
                    </c:forEach>--%>
                    <tr>
                      <div id="goodsCategoryjsTree"></div>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
</html>