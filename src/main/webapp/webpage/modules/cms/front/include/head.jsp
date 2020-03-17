<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" /><meta http-equiv="Pragma" content="no-cache" /><meta http-equiv="Expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=7,IE=9,IE=10" />
<!-- 引入vendor.js,该文件压缩了jQuery，datatime等常用js文件以提高加载速度 -->
<script src="${ctxStatic}/common/js/vendor.js"></script><!-- 该插件已经集成jquery 无需重复引入 -->
<link href="${ctxStatic}/plugin/awesome/4.4/css/font-awesome.min.css" rel="stylesheet" />
<c:if test="${site.theme eq 'basic'}">
	<link href="${ctxStatic}/plugin/bootstrap/2.3.1/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/plugin/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
	<!--[if lte IE 6]><link href="${ctxStatic}/plugin/bootstrap/bsie/css/bootstrap-ie6.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/plugin/bootstrap/bsie/js/bootstrap-ie.min.js" type="text/javascript"></script><![endif]-->
	<link href="${ctxStaticTheme}/style.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStaticTheme}/script.js" type="text/javascript"></script>
</c:if>