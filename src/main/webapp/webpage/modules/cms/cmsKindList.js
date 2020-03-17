<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
    var to = false;
    $('#search_q').keyup(function () {
        if(to) { clearTimeout(to); }
        to = setTimeout(function () {
            var v = $('#search_q').val();
            $('#jstree').jstree(true).search(v);
        }, 250);
    });
    $('#jstree').jstree({
        'core' : {
            "multiple" : false,
            "animation" : 0,
            "themes" : { "variant" : "large", "icons":true , "stripes":true},
            'data' : {
                "url" : "${ctx}/test/treetable/carKind/treeData",
                "dataType" : "json"
            }
        },
        "conditionalselect" : function (node, event) {
            return false;
        },
        'plugins': ["contextmenu", 'types', 'wholerow', "search"],
        "contextmenu": {
            "items": function (node) {
                var tmp = $.jstree.defaults.contextmenu.items();
                delete tmp.create.action;
                delete tmp.rename.action;
                tmp.rename = null;
                tmp.create = {
                    "label": "添加下级栏目",
                    "action": function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        jh.openDialog('添加下级栏目', ' ${ctx}/test/treetable/carKind/form?parent.id=' + obj.id + "&parent.name=" + obj.text, '800px', '500px', $('#jstree'));
                    }
                };
                tmp.remove = {
                    "label": "删除栏目",
                    "action": function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        jh.confirm('确认要删除栏目吗？', function(){
                            jh.loading();
                            $.get("${ctx}/test/treetable/carKind/delete?id="+obj.id, function(data){
                                if(data.success){
                                    $('#jstree').jstree("refresh");
                                    jh.success(data.msg);
                                }else{
                                    jh.error(data.msg);
                                }
                            })

                        });
                    }
                }
                tmp.ccp = {
                    "label": "编辑栏目",
                    "action": function (data) {
                        var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                        var parentId = inst.get_parent(data.reference);
                        var parent = inst.get_node(parentId);
                        jh.openDialog('编辑分组', '${ctx}/test/treetable/carKind/form?id=' + obj.id, '800px', '500px', $('#jstree'));
                    }
                }
                return tmp;
            }

        },
        "types":{
            'default' : { 'icon' : 'fa fa-file-text-o' },
            '1' : {'icon' : 'fa fa-home'},
            '2' : {'icon' : 'fa fa-umbrella' },
            '3' : { 'icon' : 'fa fa-group'},
            '4' : { 'icon' : 'fa fa-file-text-o' }
        }

    }).bind("activate_node.jstree", function (obj, e) {
        var id = $('#jstree').jstree(true).get_selected()[0];
        var opt = {
            silent: true,
            query:{
                'kind.id':id
            }
        };
        $('#carTable').bootstrapTable('refresh',opt);
    }).on('loaded.jstree', function() {
        $("#jstree").jstree('open_all');
    });
});
</script>