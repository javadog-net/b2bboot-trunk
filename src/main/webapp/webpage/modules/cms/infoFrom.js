<%@ page contentType="text/html;charset=UTF-8" %>
<script>
function add(){
    location.href="infoEdit.do?pageFuncId="+$("#pageFuncId").val()+"&isShowHead=0&channelid="+$("#channel").val();
}
function edit(){
    if(isCheckOne()){
        location.href="infoEdit.do?pageFuncId="+$("#pageFuncId").val()+"&isShowHead=0&id="+getCheckOneValue();
        $("#errorDiv").fadeOut("slow");
    }else{
        $("#errorDiv").fadeIn("slow");
        $("#errorInfo").html("请选择一条数据");
    }
}
//删除图片
function delOldImg(){
    $("#oldImgSpan").empty();
    $("#imgDelBtn").fadeOut("slow");
    $("#img").val("");
}
function issignClick(issign){
    if(1==issign){
        $("#signDiv").fadeIn("slow");
    }else{
        $("#signDiv").fadeOut("slow");
    }
}
function checkallsign(checked){
    var objs=document.getElementsByName("signusers");
    if(objs!=null && objs.length>0){
        for(var i=0;i<objs.length;i++){
            objs[i].checked=checked;
            if (checked) {
                $("#"+objs[i].id).closest('.checker > span').addClass('checked');
            }else{
                $("#"+objs[i].id).closest('.checker > span').removeClass('checked');
            }
        }
    }
}

function opentimetypeClick(opentimetype){
    if(1==opentimetype){
        $("#openendtime").fadeOut("slow");
    }else{
        $("#openendtime").fadeIn("slow");
    }
}
//提交验证
function formCheck(){
    var isCheck=true;
    //自定义字段验证
    isCheck=fieldValidation();
    if(!isCheck){
        $('#myTab a[href="#field"]').tab('show');
    }
    if($.trim($("#channelId").val())==""){
        $("#channelHelp").html("必须选择");
        $("#channelDiv").addClass("error");
        $("#channelId").focus();
        $('#myTab a[href="#base"]').tab('show');
        isCheck=false;
    }else{
        $("#channelHelp").html("");
        $("#channelDiv").removeClass("error");
    }
    if($.trim($("#title").val())==""){
        $("#titleHelp").html("必须填写");
        $("#titleDiv").addClass("error");
        $("#title").focus();
        $('#myTab a[href="#base"]').tab('show');
        isCheck=false;
    }else{
        $("#titleHelp").html("");
        $("#titleDiv").removeClass("error");
    }
    return isCheck;
}

//选择栏目
function selectChannel(){
    openWindow('选择栏目','channelTree.do?channelClick=selectChannel',500,500,'false');
}
//导入Word
function importWord(){
    openWindow('导入Word','importWord.do',500,260,'false');
}
//设置内容
function setContent(html){
    if(typeof(ueeditor)!="undefined"){
        ueeditor.setContent(html,true);
    }
    if(typeof(keeditor)!="undefined"){
        keeditor.html(html);
    }
    if(typeof(ckeeditor)!="undefined"){
        ckeeditor.setData(html);
    }
}
//增加附件
function addAttch(){
    var id = Math.uuidFast();
    var str="";
    str+="<tr id='attchtr"+id+"'>";
    str+="<td>";
    str+="<input id='attchs"+id+"' uniform='true' type='checkbox' name='attchs' value='"+id+"'/>";
    str+="</td>";
    str+="<td>";
    str+="<INPUT id='attchfile"+id+"' uniform='true' type=file name='attchfile"+id+"' >";
    str+="</td>";
    str+="<td>";
    str+="<input type='text' name='attchsordernum"+id+"' style='width:60px' oninput=if(!isInt(value))value=''   onpropertychange=if(!isInt(value))execCommand('undo') onkeyup=if(!isInt(value))execCommand('undo') onafterpaste=if(!isInt(value))execCommand('undo') />";
    str+="</td>";
    str+="</tr>";
    $("#attchTbody").append(str);
    $('input[uniform=true]').uniform()
    if(!$.browser.msie){
        //ie下调用此方法后会导致文件提交不上去，所以只在非ie下调用
        $("#attch"+id).click();
    }
}
//删除附件
function delAttch(){
    var objs=document.getElementsByName("attchs");
    var ids=new Array();
    if(objs!=null && objs.length>0){
        for(var i=0;i<objs.length;i++){
            if(objs[i].checked){
                ids[ids.length]=objs[i].value;
            }
        }
    }
    if(ids.length>0){
        for(var i=0;i<ids.length;i++){
            $("#attchtr"+ids[i]).remove();
            $("#delOldattchs").val($("#delOldattchs").val()+ids[i]+";");
        }
    }
}

//增加图片
function addImg(){
    var id = Math.uuidFast();
    var str="";
    str+="<tr id='imgtr"+id+"'>";
    str+="<td>";
    str+="<input id='imgs"+id+"' uniform='true' type='checkbox' name='imgs' value='"+id+"'/>";
    str+="</td>";
    str+="<td>";
    str+="<INPUT id='img"+id+"' uniform='true' type=file name=imgsfile"+id+" >";
    str+="</td>";
    str+="<td>";
    str+="<INPUT   type=text name=imgstitle"+id+" >";
    str+="</td>";
    str+="<td>";
    str+="<textarea name=imgscontent"+id+" ></textarea>";
    str+="</td>";
    str+="<td>";
    str+="<input type='text' name='imgsordernum"+id+"' style='width:60px' oninput=if(!isInt(value))value=''   onpropertychange=if(!isInt(value))execCommand('undo') onkeyup=if(!isInt(value))execCommand('undo') onafterpaste=if(!isInt(value))execCommand('undo') />";
    str+="</td>";
    str+="</tr>";
    $("#imgTbody").append(str);
    $('input[uniform=true]').uniform()
    if(!$.browser.msie){
        //ie下调用此方法后会导致文件提交不上去，所以只在非ie下调用
        $("#img"+id).click();
    }
}
//删除图片
function delImg(){
    var objs=document.getElementsByName("imgs");
    var ids=new Array();
    if(objs!=null && objs.length>0){
        for(var i=0;i<objs.length;i++){
            if(objs[i].checked){
                ids[ids.length]=objs[i].value;
            }
        }
    }
    if(ids.length>0){
        for(var i=0;i<ids.length;i++){
            $("#imgtr"+ids[i]).remove();
            $("#delOldimgs").val($("#delOldimgs").val()+ids[i]+";");
        }
    }
}

//增加产品关联
function addProduct(){
    var id = Math.uuidFast();
    var str="";
    str+="<tr id='infoproducttr"+id+"'>"
    str+="<td>"
    str+="<input id='infoproducts"+id+"' uniform='true' type='checkbox' iewrap='false'  name='infoproducts' value='"+id+"'/>"
    str+="</td>"
    str+="<input id='productid"+id+"' uniform='true' type='hidden' name='productid"+id+"'  value=''>"
    str+="<td>"
    str+="<input id='productname"+id+"' uniform='true' type='text' name='productname"+id+"' onclick='productSelect(id)' value=''>"
    str+="</td>"
    str+="<td>"
    str+="<input type='text' name='productordernum"+id+"' style='width:60px' oninput=if(!isInt(value))value=''   onpropertychange=if(!isInt(value))execCommand('undo')"
    str+="onkeyup=if(!isInt(value))execCommand('undo') onafterpaste=if(!isInt(value))execCommand('undo') />"
    str+="</td>"
    str+="</tr>"
    $("#infoproductTbody").append(str);
    $('input[uniform=true]').uniform()
    if(!$.browser.msie){
        //ie下调用此方法后会导致文件提交不上去，所以只在非ie下调用
        $("#productname"+id).click();
    }
}

function productSelect(id){
    //openWindow('选择商品','../../admin/shop/productSelect.do?selectid='+id,1000,500,false);
    var title ="选择商品";
    var url = '../../admin/shop/productSelect.do?selectid='+id;
    var width = 800;
    var height = 400;
    $('#openWindowModal h3').html(title);
    $('#openWindowBtn').show();
    $('#openWindowIFrame').attr('src', url);
    $('#openWindowModal').width(width);
    $('#openWindowModal .modal-body').css('max-height', '999px');
    $('#openWindowModal').height(height);
    $('#openWindowModal .modal-body').height(height);
    $('#openWindowIFrame').height(height);
    //$('#openWindowModal').css("top","300px");
    $('#openWindowModal').css( { position : 'absolute', 'top' : 270, left : 300 } );
    //$('#openWindowModal').center();
    //$("body").eq(0).css("overflow","hidden");
    $('#openWindowModal').modal({keyboard:true});

}

//删除关联商品
function delProduct(){
    var objs=document.getElementsByName("infoproducts");
    var ids=new Array();
    if(objs!=null && objs.length>0){
        for(var i=0;i<objs.length;i++){
            if(objs[i].checked){
                ids[ids.length]=objs[i].value;
            }
        }
    }
    if(ids.length>0){
        for(var i=0;i<ids.length;i++){
            $("#infoproducttr"+ids[i]).remove();
            $("#delOldProduct").val($("#delOldProduct").val()+ids[i]+";");
        }
    }
}

//信息管理页面选择栏目
function infoSelectChannel(id,name){
    $("#infoFrame").attr("src","infoList.do?pageFuncId="+$("#pageFuncId").val()+"&channel="+id);
    $("#infoFrame").load(function(){
        $(this).height($(this).contents().find("body").height()+60);
    });
}
function infosign(infoid){
    openWindow('信息签收','../../infoSign.do?cansign=false&id='+infoid+'&date='+new Date(),0,0,'true');
}
function infostate(infoid){
    openWindow('信息审核记录','infoStateLog.do?id='+infoid+'&date='+new Date(),0,0,'true');
}
function copy(){
    if(isCheck()){
        openWindow('复制到栏目','channelTree.do?channelClick=infoCopy',500,500,'true');
        $("#errorDiv").fadeOut("slow");
    }else{
        $("#errorDiv").fadeIn("slow");
        $("#errorInfo").html("请选择数据");
    }
}
function move(){
    if(isCheck()){
        openWindow('移动到栏目','channelTree.do?channelClick=infoMove',500,500,'true');
        $("#errorDiv").fadeOut("slow");
    }else{
        $("#errorDiv").fadeIn("slow");
        $("#errorInfo").html("请选择数据");
    }
}

function del(){
    if(isCheck()){
        var confirm = $.scojs_confirm({
            content: "确定删除操作么?此操作无法回退!",
            action: function() {
                location.href="infoDel.do?pageFuncId="+$("#pageFuncId").val()+"&ids="+getCheckValue()+"&channel="+$("#channel").val();
            }
        });
        confirm.show();
        $("#errorDiv").fadeOut("slow");
    }else{
        $("#errorDiv").fadeIn("slow");
        $("#errorInfo").html("请选择数据");
    }
}
function html(){
    if(isCheck()){
        location.href="infoHtml.do?pageFuncId="+$("#pageFuncId").val()+"&ids="+getCheckValue()+"&channel="+$("#channel").val();
        $("#errorDiv").fadeOut("slow");
    }else{
        $("#errorDiv").fadeIn("slow");
        $("#errorInfo").html("请选择数据");
    }
}
function selectTempletFile(templetid,inputid){
    if(templetid==""){
        $("#"+inputid+"Help").html("请先选择此站点使用的模板");
        $("#"+inputid+"Div").addClass("error");
    }else{
        $("#"+inputid+"Help").html("");
        $("#"+inputid+"Div").removeClass("error");
        openWindow('选择模板文件','templetSelectFile.do?inputid='+inputid+'&id='+templetid,0,0,'true');
    }
}
</script>