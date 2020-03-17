<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="ani"/>
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
                    jh.post("${ctx}/shop/orders/save",$('#inputForm').serialize(),function(data){
                        if(data.success){
                            $table.bootstrapTable('refresh');
                            jh.success(data.msg);
                            jh.close($topIndex);//关闭dialog

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
<body class="bg-white">
	<table class="table table-bordered">
		<tbody>

			<tr>
				<td class="width-15 ">商品信息</td>
			</tr>
			<tr>
				<td class="width-15 active"><label class="pull-right">订单编号：</label></td>
				<td class="width-35">
					${productAppraiseDTO.orderId}
				</td>
				<td class="width-15 active"><label class="pull-right">商品sku：</label></td>
				<td class="width-35">
						${productAppraiseDTO.productSkuNo}
				</td>
			</tr>
			<tr>
				<td class="width-15 active"><label class="pull-right">商品价格：</label></td>
				<td class="width-35">
					${product.price}
				</td>
				<td class="width-15 active"><label class="pull-right">商品名称：</label></td>
				<td class="width-35">
					${product.goodsName}
				</td>
			</tr>
			<tr>
				<td class="width-15 active"><label class="pull-right">商户名称：</label></td>
				<td class="width-35">
					${product.storeName}
				</td>

			</tr>

			<table class="table table-bordered">
				<tr>
					<td class="width-15">评价信息</td>

				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">评分：</label></td>
					<td class="width-35">
						${productAppraiseDTO.appScore} 分
					</td>

				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">评价内容：</label></td>
					<td class="width-35">
						${productAppraiseDTO.appContent}
					</td>

				</tr>

			</table>
			<table class="table table-bordered">
				<tr>
					<td class="width-15">服务评分</td>

				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">物流服务：</label></td>
					<td class="width-35">
						${orderAppraise.speedScore} 5分
					</td>

				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">服务态度：</label></td>
					<td class="width-35">
						${orderAppraise.serviceScore} 5分
					</td>

				</tr>

			</table>
		</tbody>
	</table>
</body>
</html>