<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>业务表管理</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#table').bootstrapTable({
                method: 'get',
                dataType: "json",
                minimumCountColumns: 2,
                striped: true,
                cache: false,
                pagination: false,
                sortOrder: "asc",
                pageNumber: 1,
                pageSize: 10,
                pageList: [10, 25, 50, 100],
                url: ctx + "/gen/genTable/importTableData",
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
                onContextMenuItem: function (row, e) {
                    if (e.data("item") == "edit") {
                        window.location = ctx + "/gen/genTable/form?id=" + row.id
                    } else if (e.data("item") == "delete") {
                        jh.confirm('确认要删除该数据库表单记录吗？', function () {
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
                    }
                },
                onClickRow: function (row, e) {
                },
                columns: [{
                    checkbox: true
                }, {
                    field: 'name',
                    title: '名称',
                    sortable: true
                }, {
                    field: 'comments',
                    title: '描述',
                    sortable: true
                }
                ]
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
                return row.name
            })
        }
    </script>
</head>
<body id="" class="bg-white" style="">


<div class="wrapper wrapper-content">

    <!-- 搜索 -->
    <div class="accordion-group">
        <div id="collapseTwo" class="accordion-body collapse in">
            <div class="accordion-inner">
                <form id="searchForm" class="form form-horizontal well clearfix"
                      action="/manager/gen/genTable/importTableFromDB" method="post">
                    <div class="col-xs-12 col-sm-6 col-md-4">
                        <label class="label-item single-overflow pull-left" title="表名：">名称：</label>
                        <input id="name" name="name" class=" form-control input-sm" type="text" value=""
                               maxlength="50"/>
                    </div>
                    <div class="col-xs-12 col-sm-6 col-md-4">
                        <div style="margin-top:26px">
                            <a id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i
                                    class="fa fa-search"></i> 查询</a>
                            <a id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i
                                    class="fa fa-refresh"></i> 重置</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- create table from db -->
    <div id="table"></div>

</div>

</body>
</html>
