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
			<td class="width-15 ">订单详情</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">订单编号：</label></td>
			<td class="width-35">
				${order.orderId}
			</td>
			<td class="width-15 active"><label class="pull-right">订单状态：</label></td>
			<td class="width-35">
					${order.orderStatusName}
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">订单类型：</label></td>
			<td class="width-35">
					${order.orderTypeName}
			</td>
			<td class="width-15 active"><label class="pull-right">下单时间：</label></td>
			<td class="width-35">
					${order.createOpeTime}
			</td>

		</tr>
		<tr>

			<td class="width-15 active"><label class="pull-right">订单金额：</label></td>
			<td class="width-35">
				${order.totalPrice}
			</td>
			<td class="width-15 active"><label class="pull-right">支付方式：</label></td>
			<td class="width-35">
					${order.payTypeName}
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">买家名称：</label></td>
			<td class="width-35">
				${order.customerName}
			</td>
			<td class="width-15 active"><label class="pull-right">账户类型：</label></td>
			<td class="width-35">
					${order.productMoney}
			</td>
		</tr>

		<tr>
			<td class="width-15 ">四方信息</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">售达方：</label></td>
			<td class="width-35">
				${order.billToPartyName}
			</td>
			<td class="width-15 active"><label class="pull-right">送达方：</label></td>
			<td class="width-35">
				${order.shipToPartyName}
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">开票方：</label></td>
			<td class="width-35">
				${order.billingName}
			</td>
			<td class="width-15 active"><label class="pull-right">付款方：</label></td>
			<td class="width-35">
				${order.paymentName}
			</td>
		</tr>

		<tr>
			<td class="width-15 ">发票信息</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">发票类型：</label></td>
			<td class="width-35">
				${order.invoiceTypeName}
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">发票抬头：</label></td>
			<td class="width-35">
				${order.purchaserInvoice.companyName}
			</td>
			<td class="width-15 active"><label class="pull-right">纳税人识别号：</label></td>
			<td class="width-35">
				${order.purchaserInvoice.taxCode}
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">联系电话：</label></td>
			<td class="width-35">
				${order.purchaserInvoice.regPhone}
			</td>
			<td class="width-15 active"><label class="pull-right">开户行：</label></td>
			<td class="width-35">
				${order.purchaserInvoice.regBname}
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">账号：</label></td>
			<td class="width-35">
				${order.purchaserInvoice.regBaccount}
			</td>
			<td class="width-15 active"><label class="pull-right">地址：</label></td>
			<td class="width-35">
				${order.purchaserInvoice.regAddr}
			</td>
		</tr>
		<tr>
			<td class="width-15 ">物流信息</td>
		</tr>
		<tr>

			<td class="width-15 active"><label class="pull-right">物流单号：</label></td>
			<td class="width-35">
				${order.warehouseCode ? order.warehouseCode : '暂无'}
			</td>
		</tr>
		<tr>
			<td class="width-15 ">物流跟踪</td>
		</tr>
		<c:if test="${order != null && order.cloudWarehouseDTOList != null}">
			<c:forEach items="${order.cloudWarehouseDTOList}" var="w" >
				<tr>
					<td class="width-15">
						${w.operdate}
					</td>
					<td class="width-15">
						${w.operator}
					</td>
					<td class="width-15">
						${w.remark}
					</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${order.cloudWarehouseDTOList == null}">
			<tr>暂无数据</tr>
		</c:if>

		<table class="table table-bordered">
			<tr>
				采购单明细
			</tr>
			<tr>
				<td>商品编码</td>
				<td>商品名称</td>
				<td>购买数量</td>
				<td>购买价</td>
			</tr>
			<c:forEach items="${order.orderDetailList}" var="orderGoods" >
				<tr>

					<td>${orderGoods.skuNo}</td>
					<td >${orderGoods.productName}</td>
					<td >${orderGoods.buyNum}</td>
					<td >${orderGoods.basePrice}</td>
				</tr>
			</c:forEach>
		</table>
		<table class="table table-bordered">
			<tr>
				订单日志
			</tr>
			<tr>
				<td>操作时间</td>
				<td>操作人类型</td>
				<td>操作类型</td>
				<td>操作前数据</td>
				<td>操作后数据</td>
				<td>操作内容</td>
			</tr>
			<c:if test="${order != null && order.orderLogDTO != null}">
				<c:forEach items="${order.orderLogDTO}" var="log" >
					<tr>

						<td>${log.createOpeTime}</td>
						<td >${log.operTypeName}</td>
						<td >${log.opeTypeName}</td>
						<td >${log.beforeContent}</td>
						<td >${log.afterContent}</td>
						<td >${log.note}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${order.orderLogDTO == null}">
				<tr>暂无数据</tr>
			</c:if>
		</table>
		</tbody>
	</table>
</body>
</html>