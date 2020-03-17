<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title></title>
    <script src="${ctxStatic}/resources/public/js/jquery-1.7.2.min.js" type="text/javascript" charset="utf-8"></script>
    <link href="${ctxStatic}/plugin/bootstrapTable/bootstrap-table.css" rel="stylesheet" />
    <link rel="stylesheet" href="${ctxStatic}/resources/css/header.css">
    <link rel="stylesheet" href="${ctxStatic}/resources/css/nav.css">
    <link rel="stylesheet" href="${ctxStatic}/resources/css/reset.css">
    <link rel="stylesheet" href="${ctxStatic}/resources/css/refrigerator.css">
    <link rel="stylesheet" href="${ctxStatic}/resources/css/sidebar.css">
    <link rel="icon" type="image/x-icon" href="http://10.138.106.102/resources/images/logot.png">
</head>
<style>
    /* 切换菜单 */
    #nav{
        border-bottom: 2px solid #005AAA;
    }

    .tabbar{
        height: 40px;
        color: #000000;
        position: relative;
    }

    .tabbar ul{
        cursor: pointer;
    }

    .tabbar>ul>li{
        line-height: 40px;
        font-size: 19px;
        float: left;
        margin-right: 30px;
    }

    .tabbar>ul>li:hover{
        color: #005AAA;
    }

    .tabbar>ul li.all-shop{
        margin-right: 60px;
        /*text-align: center;*/
        padding: 0px 15px;
        line-height: 40px;
        width:184px;
        height:40px;
        color: #FFFFFF;
        background-color: #0059A9;
        font-size: 14px;
        vertical-align: middle;
    }

    .tabbar>ul li.all-shop>img{
        display: inline-block;
        margin-right: 25px;
        text-align: left;
        width:18px;
        height:16px;
    }

    .tabbar>ul li.phone{
        margin-right: 20px;
        float: right;
        vertical-align: middle;
    }

    .tabbar>ul li.phone img:first-child{
        width: 30px;
        height: 30px;
    }

    .tabbar>ul li.phone img:last-child{
        margin-left: 5px;
        width: 156px;
        height: 22px;
    }
    .tabbar>ul li.commercial-active,.tabbar>ul li.engineering-active{
        position: relative;
        width: 100px;
        text-align: center;
    }
    .tabbar>ul>li>.commercial-list,.tabbar>ul>li>.engineering-list{
        display: none;
        width: 100px;
        text-align: center;
        background-color: #FFFFFF;
        position: absolute;
        top: 40px;
        z-index: 9999999;
        color: #000000;
    }
    .tabbar>ul>li>.commercial-list dl{
        display: none;
        width: 100px;
        text-align: center;
        background-color: #FFFFFF;
        position: absolute;
        top: 0;
        left: 100px;
        z-index: 9999999;
        color: #000000;
    }
    .tabbar>ul>li>.engineering-list>li:hover,.tabbar>ul>li>.commercial-list>li:hover{
        color: #005AAA;
    }
    .tabbar>ul>li>.commercial-list dl dt:hover{
        color: #005AAA;
    }

    .tabbar .menu-body-list{
        /* background-color: rgb(255, 255, 255); */
        display: none;
        height: 458px;
        position: absolute;
        top: 40px;
        left: 0;
        width: 214px;
        z-index: 10000;
        background-color: #0059A9;
        opacity: 1;
    }
    .tabbar .menu-body-list>ul{
        color: white;
        width: 214px;
        height: 458px;
        background-image: url(../images/menu.png);
    }
    .tabbar .menu-body-list>ul>li{
        /* height: calc(10% - 12px);
        line-height: normal;
        text-align: left;
        padding: 5px 0px;
        margin: 0px 10px;
        border-bottom: 1px solid rgba(234,234,234,0.38); */
        height: calc(10% - 12px);
        line-height: normal;
        text-align: left;
        padding: 5px 0px;
        margin: 0px 10px;
        border-bottom: 1px solid rgba(234,234,234,0.38);
        width: 93%;
        margin-left: 0;
        padding-left: 10px;
        padding-right: 5px;
    }
    .tabbar .menu-body-list>ul>li a h5{
        font-size: 14px;
        color: white;
        width: 80px;
        margin-left: 0;
        /* float: left; */
    }
    .tabbar .menu-body-list>ul>li>p{
        margin-top: 2px;
        font-size: 12px;
        color: white;
        width: 150px;
        float: left;
    }
    .tabbar .menu-body-list>ul>li>p>a>span{
        color:#fff;
    }
    .all-shop a:hover{
        color: #fff !important;
    }
    .secondary-list{
        width: 580px;
    }
    .secondary-list dl dd{
        height: 44px;
        width: 88%;
        margin-right: 5%;
        border-bottom: 1px solid #f6f6f6;
        line-height: 44px;
        padding-left: 10px;
    }
    .secondary-list dl dd:nth-child(10){
        border: none;
    }
    .secondary-list dl dd span{
        display:block;
        padding:0 10px;
        /* border-left: 1px solid #f6f6f6; */
        font-size: 12px;
        height: 22px;
        float: left;
        line-height: 22px;
		color: #333333;
    }
    .secondary-list dl dd span:nth-child(1){
        border: none;
    }
    .menu-body-list ul li div img{
        height: 15px;
        width: 15px;
        opacity: 0.5;
    }
    .gray{
        display:none;
    }
    /* .white{
        display: ;
    } */
    /*应用栏*/
    .application-bar {
        width: 100%;
        height: 56px;
        line-height: 56px;
        background-color: #0059A9;
        position: fixed;
        top: 0;
        z-index: 99999;
        display: none;
    }

    .application-bar img {
        height: 36px;
        width: 144px;
        vertical-align: middle;
        margin-top: 10px;
    }

    .application-bar .commodities {
        margin-left: 100px;
        color: white;
        cursor: pointer;
    }
    .commodities>div{
        width: 180px;
        height: 100%;
        /* background:red; */
        text-align: center;
        font-size: 14px;
    }
    .commodities .classify{
        /* background: pink; */
        width: 180px;
        margin-top: 0px;
        background-color: #0059A9;
        opacity: 0.8;
    }
    .commodities .classify li{
        border-bottom: 1px solid rgba(234,234,234,0.38);

        width: 160px;
        height: 45px;
        position: relative;
        /* background: skyblue; */
        margin-left: 10px;
        border-bottom: 1px solid #ccc;
        color: #fff;
    }
    .commodities .classify li a h5{
        font-size: 14px;
        position: absolute;
        left: 0px;
        top: -14px;
        color:#fff
    }
    .commodities .classify li span{
        display: inline-block;
        height: 12px;
        /* background: red; */
        color: #fff;
        font-size: 12px;
        margin-top:7px;
    }
    .commodities .classify li span:nth-child(2){
        margin-left: 5px;
    }
    .application-bar .seach {
        margin-left: 10px;
    }

    .application-bar .seach input {
        height: 26px;
        width: 410px;
        padding-left: 10px;
        border: none;
        border: 1px solid #FFFFFF;
        background-color: #0059A9;
        color: #fff;
    }
    input::-webkit-input-placeholder { /* WebKit browsers 适配谷歌 */
        color: #fff;
    }


    .application-bar .seach input::-webkit-input-placeholder {
        color: #b1cde7;
    }

    .application-bar .seach input::-moz-placeholder {
        /* Mozilla Firefox 19+ */
        color: #b1cde7;
    }

    .application-bar .seach input:-moz-placeholder {
        /* Mozilla Firefox 4 to 18 */
        color: #b1cde7;
    }

    .application-bar .seach input:-ms-input-placeholder {
        /* Internet Explorer 10-11 */
        color: #b1cde7;
    }

    .application-bar .seach button {
        height: 26px;
        width: 48px;
        border: none;
        margin-left: -5px;
        background-color: #003F77;
        border: 1px solid #FFFFFF;
        border-left: none;
        color: #FFFFFF;
    }

    .application-bar .inner-fun {
        margin-left: 20px;
    }

    .application-bar .inner-fun li {
        margin-left: 60px;
        font-size: 14px;
        color: white;
    }


</style>
<body>
<!-- 应用栏 -->
<div class="application-bar" id="appbar">
    <div class="container clearfix">
        <a href="http://b2b.haier.com" target="_blank"><img class="fl" src="${ctxStatic}/resources/images/logo3.png" alt=""></a>
        <div class="commodities fl">
            <div>全部商品分类▼</div>
            <ul class="classify" style="display: none;">

            </ul>
        </div>

        <div class="seach fl">
            <input type="text" id="searchTop" placeholder="搜产品 搜方案" >
            <button class="grabbleTop">搜索<tton>
        </div>
        <ul class="inner-fun fl">
            <li class="fl" style="cursor: pointer;">我的企业购</li>
                <!-- <li class="fl">购物车<> -->
            <li class="fl" style="cursor: pointer;" onclick="kf('one')">联系客服</li>
        </ul>
    </div>
</div>
    <div>
        <%@ page contentType="text/html;charset=UTF-8" %>
        <%@ include file="/webpage/include/taglib.jsp"%>
        <div class="header">
            <div class="top newTop clearfix">
                <div class="wrap clearfix">
                    <!-- city -->
                    <div class="city-box">
                        <!-- <img class="icon-location" src="${ctxStatic}/resources/images/dizhi.png" alt=""> -->
                        <!-- <span class="name">青岛</span> -->
                        <span class="des" style="cursor: pointer; display: none;">退出</span>
                    </div>
                    <!-- //city -->
                    <!-- nav -->
                    <div class="top-nav">
                        <a href="https://www.haier.com/cn/?fc=u1.c52933.g52934.k1526543331615.pz" target="_blank">我的订单▼</a> |
                        <a href="https://www.haier.com/" target="_blank">海尔官网</a> |
                        <a href="http://www.ehaier.com/" target="_blank">海尔智家商城</a> |
                        <!-- <a href="javascript:;">手机企业购</a> -->
                        <a href="http://b2b.haier.com/shop/supplier/view/user/purchase_login.html" target="_blank">我是采购商</a> |
                        <a href="http://bbs.haier.com/forum/dkh/" target="_blank">互动论坛</a>
                    </div>
                    <!-- //nav -->
                    <!-- login -->
                    <!-- <div class="top-login" id="toplogin1">
                        <a class="login" href="purchaser/view/user/login.html?ceshi">请登录</a>
                        <a class="register" href="purchaser/view/user/reg.html" target="_blank">免费注册</a>
                    </div>
                    <div class="top-login" id="toplogin">
                        欢迎<a id="phonenber" class="login" href="purchaser/view/user/login.html" target="_blank"></a>,
                        <a id="loginout" class="login">退出</a>
                    </div> -->
                </div>
            </div>
            <div class="head clearfix">
                <div class="wrap clearfix">
                    <a href="http://b2b.haier.com/" target="_blank">
                        <h1 class="logo" title="海尔企业购-企业社群生态平台">海尔企业购-企业社群生态平台</h1>
                    </a>
                    <div class="search-box">
                        <input type="text" id="search" style="padding-left: 10px;" class="search" value="" placeholder="搜产品 搜方案" style="color: #B1B1B1;">
                        <button class="grabble" type="button" >搜索</button>
                        <div class="popular">热门：<a>空气能</a>
                            <a>冰箱</a>
                            <a>扫码洗衣机</a>
                            <a>磁悬浮</a>
                            <a>多联机</a>
                            <a>太阳能热水器</a></div>
                        <!-- <a class="search-btn" href="javascript:;" onclick="checksearchform()"></a> -->
                        <form action="http://b2b.haier.com/templetPro.do" method="post" name="searchForm" id="secondForm">
                            <input type="hidden" name="siteid" value="f0a872bf-b883-4a0c-a1bd-86f3c3a71615">
                            <input type="hidden" name="templetPath" value="search.html">
                            <input type="hidden" name="key" class="key">
                        </form>
                    </div>
                    <div class="btn contact" onclick="kf('one')">
                        <img src="${ctxStatic}/resources/images/kefu.png" alt="">
                        <span>联系客服</span>
                    </div>
                    <!-- <button class="btn linear-gradient-top contact" type="button" onclick="javascript:_html.eventList.clickNTKF('one');">联系客服</button> -->
                </div>
            </div>
        </div>
    </div>
    <div id="nav">
        <div class="tabbar container">
            <ul class="clearfix">
                <!-- <a href="http://b2bwap.jhmis.net/b2b-shop/appliance.html"> -->
                <li class="all-shop"><img src="${ctxStatic}/resources/images/classification-icon.png" alt="">
                    <a href="http://b2b.haier.com/goodList" class="krte" target="_blank" style="width: 100%; height: 100%; color: #fff;">全部分类</a>
                    <div class="menu-body-list"  style="display: none;">
                        <ul style="opacity: 1;">

                        </ul>
                        <div class="secondary-list" style="width: 500px; height: 458px; display: block; background: #fff;color: black; position: absolute; z-index: 10; left: 214px; top: 0px;">
                            <dl>

                            </dl>
                        </div>
                    </div>
                </li>
                <!-- </a> -->
                <li><a href="" target="_blank" class="homePage" id="homePage"><span class="spanone"></span></a></li>
                <li class="commercial-active" id="commercial-active" style="font-size: 18px;"><a href="" target="_blank"><span></span></a>
                    <ul class="commercial-list" id="commercial-list" style="font-size: 14px;">
                        <li id="comm-zone">商用专区
                            <dl>
                                <dt class="commOne"><a href="" target="_blank">中央空调</a></dt>
                                <dt class="commTwo"><a href="" target="_blank">商用净水</a></dt>
                                <dt class="commThree"><a href="" target="_blank">U-HOME</a></dt>
                            </dl>
                        </li>

                        <li id="program">智慧方案
                            <dl>
                                <dt class="programOne"><a href="" target="_blank">智慧地产</a></dt>
                                <dt class="programTwo"><a href="" target="_blank">智慧酒店</a></dt>
                                <dt class="programTherr"><a href="" target="_blank">智慧教育</a></dt>
                                <dt class="programFour"><a href="" target="_blank">智慧公寓</a></dt>
                                <dt class="programFive"><a href="" target="_blank">智慧家庭</a></dt>
                            </dl>
                        </li>
                    </ul>
                </li>
                <li class="case"><a href="" target="_blank"><span></span></a></li>
                <li class="engineering-active" id="engineering-active" style="font-size: 18px;"><a href="" target="_blank"><span></span></a>
                    <ul class="engineering-list" id="engineering-list" style="font-size: 14px;">
                        <li><a href="" class="tyu">产品讲坛</a></li>
                        <li><a href="" class="ghj">工程智博士</a></li>
                        <li><a href="" class="vbn">新闻动态</a></li>
                    </ul>
                </li>
                <li class="ContractUser"><a href="" target="_blank"><span></span></a></li>
                <li class="service-center"><a href="" target="_blank"><span></span></a></li>
                <li class="phone">
                    <img src="${ctxStatic}/resources/images/phone.png" alt=""><img src="${ctxStatic}/resources/images/phone-number.png" alt=""></li>
            </ul>
        </div>

    </div>
    <div id="content">
        <div class="content-left">
            <div class="title" style="width: 134px">
                <span>—</span>
                <span></span>
                <span>—</span>
            </div>
            <div class="cut-off-rule"></div>
            <ul class="leftUi">
                <%--<c:forEach var="category" items="${listGoodsCategory}" varStatus="status">
                   <a href="/index/to/tocategorylisttwo?pageNo=1&pageSize=20&categoryId=${category.id}">
                    <li>${category.name} </li>
                   </a>
                </c:forEach>--%>

            </ul>
        </div>
        <div class="content-right">
            <div class="right-top">
                <ul class="sort">
                    <li style="cursor:pointer;" class="comprehensive">综合排序</li>
                    <li class="sales" style="cursor:pointer;">
                        <span>销量</span><img src="${ctxStatic}/resources/images/图层 31@2x.png" alt="">
                    </li>
                    <li class="collect" style="cursor:pointer;">
                        <span>收藏</span><img src="${ctxStatic}/resources/images/图层 31@2x.png" alt="">
                    </li>
                    <li class="price" style="cursor:pointer;">
                        <span>价格</span><img src="${ctxStatic}/resources/images/图层 31@2x.png" alt="">
                    </li>
                    <li class="page-view" style="cursor:pointer;">
                        <span>浏览量</span><img src="${ctxStatic}/resources/images/图层 31@2x.png" alt="">
                    </li>
                </ul>
            </div>
            <div class="right-bottom">
                <ul>

                 </ul>
            </div>
        </div>
    </div>
    <div id="flip-over">
        共<span class="count"></span>条记录 <button class="previous-page" style="background: #fff; border: none;">上一页</button> <span class="pages">1</span> <button class="next-page" style="background: #fff; border: none;">下一页</button>
    </div>
    <div id="right-sidebar">
        <div class="side-bar">
            <div class="inner-fun">
                <img src="${ctxStatic}/resources/images/fixed_icon_1.png" alt="">
                <p class="enquiry"><a href="http://b2b.haier.com/postwant/" target="_blank" style="color:#fff;">批量询价</a></p>
                <div onclick="kf('one')"><img src="${ctxStatic}/resources/images/fixed_icon_2.png" alt=""></div>
                <div id="wx_show">
                    <img src="${ctxStatic}/resources/images/fixed_icon_3.png" alt="">
                    <div class="floatimg_left" style="display: none;">
                        <img src="${ctxStatic}/resources/images/erweima.png" alt="" style="display: block;">
                        <p class="qrcord_p01">扫一扫</p>
                        <p class="qrcord_p02">
                            工程服务电话
                            <span>0532-88935920</span>
                            <span>17011153200</span>
                        </p>
                    </div>
                </div>
            </div>
            <div class="inner-bottom more-top"><img src="${ctxStatic}/resources/images/fixed_icon_4.png" alt=""></div>
        </div>


    </div>
<!-- footer -->
<div class="foot clearfix">
    <ul class="quick-menu">
    </ul>
    <div class="wrap clearfix">
        <!-- <a href="/" target="_blank" style="width:162px;height:45px">
            <div class="logo"></div>
        </a> -->
        <div class="menu">
            <dl>
                <dt>关于我们</dt>
                <dd>
                    <a href="http://b2b.haier.com/aboutAs?1" target="_blank">了解海尔企业购</a>
                    <a href="http://b2b.haier.com/aboutAs?2" target="_blank">商业愿景</a>
                    <a href="http://b2b.haier.com/aboutAs?3" target="_blank">合作伙伴</a>
                    <a href="http://b2b.haier.com/aboutAs?4" target="_blank">联系我们</a>
                    <a href="http://b2b.haier.com/aboutAs?5" target="_blank">加入我们</a>
                </dd>
            </dl>
            <dl>
                <dt>新手指南</dt>
                <dd>
                    <a href="http://b2b.haier.com/newbieGuide?1" target="_blank">注册新用户</a>
                    <a href="http://b2b.haier.com/newbieGuide?2" target="_blank">相关问题</a>
                    <a href="http://b2b.haier.com/newbieGuide?3" target="_blank">采购商入门</a>
                    <a href="http://b2b.haier.com/newbieGuide?4" target="_blank">供应商入门</a>
                </dd>
            </dl>
            <dl>
                <dt>服务与支持</dt>
                <dd>
                    <a href="http://b2b.haier.com/serveSupport?1" target="_blank">企业会员</a>
                    <a href="http://b2b.haier.com/serveSupport?2" target="_blank">物流体系</a>
                    <a href="http://b2b.haier.com/serveSupport?3" target="_blank">售后服务</a>
                    <a href="http://b2b.haier.com/serveSupport?4" target="_blank">服务优势</a>
                    <a href="http://b2b.haier.com/serveSupport?5" target="_blank">安装可视</a>
                </dd>
            </dl>
            <dl>
                <dt>帮助中心</dt>
                <dd>
                    <a href="http://b2b.haier.com/helpCenter?1" target="_blank">登录注册</a>
                    <a href="http://b2b.haier.com/helpCenter?2" target="_blank">产品选购</a>
                    <a href="http://b2b.haier.com/helpCenter?3" target="_blank">社区互动</a>
                    <a href="http://b2b.haier.com/helpCenter?4" target="_blank">法律声明</a>
                    <a href="http://b2b.haier.com/helpCenter?5" target="_blank">隐私和政策</a>
                </dd>
            </dl>
        </div>
        <div class="contact">
            <p>企业询价或答疑：0532-88935920</p>
            <p>周一至周日 9:00-18:00(不包括法定节假日)</p>
            <p>投诉及建议邮箱 haierqyg@haier.com</p>
            <p>客服QQ：2198543720</p>
        </div>
        <div class="QR-code">
            <p>
                <img alt="海尔企业购工程服务管家" src="${ctxStatic}/resources/images/fwgj_code.png">
                <span>海尔企业购
							<br>工程服务管家</span>
            </p>
            <p>
                <img alt="海尔企业用户交互平台" src="${ctxStatic}/resources/images/jhpt_code.png">
                <span>海尔企业用户
							<br>交互平台</span>
            </p>
        </div>
    </div>
</div>
<div class="copyright">
    <p>鲁ICP备09096283-1丨Copyright 2014-2019海尔集团 版权所有</p>
    <p>违法和不良信息举报电话：17011153200（周一到周日，9:00-18:00）</p>
    <p>地址：山东省青岛市崂山区海尔路1号</p>
</div>

</body>
<script src="${ctxStatic}/resources/js/refrigerator.js"></script>
<script>
    // console.log(window.location.search.split("?")[1].split("=")[1]);
    var brand = '';
    var count = ''; 
    var pages = 1;
    var arrt = '';
    var combined = '';
    var NumberData = '';
    var ddd = '';
    var ccc = 'http://b2b.haier.com/shop/index/to/tocategorylisttwo?pageNo=1&pageSize=20&categoryPid=' + brand;
    var Url = 'http://b2b.haier.com';
    <%--var Url = 'http://b2b.haier.com/shop';--%>
    var brandleft = '';
    var brandRight = ''

    //下拉菜单
    //侧边栏
    $.ajax({
        url:'http://b2b.haier.com/shop/api/shop/goods/categoryTree',
        success:function(res){
            // console.log('侧边栏');
            // console.log(res);
            var arre1 = res.body.result;
            for(var i = 0; i < arre1.length; i++){
                var arre2 = arre1[i].childNode;
                // console.log(arre2);
                var $div = '<li>'+
						'<a href="http://b2b.haier.com/goods?categoryPid='+arre1[i].id+'" target="_blank"><h5>'+arre1[i].name+'</h5></a>'+
						'<a href="http://b2b.haier.com/goods?categoryPid='+arre1[i].childNode[0].parentId+'&categoryid='+arre1[i].childNode[0].id+'" target="_blank"><p><span>'+arre1[i].childNode[0].name+'</span></a> <a href=":javascript"><span>/</span></a> <a href="http://b2b.haier.com/goods?categoryPid='+arre1[i].childNode[1].parentId+'&categoryid='+arre1[i].childNode[1].id+'" target="_blank"><span>'+arre1[i].childNode[1].name+'</span></p></a>'+
						<%--'<div class="white" style="float: right; margin-top: -10px;"><img src="${ctxStatic}/resources/images/dyh1" alt=""></div>'+--%>
						<%--'<div class="gray" style="float: right; margin-top: -10px;"><img src="${ctxStatic}/resources/images/dyh1" alt=""></div>'+--%>
                         '</li>';
                $('.menu-body-list>ul').append($div);
                $('.menu-body-list>div>dl').append('<dd style="display: table;"><div style="display: table-cell;vertical-align: middle;"></div></dd>');
                // console.log(arre2.length)
                for(var j=0; j < arre2.length; j++){
                    // console.log(arre2[j].name);
                    var div2 = '<a href="http://b2b.haier.com/goods?categoryPid='+arre2[j].parentId+'&categoryid='+arre2[j].id+'" target="_blank"><span>'+arre2[j].name+'</span></a>';
                    $('.menu-body-list>div>dl').find('dd>div').eq(i).append(div2);
                };
                var div4 = '<li>\n' +
            '                    <a href="http://b2b.haier.com/goods?categoryPid='+arre1[i].id+'" target="_blank"><h5>'+arre1[i].name+'</h5></a>\n' +
            '                    <a href="http://b2b.haier.com/goods?categoryPid='+arre1[i].childNode[0].parentId+'&categoryid='+arre1[i].childNode[0].id+'" target="_blank"><p><span>'+arre1[i].childNode[0].name+'</span></a> <a href=":javascript"><span>/</span></a> <a href="http://b2b.haier.com/goods?categoryPid='+arre1[i].childNode[1].parentId+'&categoryid='+arre1[i].childNode[1].id+'" target="_blank"><span>'+arre1[i].childNode[1].name+'</span></p></a\n' +
            '                </li>'
                $('.classify').append(div4);
            };
        }
    });
	//搜索功能
    $('.grabble').click(function(){
        var inputValue = $('#search').val();
        // console.log(inputValue);
        $('.right-bottom ul').html('');
        $('.content-left ul').html('');
        var assc = "http://b2b.haier.com/shop/index/to/tocategorylisttwo?pageNo=1&pageSize=20&key=" + inputValue;
        ddd = assc;
        $.ajax({
            url:assc,
            success:function(res){
                if(res.body.result.goodList.hasOwnProperty("list")){
                    $('title').text('海尔商品分类，海尔商品采购-海尔企业购|海尔产品采购官方网站');
                }else{
                    $('title').text('数据不存在')
                }
                ccc = assc;
                const Data = res.body.result.goodList.list;
                NumberData = res.body.result.goodList.count;
                combined = Math.ceil(NumberData / 20);
                $('.count').text(NumberData);
                if(combined == 1){
                    $('.next-page').attr('disabled',true);
                };
                if(combined > 1){
                    $('.next-page').attr('disabled',false);
                }
                if(pages > 1){
                    $('.previous-page').attr('disabled',false);
                }
                if(pages = 1){
                    $('.previous-page').attr('disabled',true);
                }
                insert(Data);
                var output = Data.slice(0,3);
                $(".content-left .title span").eq(1).html('精品推荐');
                output.forEach(function(item){
                    // console.log(item);
                    var imgSrc = item.mainPicUrl;
                    var arre = imgSrc.split('|');
                    var cvb = Url + item.htmlpath;
                    let imgStr = '';
                    if((arre[0].indexOf("http") >= 0)) {
                        imgStr = arre[0];
                    }else{
                        imgStr = 'http://b2b.haier.com/shop' + arre[0];
                    }
                    $(".content-left ul").append('<a href="'+cvb+'"  target="_blank" style=""><li style="width: 180px; height:270px ; position: relative; left: -18px; top: 10px;"><img style="width: 145px; height: auto; margin-left: 10px;" src="'+imgStr+'" /><p style="display: block; text-overflow:ellipsis;overflow:hidden;  width: 180px;height: 50px; font-size: 12px; text-align: center; margin-left: 0; z-index: 10;">'+item.goodsName+'<p/></li></a>');
                });
            }
        })
    });
    $('.grabbleTop').click(function(){
        var inputValue = $('#searchTop').val();
        // console.log(inputValue);
        $('.right-bottom ul').html('');
        $('.content-left ul').html('');
        var assc = "http://b2b.haier.com/shop/index/to/tocategorylisttwo?pageNo=1&pageSize=20&key=" + inputValue;
        ddd = assc;
        $.ajax({
            url:assc,
            success:function(res){
                if(res.body.result.goodList.hasOwnProperty("list")){
                    $('title').text('海尔商品分类，海尔商品采购-海尔企业购|海尔产品采购官方网站');
                }else{
                    $('title').text('数据不存在')
                }
                ccc = assc;
                const Data = res.body.result.goodList.list;
                NumberData = res.body.result.goodList.count;
                combined = Math.ceil(NumberData / 20);
                $('.count').text(NumberData);
                if(combined == 1){
                    $('.next-page').attr('disabled',true);
                };
                if(pages > 1){
                    $('.previous-page').attr('disabled',false);
                }
                if(pages = 1){
                    $('.previous-page').attr('disabled',true);
                }
                insert(Data);
                var output = Data.slice(0,3);
                $(".content-left .title span").eq(1).html('精品推荐');
                output.forEach(function(item){
                    // console.log(item);
                    var imgSrc = item.mainPicUrl;
                    var arre = imgSrc.split('|');
                    var cvb = Url + item.htmlpath;
                    let imgStr = '';
                    if((arre[0].indexOf("http") >= 0)) {
                        imgStr = arre[0];
                    }else{
                        imgStr = 'http://b2b.haier.com/shop' + arre[0];
                    }
                    $(".content-left ul").append('<a href="'+cvb+'"  style=""><li style="width: 180px; height:270px ; position: relative; left: -18px; top: 10px;"><img style="width: 145px; height: auto; margin-left: 10px;" src="'+imgStr+'" /><p style="display: block; text-overflow:ellipsis;overflow:hidden;  width: 180px;height: 50px; font-size: 12px; text-align: center; margin-left: 0; z-index: 10;">'+item.goodsName+'<p/></li></a>');
                });
            }
        })
    });
    //取得数据后渲染节点方法函数
    function insert(index){
        index.forEach(function(item){
            var imgSrc = item.mainPicUrl;
            var arre = imgSrc.split('|');
            var imgStr = '';
			var itemP = item.htmlpath;
			//判断图片地址是否存在
			if(arre[0] == ''){
				imgStr = '${ctxStatic}/resources/images/logoError.jpg'
			}else if((arre[0].indexOf("http") >= 0)) {
                imgStr = arre[0];
            }else{
                imgStr = 'http://b2b.haier.com/shop' + arre[0];
            };
			//判断url是否存在,如果不存在则跳转404
			if(itemP == ''){
				itemP = '/404/index.html'
			};
            var dfg =  Url + itemP;
            $(".right-bottom ul").append( 
                '<li style="margin-top: 10px; padding-bottom:28px; height: 300px" xmlns="http://www.w3.org/1999/html">' +
                '<div style=" display:none; font-size: 12px; width:90px ; height: 130px;"><p class="arre" style="height: 100%; width:100% ; overflow: hidden; text-overflow: ellipsis;display:-webkit-box; -webkit-box-orient:vertical;-webkit-line-clamp:8; "></p></div>'+
                '<div class="arr" style="position: relative;  margin-top:190px ; position: relative; top:12px; left:12px; height:70px ; width: 100px;">' +
                '</div>' +
                '    <div style="width:110px ; color:#4B8DC8;  margin-left:10px ; height: 0; margin-top: 0;">' +
                '    </div>' +
                '<span style="position:relative; top:10px; left:20px; font-size:14px; color:red;">￥ '+item.marketPrice+'</span>' +
                '<div style="width:210px   marginleft:20px;">' +
                '    <span style="color: #333333; margin-top:100px;">'+item.goodsName+'</span>'+
                '</div>' +
                '   <div style="width:200px ; height: 200px; margin-top: -300px; margin-left: 18px">' +
                '      <a href="'+dfg+'" target="_blank">'+
                '			<img style="width: 100%; height: 100%; line-height:270px; " src="'+imgStr+'" onerror="javascript:this.src=\'${ctxStatic}/resources/images/bianpin.png\'"  alt="">'+
                '		</a>' +
                '   </div>'+
                '</li>'
            )
        });
    };

    // var idOne =  'http://10.138.106.102:8090/shop/webpage/to_index/refrigerator.jsp?categoryPid=4c5563fece494b8296ed4150154adff4&categoryid=fa15bb2b7a8b426fbfa010d912684cfb';
    // var idOne = 'http://10.138.106.102:8090/shop/webpage/to_index/refrigerator.jsp?categoryPid=4c5563fece494b8296ed4150154adff4';
    var idOne = window.location.search;
    if(idOne.indexOf('value') != -1){
        a3 = idOne.split('?');
        a4 = a3[1].split('=');
        $('.count').text('1');
        $('.pages').text('1');
        pages = 1;
        $('.right-bottom ul').html('');
        $('.content-left ul').html('');
        $('.pages').text('1');
        var assc = "http://b2b.haier.com/shop/index/to/tocategorylisttwo?pageNo=1&pageSize=20&key=" + a4[1];
        $.ajax({
            url:assc,
            success:function(res){
                ccc = assc;
                ddd = assc;
                NumberData = res.body.result.goodList.count;
                combined = Math.ceil(NumberData / 20);
                $('.count').text(NumberData);
                if(res.body.result.goodList.hasOwnProperty("list")){
                    $('title').text('海尔商品分类，海尔商品采购-海尔企业购|海尔产品采购官方网站')
                }else{
                    $('title').text('数据不存在')
                }
                if(pages == combined){
                    $('.next-page').attr('disabled',true);
                };
                if(pages > 1){
                    $('.previous-page').attr('disabled',false);
                }
                if(pages = 1){
                    $('.previous-page').attr('disabled',true);
                }
                ccc = assc;
                const Data = res.body.result.goodList.list;
                NumberData = res.body.result.goodList.count;
                $('.count').text(NumberData);
                insert(Data);
                var output = Data.slice(0,3);
                $(".content-left .title span").eq(1).html('精品推荐');
                output.forEach(function(item){
                    var imgSrc = item.mainPicUrl;
                    var arre = imgSrc.split('|');
                    var cvb = Url + item.htmlpath;
                    let imgStr = '';
                    if((arre[0].indexOf("http") >= 0)) {
                        imgStr = arre[0];
                    }else{
                        imgStr = 'http://b2b.haier.com/shop' + arre[0];
                    }
                    $(".content-left ul").append('<a href="'+cvb+'"  style=""><li style="width: 180px; height:270px ; position: relative; left: -18px; top: 10px;"><img style="width: 145px; height: auto; margin-left: 10px;" src="'+imgStr+'" /><p style="display: block; text-overflow:ellipsis;overflow:hidden;  width: 180px;height: 50px; font-size: 12px; text-align: center; margin-left: 0; z-index: 10;">'+item.goodsName+'<p/></li></a>');
                });
            }
        })

    }else{
        if(idOne.includes('&')){
            a1 = idOne.split('?');
            a2 = a1[1].split('&');
            brandLeft = (a2[0].split('='))[1];
            brandRight = (a2[1].split('='))[1];
            $.ajax({
                url: "http://b2b.haier.com/shop/index/to/tocategorylisttwo?pageNo=1&pageSize=20&categoryPid=" + brandLeft,
                success: function(res){
                    $('title').text('海尔' + res.body.result.category.name  +'，海尔' + res.body.result.category.name + '采购，海尔' + res.body.result.category.name + '供应商-海尔企业购|海尔产品采购官方网站');
                    NumberData = res.body.result.goodList.count;
                    combined = Math.ceil(NumberData / 20);
                    var atty = res.body.result.listGoodsCategory;
                    $(".content-left .title span").eq(1).html(res.body.result.category.name);
                    atty.forEach(function(item){
                        $(".content-left ul").append('<div class="lefta" style="cursor: pointer; display: block; width: 189px; position: relative;left: -1px; padding-left: 10px;" onclick="dianji(this)" data-URL="http://b2b.haier.com/shop/index/to/tocategorylisttwo?pageNo=1&pageSize=20&categoryId=' + item.id + '"><li style="width: 100px;">' + item.name + '</li></div>');
                    });
                    for(var i = 0; i < atty.length; i++){
                        if(atty[i].id == brandRight){
                            $(".content-left ul").find('div').eq(i).css({'background':'#0059a9',"color":'#fff'});
                        }
                    }
                }
            });
            $.ajax({
                url:"http://b2b.haier.com/shop/index/to/tocategorylisttwo?pageNo=1&pageSize=20&categoryId=" + brandRight,
                success: function(res){
                    NumberData = res.body.result.goodList.count;
                    $('.count').text(NumberData);
                    combined = Math.ceil(NumberData / 20);
                    if(combined == 1){
                        $('.next-page').attr("disabled",true);
                    };
					if(combined > 1){
                        $('.next-page').attr("disabled",false);
                    };
                    if(pages == 1){
                        $('.previous-page').attr('disabled',true);
                    }
                    ddd = "http://b2b.haier.com/shop/index/to/tocategorylisttwo?pageNo=1&pageSize=20&categoryId=" + brandRight;
                    ccc = "http://b2b.haier.com/shop/index/to/tocategorylisttwo?pageNo=1&pageSize=20&categoryId=" + brandRight;
                    let aeew = res.body.result.goodList.list;
                    insert(aeew);
                }
            })
        }else{
            brand = window.location.search.split("?")[1].split("=")[1];
            $.ajax({
                url: "http://b2b.haier.com/shop/index/to/tocategorylisttwo?pageNo=1&pageSize=20&categoryPid=" + brand,
                success: function (res) {
                    ddd = "http://b2b.haier.com/shop/index/to/tocategorylisttwo?pageNo=1&pageSize=20&categoryPid=" + brand;
                    NumberData = res.body.result.goodList.count;
                    combined = Math.ceil(NumberData / 20);
                    $('title').text('海尔' + res.body.result.category.name  +'，海尔' + res.body.result.category.name + '采购，海尔' + res.body.result.category.name + '供应商-海尔企业购|海尔产品采购官方网站');
                    var data = res.body.result;
                    $(".content-left .title span").eq(1).html(data.category.name);
                    data.listGoodsCategory.forEach(function(item){
                        $(".content-left ul").append('<div class="lefta" style="cursor: pointer; display: block; width: 189px; position: relative;left: 0px; padding-left: 10px;" onclick="dianji(this)" data-URL="http://b2b.haier.com/shop/index/to/tocategorylisttwo?pageNo=1&pageSize=20&categoryId=' + item.id + '"><li style="width: 100px;">' + item.name + '</li></div>');
                    });
                    let information = data.goodList.list;
                    insert(information);
                    $('.count').text(data.goodList.count);
                    if(pages == 1){
                        $('.previous-page').attr('disabled',true);
                    }
                    if(combined == 1){
                        $('.next-page').attr("disabled",true);
                    }
                    if(combined > 1){
                        $('.next-page').attr("disabled",false);
                    };
                    $('.leftUi div').click(function () {
                        $(this).css('background','#005aaa').siblings().css('background','#fff');
                        $(this).css('color','#fff').siblings().css('color','#333333');
                    });

                }
            });
        };
    }
   // http://localhost:8080/webpage/to_index/refrigerator.jsp?categoryPid=4c5563fece494b8296ed4150154adff4
    //左侧按钮切换


    //左侧按钮切换不同数据
    function dianji(a){
        $('.count').text('0');
        $('.pages').text('1');
    	pages = 1;
       var bbb = a.dataset.url;
        ccc = bbb;
        ddd = bbb;
       $('.right-bottom ul').html('');
        $.ajax({
            type:'post',
            contentType : "application/x-www-form-urlencoded; charset=utf-8",
            url : ccc,
            success : function(data) {
            	 NumberData = data.body.result.goodList.count;
                 combined = Math.ceil(NumberData / 20);
                 if(combined > 1){
             		$('.next-page').attr("disabled",false);
             	};
                 // console.log('条数：' + NumberData);
                 // console.log('页数：' + combined);
                let information = data.body.result.goodList.list;
                insert(information);
                $('.count').text(data.body.result.goodList.count);
                $('.pages').text(pages);
                if(pages == combined){
               	 $('.next-page').attr("disabled",true);
                };
                if(pages == 1){
                  	 $('.previous-page').attr("disabled",true);
                }
            }
        });
    }

    //综合排序
    $('.comprehensive').click(function(){
        $('.right-bottom ul').html('');
        $.ajax({
            type:'post',
            contentType : "application/x-www-form-urlencoded; charset=utf-8",
            url : ddd,
            success : function(data) {
                NumberData = data.body.result.goodList.count;
                combined = Math.ceil(NumberData / 20);
                $('.pages').text('1');
    	        pages = 1;
                if(combined == 1){
                    $('.next-page').attr("disabled",true);
                };
                if(pages == 1){
                    $('.previous-page').attr("disabled",true);
                };
                if(combined > 1){
                    $('.next-page').attr("disabled",false);
                };
                let information = data.body.result.goodList.list;
                insert(information);
                $('.count').text(data.body.result.goodList.count);
            }
        });
    });


    //销量
    $('.sales').click(function(){
        $('.right-bottom ul').html('');
        ccc = ddd + '&' + 'goodsSalenum=0',
        $.ajax({
            type:'post',
            contentType : "application/x-www-form-urlencoded; charset=utf-8",
            url : ccc,
            success : function(data) {
                NumberData = data.body.result.goodList.count;
                combined = Math.ceil(NumberData / 20);
                $('.pages').text('1');
    	        pages = 1;
                if(combined == 1){
                    $('.next-page').attr("disabled",true);
                };
                if(pages == 1){
                    $('.previous-page').attr("disabled",true);
                };
                if(combined > 1){
                    $('.next-page').attr("disabled",false);
                };
                let information = data.body.result.goodList.list;
                insert(information);
                $('.count').text(data.body.result.goodList.count);
            }
        });
    });

    //收藏
    $('.collect').click(function(){
        $('.right-bottom ul').html('');
        ccc = ddd + '&' + 'goodsCollect=0',
        $.ajax({
            type:'post',
            contentType : "application/x-www-form-urlencoded; charset=utf-8",
            url : ccc,
            success : function(data) {
                NumberData = data.body.result.goodList.count;
                combined = Math.ceil(NumberData / 20);
                $('.pages').text('1');
    	        pages = 1;
                if(combined == 1){
                    $('.next-page').attr("disabled",true);
                };
                if(pages == 1){
                    $('.previous-page').attr("disabled",true);
                };
                if(combined > 1){
                    $('.next-page').attr("disabled",false);
                };
                let information = data.body.result.goodList.list;
                insert(information);
                $('.count').text(data.body.result.goodList.count);
            }
        });
    });

    //价格
    $('.price').click(function(){
        $('.right-bottom ul').html('');
        ccc = ddd + '&' + 'isPrice=0',
        $.ajax({
            type:'post',
            contentType : "application/x-www-form-urlencoded; charset=utf-8",
            url : ccc,
            success : function(data) {
                NumberData = data.body.result.goodList.count;
                combined = Math.ceil(NumberData / 20);
                $('.pages').text('1');
    	        pages = 1;
                if(combined == 1){
                    $('.next-page').attr("disabled",true);
                };
                if(pages == 1){
                    $('.previous-page').attr("disabled",true);
                };
                if(combined > 1){
                    $('.next-page').attr("disabled",false);
                };
                let information = data.body.result.goodList.list;
                insert(information);
                $('.count').text(data.body.result.goodList.count);
            }
        });
    });

    //浏览量
    $('.page-view').click(function(){
        $('.right-bottom ul').html('');
        ccc = ddd + '&' + 'goodsClick=0'
        $.ajax({
            type:'post',
            contentType : "application/x-www-form-urlencoded; charset=utf-8",
            url : ccc,
            success : function(data) {
                NumberData = data.body.result.goodList.count;
                combined = Math.ceil(NumberData / 20);
                $('.pages').text('1');
    	        pages = 1;
                if(combined == 1){
                    $('.next-page').attr("disabled",true);
                };
                if(pages == 1){
                    $('.previous-page').attr("disabled",true);
                };
                if(combined > 1){
                    $('.next-page').attr("disabled",false);
                };
                let information = data.body.result.goodList.list;
                insert(information);
                $('.count').text(data.body.result.goodList.count);
            }
        });
    })

  
	var aaa = '';
   	//下一页
    $('.next-page').click(function(){
    	$('.next-page').attr('disabled',true);
    	pages++;
		arrt = ccc.replace(/pageNo=1/g, 'pageNo=' + pages);
        $('.right-bottom ul').html('');
        $.ajax({
            type:'post',
            contentType : "application/x-www-form-urlencoded; charset=utf-8",
            url : arrt,
            success : function(data) {
            	$('.next-page').attr('disabled',false);
                NumberData = data.body.result.goodList.count || data.body.result.count;
                combined = Math.ceil(NumberData / 20);
                let information = data.body.result.goodList.list;
                const AnumberOf = data.body.result.goodList.count;
                combined = Math.ceil(AnumberOf / 20);
                insert(information);
                $('.count').text(data.body.result.goodList.count);
                $('.pages').text(pages);
                if(pages == combined){
                	$('.next-page').attr('disabled',true);
                };
                if(pages > 1){
                	$('.previous-page').attr('disabled',false);
                }
            }
        })
    });

    //上一页
    $('.previous-page').click(function(){
    	$('.next-page').attr('disabled',true);
    	if(pages <= combined){
    		pages--
    	};
		arrt = ccc.replace(/pageNo=1/g, 'pageNo=' + pages);
        $('.right-bottom ul').html('');
        $.ajax({
            type:'post',
            contentType : "application/x-www-form-urlencoded; charset=utf-8",
            url : arrt,
            success : function(data) {
            	$('.next-page').attr('disabled',false);
                NumberData = data.body.result.goodList.count;
                combined = Math.ceil(NumberData / 20);
                let information = data.body.result.goodList.list;
                const AnumberOf = data.body.result.goodList.count;
                combined = Math.ceil(AnumberOf / 20);
                insert(information);
                $('.count').text(data.body.result.goodList.count);
                $('.pages').text(pages);
                if(pages == 1){
                	$('.previous-page').attr('disabled',true);
                }else{
                	$('.previous-page').attr('disabled',false);
                }
            }
        })
    });
    
    //销量，收藏，，价格，浏览量点击之后的效果
    $('.sort').on('click','li',function(){
    	$(this).css('background','#005aaa').siblings().css('background','#fff');
    	$(this).css('color','#fff').siblings().css('color','#333333');
    })

    //客服
    function kf(a){
        var NTKF_PARAM = {
            siteid: "kf_9702", //企业ID，必填，取值参见文档开始表
            settingid: "kf_9702_1411625589685", //缺省客服配置ID，必填，取值参见文档开始表
            uid: "", //用户ID,未登录可以为空
            uname: "", //用户名，未登录可以为空
            isvip: "1", //是否为vip用户:1|0
            userlevel: "" //网站自定义会员级别
        }

        //加载脚本，再加载脚本前NTKF_PARAM一定已赋值，脚本加载后赋值是无效的
        var protocol = "https:" == location.protocol ? "https://" : "http://";
        if (a == "one") {
            NTKF_PARAM.settingid = "kf_9702_1411625589685";
        } else if (a == "two") {
            NTKF_PARAM.settingid = "kf_9702_1503904866750";
        }
        if ($(".ntalk-window-containter").length > 0) {
            return;
        }
        $.get(protocol + 'dl.ntalker.com/js/xn6/ntkfstat.js?siteid=kf_9702' + '&v=' + Math.random(), function() {
            NTKF.im_openInPageChat(NTKF_PARAM.settingid);
        }, 'script');
    }

    $('.popular').on('click','a',function(){
        $('.count').text('1');
        $('.pages').text('1');
        pages = 1;
        var pp = $(this).prop ('firstChild').nodeValue;
        $('.right-bottom ul').html('');
        $('.content-left ul').html('');
        $('.pages').text('1');
        var assc = "http://b2b.haier.com/shop/index/to/tocategorylisttwo?pageNo=1&pageSize=20&key=" + pp;
        $.ajax({
            url:assc,
            success:function(res){
                ccc = assc;
                NumberData = res.body.result.goodList.count;
                combined = Math.ceil(NumberData / 20);
                $('.count').text(NumberData);
                if(res.body.result.goodList.hasOwnProperty("list")){
                    $('title').text('海尔' + pp  +'，海尔' + pp + '采购，海尔' + pp + '供应商-海尔企业购|海尔产品采购官方网站')
                }else{
                    $('title').text('数据不存在')

                }
                if(pages == combined){
                    $('.next-page').attr('disabled',true);
                };
                if(pages > 1){
                    $('.previous-page').attr('disabled',false);
                }
                ccc = assc;
                const Data = res.body.result.goodList.list;
                NumberData = res.body.result.goodList.count;
                $('.count').text(NumberData);
                insert(Data);
                var output = Data.slice(0,3);
                $(".content-left .title span").eq(1).html('精品推荐');
                output.forEach(function(item){
                    // console.log(item);
                    var imgSrc = item.mainPicUrl;
                    var arre = imgSrc.split('|');
                    var cvb = Url + item.htmlpath;
                    let imgStr = '';
                    if((arre[0].indexOf("http") >= 0)) {
                        imgStr = arre[0];
                    }else{
                        imgStr = 'http://b2b.haier.com/shop' + arre[0];
                    }
                    $(".content-left ul").append('<a href="'+cvb+'"  style=""><li style="width: 180px; height:270px ; position: relative; left: -18px; top: 10px;"><img style="width: 145px; height: auto; margin-left: 10px;" src="'+imgStr+'" /><p style="display: block; text-overflow:ellipsis;overflow:hidden;  width: 180px;height: 50px; font-size: 12px; text-align: center; margin-left: 0; z-index: 10;">'+item.goodsName+'<p/></li></a>');
                });
            }
        })

    })
    //左侧点击效果
    $('.content-left ul').on('click','.lefta',function(){
        $(this).css({'background':'#0059A9','color':'#fff'}).siblings().css({'background':'#fff','color':'#333333'})
    });
	
	//判断当前是否为登录状态
	if(JSON.parse(localStorage.getItem("customerAdmin")) == null && JSON.parse(localStorage.getItem("purchaserAdmin")) == null){
		$('.des').css('display','none');
	}else{
		$('.des').css('display','inline-block')
	};
	
	//退出登录
	$('.des').click(function(){
		localStorage.removeItem('customerAdmin');　
		localStorage.removeItem('purchaserAdmin');
		$(this).css('display','none');
	});
</script>
<script src="${ctxStatic}/resources/public/js/jquery-1.7.2.min.js"></script>
<script>
    $("#wx_show").hover(function(){
        $(".floatimg_left").show();
    },function(){
        $(".floatimg_left").hide();
    })

    // 返回顶部
    $(".inner-bottom").click(function(){
        $('html').animate({scrollTop : 0},500);
    })

    //客服
    function kf(a){
        var NTKF_PARAM = {
            siteid: "kf_9702", //企业ID，必填，取值参见文档开始表
            settingid: "kf_9702_1411625589685", //缺省客服配置ID，必填，取值参见文档开始表
            uid: "", //用户ID,未登录可以为空
            uname: "", //用户名，未登录可以为空
            isvip: "1", //是否为vip用户:1|0
            userlevel: "" //网站自定义会员级别
        }

        //加载脚本，再加载脚本前NTKF_PARAM一定已赋值，脚本加载后赋值是无效的
        var protocol = "https:" == location.protocol ? "https://" : "http://";
        if (a == "one") {
            NTKF_PARAM.settingid = "kf_9702_1411625589685";
        } else if (a == "two") {
            NTKF_PARAM.settingid = "kf_9702_1503904866750";
        }
        if ($(".ntalk-window-containter").length > 0) {
            return;
        }
        $.get(protocol + 'dl.ntalker.com/js/xn6/ntkfstat.js?siteid=kf_9702' + '&v=' + Math.random(), function() {
            NTKF.im_openInPageChat(NTKF_PARAM.settingid);
        }, 'script');
    }
</script>


<script type="text/javascript">
    $(function() {
        var urlId = 'par';
        var urlId2 = '';
        var urlId3 = '';
        var urlId4 = '';
        var urlId5 = '';
        var data;
        var Gurl = ''
        $.ajax({
            url: "http://b2b.haier.com/shop/api/daohang/getdaohang?parid=" + urlId,
            success: function (res) {
                // console.log('链接');
                // console.log(res);
                data = res.body.result;
                // console.log('data:' + data);
                //二级菜单url需要的id
                urlId2 = res.body.result[1].id;
                urlId3 = res.body.result[3].id;
                //添加文本内容
                $('.homePage span').text(data[0].name);
                $('.commercial-active span').text(data[1].name);
                $('.case span').text(data[2].name);
                $('.engineering-active span').text(data[3].name);
                $('.ContractUser span').text(data[4].name);
                $('.service-center span').text(data[5].name)
                //添加url
                str1 = data[0].pageUrl;
                Gurl = str1;
                // console.log('gurl: ' + Gurl);
                $('.homePage').attr('href',data[0].pageUrl);
                $('.commercial-active a').attr('href',data[0].pageUrl + data[1].pageUrl);
                $('.case a').attr('href',data[0].pageUrl + data[2].pageUrl);
                $('.engineering-active a').attr('href',data[3].pageUrl);
                $('.ContractUser a').attr('href',data[4].pageUrl);
                $('.service-center a').attr('href',data[5].pageUrl);
                //二级菜单url
                $.ajax({
                    url: "http://b2b.haier.com/shop/api/daohang/getdaohang?parid=" + urlId2,
                    success: function (res) {
                        // console.log('商用方案' + res);
                        // console.log(res);
                        urlId4 = res.body.result[0].id;
                        urlId5 = res.body.result[1].id;
                        // console.log('id4: ' + urlId4);
                        // console.log('id5: ' + urlId5);
                        $.ajax({
                            url:"http://b2b.haier.com/shop/api/daohang/getdaohang?parid=" + urlId4,
                            success: function(res) {
                                // console.log('商用方案三级菜单1：');
                                // console.log(res);
                                $('.commOne').find('a').attr('href',Gurl + res.body.result[0].pageUrl);
                                $('.commTwo').find('a').attr('href',Gurl + res.body.result[1].pageUrl);
                                $('.commTherr').find('a').attr('href',Gurl + res.body.result[2].pageUrl);
                            }
                        });
                        $.ajax({
                            url:"http://b2b.haier.com/shop/api/daohang/getdaohang?parid=" + urlId5,
                            success: function(res) {
                                // console.log('商用方案三级菜单2：');
                                // console.log(res);
                                $('.programOne').find('a').attr('href',Gurl + res.body.result[0].pageUrl);
                                $('.programTwo').find('a').attr('href',Gurl + res.body.result[1].pageUrl);
                                $('.programThree').find('a').attr('href',Gurl + res.body.result[2].pageUrl);
                                $('.programFour').find('a').attr('href',Gurl + res.body.result[3].pageUrl);
                                $('.programFive').find('a').attr('href',Gurl + res.body.result[4].pageUrl);
                            }
                        });
                    }
                });
                $.ajax({
                    url: "http://b2b.haier.com/shop/api/daohang/getdaohang?parid=" + urlId3,
                    success: function (res) {
                        // console.log('工程讲堂' + res);
                        // console.log(res);
                        Elist = res.body.result;

                        $('.tyu').attr('href',Gurl + Elist[0].pageUrl);
                        $('.ghj').attr('href',Gurl + Elist[1].pageUrl);
                        $('.vbn').attr('href',Gurl + Elist[2].pageUrl);
                    }
                });
            }
        });


        $('.clearfix').on('mouseenter','.commercial-active',function () {
            $('.commercial-list').css('display','block');
        });
        $('.clearfix').on('mouseleave','.commercial-active',function () {
            $('.commercial-list').css('display','none');
        });
        $('#comm-zone').mouseenter(function () {
            $(this).find('dl').css('display','block');
        });
        $('#comm-zone').mouseleave(function () {
            $(this).find('dl').css('display','none');
        });
        $('#program').mouseenter(function () {
            $(this).find('dl').css('display','block');
        });
        $('#program').mouseleave(function () {
            $(this).find('dl').css('display','none');
        });
        $('.engineering-active').mouseenter(function () {
            $(this).find('ul').css('display','block')
        });
        $('.engineering-active').mouseleave(function () {
            $(this).find('ul').css('display','none')
        });


        //侧边栏代码
        $('.menu-body-list').mouseenter(function(){
        	$('.menu-body-list>div').css('display','block');
        })
        $('.menu-body-list').mouseleave(function(){
        	$('.menu-body-list>div').css('display','none');
        })
        //二级菜单代码
        $('.menu-body-list>ul').on('mouseenter','li p a span',function(){
        	$(this).css('color','#333333');
        })

        $('.menu-body-list>ul').on('mouseenter','li',function(){
            var arre;
            arre = $(this).index();
            // console.log(arre);
            $('.secondary-list>dl').find('dd').eq(arre).find('p a>span').css('color','#0059a9');
            $('.secondary-list>dl').find('dd').eq(arre).siblings().find('p a span').css('color','black');
            $(this).css({
                'background':'#ffffff',
            }).siblings().css({
                'background':'none',
            });
            $(this).find('h5').css('color','#000000');
            $(this).find('p a span').css('color','#000000');
            $(this).find('.white').css('display','none').siblings('.gray').css('display','block');
        })

        $('.menu-body-list>ul').on('mouseleave','li',function(){
            $(this).find('h5').css('color','#ffffff')
            $(this).find('p a span').css('color','#ffffff')
            $(this).find('.white').css('display','block').siblings('.gray').css('display','none');
            $(this).css('background','none')
        })
        $('.secondary-list>dl').on('mouseenter','dd span',function(){
            $(this).css('color','#0059a9').siblings().css('color','black');
        })
        $('.secondary-list>dl').on('mouseleave','dd span',function(){
            $(this).css('color','black');
        })
        $('.secondary-list>dl').on('mouseenter','dd',function(){
            var arre3 = $(this).index();
            // console.log(arre3);
            arre4 = $('.menu-body-list>ul').find('li');
            arre4.eq(arre3).css('background','#ffffff');
            arre4.eq(arre3).find('h5').css('color','#000000');
            arre4.eq(arre3).find('p a span').css('color','#000000');
            arre4.eq(arre3).find('.white').css('display','none').siblings('.gray').css('display','block');
        })
        $('.secondary-list>dl').on('mouseleave','dd',function(){
            var arre3 = $(this).index();
            // console.log(arre3);
            arre4 = $('.menu-body-list>ul').find('li').eq(arre3);
            arre4.css('background','none');
            arre4.find('h5').css('color','#fff');
            arre4.find('p a span').css('color','#fff');
            arre4.find('.white').css('display','block').siblings('.gray').css('display','none');
        })


        $(".all-shop").hover(function() {
            $(".menu-body-list").show();
        }, function() {
            $(".menu-body-list").hide();
        })
        // $('.service-center').click(function(){
        //     alert('暂未上线，敬请期待！')
        // })
        // 应用栏
        $(".commodities").hover(function() {
            $(".classify").show();
        }, function() {
            $(".classify").hide();
        })
        // 监听应用栏的显示和隐藏
        $(window).scroll(function() {
            //为了保证兼容性，这里取两个值，哪个有值取哪一个
            //scrollTop就是触发滚轮事件时滚轮的高度
            var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
            // console.log("滚动距离" + scrollTop);
            if(scrollTop >= 222){
                $("#appbar").show();
            }else{
                $("#appbar").hide();
            }
        })
    })
</script>


</html>