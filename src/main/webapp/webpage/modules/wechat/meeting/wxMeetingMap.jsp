<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>E企碰碰地图获取</title>
    <style type="text/css">
        *{
            margin:0px;
            padding:0px;
        }
        body, button, input, select, textarea {
            font: 12px/16px Verdana, Helvetica, Arial, sans-serif;
        }
        p{
            width:603px;
            padding-top:3px;
            margin-top:10px;
            overflow:hidden;
        }
        #container {
            min-width:603px;
            min-height:767px;
        }
        .form-control{
            resize: none;
            height: 20px;
            padding: 6px 12px;
            font-size: 14px;
            color: #555555;
            background-color: #fff;
            background-image: none;
            border: 1px solid #ccc;
            border-radius: 0px;
            box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
            transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
            margin: 15px;
        }
        .btn{
            background: #ffffff;
            border: 1px solid #cfcfcf;
            color: #565656;
            padding: 10px 18px !important;
            border-radius: 3px;
            margin-left: 10px;
            cursor: pointer;
            font-size: 14px;
            background: #00b7ee;
            color: #fff;
            border-color: transparent;
        }
    </style>
    <script src="${ctxStatic}/plugin/qdcode/jquery.min.js" type="text/javascript"></script>
    <script charset="utf-8" src="https://map.qq.com/api/js?v=2.exp&key=V3BBZ-GDHWD-S5E4S-PSLEQ-ARCOZ-QQBP5"></script>
    <script>
        var searchService,map,markers = [];


        var init = function() {
            var center = new qq.maps.LatLng(36.128098,120.418129);
            map = new qq.maps.Map(document.getElementById('container'),{
                center: center,
                zoom: 13
            });
            qq.maps.event.addListener(
                map,
                'click',
                function(event) {
                    $("#l-position").text(event.latLng.getLng()+","+ event.latLng.getLat());
                    // alert('您点击的位置为:[' + event.latLng.getLng() +
                    //     ',' + event.latLng.getLat() + ']');
                }
            );

            var latlngBounds = new qq.maps.LatLngBounds();
            //调用Poi检索类
            searchService = new qq.maps.SearchService({
                complete : function(results){

                    var pois = results.detail.pois;
                    if(!pois){
                        alert("请输入详细或者正确地址");
                        return;
                    }
                    for(var i = 0,l = pois.length;i < l; i++){
                        var poi = pois[i];
                        latlngBounds.extend(poi.latLng);
                        var marker = new qq.maps.Marker({
                            map:map,
                            position: poi.latLng
                        });

                        marker.setTitle(i+1);

                        markers.push(marker);
                        qq.maps.event.addListener(
                            marker,
                            'click',
                            function(event) {
                                $("#l-position").text(event.latLng.getLng()+","+ event.latLng.getLat());
                            }
                        );
                    }
                    map.fitBounds(latlngBounds);
                }
            });
        }

        //调用poi类信接口
        function searchKeyword() {
            markers = [];
            var keyword = document.getElementById("keyword").value;
            region = new qq.maps.LatLng(36,120);

            searchService.setPageCapacity(5);
            searchService.searchNearBy(keyword,new qq.maps.LatLng(30,130),20000);//根据中心点坐标、半径和关键字进行周边检索。
        }
    </script>
</head>
<body onload="init()">
<div>
    <input id="keyword" class="form-control" type="textbox" value="">
    <input type="button" class="btn btn-primary" value="搜索" onclick="searchKeyword()">
    <%--<input id="l-position" class="form-control" type="textbox" value="">--%>
    <span id="l-position" style="margin-left: 20px;"></span>
</div>
<div id="container"></div>
</body>
</html>
