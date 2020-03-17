<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#goodsClassPropertiesTable').bootstrapTable({
		 
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
    	       showExport: true,
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
               url: "${ctx}/shop/goodsClassProperties/data",
               //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
               //queryParamsType:'',   
               ////查询参数,每次调用是会带上这个参数，可自定义                         
               queryParams : function(params) {
               	var searchParam = $("#searchForm_Properties").serializeJSON();
               	searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
               	searchParam.pageSize = params.limit === undefined? -1 : params.limit;
               	searchParam.orderBy = params.sort === undefined? "" : params.sort+ " "+  params.order;
			   searchParam.classId = $(".goodsClassId").val();
                   return searchParam;
               },
               //分页方式：client客户端分页，server服务端分页（*）
               sidePagination: "server",
               contextMenuTrigger:"right",//pc端 按右键弹出菜单
               contextMenuTriggerMobile:"press",//手机端 弹出菜单，click：单击， press：长按。
               contextMenu: '#context-menu',
               onContextMenuItem: function(row, $el){
                   if($el.data("item") == "editgoodsClassProperties"){
                   	edit(row.id);
                   } else if($el.data("item") == "delete"){
                        jh.confirm('确认要删除该商品属性记录吗？', function(){
                       	jh.loading();
                       	jh.get("${ctx}/shop/goodsClassProperties/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#goodsClassPropertiesTable').bootstrapTable('refresh');
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
		        field: 'name',
		        title: '属性名称',
		        sortable: true
		       
		    }
			,{
		        field: 'value',
		        title: '属性值',
		        sortable: true
		       
		    }
			,{
		        field: 'isSearch',
		        title: ' 是否参与搜索',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jh.getDictLabel(${fns:toJson(fns:getDictList('yes_no'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'updateDate',
		        title: '更新时间',
		        sortable: true
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#goodsClassPropertiesTable').bootstrapTable("toggleView");
		}
	  
	  $('#goodsClassPropertiesTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#removegoodsClassProperties').prop('disabled', ! $('#goodsClassPropertiesTable').bootstrapTable('getSelections').length);
            $('#editgoodsClassProperties').prop('disabled', $('#goodsClassPropertiesTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jh.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/shop/goodsClassProperties/import/template';
				  },
			    btn2: function(index, layero){
				        var inputForm =top.$("#importForm");
				        var top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
				        inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
				        inputForm.onsubmit = function(){
				        	jh.loading('  正在导入，请稍等...');
				        }
				        inputForm.submit();
					    jh.close(index);
				  },
				 
				  btn3: function(index){ 
					  jh.close(index);
	    	       }
			}); 
		});
		    
	  $("#search-child").click("click", function() {// 绑定查询按扭
		  $('#goodsClassPropertiesTable').bootstrapTable('refresh');
		});
	 
	 $("#reset-child").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#goodsClassPropertiesTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelectionsChild() {
        return $.map($("#goodsClassPropertiesTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAllgoodsClassProperties(){

		jh.confirm('确认要删除该商品属性记录吗？', function(){
			jh.loading();  	
			jh.get("${ctx}/shop/goodsClassProperties/deleteAll?ids=" + getIdSelectionsChild(), function(data){
         	  		if(data.success){
         	  			$('#goodsClassPropertiesTable').bootstrapTable('refresh');
         	  			jh.success(data.msg);
         	  		}else{
         	  			jh.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
   function addgoodsClassProperties(){
	  jh.openDialog('新增商品属性', "${ctx}/shop/goodsClassProperties/form?classId=" + $(".goodsClassId").val() + "&className=" + $(".inc-class").text(),'800px', '500px', $('#goodsClassPropertiesTable'));
  }
  function editgoodsClassProperties(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelectionsChild();
		}
	   <shiro:hasPermission name="shop:goodsClassProperties:edit">
	  jh.openDialog('编辑商品属性', "${ctx}/shop/goodsClassProperties/form?id=" + id + "&classId=" + $(".goodsClassId").val() + "&className=" + $(".inc-class").text(),'800px', '500px', $('#goodsClassPropertiesTable'));
	   </shiro:hasPermission>
	  <shiro:lacksPermission name="shop:goodsClassProperties:edit">
	  jh.openDialogView('查看商品属性', "${ctx}/shop/goodsClassProperties/form?id=" + id + "&classId=" + $(".goodsClassId").val() + "&className=" + $(".inc-class").text(),'800px', '500px', $('#goodsClassPropertiesTable'));
	  </shiro:lacksPermission>
  }

</script>