<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#storeEvaluateTable').bootstrapTable({
		 
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
               url: "${ctx}/shop/storeEvaluate/data",
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
               contextMenu: '#context-menu',
               onContextMenuItem: function(row, $el){
                   if($el.data("item") == "edit"){
                   	edit(row.id);
                   } else if($el.data("item") == "delete"){
                        jh.confirm('确认要删除该店铺评分记录吗？', function(){
                       	jh.loading();
                       	jh.get("${ctx}/shop/storeEvaluate/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#storeEvaluateTable').bootstrapTable('refresh');
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
		        field: 'orderSn',
		        title: '订单编号',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "<a href='javascript:edit(\""+row.id+"\")'>"+value+"</a>";
		         }
		       
		    }
			,{
		        field: 'storeId',
		        title: '店铺编号',
		        sortable: true
		       
		    }
			,{
		        field: 'storeName',
		        title: '店铺名称',
		        sortable: true
		       
		    }
			,{
		        field: 'purchaserId',
		        title: '采购商ID',
		        sortable: true
		       
		    }
			,{
		        field: 'purchaserAccountId',
		        title: '采购商账号ID',
		        sortable: true
		       
		    }
			,{
		        field: 'scoreProductQuality',
		        title: '产品质量评分',
		        sortable: true
		       
		    }
			,{
		        field: 'scoreDemandResponse',
		        title: '需求响应评分',
		        sortable: true
		       
		    }
			,{
		        field: 'scoreDeliveryCredit',
		        title: '物流配送评分',
		        sortable: true
		       
		    }
			,{
		        field: 'scoreSupplySpeed',
		        title: '供货速度评分',
		        sortable: true
		       
		    }
			,{
		        field: 'scoreCustomerService',
		        title: '售后服务评分',
		        sortable: true
		       
		    }
			,{
		        field: 'isanonymous',
		        title: '0 否  1 是匿名评价',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jh.getDictLabel(${fns:toJson(fns:getDictList('yes_no'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'addtime',
		        title: '评价时间',
		        sortable: true
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#storeEvaluateTable').bootstrapTable("toggleView");
		}
	  
	  $('#storeEvaluateTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#storeEvaluateTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#storeEvaluateTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jh.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/shop/storeEvaluate/import/template';
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
		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#storeEvaluateTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#storeEvaluateTable').bootstrapTable('refresh');
		});
		
		$('#beginAddtime').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
		$('#endAddtime').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
		
	});
		
  function getIdSelections() {
        return $.map($("#storeEvaluateTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jh.confirm('确认要删除该店铺评分记录吗？', function(){
			jh.loading();  	
			jh.get("${ctx}/shop/storeEvaluate/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#storeEvaluateTable').bootstrapTable('refresh');
         	  			jh.success(data.msg);
         	  		}else{
         	  			jh.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
   function add(){
	  jh.openDialog('新增店铺评分', "${ctx}/shop/storeEvaluate/form",'800px', '500px', $('#storeEvaluateTable'));
  }
  function edit(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
	   <shiro:hasPermission name="shop:storeEvaluate:edit">
	  jh.openDialog('编辑店铺评分', "${ctx}/shop/storeEvaluate/form?id=" + id,'800px', '500px', $('#storeEvaluateTable'));
	   </shiro:hasPermission>
	  <shiro:lacksPermission name="shop:storeEvaluate:edit">
	  jh.openDialogView('查看店铺评分', "${ctx}/shop/storeEvaluate/form?id=" + id,'800px', '500px', $('#storeEvaluateTable'));
	  </shiro:lacksPermission>
  }

</script>