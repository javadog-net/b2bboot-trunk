<%@ page contentType="text/html;charset=UTF-8" %>
 <script >
var arry = "";

 function allMakeHtml(){

     jh.confirm('确认要静态化所有栏目吗？此操作较慢！', function(){
         jh.loading();
         jh.get("${ctx}/shop/goodsCategory/allGoodsCategoryCreateHtml?ids=" + arry, function(data){
             if(data.success){
                 $('#goodsCategoryjsTree').bootstrapTable('refresh');
                 jh.success(data.msg);
             }else{
                 jh.error(data.msg);
             }
         })

     })

 }


function tijiao(){
    jh.loading();
    jh.post("${ctx}/cms/luceneCreateProductDo", {ids: arry}, function (result) {
        if (result.success) {

            jh.info(result.msg);

        } else {
            jh.info(result.msg);
        }
    })
}
function delindex(){
    jh.loading();
    jh.post("${ctx}/cms/luceneDelProductDo", {ids: arry}, function (result) {
        if (result.success) {
            jh.info(result.msg);
        } else {
            jh.info(result.msg);
        }
    })
}

function all_select(t){
    if(0 == t.value){
        $('#all_select').val(1);
        $("#goodsCategoryjsTree").jstree('select_all');
        var ref = $('#goodsCategoryjsTree').jstree(true);
        var nodes = ref.get_checked();  //使用get_checked方法
        if(nodes){
            for (var m = 0; m < nodes.length; m++) {
                arry = arry + nodes[m] + ",";
            }
        }
        $('#edit').prop('disabled',false);
        $('#del').prop('disabled', false);
    }else{
        $('#all_select').val(0);
        $("#goodsCategoryjsTree").jstree('deselect_all');
        $('#edit').prop('disabled',true);
        $('#del').prop('disabled', true);
    }
}

$('#toolbar').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
    'check-all.bs.table uncheck-all.bs.table', function () {
    $('#edit').prop('disabled', !arr.length);
    $('#del').prop('disabled', arr.length!=1);


});

function doSubmit(_window, index) {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
   console.log(arry);
jh.loading("正在提交，请稍后！");
    jh.post("${ctx}/shop/goodsCategory/diyGoodsCategoryMakeHtml", {ids: arry}, function (result) {
        if (result.success) {
            jh.info(result.msg);
           // jh.close(index);
           // _window.location.href = "${ctx}/shop/goods";
            window.location.reload();
        } else {
            jh.info(result.msg);
            //jh.close(index);
            //_window.location.href = "${ctx}/shop/goods";
            window.location.reload();
        }
    })
}

$(document).ready(function () {
    var to = false;
    $('#search_q').keyup(function () {
        if (to) {
            clearTimeout(to);
        }
        to = setTimeout(function () {
            var v = $('#search_q').val();
            $('#goodsCategoryjsTree').jstree(true).search(v);
        }, 250);

    });


    $('#goodsCategoryjsTree').on('activate_node.jstree', function (event, obj) {
        var nodes = $('#goodsCategoryjsTree').jstree(true).get_selected(true);  //使用get_checked方法
        arry = "";
        for (var m = 0; m < nodes.length; m++) {
            arry = arry + nodes[m].id + ",";
            if(nodes[m].parent && arry.indexOf(nodes[m].parent)==-1){
                arry = arry + nodes[m].parent + ",";
            }
        }
        if(arry.length>0){
            $("#del").prop("disabled",false);
            $("#edit").prop("disabled",false);
        }
        if(arry.length<=0){
            $("#del").prop("disabled",true);
            $("#edit").prop("disabled",true);
        }
    });
    $('#goodsCategoryjsTree').jstree({
        'core': {
            "multiple": true,
            "check_callback": true,
            "animation": 0,
            "themes": {"variant": "large", "icons": true, "stripes": true},
            'data': {
                "url": "${ctx}/shop/goodsCategory/treeData",
                "dataType": "json"
            }
        },
        "conditionalselect": function (node, event) {
            return false;
        },
        'plugins': ["checkbox", 'types', 'wholerow', "search"],
        "checkbox": {
            "keep_selected_style": false
        },
        "contextmenu": {//右击事件，未加，如果要加，则在上面 plugins 里加上 contextmenu
            "items": function (node) {
                var tmp = $.jstree.defaults.contextmenu.items();
                delete tmp.create.action;
                delete tmp.rename.action;
                tmp.rename = null;
                tmp.create = {
                    "label": "添加下级商品分类",
                    "action": function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        jh.openDialog('添加下级商品分类', '${ctx}/shop/goodsCategory/form?parent.id=' + obj.id + "&parent.name=" + obj.text, '800px', '500px', $('#goodsCategoryjsTree'));
                    }
                };
                tmp.remove = {
                    "label": "删除商品分类",
                    "action": function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        jh.confirm('确认要删除商品分类吗？', function () {
                            jh.loading();
                            $.get("${ctx}/shop/goodsCategory/delete?id=" + obj.id, function (data) {
                                if (data.success) {
                                    $('#goodsCategoryjsTree').jstree("refresh");
                                    jh.success(data.msg);
                                } else {
                                    jh.error(data.msg);
                                }
                            })

                        });
                    }
                }
                tmp.ccp = {
                    "label": "编辑商品分类",
                    "action": function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        var parentId = inst.get_parent(data.reference);
                        var parent = inst.get_node(parentId);
                        jh.openDialog('编辑商品分类', '${ctx}/shop/goodsCategory/form?id=' + obj.id, '800px', '500px', $('#goodsCategoryjsTree'));
                    }
                }
                return tmp;
            }

        },
        "types": {
            'default': {'icon': 'fa fa-file-text-o'},
            '1': {'icon': 'fa fa-home'},
            '2': {'icon': 'fa fa-umbrella'},
            '3': {'icon': 'fa fa-group'},
            '4': {'icon': 'fa fa-file-text-o'}
        }

    })/*.bind("activate_node.jstree", function (obj, e) {
				var node = $('#goodsCategoryjsTree').jstree(true).get_selected(true)[0];
				var opt = {
					silent: true,
					query:{
						'cat.id':node.id
					}
				};
				$("#catId").val(node.id);
				$("#catName").val(node.text);
				$('#goodsTable').bootstrapTable('refresh',opt);
			})*/.on('loaded.jstree', function () {
        $("#goodsCategoryjsTree").jstree('open_all');
    });
});
</script>