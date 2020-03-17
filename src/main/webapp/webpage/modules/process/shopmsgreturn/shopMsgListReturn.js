<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#shopMsgTable').bootstrapTable({
		 
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
               url: "${ctx}/process/shopmsg/shopMsg/returnListData",
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
                   	window.location = "${ctx}/process/shopmsg/shopMsg/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jh.confirm('确认要删除该需求相关记录吗？', function(){
                       	jh.loading();
                       	jh.get("${ctx}/process/shopmsg/shopMsg/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#shopMsgTable').bootstrapTable('refresh');
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
		        field: 'companyName',
		        title: '公司',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "<a style='color: #3ca2e0!important;text-decoration: none!important;' href='${ctx}/process/shopmsg/shopMsg/returnView?id="+row.id+"'>"+value+"</a>";
		         }
		       
		    }
			// ,{
		    //     field: 'address',
		    //     title: '省市区',
		    //     sortable: true
		    //
		    // }
			,{
		        field: 'orgName',
		        title: '工贸',
		        sortable: true

		    }
			,{
		        field: 'channel',
		        title: '渠道来源',
		        sortable: true,
			   formatter:function(value, row , index){
				   return jh.getDictLabel(${fns:toJson(fns:getDictList('msg_channel'))}, value, "-");
			   }
		       
		    }
			,{
		        field: 'connectName',
		        title: '联系人',
		        sortable: true
		       
		    }
			,{
		        field: 'mobile',
		        title: '联系方式',
		        sortable: true
		       
		    }
			,{
		        field: 'email',
		        title: '邮箱',
		        sortable: true
		       
		    },
		   {
			   field: 'isValid',
			   title: '是否有效',
			   sortable: true,
			   formatter:function(value, row , index){
			   		var result = "是";
					if(row.msgFlag=='60' || row.msgFlag=='61'){
						result = "否";
					}
					return result;
			   }
		   }
		   ,{
			   field: 'proGroup',
			   title: '产品组',
			   sortable: true

		   }
		   ,{
			   field: 'depart',
			   title: '部门',
			   sortable: true,
			   formatter:function(value, row , index){
				   return jh.getDictLabel(${fns:toJson(fns:getDictList('depart_type'))}, value, "-");
			   }

		   }
		   ,{
			   field: 'msgFlag',
			   title: '详细状态',
			   sortable: true,
			   formatter:function(value, row , index){
				   return jh.getDictLabel(${fns:toJson(fns:getDictList('msg_flag'))}, value, "-");
			   }
		   }
		   ,{
			   field: 'createDate',
			   title: '创建时间',
			   sortable: true

		   },
				   {
					   field: 'operate',
					   title: '操作',
					   events: {
						   'click .check': function (e, value, row, index) {
							   var lopen = layer.open({
								   type: 1 //Page层类型
								   ,area: ['500px', '80%']
								   ,title: '需求审核'
								   ,shade: 0.6 //遮罩透明度
								   ,maxmin: true //允许全屏最小化
								   ,anim: 1 //0-6的动画形式，-1不开启
								   ,content: $('#check')
								   , btn: ['确定', '取消']
								   ,yes: function(index, layero){
								   	var isCheck = $(".isCheck").val(),ptDescp = $(".ptDescp").val();
									   //进行平台审核
									   jh.post("${ctx}/process/shopmsg/shopMsg/ptCheck",{
										   'id': row.id,
										   'isCheck': isCheck,
										   'ptDescp': ptDescp,
									   },function(data){
									   		layer.close(lopen);
										   if(data.success){
											   jh.info(data.msg);
											   $('#shopMsgTable').bootstrapTable('refresh');
										   }else{
											   jh.error(data.msg);
										   }
									   });
								   }
								   ,btn2: function(index, layero){
								   }
								   ,cancel: function(){
								   }
							   });
						   },
						   'click .restart': function (e, value, row, index) {
							   var restart = layer.confirm('确认是否开启此需求？', {
								   btn: ['开启','取消'] //按钮
							   }, function(){
								   jh.get("${ctx}/process/shopmsg/shopMsg/restart?id=" + row.id, function(data){
									   if(data.success){
										   $('#shopMsgTable').bootstrapTable('refresh');
										   jh.success(data.msg);
										   layer.close(restart);
									   }else{
										   jh.error(data.msg);
										   layer.close(restart);
									   }
								   })

							   }, function(){
								   layer.close(restart);
							   });
						   }

					   },
					   formatter:  function operateFormatter(value, row, index) {
						   var isBackpass = row.isBackpass;
						   var retStr = "已回传"
						   if(isBackpass=='0'){
							    retStr = "<a class='btn btn-success' href='${ctx}/process/shopmsg/shopMsg/returnFor400?id="+row.id+"'>回传</a> ";
						   }
						   return retStr;
					   }


				   }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#shopMsgTable').bootstrapTable("toggleView");
		}
	  
	  $('#shopMsgTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#shopMsgTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#shopMsgTable').bootstrapTable('getSelections').length!=1);
		  	$('#close').prop('disabled', $('#shopMsgTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jh.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/process/shopmsg/shopMsg/import/template';
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
		  $('#shopMsgTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#shopMsgTable').bootstrapTable('refresh');
		});
	$('#beginCreateDate').datetimepicker({
		format: "YYYY-MM-DD HH:mm:ss"
	});
	$('#endCreateDate').datetimepicker({
		format: "YYYY-MM-DD HH:mm:ss"
	});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#shopMsgTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
	//关闭需求
	function closeMsg(){
		jh.confirm("确认关闭此需求!",function(){
			jh.loading();
			jh.get("${ctx}/process/shopmsg/shopMsg/closeMsg?id=" + getIdSelections(), function(data){
				if(data.success){
					$('#shopMsgTable').bootstrapTable('refresh');
					jh.success(data.msg);
				}else{
					jh.error(data.msg);
				}
			})

		});
	}
  
  	function deleteAll(){

		jh.confirm('确认要删除该需求相关记录吗？', function(){
			jh.loading();  	
			jh.get("${ctx}/process/shopmsg/shopMsg/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#shopMsgTable').bootstrapTable('refresh');
         	  			jh.success(data.msg);
         	  		}else{
         	  			jh.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function edit(){
	  window.location = "${ctx}/process/shopmsg/shopMsg/form?id=" + getIdSelections();
  }
  function dispatch(id,companyOrgCode){
  	if(companyOrgCode=="0"){
		jh.error("请先完成统一信用认证后再派单");
		return false;
	}
  	window.location.href = '${ctx}/process/shopmsg/shopMsg/dispatcher?id=' + id;
  }
</script>