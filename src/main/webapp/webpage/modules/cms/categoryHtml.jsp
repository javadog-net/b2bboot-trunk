<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>栏目页静态化</title>
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
        .form-group label{
            padding: 0 10px;
        }
        .maxpage{
            display: inline-block;
            height: 30px;
            padding: 4px 6px;
            margin-bottom: 10px;
            font-size: 14px;
            line-height: 20px;
            color: #555555;
            border-radius: 4px;
            border: 1px solid #cccccc;
        }
    </style>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-body">
            <sys:message content="${message}"/>
            <form:form id="inputForm"  class="form-horizontal" action="categoryHtmlDo" method="post">
            <div class="condition">
                <div class="form-group" style="height: 50px;line-height: 50px;margin-left: 7px;">
                    <label class="col-sm-3 control-label">生成静态化页面最大页数：</label>
                    <div class="col-sm-7">
                        <input type="number" name="page" class="maxpage">
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
                        <td class="pipei">
                            <a href="javascript:void(0)">
                                <i class="jstree-icon jstree-themeicon fa fa-file-text-o jstree-themeicon-custom" role="presentation"></i>
                                <span>${category.name}</span>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </form>
        <!-- 工具栏 -->

        <div id="toolbar" style="margin: 10px">
            <shiro:hasPermission name="cms:category:edit">
                <button id="createSelected" class="btn btn-success"  onclick="htmlSelected()">
                    <i class="glyphicon glyphicon-edit"></i>  静态化选中栏目
                </button>
            </shiro:hasPermission>
            <shiro:hasPermission name="cms:category:edit">
                <button id="createAll" class="btn btn-success" onclick="htmlAll()">
                    <i class="glyphicon glyphicon-edit"></i> 静态化所有栏目
                </button>
            </shiro:hasPermission>
        </div>
    </div>
</div>
</form:form>
</div>
<script>
    <%--  初始化日期 --%>
    $('#startDate').datetimepicker({
        format: "YYYY-MM-DD HH:mm:ss"
    });
    $('#endDate').datetimepicker({
        format: "YYYY-MM-DD HH:mm:ss"
    });
    function checkDate () {
        if($.trim($("#endDate").val())!="" && $.trim($("#startDate").val())!=""
            && $.trim($("#endDate").val())<$.trim($("#startDate").val())){
            $("#endDate").focus();
            $("#errorDiv").html("结束时间必须大于开始时间");
            $("#errorDiv").fadeIn("slow");
            return false;
        }
        return true;
    }
    function htmlSelected(){
        if(checkDate()){
            var data = {};
            data.startDate=$("#startDate").val();
            data.endDate=$("#endDate").val();
            var ids = [];
            $("input[type='checkbox']:checked").map(function(){
                //遍历每个checkbox对应的id
                var id = $(this).parents("tr").attr("id");
                //如果id存在
                if(id){
                    ids.push(id);
                }
            });
            data.categoryIds = ids.join(",");
            data.createType = "selected";
            ajaxPost(data);
        }
    }
    function htmlAll() {
        //请求参数
        var data = {};
        data.startDate=$("#startDate").val();
        data.endDate=$("#endDate").val();
        data.createType = "all";
        ajaxPost(data);
    }
    function ajaxPost(data){
        var loading = jh.loading("正在静态化，请稍等...")
        jh.post("${ctx}/cms/categoryHtmlDo", data, function(data){
            if(data.success){
                layer.msg(data.msg, {
                    icon: 1,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                }, function(){
                     window.location.reload();
                });
            }else{
                layer.close(loading);
                jh.error(data.msg);
            }
            jh.close(loading);
        })
    }
    //选择左侧checkbox
    $(function(){
        $("input[type='checkbox']").on("change",function() {
            var isCheck = $(this).is(":checked");
            //如果选中则将其子集全部勾选
            var pid = $(this).parents("tr")[0].getAttribute("id");
            $("tr").map(function(arr,item,index){
                if($(item).attr("pid")){
                    //将其子集
                    if(pid == $(item).attr("pid")){
                        $(item).find(".prev_span").find("input").prop('checked',isCheck);
                        deep(item,isCheck);
                    }
                }
            })
        })
    })
    //递归勾选
    function deep(item,flag){
        //如果是最深一层则停止递归
        if(!$(item).attr("haschild")){
            return;
        }
        //如果存在则
        var pid = $(item).attr("id");
        $("tr").map(function(arr,item,index){
            if($(item).attr("pid")){
                //将其子集
                if(pid == $(item).attr("pid")){
                    $(item).find(".prev_span").find("input").prop('checked',flag);
                    deep(item,flag);
                }
            }
        });
    }
</script>
</body>
</html>