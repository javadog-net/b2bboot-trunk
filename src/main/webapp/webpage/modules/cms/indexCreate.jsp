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
            <form:form id="inputForm"  class="form-horizontal" action="indexCreateDo.do" method="post">
            <div class="condition">
                    <div class="form-group" style="height: 50px;line-height: 50px;margin-left: 7px;">
                        <label class="col-sm-2 control-label">是否所有栏目：</label>
                        <div class="col-sm-10">
                            <input type="radio" name="createType" class="i-checks required" value="all"><label>是</label>
                            <input type="radio" name="createType" class="i-checks required" value="categorys" checked><label>否</label>
                        </div>
                    </div>
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


            <form id="listForm" method="post">
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
                <a id="add" class="btn btn-primary"onclick="createIndex()"><i class="glyphicon glyphicon-plus"></i> 创建索引</a>
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

function createIndex(){
    if($.trim($("#endDateInput").val())!="" && $.trim($("#startDateInput").val())!=""
        && $.trim($("#endDateInput").val())<$.trim($("#startDateInput").val())){
        $("#endDateInput").focus();
        $("#errorDiv").html("结束时间必须大于开始时间");
        $("#errorDiv").fadeIn("slow");
        return false;
    }
    $("#errorDiv").html("正在处理中,请稍候...");
    $("#errorDiv").fadeIn("slow");

    //请求参数
    var data = {};
   data.startDate=$("#startDateInput").val();
   data.endDate=$("#endDateInput").val();
    //是否所有栏目
    var createType = $("input[name='createType']:checked").val();
    if('all' == createType){
            data.createType = "all";
            ajaxPost(data);
    }else if('categorys' == createType){
        //挑选栏目
        var ids = [];
        $("input[type='checkbox']:checked").map(function(){
            //遍历每个checkbox对应的id
            var id = $(this).parents("tr").attr("id");
            //如果id存在
            if(id){
                ids.push(id);
            }
        });
        if(ids.length==0){
            jh.error("请至少勾选一条栏目");
            return;
        }
        data.ids = ids.join(",");
        data.createType = "categorys";
        ajaxPost(data);
    }else{
        jh.error("请选择创建索引参数");
    }
    //document.getElementById("inputForm").submit();
}

function ajaxPost(data){
    var loading = jh.loading("正在处理,请稍后");
    jh.post("${ctx}/cms/indexCreateDo", data, function(data){
        if(data.success){
            layer.close(loading);
            layer.msg(data.msg, {
                icon: 1,
                time: 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function(){
                // window.location.reload();
            });
        }else{
            layer.close(loading);
            jh.error(data.msg);
        }
        jh.close(loading);
    })
}
</script>
</body>
</html>