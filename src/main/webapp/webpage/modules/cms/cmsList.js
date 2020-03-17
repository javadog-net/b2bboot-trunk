<%@ page contentType="text/html;charset=UTF-8" %>
<script>
var  infoTableOption;
$(document).ready(function() {

    infoTableOption = {

		  //请求方法
               method: 'get',
               //类型json
               dataType: "json",
               //显示刷新按钮
               showRefresh: true,
               //显示切换手机试图按钮
               showToggle: true,
               //显示 内容列下拉框
    	       showColumns: true,
    	       //显示到处按钮
    	       showExport: false,
    	       //显示切换分页按钮
    	       showPaginationSwitch: true,
    	       //最低显示2行
    	       minimumCountColumns: 2,
               //是否显示行间隔色
               striped: true,
               //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
               cache: false,
               //是否显示分页（*）
               pagination: true,
                //排序方式
               sortOrder: "asc",
               //初始化加载第一页，默认第一页
               pageNumber:1,
               //每页的记录行数（*）
               pageSize: 10,
               //可供选择的每页的行数（*）
               pageList: [10, 25, 50, 100],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
               url: "${ctx}/cms/info/data/",
               toolbar:"#toolbarInfo",
               //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
               //queryParamsType:'',
               ////查询参数,每次调用是会带上这个参数，可自定义
               queryParams : function(params) {
               	var searchParam = $("#searchForm").serializeJSON();
               	searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
               	searchParam.pageSize = params.limit === undefined? -1 : params.limit;
               	searchParam.orderBy = params.sort === undefined? "" : params.sort+ " "+  params.order;
                   return searchParam;
               },
               //分页方式：client客户端分页，server服务端分页（*）
               sidePagination: "server",
               contextMenuTrigger:"right",//pc端 按右键弹出菜单
               contextMenuTriggerMobile:"press",//手机端 弹出菜单，click：单击， press：长按。
               contextMenu: '#context-menu-info',
               onContextMenuItem: function(row, $el){
                   if($el.data("item") == "edit"){
                   	editInfo(row.id);
                   } else if($el.data("item") == "delete"){
                        jh.confirm('确认要删除该文章记录吗？', function(){
                       	jh.loading();
                       	jh.get("${ctx}/cms/info/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#bootStrapTable').bootstrapTable('refresh');
                   	  			jh.success(data.msg);
                   	  		}else{
                   	  			jh.error(data.msg);
                   	  		}
                   	  	})

                   	});

                   }
               },

               onClickRow: function(row, $el){
               },
               columns: [{
		        checkbox: true

		    }
			,{
		        field: 'htmlIndexnum',
		        title: 'html索引',
		        sortable: true
		    }
			,{
		        field: 'title',
		        title: '标题',
		        sortable: true,
			    formatter:function (value,row,index) {
				   return "<a href='${pageContext.request.contextPath}${frontPath}/infoPreview.do?id="+row.id+"' target='_blank'>"+value+"</a>";
			    }

		    }

		    ,{
                       field: 'hits',
                       title: '点击数',
                       sortable: true

             }
		   ,{
			   field: 'user.name',
			   title: '发布者',
			   sortable: true

		   }
		   ,{
			   field: 'updateDate',
			   title: '更新时间',
			   sortable: true

		   }

		     ]

		};

	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端
		  $('#infoTable').bootstrapTable("toggleView");
		}

	  $('#bootStrapTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#removeInfo').prop('disabled', ! $('#bootStrapTable').bootstrapTable('getSelections').length);
            $('#editInfo').prop('disabled', $('#bootStrapTable').bootstrapTable('getSelections').length!=1);
		    $('#htmlInfo').prop('disabled', ! $('#bootStrapTable').bootstrapTable('getSelections').length);
            $('#moveInfo').prop('disabled', $('#bootStrapTable').bootstrapTable('getSelections').length!=1);
        });

	  $("#search").click("click", function() {// 绑定查询按扭
		  $("#bootStrapTable").bootstrapTable('refresh');
		});

	 $("#reset").click("click", function() {// 绑定重置按扭
		 $("#searchForm  input").val("");
		 $("#searchForm  select").val("");
		 $("#searchForm  .select-item").html("");
		 $("#bootStrapTable").bootstrapTable('refresh');
		});
	});

	function getIdSelections() {
	    return $.map($("#bootStrapTable").bootstrapTable('getSelections'), function (row) {
	        return row.id
	    });
	}

   function addInfo(){
		$(".addright").show();
		var node = $('#infoKindjsTree').jstree(true).get_selected(true)[0];
		if(!node){
			jh.error("请选择左侧栏目");
			return;
		}
	   $(".bootstrap-table").hide();
	   <shiro:hasPermission name="cms:info:edit">
		   window.location.href = "${ctx}/cms/info/form?category.id="+node.id+"&category.name="+node.text;
	   </shiro:hasPermission>
  }

  function editInfo(id){//没有权限时，不显示确定按钮
	  if(id == undefined){
			id = getIdSelections();
		}
	   <shiro:hasPermission name="cms:info:edit">
      window.location.href = "${ctx}/cms/info/form?id=" + id;
	   </shiro:hasPermission>
  }
  function deleteInfoAll() {
	  jh.confirm('确认要删除该链接管理记录吗？', function(){
		  jh.loading();
		  jh.get("${ctx}/cms/info/deleteAll?ids=" + getIdSelections(), function(data){
			  if(data.success){
				  $('#infoTableOption').bootstrapTable('refresh');
				  jh.success(data.msg);
			  }else{
				  jh.error(data.msg);
			  }
		  })

	  })
  }

/**
 * 信息静态化
 */
function infoMakeHtml(id) {
	if(id == undefined){
		id = getIdSelections();
	}
	var loading = jh.loading("正在静态化,请稍后");
	jh.post("${ctx}/cms/info/infoMakeHtml?ids="+id,{
	},function(data){
		if(data.success){
			$('#infoTableOption').bootstrapTable('refresh');
			jh.success(data.msg);
		}else{
			jh.error(data.msg);
		}
		layer.close(loading);
	});
  }

/**
 * 信息转移
 * @param id
 */
function infoMove(id) {
	  if(id == undefined){
		  id = getIdSelections();
	  }
	  debugger;
	jh.openDialog("移动到栏目",'${ctx}/cms/category/categorySelect?id='+id+"&type=1","400px","800px",window);
  }
</script>