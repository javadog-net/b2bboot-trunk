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
			<td class="width-15 active"><label class="pull-right"><font color="red">*</font>订单编号：</label></td>
			<td class="width-35">
				${orders.orderSn}
			</td>
			<td class="width-15 active"><label class="pull-right">店铺名称：</label></td>
			<td class="width-35">
					${orders.storeName}
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">经销商名称：</label></td>
			<td class="width-35">
					${orders.dealerName}
			</td>
			<td class="width-15 active"><label class="pull-right">快捷通账号：</label></td>
			<td class="width-35">
					${orders.kjtAccount}
			</td>
		</tr>
		<tr>

			<td class="width-15 active"><label class="pull-right"><font color="red">*</font>支付单号：</label></td>
			<td class="width-35">
					${orders.paySn}
			</td>
			<td class="width-15 active"><label class="pull-right"><font color="red">*</font>采购商名称：</label></td>
			<td class="width-35">
					${orders.purchaserName}
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">付款（支付）时间：</label></td>
			<td class="width-35">
					${orders.paymentDate}
			</td>
			<td class="width-15 active"><label class="pull-right"><font color="red">*</font>商品总价格：</label></td>
			<td class="width-35">
					${orders.goodsAmount}
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right"><font color="red">*</font>订单总价格：</label></td>
			<td class="width-35">
					${orders.orderAmount}
			</td>
			<td class="width-15 active"><label class="pull-right">评价状态：</label></td>
			<td class="width-35">
				${fns:getDictLabel(orders.evaluationState, 'yes_no', '否')}
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right"><font color="red">*</font>追加评价状态：</label></td>
			<td class="width-35">
				${fns:getDictLabel(orders.evaluationState, 'yes_no', '否')}
			</td>
			<td class="width-15 active"><label class="pull-right"><font color="red">*</font>订单状态：</label></td>
			<td class="width-35">
				${fns:getDictLabel(orders.orderState, 'order_state', '')}
			</td>
		</tr>
		<tr>

			<td class="width-15 active"><label class="pull-right">采购商地址：</label></td>
			<td class="width-35">

			</td>
			<td class="width-15 active"><label class="pull-right">运单信息：</label></td>
			<td class="width-35">
					${orders.shippingInfo}
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">申请开发票的状态 ：</label></td>
			<td class="width-35">
				${fns:getDictLabel(orders.isApplyInvoice, 'yes_no', '否')}
			</td>
			<td class="width-15 active"><label class="pull-right">是否已开发票：</label></td>
			<td class="width-35">
				${fns:getDictLabel(orders.isInvoiceFinished, 'yes_no', '否')}
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">发票信息：</label></td>
			<td class="width-35">

			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">确认收货时间：</label></td>
			<td class="width-35">
					${orders.deliveryTime}
			</td>
			<td class="width-15 active"></td>
			<td class="width-35" ></td>
		</tr>
		<table class="table table-bordered">
			<tr>
				<td>商品编码</td>
				<td>商品名称</td>
				<td>价格</td>
				<td>数量</td>
			</tr>
		<c:forEach items="${orderGoodsList}" var="orderGoods" >
			<tr>

				<td>${orderGoods.goodsCode}</td>
				<td >${orderGoods.goodsName}</td>
				<td >${orderGoods.payPrice}</td>
				<td >${orderGoods.num}</td>
			</tr>
		</c:forEach>
		</table>
		</tbody>
	</table>
</body>
</html>