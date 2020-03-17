<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>全站静态化</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp"%>
    <%@include file="/webpage/include/treeview.jsp" %>
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
        .form-group label{
            padding: 0 10px;
        }
        .maxpage{
            display: inline-block;
            height: 30px;
            padding: 4px 6px;
            margin-bottom: 10px;
            font-size: 14px;
            line-height: 20px;
            color: #555555;
            border-radius: 4px;
            border: 1px solid #cccccc;
        }
        .alert-info{
            background-color: #d9edf7;
            border-color: #bce8f1;
            color: #3a87ad;
        }
        .alert {
            padding: 8px 35px 8px 14px;
            margin-bottom: 20px;
            text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
            background-color: #fcf8e3;
            border: 1px solid #fbeed5;
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            border-radius: 4px;
            color: #c09853;
        }
        .alert .close {
            position: relative;
            top: -2px;
            right: -21px;
            line-height: 20px;
        }
        button.close {
            padding: 0;
            cursor: pointer;
            background: transparent;
            border: 0;
            -webkit-appearance: none;
        }
        .close {
            float: right;
            font-size: 20px;
            font-weight: bold;
            line-height: 20px;
            color: #000000;
            text-shadow: 0 1px 0 #ffffff;
            opacity: 0.2;
            filter: alpha(opacity=20);
        }
    </style>
    <script>
        $(function(){
            // 全站静态化强度切换
            $('#Strength').change(function () {
                if ($(this).val() == "0"){
                    $('#timing').hide();
                    $('#interval').hide();
                }
                if ($(this).val() == "1"){
                    $('#timing').show();
                    $('#interval').hide();
                }
                if ($(this).val() == "2"){
                    $('#interval').show();
                    $('#timing').hide();
                }
            })
            $('#branch').hide();
            // 间隔单位切换
            $('#interval_date').change(function () {
                if ($(this).val() == "0"){
                    $('#time').show();
                    $('#branch').hide();
                }
                if ($(this).val() == "1"){
                    $('#branch').show();
                    $('#time').hide();
                }
            })
        });
    </script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <form id="inputForm" class="form-horizontal" modelAttribute="htmlquartz">
            <input type="hidden" name="id" value="${id}"/>
        <div class="panel-body">
            <div class="alert alert-info">
                <button class="close" data-dismiss="alert">×</button>
                <strong>提示!</strong>
                全站静态化会静态化此站点的所有页面，您也可以设置调度来自动全站静态化！如果数据量比较大，请慎用此功能！
            </div>
            <div>
                <label class="control-label" style="width: 100px;margin-left: 5px">全站静态化调度</label>
                <div class="controls" style="margin-left: 120px;display: inline-block">
                    <select class="form-control m-b" name="type" id="Strength">
                        <option value="0">无</option>
                        <option value="1" ${"1" == htmlquartz.type ?"selected":"" }>定时全站静态化</option>
                        <option value="2" ${"2" == htmlquartz.type ?"selected":"" }>间隔重复全站静态化</option>
                    </select>
                </div>
                <div id="timing" style="margin-left: 20px;display:${"1" == htmlquartz.type ?" inline-block":"none"}">
                    <div style="display:${"1" == htmlquartz.type ?" inline-block":"none"}">
                        <select class="form-control m-b" id="timing_time" name="exetimeHour">
                            <c:forEach items="${hours}" var="obj" varStatus="status">
                                <option value="${obj}" ${obj == htmlquartz.exetimeHour?"selected":""}>
                                    ${obj}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <span>时</span>
                    <div style="display: inline-block">
                        <select class="form-control m-b" id="timing_branch" name="exetimeMin">
                            <c:forEach items="${mins}" var="obj" varStatus="status">
                                <option value="${obj}" ${obj == htmlquartz.exetimeMin?"selected":""}>
                                        ${obj}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <span>分</span>
                </div>
                <div id="interval" style="margin-left: 20px;display:${"2" == htmlquartz.type ?" inline-block":"none"}">
                    <span>间隔单位</span>
                    <div style="display: ${"2" == htmlquartz.type ?" inline-block":"none"}">
                        <select class="form-control m-b" id="interval_date" name="intervalType">
                            <option value="0" ${"0" == htmlquartz.intervalType?"selected":"" }>小时</option>
                            <option value="1" ${"1" == htmlquartz.intervalType?"selected":"" }>分钟</option>
                        </select>
                    </div>
                    <span id="time">
                        <div>
                        <select class="form-control m-b" id="interval_time" name="intervalHour">
                             <c:forEach items="${hours}" var="obj" varStatus="status">
                                <option value="${obj}" ${obj == htmlquartz.intervalHour?"selected":""}>
                                        ${obj}
                                </option>
                             </c:forEach>
                        </select>
                    </div>
                    <span>时</span>
                    </span>
                    <span id="branch">
                        <div>
                        <select class="form-control m-b" id="interval_branch" name="intervalMin">
                            <c:forEach items="${mins}" var="obj" varStatus="status">
                                <option value="${obj}" ${obj == htmlquartz.intervalMin?"selected":""}>
                                        ${obj}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <span>分</span>
                    </span>

                </div>
            </div>
        </div>
        <!-- 工具栏 -->
        <div id="toolbar" style="margin: 10px">
            <a id="htmlAll" class="btn btn-success"  onclick="htmlAllDo()">
                <i class="glyphicon glyphicon-edit"></i>  全站静态化
            </a>
            <a id="htmlSet" class="btn btn-default"  onclick="htmlAllSet()">
                <i class="glyphicon glyphicon-plus"></i>  设置静态化调度
            </a>
        </div>
    </form>
    </div>
</div>
<script type="text/javascript">
    function htmlAllDo() {
        var url = "${ctx}/cms/htmlAllDo";
        layer.confirm('全站静态化耗时较长，确定要执行此操作吗?',{btn: ['确定', '取消'],title:"提示"}, function(){
            $.ajax({
                type: "post",
                url: url,
                data: null,
                dataType: "json",
                async:false,
                success:function(data) {
                    if(data.success){
                        layer.msg('全站静态化成功', {icon: 1});
                    }else{
                        layer.msg('全站静态化失败', {icon: 2});
                    }
                }
            });
        });
    }

    function htmlAllSet() {
        var url = "${ctx}/cms/htmlAllSet";
        var data = $("#inputForm").serialize();
        layer.confirm('您确定要设置静态化调度吗?',{btn: ['确定', '取消'],title:"提示"}, function(){
            $.ajax({
                type: "post",
                url: url,
                data: data,
                dataType: "json",
                async:false,
                success:function(data) {
                    if(data.success){
                        layer.msg('静态化调度设置成功', {icon: 1});
                    }else{
                        layer.msg('静态化调度设置失败', {icon: 2});
                    }
                }
            });
        });
    }
</script>
</body>
</html>