<%@ page contentType="text/html;charset=UTF-8" %>
	<script>
$(document).ready(function() {
	var curkind = '';
	var ta = '';
	var tb = '';
	var u = '';
	var to = false;
	$('#search_q').keyup(function () {
		if(to) { clearTimeout(to); }
		to = setTimeout(function () {
			var v = $('#search_q').val();
			$('#infoKindjsTree').jstree(true).search(v);
		}, 250);
	});
	$('#infoKindjsTree').jstree({
		'core' : {
			"multiple" : false,
			"animation" : 0,
			"themes" : { "variant" : "large", "icons":true , "stripes":true},
			'data' : {
				"url" : "${ctx}/cms/category/treeData",
				"dataType" : "json"
			}
		},
		"conditionalselect" : function (node, event) {
			return false;
		},
		'plugins': ['types', 'wholerow', "search"],
		"types":{
			'default' : { 'icon' : 'fa fa-file-text-o' },
			'1' : {'icon' : 'fa fa-home'},
			'2' : {'icon' : 'fa fa-umbrella' },
			'3' : { 'icon' : 'fa fa-group'},
			'4' : { 'icon' : 'fa fa-file-text-o' }
		}

	}).bind("activate_node.jstree", function (obj, e) {
		$('html , body').animate({scrollTop: 0},'fast');
		loadlistdata();
	}).on('loaded.jstree', function(event, data) {
		//上个页面返回的id
		var nodeId = "${nodeId}";
		//获取跟节点root,此页面为智慧解决方案
		var root = data.instance.get_node(event.target.firstChild.firstChild.lastChild);
		//获取所有子节点
		var nodes = root.children_d;
		//如果不存在则默认根节点选中
		if(nodeId){
			nodes.push(root.id);
		}
		//遍历选中
		for (var i in nodes) {
			var node = data.instance.get_node(nodes[i]);
			if (node.id == nodeId) {
				data.instance.select_node(node);
			}else{
				data.instance.deselect_node(node);
			}
		}
		//关闭所有节点
		data.instance.close_all();
		if(nodeId){
			//获取回显节点
			var n = data.instance.get_node(nodeId);
			//加载数据
			loadlistdata(n);
			//打开此节点
			data.instance.open_node(n);
			//选中此节点
			data.instance.select_node(n);
		}else{
			//如果没有回显则选中第一个
			loadlistdata();
		}
	});

	function loadlistdata(n){
		var tableoption = infoTableOption;
		var node ="";
		//如果存在则使用回显
		if(n){
			node = n;
		}else{
			//如果没有则选中第一个
			node = $('#infoKindjsTree').jstree(true).get_selected(true)[0];
		}

		$("#kindId").val(node.id);
		$("#module").val(node.data);
		$("#kindName").val(node.text);
		var opt = {
			silent: true,
			query:{
				'category.id':node.id
			}
		};
        if(curkind!=node.data){
			 curkind = node.data;
			$('#searchForm').attr('action',"${ctx}/cms/info/");
			$("#bootStrapTable").bootstrapTable(tableoption);
		}else{
			$('#bootStrapTable').bootstrapTable('destroy');
			$("#bootStrapTable").bootstrapTable(tableoption);
			$("#bootStrapTable").bootstrapTable('refresh',opt);
		}
	}
});
</script>