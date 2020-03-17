<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>目录选择</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/treeview.jsp" %>
    <script type="text/javascript">
        function doSubmit(i, index) {
            var node = $('#fileTree').jstree(true).get_selected(true);
            i.val(node[0].id);
            jh.close(index)
        }

        $(document).ready(function () {
            $('#fileTree').jstree({
                "core": {
                    "animation": 0,
                    "check_callback": true,
                    "themes": {
                        "stripes": true
                    },
                    'data': {
                        'url': ctx + '/gen/genScheme/getFileTree',
                        'data': function (node) {
                            if (node.id != "#") {
                                return {
                                    'parentNode': node.id
                                }
                            }
                        }
                    }
                },
                "types": {
                    "#": {
                        "max_children": 1,
                        "max_depth": 4,
                        "valid_children": ["root"]
                    },
                    "root": {
                        "icon": "/static/3.3.4/assets/images/tree_icon.png",
                        "valid_children": ["default"]
                    },
                    "default": {
                        "valid_children": ["default", "file"]
                    },
                    "file": {
                        "icon": "glyphicon glyphicon-file",
                        "valid_children": []
                    }
                },
                "plugins": ["dnd", "search", "state", "types", "wholerow"]
            })
        });
    </script>
</head>
<body id="" class="bg-white" style="">

<div id="fileTreeDiv" style="padding: 20px">
    <ul id="fileTree">
    </ul>
</div>

</body>
</html>
