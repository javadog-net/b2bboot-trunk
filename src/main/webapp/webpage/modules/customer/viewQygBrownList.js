<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#viewQygBrownTable').bootstrapTable({
		 
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
               url: "${ctx}/customer/viewQygBrown/data",
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
                   	window.location = "${ctx}/customer/viewQygBrown/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jh.confirm('确认要删除该工程版信息视图记录吗？', function(){
                       	jh.loading();
                       	jh.get("${ctx}/customer/viewQygBrown/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#viewQygBrownTable').bootstrapTable('refresh');
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
		        field: 'brownId',
		        title: 'brown_id',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "<a href='${ctx}/customer/viewQygBrown/form?id="+row.id+"'>"+value+"</a>";
		         }
		       
		    }
			,{
		        field: 'projectId',
		        title: 'project_id',
		        sortable: true
		       
		    }
			,{
		        field: 'projectCode',
		        title: 'project_code',
		        sortable: true
		       
		    }
			,{
		        field: 'projectName',
		        title: 'project_name',
		        sortable: true
		       
		    }
			,{
		        field: 'contractId',
		        title: 'contract_id',
		        sortable: true
		       
		    }
			,{
		        field: 'brownCode',
		        title: 'brown_code',
		        sortable: true
		       
		    }
			,{
		        field: 'productGroupId',
		        title: 'product_group_id',
		        sortable: true
		       
		    }
			,{
		        field: 'productGroupCode',
		        title: 'product_group_code',
		        sortable: true
		       
		    }
			,{
		        field: 'productGroupName',
		        title: 'product_group_name',
		        sortable: true
		       
		    }
			,{
		        field: 'dealerId',
		        title: 'dealer_id',
		        sortable: true
		       
		    }
			,{
		        field: 'dealerCode',
		        title: 'dealer_code',
		        sortable: true
		       
		    }
			,{
		        field: 'dealerName',
		        title: 'dealer_name',
		        sortable: true
		       
		    }
			,{
		        field: 'center',
		        title: 'center',
		        sortable: true
		       
		    }
			,{
		        field: 'type',
		        title: 'type',
		        sortable: true
		       
		    }
			,{
		        field: 'domainCode',
		        title: 'domain_code',
		        sortable: true
		       
		    }
			,{
		        field: 'openSystem',
		        title: 'open_system',
		        sortable: true
		       
		    }
			,{
		        field: 'brownCount',
		        title: 'brown_count',
		        sortable: true
		       
		    }
			,{
		        field: 'expireTime',
		        title: 'expire_time',
		        sortable: true
		       
		    }
			,{
		        field: 'beValid',
		        title: 'be_valid',
		        sortable: true
		       
		    }
			,{
		        field: 'isCheck',
		        title: 'is_check',
		        sortable: true
		       
		    }
			,{
		        field: 'effectTime',
		        title: 'effect_time',
		        sortable: true
		       
		    }
			,{
		        field: 'isSuccess',
		        title: 'is_success',
		        sortable: true
		       
		    }
			,{
		        field: 'rrsBeSuccess',
		        title: 'rrs_be_success',
		        sortable: true
		       
		    }
			,{
		        field: 'rrsSuccessTime',
		        title: 'rrs_success_time',
		        sortable: true
		       
		    }
			,{
		        field: 'cdepcode',
		        title: 'cdepcode',
		        sortable: true
		       
		    }
			,{
		        field: 'caddress',
		        title: 'caddress',
		        sortable: true
		       
		    }
			,{
		        field: 'gvsBeSuccess',
		        title: 'gvs_be_success',
		        sortable: true
		       
		    }
			,{
		        field: 'gvsTime',
		        title: 'gvs_time',
		        sortable: true
		       
		    }
			,{
		        field: 'gvsMessage',
		        title: 'gvs_message',
		        sortable: true
		       
		    }
			,{
		        field: 'lastModifiedDate',
		        title: '最后更新时间',
		        sortable: true
		       
		    }
			,{
		        field: 'orderQuantitySum',
		        title: '申请总数量',
		        sortable: true
		       
		    }
			,{
		        field: 'expireTimeBeforeApprove',
		        title: '到期日期(审批通过后会将该字段更新到expire_time上)',
		        sortable: true
		       
		    }
			,{
		        field: 'typeBeforeApprove',
		        title: '类型(审批通过后会将该字段更新到type上)',
		        sortable: true
		       
		    }
			,{
		        field: 'basePostId',
		        title: '基础岗位ID',
		        sortable: true
		       
		    }
			,{
		        field: 'basePostCode',
		        title: '基础岗位编码',
		        sortable: true
		       
		    }
			,{
		        field: 'basePostName',
		        title: '基础岗位名称',
		        sortable: true
		       
		    }
			,{
		        field: 'gmzt',
		        title: '帐套',
		        sortable: true
		       
		    }
			,{
		        field: 'infoSourse',
		        title: '数据源',
		        sortable: true
		       
		    }
			,{
		        field: 'cusAddress',
		        title: '客户地址',
		        sortable: true
		       
		    }
			,{
		        field: 'custContact',
		        title: '联系人',
		        sortable: true
		       
		    }
			,{
		        field: 'ctel',
		        title: '电话',
		        sortable: true
		       
		    }
			,{
		        field: 'cmobile',
		        title: '移动电话',
		        sortable: true
		       
		    }
			,{
		        field: 'cproName',
		        title: '工程单位-R1甲方名称',
		        sortable: true
		       
		    }
			,{
		        field: 'cproAddress',
		        title: '工程单位地址-R项目地址',
		        sortable: true
		       
		    }
			,{
		        field: 'cproContact',
		        title: 'R2联系人',
		        sortable: true
		       
		    }
			,{
		        field: 'cproTel',
		        title: 'R2联系方式',
		        sortable: true
		       
		    }
			,{
		        field: 'cproMobile',
		        title: '移动电话2',
		        sortable: true
		       
		    }
			,{
		        field: 'sysId',
		        title: 'R2行业门类编码',
		        sortable: true
		       
		    }
			,{
		        field: 'sysName',
		        title: 'R2行业门类名称',
		        sortable: true
		       
		    }
			,{
		        field: 'backWard',
		        title: '政策兑现形式',
		        sortable: true
		       
		    }
			,{
		        field: 'iallMoney',
		        title: '整单金额',
		        sortable: true
		       
		    }
			,{
		        field: 'billFlag',
		        title: '单据类型',
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

		 
		  $('#viewQygBrownTable').bootstrapTable("toggleView");
		}
	  
	  $('#viewQygBrownTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#viewQygBrownTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#viewQygBrownTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jh.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/customer/viewQygBrown/import/template';
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
		  $('#viewQygBrownTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#viewQygBrownTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#viewQygBrownTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jh.confirm('确认要删除该工程版信息视图记录吗？', function(){
			jh.loading();  	
			jh.get("${ctx}/customer/viewQygBrown/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#viewQygBrownTable').bootstrapTable('refresh');
         	  			jh.success(data.msg);
         	  		}else{
         	  			jh.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function edit(){
	  window.location = "${ctx}/customer/viewQygBrown/form?id=" + getIdSelections();
  }
  
</script>