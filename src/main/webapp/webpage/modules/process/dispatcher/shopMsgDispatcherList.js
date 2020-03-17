<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#shopMsgDispatcherTable').bootstrapTable({
		 
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
               url: "${ctx}/process/dispatcher/shopMsgDispatcher/data",
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
                   	window.location = "${ctx}/process/dispatcher/shopMsgDispatcher/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jh.confirm('确认要删除该派单功能记录吗？', function(){
                       	jh.loading();
                       	jh.get("${ctx}/process/dispatcher/shopMsgDispatcher/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#shopMsgDispatcherTable').bootstrapTable('refresh');
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
		       
		    },{
					   field: 'companyName',
					   title: '需求公司名称',
					   sortable: true

			   },{
					   field: 'orgName',
					   title: '工贸名称',
					   sortable: true

			   },{
					   field: 'proGroup',
					   title: '产品组',
					   sortable: true

				   },{
					   field: 'channel',
					   title: '需求来源',
					   sortable: true,
					   formatter:function(value, row , index) {
						   return jh.getDictLabel(${fns:toJson(fns:getDictList('msg_channel'))}, value, "-");
					   }
				   },{
					   field: 'connectName',
					   title: '需求联系人',
					   sortable: true

				   },{
					   field: 'mobile',
					   title: '联系人电话',
					   sortable: true

				   },{
				   field: 'createDate',
				   title: '生成时间',
				   sortable: true
			   }
			,{
		        field: 'dispaFlag',
		        title: '是否派单',
		        sortable: true,
			   	formatter:function(value, row , index) {
					return jh.getDictLabel(${fns:toJson(fns:getDictList('yes_no'))}, value, "-");
				}
		    }
		   ,{
			   field: 'isClosed',
			   title: '是否关闭',
			   sortable: true,
			   formatter:function(value, row , index) {
				   return jh.getDictLabel(${fns:toJson(fns:getDictList('yes_no'))}, value, "-");
			   }
		   }
			,{
		        field: 'dispaDate',
		        title: '派单时间',
		        sortable: true
		       
		    }
			,{
		        field: 'source',
		        title: '派单来源',
		        sortable: true
		       
		    }

			,{
		        field: 'dispaType',
		        title: '派单区分',
		        sortable: true,
			   formatter:function(value, row , index) {
				   return jh.getDictLabel(${fns:toJson(fns:getDictList('dispa_type'))}, value, "-");
			   }
		       
		    },{
					   field: 'operate',
					   title: '操作',
					   events: {
					   },
					   formatter:  function operateFormatter(value, row, index) {
						   	var retStr = "";
					   		if(row.dispaFlag=='0' && row.isClosed!='1'){
								retStr = "<a class='btn btn-primary' href='${ctx}/process/dispatcher/shopMsgDispatcher/view?id="+row.msgId+"'>派单</a> ";
							}else if(row.dispaFlag=='1' && row.isClosed=='0'){
								retStr = "已派单";
							}else if(row.dispaFlag=='0' && row.isClosed=='1'){
								retStr = "已关闭";
							}
						   return retStr;
					   }


				   }

		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#shopMsgDispatcherTable').bootstrapTable("toggleView");
		}
	  
	  $('#shopMsgDispatcherTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#shopMsgDispatcherTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#shopMsgDispatcherTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jh.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/process/dispatcher/shopMsgDispatcher/import/template';
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
		  $('#shopMsgDispatcherTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#shopMsgDispatcherTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#shopMsgDispatcherTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jh.confirm('确认要删除该派单功能记录吗？', function(){
			jh.loading();  	
			jh.get("${ctx}/process/dispatcher/shopMsgDispatcher/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#shopMsgDispatcherTable').bootstrapTable('refresh');
         	  			jh.success(data.msg);
         	  		}else{
         	  			jh.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function edit(){
	  window.location = "${ctx}/process/dispatcher/shopMsgDispatcher/form?id=" + getIdSelections();
  }
  
</script>