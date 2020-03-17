<%@ page contentType="text/html;charset=UTF-8" %>
<script>

$(document).ready(function() {
	$('#wxMeetingTable').bootstrapTable({

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
    	       //显示详情按钮
    	       detailView: true,
    	       	//显示详细内容函数
	           detailFormatter: "detailFormatter",
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
               url: "${ctx}/wechat/wxMeeting/data",
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
                        jh.confirm('确认要删除该会议表记录吗？', function(){
                       	jh.loading();
                       	jh.get("${ctx}/wechat/wxMeeting/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#wxMeetingTable').bootstrapTable('refresh');
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
		        field: 'name',
		        title: '会议名称',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return "<a href='javascript:edit(\""+row.id+"\")'>"+value+"</a>";
		         }

		    }
			,{
		        field: 'site',
		        title: '会议地点',
		        sortable: true

		    }
			,{
		        field: 'startTime',
		        title: '会议开始时间',
		        sortable: true

		    }
			,{
		        field: 'endTime',
		        title: '会议结束时间',
		        sortable: true

		    }
			,{
		        field: 'guide',
		        title: '会务负责人',
		        sortable: true

		    }
			/*,{
		        field: 'generalize',
		        title: '会议概括',
		        sortable: true

		    }
			,{
		        field: 'introduce',
		        title: '会议介绍',
		        sortable: true

		    }
			,{
		        field: 'map',
		        title: '会议地图',
		        sortable: true

		    }*/
			,{
		        field: 'personSignNum',
		        title: '报名人数',
		        sortable: true

		    }
			,{
		        field: 'personAttendNum',
		        title: '参会人数',
		        sortable: true

		    }
			,{
		        field: 'originator',
		        title: '发起人',
		        sortable: true

		    }
			/*,{
		        field: 'img',
		        title: '封面图',
		        sortable: true

		    }
		    ,{
		        field: 'bannerImgs',
		        title: 'banner图',
		        sortable: true
		    
		    */
			,{
		        field: 'status',
		        title: '会议状态',
		        sortable: true,
			    formatter:function(value, row , index){
			    	console.log(row);
			    	var timestamp=new Date().getTime();
			    	var start = row.startTime.substring(0,19).replace(/-/g,'/');
			    	var end = row.endTime.substring(0,19).replace(/-/g,'/');
			    	start = new Date(start).getTime();
			    	end = new Date(end).getTime();
			    				    	
		    		if(start>timestamp){
		    			return '<font color="green">开始报名</font>';
		    		}else if(timestamp>end){
		    			return '<font color="red">会议结束</font>';
		    		}else{
		    			return '<font>会议开始</font>';
		    		}			    				    	
			    } 			    			
		    }
			,{
		        field: 'address',
		        title: '会议地址',
		        sortable: true
		    }
			,{
		        field: 'isDel',
		        title: '是否有效',
		        sortable: true,
			   formatter:function(value, row , index){
				   return value=="1" ? '<font color="red">无效</font>':'<font color="green">正常</font>';
			   }
		    }/*, {
				   field: 'operate',
				   title: '&nbsp&nbsp操&nbsp作&nbsp&nbsp',
				   align: 'center',
				   events: {
					   'click .schedule': function (e, value, row, index) {
						   jh.openDialogView('会议日程', '${ctx}/wechat/wxMeeting/schedule?id=' + row.id,'1100px', '500px');
					   },
                       'click .direct': function (e, value, row, index) {
                           jh.openDialogView('会议直播', '${ctx}/wechat/wxMeeting/direct?id=' + row.id,'1100px', '500px');
                       },
                       'click .signup': function (e, value, row, index) {
                           jh.openDialogView('会议报名', '${ctx}/wechat/wxMeeting/signup?id=' + row.id,'1100px', '500px');
                       },
                       'click .qdcode': function (e, value, row, index) {
                           jh.openDialogView('二维码','${ctx}/wechat/wxMeeting/qdcode?id=' + row.id,'500px', '500px');
                       },
                       'click .travel': function (e, value, row, index) {
                           jh.openDialogView('行程','${ctx}/wechat/wxTravel/list?meetingId=' + row.id,'1300px', '800px');
                       }
				   },
				   formatter:  function operateFormatter(value, row, index) {
					   return [
						   <shiro:hasPermission name="wechat:wxMeeting:schedule">
						   '<a href="#" class="schedule" title="会议日程" ><i class="fa fa-plus"></i> </a>',
				   			</shiro:hasPermission>
                       		<shiro:hasPermission name="wechat:wxMeetingDirect:direct">
                           '<a href="#" class="direct" title="会议直播" ><i class="fa fa-plus"></i> </a>',
                  			 </shiro:hasPermission>
                       		<shiro:hasPermission name="wechat:wxMeetingSignup:signup">
                           '<a href="#" class="signup" title="会议报名" ><i class="fa fa-plus"></i> </a>',
                   			</shiro:hasPermission>
				   ].join('');
				   }
				   formatter:  function operateFormatter(value, row, index) {
					   return [
						   <shiro:hasPermission name="wechat:wxMeeting:schedule">
						   '<a href="#" class="schedule btn btn-primary" style="margin-right: 5px; margin-bottom: 5px;">日程</a>',
				   			</shiro:hasPermission>
                       		<shiro:hasPermission name="wechat:wxMeetingDirect:direct">
                           '<a href="#" class="direct btn btn-success" style=" margin-bottom: 5px;">动态</a>',
                  			 </shiro:hasPermission>
                       		<shiro:hasPermission name="wechat:wxMeetingSignup:signup">
                           '<a href="#" class="signup btn btn-info" style="margin-right: 5px; margin-bottom: 5px;">报名</a>',
                   			</shiro:hasPermission>
                      		 <shiro:hasPermission name="wechat:wxTravel:list">
                           '<a href="#" class="travel btn btn-danger" style=" margin-bottom: 5px;">行程</a>',
                   			</shiro:hasPermission>
                       '<a href="#" class="qdcode btn btn-default">二维码 </a>'
				   ].join('');
				   }
			   }*/]

		});


	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端
		  $('#wxMeetingTable').bootstrapTable("toggleView");
		}

	  $('#wxMeetingTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#wxMeetingTable').bootstrapTable('getSelections').length);
            $('#lastremove').prop('disabled', ! $('#wxMeetingTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#wxMeetingTable').bootstrapTable('getSelections').length!=1);
            $('#schedule').prop('disabled', $('#wxMeetingTable').bootstrapTable('getSelections').length!=1);
            $('#direct').prop('disabled', $('#wxMeetingTable').bootstrapTable('getSelections').length!=1);
            $('#signup').prop('disabled', $('#wxMeetingTable').bootstrapTable('getSelections').length!=1);
            $('#list').prop('disabled', $('#wxMeetingTable').bootstrapTable('getSelections').length!=1);
            $('#qdcode').prop('disabled', $('#wxMeetingTable').bootstrapTable('getSelections').length!=1);
            $('#activity').prop('disabled', $('#wxMeetingTable').bootstrapTable('getSelections').length!=1);
            $('#upfile').prop('disabled', $('#wxMeetingTable').bootstrapTable('getSelections').length!=1);
        });

		$("#btnImport").click(function(){
			jh.open({
			    type: 1,
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/wechat/wxMeeting/import/template';
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
		  $('#wxMeetingTable').bootstrapTable('refresh');
		});

	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		   $("#searchForm  .select-item").html("");
		  $('#wxMeetingTable').bootstrapTable('refresh');
		});

				$('#startTime').datetimepicker({
					 format: "YYYY-MM-DD HH:mm:ss"
				});

	});

  function getIdSelections() {
        return $.map($("#wxMeetingTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  function getMeetingNameSelections() {
      return $.map($("#wxMeetingTable").bootstrapTable('getSelections'), function (row) {
          return row.meetingName
      });
  }

  function deleteAll(){
		jh.confirm('确认要删除该会议表记录吗？此操作将会把和会议相关的所有数据都将删除。此操作不可逆！请谨慎操作！', function(){
			jh.loading();
			jh.get("${ctx}/wechat/wxMeeting/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#wxMeetingTable').bootstrapTable('refresh');
         	  			jh.success(data.msg);
         	  		}else{
         	  			jh.error(data.msg);
         	  		}
         	  	})
		})
  }

  
  function add(){
	  jh.openDialog('新增会议表', "${ctx}/wechat/wxMeeting/form",'800px', '500px', $('#wxMeetingTable'));
  }

  function edit(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
	  <shiro:hasPermission name="wechat:wxMeeting:edit">
	  jh.openDialog('编辑会议表', "${ctx}/wechat/wxMeeting/form?id=" + id,'800px', '500px', $('#wxMeetingTable'));
	   </shiro:hasPermission>
	  <shiro:lacksPermission name="wechat:wxMeeting:edit">
	  jh.openDialogView('查看会议表', "${ctx}/wechat/wxMeeting/form?id=" + id,'800px', '500px', $('#wxMeetingTable'));
	  </shiro:lacksPermission>
  }
  
  function schedule(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
  	<shiro:hasPermission name="wechat:wxMeeting:edit">
	 // jh.openDialog('编辑会议表', "${ctx}/wechat/wxMeeting/form?id=" + id,'800px', '500px', $('#wxMeetingTable'));
	  jh.openDialogView('会议日程', '${ctx}/wechat/wxMeeting/schedule?id=' + id,'1100px', '500px');
	</shiro:hasPermission>	   
  }
  
  function direct(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
	<shiro:hasPermission name="wechat:wxMeeting:edit">
		jh.openDialogView('会议直播', '${ctx}/wechat/wxMeeting/direct?id=' + id,'1100px', '500px');
  	</shiro:hasPermission>
  }
  function signup(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
  	<shiro:hasPermission name="wechat:wxMeeting:edit">
  		jh.openDialogView('会议报名', '${ctx}/wechat/wxMeeting/signup?id=' + id,'1100px', '500px');
	</shiro:hasPermission>
  }
  function list(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
    	<shiro:hasPermission name="wechat:wxMeeting:edit">
    		jh.openDialogView('行程','${ctx}/wechat/wxTravel/list?meetingId=' + id,'1300px', '800px');
    	</shiro:hasPermission>
  }
  function qdcode(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
    	<shiro:hasPermission name="wechat:wxMeeting:edit">
    		jh.openDialogView('二维码','${ctx}/wechat/wxMeeting/qdcode?id=' + id,'500px', '500px');
    	</shiro:hasPermission>
  }
  function activity(id){//没有权限时，不显示确定按钮
	  
  	  if(id == undefined){
			id = getIdSelections();
		}
  	<shiro:hasPermission name="wechat:wxMeeting:edit">
    		jh.openDialogView('活动','${ctx}/wechat/cmsActivity/list?meetingId=' + id,'1300px', '500px');
    </shiro:hasPermission>
  }
function upfile(id){
	 if(id == undefined){
			id = getIdSelections();
		}
	<shiro:hasPermission name="wechat:wxMeeting:edit">
 		jh.openDialogView('会议附件','${ctx}/wechat/wxMeetingFile/list?meetingId=' + id,'1300px', '500px');
 </shiro:hasPermission>
}


  function detailFormatter(index, row) {
	  var htmltpl =  $("#wxMeetingChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/wechat/wxMeeting/detail?id="+row.id, function(wxMeeting){
			
    	var wxMeetingChild1RowIdx = 0, wxMeetingChild1Tpl = $("#wxMeetingChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  wxMeeting.wxMeetingScheduleList;
		for (var i=0; i<data1.length; i++){
			addRow('#wxMeetingChild-'+row.id+'-1-List', wxMeetingChild1RowIdx, wxMeetingChild1Tpl, data1[i]);
			wxMeetingChild1RowIdx = wxMeetingChild1RowIdx + 1;
		}
      })
        return html;
    }
  	
	function addRow(list, idx, tpl, row){
		if(row.type =="1"){
			row.type = "签到篇"
		}else if(row.type =="2"){
			row.type = "开场篇"
		}else if(row.type =="3"){
			row.type = "推介篇"
		}else{
			row.type = "结束篇"
		}
		$(list).append(Mustache.render(tpl, {
			idx: idx, delBtn: true, row: row
		}));
	
	}


</script>
<script type="text/template" id="wxMeetingChildrenTpl">//<!--
	<div class="tabs-container">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-{{idx}}-1" aria-expanded="true">会议日程表</a></li>
		</ul>
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade in active">
						<table class="ani table">
						<thead>
							<tr>
								<th>开始时间</th>
								<th>结束时间</th>
								<th>活动内容</th>
								<th>会议日期</th>
								<th>内容类型</th>
							</tr>
						</thead>
						<tbody id="wxMeetingChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
		</div>//-->
	</script>
	<script type="text/template" id="wxMeetingChild1Tpl">//<!--
				<tr>
					<td>
						{{row.startTime}}
					</td>
					<td>
						{{row.endTime}}
					</td>
					<td>
						{{row.content}}
					</td>
					<td>
						{{row.conferenceDay}}
					</td>
					<td>
						{{row.type}}
					</td>
				</tr>//-->
	</script>
