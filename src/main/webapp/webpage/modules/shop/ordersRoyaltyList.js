<%@ page contentType="text/html;charset=UTF-8" %>
    <script>
    $(document).ready(function() {
        $('#ordersTable').bootstrapTable({

            //请求方法
            method: 'get',
            //类型json
            dataType: "json",
            //显示刷新按钮
            showRefresh: true,
            //显示切换手机试图按钮
            showToggle: true,
            //显示 内容列下拉框
            showColumns: true,
            //显示到处按钮
            showExport: true,
            //显示切换分页按钮
            showPaginationSwitch: true,
            //最低显示2行
            minimumCountColumns: 2,
            //是否显示行间隔色
            striped: true,
            //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            cache: false,
            //是否显示分页（*）
            pagination: true,
            //排序方式
            sortOrder: "asc",
            //初始化加载第一页，默认第一页
            pageNumber:1,
            //每页的记录行数（*）
            pageSize: 10,
            //可供选择的每页的行数（*）
            pageList: [10, 25, 50, 100],
            //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
            url: "${ctx}/shop/ordersRoyalty/data",
            //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
            //queryParamsType:'',
            ////查询参数,每次调用是会带上这个参数，可自定义
            queryParams : function(params) {
                var searchParam = $("#searchForm").serializeJSON();
                searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
                searchParam.pageSize = params.limit === undefined? -1 : params.limit;
                searchParam.orderBy = params.sort === undefined? "" : params.sort+ " "+  params.order;
                return searchParam;
            },
            //分页方式：client客户端分页，server服务端分页（*）
            sidePagination: "server",
            contextMenuTrigger:"right",//pc端 按右键弹出菜单
            contextMenuTriggerMobile:"press",//手机端 弹出菜单，click：单击， press：长按。
            contextMenu: '#context-menu',

            onClickRow: function(row, $el){
            },
            columns: [{
                checkbox: true

            }
                ,{
                    field: 'orderSn',
                    title: '订单编号',
                    sortable: true
                    ,formatter:function(value, row , index){
                        return '<a  href="#" onclick="jh.openDialogView(\'查看订单\', \'${ctx}/shop/orders/form?id='+row.id+'\',\'800px\', \'800px\')">'+value+'</a>';
                    }

                }
                ,{
                    field: 'storeName',
                    title: '店铺名称',
                    sortable: true

                }
                ,{
                    field: 'dealerName',
                    title: '经销商',
                    sortable: true

                }
                ,{
                    field: 'kjtAccount',
                    title: '快捷通账号',
                    sortable: true

                }
                ,{
                    field: 'paySn',
                    title: '支付单号',
                    sortable: true

                }
                ,{
                    field: 'purchaserName',
                    title: '采购商',
                    sortable: true

                }
                ,{
                    field: 'purchaserLoginName',
                    title: '采购商账号',
                    sortable: true

                }
                ,{
                    field: 'purchaserPhone',
                    title: '采购商手机',
                    sortable: true

                }
                ,{
                    field: 'goodsAmount',
                    title: '商品总价格',
                    sortable: true

                }
                ,{
                    field: 'orderAmount',
                    title: '订单总价格',
                    sortable: true

                }
                ,{
                    field: 'createDate',
                    title: '订单生产时间',
                    sortable: true

                }
                /**
                ,{
                    field: 'paymentDate',
                    title: '付款时间',
                    sortable: true

                }
                 **/
                ,{
                    field: 'deliveryTime',
                    title: '确认收货时间',
                    sortable: true

                }
                ,{
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    events: {
                        'click .royalty': function (e, value, row, index) {
                            royalty(row.id);
                        }
                    },
                    formatter:  function operateFormatter(value, row, index) {
                                return [
                                    '<a href="#" class="royalty" title="转账"  style="display: inline-block;margin-bottom: 8px;margin-top: 5px;"><i class="btn btn-primary btn-xs" style="font-style:normal">转账</i></a>',
                            ].join('')

                    }
                }

            ]

        });


        if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端


            $('#ordersTable').bootstrapTable("toggleView");
        }

        $('#ordersTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
            'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#ordersTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#ordersTable').bootstrapTable('getSelections').length!=1);
        });

        $("#search").click("click", function() {// 绑定查询按扭
            $('#ordersTable').bootstrapTable('refresh');
        });

        $("#reset").click("click", function() {// 绑定查询按扭
            $("#searchForm  input").val("");
            $("#searchForm  select").val("");
            $("#searchForm  .select-item").html("");
            $('#ordersTable').bootstrapTable('refresh');
        });


    });

function getIdSelections() {
    return $.map($("#ordersTable").bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}
function royalty(id){
    jh.confirm('确认要分账吗？', function(){
        jh.loading();
        jh.get("${ctx}/shop/ordersRoyalty/royaltyOrder?id=" + id, function(data){
            if(data.success){
                $('#ordersTable').bootstrapTable('refresh');
                alert("result:"+data);
                jh.success(data.msg);
            }else{
                jh.error(data.msg);
            }
        })

    });
}
</script>