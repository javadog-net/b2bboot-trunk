package com.haier.order.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haier.order.dto.*;
import com.haier.order.service.OrderWebService;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class OrderServiceUtils {


    private static String remoteUrl = "http://10.135.108.211:8625/";

    private static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(100, TimeUnit.SECONDS).
            readTimeout(100, TimeUnit.SECONDS).
            writeTimeout(100, TimeUnit.SECONDS).build();

    /**
     * 保存订单
     * @param object
     * @return
     * @throws Exception
     */
    public static RetDTO batchSaveSmallShopOrder4others(Object object)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.batchSaveSmallShopOrder4others(object);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }

    /**
     * 订单管理查询订单
     * @param qm
     * @param nowPage
     * @param pageShow
     * @return
     * @throws Exception
     */
    public static RetDTO getListGetByCondition(OrderMainDTO qm, int nowPage, int pageShow)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.getListGetByCondition(qm,nowPage,pageShow);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }

    /**
     * 获取某状态下的订单数量
     * @param qm
     * @return
     */
    public static RetDTO getCustomerCount(OrderMainDTO qm)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.getCustomerCount(qm);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }

    /**
     * 获取订单详情
     * @param orderMainUuid
     * @return
     * @throws Exception
     */
    public static RetDTO getOrderViewModelByUuid(String orderMainUuid)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.getOrderViewModelByUuid(orderMainUuid);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }

    /**
     * 去支付
     * @param orderMainUuid
     * @param customerUuid           会员uuid
     * @param bankCode               银行支付码
     * @param payProductCode         支付产品码
     * @param bankCardNo             银行卡号
     * @param bankAccountName        付款银行卡账户名
     * @return
     * @throws Exception
     */
    public static RetDTO toPayOrder(String orderMainUuid,String customerUuid,String bankCode,String payProductCode
            ,String bankCardNo,String bankAccountName)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.toPayOrder(orderMainUuid,customerUuid,bankCode,payProductCode,bankCardNo,bankAccountName);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }



    /**
     * 将订单推送到GVS
     * @param uuid
     * @return
     * @throws Exception
     */
    public static RetDTO toGVS(String uuid)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.toGVS(uuid);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }


    /**
     * 将订单推送到LES
     * @param uuid
     * @return
     * @throws Exception
     */
    public static RetDTO toLES(String uuid)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.toLES(uuid);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }

    /**
     * 订单审核
     * @param uuid                       订单uuid
     * @param checkedStatus              审核状态   0未审核   1审核通过    2审核不通过
     * @return
     */
    public static RetDTO checkedOrder(String uuid, String checkedStatus,KpInvoiceDTO kpInvoiceDTO)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.checkedOrder(uuid,checkedStatus,kpInvoiceDTO);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }


    /**
     * 支付成功修改订单状态
     * @param orderIds
     * @param oper
     * @param operType
     * @return
     * @throws Exception
     */
    public static RetDTO batchPayOrder(List<String> orderIds, String oper, String operType)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.batchPayOrder(orderIds,oper,operType);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }

    /**
     * 异步回调修改订单状态
     * @param params
     * @return
     * @throws Exception
     */
    public static RetDTO notify(String params )throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.notify(params);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }

    /**
     * 保存云仓信息
     * @param params
     * @return
     * @throws Exception
     */
    public static RetDTO saveWarehouseStatus(String params )throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.saveWarehouseStatus(params);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }





    /**
     * 通过订单uuids获取订单信息
     * @param orderUuids
     * @return Map<String, OrderMainDTO>
     */
    public static RetDTO getOrderMapByUuids(List<String> orderUuids)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.getOrderMapByUuids(orderUuids);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }

    /**
     * 修改订单金额
     * @param orderMainUuid
     * @param orderMoney
     * @param oper
     * @return
     * @throws Exception
     */
    public static RetDTO updateOrderMoney(String orderMainUuid,String orderMoney, String oper)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.updateOrderMoney(orderMainUuid,orderMoney,oper);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }

    /**
     * 通过订单租uuid获取订单数据
     * @param payOrderUuid
     * @return
     */
    public static RetDTO getByOrderGroupUuid(String payOrderUuid)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.getByOrderGroupUuid(payOrderUuid);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }


    /**
     * 取消订单
     * @param orderUuid               订单uuid
     * @param cancelReason             原因
     * @param customerUuid             会员uuid
     * @param operTypeCustomer         操作人类型   1会员   2商户   3平台
     * @return
     */
    public static RetDTO closeOrder(String orderUuid, String cancelReason, String customerUuid, String operTypeCustomer)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.closeOrder(orderUuid,cancelReason,customerUuid,operTypeCustomer);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }

    /**
     * 会员确认收货
     * @param orderUuid
     * @param customerUuid
     * @param operTypeCustomer
     * @return
     * @throws Exception
     */
    public static RetDTO arrivalOrder(String orderUuid, String customerUuid, String operTypeCustomer)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.arrivalOrder4others(orderUuid,customerUuid,operTypeCustomer);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }

    /**
     * 获取订单评价列表（分页）
     * @param qm
     * @param nowPage
     * @param pageShow
     * @return
     */
    public static RetDTO getOrderMainAppraiseByCondition(Object qm,int nowPage,int pageShow)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.getOrderMainAppraiseByCondition(qm,nowPage,pageShow);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }

    /**
     * 获取订单评价详情
     * @param uuid
     * @return
     * @throws Exception
     */
    public static RetDTO getOrderMainAppraiseByUuid(String uuid)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.getOrderMainAppraiseByUuid(uuid);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }

    /**
     * 通过订单uuid获取订单评价信息
     * @param orderUuid
     * @return
     */
    public static RetDTO getOrderMainAppraiseByOrderUuid(String orderUuid)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.getOrderMainAppraiseByOrderUuid(orderUuid);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }

    /**
     * 获取商品评价列表（分页）
     * @param qm
     * @param nowPage
     * @param pageShow
     * @return
     */
    public static RetDTO getProductAppraiseByCondition(ProductAppraiseDTO qm, int nowPage, int pageShow)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.getProductAppraiseByCondition(qm,nowPage,pageShow);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }

    /**
     * 获取商品评价详情
     * @param uuid
     * @return
     */
    public static RetDTO getProductAppraiseByUuid(String uuid)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.getProductAppraiseByUuid(uuid);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);

        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }
    /**
     * 保存商品评价
     * @param product 商品评价实体
     * @return String 实体uuid
     */
    public static RetDTO saveProductAppraise(ProductAppraiseDTO product)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        try {
            Call<Object> call = service.saveProductAppraise(product);
            Object requestDTOS = call.execute().body();
            String jsonStr= JSON.toJSONString(requestDTOS);

            RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

            return retDTO;
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return null;
    }

    /**
     * 保存订单评价
     * @param order 商品评价实体
     * @return String 实体uuid
     */
    public static RetDTO saveOrderMainAppraise(OrderMainAppraiseDTO order)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        try {
            Call<Object> call = service.saveOrderMainAppraise(order);
            Object requestDTOS = call.execute().body();
            String jsonStr= JSON.toJSONString(requestDTOS);

            RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

            return retDTO;

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return null;
    }

    /**
     * 开票接口
     * @param kpInvoiceDTO
     * @return
     * @throws Exception
     */
    public static RetDTO openerInvoice(KpInvoiceDTO kpInvoiceDTO)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        try {
            Call<Object> call = service.openerInvoice(kpInvoiceDTO);
            Object requestDTOS = call.execute().body();
            String jsonStr= JSON.toJSONString(requestDTOS);

            RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

            return retDTO;

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return null;
    }

    /**
     * 发票查询
     * @param orderId
     * @return
     * @throws Exception
     */
    public static RetDTO queryInvoice(String orderId)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        try {
            Call<Object> call = service.queryInvoice(orderId);
            Object requestDTOS = call.execute().body();
            String jsonStr= JSON.toJSONString(requestDTOS);

            RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

            return retDTO;

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return null;
    }

    /**
     * 发票冲红
     * @param orderId  订单编号
     * @param reason   冲红原因
     * @return
     * @throws Exception
     */
    public static RetDTO chInvoice(String orderId,String reason)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        try {
            Call<Object> call = service.chInvoice(orderId,reason);
            Object requestDTOS = call.execute().body();
            String jsonStr= JSON.toJSONString(requestDTOS);

            RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

            return retDTO;

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return null;
    }

    /**
     * 异步回调修改订单状态
     * @return
     * @throws Exception
     */
    public static RetDTO invoiceNotify(String params)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.invoiceNotify(params);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);
        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }

    /**
     * 生成客户注册成功发送短信提醒登录的定时任务
     *
     * @param mobile
     * @param content
     * @return
     */
    public static RetDTO sendMsg(String mobile, String content,String time)throws Exception{
        // 1、生成调用retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
        // 3、组织参数
        // 4、执行调用
        Call<Object> call = service.sendMsg(mobile,content,time);
        Object requestDTOS = call.execute().body();
        String jsonStr= JSON.toJSONString(requestDTOS);
        RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);

        return retDTO;
    }


}
