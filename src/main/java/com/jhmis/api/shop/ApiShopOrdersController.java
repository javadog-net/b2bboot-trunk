package com.jhmis.api.shop;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 所有的api控制器类名命名为API+目录名+模块名+Controller
 * 主要是可能会与后台的控制器重名
 * 订单控制器
 */

@RestController
@RequestMapping("/api/shop/orders")
public class ApiShopOrdersController {

}
