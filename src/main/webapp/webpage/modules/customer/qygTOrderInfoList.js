<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#qygTOrderInfoTable').bootstrapTable({
		 
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
               url: "${ctx}/customer/qygTOrderInfo/data",
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
                   	window.location = "${ctx}/customer/qygTOrderInfo/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jh.confirm('确认要删除该巨商会订单info记录吗？', function(){
                       	jh.loading();
                       	jh.get("${ctx}/customer/qygTOrderInfo/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#qygTOrderInfoTable').bootstrapTable('refresh');
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
		        field: 'orderNo',
		        title: '订单号',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "<a href='${ctx}/customer/qygTOrderInfo/form?id="+row.id+"'>"+value+"</a>";
		         }
		       
		    }
			,{
		        field: 'planInDate',
		        title: '预计到货时间',
		        sortable: true
		       
		    }
			,{
		        field: 'orderDate',
		        title: '创建订单时间',
		        sortable: true
		       
		    }
			,{
		        field: 'createdTime',
		        title: '创建时间',
		        sortable: true
		       
		    }
			,{
		        field: 'sapDn1date',
		        title: '一次物流时间',
		        sortable: true
		       
		    }
			,{
		        field: 'sapDn5date',
		        title: '二次物流时间',
		        sortable: true
		       
		    }
			,{
		        field: 'sapCenterRecieveDate',
		        title: '中心收货时间',
		        sortable: true
		       
		    }
			,{
		        field: 'sapPtdSendDate',
		        title: 'ptd计划发货时间',
		        sortable: true
		       
		    }
			,{
		        field: 'sapJudgeDate',
		        title: '评审时间',
		        sortable: true
		       
		    }
			,{
		        field: 'sapJudgeStatus',
		        title: '评审状态',
		        sortable: true
		       
		    }
			,{
		        field: 'sapJudgeReason',
		        title: '评审拒绝原因',
		        sortable: true
		       
		    }
			,{
		        field: 'sapReorderDate',
		        title: '返单时间',
		        sortable: true
		       
		    }
			,{
		        field: 'sapSended1',
		        title: '标记是否一次物流已发货（初始0，是1）',
		        sortable: true
		       
		    }
			,{
		        field: 'sapSended5',
		        title: '标记是否二次物流已发货（初始0，是1，拒绝2）',
		        sortable: true
		       
		    }
			,{
		        field: 'sapJudged',
		        title: '标记是否已评审（初始0，是1，拒绝2）',
		        sortable: true
		       
		    }
			,{
		        field: 'sapReorderd',
		        title: '标记是否已返单（初始0，是1）',
		        sortable: true
		       
		    }
			,{
		        field: 'sapInvoiced',
		        title: '标记是否已开发票（初始0，是1）',
		        sortable: true
		       
		    }
			,{
		        field: 'sapPtded',
		        title: '是否制定发货计划（初始0，是1）',
		        sortable: true
		       
		    }
			,{
		        field: 'sapCenterRecieved',
		        title: '是否中心收货（初始0，是1）',
		        sortable: true
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#qygTOrderInfoTable').bootstrapTable("toggleView");
		}
	  
	  $('#qygTOrderInfoTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#qygTOrderInfoTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#qygTOrderInfoTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jh.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/customer/qygTOrderInfo/import/template';
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
		  $('#qygTOrderInfoTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#qygTOrderInfoTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#qygTOrderInfoTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jh.confirm('确认要删除该巨商会订单info记录吗？', function(){
			jh.loading();  	
			jh.get("${ctx}/customer/qygTOrderInfo/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#qygTOrderInfoTable').bootstrapTable('refresh');
         	  			jh.success(data.msg);
         	  		}else{
         	  			jh.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function edit(){
	  window.location = "${ctx}/customer/qygTOrderInfo/form?id=" + getIdSelections();
  }
  
</script>