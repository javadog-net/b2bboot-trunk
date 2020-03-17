<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
    $('#infogoodsTable').bootstrapTable({

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
        url: "${ctx}/shop/storeGoods/listData",
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
   /*     contextMenu: '#context-menu',
        onContextMenuItem: function(row, $el){
            if($el.data("item") == "edit"){
                window.location = "${ctx}/shop/goods/form?code=" + row.code;
            } else if($el.data("item") == "delete"){
                jh.confirm('确认要删除该商品表记录吗？', function(){
                    jh.loading();
                    jh.get("${ctx}/shop/goods/delete?id="+row.id, function(data){
                        if(data.success){
                            $('#goodsTable').bootstrapTable('refresh');
                            jh.success(data.msg);
                        }else{
                            jh.error(data.msg);
                        }
                    })

                });

            }
        },*/

        onClickRow: function(row, $el){
        },
        columns: [{
            checkbox: true

        }
            ,{
                field: 'storeName',
                title: '店铺名称',
                sortable: true

            }
            ,{
                field: 'code',
                title: '产品编码',
                sortable: true

            }
            ,{
                field: 'goodsName',
                title: '产品名称',
                sortable: true

            }
        ]

    });


    if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端


        $('#goodsTable').bootstrapTable("toggleView");
    }

    $('#goodsTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $('#remove').prop('disabled', ! $('#goodsTable').bootstrapTable('getSelections').length);
        $('#edit').prop('disabled', $('#goodsTable').bootstrapTable('getSelections').length!=1);
    });

    $("#search").click("click", function() {// 绑定查询按扭
        $('#goodsTable').bootstrapTable('refresh');
    });

    $("#reset").click("click", function() {// 绑定查询按扭
        $("#searchForm  input").val("");
        $("#searchForm  select").val("");
        $("#searchForm  .select-item").html("");
        $('#goodsTable').bootstrapTable('refresh');
    });


});



$(function(){
    //传递分类id
    $("#goodsCategoryjsTree").on("click",function(e){
        //获取等级
        var level = e.target.parentNode.getAttribute("aria-level");
        //获取父级id
        var catparid =e.target.parentNode.parentNode.parentNode.getAttribute("id");
        //获取子集id
        var catid = e.target.parentNode.getAttribute("id");
        if(level){
            //计入是几级列表
            $("#add").attr("level",level);
            $("#add").attr("catid",catid);
            $("#add").attr("catparid",catparid);
        }
        if(level=='1'){
            $('#infogoodsTable').bootstrapTable("refresh",{query:{categoryPid:catid}});
        }else if(level=='2'){
            $('#infogoodsTable').bootstrapTable("refresh",{query:{categoryId:catid}});
        }

    });
})

var index = "";
//谨慎操作
function successFun(data){
    if(data.success){
        layer.msg(data.msg,{icon:1},function(){
            window.location.reload();
        });
    }else{
        layer.msg(data.msg,{icon:5});
    }
}
</script>