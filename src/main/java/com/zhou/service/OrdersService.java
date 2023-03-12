package com.zhou.service;

import com.zhou.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 54086
* @description 针对表【orders(订单表)】的数据库操作Service
* @createDate 2022-08-19 16:14:17
*/
public interface OrdersService extends IService<Orders> {

    public void submit(Orders orders);
}
