<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>表单列表</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp" %>
    <script>
        var tableTypeJson = ${fns:toJson(fns:getDictList('table_type'))};
        var buttonText = ['<div class="btn-group" role="group">', <shiro:hasPermission name="gen:genTable:edit">'<button type="button" class="edit btn btn-info">修改 </button>', </shiro:hasPermission>'<shiro:hasPermission name="gen:genTable:del"><button type="button" class="remove btn btn-warning">移除</button>', '<button type="button" class="del btn btn-danger">删除</button>', </shiro:hasPermission><shiro:hasPermission name="gen:genTable:synchDb">'<button type="button" class="sync btn btn-success">同步数据库</button>', </shiro:hasPermission>'</div>'].join('');
        $(document).ready(function () {
            $('#table').bootstrapTable({
                method: 'get',
                dataType: "json",
                striped: true,
                cache: false,
                pagination: true,
                pageNumber: 1,
                pageSize: 10,
                pageList: [10, 25, 50, 100],
                url: ctx + "/gen/genTable/data",
                queryParams: function (params) {
                    var searchParam = $("#searchForm").serializeJSON();
                    searchParam.pageNo = params.limit === undefined ? "1" : params.offset / params.limit + 1;
                    searchParam.pageSize = params.limit === undefined ? -1 : params.limit;
                    searchParam.orderBy = params.sort === undefined ? " " : params.sort + " " + params.order;
                    return searchParam
                },
                sidePagination: "server",
                contextMenuTrigger: "right",
                contextMenuTriggerMobile: "press",
                contextMenu: '#context-menu',
                onContextMenuItem: function (row, e) {
                    if (e.data("item") == "edit") {
                        edit(row.id)
                    } else if (e.data("item") == "delete") {
                        del(row.id)
                    }
                },
                columns: [{
                    checkbox: true
                }, {
                    field: 'table_type',
                    title: '表类型',
                    formatter: function (value, row, index) {
                        return jh.getDictLabel(tableTypeJson, row.tableType)
                    }
                }, {
                    field: 'name',
                    title: '表名',
                    formatter: function (value, row, index) {
                        return '<a  href="#" onclick="jh.openDialogView(\'查看表单\', \'' + ctx + '/gen/genTable/form?id=' + row.id + '\',\'90%\', \'90%\')">' + value + '</a>'
                    }
                }, {
                    field: 'comments',
                    title: '说明'
                }, {
                    field: 'className',
                    title: '类名'
                }, {
                    field: 'parentTable',
                    title: '主表'
                }, {
                    field: 'isSync',
                    title: '同步数据库',
                    formatter: function (value, row, index) {
                        if (row.isSync == '0') {
                            return '<font color=\'red\'>未同步</font>'
                        } else {
                            return '<font>已同步</font>'
                        }
                    }
                }, {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    events: {
                        'click .edit': function (e, value, row, index) {
                            jh.openDialog('修改业务表', ctx + '/gen/genTable/form?id=' + row.id, '90%', '90%')
                        },
                        'click .remove': function (e, value, row, index) {
                            jh.confirm('确认要移除该条记录吗？', function () {
                                jh.loading();
                                jh.get(ctx + "/gen/genTable/delete?id=" + row.id, function (data) {
                                    if (data.success) {
                                        $('#table').bootstrapTable('refresh');
                                        jh.success(data.msg)
                                    } else {
                                        jh.error(data.msg)
                                    }
                                })
                            })
                        },
                        'click .del': function (e, value, row, index) {
                            jh.confirm('确认要删除该条记录并删除对应的数据库表吗？', function () {
                                jh.loading();
                                jh.get(ctx + "/gen/genTable/deleteDb?id=" + row.id, function (data) {
                                    if (data.success) {
                                        $('#table').bootstrapTable('refresh');
                                        jh.success(data.msg)
                                    } else {
                                        jh.error(data.msg)
                                    }
                                })
                            })
                        },
                        'click .sync': function (e, value, row, index) {
                            jh.open({
                                type: 1,
                                auto: false,
                                icon: 1,
                                title: "系统提示",
                                content: '<div style="padding:15px"> <div>确认要同步数据库吗？请慎重选择同步方式，强制同步数据库将删除所有数据重新建表</div> <div class=""><br/><label><input name="syncDb" type="radio" value="1" checked/> 普通同步(保留数据) </label>   <label><input name="syncDb" type="radio" value="2" /> 强制同步(重新建表) </label></div></div>',
                                btn: ['确定', '关闭'],
                                btn1: function (index, layero) {
                                    var isForce = top.$('input[type="radio"][name="syncDb"]:checked').val();
                                    jh.loading();
                                    jh.get(ctx + "/gen/genTable/synchDb?id=" + row.id + "&isForce=" + isForce, function (data) {
                                        if (data.success) {
                                            $('#table').bootstrapTable('refresh');
                                            jh.success(data.msg)
                                        } else {
                                            jh.error(data.msg)
                                        }
                                        jh.close(index)
                                    })
                                },
                                btn2: function (index) {
                                    jh.close(index)
                                }
                            })
                        }
                    },
                    formatter: function operateFormatter(value, row, index) {
                        return buttonText
                    }
                }
                ]
            });
            if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
                $('#table').bootstrapTable("toggleView")
            }
            $('#table').on('check.bs.table uncheck.bs.table load-success.bs.table check-all.bs.table uncheck-all.bs.table', function () {
                $('#remove').prop('disabled', !$('#table').bootstrapTable('getSelections').length);
                $('#edit').prop('disabled', $('#table').bootstrapTable('getSelections').length != 1)
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

        function add() {
            jh.openDialog('新建表单', ctx + '/gen/genTable/form', '90%', '90%')
        }

        function edit(id) {
            if (!id) {
                id = getIdSelections()
            }
            jh.openDialog('编辑表单', ctx + "/gen/genTable/form?id=" + id, '90%', '90%')
        }

        function del(ids) {
            if (!ids) {
                ids = getIdSelections()
            }
            jh.confirm('确认要移除选中记录吗？', function () {
                jh.loading();
                jh.get(ctx + "/gen/genTable/deleteAll?ids=" + ids, function (data) {
                    if (data.success) {
                        $('#table').bootstrapTable('refresh');
                        jh.success(data.msg)
                    } else {
                        jh.error(data.msg)
                    }
                })
            })
        }

        function genCode() {
            var rows = $("#table").bootstrapTable('getSelections');
            if (rows.length == 0) {
                jh.alert('请至少选择一条数据!');
                return
            }
            if (rows.length > 1) {
                jh.alert('只能选择一条数据!');
                return
            }
            var selectedRow = rows[0];
            var tableType = selectedRow.tableType;
            if (tableType == '2') {
                jh.alert('不能选择附表生成代码，请选择主表!');
                return
            }
            if (tableType == '5') {
                jh.alert('不能选择右表生成代码，请选择左树!');
                return
            }
            var isSync = selectedRow.isSync;
            if (isSync == '0') {
                jh.alert('请先同步数据库!');
                return
            }
            jh.openDialog('生成代码', ctx + '/gen/genTable/genCodeForm?genTable.id=' + selectedRow.id + '&tableType=' + tableType, '1000px', '500px')
        }

        function createMenu() {
            var rows = $("#table").bootstrapTable('getSelections');
            var size = rows.length;
            if (size == 0) {
                jh.alert('请至少选择一条数据!');
                return
            }
            if (size > 1) {
                jh.alert('只能选择一条数据!');
                return
            }
            var selectedRow = rows[0];
            var tableType = selectedRow.tableType;
            if (tableType == '2' || tableType == '5') {
                jh.alert('不能选择附表创建菜单，请选择主表!');
                return
            }
            var isSync = selectedRow.isSync;
            if (isSync == '0') {
                jh.alert('请先同步数据库!');
                return
            }
            jh.openDialog('创建菜单', ctx + '/gen/genScheme/menuForm?gen_table_id=' + selectedRow.id + "&genTableType=" + tableType, '800px', '500px')
        }

        function createCustomObj() {
            var rows = $("#table").bootstrapTable('getSelections');
            var size = rows.length;
            if (size == 0) {
                jh.alert('请至少选择一条数据!');
                return
            }
            if (size > 1) {
                jh.alert('只能选择一条数据!');
                return
            }
            var selectedRow = rows[0];
            var tableType = selectedRow.tableType;
            jh.confirm("是否将该表对应的实体添加到java自定义对象中？", function () {
                jh.post(ctx + "/gen/genCustomObj/add", {
                    'gen_table_id': selectedRow.id,
                    'genTableType': tableType
                }, function (data) {
                    if (data.success) {
                        jh.success(data.msg)
                    } else {
                        jh.error(data.msg)
                    }
                })
            })
        }

        function manageCustomObj() {
            jh.openTab(ctx + "/gen/genCustomObj", "自定义java对象管理", false)
        }

        function page(n, s) {
            if (n)
                $("#pageNo").val(n);
            if (s)
                $("#pageSize").val(s);
            $("#searchForm").submit();
            return false
        }

        function importFromDB() {
            jh.open({
                type: 2,
                area: ['900px', '560px'],
                title: "导入表单",
                auto: true,
                maxmin: true,
                content: ctx + '/gen/genTable/importTableFromDB',
                btn: ['确定', '关闭'],
                yes: function (index, layero) {
                    var ids = layero.find("iframe")[0].contentWindow.getIdSelections();
                    if (ids.length == 0) {
                        jh.warning("请选择至少一条数据!");
                        return
                    }
                    jh.loading("正在导入.....");
                    jh.post(ctx + "/gen/genTable/saveTableFromDB", {
                        name: ids.join(",")
                    }, function (data) {
                        if (data.success) {
                            $('#table').bootstrapTable('refresh');
                            jh.alert(data.msg)
                        } else {
                            jh.error(data.msg)
                        }
                        jh.close(index)
                    })
                },
                cancel: function (index) {
                    jh.close(index)
                }
            })
        }

    </script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">表单列表</h3>
        </div>
        <div class="panel-body">
            <sys:message content="${message}"/>

            <!-- 搜索 -->
            <div class="accordion-group">
                <div id="collapseTwo" class="accordion-body collapse">
                    <div class="accordion-inner">
                        <form:form id="searchForm" modelAttribute="genTable" class="form form-horizontal well clearfix">
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="label-item single-overflow pull-left" title="表名：">表名：</label>
                                <form:input path="nameLike" htmlEscape="false" maxlength="64" class=" form-control"/>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="label-item single-overflow pull-left" title="说明：">说明：</label>
                                <form:input path="comments" htmlEscape="false" maxlength="64" class=" form-control"/>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="label-item single-overflow pull-left" title="父表表名：">父表表名：</label>
                                <form:input path="parentTable" htmlEscape="false" maxlength="64" class=" form-control"/>
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
                <shiro:hasPermission name="gen:genTable:add">
                    <a id="add" class="btn btn-primary" onclick="add()"><i class="glyphicon glyphicon-plus"></i> 新建</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="gen:genTable:importDb">
                    <button class="btn btn-info" data-toggle="tooltip" data-placement="left" title="导入"
                            onclick="importFromDB()">
                        <i class="fa fa-folder-open-o"></i> 数据库导入表单
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="gen:genTable:edit">
                    <button id="edit" class="btn btn-success" disabled onclick="edit()">
                        <i class="glyphicon glyphicon-edit"></i> 修改
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="gen:genTable:del">
                    <button id="remove" class="btn btn-danger" disabled onclick="del()">
                        <i class="glyphicon glyphicon-remove"></i> 删除
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="gen:genTable:genCode">
                    <button id="remove" class="btn btn-warning" onclick="genCode()">
                        <i class="fa fa-folder-open-o"></i> 生成代码
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="gen:genTable:genCode">
                    <button class="btn btn-default" title="创建菜单" onclick="createMenu()"><i
                            class="fa fa-folder-open-o"></i> 创建菜单
                    </button>
                    <button class="btn btn-default" title="自定义java对象" onclick="createCustomObj()"><i
                            class="fa fa-folder-open-o"></i> 自定义对象生成
                    </button>
                    <button class="btn btn-default" title="自定义java对象" onclick="manageCustomObj()"><i
                            class="fa fa-folder-open-o"></i> 自定义对象管理
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
                <shiro:hasPermission name="share:school:campus:edit">
                    <li data-item="edit"><a>编辑</a></li>
                </shiro:hasPermission>
                <shiro:hasPermission name="share:school:campus:del">
                    <li data-item="delete"><a>删除</a></li>
                </shiro:hasPermission>
                <li data-item="action1"><a>取消</a></li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
