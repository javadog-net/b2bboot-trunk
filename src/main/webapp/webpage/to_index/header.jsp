<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>

<div class="header">
    <div class="top newTop clearfix">
        <div class="wrap clearfix">
            <!-- city -->
            <div class="city-box">
                <!-- <img class="icon-location" src="${ctxStatic}/resources/images/dizhi.png" alt=""> -->
                <!-- <span class="name">青岛</span> -->
                <span class="des" style="display: none; cursor: pointer;">退出</span>
            </div>
            <!-- //city -->
            <!-- nav -->
            <div class="top-nav">
                <a href="https://www.haier.com/cn/?fc=u1.c52933.g52934.k1526543331615.pz" target="_blank">我的订单▼</a> |
                <a href="https://www.haier.com/" target="_blank">海尔官网</a> |
                <a href="http://www.ehaier.com/" target="_blank">顺逛</a> |
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
                <input type="text" id="search" class="search" value="" placeholder="搜产品 搜方案" style="color: #B1B1B1;">
                <button class="grabble" type="button" ><a style=" width: 100%; height: 100%; color: #fff;" href="" target="_blank">搜索</a></button>
                <div class="valTltie" style="color: red; display: none;">输入不能为空</div>
                <div class="popular">热门：<a  href="" target="_blank">空气能</a>
                    <a href="" target="_blank">冰箱</a>
                    <a href="" target="_blank">扫码洗衣机</a>
                    <a href="" target="_blank">磁悬浮</a>
                    <a href="" target="_blank">多联机</a>
                    <a href="" target="_blank">太阳能热水器</a></div>
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
<script>
	//判断当前是否为登录状态
	if(JSON.parse(localStorage.getItem("customerAdmin")) == null && JSON.parse(localStorage.getItem("purchaserAdmin")) == null){
		$('.des').css('display','none');
	}else{
		$('.des').css('display','inline-block');
	};
	
	//退出登录
	$('.des').click(function(){
		localStorage.removeItem('customerAdmin');　
		localStorage.removeItem('purchaserAdmin')
		$('.des').css('display','none');
	});
	
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

    $('.grabble>a').click(function () {
        let anno = $('.search').val();
        console.log(anno);
        if(anno != ''){
            $(this).attr('href', '/webpage/to_index/refrigerator.jsp?value=' + anno);
            console.log(anno);
        }else{
            console.log(anno);
            $('.valtltie').css('display','block');
            return false
        }
    });
    $('.popular').on('click','a',function () {
        let awm = $(this).prop ('firstChild').nodeValue;
        console.log(awm);
        $(this).attr('href','/webpage/to_index/refrigerator.jsp?value=' + awm);
    })
    // $('.popular a').hover(function () {
    //     $(this).css('color','#fff')
    // })
</script>