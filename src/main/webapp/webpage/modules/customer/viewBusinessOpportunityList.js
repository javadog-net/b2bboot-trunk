<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#viewBusinessOpportunityTable').bootstrapTable({
		 
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
               url: "${ctx}/customer/viewBusinessOpportunity/data",
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
                   	window.location = "${ctx}/customer/viewBusinessOpportunity/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jh.confirm('确认要删除该项目履约记录吗？', function(){
                       	jh.loading();
                       	jh.get("${ctx}/customer/viewBusinessOpportunity/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#viewBusinessOpportunityTable').bootstrapTable('refresh');
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
		        field: 'projectId',
		        title: '项目id',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "<a href='${ctx}/customer/viewBusinessOpportunity/form?id="+row.id+"'>"+value+"</a>";
		         }
		       
		    }
			,{
		        field: 'projectCode',
		        title: '项目编码',
		        sortable: true
		       
		    }
			,{
		        field: 'projectName',
		        title: '项目名称',
		        sortable: true
		       
		    }
			,{
		        field: 'custCode',
		        title: '经销商编码',
		        sortable: true
		       
		    }
			,{
		        field: 'custName',
		        title: 'cust_name',
		        sortable: true
		       
		    }
			,{
		        field: 'centerCode',
		        title: '中心编码',
		        sortable: true
		       
		    }
			,{
		        field: 'centerName',
		        title: '中心名称',
		        sortable: true
		       
		    }
			,{
		        field: 'gridCenterName',
		        title: '网格小微',
		        sortable: true
		       
		    }
			,{
		        field: 'gridName',
		        title: '网格',
		        sortable: true
		       
		    }
			,{
		        field: 'firstCompanyName',
		        title: '甲方公司名称',
		        sortable: true
		       
		    }
			,{
		        field: 'firstCompanyOrgCode',
		        title: '甲方统一社会信用代码',
		        sortable: true
		       
		    }
			,{
		        field: 'brownCode',
		        title: '工程版单号',
		        sortable: true
		       
		    }
			,{
		        field: 'industryHomeCategoryId',
		        title: '行业id',
		        sortable: true
		       
		    }
			,{
		        field: 'industryHomeCategoryName',
		        title: '行业名称',
		        sortable: true
		       
		    }
			,{
		        field: 'projectSource',
		        title: '项目来源',
		        sortable: true
		       
		    }
			,{
		        field: 'projectManagerName',
		        title: '海尔接口人',
		        sortable: true
		       
		    }
			,{
		        field: 'projectManagerMobile',
		        title: '海尔接口人电话',
		        sortable: true
		       
		    }
			,{
		        field: 'effectTime',
		        title: '工程版生效时间',
		        sortable: true
		       
		    }
			,{
		        field: 'dcheckdatedy',
		        title: '协议签订时间',
		        sortable: true
		       
		    }
			,{
		        field: 'projectstate',
		        title: '项目状态',
		        sortable: true
		       
		    }
			,{
		        field: 'lastModifiedDateBrown',
		        title: '更新时间',
		        sortable: true
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#viewBusinessOpportunityTable').bootstrapTable("toggleView");
		}
	  
	  $('#viewBusinessOpportunityTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#viewBusinessOpportunityTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#viewBusinessOpportunityTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jh.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/customer/viewBusinessOpportunity/import/template';
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
		  $('#viewBusinessOpportunityTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#viewBusinessOpportunityTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#viewBusinessOpportunityTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jh.confirm('确认要删除该项目履约记录吗？', function(){
			jh.loading();  	
			jh.get("${ctx}/customer/viewBusinessOpportunity/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#viewBusinessOpportunityTable').bootstrapTable('refresh');
         	  			jh.success(data.msg);
         	  		}else{
         	  			jh.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function edit(){
	  window.location = "${ctx}/customer/viewBusinessOpportunity/form?id=" + getIdSelections();
  }
  
</script>