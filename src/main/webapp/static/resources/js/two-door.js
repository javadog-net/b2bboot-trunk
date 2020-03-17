$(function(){
    $("#header").load("header.jsp");
    $("#nav").load("nav.jsp");
    	// 侧边栏
    $('#right-sidebar').load('sidebar.jsp');
    var index = 0;
    $(".option").on("click",'li',function(){
        $(this).css('border','2px solid red').siblings().css('border','2px solid #fff');
        $('.larger-map').find('li').eq($(this).index()).css('display','block').siblings().css('display','none');
        index = $(this).index()
    });
    $('.button-right').click(function(){
        index += 1;
        $('.larger-map').find('li').eq(index).css('display','block').siblings().css('display','none');
        $('.option').find('li').eq(index).css('border','2px solid red').siblings().css('border','none');
    })
    $('.button-left').click(function(){
        index -= 1;
        if(index < 0){
            return index = 0;
        }
        console.log(index)
        $('.larger-map').find('li').eq(index).css('display','block').siblings().css('display','none');  
        $('.option').find('li').eq(index).css('border','2px solid red').siblings().css('border','none');

    })
    $(".toggle-button").on('click','li',function(){
        $(this).css({'background':'#005AAA','color':'#fff'})
        .siblings().css({'background':'#F2F2F2','color':'black'});
        $('.Switch-content').find('li').eq($(this).index()).css('display','block')
        .siblings().css('display','none');
    })
    $('.add').click(function(){
        var quantity = $('.amount').html();
        var quantity = Number(quantity);
        var quantity = quantity + 1;
        $('.amount').html(quantity);
    })
    $('.subtract').click(function(){
        var quantity = $('.amount').html();
        var quantity = Number(quantity);
        var quantity = quantity - 1;
        if(quantity < 1){
            alert('不能再减了');
            return quantity = 1
        }
        $('.amount').html(quantity);
    })

})