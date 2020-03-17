<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#eucMsgOrderTable').bootstrapTable({
		 
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
               url: "${ctx}/euc/eucMsgOrder/data",
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
                   	window.location = "${ctx}/euc/eucMsgOrder/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jh.confirm('确认要删除该euc订单记录吗？', function(){
                       	jh.loading();
                       	jh.get("${ctx}/euc/eucMsgOrder/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#eucMsgOrderTable').bootstrapTable('refresh');
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
               field: 'businessCode',
               title: '商机编码',
               sortable: true

           },{
               field: 'businessName',
               title: '商机名称',
               sortable: true

           }
			,{
		        field: 'contractorCode',
		        title: '经销商编码',
		        sortable: true
		       
		    }
			,{
		        field: 'contractorName',
		        title: '经销商名称',
		        sortable: true
		       
		    }
			,{
		        field: 'undertake',
					   title: '承接方式',
		        sortable: true
			   ,formatter:function(value, row , index){
		        	var endStr = "";
		        	if("0"==value){
						endStr = "工程单";
					}else if("1"==value){
						endStr = "零售";
					}else{
						endStr = "未选择承接方式";
					}
				   return endStr;
			   }
		       
		    }
			,{
		        field: 'tradeCount',
		        title: '交易金额',
		        sortable: true
		       
		    }
			,{
		        field: 'orderType',
		        title: '订单类型',
		        sortable: true,
			   formatter:function(value, row , index){
				   var endStr = "";
				   if("0"==value){
					   endStr = "派单";
				   }else if("1"==value){
					   endStr = "抢单";
				   }
				   return endStr;
			   }
		    }
			,{
		        field: 'isCheck',
		        title: '平台审核是否通过',
		        sortable: true,
					   formatter:function(value, row , index){
						   var endStr = "";
						   if("0"==value){
							   endStr = "是";
						   }else if("1"==value){
							   endStr = "否";
						   }
						   return endStr;
					   }
		       
		    }
			,{
		        field: 'checker',
		        title: '平台审核人',
		        sortable: true
		       
		    }
			,{
		        field: 'isBind',
		        title: '是否中标',
		        sortable: true,
			   formatter:function(value, row , index){
				   var endStr = "";
				   if("0"==value){
					   endStr = "否";
				   }else if("1"==value){
					   endStr = "是";
				   }
				   return endStr;
			   }
		       
		    }
			,{
		        field: 'checkDate',
		        title: '平台审核时间',
		        sortable: true
		       
		    }
			,{
		        field: 'imageUrl',
		        title: '上传凭证',
		        sortable: true,
					   formatter:function(value, row , index){
						   if(row.imageUrl=="" || row.imageUrl==undefined){
							   return "未上传";
						   }else{
							   return "<a class='btn btn-warning detail'>查看</a> ";
						   }
					   },events: {
						   'click .detail': function (e, value, row, index) {
							   var imageUrl = row.imageUrl;
							   //循环展示
							   var arrImg = imageUrl.split(",");
							   var htmlText = "";
							   arrImg.forEach(function(item,index){
								   var url = item;
								   htmlText = htmlText + "<img style='float: left;width:400px; height: 400px; margin-top: 5px; margin-left: 5px; ' src='"+ url +"'/>"
							   });
							   layer.open({
								   type: 1,
								   title: '见证性材料缩略图',
								   shadeClose: true,
								   shade: 0.8,
								   area: ['425px', '430px'],
								   content:htmlText
							   });

						   }
					   }
		       
		    }
			,{
		        field: 'updateTime',
		        title: '更新时间',
		        sortable: true
		       
		    },
				   {
					   field: 'operate',
					   title: '操作',
					   events: {
						   'click .check': function (e, value, row, index) {
							   //询问框

							   var top = layer.confirm("零售单项目审核", {
								   btn: ['通过','拒绝'] //按钮
							   }, function(){
								   jh.get("${ctx}/euc/eucMsgOrder/check?orderId=" + row.id + "&ischeck=0", function(data){
									   if(data.success){
										   $('#eucMsgOrderTable').bootstrapTable('refresh');
										   layer.close(top);
										   jh.success(data.msg);
									   }else{
										   jh.error(data.msg);
									   }
								   })
							   }, function(){
								   jh.get("${ctx}/euc/eucMsgOrder/check?orderId=" + row.id + "&ischeck=1", function(data){
									   if(data.success){
										   $('#eucMsgOrderTable').bootstrapTable('refresh');
										   layer.close(top);
										   jh.success(data.msg);
									   }else{
										   jh.error(data.msg);
									   }
								   })
							   });
						   }
					   },
					   formatter:  function operateFormatter(value, row, index) {
					   	if(row.undertake=="" || row.undertake=="0" || row.undertake==undefined){
					   		return "";
						}
					   var retStr = "";
					   if(row.isCheck=="" || row.isCheck==null ||  row.isCheck=="1"){
						   retStr = "<a class='btn btn-success check'>审核</a> ";
					   }else{
						   retStr = "已审核";
					   }
					   return retStr;
					   }
				   }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#eucMsgOrderTable').bootstrapTable("toggleView");
		}
	  
	  $('#eucMsgOrderTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#eucMsgOrderTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#eucMsgOrderTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jh.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/euc/eucMsgOrder/import/template';
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
		  $('#eucMsgOrderTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#eucMsgOrderTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#eucMsgOrderTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jh.confirm('确认要删除该euc订单记录吗？', function(){
			jh.loading();  	
			jh.get("${ctx}/euc/eucMsgOrder/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#eucMsgOrderTable').bootstrapTable('refresh');
         	  			jh.success(data.msg);
         	  		}else{
         	  			jh.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function edit(){
	  window.location = "${ctx}/euc/eucMsgOrder/form?id=" + getIdSelections();
  }
  
</script>