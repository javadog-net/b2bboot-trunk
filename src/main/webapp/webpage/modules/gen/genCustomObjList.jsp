<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>自定义对象管理</title>
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp" %>
    <script>
        $(document).ready(function () {
            $('#table').bootstrapTable({
                method: 'get',
                dataType: "json",
                showRefresh: true,
                showToggle: true,
                showColumns: true,
                showExport: true,
                showPaginationSwitch: true,
                minimumCountColumns: 2,
                striped: true,
                cache: false,
                pagination: true,
                sortOrder: "asc",
                pageNumber: 1,
                pageSize: 10,
                pageList: [10, 25, 50, 100],
                url: ctx + "/gen/genCustomObj/data",
                queryParams: function (params) {
                    var searchParam = $("#searchForm").serializeJSON();
                    searchParam.pageNo = params.limit === undefined ? "1" : params.offset / params.limit + 1;
                    searchParam.pageSize = params.limit === undefined ? -1 : params.limit;
                    searchParam.orderBy = params.sort === undefined ? "" : params.sort + " " + params.order;
                    return searchParam
                },
                sidePagination: "server",
                contextMenuTrigger: "right",
                contextMenuTriggerMobile: "press",
                contextMenu: '#context-menu',
                onContextMenuItem: function (row, e) {
                    if (e.data("item") == "edit") {
                        window.location = ctx + "/gen/genCustomObj/form?id=" + row.id
                    } else if (e.data("item") == "delete") {
                        jh.confirm('确认要删除该自定义对象记录吗？', function () {
                            jh.loading();
                            jh.get(ctx + "/gen/genCustomObj/delete?id=" + row.id, function (data) {
                                if (data.success) {
                                    $('#table').bootstrapTable('refresh');
                                    jh.success(data.msg)
                                } else {
                                    jh.error(data.msg)
                                }
                            })
                        })
                    }
                },
                onClickRow: function (row, e) {
                },
                columns: [{
                    checkbox: true
                }, {
                    field: 'label',
                    title: '标签',
                    sortable: true,
                    formatter: function (value, row, index) {
                        return "<a href='" + ctx + "/gen/genCustomObj/form?id=" + row.id + "'>" + value + "</a>"
                    }
                }, {
                    field: 'value',
                    title: '完整类名',
                    sortable: true
                }, {
                    field: 'tableName',
                    title: '物理表名',
                    sortable: true
                }, {
                    field: 'dataUrl',
                    title: '数据接口',
                    sortable: true
                }
                ]
            });
            if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
                $('#table').bootstrapTable("toggleView")
            }
            $('#table').on('check.bs.table uncheck.bs.table load-success.bs.table ' + 'check-all.bs.table uncheck-all.bs.table', function () {
                $('#remove').prop('disabled', !$('#table').bootstrapTable('getSelections').length);
                $('#edit').prop('disabled', $('#table').bootstrapTable('getSelections').length != 1)
            });
            $("#btnImport").click(function () {
                jh.open({
                    type: 1,
                    area: [500, 300],
                    title: "导入数据",
                    content: $("#importBox").html(),
                    btn: ['下载模板', '确定', '关闭'],
                    btn1: function (index, layero) {
                        window.location = ctx + '/gen/genCustomObj/import/template'
                    },
                    btn2: function (index, layero) {
                        var inputForm = top.$("#importForm");
                        var top_iframe = top.getActiveTab().attr("name");
                        inputForm.attr("target", top_iframe);
                        inputForm.onsubmit = function () {
                            jh.loading('  正在导入，请稍等...')
                        };
                        inputForm.submit();
                        jh.close(index)
                    },
                    btn3: function (index) {
                        jh.close(index)
                    }
                })
            });
            $("#search").click("click", function () {
                $('#table').bootstrapTable('refresh')
            });
            $("#reset").click("click", function () {
                $("#searchForm  input").val("");
                $("#searchForm  select").val("");
                $('#table').bootstrapTable('refresh')
            })
        });

        function getIdSelections() {
            return $.map($("#table").bootstrapTable('getSelections'), function (row) {
                return row.id
            })
        }

        function deleteAll() {
            jh.confirm('确认要删除该自定义对象记录吗？', function () {
                jh.loading();
                jh.get(ctx + "/gen/genCustomObj/deleteAll?ids=" + getIdSelections(), function (data) {
                    if (data.success) {
                        $('#table').bootstrapTable('refresh');
                        jh.success(data.msg)
                    } else {
                        jh.error(data.msg)
                    }
                })
            })
        };

        function edit() {
            window.location = ctx + "/gen/genCustomObj/form?id=" + getIdSelections()
        };
    </script>
</head>
<body id="" class="" style="">

<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">自定义对象列表</h3>
        </div>
        <div class="panel-body">
            <sys:message content="${message}"/>
            <!-- 搜索 -->
            <div class="accordion-group">
                <div id="collapseTwo" class="accordion-body collapse">
                    <div class="accordion-inner">
                        <form:form id="searchForm" modelAttribute="genCustomObj"
                                   class="form form-horizontal well clearfix">
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="label-item single-overflow pull-left" title="标签：">标签：</label>
                                <form:input path="label" htmlEscape="false" maxlength="64" class=" form-control"/>
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
                <shiro:hasPermission name="gen:genCustomObj:add">
                    <a id="add" class="btn btn-primary" href="${ctx}/gen/genCustomObj/form" title="自定义对象"><i
                            class="glyphicon glyphicon-plus"></i> 新建</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="gen:genCustomObj:edit">
                    <button id="edit" class="btn btn-success" disabled onclick="edit()">
                        <i class="glyphicon glyphicon-edit"></i> 修改
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="gen:genCustomObj:del">
                    <button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
                        <i class="glyphicon glyphicon-remove"></i> 删除
                    </button>
                </shiro:hasPermission>
                <a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2"
                   href="#collapseTwo">
                    <i class="fa fa-search"></i> 检索
                </a>
            </div>

            <!-- 表格 -->
            <table id="table" data-toolbar="#toolbar"></table>
            <!-- context menu -->
            <ul id="context-menu" class="dropdown-menu">
                <shiro:hasPermission name="gen:genCustomObj:edit">
                    <li data-item="edit"><a>编辑</a></li>
                </shiro:hasPermission>
                <shiro:hasPermission name="gen:genCustomObj:del">
                    <li data-item="delete"><a>删除</a></li>
                </shiro:hasPermission>
                <li data-item="action1"><a>取消</a></li>
            </ul>
        </div>
    </div>
</div>

</body>
</html>
