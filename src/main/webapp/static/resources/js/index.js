$(function() {
	// 引入头部
	$("#header").load("header.html");
	// 引入导航栏
	$("#nav").load("nav.html");
	$("#nav-two").load("nav-two.html");
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

	//企业购严选效果切换
	$(".product-list .inner-title ul li").click(function () {
		var index = $(this).index();
		console.log(index);
		$(this).addClass("active").siblings().removeClass("active");
		$(".product-detail>div").eq(index).addClass("active").siblings().removeClass("active")
	})

	// 品牌专区切换效果
	$(".brand-change>div").hover(function(){
		var index = $(this).index();
		$(".pro-content>div").eq(index).addClass("active").siblings().removeClass("active");
	})
	
	$(".product-list .product-detail .pro-small .inner-top div").hover(function(){
		$(this).find("p").css("color","#005aaa");
	},function(){
		$(this).find("p").css("color","#000000");
	})
	
	$(".product-list .product-detail .pro-small .inner-bottom div").hover(function(){
		$(this).find("p").css("color","#005aaa");
	},function(){
		$(this).find("p").css("color","#000000");
	})
	
	$(".product-detail .pro-content .pro-inner-top div").hover(function(){
		$(this).find("p").css("color","#005aaa");
	},function(){
		$(this).find("p").css("color","#000000");
	})
	
	$(".product-detail .pro-content .pro-inner-bottom div").hover(function(){
		$(this).find("p").css("color","#005aaa");
	},function(){
		$(this).find("p").css("color","#000000");
	})
	
	$("#commercial-active").hover(function(){
		$("#commercial-list").show();
		console.log(1111);
	},function(){
		$("#commercial-list").hide();
		console.log(2222);
	})
	
	$("#commercial-list>li#comm-zone").hover(function(){
		$("#commercial-list>#comm-zone>dl").show();
	},function(){
		$("#commercial-list>#comm-zone>dl").hide();
	})
	
	$("#commercial-list>li#program").hover(function(){
		$("#commercial-list>#program>dl").show();
	},function(){
		$("#commercial-list>#program>dl").hide();
	})
	
	
	$("#engineering-active").hover(function(){
		$("#engineering-list").show();
	},function(){
		$("#engineering-list").hide();
	})
	
	// 返回顶部
	$(".inner-bottom").click(function(){
		$('html').animate({scrollTop : 0},500);
	})
	
	//微信显示
	 $("#wx_show").hover(function(){
		$(".floatimg_left").show();
	 },function(){
		$(".floatimg_left").hide();
	 })
})