<%@ page contentType="text/html;charset=UTF-8" %>
<script>

  
  function audit(id){
	   if(id == undefined){
	        id = $("#id").val();
            $("#bidInfoId").val(id);

	    }
	    jh.open({
	        type: 1,
	        area: ['400px','200px'],
	        title:"审核",
	        content:$("#auditBox").html() ,
	        scrollbar: false,
	        btn: ['确定', '关闭'],
	        btn1: function(index, layero){
	        	debugger
	            var inputForm = layero.find("#auditForm");
	            var sel = inputForm.find("input[name='checkStatus']:checked").val();
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
	            jh.loading('  正在提交审核，请稍等...');
	           // inputForm.submit();
	          jh.post("${ctx}/bid/bidCheckRecord/issave",inputForm.serialize(),function(data){
	                if(data.success){
	                    jh.success(data.msg);
						  window.location='${ctx}/bid/bidInfo/list';
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
	            inputForm.find("input[name='checkStatus']").change(function(){
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

</script>