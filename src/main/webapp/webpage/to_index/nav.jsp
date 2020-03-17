<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<div class="tabbar container">
    <ul class="clearfix">
        <!-- <a href="http://b2bwap.jhmis.net/b2b-shop/appliance.html"> -->
        <li class="all-shop"><img src="${ctxStatic}/resources/images/classification-icon.png" alt="">
            <a href="${fns:getBaseUrl()}/goodList" target="_blank" style="width: 100%; height: 100%; color: #fff;">全部商品分类</a>
            <!-- <div class="menu-body-list" style="display: none;">
                <ul>
                    <li>
                        <h5>中央空调</h5>
                        <p><span>多联机</span><span>风管机</span></p>
                    </li>
                    <li>
                        <h5>家用空调</h5>
                        <p><span>壁挂机</span><span>柜机</span></p>
                    </li>
                    <li>
                        <h5>冷柜</h5>
                        <p><span>厨房冰箱</span><span>展示柜</span></p>
                    </li>
                    <li>
                        <h5>热水器</h5>
                        <p><span>空气能</span><span>采暖炉</span></p>
                    </li>
                    <li>
                        <h5>洗衣机</h5>
                        <p><span>扫码洗衣机</span><span>干洗机</span></p>
                    </li>
                    <li>
                        <h5>电视</h5>
                        <p><span>拼接屏</span><span>智慧黑板</span></p>
                    </li>
                    <li>
                        <h5>冰箱</h5>
                        <p><span>双门</span><span>多门</span></p>
                    </li>
                    <li>
                        <h5>厨电</h5>
                        <p><span>油烟机</span><span>燃气灶</span></p>
                    </li>
                    <li>
                        <h5>智能家电</h5>
                        <p><span>指纹锁</span><span>门禁</span></p>
                    </li>
                    <li>
                        <h5>净水</h5>
                        <p><span>直饮机</span><span>开水器</span></p>
                    </li>
                </ul>
            </div> -->
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
<script type="text/javascript">
    $(function() {
    	var urlId = 'par';
        var urlId2 = '';
        var urlId3 = '';
        var urlId4 = '';
        var urlId5 = '';
    	var data;
    	var Gurl = ''
    	console.log('nav');
	    $.ajax({
	        url: "http://b2b/haier.com/shop/api/daohang/getdaohang?parid=" + urlId,
	        success: function (res) {
	            console.log('链接');
	    		console.log(res);
	    		data = res.body.result;
	    		console.log('data:' + data);
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
                Gurl = str1.substr(0, str1.length - 1);
                console.log('gurl: ' + Gurl);
                $('.homePage').attr('href',data[0].pageUrl);
                $('..commercial-active a').attr('href',data[0].pageUrl + data[1].pageUrl);
                $('.case a').attr('href',data[0].pageUrl + data[2].pageUrl);
                $('.engineering-active a').attr('href',data[3].pageUrl);
                $('.ContractUser a').attr('href',data[4].pageUrl);
                $('.service-center a').attr('href',data[5].pageUrl);
                //二级菜单url
                $.ajax({
                    url: "http://b2b/haier.com/shop/api/daohang/getdaohang?parid=" + urlId2,
                    success: function (res) {
                        console.log('商用方案' + res);
                        console.log(res);
                        urlId4 = res.body.result[0].id;
                        urlId5 = res.body.result[1].id;
                        console.log('id4: ' + urlId4);
                        console.log('id5: ' + urlId5);
                        $.ajax({
                            url:"http://b2b/haier.com/shop/api/daohang/getdaohang?parid=" + urlId4,
                            success: function(res) {
                                console.log('商用方案三级菜单1：');
                                console.log(res);
                                $('.commOne').find('a').attr('href',Gurl + res.body.result[0].pageUrl);
                                $('.commTwo').find('a').attr('href',Gurl + res.body.result[1].pageUrl);
                                $('.commTherr').find('a').attr('href',Gurl + res.body.result[2].pageUrl);
                            }
                        });
                        $.ajax({
                            url:"http://b2b/haier.com/shop/api/daohang/getdaohang?parid=" + urlId5,
                            success: function(res) {
                                console.log('商用方案三级菜单2：');
                                console.log(res);
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
                    url: "http://b2b/haier.com/shop/api/daohang/getdaohang?parid=" + urlId3,
                    success: function (res) {
                        console.log('工程讲堂' + res);
                        console.log(res);
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


    })
</script>
