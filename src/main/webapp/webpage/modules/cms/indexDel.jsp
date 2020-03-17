<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>创建索引</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp"%>
    <%@include file="/webpage/include/treeview.jsp" %>
    <%@include file="categoryList.js" %>
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
        .condition{
            height: 190px;
            margin-top: 10px;
            /*border: thin solid #ddd;*/
        }
        .form-group label{
            padding: 0 10px;
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
                        <form:form id="searchForm" modelAttribute="category" class="form form-horizontal well clearfix">
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
            <div class="condition">
                <div class="form-group" style="height: 50px;line-height: 50px;margin-left: 7px;">
                    <label class="col-sm-2 control-label">是否所有栏目：</label>
                    <div class="col-sm-10">
                        <input type="radio" name="islm" class="i-checks required"><label>是</label>
                        <input type="radio" name="islm" class="i-checks required"><label>否</label>
                    </div>
                </div>
                <div class="form-group col-sm-12" style="height: 50px;line-height: 50px;">
                    <label class="col-sm-2 control-label" title="开始时间：">&nbsp;开始时间：</label>
                    <div class="col-sm-4">
                        <div class='input-group date' id='startDate' >
                            <input type='text'  name="startDate" class="form-control"  />
                            <span class="input-group-addon">
			                       <span class="glyphicon glyphicon-calendar"></span>
			                   </span>
                        </div>
                    </div>
                </div>
                <div class="form-group col-sm-12" style="height: 50px;line-height: 50px;">
                    <label class="col-sm-2 control-label" title="结束时间：">&nbsp;结束时间：</label>
                    <div class="col-sm-4">
                        <div class='input-group date' id='endDate' >
                            <input type='text'  name="endDate" class="form-control"  />
                            <span class="input-group-addon">
			                       <span class="glyphicon glyphicon-calendar"></span>
			                   </span>
                        </div>
                    </div>
                </div>
            </div>

            <form id="listForm" method="post" class="form-horizontal">
                <table id="treeTable" class="table table-striped table-bordered table-condensed">
                    <tr>
                        <th>栏目名称</th>
                    </tr>
                    <c:forEach items="${categoryList}" var="category">
                        <tr id="${category.id}" pId="${category.parent.id ne '1'?category.parent.id:'0'}">
                            <td class="pipei"><span>${category.name}</span></td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
            <!-- 工具栏 -->
            <div id="toolbar" style="margin: 10px">
                <shiro:hasPermission name="cms:category:edit">
                    <a id="add" class="btn btn-primary"onclick="create()"><i class="glyphicon glyphicon-plus"></i> 删除索引</a>
                </shiro:hasPermission>
            </div>
        </div>
    </div>
</div>
<script>
<%--  初始化日期 --%>
    $('#startDate').datetimepicker({
        format: "YYYY-MM-DD HH:mm:ss"
    });
    $('#endDate').datetimepicker({
        format: "YYYY-MM-DD HH:mm:ss"
    });
</script>
</body>
</html>