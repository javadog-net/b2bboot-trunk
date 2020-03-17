<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
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