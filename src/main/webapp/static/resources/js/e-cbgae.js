$(function(){
    $("#header").load("header.jsp");
    $("#nav").load("nav.jsp");
    // 侧边栏
    $('#right-sidebar').load('sidebar.jsp')
    $(".rfe-content .inner-tabbar>span").hover(function(){
        $(this).css("color","#3C3CD8");
    },function(){
        $(this).css("color","#828282");
    })
})