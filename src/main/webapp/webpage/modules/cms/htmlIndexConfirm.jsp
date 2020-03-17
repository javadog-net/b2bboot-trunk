<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>首页静态化</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp"%>
    <%@include file="/webpage/include/treeview.jsp" %>

</head>

<body>

<div class="container-fluid">
    <div class="row-fluid">

        <div class="widget-box">
<%--            <div class="widget-title">--%>
<%--								<span class="icon">--%>
<%--									<i class="icon-download-alt"></i>--%>
<%--								</span>--%>
<%--                <h5>首页静态化</h5>--%>
<%--            </div>--%>
            <div class="widget-content nopadding">

                <div class="widget-content nopadding">
                    <div id="errorDiv" class="alert alert-error hide">
                        <span id="errorInfo"></span>
                        <div id="error-btn" style="margin-top: 10px;display: none">
                            <button class="btn btn-primary" data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()">确定</button>
                        </div>
                    </div>
                    <div id="successDiv" class="alert alert-success ">
                        <span id="successInfo"></span>
                        <div id="success-btn" style="margin-top: 10px;display: none">
                            <button class="btn btn-primary" data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()">确定</button>
                        </div>
                    </div>
                </div>
            </div>
        </div><!--/row-->


    </div><!--/span-->
    <script>
        jh.confirm('确定首页静态化操作么？', function(){
            $("#successInfo").html("正在静态化首页！请稍等...");
            jh.get("${ctx}/cms/htmlIndex.do", function(data){
                if(data.success){
                    $("#successDiv").show();
                    $("#successInfo").html("首页静态化成功！");
                    $("#success-btn").show();
                }else{
                    $("#errorDiv").show();
                    $("#errorInfo").html("首页静态化失败！");
                    $("#error-btn").show();
                }
            })

        });
        function sortOrRefresh(){
            window.location.reload();
        }
    </script>
</body></html>