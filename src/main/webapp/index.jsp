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
       <%-- <input type="text" name="id" id="" value="1cb541bd032943ce977c1813956a683a">--%>
        <input type="text" name="storeId" id="" value="001">
        <input type="text" name="code" id="" value="001">
        <input type="text" name="marketPrice" id="" value="1">
        <input type="text" name="price" id="" value="1">
        <input type="text" name="isRecommend" id="" value="0">
        <input type="text" name="isPromotion" id="" value="0">
        <input type="text" name="stock" id="" value="1">
        <input type="text" name="goodsSalenum" id="" value="1">
        <input type="text" name="goodsCollect" id="" value="1">
        <input type="text" name="storePriceGroupList[0].storeId" id="" value="1">
        <input type="text" name="storePriceGroupList[0].code" id="" value="001">
        <input type="text" name="storePriceGroupList[0].price" id="" value="1">
        <input type="text" name="storePriceGroupList[0].storeGoodsPriceList[0].purchaserId" id="" value="001">
        <input type="text" name="storePriceGroupList[0].storeGoodsPriceList[1].purchaserId" id="" value="002">
        <input type="text" name="Authorization" id="" value="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyVHlwZSI6IjIiLCJleHAiOjE1MzMxNzgyNTUsInVzZXJJZCI6IjEiLCJpYXQiOjE1MzMwOTE4NTV9.1QcvL0_xQT1VAArx5tRHZ6klo6xbpjLtZcwzrjiQ2dg">

    </form>

    <script>
        $(function(){
            $.ajax({
                url:"/api/store/goods/publishProduct",
                method:"POST",
                data: $("#form").serialize(),
                success:function(res){

                },
                error:function(ee){

                }
            })
        })
    </script>

<h1> ddsdfsd</h1>
</body>
</html>