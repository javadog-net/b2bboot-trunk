<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>信息静态化</title>
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
            <form:form id="inputForm"  class="form-horizontal"  method="post">
            <div class="condition">
                    <div class="form-group col-sm-12" style="height: 50px;line-height: 50px;">
                        <label class="col-sm-2 control-label" title="开始时间：">&nbsp;开始时间：</label>
                        <div class="col-sm-4">
                            <div class='input-group date' id='startDate' >
                                <input type='text'  name="startDate" id="startDateInput" class="form-control"  />
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
                                <input type='text'  name="endDate" id="endDateInput" class="form-control"  />
                                <span class="input-group-addon">
			                       <span class="glyphicon glyphicon-calendar"></span>
			                   </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
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
                <button id="createSelected" class="btn btn-success"  onclick="htmlSelected()">
                    <i class="glyphicon glyphicon-edit"></i>  静态化选中栏目
                </button>
                <button id="createAll" class="btn btn-success" onclick="htmlAll()">
                    <i class="glyphicon glyphicon-edit"></i> 静态化所有栏目
                </button>
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
function checkDate(){
    if($.trim($("#endDateInput").val())!="" && $.trim($("#startDateInput").val())!=""
        && $.trim($("#endDateInput").val())<$.trim($("#startDateInput").val())){
        $("#endDateInput").focus();
        $("#errorDiv").html("结束时间必须大于开始时间");
        $("#errorDiv").fadeIn("slow");
        return false;
    }
    return true;
}
function htmlSelected(){
    if(checkDate()){
        var data = {};
        data.startDate=$("#startDateInput").val();
        data.endDate=$("#endDateInput").val();
        var ids = [];
        $("input[type='checkbox']:checked").map(function(){
            //遍历每个checkbox对应的id
            var id = $(this).parents("tr").attr("id");
            //如果id存在
            if(id){
                ids.push(id);
            }
        });
        data.ids = ids.join(",");
        data.createType = "selected";
        ajaxPost(data);
    }
}
function htmlAll() {
    //请求参数
    var data = {};
    data.startDate=$("#startDateInput").val();
    data.endDate=$("#endDateInput").val();
    data.createType = "all";
    ajaxPost(data);
}
function ajaxPost(data){
    var loading = jh.loading("正在静态化，请稍等...")
    jh.post("${ctx}/cms/infoHtmlDo", data, function(data){
        if(data.success){
            layer.close(loading);
            layer.msg(data.msg, {
                icon: 1,
                time: 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function(){
                layer.close(loading);
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