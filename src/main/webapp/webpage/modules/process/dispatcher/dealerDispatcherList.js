<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#dealerTable').bootstrapTable({
		 
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
               url: "${ctx}/process/shopmsg/shopMsg/dataDispatcher",
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
                   	window.location = "${ctx}/shop/dealer/dealer/form?id=" + row.id;
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
		        field: 'companyCode',
		        title: '经销商编码',
		        sortable: true

		    }
			,{
		        field: 'companyName',
		        title: '经销商名称',
		        sortable: true
		       
		    }
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
		        field: 'undertakeArea',
		        title: '承接区域',
		        sortable: true
		       
		    }
			,{
		        field: 'underProduct',
		        title: '承接品类',
		        sortable: true
		       
		    },{
               field: 'gmName',
               title: '工贸名称',
               sortable: true

           },

           /*
                  ,{
                      field: 'channelName',
                      title: '渠道名称',
                      sortable: true

                  }
                  ,{
                      field: 'taxCode',
                      title: '税码',
                      sortable: true

                  }
                  ,{
                      field: 'kjtAccount',
                      title: '快捷通账号',
                      sortable: true

                  }
                  ,{
                      field: 'legalPersonName',
                      title: '法人姓名',
                      sortable: true

                  }
                  ,{
                      field: 'legalPersonIdCard',
                      title: '法人身份号',
                      sortable: true

                  }
                  ,{
                      field: 'companyTel',
                      title: '公司电话',
                      sortable: true

                  }
                  */

/*			,{
		        field: 'undertakeArea',
		        title: '承接区域',
		        sortable: true
		       
		    }*/
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#dealerTable').bootstrapTable("toggleView");
		}
	  
	  $('#dealerTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
          var sels = $('#dealerTable').bootstrapTable('getSelections');
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
					  window.location='${ctx}/shop/dealer/dealer/import/template';
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
		  $('#dealerTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#dealerTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#dealerTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }


function getNameSelections() {
    return $.map($("#dealerTable").bootstrapTable('getSelections'), function (row) {
        return row.companyName
    });
}

  function del(id){
      jh.confirm('确认要删除该经销商记录吗？', function(){
          jh.loading();
          jh.get("${ctx}/shop/dealer/dealer/delete?id="+id, function(data){
              if(data.success){
                  $('#dealerTable').bootstrapTable('refresh');
                  jh.success(data.msg);
              }else{
                  jh.error(data.msg);
              }
          })

      });
  }

  function deleteAll(){

		jh.confirm('确认要删除该经销商记录吗？', function(){
			jh.loading();  	
			jh.get("${ctx}/shop/dealer/dealer/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#dealerTable').bootstrapTable('refresh');
         	  			jh.success(data.msg);
         	  		}else{
         	  			jh.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function edit(){
	  window.location = "${ctx}/shop/dealer/dealer/form?id=" + getIdSelections();
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
            jh.post("${ctx}/shop/dealer/dealer/audit",inputForm.serialize(),function(data){
                if(data.success){
                    $('#dealerTable').bootstrapTable('refresh');
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

var callbackdata = function () {
    var arrIds = getIdSelections();
    var arrNames = getNameSelections();
    return {
        arrIds:arrIds,
        arrNames:arrNames
    };
  }

</script>