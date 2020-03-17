<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ attribute name="input" type="java.lang.String" required="true" description="输入框"%>
<%@ attribute name="type" type="java.lang.String" required="true" description="file、image、media,thumb"%>
<%@ attribute name="multiple" type="java.lang.Boolean" required="false" description="是否多文件上传"%>
<%@ attribute name="readonly" type="java.lang.Boolean" required="false" description="是否查看模式"%>
<%@ attribute name="maxWidth" type="java.lang.String" required="false" description="最大宽度"%>
<%@ attribute name="maxHeight" type="java.lang.String" required="false" description="最大高度"%>
<%@ attribute name="auto" type="java.lang.Boolean" required="false" description="自动上传"%>
<c:set var="imageExt" value="${fns:getConfig('userfiles.image.ext')}"/>
<c:set var="imageSize" value="${fns:getConfig('userfiles.image.size')}"/>
<c:set var="fileExt" value="${fns:getConfig('userfiles.file.ext')}"/>
<c:set var="fileSize" value="${fns:getConfig('userfiles.file.size')}"/>
<c:set var="mediaExt" value="${fns:getConfig('userfiles.media.ext')}"/>
<c:set var="mediaSize" value="${fns:getConfig('userfiles.media.size')}"/>
<c:set var="fileExt" value="${imageExt}, ${mediaExt},${fileExt}"/>
<c:choose>
    <c:when test="${type eq 'image'}">
        <c:set var="ext" value="${imageExt}"/>
        <c:set var="size" value="${imageSize}"/>
        <c:set var="url" value="${ctx}/upload/uploadimage"/>
    </c:when>
    <c:when test="${type eq 'thumb'}">
        <c:set var="ext" value="${imageExt}"/>
        <c:set var="size" value="${imageSize}"/>
        <c:set var="url" value="${ctx}/upload/uploadimage?isScale=true&width=${empty maxWidth ? 360 : maxWidth}&height=${empty maxHeight ? 360 : maxHeight}"/>
    </c:when>
    <c:when test="${type eq 'media'}">
        <c:set var="ext" value="${mediaExt}"/>
        <c:set var="size" value="${mediaSize}"/>
        <c:set var="url" value="${ctx}/upload/uploadmedia"/>
    </c:when>
    <c:otherwise>
        <c:set var="ext" value="${fileExt}"/>
        <c:set var="size" value="${fileSize}"/>
        <c:set var="url" value="${ctx}/upload/uploadfile"/>
    </c:otherwise>
</c:choose>
<div id="webupload_${input}" class="webUploaderContainer">
    <ul class="filelist"></ul>
    <div class="statusBar">
        <div class="progress">
            <span class="text">0%</span>
            <span class="percentage"></span>
        </div>
        <div class="btns">
            <div id="webupload_picker_${input}" class="filepicker">选择文件</div>
            <div class="uploadBtn">开始上传</div>
        </div>
    </div>
</div>
<script>
wb = $('#webupload_${input}').WebUpload({
input:'${input}',
url:'${url}',
type:'${type}',
ext:'${ext}',
size:${size},
num:${multiple?20:1},
auto:${empty auto ? false : auto}
})
</script>