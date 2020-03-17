<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>二维码</title>
	<meta name="decorator" content="ani"/>

	<script src="${ctxStatic}/plugin/qdcode/jquery.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/plugin/qdcode/qrcode.min.js" type="text/javascript"></script>
</head>
<body class="bg-white">
		<div id="qdcode"></div>
		<script>
			var text = '${wxMeeting.id}';
			if(text){
			    text = "wxMeeting="+text;
			}
			var _qdcode = document.getElementById("qdcode");
            var qrcode = new QRCode(_qdcode, {
                text: text,
                width: 400,
                height: 380,
                colorDark : "#000000",
                colorLight : "#ffffff",
                correctLevel : QRCode.CorrectLevel.H
            });
		</script>
		<style>
			img{
				margin: 0 auto;
				margin-top: 20px;
			}
		</style>
</body>
</html>