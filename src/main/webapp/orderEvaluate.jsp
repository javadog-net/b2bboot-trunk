<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="${ctxStatic}/common/js/vendor.js"></script>
</head>
<body>

    <form id="form" method="post">
       <input type="text" name="orderId" id="" value="f052635540f741ebb2865024a93cfdba">
        <input type="text" name="scoreProductQuality" id="" value="1">
        <input type="text" name="scoreDemandResponse" id="" value="1">
        <input type="text" name="scoreDeliveryCredit" id="" value="1">
        <input type="text" name="scoreSupplySpeed" id="" value="1">
        <input type="text" name="scoreCustomerService" id="" value="1">
        <input type="text" name="isanonymous" id="" value="1">
        <input type="text" name="goodsEvaluateList[0].productQuality" id="" value="1">
        <input type="text" name="goodsEvaluateList[0].demandResponse" id="" value="1">
        <input type="text" name="goodsEvaluateList[0].deliveryCredit" id="" value="1">
        <input type="text" name="goodsEvaluateList[0].supplySpeed" id="" value="1">
        <input type="text" name="goodsEvaluateList[0].customerService" id="" value="1">
        <input type="text" name="goodsEvaluateList[0].orderId" id="" value="f052635540f741ebb2865024a93cfdba">
        <input type="text" name="goodsEvaluateList[0].goodsCode" id="" value="AA9EK6000">
        <input type="text" name="Authorization" id="" value="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyVHlwZSI6IjMiLCJleHAiOjE1MzQ1NjMxNjIsInVzZXJJZCI6ImVlMmNiMWI2NDdjNTQ2ZjFhYjFlZjJhNjE4MDA2YzdiIiwiaWF0IjoxNTM0NDc2NzYyfQ.VHZANv5SqoP0Tcr38vqVDW3HR6hwX89IjeNbi7ouLLI">

    </form>

    <script>
        $(function(){
            $.ajax({
                url:"/api/purchaser/browse/orderEvaluate",
                method:"POST",
                data: $("#form").serialize(),
                success:function(res){

                },
                error:function(ee){

                }
            })
        })
    </script>


</body>
</html>