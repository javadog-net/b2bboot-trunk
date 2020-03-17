<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>企业专享价设置</title>
    <meta name="decorator" content="ani"/>
    <style>
        .detail{
            display: none;
        }
    </style>
    <script type="text/javascript">
		    var validateForm;
			var $table; // 父页面table表格id
			var $topIndex;//弹出窗口的 index
			function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
			  if(validateForm.form()){
				  $table = table;
				  $topIndex = index;
				  jh.loading();
				  $("#inputForm").submit();
				  return true;
			  }
		
			  return false;
			}
        $(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
				    var exclusivePrice = $("#exclusivePrice").val();
                    var reg = /(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/;
				    console.log(reg.test(exclusivePrice));
                    if(!reg.test(exclusivePrice)){
                        alert('只能输入数字，小数点后只能保留两位');
                        $("#exclusivePrice").val("");
                        jh.close();//关闭dialog
                        return false;
                    }

					jh.post("${ctx}/purchasergoodsrel/purchaserGoodsRel/updatePrice",$('#inputForm').serialize(),function(data){
						if(data.success){
	                    	jh.success(data.msg);
                            jh.close($topIndex);//关闭dialog
                            parent.location.reload($topIndex); 刷新父页面
	                    }else{
            	  			jh.error(data.msg);
	                    }
					})
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		});

    </script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-body">
                	<form:form id="inputForm" modelAttribute="purchaserGoodsRel" class="form-horizontal">
                        <form:hidden path="goodsSku"/>
                        <form:hidden path="purchaserId"/>
                        <sys:message content="${message}"/>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><font color="red">*</font>企业专享价：</label>
                                <div class="col-sm-4">
                                    <form:input id="exclusivePrice" path="exclusivePrice" htmlEscape="false" class="form-control required marketPrice" />
                                </div>
                            </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
    <style>
        .upload{
            position: absolute; opacity: 0; filter:Alpha(opacity=0);
        }
        .active{
            position: relative; opacity: 1; filter:Alpha(opacity=1);
        }
    </style>

</body>
</html>