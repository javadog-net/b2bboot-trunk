<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#viewQygBrownItemTable').bootstrapTable({
		 
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
               url: "${ctx}/customer/viewQygBrownItem/data",
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
                   	window.location = "${ctx}/customer/viewQygBrownItem/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jh.confirm('确认要删除该工程版信息详情视图记录吗？', function(){
                       	jh.loading();
                       	jh.get("${ctx}/customer/viewQygBrownItem/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#viewQygBrownItemTable').bootstrapTable('refresh');
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
		        field: 'id',
		        title: 'id',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "<a href='${ctx}/customer/viewQygBrownItem/form?id="+row.id+"'>"+value+"</a>";
		         }
		       
		    }
			,{
		        field: 'brownId',
		        title: 'brown_id',
		        sortable: true
		       
		    }
			,{
		        field: 'productId',
		        title: 'product_id',
		        sortable: true
		       
		    }
			,{
		        field: 'productCode',
		        title: 'product_code',
		        sortable: true
		       
		    }
			,{
		        field: 'productName',
		        title: 'product_name',
		        sortable: true
		       
		    }
			,{
		        field: 'orderQuantity',
		        title: 'order_quantity',
		        sortable: true
		       
		    }
			,{
		        field: 'price',
		        title: 'price',
		        sortable: true
		       
		    }
			,{
		        field: 'actualOrderQuantity',
		        title: 'actual_order_quantity',
		        sortable: true
		       
		    }
			,{
		        field: 'izhikou',
		        title: 'izhikou',
		        sortable: true
		       
		    }
			,{
		        field: 'ihoufan',
		        title: 'ihoufan',
		        sortable: true
		       
		    }
			,{
		        field: 'itaifan',
		        title: 'itaifan',
		        sortable: true
		       
		    }
			,{
		        field: 'lastModifiedDate',
		        title: '最后修改时间',
		        sortable: true
		       
		    }
			,{
		        field: 'cinvmUnit',
		        title: '产品单位',
		        sortable: true
		       
		    }
			,{
		        field: 'iinvrCost',
		        title: '供价',
		        sortable: true
		       
		    }
			,{
		        field: 'avePolicy',
		        title: '参照平均政策-本型号台阶限价',
		        sortable: true
		       
		    }
			,{
		        field: 'curPolicy',
		        title: '当前参照最大政策-型号基准价',
		        sortable: true
		       
		    }
			,{
		        field: 'profit',
		        title: '本型号利润审批价-基准价',
		        sortable: true
		       
		    }
			,{
		        field: 'createdById',
		        title: '创建人ID',
		        sortable: true
		       
		    }
			,{
		        field: 'lastModifiedById',
		        title: '最后修改人ID',
		        sortable: true
		       
		    }
			,{
		        field: 'deleted',
		        title: '是否删除',
		        sortable: true
		       
		    }
			,{
		        field: 'createdBy',
		        title: '创建人',
		        sortable: true
		       
		    }
			,{
		        field: 'createdDate',
		        title: '创建时间',
		        sortable: true
		       
		    }
			,{
		        field: 'lastModifiedBy',
		        title: '最后修改人名称',
		        sortable: true
		       
		    }
			,{
		        field: 'creProId',
		        title: '创建来源信息',
		        sortable: true
		       
		    }
			,{
		        field: 'modProId',
		        title: '修改来源信息',
		        sortable: true
		       
		    }
			,{
		        field: 'batchDate',
		        title: '批处理时间',
		        sortable: true
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#viewQygBrownItemTable').bootstrapTable("toggleView");
		}
	  
	  $('#viewQygBrownItemTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#viewQygBrownItemTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#viewQygBrownItemTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jh.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/customer/viewQygBrownItem/import/template';
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
		  $('#viewQygBrownItemTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#viewQygBrownItemTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#viewQygBrownItemTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jh.confirm('确认要删除该工程版信息详情视图记录吗？', function(){
			jh.loading();  	
			jh.get("${ctx}/customer/viewQygBrownItem/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#viewQygBrownItemTable').bootstrapTable('refresh');
         	  			jh.success(data.msg);
         	  		}else{
         	  			jh.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function edit(){
	  window.location = "${ctx}/customer/viewQygBrownItem/form?id=" + getIdSelections();
  }
  
</script>