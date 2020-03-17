<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#cmsShortMsgTable').bootstrapTable({
		 
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
               url: "${ctx}/tools/sms/cmsShortMsg/data",
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
                   	window.location = "${ctx}/tools/sms/cmsShortMsg/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jh.confirm('确认要删除该发送短信记录吗？', function(){
                       	jh.loading();
                       	jh.get("${ctx}/tools/sms/cmsShortMsg/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#cmsShortMsgTable').bootstrapTable('refresh');
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
			/*,{
		        field: 'title',
		        title: '标题',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "<a href='${ctx}/tools/sms/cmsShortMsg/form?id="+row.id+"'>"+value+"</a>";
		         }
		       
		    }*/
			,{
		        field: 'content',
		        title: '内容',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "<a href='${ctx}/tools/sms/cmsShortMsg/form?id="+row.id+"'>"+value+"</a>";
		         }
		       
		    }
			,{
		        field: 'msgtype',
		        title: '短信分类',
		        sortable: true
		       
		    }
			,{
		        field: 'creater',
		        title: '创建人',
		        sortable: true
		       
		    }
			,{
		        field: 'areaname',
		        title: '地域',
		        sortable: true
		       
		    }
			,{
		        field: 'membertype',
		        title: '会员类型',
		        sortable: true
		       
		    }
			,{
		        field: 'createtime',
		        title: '创建时间',
		        sortable: true
		       
		    }
			,{
		        field: 'isshortmsg',
		        title: '是否发送',
		        sortable: true,
		        formatter:function(value, row , index){
		        	if(value==0){
		        		return '<font color="gray">' + "未发送" + '</font>';
		        	}else if(value==1){
		        		return '<font color="green">' + "已发送" + '</font>';
		        	}
		        }
		       
		    }
			,{
		        field: 'sendtime',
		        title: '发送时间',
		        sortable: true
		       
		    }
			,{
		        field: 'sender',
		        title: '发件人',
		        sortable: true
		       
		    }
			,{
		        field: 'sendtype',
		        title: '发送方式',
		        sortable: true,
		        formatter:function(value, row , index){
		        	if(value==0){
		        		return '<font color="green">' + "单发" + '</font>';
		        	}else if(value==1){
		        		return '<font color="green">' + "群发" + '</font>';
		        	}
		        }
		       
		    }
			,{
		        field: 'mobilephone',
		        title: '手机号',
		        sortable: true
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#cmsShortMsgTable').bootstrapTable("toggleView");
		}
	  
	  $('#cmsShortMsgTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#cmsShortMsgTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#cmsShortMsgTable').bootstrapTable('getSelections').length!=1);
            $('#sendsms').prop('disabled', $('#cmsShortMsgTable').bootstrapTable('getSelections').length!=1);
            
        });
		  
		$("#btnImport").click(function(){
			jh.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/tools/sms/cmsShortMsg/import/template';
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
		  $('#cmsShortMsgTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#cmsShortMsgTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#cmsShortMsgTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
    function getIsSendOr() {
        return $.map($("#cmsShortMsgTable").bootstrapTable('getSelections'), function (row) {
            return row.isshortmsg
        });
    }
  function deleteAll(){

		jh.confirm('确认要删除该发送短信记录吗？', function(){
			jh.loading();  	
			jh.get("${ctx}/tools/sms/cmsShortMsg/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#cmsShortMsgTable').bootstrapTable('refresh');
         	  			jh.success(data.msg);
         	  		}else{
         	  			jh.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function edit(){
	  var isSendOr=getIsSendOr();
		if(isSendOr==1){
			jh.alert("已发送，不可经行修改保存操作！");
		}
	  window.location = "${ctx}/tools/sms/cmsShortMsg/form?id=" + getIdSelections();
  }   
  
 function sendsms(){
	var isSendOr=getIsSendOr();
	if(isSendOr==1){
		jh.alert("已发送，不可经行重复操作！");
		return false;
	}
		jh.confirm('确认要发送该短信吗？此操作不可逆！', function(){
			jh.loading();  	
			jh.get("${ctx}/tools/sms/cmsShortMsg/send?id=" + getIdSelections(), function(data){
       	  		if(data.success){
       	  			$('#cmsShortMsgTable').bootstrapTable('refresh');
       	  			jh.success(data.msg);
       	  		}else{
       	  			jh.error(data.msg);
       	  		}
       	  	})
        	   
		})
}
  
</script>