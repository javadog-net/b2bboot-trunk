<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
    $("#treeTable").treeTable({theme:'vsStyle',expandLevel : 1,isshow:true});
});
function deleteCategory(id) {
    jh.confirm('确认要删除该栏目吗？', function(){
        jh.loading();
        jh.get("${ctx}/cms/category/delete?id="+id, function(data){
            if(data.success){
                window.location.href="${ctx}/cms/category/list"
                jh.success(data.msg);
            }else{
                jh.error(data.msg);
            }
        })

    });
}
	// 添加下级栏目
	function addList(id){
		window.location.href="${ctx}/cms/category/form?parent.id=" + id;
	}
	// 栏目修改
	function edit(id){
		window.location.href="${ctx}/cms/category/form?id=" + id;
	}
	// 查询
	$("#search").click(function () {
		$("#search_q").trigger("input");
	})
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
			// debugger;
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

	/**
	 * 转移目录
	 * @param id
	 */
	function changeCategory(id) {
		jh.openDialog("选择栏目信息",'${ctx}/cms/category/categorySelect?id='+id+"&type=0","400px","800px",window);
	}

	/**
	 * 栏目预览
	 * @param id
	 */
	function previewCategory(id) {
		window.open('${pageContext.request.contextPath}${frontPath}/categoryPreview?id='+id,"_blank");
	}
	function categoryHtml() {
		var data = {};
		var ids = [];
		$("input[type='checkbox']:checked").map(function(){
			//遍历每个checkbox对应的id
			var id = $(this).parents("tr").attr("id");
			//如果id存在
			if(id){
				ids.push(id);
			}
		});
		data.categoryIds = ids.join(",");
		data.createType = "selected";
		ajaxPost(data);
	}
function ajaxPost(data){
	var loading = jh.loading("正在静态化，请稍等...")
	jh.post("${ctx}/cms/categoryHtmlDo", data, function(data){
		if(data.success){
			layer.msg(data.msg, {
				icon: 1,
				time: 2000 //2秒关闭（如果不配置，默认是3秒）
			}, function(){
				window.location.reload();
			});
		}else{
			layer.close(loading);
			jh.error(data.msg);
		}
		jh.close(loading);
	})
}

</script>