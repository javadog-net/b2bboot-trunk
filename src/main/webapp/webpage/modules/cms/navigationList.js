<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$("#treeTable").treeTable({theme:'vsStyle',expandLevel : 1});
});

function deleteNavigation(id) {
	jh.confirm('确认要删除该栏目吗？', function(){
		jh.get("${ctx}/cms/navigation/delete?id="+id, function(data){
			if(data.success){
				window.location.href="${ctx}/cms/navigation/list"
				jh.success(data.msg);
			}else{
				jh.error(data.msg);
			}
		})

	});
}
// 添加下级栏目
function addList(id){
	window.location.href='${ctx}/cms/navigation/form?parent.id=' + id;
}
function edit(id){
	window.location.href='${ctx}/cms/navigation/form?id=' + id;
}
// 重置
$("#reset").click(function () {
	$("#search_q").val("");
	$("#search_q").trigger("input");
})
// 监听查询框
$("input#search_q").on("input",function () {
	var inputV = $(this).val();
	$(".pipei").each(function () {
		// 获取当前栏目的名称
		var text = $(this).find("span").text()
		// 查询框的内容是否匹配栏目名称
		if(inputV.indexOf(text) != -1){
			show($(this).parent(),$(this).parent().attr("depth"),$(this).parent());
			// 展示查询出的下级列表
			/*$(this).parent().css("display","table-row");
            $(this).parent().prevAll().css("display","table-row");
            // vsStyle_open
            $(this).parent().prevAll().find(".vsStyle_active_node").removeClass("vsStyle_shut").addClass("vsStyle_open");*/
			// 查询出的设置标志颜色
			$(this).find("span").css({
				"font-style": "italic",
				"color": "#8b0000",
				"font-weight": "bold"
			})
			$(this).find("i").css({
				"font-style": "italic",
				"color": "#8b0000",
				"font-weight": "bold"
			})
			// $(this).parent().find(".vsStyle_active_node").removeClass("vsStyle_shut").addClass("vsStyle_open");
			return false;
		}else{
			// debugger;
			// 隐藏非1级菜单
			$(this).parent().find(".vsStyle_active_node").removeClass("vsStyle_open").addClass("vsStyle_shut");
			if($(this).parent().attr("depth") != "1"){
				$(this).parent().css("display","none");
			}
			// 还原样式
			$(this).find("span").css({
				"font-style": "normal",
				"color": "#3ca2e0",
				"font-weight": "normal"
			})
			$(this).find("i").css({
				"font-style": "normal",
				"color": "#3ca2e0",
				"font-weight": "normal"
			})
		}
	})
});
//递归找父级添加样式
function show(now,depth,mine){
	//找到父级id
	var home = now.attr("pid");
	$("#"+home).find(".vsStyle_active_node").removeClass("vsStyle_shut").addClass("vsStyle_open");
	$("#"+home).siblings().find(".vsStyle_active_node").removeClass("vsStyle_shut").addClass("vsStyle_open");
	$("#"+home).siblings().find(".vsStyle_last_shut").addClass("vsStyle_last_open").removeClass("vsStyle_last_shut");
	if(home!=0){
		$("#"+home).css("display","table-row");
		$("#"+home).siblings().css("display","table-row");
		$("#"+home).siblings().map(function(){
			if($(this).attr("depth")>=depth){
				if(mine.attr("id")!=$(this).attr("id")){
					$(this).css("display","none");
					if($(this).prev().attr("depth") != "1"){
						$(this).prev().find(".vsStyle_active_node").removeClass("vsStyle_open").addClass("vsStyle_shut");
					}
				}else{
					// $("#"+$(this).attr("pid")).find(".vsStyle_last_shut").addClass("vsStyle_last_open");
				}
			}
		})
		$(now).css("display","table-row");
		show($("#"+home),depth,mine);
	}

}

</script>