<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#bidInfoTable').bootstrapTable({
		 
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
               url: "${ctx}/bid/bidInfo/data",
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
                   	window.location = "${ctx}/bid/bidInfo/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jh.confirm('确认要删除该招投标记录吗？', function(){
                       	jh.loading();
                       	jh.get("${ctx}/bid/bidInfo/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#bidInfoTable').bootstrapTable('refresh');
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
		        field: 'projectName',
		        title: '项目名称',
		        align: 'center',
		        sortable: true
		        ,width:200
		        ,formatter:function(value, row , index){
		        	return "<a href='${ctx}/bid/bidInfo/particular?preview=1&id=" + row.id+"' style='word-break:break-word;'>"+value+"</a>";
		         }
		       
		    }
			,{
		        field: 'industry',
		        title: '所属行业',
		        align: 'center',
		        sortable: true
		       
		    }
			,{
		        field: 'companyName',
		        title: '公司名称',
		        align: 'center',
		        sortable: true
		       
		    }
			,{
		        field: 'bidConcat',
		        title: '联系人',
		        align: 'center',
		        sortable: true
		       
		    }
		
			,{
		        field: 'addTime',
		        title: '发布时间',
		        align: 'center',
		        sortable: true
		       
		    }
			,{
		        field: 'status',
		        title: '审核状态',
	            align: 'center',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	if(row.status==0){
		        	return "待审核";
		        	}else if(row.status==1){
			        return "审核通过";
		        	}else{
		        	return "审核拒绝";
		        	}
		    }
			},
			{
		        field: 'isValid',
		        title: '项目状态',
	            align: 'center',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	if(row.isValid==1){
		        	return "有效";
		        	}else{
			        return "无效";
		        	}
		        }
			}
		    ,{
	            field: 'operate',
	            title: '操作',
	            align: 'center',
	            events: {
	                'click .edit': function (e, value, row, index) {
                        window.location = "${ctx}/bid/bidInfo/particular?id=" + row.id;
	                },
	                'click .yulan': function (e, value, row, index) {
                        window.location = "${ctx}/bid/bidInfo/particular?id=" + row.id+'&preview=1';
	                },
	                'click .baoming': function (e, value, row, index) {
                        window.location = "${ctx}/bid/bidSignup/lists?bidInfoId=" + row.id;
	                },
	                'click .del': function (e, value, row, index) {
	                	if(row.isValid==1){
	                    jh.confirm('确认下架该项目？',function(){
	                        jh.loading();
	                        $.get('${ctx}/bid/bidInfo/upStatus?id='+row.id+'&isValid=0', function(data){
	                            if(data.success){
	                                $('#bidInfoTable').bootstrapTable("refresh");
	                                jh.success(data.msg);
	                            }else{
	                                jh.error(data.msg);
	                            }
	                        })
	                    });
	                 }
	                if(row.isValid==0){
		                    jh.confirm('确认上架该项目？',function(){
		                        jh.loading();
		                        $.get('${ctx}/bid/bidInfo/upStatus?id='+row.id+'&isValid=1', function(data){
		                            if(data.success){
		                                $('#bidInfoTable').bootstrapTable("refresh");
		                                jh.success(data.msg);
		                            }else{
		                                jh.error(data.msg);
		                            }
		                        })
		                    });
		                 }
	                	
	                }
	            },
	            formatter:  function operateFormatter(value, row, index) {
	            	
	            	  var caozuo='<a href="#" class="yulan" title="预览" >[预览] </a>&nbsp;';
	            	  if(row.status==0){
	            		  caozuo +='<a href="#" class="edit" title="审核" >[审核] </a>&nbsp';
	  	              }
	            	  if(row.isValid==0){
	            		 caozuo +='<a href="#" class="del" title="有效" >[有效] </a>&nbsp';
	            	  }else{
		               caozuo +='<a href="#" class="del" title="无效" >[无效] </a>&nbsp';
	            	  }
	            	  
	            	  if(row.isValid==1&&row.status==1){
	            		  caozuo+='<br><a href="#" class="baoming" title="报名人员列表" >[报名人员列表] </a>';
	            	  }
	            	  return [
	            	          <shiro:hasPermission name="sys:dict:edit">
	            	          caozuo
	            	          </shiro:hasPermission>
	            	          ].join('');
	            }
		    }
		     ]
		
		});
		
	
	/*  return [
              
              <shiro:hasPermission name="sys:dict:edit">
              '<a href="#" class="edit" title="编辑" >[编辑] </a>',
              </shiro:hasPermission>
              <shiro:hasPermission name="sys:dict:edit">
              '<a href="#" class="del" title="删除" >[删除] </a>'
              </shiro:hasPermission>
      ].join('');*
		  */
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#bidInfoTable').bootstrapTable("toggleView");
		}
	  
	  $('#bidInfoTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#bidInfoTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#bidInfoTable').bootstrapTable('getSelections').length!=1);            
            $('#youxiao').prop('disabled', $('#bidInfoTable').bootstrapTable('getSelections').length!=1);            
            $('#wuxiao').prop('disabled', $('#bidInfoTable').bootstrapTable('getSelections').length!=1);            

        });
		  
		$("#btnImport").click(function(){
			jh.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/bid/bidInfo/import/template';
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
		  $('#bidInfoTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#bidInfoTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#bidInfoTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jh.confirm('确认要删除该招投标记录吗？', function(){
			jh.loading();  	
			jh.get("${ctx}/bid/bidInfo/deleteAllByLogic?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#bidInfoTable').bootstrapTable('refresh');
         	  			jh.success(data.msg);
         	  		}else{
         	  			jh.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  
  
  function updateValid(obj){
	  debugger
	  if(obj==0){
		jh.confirm('确认要上架该招投标记录吗？', function(){
			jh.loading();  	
			jh.get("${ctx}/bid/bidInfo/updateValid?ids=" + getIdSelections()+'&valid=1', function(data){
         	  		if(data.success){
         	  			$('#bidInfoTable').bootstrapTable('refresh');
         	  			jh.success(data.msg);
         	  		}else{
         	  			jh.error(data.msg);
         	  		}
         	  	})
          	   
		})
	  }
	  
	  if(obj==1){
			jh.confirm('确认要下架该招投标记录吗？', function(){
				jh.loading();  	
				jh.get("${ctx}/bid/bidInfo/updateValid?ids=" + getIdSelections()+'&valid=0', function(data){
	         	  		if(data.success){
	         	  			$('#bidInfoTable').bootstrapTable('refresh');
	         	  			jh.success(data.msg);
	         	  		}else{
	         	  			jh.error(data.msg);
	         	  		}
	         	  	})
	          	   
			})
		  }
  }
  function edit(){
	  window.location = "${ctx}/bid/bidInfo/edit?id=" + getIdSelections();
  }
  
 
</script>