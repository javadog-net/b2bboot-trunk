<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>清空索引</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp"%>
    <%@include file="/webpage/include/treeview.jsp" %>

</head>

<body>

<div class="container-fluid">
    <div class="row-fluid">

        <div class="widget-box">
            <div class="widget-content nopadding">

                <div class="widget-content nopadding">
                    <div id="errorDiv" class="alert alert-error hide">
                        <span id="errorInfo"></span>

                    </div>
                    <div id="successDiv" class="alert alert-success ">
                        <span id="successInfo"></span>
                    </div>
                </div>
            </div>
        </div><!--/row-->

    </div><!--/span-->
    <script>
        jh.confirm('确定要清空索引操作么？', function(){
            $("#successInfo").html("正在清空索引！请稍等...");
            jh.get("${ctx}/cms/indexClearProduct", function(data){
                if(data.success){
                    $("#successDiv").show();
                    $("#successInfo").html("清空索引成功！");
                }else{
                    $("#errorDiv").show();
                    $("#errorInfo").html("清空索引失败！");
                }
            })

        });
    </script>
</body></html>