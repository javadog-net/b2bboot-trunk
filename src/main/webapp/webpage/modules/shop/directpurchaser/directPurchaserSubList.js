<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#directPurchaserTable').bootstrapTable({
		 
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
               url: "${ctx}/shop/directpurchaser/directpurchaser/noreldata?id=${pid}",
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
                   	window.location = "${ctx}/shop/directpurchaser/directpurchaser/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                       del(row.id);
                   } 
               },
              
               onClickRow: function(row, $el){
               },
               columns: [{
		        checkbox: true
		       
		    }
            ,{
   		        field: 'loginName',
   		        title: '用户名',
   		        sortable: true
   		       
   		    }
            ,{
   		        field: 'loginNum',
   		        title: '企业88码',
   		        sortable: true
   		       
   		    }
			/*,{
		        field: 'companyCode',
		        title: '公司代码',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "<a href='${ctx}/shop/directpurchaser/directpurchaser/form?id="+row.id+"'>"+value+"</a>";
		         }
		       
		    }*/
			,{
		        field: 'companyName',
		        title: '公司名称',
		        sortable: true
		       
		    }
			/*,{
		        field: 'companyNum',
		        title: '公司人数',
		        sortable: true

		    }*/
			,{
		        field: 'areaInfo',
		        title: '所在地区',
		        sortable: true
		       
		    }
			/*,{
		        field: 'detailAddress',
		        title: '详细地址',
		        sortable: true

		    }
			,{
		        field: 'industryName',
		        title: '行业',
		        sortable: true

		    }*/
			,{
		        field: 'contacts',
		        title: '联系人',
		        sortable: true
		       
		    }
			,{
		        field: 'mobile',
		        title: '手机',
		        sortable: true
		       
		    }
			,{
		        field: 'email',
		        title: '邮箱',
		        sortable: true
		       
		    }
			/*
			,{
		        field: 'companyNature',
		        title: '公司性质',
		        sortable: true
		       
		    }
			,{
		        field: 'invitationCode',
		        title: '邀请码',
		        sortable: true
		       
		    }*/
			,{
	        field: 'companyType',
	        title: '用户类型',
	        sortable: true
	        ,formatter:function(value, row , index){

			   	if(0==value){
			   		return "散户"
				}else if(1==value){
                    return "战略"
				}
				}
			}
			,{
		        field: 'isAdmin',
		        title: '账户类型 ',
		        sortable: true
		        ,formatter:function(value, row , index){

				   	if(0==value){
				   		return "子账号"
					}else{
	                    return "主账号"
					}
					}
				}
			,{
		        field: 'auditState',
		        title: '审核状态',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jh.getDictLabel(${fns:toJson(fns:getDictList('audit_state'))}, value, "-");
		        }
		       
		    }
//			,{
//		        field: 'auditTime',
//		        title: '审核时间 ',
//		        sortable: true
//		       
//		    }
//			,{
//		        field: 'auditor',
//		        title: '审核人',
//		        sortable: true
//		       
//		    }
//			,{
//		        field: 'auditDesc',
//		        title: '审核意见',
//		        sortable: true
//		       
//		    }
//           ,{
//               field: 'updateDate',
//               title: '更新时间',
//               sortable: true
//
//           }
           , {
                       field: 'operate',
                       title: '操作',
                       align: 'center',
                       formatter:  function operateFormatter(value, row, index) {
                           return [
                        	   "<a href='javascript:savePurchaserRel(\""+row.id+"\")'>关联子账号</a>"
                        	   
                       ].join('');
                       }
                   }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#directPurchaserTable').bootstrapTable("toggleView");
		}
	  
	  $('#directPurchaserTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
          var sels = $('#directPurchaserTable').bootstrapTable('getSelections');
          $('#remove').prop('disabled', ! sels.length);
          $('#edit').prop('disabled', sels.length!=1);
          if(sels.length == 1 && sels[0].auditState =='0'){
              $('#audit').prop('disabled', false);
          } else {
              $('#audit').prop('disabled', true);
          }
        });
		  
		$("#btnImport").click(function(){
			jh.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/shop/directpurchaser/directpurchaser/import/template';
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
		  $('#directPurchaserTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#directPurchaserTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#directPurchaserTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  function savePurchaserRelAll(){

		jh.confirm('确认要关联子账号吗？', function(){
			jh.loading();  	
			jh.get("${ctx}/purchasermainrel/purchaserMasterSlaveRel/savePurchaserRelAll?ids=" + getIdSelections()+"&purchaserId=${pid}", function(data){
         	  		if(data.success){
         	  			$('#directPurchaserTable').bootstrapTable('refresh');
         	  			jh.success(data.msg);
         	  		}else{
         	  			jh.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  
  function savePurchaserRel(id){
		jh.confirm('确认要关联子账号吗？', function(){
			jh.loading();  	
			jh.get("${ctx}/purchasermainrel/purchaserMasterSlaveRel/savePurchaserRelAll?ids=" + id+"&purchaserId=${pid}", function(data){
       	  		if(data.success){
       	  			$('#directPurchaserTable').bootstrapTable('refresh');
       	  			jh.success(data.msg);
       	  		}else{
       	  			jh.error(data.msg);
       	  		}
       	  	})
        	   
		})
  }
function audit(id){
    if(id == undefined){
        id = getIdSelections();
    }
    jh.open({
        type: 1,
        area: ['400px','200px'],
        title:"审核",
        content:$("#auditBox").html() ,
        scrollbar: false,
        btn: ['确定', '关闭'],
        btn1: function(index, layero){
            var inputForm = layero.find("#auditForm");
            var sel = inputForm.find("input[name='auditState']:checked").val();
            if(sel==undefined){
                jh.alert('请选择是否同意');
                return false;
            }
            if(sel=='2'){
                var auditDesc = inputForm.find('#auditDesc');
                if($.trim(auditDesc.val())==''){
                    jh.alert('请输入不同意原因');
                    return false;
                }
            }
            jh.loading('  正在审核，请稍等...');
            jh.post("${ctx}/shop/directpurchaser/directpurchaser/audit",inputForm.serialize(),function(data){
                if(data.success){
                    $('#directPurchaserTable').bootstrapTable('refresh');
                    jh.success(data.msg);
                }else{
                    jh.error(data.msg);
                }
            });
            jh.close(index);
        },

        btn2: function(index){
            jh.close(index);
        },
        success: function(layero, index){
            //窗口打开后做初始化
            var contElem = layero.find('.layui-layer-content');
            var inputForm = contElem.find("#auditForm");
            var idElem = inputForm.find('#auditId');
            idElem.val(id);
            var auditDescDiv = inputForm.find('#auditDescDiv');
            var auditDesc = inputForm.find('#auditDesc');
            var conHeight = contElem.height();
            var layerHeight = layero.height();
            inputForm.find("input[name='auditState']").change(function(){
                var sel = $(this).val();
                if(sel == "1"){
                    auditDescDiv.addClass('hide');
                    auditDesc.val('');
                    layero.height(layerHeight);
                    contElem.height(conHeight);
                } else if(sel == "2"){
                    auditDescDiv.removeClass('hide');
                    layero.height(layerHeight+120);
                    contElem.height(conHeight+120);
                    auditDesc.focus();
                }
            })
        }
    });

}
//

$(document).ready(function() {
    var $purchaserAccoutTable =	$('#purchaserAccoutTable').bootstrapTable({
        //请求方法
        method: 'get',
        dataType: "json",
        //是否显示行间隔色
        striped: true,
        //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        cache: false,
        //是否显示分页（*）
        pagination: false,
        //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
        url: "${ctx}/shop/directpurchaser/directpurchaser/accountData",
        //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
        //queryParamsType:'',
        ////查询参数,每次调用是会带上这个参数，可自定义
        queryParams : function(params) {
            return {purchaserId:$("#purchaserId").val()};
        },
        //分页方式：client客户端分页，server服务端分页（*）
        sidePagination: "server",
        columns: [{
            field: 'loginName',
            title: '账号'
        }, {
            field: 'realName',
            title: '姓名'
        },{
            field: 'isAdmin',
            title: '管理员',
            sortable: true,
            formatter:function(value, row , index) {
                return jh.getDictLabel(${fns:toJson(fns:getDictList('yes_no'))}, value, "-");
            }
        }, {
            field: 'operate',
            title: '操作',
            align: 'center',
            events: {
                'click .edit': function (e, value, row, index) {

                    jh.openDialog('编辑子账号', '${ctx}/shop/directpurchaser/directpurchaser/accountForm?purchaserId=' + $("#purchaserId").val()+"&id="+row.id,'800px', '500px', $purchaserAccoutTable);
                },
                'click .del': function (e, value, row, index) {

                    jh.confirm('确认要删除子账号吗？',function(){
                        jh.loading();
                        $.get('${ctx}/shop/directpurchaser/directpurchaser/deleteAccount?id='+row.id+'&purchaserId=' + $("#purchaserId").val(), function(data){
                            if(data.success){
                                $('#purchaserAccoutTable').bootstrapTable("refresh");
                                jh.success(data.msg);
                            }else{
                                jh.error(data.msg);
                            }
                        })
                    });
                }
            },
            formatter:  function operateFormatter(value, row, index) {
                return [
                    <shiro:hasPermission name="sys:dict:edit">
                    '<a href="#" class="edit" title="编辑" >[编辑] </a>',
            </shiro:hasPermission>
                <shiro:hasPermission name="sys:dict:edit">
                    '<a href="#" class="del" title="删除" >[删除] </a>'
                    </shiro:hasPermission>
            ].join('');
            }
        }]

    });

    if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端
        $('#purchaserAccoutTable').bootstrapTable("toggleView");
    }

    $("#purchaserAccountButton").click(function(){

        jh.openDialog('添加子账号', '${ctx}/shop/directpurchaser/directpurchaser/accountForm?purchaserId=' + $("#purchaserId").val(),'800px', '500px', $purchaserAccoutTable);
    });

});
</script>