<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="description" content="">
    <meta name="author" content="">
    <style>
        body, html {
            background-color: #F9F9F9;
        }

        body {
            font-size: 14px;
            color: #333333;
            line-height: 20px;
        }

        .btn {
            display: inline-block;
            padding: 4px 12px;
            margin-bottom: 0;
            font-size: 14px;
            line-height: 20px;
            text-align: center;
            vertical-align: middle;
            cursor: pointer;
            color: #333333;
            text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
            background-color: #f5f5f5;
            background-image: -moz-linear-gradient(top, #ffffff, #e6e6e6);
            background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#ffffff), to(#e6e6e6));
            background-image: -webkit-linear-gradient(top, #ffffff, #e6e6e6);
            background-image: -o-linear-gradient(top, #ffffff, #e6e6e6);
            background-image: linear-gradient(to bottom, #ffffff, #e6e6e6);
            background-repeat: repeat-x;
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffffff', endColorstr='#ffe6e6e6', GradientType=0);
            border-color: #e6e6e6 #e6e6e6 #bfbfbf;
            border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
            filter: progid:DXImageTransform.Microsoft.gradient(enabled=false);
            border: 1px solid #bbbbbb;
            border-bottom-color: #a2a2a2;
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            border-radius: 4px;
            -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, .2), 0 1px 2px rgba(0, 0, 0, .05);
            -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, .2), 0 1px 2px rgba(0, 0, 0, .05);
            box-shadow: inset 0 1px 0 rgba(255, 255, 255, .2), 0 1px 2px rgba(0, 0, 0, .05);
        }

        .btn:hover {
            color: #333333;
            text-decoration: none;
            background-color: #e6e6e6;
            background-position: 0 -15px;
            -webkit-transition: background-position 0.1s linear;
            -moz-transition: background-position 0.1s linear;
            -o-transition: background-position 0.1s linear;
            transition: background-position 0.1s linear;
        }

        input[type="checkbox"] {
            vertical-align: middle;
        }

        .form-horizontal .control-group {
            border-top: 1px solid #ffffff;
            border-bottom: 1px solid #eeeeee;
            margin-bottom: 0;
        }

        .form-horizontal .controls {
            padding: 10px 0;
        }

        input[type="submit"] {
            cursor: pointer;
        }

        .btn-primary {
            color: #ffffff;
            text-shadow: 0 -1px rgba(0, 0, 0, 0.25);
            background-color: #006dcc;
            border: none;
            background-image: linear-gradient(to bottom, #0088cc, #0044cc);
            background-repeat: no-repeat;
        }

        .btn-primary:hover, .btn-primary:active, .btn-primary.active, .btn-primary.disabled, .btn-primary[disabled] {
            color: #ffffff;
            background-color: #0044cc;
        }

    </style>
</head>
<body>
<div class="container-fluid" style="margin-top:-50px">
    <div class="row-fluid">

        <div class="widget-box">
            <div class="widget-title">
								<span class="icon">
									<i class="icon-edit"></i>
								</span>
                <h5>静态化</h5>
            </div>
            <div class="widget-content nopadding">

                <div class="widget-content nopadding">

                    <form class="form-horizontal" method="post" action="goodsMakehtml.do" name="form" id="form">
                        <input type="hidden" name="id" id="id" value="${goods.id }">

                        <div class="control-group">
                            <div class="controls" style="margin-left: 190px;">
                                操作成功，请选择需要静态化的内容。
                            </div>
                        </div>

                        <div class="control-group">
                            <div class="controls" style="margin-left: 190px;">
                                <input type="checkbox" name="htmlCategory" value="1" checked/>所属栏目页静态化
                            </div>
                        </div>
                        <div class="control-group">
                            <div class="controls" style="margin-left: 190px;">
                                <input type="checkbox" name="htmlCategoryPar" value="1" checked/>所属栏目的所有父栏目页面静态化
                            </div>
                        </div>
                        <div class="control-group">
                            <div class="controls" style="margin-left: 190px;">
                                <input type="checkbox" name="htmlIndex" value="1" checked/>站点首页静态化
                            </div>
                        </div>
                        <div class="control-group">
                            <div class="controls" style="margin-left: 190px;">
                                <input type="submit" class="btn btn-primary" value="立即静态化"/>&nbsp;
                                <input type="button" value="不，我要继续添加商品"
                                       onclick="location.href='form.do?cat.id=${goods.cat.id}&&cat.name=${goods.cat.name}'"
                                       class="btn"/>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div><!--/row-->


</body>
</html>