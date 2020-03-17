<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>全商品静态化</title>
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
        jh.confirm('确定全商品静态化操作么？此操作耗时较长！', function(){
            $("#successInfo").html("正在全商品静态化！请稍等...");
            jh.get("${ctx}/shop/goodsCategory/createAllGoodsHtml", function(data){
                if(data.success){
                    $("#successDiv").show();
                    $("#successInfo").html("全商品静态化成功！");
                }else{
                    $("#errorDiv").show();
                    $("#errorInfo").html("全商品静态化失败！");
                }
            })

        });
    </script>
</body></html>