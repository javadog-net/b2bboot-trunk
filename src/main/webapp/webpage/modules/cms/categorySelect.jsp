<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>选择栏目</title>
    <meta name="decorator" content="ani"/>
    <%@include file="/webpage/include/treeview.jsp" %>
    <script type="text/javascript">

        var validateForm;
        var $table; // 父页面table表格id
        var $topIndex;//弹出窗口的 index
        function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
            if(validateForm.form()){
                $table = table;
                $topIndex = index;
                $("#inputForm").submit();
                return true;
            }

            return false;
        }
        $(document).ready(function(){
            //栏目信息
            var t = $('#categoryTree').jstree({
                'core' : {
                    "multiple" : false,
                    "animation" : 0,
                    "themes" : { "icons":true ,"stripes":false},
                    'check_callback':true,
                    'data' : {
                        "url" : "${ctx}/cms/category/treeData?extId=${category.id}",
                        "dataType" : "json" // needed only if you do not supply JSON headers
                    }
                },
                'plugins' : [ "checkbox", 'types' , 'wholerow'],
                "types":{ 'default' : {
                        'icon' : 'fa fa-file-text-o'
                    },
                    'html' : {
                        'icon' : 'fa fa-file-code-o'
                    },
                    'btn':{'icon' : 'fa fa-square'}
                },
                'checkbox' : {
                    "keep_selected_style": false,//是否默认选中
                    "three_state": false,//父子级别级联选择
                    "tie_selection": false
                },

            });

            $('#categoryTree').on('ready.jstree', function (e, data) {
                createNode(e.currentTarget, "0", "设置为根节点", "first");   //最前面插入
            });

            function createNode(parent_node, new_node_id, new_node_text, position) {
                $('#categoryTree').jstree('create_node', $(parent_node), {
                    "text": new_node_text,
                    "id": new_node_id
                }, position, false, false);
            }


            $('#categoryTree').on('check_node.jstree', function(event, obj) {
                var ref = $('#categoryTree').jstree(true);
                var nodes = ref.get_checked();  //使用get_checked方法
                $.each(nodes, function(i, nd) {
                    if (nd != obj.node.id)
                        ref.uncheck_node(nd);
                });
                $("#parId").val(nodes);
            });

            validateForm = $("#inputForm").validate({

                submitHandler: function(form){
                    debugger;
                    //栏目信息
                    jh.loading();
                    var type = ${type};
                    if(type==0){
                        $.post("${ctx}/cms/category/changeCategory",$('#inputForm').serialize(),function(data){
                            if(data.success){
                                $table.location.reload();
                                jh.success(data.msg);
                            }else{
                                jh.error(data.msg);
                            }
                            jh.close($topIndex);//关闭dialog
                        });
                    }else{
                        $.post("${ctx}/cms/info/infoMoveCategory",
                            {
                                infoIds:"${ids}",
                                distCategoryId:$("#parId").val()
                            },
                            function(data){
                            if(data.success){
                                $table.location.reload();
                                jh.success(data.msg);
                            }else{
                                jh.error(data.msg);
                            }
                            jh.close($topIndex);//关闭dialog
                        });
                    }

                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

        });

    </script>
</head>
<body>
<div class="tab-content">
    <div id="tab-1" class="tab-pane fade in  active">
        <div id="categoryTree"></div>
    </div>
</div>
   <form:form id="inputForm" modelAttribute="category" action="${ctx}/cms/category/changeCategory" method="post" class="form-horizontal">
        <form:hidden path="id"/>
        <form:hidden path="parId"/>
    </form:form>
</body>
</html>