<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>栏目列表</title>
	<meta name="decorator" content="default"/>
	<%@include file="/webpage/include/treeview.jsp" %>
	<style type="text/css">
		.jstree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
		.accordion-inner{padding:2px;}
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
			var to = false;
			$('#search_q').keyup(function () {
				if(to) { clearTimeout(to); }
				to = setTimeout(function () {
					var v = $('#search_q').val();
					$('#categoryjsTree').jstree(true).search(v);
				}, 250);
			});
			$('#categoryjsTree').jstree({
				'core' : {
					"multiple" : false,
					"animation" : 0,
					"themes" : { "variant" : "small", "icons":true , "stripes":false},
					'data' : {
						"url" : "${ctx}/cms/category/treeData",
						"dataType" : "json" 
					}
				},
				"conditionalselect" : function (node, event) {
					return false;
				},
				'plugins': ["contextmenu", 'types', 'wholerow', "search"],
				"types":{
					'default' : { 'icon' : 'fa fa-file-text-o' },
					'1' : {'icon' : 'fa fa-home'},
					'2' : {'icon' : 'fa fa-umbrella' },
					'3' : { 'icon' : 'fa fa-group'},
					'4' : { 'icon' : 'fa fa-file-text-o' }
				}

			}).bind("activate_node.jstree", function (obj, e) {
				var node = $('#categoryjsTree').jstree(true).get_selected(true)[0];
				console.log(node);
				var module = node.original.module || 'none';
				var url = "${ctx}/cms/"+module+"/?category.id="+node.id;
				window.parent.document.getElementById("cmsMainFrame").src=encodeURI(url); 
			}).on('loaded.jstree', function() {
				$("#categoryjsTree").jstree('open_all');
			});
			wSize();
		});
		$(window).resize(function(){
			wSize();
		});
		function wSize(){
			$("#categoryjsTree").width($(window).width()-16).height($(window).height()-62);
			$("#categoryjsTree").css({"overflow":"auto","overflow-x":"auto","overflow-y":"auto"});
			$("html,body").css({"overflow":"hidden","overflow-x":"hidden","overflow-y":"hidden"});
		}
	</script>
</head>
<body>
			<div class="panel jstree" >
				<div class="form-group">
					<div class="row">
					<div class="col-sm-10" >
						<div class="input-search">
							<button type="submit" class="input-search-btn">
								<i class="fa fa-search" aria-hidden="true"></i></button>
							<input   id="search_q" type="text" class="form-control input-sm" name="" placeholder="查找...">
						</div>
						</div>
					</div>
				</div>
				<div id="categoryjsTree"></div>
			</div>
</body>
</html>