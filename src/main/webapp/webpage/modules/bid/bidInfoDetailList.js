<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#bidInfoDetailTable').bootstrapTable({
		 
		  // 请求方法
               method: 'get',
               // 类型json
               dataType: "json",
               // 显示刷新按钮
               showRefresh: true,
               // 显示切换手机试图按钮
               showToggle: true,
               // 显示 内容列下拉框
    	       showColumns: true,
    	       // 显示到处按钮
    	       showExport: true,
    	       // 显示切换分页按钮
    	       showPaginationSwitch: true,
    	       // 最低显示2行
    	       minimumCountColumns: 2,
               // 是否显示行间隔色
               striped: true,
               // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
               cache: false,    
               // 是否显示分页（*）
               pagination: true,   
                // 排序方式
               sortOrder: "asc",  
               // 初始化加载第一页，默认第一页
               pageNumber:1,   
               // 每页的记录行数（*）
               pageSize: 10,  
               // 可供选择的每页的行数（*）
               pageList: [10, 25, 50, 100],
               // 这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
               url: "${ctx}/bid/bidInfoDetail/data",
               // 默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order
				// Else
               // queryParamsType:'',
               // //查询参数,每次调用是会带上这个参数，可自定义
               queryParams : function(params) {
               	var searchParam = $("#searchForm").serializeJSON();
               	searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
               	searchParam.pageSize = params.limit === undefined? -1 : params.limit;
               	searchParam.orderBy = params.sort === undefined? "" : params.sort+ " "+  params.order;
                   return searchParam;
               },
               // 分页方式：client客户端分页，server服务端分页（*）
               sidePagination: "server",
               contextMenuTrigger:"right",// pc端 按右键弹出菜单
               contextMenuTriggerMobile:"press",// 手机端 弹出菜单，click：单击， press：长按。
               contextMenu: '#context-menu',
               onContextMenuItem: function(row, $el){
                   if($el.data("item") == "edit"){
                   	window.location = "${ctx}/bid/bidInfoDetail/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jh.confirm('确认要删除该招投标记录吗？', function(){
                       	jh.loading();
                       	jh.get("${ctx}/bid/bidInfoDetail/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#bidInfoDetailTable').bootstrapTable('refresh');
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
			/*
			 * ,{ field: 'id', title: 'id', sortable: true
			 * ,formatter:function(value, row , index){ return "<a
			 * href='${ctx}/bid/bidInfoDetail/form?id="+row.id+"'>"+value+"</a>"; } }
			 */
			,{
		        field: 'projectName',
		        title: '项目名称',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	
		        	if(value.length>24){
		        		value=value.substring(0,23)+'...';
			        	}
		        	return "<a href='${ctx}/bid/bidInfoDetail/form?id="+row.id+"'>"+value+"</a>";
		         }
		    }
			,{
		        field: 'companyName',
		        title: '项目所属公司的名称',
		        sortable: true
		       
		    }
			,{
		        field: 'companyType',
		        title: '项目所属公司的类型',
		        sortable: true
		       
		    }
			,{
		        field: 'projectIndustry',
		        title: '项目所属的行业',
		        sortable: true
		       
		    }
			,{
		        field: 'projectStatus',
		        title: '项目状态',
		        sortable: true
		       
		    },{
		        field: 'status',
		        title: '项目审核状态',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	if(row.status==0){
			        	return "待审核";
			        	}else if(row.status==1){
				       return "<font color='greep'>审核通过</font>";
			        	}else{
			        	 return "<font color='red'>审核拒绝</font>";
			        	}
		         }
		       
		    }
		    ,{
		        field: 'projectContent',
		        title: '项目的内容',
		        sortable: true,
		        formatter:function(value, row , index){
		        	var text=jh.unescapeHTML(value);
		        	// alert(text.length);
		        	if(text.length>24){
		        	text=text.substring(0,23)+'...';
		        	}
		        	return text;
		        }
		       
		    }
			,{
		        field: 'projectBudget',
		        title: '项目的预算',
		        sortable: true
		       
		    }
			,{
		        field: 'projectProperties',
		        title: '项目的性质',
		        sortable: true
		       
		    }
			,{
		        field: 'startTime',
		        title: '开工时间',
		        sortable: true
		       
		    }
			,{
		        field: 'stopTime',
		        title: '竣工时间',
		        sortable: true
		       
		    }
			,{
		        field: 'buildArea',
		        title: '建筑面积',
		        sortable: true
		       
		    }
			,{
		        field: 'redecorateCondition',
		        title: '装修情况',
		        sortable: true
                
		    }
			,{
		        field: 'redecorateStand',
		        title: '装修标准',
		        sortable: true
		        ,formatter:function(value, row , index){
                	if(value.length>19){
		        		value=value.substring(0,19)+'...';
			        	}
		        	return value;
		         }  
		       
		    }
			,{
		        field: 'belongArea',
		        title: '所属地区',
		        sortable: true
		       
		    }
			/*
			 * ,{ field: 'provinceId', title: '省份id', sortable: true,
			 * formatter:function(value, row , index){ return
			 * jh.getDictLabel(${fns:toJson(fns:getDictList(''))}, value, "-"); } } ,{
			 * field: 'cityId', title: '城市id', sortable: true,
			 * formatter:function(value, row , index){ return
			 * jh.getDictLabel(${fns:toJson(fns:getDictList(''))}, value, "-"); } }
			 */
			,{
		        field: 'projectNeedProduct',
		        title: '项目所需要的产品',
		        sortable: true
		       
		    }
			,{
		        field: 'projectAddress',
		        title: '项目的地址',
		        sortable: true
                ,formatter:function(value, row , index){
		        	
		        	if(value.length>24){
		        		value=value.substring(0,23)+'...';
			        	}
		        	return value;
		         }
		    }
			,{
		        field: 'addTime',
		        title: '项目发布的时间',
		        sortable: true
		       
		    }
			,{
		        field: 'bidConcat1',
		        title: '项目所属公司联系人1',
		        sortable: true
		       
		    }
			,{
		        field: 'bidConcat1Phone',
		        title: '项目所属公司联系人1手机号',
		        sortable: true
		       
		    }
			,{
	            field: 'dd',
	            title: '&nbsp; &nbsp; &nbsp;操  &nbsp;作  &nbsp;',
	            align: 'center',
	            sortable: true,
	           /*
				 * events: { 'click .baoming': function (e, value, row, index) {
				 * window.location = "${ctx}/bid/bidSignup/lists?bidInfoId=" +
				 * row.id; } },
				 */
	            formatter:  function operateFormatter(value, row, index) {
	            	if(row.status==1){
	            		  return "<a href='${ctx}/bid/bidSignup/lists?bidInfoId="+row.id+"'class=baoming title=报名人员列表>[报名公司列表]</a>";
	            	  }else{
	            		  return "<font color='blue'>已下架无法查看报名人员</font>";
	            	  }
	            }
	          }
			
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){// 如果是移动端

		 
		  $('#bidInfoDetailTable').bootstrapTable("toggleView");
		}
	  
	  $('#bidInfoDetailTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
		
            $('#remove').prop('disabled', ! $('#bidInfoDetailTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#bidInfoDetailTable').bootstrapTable('getSelections').length!=1);
            $('#youxiao').prop('disabled', $('#bidInfoDetailTable').bootstrapTable('getSelections').length!=1);            
            $('#wuxiao').prop('disabled', $('#bidInfoDetailTable').bootstrapTable('getSelections').length!=1);            

        });
		  
		$("#btnImport").click(function(){
			jh.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/bid/bidInfoDetail/import/template';
				  },
			    btn2: function(index, layero){
				        var inputForm =top.$("#importForm");
				        var top_iframe = top.getActiveTab().attr("name");// 获取当前active的tab的iframe
				        inputForm.attr("target",top_iframe);// 表单提交成功后，从服务器返回的url在当前tab中展示
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
		  $('#bidInfoDetailTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#bidInfoDetailTable').bootstrapTable('refresh');
		});
		
		$('#startTime').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
		$('#stopTime').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
		
	});
		
  function getIdSelections() {
        return $.map($("#bidInfoDetailTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jh.confirm('确认要删除该招投标记录吗？', function(){
			jh.loading();  	
			jh.get("${ctx}/bid/bidInfoDetail/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#bidInfoDetailTable').bootstrapTable('refresh');
         	  			jh.success(data.msg);
         	  		}else{
         	  			jh.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  
  function updateValid(obj){
	  debugger
	  if(obj==1){
		jh.confirm('确认要上架该招投标记录吗？', function(){
			jh.loading();  	
			jh.get("${ctx}/bid/bidInfoDetail/updateValid?ids=" + getIdSelections()+'&valid=1', function(data){
         	  		if(data.success){
         	  			$('#bidInfoDetailTable').bootstrapTable('refresh');
         	  			jh.success(data.msg);
         	  		}else{
         	  			jh.error(data.msg);
         	  		}
         	  	})
          	   
		})
	  }
	  
	  if(obj==2){
			jh.confirm('确认要下架该招投标记录吗？', function(){
				jh.loading();  	
				jh.get("${ctx}/bid/bidInfoDetail/updateValid?ids=" + getIdSelections()+'&valid=2', function(data){
	         	  		if(data.success){
	         	  			$('#bidInfoDetailTable').bootstrapTable('refresh');
	         	  			jh.success(data.msg);
	         	  		}else{
	         	  			jh.error(data.msg);
	         	  		}
	         	  	})
	          	   
			})
		  }
  }
  
  function edit(){
	  window.location = "${ctx}/bid/bidInfoDetail/form?id=" + getIdSelections();
  }
  
</script>