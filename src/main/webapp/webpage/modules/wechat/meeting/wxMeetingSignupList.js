<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
    var id = "${wxMeeting.id}";
    if (!id) {
        id = "${wxMeetingSignup.meetingId}";
    }
	$('#wxMeetingSignupTable').bootstrapTable({
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
               pageList: [10, 25, 50, 100,300,500,1000],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
               url: "${ctx}/wechat/wxMeetingSignup/data?meetingId="+id,
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
                   	edit(row.id);
                   } else if($el.data("item") == "delete"){
                        jh.confirm('确认要删除该会议报名表记录吗？', function(){
                       	jh.loading();
                       	jh.get("${ctx}/wechat/wxMeetingSignup/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#wxMeetingSignupTable').bootstrapTable('refresh');
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
               field: 'realName',
               title: '报名人',
               sortable: true

           }
           ,{
               field: 'companyName',
               title: '公司名称',
               sortable: true

           }
           ,{
               field: 'mobile',
               title: '联系电话',
               sortable: true

           }
			,{
		        field: 'signStatus',
		        title: '报名审核状态',
		        sortable: true,
		        formatter:function(value, row , index){
		        	// return "<a href='javascript:edit(\""+row.id+"\")'>"+jh.getDictLabel(${fns:toJson(fns:getDictList('sign_status'))}, value, "-")+"</a>";
		        	/*jh.getDictLabel(${fns:toJson(fns:getDictList('sign_status'))}, value, "-");*/
                   if(value=="0"){
		        		return '<font color="#000000">' + "待审核" + '</font>';
		        	}else if(value=="1"){
		        		return '<font color="green">' + "已审核" + '</font>';
		        	}else if(value=="2"){
		        		return '<font color="red">' + "已拒绝" + '</font>';
		        	}else if(value=="3"){
		        		return '<font color="gray">' + "已签到" + '</font>';
		        	}else{
		        		return '<font color="green">' + "已审核等待填写行程" + '</font>';
		        	}
		        }

		    }
			,{
		        field: 'signTime',
		        title: '报名时间',
		        sortable: true

		    }
			,{
		        field: 'examinePerson',
		        title: '审核人',
		        sortable: true

		    }
			,{
		        field: 'examineTime',
		        title: '审核时间',
		        sortable: true

		    }
           ,{
        	   field: 'seatNum',
		        title: '座号',
		        sortable: true

           }
			,{
		        field: 'tableNum',
		        title: '桌号',
		        sortable: true

		    },{
		        field: 'carNum',
		        title: '车次',
		        sortable: true

		    },
		   {
			   field: 'orgname',
			   title: '工贸',
			   sortable: true

		   }
		     ]

		});


	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端


		  $('#wxMeetingSignupTable').bootstrapTable("toggleView");
		}

	  $('#wxMeetingSignupTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#wxMeetingSignupTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#wxMeetingSignupTable').bootstrapTable('getSelections').length!=1);
            $('#xingcheng').prop('disabled', ! $('#wxMeetingSignupTable').bootstrapTable('getSelections').length);
            $('#jujue').prop('disabled', ! $('#wxMeetingSignupTable').bootstrapTable('getSelections').length);
            $('#qiandao').prop('disabled', ! $('#wxMeetingSignupTable').bootstrapTable('getSelections').length);
        });

		$("#btnImport").click(function(){
			jh.open({
			    type: 1,
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){                                  
					  window.location='${ctx}/wechat/wxMeetingSignup/import/template?meetingId='+id;
				  },
			    btn2: function(index, layero){
				        var inputForm =top.$("#importForm");
				        var top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe
				        inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
				        inputForm.onsubmit = function(){
				        	jh.loading('  正在导入，请稍等...');
				        }
				        jh.close(index);
				        inputForm.submit();
				  },

				  btn3: function(index){
					  jh.close(index);
	    	       }
			});
		});

	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#wxMeetingSignupTable').bootstrapTable('refresh');
		});

	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#wxMeetingSignupTable').bootstrapTable('refresh');
		});

		$('#signTime').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
		$('#attendTime').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});

	});

  function getIdSelections() {
        return $.map($("#wxMeetingSignupTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }


  function getmeetingId() {
        return $.map($("#wxMeetingSignupTable").bootstrapTable('getSelections'), function (row) {
            return row.meetingId
        });
    }

  
  function deleteAll(){
		jh.confirm('确认要删除该会议报名表记录吗？', function(){
			jh.loading();
			jh.get("${ctx}/wechat/wxMeetingSignup/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#wxMeetingSignupTable').bootstrapTable('refresh');
         	  			jh.success(data.msg);
         	  		}else{
         	  			jh.error(data.msg);
         	  		}
         	  	})
		})
  }
  
  function signupAll(){
		jh.confirm('确认要进行签到处理吗？', function(){
			jh.loading();
			jh.get("${ctx}/wechat/wxMeetingSignup/signupAll?ids=" + getIdSelections(), function(data){
       	  		if(data.success){
       	  			$('#wxMeetingSignupTable').bootstrapTable('refresh');
       	  			jh.success(data.msg);
       	  		}else{
       	  			jh.error(data.msg);
       	  		}
       	  	})
		})
}
  
   function add(){
	  jh.openDialog('新增会议报名表', "${ctx}/wechat/wxMeetingSignup/form?meetingId=${wxMeeting.id}",'800px', '500px', $('#wxMeetingSignupTable'));
  }
  function edit(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
      window.location = "${ctx}/wechat/wxMeetingSignup/form?id=" + getIdSelections();
	   // <shiro:hasPermission name="wechat:wxMeetingSignup:edit">
        //   jh.openDialogView('查看会议报名表', "${ctx}/wechat/wxMeetingSignup/form?id=" + id,'800px', '500px', $('#wxMeetingSignupTable'));
	   // </shiro:hasPermission>
  }
  //会议前消息提醒短信
	function send(){
        jh.confirm('确认要发送会前通知短信吗？', function(){
            jh.loading();
            jh.get("${ctx}/wechat/wxMeetingSignup/notifyMsg?id=${wxMeeting.id}", function(data){
            	debugger;
                if(data.success){
                    jh.success(data.msg);
                }else{
                    jh.error(data.msg);
                }
            })
        })
	}
	
    function audit(){
        jh.confirm('确认要操作这些会议报名记录吗？', function(){
            jh.loading();
            jh.get("${ctx}/wechat/wxMeetingSignup/audit?ids=" + getIdSelections(), function(data){
                if(data.success){
                    $('#wxMeetingSignupTable').bootstrapTable('refresh');
                    jh.success(data.msg);
                }else{
                    jh.error(data.msg);
                }
            })
        })
    }

    function auditandschedule(){
        jh.confirm('确认要操作这些会议报名记录吗？', function(){
            jh.loading();
            jh.get("${ctx}/wechat/wxMeetingSignup/auditandschedule?ids=" + getIdSelections(), function(data){
                if(data.success){
                    $('#wxMeetingSignupTable').bootstrapTable('refresh');
                    jh.success(data.msg);
                }else{
                    jh.error(data.msg);
                }
            })
        })
    }
    function reverseAudit(){
        jh.confirm('确认要操作这些会议报名记录吗？', function(){
            jh.loading();
            jh.get("${ctx}/wechat/wxMeetingSignup/reverseAudit?ids=" + getIdSelections(), function(data){
                if(data.success){
                    $('#wxMeetingSignupTable').bootstrapTable('refresh');
                    jh.success(data.msg);
                }else{
                    jh.error(data.msg);
                }
            })
        })
    }

</script>