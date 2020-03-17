<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>商品表管理</title>
    <meta name="decorator" content="ani"/>
    <style>
        .detail{
            display: none;
        }
    </style>
    <script type="text/javascript">

        $(document).ready(function () {
            $("#inputForm").validate({
                submitHandler: function (form) {
                    jh.loading();
                    form.submit();
                },
                rules : {
                    "marketPrice" : {
                        required : true,
                        isFloat : true//期望的是true,如果为false则展示提示信息
                    }
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

        });


    </script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <a class="panelButton" href="${ctx}/shop/directgoods?repage"><i class="ti-angle-left"></i> 返回</a>
                    </h3>
                </div>
                <div class="panel-body">
                    <form:form id="inputForm" modelAttribute="goods" action="${ctx}/shop/goods/save" method="post"
                               class="form-horizontal">
                        <form:hidden path="id"/>
                        <sys:message content="${message}"/>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>产品编码：</label>
                            <div class="col-sm-4">
                                <c:if test="${isAdd}">
                                    <form:input path="code" htmlEscape="false"
                                                class="form-control required code fundamental code"/>
                                </c:if>
                                <c:if test="${!isAdd}">
                                    <form:input path="code" htmlEscape="false" readonly="true"
                                                class="form-control required code fundamental "/>
                                </c:if>
                            </div>
                            <div class="col-sm-4">
                                <c:if test="${isAdd}">
                                    <span class="btn btn-success sendProCode">调取产品中心</span>
                                    <span class="btn btn-primary return">撤销</span>
                                </c:if>
                            </div>
                        </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><font color="red">*</font>产品主图：</label>
                                <div class="col-sm-4 img">
                                    <div class="imgshow">
                                        <c:if test="${!isAdd}">
                                            <c:forEach items="${imgList}" var ="img" varStatus="status">
                                                <img src="${img}" alt="" style="width: 70px; margin-right: 10px"/>
                                            </c:forEach>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="col-sm-4 upload" >
                                    <span style="color: red">产品中心无图建议自行上传</span>
                                    <form:hidden id="mainPicUrl" path="mainPicUrl" htmlEscape="false" maxlength="255" class="form-control"/>
                                    <sys:webupload input="mainPicUrl" type="file" multiple="true"/>
                                </div>
                                <label class="col-sm-2 control-label"><font color="red">*</font>产品名称：</label>
                                <div class="col-sm-4">
                                    <form:input path="name" htmlEscape="false" readonly="true" class="form-control required fundamental"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><font color="red">*</font>产品品牌：</label>
                                <div class="col-sm-4">
                                    <form:select path="brand" value="${goods.brand}" htmlEscape="false"
                                                 class="form-control required">
                                        <form:option value="" label="请选择产品品牌" selected="true"/>
                                        <form:options items="${brandList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                                    </form:select>
                                </div>
                                <label class="col-sm-2 control-label">产品分类：</label>
                                <div class="col-sm-4">
                                    <sys:treeselect id="cat" name="cat.id" value="${goods.cat.id}" labelName="cat.name"
                                                    labelValue="${goods.cat.name}"
                                                    title="产品分类" url="/shop/goodsCategory/treeData" extId="${goods.id}"
                                                    cssClass="form-control " allowClear="true"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><font color="red">*</font>是否可用：</label>
                                <div class="col-sm-4">
                                    <form:radiobuttons path="state" items="${fns:getDictList('enable_state')}"  itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required" onKeyPress="if (event.keyCode!=46 && event.keyCode!=45 &&
event.keyCode<48 || event.keyCode>57)) event.returnValue=false"/>
                                </div>
                                <label class="col-sm-2 control-label"><font color="red">*</font>市场价：</label>
                                <div class="col-sm-4">
                                    <form:input path="marketPrice" htmlEscape="false" class="form-control required marketPrice" />
                                </div>
                            </div>
                            <div class="form-group">
                               <%-- <label class="col-sm-2 control-label"><font color="red">*</font>产品一级目录：</label>--%>
                                <div class="col-sm-4">
                                    <form:hidden path="categoryPid" htmlEscape="false" readonly="true" class="form-control required  "/>
                                </div>
                                <%--<label class="col-sm-2 control-label"><font color="red">*</font>产品二级目录：</label>--%>
                                <div class="col-sm-4">
                                    <form:hidden path="categoryId" htmlEscape="false" readonly="true" class="form-control required "/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><font color="red">*</font>页面标题：</label>
                                <div class="col-sm-4">
                                    <form:input path="title" htmlEscape="false" class="form-control required title" />
                                </div>
                                <label class="col-sm-2 control-label"><font color="red">*</font>页面关键词：</label>
                                <div class="col-sm-4">
                                    <form:input path="keyword" htmlEscape="false" class="form-control required keyword" />
                                </div>
                           
                            </div>
                            <div class="form-group">
                             <label class="col-sm-2 control-label"><font color="red">*</font>页面描述：</label>
                                <div class="col-sm-10">
                                    <form:input path="des" htmlEscape="false" class="form-control required des" />
                                </div>
                            </div>
                            
                        <div class="form-group">
                            <label class="col-sm-2 control-label">机型卖点：</label>
                            <div class="col-sm-10">
                                <form:textarea path="remarks" htmlEscape="false"  class="form-control"/>
                            </div>
                        </div>
                            <c:if test="${fns:hasPermission('shop:goods:edit') || isAdd}">
                                <div class="col-lg-3"></div>
                                <div class="col-lg-6">
                                    <div class="form-group text-center">
                                        <div>
                                            <button class="btn btn-primary btn-block btn-lg btn-parsley"
                                                    data-loading-text="正在提交...">提 交
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(".sendProCode").on("click", function () {
        var code = $(".code").val();
        var index = "";
        if (!code) {
            layer.alert("产品编码不会能为空",{icon:2});
            return;
        }
        $.ajax({
            type: "POST",
            url: "${ctx}/shop/goods/sendProCode",
            beforeSend: function(){
                //遮罩层
                index = layer.load(1, {
                    shade: [0.1,'#fff'] //0.1透明度的白色背景
                });
            },
            data: {
                code: code
            },
            success: function (data) {
                //遮罩层隐藏
                layer.close(index);
               if(data.success){
                   if(data.body.result==""){
                       layer.alert("未查到相关产品",{icon:2});
                       return;
                   }
                    layer.alert(data.msg,{icon:1});
                    var res = data.body.result.simpleProduct;
                   var resImg = data.body.result.productPictures;
                    if(res){
                        //将新增时产品灰掉
                        $(".code").attr("readonly","readonly");
                        $(".detail").show();
                        //brandName
                        $("#brand").val(res.categoryId);
                        //产品名称
                        $("#name").val(res.productGroupName+res.name);
                    }
                    if(resImg){
                        //首先清空
                        $(".imgshow").empty();
                        var str = "";
                        for(var i=0;i<resImg.length; i++){
                            str += "<img src='"+ resImg[i].url +"' style='width: 70px;margin-right:10px'/>"
                        }
                        $(".imgshow").append(str);
                    }else{
                        $(".upload").addClass("active");
                        $(".img").hide()
                    }
               }else{
                   layer.alert(data.msg,{icon:2});
               }
            },
            error: function (err) {
                layer.alert(err+ "调取失败请联系管理员",{icon:2});
            }
        });
    })

    $(".return").on("click",function(){
        $("#code").val("");
        $(".code").removeAttr("readonly");
        $("#inputForm")[0].reset();
        $(".imgshow").empty();
        window.location.reload();
    });
    $(function(){
        //判断如果是编辑并且没有图片的情况
        <c:if test="${isAdd==false && imgList==''}">
             $(".img").hide();
             $(".upload").addClass("active");
        </c:if>
    })
</script>
    <style>
        .upload{
            position: absolute; opacity: 0; filter:Alpha(opacity=0);
        }
        .active{
            position: relative; opacity: 1; filter:Alpha(opacity=1);
        }
    </style>

</body>
</html>